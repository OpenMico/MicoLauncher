package com.xiaomi.micolauncher;

import android.content.Context;
import com.xiaomi.micolauncher.skills.update.RomUpdateAdapter;

/* loaded from: classes3.dex */
public class UserBehaviorProxy {
    public static void setUserBehavior(Context context, String str) {
        setUserBehavior(context, str, false);
    }

    public static void setUserBehavior(Context context, String str, boolean z) {
        if (RomUpdateAdapter.getInstance() != null) {
            RomUpdateAdapter.getInstance().updateLastActiveTimestamp(str);
        }
    }
}
