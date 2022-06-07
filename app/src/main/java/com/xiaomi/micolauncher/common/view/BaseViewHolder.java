package com.xiaomi.micolauncher.common.view;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

/* loaded from: classes3.dex */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private boolean a;

    protected void initViewInMain() {
    }

    @CallSuper
    public void recycle() {
    }

    public void setData(List<Object> list) {
    }

    public BaseViewHolder(View view) {
        super(view);
    }

    public void initInMain() {
        if (!this.a) {
            initViewInMain();
            this.a = true;
        }
    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == 0;
    }

    protected void setVisibility(View view, int i) {
        view.setVisibility(i);
    }
}
