package de.torbilicious.linkchecker

import de.torbilicious.linkchecker.checker.LinkChecker
import de.torbilicious.linkchecker.config.ConfigLoader
import de.torbilicious.linkchecker.notification.DesktopNotifier
import de.torbilicious.linkchecker.notification.NotificationType
import de.torbilicious.linkchecker.site.Site
import de.torbilicious.linkchecker.site.SiteState.DOWN
import de.torbilicious.linkchecker.site.SiteState.UP
import de.torbilicious.linkchecker.tray.TrayApp
import java.net.URL


class LinkCheckerApp {
    private val checker = LinkChecker()
    private val notifier = DesktopNotifier()
    private val trayApp: TrayApp
    private var running = true

    init {
        val sites = ConfigLoader().getConfig().sites.map { Site(URL(it)) }

        this.trayApp = TrayApp(onExit = {
            println("Shutting down")

            running = false
        })

        while (running) {
            checkSites(sites)

            Thread.sleep(10000)
        }
    }

    private fun checkSites(sites: List<Site>) {
        sites.forEach {
            println("checking ${it.url}")

            it.state = checker.check(it)

            println("new: ${it.state}")
            println("old: ${it.previousState}")

            if (it.state == DOWN && it.previousState == UP) {
                notifier.show("${it.url} is down!", duration = 5000, type = NotificationType.WARNING)

//                trayApp.displayMessage("${it.url} is down!")
            } else if (it.state == UP && it.previousState == DOWN) {
                notifier.show("${it.url} is up again!", duration = 5000)

//                trayApp.displayMessage("${it.url} is up again!")
            }
        }

        println("Checked ${sites.size} site(s)")
    }
}

fun main(args: Array<String>) {
    LinkCheckerApp()
}

