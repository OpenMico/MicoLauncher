package com.xiaomi.passport.ui.api;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.passport.ui.settings.QueryUserInfoTask;
import com.xiaomi.passport.ui.settings.UserInfoSaver;

/* loaded from: classes4.dex */
public class LocalUserInfoHelper {
    @WorkerThread
    public static boolean saveUserAvatarAsFile(Context context, XiaomiUserCoreInfo xiaomiUserCoreInfo, String str, @Nullable String str2) {
        return UserInfoSaver.saveUserAvatarAsFile(context, xiaomiUserCoreInfo, str, str2);
    }

    @WorkerThread
    public static XiaomiUserCoreInfo queryUserCoreInfo(Context context) {
        return QueryUserInfoTask.getUserCoreInfo(context);
    }
}
