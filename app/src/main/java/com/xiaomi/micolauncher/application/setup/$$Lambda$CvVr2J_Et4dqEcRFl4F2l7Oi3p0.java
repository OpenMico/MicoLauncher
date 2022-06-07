package com.xiaomi.micolauncher.application.setup;

import com.elvishew.xlog.formatter.thread.ThreadFormatter;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.application.setup.-$$Lambda$CvVr2J_Et4dqEcRFl4F2l7Oi3p0  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$CvVr2J_Et4dqEcRFl4F2l7Oi3p0 implements ThreadFormatter {
    public static final /* synthetic */ $$Lambda$CvVr2J_Et4dqEcRFl4F2l7Oi3p0 INSTANCE = new $$Lambda$CvVr2J_Et4dqEcRFl4F2l7Oi3p0();

    private /* synthetic */ $$Lambda$CvVr2J_Et4dqEcRFl4F2l7Oi3p0() {
    }

    @Override // com.elvishew.xlog.formatter.Formatter
    public final String format(Thread thread) {
        return thread.getName();
    }
}
