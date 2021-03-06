package com.ddcompany.innercore

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "ICService", storages = [Storage("innerCore.xml")])
class ICService : PersistentStateComponent<ICService> {
    companion object {
        val logger = Logger.getInstance("ICPlugin")

        fun get(project: Project): ICService {
            return ServiceManager.getService(project, ICService::class.java)
        }
    }

    var dir = ""
    var lastPath = ""
    var serial = ""
    var mustRunApp = true
    var mustPushToRoot = false
    var pushBlackList = ArrayList<String>()

    override fun getState() = this

    override fun loadState(state: ICService) {
        XmlSerializerUtil.copyBean(state, this)
    }
}
