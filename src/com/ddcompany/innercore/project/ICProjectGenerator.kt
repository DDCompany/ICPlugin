package com.ddcompany.innercore.project

import com.ddcompany.innercore.ICIcons
import com.ddcompany.innercore.actions.ActionAddLibrary
import com.ddcompany.innercore.extensions.createChild
import com.ddcompany.innercore.extensions.getPretty
import com.ddcompany.innercore.forms.ICProjectPeer
import com.ddcompany.innercore.project.structure.buildConfig.BuildConfig
import com.google.gson.JsonObject
import com.intellij.ide.util.projectWizard.WebProjectTemplate
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.platform.ProjectGeneratorPeer

class ICProjectGenerator : WebProjectTemplate<ICProjectData>() {
    override fun generateProject(project: Project, file: VirtualFile, data: ICProjectData, module: Module) {
        ApplicationManager.getApplication().runWriteAction {
            this.generateLauncher(file)
            this.generateBuildConfig(file, data)
            this.generateModInfo(file, data)
            this.generateConfig(file)
        }
        ActionAddLibrary.apply(project)
    }

    private fun generateConfig(file: VirtualFile) {
        file.createChild("config.json", "{\n\t\"enabled\": true\n}")
    }

    private fun generateBuildConfig(file: VirtualFile, data: ICProjectData) {
        BuildConfig(file, data.api, data.libsDir, data.items).write()
    }

    private fun generateLauncher(file: VirtualFile) {
        file.createChild("launcher.js", "Launch();")
    }

    private fun generateModInfo(file: VirtualFile, data: ICProjectData) {
        val json = JsonObject()
        json.addProperty("name", data.name)
        json.addProperty("author", data.author)
        json.addProperty("version", data.version)
        json.addProperty("description", data.description)

        file.createChild("mod.info", json.getPretty())
    }

    override fun createPeer(): ProjectGeneratorPeer<ICProjectData> {
        return ICProjectPeer()
    }

    override fun getName() = "Inner Core"

    override fun getLogo() = ICIcons.INNERCORE_16

    override fun getDescription() = ""
}
