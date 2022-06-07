package com.xiaomi.micolauncher.application.setup;

import android.content.Context;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.DebugHelper;
import com.xiaomi.micolauncher.common.GlideUtils;

/* loaded from: classes3.dex */
public class DrawableSetup implements ISetupRule {
    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public boolean mustBeSync(Context context) {
        return false;
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void onDestroy() {
    }

    @Override // com.xiaomi.micolauncher.application.setup.ISetupRule
    public void setup(Context context) {
        long beginTiming = DebugHelper.beginTiming();
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_vip);
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_fire);
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_aiqiyi);
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_youku);
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_bilibili);
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_mangguo);
        GlideUtils.loadHolderDrawable(context, R.drawable.icon_video_tencent);
        GlideUtils.loadHolderDrawable(context, R.drawable.music_loading_circle);
        GlideUtils.loadHolderDrawable(context, R.drawable.app_icon_placeholder);
        GlideUtils.loadHolderDrawable(context, R.drawable.music_card_view_default);
        DebugHelper.endTiming(beginTiming, "load holder drawable", new Object[0]);
    }
}
