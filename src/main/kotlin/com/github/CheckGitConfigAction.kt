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

        val messagesGood = mutableListOf<String>()
        val messagesBad = mutableListOf<String>()

        for (root in Helpers.getAllGitRoots(project)) {
            val (name: String?, email: String?) = Helpers.retrieveLocalGitUserAndMail(root)
            val repoDetailsString = "\t${root.path}\n\tuser.name = $name\n\tuser.email = $email\n"
            val hasNameAndMail = name != null && email != null

            if (hasNameAndMail) {
                messagesGood.add(repoDetailsString)
            } else {
                messagesBad.add(repoDetailsString)
            }
        }

        val showWarningIcon = messagesBad.isNotEmpty()
        val icon = if (showWarningIcon) Messages.getWarningIcon() else Messages.getInformationIcon()
        val message = buildMessageText(messagesGood, messagesBad)
        Messages.showMessageDialog(project, message, "Local Git Identity Checker", icon)
    }

    private fun buildMessageText(messagesGood: MutableList<String>, messagesBad: MutableList<String>): String {
        var message = ""
        message = if (messagesBad.isNotEmpty()) {
            message.plus("Found ${messagesBad.size} problem(s). See below for details.\n")
        } else {
            message.plus("No problems found. See below for details.\n")
        }
        message = message.plus("\n")
        if (messagesBad.isNotEmpty()) {
            message = message.plus("Found no repo-specific git user.name and/or user.email in:\n")
            message = message.plus("\n")
            message = if (messagesBad.isNotEmpty()) message.plus(messagesBad.joinToString("\n")) else message.plus("None\n")
            message = message.plus("\n")
            message = message.plus("Found repo-specific git user.name and user.email in:\n")
            message = message.plus("\n")
        }
        message = if (messagesGood.isNotEmpty()) message.plus(messagesGood.joinToString("\n")) else message.plus("None\n")
        message = message.plus("\n")
        message = message.plus("Please edit the respective .git/config if you want to make changes.")
        return message
    }
}