package com.ddcompany.innercore.completion

import com.intellij.codeInsight.template.impl.DefaultLiveTemplatesProvider

class ICTemplateProvider : DefaultLiveTemplatesProvider {
    override fun getDefaultLiveTemplateFiles() = arrayOf("liveTemplates/snippets.xml")

    override fun getHiddenLiveTemplateFiles(): Array<String>? = null
}