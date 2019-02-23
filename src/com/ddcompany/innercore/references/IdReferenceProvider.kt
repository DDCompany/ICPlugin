package com.ddcompany.innercore.references

import com.ddcompany.innercore.ICService
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.util.ProcessingContext

class IdReferenceProvider : PsiReferenceProvider() {
    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val text = element.text

        return when {
            text.startsWith("BlockID.") ->
                arrayOf(BlockReference(element, TextRange(0, element.textLength)))
            text.startsWith("ItemID.") ->
                arrayOf(ItemReference(element, TextRange(0, element.textLength)))
            else -> PsiReference.EMPTY_ARRAY
        }
    }

    class BlockReference(element: PsiElement, private val range: TextRange) : ICReference(element, range) {
        override fun multiResolve(p0: Boolean): Array<ResolveResult> {
            val id = element.text.substring("BlockID.".length, range.endOffset)
            val block = ICService.get(element.project).mod.findBlockIds().find { it.id == id }

            return if (block != null)
                arrayOf(PsiElementResolveResult(block.psiElement))
            else emptyArray()
        }
    }

    class ItemReference(element: PsiElement, private val range: TextRange) : ICReference(element, range) {
        override fun multiResolve(p0: Boolean): Array<ResolveResult> {
            val id = element.text.substring("ItemID.".length, range.endOffset)
            val item = ICService.get(element.project).mod.findItemIds().find { it.id == id }

            return if (item != null)
                arrayOf(PsiElementResolveResult(item.psiElement))
            else emptyArray()
        }
    }
}