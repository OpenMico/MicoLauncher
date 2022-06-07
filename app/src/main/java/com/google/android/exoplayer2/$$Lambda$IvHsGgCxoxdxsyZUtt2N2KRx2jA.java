package com.google.android.exoplayer2;

import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.ListenerSet;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.-$$Lambda$IvHsGgCxoxdxsyZUtt2N2KRx2jA  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$IvHsGgCxoxdxsyZUtt2N2KRx2jA implements ListenerSet.Event {
    public static final /* synthetic */ $$Lambda$IvHsGgCxoxdxsyZUtt2N2KRx2jA INSTANCE = new $$Lambda$IvHsGgCxoxdxsyZUtt2N2KRx2jA();

    private /* synthetic */ $$Lambda$IvHsGgCxoxdxsyZUtt2N2KRx2jA() {
    }

    @Override // com.google.android.exoplayer2.util.ListenerSet.Event
    public final void invoke(Object obj) {
        ((Player.EventListener) obj).onSeekProcessed();
    }
}
