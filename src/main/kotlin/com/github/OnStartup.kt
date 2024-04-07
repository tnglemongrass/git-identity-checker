package com.github

import NotificationHelpers
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity

class OnStartup : StartupActivity {
    companion object {
        private val LOG: Logger = Logger.getInstance(OnStartup::class.java)
    }

    override fun runActivity(project: Project) {
        LOG.info("Starting GitIdentityChecker")
        val message = "Found ${GitHelpers.getAllGitRoots(project).size} git root(s)"
        LOG.info(message)

        for (root in GitHelpers.getAllGitRoots(project)) {
            if (!GitHelpers.hasLocalGitUserAndMail(root)) {
                val content = "No repo-specific git user.name and/or user.email found in ${root.path}."
                LOG.warn(content)
                NotificationHelpers.showWarning(content, project)
            }
        }
    }
}
