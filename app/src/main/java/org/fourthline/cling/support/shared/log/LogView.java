package org.fourthline.cling.support.shared.log;

import java.util.List;
import org.fourthline.cling.support.shared.View;
import org.seamless.swing.logging.LogCategory;
import org.seamless.swing.logging.LogMessage;

/* loaded from: classes5.dex */
public interface LogView extends View<Presenter> {

    /* loaded from: classes5.dex */
    public interface LogCategories extends List<LogCategory> {
    }

    /* loaded from: classes5.dex */
    public interface Presenter {
        void init();

        void onExpand(LogMessage logMessage);

        void pushMessage(LogMessage logMessage);
    }

    void dispose();

    void pushMessage(LogMessage logMessage);
}
