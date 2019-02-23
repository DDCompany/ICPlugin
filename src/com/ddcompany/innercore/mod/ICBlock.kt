package com.ddcompany.innercore.mod

import com.ddcompany.innercore.PsiHelper
import com.ddcompany.innercore.mod.ICLibMod.Companion.BLOCK_PROTO_REG_FUN
import com.intellij.lang.javascript.psi.*

class ICBlock(
        val mod: ICLibMod,
        val id: String,
        val place: String,
        val psiElement: JSCallExpression) {
    companion object {
        fun buildAll(list: ArrayList<ICBlock>, element: JSCallExpression, place: String, mod: ICLibMod) {
            val args = element.arguments
            if (args.size > 1) {
                val idExpr = args[0]
                if (PsiHelper.isValidStrLiteral(idExpr)) {
                    list.add(ICBlock(mod, (idExpr as JSLiteralExpression).stringValue!!, place, element))
                } else if (idExpr is JSReferenceExpression) {
                    PsiHelper.findValues(idExpr).forEach {
                        if (PsiHelper.isValidStrLiteral(it))
                            list.add(ICBlock(mod, (idExpr as JSLiteralExpression).stringValue!!, place, element))
                    }
                }
            }
        }
    }

    val variations = ArrayList<ICBlockVariations>()

    init {
        val expr = if (this.psiElement.text.startsWith(BLOCK_PROTO_REG_FUN)) {
            PsiHelper.getReturnOfProperty(psiElement, "getVariations")
        } else this.psiElement.arguments[1]

        if (expr is JSArrayLiteralExpression) {
            expr.expressions.forEach { objExpr ->
                if (objExpr is JSObjectLiteralExpression) {
                    ICBlockVariations.build(objExpr, mod)?.let { it1 -> variations.add(it1) }
                }
            }
        }
    }
}