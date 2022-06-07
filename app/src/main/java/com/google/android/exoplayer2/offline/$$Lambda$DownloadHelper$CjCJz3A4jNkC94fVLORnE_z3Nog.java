package com.google.android.exoplayer2.offline;

import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.MetadataOutput;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.offline.-$$Lambda$DownloadHelper$CjCJz3A4jNkC94fVLORnE_z3Nog  reason: invalid class name */
/* loaded from: classes2.dex */
public final /* synthetic */ class $$Lambda$DownloadHelper$CjCJz3A4jNkC94fVLORnE_z3Nog implements MetadataOutput {
    public static final /* synthetic */ $$Lambda$DownloadHelper$CjCJz3A4jNkC94fVLORnE_z3Nog INSTANCE = new $$Lambda$DownloadHelper$CjCJz3A4jNkC94fVLORnE_z3Nog();

    private /* synthetic */ $$Lambda$DownloadHelper$CjCJz3A4jNkC94fVLORnE_z3Nog() {
    }

    @Override // com.google.android.exoplayer2.metadata.MetadataOutput
    public final void onMetadata(Metadata metadata) {
        DownloadHelper.a(metadata);
    }
}
