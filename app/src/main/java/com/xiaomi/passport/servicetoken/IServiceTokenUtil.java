package com.xiaomi.passport.servicetoken;

import android.content.Context;
import com.xiaomi.accountsdk.futureservice.MiAccountManagerFuture;
import com.xiaomi.passport.servicetoken.data.XmAccountVisibility;

/* loaded from: classes4.dex */
public interface IServiceTokenUtil {
    MiAccountManagerFuture<XmAccountVisibility> canAccessAccount(Context context);

    boolean fastCheckSlhPhCompatibility(Context context);

    ServiceTokenFuture getServiceToken(Context context, String str);

    ServiceTokenFuture invalidateServiceToken(Context context, ServiceTokenResult serviceTokenResult);
}
