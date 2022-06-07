package com.xiaomi.micolauncher.skills.music.view_v2;

import com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager;
import java.util.List;

/* compiled from: lambda */
/* renamed from: com.xiaomi.micolauncher.skills.music.view_v2.-$$Lambda$PlayerMainFragmentV2$T9-09LLWNDI8Yw18atYgx6RlURM  reason: invalid class name */
/* loaded from: classes3.dex */
public final /* synthetic */ class $$Lambda$PlayerMainFragmentV2$T909LLWNDI8Yw18atYgx6RlURM implements LrcManager.OnLrcChangeListener {
    private final /* synthetic */ PlayerMainFragmentV2 f$0;

    public /* synthetic */ $$Lambda$PlayerMainFragmentV2$T909LLWNDI8Yw18atYgx6RlURM(PlayerMainFragmentV2 playerMainFragmentV2) {
        this.f$0 = playerMainFragmentV2;
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcManager.OnLrcChangeListener
    public final void onLrcRowsChange(String str, List list) {
        this.f$0.a(str, list);
    }
}
