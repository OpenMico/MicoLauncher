package com.xiaomi.micolauncher.module.station.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.module.station.StationCategoryListActivity;
import com.xiaomi.micolauncher.module.station.StationGroupListActivity;

/* loaded from: classes3.dex */
public class StationPatchWallHeader extends LinearLayout {
    ImageView a;
    ImageView b;
    ImageView c;

    public StationPatchWallHeader(Context context) {
        this(context, null);
    }

    public StationPatchWallHeader(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StationPatchWallHeader(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        View.inflate(getContext(), R.layout.view_station_patch_wall_header, this);
        b();
    }

    private void b() {
        this.a = (ImageView) findViewById(R.id.recent_list);
        this.b = (ImageView) findViewById(R.id.collection_list);
        this.c = (ImageView) findViewById(R.id.all_list);
        this.a.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.station.ui.-$$Lambda$StationPatchWallHeader$okfjC984X7ZOCM86P3end0u7nxQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StationPatchWallHeader.this.c(view);
            }
        });
        this.b.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.station.ui.-$$Lambda$StationPatchWallHeader$E3aWV9f3QAvDrmq7oCPLMlAGNAA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StationPatchWallHeader.this.b(view);
            }
        });
        this.c.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.station.ui.-$$Lambda$StationPatchWallHeader$5oVwG0kdTmdWM026zxkYDMvMfuo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                StationPatchWallHeader.this.a(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void c(View view) {
        StationCategoryListActivity.openStationWithTitleRecent(getContext(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void b(View view) {
        StationCategoryListActivity.openStationWithTitleCollection(getContext(), false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(View view) {
        StationGroupListActivity.openStationGroupListActivity(getContext(), 0L);
    }
}
