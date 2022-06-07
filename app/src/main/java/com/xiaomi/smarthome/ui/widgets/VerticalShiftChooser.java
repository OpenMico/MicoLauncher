package com.xiaomi.smarthome.ui.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public class VerticalShiftChooser extends ShiftChooser {
    private float d;
    private float e;
    private Rect a = null;
    private Rect b = null;
    private final int c = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    private boolean f = true;
    private boolean g = false;

    public VerticalShiftChooser(Context context) {
        super(context);
    }

    public VerticalShiftChooser(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VerticalShiftChooser(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        String str;
        try {
            this.mRectLayout.set(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.x08l_vertical_shift_chooser_radius);
            float width = this.mRectLayout.width() / 8.0f;
            float width2 = this.mRectLayout.width() / 10.0f;
            float height = this.mRectLayout.height() / this.mTotalShifts;
            int saveLayer = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
            canvas.drawRoundRect(this.mRectLayout, dimensionPixelSize, dimensionPixelSize, this.mBgPaint);
            if (isEnabled()) {
                if (this.mCurrentShift == this.mTotalShifts - 1) {
                    canvas.drawRoundRect(this.mRectLayout, dimensionPixelSize, dimensionPixelSize, this.mFgPaint);
                } else {
                    canvas.drawRect(new RectF(this.mRectLayout.left, this.mRectLayout.bottom - ((this.mCurrentShift + 1) * height), this.mRectLayout.right, this.mRectLayout.bottom), this.mFgPaint);
                    canvas.restoreToCount(saveLayer);
                }
            }
            for (int i = 0; i < this.mTotalShifts - 1; i++) {
                float f = (int) (this.mRectLayout.top + height + (i * height));
                canvas.drawLine(getPaddingLeft(), f, getWidth() - getPaddingRight(), f, this.mShiftPaint);
            }
            if (this.mCurrentShift >= 0) {
                if (!TextUtils.isEmpty(this.mText)) {
                    str = this.mText;
                } else if (this.mTitles != null && this.mTitles.length > this.mCurrentShift) {
                    str = this.mTitles[this.mCurrentShift];
                } else {
                    return;
                }
                float min = Math.min(this.mTextPaint.measureText(str) + ((dimensionPixelSize - width2) * 2.0f), height - (width * 2.0f));
                float f2 = height / 2.0f;
                float f3 = ((this.mRectLayout.bottom - f2) - (height * this.mCurrentShift)) + width2;
                float f4 = min / 2.0f;
                this.mThumbLayout.set(this.mRectLayout.left + width2, f3 - f4, this.mRectLayout.right - width2, f3 + f4);
                if (isEnabled()) {
                    this.mThumbPaint.setColor(this.mThumbColor);
                } else {
                    this.mThumbPaint.setColor(this.mThumbUnableColor);
                }
                if (this.showShiftText) {
                    Paint.FontMetrics fontMetrics = this.mTextPaint.getFontMetrics();
                    canvas.drawText(str, this.mRectLayout.centerX(), this.mRectLayout.top + f2 + (((fontMetrics.bottom - fontMetrics.top) / 2.0f) - fontMetrics.bottom), this.mTextPaint);
                }
                if (this.mMarkBmp != null) {
                    canvas.drawBitmap(this.mMarkBmp, a(this.mMarkBmp), b(this.mMarkBmp), (Paint) null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Rect a(@NonNull Bitmap bitmap) {
        if (this.a == null) {
            this.a = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
        return this.a;
    }

    private Rect b(@NonNull Bitmap bitmap) {
        if (this.b == null) {
            this.b = new Rect((getWidth() - bitmap.getWidth()) / 2, (getHeight() - getResources().getDimensionPixelSize(R.dimen.size_8dp)) - bitmap.getHeight(), (getWidth() + bitmap.getWidth()) / 2, getHeight() - getResources().getDimensionPixelSize(R.dimen.size_8dp));
        }
        return this.b;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:26:0x008c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.mCanChoose
            r1 = 0
            if (r0 == 0) goto L_0x00a6
            int r0 = r6.mTotalShifts
            if (r0 > 0) goto L_0x000b
            goto L_0x00a6
        L_0x000b:
            int r0 = r7.getAction()
            com.xiaomi.smarthome.ui.widgets.ShiftChooser$OnShiftChangedListener r2 = r6.mOnShiftChangedListener
            if (r2 == 0) goto L_0x00a1
            r2 = 1
            switch(r0) {
                case 0: goto L_0x008f;
                case 1: goto L_0x0063;
                case 2: goto L_0x0019;
                case 3: goto L_0x0076;
                default: goto L_0x0017;
            }
        L_0x0017:
            goto L_0x00a1
        L_0x0019:
            float r0 = r7.getX()
            float r3 = r7.getY()
            float r4 = r6.d
            float r4 = r4 - r0
            float r0 = r6.e
            float r0 = r0 - r3
            float r5 = java.lang.Math.abs(r0)
            float r4 = java.lang.Math.abs(r4)
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x00a1
            float r0 = java.lang.Math.abs(r0)
            int r4 = r6.c
            float r4 = (float) r4
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x00a1
            int r0 = r6.a(r3)
            android.view.ViewParent r3 = r6.getParent()
            r3.requestDisallowInterceptTouchEvent(r2)
            boolean r3 = r6.f
            if (r3 != 0) goto L_0x0060
            int r3 = r6.mCurrentShift
            if (r3 == r0) goto L_0x0060
            r6.mCurrentShift = r0
            r6.invalidate()
            com.xiaomi.smarthome.ui.widgets.ShiftChooser$OnShiftChangedListener r7 = r6.mOnShiftChangedListener
            int r0 = r6.mCurrentShift
            r7.onShiftChanged(r6, r0)
            r6.g = r2
            return r2
        L_0x0060:
            r6.f = r1
            goto L_0x00a1
        L_0x0063:
            float r0 = r7.getY()
            int r0 = r6.a(r0)
            int r3 = r6.mCurrentShift
            if (r3 == r0) goto L_0x0076
            r6.mCurrentShift = r0
            r6.invalidate()
            r6.g = r2
        L_0x0076:
            r6.f = r2
            android.view.ViewParent r0 = r6.getParent()
            r0.requestDisallowInterceptTouchEvent(r1)
            com.xiaomi.smarthome.ui.widgets.ShiftChooser$OnShiftChangedListener r0 = r6.mOnShiftChangedListener
            int r2 = r6.mCurrentShift
            boolean r3 = r6.g
            r0.onStopTrackingTouch(r6, r2, r3)
            boolean r0 = r6.g
            if (r0 == 0) goto L_0x00a1
            r6.g = r1
            goto L_0x00a1
        L_0x008f:
            float r0 = r7.getX()
            r6.d = r0
            float r7 = r7.getY()
            r6.e = r7
            com.xiaomi.smarthome.ui.widgets.ShiftChooser$OnShiftChangedListener r7 = r6.mOnShiftChangedListener
            r7.onStartTrackingTouch()
            return r2
        L_0x00a1:
            boolean r7 = super.onTouchEvent(r7)
            return r7
        L_0x00a6:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.smarthome.ui.widgets.VerticalShiftChooser.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private int a(float f) {
        int height = (int) (this.mRectLayout.height() / this.mTotalShifts);
        if (height == 0) {
            return this.mTotalShifts - 1;
        }
        int i = (int) ((this.mRectLayout.bottom - f) / height);
        if (i >= this.mTotalShifts) {
            i = this.mTotalShifts - 1;
        }
        if (i < 0) {
            return 0;
        }
        return i;
    }
}
