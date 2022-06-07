package com.xiaomi.accounts;

import android.accounts.AuthenticatorDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/* loaded from: classes2.dex */
public class AccountAuthenticatorCache {
    private Context a;
    private final String b = AccountManager.ACTION_AUTHENTICATOR_INTENT;
    private ServiceInfo<AuthenticatorDescription> c;

    public AccountAuthenticatorCache(Context context) {
        this.a = context;
        a();
    }

    void a() {
        Intent intent = new Intent(this.b);
        intent.setPackage(this.a.getPackageName());
        this.c = a(this.a.getPackageManager().resolveService(intent, 0));
    }

    private ServiceInfo<AuthenticatorDescription> a(ResolveInfo resolveInfo) {
        ApplicationInfo applicationInfo;
        String str;
        String str2;
        if (resolveInfo != null) {
            android.content.pm.ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            String str3 = serviceInfo.packageName;
            str2 = serviceInfo.name;
            applicationInfo = serviceInfo.applicationInfo;
            str = str3;
        } else {
            str = this.a.getPackageName();
            str2 = "com.xiaomi.passport.accountmanager.MiAuthenticatorService";
            applicationInfo = this.a.getApplicationInfo();
        }
        return new ServiceInfo<>(new AuthenticatorDescription("com.xiaomi", str, applicationInfo.labelRes, applicationInfo.icon, applicationInfo.icon, -1), new ComponentName(str, str2), applicationInfo.uid);
    }

    public ServiceInfo<AuthenticatorDescription> getServiceInfo(AuthenticatorDescription authenticatorDescription) {
        if (authenticatorDescription != null && TextUtils.equals("com.xiaomi", authenticatorDescription.type)) {
            return this.c;
        }
        Log.i("Account", "no xiaomi account type");
        return null;
    }

    public Collection<ServiceInfo<AuthenticatorDescription>> getAllServices() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(this.c);
        return Collections.unmodifiableCollection(arrayList);
    }

    /* loaded from: classes2.dex */
    public static class ServiceInfo<V> {
        public final ComponentName componentName;
        public final V type;
        public final int uid;

        public ServiceInfo(V v, ComponentName componentName, int i) {
            this.type = v;
            this.componentName = componentName;
            this.uid = i;
        }

        public String toString() {
            return "ServiceInfo: " + this.type + ", " + this.componentName + ", uid " + this.uid;
        }
    }
}
