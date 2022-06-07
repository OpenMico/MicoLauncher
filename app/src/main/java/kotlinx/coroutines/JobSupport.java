package kotlinx.coroutines;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.account.openauth.XiaomiOAuthConstants;
import com.xiaomi.micolauncher.module.video.manager.VideoDataManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequenceScope;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.Symbol;
import kotlinx.coroutines.intrinsics.CancellableKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause0;
import kotlinx.coroutines.selects.SelectInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JobSupport.kt */
@Deprecated(level = DeprecationLevel.ERROR, message = "This is internal API and may be removed in the future releases")
@Metadata(d1 = {"\u0000è\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0001\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0017\u0018\u00002\u00020X2\u00020\u00172\u00020\u007f2\u00030Ã\u0001:\u0006Ò\u0001Ó\u0001Ô\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0001¢\u0006\u0004\b\u0003\u0010\u0004J'\u0010\u000b\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0002¢\u0006\u0004\b\u000b\u0010\fJ%\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\r2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002¢\u0006\u0004\b\u0012\u0010\u0013J\u0019\u0010\u0015\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0014¢\u0006\u0004\b\u0015\u0010\u0016J\u0015\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u0018\u001a\u00020\u0017¢\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u001e\u001a\u0004\u0018\u00010\u0005H\u0080@ø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u001f\u001a\u0004\u0018\u00010\u0005H\u0082@ø\u0001\u0000¢\u0006\u0004\b\u001f\u0010\u001dJ\u0019\u0010!\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\rH\u0017¢\u0006\u0004\b!\u0010\"J\u001f\u0010!\u001a\u00020\u00112\u000e\u0010 \u001a\n\u0018\u00010#j\u0004\u0018\u0001`$H\u0016¢\u0006\u0004\b!\u0010%J\u0017\u0010&\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\r¢\u0006\u0004\b&\u0010\"J\u0019\u0010)\u001a\u00020\u00012\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\b'\u0010(J\u0017\u0010*\u001a\u00020\u00112\u0006\u0010 \u001a\u00020\rH\u0016¢\u0006\u0004\b*\u0010+J\u001b\u0010,\u001a\u0004\u0018\u00010\u00052\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b,\u0010-J\u0017\u0010.\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\rH\u0002¢\u0006\u0004\b.\u0010\"J\u000f\u00100\u001a\u00020/H\u0014¢\u0006\u0004\b0\u00101J\u0017\u00102\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\rH\u0016¢\u0006\u0004\b2\u0010\"J!\u00105\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b5\u00106J)\u0010;\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u0002072\u0006\u00109\u001a\u0002082\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b;\u0010<J\u0019\u0010=\u001a\u00020\r2\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b=\u0010>J(\u0010C\u001a\u00020@2\n\b\u0002\u0010?\u001a\u0004\u0018\u00010/2\n\b\u0002\u0010 \u001a\u0004\u0018\u00010\rH\u0080\b¢\u0006\u0004\bA\u0010BJ#\u0010D\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u0002072\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\bD\u0010EJ\u0019\u0010F\u001a\u0004\u0018\u0001082\u0006\u0010\u0014\u001a\u000203H\u0002¢\u0006\u0004\bF\u0010GJ\u0011\u0010H\u001a\u00060#j\u0002`$¢\u0006\u0004\bH\u0010IJ\u0013\u0010J\u001a\u00060#j\u0002`$H\u0016¢\u0006\u0004\bJ\u0010IJ\u0011\u0010M\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\bK\u0010LJ\u000f\u0010N\u001a\u0004\u0018\u00010\r¢\u0006\u0004\bN\u0010OJ'\u0010P\u001a\u0004\u0018\u00010\r2\u0006\u0010\u0014\u001a\u0002072\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\r0\u000fH\u0002¢\u0006\u0004\bP\u0010QJ\u0019\u0010R\u001a\u0004\u0018\u00010\u00072\u0006\u0010\u0014\u001a\u000203H\u0002¢\u0006\u0004\bR\u0010SJ\u0017\u0010U\u001a\u00020\u00012\u0006\u0010T\u001a\u00020\rH\u0014¢\u0006\u0004\bU\u0010\"J\u0017\u0010W\u001a\u00020\u00112\u0006\u0010T\u001a\u00020\rH\u0010¢\u0006\u0004\bV\u0010+J\u0019\u0010Z\u001a\u00020\u00112\b\u0010Y\u001a\u0004\u0018\u00010XH\u0004¢\u0006\u0004\bZ\u0010[JF\u0010d\u001a\u00020c2\u0006\u0010\\\u001a\u00020\u00012\u0006\u0010]\u001a\u00020\u00012'\u0010b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b_\u0012\b\b`\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00110^j\u0002`a¢\u0006\u0004\bd\u0010eJ6\u0010d\u001a\u00020c2'\u0010b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b_\u0012\b\b`\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00110^j\u0002`a¢\u0006\u0004\bd\u0010fJ\u0013\u0010g\u001a\u00020\u0011H\u0086@ø\u0001\u0000¢\u0006\u0004\bg\u0010\u001dJ\u000f\u0010h\u001a\u00020\u0001H\u0002¢\u0006\u0004\bh\u0010iJ\u0013\u0010j\u001a\u00020\u0011H\u0082@ø\u0001\u0000¢\u0006\u0004\bj\u0010\u001dJ&\u0010m\u001a\u00020l2\u0014\u0010k\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u00110^H\u0082\b¢\u0006\u0004\bm\u0010nJ\u001b\u0010o\u001a\u0004\u0018\u00010\u00052\b\u0010 \u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\bo\u0010-J\u0019\u0010q\u001a\u00020\u00012\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\bp\u0010(J\u001b\u0010s\u001a\u0004\u0018\u00010\u00052\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0000¢\u0006\u0004\br\u0010-J@\u0010t\u001a\u00020\t2'\u0010b\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\r¢\u0006\f\b_\u0012\b\b`\u0012\u0004\b\b( \u0012\u0004\u0012\u00020\u00110^j\u0002`a2\u0006\u0010\\\u001a\u00020\u0001H\u0002¢\u0006\u0004\bt\u0010uJ\u000f\u0010w\u001a\u00020/H\u0010¢\u0006\u0004\bv\u00101J\u001f\u0010x\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010 \u001a\u00020\rH\u0002¢\u0006\u0004\bx\u0010yJ.\u0010{\u001a\u00020\u0011\"\n\b\u0000\u0010z\u0018\u0001*\u00020\t2\u0006\u0010\b\u001a\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\rH\u0082\b¢\u0006\u0004\b{\u0010yJ\u0019\u0010\\\u001a\u00020\u00112\b\u0010 \u001a\u0004\u0018\u00010\rH\u0014¢\u0006\u0004\b\\\u0010+J\u0019\u0010|\u001a\u00020\u00112\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0014¢\u0006\u0004\b|\u0010\u0016J\u000f\u0010}\u001a\u00020\u0011H\u0014¢\u0006\u0004\b}\u0010~J\u0019\u0010\u0081\u0001\u001a\u00020\u00112\u0007\u0010\u0080\u0001\u001a\u00020\u007f¢\u0006\u0006\b\u0081\u0001\u0010\u0082\u0001J\u001b\u0010\u0084\u0001\u001a\u00020\u00112\u0007\u0010\u0014\u001a\u00030\u0083\u0001H\u0002¢\u0006\u0006\b\u0084\u0001\u0010\u0085\u0001J\u001a\u0010\u0086\u0001\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\tH\u0002¢\u0006\u0006\b\u0086\u0001\u0010\u0087\u0001JI\u0010\u008c\u0001\u001a\u00020\u0011\"\u0005\b\u0000\u0010\u0088\u00012\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u00028\u00000\u0089\u00012\u001d\u0010k\u001a\u0019\b\u0001\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00000\u008b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00050^ø\u0001\u0000¢\u0006\u0006\b\u008c\u0001\u0010\u008d\u0001JX\u0010\u0091\u0001\u001a\u00020\u0011\"\u0004\b\u0000\u0010z\"\u0005\b\u0001\u0010\u0088\u00012\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u00028\u00010\u0089\u00012$\u0010k\u001a \b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00010\u008b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u008e\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u008f\u0001\u0010\u0090\u0001J\u001a\u0010\u0093\u0001\u001a\u00020\u00112\u0006\u0010\n\u001a\u00020\tH\u0000¢\u0006\u0006\b\u0092\u0001\u0010\u0087\u0001JX\u0010\u0095\u0001\u001a\u00020\u0011\"\u0004\b\u0000\u0010z\"\u0005\b\u0001\u0010\u0088\u00012\u000e\u0010\u008a\u0001\u001a\t\u0012\u0004\u0012\u00028\u00010\u0089\u00012$\u0010k\u001a \b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\u000b\u0012\t\u0012\u0004\u0012\u00028\u00010\u008b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u008e\u0001H\u0000ø\u0001\u0000¢\u0006\u0006\b\u0094\u0001\u0010\u0090\u0001J\u000f\u0010\u0096\u0001\u001a\u00020\u0001¢\u0006\u0005\b\u0096\u0001\u0010iJ\u001d\u0010\u0098\u0001\u001a\u00030\u0097\u00012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b\u0098\u0001\u0010\u0099\u0001J\u001c\u0010\u009a\u0001\u001a\u00020/2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b\u009a\u0001\u0010\u009b\u0001J\u0011\u0010\u009c\u0001\u001a\u00020/H\u0007¢\u0006\u0005\b\u009c\u0001\u00101J\u0011\u0010\u009d\u0001\u001a\u00020/H\u0016¢\u0006\u0005\b\u009d\u0001\u00101J$\u0010\u009e\u0001\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002032\b\u00104\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b\u009e\u0001\u0010\u009f\u0001J\"\u0010 \u0001\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002032\u0006\u0010\u000e\u001a\u00020\rH\u0002¢\u0006\u0006\b \u0001\u0010¡\u0001J(\u0010¢\u0001\u001a\u0004\u0018\u00010\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u00052\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b¢\u0001\u0010£\u0001J&\u0010¤\u0001\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0014\u001a\u0002032\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0006\b¤\u0001\u0010¥\u0001J-\u0010¦\u0001\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u0002072\u0006\u0010\u0018\u001a\u0002082\b\u0010:\u001a\u0004\u0018\u00010\u0005H\u0082\u0010¢\u0006\u0006\b¦\u0001\u0010§\u0001J\u0019\u0010©\u0001\u001a\u0004\u0018\u000108*\u00030¨\u0001H\u0002¢\u0006\u0006\b©\u0001\u0010ª\u0001J\u001f\u0010«\u0001\u001a\u00020\u0011*\u00020\u00072\b\u0010 \u001a\u0004\u0018\u00010\rH\u0002¢\u0006\u0005\b«\u0001\u0010yJ&\u0010¬\u0001\u001a\u00060#j\u0002`$*\u00020\r2\n\b\u0002\u0010?\u001a\u0004\u0018\u00010/H\u0004¢\u0006\u0006\b¬\u0001\u0010\u00ad\u0001R\u001d\u0010±\u0001\u001a\t\u0012\u0004\u0012\u00020X0®\u00018F@\u0006¢\u0006\b\u001a\u0006\b¯\u0001\u0010°\u0001R\u001a\u0010³\u0001\u001a\u0004\u0018\u00010\r8D@\u0004X\u0084\u0004¢\u0006\u0007\u001a\u0005\b²\u0001\u0010OR\u0018\u0010µ\u0001\u001a\u00020\u00018D@\u0004X\u0084\u0004¢\u0006\u0007\u001a\u0005\b´\u0001\u0010iR\u0018\u0010·\u0001\u001a\u00020\u00018P@\u0010X\u0090\u0004¢\u0006\u0007\u001a\u0005\b¶\u0001\u0010iR\u0018\u0010¸\u0001\u001a\u00020\u00018V@\u0016X\u0096\u0004¢\u0006\u0007\u001a\u0005\b¸\u0001\u0010iR\u0015\u0010¹\u0001\u001a\u00020\u00018F@\u0006¢\u0006\u0007\u001a\u0005\b¹\u0001\u0010iR\u0015\u0010º\u0001\u001a\u00020\u00018F@\u0006¢\u0006\u0007\u001a\u0005\bº\u0001\u0010iR\u0015\u0010»\u0001\u001a\u00020\u00018F@\u0006¢\u0006\u0007\u001a\u0005\b»\u0001\u0010iR\u0018\u0010¼\u0001\u001a\u00020\u00018T@\u0014X\u0094\u0004¢\u0006\u0007\u001a\u0005\b¼\u0001\u0010iR\u001b\u0010À\u0001\u001a\u0007\u0012\u0002\b\u00030½\u00018F@\u0006¢\u0006\b\u001a\u0006\b¾\u0001\u0010¿\u0001R\u0018\u0010Â\u0001\u001a\u00020\u00018P@\u0010X\u0090\u0004¢\u0006\u0007\u001a\u0005\bÁ\u0001\u0010iR\u0017\u0010Æ\u0001\u001a\u00030Ã\u00018F@\u0006¢\u0006\b\u001a\u0006\bÄ\u0001\u0010Å\u0001R.\u0010Ì\u0001\u001a\u0004\u0018\u00010\u00192\t\u0010Ç\u0001\u001a\u0004\u0018\u00010\u00198@@@X\u0080\u000e¢\u0006\u0010\u001a\u0006\bÈ\u0001\u0010É\u0001\"\u0006\bÊ\u0001\u0010Ë\u0001R\u0019\u0010\u0014\u001a\u0004\u0018\u00010\u00058@@\u0000X\u0080\u0004¢\u0006\u0007\u001a\u0005\bÍ\u0001\u0010LR \u0010Ï\u0001\u001a\u0004\u0018\u00010\r*\u0004\u0018\u00010\u00058B@\u0002X\u0082\u0004¢\u0006\u0007\u001a\u0005\bÎ\u0001\u0010>R\u001d\u0010Ð\u0001\u001a\u00020\u0001*\u0002038B@\u0002X\u0082\u0004¢\u0006\b\u001a\u0006\bÐ\u0001\u0010Ñ\u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006Õ\u0001"}, d2 = {"Lkotlinx/coroutines/JobSupport;", "", "active", "<init>", "(Z)V", "", "expect", "Lkotlinx/coroutines/NodeList;", "list", "Lkotlinx/coroutines/JobNode;", "node", "addLastAtomic", "(Ljava/lang/Object;Lkotlinx/coroutines/NodeList;Lkotlinx/coroutines/JobNode;)Z", "", "rootCause", "", "exceptions", "", "addSuppressedExceptions", "(Ljava/lang/Throwable;Ljava/util/List;)V", XiaomiOAuthConstants.EXTRA_STATE_2, "afterCompletion", "(Ljava/lang/Object;)V", "Lkotlinx/coroutines/ChildJob;", VideoDataManager.ORIGIN_CHILD, "Lkotlinx/coroutines/ChildHandle;", "attachChild", "(Lkotlinx/coroutines/ChildJob;)Lkotlinx/coroutines/ChildHandle;", "awaitInternal$kotlinx_coroutines_core", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "awaitInternal", "awaitSuspend", "cause", "cancel", "(Ljava/lang/Throwable;)Z", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "(Ljava/util/concurrent/CancellationException;)V", "cancelCoroutine", "cancelImpl$kotlinx_coroutines_core", "(Ljava/lang/Object;)Z", "cancelImpl", "cancelInternal", "(Ljava/lang/Throwable;)V", "cancelMakeCompleting", "(Ljava/lang/Object;)Ljava/lang/Object;", "cancelParent", "", "cancellationExceptionMessage", "()Ljava/lang/String;", "childCancelled", "Lkotlinx/coroutines/Incomplete;", "update", "completeStateFinalization", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)V", "Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/ChildHandleNode;", "lastChild", "proposedUpdate", "continueCompleting", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "createCauseException", "(Ljava/lang/Object;)Ljava/lang/Throwable;", "message", "Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException$kotlinx_coroutines_core", "(Ljava/lang/String;Ljava/lang/Throwable;)Lkotlinx/coroutines/JobCancellationException;", "defaultCancellationException", "finalizeFinishingState", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/lang/Object;)Ljava/lang/Object;", "firstChild", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/ChildHandleNode;", "getCancellationException", "()Ljava/util/concurrent/CancellationException;", "getChildJobCancellationCause", "getCompletedInternal$kotlinx_coroutines_core", "()Ljava/lang/Object;", "getCompletedInternal", "getCompletionExceptionOrNull", "()Ljava/lang/Throwable;", "getFinalRootCause", "(Lkotlinx/coroutines/JobSupport$Finishing;Ljava/util/List;)Ljava/lang/Throwable;", "getOrPromoteCancellingList", "(Lkotlinx/coroutines/Incomplete;)Lkotlinx/coroutines/NodeList;", "exception", "handleJobException", "handleOnCompletionException$kotlinx_coroutines_core", "handleOnCompletionException", "Lkotlinx/coroutines/Job;", "parent", "initParentJob", "(Lkotlinx/coroutines/Job;)V", "onCancelling", "invokeImmediately", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "Lkotlinx/coroutines/DisposableHandle;", "invokeOnCompletion", "(ZZLkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/DisposableHandle;", "join", "joinInternal", "()Z", "joinSuspend", "block", "", "loopOnState", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Void;", "makeCancelling", "makeCompleting$kotlinx_coroutines_core", "makeCompleting", "makeCompletingOnce$kotlinx_coroutines_core", "makeCompletingOnce", "makeNode", "(Lkotlin/jvm/functions/Function1;Z)Lkotlinx/coroutines/JobNode;", "nameString$kotlinx_coroutines_core", "nameString", "notifyCancelling", "(Lkotlinx/coroutines/NodeList;Ljava/lang/Throwable;)V", ExifInterface.GPS_DIRECTION_TRUE, "notifyHandlers", "onCompletionInternal", "onStart", "()V", "Lkotlinx/coroutines/ParentJob;", "parentJob", "parentCancelled", "(Lkotlinx/coroutines/ParentJob;)V", "Lkotlinx/coroutines/Empty;", "promoteEmptyToNodeList", "(Lkotlinx/coroutines/Empty;)V", "promoteSingleToNodeList", "(Lkotlinx/coroutines/JobNode;)V", "R", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "Lkotlin/coroutines/Continuation;", "registerSelectClause0", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function1;)V", "Lkotlin/Function2;", "registerSelectClause1Internal$kotlinx_coroutines_core", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "registerSelectClause1Internal", "removeNode$kotlinx_coroutines_core", "removeNode", "selectAwaitCompletion$kotlinx_coroutines_core", "selectAwaitCompletion", "start", "", "startInternal", "(Ljava/lang/Object;)I", "stateString", "(Ljava/lang/Object;)Ljava/lang/String;", "toDebugString", "toString", "tryFinalizeSimpleState", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)Z", "tryMakeCancelling", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Throwable;)Z", "tryMakeCompleting", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "tryMakeCompletingSlowPath", "(Lkotlinx/coroutines/Incomplete;Ljava/lang/Object;)Ljava/lang/Object;", "tryWaitForChild", "(Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)Z", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "nextChild", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)Lkotlinx/coroutines/ChildHandleNode;", "notifyCompletion", "toCancellationException", "(Ljava/lang/Throwable;Ljava/lang/String;)Ljava/util/concurrent/CancellationException;", "Lkotlin/sequences/Sequence;", "getChildren", "()Lkotlin/sequences/Sequence;", "children", "getCompletionCause", "completionCause", "getCompletionCauseHandled", "completionCauseHandled", "getHandlesException$kotlinx_coroutines_core", "handlesException", "isActive", "isCancelled", "isCompleted", "isCompletedExceptionally", "isScopedCoroutine", "Lkotlin/coroutines/CoroutineContext$Key;", "getKey", "()Lkotlin/coroutines/CoroutineContext$Key;", "key", "getOnCancelComplete$kotlinx_coroutines_core", "onCancelComplete", "Lkotlinx/coroutines/selects/SelectClause0;", "getOnJoin", "()Lkotlinx/coroutines/selects/SelectClause0;", "onJoin", com.xiaomi.onetrack.api.b.p, "getParentHandle$kotlinx_coroutines_core", "()Lkotlinx/coroutines/ChildHandle;", "setParentHandle$kotlinx_coroutines_core", "(Lkotlinx/coroutines/ChildHandle;)V", "parentHandle", "getState$kotlinx_coroutines_core", "getExceptionOrNull", "exceptionOrNull", "isCancelling", "(Lkotlinx/coroutines/Incomplete;)Z", "AwaitContinuation", "ChildCompletion", "Finishing", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes5.dex */
public class JobSupport implements ChildJob, Job, ParentJob, SelectClause0 {
    private static final /* synthetic */ AtomicReferenceFieldUpdater a = AtomicReferenceFieldUpdater.newUpdater(JobSupport.class, Object.class, "_state");
    @NotNull
    private volatile /* synthetic */ Object _parentHandle;
    @NotNull
    private volatile /* synthetic */ Object _state;

