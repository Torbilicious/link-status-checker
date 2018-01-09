package de.torbilicious.linkchecker

import de.torbilicious.linkchecker.checker.LinkChecker
import de.torbilicious.linkchecker.config.ConfigLoader
import de.torbilicious.linkchecker.notification.DesktopNotifier
import de.torbilicious.linkchecker.site.Site
import java.net.URL

fun main(args: Array<String>) {
    val notifier = DesktopNotifier()

    val sites = ConfigLoader().getConfig().sites.map { Site(URL(it)) }

    val checker = LinkChecker()

    sites.forEach {
        println("checking ${it.url}")

        it.state = checker.check(it)

        println("new: ${it.state}")
        println("old: ${it.previousState}")
    }

    Thread.sleep(1500)

    sites.forEach {
        println("checking ${it.url}")

        it.state = checker.check(it)

        println("new: ${it.state}")
        println("old: ${it.previousState}")
    }

    notifier.show("Test notification")
}
