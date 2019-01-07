package com.ddcompany.innercore.project.structure.buildConfig

import com.ddcompany.innercore.extensions.delIf
import com.google.gson.JsonObject

class BCItemBuildDir(
        //Должно оканчиваться на "/"
        private val dir: String,
        private val targetSrc: String) : BCItem {
    override fun generate(buildConfig: BuildConfig) {
        val buildDirJson = JsonObject()
        buildDirJson.addProperty("dir", this.dir)
        buildDirJson.addProperty("targetSource", this.targetSrc)
        buildConfig.buildDirs.add(buildDirJson)

        val compileJson = JsonObject()
        compileJson.addProperty("path", this.targetSrc)
        compileJson.addProperty("sourceType", "mod")
        buildConfig.compile.add(compileJson)

        with(buildConfig.parentFile) {
            createChildDirectory(this, dir.delIf("/"))
                    .createChildData(this, ".includes")
            createChildData(this, targetSrc)
        }
    }

    override fun toString(): String {
        return "BCItemBuildDir: $dir"
    }
}