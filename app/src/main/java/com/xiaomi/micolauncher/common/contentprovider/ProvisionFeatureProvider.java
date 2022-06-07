package com.xiaomi.micolauncher.common.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.xiaomi.mico.token.TokenManager;
import com.xiaomi.mico.utils.Threads;
import com.xiaomi.micolauncher.application.Constants;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.module.miot.MiotDeviceManager;
import com.xiaomi.micolauncher.module.miot.OtInvokers;
import com.xiaomi.miot.support.MicoSupConstants;
import com.xiaomi.miot.support.MiotManager;

/* loaded from: classes3.dex */
public class ProvisionFeatureProvider extends ContentProvider {
    private final String a = "method_is_initialized";
    private final String b = "method_cmcc_boot";
    private final String c = "method_miot_onLogon";
    private final String d = "method_start_miot_service";
    private final String e = "method_restart_miot_service";
    private final String f = "method_restart_miot_service_after_login";
    private final String g = "method_interrupt_handle_ot";
    private final String h = "result_is_initialized";
    private final String i = "bindKey";
    private final String j = "partnerId";
    private final String k = "partnerToken";
    private final String l = "interruptHandleOT";

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.ContentProvider
    public Bundle call(String str, String str2, Bundle bundle) {
        char c;
        Bundle bundle2 = new Bundle();
        Log.i("ProvisionFeatureProvider", "call method=" + str);
        switch (str.hashCode()) {
            case -1749249559:
                if (str.equals("method_cmcc_boot")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1300214172:
                if (str.equals("method_miot_onLogon")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1237545012:
                if (str.equals("method_restart_miot_service_after_login")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1085592123:
                if (str.equals("method_restart_miot_service")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -445073966:
                if (str.equals("method_start_miot_service")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -233152478:
                if (str.equals("method_interrupt_handle_ot")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 684606045:
                if (str.equals("method_is_initialized")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                boolean isInitializedNoCheckAccount = SystemSetting.isInitializedNoCheckAccount(getContext());
                Log.i("ProvisionFeatureProvider", "tell Process provision isInitialized=" + isInitializedNoCheckAccount);
                bundle2.putBoolean("result_is_initialized", isInitializedNoCheckAccount);
                if (isInitializedNoCheckAccount) {
                    SystemSetting.getDeviceID();
                    SystemSetting.getCmccUserKey();
                    SystemSetting.getCmccGwAddress();
                    SystemSetting.getCmccGwAddress2();
                    SystemSetting.getCmccUserPhone();
                    SystemSetting.getCmccDeviceId();
                    SystemSetting.getCmccAndlinkToken();
                    SystemSetting.getCmccGwToken();
                    SystemSetting.getCmccDeviceToken();
                    break;
                }
                break;
            case 1:
                boolean z = bundle.getBoolean("interruptHandleOT");
                Log.i("ProvisionFeatureProvider", "receive interrupt ot tag=" + z + " main thread=" + Threads.isMainThread());
                OtInvokers.getInstance().setInterruptHandleOT(z);
                break;
            case 2:
                Log.i(MicoSupConstants.TAG_LAU, "Info: ProvisionFeatureProvider on logon");
                MiotManager.onLogon();
                break;
            case 3:
                L.init.d("ProvisionFeatureProvider: Process provision(ble) tell Launcher that getAppBindKey ok, deliver it to OT-SDK");
                MiotDeviceManager.getInstance().startService(true, bundle.getString("bindKey"), bundle.getString("partnerId"), bundle.getString("partnerToken"));
                TokenManager.getInstance().blockGetServiceToken(Constants.MICO_SID);
                break;
            case 4:
                L.init.d("ProvisionFeatureProvider: Process provision tell Launcher restart OTService");
                MiotDeviceManager.getInstance().startService(true, "", "", "");
                break;
            case 5:
                L.init.d("ProvisionFeatureProvider: Process provision tell Launcher restartServiceAfterLogin");
                MiotDeviceManager.getInstance().restartServiceAfterLogin();
                TokenManager.getInstance().blockGetServiceToken(Constants.MICO_SID);
                break;
        }
        return bundle2;
    }
}
