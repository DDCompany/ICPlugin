package com.ddcompany.innercore.mod

import com.ddcompany.innercore.extensions.appendIf
import com.ddcompany.innercore.extensions.delIf
import com.google.gson.JsonObject
import com.intellij.openapi.vfs.VirtualFile

class ICSrcModule private constructor(val file: VirtualFile, val name: String) {
    companion object {
        fun build(projectDir: VirtualFile, jsonObj: JsonObject): ICSrcModule? {
            val file = projectDir.findChild(jsonObj.get("dir").asString)
            return if (file != null) {
                return ICSrcModule(file, file.name)
            } else null
        }
    }

    fun includes(): ArrayList<VirtualFile> {
        val list = ArrayList<VirtualFile>()
        val includesFile = this.file.findOrCreateChildData(this, ".includes")

        val string = String(includesFile.inputStream.readBytes())
        string.split("\n").forEach {
            if (it.isNotBlank() && !it.startsWith("#") && !it.startsWith("//")) {
                val path = it.delIf("\r").appendIf(".js")
                val child2 = this.file.findFileByRelativePath(path)

                if (child2 != null && !child2.isDirectory)
                    list.add(child2)
            }
        }

        return list
    }
}