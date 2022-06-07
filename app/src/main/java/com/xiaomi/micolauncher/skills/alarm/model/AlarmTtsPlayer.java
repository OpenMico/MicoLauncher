package com.xiaomi.micolauncher.skills.alarm.model;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;

/* loaded from: classes3.dex */
public class AlarmTtsPlayer {
    private BaseExoPlayer a;
    private boolean b;
    private boolean c;
    private boolean d;
    private String e;
    private String f;
    private String g;
    private OnAlarmTtsPlayerListener h;

    /* loaded from: classes3.dex */
    public interface OnAlarmTtsPlayerListener {
        void onTtsError();

        void onTtsPlayEnd();

        void onTtsPrepared();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AlarmTtsPlayer(Context context, OnAlarmTtsPlayerListener onAlarmTtsPlayerListener) {
        this.h = onAlarmTtsPlayerListener;
        this.a = new BaseExoPlayer(context, AudioSource.AUDIO_SOURCE_NULL, BasePlayer.StreamType.STREAM_TYPE_ALARM, true);
        this.a.setListener(new BaseExoPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmTtsPlayer.1
            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onPrepared(Player player) {
                AlarmTtsPlayer.this.b = true;
                L.alarm.i("AlarmTtsPlayer on tts onPrepared");
                if (AlarmTtsPlayer.this.c && !AlarmTtsPlayer.this.d) {
                    player.start();
                }
                if (AlarmTtsPlayer.this.h != null) {
                    AlarmTtsPlayer.this.h.onTtsPrepared();
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onComplete(Player player) {
                AlarmTtsPlayer.this.b = false;
                if (AlarmTtsPlayer.this.h != null) {
                    AlarmTtsPlayer.this.h.onTtsPlayEnd();
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onError(Player player, int i, Exception exc) {
                L.alarm.i("on tts onError error=%d", Integer.valueOf(i));
                AlarmTtsPlayer.this.b = false;
                AlarmTtsPlayer.this.a();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            a(str, false);
        } else {
            a();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void b(String str) {
        if (!this.b) {
            a(str, true);
        } else if (!this.d) {
            this.a.start();
        }
    }

    public void start() {
        if (this.b) {
            this.a.start();
        } else {
            a(this.e, true);
        }
    }

    public void setVolume(float f) {
        BaseExoPlayer baseExoPlayer = this.a;
        if (baseExoPlayer != null) {
            baseExoPlayer.setVolume(f);
        }
    }

    public void stop() {
        BaseExoPlayer baseExoPlayer = this.a;
        if (baseExoPlayer != null) {
            baseExoPlayer.stop();
        }
    }

    public void reset() {
        release();
        this.d = false;
    }

    public void release() {
        this.d = true;
        this.b = false;
        BaseExoPlayer baseExoPlayer = this.a;
        if (baseExoPlayer != null) {
            baseExoPlayer.setListener((BaseExoPlayer.PlayerListener) null);
            this.a.release();
        }
    }

    private void a(String str, boolean z) {
        L.alarm.d("AlarmTtsPlayer prepareTts=%s,autoPlay=%b", str, Boolean.valueOf(z));
        this.b = false;
        this.c = z;
        this.e = str;
        if (!str.equals(this.f) || TextUtils.isEmpty(this.g)) {
            SpeechEventUtil.getInstance().ttsRequest(str, new SpeechEventUtil.TTSListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmTtsPlayer.2
                @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.TTSListener
                public void onTtsUrl(String str2, String str3) {
                    L.alarm.i("AlarmTtsPlayer#ttsRequest onTtsUrl=%s, tts=%s", str2, str3);
                    if (!AlarmTtsPlayer.this.d) {
                        AlarmTtsPlayer.this.f = str3;
                        AlarmTtsPlayer.this.g = str2;
                        if (!TextUtils.isEmpty(str2)) {
                            AlarmTtsPlayer.this.a.setDataSource(str2);
                        } else {
                            AlarmTtsPlayer.this.a();
                        }
                    }
                }

                @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.TTSListener
                public void onTtsFail(String str2, boolean z2) {
                    L.alarm.w("AlarmTtsPlayer#ttsRequest onTtsFail.byNewRequest=%b,", Boolean.valueOf(z2));
                    AlarmTtsPlayer.this.a();
                }
            });
            return;
        }
        L.alarm.i("AlarmTtsPlayer#ttsRequest onTtsUrl=%s, tts=%s", this.g, this.f);
        this.a.setDataSource(this.g);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        OnAlarmTtsPlayerListener onAlarmTtsPlayerListener = this.h;
        if (onAlarmTtsPlayerListener != null) {
            onAlarmTtsPlayerListener.onTtsError();
        }
    }
}
