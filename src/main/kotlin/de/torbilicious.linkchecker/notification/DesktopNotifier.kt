package de.torbilicious.linkchecker.notification

import de.torbilicious.linkchecker.notification.NotificationType.*
import dorkbox.notify.Notify
import dorkbox.notify.Pos

class DesktopNotifier {

    fun show(text: String,
             title: String = "Information",
             duration: Int = 3000,
             type: NotificationType = DEFAULT) {

        val notify = Notify.create()
                .title(title)
                .text(text)
                .darkStyle()
                .hideAfter(duration)
                .position(Pos.TOP_RIGHT)

        when (type) {
            DEFAULT -> notify.show()
            INFORMATION -> notify.showInformation()
            CONFIRM -> notify.showConfirm()
            ERROR -> notify.showError()
            WARNING -> notify.showWarning()
        }
    }
}