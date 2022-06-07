package kotlinx.coroutines.channels;

import androidx.exifinterface.media.ExifInterface;
import com.alibaba.fastjson.asm.Opcodes;
import com.xiaomi.mi_connect_sdk.BuildConfig;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import com.xiaomi.micolauncher.skills.music.controller.LoopType;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Lambda;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import org.apache.commons.codec.language.bm.Languages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Deprecated.kt */
@Metadata(d1 = {"\u0000 \u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u001f\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0002\n\u0002\u0010!\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u001aJ\u0010\u0000\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\u00072\u001a\u0010\b\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\n0\t\"\u0006\u0012\u0002\b\u00030\nH\u0001¢\u0006\u0002\u0010\u000b\u001a!\u0010\f\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a1\u0010\u0010\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\u0007*\u0006\u0012\u0002\b\u00030\nH\u0001\u001a!\u0010\u0011\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001e\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0007\u001aZ\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u0010\u0015*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010\u0018\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00150\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a0\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001aT\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u0010!\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\"\u001a\u00020\u0012H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010#\u001a+\u0010$\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\"\u001a\u00020\u0012H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010#\u001aT\u0010%\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ai\u0010&\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001727\u0010 \u001a3\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u001aT\u0010)\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a$\u0010*\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\b\b\u0000\u0010\u000e*\u00020\u001b*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\nH\u0001\u001aA\u0010+\u001a\u0002H,\"\b\b\u0000\u0010\u000e*\u00020\u001b\"\u0010\b\u0001\u0010,*\n\u0012\u0006\b\u0000\u0012\u0002H\u000e0-*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0087@ø\u0001\u0000¢\u0006\u0002\u0010/\u001a?\u0010+\u001a\u0002H,\"\b\b\u0000\u0010\u000e*\u00020\u001b\"\u000e\b\u0001\u0010,*\b\u0012\u0004\u0012\u0002H\u000e00*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0087@ø\u0001\u0000¢\u0006\u0002\u00101\u001a!\u00102\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a#\u00103\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a`\u00104\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172(\u00106\u001a$\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\n0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u00107\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u00108\u001a\u0002H\u000eH\u0087@ø\u0001\u0000¢\u0006\u0002\u00109\u001a!\u0010:\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a)\u0010;\u001a\u00020\u0012\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u00108\u001a\u0002H\u000eH\u0087@ø\u0001\u0000¢\u0006\u0002\u00109\u001a#\u0010<\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001aZ\u0010=\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u00106\u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0001ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001ao\u0010>\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001727\u00106\u001a3\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0001ø\u0001\u0000¢\u0006\u0002\u0010(\u001au\u0010?\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\b\b\u0001\u00105*\u00020\u001b*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001729\u00106\u001a5\b\u0001\u0012\u0013\u0012\u00110\u0012¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\"\u0012\u0004\u0012\u0002H\u000e\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0'H\u0007ø\u0001\u0000¢\u0006\u0002\u0010(\u001a`\u0010@\u001a\b\u0012\u0004\u0012\u0002H50\n\"\u0004\b\u0000\u0010\u000e\"\b\b\u0001\u00105*\u00020\u001b*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172$\u00106\u001a \b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H50\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a?\u0010A\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u001a\u0010B\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0Cj\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`DH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010E\u001a?\u0010F\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u001a\u0010B\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u000e0Cj\n\u0012\u0006\b\u0000\u0012\u0002H\u000e`DH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010E\u001a!\u0010G\u001a\u00020\r\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a$\u0010H\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\b\b\u0000\u0010\u000e*\u00020\u001b*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u000e0\nH\u0007\u001a!\u0010I\u001a\u0002H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a#\u0010J\u001a\u0004\u0018\u0001H\u000e\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a0\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010\u001e\u001a\u00020\u00122\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001aT\u0010L\u001a\b\u0012\u0004\u0012\u0002H\u000e0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u00172\"\u0010 \u001a\u001e\b\u0001\u0012\u0004\u0012\u0002H\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\r0\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u001b0\u0019H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a9\u0010M\u001a\u0002H,\"\u0004\b\u0000\u0010\u000e\"\u000e\b\u0001\u0010,*\b\u0012\u0004\u0012\u0002H\u000e00*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0081@ø\u0001\u0000¢\u0006\u0002\u00101\u001a;\u0010N\u001a\u0002H,\"\u0004\b\u0000\u0010\u000e\"\u0010\b\u0001\u0010,*\n\u0012\u0006\b\u0000\u0012\u0002H\u000e0-*\b\u0012\u0004\u0012\u0002H\u000e0\n2\u0006\u0010.\u001a\u0002H,H\u0081@ø\u0001\u0000¢\u0006\u0002\u0010/\u001a?\u0010O\u001a\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0P\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010Q*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0R0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001aU\u0010O\u001a\u0002HS\"\u0004\b\u0000\u0010\u0015\"\u0004\b\u0001\u0010Q\"\u0018\b\u0002\u0010S*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0015\u0012\u0006\b\u0000\u0012\u0002HQ0T*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0015\u0012\u0004\u0012\u0002HQ0R0\n2\u0006\u0010.\u001a\u0002HSH\u0081@ø\u0001\u0000¢\u0006\u0002\u0010U\u001a'\u0010V\u001a\b\u0012\u0004\u0012\u0002H\u000e0W\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a'\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u000e0Y\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0081@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a'\u0010Z\u001a\b\u0012\u0004\u0012\u0002H\u000e0[\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\nH\u0087@ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a.\u0010\\\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u000e0]0\n\"\u0004\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\n2\b\b\u0002\u0010\u0016\u001a\u00020\u0017H\u0007\u001a?\u0010^\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u000e\u0012\u0004\u0012\u0002H50R0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105*\b\u0012\u0004\u0012\u0002H\u000e0\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u0002H50\nH\u0087\u0004\u001az\u0010^\u001a\b\u0012\u0004\u0012\u0002HQ0\n\"\u0004\b\u0000\u0010\u000e\"\u0004\b\u0001\u00105\"\u0004\b\u0002\u0010Q*\b\u0012\u0004\u0012\u0002H\u000e0\n2\f\u0010_\u001a\b\u0012\u0004\u0012\u0002H50\n2\b\b\u0002\u0010\u0016\u001a\u00020\u001726\u00106\u001a2\u0012\u0013\u0012\u0011H\u000e¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(`\u0012\u0013\u0012\u0011H5¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(a\u0012\u0004\u0012\u0002HQ0\u0019H\u0001\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006b"}, d2 = {"consumesAll", "Lkotlin/Function1;", "", "Lkotlin/ParameterName;", "name", "cause", "", "Lkotlinx/coroutines/CompletionHandler;", "channels", "", "Lkotlinx/coroutines/channels/ReceiveChannel;", "([Lkotlinx/coroutines/channels/ReceiveChannel;)Lkotlin/jvm/functions/Function1;", Languages.ANY, "", "E", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "consumes", "count", "", "distinct", "distinctBy", "K", com.umeng.analytics.pro.c.R, "Lkotlin/coroutines/CoroutineContext;", "selector", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function2;)Lkotlinx/coroutines/channels/ReceiveChannel;", "drop", "n", "dropWhile", "predicate", "elementAt", MiSoundBoxCommandExtras.INDEX, "(Lkotlinx/coroutines/channels/ReceiveChannel;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "elementAtOrNull", ChildVideo.ItemsBean.TYPE_FILTER, "filterIndexed", "Lkotlin/Function3;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlin/coroutines/CoroutineContext;Lkotlin/jvm/functions/Function3;)Lkotlinx/coroutines/channels/ReceiveChannel;", "filterNot", "filterNotNull", "filterNotNullTo", HomePageRecordHelper.AREA_C, "", RtspHeaders.Values.DESTINATION, "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/SendChannel;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Lkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "first", "firstOrNull", "flatMap", "R", "transform", "indexOf", "element", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "last", "lastIndexOf", "lastOrNull", "map", "mapIndexed", "mapIndexedNotNull", "mapNotNull", "maxWith", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Comparator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "minWith", "none", "requireNoNulls", LoopType.SINGLE, "singleOrNull", "take", "takeWhile", "toChannel", "toCollection", "toMap", "", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlin/Pair;", "M", "", "(Lkotlinx/coroutines/channels/ReceiveChannel;Ljava/util/Map;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "toMutableList", "", "toMutableSet", "", "toSet", "", "withIndex", "Lkotlin/collections/IndexedValue;", "zip", "other", com.umeng.analytics.pro.ai.at, "b", "kotlinx-coroutines-core"}, k = 5, mv = {1, 5, 1}, xi = 48, xs = "kotlinx/coroutines/channels/ChannelsKt")
/* loaded from: classes5.dex */
public final /* synthetic */ class e {

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {}, l = {342}, m = Languages.ANY, n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class a<E> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        a(Continuation<? super a> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object k;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            k = e.k(null, this);
            return k;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {369, 371}, m = "minWith", n = {"comparator", "iterator", "iterator", "min"}, s = {"L$0", "L$2", "L$2", "L$3"})
    /* loaded from: classes5.dex */
    public static final class aa<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        aa(Continuation<? super aa> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object b;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            b = e.b((ReceiveChannel) null, (Comparator) null, (Continuation) this);
            return b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {}, l = {381}, m = "none", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class ab<E> extends ContinuationImpl {
        Object L$0;
        int label;
        /* synthetic */ Object result;

        ab(Continuation<? super ab> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object m;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            m = e.m(null, this);
            return m;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 1}, l = {125, 128}, m = LoopType.SINGLE, n = {"iterator", LoopType.SINGLE}, s = {"L$1", "L$1"})
    /* loaded from: classes5.dex */
    public static final class ad<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        ad(Continuation<? super ad> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object e;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            e = e.e(null, this);
            return e;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 1}, l = {137, 140}, m = "singleOrNull", n = {"iterator", LoopType.SINGLE}, s = {"L$1", "L$1"})
    /* loaded from: classes5.dex */
    public static final class ae<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        ae(Continuation<? super ae> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object f;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            f = e.f(null, this);
            return f;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {415, 243}, m = "toChannel", n = {RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv", RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class ah<E, C extends SendChannel<? super E>> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        ah(Continuation<? super ah> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toChannel(null, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {415}, m = "toCollection", n = {RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class ai<E, C extends Collection<? super E>> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        ai(Continuation<? super ai> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toCollection(null, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {415}, m = "toMap", n = {RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class aj<K, V, M extends Map<? super K, ? super V>> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        aj(Continuation<? super aj> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ChannelsKt.toMap(null, null, this);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {415}, m = "count", n = {"count", "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class d<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        d(Continuation<? super d> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object l;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            l = e.l(null, this);
            return l;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0}, l = {36}, m = "elementAt", n = {"count"}, s = {"I$1"})
    /* loaded from: classes5.dex */
    public static final class i<E> extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        i(Continuation<? super i> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object a;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            a = e.a((ReceiveChannel) null, 0, this);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0}, l = {49}, m = "elementAtOrNull", n = {"count"}, s = {"I$1"})
    /* loaded from: classes5.dex */
    public static final class j<E> extends ContinuationImpl {
        int I$0;
        int I$1;
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        j(Continuation<? super j> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object b;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            b = e.b((ReceiveChannel) null, 0, this);
            return b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {415}, m = "filterNotNullTo", n = {RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv"}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class o<E, C extends Collection<? super E>> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        o(Continuation<? super o> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object a;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            a = e.a((ReceiveChannel) null, (Collection) null, (Continuation) this);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {415, 212}, m = "filterNotNullTo", n = {RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv", RtspHeaders.Values.DESTINATION, "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class p<E, C extends SendChannel<? super E>> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        p(Continuation<? super p> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object a;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            a = e.a((ReceiveChannel) null, (SendChannel) null, (Continuation) this);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0}, l = {60}, m = "first", n = {"iterator"}, s = {"L$1"})
    /* loaded from: classes5.dex */
    public static final class q<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        q(Continuation<? super q> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object a;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            a = e.a((ReceiveChannel) null, this);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0}, l = {69}, m = "firstOrNull", n = {"iterator"}, s = {"L$1"})
    /* loaded from: classes5.dex */
    public static final class r<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        r(Continuation<? super r> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object b;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            b = e.b(null, this);
            return b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0}, l = {415}, m = "indexOf", n = {"element", MiSoundBoxCommandExtras.INDEX}, s = {"L$0", "L$1"})
    /* loaded from: classes5.dex */
    public static final class t<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        t(Continuation<? super t> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object a;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            a = e.a((ReceiveChannel) null, (Object) null, this);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 1}, l = {89, 92}, m = "last", n = {"iterator", "last"}, s = {"L$1", "L$2"})
    /* loaded from: classes5.dex */
    public static final class u<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        u(Continuation<? super u> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object c;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            c = e.c(null, this);
            return c;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 0, 0}, l = {415}, m = "lastIndexOf", n = {"element", "lastIndex", MiSoundBoxCommandExtras.INDEX, "$this$consume$iv$iv"}, s = {"L$0", "L$1", "L$2", "L$3"})
    /* loaded from: classes5.dex */
    public static final class v<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;
        /* synthetic */ Object result;

        v(Continuation<? super v> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object b;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            b = e.b((ReceiveChannel) null, (Object) null, this);
            return b;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 1, 1}, l = {113, 116}, m = "lastOrNull", n = {"iterator", "iterator", "last"}, s = {"L$1", "L$1", "L$2"})
    /* loaded from: classes5.dex */
    public static final class w<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        w(Continuation<? super w> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object d;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            d = e.d(null, this);
            return d;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt", f = "Deprecated.kt", i = {0, 0, 1, 1}, l = {356, 358}, m = "maxWith", n = {"comparator", "iterator", "iterator", "max"}, s = {"L$0", "L$2", "L$2", "L$3"})
    /* loaded from: classes5.dex */
    public static final class z<E> extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;
        /* synthetic */ Object result;

        z(Continuation<? super z> continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object a;
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            a = e.a((ReceiveChannel) null, (Comparator) null, (Continuation) this);
            return a;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n"}, d2 = {"<anonymous>", "", "cause", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class c extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ ReceiveChannel<?>[] $channels;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(ReceiveChannel<?>[] receiveChannelArr) {
            super(1);
            this.$channels = receiveChannelArr;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }

        public final void a(@Nullable Throwable th) {
            ReceiveChannel<?>[] receiveChannelArr = this.$channels;
            int length = receiveChannelArr.length;
            Throwable th2 = null;
            int i = 0;
            while (i < length) {
                ReceiveChannel<?> receiveChannel = receiveChannelArr[i];
                i++;
                try {
                    ChannelsKt.cancelConsumed(receiveChannel, th);
                } catch (Throwable th3) {
                    if (th2 == null) {
                        th2 = th3;
                    } else {
                        ExceptionsKt.addSuppressed(th2, th3);
                    }
                }
            }
            if (th2 != null) {
                throw th2;
            }
        }
    }

    @PublishedApi
    @NotNull
    public static final Function1<Throwable, Unit> a(@NotNull ReceiveChannel<?>... receiveChannelArr) {
        return new c(receiveChannelArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0064 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0071 A[Catch: Throwable -> 0x0044, all -> 0x003f, TRY_LEAVE, TryCatch #4 {Throwable -> 0x0044, all -> 0x003f, blocks: (B:13:0x003b, B:26:0x0069, B:28:0x0071, B:34:0x0080, B:35:0x0099), top: B:45:0x003b }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0080 A[Catch: Throwable -> 0x0044, all -> 0x003f, TRY_ENTER, TryCatch #4 {Throwable -> 0x0044, all -> 0x003f, blocks: (B:13:0x003b, B:26:0x0069, B:28:0x0071, B:34:0x0080, B:35:0x0099), top: B:45:0x003b }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object a(kotlinx.coroutines.channels.ReceiveChannel r8, int r9, kotlin.coroutines.Continuation r10) {
        /*
            boolean r0 = r10 instanceof kotlinx.coroutines.channels.e.i
            if (r0 == 0) goto L_0x0014
            r0 = r10
            kotlinx.coroutines.channels.e$i r0 = (kotlinx.coroutines.channels.e.i) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r10 = r0.label
            int r10 = r10 - r2
            r0.label = r10
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$i r0 = new kotlinx.coroutines.channels.e$i
            r0.<init>(r10)
        L_0x0019:
            java.lang.Object r10 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 46
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0049;
                case 1: goto L_0x002f;
                default: goto L_0x0027;
            }
        L_0x0027:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L_0x002f:
            int r8 = r0.I$1
            int r9 = r0.I$0
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r10)     // Catch: Throwable -> 0x0044, all -> 0x003f
            goto L_0x0069
        L_0x003f:
            r8 = move-exception
            r9 = r8
            r8 = r5
            goto L_0x00ba
        L_0x0044:
            r8 = move-exception
            r4 = r8
            r8 = r5
            goto L_0x00b9
        L_0x0049:
            kotlin.ResultKt.throwOnFailure(r10)
            if (r9 < 0) goto L_0x009f
            r10 = 0
            kotlinx.coroutines.channels.ChannelIterator r2 = r8.iterator()     // Catch: Throwable -> 0x009c, all -> 0x009a
        L_0x0053:
            r0.L$0 = r8     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.L$1 = r2     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.I$0 = r9     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.I$1 = r10     // Catch: Throwable -> 0x009c, all -> 0x009a
            r5 = 1
            r0.label = r5     // Catch: Throwable -> 0x009c, all -> 0x009a
            java.lang.Object r5 = r2.hasNext(r0)     // Catch: Throwable -> 0x009c, all -> 0x009a
            if (r5 != r1) goto L_0x0065
            return r1
        L_0x0065:
            r7 = r5
            r5 = r8
            r8 = r10
            r10 = r7
        L_0x0069:
            java.lang.Boolean r10 = (java.lang.Boolean) r10     // Catch: Throwable -> 0x0044, all -> 0x003f
            boolean r10 = r10.booleanValue()     // Catch: Throwable -> 0x0044, all -> 0x003f
            if (r10 == 0) goto L_0x0080
            java.lang.Object r10 = r2.next()     // Catch: Throwable -> 0x0044, all -> 0x003f
            int r6 = r8 + 1
            if (r9 != r8) goto L_0x007d
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r4)
            return r10
        L_0x007d:
            r8 = r5
            r10 = r6
            goto L_0x0053
        L_0x0080:
            java.lang.IndexOutOfBoundsException r8 = new java.lang.IndexOutOfBoundsException     // Catch: Throwable -> 0x0044, all -> 0x003f
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch: Throwable -> 0x0044, all -> 0x003f
            r10.<init>()     // Catch: Throwable -> 0x0044, all -> 0x003f
            java.lang.String r0 = "ReceiveChannel doesn't contain element at index "
            r10.append(r0)     // Catch: Throwable -> 0x0044, all -> 0x003f
            r10.append(r9)     // Catch: Throwable -> 0x0044, all -> 0x003f
            r10.append(r3)     // Catch: Throwable -> 0x0044, all -> 0x003f
            java.lang.String r9 = r10.toString()     // Catch: Throwable -> 0x0044, all -> 0x003f
            r8.<init>(r9)     // Catch: Throwable -> 0x0044, all -> 0x003f
            throw r8     // Catch: Throwable -> 0x0044, all -> 0x003f
        L_0x009a:
            r9 = move-exception
            goto L_0x00ba
        L_0x009c:
            r9 = move-exception
            r4 = r9
            goto L_0x00b9
        L_0x009f:
            java.lang.IndexOutOfBoundsException r10 = new java.lang.IndexOutOfBoundsException     // Catch: Throwable -> 0x009c, all -> 0x009a
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.<init>()     // Catch: Throwable -> 0x009c, all -> 0x009a
            java.lang.String r1 = "ReceiveChannel doesn't contain element at index "
            r0.append(r1)     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.append(r9)     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.append(r3)     // Catch: Throwable -> 0x009c, all -> 0x009a
            java.lang.String r9 = r0.toString()     // Catch: Throwable -> 0x009c, all -> 0x009a
            r10.<init>(r9)     // Catch: Throwable -> 0x009c, all -> 0x009a
            throw r10     // Catch: Throwable -> 0x009c, all -> 0x009a
        L_0x00b9:
            throw r4     // Catch: all -> 0x009a
        L_0x00ba:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.a(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0069 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0077 A[Catch: Throwable -> 0x008d, all -> 0x008b, TRY_LEAVE, TryCatch #0 {Throwable -> 0x008d, blocks: (B:24:0x0054, B:25:0x0058, B:29:0x006f, B:31:0x0077), top: B:45:0x0054 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0087  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object b(kotlinx.coroutines.channels.ReceiveChannel r7, int r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.e.j
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.channels.e$j r0 = (kotlinx.coroutines.channels.e.j) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$j r0 = new kotlinx.coroutines.channels.e$j
            r0.<init>(r9)
        L_0x0019:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x004a;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002d:
            int r7 = r0.I$1
            int r8 = r0.I$0
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: Throwable -> 0x0046, all -> 0x0042
            r6 = r0
            r0 = r7
            r7 = r4
            r4 = r1
            r1 = r6
            goto L_0x006f
        L_0x0042:
            r7 = move-exception
            r8 = r7
            r7 = r4
            goto L_0x0090
        L_0x0046:
            r7 = move-exception
            r3 = r7
            r7 = r4
            goto L_0x008f
        L_0x004a:
            kotlin.ResultKt.throwOnFailure(r9)
            if (r8 >= 0) goto L_0x0053
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            return r3
        L_0x0053:
            r9 = 0
            kotlinx.coroutines.channels.ChannelIterator r2 = r7.iterator()     // Catch: Throwable -> 0x008d, all -> 0x008b
        L_0x0058:
            r0.L$0 = r7     // Catch: Throwable -> 0x008d, all -> 0x008b
            r0.L$1 = r2     // Catch: Throwable -> 0x008d, all -> 0x008b
            r0.I$0 = r8     // Catch: Throwable -> 0x008d, all -> 0x008b
            r0.I$1 = r9     // Catch: Throwable -> 0x008d, all -> 0x008b
            r4 = 1
            r0.label = r4     // Catch: Throwable -> 0x008d, all -> 0x008b
            java.lang.Object r4 = r2.hasNext(r0)     // Catch: Throwable -> 0x008d, all -> 0x008b
            if (r4 != r1) goto L_0x006a
            return r1
        L_0x006a:
            r6 = r0
            r0 = r9
            r9 = r4
            r4 = r1
            r1 = r6
        L_0x006f:
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: Throwable -> 0x008d, all -> 0x008b
            boolean r9 = r9.booleanValue()     // Catch: Throwable -> 0x008d, all -> 0x008b
            if (r9 == 0) goto L_0x0087
            java.lang.Object r9 = r2.next()     // Catch: Throwable -> 0x008d, all -> 0x008b
            int r5 = r0 + 1
            if (r8 != r0) goto L_0x0083
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            return r9
        L_0x0083:
            r0 = r1
            r1 = r4
            r9 = r5
            goto L_0x0058
        L_0x0087:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            return r3
        L_0x008b:
            r8 = move-exception
            goto L_0x0090
        L_0x008d:
            r8 = move-exception
            r3 = r8
        L_0x008f:
            throw r3     // Catch: all -> 0x008b
        L_0x0090:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.b(kotlinx.coroutines.channels.ReceiveChannel, int, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0060 A[Catch: Throwable -> 0x003b, all -> 0x0039, TRY_LEAVE, TryCatch #4 {Throwable -> 0x003b, all -> 0x0039, blocks: (B:13:0x0035, B:22:0x0058, B:24:0x0060, B:27:0x0068, B:28:0x006f), top: B:39:0x0035 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0068 A[Catch: Throwable -> 0x003b, all -> 0x0039, TRY_ENTER, TryCatch #4 {Throwable -> 0x003b, all -> 0x0039, blocks: (B:13:0x0035, B:22:0x0058, B:24:0x0060, B:27:0x0068, B:28:0x006f), top: B:39:0x0035 }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object a(kotlinx.coroutines.channels.ReceiveChannel r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.channels.e.q
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.channels.e$q r0 = (kotlinx.coroutines.channels.e.q) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$q r0 = new kotlinx.coroutines.channels.e$q
            r0.<init>(r6)
        L_0x0019:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x003f;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002d:
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: Throwable -> 0x003b, all -> 0x0039
            goto L_0x0058
        L_0x0039:
            r5 = move-exception
            goto L_0x0077
        L_0x003b:
            r5 = move-exception
            r3 = r5
            r5 = r0
            goto L_0x0076
        L_0x003f:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.channels.ChannelIterator r6 = r5.iterator()     // Catch: Throwable -> 0x0074, all -> 0x0070
            r0.L$0 = r5     // Catch: Throwable -> 0x0074, all -> 0x0070
            r0.L$1 = r6     // Catch: Throwable -> 0x0074, all -> 0x0070
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x0074, all -> 0x0070
            java.lang.Object r0 = r6.hasNext(r0)     // Catch: Throwable -> 0x0074, all -> 0x0070
            if (r0 != r1) goto L_0x0054
            return r1
        L_0x0054:
            r4 = r0
            r0 = r5
            r5 = r6
            r6 = r4
        L_0x0058:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: Throwable -> 0x003b, all -> 0x0039
            boolean r6 = r6.booleanValue()     // Catch: Throwable -> 0x003b, all -> 0x0039
            if (r6 == 0) goto L_0x0068
            java.lang.Object r5 = r5.next()     // Catch: Throwable -> 0x003b, all -> 0x0039
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            return r5
        L_0x0068:
            java.util.NoSuchElementException r5 = new java.util.NoSuchElementException     // Catch: Throwable -> 0x003b, all -> 0x0039
            java.lang.String r6 = "ReceiveChannel is empty."
            r5.<init>(r6)     // Catch: Throwable -> 0x003b, all -> 0x0039
            throw r5     // Catch: Throwable -> 0x003b, all -> 0x0039
        L_0x0070:
            r6 = move-exception
            r0 = r5
            r5 = r6
            goto L_0x0077
        L_0x0074:
            r6 = move-exception
            r3 = r6
        L_0x0076:
            throw r3     // Catch: all -> 0x0070
        L_0x0077:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.a(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0066 A[Catch: Throwable -> 0x003d, all -> 0x0039, TRY_ENTER, TRY_LEAVE, TryCatch #4 {Throwable -> 0x003d, all -> 0x0039, blocks: (B:13:0x0035, B:23:0x005a, B:27:0x0066), top: B:39:0x0035 }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object b(kotlinx.coroutines.channels.ReceiveChannel r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.channels.e.r
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.channels.e$r r0 = (kotlinx.coroutines.channels.e.r) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$r r0 = new kotlinx.coroutines.channels.e$r
            r0.<init>(r6)
        L_0x0019:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0041;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002d:
            java.lang.Object r5 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: Throwable -> 0x003d, all -> 0x0039
            goto L_0x005a
        L_0x0039:
            r5 = move-exception
            r6 = r5
            r5 = r0
            goto L_0x0073
        L_0x003d:
            r5 = move-exception
            r3 = r5
            r5 = r0
            goto L_0x0072
        L_0x0041:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.channels.ChannelIterator r6 = r5.iterator()     // Catch: Throwable -> 0x0070, all -> 0x006e
            r0.L$0 = r5     // Catch: Throwable -> 0x0070, all -> 0x006e
            r0.L$1 = r6     // Catch: Throwable -> 0x0070, all -> 0x006e
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x0070, all -> 0x006e
            java.lang.Object r0 = r6.hasNext(r0)     // Catch: Throwable -> 0x0070, all -> 0x006e
            if (r0 != r1) goto L_0x0056
            return r1
        L_0x0056:
            r4 = r0
            r0 = r5
            r5 = r6
            r6 = r4
        L_0x005a:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: Throwable -> 0x003d, all -> 0x0039
            boolean r6 = r6.booleanValue()     // Catch: Throwable -> 0x003d, all -> 0x0039
            if (r6 != 0) goto L_0x0066
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            return r3
        L_0x0066:
            java.lang.Object r5 = r5.next()     // Catch: Throwable -> 0x003d, all -> 0x0039
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            return r5
        L_0x006e:
            r6 = move-exception
            goto L_0x0073
        L_0x0070:
            r6 = move-exception
            r3 = r6
        L_0x0072:
            throw r3     // Catch: all -> 0x006e
        L_0x0073:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.b(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0068 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0075 A[Catch: Throwable -> 0x0043, all -> 0x0040, TryCatch #6 {all -> 0x0040, Throwable -> 0x0043, blocks: (B:13:0x003c, B:24:0x006d, B:26:0x0075, B:28:0x007f, B:31:0x0089, B:32:0x0091), top: B:49:0x003c }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0091 A[Catch: Throwable -> 0x0043, all -> 0x0040, TRY_LEAVE, TryCatch #6 {all -> 0x0040, Throwable -> 0x0043, blocks: (B:13:0x003c, B:24:0x006d, B:26:0x0075, B:28:0x007f, B:31:0x0089, B:32:0x0091), top: B:49:0x003c }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object a(kotlinx.coroutines.channels.ReceiveChannel r7, java.lang.Object r8, kotlin.coroutines.Continuation r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.channels.e.t
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.channels.e$t r0 = (kotlinx.coroutines.channels.e.t) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r9 = r0.label
            int r9 = r9 - r2
            r0.label = r9
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$t r0 = new kotlinx.coroutines.channels.e$t
            r0.<init>(r9)
        L_0x0019:
            java.lang.Object r9 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0048;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002e:
            java.lang.Object r7 = r0.L$3
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r8 = r0.L$2
            kotlinx.coroutines.channels.ReceiveChannel r8 = (kotlinx.coroutines.channels.ReceiveChannel) r8
            java.lang.Object r2 = r0.L$1
            kotlin.jvm.internal.Ref$IntRef r2 = (kotlin.jvm.internal.Ref.IntRef) r2
            java.lang.Object r5 = r0.L$0
            kotlin.ResultKt.throwOnFailure(r9)     // Catch: Throwable -> 0x0043, all -> 0x0040
            goto L_0x006d
        L_0x0040:
            r7 = move-exception
            goto L_0x00ab
        L_0x0043:
            r7 = move-exception
            r4 = r7
            r7 = r8
            goto L_0x00aa
        L_0x0048:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlin.jvm.internal.Ref$IntRef r9 = new kotlin.jvm.internal.Ref$IntRef
            r9.<init>()
            kotlinx.coroutines.channels.ChannelIterator r2 = r7.iterator()     // Catch: Throwable -> 0x00a8, all -> 0x00a3
            r6 = r9
            r9 = r7
            r7 = r2
            r2 = r6
        L_0x0058:
            r0.L$0 = r8     // Catch: Throwable -> 0x009f, all -> 0x009c
            r0.L$1 = r2     // Catch: Throwable -> 0x009f, all -> 0x009c
            r0.L$2 = r9     // Catch: Throwable -> 0x009f, all -> 0x009c
            r0.L$3 = r7     // Catch: Throwable -> 0x009f, all -> 0x009c
            r0.label = r3     // Catch: Throwable -> 0x009f, all -> 0x009c
            java.lang.Object r5 = r7.hasNext(r0)     // Catch: Throwable -> 0x009f, all -> 0x009c
            if (r5 != r1) goto L_0x0069
            return r1
        L_0x0069:
            r6 = r5
            r5 = r8
            r8 = r9
            r9 = r6
        L_0x006d:
            java.lang.Boolean r9 = (java.lang.Boolean) r9     // Catch: Throwable -> 0x0043, all -> 0x0040
            boolean r9 = r9.booleanValue()     // Catch: Throwable -> 0x0043, all -> 0x0040
            if (r9 == 0) goto L_0x0091
            java.lang.Object r9 = r7.next()     // Catch: Throwable -> 0x0043, all -> 0x0040
            boolean r9 = kotlin.jvm.internal.Intrinsics.areEqual(r5, r9)     // Catch: Throwable -> 0x0043, all -> 0x0040
            if (r9 == 0) goto L_0x0089
            int r7 = r2.element     // Catch: Throwable -> 0x0043, all -> 0x0040
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)     // Catch: Throwable -> 0x0043, all -> 0x0040
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            return r7
        L_0x0089:
            int r9 = r2.element     // Catch: Throwable -> 0x0043, all -> 0x0040
            int r9 = r9 + r3
            r2.element = r9     // Catch: Throwable -> 0x0043, all -> 0x0040
            r9 = r8
            r8 = r5
            goto L_0x0058
        L_0x0091:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x0043, all -> 0x0040
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            r7 = -1
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)
            return r7
        L_0x009c:
            r7 = move-exception
            r8 = r9
            goto L_0x00ab
        L_0x009f:
            r7 = move-exception
            r4 = r7
            r7 = r9
            goto L_0x00aa
        L_0x00a3:
            r8 = move-exception
            r6 = r8
            r8 = r7
            r7 = r6
            goto L_0x00ab
        L_0x00a8:
            r8 = move-exception
            r4 = r8
        L_0x00aa:
            throw r4     // Catch: all -> 0x00a3
        L_0x00ab:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r4)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.a(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007a A[Catch: Throwable -> 0x0054, all -> 0x0051, TRY_LEAVE, TryCatch #6 {all -> 0x0051, Throwable -> 0x0054, blocks: (B:19:0x004d, B:28:0x0072, B:30:0x007a, B:42:0x00a7, B:43:0x00ae), top: B:54:0x004d }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0090 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x009d A[Catch: Throwable -> 0x0040, all -> 0x003c, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0040, all -> 0x003c, blocks: (B:13:0x0037, B:36:0x0095, B:38:0x009d), top: B:55:0x0037 }] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a7 A[Catch: Throwable -> 0x0054, all -> 0x0051, TRY_ENTER, TryCatch #6 {all -> 0x0051, Throwable -> 0x0054, blocks: (B:19:0x004d, B:28:0x0072, B:30:0x007a, B:42:0x00a7, B:43:0x00ae), top: B:54:0x004d }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object c(kotlinx.coroutines.channels.ReceiveChannel r6, kotlin.coroutines.Continuation r7) {
        /*
            Method dump skipped, instructions count: 196
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.c(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0076 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0083 A[Catch: Throwable -> 0x0047, all -> 0x0044, TryCatch #6 {all -> 0x0044, Throwable -> 0x0047, blocks: (B:13:0x0040, B:24:0x007b, B:26:0x0083, B:28:0x008d, B:29:0x0091, B:30:0x0099), top: B:47:0x0040 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0099 A[Catch: Throwable -> 0x0047, all -> 0x0044, TRY_LEAVE, TryCatch #6 {all -> 0x0044, Throwable -> 0x0047, blocks: (B:13:0x0040, B:24:0x007b, B:26:0x0083, B:28:0x008d, B:29:0x0091, B:30:0x0099), top: B:47:0x0040 }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object b(kotlinx.coroutines.channels.ReceiveChannel r8, java.lang.Object r9, kotlin.coroutines.Continuation r10) {
        /*
            Method dump skipped, instructions count: 192
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.b(kotlinx.coroutines.channels.ReceiveChannel, java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0080 A[Catch: Throwable -> 0x0057, all -> 0x0052, TRY_ENTER, TRY_LEAVE, TryCatch #6 {all -> 0x0052, Throwable -> 0x0057, blocks: (B:19:0x004e, B:29:0x0074, B:33:0x0080), top: B:54:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0096 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00a3 A[Catch: Throwable -> 0x0041, all -> 0x003c, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0041, all -> 0x003c, blocks: (B:13:0x0037, B:39:0x009b, B:41:0x00a3), top: B:56:0x0037 }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a9  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object d(kotlinx.coroutines.channels.ReceiveChannel r6, kotlin.coroutines.Continuation r7) {
        /*
            Method dump skipped, instructions count: 192
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.d(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076 A[Catch: Throwable -> 0x0051, all -> 0x004f, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0051, all -> 0x004f, blocks: (B:20:0x004b, B:29:0x006e, B:31:0x0076, B:41:0x009d, B:42:0x00a4), top: B:55:0x004b }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0095 A[Catch: Throwable -> 0x003e, all -> 0x003a, TRY_ENTER, TryCatch #6 {Throwable -> 0x003e, all -> 0x003a, blocks: (B:13:0x0033, B:35:0x0089, B:39:0x0095, B:40:0x009c), top: B:53:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009d A[Catch: Throwable -> 0x0051, all -> 0x004f, TRY_ENTER, TryCatch #5 {Throwable -> 0x0051, all -> 0x004f, blocks: (B:20:0x004b, B:29:0x006e, B:31:0x0076, B:41:0x009d, B:42:0x00a4), top: B:55:0x004b }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object e(kotlinx.coroutines.channels.ReceiveChannel r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.channels.e.ad
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.channels.e$ad r0 = (kotlinx.coroutines.channels.e.ad) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$ad r0 = new kotlinx.coroutines.channels.e$ad
            r0.<init>(r7)
        L_0x0019:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0043;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            java.lang.Object r6 = r0.L$1
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: Throwable -> 0x003e, all -> 0x003a
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x0089
        L_0x003a:
            r6 = move-exception
            r2 = r0
            goto L_0x00ac
        L_0x003e:
            r6 = move-exception
            r3 = r6
            r6 = r0
            goto L_0x00ab
        L_0x0043:
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: Throwable -> 0x0051, all -> 0x004f
            goto L_0x006e
        L_0x004f:
            r6 = move-exception
            goto L_0x00ac
        L_0x0051:
            r6 = move-exception
            r3 = r6
            r6 = r2
            goto L_0x00ab
        L_0x0055:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.ChannelIterator r7 = r6.iterator()     // Catch: Throwable -> 0x00a9, all -> 0x00a5
            r0.L$0 = r6     // Catch: Throwable -> 0x00a9, all -> 0x00a5
            r0.L$1 = r7     // Catch: Throwable -> 0x00a9, all -> 0x00a5
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x00a9, all -> 0x00a5
            java.lang.Object r2 = r7.hasNext(r0)     // Catch: Throwable -> 0x00a9, all -> 0x00a5
            if (r2 != r1) goto L_0x006a
            return r1
        L_0x006a:
            r5 = r2
            r2 = r6
            r6 = r7
            r7 = r5
        L_0x006e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: Throwable -> 0x0051, all -> 0x004f
            boolean r7 = r7.booleanValue()     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r7 == 0) goto L_0x009d
            java.lang.Object r7 = r6.next()     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$0 = r2     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$1 = r7     // Catch: Throwable -> 0x0051, all -> 0x004f
            r4 = 2
            r0.label = r4     // Catch: Throwable -> 0x0051, all -> 0x004f
            java.lang.Object r6 = r6.hasNext(r0)     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r6 != r1) goto L_0x0088
            return r1
        L_0x0088:
            r0 = r2
        L_0x0089:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: Throwable -> 0x003e, all -> 0x003a
            boolean r6 = r6.booleanValue()     // Catch: Throwable -> 0x003e, all -> 0x003a
            if (r6 != 0) goto L_0x0095
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            return r7
        L_0x0095:
            java.lang.IllegalArgumentException r6 = new java.lang.IllegalArgumentException     // Catch: Throwable -> 0x003e, all -> 0x003a
            java.lang.String r7 = "ReceiveChannel has more than one element."
            r6.<init>(r7)     // Catch: Throwable -> 0x003e, all -> 0x003a
            throw r6     // Catch: Throwable -> 0x003e, all -> 0x003a
        L_0x009d:
            java.util.NoSuchElementException r6 = new java.util.NoSuchElementException     // Catch: Throwable -> 0x0051, all -> 0x004f
            java.lang.String r7 = "ReceiveChannel is empty."
            r6.<init>(r7)     // Catch: Throwable -> 0x0051, all -> 0x004f
            throw r6     // Catch: Throwable -> 0x0051, all -> 0x004f
        L_0x00a5:
            r7 = move-exception
            r2 = r6
            r6 = r7
            goto L_0x00ac
        L_0x00a9:
            r7 = move-exception
            r3 = r7
        L_0x00ab:
            throw r3     // Catch: all -> 0x00a5
        L_0x00ac:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.e(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a A[Catch: Throwable -> 0x0051, all -> 0x004f, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0051, all -> 0x004f, blocks: (B:20:0x004b, B:29:0x006e, B:33:0x007a), top: B:55:0x004b }] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0099  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object f(kotlinx.coroutines.channels.ReceiveChannel r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.channels.e.ae
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.channels.e$ae r0 = (kotlinx.coroutines.channels.e.ae) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$ae r0 = new kotlinx.coroutines.channels.e$ae
            r0.<init>(r7)
        L_0x0019:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0055;
                case 1: goto L_0x0043;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            java.lang.Object r6 = r0.L$1
            java.lang.Object r0 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r0 = (kotlinx.coroutines.channels.ReceiveChannel) r0
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: Throwable -> 0x003e, all -> 0x003a
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x008d
        L_0x003a:
            r6 = move-exception
            r2 = r0
            goto L_0x00a4
        L_0x003e:
            r6 = move-exception
            r3 = r6
            r6 = r0
            goto L_0x00a3
        L_0x0043:
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: Throwable -> 0x0051, all -> 0x004f
            goto L_0x006e
        L_0x004f:
            r6 = move-exception
            goto L_0x00a4
        L_0x0051:
            r6 = move-exception
            r3 = r6
            r6 = r2
            goto L_0x00a3
        L_0x0055:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.ChannelIterator r7 = r6.iterator()     // Catch: Throwable -> 0x00a1, all -> 0x009d
            r0.L$0 = r6     // Catch: Throwable -> 0x00a1, all -> 0x009d
            r0.L$1 = r7     // Catch: Throwable -> 0x00a1, all -> 0x009d
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x00a1, all -> 0x009d
            java.lang.Object r2 = r7.hasNext(r0)     // Catch: Throwable -> 0x00a1, all -> 0x009d
            if (r2 != r1) goto L_0x006a
            return r1
        L_0x006a:
            r5 = r2
            r2 = r6
            r6 = r7
            r7 = r5
        L_0x006e:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: Throwable -> 0x0051, all -> 0x004f
            boolean r7 = r7.booleanValue()     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r7 != 0) goto L_0x007a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            return r3
        L_0x007a:
            java.lang.Object r7 = r6.next()     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$0 = r2     // Catch: Throwable -> 0x0051, all -> 0x004f
            r0.L$1 = r7     // Catch: Throwable -> 0x0051, all -> 0x004f
            r4 = 2
            r0.label = r4     // Catch: Throwable -> 0x0051, all -> 0x004f
            java.lang.Object r6 = r6.hasNext(r0)     // Catch: Throwable -> 0x0051, all -> 0x004f
            if (r6 != r1) goto L_0x008c
            return r1
        L_0x008c:
            r0 = r2
        L_0x008d:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: Throwable -> 0x003e, all -> 0x003a
            boolean r6 = r6.booleanValue()     // Catch: Throwable -> 0x003e, all -> 0x003a
            if (r6 == 0) goto L_0x0099
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            return r3
        L_0x0099:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r0, r3)
            return r7
        L_0x009d:
            r7 = move-exception
            r2 = r6
            r6 = r7
            goto L_0x00a4
        L_0x00a1:
            r7 = move-exception
            r3 = r7
        L_0x00a3:
            throw r3     // Catch: all -> 0x009d
        L_0x00a4:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.f(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$drop$1", f = "Deprecated.kt", i = {0}, l = {Opcodes.DCMPL, BuildConfig.MiConnectVersionBuild, 157}, m = "invokeSuspend", n = {"remaining"}, s = {"I$0"})
    /* loaded from: classes5.dex */
    public static final class g extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $n;
        final /* synthetic */ ReceiveChannel<E> $this_drop;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        g(int i, ReceiveChannel<? extends E> receiveChannel, Continuation<? super g> continuation) {
            super(2, continuation);
            this.$n = i;
            this.$this_drop = receiveChannel;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((g) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            g gVar = new g(this.$n, this.$this_drop, continuation);
            gVar.L$0 = obj;
            return gVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:19:0x0071 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:20:0x0072  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x007e  */
        /* JADX WARN: Removed duplicated region for block: B:31:0x00a3 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00a4  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00af  */
        /* JADX WARN: Removed duplicated region for block: B:39:0x00c6  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r8) {
            /*
                Method dump skipped, instructions count: 248
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.g.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, int i2, CoroutineContext coroutineContext, int i3, Object obj) {
        ReceiveChannel a2;
        if ((i3 & 2) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        a2 = a(receiveChannel, i2, coroutineContext);
        return a2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, int i2, CoroutineContext coroutineContext) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new g(i2, receiveChannel, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$dropWhile$1", f = "Deprecated.kt", i = {0, 1, 1, 2}, l = {164, Opcodes.IF_ACMPEQ, Opcodes.IF_ACMPNE, 170, 171}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e", "$this$produce"}, s = {"L$0", "L$0", "L$2", "L$0"})
    /* loaded from: classes5.dex */
    public static final class h extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
        final /* synthetic */ ReceiveChannel<E> $this_dropWhile;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        h(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super h> continuation) {
            super(2, continuation);
            this.$this_dropWhile = receiveChannel;
            this.$predicate = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((h) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            h hVar = new h(this.$this_dropWhile, this.$predicate, continuation);
            hVar.L$0 = obj;
            return hVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0083 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0084  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x0093  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00b7  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00cc  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x00e5 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00e6  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00f2  */
        /* JADX WARN: Removed duplicated region for block: B:40:0x010a  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                Method dump skipped, instructions count: 286
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.h.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        ReceiveChannel a2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        a2 = a(receiveChannel, coroutineContext, function2);
        return a2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new h(receiveChannel, function2, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filter$1", f = "Deprecated.kt", i = {0, 1, 1, 2}, l = {Opcodes.GETSTATIC, 179, 179}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e", "$this$produce"}, s = {"L$0", "L$0", "L$2", "L$0"})
    /* loaded from: classes5.dex */
    public static final class k extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
        final /* synthetic */ ReceiveChannel<E> $this_filter;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        k(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super k> continuation) {
            super(2, continuation);
            this.$this_filter = receiveChannel;
            this.$predicate = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((k) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            k kVar = new k(this.$this_filter, this.$predicate, continuation);
            kVar.L$0 = obj;
            return kVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0061 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x006e  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x008f  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00a5  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                r2 = 0
                switch(r1) {
                    case 0: goto L_0x0040;
                    case 1: goto L_0x0032;
                    case 2: goto L_0x001f;
                    case 3: goto L_0x0012;
                    default: goto L_0x000a;
                }
            L_0x000a:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L_0x0012:
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r3 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r8
                goto L_0x004f
            L_0x001f:
                java.lang.Object r1 = r8.L$2
                java.lang.Object r3 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
                java.lang.Object r4 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
                kotlin.ResultKt.throwOnFailure(r9)
                r5 = r1
                r1 = r3
                r3 = r4
                r4 = r0
                r0 = r8
                goto L_0x0087
            L_0x0032:
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r3 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
                kotlin.ResultKt.throwOnFailure(r9)
                r4 = r0
                r0 = r8
                goto L_0x0066
            L_0x0040:
                kotlin.ResultKt.throwOnFailure(r9)
                java.lang.Object r9 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
                kotlinx.coroutines.channels.ReceiveChannel<E> r1 = r8.$this_filter
                kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
                r3 = r9
                r9 = r8
            L_0x004f:
                r4 = r9
                kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                r9.L$0 = r3
                r9.L$1 = r1
                r9.L$2 = r2
                r5 = 1
                r9.label = r5
                java.lang.Object r4 = r1.hasNext(r4)
                if (r4 != r0) goto L_0x0062
                return r0
            L_0x0062:
                r7 = r0
                r0 = r9
                r9 = r4
                r4 = r7
            L_0x0066:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L_0x00a5
                java.lang.Object r9 = r1.next()
                kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r5 = r0.$predicate
                r0.L$0 = r3
                r0.L$1 = r1
                r0.L$2 = r9
                r6 = 2
                r0.label = r6
                java.lang.Object r5 = r5.invoke(r9, r0)
                if (r5 != r4) goto L_0x0084
                return r4
            L_0x0084:
                r7 = r5
                r5 = r9
                r9 = r7
            L_0x0087:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L_0x00a2
                r9 = r0
                kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
                r0.L$0 = r3
                r0.L$1 = r1
                r0.L$2 = r2
                r6 = 3
                r0.label = r6
                java.lang.Object r9 = r3.send(r5, r9)
                if (r9 != r4) goto L_0x00a2
                return r4
            L_0x00a2:
                r9 = r0
                r0 = r4
                goto L_0x004f
            L_0x00a5:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.k.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel b(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.filter(receiveChannel, coroutineContext, function2);
    }

    @PublishedApi
    @NotNull
    public static final <E> ReceiveChannel<E> b(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2) {
        ReceiveChannel<E> produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new k(receiveChannel, function2, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterIndexed$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 2}, l = {Opcodes.NEW, 188, 188}, m = "invokeSuspend", n = {"$this$produce", MiSoundBoxCommandExtras.INDEX, "$this$produce", "e", "$this$produce"}, s = {"L$0", "I$0", "L$0", "L$2", "L$0"})
    /* loaded from: classes5.dex */
    public static final class l extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function3<Integer, E, Continuation<? super Boolean>, Object> $predicate;
        final /* synthetic */ ReceiveChannel<E> $this_filterIndexed;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        l(ReceiveChannel<? extends E> receiveChannel, Function3<? super Integer, ? super E, ? super Continuation<? super Boolean>, ? extends Object> function3, Continuation<? super l> continuation) {
            super(2, continuation);
            this.$this_filterIndexed = receiveChannel;
            this.$predicate = function3;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((l) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            l lVar = new l(this.$this_filterIndexed, this.$predicate, continuation);
            lVar.L$0 = obj;
            return lVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x006b A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:0x006c  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0078  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00b9  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                r10 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r10.label
                r2 = 0
                switch(r1) {
                    case 0: goto L_0x0047;
                    case 1: goto L_0x0037;
                    case 2: goto L_0x0021;
                    case 3: goto L_0x0012;
                    default: goto L_0x000a;
                }
            L_0x000a:
                java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r11.<init>(r0)
                throw r11
            L_0x0012:
                int r1 = r10.I$0
                java.lang.Object r3 = r10.L$1
                kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
                java.lang.Object r4 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
                kotlin.ResultKt.throwOnFailure(r11)
                r11 = r10
                goto L_0x0057
            L_0x0021:
                int r1 = r10.I$0
                java.lang.Object r3 = r10.L$2
                java.lang.Object r4 = r10.L$1
                kotlinx.coroutines.channels.ChannelIterator r4 = (kotlinx.coroutines.channels.ChannelIterator) r4
                java.lang.Object r5 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r5 = (kotlinx.coroutines.channels.ProducerScope) r5
                kotlin.ResultKt.throwOnFailure(r11)
                r6 = r3
                r3 = r4
                r4 = r5
                r5 = r0
                r0 = r10
                goto L_0x0099
            L_0x0037:
                int r1 = r10.I$0
                java.lang.Object r3 = r10.L$1
                kotlinx.coroutines.channels.ChannelIterator r3 = (kotlinx.coroutines.channels.ChannelIterator) r3
                java.lang.Object r4 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
                kotlin.ResultKt.throwOnFailure(r11)
                r5 = r0
                r0 = r10
                goto L_0x0070
            L_0x0047:
                kotlin.ResultKt.throwOnFailure(r11)
                java.lang.Object r11 = r10.L$0
                kotlinx.coroutines.channels.ProducerScope r11 = (kotlinx.coroutines.channels.ProducerScope) r11
                r1 = 0
                kotlinx.coroutines.channels.ReceiveChannel<E> r3 = r10.$this_filterIndexed
                kotlinx.coroutines.channels.ChannelIterator r3 = r3.iterator()
                r4 = r11
                r11 = r10
            L_0x0057:
                r5 = r11
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r11.L$0 = r4
                r11.L$1 = r3
                r11.L$2 = r2
                r11.I$0 = r1
                r6 = 1
                r11.label = r6
                java.lang.Object r5 = r3.hasNext(r5)
                if (r5 != r0) goto L_0x006c
                return r0
            L_0x006c:
                r9 = r0
                r0 = r11
                r11 = r5
                r5 = r9
            L_0x0070:
                java.lang.Boolean r11 = (java.lang.Boolean) r11
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto L_0x00b9
                java.lang.Object r11 = r3.next()
                kotlin.jvm.functions.Function3<java.lang.Integer, E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r6 = r0.$predicate
                int r7 = r1 + 1
                java.lang.Integer r1 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r1)
                r0.L$0 = r4
                r0.L$1 = r3
                r0.L$2 = r11
                r0.I$0 = r7
                r8 = 2
                r0.label = r8
                java.lang.Object r1 = r6.invoke(r1, r11, r0)
                if (r1 != r5) goto L_0x0096
                return r5
            L_0x0096:
                r6 = r11
                r11 = r1
                r1 = r7
            L_0x0099:
                java.lang.Boolean r11 = (java.lang.Boolean) r11
                boolean r11 = r11.booleanValue()
                if (r11 == 0) goto L_0x00b6
                r11 = r0
                kotlin.coroutines.Continuation r11 = (kotlin.coroutines.Continuation) r11
                r0.L$0 = r4
                r0.L$1 = r3
                r0.L$2 = r2
                r0.I$0 = r1
                r7 = 3
                r0.label = r7
                java.lang.Object r11 = r4.send(r6, r11)
                if (r11 != r5) goto L_0x00b6
                return r5
            L_0x00b6:
                r11 = r0
                r0 = r5
                goto L_0x0057
            L_0x00b9:
                kotlin.Unit r11 = kotlin.Unit.INSTANCE
                return r11
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.l.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i2, Object obj) {
        ReceiveChannel a2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        a2 = a(receiveChannel, coroutineContext, function3);
        return a2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new l(receiveChannel, function3, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u0002H\u0002H\u008a@"}, d2 = {"<anonymous>", "", "E", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNot$1", f = "Deprecated.kt", i = {}, l = {194}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class m extends SuspendLambda implements Function2<E, Continuation<? super Boolean>, Object> {
        final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        m(Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super m> continuation) {
            super(2, continuation);
            this.$predicate = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(E e, @Nullable Continuation<? super Boolean> continuation) {
            return ((m) create(e, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            m mVar = new m(this.$predicate, continuation);
            mVar.L$0 = obj;
            return mVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            switch (this.label) {
                case 0:
                    ResultKt.throwOnFailure(obj);
                    Object obj2 = this.L$0;
                    Function2<E, Continuation<? super Boolean>, Object> function2 = this.$predicate;
                    this.label = 1;
                    obj = function2.invoke(obj2, this);
                    if (obj == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    break;
                case 1:
                    ResultKt.throwOnFailure(obj);
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Boxing.boxBoolean(!((Boolean) obj).booleanValue());
        }
    }

    public static /* synthetic */ ReceiveChannel c(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        ReceiveChannel c2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        c2 = c(receiveChannel, coroutineContext, function2);
        return c2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel c(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ChannelsKt.filter(receiveChannel, coroutineContext, new m(function2, null));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u0001H\u0002H\u008a@"}, d2 = {"<anonymous>", "", "E", "", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$filterNotNull$1", f = "Deprecated.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class n extends SuspendLambda implements Function2<E, Continuation<? super Boolean>, Object> {
        /* synthetic */ Object L$0;
        int label;

        n(Continuation<? super n> continuation) {
            super(2, continuation);
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@Nullable E e, @Nullable Continuation<? super Boolean> continuation) {
            return ((n) create(e, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            n nVar = new n(continuation);
            nVar.L$0 = obj;
            return nVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return Boxing.boxBoolean(this.L$0 != null);
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @PublishedApi
    @NotNull
    public static final <E> ReceiveChannel<E> a(@NotNull ReceiveChannel<? extends E> receiveChannel) {
        ReceiveChannel<E> b2;
        b2 = b(receiveChannel, (CoroutineContext) null, new n(null), 1, (Object) null);
        return b2;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0069 A[Catch: Throwable -> 0x003f, all -> 0x003d, TryCatch #6 {Throwable -> 0x003f, all -> 0x003d, blocks: (B:13:0x0039, B:24:0x0061, B:26:0x0069, B:28:0x006f, B:30:0x0075), top: B:47:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0075 A[Catch: Throwable -> 0x003f, all -> 0x003d, TRY_LEAVE, TryCatch #6 {Throwable -> 0x003f, all -> 0x003d, blocks: (B:13:0x0039, B:24:0x0061, B:26:0x0069, B:28:0x006f, B:30:0x0075), top: B:47:0x0039 }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object a(kotlinx.coroutines.channels.ReceiveChannel r5, java.util.Collection r6, kotlin.coroutines.Continuation r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.channels.e.o
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.channels.e$o r0 = (kotlinx.coroutines.channels.e.o) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$o r0 = new kotlinx.coroutines.channels.e$o
            r0.<init>(r7)
        L_0x0019:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0043;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002d:
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r2 = r0.L$0
            java.util.Collection r2 = (java.util.Collection) r2
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: Throwable -> 0x003f, all -> 0x003d
            goto L_0x0061
        L_0x003d:
            r5 = move-exception
            goto L_0x008a
        L_0x003f:
            r5 = move-exception
            r3 = r5
            r5 = r6
            goto L_0x0089
        L_0x0043:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch: Throwable -> 0x0087, all -> 0x0082
            r4 = r7
            r7 = r5
            r5 = r4
        L_0x004d:
            r0.L$0 = r6     // Catch: Throwable -> 0x007e, all -> 0x007b
            r0.L$1 = r7     // Catch: Throwable -> 0x007e, all -> 0x007b
            r0.L$2 = r5     // Catch: Throwable -> 0x007e, all -> 0x007b
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x007e, all -> 0x007b
            java.lang.Object r2 = r5.hasNext(r0)     // Catch: Throwable -> 0x007e, all -> 0x007b
            if (r2 != r1) goto L_0x005d
            return r1
        L_0x005d:
            r4 = r2
            r2 = r6
            r6 = r7
            r7 = r4
        L_0x0061:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: Throwable -> 0x003f, all -> 0x003d
            boolean r7 = r7.booleanValue()     // Catch: Throwable -> 0x003f, all -> 0x003d
            if (r7 == 0) goto L_0x0075
            java.lang.Object r7 = r5.next()     // Catch: Throwable -> 0x003f, all -> 0x003d
            if (r7 == 0) goto L_0x0072
            r2.add(r7)     // Catch: Throwable -> 0x003f, all -> 0x003d
        L_0x0072:
            r7 = r6
            r6 = r2
            goto L_0x004d
        L_0x0075:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x003f, all -> 0x003d
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            return r2
        L_0x007b:
            r5 = move-exception
            r6 = r7
            goto L_0x008a
        L_0x007e:
            r5 = move-exception
            r3 = r5
            r5 = r7
            goto L_0x0089
        L_0x0082:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
            goto L_0x008a
        L_0x0087:
            r6 = move-exception
            r3 = r6
        L_0x0089:
            throw r3     // Catch: all -> 0x0082
        L_0x008a:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.a(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel], vars: [r6v0 ??, r6v5 ??, r6v11 ??, r6v10 ??, r6v17 ??, r7v6 ??, r7v1 ??, r7v3 ??, r7v0 ??, r7v7 ??, r7v10 ??, r7v12 ??, r7v2 ??, r7v9 ??, r7v8 ??, r7v15 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        	at jadx.core.dex.visitors.InitCodeVariables.visit(InitCodeVariables.java:32)
        */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ java.lang.Object a(
    /*  JADX ERROR: JadxRuntimeException in pass: InitCodeVariables
        jadx.core.utils.exceptions.JadxRuntimeException: Several immutable types in one variable: [kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel], vars: [r6v0 ??, r6v5 ??, r6v11 ??, r6v10 ??, r6v17 ??, r7v6 ??, r7v1 ??, r7v3 ??, r7v0 ??, r7v7 ??, r7v10 ??, r7v12 ??, r7v2 ??, r7v9 ??, r7v8 ??, r7v15 ??]
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVarType(InitCodeVariables.java:102)
        	at jadx.core.dex.visitors.InitCodeVariables.setCodeVar(InitCodeVariables.java:78)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVar(InitCodeVariables.java:69)
        	at jadx.core.dex.visitors.InitCodeVariables.initCodeVars(InitCodeVariables.java:48)
        */
    /*  JADX ERROR: Method generation error
        jadx.core.utils.exceptions.JadxRuntimeException: Code variable not set in r6v0 ??
        	at jadx.core.dex.instructions.args.SSAVar.getCodeVar(SSAVar.java:233)
        	at jadx.core.codegen.MethodGen.addMethodArguments(MethodGen.java:209)
        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:162)
        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:364)
        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:304)
        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:270)
        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(Unknown Source)
        	at java.base/java.util.ArrayList.forEach(Unknown Source)
        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(Unknown Source)
        	at java.base/java.util.stream.Sink$ChainedReference.end(Unknown Source)
        */

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$take$1", f = "Deprecated.kt", i = {0, 1}, l = {223, 224}, m = "invokeSuspend", n = {"remaining", "remaining"}, s = {"I$0", "I$0"})
    /* loaded from: classes5.dex */
    public static final class af extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ int $n;
        final /* synthetic */ ReceiveChannel<E> $this_take;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        af(int i, ReceiveChannel<? extends E> receiveChannel, Continuation<? super af> continuation) {
            super(2, continuation);
            this.$n = i;
            this.$this_take = receiveChannel;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((af) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            af afVar = new af(this.$n, this.$this_take, continuation);
            afVar.L$0 = obj;
            return afVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:20:0x0066 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0067  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0077  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x0095  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0098  */
        /* JADX WARN: Removed duplicated region for block: B:33:0x009c  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                Method dump skipped, instructions count: 204
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.af.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel b(ReceiveChannel receiveChannel, int i2, CoroutineContext coroutineContext, int i3, Object obj) {
        ReceiveChannel b2;
        if ((i3 & 2) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        b2 = b(receiveChannel, i2, coroutineContext);
        return b2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel b(ReceiveChannel receiveChannel, int i2, CoroutineContext coroutineContext) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new af(i2, receiveChannel, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$takeWhile$1", f = "Deprecated.kt", i = {0, 1, 1}, l = {234, 235, 236}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "e"}, s = {"L$0", "L$0", "L$2"})
    /* loaded from: classes5.dex */
    public static final class ag extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function2<E, Continuation<? super Boolean>, Object> $predicate;
        final /* synthetic */ ReceiveChannel<E> $this_takeWhile;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        ag(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> function2, Continuation<? super ag> continuation) {
            super(2, continuation);
            this.$this_takeWhile = receiveChannel;
            this.$predicate = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((ag) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            ag agVar = new ag(this.$this_takeWhile, this.$predicate, continuation);
            agVar.L$0 = obj;
            return agVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x005e A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:0x005f  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x006b  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x008e  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0091  */
        /* JADX WARN: Removed duplicated region for block: B:28:0x00a9  */
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
                    case 0: goto L_0x003f;
                    case 1: goto L_0x0031;
                    case 2: goto L_0x001e;
                    case 3: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r8.<init>(r0)
                throw r8
            L_0x0011:
                java.lang.Object r1 = r7.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r2 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
                kotlin.ResultKt.throwOnFailure(r8)
                r8 = r7
                goto L_0x004e
            L_0x001e:
                java.lang.Object r1 = r7.L$2
                java.lang.Object r2 = r7.L$1
                kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
                java.lang.Object r3 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
                kotlin.ResultKt.throwOnFailure(r8)
                r4 = r0
                r0 = r7
                r6 = r3
                r3 = r2
                r2 = r6
                goto L_0x0086
            L_0x0031:
                java.lang.Object r1 = r7.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r2 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
                kotlin.ResultKt.throwOnFailure(r8)
                r3 = r0
                r0 = r7
                goto L_0x0063
            L_0x003f:
                kotlin.ResultKt.throwOnFailure(r8)
                java.lang.Object r8 = r7.L$0
                kotlinx.coroutines.channels.ProducerScope r8 = (kotlinx.coroutines.channels.ProducerScope) r8
                kotlinx.coroutines.channels.ReceiveChannel<E> r1 = r7.$this_takeWhile
                kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
                r2 = r8
                r8 = r7
            L_0x004e:
                r3 = r8
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                r8.L$0 = r2
                r8.L$1 = r1
                r4 = 1
                r8.label = r4
                java.lang.Object r3 = r1.hasNext(r3)
                if (r3 != r0) goto L_0x005f
                return r0
            L_0x005f:
                r6 = r0
                r0 = r8
                r8 = r3
                r3 = r6
            L_0x0063:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                if (r8 == 0) goto L_0x00a9
                java.lang.Object r8 = r1.next()
                kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super java.lang.Boolean>, java.lang.Object> r4 = r0.$predicate
                r0.L$0 = r2
                r0.L$1 = r1
                r0.L$2 = r8
                r5 = 2
                r0.label = r5
                java.lang.Object r4 = r4.invoke(r8, r0)
                if (r4 != r3) goto L_0x0081
                return r3
            L_0x0081:
                r6 = r1
                r1 = r8
                r8 = r4
                r4 = r3
                r3 = r6
            L_0x0086:
                java.lang.Boolean r8 = (java.lang.Boolean) r8
                boolean r8 = r8.booleanValue()
                if (r8 != 0) goto L_0x0091
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            L_0x0091:
                r8 = r0
                kotlin.coroutines.Continuation r8 = (kotlin.coroutines.Continuation) r8
                r0.L$0 = r2
                r0.L$1 = r3
                r5 = 0
                r0.L$2 = r5
                r5 = 3
                r0.label = r5
                java.lang.Object r8 = r2.send(r1, r8)
                if (r8 != r4) goto L_0x00a5
                return r4
            L_0x00a5:
                r8 = r0
                r1 = r3
                r0 = r4
                goto L_0x004e
            L_0x00a9:
                kotlin.Unit r8 = kotlin.Unit.INSTANCE
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.ag.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel d(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        ReceiveChannel d2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        d2 = d(receiveChannel, coroutineContext, function2);
        return d2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel d(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new ag(receiveChannel, function2, null));
        return produce;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0070 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007c A[Catch: Throwable -> 0x009c, all -> 0x009a, TryCatch #5 {Throwable -> 0x009c, all -> 0x009a, blocks: (B:28:0x0074, B:30:0x007c, B:34:0x0094), top: B:50:0x0074 }] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0094 A[Catch: Throwable -> 0x009c, all -> 0x009a, TRY_LEAVE, TryCatch #5 {Throwable -> 0x009c, all -> 0x009a, blocks: (B:28:0x0074, B:30:0x007c, B:34:0x0094), top: B:50:0x0074 }] */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <E, C extends kotlinx.coroutines.channels.SendChannel<? super E>> java.lang.Object b(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r6, @org.jetbrains.annotations.NotNull C r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super C> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.e.ah
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.e$ah r0 = (kotlinx.coroutines.channels.e.ah) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$ah r0 = new kotlinx.coroutines.channels.e$ah
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0059;
                case 1: goto L_0x003f;
                case 2: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r2 = (kotlinx.coroutines.channels.SendChannel) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0055, all -> 0x0052
            r8 = r6
            r6 = r7
            goto L_0x0061
        L_0x003f:
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            kotlinx.coroutines.channels.SendChannel r2 = (kotlinx.coroutines.channels.SendChannel) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0055, all -> 0x0052
            r5 = r8
            r8 = r7
            r7 = r5
            goto L_0x0074
        L_0x0052:
            r6 = move-exception
            r8 = r7
            goto L_0x00a7
        L_0x0055:
            r6 = move-exception
            r3 = r6
            r6 = r7
            goto L_0x00a6
        L_0x0059:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: Throwable -> 0x00a4, all -> 0x00a0
            r2 = r7
        L_0x0061:
            r0.L$0 = r2     // Catch: Throwable -> 0x00a4, all -> 0x00a0
            r0.L$1 = r6     // Catch: Throwable -> 0x00a4, all -> 0x00a0
            r0.L$2 = r8     // Catch: Throwable -> 0x00a4, all -> 0x00a0
            r7 = 1
            r0.label = r7     // Catch: Throwable -> 0x00a4, all -> 0x00a0
            java.lang.Object r7 = r8.hasNext(r0)     // Catch: Throwable -> 0x00a4, all -> 0x00a0
            if (r7 != r1) goto L_0x0071
            return r1
        L_0x0071:
            r5 = r8
            r8 = r6
            r6 = r5
        L_0x0074:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: Throwable -> 0x009c, all -> 0x009a
            boolean r7 = r7.booleanValue()     // Catch: Throwable -> 0x009c, all -> 0x009a
            if (r7 == 0) goto L_0x0094
            java.lang.Object r7 = r6.next()     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.L$0 = r2     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.L$1 = r8     // Catch: Throwable -> 0x009c, all -> 0x009a
            r0.L$2 = r6     // Catch: Throwable -> 0x009c, all -> 0x009a
            r4 = 2
            r0.label = r4     // Catch: Throwable -> 0x009c, all -> 0x009a
            java.lang.Object r7 = r2.send(r7, r0)     // Catch: Throwable -> 0x009c, all -> 0x009a
            if (r7 != r1) goto L_0x0090
            return r1
        L_0x0090:
            r5 = r8
            r8 = r6
            r6 = r5
            goto L_0x0061
        L_0x0094:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x009c, all -> 0x009a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            return r2
        L_0x009a:
            r6 = move-exception
            goto L_0x00a7
        L_0x009c:
            r6 = move-exception
            r3 = r6
            r6 = r8
            goto L_0x00a6
        L_0x00a0:
            r7 = move-exception
            r8 = r6
            r6 = r7
            goto L_0x00a7
        L_0x00a4:
            r7 = move-exception
            r3 = r7
        L_0x00a6:
            throw r3     // Catch: all -> 0x00a0
        L_0x00a7:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r8, r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.b(kotlinx.coroutines.channels.ReceiveChannel, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005c A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0069 A[Catch: Throwable -> 0x003f, all -> 0x003d, TryCatch #5 {Throwable -> 0x003f, all -> 0x003d, blocks: (B:13:0x0039, B:24:0x0061, B:26:0x0069, B:27:0x0073), top: B:46:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0073 A[Catch: Throwable -> 0x003f, all -> 0x003d, TRY_LEAVE, TryCatch #5 {Throwable -> 0x003f, all -> 0x003d, blocks: (B:13:0x0039, B:24:0x0061, B:26:0x0069, B:27:0x0073), top: B:46:0x0039 }] */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <E, C extends java.util.Collection<? super E>> java.lang.Object b(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends E> r5, @org.jetbrains.annotations.NotNull C r6, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super C> r7) {
        /*
            boolean r0 = r7 instanceof kotlinx.coroutines.channels.e.ai
            if (r0 == 0) goto L_0x0014
            r0 = r7
            kotlinx.coroutines.channels.e$ai r0 = (kotlinx.coroutines.channels.e.ai) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r7 = r0.label
            int r7 = r7 - r2
            r0.label = r7
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$ai r0 = new kotlinx.coroutines.channels.e$ai
            r0.<init>(r7)
        L_0x0019:
            java.lang.Object r7 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0043;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002d:
            java.lang.Object r5 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r5 = (kotlinx.coroutines.channels.ChannelIterator) r5
            java.lang.Object r6 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r6 = (kotlinx.coroutines.channels.ReceiveChannel) r6
            java.lang.Object r2 = r0.L$0
            java.util.Collection r2 = (java.util.Collection) r2
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: Throwable -> 0x003f, all -> 0x003d
            goto L_0x0061
        L_0x003d:
            r5 = move-exception
            goto L_0x0088
        L_0x003f:
            r5 = move-exception
            r3 = r5
            r5 = r6
            goto L_0x0087
        L_0x0043:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.ChannelIterator r7 = r5.iterator()     // Catch: Throwable -> 0x0085, all -> 0x0080
            r4 = r7
            r7 = r5
            r5 = r4
        L_0x004d:
            r0.L$0 = r6     // Catch: Throwable -> 0x007c, all -> 0x0079
            r0.L$1 = r7     // Catch: Throwable -> 0x007c, all -> 0x0079
            r0.L$2 = r5     // Catch: Throwable -> 0x007c, all -> 0x0079
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x007c, all -> 0x0079
            java.lang.Object r2 = r5.hasNext(r0)     // Catch: Throwable -> 0x007c, all -> 0x0079
            if (r2 != r1) goto L_0x005d
            return r1
        L_0x005d:
            r4 = r2
            r2 = r6
            r6 = r7
            r7 = r4
        L_0x0061:
            java.lang.Boolean r7 = (java.lang.Boolean) r7     // Catch: Throwable -> 0x003f, all -> 0x003d
            boolean r7 = r7.booleanValue()     // Catch: Throwable -> 0x003f, all -> 0x003d
            if (r7 == 0) goto L_0x0073
            java.lang.Object r7 = r5.next()     // Catch: Throwable -> 0x003f, all -> 0x003d
            r2.add(r7)     // Catch: Throwable -> 0x003f, all -> 0x003d
            r7 = r6
            r6 = r2
            goto L_0x004d
        L_0x0073:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x003f, all -> 0x003d
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            return r2
        L_0x0079:
            r5 = move-exception
            r6 = r7
            goto L_0x0088
        L_0x007c:
            r5 = move-exception
            r3 = r5
            r5 = r7
            goto L_0x0087
        L_0x0080:
            r6 = move-exception
            r4 = r6
            r6 = r5
            r5 = r4
            goto L_0x0088
        L_0x0085:
            r6 = move-exception
            r3 = r6
        L_0x0087:
            throw r3     // Catch: all -> 0x0080
        L_0x0088:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r6, r3)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.b(kotlinx.coroutines.channels.ReceiveChannel, java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object g(ReceiveChannel receiveChannel, Continuation continuation) {
        return ChannelsKt.toMap(receiveChannel, new LinkedHashMap(), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006a A[Catch: Throwable -> 0x0040, all -> 0x003d, TryCatch #5 {Throwable -> 0x0040, all -> 0x003d, blocks: (B:13:0x0039, B:24:0x0062, B:26:0x006a, B:27:0x007e), top: B:46:0x0039 }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007e A[Catch: Throwable -> 0x0040, all -> 0x003d, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0040, all -> 0x003d, blocks: (B:13:0x0039, B:24:0x0062, B:26:0x006a, B:27:0x007e), top: B:46:0x0039 }] */
    @kotlin.PublishedApi
    @org.jetbrains.annotations.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final <K, V, M extends java.util.Map<? super K, ? super V>> java.lang.Object a(@org.jetbrains.annotations.NotNull kotlinx.coroutines.channels.ReceiveChannel<? extends kotlin.Pair<? extends K, ? extends V>> r6, @org.jetbrains.annotations.NotNull M r7, @org.jetbrains.annotations.NotNull kotlin.coroutines.Continuation<? super M> r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.e.aj
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.e$aj r0 = (kotlinx.coroutines.channels.e.aj) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$aj r0 = new kotlinx.coroutines.channels.e$aj
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0044;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L_0x002d:
            java.lang.Object r6 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r6 = (kotlinx.coroutines.channels.ChannelIterator) r6
            java.lang.Object r7 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r7 = (kotlinx.coroutines.channels.ReceiveChannel) r7
            java.lang.Object r2 = r0.L$0
            java.util.Map r2 = (java.util.Map) r2
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0040, all -> 0x003d
            goto L_0x0062
        L_0x003d:
            r6 = move-exception
            goto L_0x0093
        L_0x0040:
            r6 = move-exception
            r3 = r6
            r6 = r7
            goto L_0x0092
        L_0x0044:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ChannelIterator r8 = r6.iterator()     // Catch: Throwable -> 0x0090, all -> 0x008b
            r5 = r8
            r8 = r6
            r6 = r5
        L_0x004e:
            r0.L$0 = r7     // Catch: Throwable -> 0x0087, all -> 0x0084
            r0.L$1 = r8     // Catch: Throwable -> 0x0087, all -> 0x0084
            r0.L$2 = r6     // Catch: Throwable -> 0x0087, all -> 0x0084
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x0087, all -> 0x0084
            java.lang.Object r2 = r6.hasNext(r0)     // Catch: Throwable -> 0x0087, all -> 0x0084
            if (r2 != r1) goto L_0x005e
            return r1
        L_0x005e:
            r5 = r2
            r2 = r7
            r7 = r8
            r8 = r5
        L_0x0062:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: Throwable -> 0x0040, all -> 0x003d
            boolean r8 = r8.booleanValue()     // Catch: Throwable -> 0x0040, all -> 0x003d
            if (r8 == 0) goto L_0x007e
            java.lang.Object r8 = r6.next()     // Catch: Throwable -> 0x0040, all -> 0x003d
            kotlin.Pair r8 = (kotlin.Pair) r8     // Catch: Throwable -> 0x0040, all -> 0x003d
            java.lang.Object r4 = r8.getFirst()     // Catch: Throwable -> 0x0040, all -> 0x003d
            java.lang.Object r8 = r8.getSecond()     // Catch: Throwable -> 0x0040, all -> 0x003d
            r2.put(r4, r8)     // Catch: Throwable -> 0x0040, all -> 0x003d
            r8 = r7
            r7 = r2
            goto L_0x004e
        L_0x007e:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x0040, all -> 0x003d
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            return r2
        L_0x0084:
            r6 = move-exception
            r7 = r8
            goto L_0x0093
        L_0x0087:
            r6 = move-exception
            r3 = r6
            r6 = r8
            goto L_0x0092
        L_0x008b:
            r7 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x0093
        L_0x0090:
            r7 = move-exception
            r3 = r7
        L_0x0092:
            throw r3     // Catch: all -> 0x008b
        L_0x0093:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r7, r3)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.a(kotlinx.coroutines.channels.ReceiveChannel, java.util.Map, kotlin.coroutines.Continuation):java.lang.Object");
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object h(ReceiveChannel receiveChannel, Continuation continuation) {
        return ChannelsKt.toCollection(receiveChannel, new ArrayList(), continuation);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ Object i(ReceiveChannel receiveChannel, Continuation continuation) {
        return ChannelsKt.toMutableSet(receiveChannel, continuation);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$flatMap$1", f = "Deprecated.kt", i = {0, 1, 2}, l = {279, 280, 280}, m = "invokeSuspend", n = {"$this$produce", "$this$produce", "$this$produce"}, s = {"L$0", "L$0", "L$0"})
    /* loaded from: classes5.dex */
    public static final class s extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ReceiveChannel<E> $this_flatMap;
        final /* synthetic */ Function2<E, Continuation<? super ReceiveChannel<? extends R>>, Object> $transform;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        s(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super ReceiveChannel<? extends R>>, ? extends Object> function2, Continuation<? super s> continuation) {
            super(2, continuation);
            this.$this_flatMap = receiveChannel;
            this.$transform = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super R> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((s) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            s sVar = new s(this.$this_flatMap, this.$transform, continuation);
            sVar.L$0 = obj;
            return sVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0059 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:0x005a  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0066  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x008f A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0090  */
        /* JADX WARN: Removed duplicated region for block: B:23:0x0093  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                switch(r1) {
                    case 0: goto L_0x003a;
                    case 1: goto L_0x002c;
                    case 2: goto L_0x001e;
                    case 3: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L_0x0011:
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r2 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r8
                goto L_0x0049
            L_0x001e:
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r2 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
                kotlin.ResultKt.throwOnFailure(r9)
                r3 = r0
                r0 = r8
                goto L_0x007a
            L_0x002c:
                java.lang.Object r1 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r1 = (kotlinx.coroutines.channels.ChannelIterator) r1
                java.lang.Object r2 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r2 = (kotlinx.coroutines.channels.ProducerScope) r2
                kotlin.ResultKt.throwOnFailure(r9)
                r3 = r0
                r0 = r8
                goto L_0x005e
            L_0x003a:
                kotlin.ResultKt.throwOnFailure(r9)
                java.lang.Object r9 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
                kotlinx.coroutines.channels.ReceiveChannel<E> r1 = r8.$this_flatMap
                kotlinx.coroutines.channels.ChannelIterator r1 = r1.iterator()
                r2 = r9
                r9 = r8
            L_0x0049:
                r3 = r9
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                r9.L$0 = r2
                r9.L$1 = r1
                r4 = 1
                r9.label = r4
                java.lang.Object r3 = r1.hasNext(r3)
                if (r3 != r0) goto L_0x005a
                return r0
            L_0x005a:
                r7 = r0
                r0 = r9
                r9 = r3
                r3 = r7
            L_0x005e:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L_0x0093
                java.lang.Object r9 = r1.next()
                kotlin.jvm.functions.Function2<E, kotlin.coroutines.Continuation<? super kotlinx.coroutines.channels.ReceiveChannel<? extends R>>, java.lang.Object> r4 = r0.$transform
                r0.L$0 = r2
                r0.L$1 = r1
                r5 = 2
                r0.label = r5
                java.lang.Object r9 = r4.invoke(r9, r0)
                if (r9 != r3) goto L_0x007a
                return r3
            L_0x007a:
                kotlinx.coroutines.channels.ReceiveChannel r9 = (kotlinx.coroutines.channels.ReceiveChannel) r9
                r4 = r2
                kotlinx.coroutines.channels.SendChannel r4 = (kotlinx.coroutines.channels.SendChannel) r4
                r5 = r0
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r0.L$0 = r2
                r0.L$1 = r1
                r6 = 3
                r0.label = r6
                java.lang.Object r9 = kotlinx.coroutines.channels.ChannelsKt.toChannel(r9, r4, r5)
                if (r9 != r3) goto L_0x0090
                return r3
            L_0x0090:
                r9 = r0
                r0 = r3
                goto L_0x0049
            L_0x0093:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.s.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel e(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        ReceiveChannel e;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        e = e(receiveChannel, coroutineContext, function2);
        return e;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel e(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new s(receiveChannel, function2, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$map$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 2, 2}, l = {415, 288, 288}, m = "invokeSuspend", n = {"$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv", "$this$produce", "$this$consume$iv$iv"}, s = {"L$0", "L$2", "L$0", "L$2", "L$0", "L$2"})
    /* loaded from: classes5.dex */
    public static final class x extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ReceiveChannel<E> $this_map;
        final /* synthetic */ Function2<E, Continuation<? super R>, Object> $transform;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        x(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2, Continuation<? super x> continuation) {
            super(2, continuation);
            this.$this_map = receiveChannel;
            this.$transform = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super R> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((x) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            x xVar = new x(this.$this_map, this.$transform, continuation);
            xVar.L$0 = obj;
            return xVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x0090 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0091  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x009d A[Catch: Throwable -> 0x00d9, all -> 0x00d7, TryCatch #1 {all -> 0x00d7, blocks: (B:7:0x0022, B:17:0x0063, B:20:0x0075, B:21:0x007f, B:25:0x0095, B:27:0x009d, B:31:0x00b7, B:35:0x00cf, B:41:0x00db), top: B:44:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00ca A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00cb  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x00cf A[Catch: Throwable -> 0x00d9, all -> 0x00d7, TRY_LEAVE, TryCatch #1 {all -> 0x00d7, blocks: (B:7:0x0022, B:17:0x0063, B:20:0x0075, B:21:0x007f, B:25:0x0095, B:27:0x009d, B:31:0x00b7, B:35:0x00cf, B:41:0x00db), top: B:44:0x0007 }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                Method dump skipped, instructions count: 236
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.x.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel f(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.map(receiveChannel, coroutineContext, function2);
    }

    @PublishedApi
    @NotNull
    public static final <E, R> ReceiveChannel<R> f(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super Continuation<? super R>, ? extends Object> function2) {
        ReceiveChannel<R> produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new x(receiveChannel, function2, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H\u008a@"}, d2 = {"<anonymous>", "", "E", "R", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$mapIndexed$1", f = "Deprecated.kt", i = {0, 0, 1, 2}, l = {296, 297, 297}, m = "invokeSuspend", n = {"$this$produce", MiSoundBoxCommandExtras.INDEX, "$this$produce", "$this$produce"}, s = {"L$0", "I$0", "L$0", "L$0"})
    /* loaded from: classes5.dex */
    public static final class y extends SuspendLambda implements Function2<ProducerScope<? super R>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ReceiveChannel<E> $this_mapIndexed;
        final /* synthetic */ Function3<Integer, E, Continuation<? super R>, Object> $transform;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        y(ReceiveChannel<? extends E> receiveChannel, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> function3, Continuation<? super y> continuation) {
            super(2, continuation);
            this.$this_mapIndexed = receiveChannel;
            this.$transform = function3;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super R> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((y) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            y yVar = new y(this.$this_mapIndexed, this.$transform, continuation);
            yVar.L$0 = obj;
            return yVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0072 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0073  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0080  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00b4 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:23:0x00b5  */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00ba  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                Method dump skipped, instructions count: 202
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.y.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel b(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3);
    }

    @PublishedApi
    @NotNull
    public static final <E, R> ReceiveChannel<R> b(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> function3) {
        ReceiveChannel<R> produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new y(receiveChannel, function3, null));
        return produce;
    }

    public static /* synthetic */ ReceiveChannel c(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3, int i2, Object obj) {
        ReceiveChannel c2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        c2 = c(receiveChannel, coroutineContext, function3);
        return c2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel c(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function3 function3) {
        return ChannelsKt.filterNotNull(ChannelsKt.mapIndexed(receiveChannel, coroutineContext, function3));
    }

    public static /* synthetic */ ReceiveChannel g(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        ReceiveChannel g2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        g2 = g(receiveChannel, coroutineContext, function2);
        return g2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel g(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2) {
        return ChannelsKt.filterNotNull(ChannelsKt.map(receiveChannel, coroutineContext, function2));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00040\u0003H\u008a@"}, d2 = {"<anonymous>", "", "E", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/collections/IndexedValue;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$withIndex$1", f = "Deprecated.kt", i = {0, 0, 1}, l = {313, 314}, m = "invokeSuspend", n = {"$this$produce", MiSoundBoxCommandExtras.INDEX, "$this$produce"}, s = {"L$0", "I$0", "L$0"})
    /* loaded from: classes5.dex */
    public static final class ak extends SuspendLambda implements Function2<ProducerScope<? super IndexedValue<? extends E>>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ReceiveChannel<E> $this_withIndex;
        int I$0;
        private /* synthetic */ Object L$0;
        Object L$1;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        ak(ReceiveChannel<? extends E> receiveChannel, Continuation<? super ak> continuation) {
            super(2, continuation);
            this.$this_withIndex = receiveChannel;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super IndexedValue<? extends E>> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((ak) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            ak akVar = new ak(this.$this_withIndex, continuation);
            akVar.L$0 = obj;
            return akVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:11:0x0056 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:12:0x0057  */
        /* JADX WARN: Removed duplicated region for block: B:15:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:19:0x0087  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r9) {
            /*
                r8 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r8.label
                switch(r1) {
                    case 0: goto L_0x0033;
                    case 1: goto L_0x0023;
                    case 2: goto L_0x0011;
                    default: goto L_0x0009;
                }
            L_0x0009:
                java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
                java.lang.String r0 = "call to 'resume' before 'invoke' with coroutine"
                r9.<init>(r0)
                throw r9
            L_0x0011:
                int r1 = r8.I$0
                java.lang.Object r2 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
                java.lang.Object r3 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
                kotlin.ResultKt.throwOnFailure(r9)
                r9 = r8
                r7 = r3
                r3 = r0
                r0 = r7
                goto L_0x0044
            L_0x0023:
                int r1 = r8.I$0
                java.lang.Object r2 = r8.L$1
                kotlinx.coroutines.channels.ChannelIterator r2 = (kotlinx.coroutines.channels.ChannelIterator) r2
                java.lang.Object r3 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
                kotlin.ResultKt.throwOnFailure(r9)
                r4 = r0
                r0 = r8
                goto L_0x005c
            L_0x0033:
                kotlin.ResultKt.throwOnFailure(r9)
                java.lang.Object r9 = r8.L$0
                kotlinx.coroutines.channels.ProducerScope r9 = (kotlinx.coroutines.channels.ProducerScope) r9
                r1 = 0
                kotlinx.coroutines.channels.ReceiveChannel<E> r2 = r8.$this_withIndex
                kotlinx.coroutines.channels.ChannelIterator r2 = r2.iterator()
                r3 = r0
                r0 = r9
                r9 = r8
            L_0x0044:
                r4 = r9
                kotlin.coroutines.Continuation r4 = (kotlin.coroutines.Continuation) r4
                r9.L$0 = r0
                r9.L$1 = r2
                r9.I$0 = r1
                r5 = 1
                r9.label = r5
                java.lang.Object r4 = r2.hasNext(r4)
                if (r4 != r3) goto L_0x0057
                return r3
            L_0x0057:
                r7 = r0
                r0 = r9
                r9 = r4
                r4 = r3
                r3 = r7
            L_0x005c:
                java.lang.Boolean r9 = (java.lang.Boolean) r9
                boolean r9 = r9.booleanValue()
                if (r9 == 0) goto L_0x0087
                java.lang.Object r9 = r2.next()
                kotlin.collections.IndexedValue r5 = new kotlin.collections.IndexedValue
                int r6 = r1 + 1
                r5.<init>(r1, r9)
                r9 = r0
                kotlin.coroutines.Continuation r9 = (kotlin.coroutines.Continuation) r9
                r0.L$0 = r3
                r0.L$1 = r2
                r0.I$0 = r6
                r1 = 2
                r0.label = r1
                java.lang.Object r9 = r3.send(r5, r9)
                if (r9 != r4) goto L_0x0082
                return r4
            L_0x0082:
                r9 = r0
                r0 = r3
                r3 = r4
                r1 = r6
                goto L_0x0044
            L_0x0087:
                kotlin.Unit r9 = kotlin.Unit.INSTANCE
                return r9
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.ak.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, int i2, Object obj) {
        ReceiveChannel a2;
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        a2 = a(receiveChannel, coroutineContext);
        return a2;
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, CoroutineContext coroutineContext) {
        ReceiveChannel produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new ak(receiveChannel, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u0004\n\u0002\b\u0003\u0010\u0000\u001a\u0002H\u0001\"\u0004\b\u0000\u0010\u00012\u0006\u0010\u0002\u001a\u0002H\u0001H\u008a@"}, d2 = {"<anonymous>", "E", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinct$1", f = "Deprecated.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* renamed from: kotlinx.coroutines.channels.e$e  reason: collision with other inner class name */
    /* loaded from: classes5.dex */
    public static final class C0372e extends SuspendLambda implements Function2<E, Continuation<? super E>, Object> {
        /* synthetic */ Object L$0;
        int label;

        C0372e(Continuation<? super C0372e> continuation) {
            super(2, continuation);
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(E e, @Nullable Continuation<? super E> continuation) {
            return ((C0372e) create(e, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            C0372e eVar = new C0372e(continuation);
            eVar.L$0 = obj;
            return eVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return this.L$0;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel b(ReceiveChannel receiveChannel) {
        ReceiveChannel h2;
        h2 = h(receiveChannel, null, new C0372e(null), 1, null);
        return h2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u0004H\u008a@"}, d2 = {"<anonymous>", "", "E", "K", "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$distinctBy$1", f = "Deprecated.kt", i = {0, 0, 1, 1, 1, 2, 2, 2}, l = {326, 327, 329}, m = "invokeSuspend", n = {"$this$produce", "keys", "$this$produce", "keys", "e", "$this$produce", "keys", "k"}, s = {"L$0", "L$1", "L$0", "L$1", "L$3", "L$0", "L$1", "L$3"})
    /* loaded from: classes5.dex */
    public static final class f extends SuspendLambda implements Function2<ProducerScope<? super E>, Continuation<? super Unit>, Object> {
        final /* synthetic */ Function2<E, Continuation<? super K>, Object> $selector;
        final /* synthetic */ ReceiveChannel<E> $this_distinctBy;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        f(ReceiveChannel<? extends E> receiveChannel, Function2<? super E, ? super Continuation<? super K>, ? extends Object> function2, Continuation<? super f> continuation) {
            super(2, continuation);
            this.$this_distinctBy = receiveChannel;
            this.$selector = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super E> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((f) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            f fVar = new f(this.$this_distinctBy, this.$selector, continuation);
            fVar.L$0 = obj;
            return fVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0083 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:13:0x0084  */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0090  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x00b3  */
        /* JADX WARN: Removed duplicated region for block: B:26:0x00d1  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00d4  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r10) {
            /*
                Method dump skipped, instructions count: 228
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.f.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel h(ReceiveChannel receiveChannel, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.distinctBy(receiveChannel, coroutineContext, function2);
    }

    @PublishedApi
    @NotNull
    public static final <E, K> ReceiveChannel<E> h(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super Continuation<? super K>, ? extends Object> function2) {
        ReceiveChannel<E> produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumes(receiveChannel), new f(receiveChannel, function2, null));
        return produce;
    }

    @PublishedApi
    @Nullable
    public static final <E> Object j(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull Continuation<? super Set<E>> continuation) {
        return ChannelsKt.toCollection(receiveChannel, new LinkedHashSet(), continuation);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0035  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object k(kotlinx.coroutines.channels.ReceiveChannel r4, kotlin.coroutines.Continuation r5) {
        /*
            boolean r0 = r5 instanceof kotlinx.coroutines.channels.e.a
            if (r0 == 0) goto L_0x0014
            r0 = r5
            kotlinx.coroutines.channels.e$a r0 = (kotlinx.coroutines.channels.e.a) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r5 = r0.label
            int r5 = r5 - r2
            r0.label = r5
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$a r0 = new kotlinx.coroutines.channels.e$a
            r0.<init>(r5)
        L_0x0019:
            java.lang.Object r5 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 0
            switch(r2) {
                case 0: goto L_0x0035;
                case 1: goto L_0x002d;
                default: goto L_0x0025;
            }
        L_0x0025:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L_0x002d:
            java.lang.Object r4 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r4 = (kotlinx.coroutines.channels.ReceiveChannel) r4
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: Throwable -> 0x004e, all -> 0x004c
            goto L_0x0048
        L_0x0035:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.channels.ChannelIterator r5 = r4.iterator()     // Catch: Throwable -> 0x004e, all -> 0x004c
            r0.L$0 = r4     // Catch: Throwable -> 0x004e, all -> 0x004c
            r2 = 1
            r0.label = r2     // Catch: Throwable -> 0x004e, all -> 0x004c
            java.lang.Object r5 = r5.hasNext(r0)     // Catch: Throwable -> 0x004e, all -> 0x004c
            if (r5 != r1) goto L_0x0048
            return r1
        L_0x0048:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r3)
            return r5
        L_0x004c:
            r5 = move-exception
            goto L_0x0051
        L_0x004e:
            r5 = move-exception
            r3 = r5
            throw r3     // Catch: all -> 0x004c
        L_0x0051:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r4, r3)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.k(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0061 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006d A[Catch: Throwable -> 0x0040, all -> 0x003e, TryCatch #5 {Throwable -> 0x0040, all -> 0x003e, blocks: (B:13:0x003a, B:24:0x0065, B:26:0x006d, B:27:0x0077), top: B:46:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0077 A[Catch: Throwable -> 0x0040, all -> 0x003e, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0040, all -> 0x003e, blocks: (B:13:0x003a, B:24:0x0065, B:26:0x006d, B:27:0x0077), top: B:46:0x003a }] */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object l(kotlinx.coroutines.channels.ReceiveChannel r7, kotlin.coroutines.Continuation r8) {
        /*
            boolean r0 = r8 instanceof kotlinx.coroutines.channels.e.d
            if (r0 == 0) goto L_0x0014
            r0 = r8
            kotlinx.coroutines.channels.e$d r0 = (kotlinx.coroutines.channels.e.d) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r8 = r0.label
            int r8 = r8 - r2
            r0.label = r8
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$d r0 = new kotlinx.coroutines.channels.e$d
            r0.<init>(r8)
        L_0x0019:
            java.lang.Object r8 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0044;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L_0x002e:
            java.lang.Object r7 = r0.L$2
            kotlinx.coroutines.channels.ChannelIterator r7 = (kotlinx.coroutines.channels.ChannelIterator) r7
            java.lang.Object r2 = r0.L$1
            kotlinx.coroutines.channels.ReceiveChannel r2 = (kotlinx.coroutines.channels.ReceiveChannel) r2
            java.lang.Object r5 = r0.L$0
            kotlin.jvm.internal.Ref$IntRef r5 = (kotlin.jvm.internal.Ref.IntRef) r5
            kotlin.ResultKt.throwOnFailure(r8)     // Catch: Throwable -> 0x0040, all -> 0x003e
            goto L_0x0065
        L_0x003e:
            r7 = move-exception
            goto L_0x0091
        L_0x0040:
            r7 = move-exception
            r4 = r7
            r7 = r2
            goto L_0x0090
        L_0x0044:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlin.jvm.internal.Ref$IntRef r8 = new kotlin.jvm.internal.Ref$IntRef
            r8.<init>()
            kotlinx.coroutines.channels.ChannelIterator r2 = r7.iterator()     // Catch: Throwable -> 0x008e, all -> 0x008a
            r5 = r8
            r8 = r7
            r7 = r2
        L_0x0053:
            r0.L$0 = r5     // Catch: Throwable -> 0x0086, all -> 0x0083
            r0.L$1 = r8     // Catch: Throwable -> 0x0086, all -> 0x0083
            r0.L$2 = r7     // Catch: Throwable -> 0x0086, all -> 0x0083
            r0.label = r3     // Catch: Throwable -> 0x0086, all -> 0x0083
            java.lang.Object r2 = r7.hasNext(r0)     // Catch: Throwable -> 0x0086, all -> 0x0083
            if (r2 != r1) goto L_0x0062
            return r1
        L_0x0062:
            r6 = r2
            r2 = r8
            r8 = r6
        L_0x0065:
            java.lang.Boolean r8 = (java.lang.Boolean) r8     // Catch: Throwable -> 0x0040, all -> 0x003e
            boolean r8 = r8.booleanValue()     // Catch: Throwable -> 0x0040, all -> 0x003e
            if (r8 == 0) goto L_0x0077
            r7.next()     // Catch: Throwable -> 0x0040, all -> 0x003e
            int r8 = r5.element     // Catch: Throwable -> 0x0040, all -> 0x003e
            int r8 = r8 + r3
            r5.element = r8     // Catch: Throwable -> 0x0040, all -> 0x003e
            r8 = r2
            goto L_0x0053
        L_0x0077:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch: Throwable -> 0x0040, all -> 0x003e
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            int r7 = r5.element
            java.lang.Integer r7 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r7)
            return r7
        L_0x0083:
            r7 = move-exception
            r2 = r8
            goto L_0x0091
        L_0x0086:
            r7 = move-exception
            r4 = r7
            r7 = r8
            goto L_0x0090
        L_0x008a:
            r8 = move-exception
            r2 = r7
            r7 = r8
            goto L_0x0091
        L_0x008e:
            r8 = move-exception
            r4 = r8
        L_0x0090:
            throw r4     // Catch: all -> 0x008a
        L_0x0091:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r2, r4)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.l(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008d A[Catch: Throwable -> 0x0060, all -> 0x005a, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0060, all -> 0x005a, blocks: (B:19:0x0056, B:29:0x0081, B:33:0x008d), top: B:58:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b3 A[Catch: Throwable -> 0x0045, all -> 0x0040, TRY_LEAVE, TryCatch #6 {all -> 0x0040, Throwable -> 0x0045, blocks: (B:13:0x003b, B:39:0x00ab, B:41:0x00b3), top: B:56:0x003b }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c1  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object a(kotlinx.coroutines.channels.ReceiveChannel r7, java.util.Comparator r8, kotlin.coroutines.Continuation r9) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.a(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0089  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008d A[Catch: Throwable -> 0x0060, all -> 0x005a, TRY_ENTER, TRY_LEAVE, TryCatch #5 {Throwable -> 0x0060, all -> 0x005a, blocks: (B:19:0x0056, B:29:0x0081, B:33:0x008d), top: B:58:0x0056 }] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00a6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b3 A[Catch: Throwable -> 0x0045, all -> 0x0040, TRY_LEAVE, TryCatch #6 {all -> 0x0040, Throwable -> 0x0045, blocks: (B:13:0x003b, B:39:0x00ab, B:41:0x00b3), top: B:56:0x003b }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c1  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object b(kotlinx.coroutines.channels.ReceiveChannel r7, java.util.Comparator r8, kotlin.coroutines.Continuation r9) {
        /*
            Method dump skipped, instructions count: 216
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.b(kotlinx.coroutines.channels.ReceiveChannel, java.util.Comparator, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0026  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0050  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0051  */
    @kotlin.Deprecated(level = kotlin.DeprecationLevel.HIDDEN, message = "Binary compatibility")
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final /* synthetic */ java.lang.Object m(kotlinx.coroutines.channels.ReceiveChannel r5, kotlin.coroutines.Continuation r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.channels.e.ab
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.channels.e$ab r0 = (kotlinx.coroutines.channels.e.ab) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r6 = r0.label
            int r6 = r6 - r2
            r0.label = r6
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.e$ab r0 = new kotlinx.coroutines.channels.e$ab
            r0.<init>(r6)
        L_0x0019:
            java.lang.Object r6 = r0.result
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 1
            r4 = 0
            switch(r2) {
                case 0: goto L_0x0036;
                case 1: goto L_0x002e;
                default: goto L_0x0026;
            }
        L_0x0026:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L_0x002e:
            java.lang.Object r5 = r0.L$0
            kotlinx.coroutines.channels.ReceiveChannel r5 = (kotlinx.coroutines.channels.ReceiveChannel) r5
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: Throwable -> 0x005c, all -> 0x005a
            goto L_0x0048
        L_0x0036:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.channels.ChannelIterator r6 = r5.iterator()     // Catch: Throwable -> 0x005c, all -> 0x005a
            r0.L$0 = r5     // Catch: Throwable -> 0x005c, all -> 0x005a
            r0.label = r3     // Catch: Throwable -> 0x005c, all -> 0x005a
            java.lang.Object r6 = r6.hasNext(r0)     // Catch: Throwable -> 0x005c, all -> 0x005a
            if (r6 != r1) goto L_0x0048
            return r1
        L_0x0048:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: Throwable -> 0x005c, all -> 0x005a
            boolean r6 = r6.booleanValue()     // Catch: Throwable -> 0x005c, all -> 0x005a
            if (r6 != 0) goto L_0x0051
            goto L_0x0052
        L_0x0051:
            r3 = 0
        L_0x0052:
            java.lang.Boolean r6 = kotlin.coroutines.jvm.internal.Boxing.boxBoolean(r3)     // Catch: Throwable -> 0x005c, all -> 0x005a
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r4)
            return r6
        L_0x005a:
            r6 = move-exception
            goto L_0x005f
        L_0x005c:
            r6 = move-exception
            r4 = r6
            throw r4     // Catch: all -> 0x005a
        L_0x005f:
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r5, r4)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.m(kotlinx.coroutines.channels.ReceiveChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\n\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0010\u0003\u001a\u0004\u0018\u0001H\u0001H\u008a@"}, d2 = {"<anonymous>", "E", "", "it"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$requireNoNulls$1", f = "Deprecated.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, s = {})
    /* loaded from: classes5.dex */
    public static final class ac extends SuspendLambda implements Function2<E, Continuation<? super E>, Object> {
        final /* synthetic */ ReceiveChannel<E> $this_requireNoNulls;
        /* synthetic */ Object L$0;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        ac(ReceiveChannel<? extends E> receiveChannel, Continuation<? super ac> continuation) {
            super(2, continuation);
            this.$this_requireNoNulls = receiveChannel;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@Nullable E e, @Nullable Continuation<? super E> continuation) {
            return ((ac) create(e, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            ac acVar = new ac(this.$this_requireNoNulls, continuation);
            acVar.L$0 = obj;
            return acVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @Nullable
        public final Object invokeSuspend(@NotNull Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                Object obj2 = this.L$0;
                if (obj2 != null) {
                    return obj2;
                }
                throw new IllegalArgumentException("null element found in " + this.$this_requireNoNulls + '.');
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Left for binary compatibility")
    public static final /* synthetic */ ReceiveChannel c(ReceiveChannel receiveChannel) {
        ReceiveChannel f2;
        f2 = f(receiveChannel, null, new ac(receiveChannel, null), 1, null);
        return f2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u0002H\u00022\u0006\u0010\u0005\u001a\u0002H\u0003H\n"}, d2 = {"<anonymous>", "Lkotlin/Pair;", "E", "R", "t1", "t2"}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class al extends Lambda implements Function2<E, R, Pair<? extends E, ? extends R>> {
        public static final al a = new al();

        al() {
            super(2);
        }

        @NotNull
        /* renamed from: a */
        public final Pair<E, R> invoke(E e, R r) {
            return TuplesKt.to(e, r);
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Binary compatibility")
    public static final /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2) {
        ReceiveChannel a2;
        a2 = a(receiveChannel, receiveChannel2, null, al.a, 2, null);
        return a2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u0005H\u008a@"}, d2 = {"<anonymous>", "", "E", "R", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "Lkotlinx/coroutines/channels/ProducerScope;"}, k = 3, mv = {1, 5, 1}, xi = 48)
    @DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$zip$2", f = "Deprecated.kt", i = {0, 0, 0, 1, 1, 1, 1}, l = {415, 397, 399}, m = "invokeSuspend", n = {"$this$produce", "otherIterator", "$this$consume$iv$iv", "$this$produce", "otherIterator", "$this$consume$iv$iv", "element1"}, s = {"L$0", "L$1", "L$3", "L$0", "L$1", "L$3", "L$5"})
    /* loaded from: classes5.dex */
    public static final class am extends SuspendLambda implements Function2<ProducerScope<? super V>, Continuation<? super Unit>, Object> {
        final /* synthetic */ ReceiveChannel<R> $other;
        final /* synthetic */ ReceiveChannel<E> $this_zip;
        final /* synthetic */ Function2<E, R, V> $transform;
        private /* synthetic */ Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        Object L$5;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        /* JADX WARN: Multi-variable type inference failed */
        am(ReceiveChannel<? extends R> receiveChannel, ReceiveChannel<? extends E> receiveChannel2, Function2<? super E, ? super R, ? extends V> function2, Continuation<? super am> continuation) {
            super(2, continuation);
            this.$other = receiveChannel;
            this.$this_zip = receiveChannel2;
            this.$transform = function2;
        }

        @Nullable
        /* renamed from: a */
        public final Object invoke(@NotNull ProducerScope<? super V> producerScope, @Nullable Continuation<? super Unit> continuation) {
            return ((am) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @NotNull
        public final Continuation<Unit> create(@Nullable Object obj, @NotNull Continuation<?> continuation) {
            am amVar = new am(this.$other, this.$this_zip, this.$transform, continuation);
            amVar.L$0 = obj;
            return amVar;
        }

        /* JADX WARN: Removed duplicated region for block: B:23:0x00a2 A[RETURN] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x00a3  */
        /* JADX WARN: Removed duplicated region for block: B:27:0x00af A[Catch: Throwable -> 0x0103, all -> 0x0101, TryCatch #1 {all -> 0x0101, blocks: (B:7:0x0026, B:17:0x006d, B:20:0x0084, B:21:0x008d, B:25:0x00a7, B:27:0x00af, B:31:0x00cd, B:34:0x00d8, B:38:0x00f9, B:44:0x0105), top: B:47:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00d5  */
        /* JADX WARN: Removed duplicated region for block: B:34:0x00d8 A[Catch: Throwable -> 0x0103, all -> 0x0101, TryCatch #1 {all -> 0x0101, blocks: (B:7:0x0026, B:17:0x006d, B:20:0x0084, B:21:0x008d, B:25:0x00a7, B:27:0x00af, B:31:0x00cd, B:34:0x00d8, B:38:0x00f9, B:44:0x0105), top: B:47:0x0007 }] */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00f9 A[Catch: Throwable -> 0x0103, all -> 0x0101, TRY_LEAVE, TryCatch #1 {all -> 0x0101, blocks: (B:7:0x0026, B:17:0x006d, B:20:0x0084, B:21:0x008d, B:25:0x00a7, B:27:0x00af, B:31:0x00cd, B:34:0x00d8, B:38:0x00f9, B:44:0x0105), top: B:47:0x0007 }] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        @org.jetbrains.annotations.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object invokeSuspend(@org.jetbrains.annotations.NotNull java.lang.Object r11) {
            /*
                Method dump skipped, instructions count: 278
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.e.am.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }

    public static /* synthetic */ ReceiveChannel a(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, CoroutineContext coroutineContext, Function2 function2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            Dispatchers dispatchers = Dispatchers.INSTANCE;
            coroutineContext = Dispatchers.getUnconfined();
        }
        return ChannelsKt.zip(receiveChannel, receiveChannel2, coroutineContext, function2);
    }

    @PublishedApi
    @NotNull
    public static final <E, R, V> ReceiveChannel<V> a(@NotNull ReceiveChannel<? extends E> receiveChannel, @NotNull ReceiveChannel<? extends R> receiveChannel2, @NotNull CoroutineContext coroutineContext, @NotNull Function2<? super E, ? super R, ? extends V> function2) {
        ReceiveChannel<V> produce;
        produce = ProduceKt.produce(GlobalScope.INSTANCE, (r12 & 1) != 0 ? EmptyCoroutineContext.INSTANCE : coroutineContext, (r12 & 2) != 0 ? 0 : 0, (r12 & 4) != 0 ? CoroutineStart.DEFAULT : null, (r12 & 8) != 0 ? null : ChannelsKt.consumesAll(receiveChannel, receiveChannel2), new am(receiveChannel2, receiveChannel, function2, null));
        return produce;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* compiled from: Deprecated.kt */
    @Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\n"}, d2 = {"<anonymous>", "", "cause", ""}, k = 3, mv = {1, 5, 1}, xi = 48)
    /* loaded from: classes5.dex */
    public static final class b extends Lambda implements Function1<Throwable, Unit> {
        final /* synthetic */ ReceiveChannel<?> $this_consumes;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(ReceiveChannel<?> receiveChannel) {
            super(1);
            this.$this_consumes = receiveChannel;
        }

        @Override // kotlin.jvm.functions.Function1
        public /* synthetic */ Unit invoke(Throwable th) {
            a(th);
            return Unit.INSTANCE;
        }

        public final void a(@Nullable Throwable th) {
            ChannelsKt.cancelConsumed(this.$this_consumes, th);
        }
    }

    @PublishedApi
    @NotNull
    public static final Function1<Throwable, Unit> d(@NotNull ReceiveChannel<?> receiveChannel) {
        return new b(receiveChannel);
    }
}
