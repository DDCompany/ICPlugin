package com.ddcompany.innercore.actions;

import com.ddcompany.innercore.forms.DialogAdbDeviceSelect;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;

public class ActionPushToDevice extends AnAction {

    public ActionPushToDevice() {
        super(AllIcons.General.ArrowDown);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        new DialogAdbDeviceSelect(e.getData(CommonDataKeys.PROJECT)).show();
    }
}
