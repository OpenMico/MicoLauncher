package com.rd;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.TextUtilsCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.rd.IndicatorManager;
import com.rd.animation.type.AnimationType;
import com.rd.draw.controller.DrawController;
import com.rd.draw.data.Indicator;
import com.rd.draw.data.Orientation;
import com.rd.draw.data.PositionSavedState;
import com.rd.draw.data.RtlMode;
import com.rd.utils.CoordinatesUtils;
import com.rd.utils.DensityUtils;
import com.rd.utils.IdUtils;

/* loaded from: classes2.dex */
public class PageIndicatorView extends View implements View.OnTouchListener, ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener, IndicatorManager.a {
    private static final Handler a = new Handler(Looper.getMainLooper());
    private IndicatorManager b;
    private DataSetObserver c;
    private ViewPager d;
    private boolean e;
    private Runnable f = new Runnable() { // from class: com.rd.PageIndicatorView.2
        @Override // java.lang.Runnable
        public void run() {
            PageIndicatorView.this.b.indicator().setIdle(true);
            PageIndicatorView.this.i();
        }
    };

    public PageIndicatorView(Context context) {
        super(context);
        a((AttributeSet) null);
    }

    public PageIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(attributeSet);
    }

    public PageIndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(attributeSet);
    }

    @TargetApi(21)
    public PageIndicatorView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        a(attributeSet);
    }

    @Override // android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        a(getParent());
    }

    @Override // android.view.View
    protected void onDetachedFromWindow() {
        c();
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Indicator indicator = this.b.indicator();
        PositionSavedState positionSavedState = new PositionSavedState(super.onSaveInstanceState());
        positionSavedState.setSelectedPosition(indicator.getSelectedPosition());
        positionSavedState.setSelectingPosition(indicator.getSelectingPosition());
        positionSavedState.setLastSelectedPosition(indicator.getLastSelectedPosition());
        return positionSavedState;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof PositionSavedState) {
            Indicator indicator = this.b.indicator();
            PositionSavedState positionSavedState = (PositionSavedState) parcelable;
            indicator.setSelectedPosition(positionSavedState.getSelectedPosition());
            indicator.setSelectingPosition(positionSavedState.getSelectingPosition());
            indicator.setLastSelectedPosition(positionSavedState.getLastSelectedPosition());
            super.onRestoreInstanceState(positionSavedState.getSuperState());
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        Pair<Integer, Integer> measureViewSize = this.b.drawer().measureViewSize(i, i2);
        setMeasuredDimension(((Integer) measureViewSize.first).intValue(), ((Integer) measureViewSize.second).intValue());
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.b.drawer().draw(canvas);
    }

    @Override // android.view.View
    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.b.drawer().touch(motionEvent);
        return true;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.b.indicator().isFadeOnIdle()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                k();
                break;
            case 1:
                j();
                break;
        }
        return false;
    }

    @Override // com.rd.IndicatorManager.a
    public void onIndicatorUpdated() {
        invalidate();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
        a(i, f);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        a(i);
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
        if (i == 0) {
            this.b.indicator().setInteractiveAnimation(this.e);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnAdapterChangeListener
    public void onAdapterChanged(@NonNull ViewPager viewPager, @Nullable PagerAdapter pagerAdapter, @Nullable PagerAdapter pagerAdapter2) {
        DataSetObserver dataSetObserver;
        if (this.b.indicator().isDynamicCount()) {
            if (!(pagerAdapter == null || (dataSetObserver = this.c) == null)) {
                pagerAdapter.unregisterDataSetObserver(dataSetObserver);
                this.c = null;
            }
            b();
        }
        d();
    }

    public void setCount(int i) {
        if (i >= 0 && this.b.indicator().getCount() != i) {
            this.b.indicator().setCount(i);
            e();
            requestLayout();
        }
    }

    public int getCount() {
        return this.b.indicator().getCount();
    }

    public void setDynamicCount(boolean z) {
        this.b.indicator().setDynamicCount(z);
        if (z) {
            b();
        } else {
            c();
        }
    }

    public void setFadeOnIdle(boolean z) {
        this.b.indicator().setFadeOnIdle(z);
        if (z) {
            j();
        } else {
            k();
        }
    }

    public void setRadius(int i) {
        if (i < 0) {
            i = 0;
        }
        this.b.indicator().setRadius(DensityUtils.dpToPx(i));
        invalidate();
    }

    public void setRadius(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.b.indicator().setRadius((int) f);
        invalidate();
    }

    public int getRadius() {
        return this.b.indicator().getRadius();
    }

    public void setPadding(int i) {
        if (i < 0) {
            i = 0;
        }
        this.b.indicator().setPadding(DensityUtils.dpToPx(i));
        invalidate();
    }

    public void setPadding(float f) {
        if (f < 0.0f) {
            f = 0.0f;
        }
        this.b.indicator().setPadding((int) f);
        invalidate();
    }

    public int getPadding() {
        return this.b.indicator().getPadding();
    }

    public void setScaleFactor(float f) {
        if (f > 1.0f) {
            f = 1.0f;
        } else if (f < 0.3f) {
            f = 0.3f;
        }
        this.b.indicator().setScaleFactor(f);
    }

    public float getScaleFactor() {
        return this.b.indicator().getScaleFactor();
    }

    public void setStrokeWidth(float f) {
        int radius = this.b.indicator().getRadius();
        if (f < 0.0f) {
            f = 0.0f;
        } else {
            float f2 = radius;
            if (f > f2) {
                f = f2;
            }
        }
        this.b.indicator().setStroke((int) f);
        invalidate();
    }

    public void setStrokeWidth(int i) {
        int dpToPx = DensityUtils.dpToPx(i);
        int radius = this.b.indicator().getRadius();
        if (dpToPx < 0) {
            dpToPx = 0;
        } else if (dpToPx > radius) {
            dpToPx = radius;
        }
        this.b.indicator().setStroke(dpToPx);
        invalidate();
    }

    public int getStrokeWidth() {
        return this.b.indicator().getStroke();
    }

    public void setSelectedColor(int i) {
        this.b.indicator().setSelectedColor(i);
        invalidate();
    }

    public int getSelectedColor() {
        return this.b.indicator().getSelectedColor();
    }

    public void setUnselectedColor(int i) {
        this.b.indicator().setUnselectedColor(i);
        invalidate();
    }

    public int getUnselectedColor() {
        return this.b.indicator().getUnselectedColor();
    }

    public void setAutoVisibility(boolean z) {
        if (!z) {
            setVisibility(0);
        }
        this.b.indicator().setAutoVisibility(z);
        e();
    }

    public void setOrientation(@Nullable Orientation orientation) {
        if (orientation != null) {
            this.b.indicator().setOrientation(orientation);
            requestLayout();
        }
    }

    public void setAnimationDuration(long j) {
        this.b.indicator().setAnimationDuration(j);
    }

    public void setIdleDuration(long j) {
        this.b.indicator().setIdleDuration(j);
        if (this.b.indicator().isFadeOnIdle()) {
            j();
        } else {
            k();
        }
    }

    public long getAnimationDuration() {
        return this.b.indicator().getAnimationDuration();
    }

    public void setAnimationType(@Nullable AnimationType animationType) {
        this.b.onValueUpdated(null);
        if (animationType != null) {
            this.b.indicator().setAnimationType(animationType);
        } else {
            this.b.indicator().setAnimationType(AnimationType.NONE);
        }
        invalidate();
    }

    public void setInteractiveAnimation(boolean z) {
        this.b.indicator().setInteractiveAnimation(z);
        this.e = z;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public void setViewPager(@Nullable ViewPager viewPager) {
        releaseViewPager();
        if (viewPager != null) {
            this.d = viewPager;
            this.d.addOnPageChangeListener(this);
            this.d.addOnAdapterChangeListener(this);
            this.d.setOnTouchListener(this);
            this.b.indicator().setViewPagerId(this.d.getId());
            setDynamicCount(this.b.indicator().isDynamicCount());
            d();
        }
    }

    public void releaseViewPager() {
        ViewPager viewPager = this.d;
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this);
            this.d.removeOnAdapterChangeListener(this);
            this.d = null;
        }
    }

    public void setRtlMode(@Nullable RtlMode rtlMode) {
        Indicator indicator = this.b.indicator();
        if (rtlMode == null) {
            indicator.setRtlMode(RtlMode.Off);
        } else {
            indicator.setRtlMode(rtlMode);
        }
        if (this.d != null) {
            int selectedPosition = indicator.getSelectedPosition();
            if (f()) {
                selectedPosition = (indicator.getCount() - 1) - selectedPosition;
            } else {
                ViewPager viewPager = this.d;
                if (viewPager != null) {
                    selectedPosition = viewPager.getCurrentItem();
                }
            }
            indicator.setLastSelectedPosition(selectedPosition);
            indicator.setSelectingPosition(selectedPosition);
            indicator.setSelectedPosition(selectedPosition);
            invalidate();
        }
    }

    public int getSelection() {
        return this.b.indicator().getSelectedPosition();
    }

    public void setSelection(int i) {
        Indicator indicator = this.b.indicator();
        int b = b(i);
        if (b != indicator.getSelectedPosition() && b != indicator.getSelectingPosition()) {
            indicator.setInteractiveAnimation(false);
            indicator.setLastSelectedPosition(indicator.getSelectedPosition());
            indicator.setSelectingPosition(b);
            indicator.setSelectedPosition(b);
            this.b.animate().basic();
        }
    }

    public void setSelected(int i) {
        Indicator indicator = this.b.indicator();
        AnimationType animationType = indicator.getAnimationType();
        indicator.setAnimationType(AnimationType.NONE);
        setSelection(i);
        indicator.setAnimationType(animationType);
    }

    public void clearSelection() {
        Indicator indicator = this.b.indicator();
        indicator.setInteractiveAnimation(false);
        indicator.setLastSelectedPosition(-1);
        indicator.setSelectingPosition(-1);
        indicator.setSelectedPosition(-1);
        this.b.animate().basic();
    }

    public void setProgress(int i, float f) {
        Indicator indicator = this.b.indicator();
        if (indicator.isInteractiveAnimation()) {
            int count = indicator.getCount();
            if (count <= 0 || i < 0) {
                i = 0;
            } else {
                int i2 = count - 1;
                if (i > i2) {
                    i = i2;
                }
            }
            if (f < 0.0f) {
                f = 0.0f;
            } else if (f > 1.0f) {
                f = 1.0f;
            }
            if (f == 1.0f) {
                indicator.setLastSelectedPosition(indicator.getSelectedPosition());
                indicator.setSelectedPosition(i);
            }
            indicator.setSelectingPosition(i);
            this.b.animate().interactive(f);
        }
    }

    public void setClickListener(@Nullable DrawController.ClickListener clickListener) {
        this.b.drawer().setClickListener(clickListener);
    }

    private void a(@Nullable AttributeSet attributeSet) {
        a();
        b(attributeSet);
        if (this.b.indicator().isFadeOnIdle()) {
            j();
        }
    }

    private void a() {
        if (getId() == -1) {
            setId(IdUtils.generateViewId());
        }
    }

    private void b(@Nullable AttributeSet attributeSet) {
        this.b = new IndicatorManager(this);
        this.b.drawer().initAttributes(getContext(), attributeSet);
        Indicator indicator = this.b.indicator();
        indicator.setPaddingLeft(getPaddingLeft());
        indicator.setPaddingTop(getPaddingTop());
        indicator.setPaddingRight(getPaddingRight());
        indicator.setPaddingBottom(getPaddingBottom());
        this.e = indicator.isInteractiveAnimation();
    }

    private void b() {
        ViewPager viewPager;
        if (this.c == null && (viewPager = this.d) != null && viewPager.getAdapter() != null) {
            this.c = new DataSetObserver() { // from class: com.rd.PageIndicatorView.1
                @Override // android.database.DataSetObserver
                public void onChanged() {
                    PageIndicatorView.this.d();
                }
            };
            try {
                this.d.getAdapter().registerDataSetObserver(this.c);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    private void c() {
        ViewPager viewPager;
        if (this.c != null && (viewPager = this.d) != null && viewPager.getAdapter() != null) {
            try {
                this.d.getAdapter().unregisterDataSetObserver(this.c);
                this.c = null;
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        ViewPager viewPager = this.d;
        if (viewPager != null && viewPager.getAdapter() != null) {
            int count = this.d.getAdapter().getCount();
            int currentItem = f() ? (count - 1) - this.d.getCurrentItem() : this.d.getCurrentItem();
            this.b.indicator().setSelectedPosition(currentItem);
            this.b.indicator().setSelectingPosition(currentItem);
            this.b.indicator().setLastSelectedPosition(currentItem);
            this.b.indicator().setCount(count);
            this.b.animate().end();
            e();
            requestLayout();
        }
    }

    private void e() {
        if (this.b.indicator().isAutoVisibility()) {
            int count = this.b.indicator().getCount();
            int visibility = getVisibility();
            if (visibility != 0 && count > 1) {
                setVisibility(0);
            } else if (visibility != 4 && count <= 1) {
                setVisibility(4);
            }
        }
    }

    private void a(int i) {
        Indicator indicator = this.b.indicator();
        boolean g = g();
        int count = indicator.getCount();
        if (g) {
            if (f()) {
                i = (count - 1) - i;
            }
            setSelection(i);
        }
    }

    private void a(int i, float f) {
        Indicator indicator = this.b.indicator();
        if (g() && indicator.isInteractiveAnimation() && indicator.getAnimationType() != AnimationType.NONE) {
            Pair<Integer, Float> progress = CoordinatesUtils.getProgress(indicator, i, f, f());
            setProgress(((Integer) progress.first).intValue(), ((Float) progress.second).floatValue());
        }
    }

    private boolean f() {
        switch (this.b.indicator().getRtlMode()) {
            case On:
                return true;
            case Off:
                return false;
            case Auto:
                return TextUtilsCompat.getLayoutDirectionFromLocale(getContext().getResources().getConfiguration().locale) == 1;
            default:
                return false;
        }
    }

    private boolean g() {
        return (getMeasuredHeight() == 0 && getMeasuredWidth() == 0) ? false : true;
    }

    private void a(@Nullable ViewParent viewParent) {
        if (viewParent != null && (viewParent instanceof ViewGroup) && ((ViewGroup) viewParent).getChildCount() > 0) {
            ViewPager a2 = a((ViewGroup) viewParent, this.b.indicator().getViewPagerId());
            if (a2 != null) {
                setViewPager(a2);
            } else {
                a(viewParent.getParent());
            }
        }
    }

    @Nullable
    private ViewPager a(@NonNull ViewGroup viewGroup, int i) {
        View findViewById;
        if (viewGroup.getChildCount() > 0 && (findViewById = viewGroup.findViewById(i)) != null && (findViewById instanceof ViewPager)) {
            return (ViewPager) findViewById;
        }
        return null;
    }

    private int b(int i) {
        int count = this.b.indicator().getCount() - 1;
        if (i < 0) {
            return 0;
        }
        return i > count ? count : i;
    }

    private void h() {
        animate().cancel();
        animate().alpha(1.0f).setDuration(250L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void i() {
        animate().cancel();
        animate().alpha(0.0f).setDuration(250L);
    }

    private void j() {
        a.removeCallbacks(this.f);
        a.postDelayed(this.f, this.b.indicator().getIdleDuration());
    }

    private void k() {
        a.removeCallbacks(this.f);
        h();
    }
}
