package com.xiaomi.micolauncher.module.multicp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.EventBusRegistry;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.module.multicp.events.CollapseEvent;

/* loaded from: classes3.dex */
public class MoreView extends FrameLayout {
    public MoreView(@NonNull Context context) {
        this(context, null);
    }

    public MoreView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MoreView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_more, (ViewGroup) this, true);
        findViewById(R.id.collapseView).setOnClickListener($$Lambda$MoreView$ap7mbefRiGXF2q5yoglJUvmUqIU.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(View view) {
        if (!UiUtils.isFastClick()) {
            EventBusRegistry.getEventBus().post(new CollapseEvent(false));
        }
    }
}
