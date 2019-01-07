package com.ddcompany.innercore.forms;

import com.ddcompany.innercore.AdbHelper;
import com.ddcompany.innercore.ICProjectService;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import se.vidstige.jadb.JadbDevice;

import javax.swing.*;

public class DialogAdbDeviceSelect extends DialogWrapper {
    private PanelAdbDeviceSelect panelAdbDeviceSelect;
    private Project project;

    public DialogAdbDeviceSelect(@Nullable Project project) {
        super(project);
        this.project = project;
        panelAdbDeviceSelect = new PanelAdbDeviceSelect(project);

        this.setTitle("Push Mod to Device...");
        this.init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return panelAdbDeviceSelect.getPanel();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (panelAdbDeviceSelect.getList().getSelectedValue() == null)
            return new ValidationInfo("Device is not selected!");

        if (panelAdbDeviceSelect.getDirName().isEmpty())
            return new ValidationInfo("Mods Folder is not set!");

        return null;
    }

    @Override
    protected void doOKAction() {
        JadbDevice jadbDevice = panelAdbDeviceSelect.getList().getSelectedValue();
        if (jadbDevice != null) {
            FileDocumentManager.getInstance().saveAllDocuments();

            AdbHelper.pushToDevice(project, project.getBaseDir(), jadbDevice, panelAdbDeviceSelect.getDirName(), panelAdbDeviceSelect.isSmartPush(),
                    panelAdbDeviceSelect.isStartInnerCore());

            ICProjectService innerProjectService = ICProjectService.get(project);
            innerProjectService.setModDir(this.panelAdbDeviceSelect.getDirName());
            innerProjectService.setRunInnerCore(this.panelAdbDeviceSelect.isStartInnerCore());
            innerProjectService.setDevice(jadbDevice.getSerial());
            innerProjectService.setSmartPush(panelAdbDeviceSelect.isSmartPush());

            this.close(0);
        }
    }
}
