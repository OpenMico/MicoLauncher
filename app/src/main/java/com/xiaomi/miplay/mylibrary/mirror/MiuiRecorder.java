package com.xiaomi.miplay.mylibrary.mirror;

import android.content.Context;
import android.media.AudioRecord;
import android.util.Log;
import com.xiaomi.miplay.mylibrary.session.utils.Logger;
import miui.media.MiuiAudioPlaybackRecorder;

/* loaded from: classes4.dex */
public class MiuiRecorder {
    private static final String b = "MiuiRecorder";
    private Context a;
    private MiuiAudioPlaybackRecorder c = null;
    private AudioRecord d;

    public MiuiRecorder(Context context) {
        this.a = context;
    }

    public AudioRecord createAudioRecord(int i, int i2, int i3, int[] iArr) {
        Log.d(b, "createAudioRecord.");
        try {
            if (this.c == null) {
                this.c = new MiuiAudioPlaybackRecorder(this.a);
            }
        } catch (Throwable th) {
            Log.d(b, th.toString());
        }
        this.d = this.c.createRecorder(i, i2, i3, iArr, 2);
        Log.d(b, "createAudioRecord end.");
        return this.d;
    }

    public boolean releaseRecorder() {
        Log.d(b, "releaseRecorder.");
        boolean releaseRecorder = this.c.releaseRecorder(this.d);
        Logger.d(b, "releaseRecorder end", new Object[0]);
        return releaseRecorder;
    }

    public void updateUid(int[] iArr, AudioRecord audioRecord) {
        Log.d(b, "updateUid.");
        MiuiAudioPlaybackRecorder miuiAudioPlaybackRecorder = this.c;
        if (miuiAudioPlaybackRecorder != null) {
            miuiAudioPlaybackRecorder.updateUid(iArr, audioRecord);
        }
        Log.d(b, "updateUid end.");
    }
}
