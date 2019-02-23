package com.ddcompany.innercore.mod

import com.ddcompany.innercore.ICIcons
import com.intellij.lang.javascript.psi.JSArrayLiteralExpression
import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.lang.javascript.psi.JSObjectLiteralExpression
import java.awt.Image.SCALE_SMOOTH
import javax.swing.Icon
import javax.swing.ImageIcon

class ICBlockVariations(
        mod: ICLibMod,
        textureName: String,
        meta: Int,
        psiElement: JSObjectLiteralExpression) {
    companion object {
        fun build(element: JSObjectLiteralExpression, mod: ICLibMod): ICBlockVariations? {
            element.findProperty("texture")?.let {
                val value = it.value
                if (value is JSArrayLiteralExpression && !value.expressions.isEmpty()) {
                    //3 - фронтальная текстура
                    val expr = value.expressions[Math.min(value.expressions.size - 1, 3)]
                    if (expr is JSArrayLiteralExpression) {
                        val nameExpr = expr.expressions.getOrNull(0)
                        if (nameExpr is JSLiteralExpression && nameExpr.isStringLiteral) {
                            val metaExpr = expr.expressions.getOrNull(1)
                            val meta = if (metaExpr is JSLiteralExpression && metaExpr.isNumericLiteral) {
                                (metaExpr.value as Long).toInt()
                            } else 0
                            return ICBlockVariations(mod, nameExpr.stringValue!!, meta, element)
                        }
                    }
                }
            }
            return null
        }
    }

    val name: String
    val icon: Icon

    init {
        //NAME
        name = psiElement.findProperty("name")?.let {
            val expr = it.value
            if (expr is JSLiteralExpression && expr.isStringLiteral)
                expr.stringValue!!
            else ""
        } ?: ""

        //ICON
        val file = mod.findResByName(textureName, meta, ResourceType.TERRAIN_TEXT)
        icon = if (file != null) {
            ImageIcon(ImageIcon(file.path).image.getScaledInstance(16, 16, SCALE_SMOOTH))
        } else ICIcons.INVALID_16
    }
}