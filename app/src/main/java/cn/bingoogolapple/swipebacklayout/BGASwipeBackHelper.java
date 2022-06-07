package cn.bingoogolapple.swipebacklayout;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.FloatRange;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout;
import java.util.List;

/* loaded from: classes.dex */
public class BGASwipeBackHelper {
    private Activity a;
    private Delegate b;
    private BGASwipeBackLayout c;

    /* loaded from: classes.dex */
    public interface Delegate {
        boolean isSupportSwipeBack();

        void onSwipeBackLayoutCancel();

        void onSwipeBackLayoutExecuted();

        void onSwipeBackLayoutSlide(float f);
    }

    public static void init(Application application, List<Class<? extends View>> list) {
        a.a().a(application, list);
    }

    public BGASwipeBackHelper(Activity activity, Delegate delegate) {
        this.a = activity;
        this.b = delegate;
        a();
    }

    private void a() {
        if (this.b.isSupportSwipeBack()) {
            this.c = new BGASwipeBackLayout(this.a);
            this.c.a(this.a);
            this.c.setPanelSlideListener(new BGASwipeBackLayout.PanelSlideListener() { // from class: cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper.1
                @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.PanelSlideListener
                public void onPanelSlide(View view, float f) {
                    if (f < 0.03d) {
                        BGAKeyboardUtil.closeKeyboard(BGASwipeBackHelper.this.a);
                    }
                    BGASwipeBackHelper.this.b.onSwipeBackLayoutSlide(f);
                }

                @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.PanelSlideListener
                public void onPanelOpened(View view) {
                    BGASwipeBackHelper.this.b.onSwipeBackLayoutExecuted();
                }

                @Override // cn.bingoogolapple.swipebacklayout.BGASwipeBackLayout.PanelSlideListener
                public void onPanelClosed(View view) {
                    BGASwipeBackHelper.this.b.onSwipeBackLayoutCancel();
                }
            });
        }
    }

    public BGASwipeBackHelper setSwipeBackEnable(boolean z) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setSwipeBackEnable(z);
        }
        return this;
    }

    public BGASwipeBackHelper setIsOnlyTrackingLeftEdge(boolean z) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setIsOnlyTrackingLeftEdge(z);
        }
        return this;
    }

    public BGASwipeBackHelper setIsWeChatStyle(boolean z) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setIsWeChatStyle(z);
        }
        return this;
    }

    public BGASwipeBackHelper setShadowResId(@DrawableRes int i) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setShadowResId(i);
        }
        return this;
    }

    public BGASwipeBackHelper setIsNeedShowShadow(boolean z) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setIsNeedShowShadow(z);
        }
        return this;
    }

    public BGASwipeBackHelper setIsShadowAlphaGradient(boolean z) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setIsShadowAlphaGradient(z);
        }
        return this;
    }

    public BGASwipeBackHelper setSwipeBackThreshold(@FloatRange(from = 0.0d, to = 1.0d) float f) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setSwipeBackThreshold(f);
        }
        return this;
    }

    public BGASwipeBackHelper setIsNavigationBarOverlap(boolean z) {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            bGASwipeBackLayout.setIsNavigationBarOverlap(z);
        }
        return this;
    }

    public boolean isSliding() {
        BGASwipeBackLayout bGASwipeBackLayout = this.c;
        if (bGASwipeBackLayout != null) {
            return bGASwipeBackLayout.a();
        }
        return false;
    }

    public void executeForwardAnim() {
        executeForwardAnim(this.a);
    }

    public void executeBackwardAnim() {
        executeBackwardAnim(this.a);
    }

    public void executeSwipeBackAnim() {
        executeSwipeBackAnim(this.a);
    }

    public static void executeForwardAnim(Activity activity) {
        activity.overridePendingTransition(R.anim.bga_sbl_activity_forward_enter, R.anim.bga_sbl_activity_forward_exit);
    }

    public static void executeBackwardAnim(Activity activity) {
        activity.overridePendingTransition(R.anim.bga_sbl_activity_backward_enter, R.anim.bga_sbl_activity_backward_exit);
    }

    public static void executeSwipeBackAnim(Activity activity) {
        activity.overridePendingTransition(R.anim.bga_sbl_activity_swipeback_enter, R.anim.bga_sbl_activity_swipeback_exit);
    }

    public void forwardAndFinish(Class<?> cls) {
        forward(cls);
        this.a.finish();
    }

    public void forward(Class<?> cls) {
        BGAKeyboardUtil.closeKeyboard(this.a);
        Activity activity = this.a;
        activity.startActivity(new Intent(activity, cls));
        executeForwardAnim();
    }

    public void forward(Class<?> cls, int i) {
        forward(new Intent(this.a, cls), i);
    }

    public void forwardAndFinish(Intent intent) {
        forward(intent);
        this.a.finish();
    }

    public void forward(Intent intent) {
        BGAKeyboardUtil.closeKeyboard(this.a);
        this.a.startActivity(intent);
        executeForwardAnim();
    }

    public void forward(Intent intent, int i) {
        BGAKeyboardUtil.closeKeyboard(this.a);
        this.a.startActivityForResult(intent, i);
        executeForwardAnim();
    }

    public void backward() {
        BGAKeyboardUtil.closeKeyboard(this.a);
        this.a.finish();
        executeBackwardAnim();
    }

    public void swipeBackward() {
        BGAKeyboardUtil.closeKeyboard(this.a);
        this.a.finish();
        executeSwipeBackAnim();
    }

    public void backwardAndFinish(Class<?> cls) {
        BGAKeyboardUtil.closeKeyboard(this.a);
        Activity activity = this.a;
        activity.startActivity(new Intent(activity, cls));
        this.a.finish();
        executeBackwardAnim();
    }
}
