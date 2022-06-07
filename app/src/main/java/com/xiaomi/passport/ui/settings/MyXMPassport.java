package com.xiaomi.passport.ui.settings;

import android.text.TextUtils;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.ServerError;
import com.xiaomi.accountsdk.account.ServerErrorCode;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.data.BaseConstants;
import com.xiaomi.accountsdk.account.data.PassportInfo;
import com.xiaomi.accountsdk.account.exception.InvalidBindAddressException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.UsedEmailAddressException;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SecureRequestForAccount;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.accountsdk.utils.UserSpaceIdUtil;
import java.io.IOException;

/* loaded from: classes4.dex */
class MyXMPassport {
    private static final String URL_SEND_EMAIL_ACTIVATE_MESSAGE = URLs.URL_ACCOUNT_SAFE_API_BASE + "/user/sendEmailActivateMessage";

    MyXMPassport() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void sendEmailActivateMessage(PassportInfo passportInfo, String str, String str2, String str3, String str4, String str5) throws InvalidResponseException, CipherException, IOException, AuthenticationFailureException, AccessDeniedException, InvalidBindAddressException, NeedCaptchaException, UsedEmailAddressException, ReachLimitException {
        if (passportInfo != null) {
            EasyMap easyPut = new EasyMap().easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId()).easyPut("address", str).easyPut("sid", passportInfo.getServiceId()).easyPut(SimpleRequestForAccount.COOKIE_NAME_DEVICE_ID, str3).easyPutOpt(SimpleRequestForAccount.COOKIE_NAME_USER_SPACE_ID, UserSpaceIdUtil.getNullableUserSpaceIdCookie()).easyPut("authST", str2).easyPut("icode", str4);
            EasyMap<String, String> passportCookie = getPassportCookie(passportInfo);
            passportCookie.easyPut("ick", str5);
            SimpleRequest.MapContent postAsMap = SecureRequestForAccount.postAsMap(URL_SEND_EMAIL_ACTIVATE_MESSAGE, easyPut, passportCookie, true, passportInfo.getSecurity());
            if (postAsMap != null) {
                Integer num = (Integer) postAsMap.getFromBody("code");
                String str6 = (String) postAsMap.getFromBody("description");
                String str7 = "code: " + num + " ;description: " + str6;
                ServerError serverError = new ServerError(postAsMap);
                int intValue = num.intValue();
                if (intValue != 0) {
                    if (intValue != 20031) {
                        if (intValue != 70006) {
                            if (intValue != 70013) {
                                if (intValue != 87001) {
                                    switch (intValue) {
                                        case 70021:
                                            break;
                                        case ServerErrorCode.ERROR_REACH_LIMIT /* 70022 */:
                                            throw new ReachLimitException(str7);
                                        default:
                                            throw new InvalidResponseException(serverError);
                                    }
                                }
                            }
                            throw new UsedEmailAddressException(str7);
                        }
                        throw new InvalidBindAddressException(str7);
                    }
                    throw new NeedCaptchaException(num.intValue(), str6, (String) postAsMap.getFromBody("info"));
                }
                return;
            }
            throw new IOException("failed to checkAvailabilityOfBindingEmail");
        }
        throw new IllegalArgumentException("passportInfo is null");
    }

    private static EasyMap<String, String> getPassportCookie(PassportInfo passportInfo) {
        if (passportInfo != null) {
            EasyMap<String, String> easyPut = new EasyMap().easyPut(AuthorizeActivityBase.KEY_SERVICETOKEN, passportInfo.getServiceToken());
            if (TextUtils.isEmpty(passportInfo.getEncryptedUserId())) {
                easyPut.easyPut(BaseConstants.EXTRA_USER_ID, passportInfo.getUserId());
            } else {
                easyPut.easyPut("cUserId", passportInfo.getEncryptedUserId());
            }
            return easyPut;
        }
        throw new IllegalArgumentException("passportInfo is null");
    }
}
