package com.xiaomi.mico.settingslib.lock;

import com.xiaomi.mico.settingslib.R;

/* loaded from: classes3.dex */
public enum LockApp {
    MI_JIA(LockSetting.MI_JIA_APP, R.drawable.skill_mijia, R.string.mijia),
    AI_QI_YI(LockSetting.AI_QI_YI_APP, R.drawable.skill_aiqiyi, R.string.aiqiyi),
    YOU_KU(LockSetting.YOU_KU_APP, R.drawable.skill_youku, R.string.youku),
    DOU_YIN(LockSetting.TIK_TOK_APP, R.drawable.skill_tiktok_no_beta, R.string.tiktok),
    YY(LockSetting.YY_APP, R.drawable.skill_yy, R.string.yy),
    TENCENT(LockSetting.TENCENT_APP, R.drawable.skill_tencent, R.string.tencent),
    BILIBILI(LockSetting.BILIBILI_APP, R.drawable.skill_bilibili, R.string.bilibili);
    
    private final int appNameResId;
    private final int iconResId;
    private String id;

    LockApp(String str, int i, int i2) {
        this.id = str;
        this.iconResId = i;
        this.appNameResId = i2;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String str) {
        this.id = str;
    }

    public int getIconResId() {
        return this.iconResId;
    }

    public int getAppNameResId() {
        return this.appNameResId;
    }
}
