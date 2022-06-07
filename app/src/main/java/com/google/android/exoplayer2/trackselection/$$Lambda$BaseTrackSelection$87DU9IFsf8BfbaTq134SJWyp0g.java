package com.google.android.exoplayer2.trackselection;

import com.google.android.exoplayer2.Format;
import java.util.Comparator;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.trackselection.-$$Lambda$BaseTrackSelection$87DU9IFsf8B-fbaTq134SJWyp0g  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$BaseTrackSelection$87DU9IFsf8BfbaTq134SJWyp0g implements Comparator {
    public static final /* synthetic */ $$Lambda$BaseTrackSelection$87DU9IFsf8BfbaTq134SJWyp0g INSTANCE = new $$Lambda$BaseTrackSelection$87DU9IFsf8BfbaTq134SJWyp0g();

    private /* synthetic */ $$Lambda$BaseTrackSelection$87DU9IFsf8BfbaTq134SJWyp0g() {
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        int a;
        a = BaseTrackSelection.a((Format) obj, (Format) obj2);
        return a;
    }
}
