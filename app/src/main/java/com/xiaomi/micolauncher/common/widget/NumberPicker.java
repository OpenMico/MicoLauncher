package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.view.GravityCompat;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* loaded from: classes3.dex */
public class NumberPicker extends LinearLayout {
    private static final long DEFAULT_LONG_PRESS_UPDATE_INTERVAL = 300;
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private static final int SELECTOR_MAX_FLING_VELOCITY_ADJUSTMENT = 8;
    private static final int SELECTOR_MIDDLE_ITEM_INDEX = 2;
    static final int SELECTOR_WHEEL_ITEM_COUNT = 5;
    private static final int SIZE_UNSPECIFIED = -1;
    private static final int SNAP_SCROLL_DURATION = 300;
    private static final int TEXT_PADDING = 30;
    private static final float TOP_AND_BOTTOM_FADING_EDGE_STRENGTH = 0.9f;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDERS_DISTANCE = 48;
    private static final int UNSCALED_DEFAULT_SELECTION_DIVIDER_HEIGHT = 2;
    private final int ATTR_INDEX_LABEL;
    private final int ATTR_INDEX_TEXTSIZE;
    private int MAX_HEIGHT;
    private final int[] MiuiNumberPicker_Styleable;
    private float SELECTION_DIVIDERS_DISTANCE;
    private float TEXT_MAX_ASCENT;
    private int TEXT_SIZE_LABEL;
    private int TEXT_SIZE_MAX;
    private int TEXT_SIZE_MIN;
    private AccessibilityNodeProviderImpl mAccessibilityNodeProvider;
    private final Scroller mAdjustScroller;
    private BeginSoftInputOnLongPressCommand mBeginSoftInputOnLongPressCommand;
    private int mBottomSelectionDividerBottom;
    private ChangeCurrentByOneFromLongPressCommand mChangeCurrentByOneFromLongPressCommand;
    private final boolean mComputeMaxWidth;
    private int mCurrentScrollOffset;
    private boolean mDecrementVirtualButtonPressed;
    private float mDisplayedMaxTextWidth;
    private String[] mDisplayedValues;
    private final Scroller mFlingScroller;
    private Formatter mFormatter;
    private final boolean mHasSelectorWheel;
    private int mHighlightBackgroundResId;
    private boolean mIncrementVirtualButtonPressed;
    private boolean mIngonreMoveEvents;
    private int mInitialScrollOffset;
    private final EditText mInputText;
    private CharSequence mLabel;
    private Paint mLabelPaint;
    private long mLastDownEventTime;
    private float mLastDownEventY;
    private float mLastDownOrMoveEventY;
    private int mLastHandledDownDpadKeyCode;
    private int mLastHoveredChildVirtualViewId;
    private long mLongPressUpdateInterval;
    private final int mMaxHeight;
    private int mMaxValue;
    private int mMaxWidth;
    private int mMaximumFlingVelocity;
    private final int mMinHeight;
    private int mMinValue;
    private final int mMinWidth;
    private int mMinimumFlingVelocity;
    private boolean mNoHighlightBeforeChange;
    private int mNormalBackgroundResId;
    private int mNormalTextColor;
    private int mNormalTextSize;
    private OnScrollListener mOnScrollListener;
    private OnValueChangeListener mOnValueChangeListener;
    private final PressedStateHelper mPressedStateHelper;
    private int mPreviousScrollerY;
    private int mScrollState;
    private int mSelectTextColor;
    private int mSelectTextSize;
    private final int mSelectionDividerHeight;
    private final int mSelectionDividersDistance;
    private int mSelectorElementHeight;
    private final SparseArray<String> mSelectorIndexToStringCache;
    private final int[] mSelectorIndices;
    private int mSelectorTextGapHeight;
    private final Paint mSelectorWheelPaint;
    private SetSelectionCommand mSetSelectionCommand;
    private boolean mShowSoftInputOnTap;
    private int mSoundId;
    private SoundPool mSoundPlayer;
    private final int mTextSize;
    private int mTopSelectionDividerTop;
    private int mTouchSlop;
    private int mValue;
    private boolean mValueChanged;
    private VelocityTracker mVelocityTracker;
    private final Drawable mVirtualButtonPressedDrawable;
    private boolean mWrapSelectorWheel;
    public static final Formatter TWO_DIGIT_FORMATTER = new Formatter() { // from class: com.xiaomi.micolauncher.common.widget.NumberPicker.1
        final StringBuilder mBuilder = new StringBuilder();
        final java.util.Formatter mFmt = new java.util.Formatter(this.mBuilder, Locale.US);
        final Object[] mArgs = new Object[1];

        @Override // com.xiaomi.micolauncher.common.widget.NumberPicker.Formatter
        public String format(int i) {
            this.mArgs[0] = Integer.valueOf(i);
            StringBuilder sb = this.mBuilder;
            sb.delete(0, sb.length());
            this.mFmt.format("%02d", this.mArgs);
            return this.mFmt.toString();
        }
    };
    private static final int[] PRESSED_STATE_SET = {16842919};
    private static int TEXT_COLOR_LABEL = 268435455;
    private static int TEXT_COLOR_FG = -1;
    private static int TEXT_COLOR_BG = Integer.MAX_VALUE;
    private static final char[] DIGIT_CHARACTERS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /* loaded from: classes3.dex */
    public interface Formatter {
        String format(int i);
    }

