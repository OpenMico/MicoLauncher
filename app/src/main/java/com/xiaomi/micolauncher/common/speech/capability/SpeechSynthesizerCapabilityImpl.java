package com.xiaomi.micolauncher.common.speech.capability;

import android.os.Handler;
import com.xiaomi.ai.android.capability.SpeechSynthesizerCapability;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.speech.utils.PcmData;
import com.xiaomi.micolauncher.common.speech.utils.QueryLatency;

/* loaded from: classes3.dex */
public final class SpeechSynthesizerCapabilityImpl extends SpeechSynthesizerCapability {
    private Handler a;
    private long b;

    public SpeechSynthesizerCapabilityImpl(Handler handler) {
        this.a = handler;
    }

    @Override // com.xiaomi.ai.android.capability.SpeechSynthesizerCapability
    public void onPlayStart(int i) {
        L.speech.d("onPlayStart");
        this.b = 0L;
        this.a.removeMessages(1005);
        this.a.obtainMessage(1005, new PcmData(16000, 1, 16, false, null)).sendToTarget();
    }

    @Override // com.xiaomi.ai.android.capability.SpeechSynthesizerCapability
    public void onPlayStart(int i, String str) {
        L.speech.d("onPlayStart=%s", str);
        this.b = 0L;
        PcmData pcmData = new PcmData(16000, 1, 16, false, null);
        pcmData.setDialogId(str);
        this.a.removeMessages(1005);
        this.a.obtainMessage(1005, pcmData).sendToTarget();
    }

    @Override // com.xiaomi.ai.android.capability.SpeechSynthesizerCapability
    public void onPlayFinish() {
        L.speech.d("onPlayFinish");
        QueryLatency.getInstance().setTtsEndPacketMs(this.b);
        this.a.obtainMessage(1005, new PcmData(16000, 1, 16, true, null)).sendToTarget();
    }

    @Override // com.xiaomi.ai.android.capability.SpeechSynthesizerCapability
    public void onPcmData(byte[] bArr) {
        this.b += bArr.length;
        QueryLatency.getInstance().compareSetTtsFirstPacketMs();
        this.a.obtainMessage(1005, new PcmData(16000, 1, 16, false, bArr)).sendToTarget();
    }
}
