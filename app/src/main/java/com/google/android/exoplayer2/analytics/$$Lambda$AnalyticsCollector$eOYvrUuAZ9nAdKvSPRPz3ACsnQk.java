package com.google.android.exoplayer2.analytics;

import com.google.android.exoplayer2.util.FlagSet;
import com.google.android.exoplayer2.util.ListenerSet;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.analytics.-$$Lambda$AnalyticsCollector$eOYvrUuAZ9nAdKvSPRPz3ACsnQk  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$AnalyticsCollector$eOYvrUuAZ9nAdKvSPRPz3ACsnQk implements ListenerSet.IterationFinishedEvent {
    public static final /* synthetic */ $$Lambda$AnalyticsCollector$eOYvrUuAZ9nAdKvSPRPz3ACsnQk INSTANCE = new $$Lambda$AnalyticsCollector$eOYvrUuAZ9nAdKvSPRPz3ACsnQk();

    private /* synthetic */ $$Lambda$AnalyticsCollector$eOYvrUuAZ9nAdKvSPRPz3ACsnQk() {
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.IterationFinishedEvent
    public final void invoke(Object obj, FlagSet flagSet) {
        AnalyticsCollector.a((AnalyticsListener) obj, flagSet);
    }
}
