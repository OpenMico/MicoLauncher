package com.xiaomi.micolauncher.skills.alarm;

import android.content.Context;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;

/* loaded from: classes3.dex */
public class AlarmPreviewAudioPlayer {
    private MultiAudioPlayer a = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_ALARM);

    public AlarmPreviewAudioPlayer() {
        this.a.setVolume(SystemVolume.getInstance().getAlarmVolume());
    }

    public void startPreview(final Context context) {
        if (!this.a.isPlaying()) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.-$$Lambda$AlarmPreviewAudioPlayer$vdV7oHFOwKAM5r68Q6cM6xvbzNI
                @Override // java.lang.Runnable
                public final void run() {
                    AlarmPreviewAudioPlayer.this.a(context);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Context context) {
        this.a.playRawFile(R.raw.alarm_default_7s_preview, context);
    }

    public void stopPreveiw() {
        this.a.stop();
    }

    public void relasePlayer() {
        MultiAudioPlayer multiAudioPlayer = this.a;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.release();
        }
    }
}
