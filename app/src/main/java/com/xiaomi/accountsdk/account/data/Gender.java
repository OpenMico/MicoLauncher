package com.xiaomi.accountsdk.account.data;

import org.fourthline.cling.support.messagebox.parser.MessageElement;

/* loaded from: classes2.dex */
public enum Gender {
    MALE(MessageElement.XPATH_PREFIX),
    FEMALE("f");
    
    private String mGender;

    Gender(String str) {
        this.mGender = str;
    }

    public String getType() {
        return this.mGender;
    }
}
