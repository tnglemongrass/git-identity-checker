import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project

class NotificationHelpers {
    companion object {
        private const val GROUP_ID = "GitIdentityChecker Notification"
        private const val TITLE = "Local git identity checker"

        fun showInfo(message: String, project: Project) {
            val notification = Notification(GROUP_ID, TITLE, message, NotificationType.INFORMATION)
            Notifications.Bus.notify(notification, project)
        }

        fun showWarning(message: String, project: Project) {
            val notification = Notification(GROUP_ID, TITLE, message, NotificationType.WARNING)
            Notifications.Bus.notify(notification, project)
        }
    }
}