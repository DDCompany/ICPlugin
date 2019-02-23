package com.ddcompany.innercore.completion

import com.ddcompany.innercore.ICService
import com.ddcompany.innercore.mod.ICMod
import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.javascript.completion.JSCompletionUtil
import com.intellij.lang.javascript.completion.JSLookupPriority
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.string
import com.intellij.util.ProcessingContext

class IdCompletionContributor : CompletionContributor() {
    init {
        this.extend(CompletionType.BASIC, psiElement().withParent(psiElement().withText(string().startsWith("BlockID"))), BlockIdCompletionProvider())
        this.extend(CompletionType.BASIC, psiElement().withParent(psiElement().withText(string().startsWith("ItemID"))), ItemIdCompletionProvider())
    }

    class BlockIdCompletionProvider : CompletionProvider<CompletionParameters>() {
        override fun addCompletions(params: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
            val project = params.editor.project ?: return
            val service = ICService.get(project)
            service.mod.findBlockIds().forEach { block ->
                block.variations.forEachIndexed { index, variation ->
                    val lookupString = block.id + "$" + index
                    var builder = LookupElementBuilder.create(lookupString)
                            .withPresentableText(block.id)
                            .withTypeText("\"${variation.name}\"")
                            .withInsertHandler { context, _ ->
                                val editor = context.editor
                                val caretOffset = editor.caretModel.offset
                                val document = editor.document
                                val pos = caretOffset - lookupString.length

                                document.deleteString(pos, caretOffset)
                                document.insertString(pos, block.id)
                                editor.caretModel.moveToOffset(pos + block.id.length)
                            }
                            .withIcon(variation.icon)

                    builder = if (block.mod is ICMod) {
                        builder.appendTailText("$$index[${block.place}]", true)
                    } else builder.appendTailText("$$index[${block.mod.name}:${block.place}]", true)

                    resultSet.addElement(JSCompletionUtil.withJSLookupPriority(builder, JSLookupPriority.NESTING_LEVEL_1))
                }
            }
        }
    }

    class ItemIdCompletionProvider : CompletionProvider<CompletionParameters>() {
        override fun addCompletions(params: CompletionParameters, context: ProcessingContext, resultSet: CompletionResultSet) {
            val project = params.editor.project ?: return
            val service = ICService.get(project)
            service.mod.findItemIds().forEach { item ->
                var builder = LookupElementBuilder.create(item.id)
                        .withTypeText("\"${item.name}\"")
                        .withIcon(item.icon)

                builder = if (item.mod is ICMod) {
                    builder.appendTailText("[${item.place}]", true)
                } else builder.appendTailText("[${item.mod.name}:${item.place}]", true)

                resultSet.addElement(JSCompletionUtil.withJSLookupPriority(builder, JSLookupPriority.NESTING_LEVEL_1))
            }
        }
    }
}