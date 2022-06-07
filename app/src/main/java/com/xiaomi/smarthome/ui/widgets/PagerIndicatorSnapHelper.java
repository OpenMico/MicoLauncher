package com.xiaomi.smarthome.ui.widgets;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.smarthome.ui.adapter.UserGuideIndicatorAdapter;
import java.util.List;

/* loaded from: classes4.dex */
public class PagerIndicatorSnapHelper extends PagerSnapHelper {
    private RecyclerView b;
    private List<Boolean> c;
    private int d;

    public void attachToIndicators(RecyclerView recyclerView, List<Boolean> list) {
        this.b = recyclerView;
        this.c = list;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).booleanValue()) {
                this.d = i;
            }
        }
    }

    @Override // androidx.recyclerview.widget.SnapHelper, androidx.recyclerview.widget.RecyclerView.OnFlingListener
    public boolean onFling(int i, int i2) {
        boolean onFling = super.onFling(i, i2);
        if (onFling) {
            UserGuideIndicatorAdapter userGuideIndicatorAdapter = (UserGuideIndicatorAdapter) this.b.getAdapter();
            int itemCount = userGuideIndicatorAdapter.getItemCount();
            if (i > 0) {
                int i3 = this.d;
                if (i3 + 1 >= itemCount) {
                    return true;
                }
                this.c.set(i3, false);
                this.d++;
                this.c.set(this.d, true);
            } else if (i < 0) {
                int i4 = this.d;
                if (i4 - 1 < 0) {
                    return true;
                }
                this.c.set(i4, false);
                this.d--;
                this.c.set(this.d, true);
            }
            userGuideIndicatorAdapter.notifyDataSetChanged();
        }
        return onFling;
    }
}
