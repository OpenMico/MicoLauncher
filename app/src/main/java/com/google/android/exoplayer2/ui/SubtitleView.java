package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.accessibility.CaptioningManager;
import android.widget.FrameLayout;
import androidx.annotation.Dimension;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.util.Util;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class SubtitleView extends FrameLayout implements TextOutput {
    public static final float DEFAULT_BOTTOM_PADDING_FRACTION = 0.08f;
    public static final float DEFAULT_TEXT_SIZE_FRACTION = 0.0533f;
    public static final int VIEW_TYPE_CANVAS = 1;
    public static final int VIEW_TYPE_WEB = 2;
    private List<Cue> a;
    private CaptionStyleCompat b;
    private int c;
    private float d;
    private float e;
    private boolean f;
    private boolean g;
    private int h;
    private a i;
    private View j;

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface ViewType {
    }

    /* loaded from: classes2.dex */
    public interface a {
        void a(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f, int i, float f2);
    }

    public SubtitleView(Context context) {
        this(context, null);
    }

    public SubtitleView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = Collections.emptyList();
        this.b = CaptionStyleCompat.DEFAULT;
        this.c = 0;
        this.d = 0.0533f;
        this.e = 0.08f;
        this.f = true;
        this.g = true;
        a aVar = new a(context);
        this.i = aVar;
        this.j = aVar;
        addView(this.j);
        this.h = 1;
    }

    @Override // com.google.android.exoplayer2.text.TextOutput
    public void onCues(List<Cue> list) {
        setCues(list);
    }

    public void setCues(@Nullable List<Cue> list) {
        if (list == null) {
            list = Collections.emptyList();
        }
        this.a = list;
        a();
    }

    public void setViewType(int i) {
        if (this.h != i) {
            switch (i) {
                case 1:
                    setView(new a(getContext()));
                    break;
                case 2:
                    setView(new f(getContext()));
                    break;
                default:
                    throw new IllegalArgumentException();
            }
            this.h = i;
        }
    }

    private <T extends View & a> void setView(T t) {
        removeView(this.j);
        View view = this.j;
        if (view instanceof f) {
            ((f) view).a();
        }
        this.j = t;
        this.i = t;
        addView(t);
    }

    public void setFixedTextSize(@Dimension int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        a(2, TypedValue.applyDimension(i, f, resources.getDisplayMetrics()));
    }

    public void setUserDefaultTextSize() {
        setFractionalTextSize(getUserCaptionFontScale() * 0.0533f);
    }

    public void setFractionalTextSize(float f) {
        setFractionalTextSize(f, false);
    }

    public void setFractionalTextSize(float f, boolean z) {
        a(z ? 1 : 0, f);
    }

    private void a(int i, float f) {
        this.c = i;
        this.d = f;
        a();
    }

    public void setApplyEmbeddedStyles(boolean z) {
        this.f = z;
        a();
    }

    public void setApplyEmbeddedFontSizes(boolean z) {
        this.g = z;
        a();
    }

    public void setUserDefaultStyle() {
        setStyle(getUserCaptionStyle());
    }

    public void setStyle(CaptionStyleCompat captionStyleCompat) {
        this.b = captionStyleCompat;
        a();
    }

    public void setBottomPaddingFraction(float f) {
        this.e = f;
        a();
    }

    private float getUserCaptionFontScale() {
        CaptioningManager captioningManager;
        if (Util.SDK_INT < 19 || isInEditMode() || (captioningManager = (CaptioningManager) getContext().getSystemService("captioning")) == null || !captioningManager.isEnabled()) {
            return 1.0f;
        }
        return captioningManager.getFontScale();
    }

    private CaptionStyleCompat getUserCaptionStyle() {
        if (Util.SDK_INT < 19 || isInEditMode()) {
            return CaptionStyleCompat.DEFAULT;
        }
        CaptioningManager captioningManager = (CaptioningManager) getContext().getSystemService("captioning");
        if (captioningManager == null || !captioningManager.isEnabled()) {
            return CaptionStyleCompat.DEFAULT;
        }
        return CaptionStyleCompat.createFromCaptionStyle(captioningManager.getUserStyle());
    }

    private void a() {
        this.i.a(getCuesWithStylingPreferencesApplied(), this.b, this.d, this.c, this.e);
    }

    private List<Cue> getCuesWithStylingPreferencesApplied() {
        if (this.f && this.g) {
            return this.a;
        }
        ArrayList arrayList = new ArrayList(this.a.size());
        for (int i = 0; i < this.a.size(); i++) {
            arrayList.add(a(this.a.get(i)));
        }
        return arrayList;
    }

    private Cue a(Cue cue) {
        Cue.Builder buildUpon = cue.buildUpon();
        if (!this.f) {
            e.a(buildUpon);
        } else if (!this.g) {
            e.b(buildUpon);
        }
        return buildUpon.build();
    }
}
