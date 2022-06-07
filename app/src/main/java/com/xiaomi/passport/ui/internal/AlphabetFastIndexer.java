package com.xiaomi.passport.ui.internal;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.WrapperListAdapter;
import androidx.appcompat.widget.AppCompatTextView;
import com.xiaomi.passport.ui.R;
import java.lang.ref.WeakReference;
import java.util.Arrays;

@SuppressLint({"NewApi"})
/* loaded from: classes4.dex */
public class AlphabetFastIndexer extends AppCompatTextView {
    private static final int FADE_DELAYED = 1500;
    private static final int MSG_FADE = 1;
    private static final String STARRED_LABEL = "★";
    public static final String STARRED_TITLE = "!";
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_NONE = 0;
    private Handler mHandler;
    private int mLastAlphabetIndex;
    private int mListScrollState;
    private AdapterView<?> mListView;
    private TextView mOverlay;
    private Runnable mRefreshMaskRunnable;
    private int mState;
    private ValueAnimator.AnimatorUpdateListener mTextHilightAnimListener;
    private TextHilighter mTextHilighter;

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AlphabetFastIndexer(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTextHilightAnimListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                AlphabetFastIndexer.this.mTextHilighter.update(AlphabetFastIndexer.this.getWidth() / 2.0f, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                AlphabetFastIndexer.this.postInvalidate();
            }
        };
        this.mListScrollState = 0;
        this.mState = 0;
        this.mRefreshMaskRunnable = new Runnable() { // from class: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.2
            @Override // java.lang.Runnable
            public void run() {
                AlphabetFastIndexer.this.refreshMask();
            }
        };
        this.mHandler = new Handler() { // from class: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1 && AlphabetFastIndexer.this.mOverlay != null) {
                    AlphabetFastIndexer.this.mOverlay.setVisibility(8);
                }
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AlphabetFastIndexer, i, 0);
        this.mTextHilighter = new TextHilighter(context, obtainStyledAttributes);
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.mLastAlphabetIndex = -1;
        post(this.mRefreshMaskRunnable);
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int paddingTop = getPaddingTop();
        int height = (getHeight() - paddingTop) - getPaddingBottom();
        if (height > 0) {
            String[] strArr = this.mTextHilighter.mIndexes;
            float length = height / strArr.length;
            float width = getWidth() / 2.0f;
            this.mTextHilighter.beginDraw();
            float f = paddingTop + (length / 2.0f);
            for (int i = 0; i < strArr.length; i++) {
                this.mTextHilighter.draw(canvas, isPressed(), i, width, f);
                f += length;
            }
            this.mTextHilighter.endDraw(canvas);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        attatch();
    }

    public void attatch() {
        this.mLastAlphabetIndex = -1;
        FrameLayout frameLayout = (FrameLayout) getParent();
        this.mOverlay = (TextView) frameLayout.findViewById(R.id.fast_indexer_high_light);
        this.mListView = (AdapterView) frameLayout.findViewById(R.id.fast_indexer_list);
        this.mOverlay.setVisibility(8);
        refreshMask();
    }

    public void drawThumb(CharSequence charSequence) {
        if (this.mState == 0 && this.mListScrollState == 2) {
            drawThumbInternal(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshMask() {
        int sectionForPosition;
        if (this.mListView != null) {
            int i = 0;
            SectionIndexer sectionIndexer = getSectionIndexer();
            if (!(sectionIndexer == null || (sectionForPosition = sectionIndexer.getSectionForPosition(this.mListView.getFirstVisiblePosition() - getListOffset())) == -1)) {
                String str = (String) sectionIndexer.getSections()[sectionForPosition];
                if (!TextUtils.isEmpty(str)) {
                    i = Arrays.binarySearch(this.mTextHilighter.mIndexes, str);
                }
            }
            if (this.mLastAlphabetIndex != i) {
                this.mLastAlphabetIndex = i;
                if (1 != this.mState) {
                    slidTextHilightBackground(this.mLastAlphabetIndex);
                }
                invalidate();
            }
        }
    }

    private SectionIndexer getSectionIndexer() {
        boolean z;
        AdapterView<?> adapterView = this.mListView;
        if (adapterView == null) {
            return null;
        }
        Object adapter = adapterView.getAdapter();
        while (true) {
            z = adapter instanceof SectionIndexer;
            if (z || !(adapter instanceof WrapperListAdapter)) {
                break;
            }
            adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
        }
        if (z) {
            return (SectionIndexer) adapter;
        }
        return null;
    }

    private int getListOffset() {
        AdapterView<?> adapterView = this.mListView;
        if (adapterView instanceof ListView) {
            return ((ListView) adapterView).getHeaderViewsCount();
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0039  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            android.widget.AdapterView<?> r0 = r4.mListView
            r1 = 0
            if (r0 != 0) goto L_0x0009
            r4.stop(r1)
            return r1
        L_0x0009:
            android.widget.SectionIndexer r0 = r4.getSectionIndexer()
            if (r0 != 0) goto L_0x0013
            r4.stop(r1)
            return r1
        L_0x0013:
            int r2 = r5.getAction()
            r2 = r2 & 255(0xff, float:3.57E-43)
            r3 = 1
            switch(r2) {
                case 0: goto L_0x0024;
                case 1: goto L_0x001e;
                case 2: goto L_0x0029;
                case 3: goto L_0x001e;
                default: goto L_0x001d;
            }
        L_0x001d:
            goto L_0x0078
        L_0x001e:
            int r5 = r4.mLastAlphabetIndex
            r4.slidTextHilightBackground(r5)
            goto L_0x0078
        L_0x0024:
            r4.mState = r3
            r4.setPressed(r3)
        L_0x0029:
            float r2 = r5.getY()
            int r2 = r4.getPostion(r2, r0)
            if (r2 >= 0) goto L_0x0039
            android.widget.AdapterView<?> r5 = r4.mListView
            r5.setSelection(r1)
            goto L_0x007d
        L_0x0039:
            r4.scrollTo(r0, r2)
            com.xiaomi.passport.ui.internal.AlphabetFastIndexer$TextHilighter r0 = r4.mTextHilighter
            if (r0 == 0) goto L_0x007d
            float r0 = r5.getY()
            int r1 = r4.getTop()
            int r2 = r4.getPaddingTop()
            int r1 = r1 + r2
            float r1 = (float) r1
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 <= 0) goto L_0x007d
            float r0 = r5.getY()
            int r1 = r4.getBottom()
            int r2 = r4.getPaddingBottom()
            int r1 = r1 - r2
            float r1 = (float) r1
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            if (r0 >= 0) goto L_0x007d
            com.xiaomi.passport.ui.internal.AlphabetFastIndexer$TextHilighter r0 = r4.mTextHilighter
            int r1 = r4.getWidth()
            int r1 = r1 / 2
            float r1 = (float) r1
            float r5 = r5.getY()
            r0.update(r1, r5)
            r4.postInvalidate()
            goto L_0x007d
        L_0x0078:
            r5 = 1500(0x5dc, float:2.102E-42)
            r4.stop(r5)
        L_0x007d:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.passport.ui.internal.AlphabetFastIndexer.onTouchEvent(android.view.MotionEvent):boolean");
    }

    void stop(int i) {
        setPressed(false);
        this.mState = 0;
        postInvalidate();
        this.mHandler.removeMessages(1);
        if (i <= 0) {
            TextView textView = this.mOverlay;
            if (textView != null) {
                textView.setVisibility(8);
                return;
            }
            return;
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), i);
    }

    private int getPostion(float f, SectionIndexer sectionIndexer) {
        Object[] sections = sectionIndexer.getSections();
        if (sections == null) {
            return -1;
        }
        int paddingTop = getPaddingTop();
        int height = (getHeight() - paddingTop) - getPaddingBottom();
        if (height <= 0) {
            return -1;
        }
        int length = (int) (this.mTextHilighter.mIndexes.length * ((f - paddingTop) / height));
        if (length < 0) {
            return -1;
        }
        if (length >= this.mTextHilighter.mIndexes.length) {
            return sections.length;
        }
        int binarySearch = Arrays.binarySearch(sections, this.mTextHilighter.mIndexes[length]);
        if (binarySearch < 0) {
            binarySearch = (-binarySearch) - 2;
        }
        if (binarySearch < 0) {
            return 0;
        }
        return binarySearch;
    }

    private void scrollTo(SectionIndexer sectionIndexer, int i) {
        int i2;
        int i3;
        int i4;
        int count = this.mListView.getCount();
        int listOffset = getListOffset();
        float f = (1.0f / count) / 8.0f;
        Object[] sections = sectionIndexer.getSections();
        if (sections == null || sections.length <= 1) {
            int round = Math.round(i * count);
            AdapterView<?> adapterView = this.mListView;
            if (adapterView instanceof ExpandableListView) {
                ExpandableListView expandableListView = (ExpandableListView) adapterView;
                expandableListView.setSelectionFromTop(expandableListView.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(round + listOffset)), 0);
            } else if (adapterView instanceof ListView) {
                ((ListView) adapterView).setSelectionFromTop(round + listOffset, 0);
            } else {
                adapterView.setSelection(round + listOffset);
            }
            i2 = -1;
        } else {
            int length = sections.length;
            int i5 = i >= length ? length - 1 : i;
            int positionForSection = sectionIndexer.getPositionForSection(i5);
            int i6 = i5 + 1;
            int positionForSection2 = i5 < length + (-1) ? sectionIndexer.getPositionForSection(i6) : count;
            if (positionForSection2 == positionForSection) {
                i4 = i5;
                i3 = positionForSection;
                while (true) {
                    if (i4 > 0) {
                        i4--;
                        i3 = sectionIndexer.getPositionForSection(i4);
                        if (i3 == positionForSection) {
                            if (i4 == 0) {
                                i4 = i5;
                                i2 = 0;
                                break;
                            }
                        } else {
                            i2 = i4;
                            break;
                        }
                    } else {
                        i2 = i5;
                        i4 = i2;
                        break;
                    }
                }
            } else {
                i4 = i5;
                i3 = positionForSection;
                i2 = i4;
            }
            int i7 = i6 + 1;
            int i8 = i6;
            while (i7 < length && sectionIndexer.getPositionForSection(i7) == positionForSection2) {
                i7++;
                i8++;
            }
            float f2 = length;
            float f3 = i4 / f2;
            float f4 = i8 / f2;
            float f5 = i / f2;
            if (i4 != i5 || f5 - f3 >= f) {
                i3 += Math.round(((positionForSection2 - i3) * (f5 - f3)) / (f4 - f3));
            }
            int i9 = count - 1;
            if (i3 > i9) {
                i3 = i9;
            }
            AdapterView<?> adapterView2 = this.mListView;
            if (adapterView2 instanceof ExpandableListView) {
                ExpandableListView expandableListView2 = (ExpandableListView) adapterView2;
                expandableListView2.setSelectionFromTop(expandableListView2.getFlatListPosition(ExpandableListView.getPackedPositionForGroup(i3 + listOffset)), 0);
            } else if (adapterView2 instanceof ListView) {
                ((ListView) adapterView2).setSelectionFromTop(i3 + listOffset, 0);
            } else {
                adapterView2.setSelection(i3 + listOffset);
            }
        }
        if (i2 >= 0 && sections != null) {
            String obj = sections[i2].toString();
            if (!TextUtils.isEmpty(obj)) {
                drawThumbInternal(obj.subSequence(0, 1));
            }
        }
    }

    private void drawThumbInternal(CharSequence charSequence) {
        if (this.mListView != null) {
            if (TextUtils.equals(charSequence, "!")) {
                charSequence = STARRED_LABEL;
            }
            this.mOverlay.setText(charSequence);
            if (getVisibility() == 0) {
                this.mOverlay.setVisibility(0);
                this.mHandler.removeMessages(1);
                this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 1500L);
            }
        }
    }

    private void slidTextHilightBackground(int i) {
        if (this.mTextHilighter != null) {
            if (i < 0) {
                i = 0;
            }
            int paddingTop = getPaddingTop();
            float height = ((getHeight() - paddingTop) - getPaddingBottom()) / this.mTextHilighter.mIndexes.length;
            this.mTextHilighter.startSlidding((i * height) + paddingTop + (height / 2.0f) + 1.0f, this.mTextHilightAnimListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class TextHilighter {
        int mActivatedColor;
        ValueAnimator mAnimator;
        BitmapDrawable mBackground;
        Bitmap mBmpBuffer;
        Canvas mCanvas;
        Xfermode mClearMode;
        Xfermode mDstInMode;
        int mHilightColor;
        String[] mIndexes;
        int mNormalColor;
        Rect mTextBoundIntersect;
        Paint mPaint = new Paint();
        Rect mTextBound = new Rect();
        Rect mSrcBounds = new Rect();

        TextHilighter(Context context, TypedArray typedArray) {
            Resources resources = context.getResources();
            CharSequence[] textArray = typedArray.getTextArray(R.styleable.AlphabetFastIndexer_indexerTable);
            if (textArray != null) {
                this.mIndexes = new String[textArray.length];
                int i = 0;
                for (CharSequence charSequence : textArray) {
                    i++;
                    this.mIndexes[i] = charSequence.toString();
                }
            } else {
                this.mIndexes = resources.getStringArray(R.array.alphabet_table);
            }
            this.mNormalColor = typedArray.getColor(R.styleable.AlphabetFastIndexer_indexerTextColor, 0);
            this.mActivatedColor = typedArray.getColor(R.styleable.AlphabetFastIndexer_indexerTextActivatedColor, 0);
            this.mHilightColor = typedArray.getColor(R.styleable.AlphabetFastIndexer_indexerTextHighlightColor, 0);
            this.mBackground = (BitmapDrawable) typedArray.getDrawable(R.styleable.AlphabetFastIndexer_indexerTextHighligtBackground);
            this.mPaint.setTextSize(typedArray.getDimension(R.styleable.AlphabetFastIndexer_indexerTextSize, 0.0f));
            this.mPaint.setAntiAlias(true);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            this.mBmpBuffer = this.mBackground.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
            this.mCanvas = new Canvas(this.mBmpBuffer);
            this.mTextBoundIntersect = new Rect();
            this.mClearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
            this.mDstInMode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
            update(0.0f, 0.0f);
        }

        void update(float f, float f2) {
            float intrinsicWidth = this.mBackground.getIntrinsicWidth() / 2.0f;
            float intrinsicHeight = this.mBackground.getIntrinsicHeight() / 2.0f;
            this.mTextBound.set((int) ((f - intrinsicWidth) + 1.0f), (int) (f2 - intrinsicHeight), (int) (f + intrinsicWidth + 1.0f), (int) (f2 + intrinsicHeight));
        }

        void draw(Canvas canvas, boolean z, int i, float f, float f2) {
            Paint paint = this.mPaint;
            String str = TextUtils.equals(this.mIndexes[i], "!") ? AlphabetFastIndexer.STARRED_LABEL : this.mIndexes[i];
            paint.getTextBounds(str, 0, str.length(), this.mSrcBounds);
            float width = this.mSrcBounds.width();
            float height = this.mSrcBounds.height();
            paint.setColor(z ? this.mActivatedColor : this.mNormalColor);
            canvas.drawText(str, f, f2 - ((this.mSrcBounds.top + this.mSrcBounds.bottom) / 2.0f), paint);
            float f3 = width / 2.0f;
            float f4 = height / 2.0f;
            if (this.mTextBoundIntersect.intersect((int) (f - f3), (int) (f2 - f4), (int) (f3 + f), (int) (f4 + f2))) {
                paint.setColor(this.mHilightColor);
                this.mCanvas.drawText(str, f - this.mTextBound.left, (f2 - this.mTextBound.top) - ((this.mSrcBounds.top + this.mSrcBounds.bottom) / 2.0f), paint);
                this.mTextBoundIntersect.set(this.mTextBound);
            }
        }

        void beginDraw() {
            Paint paint = this.mPaint;
            Xfermode xfermode = paint.getXfermode();
            paint.setXfermode(this.mClearMode);
            this.mCanvas.drawPaint(paint);
            paint.setXfermode(xfermode);
            this.mBackground.setBounds(0, 0, this.mTextBound.width(), this.mTextBound.height());
            this.mBackground.draw(this.mCanvas);
            this.mTextBoundIntersect.set(this.mTextBound);
        }

        void endDraw(Canvas canvas) {
            Paint paint = this.mBackground.getPaint();
            Xfermode xfermode = paint.getXfermode();
            paint.setXfermode(this.mDstInMode);
            this.mBackground.draw(this.mCanvas);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(this.mBmpBuffer, (Rect) null, this.mTextBound, (Paint) null);
        }

        void startSlidding(float f, ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
            ValueAnimator valueAnimator = this.mAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            Rect rect = this.mTextBound;
            this.mAnimator = ValueAnimator.ofFloat(rect == null ? f : (rect.top + this.mTextBound.bottom) / 2.0f, f);
            this.mAnimator.addUpdateListener(animatorUpdateListener);
            this.mAnimator.setDuration(200L);
            this.mAnimator.start();
        }
    }

    /* loaded from: classes4.dex */
    public static class OnScrollerDecorator implements AbsListView.OnScrollListener {
        private final WeakReference<AlphabetFastIndexer> mIndexerRef;
        private final AbsListView.OnScrollListener mListener;
        private String mPreviousThumb = "";

        protected String itemToString(Object obj) {
            return null;
        }

        public OnScrollerDecorator(AlphabetFastIndexer alphabetFastIndexer, AbsListView.OnScrollListener onScrollListener) {
            this.mIndexerRef = new WeakReference<>(alphabetFastIndexer);
            this.mListener = onScrollListener;
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.refreshMask();
                String itemToString = itemToString(((ListAdapter) absListView.getAdapter()).getItem(i));
                if (TextUtils.isEmpty(itemToString)) {
                    String upperCase = itemToString.substring(0, 1).toUpperCase();
                    if (!TextUtils.equals(upperCase, this.mPreviousThumb)) {
                        alphabetFastIndexer.drawThumb(upperCase);
                        this.mPreviousThumb = upperCase;
                    }
                }
            }
            AbsListView.OnScrollListener onScrollListener = this.mListener;
            if (onScrollListener != null) {
                onScrollListener.onScroll(absListView, i, i2, i3);
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            AlphabetFastIndexer alphabetFastIndexer = this.mIndexerRef.get();
            if (alphabetFastIndexer != null) {
                alphabetFastIndexer.mListScrollState = i;
            }
            AbsListView.OnScrollListener onScrollListener = this.mListener;
            if (onScrollListener != null) {
                onScrollListener.onScrollStateChanged(absListView, i);
            }
        }
    }
}
