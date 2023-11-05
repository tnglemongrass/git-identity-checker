package com.github

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class ProjectOpenedListener : StartupActivity.DumbAware {
    override fun runActivity(project: Project) {
        val isGitRepo = Helpers.isGitProject(project)
        if (isGitRepo) {
            val root = Helpers.getAllGitRoots(project)[0]
            if (!Helpers.hasLocalGitUserAndMail(root)) {
                val content = "No repo-specific git user.name and user.email found."
                val notification = Notification("GitIdentityChecker", "Local git identity checker", content, NotificationType.WARNING)
                Notifications.Bus.notify(notification, project)
            }
        }
    }
}
