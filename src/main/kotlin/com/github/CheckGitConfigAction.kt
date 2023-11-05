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
        val hasNameAndMail = name != null && email != null

        val icon = if (hasNameAndMail) Messages.getInformationIcon() else Messages.getWarningIcon()
        val message = buildMessageText(name, email, hasNameAndMail)
        Messages.showMessageDialog(project, message, "Local Git Identity Checker", icon)
    }

    private fun buildMessageText(name: String?, email: String?, hasNameAndMail: Boolean): String {
        val message1 = "Repo-specific git user.name and user.email found with values:\nuser.name = $name\nuser.email = $email"
        val message2 = "No repo-specific git user.name and/or user.email found in .git/config."
        return if (hasNameAndMail) message1 else message2
    }
}