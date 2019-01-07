package com.ddcompany.innercore;

import com.google.common.io.CharStreams;
import com.intellij.openapi.application.ex.ApplicationManagerEx;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.commons.codec.Charsets;
import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.RemoteFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdbHelper {

    private static SimpleDateFormat androidDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public static void executeApp(JadbDevice device, String pkg) throws IOException, JadbException {
        device.executeShell("monkey", "-p", pkg, "-c", "android.intent.category.LAUNCHER", "1");
    }

    public static void terminateApp(JadbDevice device, String pkg) throws IOException, JadbException {
        device.executeShell("am", "force-stop", pkg);
    }

    public static void restartApp(JadbDevice device, String pkg) throws IOException, JadbException {
        terminateApp(device, pkg);
        executeApp(device, pkg);
    }

    public static JadbDevice getDeviceFromSerial(String serial){
        try {
            JadbConnection connection = new JadbConnection();
            for (JadbDevice device : connection.getDevices()) {
                if(device.getSerial().equals(serial))
                    return device;
            }
        } catch (IOException | JadbException e) {
            e.printStackTrace();
        }

        return null;
    }

    //
    // Pushing
    //

    public static boolean isValidFile(VirtualFile virtualFile) {
        return !virtualFile.getPath().contains(".idea") && !virtualFile.getPath().contains(".git");
    }

    public static void pushToDevice(Project project, VirtualFile file){
        JadbDevice jadbDevice = AdbHelper.getDeviceFromSerial(ICProjectService.get(project).getSerial());
        String modDir = ICProjectService.get(project).getModDirectory();

        if(jadbDevice == null)
            return;

        if(modDir.isEmpty())
            return;

        AdbHelper.pushToDevice(project, file, jadbDevice,
                modDir, ICProjectService.get(project).isSmartPush(), ICProjectService.get(project).isRunInnerCore());
    }

    public static void pushToDevice(Project project, VirtualFile file, JadbDevice device, String dirName, boolean smartPush, boolean startInnerCore) {

        ICProjectService.get(project).setLast(file.getPath());
        ICProjectService.get(project).setSerial(device.getSerial());

        Runnable runnable = () -> {
            ProgressIndicator progressIndicator = ProgressManager.getInstance().getProgressIndicator();
            double fraction = 0.0;

            progressIndicator.setText("Getting files to push...");
            List<VirtualFile> filesToPush = getFilesToPush(new ArrayList<>(), file, device, smartPush, dirName, project);

            for (VirtualFile virtualFile : filesToPush) {
                try {
                    if (virtualFile.isDirectory()) {
                        device.executeShell("mkdir", getPathInAndroidFS(virtualFile.getPath(), dirName, project));
                        continue;
                    }

                    pushToDevice(device, new File(virtualFile.getPath()), new RemoteFile(getPathInAndroidFS(virtualFile.getPath(), dirName, project)));
                    progressIndicator.setText("Pushing " + virtualFile.getPath() + "...");
                    progressIndicator.setFraction(fraction / filesToPush.size());
                    fraction += 1.0;
                } catch (IOException | JadbException e) {
                    e.printStackTrace();
                }
            }

            if (startInnerCore) {
                progressIndicator.setText("Running InnerCore...");
                try {
                    AdbHelper.restartApp(device, "com.zhekasmirnov.innercore");
                } catch (IOException | JadbException e) {
                    e.printStackTrace();
                }
            }

        };

        ApplicationManagerEx.getApplicationEx().runProcessWithProgressSynchronously(runnable, "Pushing mod to device", true, project);
    }

    public static List<VirtualFile> getFilesToPush(List<VirtualFile> list, VirtualFile virtualFile, JadbDevice device,
                                                   boolean smartPush, String dirName, Project project) {
        if(!virtualFile.isDirectory()){
            list.add(virtualFile);
            return list;
        }

        Date date = new Date();
        List<VirtualFile> virtualFiles = VfsUtil.collectChildrenRecursively(virtualFile);

        for (VirtualFile virtualFile1 : virtualFiles) {
            if (!isValidFile(virtualFile1)) continue;

            if (!smartPush || validToPush(virtualFile1, device, date, dirName, project))
                list.add(virtualFile1);
        }

        return list;
    }

    private static void pushToDevice(JadbDevice device, File file, RemoteFile remoteFile) throws IOException {
        try {
            device.push(file, remoteFile);
        } catch (JadbException e) {
            pushToDevice(device, file, remoteFile);
        }
    }

    private static String getPathInAndroidFS(String path, String dirName, Project project) {
        return "/storage/emulated/0/games/com.mojang/mods/" + dirName + path.replaceFirst(project.getBasePath(), "");
    }

    public static boolean validToPush(VirtualFile virtualFile, JadbDevice device, Date date, String dirName, Project project) {
        try {
            if (virtualFile.isDirectory()) return true;

            String path = getPathInAndroidFS(virtualFile.getPath(), dirName, project);
            InputStream inputStream = device.executeShell("ls -l", path);
            String output = CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8));

            if (output.endsWith("No such parentFile or directory")) return true;

            String[] parts = output.split(" ");
            String modifyDate = parts[parts.length - 3] + " " + parts[parts.length - 2];
            File file = new File(virtualFile.getPath());

            //Если даты изменения файлов не совпадают
            if (!modifyDate.equals(androidDateFormat.format(file.lastModified())))
                return true;

            //Если дата изменения файла равна текущей
            if (file.lastModified() == date.getTime())
                return true;


        } catch (IOException | JadbException e) {
            e.printStackTrace();
        }

        return false;
    }

}
