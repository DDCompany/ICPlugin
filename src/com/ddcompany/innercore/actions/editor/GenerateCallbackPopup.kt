package com.ddcompany.innercore.actions.editor

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.ui.popup.PopupStep
import com.intellij.openapi.ui.popup.util.BaseListPopupStep
import com.intellij.util.DocumentUtil

class CallbackGroupsPopupStep(val editor: Editor) : BaseListPopupStep<CallbackGroup>("Generate Callback", callbackGroups) {
    override fun hasSubstep(selectedValue: CallbackGroup?) = true
    override fun onChosen(selectedValue: CallbackGroup, finalChoice: Boolean) = CallbacksPopupStep(editor, selectedValue)
    override fun getTextFor(value: CallbackGroup) = value.name
}

class CallbacksPopupStep(val editor: Editor, val group: CallbackGroup) : BaseListPopupStep<ICCallback>(group.name, group.children) {
    override fun onChosen(callback: ICCallback, p1: Boolean): PopupStep<*>? {
        DocumentUtil.writeInRunUndoTransparentAction {
            editor.document.insertString(editor.caretModel.offset, callback.string)
            editor.caretModel.moveToOffset(editor.caretModel.offset + callback.offset)
        }
        return PopupStep.FINAL_CHOICE
    }

    override fun getTextFor(value: ICCallback) = value.name
}