package com.xiaomi.micolauncher.module.music.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class MusicPatchWallFooter extends RelativeLayout {
    private Context a;

    public MusicPatchWallFooter(Context context) {
        this(context, null);
    }

    public MusicPatchWallFooter(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MusicPatchWallFooter(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = context;
        a();
    }

    private void a() {
        View.inflate(this.a, R.layout.view_music_patch_wall_footer, this);
    }
}
