package com.google.android.material.transition;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.FloatRange;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;
import androidx.core.view.animation.PathInterpolatorCompat;
import androidx.transition.PathMotion;
import androidx.transition.PatternPathMotion;
import androidx.transition.Transition;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.AbsoluteCornerSize;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.xiaomi.mipush.sdk.Constants;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: TransitionUtils.java */
/* loaded from: classes2.dex */
public class j {
    private static final RectF a = new RectF();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: TransitionUtils.java */
    /* loaded from: classes2.dex */
    public interface a {
        void a(Canvas canvas);
    }

    /* compiled from: TransitionUtils.java */
    /* loaded from: classes2.dex */
    public interface b {
        @NonNull
        CornerSize a(@NonNull CornerSize cornerSize, @NonNull CornerSize cornerSize2);
    }

    public static float a(float f, float f2, float f3) {
        return f + (f3 * (f2 - f));
    }

    @NonNull
    public static <T> T a(@Nullable T t, @NonNull T t2) {
        return t != null ? t : t2;
    }

    public static boolean a(Transition transition, Context context, @AttrRes int i, TimeInterpolator timeInterpolator) {
        if (i == 0 || transition.getInterpolator() != null) {
            return false;
        }
        transition.setInterpolator(a(context, i, timeInterpolator));
        return true;
    }

    public static boolean a(Transition transition, Context context, @AttrRes int i) {
        int resolveInteger;
        if (i == 0 || transition.getDuration() != -1 || (resolveInteger = MaterialAttributes.resolveInteger(context, i, -1)) == -1) {
            return false;
        }
        transition.setDuration(resolveInteger);
        return true;
    }

    public static boolean b(Transition transition, Context context, @AttrRes int i) {
        PathMotion a2;
        if (i == 0 || (a2 = a(context, i)) == null) {
            return false;
        }
        transition.setPathMotion(a2);
        return true;
    }

