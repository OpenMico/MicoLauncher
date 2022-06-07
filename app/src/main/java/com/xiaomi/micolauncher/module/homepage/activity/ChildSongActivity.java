package com.xiaomi.micolauncher.module.homepage.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;

/* loaded from: classes3.dex */
public class ChildSongActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.child_song_layout);
        setDefaultScheduleDuration();
    }
}
