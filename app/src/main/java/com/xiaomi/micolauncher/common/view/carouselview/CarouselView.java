package com.xiaomi.micolauncher.common.view.carouselview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.viewpager.widget.ViewPager;
import com.xiaomi.micolauncher.R;
import com.xiaomi.micolauncher.common.L;
import com.xiaomi.micolauncher.common.WeakRefHandler;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class CarouselView extends RelativeLayout {
    private static final long a = TimeUnit.SECONDS.toMillis(60);
    private OnPageSelectedListener b;
    protected int mPageCount;
    protected boolean mIsInfinite = true;
    protected boolean mIsAutoSwitch = true;
    protected long mSwitchTimeInterval = a;
    protected boolean mIsScrolling = false;
    private Handler.Callback d = new Handler.Callback() { // from class: com.xiaomi.micolauncher.common.view.carouselview.CarouselView.1
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what == 1) {
                CarouselView.this.a();
            }
            return true;
        }
    };
    protected ViewPager mViewPager = (ViewPager) findViewById(R.id.view_pager);
    protected FadePageTransformer mTransformer = new FadePageTransformer();
    private Handler c = new WeakRefHandler(this.d);

    /* loaded from: classes3.dex */
    public interface OnPageSelectedListener {
        void onSelected(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a() {
        if (this.mIsAutoSwitch) {
            if (!this.mIsScrolling) {
                this.mViewPager.setCurrentItem(this.mViewPager.getCurrentItem() + 1, true);
            }
            this.c.sendEmptyMessageDelayed(1, this.mSwitchTimeInterval);
        }
    }

    public void setCurrentIndex(int i, boolean z) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager == null) {
            return;
        }
        if (this.mIsInfinite) {
            L.homepage.d("set current item is %d", Integer.valueOf((this.mPageCount * 100) + i));
            this.mViewPager.setCurrentItem((this.mPageCount * 100) + i, z);
            return;
        }
        viewPager.setCurrentItem(i, z);
    }

    public void setCurrentIndex(int i) {
        setCurrentIndex(i, false);
    }

    public void setWallPaperCurrentIndex(int i) {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            viewPager.setCurrentItem(i, false);
        }
    }

    public void startAutoSwitch() {
        if (this.mIsAutoSwitch) {
            this.c.removeMessages(1);
            this.c.sendEmptyMessageDelayed(1, this.mSwitchTimeInterval);
        }
    }

    public void startSwitch() {
        this.c.removeMessages(1);
        this.c.sendEmptyMessage(1);
    }

    public void setAutoSwitchTimePeriod(int i) {
        CarouselViewScroller carouselViewScroller = new CarouselViewScroller(getContext());
        carouselViewScroller.setScrollDuration(i);
        carouselViewScroller.initViewPagerScroll(this.mViewPager);
    }

    public void stopAutoSwitch() {
        this.c.removeMessages(1);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        stopAutoSwitch();
        startAutoSwitch();
    }

    public void stop() {
        stopAutoSwitch();
    }

    public void setOffscreenPageLimit(int i) {
        this.mViewPager.setOffscreenPageLimit(i);
    }

    public CarouselView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        inflate(context, R.layout.view_carousel, this);
        this.mViewPager.setOffscreenPageLimit(10);
        this.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.xiaomi.micolauncher.common.view.carouselview.CarouselView.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                CarouselView.this.b();
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                if (CarouselView.this.b != null) {
                    CarouselView.this.b.onSelected(i);
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                CarouselView carouselView = CarouselView.this;
                boolean z = true;
                if (i != 1) {
                    z = false;
                }
                carouselView.mIsScrolling = z;
            }
        });
    }

    public void setOnPageSelectedListener(OnPageSelectedListener onPageSelectedListener) {
        this.b = onPageSelectedListener;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.c.removeMessages(1);
        this.c.removeCallbacksAndMessages(null);
    }

    public ViewPager getViewPager() {
        return this.mViewPager;
    }

    private void setPageCount(int i) {
        this.mPageCount = i;
    }

    public void setIsAutoSwitch(boolean z) {
        this.mIsAutoSwitch = z;
        if (this.mIsAutoSwitch) {
            startAutoSwitch();
        } else {
            stopAutoSwitch();
        }
    }

    public void setIsInfinite(boolean z) {
        this.mIsInfinite = z;
    }

    public void setSwitchTimeInterval(long j) {
        this.mSwitchTimeInterval = j;
    }

    public void setItemViewList(List<View> list) {
        setPageCount(list != null ? list.size() : 0);
        this.mViewPager.setAdapter(new CarouselViewAdapter(list, this.mPageCount));
        int i = this.mPageCount;
        if (i > 1) {
            this.mViewPager.setCurrentItem((i * 100) + 1, false);
            setIsAutoSwitch(true);
        } else if (i == 1) {
            this.mViewPager.setCurrentItem(0, false);
            setIsAutoSwitch(false);
        }
        if (this.mPageCount > 3) {
            this.mViewPager.setPageMargin(2);
            this.mViewPager.setClipToPadding(false);
            this.mViewPager.setPadding(18, 0, 54, 0);
        }
    }

    public void setCustomCarouselViewAdapter(CustomCarouselViewAdapter customCarouselViewAdapter) {
        this.mViewPager.setAdapter(customCarouselViewAdapter);
        setPageCount(customCarouselViewAdapter != null ? customCarouselViewAdapter.getPageCount() : 0);
        int i = this.mPageCount;
        if (i > 1 && this.mIsInfinite) {
            this.mViewPager.setCurrentItem(i * 100, false);
            setIsAutoSwitch(true);
        } else if (this.mPageCount == 1) {
            this.mViewPager.setCurrentItem(0, false);
            setIsAutoSwitch(false);
        }
    }
}
