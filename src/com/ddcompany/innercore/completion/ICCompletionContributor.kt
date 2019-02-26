package com.ddcompany.innercore.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.patterns.StandardPatterns.string

class ICCompletionContributor : CompletionContributor() {
    init {
        this.extend(CompletionType.BASIC, psiElement().withParent(psiElement().withText(string().startsWith("BlockID"))), BlockIdCompletionProvider())
        this.extend(CompletionType.BASIC, psiElement().withParent(psiElement().withText(string().startsWith("ItemID"))), ItemIdCompletionProvider())
    }
}