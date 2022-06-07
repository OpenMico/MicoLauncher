package androidx.core.widget;

import android.widget.ListView;
import androidx.annotation.NonNull;

/* loaded from: classes.dex */
public class ListViewAutoScrollHelper extends AutoScrollHelper {
    private final ListView f;

    @Override // androidx.core.widget.AutoScrollHelper
    public boolean canTargetScrollHorizontally(int i) {
        return false;
    }

    public ListViewAutoScrollHelper(@NonNull ListView listView) {
        super(listView);
        this.f = listView;
    }

    @Override // androidx.core.widget.AutoScrollHelper
    public void scrollTargetBy(int i, int i2) {
        ListViewCompat.scrollListBy(this.f, i2);
    }

    @Override // androidx.core.widget.AutoScrollHelper
    public boolean canTargetScrollVertically(int i) {
        ListView listView = this.f;
        int count = listView.getCount();
        if (count == 0) {
            return false;
        }
        int childCount = listView.getChildCount();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int i2 = firstVisiblePosition + childCount;
        if (i > 0) {
            if (i2 >= count && listView.getChildAt(childCount - 1).getBottom() <= listView.getHeight()) {
                return false;
            }
        } else if (i >= 0) {
            return false;
        } else {
            if (firstVisiblePosition <= 0 && listView.getChildAt(0).getTop() >= 0) {
                return false;
            }
        }
        return true;
    }
}
