package com.xiaomi.micolauncher.module.child.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import java.util.List;

/* loaded from: classes3.dex */
public class CourseTabFilterAdapter extends RecyclerView.Adapter<a> {
    private List<ChildVideo.ItemsBean> a;
    private Context b;
    private int c;
    private OnFilterItemClickListener d;
    private int e;
    private int f;
    private TextView g;

    /* loaded from: classes3.dex */
    public interface OnFilterItemClickListener {
        void onFilterClick(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CourseTabFilterAdapter(Context context, int i) {
        this.b = context;
        this.c = i;
        this.e = context.getResources().getDimensionPixelOffset(R.dimen.headline_1);
        this.f = context.getResources().getDimensionPixelOffset(R.dimen.footnote);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(this.b).inflate(R.layout.item_course_filter, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull a aVar, int i) {
        if (ContainerUtil.hasData(this.a) && this.a.get(i) != null) {
            aVar.a.setText(this.a.get(i).getTitle());
            aVar.a.setTextSize(this.c == i ? this.e : this.f);
            if (this.g == null) {
                this.g = aVar.a;
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<ChildVideo.ItemsBean> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a;

        a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.filter_name_tv);
            view.setOnClickListener(this);
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (CourseTabFilterAdapter.this.c != getLayoutPosition() && getLayoutPosition() >= 0 && CourseTabFilterAdapter.this.g != view) {
                CourseTabFilterAdapter.this.c = getLayoutPosition();
                if (CourseTabFilterAdapter.this.d != null) {
                    CourseTabFilterAdapter.this.d.onFilterClick(getLayoutPosition());
                }
                if (CourseTabFilterAdapter.this.g != null) {
                    CourseTabFilterAdapter.this.g.setTextSize(CourseTabFilterAdapter.this.f);
                }
                CourseTabFilterAdapter.this.g = (TextView) view;
                CourseTabFilterAdapter.this.g.setTextSize(CourseTabFilterAdapter.this.e);
            }
        }
    }

    public void setDefaultPosition() {
        this.c = 0;
        this.g = null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(OnFilterItemClickListener onFilterItemClickListener) {
        this.d = onFilterItemClickListener;
    }
}
