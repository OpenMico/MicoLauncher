package com.xiaomi.micolauncher.skills.common.view;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import com.rd.utils.DensityUtils;
import com.xiaomi.micolauncher.R;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class AniTextView extends ViewGroup implements AsrTextInterface {
    private Context a;
    private int b;
    private int c;
    private CharSequence d;
    private ColorStateList e;
    private ArrayList<TextView> f;
    private long g;

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str, boolean z, boolean z2) {
    }

    public AniTextView(Context context) {
        this(context, null);
    }

    public AniTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AniTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = "";
        this.b = DensityUtils.dpToPx(36);
        this.c = 5;
        this.e = getResources().getColorStateList(R.color.white, null);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AniTextView);
        if (obtainStyledAttributes != null) {
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i2 = 0; i2 < indexCount; i2++) {
                int index = obtainStyledAttributes.getIndex(i2);
                switch (index) {
                    case 0:
                        this.c = obtainStyledAttributes.getDimensionPixelSize(index, 5);
                        break;
                    case 1:
                        this.d = obtainStyledAttributes.getText(index);
                        break;
                    case 2:
                        this.e = obtainStyledAttributes.getColorStateList(index);
                        break;
                    case 3:
                        this.b = obtainStyledAttributes.getDimensionPixelSize(index, DensityUtils.dpToPx(36));
                        break;
                }
            }
            obtainStyledAttributes.recycle();
        }
        this.a = context;
        this.f = new ArrayList<>();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int paddingLeft = i + getPaddingLeft();
        int paddingRight = i3 - getPaddingRight();
        int paddingTop = i2 + getPaddingTop();
        int paddingBottom = i4 - getPaddingBottom();
        int childCount = getChildCount();
        if (childCount != 0) {
            int i6 = 0;
            int measuredHeight = getChildAt(0).getMeasuredHeight();
            int i7 = 1;
            int i8 = 0;
            for (int i9 = 0; i9 < childCount; i9++) {
                int measuredWidth = getChildAt(i9).getMeasuredWidth();
                if (paddingLeft + measuredWidth > paddingRight) {
                    paddingLeft = i + getPaddingLeft();
                    paddingTop += this.c + measuredHeight;
                    i7++;
                    if (paddingTop + measuredHeight > paddingBottom) {
                        break;
                    }
                }
                paddingLeft += measuredWidth;
                i8 += measuredWidth;
            }
            int paddingTop2 = ((((i4 + i2) + getPaddingTop()) - getPaddingBottom()) - ((i7 * measuredHeight) + ((i7 - 1) * this.c))) / 2;
            int i10 = i7 - 1;
            if (i10 == 0) {
                i5 = ((((i3 + i) + getPaddingLeft()) - getPaddingRight()) - i8) / 2;
            } else {
                i5 = i + getPaddingLeft();
            }
            while (i6 < childCount) {
                View childAt = getChildAt(i6);
                int measuredWidth2 = childAt.getMeasuredWidth();
                if (i5 + measuredWidth2 > paddingRight) {
                    paddingTop2 += this.c + measuredHeight;
                    i10--;
                    if (i10 == 0) {
                        i5 = ((((i3 + i) + getPaddingLeft()) - getPaddingRight()) - i8) / 2;
                    } else {
                        i5 = i + getPaddingLeft();
                    }
                    if (paddingTop2 + measuredHeight > paddingBottom) {
                        return;
                    }
                }
                int i11 = i5 + measuredWidth2;
                childAt.layout(i5, paddingTop2, i11, paddingTop2 + measuredHeight);
                i8 -= measuredWidth2;
                i6++;
                i5 = i11;
            }
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str) {
        setText(str, true);
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setText(String str, boolean z) {
        long uptimeMillis = SystemClock.uptimeMillis();
        long j = this.g;
        long j2 = uptimeMillis < j ? (j - uptimeMillis) + 200 : 100L;
        if (str != null) {
            if (str.isEmpty()) {
                this.d = "";
                removeAllViews();
                this.f.clear();
                return;
            }
            char[] charArray = str.toCharArray();
            int i = 0;
            for (char c : charArray) {
                if (i >= this.f.size()) {
                    TextView textView = new TextView(this.a);
                    textView.setTextSize(0, this.b);
                    textView.setTextColor(this.e);
                    this.f.add(textView);
                }
                TextView textView2 = this.f.get(i);
                if (!textView2.getText().equals(String.valueOf(c))) {
                    textView2.setText(String.valueOf(c));
                }
                i++;
            }
            while (i < this.f.size()) {
                this.f.get(i).setText("");
                i++;
            }
            for (int length = this.d.length(); length < this.f.size(); length++) {
                final TextView textView3 = this.f.get(length);
                if (textView3.getText().length() > 0) {
                    if (!z) {
                        addView(textView3);
                    } else {
                        new Handler().postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.skills.common.view.AniTextView.1
                            @Override // java.lang.Runnable
                            public void run() {
                                if (textView3.getText().length() > 0) {
                                    AniTextView.this.addView(textView3);
                                    AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                                    alphaAnimation.setDuration(800L);
                                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, 1, 0.5f, 1, 0.5f);
                                    scaleAnimation.setDuration(500L);
                                    AnimationSet animationSet = new AnimationSet(AniTextView.this.a, null);
                                    animationSet.addAnimation(alphaAnimation);
                                    animationSet.addAnimation(scaleAnimation);
                                    textView3.startAnimation(animationSet);
                                }
                            }
                        }, j2);
                        j2 += 200;
                    }
                }
            }
            this.g = j2;
            this.d = str;
        }
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        measureChildren(i, i2);
        View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (getChildCount() == 0) {
            setMeasuredDimension(0, 0);
        } else {
            setMeasuredDimension(size, size2);
        }
    }

    @Override // com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public float getTextSize() {
        return this.b;
    }

    @Override // android.view.View, com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setVisibility(int i) {
        super.setVisibility(i);
    }

    @Override // android.view.View, com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public ViewPropertyAnimator animate() {
        return super.animate();
    }

    @Override // android.view.View, com.xiaomi.micolauncher.skills.common.view.AsrTextInterface
    public void setAlpha(float f) {
        super.setAlpha(f);
    }
}
