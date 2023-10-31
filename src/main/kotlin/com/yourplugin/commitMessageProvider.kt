package com.yourplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.vcs.changes.LocalChangeList
import com.intellij.openapi.vcs.changes.ui.CommitMessageProvider
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class CommitMessageProvider : CommitMessageProvider {
    override fun getCommitMessage(forChangelist: LocalChangeList, project: Project): String? {
        println("Hello world!")
        println("HERE commitMessageProvider.getCommitMessage")
        try {
            Files.write(Paths.get("/tmp/reached1.txt"), "The code was reached".toByteArray(), StandardOpenOption.CREATE)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return "hello world!"
    }
}
