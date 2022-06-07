package com.xiaomi.passport.ui.settings;

import com.xiaomi.passport.ui.R;

/* loaded from: classes4.dex */
public class AsyncTaskResult {
    static final int EXCEPTION_TYPE_BINDING_PHONE_RESTRICTED = 11;
    static final int EXCEPTION_TYPE_ERROR_CAPTCHA = 12;
    static final int EXCEPTION_TYPE_ERROR_VERIFY_CODE = 7;
    static final int EXCEPTION_TYPE_FORBIDDEN = 4;
    static final int EXCEPTION_TYPE_GET_PHONE_VCODE_EXCEED_LIMIT = 10;
    static final int EXCEPTION_TYPE_ILLEGAL_DEVICE_ID = 14;
    static final int EXCEPTION_TYPE_INVALID_BIND_ADDRESS = 9;
    static final int EXCEPTION_TYPE_INVALID_PARAM = 16;
    static final int EXCEPTION_TYPE_NEED_SMS_CODE = 15;
    static final int EXCEPTION_TYPE_NETWORK = 2;
    static final int EXCEPTION_TYPE_NO = 0;
    static final int EXCEPTION_TYPE_PASSWORD = 1;
    static final int EXCEPTION_TYPE_SEND_EMAIL_REACH_LIMIT = 13;
    static final int EXCEPTION_TYPE_SERVER = 3;
    static final int EXCEPTION_TYPE_TOKEN_EXPIRED = 6;
    static final int EXCEPTION_TYPE_UNKNOWN = 5;
    static final int EXCEPTION_TYPE_USED_EMAIL_ADDRESS = 8;
    static final int EXCEPTION_TYPE_WRONG_PHONE_NUMBER_FORMAT = 17;
    private int mExceptionType;

    public AsyncTaskResult() {
        this.mExceptionType = 0;
    }

    public AsyncTaskResult(int i) {
        this.mExceptionType = i;
    }

    public boolean hasException() {
        return this.mExceptionType != 0;
    }

    public int getExceptionReason() {
        switch (this.mExceptionType) {
            case 0:
                return 0;
            case 1:
                return R.string.passport_bad_authentication;
            case 2:
                return R.string.passport_error_network;
            case 3:
                return R.string.passport_error_server;
            case 4:
                return R.string.passport_access_denied;
            case 5:
            case 15:
            case 16:
            default:
                return R.string.passport_error_unknown;
            case 6:
                return R.string.sns_access_token_expired_warning;
            case 7:
                return R.string.passport_wrong_vcode;
            case 8:
                return R.string.error_dup_binded_email;
            case 9:
                return R.string.error_invalid_bind_address;
            case 10:
                return R.string.get_phone_verifycode_exceed_limit;
            case 11:
                return R.string.exceed_binded_phone_times_notice;
            case 12:
                return R.string.passport_wrong_captcha;
            case 13:
                return R.string.resend_email_reach_limit_message;
            case 14:
                return R.string.passport_error_device_id;
            case 17:
                return R.string.passport_wrong_phone_number_format;
        }
    }

    public int getExceptionType() {
        return this.mExceptionType;
    }
}
