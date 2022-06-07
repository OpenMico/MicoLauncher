package com.xiaomi.micolauncher.skills.common;

import android.content.Context;
import android.graphics.Paint;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class WakeupUtil {
    public static boolean withinShowLines(Context context, String str) {
        Paint paint = new Paint();
        paint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.wakeup_asr_text_size));
        return ((int) (paint.measureText(str) + 615.0f)) / 616 <= 5;
    }
}
