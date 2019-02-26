package com.ddcompany.innercore.completion

import com.ddcompany.innercore.ICService
import com.ddcompany.innercore.mod.ICMod
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionProvider
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.lang.javascript.completion.JSCompletionUtil
import com.intellij.lang.javascript.completion.JSLookupPriority
import com.intellij.util.ProcessingContext

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