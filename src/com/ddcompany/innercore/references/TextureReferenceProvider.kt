package com.ddcompany.innercore.references

import com.ddcompany.innercore.PsiHelper
import com.ddcompany.innercore.extensions.rangeWithoutQuarts
import com.ddcompany.innercore.index.TexturesIndex
import com.ddcompany.innercore.mod.ResourceType
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.javascript.psi.*
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.util.ProcessingContext

class TextureReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        if (element is JSLiteralExpression) {
            var expr = PsiHelper.parentsTreeFollow(element,
                    JSArrayLiteralExpression::class.java,
                    JSArrayLiteralExpression::class.java,
                    JSProperty::class.java,
                    JSObjectLiteralExpression::class.java,
                    JSArrayLiteralExpression::class.java,
                    JSArgumentList::class.java,
                    JSCallExpression::class.java)

            if (expr is JSCallExpression) {
                if (expr.text.startsWith("Block.createBlock") && element.textLength > 1)
                    return arrayOf(TextureReference(element, element.rangeWithoutQuarts(), ResourceType.TERRAIN_TEXT))
            } else {
                expr = PsiHelper.parentsTreeFollow(element,
                        JSArrayLiteralExpression::class.java,
                        JSArrayLiteralExpression::class.java,
                        JSProperty::class.java,
                        JSObjectLiteralExpression::class.java,
                        JSArrayLiteralExpression::class.java,
                        JSReturnStatement::class.java,
                        JSBlockStatement::class.java,
                        JSFunction::class.java)

                if (expr is JSFunction && expr.name == "getVariations") {
                    if (element.textLength > 1)
                        return arrayOf(TextureReference(element, element.rangeWithoutQuarts(), ResourceType.TERRAIN_TEXT))
                }
            }

            expr = PsiHelper.parentsTreeFollow(element,
                    JSProperty::class.java,
                    JSObjectLiteralExpression::class.java,
                    JSArgumentList::class.java,
                    JSCallExpression::class.java)

            if (expr is JSCallExpression && expr.text.startsWith("Item.createItem")) {
                if (element.textLength > 1)
                    return arrayOf(TextureReference(element, element.rangeWithoutQuarts(), ResourceType.ITEMS_TEXT))
            } else {
                expr = PsiHelper.parentsTreeFollow(element,
                        JSProperty::class.java,
                        JSObjectLiteralExpression::class.java,
                        JSReturnStatement::class.java,
                        JSBlockStatement::class.java,
                        JSFunction::class.java)

                if (expr is JSFunction && expr.name == "getTexture") {
                    if (element.textLength > 1)
                        return arrayOf(TextureReference(element, element.rangeWithoutQuarts(), ResourceType.ITEMS_TEXT))
                }
            }
        }

        return PsiReference.EMPTY_ARRAY
    }

    class TextureReference(
            private val element: JSLiteralExpression,
            range: TextRange,
            private val type: ResourceType) : ICReference(element, range) {

        override fun resolve(): PsiElement? {
            val name = element.stringValue!!
            val parent = element.parent
            val meta = if (parent is JSArrayLiteralExpression) {
                val expr = parent.expressions.getOrNull(1)
                if (expr is JSLiteralExpression && expr.isNumericLiteral) {
                    (expr.value as Long).toInt()
                } else 0
            } else 0

            val project = element.containingFile.project
            val file =
                    TexturesIndex.getTexture(name + "_" + meta, type, project)?.file ?: return element

            return PsiManager.getInstance(project).findFile(file)
        }

        override fun getVariants(): Array<Any> {
            val list = ArrayList<LookupElement>()
            val project = element.containingFile.project

            TexturesIndex.eachTexture(type.id, project) { key, texture ->
                list.add(LookupElementBuilder.create(key)
                        .withTypeText("${texture.width}x${texture.height}")
                        .withInsertHandler { context, _ ->
                            val editor = context.editor
                            val caretOffset = editor.caretModel.offset
                            val document = editor.document
                            val psiFile = PsiDocumentManager.getInstance(project).getPsiFile(document)
                            var psiElem = psiFile?.findElementAt(caretOffset)?.parent?.parent

                            when (type) {
                                ResourceType.TERRAIN_TEXT -> {
                                    println(psiElem)
                                    if (psiElem is JSArrayLiteralExpression) {
                                        if (psiElem.expressions.size > 1) {
                                            val expr = psiElem.expressions[1]
                                            if (expr is JSLiteralExpression)
                                                document.replaceString(expr.textOffset, expr.textOffset + expr.textLength, texture.meta)
                                        } else document.insertString(psiElem.textOffset + psiElem.textLength - 1, ", ${texture.meta}")
                                    }
                                }
                                ResourceType.ITEMS_TEXT -> {
                                    psiElem = psiElem?.parent
                                    if (psiElem is JSObjectLiteralExpression) {
                                        val metaProperty = psiElem.findProperty("meta")
                                        if (metaProperty != null) {
                                            val expr = metaProperty.value
                                            if (expr is JSLiteralExpression)
                                                document.replaceString(expr.textOffset, expr.textOffset + expr.textLength, texture.meta)
                                        } else document.insertString(psiElem.textOffset + psiElem.textLength - 1, ", meta: ${texture.meta}")
                                    }
                                }
                            }

                            document.replaceString(caretOffset - key.length, caretOffset, texture.name)
                        }
                        .withIcon(texture.scaledIcon))
            }
            return list.toTypedArray()
        }

        override fun isSoft(): Boolean {
            return true
        }
    }
}