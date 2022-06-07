package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Base64;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.FrameLayout;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.ui.SpannedToHtmlConverter;
import com.google.android.exoplayer2.ui.SubtitleView;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.base.Charsets;
import io.netty.handler.codec.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jetty.http.MimeTypes;

/* compiled from: WebViewSubtitleOutput.java */
/* loaded from: classes2.dex */
final class f extends FrameLayout implements SubtitleView.a {
    private final a a;
    private final WebView b;
    private List<Cue> c;
    private CaptionStyleCompat d;
    private float e;
    private int f;
    private float g;

    private static String a(int i) {
        switch (i) {
            case 1:
                return "vertical-rl";
            case 2:
                return "vertical-lr";
            default:
                return "horizontal-tb";
        }
    }

    private static int b(int i) {
        switch (i) {
            case 1:
                return -50;
            case 2:
                return -100;
            default:
                return 0;
        }
    }

    public f(Context context) {
        this(context, null);
    }

    public f(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.c = Collections.emptyList();
        this.d = CaptionStyleCompat.DEFAULT;
        this.e = 0.0533f;
        this.f = 0;
        this.g = 0.08f;
        this.a = new a(context, attributeSet);
        this.b = new WebView(this, context, attributeSet) { // from class: com.google.android.exoplayer2.ui.f.1
            @Override // android.webkit.WebView, android.view.View
            public boolean onTouchEvent(MotionEvent motionEvent) {
                super.onTouchEvent(motionEvent);
                return false;
            }

            @Override // android.view.View
            public boolean performClick() {
                super.performClick();
                return false;
            }
        };
        this.b.setBackgroundColor(0);
        addView(this.a);
        addView(this.b);
    }

