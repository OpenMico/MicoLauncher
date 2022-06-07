package com.xiaomi.micolauncher.module.homepage.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4EntertainmentAndApps;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.homepage.record.AppRecordHelper;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public abstract class AppBaseView extends LinearLayout {
    private AppInfo a;
    protected final ImageView appIcon;
    protected final TextView appName;
    private String b;
    private String c;
    private int d;
    private int e;

    protected abstract int layoutId();

    public AppBaseView(Context context) {
        this(context, null);
    }

    @SuppressLint({"CheckResult"})
    public AppBaseView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.e = -1;
        LayoutInflater.from(context).inflate(layoutId(), this);
        this.appIcon = (ImageView) findViewById(R.id.app_icon);
        this.appName = (TextView) findViewById(R.id.app_tv);
        this.appName.setSingleLine();
    }

    public void initMain() {
        AnimLifecycleManager.repeatOnAttach(this, MicoAnimConfigurator4EntertainmentAndApps.get());
        RxViewHelp.debounceClicksWithOneSeconds(this).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppBaseView$2u6Ji8tULc8dvy083CYa1bMD1h0
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                AppBaseView.this.a(obj);
            }
        });
    }

    public /* synthetic */ void a(Object obj) throws Exception {
        a(this.a);
    }

    public void setPlaceViewHolderDrawableId(int i) {
        this.e = i;
    }

    public void setIconSize(int i) {
        this.d = i;
    }

    public void bindAppInfo(AppInfo appInfo, String str, String str2, float f) {
        if (!ChildModeManager.getManager().isChildMode()) {
            this.appName.setTextSize(f);
        }
        bindAppInfo(appInfo, str, str2);
    }

    public void bindAppInfo(AppInfo appInfo, String str, String str2) {
        this.c = str;
        this.b = str2;
        this.a = appInfo;
        boolean isChildMode = ChildModeManager.getManager().isChildMode();
        int i = R.drawable.app_icon_placeholder;
        int i2 = R.dimen.app_icon_corner;
        if (isChildMode || this.d == 0) {
            Context context = getContext();
            String iconUrl = appInfo.getIconUrl();
            ImageView imageView = this.appIcon;
            Context context2 = getContext();
            if (isChildMode) {
                i2 = R.dimen.child_app_icon_corner;
            }
            int size = UiUtils.getSize(context2, i2);
            if (isChildMode) {
                i = R.drawable.img_appcenter_placeholder;
            }
            GlideUtils.bindImageViewWithRoundCorners(context, iconUrl, imageView, size, i);
        } else {
            Context applicationContext = getContext().getApplicationContext();
            String iconUrl2 = appInfo.getIconUrl();
            ImageView imageView2 = this.appIcon;
            int size2 = UiUtils.getSize(getContext(), R.dimen.app_icon_corner);
            int i3 = this.e;
            int i4 = i3 == -1 ? R.drawable.app_icon_placeholder : i3;
            int i5 = this.d;
            GlideUtils.bindImageViewWithRoundUseContext(applicationContext, iconUrl2, imageView2, size2, i4, i5, i5);
        }
        this.appName.setText(appInfo.getAppName());
    }

    private void a(final AppInfo appInfo) {
        if (AppInfo.TYPE_THIRD_PARTY.equals(appInfo.getAppType())) {
            AppStoreApi.handleAppWithPkgName(getContext(), appInfo.getPackageName(), new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.view.-$$Lambda$AppBaseView$HGydHcswsazWbVpURLpZNu-Xhms
                @Override // java.lang.Runnable
                public final void run() {
                    AppBaseView.this.b(appInfo);
                }
            });
        } else {
            SchemaManager.handleSchema(getContext(), appInfo.getMicoAction());
            SkillDataManager.getManager().addRecord(appInfo);
        }
        AppRecordHelper.recordAppClickTrackKey(this.b);
        AppRecordHelper.recordAppClickByMi(this.c, appInfo.getAppName());
    }

    public /* synthetic */ void b(AppInfo appInfo) {
        ThirdPartyAppProxy.getInstance().startApp(getContext(), appInfo.getPackageName());
    }
}
