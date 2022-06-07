package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.ui.SubtitleView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: CanvasSubtitleOutput.java */
/* loaded from: classes2.dex */
final class a extends View implements SubtitleView.a {
    private final List<d> a;
    private List<Cue> b;
    private int c;
    private float d;
    private CaptionStyleCompat e;
    private float f;

    public a(Context context) {
        this(context, null);
    }

    public a(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new ArrayList();
        this.b = Collections.emptyList();
        this.c = 0;
        this.d = 0.0533f;
        this.e = CaptionStyleCompat.DEFAULT;
        this.f = 0.08f;
    }

    @Override // com.google.android.exoplayer2.ui.SubtitleView.a
    public void a(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f, int i, float f2) {
        this.b = list;
        this.e = captionStyleCompat;
        this.d = f;
        this.c = i;
        this.f = f2;
        while (this.a.size() < list.size()) {
            this.a.add(new d(getContext()));
        }
        invalidate();
    }

    @Override // android.view.View
    public void dispatchDraw(Canvas canvas) {
        List<Cue> list = this.b;
        if (!list.isEmpty()) {
            int height = getHeight();
            int paddingLeft = getPaddingLeft();
            int paddingTop = getPaddingTop();
            int width = getWidth() - getPaddingRight();
            int paddingBottom = height - getPaddingBottom();
            if (paddingBottom > paddingTop && width > paddingLeft) {
                int i = paddingBottom - paddingTop;
                float a = e.a(this.c, this.d, height, i);
                if (a > 0.0f) {
                    int size = list.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Cue cue = list.get(i2);
                        if (cue.verticalType != Integer.MIN_VALUE) {
                            cue = a(cue);
                        }
                        this.a.get(i2).a(cue, this.e, a, e.a(cue.textSizeType, cue.textSize, height, i), this.f, canvas, paddingLeft, paddingTop, width, paddingBottom);
                        i2++;
                        size = size;
                        i = i;
                        paddingBottom = paddingBottom;
                        width = width;
                    }
                }
            }
        }
    }

    private static Cue a(Cue cue) {
        Cue.Builder textAlignment = cue.buildUpon().setPosition(-3.4028235E38f).setPositionAnchor(Integer.MIN_VALUE).setTextAlignment(null);
        if (cue.lineType == 0) {
            textAlignment.setLine(1.0f - cue.line, 0);
        } else {
            textAlignment.setLine((-cue.line) - 1.0f, 1);
        }
        int i = cue.lineAnchor;
        if (i == 0) {
            textAlignment.setLineAnchor(2);
        } else if (i == 2) {
            textAlignment.setLineAnchor(0);
        }
        return textAlignment.build();
    }
}
