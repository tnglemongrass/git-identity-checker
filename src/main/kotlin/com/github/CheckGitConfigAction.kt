package com.github

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class CheckGitConfigAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        hasLocalGitUserAndMailWithMessageBox(project)
    }

    private fun hasLocalGitUserAndMailWithMessageBox(project: Project) {
        if (!Helpers.hasGitRoots(project)) {
            Messages.showMessageDialog(project, "No git root found.", "Local Git Identity Checker", Messages.getErrorIcon())
            return
        }

        val root = Helpers.getAllGitRoots(project)[0]
        val (name: String?, email: String?) = Helpers.retrieveLocalGitUserAndMail(root)
        val conditionFulfilled = name != null && email != null



        if (conditionFulfilled) {
            Messages.showMessageDialog(project, "Repo-specific git user.name and user.email found with values:\nuser.name = $name\nuser.email = $email", "Local Git Identity Checker", Messages.getInformationIcon())
            return
        } else {
            Messages.showMessageDialog(project, "No repo-specific git user.name and/or user.email found in .git/config.", "Local Git Identity Checker", Messages.getWarningIcon())
            return
        }
    }
}