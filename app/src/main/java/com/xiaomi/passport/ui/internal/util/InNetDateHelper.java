package com.xiaomi.passport.ui.internal.util;

import android.app.FragmentManager;
import android.content.Context;
import com.xiaomi.accountsdk.account.data.QueryPhoneInfoParams;
import com.xiaomi.accountsdk.account.data.RegisterUserInfo;
import com.xiaomi.passport.v2.utils.InNetDateController;

/* loaded from: classes4.dex */
public class InNetDateHelper {
    private static final String STAT_CATEGORY_IN_NET_DATE = "in_net_date";
    private static String TAG = "AccountInNetDateHelper";
    private boolean agreeToGetInNetDate = true;
    private Object agreeToGetInNetDateLock = new Object();
    private Context context;
    private FragmentManager fm;

    private void showGetInNetDateDialog(RegisterUserInfo registerUserInfo) {
    }

    private InNetDateHelper(Context context, FragmentManager fragmentManager) {
        this.context = context;
        this.fm = fragmentManager;
    }

    public static RegisterUserInfo updateRegisterStatusIfNeed(Context context, FragmentManager fragmentManager, RegisterUserInfo registerUserInfo) {
        return new InNetDateHelper(context, fragmentManager).updateRegisterStatusIfNeed(registerUserInfo, null);
    }

    public static RegisterUserInfo updateRegisterStatusIfNeed(Context context, FragmentManager fragmentManager, RegisterUserInfo registerUserInfo, QueryPhoneInfoParams queryPhoneInfoParams) {
        return new InNetDateHelper(context, fragmentManager).updateRegisterStatusIfNeed(registerUserInfo, queryPhoneInfoParams);
    }

    private RegisterUserInfo updateRegisterStatusIfNeed(RegisterUserInfo registerUserInfo, QueryPhoneInfoParams queryPhoneInfoParams) {
        if (!(registerUserInfo.status == RegisterUserInfo.RegisterStatus.STATUS_USED_POSSIBLY_RECYCLED && registerUserInfo.needGetActiveTime)) {
            return registerUserInfo;
        }
        if (registerUserInfo.needToast) {
            showGetInNetDateDialog(registerUserInfo);
            try {
                synchronized (this.agreeToGetInNetDateLock) {
                    this.agreeToGetInNetDateLock.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return InNetDateController.updatePhoneUserStatus(this.context, registerUserInfo, new InNetDateController.PhoneParams(queryPhoneInfoParams.phone, queryPhoneInfoParams.ticket, queryPhoneInfoParams.activatorPhoneInfo), this.agreeToGetInNetDate);
    }
}
