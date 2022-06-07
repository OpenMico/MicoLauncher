package com.xiaomi.micolauncher.skills.video.model;

/* loaded from: classes3.dex */
public enum VideoPlayerType {
    UNKNOWN(-1),
    TENCENT_VIDEO(0),
    IQIYI(1),
    YOUKU(2),
    MANGGUO_TV(3),
    BILIBILI(4),
    MI_PLAYER(5),
    VIPKID(6),
    MAOYAN(7),
    DOUBAN(8),
    SOUHU(9);
    
    private int id;

    VideoPlayerType(int i) {
        this.id = i;
    }

    public int getId() {
        return this.id;
    }
}
