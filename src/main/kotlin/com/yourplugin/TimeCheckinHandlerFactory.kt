package com.yourplugin;

import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vcs.CheckinProjectPanel;
import com.intellij.openapi.vcs.changes.CommitContext;
import com.intellij.openapi.vcs.checkin.CheckinHandler;
import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

import java.io.*;

import com.intellij.openapi.diagnostic.Logger;

public class TimeCheckinHandlerFactory extends CheckinHandlerFactory {
    private static final Logger LOG = Logger.getInstance(TimeCheckinHandlerFactory.class);

    @NotNull
    @Override
    public CheckinHandler createHandler(@NotNull CheckinProjectPanel checkinProjectPanel, @NotNull CommitContext commitContext) {
        LOG.info("asdf CreateHandler called!");
        return new CheckinHandler() {
            @Override
            public ReturnResult beforeCheckin() {
                // somehow this does not get called?

                // write "beforeCheckin called" to /tmp/logfile.txt

                String message = "here";
                String filePath = "/tmp/logfile.txt";

                try {
                    FileWriter writer = new FileWriter(filePath, true);
                    writer.write(message);
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


                LocalTime now = LocalTime.now();
                if (now.isAfter(LocalTime.of(18, 0))) {
                    LOG.info("asdf Commit attempt after 18:00");
                    Messages.showErrorDialog("Commits after 18:00 are not allowed.", "Commit Error");
                    return ReturnResult.CANCEL;
                }
                LOG.info("asdf Commit attempt before 18:00");
                return ReturnResult.COMMIT;
            }
        };
    }
}
