package com.ddcompany.innercore.forms

import com.ddcompany.innercore.project.ICProjectData
import com.intellij.ide.util.projectWizard.SettingsStep
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.platform.ProjectGeneratorPeer
import com.intellij.platform.WebProjectGenerator

class ICProjectPeer : ProjectGeneratorPeer<ICProjectData> {
    private val panel = ICProjectPanel()

    override fun validate(): ValidationInfo? {
        if (this.panel.author.isEmpty())
            return ValidationInfo("Author is not valid")

        if (this.panel.libsDir.isEmpty())
            return ValidationInfo("Libraries directory is not valid")

        if (this.panel.name.isEmpty())
            return ValidationInfo("Name is not valid")

        if (this.panel.version.isEmpty())
            return ValidationInfo("Version is not valid")

        //Описание может быть пустым

        return null
    }

    override fun getSettings(): ICProjectData {
        return ICProjectData(
                this.panel.name,
                this.panel.version,
                this.panel.author,
                this.panel.desc,
                this.panel.libsDir,
                this.panel.api,
                this.panel.bcItems)
    }

    override fun addSettingsStateListener(p0: WebProjectGenerator.SettingsStateListener) {
    }

    override fun buildUI(p0: SettingsStep) {
    }

    override fun isBackgroundJobRunning() = false

    override fun getComponent() = this.panel.area!!
}