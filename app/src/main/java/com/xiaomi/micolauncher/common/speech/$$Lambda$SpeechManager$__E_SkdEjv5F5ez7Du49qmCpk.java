package com.xiaomi.micolauncher.common.speech;

import com.xiaomi.ai.speaker.vpmclient.VpmClient;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.speech.-$$Lambda$SpeechManager$__E_SkdEj-v5F5ez7Du49-qmCpk  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$SpeechManager$__E_SkdEjv5F5ez7Du49qmCpk implements VpmClient.ILogHook {
    public static final /* synthetic */ $$Lambda$SpeechManager$__E_SkdEjv5F5ez7Du49qmCpk INSTANCE = new $$Lambda$SpeechManager$__E_SkdEjv5F5ez7Du49qmCpk();

    private /* synthetic */ $$Lambda$SpeechManager$__E_SkdEjv5F5ez7Du49qmCpk() {
    }

    @Override // com.xiaomi.ai.speaker.vpmclient.VpmClient.ILogHook
    public final void onLog(String str, Object[] objArr) {
        SpeechManager.a(str, objArr);
    }
}
