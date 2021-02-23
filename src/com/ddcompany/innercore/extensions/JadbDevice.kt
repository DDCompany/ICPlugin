package com.ddcompany.innercore.extensions

import se.vidstige.jadb.JadbDevice

fun JadbDevice.mkdir(path: String) {
    this.executeShell("mkdir", "-p", path)
}

fun JadbDevice.execApp(pkg: String) {
    this.executeShell("monkey", "-p", pkg, "-c", "android.intent.category.LAUNCHER", "1")
}

fun JadbDevice.killApp(pkg: String) {
    this.executeShell("am", "force-stop", pkg)
}

fun JadbDevice.restartApp(pkg: String) {
    this.killApp(pkg)
    this.execApp(pkg)
}