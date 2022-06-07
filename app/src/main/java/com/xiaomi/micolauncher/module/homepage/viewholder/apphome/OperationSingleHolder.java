package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.request.RequestOptions;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.transformation.MicoTransformUtils;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class OperationSingleHolder extends BaseAppHolder {
    private static RequestOptions e;
    private static int f;
    private static int g;
    ImageView a;
    ImageView b;
    TextView c;
    TextView d;
    private boolean h;
    private int i;
    private int j;
    private int k;
    private CardView l;

    public OperationSingleHolder(View view) {
        super(view, true);
        this.a = (ImageView) view.findViewById(R.id.operation_content_cover);
        this.b = (ImageView) view.findViewById(R.id.operation_icon);
        this.c = (TextView) view.findViewById(R.id.operation_title);
        this.d = (TextView) view.findViewById(R.id.operation_sub);
        this.l = (CardView) view.findViewById(R.id.card_view);
        if (f == 0) {
            f = view.getResources().getDimensionPixelOffset(R.dimen.app_card_item_radius);
        }
        if (g == 0) {
            g = UiUtils.getSize(this.context, R.dimen.operate_bottom_app_icon_corner);
        }
        this.i = SizeUtils.dp2px(80.0f);
        this.h = ChildModeManager.getManager().isChildMode();
        this.j = UiUtils.getSize(this.context, R.dimen.operate_layout_width);
        this.k = UiUtils.getSize(this.context, R.dimen.operate_single_app_height);
        if (e == null && this.h) {
            e = GlideUtils.createVideoRequestOptionsWithTransform(UiUtils.getSize(this.context, R.dimen.child_operate_layout_width), UiUtils.getSize(this.context, R.dimen.child_operate_single_cover_height), MicoTransformUtils.topCornerTransformation(f));
        }
        CardView cardView = this.l;
        if (cardView != null) {
            cardView.setRadius(f);
        }
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    @SuppressLint({"CheckResult"})
    protected void initViewInMain() {
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$OperationSingleHolder$nWGaK6Dp-9qC1Mge5zQA2PSk1CQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                OperationSingleHolder.this.a(obj);
            }
        });
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        executeAction();
        AppRecordHelper.recordAppClickTrackKey(this.recommendCard.getTrackKey());
        AppRecordHelper.recordAppClickByMi(this.tabName, this.appInfo.getAppName());
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindData(RecommendCard recommendCard, String str) {
        super.bindData(recommendCard, str);
        this.appInfo = SkillDataManager.getManager().getAppInfoByAppKey(recommendCard.getAppKey());
        if (this.appInfo == null) {
            L.skillpage.i("app key %d is a hidden app", Long.valueOf(recommendCard.getAppKey()));
            return;
        }
        GlideUtils.bindImageViewWithRoundCorners(this.context.getApplicationContext(), this.appInfo.getIconUrl(), this.b, g, this.h ? R.drawable.img_appcenter_placeholder : R.drawable.app_icon_placeholder);
        if (this.h) {
            GlideUtils.bindImageViewWithDesignatedLocation(this.a, recommendCard.getCoverUrl(), e);
        } else {
            GlideUtils.bindImageViewWithBlurUseContext(this.context.getApplicationContext(), recommendCard.getCoverUrl(), this.a, R.drawable.app_card_loading, this.j, this.k, this.i);
        }
        this.c.setText(recommendCard.getTitle());
        this.d.setMaxLines(1);
        this.d.setEllipsize(TextUtils.TruncateAt.END);
        this.d.setText(recommendCard.getDescription());
    }
}
