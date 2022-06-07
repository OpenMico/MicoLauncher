package com.xiaomi.micolauncher.skills.video.player;

import io.reactivex.functions.Consumer;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.video.player.-$$Lambda$d5KVShUUQF_LV1-jOrLthtxT2FY  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY implements Consumer {
    private final /* synthetic */ VideoPlayerPresenter f$0;

    public /* synthetic */ $$Lambda$d5KVShUUQF_LV1jOrLthtxT2FY(VideoPlayerPresenter videoPlayerPresenter) {
        this.f$0 = videoPlayerPresenter;
    }

    @Override // io.reactivex.functions.Consumer
    public final void accept(Object obj) {
        this.f$0.onLoadResourceError((Throwable) obj);
    }
}
