package com.xiaomi.mipush.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.push.ev;
import com.xiaomi.push.ew;
import java.util.HashSet;
import java.util.Set;

@TargetApi(14)
/* loaded from: classes4.dex */
public class a implements Application.ActivityLifecycleCallbacks {
    private Set<String> a = new HashSet();

    private static void a(Application application) {
        application.registerActivityLifecycleCallbacks(new a());
    }

    public static void a(Context context) {
        a((Application) context.getApplicationContext());
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        ew a;
        String packageName;
        String a2;
        int i;
        String str;
        Intent intent = activity.getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("messageId");
            int intExtra = intent.getIntExtra("eventMessageType", -1);
            if (!TextUtils.isEmpty(stringExtra) && intExtra > 0 && !this.a.contains(stringExtra)) {
                this.a.add(stringExtra);
                if (intExtra == 3000) {
                    a = ew.a(activity.getApplicationContext());
                    packageName = activity.getPackageName();
                    a2 = ev.m896a(intExtra);
                    i = 3008;
                    str = "App calls by business message is visiable";
                } else if (intExtra == 1000) {
                    a = ew.a(activity.getApplicationContext());
                    packageName = activity.getPackageName();
                    a2 = ev.m896a(intExtra);
                    i = 1008;
                    str = "app calls by notification message is visiable";
                } else {
                    return;
                }
                a.a(packageName, a2, stringExtra, i, str);
            }
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStarted(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityStopped(Activity activity) {
    }
}
