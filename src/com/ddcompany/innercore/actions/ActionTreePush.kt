package com.ddcompany.innercore.actions

import com.ddcompany.innercore.ICIcons
import com.ddcompany.innercore.forms.DialogPush
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiFileSystemItem

class ActionTreePush : AnAction(ICIcons.PHONE) {
    override fun actionPerformed(event: AnActionEvent) {
        val navigatable = event.getData(CommonDataKeys.NAVIGATABLE)

        if (navigatable != null)
            DialogPush(event.project, (navigatable as PsiFileSystemItem).virtualFile).show()
    }
}
