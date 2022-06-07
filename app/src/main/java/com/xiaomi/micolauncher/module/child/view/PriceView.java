package com.xiaomi.micolauncher.module.child.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.xiaomi.mico.base.utils.ImageUtils;
import com.xiaomi.micolauncher.R;
import java.math.BigDecimal;

/* loaded from: classes3.dex */
public class PriceView extends LinearLayout {
    private float a;
    private int b;
    private int c;
    private Context d;

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PriceView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setOrientation(0);
        setGravity(1);
        this.d = context;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PriceView);
        this.a = obtainStyledAttributes.getFloat(0, 0.0f);
        this.b = obtainStyledAttributes.getColor(1, ContextCompat.getColor(context, R.color.white));
        this.c = obtainStyledAttributes.getInt(2, 1);
        obtainStyledAttributes.recycle();
        a();
    }

    void a() {
        removeAllViews();
        String plainString = new BigDecimal(String.valueOf(this.a)).stripTrailingZeros().toPlainString();
        String str = this.c == 1 ? "" : "_b";
        Pair<Integer, Integer> priceParams = PriceConfig.getPriceParams(getResources(), PriceConfig.CHAR_SIGN_MONEY.charValue(), this.c);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(((Integer) priceParams.first).intValue(), ((Integer) priceParams.second).intValue());
        ImageView imageView = new ImageView(this.d);
        imageView.setLayoutParams(layoutParams);
        imageView.setImageResource(this.c == 1 ? R.drawable.child_vip_money : R.drawable.child_vip_money_b);
        imageView.setColorFilter(this.b);
        addView(imageView);
        char[] charArray = plainString.toCharArray();
        for (char c : charArray) {
            if (c == '.') {
                c = 'd';
            }
            Pair<Integer, Integer> priceParams2 = PriceConfig.getPriceParams(getResources(), c, this.c);
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(((Integer) priceParams2.first).intValue(), ((Integer) priceParams2.second).intValue());
            ImageView imageView2 = new ImageView(this.d);
            imageView2.setLayoutParams(layoutParams2);
            imageView2.setImageResource(ImageUtils.getDrawableResId(this.d, "child_vip_" + c + str));
            imageView2.setColorFilter(this.b);
            addView(imageView2);
        }
    }

    public void setPrice(float f) {
        this.a = f;
        a();
    }

    public void setPriceTextSize(int i) {
        this.c = i;
    }

    public void setPriceTextColor(int i) {
        this.b = i;
    }
}
