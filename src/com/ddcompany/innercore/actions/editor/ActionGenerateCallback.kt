package com.ddcompany.innercore.actions.editor

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.JBPopupFactory

class ActionGenerateCallback : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getData(CommonDataKeys.EDITOR) as Editor
        JBPopupFactory.getInstance().createListPopup(CallbackGroupsPopupStep(editor)).showInBestPositionFor(e.dataContext)
    }
}