package com.xiaomi.micolauncher.common.track;

import com.xiaomi.ai.android.helper.TrackHelper;
import com.xiaomi.ai.error.AivsError;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.track.-$$Lambda$TrackLog$E4-O95n3NfnW5qiUymZaACdONVs  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$TrackLog$E4O95n3NfnW5qiUymZaACdONVs implements TrackHelper.OnErrorListener {
    public static final /* synthetic */ $$Lambda$TrackLog$E4O95n3NfnW5qiUymZaACdONVs INSTANCE = new $$Lambda$TrackLog$E4O95n3NfnW5qiUymZaACdONVs();

    private /* synthetic */ $$Lambda$TrackLog$E4O95n3NfnW5qiUymZaACdONVs() {
    }

    @Override // com.xiaomi.ai.android.helper.TrackHelper.OnErrorListener
    public final void onError(AivsError aivsError) {
        TrackLog.a(aivsError);
    }
}
