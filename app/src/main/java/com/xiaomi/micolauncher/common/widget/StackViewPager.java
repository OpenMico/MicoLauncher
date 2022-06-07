package com.xiaomi.micolauncher.common.widget;

import android.animation.Animator;
import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.elvishew.xlog.Logger;
import com.elvishew.xlog.XLog;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.adapter.MyFragmentPagerAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class StackViewPager extends FrameLayout {
    private static final boolean DEBUG = false;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final boolean USE_CACHE = false;
    private MyFragmentPagerAdapter mAdapter;
    private int mCapturedViewLeft;
    private int mCapturedViewTop;
    private MotionEvent mCurrentEvent;
    private ArrayList<ViewPager.OnPageChangeListener> mOnPageChangeListeners;
    private boolean mScrollingCacheEnabled;
    ViewDragHelper mViewDragHelper;
    private int newCurrItem;
    private float offsetDelta;
    private static final String TAG = "StackViewPager";
    private static final Logger logger = XLog.tag(TAG).build();
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() { // from class: com.xiaomi.micolauncher.common.widget.StackViewPager.1
        public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    };
    private MyDataSetObserver dataSetObserver = new MyDataSetObserver();
    private List<ItemInfo> mItems = new ArrayList();
    private int mCurItem = -1;
    private int mPrevItem = -1;

    /* loaded from: classes3.dex */
    public enum Direction {
        none,
        auto,
        left,
        right
    }

    /* loaded from: classes3.dex */
    public interface PageTransformer {
        boolean onPageEnter(Direction direction);

        boolean transformPage(int i, int i2, int i3, int i4);
    }

    public StackViewPager(@NonNull Context context) {
        super(context);
        initViewPager();
    }

    public StackViewPager(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        initViewPager();
    }

    private void initViewPager() {
        this.mViewDragHelper = ViewDragHelper.create(this, new ViewDragHelper.Callback() { // from class: com.xiaomi.micolauncher.common.widget.StackViewPager.2
            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public boolean tryCaptureView(@NonNull View view, int i) {
                if (view.getVisibility() != 0) {
                    return false;
                }
                StackViewPager stackViewPager = StackViewPager.this;
                if (stackViewPager.canScroll(view, false, 1, (int) stackViewPager.mCurrentEvent.getX(i), (int) StackViewPager.this.mCurrentEvent.getY(i))) {
                    return false;
                }
                StackViewPager.this.offsetDelta = 0.0f;
                view.clearAnimation();
                view.setTranslationX(0.0f);
                return true;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int clampViewPositionHorizontal(@NonNull View view, int i, int i2) {
                StackViewPager.this.offsetDelta += i2 * 0.2f;
                L.launcher.d("clampViewPositionHorizontal left: %f dx:%d", Float.valueOf(StackViewPager.this.offsetDelta), Integer.valueOf(i2));
                return (int) StackViewPager.this.offsetDelta;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewReleased(@NonNull View view, float f, float f2) {
                super.onViewReleased(view, f, f2);
                L.launcher.d("onViewReleased current alpha %f", Float.valueOf(view.getAlpha()));
                Logger logger2 = L.launcher;
                logger2.d("releasedChild.getLeft:" + view.getLeft() + " width:" + view.getWidth());
                if (StackViewPager.this.mAdapter.getCount() <= 1 || Math.abs(view.getLeft()) <= view.getWidth() * 0.035d) {
                    StackViewPager.this.mViewDragHelper.settleCapturedViewAt(0, 0);
                    L.launcher.d("reset to origin position");
                } else {
                    L.launcher.d("try to scroll next page");
                    int i = view.getX() > 0.0f ? -1 : 1;
                    int currentItem = ((StackViewPager.this.getCurrentItem() + i) + StackViewPager.this.mAdapter.getCount()) % StackViewPager.this.mAdapter.getCount();
                    L.launcher.d("currentItem:%d next:%d", Integer.valueOf(StackViewPager.this.getCurrentItem()), Integer.valueOf(currentItem));
                    if (i == 1) {
                        StackViewPager.this.setCurrentItem(currentItem, Direction.left);
                    } else {
                        StackViewPager.this.setCurrentItem(currentItem, Direction.right);
                    }
                    view.setAlpha(1.0f);
                    view.setX(0.0f);
                    view.setTranslationX(0.0f);
                }
                StackViewPager.this.invalidate();
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public void onViewPositionChanged(@NonNull View view, int i, int i2, int i3, int i4) {
                super.onViewPositionChanged(view, i, i2, i3, i4);
                StackViewPager.this.mCapturedViewLeft = i;
                StackViewPager.this.mCapturedViewTop = i2;
                boolean z = false;
                if (view.getTag() != null && (view.getTag() instanceof PageTransformer) && ((PageTransformer) view.getTag()).transformPage(i, i2, i3, i4)) {
                    z = true;
                }
                if (!z) {
                    view.setAlpha(Math.max(0.0f, 1.0f - (((Math.abs(i) * 5.0f) * 3.5f) / view.getWidth())));
                }
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getOrderedChildIndex(int i) {
                for (int childCount = StackViewPager.this.getChildCount() - 1; childCount >= 0; childCount--) {
                    if (StackViewPager.this.getChildAt(childCount).getVisibility() == 0) {
                        return childCount;
                    }
                }
                return 0;
            }

            @Override // androidx.customview.widget.ViewDragHelper.Callback
            public int getViewHorizontalDragRange(@NonNull View view) {
                return view.getWidth();
            }
        });
    }

    public void setCurrentItem(int i) {
        setCurrentItem(i, Direction.none);
    }

    public void setCurrentItem(int i, Direction direction) {
        this.mPrevItem = this.mCurItem;
        this.mCurItem = i;
        if (this.mCurItem == this.mPrevItem) {
            this.mPrevItem = -1;
        }
        refreshViews(direction);
        ArrayList<ViewPager.OnPageChangeListener> arrayList = this.mOnPageChangeListeners;
        if (arrayList != null) {
            arrayList.forEach(new Consumer() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$StackViewPager$fJUouNr_wMUiXi7gbjpfqjLxZuE
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((ViewPager.OnPageChangeListener) obj).onPageSelected(StackViewPager.this.mCurItem);
                }
            });
        }
    }

    public void setAdapter(MyFragmentPagerAdapter myFragmentPagerAdapter) {
        MyFragmentPagerAdapter myFragmentPagerAdapter2 = this.mAdapter;
        if (myFragmentPagerAdapter2 != null) {
            myFragmentPagerAdapter2.unregisterDataSetObserver(this.dataSetObserver);
        }
        this.mAdapter = myFragmentPagerAdapter;
        this.mAdapter.registerDataSetObserver(this.dataSetObserver);
        dataSetChanged();
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        super.onViewAdded(view);
        refreshViews(Direction.none);
    }

    void dataSetChanged() {
        int count = this.mAdapter.getCount();
        int i = this.mCurItem;
        int i2 = 0;
        boolean z = false;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    if (!z) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        z = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, (Object) itemInfo.object);
                    if (this.mCurItem == itemInfo.position) {
                        i = Math.max(0, Math.min(this.mCurItem, count - 1));
                    }
                } else if (itemInfo.position != itemPosition) {
                    if (itemInfo.position == this.mCurItem) {
                        i = itemPosition;
                    }
                    itemInfo.position = itemPosition;
                }
            }
            i2++;
        }
        if (z) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        boolean z2 = false;
        for (int i3 = 0; i3 < this.mAdapter.getCount(); i3++) {
            if (!hasInstanced(i3)) {
                if (!z2) {
                    this.mAdapter.startUpdate((ViewGroup) this);
                    z2 = true;
                }
                ItemInfo itemInfo2 = new ItemInfo();
                itemInfo2.position = i3;
                itemInfo2.object = (Fragment) this.mAdapter.instantiateItem((ViewGroup) this, i3);
                this.mItems.add(itemInfo2);
            }
        }
        if (z2) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        setCurrentItem(i);
    }

    private boolean hasInstanced(int i) {
        for (ItemInfo itemInfo : this.mItems) {
            if (itemInfo.position == i) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View capturedView = this.mViewDragHelper.getCapturedView();
        if (capturedView != null) {
            capturedView.offsetLeftAndRight(this.mCapturedViewLeft);
            capturedView.offsetTopAndBottom(this.mCapturedViewTop);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        try {
            this.mCurrentEvent = motionEvent;
            return this.mViewDragHelper.shouldInterceptTouchEvent(motionEvent);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        try {
            this.mCurrentEvent = motionEvent;
            this.mViewDragHelper.processTouchEvent(motionEvent);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public MyFragmentPagerAdapter getAdapter() {
        return this.mAdapter;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    @Override // android.view.View
    public void clearAnimation() {
        super.clearAnimation();
        this.mItems.forEach($$Lambda$StackViewPager$KkEj1c10eTFDNs3ioilM7FJdAbk.INSTANCE);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$clearAnimation$1(ItemInfo itemInfo) {
        if (itemInfo.object.getView() != null) {
            itemInfo.object.getView().animate().cancel();
        }
    }

    private static /* synthetic */ void lambda$refreshViews$2(ItemInfo itemInfo) {
        Logger logger2 = logger;
        logger2.d(itemInfo.object.getClass().getSimpleName() + " index:" + itemInfo.position);
    }

    private void refreshViews(final Direction direction) {
        boolean z;
        Iterator<ItemInfo> it = this.mItems.iterator();
        final ItemInfo itemInfo = null;
        final ItemInfo itemInfo2 = null;
        ItemInfo itemInfo3 = null;
        while (true) {
            z = true;
            if (!it.hasNext()) {
                break;
            }
            ItemInfo next = it.next();
            if (next.position == this.mPrevItem) {
                itemInfo3 = next;
            } else if (next.position == this.mCurItem) {
                itemInfo2 = next;
            } else {
                if (next.object.getView() == null || next.object.getView().getVisibility() != 0) {
                    z = false;
                }
                if (z) {
                    next.object.getView().setVisibility(8);
                    next.object.setUserVisibleHint(false);
                }
            }
        }
        if (itemInfo2 == null) {
            logger.i("not found current item " + this.mCurItem);
            return;
        }
        if (!itemInfo2.equals(itemInfo3)) {
            itemInfo = itemInfo3;
        }
        if (itemInfo2.object.getView() == null) {
            logger.i("current item view not created, total Child count %d", Integer.valueOf(getChildCount()));
            return;
        }
        long j = 0;
        switch (direction) {
            case auto:
                if (!(itemInfo == null || itemInfo.object.getView() == null)) {
                    logger.d(itemInfo.object.getClass().getSimpleName() + " start to hide");
                    final View view = itemInfo.object.getView();
                    view.animate().cancel();
                    view.animate().alpha(0.0f).setDuration(1000L).setStartDelay(0L).setListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.common.widget.StackViewPager.3
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (itemInfo.object.getView() != null) {
                                itemInfo.object.getView().setVisibility(8);
                                itemInfo.object.setUserVisibleHint(false);
                            }
                        }
                    }).start();
                }
                if (itemInfo2.object.getView() != null) {
                    final View view2 = itemInfo2.object.getView();
                    view2.animate().cancel();
                    itemInfo2.object.setUserVisibleHint(true);
                    view2.setVisibility(0);
                    view2.setAlpha(0.0f);
                    ViewPropertyAnimator alpha = view2.animate().alpha(1.0f);
                    if (itemInfo != null) {
                        j = 1050;
                    }
                    alpha.setStartDelay(j).setDuration(1000L).setListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.common.widget.StackViewPager.4
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            ItemInfo itemInfo4 = itemInfo2;
                            if (itemInfo4 != null && itemInfo4.object.getView() != null) {
                                itemInfo2.object.getView().setAlpha(1.0f);
                                itemInfo2.object.getView().setVisibility(0);
                                itemInfo2.object.setUserVisibleHint(true);
                            }
                        }
                    }).start();
                    return;
                }
                return;
            case left:
            case right:
                if (!(itemInfo == null || itemInfo.object.getView() == null)) {
                    itemInfo.object.getView().animate().cancel();
                    itemInfo.object.setUserVisibleHint(false);
                    itemInfo.object.getView().setVisibility(8);
                }
                final View view3 = itemInfo2.object.getView();
                view3.setVisibility(0);
                itemInfo2.object.setUserVisibleHint(true);
                if ((itemInfo2 instanceof PageTransformer) && ((PageTransformer) itemInfo2.object).onPageEnter(direction)) {
                    z = false;
                }
                if (z) {
                    view3.setAlpha(0.0f);
                    int width = (int) (view3.getWidth() * 0.05f);
                    if (direction != Direction.left) {
                        width *= -1;
                    }
                    view3.setTranslationX(width);
                    view3.animate().cancel();
                    view3.animate().translationX(0.0f).alpha(1.0f).setDuration(500L).setStartDelay(0L).setListener(new Animator.AnimatorListener() { // from class: com.xiaomi.micolauncher.common.widget.StackViewPager.5
                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationRepeat(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            view3.setVisibility(0);
                            view3.setAlpha(1.0f);
                            view3.setTranslationX(0.0f);
                        }
                    }).start();
                    return;
                }
                return;
            default:
                if (itemInfo2 != null) {
                    itemInfo2.object.getView().animate().cancel();
                    if (itemInfo2.object.getView().getVisibility() == 8) {
                        itemInfo2.object.setUserVisibleHint(true);
                    }
                    itemInfo2.object.getView().setVisibility(0);
                    itemInfo2.object.getView().setAlpha(1.0f);
                }
                if (itemInfo != null && itemInfo.object.getView() != null) {
                    itemInfo.object.getView().animate().cancel();
                    if (itemInfo.object.getView().getVisibility() == 0) {
                        itemInfo.object.setUserVisibleHint(false);
                    }
                    itemInfo.object.getView().setVisibility(8);
                    return;
                }
                return;
        }
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList<>();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    public void removeOnPageChangeListener(@NonNull ViewPager.OnPageChangeListener onPageChangeListener) {
        ArrayList<ViewPager.OnPageChangeListener> arrayList = this.mOnPageChangeListeners;
        if (arrayList != null) {
            arrayList.remove(onPageChangeListener);
        }
    }

    protected boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes3.dex */
    public class ItemInfo {
        public Fragment object;
        public int position;

        ItemInfo() {
        }
    }

    /* loaded from: classes3.dex */
    class MyDataSetObserver extends DataSetObserver {
        MyDataSetObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            StackViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            StackViewPager.this.dataSetChanged();
        }
    }
}
