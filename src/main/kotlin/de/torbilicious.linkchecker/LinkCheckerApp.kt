package de.torbilicious.linkchecker

import de.torbilicious.linkchecker.checker.LinkChecker
import de.torbilicious.linkchecker.config.ConfigLoader
import de.torbilicious.linkchecker.notification.DesktopNotifier
import de.torbilicious.linkchecker.notification.NotificationType
import de.torbilicious.linkchecker.site.Site
import de.torbilicious.linkchecker.site.SiteState.DOWN
import de.torbilicious.linkchecker.site.SiteState.UP
import java.net.URL

val checker = LinkChecker()
val notifier = DesktopNotifier()

fun main(args: Array<String>) {
    val sites = ConfigLoader().getConfig().sites.map { Site(URL(it)) }

    var running = true

    while (running) {
        checkSites(sites)

        Thread.sleep(10000)

//        running = false
    }
}

fun checkSites(sites: List<Site>) {
    sites.forEach {
        println("checking ${it.url}")

        it.state = checker.check(it)

        println("new: ${it.state}")
        println("old: ${it.previousState}")

        if (it.state == DOWN && it.previousState == UP) {
            notifier.show("${it.url} is down!", duration = 5000, type = NotificationType.WARNING)
        } else if (it.state == UP && it.previousState == DOWN) {
            notifier.show("${it.url} is up again!", duration = 5000)
        }
    }

    println("Checked ${sites.size} site(s)")
}
