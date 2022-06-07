package com.xiaomi.micolauncher.module.child.childvideo.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoGroupListActivity;

/* loaded from: classes3.dex */
public class ChildVideoTypeHolder extends BaseChildVideoHolder {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;

    public ChildVideoTypeHolder(Context context, View view) {
        super(context, view);
        this.a = (ImageView) view.findViewById(R.id.story_video_type_english);
        this.b = (ImageView) view.findViewById(R.id.story_video_type_kindergarten);
        this.c = (ImageView) view.findViewById(R.id.story_video_type_free);
        this.d = (ImageView) view.findViewById(R.id.story_video_type_cartoon);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoTypeHolder$GHNnlpMrtjigWyHGb0wDiSekFE0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoTypeHolder.this.d(view2);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoTypeHolder$H8Q6pWLxDQ_DG-d4pWJhXGb_2Ac
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoTypeHolder.this.c(view2);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoTypeHolder$SKiYYEa4panJll2iR3D6VqkNDOs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoTypeHolder.this.b(view2);
            }
        });
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoTypeHolder$HuyZszDkO0c6V_0l8g4HPAn_J9g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoTypeHolder.this.a(view2);
            }
        });
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(View view) {
        a(1736, R.string.video_english);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        a(1783, R.string.video_kindergarten);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        a(1780, R.string.video_free);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a(1782, R.string.video_type_cartoon);
    }

    private void a(int i, int i2) {
        ChildVideoGroupListActivity.openChildVideoGroupListActivity(this.itemView.getContext(), i, true);
    }
}
