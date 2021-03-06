package com.ddcompany.innercore

import com.ddcompany.innercore.extensions.mkdir
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.MessageType
import com.intellij.openapi.ui.popup.Balloon
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.WindowManager
import com.intellij.ui.awt.RelativePoint
import se.vidstige.jadb.JadbConnection
import se.vidstige.jadb.JadbDevice
import se.vidstige.jadb.JadbException
import se.vidstige.jadb.RemoteFile
import java.io.File

object AdbPusher {
    const val HORIZON_PKG = "com.zheka.horizon"
    const val HORIZON_MODS_DIR = "/storage/emulated/0/games/horizon/packs/Inner_Core/innercore/mods/"

    fun push(device: JadbDevice, modsDir: String, project: Project, file: VirtualFile) {
        val service = ICService.get(project)
        val dir = modsDir + service.dir
        service.lastPath = file.path

        ProgressManager.getInstance().run(object : Task.Backgroundable(project, "Pushing...") {
            override fun run(indicator: ProgressIndicator) {
                var fraction = 0.0
                val startTime = System.currentTimeMillis()
                indicator.text = "Getting Files..."
                indicator.isIndeterminate = false

                val files = getForPush(file, service)
                files.forEachIndexed { index, it ->
                    var okPushed = false
                    while (!okPushed) {
                        try {
                            val path = formatPath(dir, it, project, service.mustPushToRoot)
                            if (it.isDirectory)
                                device.mkdir(path)
                            else device.push(File(it.path), RemoteFile(path))
                        } catch (e: JadbException) {
                            continue
                        }

                        okPushed = true
                    }

                    if (indicator.isCanceled)
                        return

                    indicator.text = "Pushing ($index of ${files.size})..."
                    indicator.text2 = it.path
                    indicator.fraction = ++fraction / files.size
                }

                val delta = System.currentTimeMillis() - startTime
                val deltaFormatted = if (delta > 1000) (delta / 1000).toString() + "s" else delta.toString() + "ms"
                JBPopupFactory.getInstance()
                        .createHtmlTextBalloonBuilder("Successfully pushed in $deltaFormatted ", MessageType.INFO, null)
                        .createBalloon()
                        .show(RelativePoint.getCenterOf(WindowManager.getInstance().getStatusBar(project).component), Balloon.Position.atRight)
            }
        })

    }

    fun getDevice(serial: String) =
            JadbConnection().devices.find { it.serial == serial }

    private fun formatPath(dir: String, file: VirtualFile, project: Project, toRoot: Boolean): String {
        return if (toRoot)
            "$dir/${file.name}"
        else dir + file.path.replaceFirst(project.basePath!!, "")
    }

    private fun getForPush(file: VirtualFile, service: ICService): List<VirtualFile> {
        if (!file.isDirectory)
            return listOf(file)

        val list = VfsUtil.collectChildrenRecursively(file)
        list.removeIf {
            val path = it.path
            service.pushBlackList.forEach { blackPath ->
                if (path.startsWith(blackPath))
                    return@removeIf true
            }

            return@removeIf false
        }

        return list
    }
}