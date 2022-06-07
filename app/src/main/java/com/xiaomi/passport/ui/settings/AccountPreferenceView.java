package com.xiaomi.passport.ui.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.xiaomi.passport.ui.R;

/* loaded from: classes4.dex */
public class AccountPreferenceView extends LinearLayout {
    private ImageView mImageView;
    private View mRightArrowView;
    private TextView mValueView;

    public AccountPreferenceView(Context context) {
        super(context);
        initLayout(context, null);
    }

    public AccountPreferenceView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initLayout(context, attributeSet);
    }

    public AccountPreferenceView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initLayout(context, attributeSet);
    }

    private void initLayout(Context context, AttributeSet attributeSet) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.account_preference_item, this);
        TextView textView = (TextView) inflate.findViewById(16908310);
        this.mValueView = (TextView) inflate.findViewById(R.id.value_right);
        this.mImageView = (ImageView) inflate.findViewById(R.id.image_right);
        this.mRightArrowView = inflate.findViewById(R.id.arrow_right);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AccountPreferenceView);
            String string = obtainStyledAttributes.getString(R.styleable.AccountPreferenceView_title);
            if (!TextUtils.isEmpty(string)) {
                textView.setText(string);
            }
            obtainStyledAttributes.recycle();
        }
    }

    public void setValue(String str) {
        this.mValueView.setVisibility(!TextUtils.isEmpty(str) ? 0 : 8);
        this.mValueView.setText(str);
    }

    public void setRightArrowVisible(boolean z) {
        this.mRightArrowView.setVisibility(z ? 0 : 4);
    }

    public void setImageBitmap(Bitmap bitmap) {
        this.mImageView.setVisibility(bitmap != null ? 0 : 8);
        this.mImageView.setImageBitmap(bitmap);
    }

    public void setValue(int i) {
        setValue(getContext().getString(i));
    }

    public CharSequence getValue() {
        return this.mValueView.getText();
    }
}
