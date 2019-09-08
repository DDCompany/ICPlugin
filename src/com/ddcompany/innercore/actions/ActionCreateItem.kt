package com.ddcompany.innercore.actions

import com.ddcompany.innercore.forms.DialogCreateItem
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor

class ActionCreateItem : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        event.project ?: return
        val editor = event.getData(CommonDataKeys.EDITOR) as Editor

        val dialog = DialogCreateItem(event.project!!, editor)
        dialog.show()
    }
}