package com.xiaomi.passport.ui.internal.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import com.xiaomi.accountsdk.account.AccountIntent;
import com.xiaomi.accountsdk.account.XMPassport;
import com.xiaomi.accountsdk.account.data.XiaomiUserCoreInfo;
import com.xiaomi.accountsdk.request.AccessDeniedException;
import com.xiaomi.accountsdk.request.AuthenticationFailureException;
import com.xiaomi.accountsdk.request.CipherException;
import com.xiaomi.accountsdk.request.InvalidResponseException;
import com.xiaomi.accountsdk.utils.AccountLog;
import com.xiaomi.passport.data.XMPassportInfo;
import com.xiaomi.passport.ui.R;
import com.xiaomi.passport.ui.internal.PassportGroupEditText;
import com.xiaomi.passport.ui.settings.UserInfoTransparentActivity;
import com.xiaomi.passport.ui.settings.UserPhoneInfoActivity;
import java.io.IOException;
import java.util.List;

/* loaded from: classes4.dex */
public class SysHelper {
    private static final String TAG = "SysHelper";

    private static boolean atLeast2True(boolean z, boolean z2, boolean z3) {
        return (z && z2) || (z && z3) || (z2 && z3);
    }

    public static int getEditViewInputType(boolean z) {
        return (z ? 144 : 128) | 1;
    }

    public static XiaomiUserCoreInfo queryXiaomiUserCoreInfo(Context context, XMPassportInfo xMPassportInfo, List<XiaomiUserCoreInfo.Flag> list) {
        if (xMPassportInfo == null) {
            AccountLog.w(TAG, "passportInfo is null");
            return null;
        }
        for (int i = 0; i < 2; i++) {
            try {
                return XMPassport.getXiaomiUserCoreInfo(xMPassportInfo, "passportapi", list);
            } catch (AccessDeniedException e) {
                AccountLog.e(TAG, "access denied when get user info", e);
            } catch (AuthenticationFailureException e2) {
                AccountLog.e(TAG, "auth failure when get user info", e2);
                xMPassportInfo.refreshAuthToken(context);
            } catch (CipherException e3) {
                AccountLog.e(TAG, "CipherException when get user info", e3);
            } catch (InvalidResponseException e4) {
                AccountLog.e(TAG, "invalid response when get user info", e4);
            } catch (IOException e5) {
                AccountLog.e(TAG, "IOException when get user info", e5);
            }
        }
        return null;
    }

    public static Bitmap createPhoto(Activity activity, String str) {
        Bitmap userAvatarByAbsPath = str != null ? BitmapUtils.getUserAvatarByAbsPath(activity, str) : null;
        if (userAvatarByAbsPath == null) {
            userAvatarByAbsPath = BitmapFactory.decodeResource(activity.getResources(), R.drawable.passport_default_avatar);
        }
        if (userAvatarByAbsPath == null) {
            return null;
        }
        Bitmap createPhoto = BitmapFactory.createPhoto(activity, userAvatarByAbsPath);
        userAvatarByAbsPath.recycle();
        return createPhoto;
    }

    public static Intent newViewPhoneInfoIntent(Context context, String str) {
        Intent intent = new Intent(context, UserPhoneInfoActivity.class);
        intent.setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
        intent.putExtra(AccountIntent.STAT_KEY_SOURCE, str);
        return intent;
    }

    public static Intent newViewUserInfoTransparentIntent(Context context, String str) {
        Intent intent = new Intent(context, UserInfoTransparentActivity.class);
        intent.setPackage(AccountIntent.PACKAGE_XIAOMI_ACCOUNT);
        intent.putExtra(AccountIntent.STAT_KEY_SOURCE, str);
        return intent;
    }

    public static void displaySoftInputIfNeed(Context context, View view, boolean z) {
        if (view == null) {
            AccountLog.w(TAG, "no focused view");
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        if (!z || context.getResources().getConfiguration().orientation != 1) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } else {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public static void replaceToFragment(Activity activity, Fragment fragment, boolean z) {
        replaceToFragment(activity, fragment, z, 16908290);
    }

    public static void replaceToFragment(Activity activity, Fragment fragment, boolean z, int i) {
        if (activity != null && !activity.isFinishing()) {
            try {
                FragmentManager fragmentManager = activity.getFragmentManager();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                if (z) {
                    int backStackEntryCount = fragmentManager.getBackStackEntryCount();
                    for (int i2 = 0; i2 < backStackEntryCount; i2++) {
                        fragmentManager.popBackStack();
                    }
                }
                beginTransaction.setTransition(4099);
                beginTransaction.replace(i, fragment);
                if (!z) {
                    beginTransaction.addToBackStack(null);
                }
                beginTransaction.commitAllowingStateLoss();
            } catch (IllegalStateException e) {
                AccountLog.w(TAG, "fragment", e);
            }
        }
    }

    public static boolean checkPasswordPattern(String str) {
        int length;
        if (str == null || (length = str.length()) < 8 || length > 16) {
            return false;
        }
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < ' ' || charAt > '~') {
                return false;
            }
            if ((charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z')) {
                z = true;
            } else if (charAt < '0' || charAt > '9') {
                z3 = true;
            } else {
                z2 = true;
            }
        }
        return atLeast2True(z, z2, z3);
    }

    public static void updateShowPasswordState(PassportGroupEditText passportGroupEditText, ImageView imageView, boolean z, Resources resources) {
        if (passportGroupEditText != null && imageView != null) {
            passportGroupEditText.setInputType(getEditViewInputType(z));
            passportGroupEditText.setTypeface(Typeface.DEFAULT);
            passportGroupEditText.setSelection(passportGroupEditText.getText().length());
            int i = 0;
            if (Build.VERSION.SDK_INT >= 17) {
                int paddingStart = passportGroupEditText.getPaddingStart();
                int paddingTop = passportGroupEditText.getPaddingTop();
                if (resources != null) {
                    i = resources.getDimensionPixelSize(R.dimen.passport_password_alert_icon_padding_right);
                }
                passportGroupEditText.setPaddingRelative(paddingStart, paddingTop, i, passportGroupEditText.getPaddingBottom());
            } else {
                int paddingLeft = passportGroupEditText.getPaddingLeft();
                int paddingTop2 = passportGroupEditText.getPaddingTop();
                if (resources != null) {
                    i = resources.getDimensionPixelSize(R.dimen.passport_password_alert_icon_padding_right);
                }
                passportGroupEditText.setPadding(paddingLeft, paddingTop2, i, passportGroupEditText.getPaddingBottom());
            }
            imageView.setImageResource(z ? R.drawable.passport_password_show : R.drawable.passport_password_not_show);
        }
    }
}
