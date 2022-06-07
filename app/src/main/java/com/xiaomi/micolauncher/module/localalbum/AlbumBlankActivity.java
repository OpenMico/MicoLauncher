package com.xiaomi.micolauncher.module.localalbum;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.widget.TitleBar;

/* loaded from: classes3.dex */
public class AlbumBlankActivity extends BaseActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_blank_gallery);
        TitleBar titleBar = (TitleBar) findViewById(R.id.title_bar);
        titleBar.setOnLeftIconClickListener(new TitleBar.OnLeftIconClickListener() { // from class: com.xiaomi.micolauncher.module.localalbum.-$$Lambda$4Lg0l3RUecNNdqKjXxqkoISJxCE
            @Override // com.xiaomi.micolauncher.common.widget.TitleBar.OnLeftIconClickListener
            public final void onLeftIconClick() {
                AlbumBlankActivity.this.finish();
            }
        });
        titleBar.showLeftIcon(false);
    }
}
