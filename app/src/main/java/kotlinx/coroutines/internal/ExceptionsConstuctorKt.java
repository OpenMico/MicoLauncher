package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CopyableThrowable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: ExceptionsConstuctor.kt */
@Metadata(d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0006j\u0004\u0018\u0001`\u00072\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0002\u001a1\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00072\u0014\b\u0004\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0006H\u0082\b\u001a!\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u0002H\u0010H\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\b\b\u0002\u0010\u0014\u001a\u00020\tH\u0082\u0010\u001a\u0018\u0010\u0015\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0016\u001a\u00020\tH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000\"4\u0010\u0002\u001a(\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00070\u0003X\u0082\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00062\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006¨\u0006\u0018"}, d2 = {"cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "throwableFields", "", "createConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "E", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ExceptionsConstuctorKt {
    private static final int a = a(Throwable.class, -1);
    @NotNull
    private static final ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    @NotNull
    private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> c = new WeakHashMap<>();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ExceptionsConstuctor.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "E", "", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a extends Lambda implements Function1 {
        public static final a a = new a();

        a() {
            super(1);
        }

        @Nullable
        /* renamed from: a */
        public final Void invoke(@NotNull Throwable th) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: ExceptionsConstuctor.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010\u0003\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003H\n"}, d2 = {"<anonymous>", "", "E", "", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b extends Lambda implements Function1 {
        public static final b a = new b();

        b() {
            super(1);
        }

        @Nullable
        /* renamed from: a */
        public final Void invoke(@NotNull Throwable th) {
            return null;
        }
    }

    /* JADX WARN: Finally extract failed */
    @Nullable
    public static final <E extends Throwable> E tryCopyException(@NotNull E e) {
        Object obj;
        if (e instanceof CopyableThrowable) {
            try {
                Result.Companion companion = Result.Companion;
                obj = Result.m1220constructorimpl(((CopyableThrowable) e).createCopy());
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                obj = Result.m1220constructorimpl(ResultKt.createFailure(th));
            }
            if (Result.m1225isFailureimpl(obj)) {
                obj = null;
            }
            return (E) ((Throwable) obj);
        }
        ReentrantReadWriteLock.ReadLock readLock = b.readLock();
        readLock.lock();
        try {
            Function1<Throwable, Throwable> function1 = c.get(e.getClass());
            readLock.unlock();
            if (function1 != null) {
                return (E) function1.invoke(e);
            }
            int i = 0;
            if (a != a(e.getClass(), 0)) {
                ReentrantReadWriteLock reentrantReadWriteLock = b;
                ReentrantReadWriteLock.ReadLock readLock2 = reentrantReadWriteLock.readLock();
                int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
                for (int i2 = 0; i2 < readHoldCount; i2++) {
                    readLock2.unlock();
                }
                ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
                writeLock.lock();
                try {
                    c.put(e.getClass(), a.a);
                    Unit unit = Unit.INSTANCE;
                    return null;
                } finally {
                    while (i < readHoldCount) {
                        readLock2.lock();
                        i++;
                    }
                    writeLock.unlock();
                }
            } else {
                Iterator it = ArraysKt.sortedWith(e.getClass().getConstructors(), new Comparator<T>() { // from class: kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(Integer.valueOf(((Constructor) t2).getParameterTypes().length), Integer.valueOf(((Constructor) t).getParameterTypes().length));
                    }
                }).iterator();
                Function1<Throwable, Throwable> function12 = null;
                while (it.hasNext() && (function12 = a((Constructor) it.next())) == null) {
                }
                ReentrantReadWriteLock reentrantReadWriteLock2 = b;
                ReentrantReadWriteLock.ReadLock readLock3 = reentrantReadWriteLock2.readLock();
                int readHoldCount2 = reentrantReadWriteLock2.getWriteHoldCount() == 0 ? reentrantReadWriteLock2.getReadHoldCount() : 0;
                for (int i3 = 0; i3 < readHoldCount2; i3++) {
                    readLock3.unlock();
                }
                ReentrantReadWriteLock.WriteLock writeLock2 = reentrantReadWriteLock2.writeLock();
                writeLock2.lock();
                try {
                    c.put(e.getClass(), function12 == null ? b.a : function12);
                    Unit unit2 = Unit.INSTANCE;
                    while (i < readHoldCount2) {
                        readLock3.lock();
                        i++;
                    }
                    writeLock2.unlock();
                    if (function12 == null) {
                        return null;
                    }
                    return (E) function12.invoke(e);
                } finally {
                    while (i < readHoldCount2) {
                        readLock3.lock();
                        i++;
                    }
                    writeLock2.unlock();
                }
            }
        } catch (Throwable th2) {
            readLock.unlock();
            throw th2;
        }
    }

    private static final Function1<Throwable, Throwable> a(Constructor<?> constructor) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        switch (parameterTypes.length) {
            case 0:
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4(constructor);
            case 1:
                Class<?> cls = parameterTypes[0];
                if (Intrinsics.areEqual(cls, Throwable.class)) {
                    return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2(constructor);
                }
                if (Intrinsics.areEqual(cls, String.class)) {
                    return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3(constructor);
                }
                return null;
            case 2:
                if (!Intrinsics.areEqual(parameterTypes[0], String.class) || !Intrinsics.areEqual(parameterTypes[1], Throwable.class)) {
                    return null;
                }
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1(constructor);
            default:
                return null;
        }
    }

    private static final int a(Class<?> cls, int i) {
        Object obj;
        JvmClassMappingKt.getKotlinClass(cls);
        try {
            Result.Companion companion = Result.Companion;
            obj = Result.m1220constructorimpl(Integer.valueOf(a(cls, 0, 1, null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            obj = Result.m1220constructorimpl(ResultKt.createFailure(th));
        }
        obj = Integer.valueOf(i);
        if (Result.m1225isFailureimpl(obj)) {
        }
        return ((Number) obj).intValue();
    }

    static /* synthetic */ int a(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return b(cls, i);
    }

    private static final int b(Class<?> cls, int i) {
        do {
            int i2 = 0;
            for (Field field : cls.getDeclaredFields()) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    i2++;
                }
            }
            i += i2;
            cls = cls.getSuperclass();
        } while (cls != null);
        return i;
    }
}
