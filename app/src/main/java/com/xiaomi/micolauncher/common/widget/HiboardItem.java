package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;

/* loaded from: classes3.dex */
public class HiboardItem extends LinearLayout {
    ImageView icon;
    TextView label;

    public HiboardItem(Context context) {
        super(context);
    }

    public HiboardItem(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setupView(context, attributeSet);
    }

    public HiboardItem(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setupView(context, attributeSet);
    }

    private void setupView(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.hiboard_item, this);
        this.icon = (ImageView) findViewById(R.id.icon);
        this.label = (TextView) findViewById(R.id.label);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HiboardItem);
            GlideUtils.bindImageView(context, obtainStyledAttributes.getResourceId(0, -1), this.icon);
            this.label.setText(obtainStyledAttributes.getString(1));
            obtainStyledAttributes.recycle();
        }
    }

    public void setItemTextColor(int i) {
        this.label.setTextColor(i);
    }
}
