package com.zlw.main.recorderlib.recorder.listener;

import com.zlw.main.recorderlib.recorder.RecordHelper;

/* loaded from: classes4.dex */
public interface RecordStateListener {
    void onError(String str);

    void onStateChange(RecordHelper.RecordState recordState);
}
