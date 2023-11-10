package com.github

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vcs.CheckinProjectPanel
import com.intellij.openapi.vcs.changes.CommitContext
import com.intellij.openapi.vcs.changes.ui.BooleanCommitOption
import com.intellij.openapi.vcs.checkin.CheckinHandler
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory
import com.intellij.openapi.vcs.ui.RefreshableOnComponent
import com.intellij.openapi.vfs.VirtualFile


class OnCommit : CheckinHandlerFactory() {
    companion object {
        private val LOG: Logger = Logger.getInstance(CheckinHandlerFactory::class.java)
    }

    override fun createHandler(checkinProjectPanel: CheckinProjectPanel, commitContext: CommitContext): CheckinHandler {
        return object : CheckinHandler() {

            private var doGitIdentityCheck_: Boolean = true

            override fun beforeCheckin(): ReturnResult {
                val isGitRepo = Helpers.hasGitRoots(checkinProjectPanel.project)
                if (doGitIdentityCheck() && isGitRepo) {
                    val rootsWithProblems = mutableListOf<VirtualFile>()
                    // in the 'commit' situation we have a more specific information available about affected repositories, (no need to check ALL existing git projects)
                    for (root in checkinProjectPanel.roots) {
                        // check if a local git config files exist, its path is ${repo root}/.git/config
                        val hasLocalUserAndMail: Boolean = Helpers.hasLocalGitUserAndMail(root)
                        if (!hasLocalUserAndMail) {
                            LOG.info("GitIdentityChecker - No repo-specific git user.name and user.email found in ${root.path}.")
                            rootsWithProblems.add(root)
                        }
                    }

                    if (rootsWithProblems.isNotEmpty()) {
                        val dialogResult = Messages.showYesNoDialog(
                                "No repo-specific git user.name and/or user.email found for\n" +
                                        " ${rootsWithProblems.joinToString(separator = "\n") { root -> root.path }}\n" +
                                        "Are you REALLY sure to continue?", "Local Git Identity Checker", Messages.getErrorIcon())
                        return if (dialogResult == Messages.YES) {
                            ReturnResult.COMMIT
                        } else {
                            ReturnResult.CANCEL
                        }
                    }
                }

                return ReturnResult.COMMIT

            }

            override fun getBeforeCheckinConfigurationPanel(): RefreshableOnComponent {
                return BooleanCommitOption(checkinProjectPanel, "Check Local Git Identity", false, this::doGitIdentityCheck, this::setDoGitIdentityCheck)
            }

            private fun doGitIdentityCheck(): Boolean {
                return doGitIdentityCheck_
            }

            private fun setDoGitIdentityCheck(b: Boolean) {
                doGitIdentityCheck_ = b
            }
        }
    }
}
