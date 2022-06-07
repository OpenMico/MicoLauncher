package com.xiaomi.micolauncher.instruciton.impl;

import android.text.TextUtils;
import com.xiaomi.ai.api.SpeechSynthesizer;
import com.xiaomi.ai.api.common.Instruction;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.event.TtsPlayEndEvent;
import com.xiaomi.micolauncher.common.event.TtsPlayErrorEvent;
import com.xiaomi.micolauncher.common.player.MultiAudioPlayer;
import com.xiaomi.micolauncher.common.player.base.BasePlayer;
import com.xiaomi.micolauncher.common.player.base.FakePlayer;
import com.xiaomi.micolauncher.common.player.policy.AudioSource;
import com.xiaomi.micolauncher.instruciton.base.BaseOperation;
import com.xiaomi.micolauncher.instruciton.base.OperationManager;
import com.xiaomi.micolauncher.instruciton.impl.SpeechSynthesizerSpeakOperation;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public class SpeechSynthesizerSpeakOperation extends BaseOperation<Instruction<SpeechSynthesizer.Speak>> {
    private MultiAudioPlayer b;
    private String c;
    private String d;
    protected FakePlayer fakePlayer;

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected boolean supportAsync() {
        return true;
    }

    public SpeechSynthesizerSpeakOperation(Instruction instruction) {
        super(instruction);
    }

    @Override // com.xiaomi.micolauncher.instruciton.base.BaseOperation
    protected BaseOperation.OpState onProcess(boolean z) {
        BaseOperation.OpState opState = BaseOperation.OpState.STATE_FAIL;
        SpeechSynthesizer.Speak speak = (SpeechSynthesizer.Speak) this.instruction.getPayload();
        this.c = speak.getText();
        if (speak.getUrl().isPresent()) {
            this.d = speak.getUrl().get();
            if (!TextUtils.isEmpty(this.d)) {
                a(this.d);
                opState = BaseOperation.OpState.STATE_SUCCESS;
            }
        }
        L.ope.d("Operation=%s,onProcess dependence=%s", this, Boolean.valueOf(z));
        if (z) {
            opState = BaseOperation.OpState.STATE_PROCESSING;
            if (TextUtils.isEmpty(this.d)) {
                register();
            }
        }
        return opState;
    }

    private void a(String str) {
        b("play");
        this.b = new MultiAudioPlayer(getContext(), BasePlayer.StreamType.STREAM_TYPE_MUSIC);
        this.b.setListener(new MultiAudioPlayer.PlayerListener() { // from class: com.xiaomi.micolauncher.instruciton.impl.SpeechSynthesizerSpeakOperation.1
            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onPrepared() {
                SpeechSynthesizerSpeakOperation.this.b("play");
            }

            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onComplete() {
                L.ope.i("SpeakOperation MultiAudioPlayer onComplete");
                SpeechSynthesizerSpeakOperation.this.a(true);
            }

            @Override // com.xiaomi.micolauncher.common.player.MultiAudioPlayer.PlayerListener
            public void onError(int i, Exception exc) {
                L.ope.i("SpeakOperation MultiAudioPlayer onError");
                SpeechSynthesizerSpeakOperation.this.a(true);
                SpeechSynthesizerSpeakOperation.this.b("error");
            }
        });
        this.fakePlayer = new AnonymousClass2(AudioSource.AUDIO_SOURCE_TTS, str);
        this.fakePlayer.start();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaomi.micolauncher.instruciton.impl.SpeechSynthesizerSpeakOperation$2  reason: invalid class name */
    /* loaded from: classes3.dex */
    public class AnonymousClass2 extends FakePlayer {
        final /* synthetic */ String a;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        AnonymousClass2(AudioSource audioSource, String str) {
            super(audioSource);
            this.a = str;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStart() {
            final String str = this.a;
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$SpeechSynthesizerSpeakOperation$2$2baJSTnS1PAduMhKerPEGUAFDfE
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechSynthesizerSpeakOperation.AnonymousClass2.this.a(str);
                }
            });
            SpeechSynthesizerSpeakOperation.this.b("prepare");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void a(String str) {
            if (SpeechSynthesizerSpeakOperation.this.b != null) {
                SpeechSynthesizerSpeakOperation.this.b.playUrl(str);
            }
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer
        public void postStop(AudioSource audioSource) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$SpeechSynthesizerSpeakOperation$2$zYIrOQErzfmwiXpdIgJYeU0gwas
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechSynthesizerSpeakOperation.AnonymousClass2.this.d();
                }
            });
            L.ope.i("post stop speak Operation=%s", SpeechSynthesizerSpeakOperation.this.getId());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void d() {
            if (SpeechSynthesizerSpeakOperation.this.b != null) {
                SpeechSynthesizerSpeakOperation.this.b.stop();
            }
            SpeechSynthesizerSpeakOperation.this.a();
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.xiaomi.micolauncher.common.player.base.FakePlayer, com.xiaomi.micolauncher.common.player.base.Player
        public void forceStop(AudioSource audioSource) {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.instruciton.impl.-$$Lambda$SpeechSynthesizerSpeakOperation$2$YiTX9Mhv2Fh-i6JL8jqOhfieyAI
                @Override // java.lang.Runnable
                public final void run() {
                    SpeechSynthesizerSpeakOperation.AnonymousClass2.this.c();
                }
            });
            L.ope.i("post stop speak Operation=%s", SpeechSynthesizerSpeakOperation.this.getId());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void c() {
            if (SpeechSynthesizerSpeakOperation.this.b != null) {
                SpeechSynthesizerSpeakOperation.this.b.stop();
            }
            SpeechSynthesizerSpeakOperation.this.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(boolean z) {
        L.ope.i("onTtsPlayFinish=%s", Boolean.valueOf(z));
        FakePlayer fakePlayer = this.fakePlayer;
        if (fakePlayer != null) {
            fakePlayer.stop();
        }
        a();
        if (z) {
            OperationManager.getInstance().onTtsPlayEnd(this.c, getDialogId(), z);
        } else {
            OperationManager.getInstance().onTtsPlayError(this.c, getDialogId());
        }
        notifyProcessDone(z ? BaseOperation.OpState.STATE_SUCCESS : BaseOperation.OpState.STATE_FAIL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        MultiAudioPlayer multiAudioPlayer = this.b;
        if (multiAudioPlayer != null) {
            multiAudioPlayer.release();
            this.b = null;
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onTtsPlayFinishEvent(TtsPlayEndEvent ttsPlayEndEvent) {
        L.ope.i("onTtsPlayFinishEvent=%s,query=%s,interrupt=%s", getId(), ttsPlayEndEvent.query, Boolean.valueOf(ttsPlayEndEvent.interrupt));
        if (ttsPlayEndEvent.interrupt) {
            notifyProcessDone(BaseOperation.OpState.STATE_FAIL);
        } else {
            notifyProcessDone(BaseOperation.OpState.STATE_SUCCESS);
        }
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onTtsPlayErrorEvent(TtsPlayErrorEvent ttsPlayErrorEvent) {
        L.ope.i("onTtsPlayErrorEvent=%s", getId());
        notifyProcessDone(BaseOperation.OpState.STATE_FAIL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(String str) {
        L.monitor.i("dialog_id=%s,player_name=%s,action=%s,text=%s,play_url=%s", getDialogId(), "SpeakOperation", str, this.c, this.d);
    }
}
