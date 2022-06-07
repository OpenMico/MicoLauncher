package com.xiaomi.micolauncher.module.intercom;

import com.zlw.main.recorderlib.recorder.listener.RecordResultListener;
import java.io.File;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.module.intercom.-$$Lambda$IntercomRecordFragment$DufslfuhpMcoVDo5m5IE6wmC938  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$IntercomRecordFragment$DufslfuhpMcoVDo5m5IE6wmC938 implements RecordResultListener {
    public static final /* synthetic */ $$Lambda$IntercomRecordFragment$DufslfuhpMcoVDo5m5IE6wmC938 INSTANCE = new $$Lambda$IntercomRecordFragment$DufslfuhpMcoVDo5m5IE6wmC938();

    private /* synthetic */ $$Lambda$IntercomRecordFragment$DufslfuhpMcoVDo5m5IE6wmC938() {
    }

    @Override // com.zlw.main.recorderlib.recorder.listener.RecordResultListener
    public final void onResult(File file) {
        IntercomRecordFragment.a(file);
    }
}
