package com.ddcompany.innercore.actions;

import com.ddcompany.innercore.AdbHelper;
import com.ddcompany.innercore.ICIcons;
import com.ddcompany.innercore.ICProjectService;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

import java.io.IOException;

public class ActionStopInnerCore extends AnAction {

    public ActionStopInnerCore() {
        super(ICIcons.INSTANCE.getSTOP());
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        JadbDevice jadbDevice = AdbHelper.getDeviceFromSerial(ICProjectService.get(anActionEvent.getProject()).getSerial());
        try {
            jadbDevice.executeShell("am force-stop com.zhekasmirnov.innercore");
        } catch (IOException | JadbException e) {
            e.printStackTrace();
        }
    }
}
