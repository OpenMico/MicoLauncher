package com.xiaomi.micolauncher.common.player;

import android.content.Context;
import com.xiaomi.miplay.audioclient.tv.MiPlayTVClient;
import com.xiaomi.miplay.audioclient.tv.MiPlayTVClientCallback;
import com.xiaomi.miplay.audioclient.tv.TVMediaMetaData;

/* loaded from: classes3.dex */
public class MiPlayerManager extends MiPlayTVClientCallback {
    private static MiPlayerManager a;
    private static final Object b = new Object();
    private MiPlayTVClient c;

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onCmdSessionError() {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onCmdSessionSuccess() {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onDurationUpdated(long j) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    @Deprecated
    public void onInitError() {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    @Deprecated
    public void onInitSuccess() {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onMediaInfoAck(TVMediaMetaData tVMediaMetaData) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onMediaInfoChange(TVMediaMetaData tVMediaMetaData) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onMirrorModeNotify(int i) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onPlayStateAck(int i) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onPlayStateChange(int i) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onPositionAck(long j) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onSourceNameChange(String str) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onVolumeAck(int i) {
    }

    @Override // com.xiaomi.miplay.audioclient.tv.IMiPlayTVClientCallback
    public void onVolumeChange(int i) {
    }

    public static MiPlayerManager getInstance(Context context) {
        MiPlayerManager miPlayerManager;
        synchronized (b) {
            if (a == null) {
                a = new MiPlayerManager(context);
            }
            miPlayerManager = a;
        }
        return miPlayerManager;
    }

    public MiPlayerManager(Context context) {
        this.c = new MiPlayTVClient(context);
    }

    public void init() {
        this.c.initAsync(this);
    }

    public void unInit() {
        this.c.unInit();
    }
}
