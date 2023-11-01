package com.github

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.intellij.openapi.ui.Messages

class ProjectOpenedListener : ProjectManagerListener {
    override fun projectOpened(project: Project) {
        val isGitRepo = Helpers.isGitProject(project)
        if (isGitRepo) {
            if (!Helpers.hasLocalGitUserAndMail(project)) {
                val content = "No repo-specific git user.name and user.mail found."
                val notification = Notification("GitIdentityChecker", "Local git identity checker", content, NotificationType.WARNING)
                Notifications.Bus.notify(notification, project)
            }
        }
    }
}
