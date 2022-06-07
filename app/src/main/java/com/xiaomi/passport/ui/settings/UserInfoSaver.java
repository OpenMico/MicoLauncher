package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.content.Context;
import android.text.TextUtils;
import androidx.annotation.WorkerThread;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.SimpleRequest;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.ui.internal.Constants;
import com.xiaomi.passport.ui.internal.util.BitmapUtils;
import com.xiaomi.passport.ui.settings.utils.UserDataProxy;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public class UserInfoSaver {
    private static final String TAG = "UserInfoSaver";

    /* JADX INFO: Access modifiers changed from: package-private */
    @WorkerThread
    public static void saveXiaomiUserCoreInfo(Context context, Account account, XiaomiUserCoreInfo xiaomiUserCoreInfo) {
        if (account == null) {
            AccountLog.w(TAG, "no Xiaomi account, skip to save user info");
            return;
        }
        UserDataProxy userDataProxy = new UserDataProxy(context);
        userDataProxy.setUserData(account, Constants.ACCOUNT_USER_NAME, xiaomiUserCoreInfo.userName);
        userDataProxy.setUserData(account, Constants.ACCOUNT_NICK_NAME, xiaomiUserCoreInfo.nickName);
        userDataProxy.setUserData(account, Constants.ACCOUNT_USER_EMAIL, xiaomiUserCoreInfo.emailAddress);
        userDataProxy.setUserData(account, Constants.ACCOUNT_USER_PHONE, xiaomiUserCoreInfo.safePhone);
        if (xiaomiUserCoreInfo.gender != null) {
            userDataProxy.setUserData(account, Constants.ACCOUNT_USER_GENDER, xiaomiUserCoreInfo.gender.getType());
        }
        String str = "xiaomi_user_avatar_" + account.name;
        if (saveUserAvatarAsFile(context, xiaomiUserCoreInfo, str, userDataProxy.getUserData(account, Constants.ACCOUNT_AVATAR_URL))) {
            userDataProxy.setUserData(account, Constants.ACCOUNT_AVATAR_URL, xiaomiUserCoreInfo.avatarAddress);
            userDataProxy.setUserData(account, Constants.ACCOUNT_AVATAR_FILE_NAME, str);
        }
    }

    @WorkerThread
    public static boolean saveUserAvatarAsFile(Context context, XiaomiUserCoreInfo xiaomiUserCoreInfo, String str, String str2) {
        String str3 = xiaomiUserCoreInfo.avatarAddress;
        File fileStreamPath = context.getFileStreamPath(str);
        if (str3 == null) {
            return false;
        }
        if (TextUtils.equals(str3, str2) && fileStreamPath.isFile() && fileStreamPath.exists()) {
            return false;
        }
        SimpleRequest.StreamContent streamContent = null;
        try {
            streamContent = SimpleRequest.getAsStream(str3, null, null);
        } catch (AccessDeniedException e) {
            AccountLog.e(TAG, "access denied when download avatar", e);
        } catch (AuthenticationFailureException e2) {
            AccountLog.e(TAG, "auth failed when download avatar", e2);
        } catch (IOException e3) {
            AccountLog.e(TAG, "IO error when download avatar", e3);
        }
        if (streamContent == null) {
            return false;
        }
        try {
            try {
                if (BitmapUtils.saveAsFile(context, streamContent.getStream(), str) != null) {
                    return true;
                }
            } catch (IOException e4) {
                AccountLog.e(TAG, "failed to save avatar", e4);
            }
            return false;
        } finally {
            streamContent.closeStream();
        }
    }
}
