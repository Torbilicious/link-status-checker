package de.torbilicious

import de.torbilicious.notification.DesktopNotifier

fun main(args: Array<String>) {
    val notifier = DesktopNotifier()

    notifier.show("Test notification")
}
