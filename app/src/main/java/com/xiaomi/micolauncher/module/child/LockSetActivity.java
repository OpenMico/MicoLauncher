package com.xiaomi.micolauncher.module.child;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.settingslib.core.MicoSettings;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.ubus.storage.ChildModeStorage;
import com.xiaomi.micolauncher.module.homepage.event.MainPageMaskEvent;
import com.xiaomi.micolauncher.module.setting.widget.InputPassView;

/* loaded from: classes3.dex */
public class LockSetActivity extends BaseActivity implements InputPassView.OnInputPassClickListener {
    public static final String EXTRA_QUIT_CHILD_MODE = "extra_quit_child_mode";
    public static final int REQUEST_CODE_SET_PASS_OK = 291;
    private InputPassView a;
    private TextView b;
    private ImageView c;
    private TextView d;
    private String e;
    private boolean f = true;
    private View g;
    private boolean h;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_lock_set);
        findViewById(R.id.big_setting_blank).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.-$$Lambda$LockSetActivity$iX1ll8vK3wHWxHMlxmUq1rYwkjE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockSetActivity.this.b(view);
            }
        });
        this.a = (InputPassView) findViewById(R.id.input_pass_view);
        this.b = (TextView) findViewById(R.id.set_pass_tip);
        this.c = (ImageView) findViewById(R.id.set_pass_return);
        this.d = (TextView) findViewById(R.id.set_pass_error_tip);
        this.g = findViewById(R.id.big_setting_blank);
        this.a.setOnInputPassClickListener(this);
        this.h = getIntent().getBooleanExtra(EXTRA_QUIT_CHILD_MODE, false);
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.-$$Lambda$LockSetActivity$TFgPih3DJXIj92EqeCqXZ1zDlYU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockSetActivity.this.a(view);
            }
        });
        setDefaultScheduleDuration();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        this.b.setText(R.string.security_input_new);
        this.c.setVisibility(8);
        this.d.setVisibility(8);
        this.a.clear();
        this.f = true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        EventBusRegistry.getEventBus().post(new MainPageMaskEvent(true));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        EventBusRegistry.getEventBus().post(new MainPageMaskEvent(false));
    }

    @Override // com.xiaomi.micolauncher.module.setting.widget.InputPassView.OnInputPassClickListener
    public void onInputDone(String str, boolean z) {
        if (this.f) {
            this.e = str;
            this.a.clear();
            this.c.setVisibility(0);
            this.b.setText(getString(R.string.security_input_again));
        } else if (this.e.equals(str)) {
            MicoSettings.setAppLockPass(this, str);
            setResult(-1);
            this.g.setBackgroundColor(getResources().getColor(R.color.color_00000000));
            finish();
            if (this.h) {
                ChildModeStorage.getInstance().quitChildMode(this);
            }
        } else {
            this.d.setText(R.string.security_input_again_error);
            this.d.setVisibility(0);
            this.a.clear();
        }
        this.f = false;
    }
}
