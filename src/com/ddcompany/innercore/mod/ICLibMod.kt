package com.ddcompany.innercore.mod

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.intellij.lang.javascript.psi.JSCallExpression
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileManager
import com.intellij.openapi.vfs.newvfs.BulkFileListener
import com.intellij.openapi.vfs.newvfs.events.VFileEvent
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiManager
import com.intellij.psi.PsiRecursiveElementVisitor
import java.io.FileReader

open class ICLibMod {
    var librariesFile: VirtualFile? = null
    /**
     * Список модулей с исходным кодом (описываются в 'buildDirs' и 'compile')
     */
    private val modules = ArrayList<ICSrcModule>()
    /**
     * Список модулей с текстурами блоком, предметов, мобов и тд
     */
    private val resources = ArrayList<ICResModule>()
    private val jsonParser = JsonParser()
    lateinit var project: Project
    lateinit var modFile: VirtualFile
    var name = "Unknown"

    var inilialized = false
        private set
    var valid = false
        private set

    companion object {
        const val BLOCK_PROTO_REG_FUN = "Block.setPrototype"
        const val BLOCK_REG_FUN = "Block.createBlock"
        const val BLOCK_WITH_ROT_REG_FUN = "Block.createBlockWithRotation"

        const val ITEM_PROTO_REG_FUN = "Item.setPrototype"
        const val ITEM_REG_FUN = "Item.createItem"
    }

    fun init(project: Project, file: VirtualFile) {
        this.project = project
        this.inilialized = true
        this.modFile = file
        val buildConfig = modFile.findChild("build.config")

        if (buildConfig != null) {
            valid = true
            this.refreshBuildConfig(buildConfig, modFile)
            this.loadInfo(modFile)

            val connection = project.messageBus.connect()
            connection.subscribe(VirtualFileManager.VFS_CHANGES, object : BulkFileListener {
                override fun after(events: MutableList<out VFileEvent>) {
                    events.forEach {
                        if (it.path == buildConfig.path)
                            refreshBuildConfig(buildConfig, modFile)
                    }
                }
            })
        }
    }

    fun findBlockIds(): ArrayList<ICBlock> {
        val list = ArrayList<ICBlock>()
        this.find { file, element, module, mod ->
            if (element is JSCallExpression) {
                val text = element.text
                if (text.startsWith(BLOCK_PROTO_REG_FUN)
                        || text.startsWith(BLOCK_REG_FUN)
                        || text.startsWith(BLOCK_WITH_ROT_REG_FUN)) {
                    ICBlock.buildAll(list, element, module?.name ?: file.name, mod)
                }
            }
        }

        return list
    }

    fun findItemIds(): ArrayList<ICItem> {
        val list = ArrayList<ICItem>()
        this.find { file, element, module, mod ->
            if (element is JSCallExpression) {
                val text = element.text
                if (text.startsWith(ITEM_PROTO_REG_FUN)
                        || text.startsWith(ITEM_REG_FUN)) {
                    ICItem.buildAll(list, element, module?.name ?: file.name, mod)
                }
            }
        }

        return list
    }

    fun visit(file: VirtualFile, module: ICSrcModule?, func: (file: VirtualFile, element: PsiElement, module: ICSrcModule?, mod: ICLibMod) -> Unit) {
        val psiFile = PsiManager.getInstance(this.project).findFile(file)!!
        val self = this
        psiFile.accept(object : PsiRecursiveElementVisitor() {
            override fun visitElement(element: PsiElement) {
                super.visitElement(element)
                func(file, element, module, self)
            }
        })
    }

    open fun find(func: (file: VirtualFile, element: PsiElement, module: ICSrcModule?, mod: ICLibMod) -> Unit) {
        this.modules.forEach { module ->
            module.includes().forEach {
                visit(it, module, func)
            }
        }
    }

    open fun isInResourcesDir(file: VirtualFile, type: ResourceType) =
            this.resources.any { file.path.startsWith(it.file.path + "/" + type.dir) }

    private fun initSrcModules(json: JsonObject, projectDir: VirtualFile) {
        json.getAsJsonArray("buildDirs")?.forEach {
            if (it is JsonObject)
                ICSrcModule.build(projectDir, it)?.let { it1 -> this.modules.add(it1) }
        }
    }

    private fun initResModules(json: JsonObject, projectDir: VirtualFile) {
        json.getAsJsonArray("resources")?.forEach {
            if (it is JsonObject) {
                if (it.has("resourceType") && it.get("resourceType").asString == "resource") {
                    ICResModule.build(projectDir, it)?.let { it1 -> this.resources.add(it1) }
                }
            }
        }
    }

    private fun loadInfo(projectDir: VirtualFile) {
        val file = projectDir.findOrCreateChildData(this, "mod.info")
        if (file.inputStream.readBytes().isEmpty())
            file.setBinaryContent("{}".toByteArray())

        val json = this.jsonParser.parse(FileReader(file.path)).asJsonObject
        val nameElem = json.get("name")
        if (nameElem != null)
            this.name = nameElem.asString
    }

    private fun refreshBuildConfig(buildConfig: VirtualFile, projectDir: VirtualFile) {
        this.modules.clear()
        this.resources.clear()
        this.librariesFile = null

        this.jsonParser.parse(FileReader(buildConfig.path))?.let {
            if (it.isJsonObject) {
                val json = it.asJsonObject
                this.initSrcModules(json, projectDir)
                this.initResModules(json, projectDir)

                json.getAsJsonObject("defaultConfig")?.let { obj ->
                    val string = obj.get("libraryDir").asString
                    this.librariesFile = projectDir.findChild(string)
                }
            }
        }
    }
}