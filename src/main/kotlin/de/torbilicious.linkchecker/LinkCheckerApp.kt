package de.torbilicious.linkchecker

import de.torbilicious.linkchecker.config.ConfigLoader
import de.torbilicious.notification.DesktopNotifier

fun main(args: Array<String>) {
    val notifier = DesktopNotifier()

    val config = ConfigLoader().getConfig()

    config.sites.forEach {
        println(it)
    }

    notifier.show("Test notification")
}
