package com.ddcompany.innercore.actions

import com.ddcompany.innercore.AdbPusher
import com.ddcompany.innercore.ICIcons
import com.ddcompany.innercore.ICService
import com.ddcompany.innercore.extensions.killApp
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ActionICStop : AnAction(ICIcons.STOP_16) {
    override fun actionPerformed(event: AnActionEvent) {
        val service = ICService.get(event.project!!)
        if (!service.serial.isEmpty()) {
            val device = AdbPusher.getDevice(service.serial)
            device?.killApp(AdbPusher.IC_PKG)
        }
    }
}
