package com.xiaomi.micolauncher.module.homepage.viewholder.skill;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.application.ActivityLifeCycleManager;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.appstore.activity.AppStoreActivity;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class AppViewHolder extends BaseViewHolder {
    ImageView[] a = new ImageView[8];
    private Context b;

    public AppViewHolder(View view) {
        super(view);
        this.b = view.getContext();
        this.a[0] = (ImageView) view.findViewById(R.id.app_mijia);
        this.a[1] = (ImageView) view.findViewById(R.id.app_phone);
        this.a[2] = (ImageView) view.findViewById(R.id.app_setting);
        this.a[3] = (ImageView) view.findViewById(R.id.app_schedule);
        this.a[4] = (ImageView) view.findViewById(R.id.app_aiqiyi);
        this.a[5] = (ImageView) view.findViewById(R.id.app_bilibili);
        this.a[6] = (ImageView) view.findViewById(R.id.app_weather);
        this.a[7] = (ImageView) view.findViewById(R.id.app_more);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"ClickableViewAccessibility"})
    public void setData(List<Object> list) {
        int min = Math.min(this.a.length, list.size());
        for (int i = 0; i < min; i++) {
            final SkillApp skillApp = (SkillApp) list.get(i);
            GlideUtils.bindImageView(this.b, skillApp.getIconRes(), this.a[i]);
            this.a[i].setOnTouchListener(MicoAnimationTouchListener.getInstance());
            RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.skill.-$$Lambda$AppViewHolder$4ie9axSX1Qy6RaVzh2_1E87hwEQ
                @Override // io.reactivex.functions.Consumer
                public final void accept(Object obj) {
                    AppViewHolder.this.a(skillApp, obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SkillApp skillApp, Object obj) throws Exception {
        if (TextUtils.isEmpty(skillApp.getUrl())) {
            ActivityLifeCycleManager.startActivityQuietly(new Intent(this.b, AppStoreActivity.class));
        } else {
            SchemaManager.handleSchema(this.b, skillApp.getUrl());
        }
    }
}
