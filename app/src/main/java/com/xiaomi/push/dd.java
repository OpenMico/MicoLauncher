package com.xiaomi.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.push.service.ag;

/* loaded from: classes4.dex */
final class dd implements ds {
    private void a(Context context, String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            try {
                if (ah.a(context, String.valueOf(12), 1L)) {
                    hp hpVar = new hp();
                    hpVar.a(str + Constants.COLON_SEPARATOR + str2);
                    hpVar.a(System.currentTimeMillis());
                    hpVar.a(hj.BroadcastAction);
                    dy.a(context, hpVar);
                }
            } catch (Throwable unused) {
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(Context context, Intent intent) {
        int a;
        try {
            String dataString = intent.getDataString();
            if (!TextUtils.isEmpty(dataString)) {
                String[] split = dataString.split(Constants.COLON_SEPARATOR);
                if (split.length >= 2 && !TextUtils.isEmpty(split[1])) {
                    String str = split[1];
                    long currentTimeMillis = System.currentTimeMillis();
                    boolean a2 = ag.a(context).a(hm.BroadcastActionCollectionSwitch.a(), true);
                    if (TextUtils.equals("android.intent.action.PACKAGE_RESTARTED", intent.getAction())) {
                        if (ah.a(context, String.valueOf(12), 1L) && a2) {
                            if (TextUtils.isEmpty(dx.a)) {
                                dx.a += dr.f23a + Constants.COLON_SEPARATOR;
                            }
                            dx.a += str + "(" + currentTimeMillis + ")" + Constants.ACCEPT_TIME_SEPARATOR_SP;
                        }
                    } else if (TextUtils.equals("android.intent.action.PACKAGE_CHANGED", intent.getAction())) {
                        if (ah.a(context, String.valueOf(12), 1L) && a2) {
                            if (TextUtils.isEmpty(dx.b)) {
                                dx.b += dr.b + Constants.COLON_SEPARATOR;
                            }
                            dx.b += str + "(" + currentTimeMillis + ")" + Constants.ACCEPT_TIME_SEPARATOR_SP;
                        }
                    } else {
                        if (TextUtils.equals("android.intent.action.PACKAGE_ADDED", intent.getAction())) {
                            if (!intent.getExtras().getBoolean("android.intent.extra.REPLACING") && a2) {
                                a = hj.BroadcastActionAdded.a();
                            } else {
                                return;
                            }
                        } else if (TextUtils.equals("android.intent.action.PACKAGE_REMOVED", intent.getAction())) {
                            if (!intent.getExtras().getBoolean("android.intent.extra.REPLACING") && a2) {
                                a = hj.BroadcastActionRemoved.a();
                            } else {
                                return;
                            }
                        } else if (TextUtils.equals("android.intent.action.PACKAGE_REPLACED", intent.getAction())) {
                            if (a2) {
                                a = hj.BroadcastActionReplaced.a();
                            } else {
                                return;
                            }
                        } else if (TextUtils.equals("android.intent.action.PACKAGE_DATA_CLEARED", intent.getAction()) && a2) {
                            a = hj.BroadcastActionDataCleared.a();
                        } else {
                            return;
                        }
                        a(context, String.valueOf(a), str);
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    @Override // com.xiaomi.push.ds
    public void a(Context context, Intent intent) {
        if (intent != null) {
            aj.a(context).a(new df(this, context, intent));
        }
    }
}
