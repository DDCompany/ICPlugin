package com.ddcompany.innercore.mod

import com.ddcompany.innercore.PsiHelper
import com.ddcompany.innercore.index.TexturesIndex
import com.ddcompany.innercore.mod.ICLibMod.Companion.ITEM_PROTO_REG_FUN
import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.lang.javascript.psi.JSObjectLiteralExpression
import com.intellij.lang.javascript.psi.JSReferenceExpression
import javax.swing.Icon

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
            PsiHelper.getReturnOfProperty(psiElement, "getIcon")
        } else psiElement.arguments[2]

        var textureName = ""
        var textureMeta = 0

        if (textureExpr is JSObjectLiteralExpression) {
            val nameExpr = textureExpr.findProperty("name")?.value
            val metaExpr = textureExpr.findProperty("meta")?.value

            if (PsiHelper.isValidStrLiteral(nameExpr)) {
                textureMeta = if (metaExpr is JSLiteralExpression && metaExpr.isNumericLiteral) {
                    (metaExpr.value as Long).toInt()
                } else 0
                textureName = (nameExpr as JSLiteralExpression).stringValue!!
            }
        }

        icon = TexturesIndex.getIcon(textureName + "_" + textureMeta, ResourceType.ITEMS_TEXT, mod.project)

        //NAME
        val nameExpr = if (psiElement.text.startsWith(ITEM_PROTO_REG_FUN)) {
            PsiHelper.getReturnOfProperty(psiElement, "getName")
        } else psiElement.arguments[1]

        name = if (PsiHelper.isValidStrLiteral(nameExpr)) {
            (nameExpr as JSLiteralExpression).stringValue!!
        } else ""
    }
}