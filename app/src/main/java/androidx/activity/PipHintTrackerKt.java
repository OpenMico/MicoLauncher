package androidx.activity;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.RequiresApi;
import com.umeng.analytics.pro.ai;
import com.xiaomi.onetrack.OneTrack;
import com.xiaomi.onetrack.a.c;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.ExperimentalCoroutinesApi;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: PipHintTracker.kt */
@Metadata(d1 = {"\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001d\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0006"}, d2 = {"trackPipAnimationHintView", "", "Landroid/app/Activity;", OneTrack.Event.VIEW, "Landroid/view/View;", "(Landroid/app/Activity;Landroid/view/View;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "activity-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class PipHintTrackerKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final Rect a(View view) {
        Rect rect = new Rect();
        view.getGlobalVisibleRect(rect);
        return rect;
    }

    /* compiled from: PipHintTracker.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "Landroid/graphics/Rect;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$flow$1", f = "PipHintTracker.kt", i = {}, l = {88}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes.dex */
    static final class a extends SuspendLambda implements Function2<ProducerScope<? super Rect>, Continuation<? super Unit>, Object> {
        final /* synthetic */ View $view;
        private /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(View view, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$view = view;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super Rect> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            a aVar = new a(this.$view, continuation);
            aVar.L$0 = obj;
            return aVar;
        }

        /* JADX WARN: Type inference failed for: r3v1, types: [androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1] */
        /* JADX WARN: Unknown variable types count: 1 */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r7) {
            /*
                r6 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r6.label
                switch(r1) {
                    case 0: goto L_0x0015;
                    case 1: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r7.<init>(r0)
                throw r7
            L_0x0011:
                kotlin.ResultKt.throwOnFailure(r7)
                goto L_0x0072
            L_0x0015:
                kotlin.ResultKt.throwOnFailure(r7)
                java.lang.Object r7 = r6.L$0
                kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
                androidx.activity.PipHintTrackerKt$a$a r1 = new androidx.activity.PipHintTrackerKt$a$a
                r1.<init>(r7)
                android.view.View$OnLayoutChangeListener r1 = (android.view.View.OnLayoutChangeListener) r1
                androidx.activity.PipHintTrackerKt$a$b r2 = new androidx.activity.PipHintTrackerKt$a$b
                android.view.View r3 = r6.$view
                r2.<init>(r7, r3)
                android.view.ViewTreeObserver$OnScrollChangedListener r2 = (android.view.ViewTreeObserver.OnScrollChangedListener) r2
                androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 r3 = new androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1
                android.view.View r4 = r6.$view
                r3.<init>()
                androidx.activity.Api19Impl r4 = androidx.activity.Api19Impl.INSTANCE
                android.view.View r5 = r6.$view
                boolean r4 = r4.isAttachedToWindow(r5)
                if (r4 == 0) goto L_0x0054
                android.view.View r4 = r6.$view
                android.graphics.Rect r4 = androidx.activity.PipHintTrackerKt.access$trackPipAnimationHintView$positionInWindow(r4)
                r7.offer(r4)
                android.view.View r4 = r6.$view
                android.view.ViewTreeObserver r4 = r4.getViewTreeObserver()
                r4.addOnScrollChangedListener(r2)
                android.view.View r4 = r6.$view
                r4.addOnLayoutChangeListener(r1)
            L_0x0054:
                android.view.View r4 = r6.$view
                r5 = r3
                android.view.View$OnAttachStateChangeListener r5 = (android.view.View.OnAttachStateChangeListener) r5
                r4.addOnAttachStateChangeListener(r5)
                androidx.activity.PipHintTrackerKt$a$1 r4 = new androidx.activity.PipHintTrackerKt$a$1
                android.view.View r5 = r6.$view
                r4.<init>(r5, r2, r1, r3)
                kotlin.jvm.functions.Function0 r4 = (kotlin.jvm.functions.Function0) r4
                r1 = r6
                kotlin.coroutines.Continuation r1 = (kotlin.coroutines.Continuation) r1
                r2 = 1
                r6.label = r2
                java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r7, r4, r1)
                if (r7 != r0) goto L_0x0072
                return r0
            L_0x0072:
                kotlin.Unit r7 = kotlin.Unit.INSTANCE
                return r7
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.activity.PipHintTrackerKt.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: PipHintTracker.kt */
        @Metadata(d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\n"}, d2 = {"<anonymous>", "", ai.aC, "Landroid/view/View;", "kotlin.jvm.PlatformType", c.a, "", ai.aF, "r", "b", "oldLeft", "oldTop", "oldRight", "oldBottom"}, k = 3, mv = {1, 5, 1}, xi = 48)
        /* renamed from: androidx.activity.PipHintTrackerKt$a$a  reason: collision with other inner class name */
        /* loaded from: classes.dex */
        public static final class View$OnLayoutChangeListenerC0001a implements View.OnLayoutChangeListener {
            final /* synthetic */ ProducerScope<Rect> a;

            /* JADX WARN: Multi-variable type inference failed */
            View$OnLayoutChangeListenerC0001a(ProducerScope<? super Rect> producerScope) {
                this.a = producerScope;
            }

            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View v, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                if (i != i5 || i3 != i7 || i2 != i6 || i4 != i8) {
                    ProducerScope<Rect> producerScope = this.a;
                    Intrinsics.checkNotNullExpressionValue(v, "v");
                    producerScope.offer(PipHintTrackerKt.a(v));
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: PipHintTracker.kt */
        @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, d2 = {"<anonymous>", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        /* loaded from: classes.dex */
        public static final class b implements ViewTreeObserver.OnScrollChangedListener {
            final /* synthetic */ ProducerScope<Rect> a;
            final /* synthetic */ View b;

            /* JADX WARN: Multi-variable type inference failed */
            b(ProducerScope<? super Rect> producerScope, View view) {
                this.a = producerScope;
                this.b = view;
            }

            @Override // android.view.ViewTreeObserver.OnScrollChangedListener
            public final void onScrollChanged() {
                this.a.offer(PipHintTrackerKt.a(this.b));
            }
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* compiled from: PipHintTracker.kt */
        @Metadata(d1 = {"\u0000\u0006\n\u0000\n\u0002\u0010\u0002\u0010\u0000\u001a\u00020\u0001H\n"}, d2 = {"<anonymous>", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
        /* renamed from: androidx.activity.PipHintTrackerKt$a$1  reason: invalid class name */
        /* loaded from: classes.dex */
        public static final class AnonymousClass1 extends Lambda implements Function0<Unit> {
            final /* synthetic */ PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 $attachStateChangeListener;
            final /* synthetic */ View.OnLayoutChangeListener $layoutChangeListener;
            final /* synthetic */ ViewTreeObserver.OnScrollChangedListener $scrollChangeListener;
            final /* synthetic */ View $view;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            AnonymousClass1(View view, ViewTreeObserver.OnScrollChangedListener onScrollChangedListener, View.OnLayoutChangeListener onLayoutChangeListener, PipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1 pipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1) {
                super(0);
                this.$view = view;
                this.$scrollChangeListener = onScrollChangedListener;
                this.$layoutChangeListener = onLayoutChangeListener;
                this.$attachStateChangeListener = pipHintTrackerKt$trackPipAnimationHintView$flow$1$attachStateChangeListener$1;
            }

            @Override // kotlin.jvm.functions.Function0
            public /* synthetic */ Unit invoke() {
                a();
                return Unit.INSTANCE;
            }

            public final void a() {
                this.$view.getViewTreeObserver().removeOnScrollChangedListener(this.$scrollChangeListener);
                this.$view.removeOnLayoutChangeListener(this.$layoutChangeListener);
                this.$view.removeOnAttachStateChangeListener(this.$attachStateChangeListener);
            }
        }
    }

    @RequiresApi(26)
    @ExperimentalCoroutinesApi
    @Nullable
    public static final Object trackPipAnimationHintView(@NotNull final Activity activity, @NotNull View view, @NotNull Continuation<? super Unit> continuation) {
        Object collect = FlowKt.callbackFlow(new a(view, null)).collect(new FlowCollector<Rect>() { // from class: androidx.activity.PipHintTrackerKt$trackPipAnimationHintView$$inlined$collect$1
            @Override // kotlinx.coroutines.flow.FlowCollector
            @Nullable
            public Object emit(Rect rect, @NotNull Continuation continuation2) {
                Api26Impl.INSTANCE.setPipParamsSourceRectHint(activity, rect);
                return Unit.INSTANCE;
            }
        }, continuation);
        return collect == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
    }
}
