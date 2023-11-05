package com.github

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class ProjectOpenedListener : StartupActivity.DumbAware {
    override fun runActivity(project: Project) {
        for (root in Helpers.getAllGitRoots(project)) {
            if (!Helpers.hasLocalGitUserAndMail(root)) {
                val content = "No repo-specific git user.name and/or user.email found in ${root.path}."
                val notification = Notification("GitIdentityChecker", "Local git identity checker", content, NotificationType.WARNING)
                Notifications.Bus.notify(notification, project)
            }
        }
    }
}
