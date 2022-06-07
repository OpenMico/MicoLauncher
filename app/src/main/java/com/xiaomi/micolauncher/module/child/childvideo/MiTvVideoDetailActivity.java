package com.xiaomi.micolauncher.module.child.childvideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.api.model.ChildVideoDetail;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.base.BaseActivity;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.skills.video.VideoPlayerApi;
import io.reactivex.functions.Consumer;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes3.dex */
public class MiTvVideoDetailActivity extends BaseActivity {
    public static final String EXTRA_COVER = "extra_cover";
    public static final String EXTRA_ID = "extra_id";
    public static final String EXTRA_TITLE = "extra_title";
    public static final String EXTRA_TYPE = "extra_type";
    private String a;
    private String b;
    private String c;
    private ChildVideo.MiTvType d;
    private ImageView e;
    private ImageView f;
    private TextView g;
    private TextView h;
    private TextView i;
    private TextView j;
    private ConstraintLayout k;
    private TextView l;
    private TextView m;
    private TextView n;
    private ImageView o;
    private boolean p;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.base.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    @SuppressLint({"ClickableViewAccessibility"})
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_mitv_video_detail);
        setDefaultScheduleDuration();
        if (getIntent() != null) {
            this.a = getIntent().getStringExtra(EXTRA_ID);
            this.b = getIntent().getStringExtra(EXTRA_TITLE);
            this.c = getIntent().getStringExtra(EXTRA_COVER);
            this.d = (ChildVideo.MiTvType) getIntent().getSerializableExtra(EXTRA_TYPE);
        }
        this.e = (ImageView) findViewById(R.id.detail_cover);
        this.f = (ImageView) findViewById(R.id.detail_vip);
        this.g = (TextView) findViewById(R.id.detail_title);
        this.h = (TextView) findViewById(R.id.detail_subtitle);
        this.i = (TextView) findViewById(R.id.detail_target);
        this.j = (TextView) findViewById(R.id.detail_intro);
        this.k = (ConstraintLayout) findViewById(R.id.play_button_cl);
        this.l = (TextView) findViewById(R.id.open_vip);
        this.m = (TextView) findViewById(R.id.text_play);
        this.n = (TextView) findViewById(R.id.detail_rating);
        this.o = (ImageView) findViewById(R.id.video_detail_back_iv);
        this.k.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.l.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.k.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVideoDetailActivity$q0XYSTysAayfDy-Gtt9PMc_Ruqs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiTvVideoDetailActivity.this.c(view);
            }
        });
        this.l.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVideoDetailActivity$aDnMp_xpghkRHO7k3PyqWWRN1NQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiTvVideoDetailActivity.this.b(view);
            }
        });
        addToDisposeBag(ChildVideoDataManager.getManager().loadMiTvVideoDetail(this.a).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVideoDetailActivity$96PEnypJ_ghw6_uK4n769voBl7o
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVideoDetailActivity.this.a((ChildVideoDetail) obj);
            }
        }, $$Lambda$MiTvVideoDetailActivity$_UYnF9NG48edl8gEUINfiSlEY0.INSTANCE));
        b();
        this.o.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVideoDetailActivity$vhmG5SbRw7JnIJf6iaBtzVLr4_8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MiTvVideoDetailActivity.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        VideoPlayerApi.playMiTv(getApplicationContext(), this.a, this.c, this.b, 0, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        Intent intent = new Intent(getApplicationContext(), MiTvVipActivity.class);
        intent.putExtra(MiTvVipActivity.INTENT_EXTRA_MI_TV_TYPE, this.d);
        ActivityLifeCycleManager.startActivityQuietly(intent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Throwable th) throws Exception {
        L.base.w("loadMiTvVideoDetail", th);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        finish();
    }

    private void a() {
        addToDisposeBag(ChildVideoDataManager.getManager().getVipPromotion(getApplicationContext()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVideoDetailActivity$XQ8gais_U_Fko_6l4wZvWhwiknI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVideoDetailActivity.this.a((ChildVideo.MiTvPromotion) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo.MiTvPromotion miTvPromotion) throws Exception {
        if (ContainerUtil.hasData(miTvPromotion.getData())) {
            for (ChildVideo.MiTvPromotion.Promotion promotion : miTvPromotion.getData()) {
                if (promotion.getStatus() == 1) {
                    switch (this.d) {
                        case CHILD_VIDEO:
                            if (1001 == promotion.getPromotion_type()) {
                                a(R.string.open_video_vip_first);
                                break;
                            } else {
                                continue;
                            }
                        case COURSE_PRIMARY:
                            if (1010 == promotion.getPromotion_type()) {
                                a(R.string.open_course_vip_first);
                                break;
                            } else {
                                continue;
                            }
                        case COURSE_JUNIOR:
                            if (1009 == promotion.getPromotion_type()) {
                                a(R.string.open_course_vip_first);
                                break;
                            } else {
                                continue;
                            }
                        case COURSE_HIGH:
                            if (1009 == promotion.getPromotion_type()) {
                                a(R.string.open_course_vip_first);
                                break;
                            } else {
                                continue;
                            }
                    }
                }
            }
        }
        if (!this.p) {
            a(R.string.open_vip);
        }
    }

    private void a(int i) {
        this.l.setText(getString(i));
        this.p = true;
        this.l.setVisibility(0);
    }

    private void b() {
        addToDisposeBag(ChildVideoDataManager.getManager().c(this.d.getpCode()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.child.childvideo.-$$Lambda$MiTvVideoDetailActivity$pPoT7fu0GYBy4gqpeVVWax4_okI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                MiTvVideoDetailActivity.this.a((ChildVideo.DueTime) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ChildVideo.DueTime dueTime) throws Exception {
        if (dueTime.getDueTime() == 0 || dueTime.getCode() != 0 || dueTime.getDueTime() * 1000 < System.currentTimeMillis()) {
            SystemSetting.setKeyMiTvVipStatus(false);
            this.m.setText(getString(R.string.trail));
            a();
            return;
        }
        SystemSetting.setKeyMiTvVipStatus(true);
        a(R.string.continue_vip);
        this.m.setText(getString(R.string.play));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(ChildVideoDetail childVideoDetail) {
        if (childVideoDetail != null) {
            ChildVideoDetail.MediaBean media = childVideoDetail.getMedia();
            if (TextUtils.isEmpty(media.getEdu_goals())) {
                this.i.setVisibility(8);
            }
            this.g.setText(media.getMedianame());
            this.i.setText(getString(R.string.detail_target).concat(media.getEdu_goals()));
            this.j.setText(getString(R.string.detail_intro).concat(media.getDesc()));
            this.n.setText(String.valueOf(media.getXiaomi_rating()));
            String str = "";
            String str2 = "";
            String str3 = "";
            String str4 = "";
            for (int i = 0; i < ContainerUtil.getSize(media.getGenres()); i++) {
                switch (i) {
                    case 0:
                        str3 = media.getGenres().get(0).concat(StringUtils.SPACE);
                        break;
                    case 1:
                        str3 = str3.replace(StringUtils.SPACE, "").concat("、").concat(media.getGenres().get(1).concat(StringUtils.SPACE));
                        break;
                }
            }
            if (!TextUtils.isEmpty(media.getPremiere_date()) && Integer.valueOf(media.getPremiere_date()).intValue() > 0) {
                str = media.getPremiere_date().concat(StringUtils.SPACE);
            }
            if (ContainerUtil.hasData(media.getRegions())) {
                str2 = media.getRegions().get(0).concat(StringUtils.SPACE);
            }
            if (media.getPlaytime() > 0) {
                str4 = String.valueOf(media.getPlaytime()).concat("分钟 ").concat(StringUtils.SPACE);
            }
            if (media.getXiaomi_rating() > 0.0d) {
                this.n.setVisibility(0);
            }
            if (ContainerUtil.hasData(media.getProduct()) && this.d == ChildVideo.MiTvType.CHILD_VIDEO) {
                this.f.setVisibility(0);
            }
            this.h.setText(String.format("%s%s%s%s%s", str, str2, str3, str4, media.getAudience_ages()));
            GlideUtils.bindImageViewWithRoundCorners(this, media.getPosterurl(), this.e, 25);
        }
    }
}
