package com.github

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.ProjectLevelVcsManager
import com.intellij.openapi.vfs.VirtualFile

class Helpers {
    companion object {

        fun getAllGitRoots(project: Project): Array<VirtualFile> {
            val vcsManager = ProjectLevelVcsManager.getInstance(project)
            val allRoots = vcsManager.allVersionedRoots
            return allRoots.filter { root: VirtualFile -> isGitRoot(root) }.toTypedArray()
        }

        fun hasGitRoots(project: Project): Boolean {
            for (root in getAllGitRoots(project)) if (isGitRoot(root)) return true
            return false
        }

        private fun isGitRoot(rootDir: VirtualFile): Boolean {
            val configFile = rootDir.findFileByRelativePath(".git/config")
            if (configFile != null) {
                return configFile.isValid
            }
            return false
        }

        fun hasLocalGitUserAndMail(rootDir: VirtualFile): Boolean {
            val (name: String?, email: String?) = retrieveLocalGitUserAndMail(rootDir)
            return name != null && email != null
        }

        fun retrieveLocalGitUserAndMail(rootDir: VirtualFile): Pair<String?, String?> {
            val gitConfigFile = rootDir.findFileByRelativePath(".git/config")
            val configLines = gitConfigFile?.inputStream?.bufferedReader().use { it?.readLines() }

            var currentUserSection = false
            var name: String? = null
            var email: String? = null

            if (configLines != null) {
                for (line in configLines) {
                    when {
                        line.trim() == "[user]" -> currentUserSection = true
                        currentUserSection && line.trim().startsWith("name") -> name = line.substringAfter("=").trim()
                        currentUserSection && line.trim().startsWith("email") -> email = line.substringAfter("=").trim()
                        line.trim().startsWith("[") && line.trim() != "[user]" -> currentUserSection = false
                    }
                }
            }
            return Pair(name, email)
        }
    }
}