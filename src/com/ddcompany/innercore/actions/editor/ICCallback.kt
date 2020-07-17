package com.ddcompany.innercore.actions.editor

import com.ddcompany.innercore.ICService
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.util.ResourceUtil
import org.w3c.dom.Element
import javax.xml.parsers.DocumentBuilderFactory

val callbackGroups: List<CallbackGroup> by lazy {
    val url = ResourceUtil.getResource(ICCallback::class.java, "callbacks", "chat.xml")
    if (url == null) {
        ICService.logger.error("Error while loading callbacks: chat.xml not found")
        return@lazy emptyList<CallbackGroup>()
    }

    val dir = VfsUtil.findFileByURL(url)!!.parent
    val list = ArrayList<CallbackGroup>()
    val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    dir.children.forEach { file ->
        file.inputStream.use { stream ->
            val document = builder.parse(stream)
            val nodes = document.getElementsByTagName("callback-group")

            for (i in 0 until nodes.length) {
                val node = nodes.item(i)

                if (node is Element) {
                    if (!node.hasAttribute("name")) {
                        ICService.logger.error("Invalid name of callback group")
                        continue
                    }

                    val groupName = node.getAttribute("name")
                    val group = list.find { it.name == groupName } ?: CallbackGroup(groupName)
                    val callbackNodes = node.getElementsByTagName("callback")

                    for (k in 0 until callbackNodes.length) {
                        val callbackNode = callbackNodes.item(k)
                        if (callbackNode is Element) {
                            if (!callbackNode.hasAttribute("name")) {
                                ICService.logger.error("Invalid name of callback in '$groupName'")
                                continue
                            }

                            val name = callbackNode.getAttribute("name")
                            val arguments = callbackNode.getAttribute("arguments")

                            group.children.add(ICCallback(name, arguments ?: ""))
                        }
                    }

                    list.add(group)
                }
            }
        }
    }
    list
}

class CallbackGroup(val name: String) {
    val children = ArrayList<ICCallback>()
}

class ICCallback(val name: String, arguments: String = "") {
    val string = "Callback.addCallback(\"$name\", function($arguments) {});"
    val offset = string.length - 3

    override fun toString(): String {
        return string
    }
}
