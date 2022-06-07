package com.xiaomi.micolauncher.module.child.childvideo.holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.child.childvideo.ChildVideoGroupListActivity;

/* loaded from: classes3.dex */
public class ChildVideoAgeHolder extends BaseChildVideoHolder {
    ImageView a;
    ImageView b;
    ImageView c;
    ImageView d;

    public ChildVideoAgeHolder(Context context, View view) {
        super(context, view);
        this.a = (ImageView) view.findViewById(R.id.story_age_02);
        this.b = (ImageView) view.findViewById(R.id.story_age_34);
        this.c = (ImageView) view.findViewById(R.id.story_age_56);
        this.d = (ImageView) view.findViewById(R.id.story_age_712);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoAgeHolder$3nURzPKjCkUUaGsrg617eNlB820
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoAgeHolder.this.d(view2);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoAgeHolder$l5AlnlPFHINMmLWy5lp88ZJYx2g
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoAgeHolder.this.c(view2);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoAgeHolder$ZP9n8mzLIN-f3Zioda2Hs_4M-Ss
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoAgeHolder.this.b(view2);
            }
        });
        this.d.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childvideo.holder.-$$Lambda$ChildVideoAgeHolder$Y72VDLoTUYt11c9aQqx0hm68GyM
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ChildVideoAgeHolder.this.a(view2);
            }
        });
        this.a.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.b.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.c.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        this.d.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void d(View view) {
        a(1784, R.string.video_0_2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        a(1785, R.string.video_3_4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        a(1786, R.string.video_5_6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a(1787, R.string.video_7_12);
    }

    private void a(int i, int i2) {
        ChildVideoGroupListActivity.openChildVideoGroupListActivity(this.itemView.getContext(), i, true);
    }
}
