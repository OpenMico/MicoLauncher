package androidx.core.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.OneTrack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: View.kt */
@Metadata(d1 = {"\u0000j\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u001a5\u0010 \u001a\u00020!*\u00020\u00022#\b\u0004\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020!0#H\u0086\bø\u0001\u0000\u001a5\u0010'\u001a\u00020!*\u00020\u00022#\b\u0004\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020!0#H\u0086\bø\u0001\u0000\u001a5\u0010(\u001a\u00020!*\u00020\u00022#\b\u0004\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020!0#H\u0086\bø\u0001\u0000\u001a5\u0010)\u001a\u00020!*\u00020\u00022#\b\u0004\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020!0#H\u0086\bø\u0001\u0000\u001a5\u0010*\u001a\u00020+*\u00020\u00022#\b\u0004\u0010\"\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(&\u0012\u0004\u0012\u00020!0#H\u0086\bø\u0001\u0000\u001a\u0014\u0010,\u001a\u00020-*\u00020\u00022\b\b\u0002\u0010.\u001a\u00020/\u001a(\u00100\u001a\u000201*\u00020\u00022\u0006\u00102\u001a\u0002032\u000e\b\u0004\u0010\"\u001a\b\u0012\u0004\u0012\u00020!04H\u0086\bø\u0001\u0000\u001a(\u00105\u001a\u000201*\u00020\u00022\u0006\u00102\u001a\u0002032\u000e\b\u0004\u0010\"\u001a\b\u0012\u0004\u0012\u00020!04H\u0087\bø\u0001\u0000\u001a\u0017\u00106\u001a\u00020!*\u00020\u00022\b\b\u0001\u00107\u001a\u00020\u0013H\u0086\b\u001a:\u00108\u001a\u00020!\"\n\b\u0000\u00109\u0018\u0001*\u00020:*\u00020\u00022\u0017\u0010;\u001a\u0013\u0012\u0004\u0012\u0002H9\u0012\u0004\u0012\u00020!0#¢\u0006\u0002\b<H\u0087\bø\u0001\u0000¢\u0006\u0002\b=\u001a)\u00108\u001a\u00020!*\u00020\u00022\u0017\u0010;\u001a\u0013\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020!0#¢\u0006\u0002\b<H\u0086\bø\u0001\u0000\u001a5\u0010>\u001a\u00020!*\u00020\u00022\b\b\u0003\u0010?\u001a\u00020\u00132\b\b\u0003\u0010@\u001a\u00020\u00132\b\b\u0003\u0010A\u001a\u00020\u00132\b\b\u0003\u0010B\u001a\u00020\u0013H\u0086\b\u001a5\u0010C\u001a\u00020!*\u00020\u00022\b\b\u0003\u0010D\u001a\u00020\u00132\b\b\u0003\u0010@\u001a\u00020\u00132\b\b\u0003\u0010E\u001a\u00020\u00132\b\b\u0003\u0010B\u001a\u00020\u0013H\u0087\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u001b\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0004\"*\u0010\n\u001a\u00020\t*\u00020\u00022\u0006\u0010\b\u001a\u00020\t8Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\"*\u0010\u000e\u001a\u00020\t*\u00020\u00022\u0006\u0010\b\u001a\u00020\t8Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000b\"\u0004\b\u000f\u0010\r\"*\u0010\u0010\u001a\u00020\t*\u00020\u00022\u0006\u0010\b\u001a\u00020\t8Æ\u0002@Æ\u0002X\u0086\u000e¢\u0006\f\u001a\u0004\b\u0010\u0010\u000b\"\u0004\b\u0011\u0010\r\"\u0016\u0010\u0012\u001a\u00020\u0013*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015\"\u0016\u0010\u0016\u001a\u00020\u0013*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015\"\u0016\u0010\u0018\u001a\u00020\u0013*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0015\"\u0016\u0010\u001a\u001a\u00020\u0013*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0015\"\u0016\u0010\u001c\u001a\u00020\u0013*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001d\u0010\u0015\"\u0016\u0010\u001e\u001a\u00020\u0013*\u00020\u00028Æ\u0002¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u0015\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006F"}, d2 = {"allViews", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "getAllViews", "(Landroid/view/View;)Lkotlin/sequences/Sequence;", "ancestors", "Landroid/view/ViewParent;", "getAncestors", com.xiaomi.onetrack.api.b.p, "", "isGone", "(Landroid/view/View;)Z", "setGone", "(Landroid/view/View;Z)V", "isInvisible", "setInvisible", "isVisible", "setVisible", "marginBottom", "", "getMarginBottom", "(Landroid/view/View;)I", "marginEnd", "getMarginEnd", "marginLeft", "getMarginLeft", "marginRight", "getMarginRight", "marginStart", "getMarginStart", "marginTop", "getMarginTop", "doOnAttach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", OneTrack.Event.VIEW, "doOnDetach", "doOnLayout", "doOnNextLayout", "doOnPreDraw", "Landroidx/core/view/OneShotPreDrawListener;", "drawToBitmap", "Landroid/graphics/Bitmap;", "config", "Landroid/graphics/Bitmap$Config;", "postDelayed", "Ljava/lang/Runnable;", "delayInMillis", "", "Lkotlin/Function0;", "postOnAnimationDelayed", "setPadding", "size", "updateLayoutParams", ExifInterface.GPS_DIRECTION_TRUE, "Landroid/view/ViewGroup$LayoutParams;", "block", "Lkotlin/ExtensionFunctionType;", "updateLayoutParamsTyped", "updatePadding", "left", "top", "right", "bottom", "updatePaddingRelative", "start", "end", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ViewKt {
    public static final void doOnNextLayout(@NotNull View view, @NotNull final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.core.view.ViewKt$doOnNextLayout$1
            @Override // android.view.View.OnLayoutChangeListener
            public void onLayoutChange(@NotNull View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                Intrinsics.checkNotNullParameter(view2, "view");
                view2.removeOnLayoutChangeListener(this);
                action.invoke(view2);
            }
        });
    }

    public static final void doOnLayout(@NotNull View view, @NotNull final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        if (!ViewCompat.isLaidOut(view) || view.isLayoutRequested()) {
            view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: androidx.core.view.ViewKt$doOnLayout$$inlined$doOnNextLayout$1
                @Override // android.view.View.OnLayoutChangeListener
                public void onLayoutChange(@NotNull View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    Intrinsics.checkNotNullParameter(view2, "view");
                    view2.removeOnLayoutChangeListener(this);
                    Function1.this.invoke(view2);
                }
            });
        } else {
            action.invoke(view);
        }
    }

    @NotNull
    public static final OneShotPreDrawListener doOnPreDraw(@NotNull final View view, @NotNull final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        OneShotPreDrawListener add = OneShotPreDrawListener.add(view, new Runnable() { // from class: androidx.core.view.ViewKt$doOnPreDraw$1
            @Override // java.lang.Runnable
            public final void run() {
                action.invoke(view);
            }
        });
        Intrinsics.checkNotNullExpressionValue(add, "View.doOnPreDraw(\n    crossinline action: (view: View) -> Unit\n): OneShotPreDrawListener = OneShotPreDrawListener.add(this) { action(this) }");
        return add;
    }

    public static final void doOnAttach(@NotNull final View view, @NotNull final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        if (ViewCompat.isAttachedToWindow(view)) {
            action.invoke(view);
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.core.view.ViewKt$doOnAttach$1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(@NotNull View view2) {
                    Intrinsics.checkNotNullParameter(view2, "view");
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(@NotNull View view2) {
                    Intrinsics.checkNotNullParameter(view2, "view");
                    view.removeOnAttachStateChangeListener(this);
                    action.invoke(view2);
                }
            });
        }
    }

    public static final void doOnDetach(@NotNull final View view, @NotNull final Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        if (!ViewCompat.isAttachedToWindow(view)) {
            action.invoke(view);
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: androidx.core.view.ViewKt$doOnDetach$1
                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(@NotNull View view2) {
                    Intrinsics.checkNotNullParameter(view2, "view");
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(@NotNull View view2) {
                    Intrinsics.checkNotNullParameter(view2, "view");
                    view.removeOnAttachStateChangeListener(this);
                    action.invoke(view2);
                }
            });
        }
    }

    public static /* synthetic */ void updatePaddingRelative$default(View view, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = view.getPaddingStart();
        }
        if ((i5 & 2) != 0) {
            i2 = view.getPaddingTop();
        }
        if ((i5 & 4) != 0) {
            i3 = view.getPaddingEnd();
        }
        if ((i5 & 8) != 0) {
            i4 = view.getPaddingBottom();
        }
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPaddingRelative(i, i2, i3, i4);
    }

    @RequiresApi(17)
    public static final void updatePaddingRelative(@NotNull View view, @Px int i, @Px int i2, @Px int i3, @Px int i4) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPaddingRelative(i, i2, i3, i4);
    }

    public static /* synthetic */ void updatePadding$default(View view, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = view.getPaddingLeft();
        }
        if ((i5 & 2) != 0) {
            i2 = view.getPaddingTop();
        }
        if ((i5 & 4) != 0) {
            i3 = view.getPaddingRight();
        }
        if ((i5 & 8) != 0) {
            i4 = view.getPaddingBottom();
        }
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(i, i2, i3, i4);
    }

    public static final void updatePadding(@NotNull View view, @Px int i, @Px int i2, @Px int i3, @Px int i4) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(i, i2, i3, i4);
    }

    public static final void setPadding(@NotNull View view, @Px int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setPadding(i, i, i, i);
    }

    @NotNull
    public static final Runnable postDelayed(@NotNull View view, long j, @NotNull final Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        Runnable viewKt$postDelayed$runnable$1 = new Runnable() { // from class: androidx.core.view.ViewKt$postDelayed$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                action.invoke();
            }
        };
        view.postDelayed(viewKt$postDelayed$runnable$1, j);
        return viewKt$postDelayed$runnable$1;
    }

    @RequiresApi(16)
    @NotNull
    public static final Runnable postOnAnimationDelayed(@NotNull View view, long j, @NotNull final Function0<Unit> action) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        Runnable viewKt$postOnAnimationDelayed$runnable$1 = new Runnable() { // from class: androidx.core.view.ViewKt$postOnAnimationDelayed$runnable$1
            @Override // java.lang.Runnable
            public final void run() {
                action.invoke();
            }
        };
        view.postOnAnimationDelayed(viewKt$postOnAnimationDelayed$runnable$1, j);
        return viewKt$postOnAnimationDelayed$runnable$1;
    }

    public static /* synthetic */ Bitmap drawToBitmap$default(View view, Bitmap.Config config, int i, Object obj) {
        if ((i & 1) != 0) {
            config = Bitmap.Config.ARGB_8888;
        }
        return drawToBitmap(view, config);
    }

    @NotNull
    public static final Bitmap drawToBitmap(@NotNull View view, @NotNull Bitmap.Config config) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(config, "config");
        if (ViewCompat.isLaidOut(view)) {
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), config);
            Intrinsics.checkNotNullExpressionValue(createBitmap, "createBitmap(width, height, config)");
            Canvas canvas = new Canvas(createBitmap);
            canvas.translate(-view.getScrollX(), -view.getScrollY());
            view.draw(canvas);
            return createBitmap;
        }
        throw new IllegalStateException("View needs to be laid out before calling drawToBitmap()");
    }

    public static final boolean isVisible(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getVisibility() == 0;
    }

    public static final void setVisible(@NotNull View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(z ? 0 : 8);
    }

    public static final boolean isInvisible(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getVisibility() == 4;
    }

    public static final void setInvisible(@NotNull View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(z ? 4 : 0);
    }

    public static final boolean isGone(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getVisibility() == 8;
    }

    public static final void setGone(@NotNull View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setVisibility(z ? 8 : 0);
    }

    @JvmName(name = "updateLayoutParamsTyped")
    public static final /* synthetic */ <T extends ViewGroup.LayoutParams> void updateLayoutParamsTyped(View view, Function1<? super T, Unit> block) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        block.invoke(layoutParams2);
        view.setLayoutParams(layoutParams2);
    }

    public static final int getMarginLeft(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.leftMargin;
    }

    public static final int getMarginTop(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.topMargin;
    }

    public static final int getMarginRight(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.rightMargin;
    }

    public static final int getMarginBottom(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginLayoutParams = layoutParams instanceof ViewGroup.MarginLayoutParams ? (ViewGroup.MarginLayoutParams) layoutParams : null;
        if (marginLayoutParams == null) {
            return 0;
        }
        return marginLayoutParams.bottomMargin;
    }

    public static final int getMarginStart(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return 0;
    }

    public static final int getMarginEnd(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return 0;
    }

    /* compiled from: View.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes.dex */
    /* synthetic */ class b extends FunctionReferenceImpl implements Function1<ViewParent, ViewParent> {
        public static final b a = new b();

        b() {
            super(1, ViewParent.class, "getParent", "getParent()Landroid/view/ViewParent;", 0);
        }

        /* renamed from: a */
        public final ViewParent invoke(@NotNull ViewParent p0) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            return p0.getParent();
        }
    }

    @NotNull
    public static final Sequence<ViewParent> getAncestors(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return SequencesKt.generateSequence(view.getParent(), b.a);
    }

    /* compiled from: View.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Landroid/view/View;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "androidx.core.view.ViewKt$allViews$1", f = "View.kt", i = {0}, l = {406, 408}, m = "invokeSuspend", n = {"$this$sequence"}, s = {"L$0"})
    /* loaded from: classes.dex */
    static final class a extends RestrictedSuspendLambda implements Function2<SequenceScope<? super View>, Continuation<? super Unit>, Object> {
        final /* synthetic */ View $this_allViews;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(View view, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$this_allViews = view;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull SequenceScope<? super View> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            a aVar = new a(this.$this_allViews, continuation);
            aVar.L$0 = obj;
            return aVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x003c  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r5) {
            /*
                r4 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r4.label
                switch(r1) {
                    case 0: goto L_0x001d;
                    case 1: goto L_0x0015;
                    case 2: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r5.<init>(r0)
                throw r5
            L_0x0011:
                kotlin.ResultKt.throwOnFailure(r5)
                goto L_0x0052
            L_0x0015:
                java.lang.Object r1 = r4.L$0
                kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
                kotlin.ResultKt.throwOnFailure(r5)
                goto L_0x0036
            L_0x001d:
                kotlin.ResultKt.throwOnFailure(r5)
                java.lang.Object r5 = r4.L$0
                r1 = r5
                kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
                android.view.View r5 = r4.$this_allViews
                r2 = r4
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r4.L$0 = r1
                r3 = 1
                r4.label = r3
                java.lang.Object r5 = r1.yield(r5, r2)
                if (r5 != r0) goto L_0x0036
                return r0
            L_0x0036:
                android.view.View r5 = r4.$this_allViews
                boolean r2 = r5 instanceof android.view.ViewGroup
                if (r2 == 0) goto L_0x0052
                android.view.ViewGroup r5 = (android.view.ViewGroup) r5
                kotlin.sequences.Sequence r5 = androidx.core.view.ViewGroupKt.getDescendants(r5)
                r2 = r4
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 0
                r4.L$0 = r3
                r3 = 2
                r4.label = r3
                java.lang.Object r5 = r1.yieldAll(r5, r2)
                if (r5 != r0) goto L_0x0052
                return r0
            L_0x0052:
                kotlin.Unit r5 = kotlin.Unit.INSTANCE
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.ViewKt.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public static final Sequence<View> getAllViews(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return SequencesKt.sequence(new a(view, null));
    }

    public static final void updateLayoutParams(@NotNull View view, @NotNull Function1<? super ViewGroup.LayoutParams, Unit> block) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(block, "block");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null) {
            block.invoke(layoutParams);
            view.setLayoutParams(layoutParams);
            return;
        }
        throw new NullPointerException("null cannot be cast to non-null type android.view.ViewGroup.LayoutParams");
    }
}
