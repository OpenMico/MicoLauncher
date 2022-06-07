package com.xiaomi.micolauncher.module.homepage.bean;

import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public enum ChildSongQueryFirst implements ChildSongQueryInterface {
    YAO_LAN(R.drawable.yaolanqu, R.string.child_song_query_lullaby),
    TAI_JIAO(R.drawable.taijiao, R.string.child_song_query_prenatal_education),
    LIU_XING(R.drawable.liuxingtongyao, R.string.child_song_query_popular_nursery_rhymes),
    AN_QUAN(R.drawable.anquanhaoxiguan, R.string.child_song_query_safe_habits),
    SHENG_HUO(R.drawable.shenghuohaoxiguan, R.string.child_song_query_good_habits),
    DONG_HUA(R.drawable.donghuaerge, R.string.child_song_query_animated_nursery_rhymes);
    
    public int iconResId;
    public int titleResId;

    @Override // com.xiaomi.micolauncher.module.homepage.bean.ChildSongQueryInterface
    public int getIconResId() {
        return this.iconResId;
    }

    @Override // com.xiaomi.micolauncher.module.homepage.bean.ChildSongQueryInterface
    public int getTitleResId() {
        return this.titleResId;
    }

    ChildSongQueryFirst(int i, int i2) {
        this.iconResId = i;
        this.titleResId = i2;
    }

    public static ChildSongQueryInterface[] getEnumValues() {
        return values();
    }
}
