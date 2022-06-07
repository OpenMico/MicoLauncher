package com.xiaomi.miplay.mylibrary.external.api;

/* loaded from: classes4.dex */
public interface IMediaControlCallback {
    void onBuffering();

    int onCirculateEnd();

    int onCirculateFail(String str);

    void onCirculateModeChange(int i);

    int onCirculateStart();

    void onConnectMirrorSuccess();

    int onNext();

    int onNotifyPropertiesInfo(String str);

    int onPaused();

    int onPlayed();

    int onPositionChanged(long j);

    int onPrev();

    void onResumeMirrorFail();

    void onResumeMirrorSuccess();

    int onResumed();

    int onSeekDoneNotify();

    int onSeekedTo(long j);

    int onStopped(int i);

    void onVolumeChange(double d);
}
