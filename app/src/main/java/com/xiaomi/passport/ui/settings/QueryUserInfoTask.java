package com.xiaomi.passport.ui.settings;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.Nullable;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.accountmanager.MiAccountManager;
import com.xiaomi.passport.data.XMPassportInfo;
import com.xiaomi.passport.ui.internal.util.SysHelper;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public class QueryUserInfoTask extends AsyncTask<Void, Void, XiaomiUserCoreInfo> {
    private static final String TAG = "QueryUserInfoTask";
    private final Account mAccount;
    private final QueryUserInfoCallback mCallback;
    @SuppressLint({"StaticFieldLeak"})
    private final Context mContext;

    /* loaded from: classes4.dex */
    public interface QueryUserInfoCallback {
        void onResult(XiaomiUserCoreInfo xiaomiUserCoreInfo);
    }

    public QueryUserInfoTask(Context context, QueryUserInfoCallback queryUserInfoCallback) {
        this.mContext = context;
        this.mCallback = queryUserInfoCallback;
        this.mAccount = MiAccountManager.get(this.mContext).getXiaomiAccount();
    }

    public XiaomiUserCoreInfo doInBackground(Void[] voidArr) {
        XiaomiUserCoreInfo handleQueryUserInfo = handleQueryUserInfo();
        if (handleQueryUserInfo != null) {
            UserInfoSaver.saveXiaomiUserCoreInfo(this.mContext, this.mAccount, handleQueryUserInfo);
        }
        return handleQueryUserInfo;
    }

    public void onPostExecute(XiaomiUserCoreInfo xiaomiUserCoreInfo) {
        QueryUserInfoCallback queryUserInfoCallback = this.mCallback;
        if (queryUserInfoCallback != null) {
            queryUserInfoCallback.onResult(xiaomiUserCoreInfo);
        }
    }

    private XiaomiUserCoreInfo handleQueryUserInfo() {
        return getUserCoreInfo(this.mContext);
    }

    @Nullable
    public static XiaomiUserCoreInfo getUserCoreInfo(Context context) {
        if (MiAccountManager.get(context).getXiaomiAccount() == null) {
            AccountLog.w(TAG, "no Xiaomi account, skip to query user info");
            return null;
        }
        XMPassportInfo build = XMPassportInfo.build(context, "passportapi");
        ArrayList arrayList = new ArrayList();
        arrayList.add(XiaomiUserCoreInfo.Flag.BASE_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.BIND_ADDRESS);
        arrayList.add(XiaomiUserCoreInfo.Flag.EXTRA_INFO);
        arrayList.add(XiaomiUserCoreInfo.Flag.SETTING_INFO);
        return SysHelper.queryXiaomiUserCoreInfo(context, build, arrayList);
    }
}
