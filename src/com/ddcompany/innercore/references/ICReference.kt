package com.ddcompany.innercore.references

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.ResolveResult

open class ICReference(element: PsiElement, range: TextRange)
    : PsiReferenceBase<PsiElement>(element, range), PsiPolyVariantReference {
    override fun resolve(): PsiElement? {
        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun multiResolve(p0: Boolean): Array<ResolveResult> {
        return emptyArray()
    }

    override fun getVariants(): Array<Any> {
        return emptyArray()
    }
}