package com.xiaomi.micolauncher.skills.alarm.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import androidx.annotation.GuardedBy;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.base.BaseExoPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.Player;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil;
import com.xiaomi.micolauncher.module.setting.utils.SystemVolume;
import com.xiaomi.micolauncher.skills.alarm.AlarmCacheManager;
import com.xiaomi.micolauncher.skills.alarm.AlarmHelper;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmMusicController;
import com.xiaomi.micolauncher.skills.alarm.model.AlarmTtsPlayer;
import com.xiaomi.micolauncher.skills.alarm.model.bean.AlarmRealmObjectBean;
import com.xiaomi.micolauncher.skills.video.player.utils.Utils;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class AlarmAudioPlayer {
    private boolean A;
    private int B;
    private AlarmAudioPlayerListener C;
    private AlarmTtsPlayer.OnAlarmTtsPlayerListener D;
    private BaseExoPlayer.PlayerListener E;
    private String k;
    private String l;
    private String m;
    private String n;
    private String o;
    private String p;
    private AlarmMusicController q;
    private AlarmTtsPlayer r;
    @GuardedBy("this")
    public BaseExoPlayer ringtonePlayer;
    private long s;
    private String t;
    private boolean u;
    private Context v;
    private Handler w;
    private boolean x;
    private volatile boolean y;
    private boolean z;
    private final long a = TimeUnit.SECONDS.toMillis(8);
    private final long b = 1500;
    private final int c = 1;
    private final int d = 21;
    private final int e = 22;
    private final int f = 23;
    private final int g = 2;
    private final int h = 3;
    private final int i = 1;
    private final int j = 2;
    private final AlarmMusicController.MusicControllerListener F = new AlarmMusicController.MusicControllerListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.1
        @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmMusicController.MusicControllerListener
        public void onMusicRingtoneError() {
            AlarmAudioPlayer.this.q = null;
            AlarmAudioPlayer.this.playDefaultAlarm();
        }

        @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmMusicController.MusicControllerListener
        public void onStartMusicRingtone() {
            AlarmAudioPlayer.this.b(false);
        }

        @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmMusicController.MusicControllerListener
        public void onResourceChange(String str) {
            AlarmAudioPlayer.this.d(str);
        }
    };

    /* loaded from: classes3.dex */
    public interface AlarmAudioPlayerListener {
        void onMusicResourceChange(String str);

        void onRingtoneStop();

        void onTtsError();

        void onTtsPlayEnd();
    }

    public AlarmAudioPlayer(Context context, AlarmAudioPlayerListener alarmAudioPlayerListener) {
        this.v = context;
        this.C = alarmAudioPlayerListener;
        b();
        c();
        d();
        a();
        this.r = new AlarmTtsPlayer(context, this.D);
    }

    public void a() {
        this.ringtonePlayer = new BaseExoPlayer(this.v, AudioSource.AUDIO_SOURCE_NULL, BasePlayer.StreamType.STREAM_TYPE_ALARM, true);
        this.ringtonePlayer.setPrepareTimeOut(TimeUnit.SECONDS.toMillis(6L));
        this.ringtonePlayer.setListener(this.E);
        AlarmMusicController alarmMusicController = this.q;
        if (alarmMusicController != null) {
            alarmMusicController.a(this.ringtonePlayer);
        }
    }

    private void b() {
        this.D = new AlarmTtsPlayer.OnAlarmTtsPlayerListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.2
            @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmTtsPlayer.OnAlarmTtsPlayerListener
            public void onTtsPrepared() {
                AlarmAudioPlayer.this.x = true;
            }

            @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmTtsPlayer.OnAlarmTtsPlayerListener
            public void onTtsPlayEnd() {
                if (AlarmAudioPlayer.this.y) {
                    L.alarm.i("onTtsPlayEnd AlarmAudioPlayer released");
                } else {
                    AlarmAudioPlayer.this.e();
                }
            }

            @Override // com.xiaomi.micolauncher.skills.alarm.model.AlarmTtsPlayer.OnAlarmTtsPlayerListener
            public void onTtsError() {
                if (AlarmAudioPlayer.this.y) {
                    L.alarm.i("onTtsError AlarmAudioPlayer released");
                } else {
                    AlarmAudioPlayer.this.j();
                }
            }
        };
    }

    private void c() {
        this.E = new BaseExoPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.3
            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onPrepared(Player player) {
                player.start();
                L.alarm.i("AlarmAudioPlayer alarm ringtone onPrepared");
                if (AlarmAudioPlayer.this.A) {
                    AlarmAudioPlayer.this.A = false;
                    AlarmAudioPlayer alarmAudioPlayer = AlarmAudioPlayer.this;
                    alarmAudioPlayer.c(alarmAudioPlayer.z);
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onComplete(Player player) {
                L.alarm.i("AlarmAudioPlayer alarm ringtone onComplete");
                if (AlarmAudioPlayer.this.y) {
                    L.alarm.i("alarm audio player released");
                } else if (AlarmAudioPlayer.this.isMoreThanTenMinute()) {
                    L.alarm.i("AlarmAudioPlayer alarm ringtone over ten minutes");
                    AlarmAudioPlayer.this.stop();
                    AlarmAudioPlayer.this.k();
                } else if (AlarmAudioPlayer.this.q == null || AlarmAudioPlayer.this.q.h() || !AlarmAudioPlayer.this.q.c() || !AlarmAudioPlayer.this.q.d()) {
                    player.restart();
                } else {
                    L.alarm.i("AlarmAudioPlayer alarm ringtone less than ten minutes");
                    AlarmAudioPlayer.this.q.e();
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.base.BaseExoPlayer.PlayerListener
            public void onError(Player player, int i, Exception exc) {
                L.alarm.i("on ringtone error=%d,released=%b", Integer.valueOf(i), Boolean.valueOf(AlarmAudioPlayer.this.y));
                if (!AlarmAudioPlayer.this.y) {
                    if (i != -110) {
                        if (player != null) {
                            player.release();
                        }
                        AlarmAudioPlayer.this.a();
                    }
                    if (AlarmAudioPlayer.this.q == null) {
                        AlarmAudioPlayer.this.playDefaultAlarm();
                    } else if (i == -110 && AlarmAudioPlayer.this.q.g() == 0) {
                        L.alarm.i("music alarm prepare timeout play default");
                        AlarmAudioPlayer.this.playDefaultAlarm();
                        AlarmAudioPlayer.this.q.a(true);
                        AlarmAudioPlayer.this.q = null;
                    } else if (AlarmAudioPlayer.this.q.c()) {
                        AlarmAudioPlayer.this.q.f();
                        AlarmAudioPlayer.this.q.e();
                    } else if (player != null) {
                        player.restart();
                    } else {
                        L.alarm.i("music alarm player is empty");
                        AlarmAudioPlayer.this.playDefaultAlarm();
                    }
                }
            }
        };
    }

    @SuppressLint({"HandlerLeak"})
    private void d() {
        this.w = new Handler() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.4
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                L.alarm.i("alarm msg=%d,ttsPrepared=%b", Integer.valueOf(message.what), Boolean.valueOf(AlarmAudioPlayer.this.x));
                int i = message.what;
                switch (i) {
                    case 1:
                        if (!TextUtils.isEmpty(AlarmAudioPlayer.this.o)) {
                            AlarmAudioPlayer.this.r.setVolume(0.7f);
                            if (AlarmAudioPlayer.this.x) {
                                AlarmAudioPlayer.this.ringtonePlayer.fadeOut(0.3f, 1500L);
                                AlarmAudioPlayer.this.w.sendEmptyMessageDelayed(2, 1500L);
                                return;
                            }
                            AlarmAudioPlayer alarmAudioPlayer = AlarmAudioPlayer.this;
                            alarmAudioPlayer.c(alarmAudioPlayer.o);
                            return;
                        }
                        return;
                    case 2:
                        if (AlarmAudioPlayer.this.x) {
                            AlarmAudioPlayer.this.r.setVolume(1.0f);
                            AlarmAudioPlayer.this.r.start();
                            return;
                        }
                        AlarmAudioPlayer alarmAudioPlayer2 = AlarmAudioPlayer.this;
                        alarmAudioPlayer2.a(alarmAudioPlayer2.o);
                        return;
                    default:
                        switch (i) {
                            case 21:
                                AlarmAudioPlayer.this.B = 1;
                                if (AlarmAudioPlayer.this.x) {
                                    AlarmAudioPlayer.this.ringtonePlayer.pause();
                                    AlarmAudioPlayer.this.r.setVolume(AlarmAudioPlayer.this.a(0.5f));
                                    AlarmAudioPlayer.this.r.start();
                                    return;
                                }
                                AlarmAudioPlayer alarmAudioPlayer3 = AlarmAudioPlayer.this;
                                alarmAudioPlayer3.a(alarmAudioPlayer3.o);
                                return;
                            case 22:
                                AlarmAudioPlayer.this.B = 2;
                                if (AlarmAudioPlayer.this.x) {
                                    AlarmAudioPlayer.this.ringtonePlayer.pause();
                                    AlarmAudioPlayer.this.r.setVolume(0.75f);
                                    AlarmAudioPlayer.this.r.start();
                                    return;
                                }
                                AlarmAudioPlayer alarmAudioPlayer4 = AlarmAudioPlayer.this;
                                alarmAudioPlayer4.a(alarmAudioPlayer4.n);
                                return;
                            case 23:
                                AlarmAudioPlayer.this.B = 3;
                                if (AlarmAudioPlayer.this.x) {
                                    AlarmAudioPlayer.this.ringtonePlayer.pause();
                                    AlarmAudioPlayer.this.r.setVolume(1.0f);
                                    AlarmAudioPlayer.this.r.start();
                                    return;
                                }
                                AlarmAudioPlayer alarmAudioPlayer5 = AlarmAudioPlayer.this;
                                alarmAudioPlayer5.a(alarmAudioPlayer5.n);
                                return;
                            default:
                                return;
                        }
                }
            }
        };
    }

    public void e() {
        boolean isVideoRingtone = AlarmHelper.isVideoRingtone(this.p);
        L.alarm.i("AlarmAudioPlayer ttsPlayEnd isVideoRingtone=%s，hasEvent=%s,defaultRingtone=%s,nextTtsType=%s", Boolean.valueOf(isVideoRingtone), Boolean.valueOf(h()), Boolean.valueOf(this.z), Integer.valueOf(this.B));
        if (isVideoRingtone) {
            i();
        } else if (!h() || !this.z) {
            this.ringtonePlayer.fadeIn(0.3f, 1.0f, 1500L);
            i();
        } else {
            int i = this.B;
            if (i == 1) {
                this.ringtonePlayer.restart();
                this.ringtonePlayer.setVolume(0.75f);
                this.w.sendEmptyMessageDelayed(22, this.a);
            } else if (i == 2 || i == 3) {
                this.ringtonePlayer.restart();
                this.ringtonePlayer.setVolume(1.0f);
                this.w.sendEmptyMessageDelayed(23, this.a);
            } else {
                this.ringtonePlayer.setVolume(1.0f);
            }
        }
    }

    public void bell(AlarmRealmObjectBean alarmRealmObjectBean, boolean z) {
        L.alarm.i("AlarmAudioPlayer bell alarmItem=%s", alarmRealmObjectBean.toString());
        this.k = alarmRealmObjectBean.getIdStr();
        this.l = alarmRealmObjectBean.getRingtone();
        this.p = alarmRealmObjectBean.getRingtoneType();
        this.m = alarmRealmObjectBean.getEvent();
        this.n = alarmRealmObjectBean.getReminder();
        this.o = alarmRealmObjectBean.getTimeReminder();
        this.s = System.currentTimeMillis();
        f();
        a(z);
    }

    private void a(boolean z) {
        if (!Utils.isNetworkConncected(this.v)) {
            L.alarm.i("AlarmAudioPlayer network not connected");
            playDefaultAlarm();
        } else if (AlarmHelper.isVideoRingtone(this.p)) {
            L.alarm.i("AlarmAudioPlayer video ringtone play tts");
            a(this.o);
        } else if (!TextUtils.isEmpty(this.l)) {
            List<Instruction> list = null;
            if (z) {
                list = AlarmCacheManager.getInstance().getMusicCacheById(this.k);
            }
            if (ContainerUtil.hasData(list)) {
                L.alarm.i("AlarmAudioPlayer#playSound will playCache");
                a(list);
                return;
            }
            b("alarm_" + this.l);
        } else {
            playDefaultAlarm();
        }
    }

    public void setVolume(float f) {
        BaseExoPlayer baseExoPlayer = this.ringtonePlayer;
        if (baseExoPlayer != null) {
            baseExoPlayer.setVolume(f);
        }
        AlarmTtsPlayer alarmTtsPlayer = this.r;
        if (alarmTtsPlayer != null) {
            alarmTtsPlayer.setVolume(f);
        }
    }

    public void stop() {
        L.alarm.i("alarm audio player stop");
        this.y = true;
        Handler handler = this.w;
        if (handler != null) {
            handler.removeMessages(1);
            this.w.removeMessages(21);
            this.w.removeMessages(22);
            this.w.removeMessages(23);
            this.w.removeMessages(2);
        }
        AlarmMusicController alarmMusicController = this.q;
        if (alarmMusicController != null) {
            alarmMusicController.a();
            this.q = null;
        }
        AlarmTtsPlayer alarmTtsPlayer = this.r;
        if (alarmTtsPlayer != null) {
            alarmTtsPlayer.release();
            this.r = null;
        }
        BaseExoPlayer baseExoPlayer = this.ringtonePlayer;
        if (baseExoPlayer != null) {
            baseExoPlayer.setListener((BaseExoPlayer.PlayerListener) null);
            this.ringtonePlayer.release();
            this.ringtonePlayer = null;
        }
        L.alarm.i("AlarmAudioPlayer clearMusicCache ids=%s when player stop", this.k);
        AlarmCacheManager.getInstance().clearMusicCache(this.k);
        AlarmModel.getInstance().clearFiringStatus();
    }

    private void f() {
        this.t = null;
        this.x = false;
        this.z = false;
        this.A = true;
        this.u = false;
        AlarmTtsPlayer alarmTtsPlayer = this.r;
        if (alarmTtsPlayer != null) {
            alarmTtsPlayer.reset();
            this.r = new AlarmTtsPlayer(this.v, this.D);
        }
        AlarmMusicController alarmMusicController = this.q;
        if (alarmMusicController != null) {
            alarmMusicController.b();
            this.q = new AlarmMusicController(this.ringtonePlayer, this.F);
        }
        this.y = false;
    }

    public void a(String str) {
        AlarmTtsPlayer alarmTtsPlayer = this.r;
        if (alarmTtsPlayer != null) {
            alarmTtsPlayer.b(str);
            return;
        }
        e();
        L.alarm.i("alarmTtsPlayer is null ");
    }

    public void a(List<Instruction> list) {
        if (this.q == null) {
            this.q = new AlarmMusicController(this.ringtonePlayer, this.F);
        }
        if (!this.q.a(list)) {
            this.q = null;
            playDefaultAlarm();
        }
    }

    private void b(String str) {
        L.alarm.i("AlarmAudioPlayer playQueryAlarm query=%s", str);
        SpeechEventUtil.getInstance().nlpRequest(str, new SpeechEventUtil.NLPListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.AlarmAudioPlayer.5
            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.NLPListener
            public void onNlpResult(List<Instruction> list, String str2) {
                L.alarm.i("onNlpResult=%s", str2);
                AlarmAudioPlayer.this.a(list);
            }

            @Override // com.xiaomi.micolauncher.common.speech.utils.SpeechEventUtil.NLPListener
            public void onNlpFail(String str2, boolean z) {
                L.alarm.i("onNlpFail=%s", str2);
                AlarmAudioPlayer.this.playDefaultAlarm();
            }
        });
    }

    public void playDefaultAlarm() {
        L.alarm.i("AlarmAudioPlayer play default alarm");
        if (this.ringtonePlayer != null) {
            g();
            b(true);
        }
    }

    private void g() {
        if (this.ringtonePlayer != null && !this.y && this.v != null) {
            if (h()) {
                this.ringtonePlayer.setDataSource(R.raw.alarm_default_7s, this.v);
            } else {
                this.ringtonePlayer.setDataSource(R.raw.alarm_default_30s, this.v);
            }
        }
    }

    public void b(boolean z) {
        this.z = z;
        L.alarm.i("defaultRingtone =%b hasEvent=%b", Boolean.valueOf(z), Boolean.valueOf(h()));
    }

    public void c(boolean z) {
        L.alarm.i("play default alarm defaultRingtone =%b hasEvent=%b", Boolean.valueOf(z), Boolean.valueOf(h()));
        this.z = z;
        if (this.ringtonePlayer == null) {
            L.alarm.e("AlarmAudioPlayer#setRingtoneVolume ringtonePlayer is null");
            return;
        }
        if (!h() || !z) {
            long millis = TimeUnit.SECONDS.toMillis(20L);
            this.ringtonePlayer.fadeIn(0.1f, 1.0f, millis);
            this.w.sendEmptyMessageDelayed(1, millis);
        } else {
            this.ringtonePlayer.setVolume(a(0.5f));
            this.w.sendEmptyMessageDelayed(21, this.a);
        }
        c(this.o);
    }

    public void c(String str) {
        this.x = false;
        AlarmTtsPlayer alarmTtsPlayer = this.r;
        if (alarmTtsPlayer != null) {
            alarmTtsPlayer.a(str);
        }
    }

    private boolean h() {
        return !ContainerUtil.isEmpty(this.m);
    }

    public void release() {
        stop();
        this.C = null;
        this.v = null;
    }

    public boolean isMoreThanTenMinute() {
        return this.s > 0 && System.currentTimeMillis() - this.s > TimeUnit.MINUTES.toMillis(10L);
    }

    private void i() {
        AlarmAudioPlayerListener alarmAudioPlayerListener = this.C;
        if (alarmAudioPlayerListener != null) {
            alarmAudioPlayerListener.onTtsPlayEnd();
        }
    }

    public void j() {
        AlarmAudioPlayerListener alarmAudioPlayerListener = this.C;
        if (alarmAudioPlayerListener != null) {
            alarmAudioPlayerListener.onTtsError();
        }
    }

    public void k() {
        AlarmAudioPlayerListener alarmAudioPlayerListener = this.C;
        if (alarmAudioPlayerListener != null) {
            alarmAudioPlayerListener.onRingtoneStop();
        }
    }

    public void d(String str) {
        AlarmAudioPlayerListener alarmAudioPlayerListener = this.C;
        if (alarmAudioPlayerListener != null) {
            alarmAudioPlayerListener.onMusicResourceChange(str);
        }
    }

    public float a(float f) {
        int alarmVolume = SystemVolume.getInstance().getAlarmVolume();
        if (alarmVolume <= 30) {
            return 0.67f;
        }
        return alarmVolume >= 40 ? f : 1.14f - (alarmVolume * 0.016f);
    }
}
