package com.xiaomi.micolauncher.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import com.android.internal.graphics.palette.Palette;
import com.xiaomi.micolauncher.R;
import java.io.IOException;
import java.lang.ref.WeakReference;

/* loaded from: classes3.dex */
public class PaletteShadowView extends AppCompatImageView {
    private static final int DEFAULT_OFFSET = 4;
    private static final int DEFAULT_SHADOW_RADIUS = 12;
    private static final int MSG = 257;
    private int DEFAULT_PADDING;
    private AsyncTask asyncTask;
    private Bitmap bitmap;
    private Drawable initialDrawable;
    private int offsetX;
    private int offsetY;
    private Paint paint;
    private Palette palette;
    private Palette.PaletteAsyncListener paletteAsyncListener;
    private Bitmap realBitmap;
    private int roundedRadius;
    private int shadowColor;
    private ShadowHandler shadowHandler;
    private Paint shadowPaint;
    private int shadowRadius;
    private RectF shadowRect;
    private int size;

    public PaletteShadowView(Context context) {
        super(context);
        this.DEFAULT_PADDING = 28;
        this.size = 365;
        this.paint = new Paint();
        this.shadowPaint = new Paint();
        this.shadowColor = -1;
        this.roundedRadius = 0;
        this.shadowHandler = new ShadowHandler();
        initListener();
    }

