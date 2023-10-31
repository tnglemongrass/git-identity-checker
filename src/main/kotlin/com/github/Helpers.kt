package com.github

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import java.io.File

class Helpers {
    companion object {
        fun hasLocalGitUserAndMailWithMessageBox(project: Project): Boolean {
            val (name: String?, email: String?) = retrieveLocalGitUserAndMail(project)
            val conditionFulfilled = name != null && email != null

            if (conditionFulfilled) {
                Messages.showMessageDialog(project, "Local git user.name and user.email found with values:\nuser.name = $name\nuser.mail = $email", "Local Git Config Checker", Messages.getInformationIcon())
                return true
            } else {
                Messages.showMessageDialog(project, "Git user.name and/or user.email not found in .git/config.", "Local Git Config Checker", Messages.getWarningIcon())
                return false
            }
        }

        fun hasLocalGitUserAndMail(project: Project): Boolean {
            val (name: String?, email: String?) = retrieveLocalGitUserAndMail(project)
            return name != null && email != null
        }

        private fun retrieveLocalGitUserAndMail(project: Project): Pair<String?, String?> {
            val gitConfigFile = File(project.basePath, ".git/config")
            val configLines = gitConfigFile.readLines()

            var currentUserSection = false
            var name: String? = null
            var email: String? = null

            for (line in configLines) {
                when {
                    line.trim() == "[user]" -> currentUserSection = true
                    currentUserSection && line.trim().startsWith("name") -> name = line.substringAfter("=").trim()
                    currentUserSection && line.trim().startsWith("email") -> email = line.substringAfter("=").trim()
                    line.trim().startsWith("[") && line.trim() != "[user]" -> currentUserSection = false
                }
            }
            return Pair(name, email)
        }
    }
}