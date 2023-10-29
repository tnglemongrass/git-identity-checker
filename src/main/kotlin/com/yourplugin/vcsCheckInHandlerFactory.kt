package com.yourplugin;

import com.intellij.openapi.vcs.checkin.VcsCheckinHandlerFactory;

public class vcsCheckInHandlerFactory extends VcsCheckinHandlerFactory {
    // kaum benutzt, vmtl besser den normalen checkin handler zu verwenden
    protected vcsCheckInHandlerFactory() {
        super();
        System.out.println("HERE vcsCheckInHandlerFactory.vcsCheckInHandlerFactory");
    }
}
