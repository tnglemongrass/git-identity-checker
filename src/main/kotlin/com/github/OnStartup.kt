package com.github

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class OnStartup : StartupActivity {
    companion object {
        private val LOG: Logger = Logger.getInstance(OnStartup::class.java)
    }
    override fun runActivity(project: Project) {
        LOG.info("Starting GitIdentityChecker")
        LOG.info("Found ${Helpers.getAllGitRoots(project).size} git root(s)")

        for (root in Helpers.getAllGitRoots(project)) {
            if (!Helpers.hasLocalGitUserAndMail(root)) {
                val content = "No repo-specific git user.name and/or user.email found in ${root.path}."
                val notification = Notification("GitIdentityChecker Notification", "Local git identity checker", content, NotificationType.WARNING)
                Notifications.Bus.notify(notification, project)
                LOG.warn("No repo-specific git user.name and/or user.email found in ${root.path}")
            }
        }
    }
}