    protected void afterCompletion(@Nullable Object obj) {
    }

    @NotNull
    public String cancellationExceptionMessage() {
        return "Job was cancelled";
    }

    public boolean getHandlesException$kotlinx_coroutines_core() {
        return true;
    }

    public boolean getOnCancelComplete$kotlinx_coroutines_core() {
        return false;
    }

    protected boolean handleJobException(@NotNull Throwable th) {
        return false;
    }

    protected boolean isScopedCoroutine() {
        return false;
    }

    protected void onCancelling(@Nullable Throwable th) {
    }

    protected void onCompletionInternal(@Nullable Object obj) {
    }

    protected void onStart() {
    }

    public JobSupport(boolean z) {
        this._state = z ? JobSupportKt.f : JobSupportKt.e;
        this._parentHandle = null;
    }

    @Override // kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        cancel((CancellationException) null);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    public <R> R fold(R r, @NotNull Function2<? super R, ? super CoroutineContext.Element, ? extends R> function2) {
        return (R) Job.DefaultImpls.fold(this, r, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    @Nullable
    public <E extends CoroutineContext.Element> E get(@NotNull CoroutineContext.Key<E> key) {
        return (E) Job.DefaultImpls.get(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element, kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext minusKey(@NotNull CoroutineContext.Key<?> key) {
        return Job.DefaultImpls.minusKey(this, key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    @NotNull
    public CoroutineContext plus(@NotNull CoroutineContext coroutineContext) {
        return Job.DefaultImpls.plus(this, coroutineContext);
    }

    @Override // kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.ERROR, message = "Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")
    @NotNull
    public Job plus(@NotNull Job job) {
        return Job.DefaultImpls.plus((Job) this, job);
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    @NotNull
    public final CoroutineContext.Key<?> getKey() {
        return Job.Key;
    }

    @Nullable
    public final ChildHandle getParentHandle$kotlinx_coroutines_core() {
        return (ChildHandle) this._parentHandle;
    }

    public final void setParentHandle$kotlinx_coroutines_core(@Nullable ChildHandle childHandle) {
        this._parentHandle = childHandle;
    }

    protected final void initParentJob(@Nullable Job job) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getParentHandle$kotlinx_coroutines_core() == null)) {
                throw new AssertionError();
            }
        }
        if (job == null) {
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
            return;
        }
        job.start();
        ChildHandle attachChild = job.attachChild(this);
        setParentHandle$kotlinx_coroutines_core(attachChild);
        if (isCompleted()) {
            attachChild.dispose();
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
        }
    }

    @Override // kotlinx.coroutines.Job
    public boolean isActive() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        return (state$kotlinx_coroutines_core instanceof Incomplete) && ((Incomplete) state$kotlinx_coroutines_core).isActive();
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof Incomplete);
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCancelled() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        return (state$kotlinx_coroutines_core instanceof CompletedExceptionally) || ((state$kotlinx_coroutines_core instanceof c) && ((c) state$kotlinx_coroutines_core).d());
    }

