package com.ddcompany.innercore.actions

import com.ddcompany.innercore.ICIcons
import com.ddcompany.innercore.forms.DialogPush
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys

class ActionPush : AnAction(ICIcons.PHONE_16) {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.getData(CommonDataKeys.PROJECT)
        DialogPush(project, project?.baseDir!!).show()
    }
}
