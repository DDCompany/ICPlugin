package com.ddcompany.innercore.project.structure.buildConfig

import com.ddcompany.innercore.extensions.delIf
import com.google.gson.JsonObject
import java.lang.IllegalArgumentException

class BCItemRes(
        private val type: BCType,
        //Должно оканчиваться на "/"
        private val dir: String) : BCItem {
    private val textureDirs = arrayOf("items-opaque", "terrain-atlas", "armor")

    override fun generate(buildConfig: BuildConfig) {
        val json = JsonObject()
        json.addProperty("path", this.dir)
        json.addProperty("resourceType", this.getResType())
        buildConfig.resources.add(json)

        with(buildConfig.parentFile.createChildDirectory(this, this.dir.delIf("/"))) {
            if (type != BCType.TEXTURES)
                return@with

            textureDirs.forEach {
                createChildDirectory(this, it)
            }
        }
    }

    private fun getResType(): String {
        return when (this.type) {
            BCType.GUI -> "gui"
            BCType.TEXTURES -> "resource"
            else -> throw IllegalArgumentException("Invalid BCType: ${this.type.name}")
        }
    }

    override fun toString(): String {
        return "BCItemRes: $dir"
    }
}