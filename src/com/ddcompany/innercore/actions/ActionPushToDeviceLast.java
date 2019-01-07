package com.ddcompany.innercore.actions;

import com.ddcompany.innercore.AdbHelper;
import com.ddcompany.innercore.ICProjectService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.vfs.LocalFileSystem;

public class ActionPushToDeviceLast extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        String lastDir = ICProjectService.get(e.getProject()).getLast();

        if(lastDir.isEmpty())
            return;

        AdbHelper.pushToDevice(e.getProject(), LocalFileSystem.getInstance().findFileByPath(lastDir));
    }
}
