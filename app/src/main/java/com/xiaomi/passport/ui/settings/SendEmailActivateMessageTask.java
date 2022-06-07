package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.os.AsyncTask;
import com.xiaomi.accountsdk.account.exception.InvalidBindAddressException;
import com.xiaomi.accountsdk.account.exception.NeedCaptchaException;
import com.xiaomi.accountsdk.account.exception.ReachLimitException;
import com.xiaomi.accountsdk.account.exception.UsedEmailAddressException;
import com.xiaomi.accountsdk.hasheddeviceidlib.HashedDeviceIdUtil;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.data.XMPassportInfo;
import java.io.IOException;

/* loaded from: classes4.dex */
public class SendEmailActivateMessageTask extends AsyncTask<Void, Void, EmailTaskResult> {
    private static final String TAG = "SendEmailActivateMessag";
    private String mAuthST;
    private Context mContext;
    private String mEmailAddress;
    private String sCaptCode;
    private String sCaptIck;

    public SendEmailActivateMessageTask(Context context, String str, String str2, String str3, String str4) {
        this.mContext = context.getApplicationContext();
        this.mEmailAddress = str;
        this.mAuthST = str2;
        this.sCaptCode = str3;
        this.sCaptIck = str4;
    }

    public EmailTaskResult doInBackground(Void... voidArr) {
        XMPassportInfo build = XMPassportInfo.build(this.mContext, "passportapi");
        String str = null;
        int i = 5;
        if (build == null) {
            AccountLog.w(TAG, "null passportInfo");
            return new EmailTaskResult(5, null);
        }
        String hashedDeviceIdNoThrow = new HashedDeviceIdUtil(this.mContext).getHashedDeviceIdNoThrow();
        i = 0;
        int i2 = 0;
        while (i2 < 2) {
            try {
                MyXMPassport.sendEmailActivateMessage(build, this.mEmailAddress, this.mAuthST, hashedDeviceIdNoThrow, this.sCaptCode, this.sCaptIck);
                break;
            } catch (InvalidBindAddressException e) {
                AccountLog.e(TAG, "InvalidBindAddressException", e);
                i = 9;
            } catch (NeedCaptchaException e2) {
                AccountLog.e(TAG, "NeedCaptchaException", e2);
                str = e2.getCaptchaUrl();
                i = 12;
            } catch (ReachLimitException e3) {
                AccountLog.e(TAG, "ReachLimitException", e3);
                i = 13;
            } catch (UsedEmailAddressException e4) {
                AccountLog.e(TAG, "UsedEmailAddressException", e4);
                i = 8;
            } catch (AccessDeniedException e5) {
                AccountLog.e(TAG, "AccessDeniedException", e5);
                i = 4;
            } catch (AuthenticationFailureException e6) {
                AccountLog.e(TAG, "AuthenticationFailureException", e6);
                build.refreshAuthToken(this.mContext);
                i2++;
                i = 1;
            } catch (CipherException e7) {
                AccountLog.e(TAG, "CipherException", e7);
                i = 3;
            } catch (InvalidResponseException e8) {
                AccountLog.e(TAG, "InvalidResponseException", e8);
                i = 3;
            } catch (IOException e9) {
                AccountLog.e(TAG, "IOException", e9);
                i = 2;
            }
        }
        return new EmailTaskResult(i, str);
    }

    /* loaded from: classes4.dex */
    public class EmailTaskResult {
        String captchaPath;
        int exceptionType;

        EmailTaskResult(int i, String str) {
            SendEmailActivateMessageTask.this = r1;
            this.exceptionType = i;
            this.captchaPath = str;
        }
    }
}
