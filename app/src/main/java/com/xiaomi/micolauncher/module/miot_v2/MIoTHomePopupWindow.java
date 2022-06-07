package com.xiaomi.micolauncher.module.miot_v2;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.miot.support.core.MiotHome;
import java.util.List;

/* loaded from: classes3.dex */
public class MIoTHomePopupWindow {
    private Context a;
    private MIoTHomeAdapter b;
    private List<MiotHome> c;
    private PopupWindow d;

    /* loaded from: classes3.dex */
    public interface HomeSelectListener {
        void onHomeChanged(MiotHome miotHome, List<MiotHome> list);
    }

    public void setHomeSelectListener(HomeSelectListener homeSelectListener) {
        MIoTHomeAdapter mIoTHomeAdapter = this.b;
        if (mIoTHomeAdapter != null) {
            mIoTHomeAdapter.setListener(homeSelectListener);
            return;
        }
        throw new IllegalArgumentException("HomeAdapter should be init before setHomeSelectListener");
    }

    public void setPopWindowWidth(int i) {
        PopupWindow popupWindow = this.d;
        if (popupWindow != null) {
            popupWindow.setWidth(i);
            this.d.update();
        }
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        PopupWindow popupWindow = this.d;
        if (popupWindow != null) {
            popupWindow.setOnDismissListener(onDismissListener);
        }
    }

    public void setHomeList(List<MiotHome> list) {
        this.c = list;
        MIoTHomeAdapter mIoTHomeAdapter = this.b;
        if (mIoTHomeAdapter != null) {
            mIoTHomeAdapter.setHomeList(list);
            this.b.notifyDataSetChanged();
            PopupWindow popupWindow = this.d;
            if (popupWindow != null) {
                popupWindow.setHeight((list.size() * 55) + 93 + ((list.size() - 1) * 33));
                return;
            }
            return;
        }
        throw new IllegalArgumentException("HomeAdapter should be init before setHomeList");
    }

    public MIoTHomePopupWindow(Context context) {
        this.a = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.view_miot_home_list, (ViewGroup) null);
        this.d = new PopupWindow(inflate);
        this.d.setFocusable(true);
        this.d.setOutsideTouchable(true);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.b = new MIoTHomeAdapter(context);
        recyclerView.setAdapter(this.b);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() { // from class: com.xiaomi.micolauncher.module.miot_v2.MIoTHomePopupWindow.1
            @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView2, RecyclerView.State state) {
                if (recyclerView2.getChildLayoutPosition(view) > 0) {
                    rect.top = 33;
                }
            }
        });
    }

    public void show(View view, int i, int i2) {
        List<MiotHome> list = this.c;
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Home List should not be empty");
        }
        this.d.showAsDropDown(view, i, -i2);
    }

    public boolean isShowing() {
        PopupWindow popupWindow = this.d;
        return popupWindow != null && popupWindow.isShowing();
    }

    public void dismiss() {
        PopupWindow popupWindow = this.d;
        if (popupWindow != null) {
            popupWindow.setFocusable(false);
            this.d.dismiss();
        }
    }
}
