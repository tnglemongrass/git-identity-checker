package com.yourplugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.changes.LocalChangeList;
import com.intellij.openapi.vcs.changes.ui.CommitMessageProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

public class commitMessageProvider implements CommitMessageProvider {

    // hier gibt es nur eine Methode die man implementieren kann
    // diese kann vermutlich lediglich die Commit message veraendern, jedoch nichts verhindern
    @Override
    public @Nullable String getCommitMessage(@NotNull LocalChangeList forChangelist, @NotNull Project project) {
        System.out.println("Hello world!");
        System.out.println("HERE commitMessageProvider.getCommitMessage");
        try {
            Files.write(Paths.get("/tmp/reached1.txt"), "The code was reached".getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "hello world!";
    }
}
