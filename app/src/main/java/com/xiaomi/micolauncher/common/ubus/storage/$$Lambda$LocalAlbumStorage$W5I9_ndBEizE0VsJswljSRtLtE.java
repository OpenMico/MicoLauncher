package com.xiaomi.micolauncher.common.ubus.storage;

import android.media.MediaScannerConnection;
import android.net.Uri;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.common.ubus.storage.-$$Lambda$LocalAlbumStorage$W5I9_ndBEizE0VsJswljSR-tLtE  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$LocalAlbumStorage$W5I9_ndBEizE0VsJswljSRtLtE implements MediaScannerConnection.OnScanCompletedListener {
    public static final /* synthetic */ $$Lambda$LocalAlbumStorage$W5I9_ndBEizE0VsJswljSRtLtE INSTANCE = new $$Lambda$LocalAlbumStorage$W5I9_ndBEizE0VsJswljSRtLtE();

    private /* synthetic */ $$Lambda$LocalAlbumStorage$W5I9_ndBEizE0VsJswljSRtLtE() {
    }

    @Override // android.media.MediaScannerConnection.OnScanCompletedListener
    public final void onScanCompleted(String str, Uri uri) {
        LocalAlbumStorage.a(str, uri);
    }
}
