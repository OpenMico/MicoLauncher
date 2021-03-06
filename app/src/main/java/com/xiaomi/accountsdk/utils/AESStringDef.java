package com.xiaomi.accountsdk.utils;

import android.text.TextUtils;

/* loaded from: classes2.dex */
public class AESStringDef {
    private static final String aesFormat = "%s:%s:%s";
    private static final String tokenSeperator = ":";
    private String IV;
    private String data;
    private String version;

    /* loaded from: classes2.dex */
    static class InvalidAESDataException extends Exception {
        InvalidAESDataException(String str) {
            super(str);
        }
    }

    private AESStringDef() {
    }

    public String toString() {
        return String.format(aesFormat, this.version, this.IV, this.data);
    }

    public static AESStringDef getInstance(String str) throws InvalidAESDataException {
        String[] split = TextUtils.split(str, ":");
        if (split == null || split.length != 3) {
            throw new InvalidAESDataException("invalid encrypt string format,the correct format is version:iv:content but original string is:" + str);
        }
        AESStringDef aESStringDef = new AESStringDef();
        aESStringDef.version = split[0];
        aESStringDef.IV = split[1];
        aESStringDef.data = split[2];
        return aESStringDef;
    }

    public static AESStringDef getInstance(String str, String str2, String str3) throws InvalidAESDataException {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            throw new InvalidAESDataException("invalid AES data");
        }
        AESStringDef aESStringDef = new AESStringDef();
        aESStringDef.version = str;
        aESStringDef.IV = str2;
        aESStringDef.data = str3;
        return aESStringDef;
    }

    public String getVersion() {
        return this.version;
    }

    public String getIV() {
        return this.IV;
    }

    public String getData() {
        return this.data;
    }
}