    static TimeInterpolator a(Context context, @AttrRes int i, @NonNull TimeInterpolator timeInterpolator) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            return timeInterpolator;
        }
        if (typedValue.type == 3) {
            String valueOf = String.valueOf(typedValue.string);
            if (a(valueOf, "cubic-bezier")) {
                String[] split = b(valueOf, "cubic-bezier").split(Constants.ACCEPT_TIME_SEPARATOR_SP);
                if (split.length == 4) {
                    return PathInterpolatorCompat.create(a(split, 0), a(split, 1), a(split, 2), a(split, 3));
                }
                throw new IllegalArgumentException("Motion easing theme attribute must have 4 control points if using bezier curve format; instead got: " + split.length);
            } else if (a(valueOf, "path")) {
                return PathInterpolatorCompat.create(PathParser.createPathFromPathData(b(valueOf, "path")));
            } else {
                throw new IllegalArgumentException("Invalid motion easing type: " + valueOf);
            }
        } else {
            throw new IllegalArgumentException("Motion easing theme attribute must be a string");
        }
    }

    private static boolean a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("(");
        return str.startsWith(sb.toString()) && str.endsWith(")");
    }

    private static String b(String str, String str2) {
        return str.substring(str2.length() + 1, str.length() - 1);
    }

    private static float a(String[] strArr, int i) {
        float parseFloat = Float.parseFloat(strArr[i]);
        if (parseFloat >= 0.0f && parseFloat <= 1.0f) {
            return parseFloat;
        }
        throw new IllegalArgumentException("Motion easing control point value must be between 0 and 1; instead got: " + parseFloat);
    }

    @Nullable
    static PathMotion a(Context context, @AttrRes int i) {
        TypedValue typedValue = new TypedValue();
        if (!context.getTheme().resolveAttribute(i, typedValue, true)) {
            return null;
        }
        if (typedValue.type == 16) {
            int i2 = typedValue.data;
            if (i2 == 0) {
                return null;
            }
            if (i2 == 1) {
                return new MaterialArcMotion();
            }
            throw new IllegalArgumentException("Invalid motion path type: " + i2);
        } else if (typedValue.type == 3) {
            return new PatternPathMotion(PathParser.createPathFromPathData(String.valueOf(typedValue.string)));
        } else {
            throw new IllegalArgumentException("Motion path theme attribute must either be an enum value or path data string");
        }
    }

    public static ShapeAppearanceModel a(ShapeAppearanceModel shapeAppearanceModel, final RectF rectF) {
        return shapeAppearanceModel.withTransformedCornerSizes(new ShapeAppearanceModel.CornerSizeUnaryOperator() { // from class: com.google.android.material.transition.j.1
            @Override // com.google.android.material.shape.ShapeAppearanceModel.CornerSizeUnaryOperator
            @NonNull
            public CornerSize apply(@NonNull CornerSize cornerSize) {
                return cornerSize instanceof RelativeCornerSize ? cornerSize : new RelativeCornerSize(cornerSize.getCornerSize(rectF) / rectF.height());
            }
        });
    }

    static ShapeAppearanceModel a(ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, RectF rectF, b bVar) {
        return (b(shapeAppearanceModel, rectF) ? shapeAppearanceModel : shapeAppearanceModel2).toBuilder().setTopLeftCornerSize(bVar.a(shapeAppearanceModel.getTopLeftCornerSize(), shapeAppearanceModel2.getTopLeftCornerSize())).setTopRightCornerSize(bVar.a(shapeAppearanceModel.getTopRightCornerSize(), shapeAppearanceModel2.getTopRightCornerSize())).setBottomLeftCornerSize(bVar.a(shapeAppearanceModel.getBottomLeftCornerSize(), shapeAppearanceModel2.getBottomLeftCornerSize())).setBottomRightCornerSize(bVar.a(shapeAppearanceModel.getBottomRightCornerSize(), shapeAppearanceModel2.getBottomRightCornerSize())).build();
    }

    private static boolean b(ShapeAppearanceModel shapeAppearanceModel, RectF rectF) {
        return (shapeAppearanceModel.getTopLeftCornerSize().getCornerSize(rectF) == 0.0f && shapeAppearanceModel.getTopRightCornerSize().getCornerSize(rectF) == 0.0f && shapeAppearanceModel.getBottomRightCornerSize().getCornerSize(rectF) == 0.0f && shapeAppearanceModel.getBottomLeftCornerSize().getCornerSize(rectF) == 0.0f) ? false : true;
    }

    public static float a(float f, float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3, @FloatRange(from = 0.0d, to = 1.0d) float f4, @FloatRange(from = 0.0d, to = 1.0d) float f5) {
        return a(f, f2, f3, f4, f5, false);
    }

    public static float a(float f, float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3, @FloatRange(from = 0.0d, to = 1.0d) float f4, @FloatRange(from = 0.0d) float f5, boolean z) {
        if (!z || (f5 >= 0.0f && f5 <= 1.0f)) {
            return f5 < f3 ? f : f5 > f4 ? f2 : a(f, f2, (f5 - f3) / (f4 - f3));
        }
        return a(f, f2, f5);
    }

    public static int a(int i, int i2, @FloatRange(from = 0.0d, to = 1.0d) float f, @FloatRange(from = 0.0d, to = 1.0d) float f2, @FloatRange(from = 0.0d, to = 1.0d) float f3) {
        return f3 < f ? i : f3 > f2 ? i2 : (int) a(i, i2, (f3 - f) / (f2 - f));
    }

    public static ShapeAppearanceModel a(ShapeAppearanceModel shapeAppearanceModel, ShapeAppearanceModel shapeAppearanceModel2, final RectF rectF, final RectF rectF2, @FloatRange(from = 0.0d, to = 1.0d) final float f, @FloatRange(from = 0.0d, to = 1.0d) final float f2, @FloatRange(from = 0.0d, to = 1.0d) final float f3) {
        return f3 < f ? shapeAppearanceModel : f3 > f2 ? shapeAppearanceModel2 : a(shapeAppearanceModel, shapeAppearanceModel2, rectF, new b() { // from class: com.google.android.material.transition.j.2
            @Override // com.google.android.material.transition.j.b
            @NonNull
            public CornerSize a(@NonNull CornerSize cornerSize, @NonNull CornerSize cornerSize2) {
                return new AbsoluteCornerSize(j.a(cornerSize.getCornerSize(rectF), cornerSize2.getCornerSize(rectF2), f, f2, f3));
            }
        });
    }

    public static Shader a(@ColorInt int i) {
        return new LinearGradient(0.0f, 0.0f, 0.0f, 0.0f, i, i, Shader.TileMode.CLAMP);
    }

    public static View a(View view, @IdRes int i) {
        View findViewById = view.findViewById(i);
        return findViewById != null ? findViewById : b(view, i);
    }

    public static View b(View view, @IdRes int i) {
        String resourceName = view.getResources().getResourceName(i);
        while (view != null) {
            if (view.getId() != i) {
                ViewParent parent = view.getParent();
                if (!(parent instanceof View)) {
                    break;
                }
                view = (View) parent;
            } else {
                return view;
            }
        }
        throw new IllegalArgumentException(resourceName + " is not a valid ancestor");
    }

    public static RectF a(View view) {
        return new RectF(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    public static RectF b(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        int i2 = iArr[1];
        return new RectF(i, i2, view.getWidth() + i, view.getHeight() + i2);
    }

    public static float a(@NonNull RectF rectF) {
        return rectF.width() * rectF.height();
    }

    private static int a(Canvas canvas, Rect rect, int i) {
        a.set(rect);
        if (Build.VERSION.SDK_INT >= 21) {
            return canvas.saveLayerAlpha(a, i);
        }
        return canvas.saveLayerAlpha(a.left, a.top, a.right, a.bottom, i, 31);
    }

    public static void a(Canvas canvas, Rect rect, float f, float f2, float f3, int i, a aVar) {
        if (i > 0) {
            int save = canvas.save();
            canvas.translate(f, f2);
            canvas.scale(f3, f3);
            if (i < 255) {
                a(canvas, rect, i);
            }
            aVar.a(canvas);
            canvas.restoreToCount(save);
        }
    }
}
