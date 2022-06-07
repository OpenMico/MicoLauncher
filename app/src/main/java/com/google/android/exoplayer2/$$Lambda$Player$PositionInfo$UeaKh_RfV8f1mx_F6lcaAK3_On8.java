package com.google.android.exoplayer2;

import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.Player;

/* compiled from: lambda */
/* renamed from: com.google.android.exoplayer2.-$$Lambda$Player$PositionInfo$UeaKh_RfV8f1mx_F6lcaAK3_On8  reason: invalid class name */
/* loaded from: classes.dex */
public final /* synthetic */ class $$Lambda$Player$PositionInfo$UeaKh_RfV8f1mx_F6lcaAK3_On8 implements Bundleable.Creator {
    public static final /* synthetic */ $$Lambda$Player$PositionInfo$UeaKh_RfV8f1mx_F6lcaAK3_On8 INSTANCE = new $$Lambda$Player$PositionInfo$UeaKh_RfV8f1mx_F6lcaAK3_On8();

    private /* synthetic */ $$Lambda$Player$PositionInfo$UeaKh_RfV8f1mx_F6lcaAK3_On8() {
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        Player.PositionInfo a;
        a = Player.PositionInfo.a(bundle);
        return a;
    }
}
