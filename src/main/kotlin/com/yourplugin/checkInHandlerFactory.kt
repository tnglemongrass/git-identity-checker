//package com.yourplugin;
//
//import com.intellij.openapi.Disposable;
//import com.intellij.openapi.vcs.CheckinProjectPanel;
//import com.intellij.openapi.vcs.VcsException;
//import com.intellij.openapi.vcs.changes.CommitContext;
//import com.intellij.openapi.vcs.changes.CommitExecutor;
//import com.intellij.openapi.vcs.checkin.CheckinHandler;
//import com.intellij.openapi.vcs.checkin.CheckinHandlerFactory;
//import com.intellij.openapi.vcs.ui.RefreshableOnComponent;
//import com.intellij.util.PairConsumer;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//import java.util.List;
//
//public class checkInHandlerFactory extends CheckinHandlerFactory {
//    @Override
//    public @NotNull CheckinHandler createHandler(@NotNull CheckinProjectPanel panel, @NotNull CommitContext commitContext) {
//        return new CheckinHandler() {
//            @Override
//            public @Nullable RefreshableOnComponent getBeforeCheckinConfigurationPanel() {
//                return super.getBeforeCheckinConfigurationPanel();
//            }
//
//            @Override
//            public @Nullable RefreshableOnComponent getAfterCheckinConfigurationPanel(Disposable parentDisposable) {
//                return super.getAfterCheckinConfigurationPanel(parentDisposable);
//            }
//
//            @Override
//            public ReturnResult beforeCheckin(@Nullable CommitExecutor executor, PairConsumer<Object, Object> additionalDataConsumer) {
//                System.out.println("HERE checkInHandlerFactory.beforeCheckin");
//                return super.beforeCheckin(executor, additionalDataConsumer);
//            }
//
//            @Override
//            public ReturnResult beforeCheckin() {
//                System.out.println("HERE checkInHandlerFactory.beforeCheckin");
//                return super.beforeCheckin();
//            }
//
//            @Override
//            public void checkinSuccessful() {
//                super.checkinSuccessful();
//            }
//
//            @Override
//            public void checkinFailed(List<VcsException> exception) {
//                super.checkinFailed(exception);
//            }
//
//            @Override
//            public void includedChangesChanged() {
//                super.includedChangesChanged();
//            }
//
//            @Override
//            public boolean acceptExecutor(CommitExecutor executor) {
//                return super.acceptExecutor(executor);
//            }
//        };
//    }
//}
