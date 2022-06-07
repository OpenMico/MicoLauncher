package com.xiaomi.smarthome.ui.widgets;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xiaomi.mico.base.ui.MicoAnimationTouchListener;
import com.xiaomi.mico.common.TypefaceUtil;
import com.xiaomi.micolauncher.common.view.BaseViewHolder;
import com.xiaomi.miot.support.core.MiotHome;
import com.xiaomi.smarthome.R;
import com.xiaomi.smarthome.core.miot.MicoMiotDeviceManager;

/* loaded from: classes4.dex */
public class MiotHomeViewHolder extends BaseViewHolder {
    private final TextView a;
    private final TextView b;
    private final ImageView c;

    public MiotHomeViewHolder(@NonNull View view) {
        super(view);
        this.a = (TextView) view.findViewById(R.id.home_title);
        this.b = (TextView) view.findViewById(R.id.share_mark);
        this.c = (ImageView) view.findViewById(R.id.mark);
        view.setOnTouchListener(MicoAnimationTouchListener.getInstance());
    }

    public void bindData(MiotHome miotHome, int i, int i2) {
        int i3 = 8;
        if (miotHome == null) {
            this.c.setVisibility(8);
            this.b.setVisibility(8);
            return;
        }
        boolean isCurrentHome = MicoMiotDeviceManager.getInstance().isCurrentHome(miotHome.homeId);
        String str = miotHome.homeName;
        if (TextUtils.isEmpty(str)) {
            str = this.itemView.getContext().getString(R.string.miot_home_name_empty_suffix, MicoMiotDeviceManager.getInstance().getUserId());
        }
        this.a.setText(str);
        if (isCurrentHome) {
            this.a.setTypeface(Typeface.create(TypefaceUtil.FONT_WEIGHT_W500, 0));
        }
        this.c.setVisibility(isCurrentHome ? 0 : 8);
        this.itemView.setBackgroundResource(a(isCurrentHome, i, i2));
        TextView textView = this.b;
        if (MicoMiotDeviceManager.getInstance().isSharedHome(miotHome.homeId)) {
            i3 = 0;
        }
        textView.setVisibility(i3);
    }

    private int a(boolean z, int i, int i2) {
        if (!z) {
            return 17170445;
        }
        if (1 == i2) {
            return R.drawable.shape_mico_miot_home_all;
        }
        if (i == 0) {
            return R.drawable.shape_mico_miot_home_top;
        }
        if (i == i2 - 1) {
            return R.drawable.shape_mico_miot_home_bottom;
        }
        return R.drawable.shape_mico_miot_home_middle;
    }
}
