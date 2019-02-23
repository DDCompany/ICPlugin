package com.ddcompany.innercore.mod

import com.google.gson.JsonObject
import com.intellij.openapi.vfs.VirtualFile

class ICSrcModule private constructor(val file: VirtualFile, val name: String) {
    companion object {
        fun build(projectDir: VirtualFile, jsonObj: JsonObject): ICSrcModule? {
            val file = projectDir.findChild(jsonObj.get("dir").asString)
            if (file != null) {
                return ICSrcModule(file, file.name)
            }
            return null
        }
    }

    fun includes(): ArrayList<VirtualFile> {
        val list = ArrayList<VirtualFile>()
        val includesFile = this.file.findOrCreateChildData(this, ".includes")

        val string = String(includesFile.inputStream.readBytes())
        string.split("\n").forEach {
            if (it.isNotBlank() && !it.startsWith("#") && !it.startsWith("//")) {
//                println(it)
//                val child = File(file.path + File.separator + it)
//                println("\t" + child)
//                println("\t" + child.exists())
//                println("\t" + child.canRead())

                val path = if (it.endsWith(".js")) it else "$it.js"
                val child2 = this.file.findFileByRelativePath(path)
                if (child2 != null && !child2.isDirectory)
                    list.add(child2)
            }
        }

        return list
    }
}