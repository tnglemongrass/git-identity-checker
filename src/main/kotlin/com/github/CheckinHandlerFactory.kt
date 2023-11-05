package com.github

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory

class CheckinHandlerFactory : CheckinHandlerFactory() {
    companion object {
        private val LOG: Logger = Logger.getInstance(CheckinHandlerFactory::class.java)
    }

    override fun createHandler(checkinProjectPanel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {
            override fun beforeCheckin(): ReturnResult {
                val doGitIdentityCheck = true
                val isGitRepo = Helpers.hasGitRoots(checkinProjectPanel.project)

                if (doGitIdentityCheck && isGitRepo) {
                    // check if a local git config files exist, its path is ${repo root}/.git/config
                    val root = Helpers.getAllGitRoots(checkinProjectPanel.project)[0]
                    val hasLocalUserAndMail: Boolean = Helpers.hasLocalGitUserAndMail(root)
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