    /* loaded from: classes3.dex */
    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScrollStateChange(NumberPicker numberPicker, int i);
    }

    /* loaded from: classes3.dex */
    public interface OnValueChangeListener {
        void onValueChange(NumberPicker numberPicker, int i, int i2);
    }

    private float getTextSize(float f, int i, int i2) {
        return f >= 3.0f ? i2 : ((f / 3.0f) * (i2 - i)) + i;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        return TOP_AND_BOTTOM_FADING_EDGE_STRENGTH;
    }

    public NumberPicker(Context context) {
        this(context, null);
    }

    public NumberPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.numberPickerStyle);
    }

    public NumberPicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        int i2;
        int i3;
        this.mLongPressUpdateInterval = 300L;
        this.mSelectorIndexToStringCache = new SparseArray<>();
        this.mSelectorIndices = new int[5];
        this.mInitialScrollOffset = Integer.MIN_VALUE;
        this.mScrollState = 0;
        this.mLastHandledDownDpadKeyCode = -1;
        this.TEXT_SIZE_MAX = 24;
        this.TEXT_SIZE_MIN = 16;
        this.TEXT_SIZE_LABEL = 11;
        this.SELECTION_DIVIDERS_DISTANCE = 56.0f;
        this.MAX_HEIGHT = 260;
        this.mSelectTextSize = getResources().getDimensionPixelSize(R.dimen.number_picker_text_size);
        this.mNormalTextSize = getResources().getDimensionPixelSize(R.dimen.number_picker_text_size_small);
        this.mSelectTextColor = -1;
        this.mNormalTextColor = Integer.MAX_VALUE;
        this.MiuiNumberPicker_Styleable = new int[]{16842901, 16843087};
        this.ATTR_INDEX_TEXTSIZE = 0;
        this.ATTR_INDEX_LABEL = 1;
        this.mNoHighlightBeforeChange = false;
        float f = getResources().getDisplayMetrics().density;
        if (f != 1.0f) {
            this.TEXT_SIZE_MIN = (int) (this.TEXT_SIZE_MIN * f);
            this.TEXT_SIZE_MAX = (int) (this.TEXT_SIZE_MAX * f);
            this.TEXT_SIZE_LABEL = (int) (this.TEXT_SIZE_LABEL * f);
            this.SELECTION_DIVIDERS_DISTANCE *= f;
            this.MAX_HEIGHT = (int) (this.MAX_HEIGHT * f);
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, this.MiuiNumberPicker_Styleable, 0, 0);
        this.mLabel = obtainStyledAttributes.getText(1);
        this.TEXT_SIZE_MAX = obtainStyledAttributes.getDimensionPixelSize(0, this.TEXT_SIZE_MAX);
        obtainStyledAttributes.recycle();
        this.mSoundPlayer = new SoundPool(1, 1, 0);
        this.mSoundId = this.mSoundPlayer.load(getContext(), R.raw.numberpicker_value_change_new, 1);
        this.mHasSelectorWheel = true;
        this.mSelectionDividerHeight = (int) TypedValue.applyDimension(1, 2.0f, getResources().getDisplayMetrics());
        this.mSelectionDividersDistance = (int) this.SELECTION_DIVIDERS_DISTANCE;
        this.mMinHeight = -1;
        this.mMaxHeight = this.MAX_HEIGHT;
        int i4 = this.mMinHeight;
        if (i4 == -1 || (i3 = this.mMaxHeight) == -1 || i4 <= i3) {
            this.mMinWidth = -1;
            this.mMaxWidth = -1;
            int i5 = this.mMinWidth;
            if (i5 == -1 || (i2 = this.mMaxWidth) == -1 || i5 <= i2) {
                this.mComputeMaxWidth = this.mMaxWidth == -1;
                this.mVirtualButtonPressedDrawable = getResources().getDrawable(R.drawable.bg_picker_virtual);
                this.mPressedStateHelper = new PressedStateHelper();
                setWillNotDraw(!this.mHasSelectorWheel);
                ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(R.layout.widget_number_picker, (ViewGroup) this, true);
                this.mInputText = (EditText) findViewById(R.id.number_picker_input);
                this.mInputText.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: com.xiaomi.micolauncher.common.widget.NumberPicker.2
                    @Override // android.view.View.OnFocusChangeListener
                    public void onFocusChange(View view, boolean z) {
                        if (z) {
                            NumberPicker.this.mInputText.selectAll();
                            return;
                        }
                        NumberPicker.this.mInputText.setSelection(0, 0);
                        NumberPicker.this.validateInputTextView(view);
                    }
                });
                this.mInputText.setFilters(new InputFilter[]{new InputTextFilter()});
                this.mInputText.setRawInputType(2);
                this.mInputText.setImeOptions(6);
                this.mInputText.setVisibility(4);
                this.mInputText.setGravity(GravityCompat.START);
                if (Build.VERSION.SDK_INT >= 14) {
                    this.mInputText.setScaleX(0.0f);
                }
                this.mInputText.setSaveEnabled(false);
                EditText editText = this.mInputText;
                editText.setPadding(30, editText.getPaddingTop(), 30, this.mInputText.getPaddingRight());
                ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
                this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
                this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
                this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity() / 8;
                this.mTextSize = (int) this.mInputText.getTextSize();
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setTextAlign(Paint.Align.CENTER);
                paint.setTextSize(this.TEXT_SIZE_MAX);
                paint.setTypeface(this.mInputText.getTypeface());
                paint.setColor(this.mInputText.getTextColors().getColorForState(ENABLED_STATE_SET, -1));
                this.mSelectorWheelPaint = paint;
                this.TEXT_MAX_ASCENT = paint.ascent();
                this.mLabelPaint = new Paint();
                this.mLabelPaint.setAntiAlias(true);
                this.mLabelPaint.setFakeBoldText(true);
                this.mLabelPaint.setColor(TEXT_COLOR_LABEL);
                this.mLabelPaint.setTextSize(this.TEXT_SIZE_LABEL);
                if (Build.VERSION.SDK_INT >= 14) {
                    this.mFlingScroller = new Scroller(getContext(), null, true);
                } else {
                    this.mFlingScroller = new Scroller(getContext(), null);
                }
                this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(2.5f));
                updateInputTextView();
                if (Build.VERSION.SDK_INT >= 16 && getImportantForAccessibility() == 0) {
                    setImportantForAccessibility(1);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("minWidth > maxWidth");
        }
        throw new IllegalArgumentException("minHeight > maxHeight");
    }

    public void setNoHighlightBeforeChange(boolean z) {
        this.mNoHighlightBeforeChange = z;
    }

    public void setBackgroundResIds(int i, int i2) {
        this.mHighlightBackgroundResId = i2;
        this.mNormalBackgroundResId = i;
    }

    public void updateBackground() {
        int i = shouldShowNormalState() ? this.mNormalBackgroundResId : this.mHighlightBackgroundResId;
        if (i > 0) {
            setBackgroundResource(i);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!this.mHasSelectorWheel) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth2 = this.mInputText.getMeasuredWidth();
        int measuredHeight2 = this.mInputText.getMeasuredHeight();
        int i5 = (measuredWidth - measuredWidth2) / 2;
        int i6 = (measuredHeight - measuredHeight2) / 2;
        this.mInputText.layout(i5, i6, measuredWidth2 + i5, measuredHeight2 + i6);
        if (z) {
            initializeSelectorWheel();
            initializeFadingEdges();
            int height = getHeight();
            int i7 = this.mSelectionDividersDistance;
            int i8 = this.mSelectionDividerHeight;
            this.mTopSelectionDividerTop = ((height - i7) / 2) - i8;
            this.mBottomSelectionDividerBottom = this.mTopSelectionDividerTop + (i8 * 2) + i7;
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        if (!this.mHasSelectorWheel) {
            super.onMeasure(i, i2);
            return;
        }
        super.onMeasure(makeMeasureSpec(i, this.mMaxWidth), makeMeasureSpec(i2, this.mMaxHeight));
        setMeasuredDimension(resolveSizeAndStateRespectingMinSize(this.mMinWidth, getMeasuredWidth(), i), resolveSizeAndStateRespectingMinSize(this.mMinHeight, getMeasuredHeight(), i2));
    }

    private boolean moveToFinalScrollerPosition(Scroller scroller) {
        scroller.forceFinished(true);
        int finalY = scroller.getFinalY() - scroller.getCurrY();
        int i = this.mInitialScrollOffset - ((this.mCurrentScrollOffset + finalY) % this.mSelectorElementHeight);
        if (i == 0) {
            return false;
        }
        int abs = Math.abs(i);
        int i2 = this.mSelectorElementHeight;
        if (abs > i2 / 2) {
            i = i > 0 ? i - i2 : i + i2;
        }
        scrollBy(0, finalY + i);
        return true;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mHasSelectorWheel || !isEnabled() || motionEvent.getActionMasked() != 0) {
            return false;
        }
        removeAllCallbacks();
        this.mInputText.setVisibility(4);
        float y = motionEvent.getY();
        this.mLastDownEventY = y;
        this.mLastDownOrMoveEventY = y;
        this.mLastDownEventTime = motionEvent.getEventTime();
        this.mIngonreMoveEvents = false;
        this.mShowSoftInputOnTap = false;
        float f = this.mLastDownEventY;
        if (f < this.mTopSelectionDividerTop) {
            if (this.mScrollState == 0) {
                this.mPressedStateHelper.buttonPressDelayed(2);
            }
        } else if (f > this.mBottomSelectionDividerBottom && this.mScrollState == 0) {
            this.mPressedStateHelper.buttonPressDelayed(1);
        }
        if (!this.mFlingScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
            onScrollStateChange(0);
        } else if (!this.mAdjustScroller.isFinished()) {
            this.mFlingScroller.forceFinished(true);
            this.mAdjustScroller.forceFinished(true);
        } else {
            float f2 = this.mLastDownEventY;
            if (f2 < this.mTopSelectionDividerTop) {
                postChangeCurrentByOneFromLongPress(false, ViewConfiguration.getLongPressTimeout());
            } else if (f2 > this.mBottomSelectionDividerBottom) {
                postChangeCurrentByOneFromLongPress(true, ViewConfiguration.getLongPressTimeout());
            } else {
                this.mShowSoftInputOnTap = true;
                postBeginSoftInputOnLongPressCommand();
            }
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || !this.mHasSelectorWheel) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 1:
                removeBeginSoftInputCommand();
                removeChangeCurrentByOneFromLongPress();
                this.mPressedStateHelper.cancel();
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumFlingVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity();
                if (Math.abs(yVelocity) > this.mMinimumFlingVelocity) {
                    fling(yVelocity);
                    onScrollStateChange(2);
                } else {
                    int y = (int) motionEvent.getY();
                    int abs = (int) Math.abs(y - this.mLastDownEventY);
                    long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
                    if (abs > this.mTouchSlop || eventTime >= ViewConfiguration.getTapTimeout()) {
                        ensureScrollWheelAdjusted();
                    } else if (this.mShowSoftInputOnTap) {
                        this.mShowSoftInputOnTap = false;
                    } else {
                        int i = (y / this.mSelectorElementHeight) - 2;
                        if (i > 0) {
                            changeValueByOne(true);
                            this.mPressedStateHelper.buttonTapped(1);
                        } else if (i < 0) {
                            changeValueByOne(false);
                            this.mPressedStateHelper.buttonTapped(2);
                        }
                    }
                    onScrollStateChange(0);
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            case 2:
                if (!this.mIngonreMoveEvents) {
                    float y2 = motionEvent.getY();
                    if (this.mScrollState == 1) {
                        scrollBy(0, (int) (y2 - this.mLastDownOrMoveEventY));
                        invalidate();
                    } else if (((int) Math.abs(y2 - this.mLastDownEventY)) > this.mTouchSlop) {
                        removeAllCallbacks();
                        onScrollStateChange(1);
                    }
                    this.mLastDownOrMoveEventY = y2;
                    break;
                }
                break;
        }
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyCode != 23 && keyCode != 66) {
            switch (keyCode) {
                case 19:
                case 20:
                    if (this.mHasSelectorWheel) {
                        switch (keyEvent.getAction()) {
                            case 0:
                                if (this.mWrapSelectorWheel || keyCode == 20 ? getValue() < getMaxValue() : getValue() > getMinValue()) {
                                    requestFocus();
                                    this.mLastHandledDownDpadKeyCode = keyCode;
                                    removeAllCallbacks();
                                    if (this.mFlingScroller.isFinished()) {
                                        changeValueByOne(keyCode == 20);
                                    }
                                    return true;
                                }
                                break;
                            case 1:
                                if (this.mLastHandledDownDpadKeyCode == keyCode) {
                                    this.mLastHandledDownDpadKeyCode = -1;
                                    return true;
                                }
                                break;
                        }
                    }
                    break;
            }
        } else {
            removeAllCallbacks();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 1 || actionMasked == 3) {
            removeAllCallbacks();
        }
        return super.dispatchTrackballEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        int i;
        if (!this.mHasSelectorWheel && Build.VERSION.SDK_INT >= 14) {
            return super.dispatchHoverEvent(motionEvent);
        }
        if (!((AccessibilityManager) getContext().getSystemService("accessibility")).isEnabled()) {
            return false;
        }
        int y = (int) motionEvent.getY();
        if (y < this.mTopSelectionDividerTop) {
            i = 3;
        } else {
            i = y > this.mBottomSelectionDividerBottom ? 1 : 2;
        }
        int actionMasked = motionEvent.getActionMasked();
        AccessibilityNodeProviderImpl accessibilityNodeProviderImpl = (AccessibilityNodeProviderImpl) getAccessibilityNodeProvider();
        if (actionMasked != 7) {
            switch (actionMasked) {
                case 9:
                    accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 128);
                    this.mLastHoveredChildVirtualViewId = i;
                    accessibilityNodeProviderImpl.performAction(i, 64, null);
                    return false;
                case 10:
                    accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 256);
                    this.mLastHoveredChildVirtualViewId = -1;
                    return false;
                default:
                    return false;
            }
        } else {
            int i2 = this.mLastHoveredChildVirtualViewId;
            if (i2 == i || i2 == -1) {
                return false;
            }
            accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i2, 256);
            accessibilityNodeProviderImpl.sendAccessibilityEventForVirtualView(i, 128);
            this.mLastHoveredChildVirtualViewId = i;
            accessibilityNodeProviderImpl.performAction(i, 64, null);
            return false;
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                return;
            }
        }
        scroller.computeScrollOffset();
        int currY = scroller.getCurrY();
        if (this.mPreviousScrollerY == 0) {
            this.mPreviousScrollerY = scroller.getStartY();
        }
        scrollBy(0, currY - this.mPreviousScrollerY);
        this.mPreviousScrollerY = currY;
        if (scroller.isFinished()) {
            onScrollerFinished(scroller);
        } else {
            invalidate();
        }
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        if (i2 != 0) {
            int[] iArr = this.mSelectorIndices;
            if (!this.mWrapSelectorWheel && i2 > 0 && iArr[2] <= this.mMinValue) {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            } else if (this.mWrapSelectorWheel || i2 >= 0 || iArr[2] < this.mMaxValue) {
                this.mCurrentScrollOffset += i2;
                while (i2 > 0) {
                    int i3 = this.mCurrentScrollOffset;
                    if (i3 - this.mInitialScrollOffset <= this.mSelectorTextGapHeight) {
                        break;
                    }
                    this.mCurrentScrollOffset = i3 - this.mSelectorElementHeight;
                    decrementSelectorIndices(iArr);
                    setValueInternal(iArr[2], true);
                    if (!this.mWrapSelectorWheel && iArr[2] <= this.mMinValue) {
                        this.mCurrentScrollOffset = this.mInitialScrollOffset;
                    }
                }
                while (i2 < 0) {
                    int i4 = this.mCurrentScrollOffset;
                    if (i4 - this.mInitialScrollOffset < (-this.mSelectorTextGapHeight)) {
                        this.mCurrentScrollOffset = i4 + this.mSelectorElementHeight;
                        incrementSelectorIndices(iArr);
                        setValueInternal(iArr[2], true);
                        if (!this.mWrapSelectorWheel && iArr[2] >= this.mMaxValue) {
                            this.mCurrentScrollOffset = this.mInitialScrollOffset;
                        }
                    } else {
                        return;
                    }
                }
            } else {
                this.mCurrentScrollOffset = this.mInitialScrollOffset;
            }
        }
    }

    public void setLabel(String str) {
        CharSequence charSequence;
        if ((this.mLabel == null && str != null) || ((charSequence = this.mLabel) != null && !charSequence.equals(str))) {
            this.mLabel = str;
            invalidate();
        }
    }

    public void setLabelTextSize(int i) {
        this.TEXT_SIZE_LABEL = i;
        Paint paint = this.mLabelPaint;
        if (paint != null) {
            paint.setTextSize(this.TEXT_SIZE_LABEL);
            this.TEXT_MAX_ASCENT = this.mLabelPaint.ascent() - 6.0f;
        }
        invalidate();
    }

    public void setOnValueChangedListener(OnValueChangeListener onValueChangeListener) {
        this.mOnValueChangeListener = onValueChangeListener;
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setFormatter(Formatter formatter) {
        if (formatter != this.mFormatter) {
            this.mFormatter = formatter;
            initializeSelectorWheelIndices();
            updateInputTextView();
        }
    }

    public void setValue(int i) {
        setValueInternal(i, false);
    }

    private void tryComputeMaxWidth() {
        if (this.mComputeMaxWidth) {
            float f = -1.0f;
            this.mSelectorWheelPaint.setTextSize(this.TEXT_SIZE_MAX);
            String[] strArr = this.mDisplayedValues;
            int i = 0;
            if (strArr == null) {
                float f2 = 0.0f;
                while (i < 9) {
                    float measureText = this.mSelectorWheelPaint.measureText(String.valueOf(i));
                    if (measureText > f2) {
                        f2 = measureText;
                    }
                    i++;
                }
                f = (int) (formatNumber(this.mMaxValue).length() * f2);
            } else {
                int length = strArr.length;
                while (i < length) {
                    float measureText2 = this.mSelectorWheelPaint.measureText(this.mDisplayedValues[i]);
                    if (measureText2 > f) {
                        f = measureText2;
                    }
                    i++;
                }
            }
            this.mDisplayedMaxTextWidth = f;
            float paddingLeft = f + this.mInputText.getPaddingLeft() + this.mInputText.getPaddingRight() + getPaddingLeft() + getPaddingRight();
            if (this.mMaxWidth != paddingLeft) {
                int i2 = this.mMinWidth;
                if (paddingLeft > i2) {
                    this.mMaxWidth = (int) paddingLeft;
                } else {
                    this.mMaxWidth = i2;
                }
            }
        }
    }

    public boolean getWrapSelectorWheel() {
        return this.mWrapSelectorWheel;
    }

    public void setWrapSelectorWheel(boolean z) {
        boolean z2 = this.mMaxValue - this.mMinValue >= this.mSelectorIndices.length;
        if ((!z || z2) && z != this.mWrapSelectorWheel) {
            this.mWrapSelectorWheel = z;
        }
        refreshWheel();
    }

    public void setOnLongPressUpdateInterval(long j) {
        this.mLongPressUpdateInterval = j;
    }

    public int getValue() {
        return this.mValue;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int i) {
        if (this.mMinValue != i) {
            if (i >= 0) {
                this.mMinValue = i;
                int i2 = this.mMinValue;
                if (i2 > this.mValue) {
                    this.mValue = i2;
                }
                setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
                initializeSelectorWheelIndices();
                updateInputTextView();
                tryComputeMaxWidth();
                invalidate();
                return;
            }
            throw new IllegalArgumentException("minValue must be >= 0");
        }
    }

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int i) {
        if (this.mMaxValue != i) {
            if (i >= 0) {
                this.mMaxValue = i;
                int i2 = this.mMaxValue;
                if (i2 < this.mValue) {
                    this.mValue = i2;
                }
                setWrapSelectorWheel(this.mMaxValue - this.mMinValue > this.mSelectorIndices.length);
                initializeSelectorWheelIndices();
                updateInputTextView();
                tryComputeMaxWidth();
                invalidate();
                return;
            }
            throw new IllegalArgumentException("maxValue must be >= 0");
        }
    }

    public String[] getDisplayedValues() {
        return this.mDisplayedValues;
    }

    public void setDisplayedValues(String[] strArr) {
        if (this.mDisplayedValues != strArr) {
            this.mDisplayedValues = strArr;
            if (this.mDisplayedValues != null) {
                this.mInputText.setRawInputType(524289);
            } else {
                this.mInputText.setRawInputType(2);
            }
            updateInputTextView();
            initializeSelectorWheelIndices();
            tryComputeMaxWidth();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllCallbacks();
        this.mSoundPlayer.release();
    }

    @Override // android.widget.LinearLayout, android.view.View
    protected void onDraw(Canvas canvas) {
        if (!this.mHasSelectorWheel) {
            super.onDraw(canvas);
            return;
        }
        int width = getWidth();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        float right = (((getRight() - getLeft()) + paddingLeft) - paddingRight) / 2;
        float f = this.mCurrentScrollOffset;
        Drawable drawable = this.mVirtualButtonPressedDrawable;
        if (drawable != null && this.mScrollState == 0) {
            if (this.mDecrementVirtualButtonPressed) {
                drawable.setState(PRESSED_STATE_SET);
                this.mVirtualButtonPressedDrawable.setBounds(paddingLeft, 0, width - paddingRight, this.mTopSelectionDividerTop);
                this.mVirtualButtonPressedDrawable.draw(canvas);
            }
            if (this.mIncrementVirtualButtonPressed) {
                this.mVirtualButtonPressedDrawable.setState(PRESSED_STATE_SET);
                this.mVirtualButtonPressedDrawable.setBounds(paddingLeft, this.mBottomSelectionDividerBottom, width - paddingRight, getBottom());
                this.mVirtualButtonPressedDrawable.draw(canvas);
            }
        }
        float f2 = this.mInitialScrollOffset + (this.mSelectorElementHeight * 2);
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        for (int i : this.mSelectorIndices) {
            String str = sparseArray.get(i);
            float abs = shouldShowNormalState() ? 2.0f : Math.abs(f2 - f) / this.mSelectorElementHeight;
            float textSize = getTextSize(abs, this.TEXT_SIZE_MAX, this.TEXT_SIZE_MIN);
            L.speech.d("getTextSize.posy=" + abs + ", scrollSelectorValue=" + str + "TEXT_SIZE_MAX=" + this.TEXT_SIZE_MAX);
            this.mSelectorWheelPaint.setTextSize(textSize);
            if (abs < 1.0f) {
                this.mSelectorWheelPaint.setColor(getAlphaGradient(abs, this.mSelectTextColor, true));
                this.mSelectorWheelPaint.setTextSize(this.mSelectTextSize);
                canvas.drawText(str, right, ((textSize - this.TEXT_SIZE_MIN) / 2.0f) + f, this.mSelectorWheelPaint);
            } else {
                this.mSelectorWheelPaint.setColor(getAlphaGradient(abs, this.mNormalTextColor, false));
                this.mSelectorWheelPaint.setTextSize(this.mNormalTextSize);
                canvas.drawText(str, right, ((textSize - this.TEXT_SIZE_MIN) / 2.0f) + f, this.mSelectorWheelPaint);
            }
            f += this.mSelectorElementHeight;
        }
        if (!TextUtils.isEmpty(this.mLabel)) {
            canvas.drawText(this.mLabel.toString(), right + (this.mDisplayedMaxTextWidth / 2.0f), f2 + ((this.TEXT_SIZE_MAX - this.TEXT_SIZE_MIN) / 2) + this.TEXT_MAX_ASCENT + this.TEXT_SIZE_LABEL, this.mLabelPaint);
        }
    }

    private boolean shouldShowNormalState() {
        return this.mNoHighlightBeforeChange && !this.mValueChanged;
    }

    private int getAlphaGradient(float f, int i, boolean z) {
        int i2;
        if (f >= 3.0f) {
            return i;
        }
        if (z) {
            i2 = (int) (((-f) * Color.alpha(i)) + Color.alpha(i));
        } else {
            i2 = (int) (f * Color.alpha(i));
        }
        return (i2 << 24) | (i & 16777215);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        if (Build.VERSION.SDK_INT >= 15) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setClassName(NumberPicker.class.getName());
            accessibilityEvent.setScrollable(true);
            accessibilityEvent.setScrollY((this.mMinValue + this.mValue) * this.mSelectorElementHeight);
            accessibilityEvent.setMaxScrollY((this.mMaxValue - this.mMinValue) * this.mSelectorElementHeight);
        }
    }

    @Override // android.view.View
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        if (!this.mHasSelectorWheel && Build.VERSION.SDK_INT >= 16) {
            return super.getAccessibilityNodeProvider();
        }
        if (this.mAccessibilityNodeProvider == null) {
            this.mAccessibilityNodeProvider = new AccessibilityNodeProviderImpl();
        }
        return this.mAccessibilityNodeProvider;
    }

    private int makeMeasureSpec(int i, int i2) {
        if (i2 == -1) {
            return i;
        }
        int size = View.MeasureSpec.getSize(i);
        int mode = View.MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE) {
            return View.MeasureSpec.makeMeasureSpec(Math.min(size, i2), 1073741824);
        }
        if (mode == 0) {
            return View.MeasureSpec.makeMeasureSpec(i2, 1073741824);
        }
        if (mode == 1073741824) {
            return i;
        }
        throw new IllegalArgumentException("Unknown measure mode: " + mode);
    }

    private int resolveSizeAndStateRespectingMinSize(int i, int i2, int i3) {
        if (i == -1) {
            return i2;
        }
        return Build.VERSION.SDK_INT >= 11 ? resolveSizeAndState(Math.max(i, i2), i3, 0) : i2;
    }

    private void initializeSelectorWheelIndices() {
        this.mSelectorIndexToStringCache.clear();
        int[] iArr = this.mSelectorIndices;
        int value = getValue();
        for (int i = 0; i < this.mSelectorIndices.length; i++) {
            int i2 = (i - 2) + value;
            if (this.mWrapSelectorWheel) {
                i2 = getWrappedSelectorIndex(i2);
            }
            iArr[i] = i2;
            ensureCachedScrollSelectorValue(iArr[i]);
        }
    }

    private void setValueInternal(int i, boolean z) {
        int i2;
        if (this.mValue != i) {
            this.mValueChanged = true;
            if (this.mWrapSelectorWheel) {
                i2 = getWrappedSelectorIndex(i);
            } else {
                i2 = Math.min(Math.max(i, this.mMinValue), this.mMaxValue);
            }
            int i3 = this.mValue;
            this.mValue = i2;
            updateInputTextView();
            if (z) {
                notifyChange(i3, i2);
            }
            initializeSelectorWheelIndices();
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeValueByOne(boolean z) {
        if (this.mHasSelectorWheel) {
            this.mInputText.setVisibility(4);
            if (!moveToFinalScrollerPosition(this.mFlingScroller)) {
                moveToFinalScrollerPosition(this.mAdjustScroller);
            }
            this.mPreviousScrollerY = 0;
            if (z) {
                this.mFlingScroller.startScroll(0, 0, 0, -this.mSelectorElementHeight, 300);
            } else {
                this.mFlingScroller.startScroll(0, 0, 0, this.mSelectorElementHeight, 300);
            }
            invalidate();
        } else if (z) {
            setValueInternal(this.mValue + 1, true);
        } else {
            setValueInternal(this.mValue - 1, true);
        }
    }

    private void initializeSelectorWheel() {
        initializeSelectorWheelIndices();
        int[] iArr = this.mSelectorIndices;
        this.mSelectorTextGapHeight = (int) ((((getBottom() - getTop()) - (iArr.length * this.mTextSize)) / iArr.length) + 0.5f);
        this.mSelectorElementHeight = this.mTextSize + this.mSelectorTextGapHeight;
        this.mInitialScrollOffset = (this.mInputText.getBaseline() + this.mInputText.getTop()) - (this.mSelectorElementHeight * 2);
        this.mCurrentScrollOffset = this.mInitialScrollOffset;
        updateInputTextView();
    }

    private void initializeFadingEdges() {
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(((getBottom() - getTop()) - this.mTextSize) / 2);
    }

    private void onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            if (!ensureScrollWheelAdjusted()) {
                updateInputTextView();
            }
            onScrollStateChange(0);
        } else if (this.mScrollState != 1) {
            updateInputTextView();
        }
    }

    private void onScrollStateChange(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
            OnScrollListener onScrollListener = this.mOnScrollListener;
            if (onScrollListener != null) {
                onScrollListener.onScrollStateChange(this, i);
            }
        }
    }

    private void fling(int i) {
        this.mPreviousScrollerY = 0;
        if (i > 0) {
            this.mFlingScroller.fling(0, 0, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        } else {
            this.mFlingScroller.fling(0, Integer.MAX_VALUE, 0, i, 0, 0, 0, Integer.MAX_VALUE);
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getWrappedSelectorIndex(int i) {
        int i2 = this.mMaxValue;
        if (i > i2) {
            int i3 = this.mMinValue;
            return (i3 + ((i - i2) % (i2 - i3))) - 1;
        }
        int i4 = this.mMinValue;
        return i < i4 ? (i2 - ((i4 - i) % (i2 - i4))) + 1 : i;
    }

    private void incrementSelectorIndices(int[] iArr) {
        int i = 0;
        while (i < iArr.length - 1) {
            int i2 = i + 1;
            iArr[i] = iArr[i2];
            i = i2;
        }
        int i3 = iArr[iArr.length - 2] + 1;
        if (this.mWrapSelectorWheel && i3 > this.mMaxValue) {
            i3 = this.mMinValue;
        }
        iArr[iArr.length - 1] = i3;
        ensureCachedScrollSelectorValue(i3);
    }

    private void decrementSelectorIndices(int[] iArr) {
        for (int length = iArr.length - 1; length > 0; length--) {
            iArr[length] = iArr[length - 1];
        }
        int i = iArr[1] - 1;
        if (this.mWrapSelectorWheel && i < this.mMinValue) {
            i = this.mMaxValue;
        }
        iArr[0] = i;
        ensureCachedScrollSelectorValue(i);
    }

    private void ensureCachedScrollSelectorValue(int i) {
        String str;
        SparseArray<String> sparseArray = this.mSelectorIndexToStringCache;
        if (sparseArray.get(i) == null) {
            int i2 = this.mMinValue;
            if (i < i2 || i > this.mMaxValue) {
                str = "";
            } else {
                String[] strArr = this.mDisplayedValues;
                if (strArr != null) {
                    str = strArr[i - i2];
                } else {
                    str = formatNumber(i);
                }
            }
            sparseArray.put(i, str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String formatNumber(int i) {
        Formatter formatter = this.mFormatter;
        return formatter != null ? formatter.format(i) : String.valueOf(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void validateInputTextView(View view) {
        String valueOf = String.valueOf(((TextView) view).getText());
        if (TextUtils.isEmpty(valueOf)) {
            updateInputTextView();
        } else {
            setValueInternal(getSelectedPos(valueOf), true);
        }
    }

    private boolean updateInputTextView() {
        String[] strArr = this.mDisplayedValues;
        String formatNumber = strArr == null ? formatNumber(this.mValue) : strArr[this.mValue - this.mMinValue];
        if (TextUtils.isEmpty(formatNumber) || formatNumber.equals(this.mInputText.getText().toString())) {
            return false;
        }
        this.mInputText.setText(formatNumber);
        return true;
    }

    private void notifyChange(int i, int i2) {
        this.mSoundPlayer.play(this.mSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        OnValueChangeListener onValueChangeListener = this.mOnValueChangeListener;
        if (onValueChangeListener != null) {
            onValueChangeListener.onValueChange(this, i, this.mValue);
        }
    }

    private void postChangeCurrentByOneFromLongPress(boolean z, long j) {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand == null) {
            this.mChangeCurrentByOneFromLongPressCommand = new ChangeCurrentByOneFromLongPressCommand();
        } else {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        this.mChangeCurrentByOneFromLongPressCommand.setStep(z);
        postDelayed(this.mChangeCurrentByOneFromLongPressCommand, j);
    }

    private void removeChangeCurrentByOneFromLongPress() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
    }

    private void postBeginSoftInputOnLongPressCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand == null) {
            this.mBeginSoftInputOnLongPressCommand = new BeginSoftInputOnLongPressCommand();
        } else {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        postDelayed(this.mBeginSoftInputOnLongPressCommand, ViewConfiguration.getLongPressTimeout());
    }

    private void removeBeginSoftInputCommand() {
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
    }

    private void removeAllCallbacks() {
        ChangeCurrentByOneFromLongPressCommand changeCurrentByOneFromLongPressCommand = this.mChangeCurrentByOneFromLongPressCommand;
        if (changeCurrentByOneFromLongPressCommand != null) {
            removeCallbacks(changeCurrentByOneFromLongPressCommand);
        }
        SetSelectionCommand setSelectionCommand = this.mSetSelectionCommand;
        if (setSelectionCommand != null) {
            removeCallbacks(setSelectionCommand);
        }
        BeginSoftInputOnLongPressCommand beginSoftInputOnLongPressCommand = this.mBeginSoftInputOnLongPressCommand;
        if (beginSoftInputOnLongPressCommand != null) {
            removeCallbacks(beginSoftInputOnLongPressCommand);
        }
        this.mPressedStateHelper.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getSelectedPos(String str) {
        try {
            if (this.mDisplayedValues == null) {
                return Integer.parseInt(str);
            }
            for (int i = 0; i < this.mDisplayedValues.length; i++) {
                str = str.toLowerCase();
                if (this.mDisplayedValues[i].toLowerCase().startsWith(str)) {
                    return this.mMinValue + i;
                }
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return this.mMinValue;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postSetSelectionCommand(int i, int i2) {
        SetSelectionCommand setSelectionCommand = this.mSetSelectionCommand;
        if (setSelectionCommand == null) {
            this.mSetSelectionCommand = new SetSelectionCommand();
        } else {
            removeCallbacks(setSelectionCommand);
        }
        this.mSetSelectionCommand.mSelectionStart = i;
        this.mSetSelectionCommand.mSelectionEnd = i2;
        post(this.mSetSelectionCommand);
    }

    /* loaded from: classes3.dex */
    class InputTextFilter extends NumberKeyListener {
        @Override // android.text.method.KeyListener
        public int getInputType() {
            return 1;
        }

        InputTextFilter() {
        }

        @Override // android.text.method.NumberKeyListener
        protected char[] getAcceptedChars() {
            return NumberPicker.DIGIT_CHARACTERS;
        }

        @Override // android.text.method.NumberKeyListener, android.text.InputFilter
        public CharSequence filter(CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            if (NumberPicker.this.mDisplayedValues == null) {
                CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
                if (filter == null) {
                    filter = charSequence.subSequence(i, i2);
                }
                String str = String.valueOf(spanned.subSequence(0, i3)) + ((Object) filter) + ((Object) spanned.subSequence(i4, spanned.length()));
                return "".equals(str) ? str : (NumberPicker.this.getSelectedPos(str) > NumberPicker.this.mMaxValue || str.length() > String.valueOf(NumberPicker.this.mMaxValue).length()) ? "" : filter;
            }
            String valueOf = String.valueOf(charSequence.subSequence(i, i2));
            if (TextUtils.isEmpty(valueOf)) {
                return "";
            }
            String str2 = String.valueOf(spanned.subSequence(0, i3)) + ((Object) valueOf) + ((Object) spanned.subSequence(i4, spanned.length()));
            String lowerCase = str2.toLowerCase();
            String[] strArr = NumberPicker.this.mDisplayedValues;
            for (String str3 : strArr) {
                if (str3.toLowerCase().startsWith(lowerCase)) {
                    NumberPicker.this.postSetSelectionCommand(str2.length(), str3.length());
                    return str3.subSequence(i3, str3.length());
                }
            }
            return "";
        }
    }

    private boolean ensureScrollWheelAdjusted() {
        int i;
        int i2 = this.mInitialScrollOffset - this.mCurrentScrollOffset;
        if (i2 == 0) {
            return false;
        }
        this.mPreviousScrollerY = 0;
        int abs = Math.abs(i2);
        int i3 = this.mSelectorElementHeight;
        if (abs > i3 / 2) {
            if (i2 > 0) {
                i3 = -i3;
            }
            i = i2 + i3;
        } else {
            i = i2;
        }
        this.mAdjustScroller.startScroll(0, 0, 0, i, 800);
        invalidate();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class PressedStateHelper implements Runnable {
        public static final int BUTTON_DECREMENT = 2;
        public static final int BUTTON_INCREMENT = 1;
        private final int MODE_PRESS = 1;
        private final int MODE_TAPPED = 2;
        private int mManagedButton;
        private int mMode;

        PressedStateHelper() {
        }

        public void cancel() {
            this.mMode = 0;
            this.mManagedButton = 0;
            NumberPicker.this.removeCallbacks(this);
            if (NumberPicker.this.mIncrementVirtualButtonPressed) {
                NumberPicker.this.mIncrementVirtualButtonPressed = false;
                NumberPicker numberPicker = NumberPicker.this;
                numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
            }
            if (NumberPicker.this.mDecrementVirtualButtonPressed) {
                NumberPicker.this.mDecrementVirtualButtonPressed = false;
                NumberPicker numberPicker2 = NumberPicker.this;
                numberPicker2.invalidate(0, 0, numberPicker2.getRight(), NumberPicker.this.mTopSelectionDividerTop);
            }
        }

        public void buttonPressDelayed(int i) {
            cancel();
            this.mMode = 1;
            this.mManagedButton = i;
            NumberPicker.this.postDelayed(this, ViewConfiguration.getTapTimeout());
        }

        public void buttonTapped(int i) {
            cancel();
            this.mMode = 2;
            this.mManagedButton = i;
            NumberPicker.this.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            switch (this.mMode) {
                case 1:
                    switch (this.mManagedButton) {
                        case 1:
                            NumberPicker.this.mIncrementVirtualButtonPressed = true;
                            NumberPicker numberPicker = NumberPicker.this;
                            numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        case 2:
                            NumberPicker.this.mDecrementVirtualButtonPressed = true;
                            NumberPicker numberPicker2 = NumberPicker.this;
                            numberPicker2.invalidate(0, 0, numberPicker2.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return;
                        default:
                            return;
                    }
                case 2:
                    switch (this.mManagedButton) {
                        case 1:
                            if (!NumberPicker.this.mIncrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker numberPicker3 = NumberPicker.this;
                            numberPicker3.mIncrementVirtualButtonPressed = true ^ numberPicker3.mIncrementVirtualButtonPressed;
                            NumberPicker numberPicker4 = NumberPicker.this;
                            numberPicker4.invalidate(0, numberPicker4.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                            return;
                        case 2:
                            if (!NumberPicker.this.mDecrementVirtualButtonPressed) {
                                NumberPicker.this.postDelayed(this, ViewConfiguration.getPressedStateDuration());
                            }
                            NumberPicker numberPicker5 = NumberPicker.this;
                            numberPicker5.mDecrementVirtualButtonPressed = true ^ numberPicker5.mDecrementVirtualButtonPressed;
                            NumberPicker numberPicker6 = NumberPicker.this;
                            numberPicker6.invalidate(0, 0, numberPicker6.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                            return;
                        default:
                            return;
                    }
                default:
                    return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class SetSelectionCommand implements Runnable {
        private int mSelectionEnd;
        private int mSelectionStart;

        SetSelectionCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPicker.this.mInputText.setSelection(this.mSelectionStart, this.mSelectionEnd);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class ChangeCurrentByOneFromLongPressCommand implements Runnable {
        private boolean mIncrement;

        ChangeCurrentByOneFromLongPressCommand() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setStep(boolean z) {
            this.mIncrement = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPicker.this.changeValueByOne(this.mIncrement);
            NumberPicker numberPicker = NumberPicker.this;
            numberPicker.postDelayed(this, numberPicker.mLongPressUpdateInterval);
        }
    }

    /* loaded from: classes3.dex */
    public static class CustomEditText extends AppCompatEditText {
        public CustomEditText(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override // android.widget.TextView
        public void onEditorAction(int i) {
            super.onEditorAction(i);
            if (i == 6) {
                clearFocus();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class BeginSoftInputOnLongPressCommand implements Runnable {
        BeginSoftInputOnLongPressCommand() {
        }

        @Override // java.lang.Runnable
        public void run() {
            NumberPicker.this.mIngonreMoveEvents = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class AccessibilityNodeProviderImpl extends AccessibilityNodeProvider {
        private static final int UNDEFINED = Integer.MIN_VALUE;
        private static final int VIRTUAL_VIEW_ID_DECREMENT = 3;
        private static final int VIRTUAL_VIEW_ID_INCREMENT = 1;
        private static final int VIRTUAL_VIEW_ID_INPUT = 2;
        private final Rect mTempRect = new Rect();
        private final int[] mTempArray = new int[2];
        private int mAccessibilityFocusedView = Integer.MIN_VALUE;

        AccessibilityNodeProviderImpl() {
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
            if (i == -1) {
                return createAccessibilityNodeInfoForNumberPicker(NumberPicker.this.getScrollX(), NumberPicker.this.getScrollY(), NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.getScrollY() + (NumberPicker.this.getBottom() - NumberPicker.this.getTop()));
            }
            switch (i) {
                case 1:
                    return createAccessibilityNodeInfoForVirtualButton(1, getVirtualIncrementButtonText(), NumberPicker.this.getScrollX(), NumberPicker.this.mBottomSelectionDividerBottom - NumberPicker.this.mSelectionDividerHeight, NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.getScrollY() + (NumberPicker.this.getBottom() - NumberPicker.this.getTop()));
                case 2:
                    return createAccessibiltyNodeInfoForInputText(NumberPicker.this.getScrollX(), NumberPicker.this.mTopSelectionDividerTop + NumberPicker.this.mSelectionDividerHeight, NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.mBottomSelectionDividerBottom - NumberPicker.this.mSelectionDividerHeight);
                case 3:
                    return createAccessibilityNodeInfoForVirtualButton(3, getVirtualDecrementButtonText(), NumberPicker.this.getScrollX(), NumberPicker.this.getScrollY(), NumberPicker.this.getScrollX() + (NumberPicker.this.getRight() - NumberPicker.this.getLeft()), NumberPicker.this.mTopSelectionDividerTop + NumberPicker.this.mSelectionDividerHeight);
                default:
                    return super.createAccessibilityNodeInfo(i);
            }
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
            if (TextUtils.isEmpty(str)) {
                return Collections.emptyList();
            }
            String lowerCase = str.toLowerCase();
            ArrayList arrayList = new ArrayList();
            if (i != -1) {
                switch (i) {
                    case 1:
                    case 2:
                    case 3:
                        findAccessibilityNodeInfosByTextInChild(lowerCase, i, arrayList);
                        return arrayList;
                    default:
                        return super.findAccessibilityNodeInfosByText(str, i);
                }
            } else {
                findAccessibilityNodeInfosByTextInChild(lowerCase, 3, arrayList);
                findAccessibilityNodeInfosByTextInChild(lowerCase, 2, arrayList);
                findAccessibilityNodeInfosByTextInChild(lowerCase, 1, arrayList);
                return arrayList;
            }
        }

        @Override // android.view.accessibility.AccessibilityNodeProvider
        public boolean performAction(int i, int i2, Bundle bundle) {
            boolean z = false;
            if (i != -1) {
                switch (i) {
                    case 1:
                        if (i2 != 16) {
                            if (i2 != 64) {
                                if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                                    return false;
                                }
                                this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                sendAccessibilityEventForVirtualView(i, 65536);
                                NumberPicker numberPicker = NumberPicker.this;
                                numberPicker.invalidate(0, numberPicker.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                                return true;
                            } else if (this.mAccessibilityFocusedView == i) {
                                return false;
                            } else {
                                this.mAccessibilityFocusedView = i;
                                sendAccessibilityEventForVirtualView(i, 32768);
                                NumberPicker numberPicker2 = NumberPicker.this;
                                numberPicker2.invalidate(0, numberPicker2.mBottomSelectionDividerBottom, NumberPicker.this.getRight(), NumberPicker.this.getBottom());
                                return true;
                            }
                        } else if (!NumberPicker.this.isEnabled()) {
                            return false;
                        } else {
                            NumberPicker.this.changeValueByOne(true);
                            sendAccessibilityEventForVirtualView(i, 1);
                            return true;
                        }
                    case 2:
                        if (i2 == 16) {
                            return NumberPicker.this.isEnabled();
                        }
                        if (i2 != 64) {
                            if (i2 != 128) {
                                switch (i2) {
                                    case 1:
                                        if (!NumberPicker.this.isEnabled() || NumberPicker.this.mInputText.isFocused()) {
                                            return false;
                                        }
                                        return NumberPicker.this.mInputText.requestFocus();
                                    case 2:
                                        if (!NumberPicker.this.isEnabled() || !NumberPicker.this.mInputText.isFocused()) {
                                            return false;
                                        }
                                        NumberPicker.this.mInputText.clearFocus();
                                        return true;
                                    default:
                                        return NumberPicker.this.mInputText.performAccessibilityAction(i2, bundle);
                                }
                            } else if (this.mAccessibilityFocusedView != i) {
                                return false;
                            } else {
                                this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                sendAccessibilityEventForVirtualView(i, 65536);
                                NumberPicker.this.mInputText.invalidate();
                                return true;
                            }
                        } else if (this.mAccessibilityFocusedView == i) {
                            return false;
                        } else {
                            this.mAccessibilityFocusedView = i;
                            sendAccessibilityEventForVirtualView(i, 32768);
                            NumberPicker.this.mInputText.invalidate();
                            return true;
                        }
                    case 3:
                        if (i2 != 16) {
                            if (i2 != 64) {
                                if (i2 != 128 || this.mAccessibilityFocusedView != i) {
                                    return false;
                                }
                                this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                                sendAccessibilityEventForVirtualView(i, 65536);
                                NumberPicker numberPicker3 = NumberPicker.this;
                                numberPicker3.invalidate(0, 0, numberPicker3.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                                return true;
                            } else if (this.mAccessibilityFocusedView == i) {
                                return false;
                            } else {
                                this.mAccessibilityFocusedView = i;
                                sendAccessibilityEventForVirtualView(i, 32768);
                                NumberPicker numberPicker4 = NumberPicker.this;
                                numberPicker4.invalidate(0, 0, numberPicker4.getRight(), NumberPicker.this.mTopSelectionDividerTop);
                                return true;
                            }
                        } else if (!NumberPicker.this.isEnabled()) {
                            return false;
                        } else {
                            if (i == 1) {
                                z = true;
                            }
                            NumberPicker.this.changeValueByOne(z);
                            sendAccessibilityEventForVirtualView(i, 1);
                            return true;
                        }
                }
            } else if (i2 != 64) {
                if (i2 != 128) {
                    if (i2 != 4096) {
                        if (i2 == 8192) {
                            if (!NumberPicker.this.isEnabled() || (!NumberPicker.this.getWrapSelectorWheel() && NumberPicker.this.getValue() <= NumberPicker.this.getMinValue())) {
                                return false;
                            }
                            NumberPicker.this.changeValueByOne(false);
                            return true;
                        }
                    } else if (!NumberPicker.this.isEnabled() || (!NumberPicker.this.getWrapSelectorWheel() && NumberPicker.this.getValue() >= NumberPicker.this.getMaxValue())) {
                        return false;
                    } else {
                        NumberPicker.this.changeValueByOne(true);
                        return true;
                    }
                } else if (this.mAccessibilityFocusedView != i) {
                    return false;
                } else {
                    this.mAccessibilityFocusedView = Integer.MIN_VALUE;
                    return true;
                }
            } else if (this.mAccessibilityFocusedView == i) {
                return false;
            } else {
                this.mAccessibilityFocusedView = i;
                return true;
            }
            return super.performAction(i, i2, bundle);
        }

        public void sendAccessibilityEventForVirtualView(int i, int i2) {
            switch (i) {
                case 1:
                    if (hasVirtualIncrementButton()) {
                        sendAccessibilityEventForVirtualButton(i, i2, getVirtualIncrementButtonText());
                        return;
                    }
                    return;
                case 2:
                    sendAccessibilityEventForVirtualText(i2);
                    return;
                case 3:
                    if (hasVirtualDecrementButton()) {
                        sendAccessibilityEventForVirtualButton(i, i2, getVirtualDecrementButtonText());
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        private void sendAccessibilityEventForVirtualText(int i) {
            if (((AccessibilityManager) NumberPicker.this.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
                NumberPicker.this.mInputText.onInitializeAccessibilityEvent(obtain);
                NumberPicker.this.mInputText.onPopulateAccessibilityEvent(obtain);
                obtain.setSource(NumberPicker.this, 2);
                NumberPicker numberPicker = NumberPicker.this;
                numberPicker.requestSendAccessibilityEvent(numberPicker, obtain);
            }
        }

        private void sendAccessibilityEventForVirtualButton(int i, int i2, String str) {
            if (((AccessibilityManager) NumberPicker.this.getContext().getSystemService("accessibility")).isEnabled()) {
                AccessibilityEvent obtain = AccessibilityEvent.obtain(i2);
                obtain.setClassName(Button.class.getName());
                obtain.setPackageName(NumberPicker.this.getContext().getPackageName());
                obtain.getText().add(str);
                obtain.setEnabled(NumberPicker.this.isEnabled());
                obtain.setSource(NumberPicker.this, i);
                NumberPicker numberPicker = NumberPicker.this;
                numberPicker.requestSendAccessibilityEvent(numberPicker, obtain);
            }
        }

        private void findAccessibilityNodeInfosByTextInChild(String str, int i, List<AccessibilityNodeInfo> list) {
            switch (i) {
                case 1:
                    String virtualIncrementButtonText = getVirtualIncrementButtonText();
                    if (!TextUtils.isEmpty(virtualIncrementButtonText) && virtualIncrementButtonText.toLowerCase().contains(str)) {
                        list.add(createAccessibilityNodeInfo(1));
                        return;
                    }
                    return;
                case 2:
                    Editable text = NumberPicker.this.mInputText.getText();
                    if (TextUtils.isEmpty(text) || !text.toString().toLowerCase().contains(str)) {
                        Editable text2 = NumberPicker.this.mInputText.getText();
                        if (!TextUtils.isEmpty(text2) && text2.toString().toLowerCase().contains(str)) {
                            list.add(createAccessibilityNodeInfo(2));
                            return;
                        }
                        return;
                    }
                    list.add(createAccessibilityNodeInfo(2));
                    return;
                case 3:
                    String virtualDecrementButtonText = getVirtualDecrementButtonText();
                    if (!TextUtils.isEmpty(virtualDecrementButtonText) && virtualDecrementButtonText.toLowerCase().contains(str)) {
                        list.add(createAccessibilityNodeInfo(3));
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        private AccessibilityNodeInfo createAccessibiltyNodeInfoForInputText(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo createAccessibilityNodeInfo = NumberPicker.this.mInputText.createAccessibilityNodeInfo();
            createAccessibilityNodeInfo.setSource(NumberPicker.this, 2);
            if (this.mAccessibilityFocusedView != 2) {
                createAccessibilityNodeInfo.addAction(64);
            }
            if (this.mAccessibilityFocusedView == 2) {
                createAccessibilityNodeInfo.addAction(128);
            }
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            createAccessibilityNodeInfo.setVisibleToUser(NumberPicker.this.getVisibility() == 0);
            createAccessibilityNodeInfo.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            createAccessibilityNodeInfo.setBoundsInScreen(rect);
            return createAccessibilityNodeInfo;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForVirtualButton(int i, String str, int i2, int i3, int i4, int i5) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setClassName(Button.class.getName());
            obtain.setPackageName(NumberPicker.this.getContext().getPackageName());
            obtain.setSource(NumberPicker.this, i);
            obtain.setParent(NumberPicker.this);
            obtain.setText(str);
            obtain.setClickable(true);
            obtain.setLongClickable(true);
            obtain.setEnabled(NumberPicker.this.isEnabled());
            Rect rect = this.mTempRect;
            rect.set(i2, i3, i4, i5);
            obtain.setVisibleToUser(NumberPicker.this.getVisibility() == 0);
            obtain.setBoundsInParent(rect);
            int[] iArr = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != i) {
                obtain.addAction(64);
            }
            if (this.mAccessibilityFocusedView == i) {
                obtain.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                obtain.addAction(16);
            }
            return obtain;
        }

        private AccessibilityNodeInfo createAccessibilityNodeInfoForNumberPicker(int i, int i2, int i3, int i4) {
            AccessibilityNodeInfo obtain = AccessibilityNodeInfo.obtain();
            obtain.setClassName(NumberPicker.class.getName());
            obtain.setPackageName(NumberPicker.this.getContext().getPackageName());
            obtain.setSource(NumberPicker.this);
            if (hasVirtualDecrementButton()) {
                obtain.addChild(NumberPicker.this, 3);
            }
            obtain.addChild(NumberPicker.this, 2);
            if (hasVirtualIncrementButton()) {
                obtain.addChild(NumberPicker.this, 1);
            }
            obtain.setParent((View) NumberPicker.this.getParentForAccessibility());
            obtain.setEnabled(NumberPicker.this.isEnabled());
            obtain.setScrollable(true);
            Rect rect = this.mTempRect;
            rect.set(i, i2, i3, i4);
            obtain.setBoundsInParent(rect);
            obtain.setVisibleToUser(NumberPicker.this.getVisibility() == 0);
            int[] iArr = this.mTempArray;
            NumberPicker.this.getLocationOnScreen(iArr);
            rect.offset(iArr[0], iArr[1]);
            obtain.setBoundsInScreen(rect);
            if (this.mAccessibilityFocusedView != -1) {
                obtain.addAction(64);
            }
            if (this.mAccessibilityFocusedView == -1) {
                obtain.addAction(128);
            }
            if (NumberPicker.this.isEnabled()) {
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue()) {
                    obtain.addAction(4096);
                }
                if (NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue()) {
                    obtain.addAction(8192);
                }
            }
            return obtain;
        }

        private boolean hasVirtualDecrementButton() {
            return NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() > NumberPicker.this.getMinValue();
        }

        private boolean hasVirtualIncrementButton() {
            return NumberPicker.this.getWrapSelectorWheel() || NumberPicker.this.getValue() < NumberPicker.this.getMaxValue();
        }

        private String getVirtualDecrementButtonText() {
            int i = NumberPicker.this.mValue - 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                i = NumberPicker.this.getWrappedSelectorIndex(i);
            }
            if (i >= NumberPicker.this.mMinValue) {
                return NumberPicker.this.mDisplayedValues == null ? NumberPicker.this.formatNumber(i) : NumberPicker.this.mDisplayedValues[i - NumberPicker.this.mMinValue];
            }
            return null;
        }

        private String getVirtualIncrementButtonText() {
            int i = NumberPicker.this.mValue + 1;
            if (NumberPicker.this.mWrapSelectorWheel) {
                i = NumberPicker.this.getWrappedSelectorIndex(i);
            }
            if (i <= NumberPicker.this.mMaxValue) {
                return NumberPicker.this.mDisplayedValues == null ? NumberPicker.this.formatNumber(i) : NumberPicker.this.mDisplayedValues[i - NumberPicker.this.mMinValue];
            }
            return null;
        }
    }

    private void refreshWheel() {
        initializeSelectorWheelIndices();
        invalidate();
    }

    public void setSelectTextColor(int i) {
        this.mSelectTextColor = getResources().getColor(i);
    }

    public void setNormalTextColor(int i) {
        this.mNormalTextColor = getResources().getColor(i);
    }

    public void setSelectTextSize(int i) {
        this.mSelectTextSize = getResources().getDimensionPixelSize(i);
    }

    public void setNormalTextSize(int i) {
        this.mNormalTextSize = getResources().getDimensionPixelSize(i);
    }
}
