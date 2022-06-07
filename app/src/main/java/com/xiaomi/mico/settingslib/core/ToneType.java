package com.xiaomi.mico.settingslib.core;

/* loaded from: classes3.dex */
public enum ToneType {
    AiNiRobot("AiNiRobot", "蜜糖(甜美可爱女声)", ""),
    XiaoMi("XiaoMi", "茉莉(温柔知性女声)", "_xiaomi"),
    XiaoMi_M88("XiaoMi_M88", "青葱(阳光活力男声)", "_xiaomi_m88"),
    XiaoMi_xinran("XiaoMi", "泡芙(奶萌乖巧童声)", "_xiaomi_xinran");
    
    private String nameRaw;
    private String nameZh;
    private String vendor;

    ToneType(String str, String str2, String str3) {
        this.vendor = str;
        this.nameZh = str2;
        this.nameRaw = str3;
    }

    public String getNameZh() {
        return this.nameZh;
    }

    public String getNameRaw() {
        return this.nameRaw;
    }
}
