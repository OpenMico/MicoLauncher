package com.xiaomi.micolauncher.module.video.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.BlurBuilder;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.video.manager.VideoThirdPartyJumpManager;

/* loaded from: classes3.dex */
public class VideoThirdPartyJumpActivity extends BaseActivity {
    private RelativeLayout a;
    private TextView b;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_third_party_jump);
        a();
        b();
    }

    private void a() {
        this.a = (RelativeLayout) findViewById(R.id.container_view);
        this.b = (TextView) findViewById(R.id.confirm_btn);
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.video.ui.-$$Lambda$VideoThirdPartyJumpActivity$WCibo_Lkjh7rcgOWcs_0anxxcKY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                VideoThirdPartyJumpActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        c();
        finish();
    }

    private void b() {
        try {
            BlurBuilder.snapShotFullScreen(this);
            this.a.setBackground(new BitmapDrawable(getResources(), BlurBuilder.blur(this)));
        } catch (Exception e) {
            XLog.e(e);
        }
    }

    private void c() {
        VideoThirdPartyJumpManager.recordOpenThirdPartyActivity(this);
        SchemaManager.handleSchema(this, "thirdparty://ibili/start_cast_directly");
    }
}
