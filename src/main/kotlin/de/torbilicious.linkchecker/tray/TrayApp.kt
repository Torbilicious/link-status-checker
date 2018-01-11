package de.torbilicious.linkchecker.tray

import java.awt.MenuItem
import java.awt.PopupMenu
import java.awt.SystemTray
import java.awt.TrayIcon
import javax.imageio.ImageIO


class TrayApp(private val onExit: () -> Unit = {}) {
    private val systemTray: SystemTray = SystemTray.getSystemTray()
    private var trayIcon: TrayIcon? = null

    private val imageMap = mapOf(
            0 to "number-zero-in-a-circle.png",
            1 to "number-one-in-a-circle.png",
            2 to "number-two-in-a-circle.png",
            3 to "number-three-in-a-circle.png",
            4 to "number-four-in-circular-button.png",
            5 to "number-five-in-circular-button.png"
    )

    var iconIndex = 0

    init {
        if (trayIcon != null) {
            systemTray.remove(trayIcon)
        }

        val iconPath = TrayApp::class.java.
                classLoader.
                getResource(imageMap[iconIndex])

        val image = ImageIO.read(iconPath)

        this.trayIcon = TrayIcon(image)

        val popupMenu = PopupMenu()
        val menuItem = MenuItem("Exit")
        menuItem.addActionListener {
            println("Clicked ${it.source}")

            exit()
        }

        popupMenu.add(menuItem)

        trayIcon?.toolTip = "Link Checker"
        trayIcon?.popupMenu = popupMenu

        systemTray.add(trayIcon)
    }

    private fun exit() {
        systemTray.remove(trayIcon)

        onExit()
    }

    fun displayMessage(text: String) {
        trayIcon?.displayMessage("Link Checker", text, TrayIcon.MessageType.INFO)
    }

    fun changeIcon(number: Int) {
        this.iconIndex = if (number > 5) {
            5
        } else {
            number
        }

        refreshIcon()
    }

    private fun refreshIcon() {
        val iconPath = TrayApp::class.java.
                classLoader.
                getResource(imageMap[iconIndex])

        val image = ImageIO.read(iconPath)

        trayIcon?.image = image
    }
}