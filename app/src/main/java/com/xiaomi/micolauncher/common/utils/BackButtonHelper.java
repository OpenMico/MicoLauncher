package com.xiaomi.micolauncher.common.utils;

import android.content.Context;
import com.xiaomi.micolauncher.common.setting.SystemSetting;
import com.xiaomi.micolauncher.common.widget.TitleBar;

/* loaded from: classes3.dex */
public class BackButtonHelper {
    public static void setBackIconVisibility(Context context, TitleBar titleBar) {
        titleBar.showLeftIcon(!SystemSetting.isIntroMoviePlayed(context));
    }
}
