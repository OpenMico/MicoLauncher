package com.xiaomi.micolauncher.module.multicp.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.databinding.WidgetCpsBarBinding;
import com.xiaomi.micolauncher.module.multicp.adapter.ContentProviderAdapter;
import com.xiaomi.micolauncher.skills.video.model.VideoContentProvider;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class ContentProviderBar extends ConstraintLayout {
    private WidgetCpsBarBinding a;
    private ContentProviderAdapter b;
    private int c;
    private int d;
    private final List<VideoContentProvider> e;

    /* loaded from: classes3.dex */
    public interface OnCpClickedListener {
        void onCpChanged(VideoContentProvider videoContentProvider);
    }

    public ContentProviderBar(Context context) {
        this(context, null);
    }

    public ContentProviderBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentProviderBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.c = 1;
        this.e = new ArrayList();
        a(context);
    }

    private void a(Context context) {
        this.a = (WidgetCpsBarBinding) DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.widget_cps_bar, this, true);
        if (this.b == null) {
            this.b = new ContentProviderAdapter(this.e);
        }
        final int dimensionPixelOffset = context.getResources().getDimensionPixelOffset(R.dimen.mc21dp);
        this.a.rv.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.multicp.widget.ContentProviderBar.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
                super.getItemOffsets(rect, view, recyclerView, state);
                rect.right = dimensionPixelOffset;
            }
        });
        this.a.rv.setAdapter(this.b);
        this.a.ivArrow.setOnClickListener(new View.OnClickListener() { // from class: com.xiaomi.micolauncher.module.multicp.widget.-$$Lambda$ContentProviderBar$krVNiuLupLuXrKJP-bWy1qCmDYI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                ContentProviderBar.this.a(view);
            }
        });
    }

    public /* synthetic */ void a(View view) {
        if (!UiUtils.isFastClick()) {
            int i = this.c;
            if (i < this.d) {
                this.c = i + 1;
            } else {
                this.c = i - 1;
            }
            this.a.rv.smoothScrollToPosition((this.c - 1) * 4);
            if (this.c >= this.d) {
                this.a.ivArrow.setImageResource(R.drawable.icon_cps_video_back);
            } else {
                this.a.ivArrow.setImageResource(R.drawable.icon_cps_video_more);
            }
        }
    }

    public void setOnCpClickedListener(OnCpClickedListener onCpClickedListener) {
        this.b.setOnCpClickedListener(onCpClickedListener);
    }

    public void updateData(@NonNull List<VideoContentProvider> list) {
        this.e.clear();
        this.e.addAll(list);
        this.d = (this.e.size() / 4) + 1;
        this.b.notifyDataSetChanged();
        this.a.ivArrow.setVisibility(this.d <= 1 ? 8 : 0);
    }
}
