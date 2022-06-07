package kotlinx.coroutines.internal;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.api.model.Remote;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

/* compiled from: ConcurrentLinkedList.kt */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\u001a#\u0010\u0002\u001a\u00028\u0000\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u0000*\u00028\u0000H\u0000¢\u0006\u0004\b\u0002\u0010\u0003\u001ao\u0010\u000e\u001a\b\u0012\u0004\u0012\u00028\u00000\r\"\u000e\b\u0000\u0010\u0005*\b\u0012\u0004\u0012\u00028\u00000\u0004*\u00028\u00002\u0006\u0010\u0007\u001a\u00020\u000628\u0010\f\u001a4\u0012\u0013\u0012\u00110\u0006¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u0007\u0012\u0015\u0012\u0013\u0018\u00018\u0000¢\u0006\f\b\t\u0012\b\b\n\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00028\u00000\bH\u0082\bø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\"\u001c\u0010\u0011\u001a\u00020\u00108\u0002@\u0003X\u0083\u0004¢\u0006\f\n\u0004\b\u0011\u0010\u0012\u0012\u0004\b\u0013\u0010\u0014\"\u0016\u0010\u0016\u001a\u00020\u00158\u0002@\u0002X\u0082T¢\u0006\u0006\n\u0004\b\u0016\u0010\u0017\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0018"}, d2 = {"Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "N", "close", "(Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;)Lkotlinx/coroutines/internal/ConcurrentLinkedListNode;", "Lkotlinx/coroutines/internal/Segment;", ExifInterface.LATITUDE_SOUTH, "", "id", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", Remote.Response.ControlAction.ACTION_PREV, "createNewSegment", "Lkotlinx/coroutines/internal/SegmentOrClosed;", "findSegmentInternal", "(Lkotlinx/coroutines/internal/Segment;JLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/Symbol;", "CLOSED", "Lkotlinx/coroutines/internal/Symbol;", "getCLOSED$annotations", "()V", "", "POINTERS_SHIFT", "I", "kotlinx-coroutines-core"}, k = 2, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public final class ConcurrentLinkedListKt {
    @NotNull
    private static final Symbol a = new Symbol("CLOSED");

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [kotlinx.coroutines.internal.ConcurrentLinkedListNode] */
    /* JADX WARN: Unknown variable types count: 1 */
    @org.jetbrains.annotations.NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <N extends kotlinx.coroutines.internal.ConcurrentLinkedListNode<N>> N close(@org.jetbrains.annotations.NotNull N r2) {
        /*
        L_0x0000:
            java.lang.Object r0 = kotlinx.coroutines.internal.ConcurrentLinkedListNode.access$getNextOrClosed(r2)
            kotlinx.coroutines.internal.Symbol r1 = access$getCLOSED$p()
            if (r0 != r1) goto L_0x000b
            return r2
        L_0x000b:
            kotlinx.coroutines.internal.ConcurrentLinkedListNode r0 = (kotlinx.coroutines.internal.ConcurrentLinkedListNode) r0
            if (r0 != 0) goto L_0x0016
            boolean r0 = r2.markAsClosed()
            if (r0 == 0) goto L_0x0000
            return r2
        L_0x0016:
            r2 = r0
            goto L_0x0000
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ConcurrentLinkedListKt.close(kotlinx.coroutines.internal.ConcurrentLinkedListNode):kotlinx.coroutines.internal.ConcurrentLinkedListNode");
    }
}
