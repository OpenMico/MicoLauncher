package com.xiaomi.micolauncher.module.homepage.bean;

import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public enum ChildSongQuerySecond implements ChildSongQueryInterface {
    YAO_LAN(R.drawable.yingwenmoerduo, R.string.child_song_query_ying_yu),
    TAI_JIAO(R.drawable.qimengrenzhi, R.string.child_song_query_qi_meng),
    SAN_ZI(R.drawable.sanzierge, R.string.child_song_query_popular_san_zi),
    QI_MIAO(R.drawable.qimiaogushici, R.string.child_song_query_qi_miao),
    YI_YA(R.drawable.yiyafasheng, R.string.child_song_query_yi_ya),
    DONG_WU(R.drawable.dongwushijie, R.string.child_song_query_dong_wu);
    
    public int iconResId;
    public int titleResId;

    ChildSongQuerySecond(int i, int i2) {
        this.iconResId = i;
        this.titleResId = i2;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.ChildSongQueryInterface
    public int getIconResId() {
        return this.iconResId;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.ChildSongQueryInterface
    public int getTitleResId() {
        return this.titleResId;
    }

    public static ChildSongQueryInterface[] getEnumValues() {
        return values();
    }
}
