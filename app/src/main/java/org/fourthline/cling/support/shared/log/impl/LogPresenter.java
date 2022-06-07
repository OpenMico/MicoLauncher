package org.fourthline.cling.support.shared.log.impl;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.swing.SwingUtilities;
import org.fourthline.cling.support.shared.TextExpand;
import org.fourthline.cling.support.shared.log.LogView;
import org.seamless.swing.logging.LogMessage;

@ApplicationScoped
/* loaded from: classes5.dex */
public class LogPresenter implements LogView.Presenter {
    @Inject
    protected Event<TextExpand> textExpandEvent;
    @Inject
    protected LogView view;

    @Override // org.fourthline.cling.support.shared.log.LogView.Presenter
    public void init() {
        this.view.setPresenter(this);
    }

    @Override // org.fourthline.cling.support.shared.log.LogView.Presenter
    public void onExpand(LogMessage logMessage) {
        this.textExpandEvent.fire(new TextExpand(logMessage.getMessage()));
    }

    @PreDestroy
    public void destroy() {
        SwingUtilities.invokeLater(new Runnable() { // from class: org.fourthline.cling.support.shared.log.impl.LogPresenter.1
            @Override // java.lang.Runnable
            public void run() {
                LogPresenter.this.view.dispose();
            }
        });
    }

    @Override // org.fourthline.cling.support.shared.log.LogView.Presenter
    public void pushMessage(final LogMessage logMessage) {
        SwingUtilities.invokeLater(new Runnable() { // from class: org.fourthline.cling.support.shared.log.impl.LogPresenter.2
            @Override // java.lang.Runnable
            public void run() {
                LogPresenter.this.view.pushMessage(logMessage);
            }
        });
    }
}
