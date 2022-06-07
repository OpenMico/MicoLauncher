package com.xiaomi.micolauncher.module.homepage.viewholder.new_home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import java.util.List;

/* loaded from: classes3.dex */
public class VideoRecommendAppViewHolder extends BaseViewHolder implements View.OnClickListener {
    private static int h;
    private static int j;
    private int a;
    private final TextView b;
    private ImageView[] c;
    private TextView[] d;
    private TextView[] e;
    private List<VideoTabInfo.RecommendAppInfo> f;
    private long g;
    private String i = "";

    @SuppressLint({"CheckResult", "ClickableViewAccessibility"})
    public VideoRecommendAppViewHolder(View view) {
        super(view);
        this.a = view.getResources().getInteger(R.integer.recommend_video_app_count);
        this.b = (TextView) view.findViewById(R.id.title);
        this.c = new ImageView[this.a];
        this.c[0] = (ImageView) view.findViewById(R.id.app_icon_1);
        this.c[1] = (ImageView) view.findViewById(R.id.app_icon_2);
        this.c[2] = (ImageView) view.findViewById(R.id.app_icon_3);
        this.d = new TextView[this.a];
        this.d[0] = (TextView) view.findViewById(R.id.app_name_1);
        this.d[1] = (TextView) view.findViewById(R.id.app_name_2);
        this.d[2] = (TextView) view.findViewById(R.id.app_name_3);
        this.e = new TextView[this.a];
        this.e[0] = (TextView) view.findViewById(R.id.app_summary_1);
        this.e[1] = (TextView) view.findViewById(R.id.app_summary_2);
        this.e[2] = (TextView) view.findViewById(R.id.app_summary_3);
        if (this.a == 4) {
            view.findViewById(R.id.divider_3).setVisibility(0);
            this.c[3] = (ImageView) view.findViewById(R.id.app_icon_4);
            this.d[3] = (TextView) view.findViewById(R.id.app_name_4);
            this.e[3] = (TextView) view.findViewById(R.id.app_summary_4);
        }
        if (h == 0) {
            h = UiUtils.getEntertainmentCornerRadius(view.getContext());
        }
        if (j == 0) {
            j = view.getContext().getResources().getDimensionPixelSize(R.dimen.video_app_icon_size);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0022, code lost:
        return;
     */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onClick(android.view.View r8) {
        /*
            r7 = this;
            if (r8 != 0) goto L_0x0003
            return
        L_0x0003:
            r0 = 0
        L_0x0004:
            int r1 = r7.a
            r2 = -1
            if (r0 >= r1) goto L_0x001f
            android.widget.ImageView[] r1 = r7.c
            r1 = r1[r0]
            if (r1 == r8) goto L_0x0020
            android.widget.TextView[] r1 = r7.d
            r1 = r1[r0]
            if (r1 == r8) goto L_0x0020
            android.widget.TextView[] r1 = r7.e
            r1 = r1[r0]
            if (r1 != r8) goto L_0x001c
            goto L_0x0020
        L_0x001c:
            int r0 = r0 + 1
            goto L_0x0004
        L_0x001f:
            r0 = r2
        L_0x0020:
            if (r2 != r0) goto L_0x0023
            return
        L_0x0023:
            long r1 = java.lang.System.currentTimeMillis()
            long r3 = r7.g
            long r3 = r1 - r3
            long r3 = java.lang.Math.abs(r3)
            r5 = 1000(0x3e8, double:4.94E-321)
            int r8 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r8 >= 0) goto L_0x0036
            return
        L_0x0036:
            r7.g = r1
            java.util.List<com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo$RecommendAppInfo> r8 = r7.f
            if (r8 != 0) goto L_0x003d
            return
        L_0x003d:
            int r8 = r8.size()
            if (r0 < r8) goto L_0x0044
            return
        L_0x0044:
            java.util.List<com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo$RecommendAppInfo> r8 = r7.f
            java.lang.Object r8 = r8.get(r0)
            com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo$RecommendAppInfo r8 = (com.xiaomi.micolauncher.module.homepage.bean.VideoTabInfo.RecommendAppInfo) r8
            com.xiaomi.mico.appstore.bean.AppInfo r8 = r8.getAppInfo()
            r7.a(r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.VideoRecommendAppViewHolder.onClick(android.view.View):void");
    }

    private void a(final AppInfo appInfo) {
        if (appInfo != null) {
            final Context context = this.itemView.getContext();
            if (AppInfo.TYPE_THIRD_PARTY.equals(appInfo.getAppType())) {
                AppStoreApi.handleAppWithPkgName(context, appInfo.getPackageName(), new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.new_home.-$$Lambda$VideoRecommendAppViewHolder$Q-YkRxwASUJtAjQbR4ulDWgxe4E
                    @Override // java.lang.Runnable
                    public final void run() {
                        VideoRecommendAppViewHolder.a(context, appInfo);
                    }
                });
            } else {
                SchemaManager.handleSchema(context, appInfo.getMicoAction());
                SkillDataManager.getManager().addRecord(appInfo);
            }
            AppRecordHelper.recordAppClickByMi("", appInfo.getAppName());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Context context, AppInfo appInfo) {
        ThirdPartyAppProxy.getInstance().startApp(context, appInfo.getPackageName());
    }

    public void bind(VideoTabInfo.RecommendAppCard recommendAppCard) {
        if (recommendAppCard == null) {
            this.b.setText(this.i);
            a();
            return;
        }
        this.b.setText(recommendAppCard.getTitle());
        this.f = recommendAppCard.getItemList();
        if (ContainerUtil.isEmpty(this.f)) {
            a();
            return;
        }
        int min = Math.min(this.f.size(), this.a);
        int i = 0;
        for (VideoTabInfo.RecommendAppInfo recommendAppInfo : this.f) {
            this.e[i].setText(recommendAppInfo.getDescription());
            AppInfo appInfo = recommendAppInfo.getAppInfo();
            if (appInfo != null) {
                this.d[i].setText(appInfo.getAppName());
                Context applicationContext = this.itemView.getContext().getApplicationContext();
                String iconUrl = appInfo.getIconUrl();
                ImageView imageView = this.c[i];
                int i2 = h;
                int i3 = j;
                GlideUtils.bindImageViewWithRoundUseContext(applicationContext, iconUrl, imageView, i2, R.drawable.app_icon_placeholder, i3, i3);
            } else {
                this.d[i].setText(this.i);
                GlideUtils.bindImageView(this.c[i], (int) R.drawable.app_icon_placeholder);
            }
            i++;
            if (i >= min) {
                return;
            }
        }
    }

    private void a() {
        for (int i = 0; i < this.a; i++) {
            this.d[i].setText(this.i);
            GlideUtils.bindImageView(this.c[i], (int) R.drawable.app_icon_placeholder);
        }
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initInMain() {
        super.initInMain();
        for (int i = 0; i < this.a; i++) {
            AnimLifecycleManager.repeatOnAttach(this.c[i], MicoAnimConfigurator4EntertainmentAndApps.get());
            this.c[i].setOnClickListener(this);
            AnimLifecycleManager.repeatOnAttach(this.d[i], MicoAnimConfigurator4EntertainmentAndApps.get());
            this.d[i].setOnClickListener(this);
            AnimLifecycleManager.repeatOnAttach(this.e[i], MicoAnimConfigurator4EntertainmentAndApps.get());
            this.e[i].setOnClickListener(this);
        }
    }
}
