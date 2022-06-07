package com.xiaomi.micolauncher.skills.music.view_v2;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerChildFragmentV2$CwujswAfc6LCNSWDG4TnCjyH9aw  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$PlayerChildFragmentV2$CwujswAfc6LCNSWDG4TnCjyH9aw implements LrcManager.OnLrcChangeListener {
    private final /* synthetic */ PlayerChildFragmentV2 f$0;

    public /* synthetic */ $$Lambda$PlayerChildFragmentV2$CwujswAfc6LCNSWDG4TnCjyH9aw(PlayerChildFragmentV2 playerChildFragmentV2) {
        this.f$0 = playerChildFragmentV2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager.OnLrcChangeListener
    public final void onLrcRowsChange(String str, List list) {
        this.f$0.a(str, list);
    }
}
