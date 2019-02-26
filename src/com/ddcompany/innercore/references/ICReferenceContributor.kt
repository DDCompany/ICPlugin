package com.ddcompany.innercore.references

import com.intellij.lang.javascript.psi.JSLiteralExpression
import com.intellij.lang.javascript.psi.JSReferenceExpression
import com.intellij.patterns.PlatformPatterns.psiElement
import com.intellij.psi.PsiReferenceContributor
import com.intellij.psi.PsiReferenceRegistrar

class ICReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(psiElement(JSReferenceExpression::class.java), IdReferenceProvider())
        registrar.registerReferenceProvider(psiElement(JSLiteralExpression::class.java), TextureReferenceProvider())
    }
}