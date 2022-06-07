package com.xiaomi.smarthome.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.entity.MicoMediaData;
import com.xiaomi.smarthome.ui.widgets.MicoMediaViewHolder;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MediaListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final LayoutInflater a;
    private a b;
    private MicoMediaData c;
    private List<MicoMediaData> d = new ArrayList();
    private int e;

    /* loaded from: classes4.dex */
    interface a {
    }

    public MediaListAdapter(Context context, MicoMediaData micoMediaData) {
        this.c = micoMediaData;
        this.a = LayoutInflater.from(context);
    }

    public void setMediaSelectListener(a aVar) {
        this.b = aVar;
    }

    public void setPlayState(int i) {
        this.e = i;
        notifyDataSetChanged();
    }

    public void setMicoMediaData(MicoMediaData micoMediaData) {
        this.c = micoMediaData;
        notifyDataSetChanged();
    }

    public void setRelayMediaData(List<MicoMediaData> list) {
        if (list != null) {
            this.d = list;
            notifyDataSetChanged();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        MicoMediaViewHolder micoMediaViewHolder = new MicoMediaViewHolder(this.a.inflate(R.layout.mico_item_miot_media_player, viewGroup, false));
        micoMediaViewHolder.initInMain();
        return micoMediaViewHolder;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @SuppressLint({"CheckResult"})
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MicoMediaViewHolder micoMediaViewHolder = (MicoMediaViewHolder) viewHolder;
        micoMediaViewHolder.bind(a(i), 2);
        micoMediaViewHolder.refreshPlayState(this.e);
    }

    private MicoMediaData a(int i) {
        if (this.d.isEmpty()) {
            return this.c;
        }
        return this.d.get(i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.d.isEmpty()) {
            return this.c == null ? 0 : 1;
        }
        return this.d.size();
    }
}
