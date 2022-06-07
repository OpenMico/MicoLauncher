package com.xiaomi.micolauncher.module.homepage.viewholder.apphome;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.schema.SchemaManager;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.micolauncher.module.appstore.manager.AppStoreApi;
import com.xiaomi.micolauncher.module.homepage.bean.RecommendCard;
import com.xiaomi.micolauncher.module.homepage.view.ShadowDrawable;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import com.xiaomi.micolauncher.skills.video.model.ThirdPartyAppProxy;

/* loaded from: classes3.dex */
public class BaseAppHolder extends BaseViewHolder {
    protected AppInfo appInfo;
    protected Context context;
    protected boolean isChildMode;
    protected RecommendCard recommendCard;
    protected String tabName;

    public BaseAppHolder(View view) {
        this(view, true);
    }

    public BaseAppHolder(View view, boolean z) {
        super(view);
        this.context = view.getContext();
        this.isChildMode = ChildModeManager.getManager().isChildMode();
        if (z) {
            setBackground();
        }
    }

    public void setBackground() {
        if (this.isChildMode) {
            View view = this.itemView;
            ShadowDrawable.setShadowDrawable(view, getClass().getName() + this.isChildMode, getBgColor(), getShapeRadius(), getShadowColor(), 12, 0, 0, getBackgroundDrawable(), getBgWidth(), getBgHeight());
            return;
        }
        this.itemView.setBackgroundResource(getBackgroundDrawable());
    }

    protected int getBackgroundDrawable() {
        return this.isChildMode ? R.drawable.operate_layout_bg_child : R.drawable.operate_layout_bg;
    }

    protected int getShapeRadius() {
        return this.context.getResources().getDimensionPixelSize(this.isChildMode ? R.dimen.card_corner_size_child : R.dimen.card_corner_size_adult);
    }

    protected int getBgColor() {
        int i;
        Resources resources;
        L.homepage.i("is child mode : %b", Boolean.valueOf(this.isChildMode));
        if (this.isChildMode) {
            resources = this.context.getResources();
            i = R.color.transparent;
        } else {
            resources = this.context.getResources();
            i = R.color.color_B9BEC7;
        }
        return resources.getColor(i);
    }

    protected int getShadowColor() {
        int i;
        Resources resources;
        if (this.isChildMode) {
            resources = this.context.getResources();
            i = R.color.transparent;
        } else {
            resources = this.context.getResources();
            i = R.color.color_B9BEC7;
        }
        return resources.getColor(i);
    }

    protected int getBgWidth() {
        return UiUtils.getSize(this.context, R.dimen.operate_layout_width);
    }

    protected int getBgHeight() {
        return UiUtils.getSize(this.context, R.dimen.operate_layout_height);
    }

    public void bindAppInfo(AppInfo appInfo, String str) {
        this.appInfo = appInfo;
        this.tabName = str;
    }

    public void bindData(RecommendCard recommendCard, String str) {
        this.recommendCard = recommendCard;
        this.tabName = str;
    }

    protected void executeAction() {
        L.skillpage.i("execute action : appType=%s, appPkg=%s", this.appInfo.getAppType(), this.appInfo.getPackageName());
        executeAction(this.appInfo);
    }

    protected void executeAction(final AppInfo appInfo) {
        if (AppInfo.TYPE_THIRD_PARTY.equals(appInfo.getAppType())) {
            AppStoreApi.handleAppWithPkgName(this.context, appInfo.getPackageName(), new Runnable() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.apphome.-$$Lambda$BaseAppHolder$-WJj1u3Dh5AgBEM267zI-_Ydkd4
                @Override // java.lang.Runnable
                public final void run() {
                    BaseAppHolder.this.a(appInfo);
                }
            });
        } else {
            SchemaManager.handleSchema(this.context, appInfo.getMicoAction());
        }
    }

    public /* synthetic */ void a(AppInfo appInfo) {
        ThirdPartyAppProxy.getInstance().startApp(this.context, appInfo.getPackageName());
    }
}
