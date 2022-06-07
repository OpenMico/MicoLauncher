package com.xiaomi.micolauncher.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import com.xiaomi.micolauncher.common.widget.RefreshLayout;

/* loaded from: classes3.dex */
public class HorizontalRefreshLayout extends RefreshLayout {
    private final String TAG = "HorizontalRefreshLayout";
    private float mTargetTranslationX = 0.0f;
    private int reboundDirection = 0;
    private float refreshStartX;
    private float startX;
    private float startY;

    public HorizontalRefreshLayout(Context context) {
        super(context);
    }

    public HorizontalRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalRefreshLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.widget.RefreshLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!(this.refreshState != 0 || this.refreshMode == 1 || this.mRefreshHeadView == null)) {
            this.mRefreshHeadView.setTranslationX(-this.mRefreshHeadHeight);
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mTarget == null || !isEnable()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        int actionMasked = motionEvent.getActionMasked();
        if (this.mTarget != null && this.mAnimType == RefreshLayout.AnimType.REFRESH) {
            return true;
        }
        switch (actionMasked) {
            case 0:
                this.startX = motionEvent.getX();
                this.startY = motionEvent.getY();
                break;
            case 1:
            case 3:
                this.startX = 0.0f;
                this.startY = 0.0f;
                break;
            case 2:
                if (!canChildScrollRight() && this.mTailMode == RefreshLayout.TailMode.REBOUND && this.startX - motionEvent.getX() > this.touchSlop && this.refreshState != 4) {
                    this.mAnimType = RefreshLayout.AnimType.REBOUND;
                    return true;
                } else if (canChildScrollLeft() || this.mHeadMode != RefreshLayout.HeadMode.REBOUND || this.startX - motionEvent.getX() >= (-this.touchSlop) || this.refreshState == 4) {
                    if (!canChildScrollLeft()) {
                        float f = this.startX;
                        if (f != 0.0f && f - motionEvent.getX() < (-this.touchSlop)) {
                            float f2 = this.startY;
                            if (!(f2 == 0.0f || Math.abs(f2 - motionEvent.getY()) >= this.touchSlop / 2 || this.refreshState == 4 || this.mRefreshHeader == null)) {
                                this.mAnimType = RefreshLayout.AnimType.REFRESH;
                                this.refreshState = 1;
                                this.mRefreshHeader.onCancel();
                                return true;
                            }
                        }
                    }
                    if (!canChildScrollLeft() && this.startX - motionEvent.getX() > this.touchSlop && this.startY != 0.0f && this.refreshState == 4 && this.mRefreshHeader != null) {
                        this.mAnimType = RefreshLayout.AnimType.REFRESH;
                        this.mRefreshHeader.onCancel();
                        return true;
                    }
                } else {
                    this.mAnimType = RefreshLayout.AnimType.REBOUND;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mTarget == null || !isEnable()) {
            return super.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action == 1 || action == 3) {
            if (this.mAnimType == RefreshLayout.AnimType.REBOUND) {
                this.reboundDirection = this.mTarget.getLeft() - this.mRect.left;
                TranslateAnimation translateAnimation = new TranslateAnimation(this.mTarget.getLeft(), this.mRect.left, 0.0f, 0.0f);
                translateAnimation.setDuration(500L);
                translateAnimation.setInterpolator(new DecelerateInterpolator());
                translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        if (HorizontalRefreshLayout.this.mOnReboundEndListener != null) {
                            if (HorizontalRefreshLayout.this.reboundDirection > 0) {
                                HorizontalRefreshLayout.this.mOnReboundEndListener.onReboundTopComplete();
                            }
                            if (HorizontalRefreshLayout.this.reboundDirection < 0) {
                                HorizontalRefreshLayout.this.mOnReboundEndListener.onReboundBottomComplete();
                            }
                            HorizontalRefreshLayout.this.reboundDirection = 0;
                        }
                    }
                });
                this.mTarget.startAnimation(translateAnimation);
                this.mTarget.layout(this.mRect.left, this.mRect.top, this.mRect.right, this.mRect.bottom);
                this.mAnimType = RefreshLayout.AnimType.NONE;
                this.refreshStartX = 0.0f;
            }
            float f = this.mTargetTranslationX;
            if (this.mAnimType == RefreshLayout.AnimType.REFRESH) {
                if (f < this.mRefreshHeadHeight - this.commonMarginP) {
                    smoothRelease();
                } else {
                    smoothLocateToRefresh(true);
                }
            }
            return false;
        }
        float f2 = this.mTargetTranslationX;
        if (this.refreshStartX == 0.0f) {
            this.refreshStartX = motionEvent.getX();
            return super.onTouchEvent(motionEvent);
        } else if (this.mAnimType == RefreshLayout.AnimType.REBOUND) {
            int x = (int) (((int) (motionEvent.getX() - this.refreshStartX)) * 0.4d);
            this.mTarget.layout(this.mRect.left + x, this.mRect.top, this.mRect.right + x, this.mRect.bottom);
            return true;
        } else if (this.mAnimType != RefreshLayout.AnimType.REFRESH || this.refreshStartX == 0.0f) {
            return super.onTouchEvent(motionEvent);
        } else {
            float x2 = motionEvent.getX() - this.refreshStartX;
            this.refreshStartX = motionEvent.getX();
            if (f2 < 0.0f) {
                setTargetTranslationX(0.0f);
                motionEvent.setAction(3);
                this.mAnimType = RefreshLayout.AnimType.NONE;
                this.refreshStartX = 0.0f;
                return false;
            }
            float abs = f2 + (x2 * (1.0f - Math.abs(f2 / this.mRefreshHeadHeight)));
            if (abs < 0.0f) {
                setTargetTranslationX(0.0f);
            } else if (abs > this.mRefreshHeadHeight) {
                setTargetTranslationX(this.mRefreshHeadHeight);
            } else {
                setTargetTranslationX(abs);
                if (this.mRefreshHeader != null) {
                    if (this.refreshMode == 0 || this.refreshMode == 2) {
                        float f3 = this.mRefreshHeadHeight;
                        int i = this.commonMarginP;
                        this.mRefreshHeadView.setTranslationX(abs - (this.mRefreshHeadHeight - this.commonMarginP));
                    }
                    float abs2 = Math.abs(this.mTargetTranslationX / (this.mRefreshHeadHeight - this.commonMarginP));
                    if (abs2 > 0.1f) {
                        this.refreshState = 2;
                        this.mRefreshHeader.onDragging(this.mTargetTranslationX, abs2, this.mRefreshHeadView);
                    }
                    if (this.mTargetTranslationX > this.mRefreshHeadHeight - this.commonMarginP && this.refreshState != 3) {
                        this.refreshState = 3;
                        this.mRefreshHeader.onReadyToRelease(this.mRefreshHeadView);
                    }
                }
            }
            return true;
        }
    }

    public void setTargetTranslationX(float f) {
        this.mTargetTranslationX = f;
        if (this.refreshMode != 2) {
            this.mTarget.setTranslationX(this.mTargetTranslationX);
        }
    }

    private void smoothRelease() {
        this.refreshState = 1;
        this.mAnimType = RefreshLayout.AnimType.NONE;
        this.refreshStartX = 0.0f;
        if (this.refreshMode == 2) {
            setTargetTranslationX(0.0f);
        } else {
            this.mTarget.animate().translationX(0.0f).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    HorizontalRefreshLayout horizontalRefreshLayout = HorizontalRefreshLayout.this;
                    horizontalRefreshLayout.setTargetTranslationX(horizontalRefreshLayout.mTarget.getTranslationX());
                }
            }).start();
        }
        if (this.mRefreshHeader != null) {
            this.mRefreshHeader.onCancel();
            if (this.refreshMode == 0 || this.refreshMode == 2) {
                this.mRefreshHeadView.animate().translationX(-this.mRefreshHeadHeight).setDuration(200L).start();
            }
        }
    }

    public void smoothLocateToRefresh(boolean z) {
        final int i;
        this.refreshState = 4;
        this.mAnimType = RefreshLayout.AnimType.NONE;
        this.refreshStartX = 0.0f;
        float f = this.mTargetTranslationX;
        long j = 150;
        if (this.mRefreshHeader == null || f <= 0.0f) {
            i = -1;
        } else {
            this.mRefreshHeader.onRefreshing(this.mRefreshHeadView);
            if (this.refreshMode == 0 || this.refreshMode == 2) {
                this.mRefreshHeadView.animate().translationX(0.0f).setDuration(150L).start();
            }
            i = this.START;
        }
        if (this.refreshMode == 2) {
            setTargetTranslationX(0.0f);
            if (this.refreshCallback != null && i == this.START) {
                this.refreshCallback.onRefreshing();
                return;
            }
            return;
        }
        ViewPropertyAnimator translationXBy = this.mTarget.animate().translationXBy((f > 0.0f ? this.mRefreshHeadHeight - this.commonMarginP : -(0 - this.commonMarginP)) - f);
        if (!z) {
            j = 500;
        }
        translationXBy.setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                HorizontalRefreshLayout horizontalRefreshLayout = HorizontalRefreshLayout.this;
                horizontalRefreshLayout.setTargetTranslationX(horizontalRefreshLayout.mTarget.getTranslationX());
                if (HorizontalRefreshLayout.this.refreshCallback != null && i == HorizontalRefreshLayout.this.START) {
                    HorizontalRefreshLayout.this.refreshCallback.onRefreshing();
                }
            }
        }).start();
    }

    public void startAutoRefresh(final boolean z) {
        postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.common.widget.HorizontalRefreshLayout.4
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    HorizontalRefreshLayout.this.setTargetTranslationX(1.0f);
                } else {
                    HorizontalRefreshLayout.this.setTargetTranslationX(-1.0f);
                }
                HorizontalRefreshLayout.this.smoothLocateToRefresh(false);
            }
        }, 100L);
    }

    public void onRefreshComplete() {
        this.refreshState = 0;
        smoothRelease();
        if (this.mRefreshHeader != null) {
            this.mRefreshHeader.onCancel();
        }
    }
}
