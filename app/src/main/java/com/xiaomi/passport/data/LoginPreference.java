package com.xiaomi.passport.data;

/* loaded from: classes4.dex */
public class LoginPreference {
    public String idcZone;
    public PhoneLoginType phoneLoginType;
    public String userRegion;

    public LoginPreference(String str, String str2, PhoneLoginType phoneLoginType) {
        this.idcZone = str;
        this.userRegion = str2;
        this.phoneLoginType = phoneLoginType;
    }

    /* loaded from: classes4.dex */
    public enum PhoneLoginType {
        ticket("ticket"),
        password("password");
        
        private final String value;

        PhoneLoginType(String str) {
            this.value = str;
        }
    }
}
