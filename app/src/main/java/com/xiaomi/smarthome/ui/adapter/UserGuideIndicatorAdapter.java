package com.xiaomi.smarthome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.UserGuideIndicatorViewHolder;
import java.util.List;

/* loaded from: classes4.dex */
public class UserGuideIndicatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater a;
    private final List<Boolean> b;

    public UserGuideIndicatorAdapter(Context context, List<Boolean> list) {
        this.a = LayoutInflater.from(context);
        this.b = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserGuideIndicatorViewHolder(this.a.inflate(R.layout.user_guide_indicator, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((UserGuideIndicatorViewHolder) viewHolder).bind(this.b.get(i).booleanValue());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.size();
    }
}
