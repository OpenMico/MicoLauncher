package com.xiaomi.passport.ui.internal.util;

import com.xiaomi.accountsdk.account.exception.InvalidPhoneNumException;
import com.xiaomi.accountsdk.account.exception.InvalidVerifyCodeException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.TokenExpiredException;
import com.xiaomi.accountsdk.account.exception.UserRestrictedException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.uicontroller.PhoneLoginController;
import java.io.IOException;

/* loaded from: classes4.dex */
public enum ErrorInfo {
    NONE(-1),
    ERROR_UNKNOWN(R.string.passport_error_unknown),
    ERROR_PASSWORD(R.string.passport_bad_authentication),
    ERROR_AUTH_FAIL(R.string.passport_error_auth_fail),
    ERROR_NETWORK(R.string.passport_error_network),
    ERROR_SERVER(R.string.passport_error_server),
    ERROR_ACCESS_DENIED(R.string.passport_access_denied),
    ERROR_CAPTCHA(R.string.passport_wrong_captcha),
    ERROR_DEVICE_ID(R.string.passport_error_device_id),
    ERROR_VERIFY_CODE(R.string.passport_wrong_vcode),
    ERROR_PHONE_REG_RESTRICTED(R.string.passport_register_restricted),
    ERROR_SEND_PHONE_VCODE_REACH_LIMIT(R.string.passport_send_too_many_code),
    ERROR_INVALID_PHONE_NUM(R.string.passport_wrong_phone_number_format),
    ERROR_NO_EXIST_USER_NAME(R.string.passport_error_user_name),
    ERROR_TOKEN_EXPIRED(R.string.passport_identification_expired),
    ERROR_PHONE_TICKET(R.string.passport_wrong_vcode),
    ERROR_NO_PHONE(R.string.passport_set_password_no_phone_msg);
    
    public int errorMessageId;

    ErrorInfo(int i) {
        this.errorMessageId = i;
    }

    public static int getMsgIdGivenErrorCode(PhoneLoginController.ErrorCode errorCode) {
        if (errorCode == PhoneLoginController.ErrorCode.ERROR_ACCESS_DENIED) {
            return ERROR_ACCESS_DENIED.errorMessageId;
        }
        if (errorCode == PhoneLoginController.ErrorCode.ERROR_AUTH_FAIL) {
            return ERROR_AUTH_FAIL.errorMessageId;
        }
        if (errorCode == PhoneLoginController.ErrorCode.ERROR_NETWORK) {
            return ERROR_NETWORK.errorMessageId;
        }
        if (errorCode == PhoneLoginController.ErrorCode.ERROR_SERVER) {
            return ERROR_SERVER.errorMessageId;
        }
        if (errorCode == PhoneLoginController.ErrorCode.ERROR_INVALID_PARAM) {
            return ERROR_INVALID_PHONE_NUM.errorMessageId;
        }
        if (errorCode == PhoneLoginController.ErrorCode.ERROR_NO_PHONE) {
            return ERROR_NO_PHONE.errorMessageId;
        }
        return ERROR_UNKNOWN.errorMessageId;
    }

    public static int getMsgIdGivenException(Throwable th) {
        ErrorInfo errorInfo;
        if (th instanceof InvalidResponseException) {
            errorInfo = ERROR_SERVER;
        } else if (th instanceof IOException) {
            errorInfo = ERROR_NETWORK;
        } else if (th instanceof AuthenticationFailureException) {
            errorInfo = ERROR_AUTH_FAIL;
        } else if (th instanceof AccessDeniedException) {
            errorInfo = ERROR_ACCESS_DENIED;
        } else if (th instanceof UserRestrictedException) {
            errorInfo = ERROR_PHONE_REG_RESTRICTED;
        } else if (th instanceof InvalidVerifyCodeException) {
            errorInfo = ERROR_VERIFY_CODE;
        } else if (th instanceof InvalidPhoneNumException) {
            errorInfo = ERROR_INVALID_PHONE_NUM;
        } else if (th instanceof ReachLimitException) {
            errorInfo = ERROR_SEND_PHONE_VCODE_REACH_LIMIT;
        } else if (th instanceof TokenExpiredException) {
            errorInfo = ERROR_TOKEN_EXPIRED;
        } else {
            errorInfo = ERROR_UNKNOWN;
        }
        return errorInfo.errorMessageId;
    }
}
