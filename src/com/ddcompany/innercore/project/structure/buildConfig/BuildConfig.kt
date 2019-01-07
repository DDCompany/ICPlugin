package com.ddcompany.innercore.project.structure.buildConfig

import com.ddcompany.innercore.extensions.createChild
import com.ddcompany.innercore.extensions.getPretty
import com.ddcompany.innercore.project.structure.ICApi
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.intellij.openapi.vfs.VirtualFile

class BuildConfig(
        val parentFile: VirtualFile,
        private val api: ICApi,
        private var libsDir: String,
        private val items: List<BCItem>) {
    private val defaultConfig = JsonObject()
    val buildDirs = JsonArray()
    val compile = JsonArray()
    val resources = JsonArray()

    init {
        if (!libsDir.endsWith("/"))
            libsDir += "/"

        this.setupDefConfig()
        this.applyItems()
    }

    fun write() {
        val json = JsonObject()
        json.add("defaultConfig", this.defaultConfig)
//        json.add("buildDirs", this.buildDirs)
//        json.add("compile", this.compile)
//        json.add("resources", this.resources)

        this.parentFile.createChild("build.config", json.getPretty())
    }

    private fun setupDefConfig() {
        with(this.defaultConfig) {
            this.addProperty("api", api.asText)
            this.addProperty("buildType", "develop")
            this.addProperty("libraryDir", libsDir)
        }
    }

    private fun applyItems() {
        this.items.forEach {
            it.generate(this)
        }
    }
}