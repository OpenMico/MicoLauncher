package com.xiaomi.micolauncher.skills.music.model.lrc;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import androidx.core.content.ContextCompat;
import com.xiaomi.mico.common.ContainerUtil;
import com.xiaomi.micolauncher.R;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: classes3.dex */
public class LrcViewImpl1 extends View implements LrcView {
    private String A;
    private List<LrcRow> B;
    private OnLrcRowChangeListener C;
    private OnLrcTouchListener D;
    private long E;
    private String G;
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int g;
    private int h;
    private float i;
    private float j;
    private TextPaint k;
    private TextPaint l;
    private Scroller m;
    private int t;
    private float u;
    private float v;
    private float w;
    private String y;
    private int f = 5;
    private float n = 0.0f;
    private boolean o = false;
    private boolean p = false;
    private boolean q = true;
    private boolean r = true;
    private Layout.Alignment s = Layout.Alignment.ALIGN_CENTER;
    private int x = -1;
    private List<LrcRow> z = new ArrayList();
    private AtomicBoolean F = new AtomicBoolean();

    /* loaded from: classes3.dex */
    public interface OnLrcRowChangeListener {
        void onLrcRowChange(LrcRow lrcRow);
    }

    /* loaded from: classes3.dex */
    public interface OnLrcTouchListener {
        void onLrcTouchActive();

        void onLrcTouchEnd();
    }

    public void setShowPreLrcText(boolean z) {
        this.r = z;
    }

    public void setOnLrcTouchListener(OnLrcTouchListener onLrcTouchListener) {
        this.D = onLrcTouchListener;
    }

    public LrcViewImpl1(Context context) {
        super(context);
        a(context, (AttributeSet) null);
    }

