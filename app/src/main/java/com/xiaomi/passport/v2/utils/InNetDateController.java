package com.xiaomi.passport.v2.utils;

import android.content.Context;
import android.os.SystemClock;
import android.text.TextUtils;
import com.xiaomi.account.openauth.AuthorizeActivityBase;
import com.xiaomi.accountsdk.account.URLs;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.ActivatorPhoneInfo;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.accountsdk.account.stat.AccountStatInterface;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.request.SimpleRequestForAccount;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.accountsdk.utils.EasyMap;
import com.xiaomi.phonenum.PhoneNumKeeper;
import com.xiaomi.phonenum.PhoneNumKeeperFactory;
import com.xiaomi.phonenum.innetdate.InNetDateResult;
import java.io.IOException;
import java.util.HashMap;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class InNetDateController {
    private static final String APP_ID = "2882303761517565051";
    private static String RECYCLE_PHONE_CHECK_URL = URLs.URL_ACCOUNT_BASE + "/recyclePhoneCheck";
    private static final String STAT_CATEGORY_IN_NET_DATE = "in_net_date";
    private static final String TAG = "InNetDateController";

    public static RegisterUserInfo updatePhoneUserStatus(Context context, RegisterUserInfo registerUserInfo, PhoneParams phoneParams, boolean z) {
        if (z) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long inNetDate = getInNetDate(context, registerUserInfo);
            HashMap hashMap = new HashMap();
            hashMap.put("time", "" + ((SystemClock.elapsedRealtime() - elapsedRealtime) / 1000));
            AccountStatInterface.getInstance().statCountEvent(STAT_CATEGORY_IN_NET_DATE, "time", hashMap);
            if (inNetDate > 0) {
                return compareInNetDateAndUpdateRegisterStatus(registerUserInfo, inNetDate);
            }
        }
        return RegisterUserInfo.copyFrom(registerUserInfo).status(queryStatusFromServer(registerUserInfo, phoneParams)).build();
    }

    private static long getInNetDate(Context context, RegisterUserInfo registerUserInfo) {
        PhoneNumKeeper createPhoneNumKeeper = new PhoneNumKeeperFactory().createPhoneNumKeeper(context, APP_ID);
        for (int i = 0; i < createPhoneNumKeeper.getSlotCount(); i++) {
            try {
                InNetDateResult inNetDate = createPhoneNumKeeper.getInNetDate(context, i);
                if (inNetDate.isFailure()) {
                    AccountLog.i(TAG, "getInNetDate failed for " + i + StringUtils.SPACE + inNetDate.getMassage());
                } else {
                    AccountLog.i(TAG, "getInNetDate success for " + i);
                    if (TextUtils.isDigitsOnly(registerUserInfo.phone) && registerUserInfo.phone.equals(inNetDate.getPhoneNum())) {
                        AccountLog.i(TAG, "getInNetDate phone match for " + i);
                        return inNetDate.getInNetDate();
                    } else if (maskPhone(registerUserInfo.phone).equals(maskPhone(inNetDate.getPhoneNum()))) {
                        AccountLog.i(TAG, "getInNetDate phone match for " + i);
                        return inNetDate.getInNetDate();
                    }
                }
            } catch (IOException e) {
                AccountLog.e(TAG, "getInNetDate", e);
            }
        }
        return -1L;
    }

    private static RegisterUserInfo compareInNetDateAndUpdateRegisterStatus(RegisterUserInfo registerUserInfo, long j) {
        int i;
        if (j > registerUserInfo.bindTime) {
            i = RegisterUserInfo.RegisterStatus.STATUS_NOT_REGISTERED.value;
        } else {
            i = RegisterUserInfo.RegisterStatus.STATUS_REGISTERED_NOT_RECYCLED.value;
        }
        return RegisterUserInfo.copyFrom(registerUserInfo).status(i).build();
    }

    private static String maskPhone(String str) {
        if (str == null || str.length() != 11) {
            return str;
        }
        return str.substring(0, 3) + "****" + str.substring(7, 11);
    }

    private static int queryStatusFromServer(RegisterUserInfo registerUserInfo, PhoneParams phoneParams) {
        EasyMap easyPut = new EasyMap().easyPut("_json", "true");
        EasyMap easyPutOpt = new EasyMap().easyPutOpt("ticketToken", registerUserInfo.ticketToken);
        if (phoneParams != null) {
            easyPut.easyPutOpt("user", phoneParams.phone).easyPutOpt("ticket", phoneParams.ticket);
            ActivatorPhoneInfo activatorPhoneInfo = phoneParams.activatorPhoneInfo;
            if (activatorPhoneInfo != null) {
                easyPut.easyPutOpt("userHash", activatorPhoneInfo.phoneHash);
                easyPutOpt.easyPutOpt(AuthorizeActivityBase.KEY_ACTIVATORTOKEN, activatorPhoneInfo.activatorToken);
            }
        }
        try {
            SimpleRequest.StringContent postAsString = SimpleRequestForAccount.postAsString(RECYCLE_PHONE_CHECK_URL, easyPut, easyPutOpt, true);
            if (postAsString != null) {
                JSONObject jSONObject = new JSONObject(XMPassport.removeSafePrefixAndGetRealBody(postAsString));
                int i = jSONObject.getInt("code");
                String str = "code: " + i + ", desc: " + jSONObject.optString("description");
                if (i == 0) {
                    return jSONObject.getJSONObject("data").getInt("status");
                }
                throw new InvalidResponseException(str);
            }
            throw new InvalidResponseException("result content is null");
        } catch (AccessDeniedException e) {
            AccountLog.e(TAG, "queryStatusFromServer", e);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (AuthenticationFailureException e2) {
            AccountLog.e(TAG, "queryStatusFromServer", e2);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (InvalidResponseException e3) {
            AccountLog.e(TAG, "queryStatusFromServer", e3);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (IOException e4) {
            AccountLog.e(TAG, "queryStatusFromServer", e4);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        } catch (JSONException e5) {
            AccountLog.e(TAG, "queryStatusFromServer", e5);
            return RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED.value;
        }
    }

    /* loaded from: classes4.dex */
    public static class PhoneParams {
        public final ActivatorPhoneInfo activatorPhoneInfo;
        public final String phone;
        public final String ticket;

        public PhoneParams(String str, String str2, ActivatorPhoneInfo activatorPhoneInfo) {
            this.phone = str;
            this.ticket = str2;
            this.activatorPhoneInfo = activatorPhoneInfo;
        }

        public PhoneParams newInputPhoneInstance(String str, String str2) {
            return new PhoneParams(str, str2, null);
        }

        public PhoneParams newActivatorPhoneInstance(ActivatorPhoneInfo activatorPhoneInfo) {
            return new PhoneParams(null, null, activatorPhoneInfo);
        }
    }
}
