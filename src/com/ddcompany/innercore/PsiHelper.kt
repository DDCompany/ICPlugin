package com.ddcompany.innercore

import com.intellij.lang.javascript.psi.*
import com.intellij.psi.PsiElement
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiTreeUtil

object PsiHelper {
//    fun elementFromText(text: String, project: Project): PsiFile? {
//        val fileType = FileTypeManager.getInstance().getFileTypeByExtension("js")
//        return PsiFileFactory.getInstance(project).createFileFromText("_______.js", JavascriptLanguage.INSTANCE, text, true, false)
//    }

    fun parentsTreeFollow(element: PsiElement, vararg classes: Class<out PsiElement>): PsiElement? {
        var current = element
        classes.forEach {
            val parent = current.parent
            if (it.isAssignableFrom(parent::class.java))
                current = parent
            else return null
        }

        return current
    }

    /**
     * Ищет значения выражения
     */
    fun findValues(expr: JSReferenceExpression): List<PsiElement> {
        val resolve = expr.reference?.resolve() ?: return emptyList()

        if (resolve is JSParameter) {
            resolve.declaringFunction ?: return emptyList()

            var paramIndex = -1
            resolve.declaringFunction?.parameterVariables?.forEachIndexed { index, elem ->
                if (elem == resolve) {
                    paramIndex = index
                    return@forEachIndexed
                }
            }

            if (paramIndex > -1) {
                val list = ArrayList<PsiElement>()
                ReferencesSearch.search(resolve.declaringFunction!!).forEach {
                    val funcCall = it.element.parent
                    if (funcCall is JSCallExpression) {
                        val arg = funcCall.arguments.getOrNull(paramIndex)

                        if (arg != null)
                            list.add(arg)
                    }
                }

                return list
            }
        } else if (resolve is JSVariable) {
            return listOf(resolve.initializer ?: return emptyList())
        } else if (resolve is JSReferenceExpression) {
            return findValues(resolve)
        }

        return emptyList()
    }

    fun getReturnOfProperty(psiElement: JSCallExpression, propName: String): JSExpression? {
        val obj = psiElement.arguments[1]
        if (obj is JSObjectLiteralExpression) {
            val property = obj.findProperty(propName)
            if (property != null) {
                return PsiTreeUtil.findChildOfType(property, JSReturnStatement::class.java)?.expression
            }
        }

        return null
    }

    /**
     * Проверяет, является ли элемент строковым литералом с не пустым значением
     */
    fun isValidStrLiteral(element: PsiElement?) = element is JSLiteralExpression && element.isStringLiteral && !element.stringValue.isNullOrBlank()
}