package com.xiaomi.micolauncher.common.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import java.util.WeakHashMap;

/* loaded from: classes3.dex */
public class BaseEventActivity extends BaseActivity {
    public static final String KEY_DIALOG_ID = "DIALOG_ID";
    private static WeakHashMap<String, BaseEventActivity> a = new WeakHashMap<>();
    public String dialogId;
    protected boolean finishBySameActivity;
    public boolean onTtsPlayEnd;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        String name = getClass().getName();
        BaseEventActivity baseEventActivity = a.get(name);
        if (baseEventActivity != null) {
            baseEventActivity.finishBySameActivity = true;
            baseEventActivity.finish();
        }
        a.put(name, this);
    }

    @Override // com.xiaomi.micolauncher.common.base.BaseActivity
    @NonNull
    public BaseActivity.EventBusRegisterMode getEventBusRegisterMode() {
        return BaseActivity.EventBusRegisterMode.WHOLE_LIFE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        String name = getClass().getName();
        if (a.get(name) == this) {
            a.remove(name);
        }
    }

    public void onTtsPlayEnd() {
        this.onTtsPlayEnd = true;
    }
}
