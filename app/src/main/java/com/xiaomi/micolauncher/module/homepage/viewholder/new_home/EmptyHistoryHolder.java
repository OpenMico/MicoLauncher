package com.xiaomi.micolauncher.module.homepage.viewholder.new_home;

import android.view.View;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.utils.UiUtils;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;

/* loaded from: classes3.dex */
public class EmptyHistoryHolder extends BaseViewHolder {
    TextView a;

    public EmptyHistoryHolder(View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.empty_view);
        this.a.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.img_video_empty, 0, 0);
        this.a.setCompoundDrawablePadding(UiUtils.getSize(view.getContext(), R.dimen.no_history_drawable_padding));
    }

    public void update(String str) {
        if ("HISTORY".equals(str)) {
            this.a.setText(R.string.no_video_history_text);
        } else {
            this.a.setText(R.string.no_video);
        }
    }
}
