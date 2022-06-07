package com.xiaomi.micolauncher.module.homepage.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.childsong.ChildSongGroupListActivity;

/* loaded from: classes3.dex */
public class ChildSongCategoryView extends LinearLayout implements View.OnClickListener {
    public static final int CATEGORY_ID_ALL = 3200;
    public static final String CATEGORY_NAME_ALL = "全部";

    public ChildSongCategoryView(Context context) {
        this(context, null);
    }

    public ChildSongCategoryView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ChildSongCategoryView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.child_song_category_view, this);
        findViewById(R.id.iv0to2).setOnClickListener(this);
        findViewById(R.id.iv3to4).setOnClickListener(this);
        findViewById(R.id.iv5to6).setOnClickListener(this);
        findViewById(R.id.ivAll).setOnClickListener(this);
        findViewById(R.id.iv0to2).setOnTouchListener(MicoAnimationTouchListener.getInstance());
        findViewById(R.id.iv3to4).setOnTouchListener(MicoAnimationTouchListener.getInstance());
        findViewById(R.id.iv5to6).setOnTouchListener(MicoAnimationTouchListener.getInstance());
        findViewById(R.id.ivAll).setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        int i = CATEGORY_ID_ALL;
        if (id == R.id.iv0to2) {
            i = 3124;
        } else if (id == R.id.iv3to4) {
            i = 3125;
        } else if (id == R.id.iv5to6) {
            i = 3126;
        }
        ChildSongGroupListActivity.openChildSongGroupListActivity(getContext(), i);
    }
}