    public PaletteShadowView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
        this.shadowHandler = new ShadowHandler();
        initListener();
    }

    public PaletteShadowView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.DEFAULT_PADDING = 28;
        this.size = 365;
        this.paint = new Paint();
        this.shadowPaint = new Paint();
        this.shadowColor = -1;
        this.roundedRadius = 0;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PaletteShadowView);
        this.initialDrawable = obtainStyledAttributes.getDrawable(5);
        this.roundedRadius = obtainStyledAttributes.getInt(2, 0);
        this.shadowRadius = obtainStyledAttributes.getInt(4, 0);
        this.shadowColor = obtainStyledAttributes.getColor(3, -1);
        this.offsetX = obtainStyledAttributes.getInt(0, 4);
        this.offsetY = obtainStyledAttributes.getInt(1, 4);
        obtainStyledAttributes.recycle();
        init();
    }

    private void init() {
        this.paint.setAntiAlias(true);
        this.paint.setDither(true);
        setLayerType(1, null);
        this.shadowPaint.setAntiAlias(true);
        this.shadowPaint.setDither(true);
        this.shadowPaint.setShadowLayer(this.shadowRadius, this.offsetX, this.offsetY, 0);
        this.shadowHandler = new ShadowHandler();
        initListener();
        Drawable drawable = this.initialDrawable;
        if (drawable != null) {
            getDrawableToBitmap(drawable);
            if (this.shadowColor != -1) {
                this.shadowHandler.sendEmptyMessage(257);
            } else {
                initShadowColor(this.bitmap);
            }
        }
    }

    private void initListener() {
        if (this.paletteAsyncListener == null) {
            this.paletteAsyncListener = new Palette.PaletteAsyncListener() { // from class: com.xiaomi.micolauncher.common.widget.-$$Lambda$PaletteShadowView$kmn44ebgJXSX07dWUvs22t0X4f4
                public final void onGenerated(Palette palette) {
                    PaletteShadowView.lambda$initListener$0(PaletteShadowView.this, palette);
                }
            };
        }
    }

    public static /* synthetic */ void lambda$initListener$0(PaletteShadowView paletteShadowView, Palette palette) {
        if (palette != null) {
            paletteShadowView.palette = palette;
            if (paletteShadowView.palette.getDominantSwatch() != null) {
                paletteShadowView.shadowColor = paletteShadowView.palette.getDominantSwatch().getRgb();
                paletteShadowView.shadowHandler.sendEmptyMessage(257);
            }
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onMeasure(int i, int i2) {
        this.size = View.MeasureSpec.getSize(i);
        setMeasuredDimension(measureDimension(this.size + getPaddingLeft() + getPaddingRight(), i), measureDimension(this.size + getPaddingTop() + getPaddingBottom(), i2));
    }

    private int measureDimension(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode != 1073741824) {
            if (mode != 0) {
                size = mode == Integer.MIN_VALUE ? this.size : i;
            } else if (this.realBitmap == null) {
                size = 0;
            }
        }
        return size < i ? i : size;
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        Bitmap bitmap;
        super.onSizeChanged(i, i2, i3, i4);
        int i5 = this.DEFAULT_PADDING;
        this.shadowRect = new RectF(i5 * 0.9f, i5 * 0.9f, getWidth() - (this.DEFAULT_PADDING * 0.8f), getHeight() - (this.DEFAULT_PADDING * 0.8f));
        if (this.realBitmap == null && (bitmap = this.bitmap) != null) {
            initShadowColor(bitmap);
            this.realBitmap = createRoundedBitmap(this.bitmap, this.roundedRadius);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        if (this.realBitmap != null) {
            RectF rectF = this.shadowRect;
            int i = this.roundedRadius;
            canvas.drawRoundRect(rectF, i, i, this.shadowPaint);
            canvas.drawBitmap(this.realBitmap, 0.0f, 0.0f, this.paint);
        }
        if (this.shadowColor != -1) {
            this.asyncTask.cancel(true);
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageResource(int i) {
        try {
            this.bitmap = BitmapFactory.decodeResource(getResources(), i);
            initShadowColor(this.bitmap);
            this.realBitmap = createRoundedBitmap(this.bitmap, this.roundedRadius);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageDrawable(@Nullable Drawable drawable) {
        try {
            getDrawableToBitmap(drawable);
            this.realBitmap = createRoundedBitmap(this.bitmap, this.roundedRadius);
            initShadowColor(this.bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        try {
            this.bitmap = bitmap;
            initShadowColor(bitmap);
            this.realBitmap = createRoundedBitmap(this.bitmap, this.roundedRadius);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageURI(@Nullable Uri uri) {
        try {
            this.bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            initShadowColor(this.bitmap);
            this.realBitmap = createRoundedBitmap(this.bitmap, this.roundedRadius);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getDrawableToBitmap(Drawable drawable) {
        if (drawable != null) {
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(createBitmap);
                drawable.setBounds(0, 0, createBitmap.getWidth(), createBitmap.getHeight());
                drawable.draw(canvas);
                this.bitmap = createBitmap;
            }
        }
    }

    private Bitmap createRoundedBitmap(Bitmap bitmap, int i) {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return null;
        }
        Bitmap createBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        BitmapShader bitmapShader = new BitmapShader(Bitmap.createScaledBitmap(bitmap, getWidth() - ((int) (this.DEFAULT_PADDING * 0.8d)), getHeight() - ((int) (this.DEFAULT_PADDING * 0.8d)), false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        this.paint.setAntiAlias(true);
        this.paint.setShader(bitmapShader);
        Canvas canvas = new Canvas(createBitmap);
        int i2 = this.DEFAULT_PADDING;
        RectF rectF = new RectF(i2 * 0.8f, i2 * 0.8f, getWidth() - (this.DEFAULT_PADDING * 0.7f), getHeight() - (this.DEFAULT_PADDING * 0.7f));
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        float f = i;
        canvas.drawRoundRect(rectF, f, f, this.paint);
        canvas.setBitmap(null);
        return createBitmap;
    }

    private void initShadowColor(Bitmap bitmap) {
        if (bitmap != null) {
            this.asyncTask = Palette.from(bitmap).generate(this.paletteAsyncListener);
        }
    }

    public void setShadowRadius(int i) {
        this.shadowRadius = i;
        this.shadowHandler.sendEmptyMessage(257);
    }

    public void setShadowColor(int i) {
        this.shadowColor = i;
        this.shadowHandler.sendEmptyMessage(257);
    }

    public void setShadowOffest(int i, int i2) {
        this.offsetX = i;
        this.offsetY = i2;
        this.shadowHandler.sendEmptyMessage(257);
    }

    public int getShadowColor() {
        return this.shadowColor;
    }

    public int getShadowRadius() {
        return this.shadowRadius;
    }

    public void setRoundedRadius(int i) {
        Bitmap bitmap = this.bitmap;
        if (bitmap != null) {
            this.roundedRadius = i;
            this.realBitmap = createRoundedBitmap(bitmap, this.roundedRadius);
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public int getRoundedRadius() {
        return this.roundedRadius;
    }

    public int[] getVibrantColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getVibrantSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getVibrantSwatch().getTitleTextColor(), this.palette.getVibrantSwatch().getBodyTextColor(), this.palette.getVibrantSwatch().getRgb()};
    }

    public int[] getDarkVibrantColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getDarkVibrantSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getDarkVibrantSwatch().getTitleTextColor(), this.palette.getDarkVibrantSwatch().getBodyTextColor(), this.palette.getDarkVibrantSwatch().getRgb()};
    }

    public int[] getLightVibrantColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getLightVibrantSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getLightVibrantSwatch().getTitleTextColor(), this.palette.getLightVibrantSwatch().getBodyTextColor(), this.palette.getLightVibrantSwatch().getRgb()};
    }

    public int[] getMutedColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getMutedSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getMutedSwatch().getTitleTextColor(), this.palette.getMutedSwatch().getBodyTextColor(), this.palette.getMutedSwatch().getRgb()};
    }

    public int[] getDarkMutedColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getDarkMutedSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getDarkMutedSwatch().getTitleTextColor(), this.palette.getDarkMutedSwatch().getBodyTextColor(), this.palette.getDarkMutedSwatch().getRgb()};
    }

    public int[] getLightMutedColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getLightMutedSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getLightMutedSwatch().getTitleTextColor(), this.palette.getLightMutedSwatch().getBodyTextColor(), this.palette.getLightMutedSwatch().getRgb()};
    }

    public int[] getDominantColor() {
        Palette palette = this.palette;
        if (palette == null || palette.getDominantSwatch() == null) {
            return null;
        }
        return new int[]{this.palette.getDominantSwatch().getTitleTextColor(), this.palette.getDominantSwatch().getBodyTextColor(), this.palette.getDominantSwatch().getRgb()};
    }

    public void setLightMutedColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getLightMutedSwatch() != null) {
            this.shadowColor = this.palette.getLightMutedSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public void setDarkMutedColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getDarkMutedSwatch() != null) {
            this.shadowColor = this.palette.getDarkMutedSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public void setMutedColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getMutedSwatch() != null) {
            this.shadowColor = this.palette.getMutedSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public void setLightVibrantColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getLightVibrantSwatch() != null) {
            this.shadowColor = this.palette.getLightVibrantSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public void setDarkVibrantColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getDarkVibrantSwatch() != null) {
            this.shadowColor = this.palette.getDarkVibrantSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public void setVibrantColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getMutedSwatch() != null) {
            this.shadowColor = this.palette.getMutedSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    public void setDominantColor() {
        Palette palette = this.palette;
        if (palette != null && palette.getDominantSwatch() != null) {
            this.shadowColor = this.palette.getDominantSwatch().getRgb();
            this.shadowHandler.sendEmptyMessage(257);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.shadowHandler.removeCallbacksAndMessages(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class ShadowHandler extends Handler {
        private final WeakReference<PaletteShadowView> weakReference;

        private ShadowHandler(PaletteShadowView paletteShadowView) {
            this.weakReference = new WeakReference<>(paletteShadowView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (this.weakReference.get() != null) {
                PaletteShadowView paletteShadowView = this.weakReference.get();
                if (paletteShadowView.offsetX < 4) {
                    paletteShadowView.offsetX = 4;
                }
                if (paletteShadowView.offsetY < 4) {
                    paletteShadowView.offsetY = 4;
                }
                if (paletteShadowView.shadowRadius < 12) {
                    paletteShadowView.shadowRadius = 12;
                }
                paletteShadowView.shadowPaint.setShadowLayer(paletteShadowView.shadowRadius, paletteShadowView.offsetX, paletteShadowView.offsetY, paletteShadowView.shadowColor);
                paletteShadowView.invalidate();
            }
        }
    }
}
