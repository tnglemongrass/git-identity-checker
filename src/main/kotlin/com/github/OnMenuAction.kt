package com.github

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile

class OnMenuAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val project = event.project ?: return
        showIdentitiesSummary(project)
    }

    private fun showIdentitiesSummary(project: Project) {
        if (!Helpers.hasGitRoots(project)) {
            Messages.showMessageDialog(project, "No git root found.", "Local Git Identity Checker", Messages.getErrorIcon())
            return
        }

        val repoDescriptions = mutableListOf<String>()
        var badCounter = 0

        for (root in Helpers.getAllGitRoots(project)) {
            val (name, email) = Helpers.retrieveLocalGitUserAndMail(root)
            val repoDetailsString = "${rootToConfigLink(root)}\nname = $name\nemail = $email\n"
            repoDescriptions.add(repoDetailsString)

            val hasNameAndMail = name != null && email != null
            if (!hasNameAndMail) badCounter++
        }

        val icon = if (badCounter > 0) Messages.getWarningIcon() else Messages.getInformationIcon()
        val message = buildMessageText(repoDescriptions, badCounter)
        Messages.showMessageDialog(project, message, "Local Git Identity Checker", icon)
    }

    private fun rootToConfigLink(root: VirtualFile): String {
        return "<a href='" + root.findFileByRelativePath(".git/config")?.path + "'>" + root.path + "</a>"
    }

    private fun buildMessageText(repoDescriptions: MutableList<String>, badCounter: Int): String {
        var message = ""
        message = if (badCounter > 0) {
            message.plus("Found $badCounter problem(s). Check the identities below.\n")
        } else {
            message.plus("No problems found. See below for details.\n")
        }
        message = message.plus("\n")
        message = if (repoDescriptions.isNotEmpty()) message.plus(repoDescriptions.joinToString("\n")) else message.plus("None\n")
        message = message.plus("\n")
        message = message.plus("Please click the link to open the respective .git/config.")
        return message
    }
}