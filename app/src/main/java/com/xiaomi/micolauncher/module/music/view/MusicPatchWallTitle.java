package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class MusicPatchWallTitle extends LinearLayout {
    private Context a;
    private TextView b;

    public MusicPatchWallTitle(Context context) {
        this(context, null);
    }

    public MusicPatchWallTitle(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallTitle(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = context;
        a();
    }

    private void a() {
        View.inflate(this.a, R.layout.view_music_patch_wall_title, this);
        this.b = (TextView) findViewById(R.id.title_tv);
    }

    public void setupView(String str) {
        this.b.setText(str);
    }

    public void setupView(int i) {
        this.b.setText(i);
    }
}
