package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.onetrack.api.b;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.channels.ChannelResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Channel.kt */
@Metadata(d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\f\u001a\u001e\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0007\u001a>\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u0016\b\u0002\u0010\u0007\u001a\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\t\u0018\u00010\b\u001aX\u0010\n\u001a\u0002H\u000b\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2#\u0010\r\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u0002H\u000b0\bH\u0086\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0012\u0010\u0013\u001a^\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000b0\f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2#\u0010\u0015\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\t0\bH\u0086\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0016\u0010\u0013\u001a^\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2#\u0010\u0015\u001a\u001f\u0012\u0015\u0012\u0013\u0018\u00010\u000e¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\t0\bH\u0086\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u0017\u0010\u0013\u001a\\\u0010\u0018\u001a\b\u0012\u0004\u0012\u0002H\u000b0\f\"\u0004\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2!\u0010\u0015\u001a\u001d\u0012\u0013\u0012\u0011H\u000b¢\u0006\f\b\u000f\u0012\b\b\u0010\u0012\u0004\b\b(\u0019\u0012\u0004\u0012\u00020\t0\bH\u0086\bø\u0001\u0000ø\u0001\u0001\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0000¢\u0006\u0004\b\u001a\u0010\u0013\u0082\u0002\u000b\n\u0002\b\u0019\n\u0005\b¡\u001e0\u0001¨\u0006\u001b"}, d2 = {"Channel", "Lkotlinx/coroutines/channels/Channel;", "E", "capacity", "", "onBufferOverflow", "Lkotlinx/coroutines/channels/BufferOverflow;", "onUndeliveredElement", "Lkotlin/Function1;", "", "getOrElse", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/channels/ChannelResult;", "onFailure", "", "Lkotlin/ParameterName;", "name", "exception", "getOrElse-WpGqRn0", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "onClosed", "action", "onClosed-WpGqRn0", "onFailure-WpGqRn0", "onSuccess", b.p, "onSuccess-WpGqRn0", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ChannelKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: getOrElse-WpGqRn0 */
    public static final <T> T m1762getOrElseWpGqRn0(@NotNull Object obj, @NotNull Function1<? super Throwable, ? extends T> function1) {
        return obj instanceof ChannelResult.Failed ? (T) function1.invoke(ChannelResult.m1770exceptionOrNullimpl(obj)) : obj;
    }

    @NotNull
    /* renamed from: onSuccess-WpGqRn0 */
    public static final <T> Object m1765onSuccessWpGqRn0(@NotNull Object obj, @NotNull Function1<? super T, Unit> function1) {
        if (!(obj instanceof ChannelResult.Failed)) {
            function1.invoke(obj);
        }
        return obj;
    }

    @NotNull
    /* renamed from: onFailure-WpGqRn0 */
    public static final <T> Object m1764onFailureWpGqRn0(@NotNull Object obj, @NotNull Function1<? super Throwable, Unit> function1) {
        if (obj instanceof ChannelResult.Failed) {
            function1.invoke(ChannelResult.m1770exceptionOrNullimpl(obj));
        }
        return obj;
    }

    @NotNull
    /* renamed from: onClosed-WpGqRn0 */
    public static final <T> Object m1763onClosedWpGqRn0(@NotNull Object obj, @NotNull Function1<? super Throwable, Unit> function1) {
        if (obj instanceof ChannelResult.Closed) {
            function1.invoke(ChannelResult.m1770exceptionOrNullimpl(obj));
        }
        return obj;
    }

    public static /* synthetic */ Channel Channel$default(int i, BufferOverflow bufferOverflow, Function1 function1, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        if ((i2 & 2) != 0) {
            bufferOverflow = BufferOverflow.SUSPEND;
        }
        if ((i2 & 4) != 0) {
            function1 = null;
        }
        return Channel(i, bufferOverflow, function1);
    }

    @NotNull
    public static final <E> Channel<E> Channel(int i, @NotNull BufferOverflow bufferOverflow, @Nullable Function1<? super E, Unit> function1) {
        ArrayChannel arrayChannel;
        ArrayChannel arrayChannel2;
        if (i == Integer.MAX_VALUE) {
            return new LinkedListChannel(function1);
        }
        int i2 = 1;
        switch (i) {
            case -2:
                if (bufferOverflow == BufferOverflow.SUSPEND) {
                    i2 = Channel.Factory.getCHANNEL_DEFAULT_CAPACITY$kotlinx_coroutines_core();
                }
                return new ArrayChannel(i2, bufferOverflow, function1);
            case -1:
                if (bufferOverflow != BufferOverflow.SUSPEND) {
                    i2 = 0;
                }
                if (i2 != 0) {
                    return new ConflatedChannel(function1);
                }
                throw new IllegalArgumentException("CONFLATED capacity cannot be used with non-default onBufferOverflow".toString());
            case 0:
                if (bufferOverflow == BufferOverflow.SUSPEND) {
                    arrayChannel = new RendezvousChannel(function1);
                } else {
                    arrayChannel = new ArrayChannel(1, bufferOverflow, function1);
                }
                return arrayChannel;
            default:
                if (i == 1 && bufferOverflow == BufferOverflow.DROP_OLDEST) {
                    arrayChannel2 = new ConflatedChannel(function1);
                } else {
                    arrayChannel2 = new ArrayChannel(i, bufferOverflow, function1);
                }
                return arrayChannel2;
        }
    }

    public static /* synthetic */ Channel Channel$default(int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return Channel$default(i, null, null, 6, null);
    }
}
