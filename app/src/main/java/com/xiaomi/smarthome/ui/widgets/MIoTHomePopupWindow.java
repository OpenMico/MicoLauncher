package com.xiaomi.smarthome.ui.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.badge.BadgeDrawable;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.ui.adapter.MiotHomeAdapter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class MIoTHomePopupWindow {
    private final Context a;
    private final MiotHomeAdapter b;
    private final List<MiotHome> c = new ArrayList();
    private final PopupWindow d;

    public MIoTHomePopupWindow(Context context) {
        this.a = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.popup_miot_home_list, (ViewGroup) null);
        this.d = new PopupWindow(inflate);
        this.d.setFocusable(true);
        this.d.setOutsideTouchable(true);
        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.b = new MiotHomeAdapter(context);
        recyclerView.setAdapter(this.b);
    }

    public void show(View view, int i, int i2) {
        if (!this.c.isEmpty()) {
            this.d.setClippingEnabled(false);
            this.d.showAtLocation(view, BadgeDrawable.TOP_START, i, i2);
            return;
        }
        throw new IllegalArgumentException("Home List should not be empty");
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

    public void setHomeSelectListener(HomeSelectListener homeSelectListener) {
        MiotHomeAdapter miotHomeAdapter = this.b;
        if (miotHomeAdapter != null) {
            miotHomeAdapter.setHomeSelectListener(homeSelectListener);
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
        this.c.clear();
        this.c.addAll(list);
        MiotHomeAdapter miotHomeAdapter = this.b;
        if (miotHomeAdapter != null) {
            miotHomeAdapter.updateDataList(list);
            this.b.notifyDataSetChanged();
            if (this.d != null) {
                int dimensionPixelSize = this.a.getResources().getDimensionPixelSize(R.dimen.mico_smart_home_home_title_height);
                int size = list.size();
                if (size > 5) {
                    size = 5;
                }
                this.d.setHeight(size * dimensionPixelSize);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("HomeAdapter should be init before setHomeList");
    }
}
