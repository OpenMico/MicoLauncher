package com.xiaomi.micolauncher.module.child.childstory.holder;

import android.content.Context;
import android.view.View;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.station.StationGroupListActivity;

/* loaded from: classes3.dex */
public class StoryAgeHolder extends BaseChildStoryHolder {
    public StoryAgeHolder(Context context, View view) {
        super(context, view);
        view.findViewById(R.id.story_age_03).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryAgeHolder$O0MHMOmOo6wJdCE4hZshx5eM8Qg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryAgeHolder.this.c(view2);
            }
        });
        view.findViewById(R.id.story_age_36).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryAgeHolder$zDY4w15ZRXP5JhjNltUlUKoEZJ0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryAgeHolder.this.b(view2);
            }
        });
        view.findViewById(R.id.story_age_6).setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.child.childstory.holder.-$$Lambda$StoryAgeHolder$PRdn4J3Bed4duqkVtm7ksX_Cbmk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                StoryAgeHolder.this.a(view2);
            }
        });
        view.findViewById(R.id.story_age_03).setOnTouchListener(MicoAnimationTouchListener.getInstance());
        view.findViewById(R.id.story_age_36).setOnTouchListener(MicoAnimationTouchListener.getInstance());
        view.findViewById(R.id.story_age_6).setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        a(1008, R.string.story_0_3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        a(1009, R.string.story_3_6);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        a(1010, R.string.story_6);
    }

    private void a(int i, int i2) {
        StationGroupListActivity.openStationGroupListActivity(this.itemView.getContext(), i, true);
    }
}
