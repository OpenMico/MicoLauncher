package com.xiaomi.micolauncher.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class DialogActivity extends BaseActivity {
    public static final int CODE_CANCEL_BUTTON = 2;
    public static final int CODE_CONFIRM_BUTTON = 1;
    public static final String KEY_DIALOG_CANCEL_BUTTON = "KEY_DIALOG_CANCEL_BUTTON";
    public static final String KEY_DIALOG_CONFIRM_BUTTON = "KEY_DIALOG_CONFIRM_BUTTON";
    public static final String KEY_DIALOG_MESSAGE = "KEY_DIALOG_MESSAGE";
    public static final String KEY_DIALOG_TIMEOUT = "KEY_DIALOG_TIMEOUT";
    public static final String KEY_TIMEOUT_RESULT = "KEY_TIMEOUT_RESULT";
    TextView a;
    Button b;
    Button c;
    private Runnable d;
    private int e;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dialog);
        this.a = (TextView) findViewById(R.id.textViewMessage);
        this.b = (Button) findViewById(R.id.buttonCancel);
        this.c = (Button) findViewById(R.id.buttonConfirm);
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(KEY_DIALOG_MESSAGE);
        String stringExtra2 = intent.getStringExtra(KEY_DIALOG_CONFIRM_BUTTON);
        String stringExtra3 = intent.getStringExtra(KEY_DIALOG_CANCEL_BUTTON);
        int intExtra = intent.getIntExtra(KEY_DIALOG_TIMEOUT, 0);
        this.e = intent.getIntExtra(KEY_TIMEOUT_RESULT, 2);
        this.a.setText(stringExtra);
        if (stringExtra2 == null || stringExtra2.length() <= 0) {
            this.c.setVisibility(8);
        } else {
            this.c.setText(stringExtra2);
            this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.base.-$$Lambda$DialogActivity$wirMrJ0XXEqKbATlrogrkUgZQFE
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DialogActivity.this.b(view);
                }
            });
        }
        if (stringExtra3 == null || stringExtra3.length() <= 0) {
            this.b.setVisibility(8);
        } else {
            this.b.setText(stringExtra3);
            this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.common.base.-$$Lambda$DialogActivity$jM-prxCbnPqEHJLGg_bLlRcPhyU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    DialogActivity.this.a(view);
                }
            });
        }
        this.d = new Runnable() { // from class: com.xiaomi.micolauncher.common.base.-$$Lambda$DialogActivity$CwvSd3cUJxHW1smKwFI8795gTF4
            @Override // java.lang.Runnable
            public final void run() {
                DialogActivity.this.a();
            }
        };
        if (intExtra > 0) {
            getHandler().postDelayed(this.d, TimeUnit.SECONDS.toMillis(intExtra));
        } else {
            setDefaultScheduleDuration();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        setResult(1);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        setResult(2);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a() {
        setResult(this.e);
        finish();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        getHandler().removeCallbacks(this.d);
    }
}
