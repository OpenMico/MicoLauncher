package com.xiaomi.micolauncher.skills.alarm.model;

import android.content.Context;
import android.text.TextUtils;
import com.xiaomi.ai.api.AIApiConstants;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.PromptSoundPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.instruciton.base.InstructionUtil;
import com.xiaomi.micolauncher.skills.alarm.HourlyChimeCacheManager;
import com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeAudioPlayer;
import com.xiaomi.micolauncher.skills.common.AsrTtsCard;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class HourlyChimeAudioPlayer extends AlarmAudioPlayer {
    private static final long b = TimeUnit.SECONDS.toMillis(1);
    MultiAudioPlayer.PlayerListener a;
    private Context c;

    public HourlyChimeAudioPlayer(Context context, MultiAudioPlayer.PlayerListener playerListener) {
        super(context, null);
        this.c = context;
        this.a = playerListener;
    }

    public void playTTS() {
        List<Instruction> hourlyChimeCache = HourlyChimeCacheManager.getInstance().getHourlyChimeCache();
        if (InstructionUtil.getIntention(hourlyChimeCache, AIApiConstants.SpeechSynthesizer.Speak) == null) {
            L.hourlychime.i("HourlyChimeAudioPlayer Play instruction is empty");
            a("", "");
            ThreadUtil.postDelayedInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeAudioPlayer.1
                @Override // java.lang.Runnable
                public void run() {
                    PromptSoundPlayer.getInstance().play(HourlyChimeAudioPlayer.this.c, R.raw.hourly_chime);
                }
            }, b);
            MultiAudioPlayer.PlayerListener playerListener = this.a;
            if (playerListener != null) {
                playerListener.onComplete();
                return;
            }
            return;
        }
        L.hourlychime.i("HourlyChimeAudioPlayer playTTS()");
        for (Instruction instruction : hourlyChimeCache) {
            L.hourlychime.i("HourlyChimeAudioPlayer playTTS  fullName: %s", instruction.getFullName());
            if (instruction.getFullName().equals(AIApiConstants.SpeechSynthesizer.Speak)) {
                SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) instruction.getPayload();
                String text = speak.getText();
                if (speak.getUrl().isPresent()) {
                    String str = speak.getUrl().get();
                    if (!TextUtils.isEmpty(str)) {
                        L.hourlychime.i("playTTS() text: %s, url ：%s", text, str);
                        a(str, text);
                    }
                }
            }
        }
    }

    private void a(final String str, String str2) {
        final MultiAudioPlayer multiAudioPlayer = new MultiAudioPlayer(BasePlayer.StreamType.STREAM_TYPE_ALARM, true);
        final MultiAudioPlayer multiAudioPlayer2 = new MultiAudioPlayer(this.c, BasePlayer.StreamType.STREAM_TYPE_ALARM);
        final AnonymousClass2 r9 = new AnonymousClass2(AudioSource.AUDIO_SOURCE_TTS, multiAudioPlayer, str, multiAudioPlayer2, str2);
        multiAudioPlayer2.setListener(new MultiAudioPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeAudioPlayer.3
            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onPrepared() {
                if (HourlyChimeAudioPlayer.this.a != null) {
                    HourlyChimeAudioPlayer.this.a.onPrepared();
                }
            }

            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onComplete() {
                L.hourlychime.i("SpeakOperation MultiAudioPlayer onComplete");
                HourlyChimeAudioPlayer.this.a(false, "");
                HourlyChimeAudioPlayer.this.a(str, true, r9);
                if (HourlyChimeAudioPlayer.this.a != null) {
                    HourlyChimeAudioPlayer.this.a.onComplete();
                }
                HourlyChimeAudioPlayer.this.b(multiAudioPlayer2, multiAudioPlayer);
            }

            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onError(int i, Exception exc) {
                L.hourlychime.i("SpeakOperation MultiAudioPlayer onError");
                HourlyChimeAudioPlayer.this.a(false, "");
                HourlyChimeAudioPlayer.this.a(str, true, r9);
                if (HourlyChimeAudioPlayer.this.a != null) {
                    HourlyChimeAudioPlayer.this.a.onError(i, exc);
                }
                HourlyChimeAudioPlayer.this.b(multiAudioPlayer2, multiAudioPlayer);
            }
        });
        r9.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.skills.alarm.model.HourlyChimeAudioPlayer$2  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 extends FakePlayer {
        final /* synthetic */ MultiAudioPlayer a;
        final /* synthetic */ String b;
        final /* synthetic */ MultiAudioPlayer c;
        final /* synthetic */ String d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(AudioSource audioSource, MultiAudioPlayer multiAudioPlayer, String str, MultiAudioPlayer multiAudioPlayer2, String str2) {
            super(audioSource);
            this.a = multiAudioPlayer;
            this.b = str;
            this.c = multiAudioPlayer2;
            this.d = str2;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStart() {
            final MultiAudioPlayer multiAudioPlayer = this.a;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeAudioPlayer$2$LS7mUOzck0jyHZIIpJtmoVJdFGE
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeAudioPlayer.AnonymousClass2.this.a(multiAudioPlayer);
                }
            });
            if (!TextUtils.isEmpty(this.b) && this.c != null) {
                File localAudioFile = HourlyChimeCacheManager.getInstance().getLocalAudioFile(this.b);
                if (localAudioFile == null || !localAudioFile.exists()) {
                    this.c.playUrl(this.b);
                } else {
                    L.hourlychime.i("playFile");
                    this.c.playFile(localAudioFile);
                }
            }
            if (!TextUtils.isEmpty(this.d)) {
                HourlyChimeAudioPlayer.this.a(true, this.d);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(MultiAudioPlayer multiAudioPlayer) {
            if (multiAudioPlayer != null) {
                multiAudioPlayer.playRawFile(R.raw.earthquake_alarm, HourlyChimeAudioPlayer.this.c);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStop(AudioSource audioSource) {
            final MultiAudioPlayer multiAudioPlayer = this.c;
            final MultiAudioPlayer multiAudioPlayer2 = this.a;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeAudioPlayer$2$atWM_m2Nu6BDwNxPLrpntLsfIwA
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeAudioPlayer.AnonymousClass2.this.b(multiAudioPlayer, multiAudioPlayer2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void b(MultiAudioPlayer multiAudioPlayer, MultiAudioPlayer multiAudioPlayer2) {
            if (multiAudioPlayer != null) {
                multiAudioPlayer.stop();
            }
            HourlyChimeAudioPlayer.this.b(multiAudioPlayer, multiAudioPlayer2);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void forceStop(AudioSource audioSource) {
            final MultiAudioPlayer multiAudioPlayer = this.c;
            final MultiAudioPlayer multiAudioPlayer2 = this.a;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeAudioPlayer$2$lwKvSGRa4g7OHnQfMyh2zznM7Lc
                @Override // java.lang.Runnable
                public final void run() {
                    HourlyChimeAudioPlayer.AnonymousClass2.this.a(multiAudioPlayer, multiAudioPlayer2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(MultiAudioPlayer multiAudioPlayer, MultiAudioPlayer multiAudioPlayer2) {
            if (multiAudioPlayer != null) {
                multiAudioPlayer.stop();
            }
            HourlyChimeAudioPlayer.this.b(multiAudioPlayer, multiAudioPlayer2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z, String str) {
        L.hourlychime.d("displayTts=%s,text=%s", Boolean.valueOf(z), str);
        if (z) {
            AsrTtsCard.getInstance().onTtsRequest(str);
        } else {
            AsrTtsCard.getInstance().hideTts();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void c(MultiAudioPlayer multiAudioPlayer, MultiAudioPlayer multiAudioPlayer2) {
        if (multiAudioPlayer != null) {
            multiAudioPlayer.release();
            multiAudioPlayer.setListener(null);
        }
        if (multiAudioPlayer2 != null) {
            multiAudioPlayer2.release();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(final MultiAudioPlayer multiAudioPlayer, final MultiAudioPlayer multiAudioPlayer2) {
        ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.skills.alarm.model.-$$Lambda$HourlyChimeAudioPlayer$-6lQU09_ZZR5g9GcergztKWC97E
            @Override // java.lang.Runnable
            public final void run() {
                HourlyChimeAudioPlayer.this.c(multiAudioPlayer, multiAudioPlayer2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(String str, boolean z, FakePlayer fakePlayer) {
        L.hourlychime.i("onTtsPlayFinish=%s", Boolean.valueOf(z));
        if (fakePlayer != null) {
            fakePlayer.stop();
        }
        HourlyChimeCacheManager.getInstance().deleteLocalAudioFile(str);
    }
}
