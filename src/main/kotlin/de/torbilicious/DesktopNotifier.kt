package de.torbilicious

import de.torbilicious.NotificationType.*
import dorkbox.notify.Notify
import dorkbox.notify.Pos

enum class NotificationType {
    DEFAULT,
    INFORMATION,
    CONFIRM,
    ERROR,
    WARNING
}

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
            NotificationType.INFORMATION -> notify.showInformation()
            NotificationType.CONFIRM -> notify.showConfirm()
            NotificationType.ERROR -> notify.showError()
            NotificationType.WARNING -> notify.showWarning()
        }
    }
}