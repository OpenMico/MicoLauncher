package org.hapjs.features.channel.transparentactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/* loaded from: classes5.dex */
public class TransparentActivity extends Activity {
    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        getWindow().addFlags(56);
        super.onCreate(bundle);
        TransparentActivityManager.getInstance().onActivityLaunch(this);
    }

    @Override // android.app.Activity
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        TransparentActivityManager.getInstance().onActivityLaunch(this);
    }

    @Override // android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        TransparentActivityManager.getInstance().onActivityDestroy();
    }
}
