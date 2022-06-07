package org.fourthline.cling.support.shared;

/* loaded from: classes5.dex */
public class AWTExceptionHandler {
    public void handle(Throwable th) {
        System.err.println("============= The application encountered an unrecoverable error, exiting... =============");
        th.printStackTrace(System.err);
        System.err.println("==========================================================================================");
        System.exit(1);
    }
}
