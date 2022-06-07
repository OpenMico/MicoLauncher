package com.xiaomi.micolauncher.module.multicp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.LayoutAccurateShotMultiCpBinding;
import com.xiaomi.micolauncher.module.multicp.adapter.AccurateMultiShotAdapter;
import com.xiaomi.micolauncher.module.multicp.events.CollapseEvent;
import com.xiaomi.micolauncher.skills.video.model.VideoItem;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class AccurateMultiShotView extends ConstraintLayout {
    private AccurateMultiShotAdapter a;
    private final List<VideoItem> b;

    public AccurateMultiShotView(Context context) {
        this(context, null);
    }

    public AccurateMultiShotView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AccurateMultiShotView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.b = new ArrayList();
        a(context);
    }

    private void a(Context context) {
        LayoutAccurateShotMultiCpBinding inflate = LayoutAccurateShotMultiCpBinding.inflate(LayoutInflater.from(context), this, true);
        this.a = new AccurateMultiShotAdapter(this.b);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 0, false);
        inflate.rv.setAdapter(this.a);
        inflate.rv.setLayoutManager(linearLayoutManager);
        final int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.mc42dp);
        inflate.rv.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.multicp.widget.AccurateMultiShotView.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                rect.right = dimensionPixelOffset;
            }
        });
        inflate.collapseView.setOnClickListener($$Lambda$AccurateMultiShotView$cGGFJlaCNzyW29aUx03dt14pWrI.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view) {
        if (!UiUtils.isFastClick()) {
            EventBusRegistry.getEventBus().post(new CollapseEvent(true));
        }
    }

    public void updateData(@NonNull List<VideoItem> list) {
        this.b.clear();
        this.b.addAll(list);
        this.a.notifyDataSetChanged();
    }

    @Override // android.view.View
    public boolean performClick() {
        return super.performClick();
    }
}
