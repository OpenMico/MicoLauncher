package com.xiaomi.micolauncher.module.setting;

import android.content.Context;
import android.content.Intent;
import com.xiaomi.mico.account.sdk.LoginManager;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.mico.settingslib.core.BigSettings;
import com.xiaomi.mico.settingslib.core.Constants;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.mico.settingslib.lock.LockSetting;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.ThreadUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.setting.widget.LockAppDialog;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class SettingOpenManager {
    public static final String SETTINGS_BIG_CATEGORY = "com.mico.settings.big.BIGSETTINGDCREEN";
    public static final String SETTINGS_BIG_CATEORY_RESETCONFIRM = "com.mico.settings.big.RESETCONFIRM";
    public static final String SETTINGS_BIG_SCREEN_ACTION = "com.mico.settings.big";
    public static final String SETTINGS_RESET_ACTION = "com.xiaomi.mico.settings.reset";
    public static final String SETTINGS_SCREEN_ACTION = "com.mico.settings";
    public static final String SETTINGS_SCREEN_CATEGORY = "com.mico.settings.SETTTINGS";
    public static final String SETTINGS_SCREEN_CATEGORY_RESETCONFIRM = "com.mico.settings.RESETCONFIRM";
    public static final String SETTINGS_SCREEN_CATEGORY_WIFI = "com.mico.settings.WIFI";
    public static final String SP_PASS_KEY = "security_pass_key";

    public static void openChildProtection(Context context) {
        startSettingsActivity(context, SETTINGS_BIG_SCREEN_ACTION, SETTINGS_BIG_CATEGORY, BigSettings.CHILD_PROTECTION);
    }

    public static void openWifi(Context context) {
        if (Hardware.isBigScreen()) {
            startSettingsActivity(context, SETTINGS_BIG_SCREEN_ACTION, SETTINGS_BIG_CATEGORY, BigSettings.WIRELESS);
        } else {
            a(context, SETTINGS_RESET_ACTION, SETTINGS_SCREEN_CATEGORY_WIFI);
        }
    }

    public static void openIntercomSetting(Context context) {
        if (Hardware.isBigScreen()) {
            startSettingsActivity(context, SETTINGS_BIG_SCREEN_ACTION, SETTINGS_BIG_CATEGORY, BigSettings.INTERCOM);
        } else {
            a(context, SETTINGS_RESET_ACTION, SETTINGS_SCREEN_CATEGORY_WIFI);
        }
    }

    public static void openSetting(Context context) {
        if (ChildModeManager.getManager().isChildMode()) {
            ToastUtil.showToast((int) R.string.child_look_other);
        } else if (Hardware.isBigScreen()) {
            a(context, SETTINGS_BIG_SCREEN_ACTION, SETTINGS_BIG_CATEGORY);
        } else {
            a(context, SETTINGS_SCREEN_ACTION, SETTINGS_SCREEN_CATEGORY);
        }
    }

    public static void openSetting(Context context, BigSettings bigSettings) {
        if (ChildModeManager.getManager().isChildMode()) {
            ToastUtil.showToast((int) R.string.child_look_other);
        } else if (Hardware.isBigScreen()) {
            startSettingsActivity(context, SETTINGS_BIG_SCREEN_ACTION, SETTINGS_BIG_CATEGORY, bigSettings);
        } else {
            startSettingsActivity(context, SETTINGS_SCREEN_ACTION, SETTINGS_SCREEN_CATEGORY, bigSettings);
        }
    }

    public static void openResetting(Context context) {
        if (Hardware.isBigScreen()) {
            a(context, SETTINGS_BIG_SCREEN_ACTION, SETTINGS_BIG_CATEORY_RESETCONFIRM);
        } else {
            a(context, SETTINGS_RESET_ACTION, SETTINGS_SCREEN_CATEGORY_RESETCONFIRM);
        }
    }

    private static void a(Context context, String str, String str2) {
        startSettingsActivity(context, str, str2, null);
    }

    public static void startSettingsActivity(final Context context, final String str, final String str2, final BigSettings bigSettings) {
        if (!Hardware.isBigScreen() || !LockSetting.getHasLock(context, LockSetting.PACKAGE_SETTING)) {
            a(context, str, str2, bigSettings);
        } else {
            ThreadUtil.postInMainThread(new Runnable() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$SettingOpenManager$4IjXDWrPjGYcpkK3goPaRukhMYQ
                @Override // java.lang.Runnable
                public final void run() {
                    SettingOpenManager.b(context, str, str2, bigSettings);
                }
            });
        }
    }

    public static /* synthetic */ void b(final Context context, final String str, final String str2, final BigSettings bigSettings) {
        new LockAppDialog(context, new LockAppDialog.OnLockAppPassCorrectListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$SettingOpenManager$FE1VWv5WwobtRRMKiTVYvvK9r0U
            @Override // com.xiaomi.micolauncher.module.setting.widget.LockAppDialog.OnLockAppPassCorrectListener
            public final void onPassCorrect() {
                SettingOpenManager.a(context, str, str2, bigSettings);
            }
        }).show();
    }

    public static void a(Context context, String str, String str2, BigSettings bigSettings) {
        Intent intent = new Intent();
        intent.setAction(str);
        intent.addCategory(str2);
        intent.putExtra(Constants.EXTRA_LAUNCHED_BY_LAUNCHER, true);
        intent.putExtra(Constants.EXTRA_SN, com.xiaomi.micolauncher.application.Constants.getSn());
        intent.putExtra(Constants.EXTRA_MODEL, Hardware.current(context).getName());
        intent.putExtra(Constants.EXTRA_DEVICE_ID, SystemSetting.getDeviceID());
        intent.putExtra(Constants.EXTRA_USER_ID, LoginManager.get().getUserId());
        intent.putExtra(Constants.EXTRA_CUSER_ID, LoginManager.get().getCUserId());
        intent.putExtra(Constants.EXTRA_ROM_CHANNEL, SystemSetting.getRomChannel());
        intent.putExtra(Constants.EXTRA_ROM_VERSION, SystemSetting.getRomVersion());
        intent.putExtra(Constants.EXTRA_MICO_SID_TOKEN, TokenManager.getInstance().getEncryptedToken("micoapi"));
        if (bigSettings != null) {
            intent.putExtra(MicoSettings.BIG_CURRENT_FRAGMENT, bigSettings);
        }
        context.startActivity(intent);
    }
}
