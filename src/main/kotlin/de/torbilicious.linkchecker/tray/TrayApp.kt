package de.torbilicious.linkchecker.tray

import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import javax.imageio.ImageIO


class TrayApp(private val onExit: () -> Unit = {}) {
    private val systemTray: SystemTray = SystemTray.getSystemTray()
    private val trayIcon: TrayIcon

    init {
        @Suppress("SENSELESS_COMPARISON")
        if (systemTray == null) {
            throw RuntimeException("Unable to load SystemTray!")
        }

        val iconPath = TrayApp::class.java.
                classLoader.
                getResource("grid-world.png")

        val image = ImageIO.read(iconPath)

        this.trayIcon = TrayIcon(image)

        val popupMenu = PopupMenu()
        val menuItem = MenuItem("Exit")
        menuItem.addActionListener {
            println("Clicked ${it.source}")

            exit()
        }

        popupMenu.add(menuItem)

        trayIcon.toolTip = "Link Checker"
        trayIcon.popupMenu = popupMenu

        systemTray.add(trayIcon)
    }

    private fun exit() {
        systemTray.remove(trayIcon)

        onExit()
    }

    fun displayMessage(text: String) {
        trayIcon.displayMessage("Link Checker", text, TrayIcon.MessageType.INFO)
    }
}