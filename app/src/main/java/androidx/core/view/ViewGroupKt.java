package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Px;
import androidx.annotation.RequiresApi;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import com.xiaomi.onetrack.OneTrack;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ViewGroup.kt */
@Metadata(d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010)\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0015\u0010\f\u001a\u00020\r*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0086\n\u001a3\u0010\u000f\u001a\u00020\u0010*\u00020\u00032!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00100\u0012H\u0086\bø\u0001\u0000\u001aH\u0010\u0015\u001a\u00020\u0010*\u00020\u000326\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0017\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\u00100\u0016H\u0086\bø\u0001\u0000\u001a\u0015\u0010\u0018\u001a\u00020\u0002*\u00020\u00032\u0006\u0010\u0017\u001a\u00020\tH\u0086\u0002\u001a\r\u0010\u0019\u001a\u00020\r*\u00020\u0003H\u0086\b\u001a\r\u0010\u001a\u001a\u00020\r*\u00020\u0003H\u0086\b\u001a\u0013\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00020\u001c*\u00020\u0003H\u0086\u0002\u001a\u0015\u0010\u001d\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0086\n\u001a\u0015\u0010\u001e\u001a\u00020\u0010*\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0002H\u0086\n\u001a\u0017\u0010\u001f\u001a\u00020\u0010*\u00020 2\b\b\u0001\u0010\b\u001a\u00020\tH\u0086\b\u001a5\u0010!\u001a\u00020\u0010*\u00020 2\b\b\u0003\u0010\"\u001a\u00020\t2\b\b\u0003\u0010#\u001a\u00020\t2\b\b\u0003\u0010$\u001a\u00020\t2\b\b\u0003\u0010%\u001a\u00020\tH\u0086\b\u001a5\u0010&\u001a\u00020\u0010*\u00020 2\b\b\u0003\u0010'\u001a\u00020\t2\b\b\u0003\u0010#\u001a\u00020\t2\b\b\u0003\u0010(\u001a\u00020\t2\b\b\u0003\u0010%\u001a\u00020\tH\u0087\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u001b\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005\"\u0016\u0010\b\u001a\u00020\t*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006)"}, d2 = {"children", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "Landroid/view/ViewGroup;", "getChildren", "(Landroid/view/ViewGroup;)Lkotlin/sequences/Sequence;", "descendants", "getDescendants", "size", "", "getSize", "(Landroid/view/ViewGroup;)I", "contains", "", OneTrack.Event.VIEW, "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", MiSoundBoxCommandExtras.INDEX, BluetoothConstants.GET, "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "plusAssign", "setMargins", "Landroid/view/ViewGroup$MarginLayoutParams;", "updateMargins", "left", "top", "right", "bottom", "updateMarginsRelative", "start", "end", "core-ktx_release"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes.dex */
public final class ViewGroupKt {
    @NotNull
    public static final View get(@NotNull ViewGroup viewGroup, int i) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        View childAt = viewGroup.getChildAt(i);
        if (childAt != null) {
            return childAt;
        }
        throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + viewGroup.getChildCount());
    }

    public static final boolean contains(@NotNull ViewGroup viewGroup, @NotNull View view) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Intrinsics.checkNotNullParameter(view, "view");
        return viewGroup.indexOfChild(view) != -1;
    }

    public static final void plusAssign(@NotNull ViewGroup viewGroup, @NotNull View view) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Intrinsics.checkNotNullParameter(view, "view");
        viewGroup.addView(view);
    }

    public static final void minusAssign(@NotNull ViewGroup viewGroup, @NotNull View view) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Intrinsics.checkNotNullParameter(view, "view");
        viewGroup.removeView(view);
    }

    public static final int getSize(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return viewGroup.getChildCount();
    }

    public static final boolean isEmpty(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return viewGroup.getChildCount() == 0;
    }

    public static final boolean isNotEmpty(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return viewGroup.getChildCount() != 0;
    }

    public static final void forEach(@NotNull ViewGroup viewGroup, @NotNull Function1<? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                View childAt = viewGroup.getChildAt(i);
                Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(index)");
                action.invoke(childAt);
                if (i2 < childCount) {
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }

    public static final void forEachIndexed(@NotNull ViewGroup viewGroup, @NotNull Function2<? super Integer, ? super View, Unit> action) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        Intrinsics.checkNotNullParameter(action, "action");
        int childCount = viewGroup.getChildCount();
        if (childCount > 0) {
            int i = 0;
            while (true) {
                int i2 = i + 1;
                Integer valueOf = Integer.valueOf(i);
                View childAt = viewGroup.getChildAt(i);
                Intrinsics.checkNotNullExpressionValue(childAt, "getChildAt(index)");
                action.invoke(valueOf, childAt);
                if (i2 < childCount) {
                    i = i2;
                } else {
                    return;
                }
            }
        }
    }

    @NotNull
    public static final Iterator<View> iterator(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return new ViewGroupKt$iterator$1(viewGroup);
    }

    @NotNull
    public static final Sequence<View> getChildren(@NotNull final ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return new Sequence<View>() { // from class: androidx.core.view.ViewGroupKt$children$1
            @Override // kotlin.sequences.Sequence
            @NotNull
            public Iterator<View> iterator() {
                return ViewGroupKt.iterator(viewGroup);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ViewGroup.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Landroid/view/View;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "androidx.core.view.ViewGroupKt$descendants$1", f = "ViewGroup.kt", i = {0, 0, 0, 1, 1}, l = {97, 99}, m = "invokeSuspend", n = {"$this$sequence", "$this$forEach$iv", VideoDataManager.ORIGIN_CHILD, "$this$sequence", "$this$forEach$iv"}, s = {"L$0", "L$1", "L$2", "L$0", "L$1"})
    /* loaded from: classes.dex */
    public static final class a extends RestrictedSuspendLambda implements Function2<SequenceScope<? super View>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ViewGroup $this_descendants;
        int I$0;
        int I$1;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(ViewGroup viewGroup, Continuation<? super a> continuation) {
            super(2, continuation);
            this.$this_descendants = viewGroup;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull SequenceScope<? super View> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
            return ((a) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            a aVar = new a(this.$this_descendants, continuation);
            aVar.L$0 = obj;
            return aVar;
        }

        /* JADX WARN: Code restructure failed: missing block: B:23:0x00a4, code lost:
            if (r2 >= r3) goto L_0x00a6;
         */
        /* JADX WARN: Removed duplicated region for block: B:17:0x007a  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x009d  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                r9 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r9.label
                switch(r1) {
                    case 0: goto L_0x003f;
                    case 1: goto L_0x0024;
                    case 2: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r10.<init>(r0)
                throw r10
            L_0x0011:
                int r1 = r9.I$1
                int r2 = r9.I$0
                java.lang.Object r3 = r9.L$1
                android.view.ViewGroup r3 = (android.view.ViewGroup) r3
                java.lang.Object r4 = r9.L$0
                kotlin.sequences.SequenceScope r4 = (kotlin.sequences.SequenceScope) r4
                kotlin.ResultKt.throwOnFailure(r10)
                r10 = r0
                r0 = r9
                goto L_0x0096
            L_0x0024:
                int r1 = r9.I$1
                int r2 = r9.I$0
                java.lang.Object r3 = r9.L$2
                android.view.View r3 = (android.view.View) r3
                java.lang.Object r4 = r9.L$1
                android.view.ViewGroup r4 = (android.view.ViewGroup) r4
                java.lang.Object r5 = r9.L$0
                kotlin.sequences.SequenceScope r5 = (kotlin.sequences.SequenceScope) r5
                kotlin.ResultKt.throwOnFailure(r10)
                r10 = r0
                r0 = r9
                r7 = r5
                r5 = r2
                r2 = r3
                r3 = r4
                r4 = r7
                goto L_0x0076
            L_0x003f:
                kotlin.ResultKt.throwOnFailure(r10)
                java.lang.Object r10 = r9.L$0
                kotlin.sequences.SequenceScope r10 = (kotlin.sequences.SequenceScope) r10
                android.view.ViewGroup r1 = r9.$this_descendants
                r2 = 0
                int r3 = r1.getChildCount()
                if (r3 <= 0) goto L_0x00a6
                r4 = r0
                r0 = r9
            L_0x0051:
                int r5 = r2 + 1
                android.view.View r2 = r1.getChildAt(r2)
                java.lang.String r6 = "getChildAt(index)"
                kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r6)
                r0.L$0 = r10
                r0.L$1 = r1
                r0.L$2 = r2
                r0.I$0 = r5
                r0.I$1 = r3
                r6 = 1
                r0.label = r6
                java.lang.Object r6 = r10.yield(r2, r0)
                if (r6 != r4) goto L_0x0070
                return r4
            L_0x0070:
                r7 = r4
                r4 = r10
                r10 = r7
                r8 = r3
                r3 = r1
                r1 = r8
            L_0x0076:
                boolean r6 = r2 instanceof android.view.ViewGroup
                if (r6 == 0) goto L_0x009d
                android.view.ViewGroup r2 = (android.view.ViewGroup) r2
                kotlin.sequences.Sequence r2 = androidx.core.view.ViewGroupKt.getDescendants(r2)
                r0.L$0 = r4
                r0.L$1 = r3
                r6 = 0
                r0.L$2 = r6
                r0.I$0 = r5
                r0.I$1 = r1
                r6 = 2
                r0.label = r6
                java.lang.Object r2 = r4.yieldAll(r2, r0)
                if (r2 != r10) goto L_0x0095
                return r10
            L_0x0095:
                r2 = r5
            L_0x0096:
                r7 = r4
                r4 = r10
                r10 = r7
                r8 = r3
                r3 = r1
                r1 = r8
                goto L_0x00a4
            L_0x009d:
                r2 = r5
                r7 = r4
                r4 = r10
                r10 = r7
                r8 = r3
                r3 = r1
                r1 = r8
            L_0x00a4:
                if (r2 < r3) goto L_0x0051
            L_0x00a6:
                kotlin.Unit r10 = kotlin.Unit.INSTANCE
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.view.ViewGroupKt.a.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @NotNull
    public static final Sequence<View> getDescendants(@NotNull ViewGroup viewGroup) {
        Intrinsics.checkNotNullParameter(viewGroup, "<this>");
        return SequencesKt.sequence(new a(viewGroup, null));
    }

    public static final void setMargins(@NotNull ViewGroup.MarginLayoutParams marginLayoutParams, @Px int i) {
        Intrinsics.checkNotNullParameter(marginLayoutParams, "<this>");
        marginLayoutParams.setMargins(i, i, i, i);
    }

    public static /* synthetic */ void updateMargins$default(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = marginLayoutParams.leftMargin;
        }
        if ((i5 & 2) != 0) {
            i2 = marginLayoutParams.topMargin;
        }
        if ((i5 & 4) != 0) {
            i3 = marginLayoutParams.rightMargin;
        }
        if ((i5 & 8) != 0) {
            i4 = marginLayoutParams.bottomMargin;
        }
        Intrinsics.checkNotNullParameter(marginLayoutParams, "<this>");
        marginLayoutParams.setMargins(i, i2, i3, i4);
    }

    public static final void updateMargins(@NotNull ViewGroup.MarginLayoutParams marginLayoutParams, @Px int i, @Px int i2, @Px int i3, @Px int i4) {
        Intrinsics.checkNotNullParameter(marginLayoutParams, "<this>");
        marginLayoutParams.setMargins(i, i2, i3, i4);
    }

    public static /* synthetic */ void updateMarginsRelative$default(ViewGroup.MarginLayoutParams marginLayoutParams, int i, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = marginLayoutParams.getMarginStart();
        }
        if ((i5 & 2) != 0) {
            i2 = marginLayoutParams.topMargin;
        }
        if ((i5 & 4) != 0) {
            i3 = marginLayoutParams.getMarginEnd();
        }
        if ((i5 & 8) != 0) {
            i4 = marginLayoutParams.bottomMargin;
        }
        Intrinsics.checkNotNullParameter(marginLayoutParams, "<this>");
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.topMargin = i2;
        marginLayoutParams.setMarginEnd(i3);
        marginLayoutParams.bottomMargin = i4;
    }

    @RequiresApi(17)
    public static final void updateMarginsRelative(@NotNull ViewGroup.MarginLayoutParams marginLayoutParams, @Px int i, @Px int i2, @Px int i3, @Px int i4) {
        Intrinsics.checkNotNullParameter(marginLayoutParams, "<this>");
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.topMargin = i2;
        marginLayoutParams.setMarginEnd(i3);
        marginLayoutParams.bottomMargin = i4;
    }
}
