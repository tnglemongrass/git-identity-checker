package com.github

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import org.jetbrains.annotations.NotNull

class CheckinHandlerFactory : CheckinHandlerFactory() {
    companion object {
        private val LOG: Logger = Logger.getInstance(CheckinHandlerFactory::class.java)
    }

    @NotNull
    override fun createHandler(@NotNull checkinProjectPanel: CheckinProjectPanel, @NotNull commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {
            override fun beforeCheckin(): ReturnResult {
                val isGitRepo = Helpers.isGitProject(checkinProjectPanel.project)

                val doGitIdentityCheck = true

                if (doGitIdentityCheck && isGitRepo) {
                    // check if a local git config files exist, its path is ${repo root}/.git/config
                    val hasLocalUserAndMail: Boolean = Helpers.hasLocalGitUserAndMail(checkinProjectPanel.project)
                    if (!hasLocalUserAndMail) {
                        LOG.info("GitIdentityChecker - No repo-specific git user.name and user.email found.")
                        val dialogResult = Messages.showYesNoDialog("No repo-specific git user.name and user.email found. Are you REALLY sure to continue?", "Local Git Identity Checker", Messages.getErrorIcon())
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
