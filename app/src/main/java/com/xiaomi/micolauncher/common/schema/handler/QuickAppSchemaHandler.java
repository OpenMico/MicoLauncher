package com.xiaomi.micolauncher.common.schema.handler;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.schema.SchemaHandler;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class QuickAppSchemaHandler implements SchemaHandler {
    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean needLogin() {
        return true;
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public boolean canHandle(Uri uri) {
        return "hap".equals(uri.getScheme());
    }

    @Override // com.xiaomi.micolauncher.common.schema.SchemaHandler
    public void process(Context context, Uri uri, Object obj) {
        if (canHandle(uri) && ChildModeManager.getManager().allowPlayingVideoOrShowActivity(context)) {
            ActivityLifeCycleManager.startActivityQuietly(context, new Intent("android.intent.action.VIEW", uri));
        }
    }
}
