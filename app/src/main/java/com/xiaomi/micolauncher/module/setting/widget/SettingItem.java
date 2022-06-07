package com.xiaomi.micolauncher.module.setting.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.xiaomi.micolauncher.R;

/* loaded from: classes3.dex */
public class SettingItem extends RelativeLayout {
    TextView a;
    TextView b;
    TextView c;
    View d;
    ImageView e;
    TextView f;
    View g;
    CheckBox h;
    private CompoundButton.OnCheckedChangeListener i;
    private boolean j;
    private boolean k;
    private boolean l;
    private int m;

    public SettingItem(Context context) {
        super(context);
        a(context, null);
    }

    public SettingItem(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public SettingItem(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        LayoutInflater.from(context).inflate(R.layout.view_system_setting_item, this);
        this.g = findViewById(R.id.setting_item);
        this.a = (TextView) findViewById(R.id.setting_item_name);
        this.b = (TextView) findViewById(R.id.setting_item_desc);
        this.c = (TextView) findViewById(R.id.setting_item_info);
        this.d = findViewById(R.id.divider_view);
        this.e = (ImageView) findViewById(R.id.setting_item_right_arrow);
        this.f = (TextView) findViewById(R.id.setting_item_bubble_count);
        this.h = (CheckBox) findViewById(R.id.setting_item_switcher);
        int i = 4;
        int i2 = 0;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SettingItem);
            this.m = obtainStyledAttributes.getResourceId(4, 0);
            this.j = obtainStyledAttributes.getBoolean(2, true);
            this.k = obtainStyledAttributes.getBoolean(3, true);
            this.l = obtainStyledAttributes.getBoolean(1, false);
            obtainStyledAttributes.recycle();
        }
        this.d.setVisibility(this.j ? 0 : 4);
        ImageView imageView = this.e;
        if (this.k) {
            i = 0;
        }
        imageView.setVisibility(i);
        CheckBox checkBox = this.h;
        if (!this.l) {
            i2 = 8;
        }
        checkBox.setVisibility(i2);
        if (this.l) {
            this.e.setVisibility(8);
        }
        int i3 = this.m;
        if (i3 != 0) {
            this.a.setText(i3);
        }
    }

    public void setName(CharSequence charSequence) {
        this.a.setText(charSequence);
    }

    public void setName(int i) {
        this.a.setText(i);
    }

    public void setDescription(CharSequence charSequence) {
        this.b.setText(charSequence);
        this.b.setVisibility(0);
    }

    public void setDescription(int i) {
        this.b.setText(i);
        this.b.setVisibility(0);
    }

    public void setDescriptionTextColor(int i) {
        this.b.setTextColor(i);
    }

    public void setItemInfo(CharSequence charSequence) {
        this.c.setText(charSequence);
        this.c.setVisibility(0);
    }

    public void setItemInfo(int i) {
        this.c.setText(i);
        this.c.setVisibility(0);
    }

    public ImageView getRightArrow() {
        return this.e;
    }

    public TextView getBubbleCountView() {
        return this.f;
    }

    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        this.i = onCheckedChangeListener;
        this.h.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    public void setSwitcherChecked(boolean z, boolean z2) {
        if (!z2) {
            this.h.setOnCheckedChangeListener(null);
            this.h.setChecked(z);
            this.h.setOnCheckedChangeListener(this.i);
            return;
        }
        this.h.setChecked(z);
    }

    public boolean isChecked() {
        return this.h.isChecked();
    }

    public void setEnable(boolean z) {
        this.h.setEnabled(z);
    }
}
