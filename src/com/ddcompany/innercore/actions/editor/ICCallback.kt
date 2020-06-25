package com.ddcompany.innercore.actions.editor

import com.ddcompany.innercore.ICService
import com.google.common.io.Resources
import org.w3c.dom.Element
import java.io.File
import java.nio.charset.Charset
import javax.xml.parsers.DocumentBuilderFactory

val callbackGroups: ArrayList<CallbackGroup> by lazy {
    val list = ArrayList<CallbackGroup>()
    val dir = File(ICCallback::class.java.getResource("/callbacks").file)
    println(Resources.getResource("/callbacks"))
    println(Resources.toString(Resources.getResource("/callbacks"), Charset.forName("UTF-8")))
    val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

    dir.listFiles { file ->
        val document = builder.parse(file)
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
        true
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
