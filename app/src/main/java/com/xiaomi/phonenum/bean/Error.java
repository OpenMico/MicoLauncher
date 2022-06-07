package com.xiaomi.phonenum.bean;

import com.xiaomi.phonenum.bean.PhoneNum;

/* loaded from: classes4.dex */
public enum Error {
    NONE(0),
    TOKEN_EXPIRED(408),
    UNKNOW(1000),
    SEND_SMS_FAILED(1001),
    JSON(1002),
    SIM_NOT_READY(1003),
    DATA_NOT_ENABLED(1004),
    CELLULAR_NETWORK_NOT_AVAILABLE(1005),
    FAILED_ALL(1006),
    RECIVE_UNIKEY_FAILED(1007),
    NO_CHANGE_NETWORK_STATE_PERMISSION(1009),
    NO_READ_PHONE_STATE_PERMISSION(1010),
    INTERRUPTED(1011),
    SMS_OBTAIN_FAILED(1012),
    NETWORK_ROAMING(1013),
    IO_EXCEPTION(1014),
    NOT_IN_SERVICE(1015),
    NO_SEND_SMS_PERMISSION(1016),
    NO_RECEIVE_SMS_PERMISSION(1017),
    NOT_SUPPORT(1018);
    
    public final int code;

    Error(int i) {
        this.code = i;
    }

    public static Error codeToError(int i) {
        Error[] values = values();
        for (Error error : values) {
            if (error.code == i) {
                return error;
            }
        }
        return UNKNOW;
    }

    public PhoneNum result() {
        return new PhoneNum.Builder().errorCode(this.code).build();
    }

    public PhoneNum result(String str) {
        return new PhoneNum.Builder().errorCode(this.code).errorMsg(str).build();
    }
}
