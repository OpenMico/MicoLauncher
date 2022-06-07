package com.xiaomi.micolauncher.module.child.course;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.api.model.CourseTab;
import java.util.List;

/* loaded from: classes3.dex */
public class CourseTabAdapter extends RecyclerView.Adapter<a> {
    private List<CourseTab> a;
    private Context b;
    private int c;
    private OnCourseItemClickListener d;

    /* loaded from: classes3.dex */
    public interface OnCourseItemClickListener {
        void onCourseClick(int i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public CourseTabAdapter(Context context, int i) {
        this.b = context;
        this.c = i;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(LayoutInflater.from(this.b).inflate(R.layout.item_course_tab, viewGroup, false));
    }

    public void onBindViewHolder(@NonNull a aVar, int i) {
        if (ContainerUtil.hasData(this.a) && this.a.get(i) != null) {
            aVar.a.setText(this.a.get(i).getName());
            aVar.a.setSelected(this.c == i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ContainerUtil.getSize(this.a);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(List<CourseTab> list) {
        this.a = list;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class a extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView a;

        a(View view) {
            super(view);
            this.a = (TextView) view.findViewById(R.id.course_tab_tv);
            view.setOnClickListener(this);
            view.setOnTouchListener(MicoAnimationTouchListener.getInstance());
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (CourseTabAdapter.this.c != getLayoutPosition() && getLayoutPosition() >= 0) {
                CourseTabAdapter courseTabAdapter = CourseTabAdapter.this;
                courseTabAdapter.notifyItemChanged(courseTabAdapter.c);
                CourseTabAdapter.this.c = getLayoutPosition();
                if (CourseTabAdapter.this.d != null) {
                    CourseTabAdapter.this.d.onCourseClick(getLayoutPosition());
                }
                CourseTabAdapter courseTabAdapter2 = CourseTabAdapter.this;
                courseTabAdapter2.notifyItemChanged(courseTabAdapter2.c);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(OnCourseItemClickListener onCourseItemClickListener) {
        this.d = onCourseItemClickListener;
    }
}
