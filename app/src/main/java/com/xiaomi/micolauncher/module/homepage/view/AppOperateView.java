package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xiaomi.mico.appstore.bean.AppInfo;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.utils.X2CWrapper;
import com.xiaomi.micolauncher.module.homepage.bean.AppItem;
import com.xiaomi.micolauncher.module.skill.manager.SkillDataManager;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;

/* loaded from: classes3.dex */
public class AppOperateView extends FrameLayout {
    private static int d;
    private static int f;
    ImageView a;
    TextView b;
    TextView c;
    private boolean e;

    public AppOperateView(Context context) {
        this(context, null);
    }

    public AppOperateView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AppOperateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e = ChildModeManager.getManager().isChildMode();
        X2CWrapper.inflate(context, this.e ? R.layout.child_app_operation_item_first_type : R.layout.app_operation_item_first_type, this);
        this.a = (ImageView) findViewById(R.id.app_icon);
        this.b = (TextView) findViewById(R.id.app_name_tv);
        this.c = (TextView) findViewById(R.id.app_introduction);
        if (d == 0) {
            d = UiUtils.getSize(context, R.dimen.home_skill_app_operation_item_app_icon_size);
        }
        if (f == 0) {
            f = UiUtils.getSize(context, R.dimen.app_icon_corner);
        }
    }

    public void bindData(AppItem appItem) {
        AppInfo appInfoByAppKey = SkillDataManager.getManager().getAppInfoByAppKey(appItem.getAppKey());
        String iconUrl = appInfoByAppKey == null ? null : appInfoByAppKey.getIconUrl();
        if (this.e) {
            GlideUtils.bindImageViewWithRoundCorners(getContext(), iconUrl, this.a, UiUtils.getSize(getContext(), R.dimen.child_app_icon_corner), (int) R.drawable.img_appcenter_placeholder);
        } else {
            Context applicationContext = getContext().getApplicationContext();
            ImageView imageView = this.a;
            int i = f;
            int i2 = d;
            GlideUtils.bindImageViewWithRoundUseContext(applicationContext, iconUrl, imageView, i, R.drawable.app_icon_placeholder, i2, i2);
        }
        this.b.setText(appItem.getItemName());
        this.c.setText(appItem.getDescription());
    }
}
