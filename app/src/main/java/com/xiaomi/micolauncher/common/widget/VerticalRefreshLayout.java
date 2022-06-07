package com.xiaomi.micolauncher.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import com.xiaomi.micolauncher.common.widget.RefreshLayout;

/* loaded from: classes3.dex */
public class VerticalRefreshLayout extends RefreshLayout {
    private final String TAG = "VerticalRefreshLayout";
    private float mTargetTranslationY = 0.0f;
    private int reboundDirection = 0;
    private float refreshStartY;
    private float startX;
    private float startY;

    public VerticalRefreshLayout(Context context) {
        super(context);
    }

    public VerticalRefreshLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public VerticalRefreshLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaomi.micolauncher.common.widget.RefreshLayout, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (!(this.refreshState != 0 || this.refreshMode == 1 || this.mRefreshHeadView == null)) {
            this.mRefreshHeadView.setTranslationY(-this.mRefreshHeadHeight);
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
                Log.e("VerticalRefreshLayout", "-------canChildScrollUp:" + canChildScrollUp() + ", canChildScrollDown:" + canChildScrollDown());
                Log.e("VerticalRefreshLayout", "-------startY:" + this.startY + ",ev.getY():" + motionEvent.getY() + ", refreshState:" + this.refreshState);
                if (!canChildScrollUp() && this.mHeadMode == RefreshLayout.HeadMode.REBOUND && this.startY - motionEvent.getY() < (-this.touchSlop) && this.refreshState != 4) {
                    this.mAnimType = RefreshLayout.AnimType.REBOUND;
                    return true;
                } else if (canChildScrollDown() || this.mTailMode != RefreshLayout.TailMode.REBOUND || this.startY - motionEvent.getY() <= this.touchSlop || this.refreshState == 4) {
                    if (!canChildScrollUp()) {
                        float f = this.startY;
                        if (f != 0.0f && f - motionEvent.getY() < (-this.touchSlop)) {
                            float f2 = this.startX;
                            if (!(f2 == 0.0f || Math.abs(f2 - motionEvent.getX()) >= this.touchSlop / 2 || this.refreshState == 4 || this.mRefreshHeader == null)) {
                                this.mAnimType = RefreshLayout.AnimType.REFRESH;
                                this.refreshState = 1;
                                this.mRefreshHeader.onCancel();
                                return true;
                            }
                        }
                    }
                    if (!canChildScrollUp() && this.startY - motionEvent.getY() > this.touchSlop && this.startX != 0.0f && this.refreshState == 4 && this.mRefreshHeader != null) {
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
                this.reboundDirection = this.mTarget.getTop() - this.mRect.top;
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, this.mTarget.getTop(), this.mRect.top);
                translateAnimation.setDuration(500L);
                translateAnimation.setInterpolator(new DecelerateInterpolator());
                translateAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.xiaomi.micolauncher.common.widget.VerticalRefreshLayout.1
                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public void onAnimationEnd(Animation animation) {
                        if (VerticalRefreshLayout.this.mOnReboundEndListener != null) {
                            if (VerticalRefreshLayout.this.reboundDirection > 0) {
                                VerticalRefreshLayout.this.mOnReboundEndListener.onReboundTopComplete();
                            }
                            if (VerticalRefreshLayout.this.reboundDirection < 0) {
                                VerticalRefreshLayout.this.mOnReboundEndListener.onReboundBottomComplete();
                            }
                            VerticalRefreshLayout.this.reboundDirection = 0;
                        }
                    }
                });
                this.mTarget.startAnimation(translateAnimation);
                this.mTarget.layout(this.mRect.left, this.mRect.top, this.mRect.right, this.mRect.bottom);
                this.mAnimType = RefreshLayout.AnimType.NONE;
                this.refreshStartY = 0.0f;
            }
            float f = this.mTargetTranslationY;
            if (this.mAnimType == RefreshLayout.AnimType.REFRESH) {
                if (f < this.mRefreshHeadHeight - this.commonMarginP) {
                    smoothRelease();
                } else {
                    smoothLocateToRefresh(true);
                }
            }
            return false;
        }
        float f2 = this.mTargetTranslationY;
        if (this.refreshStartY == 0.0f) {
            this.refreshStartY = motionEvent.getY();
            return super.onTouchEvent(motionEvent);
        } else if (this.mAnimType == RefreshLayout.AnimType.REBOUND) {
            int y = (int) (((int) (motionEvent.getY() - this.refreshStartY)) * 0.4d);
            this.mTarget.layout(this.mRect.left, this.mRect.top + y, this.mRect.right, this.mRect.bottom + y);
            return true;
        } else if (this.mAnimType != RefreshLayout.AnimType.REFRESH || this.refreshStartY == 0.0f) {
            return super.onTouchEvent(motionEvent);
        } else {
            float y2 = motionEvent.getY() - this.refreshStartY;
            this.refreshStartY = motionEvent.getY();
            if (f2 < 0.0f) {
                setTargetTranslationY(0.0f);
                motionEvent.setAction(3);
                this.mAnimType = RefreshLayout.AnimType.NONE;
                this.refreshStartY = 0.0f;
                return false;
            }
            float abs = f2 + (y2 * (1.0f - Math.abs(f2 / this.mRefreshHeadHeight)));
            if (abs < 0.0f) {
                setTargetTranslationY(0.0f);
            } else if (abs > this.mRefreshHeadHeight) {
                setTargetTranslationY(this.mRefreshHeadHeight);
            } else {
                setTargetTranslationY(abs);
                if (this.mRefreshHeader != null) {
                    if (this.refreshMode == 0 || this.refreshMode == 2) {
                        float f3 = this.mRefreshHeadHeight;
                        int i = this.commonMarginP;
                        this.mRefreshHeadView.setTranslationY(abs - (this.mRefreshHeadHeight - this.commonMarginP));
                    }
                    float abs2 = Math.abs(this.mTargetTranslationY / (this.mRefreshHeadHeight - this.commonMarginP));
                    if (abs2 > 0.1f) {
                        this.refreshState = 2;
                        this.mRefreshHeader.onDragging(this.mTargetTranslationY, abs2, this.mRefreshHeadView);
                    }
                    if (this.mTargetTranslationY > this.mRefreshHeadHeight - this.commonMarginP && this.refreshState != 3) {
                        this.refreshState = 3;
                        this.mRefreshHeader.onReadyToRelease(this.mRefreshHeadView);
                    }
                }
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setTargetTranslationY(float f) {
        this.mTargetTranslationY = f;
        if (this.refreshMode != 2) {
            this.mTarget.setTranslationY(this.mTargetTranslationY);
        }
    }

    private void smoothRelease() {
        this.refreshState = 1;
        this.mAnimType = RefreshLayout.AnimType.NONE;
        this.refreshStartY = 0.0f;
        if (this.refreshMode == 2) {
            setTargetTranslationY(0.0f);
        } else {
            this.mTarget.animate().translationY(0.0f).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.common.widget.VerticalRefreshLayout.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    VerticalRefreshLayout verticalRefreshLayout = VerticalRefreshLayout.this;
                    verticalRefreshLayout.setTargetTranslationY(verticalRefreshLayout.mTarget.getTranslationY());
                }
            }).start();
        }
        if (this.mRefreshHeader != null) {
            this.mRefreshHeader.onCancel();
            if (this.refreshMode == 0 || this.refreshMode == 2) {
                this.mRefreshHeadView.animate().translationY(-this.mRefreshHeadHeight).setDuration(200L).start();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void smoothLocateToRefresh(boolean z) {
        final int i;
        this.refreshState = 4;
        this.mAnimType = RefreshLayout.AnimType.NONE;
        this.refreshStartY = 0.0f;
        float f = this.mTargetTranslationY;
        long j = 150;
        if (this.mRefreshHeader == null || f <= 0.0f) {
            i = -1;
        } else {
            this.mRefreshHeader.onRefreshing(this.mRefreshHeadView);
            if (this.refreshMode == 0 || this.refreshMode == 2) {
                this.mRefreshHeadView.animate().translationY(0.0f).setDuration(150L).start();
            }
            i = this.START;
        }
        if (this.refreshMode == 2) {
            setTargetTranslationY(0.0f);
            if (this.refreshCallback != null && i == this.START) {
                this.refreshCallback.onRefreshing();
                return;
            }
            return;
        }
        ViewPropertyAnimator translationYBy = this.mTarget.animate().translationYBy((f > 0.0f ? this.mRefreshHeadHeight - this.commonMarginP : -(0 - this.commonMarginP)) - f);
        if (!z) {
            j = 500;
        }
        translationYBy.setDuration(j).setListener(new AnimatorListenerAdapter() { // from class: com.xiaomi.micolauncher.common.widget.VerticalRefreshLayout.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                VerticalRefreshLayout verticalRefreshLayout = VerticalRefreshLayout.this;
                verticalRefreshLayout.setTargetTranslationY(verticalRefreshLayout.mTarget.getTranslationY());
                if (VerticalRefreshLayout.this.refreshCallback != null && i == VerticalRefreshLayout.this.START) {
                    VerticalRefreshLayout.this.refreshCallback.onRefreshing();
                }
            }
        }).start();
    }

    public void startAutoRefresh(final boolean z) {
        postDelayed(new Runnable() { // from class: com.xiaomi.micolauncher.common.widget.VerticalRefreshLayout.4
            @Override // java.lang.Runnable
            public void run() {
                if (z) {
                    VerticalRefreshLayout.this.setTargetTranslationY(1.0f);
                } else {
                    VerticalRefreshLayout.this.setTargetTranslationY(-1.0f);
                }
                VerticalRefreshLayout.this.smoothLocateToRefresh(false);
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
