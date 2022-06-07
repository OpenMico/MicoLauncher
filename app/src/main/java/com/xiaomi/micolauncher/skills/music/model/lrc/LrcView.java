package com.xiaomi.micolauncher.skills.music.model.lrc;

import java.util.List;

/* loaded from: classes3.dex */
public interface LrcView {
    boolean hasLrcRows();

    void invalidateView();

    void onReset();

    void reset();

    void seekTo(int i, boolean z, boolean z2);

    void setEmptyText(String str);

    void setLrcRows(List<LrcRow> list);
}
