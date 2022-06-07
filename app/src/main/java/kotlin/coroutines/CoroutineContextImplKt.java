package kotlin.coroutines;

import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CoroutineContextImpl.kt */
@Metadata(d1 = {"\u0000\u0018\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a+\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0007¢\u0006\u0002\u0010\u0005\u001a\u0018\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004H\u0007¨\u0006\b"}, d2 = {"getPolymorphicElement", "E", "Lkotlin/coroutines/CoroutineContext$Element;", "key", "Lkotlin/coroutines/CoroutineContext$Key;", "(Lkotlin/coroutines/CoroutineContext$Element;Lkotlin/coroutines/CoroutineContext$Key;)Lkotlin/coroutines/CoroutineContext$Element;", "minusPolymorphicKey", "Lkotlin/coroutines/CoroutineContext;", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class CoroutineContextImplKt {
    /* JADX WARN: Multi-variable type inference failed */
    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @Nullable
    public static final <E extends CoroutineContext.Element> E getPolymorphicElement(@NotNull CoroutineContext.Element getPolymorphicElement, @NotNull CoroutineContext.Key<E> key) {
        Intrinsics.checkNotNullParameter(getPolymorphicElement, "$this$getPolymorphicElement");
        Intrinsics.checkNotNullParameter(key, "key");
        if (key instanceof AbstractCoroutineContextKey) {
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
            if (!abstractCoroutineContextKey.isSubKey$kotlin_stdlib(getPolymorphicElement.getKey())) {
                return null;
            }
            E e = (E) abstractCoroutineContextKey.tryCast$kotlin_stdlib(getPolymorphicElement);
            if (!(e instanceof CoroutineContext.Element)) {
                return null;
            }
            return e;
        } else if (getPolymorphicElement.getKey() == key) {
            return getPolymorphicElement;
        } else {
            return null;
        }
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final CoroutineContext minusPolymorphicKey(@NotNull CoroutineContext.Element minusPolymorphicKey, @NotNull CoroutineContext.Key<?> key) {
        Intrinsics.checkNotNullParameter(minusPolymorphicKey, "$this$minusPolymorphicKey");
        Intrinsics.checkNotNullParameter(key, "key");
        if (key instanceof AbstractCoroutineContextKey) {
            AbstractCoroutineContextKey abstractCoroutineContextKey = (AbstractCoroutineContextKey) key;
            boolean isSubKey$kotlin_stdlib = abstractCoroutineContextKey.isSubKey$kotlin_stdlib(minusPolymorphicKey.getKey());
            Object obj = minusPolymorphicKey;
            if (isSubKey$kotlin_stdlib) {
                CoroutineContext.Element tryCast$kotlin_stdlib = abstractCoroutineContextKey.tryCast$kotlin_stdlib(minusPolymorphicKey);
                obj = minusPolymorphicKey;
                if (tryCast$kotlin_stdlib != null) {
                    obj = EmptyCoroutineContext.INSTANCE;
                }
            }
            return (CoroutineContext) obj;
        }
        CoroutineContext.Key<?> key2 = minusPolymorphicKey.getKey();
        Object obj2 = minusPolymorphicKey;
        if (key2 == key) {
            obj2 = EmptyCoroutineContext.INSTANCE;
        }
        return (CoroutineContext) obj2;
    }
}
