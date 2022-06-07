package com.xiaomi.micolauncher.module.homepage.viewholder;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaomi.mico.base.ui.RxViewHelp;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.ui.MicoAnimConfigurator4TabAndSwitch;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.AnimLifecycleManager;
import com.xiaomi.micolauncher.module.homepage.bean.CategoryTab;
import com.xiaomi.micolauncher.module.homepage.bean.VideoTabData;
import com.xiaomi.micolauncher.module.video.childmode.ChildModeManager;
import io.reactivex.functions.Consumer;

/* loaded from: classes3.dex */
public class CategoryViewHolder extends RecyclerView.ViewHolder {
    private final TextView a;
    private Object b;
    private int c;
    private TabClickListener d;

    /* loaded from: classes3.dex */
    public interface TabClickListener {
        void onTabClick(int i, Object obj);
    }

    @SuppressLint({"CheckResult"})
    public CategoryViewHolder(@NonNull View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.category_tv);
        AnimLifecycleManager.repeatOnAttach(view, MicoAnimConfigurator4TabAndSwitch.get());
        RxViewHelp.debounceClicks(view, 500L).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.module.homepage.viewholder.-$$Lambda$CategoryViewHolder$yzS7PeFcIvkXHJwlabi_TihpsCQ
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                CategoryViewHolder.this.a(obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Object obj) throws Exception {
        TabClickListener tabClickListener = this.d;
        if (tabClickListener != null) {
            tabClickListener.onTabClick(this.c, this.b);
        }
    }

    public void setTabClickListener(TabClickListener tabClickListener) {
        this.d = tabClickListener;
    }

    public void bindData(int i, boolean z, Object obj) {
        this.c = i;
        this.b = obj;
        if (obj instanceof VideoTabData) {
            this.a.setText(((VideoTabData) obj).getDesc());
            a(this.a, z);
            return;
        }
        this.a.setText(((CategoryTab) obj).getAppTabName());
        if (ChildModeManager.getManager().isChildMode()) {
            this.a.setTextSize(UiUtils.getSize(this.itemView.getContext(), z ? R.dimen.category_select_size : R.dimen.category_unselect_size));
            this.a.setTextColor(this.itemView.getContext().getColor(R.color.white));
            return;
        }
        a(this.a, z);
    }

    private void a(TextView textView, boolean z) {
        textView.setTextColor(this.itemView.getContext().getColor(z ? R.color.tab_title_focused_color : R.color.tab_title_color));
        UiUtils.setAppTextStyle(textView, z ? TypefaceUtil.FONT_WEIGHT_W600 : TypefaceUtil.FONT_WEIGHT_W400, 0);
    }
}
