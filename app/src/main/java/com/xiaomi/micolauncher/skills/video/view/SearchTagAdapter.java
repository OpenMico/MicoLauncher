package com.xiaomi.micolauncher.skills.video.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.ai.api.Education;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import java.util.List;

/* loaded from: classes3.dex */
public class SearchTagAdapter extends RecyclerView.Adapter<a> {
    private List<Education.EduSearchTag> a;
    private Context b;
    private int c = 0;
    private OnFilterItemClickListener d;
    private int e;
    private int f;
    private int g;
    private int h;
    private TextView i;

    /* loaded from: classes3.dex */
    public interface OnFilterItemClickListener {
        void onFilterClick(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public SearchTagAdapter(Context context) {
        this.b = context;
        this.e = context.getResources().getDimensionPixelOffset(R.dimen.title_2);
        this.f = context.getResources().getDimensionPixelOffset(R.dimen.footnote);
        this.g = ContextCompat.getColor(context, R.color.light_system_primary);
        this.h = ContextCompat.getColor(context, R.color.light_system_secondary);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(this.b).inflate(R.layout.item_search_tag, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull a aVar, int i) {
        if (ContainerUtil.hasData(this.a) && this.a.get(i) != null) {
            aVar.a.setText(this.a.get(i).getText());
            boolean z = this.c == i;
            aVar.a.setTextSize(z ? this.e : this.f);
            aVar.a.setTextColor(z ? this.g : this.h);
            if (this.i == null && z) {
                this.i = aVar.a;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<Education.EduSearchTag> list) {
        this.a = list;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                this.c = i;
            }
        }
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a;

        a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.name_tv);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (SearchTagAdapter.this.c != getLayoutPosition() && getLayoutPosition() >= 0 && SearchTagAdapter.this.i != view) {
                SearchTagAdapter.this.c = getLayoutPosition();
                if (SearchTagAdapter.this.d != null) {
                    SearchTagAdapter.this.d.onFilterClick(getLayoutPosition());
                }
                if (SearchTagAdapter.this.i != null) {
                    SearchTagAdapter.this.i.setTextSize(SearchTagAdapter.this.f);
                    SearchTagAdapter.this.i.setTextColor(SearchTagAdapter.this.h);
                }
                SearchTagAdapter.this.i = (TextView) view;
                SearchTagAdapter.this.i.setTextSize(SearchTagAdapter.this.e);
                SearchTagAdapter.this.i.setTextColor(SearchTagAdapter.this.g);
            }
        }
    }

    public void setDefaultPosition() {
        this.c = 0;
        this.i = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(OnFilterItemClickListener onFilterItemClickListener) {
        this.d = onFilterItemClickListener;
    }
}
