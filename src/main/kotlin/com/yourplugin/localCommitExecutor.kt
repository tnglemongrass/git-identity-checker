//package com.yourplugin;
//
//import com.intellij.openapi.vcs.changes.LocalCommitExecutor;
//import org.jetbrains.annotations.Nls;
//import org.jetbrains.annotations.NonNls;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//public class localCommitExecutor extends LocalCommitExecutor {
//
//    // areChangesRequired sieht eigentlich ganz gut aus
//    // localCommitExecutor erstellt aber nur einen neuen Button?
//
//    @Override
//    public boolean areChangesRequired() {
//        System.out.println("HERE localCommitExecutor.areChangesRequired");
//        return super.areChangesRequired();
//    }
//
//    @Override
//    public @Nls @NotNull String getActionText() {
//        return null;
//    }
//
//
//    @Override
//    public @Nullable @NonNls String getHelpId() {
//        return null;
//    }
//}
