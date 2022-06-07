package com.xiaomi.smarthome.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.widgets.UserGuideImageViewHolder;

/* loaded from: classes4.dex */
public class UserGuideImageAdapter extends RecyclerView.Adapter<UserGuideImageViewHolder> {
    private final LayoutInflater a;
    private final int[] b;

    public UserGuideImageAdapter(Context context, int[] iArr) {
        this.a = LayoutInflater.from(context);
        this.b = iArr;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public UserGuideImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new UserGuideImageViewHolder(this.a.inflate(R.layout.user_guide_image, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull UserGuideImageViewHolder userGuideImageViewHolder, int i) {
        userGuideImageViewHolder.bind(this.b[i]);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.length;
    }
}
