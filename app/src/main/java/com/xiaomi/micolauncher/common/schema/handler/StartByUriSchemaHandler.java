package com.xiaomi.micolauncher.common.schema.handler;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;

/* loaded from: classes3.dex */
public class StartByUriSchemaHandler implements SchemaHandler {
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return "thirdpartyapp".equals(uri.getScheme()) | "efunbox".equals(uri.getScheme());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(final Context context, final Uri uri, Object obj) {
        L.homepage.i("host : %s,authority : %s", uri.getHost(), uri.getAuthority());
        AppStoreApi.handleAppWithPkgName(context, uri.getHost(), new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$StartByUriSchemaHandler$VajuggsL4g8gbxLSUlHmJ60ng3g
            @Override // java.lang.Runnable
            public final void run() {
                StartByUriSchemaHandler.this.b(context, uri);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void b(Context context, Uri uri) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addFlags(268435456);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            L.base.e("StartByUriSchemaHandler startViewUri", e);
        }
    }
}
