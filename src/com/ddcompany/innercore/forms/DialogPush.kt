package com.ddcompany.innercore.forms

import com.ddcompany.innercore.AdbPusher
import com.ddcompany.innercore.ICService
import com.ddcompany.innercore.extensions.restartApp
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.openapi.vfs.VirtualFile

class DialogPush(private val project: Project?, private val file: VirtualFile) : DialogWrapper(project) {
    private val panel = PanelPush(project, file)

    init {
        this.title = "Push Mod to Device"
        this.init()
    }

    override fun doValidate(): ValidationInfo? {
        if (panel.dir.isEmpty())
            return ValidationInfo("Mod Directory is not valid!")

        if (panel.listDevices.selectedValue == null)
            return ValidationInfo("Device is not selected!")

        return null
    }

    override fun doOKAction() {
        val device = panel.listDevices.selectedValue
        FileDocumentManager.getInstance().saveAllDocuments()

        val service = ICService.get(project!!)
        service.dir = this.panel.dir
        service.mustRunApp = this.panel.mustRunApp()
        service.mustPushToRoot = this.panel.mustPushToRoot()
        service.serial = device.serial
        service.pushBlackList = this.panel.blackList
        service.launcherType = this.panel.launcherType

        AdbPusher.push(device, service.launcherType.modsDir, project, file)

        if (service.mustRunApp)
            device.restartApp(service.launcherType.pkg);

        this.close(0)
    }

    override fun createCenterPanel() = panel.area!!
}