    @Override // com.google.android.exoplayer2.ui.SubtitleView.a
    public void a(List<Cue> list, CaptionStyleCompat captionStyleCompat, float f, int i, float f2) {
        this.d = captionStyleCompat;
        this.e = f;
        this.f = i;
        this.g = f2;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i2 = 0; i2 < list.size(); i2++) {
            Cue cue = list.get(i2);
            if (cue.bitmap != null) {
                arrayList.add(cue);
            } else {
                arrayList2.add(cue);
            }
        }
        if (!this.c.isEmpty() || !arrayList2.isEmpty()) {
            this.c = arrayList2;
            b();
        }
        this.a.a(arrayList, captionStyleCompat, f, i, f2);
        invalidate();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z && !this.c.isEmpty()) {
            b();
        }
    }

    public void a() {
        this.b.destroy();
    }

    private void b() {
        String str;
        int i;
        int i2;
        String str2;
        int i3;
        String str3;
        String str4;
        String str5;
        f fVar = this;
        StringBuilder sb = new StringBuilder();
        int i4 = 0;
        int i5 = 1;
        float f = 1.2f;
        sb.append(Util.formatInvariant("<body><div style='-webkit-user-select:none;position:fixed;top:0;bottom:0;left:0;right:0;color:%s;font-size:%s;line-height:%.2f;text-shadow:%s;'>", b.a(fVar.d.foregroundColor), fVar.a(fVar.f, fVar.e), Float.valueOf(1.2f), a(fVar.d)));
        HashMap hashMap = new HashMap();
        hashMap.put(b.a("default_bg"), Util.formatInvariant("background-color:%s;", b.a(fVar.d.backgroundColor)));
        int i6 = 0;
        while (i6 < fVar.c.size()) {
            Cue cue = fVar.c.get(i6);
            float f2 = cue.position != -3.4028235E38f ? cue.position * 100.0f : 50.0f;
            int b = b(cue.positionAnchor);
            if (cue.line == -3.4028235E38f) {
                Object[] objArr = new Object[i5];
                objArr[i4] = Float.valueOf((1.0f - fVar.g) * 100.0f);
                str = Util.formatInvariant("%.2f%%", objArr);
                i = -100;
                i2 = i4;
            } else if (cue.lineType != i5) {
                Object[] objArr2 = new Object[i5];
                objArr2[i4] = Float.valueOf(cue.line * 100.0f);
                str = Util.formatInvariant("%.2f%%", objArr2);
                if (cue.verticalType == i5) {
                    i = -b(cue.lineAnchor);
                } else {
                    i = b(cue.lineAnchor);
                }
                i2 = i4;
            } else if (cue.line >= 0.0f) {
                Object[] objArr3 = new Object[i5];
                objArr3[i4] = Float.valueOf(cue.line * f);
                str = Util.formatInvariant("%.2fem", objArr3);
                i2 = i4;
                i = i2;
            } else {
                Object[] objArr4 = new Object[i5];
                objArr4[i4] = Float.valueOf(((-cue.line) - 1.0f) * f);
                str = Util.formatInvariant("%.2fem", objArr4);
                i = i4;
                i2 = i5;
            }
            if (cue.size != -3.4028235E38f) {
                Object[] objArr5 = new Object[i5];
                objArr5[i4] = Float.valueOf(cue.size * 100.0f);
                str2 = Util.formatInvariant("%.2f%%", objArr5);
            } else {
                str2 = "fit-content";
            }
            String a = a(cue.textAlignment);
            String a2 = a(cue.verticalType);
            String a3 = fVar.a(cue.textSizeType, cue.textSize);
            String a4 = b.a(cue.windowColorSet ? cue.windowColor : fVar.d.windowColor);
            switch (cue.verticalType) {
                case 1:
                    str4 = i2 != 0 ? "left" : "right";
                    str3 = "top";
                    i3 = i;
                    break;
                case 2:
                    str4 = i2 != 0 ? "right" : "left";
                    str3 = "top";
                    i3 = i;
                    break;
                default:
                    str4 = i2 != 0 ? "bottom" : "top";
                    str3 = "left";
                    i3 = i;
                    break;
            }
            if (cue.verticalType == 2 || cue.verticalType == 1) {
                str5 = "height";
            } else {
                str5 = "width";
                b = i3;
                i3 = b;
            }
            SpannedToHtmlConverter.HtmlAndCss a5 = SpannedToHtmlConverter.a(cue.text, getContext().getResources().getDisplayMetrics().density);
            Iterator it = hashMap.keySet().iterator();
            while (it.hasNext()) {
                String str6 = (String) it.next();
                String str7 = (String) hashMap.put(str6, (String) hashMap.get(str6));
                Assertions.checkState(str7 == null || str7.equals(hashMap.get(str6)));
                it = it;
                a5 = a5;
            }
            sb.append(Util.formatInvariant("<div style='position:absolute;z-index:%s;%s:%.2f%%;%s:%s;%s:%s;text-align:%s;writing-mode:%s;font-size:%s;background-color:%s;transform:translate(%s%%,%s%%)%s;'>", Integer.valueOf(i6), str3, Float.valueOf(f2), str4, str, str5, str2, a, a2, a3, a4, Integer.valueOf(i3), Integer.valueOf(b), a(cue)));
            sb.append(Util.formatInvariant("<span class='%s'>", "default_bg"));
            if (cue.multiRowAlignment != null) {
                sb.append(Util.formatInvariant("<span style='display:inline-block; text-align:%s;'>", a(cue.multiRowAlignment)));
                sb.append(a5.html);
                sb.append("</span>");
            } else {
                sb.append(a5.html);
            }
            sb.append("</span>");
            sb.append("</div>");
            i6++;
            fVar = this;
            f = 1.2f;
            i4 = 0;
            i5 = 1;
        }
        sb.append("</div></body></html>");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("<html><head><style>");
        for (String str8 : hashMap.keySet()) {
            sb2.append(str8);
            sb2.append("{");
            sb2.append((String) hashMap.get(str8));
            sb2.append("}");
        }
        sb2.append("</style></head>");
        sb.insert(0, sb2.toString());
        this.b.loadData(Base64.encodeToString(sb.toString().getBytes(Charsets.UTF_8), 1), MimeTypes.TEXT_HTML, HttpHeaders.Values.BASE64);
    }

    private static String a(Cue cue) {
        if (cue.shearDegrees == 0.0f) {
            return "";
        }
        return Util.formatInvariant("%s(%.2fdeg)", (cue.verticalType == 2 || cue.verticalType == 1) ? "skewY" : "skewX", Float.valueOf(cue.shearDegrees));
    }

    private String a(int i, float f) {
        float a = e.a(i, f, getHeight(), (getHeight() - getPaddingTop()) - getPaddingBottom());
        return a == -3.4028235E38f ? "unset" : Util.formatInvariant("%.2fpx", Float.valueOf(a / getContext().getResources().getDisplayMetrics().density));
    }

    private static String a(CaptionStyleCompat captionStyleCompat) {
        switch (captionStyleCompat.edgeType) {
            case 1:
                return Util.formatInvariant("1px 1px 0 %1$s, 1px -1px 0 %1$s, -1px 1px 0 %1$s, -1px -1px 0 %1$s", b.a(captionStyleCompat.edgeColor));
            case 2:
                return Util.formatInvariant("0.1em 0.12em 0.15em %s", b.a(captionStyleCompat.edgeColor));
            case 3:
                return Util.formatInvariant("0.06em 0.08em 0.15em %s", b.a(captionStyleCompat.edgeColor));
            case 4:
                return Util.formatInvariant("-0.05em -0.05em 0.15em %s", b.a(captionStyleCompat.edgeColor));
            default:
                return "unset";
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: WebViewSubtitleOutput.java */
    /* renamed from: com.google.android.exoplayer2.ui.f$2  reason: invalid class name */
    /* loaded from: classes2.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[Layout.Alignment.values().length];

        static {
            try {
                a[Layout.Alignment.ALIGN_NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[Layout.Alignment.ALIGN_OPPOSITE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[Layout.Alignment.ALIGN_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private static String a(@Nullable Layout.Alignment alignment) {
        if (alignment == null) {
            return "center";
        }
        switch (AnonymousClass2.a[alignment.ordinal()]) {
            case 1:
                return "start";
            case 2:
                return "end";
            default:
                return "center";
        }
    }
}
