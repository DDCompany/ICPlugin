package com.ddcompany.innercore.mod

import com.ddcompany.innercore.ICIcons
import com.ddcompany.innercore.PsiHelper
import com.ddcompany.innercore.mod.ICLibMod.Companion.ITEM_PROTO_REG_FUN
import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.lang.javascript.psi.JSObjectLiteralExpression
import com.intellij.lang.javascript.psi.JSReferenceExpression
import java.awt.Image
import javax.swing.Icon
import javax.swing.ImageIcon

class ICItem(
        val mod: ICLibMod,
        val id: String,
        val place: String,
        val psiElement: JSCallExpression) {
    companion object {
        fun buildAll(list: ArrayList<ICItem>, element: JSCallExpression, place: String, mod: ICLibMod) {
            val args = element.arguments
            if (args.size > 1) {
                val idExpr = args[0]
                if (PsiHelper.isValidStrLiteral(idExpr)) {
                    list.add(ICItem(mod, (idExpr as JSLiteralExpression).stringValue!!, place, element))
                } else if (idExpr is JSReferenceExpression) {
                    PsiHelper.findValues(idExpr).forEach {
                        if (PsiHelper.isValidStrLiteral(it))
                            list.add(ICItem(mod, (idExpr as JSLiteralExpression).stringValue!!, place, element))
                    }
                }
            }
        }
    }

    val icon: Icon
    val name: String

    init {
        //ICON
        val textureExpr = if (psiElement.text.startsWith(ITEM_PROTO_REG_FUN)) {
            PsiHelper.getReturnOfProperty(psiElement, "getTexture")
        } else psiElement.arguments[2]

        icon = if (textureExpr is JSObjectLiteralExpression) {
            val nameExpr = textureExpr.findProperty("name")?.value
            val metaExpr = textureExpr.findProperty("meta")?.value

            if (PsiHelper.isValidStrLiteral(nameExpr)) {
                val meta = if (metaExpr is JSLiteralExpression && metaExpr.isNumericLiteral) {
                    (metaExpr.value as Long).toInt()
                } else 0
                val textureName = (nameExpr as JSLiteralExpression).stringValue!!

                val file = mod.findResByName(textureName, meta, ResourceType.ITEMS_TEXT)
                if (file != null)
                    ImageIcon(ImageIcon(file.path).image.getScaledInstance(16, 16, Image.SCALE_SMOOTH))
                else ICIcons.INVALID_16
            } else ICIcons.INVALID_16
        } else ICIcons.INVALID_16

        //NAME
        val nameExpr = if (psiElement.text.startsWith(ITEM_PROTO_REG_FUN)) {
            PsiHelper.getReturnOfProperty(psiElement, "getName")
        } else psiElement.arguments[1]

        name = if (PsiHelper.isValidStrLiteral(nameExpr)) {
            (nameExpr as JSLiteralExpression).stringValue!!
        } else ""
    }
}