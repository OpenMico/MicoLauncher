package com.xiaomi.micolauncher.module.setting;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.widget.TitleBar;

/* loaded from: classes3.dex */
public class DevTestActivity extends BaseActivity {
    private TitleBar a;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_develop);
        this.a = (TitleBar) findViewById(R.id.title_bar);
        this.a.setOnLeftIconClickListener(new TitleBar.OnLeftIconClickListener() { // from class: com.xiaomi.micolauncher.module.setting.-$$Lambda$DevTestActivity$hqKA0JmCoHJi7B6-6iqZ7a5gcDo
            @Override // com.xiaomi.micolauncher.common.widget.TitleBar.OnLeftIconClickListener
            public final void onLeftIconClick() {
                DevTestActivity.this.a();
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new DevFragment()).commit();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        finish();
    }
}
