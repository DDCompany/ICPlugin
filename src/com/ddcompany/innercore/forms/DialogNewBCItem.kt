package com.ddcompany.innercore.forms

import com.ddcompany.innercore.extensions.appendIf
import com.ddcompany.innercore.project.structure.buildConfig.BCItemBuildDir
import com.ddcompany.innercore.project.structure.buildConfig.BCItemRes
import com.ddcompany.innercore.project.structure.buildConfig.BCType
import com.ddcompany.innercore.project.structure.buildConfig.BCItem
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import javax.swing.DefaultListModel

class DialogNewBCItem(
        project: Project?,
        private val listModel: DefaultListModel<BCItem>) : DialogWrapper(project) {
    private val panel = PanelNewBCItem()

    init {
        this.title = "New Structure Item"
        this.init()
    }

    override fun doValidate(): ValidationInfo? {
        if (this.panel.dir.isEmpty())
            return ValidationInfo("Directory is not valid")

        if (this.panel.type == BCType.SRC && this.panel.targetFile.isEmpty())
            return ValidationInfo("Target parentFile is not valid")

        return null
    }

    override fun doOKAction() {
        if (panel.type == BCType.SRC)
            this.listModel.addElement(BCItemBuildDir(this.panel.dir.appendIf("/"),
                    this.panel.targetFile.appendIf(".js")))
        else
            this.listModel.addElement(BCItemRes(this.panel.type, this.panel.dir.appendIf("/")))

        this.close(0)
    }

    override fun createCenterPanel() = panel.area!!
}
