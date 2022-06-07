package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.common.widget.CustomDialog;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.homepage.event.DeleteAppEvent;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class AppViewHolder extends BaseAppHolder {
    TextView a;
    ImageView b;
    ImageView c;
    CustomDialog d;
    private boolean e = true;
    private int f;

    @SuppressLint({"CheckResult"})
    public AppViewHolder(View view) {
        super(view, false);
        this.context = view.getContext();
        this.a = (TextView) view.findViewById(R.id.app_title);
        this.b = (ImageView) view.findViewById(R.id.delete_iv);
        this.c = (ImageView) view.findViewById(R.id.app_icon);
    }

    @Override // com.xiaomi.micolauncher.common.view.BaseViewHolder
    public void initInMain() {
        super.initInMain();
        RxViewHelp.debounceClicksWithOneSeconds(this.itemView).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppViewHolder$tkIS3HQ7bSLjGZ779b_j4tiuLBw
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppViewHolder.this.b(obj);
            }
        });
        RxViewHelp.debounceClicksWithOneSeconds(this.b).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppViewHolder$vS5oMyAKJX0zs6Jpm-N6sQXkClI
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppViewHolder.this.a(obj);
            }
        });
        AnimLifecycleManager.repeatOnAttach(this.itemView, MicoAnimConfigurator4EntertainmentAndApps.get());
    }

    public /* synthetic */ void b(Object obj) throws Exception {
        if (this.e) {
            a();
            return;
        }
        executeAction();
        AppRecordHelper.recordAppClickByMi(this.tabName, this.appInfo.getAppName());
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        a();
    }

    @Override // com.xiaomi.micolauncher.module.homepage.viewholder.apphome.BaseAppHolder
    public void bindAppInfo(AppInfo appInfo, String str) {
        super.bindAppInfo(appInfo, str);
        GlideUtils.bindImageViewWithRoundCorners(this.context, appInfo.getIconUrl(), this.c, UiUtils.getSize(this.context, R.dimen.app_icon_corner), ChildModeManager.getManager().isChildMode() ? R.drawable.img_appcenter_placeholder : R.drawable.skill_app_icon_place_holder);
        this.a.setSingleLine();
        this.a.setText(appInfo.getAppName());
        this.a.setTextColor(this.context.getResources().getColor(this.isChildMode ? R.color.child_app_name_color : R.color.app_name_color));
        if (this.e) {
            manageApp();
            return;
        }
        b();
        completeManageApp();
    }

    public void setManaged(boolean z) {
        this.e = z;
    }

    public void setPosition(int i) {
        this.f = i;
    }

    public void manageApp() {
        L.skillpage.i("%s is removable? %b", this.appInfo.getAppName(), Boolean.valueOf(this.appInfo.isRemovable()));
        if (this.appInfo.isRemovable()) {
            this.b.setVisibility(0);
            this.b.setImageResource(ChildModeManager.getManager().isChildMode() ? R.drawable.kid_skill_icon_delete : R.drawable.skill_icon_delete);
            return;
        }
        this.b.setVisibility(4);
    }

    public void completeManageApp() {
        this.b.setVisibility(4);
    }

    @SuppressLint({"CheckResult"})
    private void a() {
        if (this.appInfo.isRemovable()) {
            this.d = new CustomDialog.Builder().setTitle(this.context.getString(R.string.confirm_delete_app, this.appInfo.getAppName())).setMessageResId(R.string.delete_app_promption).setPositiveResId(R.string.common_confirm).setNegativeResId(R.string.common_cancel).setPositiveOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppViewHolder$pW0m1-kXSEUtiNZTFJNOK17a558
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AppViewHolder.this.b(view);
                }
            }).setNegativeOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$AppViewHolder$xDfi7qJPgaV9Q4Cp9dPByqewonU
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AppViewHolder.this.a(view);
                }
            }).build();
            this.d.show();
        }
    }

    public /* synthetic */ void b(View view) {
        SkillDataManager.getManager().deleteRecord(this.appInfo);
        EventBusRegistry.getEventBus().post(new DeleteAppEvent(this.f));
        AppStoreApi.deepClearApps(this.context, this.appInfo.getPackageName()).subscribe(new Consumer<String>() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.AppViewHolder.1
            /* renamed from: a */
            public void accept(String str) throws Exception {
                L.skillpage.i("deep clear apps result : %s", str);
            }
        });
        this.d.dismiss();
    }

    public /* synthetic */ void a(View view) {
        this.d.dismiss();
    }

    private void b() {
        CustomDialog customDialog = this.d;
        if (customDialog != null && customDialog.isShowing()) {
            this.d.dismiss();
        }
    }
}
