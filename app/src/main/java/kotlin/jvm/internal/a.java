package kotlin.jvm.internal;

import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import java.util.NoSuchElementException;
import kotlin.Metadata;
import kotlin.collections.BooleanIterator;
import org.jetbrains.annotations.NotNull;

/* compiled from: ArrayIterators.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0018\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\bH\u0096\u0002J\b\u0010\t\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\n"}, d2 = {"Lkotlin/jvm/internal/ArrayBooleanIterator;", "Lkotlin/collections/BooleanIterator;", "array", "", "([Z)V", MiSoundBoxCommandExtras.INDEX, "", "hasNext", "", "nextBoolean", "kotlin-stdlib"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
final class a extends BooleanIterator {
    private int a;
    private final boolean[] b;

    public a(@NotNull boolean[] array) {
        Intrinsics.checkNotNullParameter(array, "array");
        this.b = array;
    }

    @Override // java.util.Iterator
    public boolean hasNext() {
        return this.a < this.b.length;
    }

    @Override // kotlin.collections.BooleanIterator
    public boolean nextBoolean() {
        try {
            boolean[] zArr = this.b;
            int i = this.a;
            this.a = i + 1;
            return zArr[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            this.a--;
            throw new NoSuchElementException(e.getMessage());
        }
    }
}
