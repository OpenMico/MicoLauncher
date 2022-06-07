package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.GlideUtils;
import com.xiaomi.micolauncher.common.rxhelpers.MicoSchedulers;
import com.xiaomi.micolauncher.common.widget.TitleBar;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class TitleBar extends RelativeLayout {
    ImageView mLeftIcon;
    ImageView mRightIcon;
    TextView mTitle;

    /* loaded from: classes3.dex */
    public interface OnLeftIconClickListener {
        void onLeftIconClick();
    }

    /* loaded from: classes3.dex */
    public interface OnRightIconClickListener {
        void onRightIconClick();
    }

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, attributeSet);
    }

    public TitleBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context, attributeSet);
    }

    private void init(Context context, AttributeSet attributeSet) {
        int i;
        boolean z;
        int i2;
        int i3;
        String str;
        LayoutInflater.from(context).inflate(R.layout.widget_title_bar, this);
        this.mTitle = (TextView) findViewById(R.id.title_bar_title);
        this.mLeftIcon = (ImageView) findViewById(R.id.title_bar_left_icon);
        this.mRightIcon = (ImageView) findViewById(R.id.title_bar_right_icon);
        int color = getResources().getColor(R.color.common_textcolor_1);
        boolean z2 = true;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TitleBar);
            i = obtainStyledAttributes.getResourceId(0, 0);
            str = obtainStyledAttributes.getString(5);
            color = obtainStyledAttributes.getColor(7, color);
            z2 = obtainStyledAttributes.getBoolean(1, true);
            i3 = obtainStyledAttributes.getResourceId(2, R.drawable.icon_back);
            i2 = obtainStyledAttributes.getResourceId(4, -1);
            obtainStyledAttributes.getString(3);
            z = obtainStyledAttributes.getBoolean(6, false);
            obtainStyledAttributes.recycle();
        } else {
            str = null;
            i3 = -1;
            i2 = -1;
            i = 0;
            z = false;
        }
        setBackgroundResource(i);
        setTitle(str);
        this.mTitle.setTextColor(color);
        if (z2 && i3 == -1) {
            i3 = R.drawable.icon_titilebar_back;
        }
        if (i3 != -1) {
            setLeftIcon(i3);
        } else {
            showLeftIcon(false);
        }
        if (i2 != -1) {
            setRightIcon(i2);
        } else {
            showRightIcon(false);
        }
        showLeftIcon(false);
        if (z) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(this.mTitle.getLayoutParams());
            layoutParams.addRule(5);
            layoutParams.setMargins(60, 0, 0, 0);
            this.mTitle.setLayoutParams(layoutParams);
        }
    }

    public TitleBar setTitle(CharSequence charSequence) {
        this.mTitle.setText(charSequence);
        return this;
    }

    public TitleBar setTitle(int i) {
        this.mTitle.setText(i);
        return this;
    }

    public TextView getTitleView() {
        return this.mTitle;
    }

    public ImageView getLeftIcon() {
        return this.mLeftIcon;
    }

    public TitleBar showLeftIcon(boolean z) {
        this.mLeftIcon.setVisibility(z ? 0 : 4);
        return this;
    }

    private TitleBar setLeftIcon(int i) {
        showLeftIcon(true);
        GlideUtils.bindImageView(getContext(), i, this.mLeftIcon);
        return this;
    }

    public TitleBar showRightIcon(boolean z) {
        this.mRightIcon.setVisibility(z ? 0 : 4);
        return this;
    }

    public TitleBar setRightIcon(int i) {
        showRightIcon(true);
        GlideUtils.bindImageView(getContext(), i, this.mRightIcon);
        return this;
    }

    public TitleBar setOnLeftIconClickListener(final OnLeftIconClickListener onLeftIconClickListener) {
        RxView.clicks(this.mLeftIcon).throttleFirst(200L, TimeUnit.MILLISECONDS).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$TitleBar$nXgCQlN2JMpkW8nHTmo0rGIiQfo
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                TitleBar.OnLeftIconClickListener.this.onLeftIconClick();
            }
        });
        return this;
    }

    public TitleBar setOnRightIconClickListener(final OnRightIconClickListener onRightIconClickListener) {
        RxView.clicks(this.mRightIcon).throttleFirst(200L, TimeUnit.MILLISECONDS).observeOn(MicoSchedulers.mainThread()).subscribe(new Consumer() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$TitleBar$C81Zy1cI-OLl1A8gbe3Men8a3EM
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                TitleBar.OnRightIconClickListener.this.onRightIconClick();
            }
        });
        return this;
    }
}
