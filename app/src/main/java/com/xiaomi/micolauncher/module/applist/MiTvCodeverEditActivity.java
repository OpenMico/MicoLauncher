package com.xiaomi.micolauncher.module.applist;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.GenerateOpaqueUtil;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoDataManager;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;

/* loaded from: classes3.dex */
public class MiTvCodeverEditActivity extends AppCompatActivity {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mi_tv_codever_edit);
        final EditText editText = (EditText) findViewById(R.id.version_edit);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.applist.-$$Lambda$MiTvCodeverEditActivity$ACDEXHGgbcOF2e-17CYohu_j6Jc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiTvCodeverEditActivity.a(editText, view);
            }
        });
        findViewById(R.id.reset).setOnClickListener($$Lambda$MiTvCodeverEditActivity$15kICnX0zT5n_T6FxJMeytCwiuc.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(EditText editText, View view) {
        try {
            ChildVideoDataManager.CODEVER = Integer.parseInt(editText.getText().toString());
            GenerateOpaqueUtil.CODEVER_STRING = "&codever=" + ChildVideoDataManager.CODEVER;
            ToastUtil.showToast((int) R.string.mitv_code_config_success);
        } catch (NumberFormatException unused) {
            ToastUtil.showToast((int) R.string.mitv_code_version_error);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view) {
        ChildVideoDataManager.CODEVER = RomUpdateAdapter.getInstance().getVersion().toIntVersion();
    }
}
