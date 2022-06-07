package com.xiaomi.mipush.sdk.help;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.p;

/* loaded from: classes4.dex */
public class HelpService extends IntentService {
    public HelpService() {
        super("intentService");
    }

    @Override // android.app.IntentService
    protected void onHandleIntent(Intent intent) {
        if (!TextUtils.isEmpty(intent.getStringExtra("awake_info"))) {
            p.a(this, intent, null);
        }
    }
}
