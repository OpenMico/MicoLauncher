package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import java.util.GregorianCalendar;
import java.util.Locale;

/* loaded from: classes3.dex */
public class MusicPatchWallTimeView extends RelativeLayout {
    private Context a;
    private TextView b;
    private TextView c;

    public MusicPatchWallTimeView(Context context) {
        this(context, null);
    }

    public MusicPatchWallTimeView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallTimeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = context;
        a();
    }

    private void a() {
        View.inflate(this.a, R.layout.view_music_patch_wall_time, this);
        this.b = (TextView) findViewById(R.id.time_month_tv);
        this.c = (TextView) findViewById(R.id.time_day_tv);
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        this.b.setText(String.format(Locale.getDefault(), "%02d", Integer.valueOf(gregorianCalendar.get(2) + 1)));
        this.c.setText(String.format(Locale.getDefault(), "%02d", Integer.valueOf(gregorianCalendar.get(5))));
    }
}
