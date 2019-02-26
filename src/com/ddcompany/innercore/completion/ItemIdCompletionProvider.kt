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