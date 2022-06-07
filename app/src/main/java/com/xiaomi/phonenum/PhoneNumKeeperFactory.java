package com.xiaomi.phonenum;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.xiaomi.phonenum.phone.PhoneInfoManager;
import com.xiaomi.phonenum.phone.PhoneUtil;
import com.xiaomi.phonenum.utils.RomUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class PhoneNumKeeperFactory {
    public PhoneNumKeeper createPhoneNumKeeper(Context context, String str) {
        Context applicationContext = context.getApplicationContext();
        PhoneUtil defaultPhoneUtil = PhoneInfoManager.getDefaultPhoneUtil(context);
        PhoneNumKeeper phoneNumKeeper = new PhoneNumKeeper(defaultPhoneUtil);
        PhoneNumStore phoneNumStore = new PhoneNumStore(applicationContext, str, defaultPhoneUtil);
        if (!a(applicationContext) || !RomUtil.checkPermission(applicationContext, "com.xiaomi.permission.CLOUD_MANAGER")) {
            phoneNumKeeper.setPhoneNumGetter(new d(phoneNumStore));
        } else {
            phoneNumKeeper.setPhoneNumGetter(new a(new b(applicationContext), phoneNumStore));
        }
        return phoneNumKeeper;
    }

    private boolean a(Context context) {
        Intent intent = new Intent(Constant.ACTION_BIND_SERVICE);
        intent.setPackage("com.xiaomi.simactivate.service");
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        return queryIntentServices != null && queryIntentServices.size() > 0;
    }
}
