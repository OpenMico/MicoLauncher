package com.xiaomi.smarthome.ui.widgets;

import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class DeviceStatusIndicatorViewHolder extends RecyclerView.ViewHolder {
    private final ImageView a;

    public DeviceStatusIndicatorViewHolder(@NonNull View view) {
        super(view);
        view.setClickable(false);
        view.setEnabled(false);
        this.a = (ImageView) view.findViewById(R.id.status_indicator);
    }

    public void bind(boolean z) {
        this.a.setImageResource(z ? R.drawable.ic_os_status_open_indicator : R.drawable.ic_os_status_close_indicator);
    }
}
