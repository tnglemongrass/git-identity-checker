package com.github

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import org.jetbrains.annotations.NotNull
import java.time.LocalTime

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
        return object : CheckinHandler() {
            override fun beforeCheckin(): ReturnResult {
                val isGitRepo = Helpers.isGitProject(checkinProjectPanel.project)

                val doAfterSixCheck = false
                val doGitIdentityCheck = true

                if (doAfterSixCheck) {
                    // check if commit is attempted after 18:00
                    val now = LocalTime.now()
                    return if (now.isAfter(LocalTime.of(18, 0))) {
                        LOG.info("GitIdentityChecker - Commit attempt after 18:00 - condition failed")
                        Messages.showErrorDialog("Commits after 18:00 are not allowed.", "No Commits After Six")
                        ReturnResult.CANCEL
                    } else {
                        LOG.info("GitIdentityChecker - Commit attempt before 18:00 - condition fulfilled")
                        ReturnResult.COMMIT
                    }
                }

                if (doGitIdentityCheck && isGitRepo) {
                    // check if a local git config files exist, its path is ${repo root}/.git/config
                    val hasLocalUserAndMail: Boolean = Helpers.hasLocalGitUserAndMail(checkinProjectPanel.project)
                    if (!hasLocalUserAndMail) {
                        LOG.info("GitIdentityChecker - No repo-specific git user.name and user.mail found.")
                        val dialogResult = Messages.showYesNoDialog("No repo-specific git user.name and user.mail found. Are you REALLY sure to continue?", "Local Git Identity Checker", Messages.getErrorIcon())
                        return if (dialogResult == Messages.YES) {
                            ReturnResult.COMMIT
                        } else {
                            ReturnResult.CANCEL
                        }
                    }
                }

                return ReturnResult.COMMIT

            }
        }
    }
}
