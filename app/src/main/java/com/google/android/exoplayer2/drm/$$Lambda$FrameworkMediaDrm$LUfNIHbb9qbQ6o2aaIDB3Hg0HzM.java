package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.drm.ExoMediaDrm;
import java.util.UUID;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.drm.-$$Lambda$FrameworkMediaDrm$LUfNIHbb9qbQ6o2aaIDB3Hg0HzM  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$FrameworkMediaDrm$LUfNIHbb9qbQ6o2aaIDB3Hg0HzM implements ExoMediaDrm.Provider {
    public static final /* synthetic */ $$Lambda$FrameworkMediaDrm$LUfNIHbb9qbQ6o2aaIDB3Hg0HzM INSTANCE = new $$Lambda$FrameworkMediaDrm$LUfNIHbb9qbQ6o2aaIDB3Hg0HzM();

    private /* synthetic */ $$Lambda$FrameworkMediaDrm$LUfNIHbb9qbQ6o2aaIDB3Hg0HzM() {
    }

    @Override // com.google.android.exoplayer2.drm.ExoMediaDrm.Provider
    public final ExoMediaDrm acquireExoMediaDrm(UUID uuid) {
        ExoMediaDrm b;
        b = FrameworkMediaDrm.b(uuid);
        return b;
    }
}
