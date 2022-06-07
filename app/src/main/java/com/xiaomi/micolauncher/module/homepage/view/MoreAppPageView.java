package com.xiaomi.micolauncher.module.homepage.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.module.main.util.CommonUtil;
import com.xiaomi.micolauncher.module.skill.bean.SkillApp;
import io.reactivex.functions.Consumer;
import java.util.List;

/* loaded from: classes3.dex */
public class MoreAppPageView extends ConstraintLayout {
    ImageView[] a;
    private Activity b;

    public MoreAppPageView(Context context) {
        this(context, null);
    }

    public MoreAppPageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MoreAppPageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new ImageView[6];
        LayoutInflater.from(context).inflate(R.layout.more_app_page_layout, this);
        this.a[0] = (ImageView) findViewById(R.id.more_0);
        this.a[1] = (ImageView) findViewById(R.id.more_1);
        this.a[2] = (ImageView) findViewById(R.id.more_2);
        this.a[3] = (ImageView) findViewById(R.id.more_3);
        this.a[4] = (ImageView) findViewById(R.id.more_4);
        this.a[5] = (ImageView) findViewById(R.id.more_5);
    }

    public void setHostActivity(Activity activity) {
        this.b = activity;
    }

    public void setDatas(List<SkillApp> list) {
        if (!ContainerUtil.isEmpty(list)) {
            int size = list.size();
            ImageView[] imageViewArr = this.a;
            int length = size > imageViewArr.length ? imageViewArr.length : list.size();
            final Context context = getContext();
            for (int i = 0; i < length; i++) {
                final SkillApp skillApp = list.get(i);
                GlideUtils.bindImageView(context, skillApp.getIconRes(), this.a[i]);
                RxViewHelp.debounceClicksWithOneSeconds(this.a[i]).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$MoreAppPageView$6Ntp1kaDX1J0E13wIKCjwVsF5Z4
                    @Override // io.reactivex.functions.Consumer
                    public final void accept(Object obj) {
                        MoreAppPageView.this.a(skillApp, context, obj);
                    }
                });
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(SkillApp skillApp, Context context, Object obj) throws Exception {
        if (!skillApp.isNeedNetwork() || CommonUtil.checkHasNetworkAndShowToast(context)) {
            SchemaManager.handleSchema(context, skillApp.getUrl());
            Activity activity = this.b;
            if (activity != null) {
                activity.finish();
                L.skillpage.i("finish MoreApp activity");
                return;
            }
            L.skillpage.i("MoreApp activity is null");
        }
    }
}
