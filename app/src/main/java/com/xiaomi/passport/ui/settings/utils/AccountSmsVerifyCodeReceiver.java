package com.xiaomi.passport.ui.settings.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.xiaomi.accountsdk.utils.AccountLog;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes4.dex */
public class AccountSmsVerifyCodeReceiver extends BroadcastReceiver {
    static final String PATTERN_VERIFY_CODE_SMS = "^([\\D]*|.*[\\D])(\\d{4}|\\d{6}|\\d{8})([\\D].*|[\\D]*)$";
    public static final int SMS_PERMISSION_REQUEST_CODE = 51;
    private static final String TAG = "SmsVerifyCodeReceiver";
    private SmsVerifyCodeMessageListener mSmsVerifyCodeMessageListener;

    /* loaded from: classes4.dex */
    public interface SmsVerifyCodeMessageListener {
        void onReceived(String str, String str2);
    }

    public AccountSmsVerifyCodeReceiver(SmsVerifyCodeMessageListener smsVerifyCodeMessageListener) {
        this.mSmsVerifyCodeMessageListener = smsVerifyCodeMessageListener;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        List<String> smsMessages = getSmsMessages(intent);
        if (smsMessages == null || smsMessages.size() == 0) {
            AccountLog.i(TAG, "null message");
            return;
        }
        Pattern compile = Pattern.compile(PATTERN_VERIFY_CODE_SMS);
        for (String str : smsMessages) {
            Matcher matcher = compile.matcher(str);
            if (matcher.matches()) {
                this.mSmsVerifyCodeMessageListener.onReceived(str, matcher.group(2));
            }
        }
    }

    List<String> getSmsMessages(Intent intent) {
        if (intent == null || intent.getExtras() == null) {
            AccountLog.i(TAG, "bundle is null");
            return null;
        }
        Object[] objArr = (Object[]) intent.getExtras().get("pdus");
        ArrayList arrayList = new ArrayList();
        if (objArr != null) {
            for (Object obj : objArr) {
                String messageBody = SmsMessage.createFromPdu((byte[]) obj).getMessageBody();
                if (!TextUtils.isEmpty(messageBody)) {
                    arrayList.add(messageBody);
                }
            }
        } else {
            AccountLog.w(TAG, "no pdus in bundle data");
        }
        return arrayList;
    }

    public static boolean hasSmsReceivePermission(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 4096);
            if (packageInfo.requestedPermissions != null) {
                for (String str : packageInfo.requestedPermissions) {
                    if ("android.permission.RECEIVE_SMS".equals(str)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean tryRequestSmsPermission(Activity activity) {
        if (!hasSmsReceivePermission(activity)) {
            AccountLog.d(TAG, "no android.permission.RECEIVE_SMS permission in AndroidManifest.xml, ignore");
            return false;
        } else if (ContextCompat.checkSelfPermission(activity, "android.permission.RECEIVE_SMS") == 0) {
            return false;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.RECEIVE_SMS")) {
                AccountLog.d(TAG, "ignore showing prompt when sms permission already denied");
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.RECEIVE_SMS"}, 51);
            return true;
        }
    }
}
