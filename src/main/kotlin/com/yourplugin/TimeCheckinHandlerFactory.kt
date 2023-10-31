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

    @NotNull
    override fun createHandler(@NotNull checkinProjectPanel: CheckinProjectPanel, @NotNull commitContext: CommitContext): CheckinHandler {
        LOG.info("asdf - CreateHandler called!")
        return object : CheckinHandler() {
            override fun beforeCheckin(): ReturnResult {
                // temporarily try to prevent any commits via intellij
                // Messages.showErrorDialog("Asdf2 - Commits after 18:00 are not allowed.", "Commit Error")
                // return ReturnResult.CANCEL

                val now = LocalTime.now()
                return if (now.isAfter(LocalTime.of(18, 0))) {
                    LOG.info("asdf1 - Commit attempt after 18:00 - condition failed")

                    // TODO: maybe there is some possibility to show a yes/no/cancel box?
                    Messages.showErrorDialog("Asdf1 - Commits after 18:00 are not allowed.", "Commit Error")
                    return ReturnResult.CANCEL
                } else {
                    LOG.info("asdf - Commit attempt before 18:00 - condition fulfilled")
                    ReturnResult.COMMIT
                }
            }
        }
    }
}
