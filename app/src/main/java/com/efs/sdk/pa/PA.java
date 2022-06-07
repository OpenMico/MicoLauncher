package com.efs.sdk.pa;

import android.content.Context;
import android.view.View;

/* loaded from: classes.dex */
public interface PA {
    void enableDumpToFile(String str);

    void enableLog(boolean z);

    int endCalFPS(String str);

    long endCalTime(String str);

    void registerPAANRListener(Context context, PAANRListener pAANRListener);

    void registerPAANRListener(Context context, PAANRListener pAANRListener, long j);

    void registerPAANRListener(Context context, PAANRListener pAANRListener, long j, Thread thread);

    void registerPAMsgListener(PAMsgListener pAMsgListener);

    void start();

    void startCalFPS(String str, View view);

    void startCalTime(String str);

    void stop();

    void unRegisterPAMsgListener();

    void unregisterPAANRListener();
}
