package com.xiaomi.micolauncher.module.main.util;

import android.content.Context;
import com.xiaomi.mico.base.utils.NetworkUtil;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class CommonUtil {
    public static boolean checkHasNetworkAndShowToast(Context context) {
        boolean isNetworkConnected = NetworkUtil.isNetworkConnected(context);
        if (!isNetworkConnected) {
            ToastUtil.showToast((int) R.string.setting_update_network_broken);
        }
        return isNetworkConnected;
    }
}
