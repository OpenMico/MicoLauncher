package com.xiaomi.push;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.Constants;

/* loaded from: classes4.dex */
public class dk implements Application.ActivityLifecycleCallbacks {
    private String a;
    private String b;
    private Context c;

    public dk(Context context, String str) {
        this.a = "";
        this.c = context;
        this.a = str;
    }

    private void a(String str) {
        hp hpVar = new hp();
        hpVar.a(str);
        hpVar.a(System.currentTimeMillis());
        hpVar.a(hj.ActivityActiveTimeStamp);
        dy.a(this.c, hpVar);
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityDestroyed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityPaused(Activity activity) {
        String localClassName = activity.getLocalClassName();
        if (!TextUtils.isEmpty(this.a) && !TextUtils.isEmpty(localClassName)) {
            this.b = "";
            if (TextUtils.isEmpty(this.b) || TextUtils.equals(this.b, localClassName)) {
                a(this.c.getPackageName() + "|" + localClassName + Constants.COLON_SEPARATOR + this.a + Constants.ACCEPT_TIME_SEPARATOR_SP + String.valueOf(System.currentTimeMillis() / 1000));
                this.a = "";
                this.b = "";
                return;
            }
            this.a = "";
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public void onActivityResumed(Activity activity) {
        if (TextUtils.isEmpty(this.b)) {
            this.b = activity.getLocalClassName();
        }
        this.a = String.valueOf(System.currentTimeMillis() / 1000);
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
