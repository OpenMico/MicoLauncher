package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.trackselection.TrackSelector;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.offline.-$$Lambda$DownloadHelper$RIWRHV1mg0J2ynRMoPs2q6f1NWs  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$DownloadHelper$RIWRHV1mg0J2ynRMoPs2q6f1NWs implements TrackSelector.InvalidationListener {
    public static final /* synthetic */ $$Lambda$DownloadHelper$RIWRHV1mg0J2ynRMoPs2q6f1NWs INSTANCE = new $$Lambda$DownloadHelper$RIWRHV1mg0J2ynRMoPs2q6f1NWs();

    private /* synthetic */ $$Lambda$DownloadHelper$RIWRHV1mg0J2ynRMoPs2q6f1NWs() {
    }

    @Override // com.google.android.exoplayer2.trackselection.TrackSelector.InvalidationListener
    public final void onTrackSelectionsInvalidated() {
        DownloadHelper.e();
    }
}