    private final Object a(c cVar, Object obj) {
        boolean d2;
        Throwable a2;
        boolean z = true;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getState$kotlinx_coroutines_core() == cVar)) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED() && !(!cVar.c())) {
            throw new AssertionError();
        } else if (!DebugKt.getASSERTIONS_ENABLED() || cVar.a()) {
            CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
            Throwable th = completedExceptionally == null ? null : completedExceptionally.cause;
            synchronized (cVar) {
                d2 = cVar.d();
                List<Throwable> b2 = cVar.b(th);
                a2 = a(cVar, (List<? extends Throwable>) b2);
                if (a2 != null) {
                    a(a2, (List<? extends Throwable>) b2);
                }
            }
            if (!(a2 == null || a2 == th)) {
                obj = new CompletedExceptionally(a2, false, 2, null);
            }
            if (a2 != null) {
                if (!a(a2) && !handleJobException(a2)) {
                    z = false;
                }
                if (z) {
                    if (obj != null) {
                        ((CompletedExceptionally) obj).makeHandled();
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.CompletedExceptionally");
                    }
                }
            }
            if (!d2) {
                onCancelling(a2);
            }
            onCompletionInternal(obj);
            boolean compareAndSet = a.compareAndSet(this, cVar, JobSupportKt.boxIncomplete(obj));
            if (!DebugKt.getASSERTIONS_ENABLED() || compareAndSet) {
                b(cVar, obj);
                return obj;
            }
            throw new AssertionError();
        } else {
            throw new AssertionError();
        }
    }

    private final Throwable a(c cVar, List<? extends Throwable> list) {
        Object obj;
        Object obj2;
        boolean z;
        boolean z2;
        if (!list.isEmpty()) {
            List<? extends Throwable> list2 = list;
            Iterator<T> it = list2.iterator();
            while (true) {
                if (!it.hasNext()) {
                    obj = null;
                    break;
                }
                obj = it.next();
                if (!(((Throwable) obj) instanceof CancellationException)) {
                    z2 = true;
                    continue;
                } else {
                    z2 = false;
                    continue;
                }
                if (z2) {
                    break;
                }
            }
            Throwable th = (Throwable) obj;
            if (th != null) {
                return th;
            }
            Throwable th2 = (Throwable) list.get(0);
            if (th2 instanceof TimeoutCancellationException) {
                Iterator<T> it2 = list2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        obj2 = null;
                        break;
                    }
                    obj2 = it2.next();
                    Throwable th3 = (Throwable) obj2;
                    if (th3 == th2 || !(th3 instanceof TimeoutCancellationException)) {
                        z = false;
                        continue;
                    } else {
                        z = true;
                        continue;
                    }
                    if (z) {
                        break;
                    }
                }
                Throwable th4 = (Throwable) obj2;
                if (th4 != null) {
                    return th4;
                }
            }
            return th2;
        } else if (cVar.d()) {
            return new JobCancellationException(cancellationExceptionMessage(), null, this);
        } else {
            return null;
        }
    }

    private final void a(Throwable th, List<? extends Throwable> list) {
        if (list.size() > 1) {
            Set newSetFromMap = Collections.newSetFromMap(new IdentityHashMap(list.size()));
            Throwable unwrapImpl = !DebugKt.getRECOVER_STACK_TRACES() ? th : StackTraceRecoveryKt.unwrapImpl(th);
            for (Throwable th2 : list) {
                if (DebugKt.getRECOVER_STACK_TRACES()) {
                    th2 = StackTraceRecoveryKt.unwrapImpl(th2);
                }
                if (th2 != th && th2 != unwrapImpl && !(th2 instanceof CancellationException) && newSetFromMap.add(th2)) {
                    ExceptionsKt.addSuppressed(th, th2);
                }
            }
        }
    }

    private final boolean a(Incomplete incomplete, Object obj) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!((incomplete instanceof n) || (incomplete instanceof JobNode))) {
                throw new AssertionError();
            }
        }
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(!(obj instanceof CompletedExceptionally))) {
                throw new AssertionError();
            }
        }
        if (!a.compareAndSet(this, incomplete, JobSupportKt.boxIncomplete(obj))) {
            return false;
        }
        onCancelling(null);
        onCompletionInternal(obj);
        b(incomplete, obj);
        return true;
    }

    private final void b(Incomplete incomplete, Object obj) {
        ChildHandle parentHandle$kotlinx_coroutines_core = getParentHandle$kotlinx_coroutines_core();
        if (parentHandle$kotlinx_coroutines_core != null) {
            parentHandle$kotlinx_coroutines_core.dispose();
            setParentHandle$kotlinx_coroutines_core(NonDisposableHandle.INSTANCE);
        }
        Throwable th = null;
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally != null) {
            th = completedExceptionally.cause;
        }
        if (incomplete instanceof JobNode) {
            try {
                ((JobNode) incomplete).invoke(th);
            } catch (Throwable th2) {
                handleOnCompletionException$kotlinx_coroutines_core(new CompletionHandlerException("Exception in completion handler " + incomplete + " for " + this, th2));
            }
        } else {
            NodeList list = incomplete.getList();
            if (list != null) {
                b(list, th);
            }
        }
    }

    private final void a(NodeList nodeList, Throwable th) {
        onCancelling(th);
        NodeList nodeList2 = nodeList;
        CompletionHandlerException completionHandlerException = null;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) nodeList2.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, nodeList2); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof JobCancellingNode) {
                JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                try {
                    jobNode.invoke(th);
                } catch (Throwable th2) {
                    CompletionHandlerException completionHandlerException2 = completionHandlerException;
                    if (completionHandlerException2 == null) {
                        completionHandlerException2 = null;
                    } else {
                        ExceptionsKt.addSuppressed(completionHandlerException2, th2);
                    }
                    if (completionHandlerException2 == null) {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th2);
                    }
                }
            }
        }
        CompletionHandlerException completionHandlerException3 = completionHandlerException;
        if (completionHandlerException3 != null) {
            handleOnCompletionException$kotlinx_coroutines_core(completionHandlerException3);
        }
        a(th);
    }

    private final boolean a(Throwable th) {
        if (isScopedCoroutine()) {
            return true;
        }
        boolean z = th instanceof CancellationException;
        ChildHandle parentHandle$kotlinx_coroutines_core = getParentHandle$kotlinx_coroutines_core();
        return (parentHandle$kotlinx_coroutines_core == null || parentHandle$kotlinx_coroutines_core == NonDisposableHandle.INSTANCE) ? z : parentHandle$kotlinx_coroutines_core.childCancelled(th) || z;
    }

    private final int a(Object obj) {
        n nVar;
        if (obj instanceof n) {
            if (((n) obj).isActive()) {
                return 0;
            }
            AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = a;
            nVar = JobSupportKt.f;
            if (!atomicReferenceFieldUpdater.compareAndSet(this, obj, nVar)) {
                return -1;
            }
            onStart();
            return 1;
        } else if (!(obj instanceof InactiveNodeList)) {
            return 0;
        } else {
            if (!a.compareAndSet(this, obj, ((InactiveNodeList) obj).getList())) {
                return -1;
            }
            onStart();
            return 1;
        }
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final CancellationException getCancellationException() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof c) {
            Throwable b2 = ((c) state$kotlinx_coroutines_core).b();
            if (b2 != null) {
                return toCancellationException(b2, Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " is cancelling"));
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
        } else if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
            return state$kotlinx_coroutines_core instanceof CompletedExceptionally ? toCancellationException$default(this, ((CompletedExceptionally) state$kotlinx_coroutines_core).cause, null, 1, null) : new JobCancellationException(Intrinsics.stringPlus(DebugStringsKt.getClassSimpleName(this), " has completed normally"), null, this);
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
        }
    }

    public static /* synthetic */ CancellationException toCancellationException$default(JobSupport jobSupport, Throwable th, String str, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                str = null;
            }
            return jobSupport.toCancellationException(th, str);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: toCancellationException");
    }

    @NotNull
    protected final CancellationException toCancellationException(@NotNull Throwable th, @Nullable String str) {
        CancellationException cancellationException = th instanceof CancellationException ? (CancellationException) th : null;
        if (cancellationException != null) {
            return cancellationException;
        }
        if (str == null) {
            str = cancellationExceptionMessage();
        }
        return new JobCancellationException(str, th, this);
    }

    @Nullable
    protected final Throwable getCompletionCause() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof c) {
            Throwable b2 = ((c) state$kotlinx_coroutines_core).b();
            if (b2 != null) {
                return b2;
            }
            throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
        } else if (state$kotlinx_coroutines_core instanceof Incomplete) {
            throw new IllegalStateException(Intrinsics.stringPlus("Job is still new or active: ", this).toString());
        } else if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            return ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        } else {
            return null;
        }
    }

    protected final boolean getCompletionCauseHandled() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        return (state$kotlinx_coroutines_core instanceof CompletedExceptionally) && ((CompletedExceptionally) state$kotlinx_coroutines_core).getHandled();
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final DisposableHandle invokeOnCompletion(@NotNull Function1<? super Throwable, Unit> function1) {
        return invokeOnCompletion(false, true, function1);
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final DisposableHandle invokeOnCompletion(boolean z, boolean z2, @NotNull Function1<? super Throwable, Unit> function1) {
        JobNode a2 = a(function1, z);
        while (true) {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (state$kotlinx_coroutines_core instanceof n) {
                n nVar = (n) state$kotlinx_coroutines_core;
                if (!nVar.isActive()) {
                    a(nVar);
                } else if (a.compareAndSet(this, state$kotlinx_coroutines_core, a2)) {
                    return a2;
                }
            } else {
                Throwable th = null;
                if (state$kotlinx_coroutines_core instanceof Incomplete) {
                    NodeList list = ((Incomplete) state$kotlinx_coroutines_core).getList();
                    if (list != null) {
                        DisposableHandle disposableHandle = NonDisposableHandle.INSTANCE;
                        if (z && (state$kotlinx_coroutines_core instanceof c)) {
                            synchronized (state$kotlinx_coroutines_core) {
                                th = ((c) state$kotlinx_coroutines_core).b();
                                if (th == null || ((function1 instanceof ChildHandleNode) && !((c) state$kotlinx_coroutines_core).a())) {
                                    if (a(state$kotlinx_coroutines_core, list, a2)) {
                                        if (th == null) {
                                            return a2;
                                        }
                                        disposableHandle = a2;
                                    }
                                }
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                        if (th != null) {
                            if (z2) {
                                function1.invoke(th);
                            }
                            return disposableHandle;
                        } else if (a(state$kotlinx_coroutines_core, list, a2)) {
                            return a2;
                        }
                    } else if (state$kotlinx_coroutines_core != null) {
                        a((JobNode) state$kotlinx_coroutines_core);
                    } else {
                        throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.JobNode");
                    }
                } else {
                    if (z2) {
                        CompletedExceptionally completedExceptionally = state$kotlinx_coroutines_core instanceof CompletedExceptionally ? (CompletedExceptionally) state$kotlinx_coroutines_core : null;
                        if (completedExceptionally != null) {
                            th = completedExceptionally.cause;
                        }
                        function1.invoke(th);
                    }
                    return NonDisposableHandle.INSTANCE;
                }
            }
        }
    }

    private final JobNode a(Function1<? super Throwable, Unit> function1, boolean z) {
        s sVar = null;
        if (z) {
            if (function1 instanceof JobCancellingNode) {
                sVar = (JobCancellingNode) function1;
            }
            if (sVar == null) {
                sVar = new r(function1);
            }
            sVar = sVar;
        } else {
            JobNode jobNode = function1 instanceof JobNode ? (JobNode) function1 : null;
            if (jobNode != null) {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (!(!(jobNode instanceof JobCancellingNode))) {
                        throw new AssertionError();
                    }
                }
                sVar = jobNode;
            }
            if (sVar == null) {
                sVar = new s(function1);
            }
        }
        sVar.setJob(this);
        return sVar;
    }

    private final boolean a(Object obj, NodeList nodeList, JobNode jobNode) {
        NodeList nodeList2 = nodeList;
        JobNode jobNode2 = jobNode;
        while (true) {
            switch (nodeList2.getPrevNode().tryCondAddNext(jobNode2, nodeList2, new LockFreeLinkedListNode.CondAddOp(this, obj) { // from class: kotlinx.coroutines.JobSupport$addLastAtomic$$inlined$addLastIf$1
                final /* synthetic */ JobSupport b;
                final /* synthetic */ Object c;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(LockFreeLinkedListNode.this);
                    this.b = this;
                    this.c = obj;
                }

                @Nullable
                public Object prepare(@NotNull LockFreeLinkedListNode lockFreeLinkedListNode) {
                    if (this.b.getState$kotlinx_coroutines_core() == this.c) {
                        return null;
                    }
                    return LockFreeLinkedListKt.getCONDITION_FALSE();
                }
            })) {
                case 1:
                    return true;
                case 2:
                    return false;
            }
        }
    }

    private final void a(n nVar) {
        NodeList nodeList = new NodeList();
        a.compareAndSet(this, nVar, nVar.isActive() ? nodeList : new InactiveNodeList(nodeList));
    }

    private final void a(JobNode jobNode) {
        jobNode.addOneIfEmpty(new NodeList());
        a.compareAndSet(this, jobNode, jobNode.getNextNode());
    }

    @Override // kotlinx.coroutines.Job
    @Nullable
    public final Object join(@NotNull Continuation<? super Unit> continuation) {
        if (!a()) {
            JobKt.ensureActive(continuation.getContext());
            return Unit.INSTANCE;
        }
        Object a2 = a(continuation);
        return a2 == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? a2 : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final SelectClause0 getOnJoin() {
        return this;
    }

    public void cancelInternal(@NotNull Throwable th) {
        cancelImpl$kotlinx_coroutines_core(th);
    }

    @Override // kotlinx.coroutines.ChildJob
    public final void parentCancelled(@NotNull ParentJob parentJob) {
        cancelImpl$kotlinx_coroutines_core(parentJob);
    }

    public boolean childCancelled(@NotNull Throwable th) {
        if (th instanceof CancellationException) {
            return true;
        }
        return cancelImpl$kotlinx_coroutines_core(th) && getHandlesException$kotlinx_coroutines_core();
    }

    public final boolean cancelCoroutine(@Nullable Throwable th) {
        return cancelImpl$kotlinx_coroutines_core(th);
    }

    public final boolean cancelImpl$kotlinx_coroutines_core(@Nullable Object obj) {
        Object obj2;
        Symbol symbol;
        Symbol symbol2;
        Symbol symbol3;
        obj2 = JobSupportKt.a;
        if (getOnCancelComplete$kotlinx_coroutines_core() && (obj2 = b(obj)) == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        symbol = JobSupportKt.a;
        if (obj2 == symbol) {
            obj2 = d(obj);
        }
        symbol2 = JobSupportKt.a;
        if (obj2 == symbol2 || obj2 == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
            return true;
        }
        symbol3 = JobSupportKt.c;
        if (obj2 == symbol3) {
            return false;
        }
        afterCompletion(obj2);
        return true;
    }

    public static /* synthetic */ JobCancellationException defaultCancellationException$kotlinx_coroutines_core$default(JobSupport jobSupport, String str, Throwable th, int i, Object obj) {
        if (obj == null) {
            if ((i & 1) != 0) {
                str = null;
            }
            if ((i & 2) != 0) {
                th = null;
            }
            if (str == null) {
                str = jobSupport.cancellationExceptionMessage();
            }
            return new JobCancellationException(str, th, jobSupport);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: defaultCancellationException");
    }

    @NotNull
    public final JobCancellationException defaultCancellationException$kotlinx_coroutines_core(@Nullable String str, @Nullable Throwable th) {
        if (str == null) {
            str = cancellationExceptionMessage();
        }
        return new JobCancellationException(str, th, this);
    }

    @Override // kotlinx.coroutines.ParentJob
    @NotNull
    public CancellationException getChildJobCancellationCause() {
        Throwable th;
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        CancellationException cancellationException = null;
        if (state$kotlinx_coroutines_core instanceof c) {
            th = ((c) state$kotlinx_coroutines_core).b();
        } else if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            th = ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        } else if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
            th = null;
        } else {
            throw new IllegalStateException(Intrinsics.stringPlus("Cannot be cancelling child in this state: ", state$kotlinx_coroutines_core).toString());
        }
        if (th instanceof CancellationException) {
            cancellationException = (CancellationException) th;
        }
        return cancellationException == null ? new JobCancellationException(Intrinsics.stringPlus("Parent job is ", f(state$kotlinx_coroutines_core)), th, this) : cancellationException;
    }

    private final Throwable c(Object obj) {
        if (obj == null ? true : obj instanceof Throwable) {
            Throwable th = (Throwable) obj;
            return th == null ? new JobCancellationException(cancellationExceptionMessage(), null, this) : th;
        } else if (obj != null) {
            return ((ParentJob) obj).getChildJobCancellationCause();
        } else {
            throw new NullPointerException("null cannot be cast to non-null type kotlinx.coroutines.ParentJob");
        }
    }

    private final NodeList a(Incomplete incomplete) {
        NodeList list = incomplete.getList();
        if (list != null) {
            return list;
        }
        if (incomplete instanceof n) {
            return new NodeList();
        }
        if (incomplete instanceof JobNode) {
            a((JobNode) incomplete);
            return null;
        }
        throw new IllegalStateException(Intrinsics.stringPlus("State should have list: ", incomplete).toString());
    }

    private final boolean a(Incomplete incomplete, Throwable th) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(!(incomplete instanceof c))) {
                throw new AssertionError();
            }
        }
        if (!DebugKt.getASSERTIONS_ENABLED() || incomplete.isActive()) {
            NodeList a2 = a(incomplete);
            if (a2 == null) {
                return false;
            }
            if (!a.compareAndSet(this, incomplete, new c(a2, false, th))) {
                return false;
            }
            a(a2, th);
            return true;
        }
        throw new AssertionError();
    }

    private final Object a(Object obj, Object obj2) {
        Symbol symbol;
        Symbol symbol2;
        if (!(obj instanceof Incomplete)) {
            symbol2 = JobSupportKt.a;
            return symbol2;
        } else if ((!(obj instanceof n) && !(obj instanceof JobNode)) || (obj instanceof ChildHandleNode) || (obj2 instanceof CompletedExceptionally)) {
            return c((Incomplete) obj, obj2);
        } else {
            if (a((Incomplete) obj, obj2)) {
                return obj2;
            }
            symbol = JobSupportKt.b;
            return symbol;
        }
    }

    private final Object c(Incomplete incomplete, Object obj) {
        Symbol symbol;
        Symbol symbol2;
        Symbol symbol3;
        NodeList a2 = a(incomplete);
        if (a2 == null) {
            symbol3 = JobSupportKt.b;
            return symbol3;
        }
        Throwable th = null;
        c cVar = incomplete instanceof c ? (c) incomplete : null;
        if (cVar == null) {
            cVar = new c(a2, false, null);
        }
        synchronized (cVar) {
            if (cVar.a()) {
                symbol2 = JobSupportKt.a;
                return symbol2;
            }
            cVar.a(true);
            if (cVar == incomplete || a.compareAndSet(this, incomplete, cVar)) {
                if (DebugKt.getASSERTIONS_ENABLED() && !(!cVar.c())) {
                    throw new AssertionError();
                }
                boolean d2 = cVar.d();
                CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
                if (completedExceptionally != null) {
                    cVar.c(completedExceptionally.cause);
                }
                Throwable b2 = cVar.b();
                if (true ^ d2) {
                    th = b2;
                }
                Unit unit = Unit.INSTANCE;
                if (th != null) {
                    a(a2, th);
                }
                ChildHandleNode b3 = b(incomplete);
                if (b3 == null || !a(cVar, b3, obj)) {
                    return a(cVar, obj);
                }
                return JobSupportKt.COMPLETING_WAITING_CHILDREN;
            }
            symbol = JobSupportKt.b;
            return symbol;
        }
    }

    private final Throwable e(Object obj) {
        CompletedExceptionally completedExceptionally = obj instanceof CompletedExceptionally ? (CompletedExceptionally) obj : null;
        if (completedExceptionally == null) {
            return null;
        }
        return completedExceptionally.cause;
    }

    private final ChildHandleNode b(Incomplete incomplete) {
        ChildHandleNode childHandleNode = incomplete instanceof ChildHandleNode ? (ChildHandleNode) incomplete : null;
        if (childHandleNode != null) {
            return childHandleNode;
        }
        NodeList list = incomplete.getList();
        if (list == null) {
            return null;
        }
        return a((LockFreeLinkedListNode) list);
    }

    private final boolean a(c cVar, ChildHandleNode childHandleNode, Object obj) {
        while (Job.DefaultImpls.invokeOnCompletion$default(childHandleNode.childJob, false, false, new b(this, cVar, childHandleNode, obj), 1, null) == NonDisposableHandle.INSTANCE) {
            childHandleNode = a((LockFreeLinkedListNode) childHandleNode);
            if (childHandleNode == null) {
                return false;
            }
        }
        return true;
    }

    public final void b(c cVar, ChildHandleNode childHandleNode, Object obj) {
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(getState$kotlinx_coroutines_core() == cVar)) {
                throw new AssertionError();
            }
        }
        ChildHandleNode a2 = a((LockFreeLinkedListNode) childHandleNode);
        if (a2 == null || !a(cVar, a2, obj)) {
            afterCompletion(a(cVar, obj));
        }
    }

    private final ChildHandleNode a(LockFreeLinkedListNode lockFreeLinkedListNode) {
        while (lockFreeLinkedListNode.isRemoved()) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getPrevNode();
        }
        while (true) {
            lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode();
            if (!lockFreeLinkedListNode.isRemoved()) {
                if (lockFreeLinkedListNode instanceof ChildHandleNode) {
                    return (ChildHandleNode) lockFreeLinkedListNode;
                }
                if (lockFreeLinkedListNode instanceof NodeList) {
                    return null;
                }
            }
        }
    }

    /* compiled from: JobSupport.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u008a@"}, d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lkotlinx/coroutines/ChildJob;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.JobSupport$children$1", f = "JobSupport.kt", i = {1, 1}, l = {952, 954}, m = "invokeSuspend", n = {"this_$iv", "cur$iv"}, s = {"L$1", "L$2"})
    /* loaded from: classes5.dex */
    static final class d extends RestrictedSuspendLambda implements Function2<SequenceScope<? super ChildJob>, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(Continuation<? super d> continuation) {
            super(2, continuation);
            JobSupport.this = r1;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull SequenceScope<? super ChildJob> sequenceScope, @Nullable Continuation<? super Unit> continuation) {
            return ((d) create(sequenceScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            d dVar = new d(continuation);
            dVar.L$0 = obj;
            return dVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0068  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                r7 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r7.label
                switch(r1) {
                    case 0: goto L_0x0026;
                    case 1: goto L_0x0022;
                    case 2: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L_0x0011:
                java.lang.Object r1 = r7.L$2
                kotlinx.coroutines.internal.LockFreeLinkedListNode r1 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r1
                java.lang.Object r2 = r7.L$1
                kotlinx.coroutines.internal.LockFreeLinkedListHead r2 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r2
                java.lang.Object r3 = r7.L$0
                kotlin.sequences.SequenceScope r3 = (kotlin.sequences.SequenceScope) r3
                kotlin.ResultKt.throwOnFailure(r8)
                r8 = r7
                goto L_0x0081
            L_0x0022:
                kotlin.ResultKt.throwOnFailure(r8)
                goto L_0x0086
            L_0x0026:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlin.sequences.SequenceScope r8 = (kotlin.sequences.SequenceScope) r8
                kotlinx.coroutines.JobSupport r1 = kotlinx.coroutines.JobSupport.this
                java.lang.Object r1 = r1.getState$kotlinx_coroutines_core()
                boolean r2 = r1 instanceof kotlinx.coroutines.ChildHandleNode
                if (r2 == 0) goto L_0x0048
                kotlinx.coroutines.ChildHandleNode r1 = (kotlinx.coroutines.ChildHandleNode) r1
                kotlinx.coroutines.ChildJob r1 = r1.childJob
                r2 = r7
                kotlin.coroutines.Continuation r2 = (kotlin.coroutines.Continuation) r2
                r3 = 1
                r7.label = r3
                java.lang.Object r8 = r8.yield(r1, r2)
                if (r8 != r0) goto L_0x0086
                return r0
            L_0x0048:
                boolean r2 = r1 instanceof kotlinx.coroutines.Incomplete
                if (r2 == 0) goto L_0x0086
                kotlinx.coroutines.Incomplete r1 = (kotlinx.coroutines.Incomplete) r1
                kotlinx.coroutines.NodeList r1 = r1.getList()
                if (r1 != 0) goto L_0x0055
                goto L_0x0086
            L_0x0055:
                kotlinx.coroutines.internal.LockFreeLinkedListHead r1 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r1
                java.lang.Object r2 = r1.getNext()
                kotlinx.coroutines.internal.LockFreeLinkedListNode r2 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r2
                r3 = r8
                r8 = r7
                r6 = r2
                r2 = r1
                r1 = r6
            L_0x0062:
                boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
                if (r4 != 0) goto L_0x0086
                boolean r4 = r1 instanceof kotlinx.coroutines.ChildHandleNode
                if (r4 == 0) goto L_0x0081
                r4 = r1
                kotlinx.coroutines.ChildHandleNode r4 = (kotlinx.coroutines.ChildHandleNode) r4
                kotlinx.coroutines.ChildJob r4 = r4.childJob
                r8.L$0 = r3
                r8.L$1 = r2
                r8.L$2 = r1
                r5 = 2
                r8.label = r5
                java.lang.Object r4 = r3.yield(r4, r8)
                if (r4 != r0) goto L_0x0081
                return r0
            L_0x0081:
                kotlinx.coroutines.internal.LockFreeLinkedListNode r1 = r1.getNextNode()
                goto L_0x0062
            L_0x0086:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport.d.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final Sequence<Job> getChildren() {
        return SequencesKt.sequence(new d(null));
    }

    @Override // kotlinx.coroutines.Job
    @NotNull
    public final ChildHandle attachChild(@NotNull ChildJob childJob) {
        return (ChildHandle) Job.DefaultImpls.invokeOnCompletion$default(this, true, false, new ChildHandleNode(childJob), 2, null);
    }

    public void handleOnCompletionException$kotlinx_coroutines_core(@NotNull Throwable th) {
        throw th;
    }

    @NotNull
    public String toString() {
        return toDebugString() + '@' + DebugStringsKt.getHexAddress(this);
    }

    @InternalCoroutinesApi
    @NotNull
    public final String toDebugString() {
        return nameString$kotlinx_coroutines_core() + '{' + f(getState$kotlinx_coroutines_core()) + '}';
    }

    @NotNull
    public String nameString$kotlinx_coroutines_core() {
        return DebugStringsKt.getClassSimpleName(this);
    }

    private final String f(Object obj) {
        if (!(obj instanceof c)) {
            return obj instanceof Incomplete ? ((Incomplete) obj).isActive() ? "Active" : "New" : obj instanceof CompletedExceptionally ? "Cancelled" : "Completed";
        }
        c cVar = (c) obj;
        return cVar.d() ? "Cancelling" : cVar.a() ? "Completing" : "Active";
    }

    /* compiled from: JobSupport.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\b\u0002\u0018\u00002\u00060\u0018j\u0002`,2\u00020-B!\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fJ\u001f\u0010\u000f\u001a\u0012\u0012\u0004\u0012\u00020\u00050\rj\b\u0012\u0004\u0012\u00020\u0005`\u000eH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0005¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0016\u001a\u00020\u0015H\u0016¢\u0006\u0004\b\u0016\u0010\u0017R(\u0010\u001e\u001a\u0004\u0018\u00010\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u00188B@BX\u0082\u000e¢\u0006\f\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0016\u0010\u001f\u001a\u00020\u00038V@\u0016X\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0013\u0010!\u001a\u00020\u00038F@\u0006¢\u0006\u0006\u001a\u0004\b!\u0010 R$\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00038F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010 \"\u0004\b\"\u0010#R\u0013\u0010$\u001a\u00020\u00038F@\u0006¢\u0006\u0006\u001a\u0004\b$\u0010 R\u001c\u0010\u0002\u001a\u00020\u00018\u0016@\u0016X\u0096\u0004¢\u0006\f\n\u0004\b\u0002\u0010%\u001a\u0004\b&\u0010'R(\u0010\u0006\u001a\u0004\u0018\u00010\u00052\b\u0010\u0019\u001a\u0004\u0018\u00010\u00058F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b(\u0010)\"\u0004\b*\u0010\f¨\u0006+"}, d2 = {"Lkotlinx/coroutines/JobSupport$Finishing;", "Lkotlinx/coroutines/NodeList;", "list", "", "isCompleting", "", "rootCause", "<init>", "(Lkotlinx/coroutines/NodeList;ZLjava/lang/Throwable;)V", "exception", "", "addExceptionLocked", "(Ljava/lang/Throwable;)V", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "allocateList", "()Ljava/util/ArrayList;", "proposedException", "", "sealLocked", "(Ljava/lang/Throwable;)Ljava/util/List;", "", "toString", "()Ljava/lang/String;", "", com.xiaomi.onetrack.api.b.p, "getExceptionsHolder", "()Ljava/lang/Object;", "setExceptionsHolder", "(Ljava/lang/Object;)V", "exceptionsHolder", "isActive", "()Z", "isCancelling", "setCompleting", "(Z)V", "isSealed", "Lkotlinx/coroutines/NodeList;", "getList", "()Lkotlinx/coroutines/NodeList;", "getRootCause", "()Ljava/lang/Throwable;", "setRootCause", "kotlinx-coroutines-core", "Lkotlinx/coroutines/internal/SynchronizedObject;", "Lkotlinx/coroutines/Incomplete;"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class c implements Incomplete {
        @NotNull
        private volatile /* synthetic */ Object _exceptionsHolder = null;
        @NotNull
        private volatile /* synthetic */ int _isCompleting;
        @NotNull
        private volatile /* synthetic */ Object _rootCause;
        @NotNull
        private final NodeList a;

        @Override // kotlinx.coroutines.Incomplete
        @NotNull
        public NodeList getList() {
            return this.a;
        }

        public c(@NotNull NodeList nodeList, boolean z, @Nullable Throwable th) {
            this.a = nodeList;
            this._isCompleting = z ? 1 : 0;
            this._rootCause = th;
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [int, boolean] */
        public final boolean a() {
            return this._isCompleting;
        }

        public final void a(boolean z) {
            this._isCompleting = z ? 1 : 0;
        }

        @Nullable
        public final Throwable b() {
            return (Throwable) this._rootCause;
        }

        public final void a(@Nullable Throwable th) {
            this._rootCause = th;
        }

        private final Object e() {
            return this._exceptionsHolder;
        }

        private final void a(Object obj) {
            this._exceptionsHolder = obj;
        }

        public final boolean c() {
            Symbol symbol;
            Object e = e();
            symbol = JobSupportKt.d;
            return e == symbol;
        }

        public final boolean d() {
            return b() != null;
        }

        @Override // kotlinx.coroutines.Incomplete
        public boolean isActive() {
            return b() == null;
        }

        @NotNull
        public final List<Throwable> b(@Nullable Throwable th) {
            ArrayList<Throwable> arrayList;
            Symbol symbol;
            Object e = e();
            if (e == null) {
                arrayList = f();
            } else if (e instanceof Throwable) {
                ArrayList<Throwable> f = f();
                f.add(e);
                arrayList = f;
            } else if (e instanceof ArrayList) {
                arrayList = (ArrayList) e;
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("State is ", e).toString());
            }
            Throwable b = b();
            if (b != null) {
                arrayList.add(0, b);
            }
            if (th != null && !Intrinsics.areEqual(th, b)) {
                arrayList.add(th);
            }
            symbol = JobSupportKt.d;
            a(symbol);
            return arrayList;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void c(@NotNull Throwable th) {
            Throwable b = b();
            if (b == null) {
                a(th);
            } else if (th != b) {
                Object e = e();
                if (e == null) {
                    a((Object) th);
                } else if (e instanceof Throwable) {
                    if (th != e) {
                        ArrayList<Throwable> f = f();
                        f.add(e);
                        f.add(th);
                        Unit unit = Unit.INSTANCE;
                        a(f);
                    }
                } else if (e instanceof ArrayList) {
                    ((ArrayList) e).add(th);
                } else {
                    throw new IllegalStateException(Intrinsics.stringPlus("State is ", e).toString());
                }
            }
        }

        private final ArrayList<Throwable> f() {
            return new ArrayList<>(4);
        }

        @NotNull
        public String toString() {
            return "Finishing[cancelling=" + d() + ", completing=" + a() + ", rootCause=" + b() + ", exceptions=" + e() + ", list=" + getList() + ']';
        }
    }

    /* compiled from: JobSupport.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\b\u0002\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lkotlinx/coroutines/JobSupport$ChildCompletion;", "Lkotlinx/coroutines/JobNode;", "parent", "Lkotlinx/coroutines/JobSupport;", XiaomiOAuthConstants.EXTRA_STATE_2, "Lkotlinx/coroutines/JobSupport$Finishing;", VideoDataManager.ORIGIN_CHILD, "Lkotlinx/coroutines/ChildHandleNode;", "proposedUpdate", "", "(Lkotlinx/coroutines/JobSupport;Lkotlinx/coroutines/JobSupport$Finishing;Lkotlinx/coroutines/ChildHandleNode;Ljava/lang/Object;)V", "invoke", "", "cause", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b extends JobNode {
        @NotNull
        private final JobSupport a;
        @NotNull
        private final c b;
        @NotNull
        private final ChildHandleNode c;
        @Nullable
        private final Object d;

        @Override // kotlin.jvm.functions.Function1
        public /* bridge */ /* synthetic */ Unit invoke(Throwable th) {
            invoke2(th);
            return Unit.INSTANCE;
        }

        public b(@NotNull JobSupport jobSupport, @NotNull c cVar, @NotNull ChildHandleNode childHandleNode, @Nullable Object obj) {
            this.a = jobSupport;
            this.b = cVar;
            this.c = childHandleNode;
            this.d = obj;
        }

        @Override // kotlinx.coroutines.CompletionHandlerBase
        /* renamed from: invoke */
        public void invoke2(@Nullable Throwable th) {
            this.a.b(this.b, this.c, this.d);
        }
    }

    /* compiled from: JobSupport.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0014R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lkotlinx/coroutines/JobSupport$AwaitContinuation;", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlinx/coroutines/CancellableContinuationImpl;", "delegate", "Lkotlin/coroutines/Continuation;", "job", "Lkotlinx/coroutines/JobSupport;", "(Lkotlin/coroutines/Continuation;Lkotlinx/coroutines/JobSupport;)V", "getContinuationCancellationCause", "", "parent", "Lkotlinx/coroutines/Job;", "nameString", "", "kotlinx-coroutines-core"}, k = 1, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class a<T> extends CancellableContinuationImpl<T> {
        @NotNull
        private final JobSupport a;

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        @NotNull
        protected String nameString() {
            return "AwaitContinuation";
        }

        public a(@NotNull Continuation<? super T> continuation, @NotNull JobSupport jobSupport) {
            super(continuation, 1);
            this.a = jobSupport;
        }

        @Override // kotlinx.coroutines.CancellableContinuationImpl
        @NotNull
        public Throwable getContinuationCancellationCause(@NotNull Job job) {
            Throwable b;
            Object state$kotlinx_coroutines_core = this.a.getState$kotlinx_coroutines_core();
            return (!(state$kotlinx_coroutines_core instanceof c) || (b = ((c) state$kotlinx_coroutines_core).b()) == null) ? state$kotlinx_coroutines_core instanceof CompletedExceptionally ? ((CompletedExceptionally) state$kotlinx_coroutines_core).cause : job.getCancellationException() : b;
        }
    }

    public final boolean isCompletedExceptionally() {
        return getState$kotlinx_coroutines_core() instanceof CompletedExceptionally;
    }

    @Nullable
    public final Throwable getCompletionExceptionOrNull() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
            return e(state$kotlinx_coroutines_core);
        }
        throw new IllegalStateException("This job has not completed yet".toString());
    }

    @Nullable
    public final Object getCompletedInternal$kotlinx_coroutines_core() {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (!(!(state$kotlinx_coroutines_core instanceof Incomplete))) {
            throw new IllegalStateException("This job has not completed yet".toString());
        } else if (!(state$kotlinx_coroutines_core instanceof CompletedExceptionally)) {
            return JobSupportKt.unboxState(state$kotlinx_coroutines_core);
        } else {
            throw ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
        }
    }

    @Nullable
    public final Object awaitInternal$kotlinx_coroutines_core(@NotNull Continuation<Object> continuation) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                if (!(state$kotlinx_coroutines_core instanceof CompletedExceptionally)) {
                    return JobSupportKt.unboxState(state$kotlinx_coroutines_core);
                }
                Throwable th = ((CompletedExceptionally) state$kotlinx_coroutines_core).cause;
                if (!DebugKt.getRECOVER_STACK_TRACES()) {
                    throw th;
                } else if (!(continuation instanceof CoroutineStackFrame)) {
                    throw th;
                } else {
                    throw StackTraceRecoveryKt.a(th, (CoroutineStackFrame) continuation);
                }
            }
        } while (a(state$kotlinx_coroutines_core) < 0);
        return b(continuation);
    }

    public final Object b(Continuation<Object> continuation) {
        a aVar = new a(IntrinsicsKt.intercepted(continuation), this);
        aVar.initCancellability();
        CancellableContinuationKt.disposeOnCancellation(aVar, invokeOnCompletion(new y(aVar)));
        Object result = aVar.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result;
    }

    public final <T, R> void selectAwaitCompletion$kotlinx_coroutines_core(@NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super T, ? super Continuation<? super R>, ? extends Object> function2) {
        Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
        if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
            selectInstance.resumeSelectWithException(((CompletedExceptionally) state$kotlinx_coroutines_core).cause);
        } else {
            CancellableKt.startCoroutineCancellable$default(function2, JobSupportKt.unboxState(state$kotlinx_coroutines_core), selectInstance.getCompletion(), null, 4, null);
        }
    }

    @Nullable
    public final Object getState$kotlinx_coroutines_core() {
        while (true) {
            Object obj = this._state;
            if (!(obj instanceof OpDescriptor)) {
                return obj;
            }
            ((OpDescriptor) obj).perform(this);
        }
    }

    private final void b(NodeList nodeList, Throwable th) {
        NodeList nodeList2 = nodeList;
        CompletionHandlerException completionHandlerException = null;
        for (LockFreeLinkedListNode lockFreeLinkedListNode = (LockFreeLinkedListNode) nodeList2.getNext(); !Intrinsics.areEqual(lockFreeLinkedListNode, nodeList2); lockFreeLinkedListNode = lockFreeLinkedListNode.getNextNode()) {
            if (lockFreeLinkedListNode instanceof JobNode) {
                JobNode jobNode = (JobNode) lockFreeLinkedListNode;
                try {
                    jobNode.invoke(th);
                } catch (Throwable th2) {
                    CompletionHandlerException completionHandlerException2 = completionHandlerException;
                    if (completionHandlerException2 == null) {
                        completionHandlerException2 = null;
                    } else {
                        ExceptionsKt.addSuppressed(completionHandlerException2, th2);
                    }
                    if (completionHandlerException2 == null) {
                        completionHandlerException = new CompletionHandlerException("Exception in completion handler " + jobNode + " for " + this, th2);
                    }
                }
            }
        }
        CompletionHandlerException completionHandlerException3 = completionHandlerException;
        if (completionHandlerException3 != null) {
            handleOnCompletionException$kotlinx_coroutines_core(completionHandlerException3);
        }
    }

    @Override // kotlinx.coroutines.Job
    public final boolean start() {
        while (true) {
            switch (a(getState$kotlinx_coroutines_core())) {
                case 0:
                    return false;
                case 1:
                    return true;
            }
        }
    }

    private final boolean a() {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                return false;
            }
        } while (a(state$kotlinx_coroutines_core) < 0);
        return true;
    }

    public final Object a(Continuation<? super Unit> continuation) {
        CancellableContinuationImpl cancellableContinuationImpl = new CancellableContinuationImpl(IntrinsicsKt.intercepted(continuation), 1);
        cancellableContinuationImpl.initCancellability();
        CancellableContinuationImpl cancellableContinuationImpl2 = cancellableContinuationImpl;
        CancellableContinuationKt.disposeOnCancellation(cancellableContinuationImpl2, invokeOnCompletion(new z(cancellableContinuationImpl2)));
        Object result = cancellableContinuationImpl.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return result == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? result : Unit.INSTANCE;
    }

    @Override // kotlinx.coroutines.selects.SelectClause0
    public final <R> void registerSelectClause0(@NotNull SelectInstance<? super R> selectInstance, @NotNull Function1<? super Continuation<? super R>, ? extends Object> function1) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!selectInstance.isSelected()) {
                if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                    if (selectInstance.trySelect()) {
                        UndispatchedKt.startCoroutineUnintercepted(function1, selectInstance.getCompletion());
                        return;
                    }
                    return;
                }
            } else {
                return;
            }
        } while (a(state$kotlinx_coroutines_core) != 0);
        selectInstance.disposeOnSelect(invokeOnCompletion(new ac(selectInstance, function1)));
    }

    public final void removeNode$kotlinx_coroutines_core(@NotNull JobNode jobNode) {
        Object state$kotlinx_coroutines_core;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater;
        n nVar;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (state$kotlinx_coroutines_core instanceof JobNode) {
                if (state$kotlinx_coroutines_core == jobNode) {
                    atomicReferenceFieldUpdater = a;
                    nVar = JobSupportKt.f;
                } else {
                    return;
                }
            } else if ((state$kotlinx_coroutines_core instanceof Incomplete) && ((Incomplete) state$kotlinx_coroutines_core).getList() != null) {
                jobNode.remove();
                return;
            } else {
                return;
            }
        } while (!atomicReferenceFieldUpdater.compareAndSet(this, state$kotlinx_coroutines_core, nVar));
    }

    @Override // kotlinx.coroutines.Job
    public void cancel(@Nullable CancellationException cancellationException) {
        if (cancellationException == null) {
            cancellationException = new JobCancellationException(cancellationExceptionMessage(), null, this);
        }
        cancelInternal(cancellationException);
    }

    @Override // kotlinx.coroutines.Job
    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Added since 1.2.0 for binary compatibility with versions <= 1.1.x")
    public /* synthetic */ boolean cancel(Throwable th) {
        JobCancellationException jobCancellationException;
        if (th == null) {
            jobCancellationException = new JobCancellationException(cancellationExceptionMessage(), null, this);
        } else {
            jobCancellationException = toCancellationException$default(this, th, null, 1, null);
        }
        cancelInternal(jobCancellationException);
        return true;
    }

    private final Object b(Object obj) {
        Symbol symbol;
        Object a2;
        Symbol symbol2;
        do {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!(state$kotlinx_coroutines_core instanceof Incomplete) || ((state$kotlinx_coroutines_core instanceof c) && ((c) state$kotlinx_coroutines_core).a())) {
                symbol = JobSupportKt.a;
                return symbol;
            }
            a2 = a(state$kotlinx_coroutines_core, new CompletedExceptionally(c(obj), false, 2, null));
            symbol2 = JobSupportKt.b;
        } while (a2 == symbol2);
        return a2;
    }

    private final Object d(Object obj) {
        Symbol symbol;
        Symbol symbol2;
        Symbol symbol3;
        Symbol symbol4;
        Symbol symbol5;
        Symbol symbol6;
        Throwable th = null;
        while (true) {
            Object state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (state$kotlinx_coroutines_core instanceof c) {
                synchronized (state$kotlinx_coroutines_core) {
                    if (((c) state$kotlinx_coroutines_core).c()) {
                        symbol2 = JobSupportKt.c;
                        return symbol2;
                    }
                    boolean d2 = ((c) state$kotlinx_coroutines_core).d();
                    if (obj != null || !d2) {
                        if (th == null) {
                            th = c(obj);
                        }
                        ((c) state$kotlinx_coroutines_core).c(th);
                    }
                    Throwable b2 = ((c) state$kotlinx_coroutines_core).b();
                    if (!(!d2)) {
                        b2 = null;
                    }
                    if (b2 != null) {
                        a(((c) state$kotlinx_coroutines_core).getList(), b2);
                    }
                    symbol = JobSupportKt.a;
                    return symbol;
                }
            } else if (state$kotlinx_coroutines_core instanceof Incomplete) {
                if (th == null) {
                    th = c(obj);
                }
                Incomplete incomplete = (Incomplete) state$kotlinx_coroutines_core;
                if (!incomplete.isActive()) {
                    Object a2 = a(state$kotlinx_coroutines_core, new CompletedExceptionally(th, false, 2, null));
                    symbol5 = JobSupportKt.a;
                    if (a2 != symbol5) {
                        symbol6 = JobSupportKt.b;
                        if (a2 != symbol6) {
                            return a2;
                        }
                    } else {
                        throw new IllegalStateException(Intrinsics.stringPlus("Cannot happen in ", state$kotlinx_coroutines_core).toString());
                    }
                } else if (a(incomplete, th)) {
                    symbol4 = JobSupportKt.a;
                    return symbol4;
                }
            } else {
                symbol3 = JobSupportKt.c;
                return symbol3;
            }
        }
    }

    public final boolean makeCompleting$kotlinx_coroutines_core(@Nullable Object obj) {
        Object a2;
        Symbol symbol;
        Symbol symbol2;
        do {
            a2 = a(getState$kotlinx_coroutines_core(), obj);
            symbol = JobSupportKt.a;
            if (a2 == symbol) {
                return false;
            }
            if (a2 == JobSupportKt.COMPLETING_WAITING_CHILDREN) {
                return true;
            }
            symbol2 = JobSupportKt.b;
        } while (a2 == symbol2);
        afterCompletion(a2);
        return true;
    }

    @Nullable
    public final Object makeCompletingOnce$kotlinx_coroutines_core(@Nullable Object obj) {
        Object a2;
        Symbol symbol;
        Symbol symbol2;
        do {
            a2 = a(getState$kotlinx_coroutines_core(), obj);
            symbol = JobSupportKt.a;
            if (a2 != symbol) {
                symbol2 = JobSupportKt.b;
            } else {
                throw new IllegalStateException("Job " + this + " is already complete or completing, but is being completed with " + obj, e(obj));
            }
        } while (a2 == symbol2);
        return a2;
    }

    public final <T, R> void registerSelectClause1Internal$kotlinx_coroutines_core(@NotNull SelectInstance<? super R> selectInstance, @NotNull Function2<? super T, ? super Continuation<? super R>, ? extends Object> function2) {
        Object state$kotlinx_coroutines_core;
        do {
            state$kotlinx_coroutines_core = getState$kotlinx_coroutines_core();
            if (!selectInstance.isSelected()) {
                if (!(state$kotlinx_coroutines_core instanceof Incomplete)) {
                    if (!selectInstance.trySelect()) {
                        return;
                    }
                    if (state$kotlinx_coroutines_core instanceof CompletedExceptionally) {
                        selectInstance.resumeSelectWithException(((CompletedExceptionally) state$kotlinx_coroutines_core).cause);
                        return;
                    } else {
                        UndispatchedKt.startCoroutineUnintercepted(function2, JobSupportKt.unboxState(state$kotlinx_coroutines_core), selectInstance.getCompletion());
                        return;
                    }
                }
            } else {
                return;
            }
        } while (a(state$kotlinx_coroutines_core) != 0);
        selectInstance.disposeOnSelect(invokeOnCompletion(new ab(selectInstance, function2)));
    }
}
