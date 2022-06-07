package com.xiaomi.micolauncher.module.skill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.base.utils.ToastUtil;
import com.xiaomi.mico.common.Hardware;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.SkillStore;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SkillDetailAboutActivity extends BaseActivity {
    private String a;
    private TextView b;
    private TextView c;
    private TextView d;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_skill_detail_about);
        if (getIntent() != null) {
            this.a = getIntent().getStringExtra("EXTRA_SKILL_ID");
        }
        a();
        b();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (TextView) findViewById(R.id.content_tv);
        this.d = (TextView) findViewById(R.id.source_tv);
    }

    private void b() {
        SkillDataManager.getManager().getSkillDetail(Hardware.current(getApplicationContext()).getName(), this.a).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.skill.ui.-$$Lambda$SkillDetailAboutActivity$tY2k8Q68Qv0bElzD39vl7HUrXBE
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillDetailAboutActivity.this.b((SkillStore.SkillDetailV2) obj);
            }
        }, $$Lambda$SkillDetailAboutActivity$468pBA7Lh4XdQo0Cgan5aZ0gSio.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        ToastUtil.showToast((int) R.string.error_get_data_failed);
        L.skillpage.e("load detail data error", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: a */
    public void b(SkillStore.SkillDetailV2 skillDetailV2) {
        this.b.setText(skillDetailV2.displayName);
        this.c.setText(skillDetailV2.summary);
        this.d.setText(getString(R.string.skill_supported_company, new Object[]{skillDetailV2.developerName}));
    }

    public static void openSkillDetailAboutActivity(Context context, String str) {
        Intent intent = new Intent(context, SkillDetailAboutActivity.class);
        intent.putExtra("EXTRA_SKILL_ID", str);
        context.startActivity(intent);
    }
}
