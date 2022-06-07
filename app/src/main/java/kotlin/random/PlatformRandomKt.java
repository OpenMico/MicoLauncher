package kotlin.random;

import java.util.Random;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* compiled from: PlatformRandom.kt */
@Metadata(d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\u0000\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\u0000\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007¨\u0006\n"}, d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class PlatformRandomKt {
    public static final double doubleFromParts(int i, int i2) {
        return ((i << 27) + i2) / 9007199254740992L;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random asJavaRandom(@NotNull Random asJavaRandom) {
        Random impl;
        Intrinsics.checkNotNullParameter(asJavaRandom, "$this$asJavaRandom");
        AbstractPlatformRandom abstractPlatformRandom = (AbstractPlatformRandom) (!(asJavaRandom instanceof AbstractPlatformRandom) ? null : asJavaRandom);
        return (abstractPlatformRandom == null || (impl = abstractPlatformRandom.getImpl()) == null) ? new a(asJavaRandom) : impl;
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random asKotlinRandom(@NotNull Random asKotlinRandom) {
        Random a;
        Intrinsics.checkNotNullParameter(asKotlinRandom, "$this$asKotlinRandom");
        a aVar = (a) (!(asKotlinRandom instanceof a) ? null : asKotlinRandom);
        return (aVar == null || (a = aVar.a()) == null) ? new b(asKotlinRandom) : a;
    }
}
