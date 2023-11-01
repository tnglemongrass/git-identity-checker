package com.github

import com.intellij.openapi.project.Project
import java.io.File

class Helpers {
    companion object {

        fun isGitProject(project: Project): Boolean {
            return File(project.basePath, ".git/config").isFile
        }

        fun hasLocalGitUserAndMail(project: Project): Boolean {
            val (name: String?, email: String?) = retrieveLocalGitUserAndMail(project)
            return name != null && email != null
        }

        fun retrieveLocalGitUserAndMail(project: Project): Pair<String?, String?> {
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