package com.ddcompany.innercore.forms

import com.ddcompany.innercore.mod.ICItemType
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import java.util.regex.Pattern

class DialogCreateItem(
        private val project: Project,
        private val editor: Editor) : DialogWrapper(project) {

    private val panel = PanelCreateItem()
    private val namePattern = Pattern.compile("^[^\"]+")
    private val idPattern = Pattern.compile("[a-zA-Z0-9]+")
    private val numberPattern = Pattern.compile("[0-9]+")
    private val numberOrEmptyPattern = Pattern.compile("[0-9]*")

    init {
        this.title = "Create New Item..."
        this.init()

        ICItemType.values().forEach {
            panel.comboTypes.addItem(it)
        }

//        TexturesIndex.eachTexture(TexturesIndex.FOR_ITEMS, project) { _, texture ->
//            panel.comboTextures.addItem(texture)
//        }
    }

    override fun createCenterPanel() = panel.area!!

    override fun doValidate(): ValidationInfo? {
        if (!idPattern.matcher(panel.id).matches())
            return ValidationInfo("Invalid ID")
//        else if (ICService.get(project).mod.findItemIds().any { println(it.id); it.id == panel.id })
//            return ValidationInfo("ID already used")

        if (!namePattern.matcher(panel.name).matches())
            return ValidationInfo("Invalid Name")

        if (!numberOrEmptyPattern.matcher(panel.burnTime).matches())
            return ValidationInfo("Burn Time must be number")

        if ((panel.comboTypes.selectedItem as ICItemType) == ICItemType.FOOD
                && !numberPattern.matcher(panel.saturation).matches())
            return ValidationInfo("Invalid Saturation")

        return null
    }

    override fun doOKAction() {
        val document = editor.document

        WriteCommandAction.runWriteCommandAction(project) {
            panel.apply {
                val textureIcon = "none" //TODO:
                val builder = StringBuilder()
                builder.append("IDRegistry.genItemID(\"$id\");")
                when (panel.comboTypes.selectedItem as ICItemType) {
                    ICItemType.SIMPLE -> {
                        builder.append("\nItem.createItem(\"$id\", \"$name\", {name: \"${textureIcon}\", meta: ${textureIcon}}, {stack: $stackSize});")
                    }
                    ICItemType.FOOD -> {
                        builder.append("\nItem.createFoodItem(\"$id\", \"$name\", {name: \"${textureIcon}\", meta: ${textureIcon}}, {stack: $stackSize, food: $saturation});")
                    }
                }

                if (burnTime.isNotEmpty())
                    builder.append("\nRecipes.addFurnaceFuel(ItemID.$id, 0, $burnTime);")

                if (isRenderAsTool)
                    builder.append("\nItem.setToolRender(ItemID.$id, true);")

                if (isGlint)
                    builder.append("\nItem.setGlint(ItemID.$id, true);")

                if (isClickableOnLiquid)
                    builder.append("\nItem.setLiquidClip(ItemID.$id, true);")

                val caretOffset = editor.caretModel.offset
                document.insertString(caretOffset, builder.toString())
                editor.caretModel.moveToOffset(caretOffset + builder.length)

                close(0)
            }
        }
    }
}