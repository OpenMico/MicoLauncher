package com.xiaomi.smarthome.ui.widgets;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class UserGuideIndicatorViewHolder extends RecyclerView.ViewHolder {
    private final ImageView a;

    public UserGuideIndicatorViewHolder(@NonNull View view) {
        super(view);
        this.a = (ImageView) view.findViewById(R.id.userGuideIndicator);
    }

    public void bind(boolean z) {
        if (this.a.isSelected() != z) {
            this.a.setSelected(z);
            ViewGroup.LayoutParams layoutParams = this.a.getLayoutParams();
            if (z) {
                layoutParams.width = this.a.getResources().getDimensionPixelOffset(R.dimen.user_guide_indicator_width_selected);
            } else {
                layoutParams.width = this.a.getResources().getDimensionPixelOffset(R.dimen.user_guide_indicator_width_unselected);
            }
            layoutParams.height = this.a.getResources().getDimensionPixelOffset(R.dimen.user_guide_indicator_height);
            this.a.setLayoutParams(layoutParams);
        }
    }
}
