package com.xiaomi.micolauncher.common.schema.handler;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.HttpsUtil;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.homepage.activity.SimpleWebActivity;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class NonMicoSchemaHandler implements SchemaHandler {
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return !"mico".equals(uri.getScheme());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(final Context context, final Uri uri, Object obj) {
        if (HttpsUtil.isHttpOrHttpsSchema(uri)) {
            Intent intent = new Intent(context, SimpleWebActivity.class);
            intent.putExtra(SimpleWebActivity.NAME, uri.toString());
            ActivityLifeCycleManager.startActivityQuietly(intent);
        } else if (!"snssdk1128".equals(uri.getScheme())) {
            b(context, uri);
        } else if (ChildModeManager.getManager().allowPlayingVideoOrShowActivity(context)) {
            AppStoreApi.handleAppWithPkgName(context, "com.ss.android.ugc.aweme", new Runnable() { // from class: com.xiaomi.micolauncher.common.schema.handler.-$$Lambda$NonMicoSchemaHandler$uOwJ5MBt7bh-M0vMOiU0-PrPMAs
                @Override // java.lang.Runnable
                public final void run() {
                    NonMicoSchemaHandler.this.b(context, uri);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void b(Context context, Uri uri) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", uri);
            intent.addFlags(268435456);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            L.base.e("NonMicoSchemaHandler startViewUri", e);
        }
    }
}
