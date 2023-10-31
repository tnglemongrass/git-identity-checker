package com.yourplugin

import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import com.intellij.openapi.diagnostic.Logger
import org.jetbrains.annotations.NotNull
import java.time.LocalTime
import java.io.FileWriter
import java.io.IOException

class TimeCheckinHandlerFactory : CheckinHandlerFactory() {
    companion object {
        private val LOG: Logger = Logger.getInstance(TimeCheckinHandlerFactory::class.java)
    }

// alternatives candidates for plugin name:
//    GitGuard
//    CommitCheckr
//    RepoAuth
//    SecureCommit
//    RepoVerify
//    GitConfigCheck
//    CommitAuthenticator
//    ConfigGuard
//    RepoCommitCheck
//    GitIdentityCheck


    @NotNull
    override fun createHandler(@NotNull checkinProjectPanel: CheckinProjectPanel, @NotNull commitContext: CommitContext): CheckinHandler {
        LOG.info("asdf - CreateHandler called!")
        return object : CheckinHandler() {
            override fun beforeCheckin(): ReturnResult {
                // temporarily try to prevent any commits via intellij
                // Messages.showErrorDialog("Asdf2 - Commits after 18:00 are not allowed.", "Commit Error")
                // return ReturnResult.CANCEL

                val doAfterSixCheck = true;
                val doGitIdentityCheck = true;

                if (doAfterSixCheck) {
                    val now = LocalTime.now()
                    if (now.isAfter(LocalTime.of(18, 0))) {
                        LOG.info("NoCommitsAfterSix - Commit attempt after 18:00 - condition failed")
                        Messages.showErrorDialog("Commits after 18:00 are not allowed.", "NoCommitsAfterSix")
                        return ReturnResult.CANCEL
                    } else {
                        LOG.info("NoCommitsAfterSix - Commit attempt before 18:00 - condition fulfilled")
                        return ReturnResult.COMMIT
                    }
                }

                if (doGitIdentityCheck) {
                    val hasLocalUserAndMail = false;

                    // TODO: check if a local git config files exist, its path is ${repo root}/.git/config

                    if (hasLocalUserAndMail) {

                        LOG.info("NoCommitsAfterSix - No repo-specific git user.name and user.mail found. Proceed anyways?");
                        val dialogResult = Messages.showYesNoDialog("No repo-specific git user.name and user.mail found. Proceed anyways?", "NoCommitAfterSix: Check Local Git Config", Messages.getWarningIcon())
                        if (dialogResult == Messages.YES) {
                            return ReturnResult.COMMIT
                        } else {
                            return ReturnResult.CANCEL
                        }
                    }
                }

                return ReturnResult.COMMIT;

            }
        }
    }
}
