package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.content.Intent;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.xiaomi.passport.ui.internal.PassportUI;
import com.xiaomi.passport.ui.internal.util.SysHelper;

/* loaded from: classes4.dex */
public class UserInfoManager {
    private static final String TAG = "UserInfoManager";
    private Context mContext;

    public UserInfoManager(Context context) {
        this.mContext = context;
    }

    public void modifyUserPhoneInfo() {
        Intent newViewUserInfoTransparentIntent = SysHelper.newViewUserInfoTransparentIntent(this.mContext, UserInfoType.Modify_User_Phone.getTypeName());
        newViewUserInfoTransparentIntent.addFlags(268435456);
        this.mContext.startActivity(newViewUserInfoTransparentIntent);
    }

    public void modifyUserPhoneInfoWithUiTip() {
        Intent newViewPhoneInfoIntent = SysHelper.newViewPhoneInfoIntent(this.mContext, TAG);
        newViewPhoneInfoIntent.addFlags(268435456);
        this.mContext.startActivity(newViewPhoneInfoIntent);
    }

    /* loaded from: classes4.dex */
    public enum UserInfoType {
        Modify_User_Phone("Modify_User_Phone"),
        Modify_User_Email("Modify_User_Email"),
        Modify_User_Password("Modify_User_Password");
        
        private String typeName;

        UserInfoType(String str) {
            this.typeName = str;
        }

        public String getTypeName() {
            return this.typeName;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void sendModifyUserPhoneResultByLocalBroadcast(Context context, boolean z, int i) {
        Intent intent = new Intent(PassportUI.ACTION_LOCAL_ACCOUNT_CHANGE_PHONE);
        intent.putExtra("result", z);
        intent.putExtra("result_code", i);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void sendModifyUserEmailResultByLocalBroadcast(Context context, boolean z, int i) {
        Intent intent = new Intent(PassportUI.ACTION_LOCAL_ACCOUNT_CHANGE_EMAIL);
        intent.putExtra("result", z);
        intent.putExtra("result_code", i);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void sendChangePasswordResultByLocalBroadcast(Context context, boolean z, int i) {
        Intent intent = new Intent(PassportUI.ACTION_LOCAL_ACCOUNT_CHANGE_PASSWORD);
        intent.putExtra("result", z);
        intent.putExtra("result_code", i);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }
}
