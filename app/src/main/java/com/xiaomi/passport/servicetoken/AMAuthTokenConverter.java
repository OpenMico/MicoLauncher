package com.xiaomi.passport.servicetoken;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.passport.servicetoken.ServiceTokenResult;
import java.io.IOException;

/* loaded from: classes4.dex */
public final class AMAuthTokenConverter {
    private static final String AM_AUTH_TOKEN_SPLIT = ",";
    private static final String AM_BUNDLE_KEY_AUTH_TOKEN = "authtoken";
    private static final String AM_BUNDLE_KEY_ERROR_CODE = "errorCode";
    private static final String AM_BUNDLE_KEY_ERROR_MESSAGE = "errorMessage";
    private static final String AM_BUNDLE_KEY_INTENT = "intent";
    public static final String ERROR_MSG_PREFIX = "error#";

    public static ServiceTokenResult fromAMException(String str, Exception exc) {
        ServiceTokenResult.ErrorCode errorCode;
        if (exc instanceof OperationCanceledException) {
            errorCode = ServiceTokenResult.ErrorCode.ERROR_CANCELLED;
        } else if (exc instanceof IOException) {
            errorCode = ServiceTokenResult.ErrorCode.ERROR_IOERROR;
        } else if (exc instanceof AuthenticatorException) {
            errorCode = ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR;
        } else if (exc instanceof SecurityException) {
            errorCode = ServiceTokenResult.ErrorCode.ERROR_OLD_MIUI_ACCOUNT_MANAGER_PERMISSION_ISSUE;
        } else {
            errorCode = ServiceTokenResult.ErrorCode.ERROR_UNKNOWN;
        }
        ServiceTokenResult.Builder errorCode2 = new ServiceTokenResult.Builder(str).errorCode(errorCode);
        StringBuilder sb = new StringBuilder();
        sb.append(ERROR_MSG_PREFIX);
        sb.append(exc != null ? exc.getMessage() : "");
        return errorCode2.errorMessage(sb.toString()).errorStackTrace(Log.getStackTraceString(exc)).build();
    }

    public static Exception toAMException(ServiceTokenResult serviceTokenResult) {
        if (serviceTokenResult == null || serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_NONE || serviceTokenResult.errorMessage == null || !serviceTokenResult.errorMessage.startsWith(ERROR_MSG_PREFIX)) {
            return null;
        }
        String substring = serviceTokenResult.errorMessage.substring(6);
        if (serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_CANCELLED) {
            return new OperationCanceledException(substring);
        }
        if (serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_IOERROR) {
            return new IOException(substring);
        }
        if (serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR) {
            return new AuthenticatorException(substring);
        }
        if (serviceTokenResult.errorCode == ServiceTokenResult.ErrorCode.ERROR_OLD_MIUI_ACCOUNT_MANAGER_PERMISSION_ISSUE) {
            return new SecurityException(substring);
        }
        return new AuthenticatorException("errorcode:" + serviceTokenResult.errorCode + ";errorMsg");
    }

    public static ServiceTokenResult fromAMBundle(Bundle bundle, String str) {
        ServiceTokenResult.ErrorCode errorCode;
        if (bundle == null) {
            return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_UNKNOWN).build();
        }
        if (bundle.containsKey("authtoken")) {
            ServiceTokenResult parseAMAuthToken = parseAMAuthToken(str, bundle.getString("authtoken"), false);
            return parseAMAuthToken != null ? parseAMAuthToken : new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR).errorMessage("invalid auth token").build();
        }
        Intent intent = (Intent) bundle.getParcelable("intent");
        if (intent != null) {
            return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_USER_INTERACTION_NEEDED).intent(intent).build();
        }
        if (!bundle.containsKey("errorCode")) {
            return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_UNKNOWN).build();
        }
        int i = bundle.getInt("errorCode");
        String string = bundle.getString("errorMessage");
        if (i != 1) {
            switch (i) {
                case 3:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_IOERROR;
                    break;
                case 4:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_CANCELLED;
                    break;
                case 5:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                    break;
                case 6:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                    break;
                case 7:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                    break;
                case 8:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                    break;
                case 9:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_AUTHENTICATOR_ERROR;
                    break;
                default:
                    errorCode = ServiceTokenResult.ErrorCode.ERROR_UNKNOWN;
                    break;
            }
        } else {
            errorCode = ServiceTokenResult.ErrorCode.ERROR_REMOTE_EXCEPTION;
        }
        ServiceTokenResult.Builder errorCode2 = new ServiceTokenResult.Builder(str).errorCode(errorCode);
        return errorCode2.errorMessage(i + "#" + string).build();
    }

    public static Bundle toAMBundle(ServiceTokenResult serviceTokenResult) throws ConvertException {
        String str = serviceTokenResult.errorMessage;
        ServiceTokenResult.ErrorCode errorCode = serviceTokenResult.errorCode;
        if (errorCode == ServiceTokenResult.ErrorCode.ERROR_OLD_MIUI_ACCOUNT_MANAGER_PERMISSION_ISSUE) {
            throw new SecurityException(str + serviceTokenResult.errorStackTrace);
        } else if (errorCode == ServiceTokenResult.ErrorCode.ERROR_NONE) {
            Bundle bundle = new Bundle();
            bundle.putString("authtoken", buildAMAuthToken(serviceTokenResult));
            return bundle;
        } else if (errorCode == ServiceTokenResult.ErrorCode.ERROR_USER_INTERACTION_NEEDED) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("intent", serviceTokenResult.intent);
            return bundle2;
        } else {
            if (str != null && str.matches("\\d#.*")) {
                try {
                    int indexOf = str.indexOf("#");
                    int intValue = Integer.valueOf(str.substring(0, indexOf)).intValue();
                    String substring = str.substring(indexOf + 1);
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("errorCode", intValue);
                    bundle3.putString("errorMessage", substring);
                    return bundle3;
                } catch (NumberFormatException unused) {
                }
            }
            throw new ConvertException(errorCode, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static String buildAMAuthToken(ServiceTokenResult serviceTokenResult) {
        if (serviceTokenResult == null) {
            return null;
        }
        return serviceTokenResult.security == null ? serviceTokenResult.serviceToken : String.format("%s%s%s", serviceTokenResult.serviceToken, ",", serviceTokenResult.security);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static ServiceTokenResult parseAMAuthToken(String str, String str2, boolean z) {
        String str3;
        String str4 = null;
        if (TextUtils.isEmpty(str2)) {
            return null;
        }
        String[] split = str2.split(",");
        if (str != null && str.startsWith("weblogin:")) {
            str3 = split[0];
            if (TextUtils.isEmpty(str3)) {
                return null;
            }
        } else if (split.length != 2 || TextUtils.isEmpty(split[0]) || TextUtils.isEmpty(split[1])) {
            return null;
        } else {
            String str5 = split[0];
            str4 = split[1];
            str3 = str5;
        }
        return new ServiceTokenResult.Builder(str).errorCode(ServiceTokenResult.ErrorCode.ERROR_NONE).serviceToken(str3).security(str4).peeked(z).build();
    }

    /* loaded from: classes4.dex */
    public static class ConvertException extends Exception {
        public final ServiceTokenResult.ErrorCode errorCode;
        public final String errorMsg;

        private ConvertException(ServiceTokenResult.ErrorCode errorCode, String str) {
            this.errorCode = errorCode;
            this.errorMsg = str;
        }
    }
}