    public LrcViewImpl1(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.LrcTextView);
            this.g = obtainStyledAttributes.getDimensionPixelSize(5, context.getResources().getDimensionPixelSize(R.dimen.music_lyric_normal_size));
            this.h = obtainStyledAttributes.getDimensionPixelSize(2, context.getResources().getDimensionPixelSize(R.dimen.music_lyric_highlight_size));
            this.i = obtainStyledAttributes.getDimensionPixelSize(3, context.getResources().getDimensionPixelSize(R.dimen.music_lyric_spacing));
            this.j = obtainStyledAttributes.getDimensionPixelSize(1, context.getResources().getDimensionPixelSize(R.dimen.music_lyric_spacing));
            this.e = obtainStyledAttributes.getColor(4, ContextCompat.getColor(context, R.color.lrc_normal_color));
            this.d = obtainStyledAttributes.getColor(0, ContextCompat.getColor(context, R.color.lrc_highlight_color));
            obtainStyledAttributes.recycle();
        } else {
            this.g = context.getResources().getDimensionPixelSize(R.dimen.music_lyric_normal_size);
            this.h = context.getResources().getDimensionPixelSize(R.dimen.music_lyric_highlight_size);
            this.i = context.getResources().getDimensionPixelSize(R.dimen.music_lyric_spacing);
            this.j = context.getResources().getDimensionPixelSize(R.dimen.music_lyric_spacing);
            this.e = context.getResources().getColor(R.color.lrc_normal_color);
            this.d = context.getResources().getColor(R.color.lrc_highlight_color);
        }
        this.k = new TextPaint();
        this.k.setColor(this.e);
        this.k.setTextSize(this.g);
        this.k.setAntiAlias(true);
        this.l = new TextPaint();
        this.l.setColor(this.d);
        this.l.setTextSize(this.h);
        this.l.setAntiAlias(true);
        this.m = new Scroller(getContext());
        this.t = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.a = StaticLayout.Builder.obtain("我", 0, 1, this.k, 100).build().getHeight();
        this.c = StaticLayout.Builder.obtain("我", 0, 1, this.l, 100).build().getHeight();
        setVerticalFadingEdgeEnabled(true);
        setFadingEdgeLength(30);
        this.F.set(true);
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public void setEmptyText(String str) {
        reset();
        this.y = str;
    }

    public void setEmptyText(String str, String str2) {
        setEmptyText(str2);
        setLrcRows(str, null);
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public boolean hasLrcRows() {
        return this.z.size() > 0;
    }

    public List<LrcRow> buildLoadingLrcRow() {
        if (this.B == null) {
            this.A = getContext().getString(R.string.music_lrc_loading);
            this.B = new ArrayList();
            this.B.add(LrcRow.buildLoadingLrcRow(this.A));
        }
        return this.B;
    }

    public void showLoadingLrc() {
        setLrcRows(buildLoadingLrcRow());
    }

    public void setLrcRows(String str, List<LrcRow> list) {
        setLrcRows(list);
        this.G = str;
    }

    public String getLrcMusicId() {
        return this.G;
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public void setLrcRows(List<LrcRow> list) {
        reset();
        if (list == null) {
            postInvalidate();
            return;
        }
        this.z.addAll(list);
        this.F.set(true);
        invalidateView();
    }

    public boolean hasNotUpdatedLrc(String str) {
        String str2 = this.G;
        return str2 == null || !str2.equals(str);
    }

    public void setNormalLrcTextTypeface(Typeface typeface) {
        TextPaint textPaint = this.k;
        if (textPaint != null) {
            textPaint.setTypeface(typeface);
        }
    }

    public void setHighlightLrcTextTypeface(Typeface typeface) {
        TextPaint textPaint = this.l;
        if (textPaint != null) {
            textPaint.setTypeface(typeface);
        }
    }

    public void setPauseDraw(boolean z) {
        this.o = z;
    }

    public void setCanDrag(boolean z) {
        this.q = z;
    }

    public void setAlignment(Layout.Alignment alignment) {
        this.s = alignment;
    }

    public void showFadeIn(Animator.AnimatorListener animatorListener) {
        setAlpha(0.0f);
        animate().alpha(1.0f).setDuration(400L).setListener(animatorListener);
    }

    int getTextLayoutMaxWidth() {
        return (getWidth() - getPaddingLeft()) - getPaddingRight();
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public void reset() {
        this.G = null;
        this.E = 0L;
        this.x = -1;
        this.z.clear();
        this.m.startScroll(getScrollX(), 0, 0, 0);
        invalidateView();
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public void onReset() {
        if (!this.m.isFinished()) {
            this.m.forceFinished(true);
        }
        scrollTo(getScrollX(), 0);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        float f;
        int i;
        if (!this.o) {
            super.onDraw(canvas);
            if (hasLrcRows()) {
                int scrollY = (int) (((getScrollY() + (getMeasuredHeight() / 2)) - getFirstRowOffset()) / (this.a + this.i));
                int max = Math.max(0, scrollY - ((this.f - 1) / 2));
                if (this.r) {
                    f = b(max);
                    i = Math.min(scrollY + ((this.f - 1) / 2), this.z.size() - 1);
                } else {
                    f = b(scrollY);
                    i = Math.min(scrollY + 1, this.z.size() - 1);
                }
                if (i == 0) {
                    if (max == i) {
                        this.x = 0;
                    } else if (max > i) {
                        max = 0;
                    }
                }
                int i2 = max;
                while (max <= i) {
                    String content = ContainerUtil.isOutOfBound((long) i2, this.z) ? null : this.z.get(i2).getContent();
                    if (TextUtils.isEmpty(content)) {
                        max++;
                    } else {
                        if (max == this.x) {
                            StaticLayout a = a(content, this.l, canvas, (int) f, 3, this.j);
                            f += a.getHeight() + this.i;
                            max += a.getLineCount();
                        } else {
                            this.k.setTextSize(this.g);
                            f += a(content, this.k, canvas, (int) f, 1, this.i).getHeight() + this.i;
                            max++;
                        }
                        i2++;
                    }
                }
            } else if (!TextUtils.isEmpty(this.y)) {
                this.k.setTextSize(this.g);
                int scrollY2 = (int) (((getScrollY() + (getMeasuredHeight() / 2)) - getFirstRowOffset()) / (this.a + this.i));
                int i3 = this.f;
                int i4 = scrollY2 - ((i3 - 1) / 2);
                int min = Math.min(scrollY2 + ((i3 - 1) / 2), this.z.size() - 1);
                while (i4 <= min) {
                    i4 += a(this.y, this.k, canvas, getHeight() / 2, 2, this.i).getLineCount();
                }
            }
        }
    }

    private StaticLayout a(String str, TextPaint textPaint, Canvas canvas, int i, int i2, float f) {
        StaticLayout.Builder obtain = StaticLayout.Builder.obtain(str, 0, str.length(), textPaint, getTextLayoutMaxWidth());
        obtain.setMaxLines(i2);
        obtain.setEllipsize(TextUtils.TruncateAt.END);
        obtain.setAlignment(this.s);
        obtain.setLineSpacing(f, 1.0f);
        StaticLayout build = obtain.build();
        canvas.save();
        canvas.translate(getPaddingLeft(), i);
        build.draw(canvas);
        canvas.restore();
        return build;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00c5  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r9) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.micolauncher.skills.music.model.lrc.LrcViewImpl1.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public void seekTo(int i, boolean z, boolean z2) {
        int a;
        if (this.z.size() < 2) {
            setCurrentRow(this.z.size() - 1);
        } else if (hasLrcRows()) {
            if ((!z || !this.p) && this.x != (a = a(i))) {
                setCurrentRow(a);
                int max = Math.max((b(this.x) + (this.c / 2)) - (getMeasuredHeight() / 2), 0);
                if (z2) {
                    if (!this.m.isFinished()) {
                        this.m.forceFinished(true);
                    }
                    scrollTo(getScrollX(), max);
                } else {
                    a(max, 1500);
                }
                invalidateView();
            }
        }
    }

    private int a(int i) {
        int i2 = (this.F.get() || ((long) i) <= this.E) ? 0 : this.x;
        this.F.set(false);
        int i3 = i2;
        while (i2 < this.z.size()) {
            if (i >= this.z.get(i2).getTimeMillis() && this.x != i2) {
                this.E = i;
                i3 = i2;
            }
            i2++;
        }
        return i3;
    }

    private int getFirstRowOffset() {
        return (getMeasuredHeight() - this.c) / 2;
    }

    private int b(int i) {
        if (i == 0) {
            return getFirstRowOffset();
        }
        float f = this.i;
        return (int) (getFirstRowOffset() + this.c + f + ((i - 1) * (this.a + f)));
    }

    @Override // com.xiaomi.micolauncher.skills.music.model.lrc.LrcView
    public void invalidateView() {
        invalidate();
    }

    private void setCurrentRow(int i) {
        this.b = this.x;
        this.x = i;
        if (this.C != null && this.z.size() > i) {
            this.C.onLrcRowChange(this.z.get(i));
        }
    }

    public void setOnLrcRowChangeListener(OnLrcRowChangeListener onLrcRowChangeListener) {
        this.C = onLrcRowChangeListener;
    }

    private void a(int i, int i2) {
        int scrollY = getScrollY();
        this.m.startScroll(getScrollX(), scrollY, 0, i - scrollY, i2);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (!this.m.isFinished() && this.m.computeScrollOffset()) {
            int scrollY = getScrollY();
            int currY = this.m.getCurrY();
            if (scrollY != currY && !this.p) {
                scrollTo(getScrollX(), currY);
            }
            this.n = (this.m.timePassed() * 3.0f) / 1500.0f;
            this.n = Math.min(this.n, 1.0f);
            invalidate();
        }
    }

    public void setTotalRowsToDraw(int i) {
        this.f = i;
    }
}
