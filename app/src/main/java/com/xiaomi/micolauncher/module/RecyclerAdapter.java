package com.xiaomi.micolauncher.module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/* loaded from: classes3.dex */
public class RecyclerAdapter extends RecyclerView.Adapter<a> {
    private Context a;
    private List<Integer> b;

    /* JADX INFO: Access modifiers changed from: package-private */
    public RecyclerAdapter(Context context, List<Integer> list) {
        this.a = context;
        this.b = list;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public a onCreateViewHolder(@NotNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(this.a).inflate(R.layout.item_gallery, viewGroup, false));
    }

    public void onBindViewHolder(@NotNull a aVar, int i) {
        TextView textView = aVar.a;
        textView.setText(i + "");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.b.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public static class a extends RecyclerView.ViewHolder {
        final TextView a;

        a(View view) {
            super(view);
            this.a = (TextView) view;
        }
    }
}
