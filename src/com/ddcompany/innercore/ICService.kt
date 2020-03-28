package com.ddcompany.innercore

import com.ddcompany.innercore.forms.LauncherType
import com.ddcompany.innercore.mod.ICMod
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import com.intellij.util.xmlb.annotations.Transient

@State(name = "ICService", storages = [Storage("innerCore.xml")])
class ICService : PersistentStateComponent<ICService> {
    companion object {
        fun get(project: Project): ICService {
            val service = ServiceManager.getService(project, ICService::class.java)
            if (!service.mod.inilialized)
                service.init(project)
            return service
        }
    }

    var launcherType = LauncherType.INNER_CORE
    var dir = ""
    var lastPath = ""
    var serial = ""
    var mustRunApp = true
    var mustPushToRoot = false
    var pushBlackList = ArrayList<String>()
    @Transient
    val mod = ICMod()

    private fun init(project: Project) {
        this.mod.init(project, project.baseDir)
    }

    override fun getState() = this

    override fun loadState(state: ICService) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
