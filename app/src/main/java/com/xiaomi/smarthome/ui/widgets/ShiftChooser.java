package com.xiaomi.smarthome.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;
import com.xiaomi.mico.common.DisplayUtils;
import com.xiaomi.smarthome.R;

/* loaded from: classes4.dex */
public abstract class ShiftChooser extends View {
    protected static final String TAG = "ShiftChooser";
    protected Paint mBgPaint;
    protected boolean mCanChoose;
    protected int mCurrentShift;
    protected Paint mFgPaint;
    protected Bitmap mMarkBmp;
    protected OnShiftChangedListener mOnShiftChangedListener;
    protected RectF mRectLayout;
    protected Paint mShiftPaint;
    protected String mText;
    protected Paint mTextPaint;
    protected int mThumbColor;
    protected RectF mThumbLayout;
    protected Paint mThumbPaint;
    protected final int mThumbUnableColor;
    protected long mTimeMillisDown;
    protected String[] mTitles;
    protected int mTotalShifts;
    protected int mTouchSlop;
    protected boolean showShiftText;

    /* loaded from: classes4.dex */
    public interface OnShiftChangedListener {
        void onShiftChanged(ShiftChooser shiftChooser, int i);

        void onStartTrackingTouch();

        void onStopTrackingTouch(ShiftChooser shiftChooser, int i, boolean z);
    }

    public ShiftChooser(Context context) {
        this(context, null);
    }

    public ShiftChooser(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.mFgPaint.setStyle(Paint.Style.FILL);
        this.mFgPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
    }

    public ShiftChooser(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCanChoose = true;
        this.mTitles = new String[]{"1 档", "2 档", "3 档", "4 档"};
        this.mRectLayout = new RectF();
        this.mThumbLayout = new RectF();
        this.mShiftPaint = new Paint(1);
        this.mBgPaint = new Paint(1);
        this.mFgPaint = new Paint(1);
        this.mThumbPaint = new Paint(1);
        this.mTextPaint = new Paint(1);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ShiftChooser, i, 0);
        setBackgroundColor(obtainStyledAttributes.getColor(R.styleable.ShiftChooser_bg_color, getResources().getColor(R.color.shiftbar_bg)));
        setForegroundColor(obtainStyledAttributes.getColor(R.styleable.ShiftChooser_shift_fg_color, getResources().getColor(R.color.humidity_shiftbar_fg)));
        setThumbColor(obtainStyledAttributes.getColor(R.styleable.ShiftChooser_thumb_color, getResources().getColor(R.color.shiftbar_thumb_bg)));
        setShiftColor(obtainStyledAttributes.getColor(R.styleable.ShiftChooser_shift_color, getResources().getColor(R.color.shiftbar_shift)));
        this.mThumbUnableColor = obtainStyledAttributes.getColor(R.styleable.ShiftChooser_shift_color, getResources().getColor(R.color.shiftbar_shift));
        this.mTextPaint.setColor(obtainStyledAttributes.getColor(R.styleable.ShiftChooser_shift_text_color, ContextCompat.getColor(context, R.color.shiftbar_bar_text_color)));
        this.mTextPaint.setTextSize(obtainStyledAttributes.getDimension(R.styleable.ShiftChooser_shift_text_size, DisplayUtils.dip2px(getContext(), getResources().getDimension(R.dimen.shiftbar_text_size))));
        this.showShiftText = obtainStyledAttributes.getBoolean(R.styleable.ShiftChooser_show_shift_text, true);
        this.mTotalShifts = obtainStyledAttributes.getInteger(R.styleable.ShiftChooser_max_shifts, 5);
        obtainStyledAttributes.recycle();
        this.mThumbPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setForegroundColor(int i) {
        this.mFgPaint.setColor(i);
        invalidate();
    }

    public void setThumbColor(int i) {
        this.mThumbColor = i;
        invalidate();
    }

    public void setShiftColor(int i) {
        this.mShiftPaint.setColor(i);
        invalidate();
    }

    public void setTextPaint(@ColorRes int i, @DimenRes int i2) {
        this.mTextPaint.setColor(ContextCompat.getColor(getContext(), i));
        this.mTextPaint.setTextSize(getContext().getResources().getDimension(i2));
        invalidate();
    }

    public void setImageIcon(Bitmap bitmap) {
        this.mMarkBmp = bitmap;
        invalidate();
    }

    public void setText(String str) {
        this.mText = str;
        invalidate();
    }

    public void scrollToShift(int i, boolean z) {
        Log.e(TAG, "which: " + i);
        this.mCurrentShift = i;
        invalidate();
        OnShiftChangedListener onShiftChangedListener = this.mOnShiftChangedListener;
        if (onShiftChangedListener != null && z) {
            onShiftChangedListener.onStartTrackingTouch();
            this.mOnShiftChangedListener.onShiftChanged(this, this.mCurrentShift);
            this.mOnShiftChangedListener.onStopTrackingTouch(this, this.mCurrentShift, true);
        }
    }

    public int getCurrentShift() {
        return this.mCurrentShift;
    }

    public int getTotalShifts() {
        return this.mTotalShifts;
    }

    public void setTotalShifts(int i) {
        this.mTotalShifts = i;
        invalidate();
    }

    public void setShiftsTitles(String[] strArr) {
        if (strArr.length == this.mTotalShifts) {
            this.mTitles = strArr;
            invalidate();
            return;
        }
        throw new IllegalArgumentException("titles length not equals mTotalShifts!");
    }

    @Override // android.view.View
    public void setBackgroundColor(int i) {
        this.mBgPaint.setColor(i);
    }

    public void setCanChoose(boolean z) {
        this.mCanChoose = z;
    }

    public void setShowShiftText(boolean z) {
        this.showShiftText = z;
        invalidate();
    }

    public void updateEnableUI(boolean z) {
        setEnabled(z);
    }

    public void setOnShiftChangedListener(OnShiftChangedListener onShiftChangedListener) {
        this.mOnShiftChangedListener = onShiftChangedListener;
    }
}
