package com.jakewharton.rxbinding4.widget;

import android.widget.SearchView;
import androidx.annotation.CheckResult;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: SearchViewQueryConsumer.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u001a\u001c\u0010\u0000\u001a\n\u0012\u0006\b\u0000\u0012\u00020\u00020\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0007¨\u0006\u0006"}, d2 = {"query", "Lio/reactivex/rxjava3/functions/Consumer;", "", "Landroid/widget/SearchView;", "submit", "", "rxbinding_release"}, k = 5, mv = {1, 1, 16}, xs = "com/jakewharton/rxbinding4/widget/RxSearchView")
/* loaded from: classes2.dex */
final /* synthetic */ class af {

    /* compiled from: SearchViewQueryConsumer.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\r\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "", "text", "", "kotlin.jvm.PlatformType", "accept"}, k = 3, mv = {1, 1, 16})
    /* loaded from: classes2.dex */
    static final class a<T> implements Consumer<CharSequence> {
        final /* synthetic */ SearchView a;
        final /* synthetic */ boolean b;

        a(SearchView searchView, boolean z) {
            this.a = searchView;
            this.b = z;
        }

        /* renamed from: a */
        public final void accept(CharSequence charSequence) {
            this.a.setQuery(charSequence, this.b);
        }
    }

    @CheckResult
    @NotNull
    public static final Consumer<? super CharSequence> a(@NotNull SearchView query, boolean z) {
        Intrinsics.checkParameterIsNotNull(query, "$this$query");
        return new a(query, z);
    }
}
