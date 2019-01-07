package com.ddcompany.innercore.actions;

import com.ddcompany.innercore.AdbHelper;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.pom.Navigatable;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;

public class ActionContextPushToDevice extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Navigatable navigatable = e.getData(CommonDataKeys.NAVIGATABLE);

        if (e.getProject() != null && navigatable != null)
            if (navigatable instanceof PsiFile)
                AdbHelper.pushToDevice(e.getProject(), ((PsiFile) navigatable).getVirtualFile());
            else if (navigatable instanceof PsiDirectory)
                AdbHelper.pushToDevice(e.getProject(), ((PsiDirectory) navigatable).getVirtualFile());
    }
}
