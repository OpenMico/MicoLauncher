package com.xiaomi.micolauncher.module.homepage.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.Skill;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.speech.SpeechManager;
import com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener;
import com.xiaomi.micolauncher.common.view.base.FluidLayout;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.skill.manager.SkillAnimationUtils;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class SkillInfoActivity extends BaseActivity {
    private ImageView a;
    private TextView b;
    private FluidLayout c;
    private String d;
    private Skill.SkillInfo e;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.skill_info_layout);
        this.d = getIntent().getStringExtra("EXTRA_SKILL_ID");
        a();
        b();
        scheduleToClose(TimeUnit.MINUTES.toMillis(1L));
    }

    private void a() {
        this.a = (ImageView) findViewById(R.id.bg_iv);
        this.b = (TextView) findViewById(R.id.title_tv);
        this.c = (FluidLayout) findViewById(R.id.fluid_layout);
    }

    private void b() {
        SkillDataManager.getManager().loadSkillInfo(this.d).subscribeOn(MicoSchedulers.io()).observeOn(MicoSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.activity.-$$Lambda$SkillInfoActivity$a3u1ZmIAoRaUDfjDcqo8FWMH_TM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                SkillInfoActivity.this.a((Skill.SkillInfo) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Skill.SkillInfo skillInfo) throws Exception {
        this.e = skillInfo;
        c();
    }

    private void c() {
        Skill.SkillInfo skillInfo = this.e;
        if (skillInfo == null) {
            b();
            return;
        }
        GlideUtils.bindImageView(this, skillInfo.bgImageUrl, this.a);
        this.b.setText(this.e.name);
        d();
    }

    private void d() {
        FluidLayout fluidLayout = this.c;
        if (!(fluidLayout == null || this.e == null)) {
            fluidLayout.removeAllViews();
            int dimensionPixelSize = this.c.getContext().getResources().getDimensionPixelSize(R.dimen.skill_detail_info_margin);
            for (final String str : this.e.getTips()) {
                final View inflate = LayoutInflater.from(this).inflate(R.layout.view_skill_detail_info, (ViewGroup) null);
                final TextView textView = (TextView) inflate.findViewById(R.id.title_tv);
                textView.setText(str);
                ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(-2, -2);
                marginLayoutParams.setMargins(0, 0, dimensionPixelSize, dimensionPixelSize);
                this.c.addView(inflate, marginLayoutParams);
                final ImageView imageView = (ImageView) inflate.findViewById(R.id.image_iv);
                inflate.setOnClickListener(new AvoidFastDoubleClickViewOnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.activity.SkillInfoActivity.1
                    @Override // com.xiaomi.micolauncher.common.view.base.AvoidFastDoubleClickViewOnClickListener
                    public void onAvoidFastDoubleClick(View view) {
                        if (CommonUtil.checkHasNetworkAndShowToast(SkillInfoActivity.this)) {
                            SpeechManager.getInstance().nlpTtsRequest(str, true);
                            SkillAnimationUtils.startClickAnimation(imageView, textView.getMeasuredWidth() - (DisplayUtils.dip2px((Activity) SkillInfoActivity.this, 5.0f) * 2));
                            SkillInfoActivity.this.c.startAnimationSet(inflate);
                        }
                    }
                });
            }
        }
    }

    public static void startActivity(Context context, String str) {
        Intent intent = new Intent(context, SkillInfoActivity.class);
        intent.putExtra("EXTRA_SKILL_ID", str);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }
}
