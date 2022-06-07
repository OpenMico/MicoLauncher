package kotlin.text;

import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.mi_soundbox_command_sdk.MiSoundBoxCommandExtras;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CharIterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Strings.kt */
@Metadata(d1 = {"\u0000|\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\r\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0019\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\u0011\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b!\u001a\u001c\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u000e\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u001f\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\rH\u0086\u0002\u001a\u0015\u0010\u000f\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\n\u001a\u0018\u0010\u0014\u001a\u00020\r*\u0004\u0018\u00010\u00022\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u0018\u0010\u0015\u001a\u00020\r*\u0004\u0018\u00010\u00022\b\u0010\u000b\u001a\u0004\u0018\u00010\u0002H\u0000\u001a\u001c\u0010\u0016\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010\u0016\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a:\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0019*\u00020\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001aE\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0019*\u00020\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\rH\u0002¢\u0006\u0002\b\u001e\u001a:\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\n\u0018\u00010\u0019*\u00020\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010 \u001a\u00020\r*\u00020\u00022\u0006\u0010!\u001a\u00020\u0006\u001a7\u0010\"\u001a\u0002H#\"\f\b\u0000\u0010$*\u00020\u0002*\u0002H#\"\u0004\b\u0001\u0010#*\u0002H$2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0&H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010'\u001a7\u0010(\u001a\u0002H#\"\f\b\u0000\u0010$*\u00020\u0002*\u0002H#\"\u0004\b\u0001\u0010#*\u0002H$2\f\u0010%\u001a\b\u0012\u0004\u0012\u0002H#0&H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010'\u001a&\u0010)\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a;\u0010)\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u001d\u001a\u00020\rH\u0002¢\u0006\u0002\b+\u001a&\u0010)\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020\n2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u0010-\u001a\u00020\u0006*\u00020\u00022\u0006\u0010.\u001a\u00020/2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u0010-\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\r\u00100\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u00101\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a\r\u00102\u001a\u00020\r*\u00020\u0002H\u0087\b\u001a \u00103\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a \u00104\u001a\u00020\r*\u0004\u0018\u00010\u0002H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a\r\u00105\u001a\u000206*\u00020\u0002H\u0086\u0002\u001a&\u00107\u001a\u00020\u0006*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00107\u001a\u00020\u0006*\u00020\u00022\u0006\u0010,\u001a\u00020\n2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a&\u00108\u001a\u00020\u0006*\u00020\u00022\u0006\u0010.\u001a\u00020/2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a,\u00108\u001a\u00020\u0006*\u00020\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0010\u00109\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u0002\u001a\u0010\u0010;\u001a\b\u0012\u0004\u0012\u00020\n0<*\u00020\u0002\u001a\u0015\u0010=\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0087\f\u001a\u000f\u0010>\u001a\u00020\n*\u0004\u0018\u00010\nH\u0087\b\u001a\u001c\u0010?\u001a\u00020\u0002*\u00020\u00022\u0006\u0010@\u001a\u00020\u00062\b\b\u0002\u0010A\u001a\u00020\u0011\u001a\u001c\u0010?\u001a\u00020\n*\u00020\n2\u0006\u0010@\u001a\u00020\u00062\b\b\u0002\u0010A\u001a\u00020\u0011\u001a\u001c\u0010B\u001a\u00020\u0002*\u00020\u00022\u0006\u0010@\u001a\u00020\u00062\b\b\u0002\u0010A\u001a\u00020\u0011\u001a\u001c\u0010B\u001a\u00020\n*\u00020\n2\u0006\u0010@\u001a\u00020\u00062\b\b\u0002\u0010A\u001a\u00020\u0011\u001aG\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00010:*\u00020\u00022\u000e\u0010D\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0E2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010F\u001a\u00020\u0006H\u0002¢\u0006\u0004\bG\u0010H\u001a=\u0010C\u001a\b\u0012\u0004\u0012\u00020\u00010:*\u00020\u00022\u0006\u0010D\u001a\u00020/2\b\b\u0002\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010F\u001a\u00020\u0006H\u0002¢\u0006\u0002\bG\u001a4\u0010I\u001a\u00020\r*\u00020\u00022\u0006\u0010J\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00062\u0006\u0010@\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\rH\u0000\u001a\u0012\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u0002\u001a\u0012\u0010L\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u0002\u001a\u001a\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006\u001a\u0012\u0010N\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u0001\u001a\u001d\u0010N\u001a\u00020\n*\u00020\n2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u0006H\u0087\b\u001a\u0015\u0010N\u001a\u00020\n*\u00020\n2\u0006\u0010O\u001a\u00020\u0001H\u0087\b\u001a\u0012\u0010P\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0002\u001a\u0012\u0010P\u001a\u00020\n*\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0002\u001a\u0012\u0010Q\u001a\u00020\u0002*\u00020\u00022\u0006\u0010R\u001a\u00020\u0002\u001a\u001a\u0010Q\u001a\u00020\u0002*\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0002\u001a\u0012\u0010Q\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u0002\u001a\u001a\u0010Q\u001a\u00020\n*\u00020\n2\u0006\u0010M\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0002\u001a.\u0010S\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0014\b\b\u0010T\u001a\u000e\u0012\u0004\u0012\u00020V\u0012\u0004\u0012\u00020\u00020UH\u0087\bø\u0001\u0000\u001a\u001d\u0010S\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010W\u001a\u00020\nH\u0087\b\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010X\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010Z\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010[\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010[\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a$\u0010\\\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\u0006\u0010W\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001d\u0010]\u001a\u00020\n*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010W\u001a\u00020\nH\u0087\b\u001a)\u0010^\u001a\u00020\n*\u00020\n2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00110UH\u0087\bø\u0001\u0000¢\u0006\u0002\b_\u001a)\u0010^\u001a\u00020\n*\u00020\n2\u0012\u0010T\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00020UH\u0087\bø\u0001\u0000¢\u0006\u0002\b`\u001a\"\u0010a\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00062\u0006\u0010W\u001a\u00020\u0002\u001a\u001a\u0010a\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u00012\u0006\u0010W\u001a\u00020\u0002\u001a%\u0010a\u001a\u00020\n*\u00020\n2\u0006\u0010\u001c\u001a\u00020\u00062\u0006\u0010*\u001a\u00020\u00062\u0006\u0010W\u001a\u00020\u0002H\u0087\b\u001a\u001d\u0010a\u001a\u00020\n*\u00020\n2\u0006\u0010O\u001a\u00020\u00012\u0006\u0010W\u001a\u00020\u0002H\u0087\b\u001a=\u0010b\u001a\b\u0012\u0004\u0012\u00020\n0<*\u00020\u00022\u0012\u0010D\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0E\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010F\u001a\u00020\u0006¢\u0006\u0002\u0010c\u001a0\u0010b\u001a\b\u0012\u0004\u0012\u00020\n0<*\u00020\u00022\n\u0010D\u001a\u00020/\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010F\u001a\u00020\u0006\u001a/\u0010b\u001a\b\u0012\u0004\u0012\u00020\n0<*\u00020\u00022\u0006\u0010R\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010F\u001a\u00020\u0006H\u0002¢\u0006\u0002\bd\u001a%\u0010b\u001a\b\u0012\u0004\u0012\u00020\n0<*\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010F\u001a\u00020\u0006H\u0087\b\u001a=\u0010e\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\u0012\u0010D\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0E\"\u00020\n2\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010F\u001a\u00020\u0006¢\u0006\u0002\u0010f\u001a0\u0010e\u001a\b\u0012\u0004\u0012\u00020\n0:*\u00020\u00022\n\u0010D\u001a\u00020/\"\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010F\u001a\u00020\u0006\u001a\u001c\u0010g\u001a\u00020\r*\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u001c\u0010g\u001a\u00020\r*\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\r\u001a$\u0010g\u001a\u00020\r*\u00020\u00022\u0006\u0010M\u001a\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\r\u001a\u0012\u0010h\u001a\u00020\u0002*\u00020\u00022\u0006\u0010O\u001a\u00020\u0001\u001a\u001d\u0010h\u001a\u00020\u0002*\u00020\n2\u0006\u0010i\u001a\u00020\u00062\u0006\u0010j\u001a\u00020\u0006H\u0087\b\u001a\u001f\u0010k\u001a\u00020\n*\u00020\u00022\u0006\u0010\u001c\u001a\u00020\u00062\b\b\u0002\u0010*\u001a\u00020\u0006H\u0087\b\u001a\u0012\u0010k\u001a\u00020\n*\u00020\u00022\u0006\u0010O\u001a\u00020\u0001\u001a\u0012\u0010k\u001a\u00020\n*\u00020\n2\u0006\u0010O\u001a\u00020\u0001\u001a\u001c\u0010l\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010l\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010m\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010m\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010n\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010n\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010o\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\u00112\b\b\u0002\u0010Y\u001a\u00020\n\u001a\u001c\u0010o\u001a\u00020\n*\u00020\n2\u0006\u0010R\u001a\u00020\n2\b\b\u0002\u0010Y\u001a\u00020\n\u001a\f\u0010p\u001a\u00020\r*\u00020\nH\u0007\u001a\u0013\u0010q\u001a\u0004\u0018\u00010\r*\u00020\nH\u0007¢\u0006\u0002\u0010r\u001a\n\u0010s\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010s\u001a\u00020\u0002*\u00020\u00022\u0012\u0010t\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0UH\u0086\bø\u0001\u0000\u001a\u0016\u0010s\u001a\u00020\u0002*\u00020\u00022\n\u0010.\u001a\u00020/\"\u00020\u0011\u001a\r\u0010s\u001a\u00020\n*\u00020\nH\u0087\b\u001a$\u0010s\u001a\u00020\n*\u00020\n2\u0012\u0010t\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0UH\u0086\bø\u0001\u0000\u001a\u0016\u0010s\u001a\u00020\n*\u00020\n2\n\u0010.\u001a\u00020/\"\u00020\u0011\u001a\n\u0010u\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010u\u001a\u00020\u0002*\u00020\u00022\u0012\u0010t\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0UH\u0086\bø\u0001\u0000\u001a\u0016\u0010u\u001a\u00020\u0002*\u00020\u00022\n\u0010.\u001a\u00020/\"\u00020\u0011\u001a\r\u0010u\u001a\u00020\n*\u00020\nH\u0087\b\u001a$\u0010u\u001a\u00020\n*\u00020\n2\u0012\u0010t\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0UH\u0086\bø\u0001\u0000\u001a\u0016\u0010u\u001a\u00020\n*\u00020\n2\n\u0010.\u001a\u00020/\"\u00020\u0011\u001a\n\u0010v\u001a\u00020\u0002*\u00020\u0002\u001a$\u0010v\u001a\u00020\u0002*\u00020\u00022\u0012\u0010t\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0UH\u0086\bø\u0001\u0000\u001a\u0016\u0010v\u001a\u00020\u0002*\u00020\u00022\n\u0010.\u001a\u00020/\"\u00020\u0011\u001a\r\u0010v\u001a\u00020\n*\u00020\nH\u0087\b\u001a$\u0010v\u001a\u00020\n*\u00020\n2\u0012\u0010t\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\r0UH\u0086\bø\u0001\u0000\u001a\u0016\u0010v\u001a\u00020\n*\u00020\n2\n\u0010.\u001a\u00020/\"\u00020\u0011\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\"\u0015\u0010\u0005\u001a\u00020\u0006*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006w"}, d2 = {"indices", "Lkotlin/ranges/IntRange;", "", "getIndices", "(Ljava/lang/CharSequence;)Lkotlin/ranges/IntRange;", "lastIndex", "", "getLastIndex", "(Ljava/lang/CharSequence;)I", "commonPrefixWith", "", "other", "ignoreCase", "", "commonSuffixWith", "contains", "char", "", "regex", "Lkotlin/text/Regex;", "contentEqualsIgnoreCaseImpl", "contentEqualsImpl", "endsWith", "suffix", "findAnyOf", "Lkotlin/Pair;", "strings", "", "startIndex", "last", "findAnyOf$StringsKt__StringsKt", "findLastAnyOf", "hasSurrogatePairAt", MiSoundBoxCommandExtras.INDEX, "ifBlank", "R", HomePageRecordHelper.AREA_C, "defaultValue", "Lkotlin/Function0;", "(Ljava/lang/CharSequence;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "ifEmpty", "indexOf", "endIndex", "indexOf$StringsKt__StringsKt", "string", "indexOfAny", "chars", "", "isEmpty", "isNotBlank", "isNotEmpty", "isNullOrBlank", "isNullOrEmpty", "iterator", "Lkotlin/collections/CharIterator;", "lastIndexOf", "lastIndexOfAny", "lineSequence", "Lkotlin/sequences/Sequence;", "lines", "", "matches", "orEmpty", "padEnd", "length", "padChar", "padStart", "rangesDelimitedBy", "delimiters", "", "limit", "rangesDelimitedBy$StringsKt__StringsKt", "(Ljava/lang/CharSequence;[Ljava/lang/String;IZI)Lkotlin/sequences/Sequence;", "regionMatchesImpl", "thisOffset", "otherOffset", "removePrefix", "prefix", "removeRange", Common.RANGE, "removeSuffix", "removeSurrounding", "delimiter", "replace", "transform", "Lkotlin/Function1;", "Lkotlin/text/MatchResult;", "replacement", "replaceAfter", "missingDelimiterValue", "replaceAfterLast", "replaceBefore", "replaceBeforeLast", "replaceFirst", "replaceFirstChar", "replaceFirstCharWithChar", "replaceFirstCharWithCharSequence", "replaceRange", "split", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Ljava/util/List;", "split$StringsKt__StringsKt", "splitToSequence", "(Ljava/lang/CharSequence;[Ljava/lang/String;ZI)Lkotlin/sequences/Sequence;", "startsWith", "subSequence", "start", "end", "substring", "substringAfter", "substringAfterLast", "substringBefore", "substringBeforeLast", "toBooleanStrict", "toBooleanStrictOrNull", "(Ljava/lang/String;)Ljava/lang/Boolean;", "trim", "predicate", "trimEnd", "trimStart", "kotlin-stdlib"}, k = 5, mv = {1, 5, 1}, xi = 1, xs = "kotlin/text/StringsKt")
/* loaded from: classes5.dex */
public class p extends o {
    @NotNull
    public static final CharSequence trim(@NotNull CharSequence trim, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(trim, "$this$trim");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int length = trim.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean booleanValue = predicate.invoke(Character.valueOf(trim.charAt(!z ? i : length))).booleanValue();
            if (!z) {
                if (!booleanValue) {
                    z = true;
                } else {
                    i++;
                }
            } else if (!booleanValue) {
                break;
            } else {
                length--;
            }
        }
        return trim.subSequence(i, length + 1);
    }

    @NotNull
    public static final String trim(@NotNull String trim, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(trim, "$this$trim");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        String str = trim;
        int length = str.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean booleanValue = predicate.invoke(Character.valueOf(str.charAt(!z ? i : length))).booleanValue();
            if (!z) {
                if (!booleanValue) {
                    z = true;
                } else {
                    i++;
                }
            } else if (!booleanValue) {
                break;
            } else {
                length--;
            }
        }
        return str.subSequence(i, length + 1).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence trimStart, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(trimStart, "$this$trimStart");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int length = trimStart.length();
        for (int i = 0; i < length; i++) {
            if (!predicate.invoke(Character.valueOf(trimStart.charAt(i))).booleanValue()) {
                return trimStart.subSequence(i, trimStart.length());
            }
        }
        return "";
    }

    @NotNull
    public static final String trimStart(@NotNull String trimStart, @NotNull Function1<? super Character, Boolean> predicate) {
        String str;
        Intrinsics.checkNotNullParameter(trimStart, "$this$trimStart");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        String str2 = trimStart;
        int length = str2.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (!predicate.invoke(Character.valueOf(str2.charAt(i))).booleanValue()) {
                str = str2.subSequence(i, str2.length());
                break;
            } else {
                i++;
            }
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence trimEnd, @NotNull Function1<? super Character, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(trimEnd, "$this$trimEnd");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        int length = trimEnd.length();
        do {
            length--;
            if (length < 0) {
                return "";
            }
        } while (predicate.invoke(Character.valueOf(trimEnd.charAt(length))).booleanValue());
        return trimEnd.subSequence(0, length + 1);
    }

    @NotNull
    public static final String trimEnd(@NotNull String trimEnd, @NotNull Function1<? super Character, Boolean> predicate) {
        String str;
        Intrinsics.checkNotNullParameter(trimEnd, "$this$trimEnd");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        String str2 = trimEnd;
        int length = str2.length();
        while (true) {
            length--;
            if (length >= 0) {
                if (!predicate.invoke(Character.valueOf(str2.charAt(length))).booleanValue()) {
                    str = str2.subSequence(0, length + 1);
                    break;
                }
            } else {
                break;
            }
        }
        return str.toString();
    }

    public static /* synthetic */ CharSequence padStart$default(CharSequence charSequence, int i, char c2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c2 = ' ';
        }
        return StringsKt.padStart(charSequence, i, c2);
    }

    @NotNull
    public static final CharSequence padStart(@NotNull CharSequence padStart, int i, char c2) {
        Intrinsics.checkNotNullParameter(padStart, "$this$padStart");
        if (i < 0) {
            throw new IllegalArgumentException("Desired length " + i + " is less than zero.");
        } else if (i <= padStart.length()) {
            return padStart.subSequence(0, padStart.length());
        } else {
            StringBuilder sb = new StringBuilder(i);
            int length = i - padStart.length();
            int i2 = 1;
            if (1 <= length) {
                while (true) {
                    sb.append(c2);
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                }
            }
            sb.append(padStart);
            return sb;
        }
    }

    public static /* synthetic */ String padStart$default(String str, int i, char c2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c2 = ' ';
        }
        return StringsKt.padStart(str, i, c2);
    }

    @NotNull
    public static final String padStart(@NotNull String padStart, int i, char c2) {
        Intrinsics.checkNotNullParameter(padStart, "$this$padStart");
        return StringsKt.padStart((CharSequence) padStart, i, c2).toString();
    }

    public static /* synthetic */ CharSequence padEnd$default(CharSequence charSequence, int i, char c2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c2 = ' ';
        }
        return StringsKt.padEnd(charSequence, i, c2);
    }

    @NotNull
    public static final CharSequence padEnd(@NotNull CharSequence padEnd, int i, char c2) {
        Intrinsics.checkNotNullParameter(padEnd, "$this$padEnd");
        if (i < 0) {
            throw new IllegalArgumentException("Desired length " + i + " is less than zero.");
        } else if (i <= padEnd.length()) {
            return padEnd.subSequence(0, padEnd.length());
        } else {
            StringBuilder sb = new StringBuilder(i);
            sb.append(padEnd);
            int length = i - padEnd.length();
            int i2 = 1;
            if (1 <= length) {
                while (true) {
                    sb.append(c2);
                    if (i2 == length) {
                        break;
                    }
                    i2++;
                }
            }
            return sb;
        }
    }

    public static /* synthetic */ String padEnd$default(String str, int i, char c2, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            c2 = ' ';
        }
        return StringsKt.padEnd(str, i, c2);
    }

    @NotNull
    public static final String padEnd(@NotNull String padEnd, int i, char c2) {
        Intrinsics.checkNotNullParameter(padEnd, "$this$padEnd");
        return StringsKt.padEnd((CharSequence) padEnd, i, c2).toString();
    }

    @NotNull
    public static final CharIterator iterator(@NotNull final CharSequence iterator) {
        Intrinsics.checkNotNullParameter(iterator, "$this$iterator");
        return new CharIterator() { // from class: kotlin.text.StringsKt__StringsKt$iterator$1
            private int b;

            @Override // kotlin.collections.CharIterator
            public char nextChar() {
                CharSequence charSequence = iterator;
                int i = this.b;
                this.b = i + 1;
                return charSequence.charAt(i);
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.b < iterator.length();
            }
        };
    }

    @NotNull
    public static final IntRange getIndices(@NotNull CharSequence indices) {
        Intrinsics.checkNotNullParameter(indices, "$this$indices");
        return new IntRange(0, indices.length() - 1);
    }

    public static final int getLastIndex(@NotNull CharSequence lastIndex) {
        Intrinsics.checkNotNullParameter(lastIndex, "$this$lastIndex");
        return lastIndex.length() - 1;
    }

    public static final boolean hasSurrogatePairAt(@NotNull CharSequence hasSurrogatePairAt, int i) {
        Intrinsics.checkNotNullParameter(hasSurrogatePairAt, "$this$hasSurrogatePairAt");
        return i >= 0 && hasSurrogatePairAt.length() + (-2) >= i && Character.isHighSurrogate(hasSurrogatePairAt.charAt(i)) && Character.isLowSurrogate(hasSurrogatePairAt.charAt(i + 1));
    }

    @NotNull
    public static final String substring(@NotNull String substring, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(substring, "$this$substring");
        Intrinsics.checkNotNullParameter(range, "range");
        String substring2 = substring.substring(range.getStart().intValue(), range.getEndInclusive().intValue() + 1);
        Intrinsics.checkNotNullExpressionValue(substring2, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring2;
    }

    @NotNull
    public static final CharSequence subSequence(@NotNull CharSequence subSequence, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(subSequence, "$this$subSequence");
        Intrinsics.checkNotNullParameter(range, "range");
        return subSequence.subSequence(range.getStart().intValue(), range.getEndInclusive().intValue() + 1);
    }

    @NotNull
    public static final String substring(@NotNull CharSequence substring, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(substring, "$this$substring");
        Intrinsics.checkNotNullParameter(range, "range");
        return substring.subSequence(range.getStart().intValue(), range.getEndInclusive().intValue() + 1).toString();
    }

    public static /* synthetic */ String substringBefore$default(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringBefore(str, c2, str2);
    }

    @NotNull
    public static final String substringBefore(@NotNull String substringBefore, char c2, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringBefore, "$this$substringBefore");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int indexOf$default = StringsKt.indexOf$default((CharSequence) substringBefore, c2, 0, false, 6, (Object) null);
        if (indexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringBefore.substring(0, indexOf$default);
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringBefore$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringBefore(str, str2, str3);
    }

    @NotNull
    public static final String substringBefore(@NotNull String substringBefore, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringBefore, "$this$substringBefore");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int indexOf$default = StringsKt.indexOf$default((CharSequence) substringBefore, delimiter, 0, false, 6, (Object) null);
        if (indexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringBefore.substring(0, indexOf$default);
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfter$default(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringAfter(str, c2, str2);
    }

    @NotNull
    public static final String substringAfter(@NotNull String substringAfter, char c2, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringAfter, "$this$substringAfter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int indexOf$default = StringsKt.indexOf$default((CharSequence) substringAfter, c2, 0, false, 6, (Object) null);
        if (indexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringAfter.substring(indexOf$default + 1, substringAfter.length());
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfter$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringAfter(str, str2, str3);
    }

    @NotNull
    public static final String substringAfter(@NotNull String substringAfter, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringAfter, "$this$substringAfter");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int indexOf$default = StringsKt.indexOf$default((CharSequence) substringAfter, delimiter, 0, false, 6, (Object) null);
        if (indexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringAfter.substring(indexOf$default + delimiter.length(), substringAfter.length());
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringBeforeLast$default(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringBeforeLast(str, c2, str2);
    }

    @NotNull
    public static final String substringBeforeLast(@NotNull String substringBeforeLast, char c2, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringBeforeLast, "$this$substringBeforeLast");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) substringBeforeLast, c2, 0, false, 6, (Object) null);
        if (lastIndexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringBeforeLast.substring(0, lastIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringBeforeLast$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringBeforeLast(str, str2, str3);
    }

    @NotNull
    public static final String substringBeforeLast(@NotNull String substringBeforeLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringBeforeLast, "$this$substringBeforeLast");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) substringBeforeLast, delimiter, 0, false, 6, (Object) null);
        if (lastIndexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringBeforeLast.substring(0, lastIndexOf$default);
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfterLast$default(String str, char c2, String str2, int i, Object obj) {
        if ((i & 2) != 0) {
            str2 = str;
        }
        return StringsKt.substringAfterLast(str, c2, str2);
    }

    @NotNull
    public static final String substringAfterLast(@NotNull String substringAfterLast, char c2, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringAfterLast, "$this$substringAfterLast");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) substringAfterLast, c2, 0, false, 6, (Object) null);
        if (lastIndexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringAfterLast.substring(lastIndexOf$default + 1, substringAfterLast.length());
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    public static /* synthetic */ String substringAfterLast$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 2) != 0) {
            str3 = str;
        }
        return StringsKt.substringAfterLast(str, str2, str3);
    }

    @NotNull
    public static final String substringAfterLast(@NotNull String substringAfterLast, @NotNull String delimiter, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(substringAfterLast, "$this$substringAfterLast");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) substringAfterLast, delimiter, 0, false, 6, (Object) null);
        if (lastIndexOf$default == -1) {
            return missingDelimiterValue;
        }
        String substring = substringAfterLast.substring(lastIndexOf$default + delimiter.length(), substringAfterLast.length());
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence replaceRange(@NotNull CharSequence replaceRange, int i, int i2, @NotNull CharSequence replacement) {
        Intrinsics.checkNotNullParameter(replaceRange, "$this$replaceRange");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        if (i2 >= i) {
            StringBuilder sb = new StringBuilder();
            sb.append(replaceRange, 0, i);
            Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
            sb.append(replacement);
            sb.append(replaceRange, i2, replaceRange.length());
            Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
            return sb;
        }
        throw new IndexOutOfBoundsException("End index (" + i2 + ") is less than start index (" + i + ").");
    }

    @NotNull
    public static final CharSequence replaceRange(@NotNull CharSequence replaceRange, @NotNull IntRange range, @NotNull CharSequence replacement) {
        Intrinsics.checkNotNullParameter(replaceRange, "$this$replaceRange");
        Intrinsics.checkNotNullParameter(range, "range");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        return StringsKt.replaceRange(replaceRange, range.getStart().intValue(), range.getEndInclusive().intValue() + 1, replacement);
    }

    @NotNull
    public static final CharSequence removeRange(@NotNull CharSequence removeRange, int i, int i2) {
        Intrinsics.checkNotNullParameter(removeRange, "$this$removeRange");
        if (i2 < i) {
            throw new IndexOutOfBoundsException("End index (" + i2 + ") is less than start index (" + i + ").");
        } else if (i2 == i) {
            return removeRange.subSequence(0, removeRange.length());
        } else {
            StringBuilder sb = new StringBuilder(removeRange.length() - (i2 - i));
            sb.append(removeRange, 0, i);
            Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
            sb.append(removeRange, i2, removeRange.length());
            Intrinsics.checkNotNullExpressionValue(sb, "this.append(value, startIndex, endIndex)");
            return sb;
        }
    }

    @NotNull
    public static final CharSequence removeRange(@NotNull CharSequence removeRange, @NotNull IntRange range) {
        Intrinsics.checkNotNullParameter(removeRange, "$this$removeRange");
        Intrinsics.checkNotNullParameter(range, "range");
        return StringsKt.removeRange(removeRange, range.getStart().intValue(), range.getEndInclusive().intValue() + 1);
    }

    @NotNull
    public static final CharSequence removePrefix(@NotNull CharSequence removePrefix, @NotNull CharSequence prefix) {
        Intrinsics.checkNotNullParameter(removePrefix, "$this$removePrefix");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (StringsKt.startsWith$default(removePrefix, prefix, false, 2, (Object) null)) {
            return removePrefix.subSequence(prefix.length(), removePrefix.length());
        }
        return removePrefix.subSequence(0, removePrefix.length());
    }

    @NotNull
    public static final String removePrefix(@NotNull String removePrefix, @NotNull CharSequence prefix) {
        Intrinsics.checkNotNullParameter(removePrefix, "$this$removePrefix");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (!StringsKt.startsWith$default((CharSequence) removePrefix, prefix, false, 2, (Object) null)) {
            return removePrefix;
        }
        String substring = removePrefix.substring(prefix.length());
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.String).substring(startIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence removeSuffix(@NotNull CharSequence removeSuffix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter(removeSuffix, "$this$removeSuffix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (StringsKt.endsWith$default(removeSuffix, suffix, false, 2, (Object) null)) {
            return removeSuffix.subSequence(0, removeSuffix.length() - suffix.length());
        }
        return removeSuffix.subSequence(0, removeSuffix.length());
    }

    @NotNull
    public static final String removeSuffix(@NotNull String removeSuffix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter(removeSuffix, "$this$removeSuffix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (!StringsKt.endsWith$default((CharSequence) removeSuffix, suffix, false, 2, (Object) null)) {
            return removeSuffix;
        }
        String substring = removeSuffix.substring(0, removeSuffix.length() - suffix.length());
        Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
        return substring;
    }

    @NotNull
    public static final CharSequence removeSurrounding(@NotNull CharSequence removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter(removeSurrounding, "$this$removeSurrounding");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (removeSurrounding.length() < prefix.length() + suffix.length() || !StringsKt.startsWith$default(removeSurrounding, prefix, false, 2, (Object) null) || !StringsKt.endsWith$default(removeSurrounding, suffix, false, 2, (Object) null)) {
            return removeSurrounding.subSequence(0, removeSurrounding.length());
        }
        return removeSurrounding.subSequence(prefix.length(), removeSurrounding.length() - suffix.length());
    }

    @NotNull
    public static final String removeSurrounding(@NotNull String removeSurrounding, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
        Intrinsics.checkNotNullParameter(removeSurrounding, "$this$removeSurrounding");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (removeSurrounding.length() >= prefix.length() + suffix.length()) {
            String str = removeSurrounding;
            if (StringsKt.startsWith$default((CharSequence) str, prefix, false, 2, (Object) null) && StringsKt.endsWith$default((CharSequence) str, suffix, false, 2, (Object) null)) {
                String substring = removeSurrounding.substring(prefix.length(), removeSurrounding.length() - suffix.length());
                Intrinsics.checkNotNullExpressionValue(substring, "(this as java.lang.Strin…ing(startIndex, endIndex)");
                return substring;
            }
        }
        return removeSurrounding;
    }

    @NotNull
    public static final CharSequence removeSurrounding(@NotNull CharSequence removeSurrounding, @NotNull CharSequence delimiter) {
        Intrinsics.checkNotNullParameter(removeSurrounding, "$this$removeSurrounding");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        return StringsKt.removeSurrounding(removeSurrounding, delimiter, delimiter);
    }

    @NotNull
    public static final String removeSurrounding(@NotNull String removeSurrounding, @NotNull CharSequence delimiter) {
        Intrinsics.checkNotNullParameter(removeSurrounding, "$this$removeSurrounding");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        return StringsKt.removeSurrounding(removeSurrounding, delimiter, delimiter);
    }

    public static /* synthetic */ String replaceBefore$default(String str, char c2, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceBefore(str, c2, str2, str3);
    }

    @NotNull
    public static final String replaceBefore(@NotNull String replaceBefore, char c2, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceBefore, "$this$replaceBefore");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceBefore;
        int indexOf$default = StringsKt.indexOf$default((CharSequence) str, c2, 0, false, 6, (Object) null);
        return indexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, 0, indexOf$default, replacement).toString();
    }

    public static /* synthetic */ String replaceBefore$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceBefore(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceBefore(@NotNull String replaceBefore, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceBefore, "$this$replaceBefore");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceBefore;
        int indexOf$default = StringsKt.indexOf$default((CharSequence) str, delimiter, 0, false, 6, (Object) null);
        return indexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, 0, indexOf$default, replacement).toString();
    }

    public static /* synthetic */ String replaceAfter$default(String str, char c2, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceAfter(str, c2, str2, str3);
    }

    @NotNull
    public static final String replaceAfter(@NotNull String replaceAfter, char c2, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceAfter, "$this$replaceAfter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceAfter;
        int indexOf$default = StringsKt.indexOf$default((CharSequence) str, c2, 0, false, 6, (Object) null);
        return indexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, indexOf$default + 1, replaceAfter.length(), replacement).toString();
    }

    public static /* synthetic */ String replaceAfter$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceAfter(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceAfter(@NotNull String replaceAfter, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceAfter, "$this$replaceAfter");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceAfter;
        int indexOf$default = StringsKt.indexOf$default((CharSequence) str, delimiter, 0, false, 6, (Object) null);
        return indexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, indexOf$default + delimiter.length(), replaceAfter.length(), replacement).toString();
    }

    public static /* synthetic */ String replaceAfterLast$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceAfterLast(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceAfterLast(@NotNull String replaceAfterLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceAfterLast, "$this$replaceAfterLast");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceAfterLast;
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, delimiter, 0, false, 6, (Object) null);
        return lastIndexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, lastIndexOf$default + delimiter.length(), replaceAfterLast.length(), replacement).toString();
    }

    public static /* synthetic */ String replaceAfterLast$default(String str, char c2, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceAfterLast(str, c2, str2, str3);
    }

    @NotNull
    public static final String replaceAfterLast(@NotNull String replaceAfterLast, char c2, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceAfterLast, "$this$replaceAfterLast");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceAfterLast;
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, c2, 0, false, 6, (Object) null);
        return lastIndexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, lastIndexOf$default + 1, replaceAfterLast.length(), replacement).toString();
    }

    public static /* synthetic */ String replaceBeforeLast$default(String str, char c2, String str2, String str3, int i, Object obj) {
        if ((i & 4) != 0) {
            str3 = str;
        }
        return StringsKt.replaceBeforeLast(str, c2, str2, str3);
    }

    @NotNull
    public static final String replaceBeforeLast(@NotNull String replaceBeforeLast, char c2, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceBeforeLast, "$this$replaceBeforeLast");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceBeforeLast;
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, c2, 0, false, 6, (Object) null);
        return lastIndexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, 0, lastIndexOf$default, replacement).toString();
    }

    public static /* synthetic */ String replaceBeforeLast$default(String str, String str2, String str3, String str4, int i, Object obj) {
        if ((i & 4) != 0) {
            str4 = str;
        }
        return StringsKt.replaceBeforeLast(str, str2, str3, str4);
    }

    @NotNull
    public static final String replaceBeforeLast(@NotNull String replaceBeforeLast, @NotNull String delimiter, @NotNull String replacement, @NotNull String missingDelimiterValue) {
        Intrinsics.checkNotNullParameter(replaceBeforeLast, "$this$replaceBeforeLast");
        Intrinsics.checkNotNullParameter(delimiter, "delimiter");
        Intrinsics.checkNotNullParameter(replacement, "replacement");
        Intrinsics.checkNotNullParameter(missingDelimiterValue, "missingDelimiterValue");
        String str = replaceBeforeLast;
        int lastIndexOf$default = StringsKt.lastIndexOf$default((CharSequence) str, delimiter, 0, false, 6, (Object) null);
        return lastIndexOf$default == -1 ? missingDelimiterValue : StringsKt.replaceRange(str, 0, lastIndexOf$default, replacement).toString();
    }

    public static final boolean regionMatchesImpl(@NotNull CharSequence regionMatchesImpl, int i, @NotNull CharSequence other, int i2, int i3, boolean z) {
        Intrinsics.checkNotNullParameter(regionMatchesImpl, "$this$regionMatchesImpl");
        Intrinsics.checkNotNullParameter(other, "other");
        if (i2 < 0 || i < 0 || i > regionMatchesImpl.length() - i3 || i2 > other.length() - i3) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (!CharsKt.equals(regionMatchesImpl.charAt(i + i4), other.charAt(i2 + i4), z)) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.startsWith(charSequence, c2, z);
    }

    public static final boolean startsWith(@NotNull CharSequence startsWith, char c2, boolean z) {
        Intrinsics.checkNotNullParameter(startsWith, "$this$startsWith");
        return startsWith.length() > 0 && CharsKt.equals(startsWith.charAt(0), c2, z);
    }

    public static /* synthetic */ boolean endsWith$default(CharSequence charSequence, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.endsWith(charSequence, c2, z);
    }

    public static final boolean endsWith(@NotNull CharSequence endsWith, char c2, boolean z) {
        Intrinsics.checkNotNullParameter(endsWith, "$this$endsWith");
        return endsWith.length() > 0 && CharsKt.equals(endsWith.charAt(StringsKt.getLastIndex(endsWith)), c2, z);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.startsWith(charSequence, charSequence2, z);
    }

    public static final boolean startsWith(@NotNull CharSequence startsWith, @NotNull CharSequence prefix, boolean z) {
        Intrinsics.checkNotNullParameter(startsWith, "$this$startsWith");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (z || !(startsWith instanceof String) || !(prefix instanceof String)) {
            return StringsKt.regionMatchesImpl(startsWith, 0, prefix, 0, prefix.length(), z);
        }
        return StringsKt.startsWith$default((String) startsWith, (String) prefix, false, 2, (Object) null);
    }

    public static /* synthetic */ boolean startsWith$default(CharSequence charSequence, CharSequence charSequence2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.startsWith(charSequence, charSequence2, i, z);
    }

    public static final boolean startsWith(@NotNull CharSequence startsWith, @NotNull CharSequence prefix, int i, boolean z) {
        Intrinsics.checkNotNullParameter(startsWith, "$this$startsWith");
        Intrinsics.checkNotNullParameter(prefix, "prefix");
        if (z || !(startsWith instanceof String) || !(prefix instanceof String)) {
            return StringsKt.regionMatchesImpl(startsWith, i, prefix, 0, prefix.length(), z);
        }
        return StringsKt.startsWith$default((String) startsWith, (String) prefix, i, false, 4, (Object) null);
    }

    public static /* synthetic */ boolean endsWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.endsWith(charSequence, charSequence2, z);
    }

    public static final boolean endsWith(@NotNull CharSequence endsWith, @NotNull CharSequence suffix, boolean z) {
        Intrinsics.checkNotNullParameter(endsWith, "$this$endsWith");
        Intrinsics.checkNotNullParameter(suffix, "suffix");
        if (z || !(endsWith instanceof String) || !(suffix instanceof String)) {
            return StringsKt.regionMatchesImpl(endsWith, endsWith.length() - suffix.length(), suffix, 0, suffix.length(), z);
        }
        return StringsKt.endsWith$default((String) endsWith, (String) suffix, false, 2, (Object) null);
    }

    public static /* synthetic */ String commonPrefixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.commonPrefixWith(charSequence, charSequence2, z);
    }

    @NotNull
    public static final String commonPrefixWith(@NotNull CharSequence commonPrefixWith, @NotNull CharSequence other, boolean z) {
        Intrinsics.checkNotNullParameter(commonPrefixWith, "$this$commonPrefixWith");
        Intrinsics.checkNotNullParameter(other, "other");
        int min = Math.min(commonPrefixWith.length(), other.length());
        int i = 0;
        while (i < min && CharsKt.equals(commonPrefixWith.charAt(i), other.charAt(i), z)) {
            i++;
        }
        int i2 = i - 1;
        if (StringsKt.hasSurrogatePairAt(commonPrefixWith, i2) || StringsKt.hasSurrogatePairAt(other, i2)) {
            i--;
        }
        return commonPrefixWith.subSequence(0, i).toString();
    }

    public static /* synthetic */ String commonSuffixWith$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.commonSuffixWith(charSequence, charSequence2, z);
    }

    @NotNull
    public static final String commonSuffixWith(@NotNull CharSequence commonSuffixWith, @NotNull CharSequence other, boolean z) {
        Intrinsics.checkNotNullParameter(commonSuffixWith, "$this$commonSuffixWith");
        Intrinsics.checkNotNullParameter(other, "other");
        int length = commonSuffixWith.length();
        int length2 = other.length();
        int min = Math.min(length, length2);
        int i = 0;
        while (i < min && CharsKt.equals(commonSuffixWith.charAt((length - i) - 1), other.charAt((length2 - i) - 1), z)) {
            i++;
        }
        if (StringsKt.hasSurrogatePairAt(commonSuffixWith, (length - i) - 1) || StringsKt.hasSurrogatePairAt(other, (length2 - i) - 1)) {
            i--;
        }
        return commonSuffixWith.subSequence(length - i, length).toString();
    }

    public static /* synthetic */ int indexOfAny$default(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOfAny(charSequence, cArr, i, z);
    }

    public static final int indexOfAny(@NotNull CharSequence indexOfAny, @NotNull char[] chars, int i, boolean z) {
        boolean z2;
        Intrinsics.checkNotNullParameter(indexOfAny, "$this$indexOfAny");
        Intrinsics.checkNotNullParameter(chars, "chars");
        if (z || chars.length != 1 || !(indexOfAny instanceof String)) {
            int coerceAtLeast = RangesKt.coerceAtLeast(i, 0);
            int lastIndex = StringsKt.getLastIndex(indexOfAny);
            if (coerceAtLeast > lastIndex) {
                return -1;
            }
            while (true) {
                char charAt = indexOfAny.charAt(coerceAtLeast);
                int length = chars.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        z2 = false;
                        break;
                    } else if (CharsKt.equals(chars[i2], charAt, z)) {
                        z2 = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (z2) {
                    return coerceAtLeast;
                }
                if (coerceAtLeast == lastIndex) {
                    return -1;
                }
                coerceAtLeast++;
            }
        } else {
            return ((String) indexOfAny).indexOf(ArraysKt.single(chars), i);
        }
    }

    public static /* synthetic */ int lastIndexOfAny$default(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOfAny(charSequence, cArr, i, z);
    }

    public static final int lastIndexOfAny(@NotNull CharSequence lastIndexOfAny, @NotNull char[] chars, int i, boolean z) {
        Intrinsics.checkNotNullParameter(lastIndexOfAny, "$this$lastIndexOfAny");
        Intrinsics.checkNotNullParameter(chars, "chars");
        if (z || chars.length != 1 || !(lastIndexOfAny instanceof String)) {
            for (int coerceAtMost = RangesKt.coerceAtMost(i, StringsKt.getLastIndex(lastIndexOfAny)); coerceAtMost >= 0; coerceAtMost--) {
                char charAt = lastIndexOfAny.charAt(coerceAtMost);
                int length = chars.length;
                boolean z2 = false;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    } else if (CharsKt.equals(chars[i2], charAt, z)) {
                        z2 = true;
                        break;
                    } else {
                        i2++;
                    }
                }
                if (z2) {
                    return coerceAtMost;
                }
            }
            return -1;
        }
        return ((String) lastIndexOfAny).lastIndexOf(ArraysKt.single(chars), i);
    }

    static /* synthetic */ int a(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2, int i3, Object obj) {
        return a(charSequence, charSequence2, i, i2, z, (i3 & 16) != 0 ? false : z2);
    }

    private static final int a(CharSequence charSequence, CharSequence charSequence2, int i, int i2, boolean z, boolean z2) {
        IntRange intRange = !z2 ? new IntRange(RangesKt.coerceAtLeast(i, 0), RangesKt.coerceAtMost(i2, charSequence.length())) : RangesKt.downTo(RangesKt.coerceAtMost(i, StringsKt.getLastIndex(charSequence)), RangesKt.coerceAtLeast(i2, 0));
        if (!(charSequence instanceof String) || !(charSequence2 instanceof String)) {
            int first = intRange.getFirst();
            int last = intRange.getLast();
            int step = intRange.getStep();
            if (step >= 0) {
                if (first > last) {
                    return -1;
                }
            } else if (first < last) {
                return -1;
            }
            while (!StringsKt.regionMatchesImpl(charSequence2, 0, charSequence, first, charSequence2.length(), z)) {
                if (first == last) {
                    return -1;
                }
                first += step;
            }
            return first;
        }
        int first2 = intRange.getFirst();
        int last2 = intRange.getLast();
        int step2 = intRange.getStep();
        if (step2 >= 0) {
            if (first2 > last2) {
                return -1;
            }
        } else if (first2 < last2) {
            return -1;
        }
        while (!StringsKt.regionMatches((String) charSequence2, 0, (String) charSequence, first2, charSequence2.length(), z)) {
            if (first2 == last2) {
                return -1;
            }
            first2 += step2;
        }
        return first2;
    }

    public static final Pair<Integer, String> a(CharSequence charSequence, Collection<String> collection, int i, boolean z, boolean z2) {
        Object obj;
        Object obj2;
        if (z || collection.size() != 1) {
            IntRange intRange = !z2 ? new IntRange(RangesKt.coerceAtLeast(i, 0), charSequence.length()) : RangesKt.downTo(RangesKt.coerceAtMost(i, StringsKt.getLastIndex(charSequence)), 0);
            if (charSequence instanceof String) {
                int first = intRange.getFirst();
                int last = intRange.getLast();
                int step = intRange.getStep();
                if (step < 0 ? first >= last : first <= last) {
                    while (true) {
                        Iterator<T> it = collection.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                obj2 = null;
                                break;
                            }
                            obj2 = it.next();
                            String str = (String) obj2;
                            if (StringsKt.regionMatches(str, 0, (String) charSequence, first, str.length(), z)) {
                                break;
                            }
                        }
                        String str2 = (String) obj2;
                        if (str2 == null) {
                            if (first == last) {
                                break;
                            }
                            first += step;
                        } else {
                            return TuplesKt.to(Integer.valueOf(first), str2);
                        }
                    }
                }
            } else {
                int first2 = intRange.getFirst();
                int last2 = intRange.getLast();
                int step2 = intRange.getStep();
                if (step2 < 0 ? first2 >= last2 : first2 <= last2) {
                    while (true) {
                        Iterator<T> it2 = collection.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                obj = null;
                                break;
                            }
                            obj = it2.next();
                            String str3 = (String) obj;
                            if (StringsKt.regionMatchesImpl(str3, 0, charSequence, first2, str3.length(), z)) {
                                break;
                            }
                        }
                        String str4 = (String) obj;
                        if (str4 == null) {
                            if (first2 == last2) {
                                break;
                            }
                            first2 += step2;
                        } else {
                            return TuplesKt.to(Integer.valueOf(first2), str4);
                        }
                    }
                }
            }
            return null;
        }
        String str5 = (String) CollectionsKt.single(collection);
        int indexOf$default = !z2 ? StringsKt.indexOf$default(charSequence, str5, i, false, 4, (Object) null) : StringsKt.lastIndexOf$default(charSequence, str5, i, false, 4, (Object) null);
        if (indexOf$default < 0) {
            return null;
        }
        return TuplesKt.to(Integer.valueOf(indexOf$default), str5);
    }

    public static /* synthetic */ Pair findAnyOf$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.findAnyOf(charSequence, collection, i, z);
    }

    @Nullable
    public static final Pair<Integer, String> findAnyOf(@NotNull CharSequence findAnyOf, @NotNull Collection<String> strings, int i, boolean z) {
        Intrinsics.checkNotNullParameter(findAnyOf, "$this$findAnyOf");
        Intrinsics.checkNotNullParameter(strings, "strings");
        return a(findAnyOf, strings, i, z, false);
    }

    public static /* synthetic */ Pair findLastAnyOf$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.findLastAnyOf(charSequence, collection, i, z);
    }

    @Nullable
    public static final Pair<Integer, String> findLastAnyOf(@NotNull CharSequence findLastAnyOf, @NotNull Collection<String> strings, int i, boolean z) {
        Intrinsics.checkNotNullParameter(findLastAnyOf, "$this$findLastAnyOf");
        Intrinsics.checkNotNullParameter(strings, "strings");
        return a(findLastAnyOf, strings, i, z, true);
    }

    public static /* synthetic */ int indexOfAny$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOfAny(charSequence, collection, i, z);
    }

    public static final int indexOfAny(@NotNull CharSequence indexOfAny, @NotNull Collection<String> strings, int i, boolean z) {
        Integer first;
        Intrinsics.checkNotNullParameter(indexOfAny, "$this$indexOfAny");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Pair<Integer, String> a2 = a(indexOfAny, strings, i, z, false);
        if (a2 == null || (first = a2.getFirst()) == null) {
            return -1;
        }
        return first.intValue();
    }

    public static /* synthetic */ int lastIndexOfAny$default(CharSequence charSequence, Collection collection, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOfAny(charSequence, collection, i, z);
    }

    public static final int lastIndexOfAny(@NotNull CharSequence lastIndexOfAny, @NotNull Collection<String> strings, int i, boolean z) {
        Integer first;
        Intrinsics.checkNotNullParameter(lastIndexOfAny, "$this$lastIndexOfAny");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Pair<Integer, String> a2 = a(lastIndexOfAny, strings, i, z, true);
        if (a2 == null || (first = a2.getFirst()) == null) {
            return -1;
        }
        return first.intValue();
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, char c2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOf(charSequence, c2, i, z);
    }

    public static final int indexOf(@NotNull CharSequence indexOf, char c2, int i, boolean z) {
        Intrinsics.checkNotNullParameter(indexOf, "$this$indexOf");
        return (z || !(indexOf instanceof String)) ? StringsKt.indexOfAny(indexOf, new char[]{c2}, i, z) : ((String) indexOf).indexOf(c2, i);
    }

    public static /* synthetic */ int indexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.indexOf(charSequence, str, i, z);
    }

    public static final int indexOf(@NotNull CharSequence indexOf, @NotNull String string, int i, boolean z) {
        Intrinsics.checkNotNullParameter(indexOf, "$this$indexOf");
        Intrinsics.checkNotNullParameter(string, "string");
        if (z || !(indexOf instanceof String)) {
            return a(indexOf, string, i, indexOf.length(), z, false, 16, null);
        }
        return ((String) indexOf).indexOf(string, i);
    }

    public static /* synthetic */ int lastIndexOf$default(CharSequence charSequence, char c2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOf(charSequence, c2, i, z);
    }

    public static final int lastIndexOf(@NotNull CharSequence lastIndexOf, char c2, int i, boolean z) {
        Intrinsics.checkNotNullParameter(lastIndexOf, "$this$lastIndexOf");
        return (z || !(lastIndexOf instanceof String)) ? StringsKt.lastIndexOfAny(lastIndexOf, new char[]{c2}, i, z) : ((String) lastIndexOf).lastIndexOf(c2, i);
    }

    public static /* synthetic */ int lastIndexOf$default(CharSequence charSequence, String str, int i, boolean z, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = StringsKt.getLastIndex(charSequence);
        }
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.lastIndexOf(charSequence, str, i, z);
    }

    public static final int lastIndexOf(@NotNull CharSequence lastIndexOf, @NotNull String string, int i, boolean z) {
        Intrinsics.checkNotNullParameter(lastIndexOf, "$this$lastIndexOf");
        Intrinsics.checkNotNullParameter(string, "string");
        if (z || !(lastIndexOf instanceof String)) {
            return a(lastIndexOf, string, i, 0, z, true);
        }
        return ((String) lastIndexOf).lastIndexOf(string, i);
    }

    public static /* synthetic */ boolean contains$default(CharSequence charSequence, CharSequence charSequence2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.contains(charSequence, charSequence2, z);
    }

    public static final boolean contains(@NotNull CharSequence contains, @NotNull CharSequence other, boolean z) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        Intrinsics.checkNotNullParameter(other, "other");
        return other instanceof String ? StringsKt.indexOf$default(contains, (String) other, 0, z, 2, (Object) null) >= 0 : a(contains, other, 0, contains.length(), z, false, 16, null) >= 0;
    }

    public static /* synthetic */ boolean contains$default(CharSequence charSequence, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.contains(charSequence, c2, z);
    }

    public static final boolean contains(@NotNull CharSequence contains, char c2, boolean z) {
        Intrinsics.checkNotNullParameter(contains, "$this$contains");
        return StringsKt.indexOf$default(contains, c2, 0, z, 2, (Object) null) >= 0;
    }

    static /* synthetic */ Sequence a(CharSequence charSequence, char[] cArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return a(charSequence, cArr, i, z, i2);
    }

    /* compiled from: Strings.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\r\n\u0002\b\u0002\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "Lkotlin/Pair;", "", "", "currentIndex", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class a extends Lambda implements Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>> {
        final /* synthetic */ char[] $delimiters;
        final /* synthetic */ boolean $ignoreCase;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        a(char[] cArr, boolean z) {
            super(2);
            this.$delimiters = cArr;
            this.$ignoreCase = z;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* synthetic */ Pair<? extends Integer, ? extends Integer> invoke(CharSequence charSequence, Integer num) {
            return a(charSequence, num.intValue());
        }

        @Nullable
        public final Pair<Integer, Integer> a(@NotNull CharSequence receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            int indexOfAny = StringsKt.indexOfAny(receiver, this.$delimiters, i, this.$ignoreCase);
            if (indexOfAny < 0) {
                return null;
            }
            return TuplesKt.to(Integer.valueOf(indexOfAny), 1);
        }
    }

    private static final Sequence<IntRange> a(CharSequence charSequence, char[] cArr, int i, boolean z, int i2) {
        if (i2 >= 0) {
            return new c(charSequence, i, i2, new a(cArr, z));
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + i2 + '.').toString());
    }

    static /* synthetic */ Sequence a(CharSequence charSequence, String[] strArr, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        if ((i3 & 8) != 0) {
            i2 = 0;
        }
        return a(charSequence, strArr, i, z, i2);
    }

    private static final Sequence<IntRange> a(CharSequence charSequence, String[] strArr, int i, boolean z, int i2) {
        if (i2 >= 0) {
            return new c(charSequence, i, i2, new b(ArraysKt.asList(strArr), z));
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + i2 + '.').toString());
    }

    /* compiled from: Strings.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\r\n\u0002\b\u0002\u0010\u0000\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0018\u00010\u0001*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0002H\n¢\u0006\u0002\b\u0005"}, d2 = {"<anonymous>", "Lkotlin/Pair;", "", "", "currentIndex", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    public static final class b extends Lambda implements Function2<CharSequence, Integer, Pair<? extends Integer, ? extends Integer>> {
        final /* synthetic */ List $delimitersList;
        final /* synthetic */ boolean $ignoreCase;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        b(List list, boolean z) {
            super(2);
            this.$delimitersList = list;
            this.$ignoreCase = z;
        }

        @Override // kotlin.jvm.functions.Function2
        public /* synthetic */ Pair<? extends Integer, ? extends Integer> invoke(CharSequence charSequence, Integer num) {
            return a(charSequence, num.intValue());
        }

        @Nullable
        public final Pair<Integer, Integer> a(@NotNull CharSequence receiver, int i) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            Pair a = p.a(receiver, (Collection<String>) this.$delimitersList, i, this.$ignoreCase, false);
            if (a != null) {
                return TuplesKt.to(a.getFirst(), Integer.valueOf(((String) a.getSecond()).length()));
            }
            return null;
        }
    }

    /* compiled from: Strings.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lkotlin/ranges/IntRange;", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    static final class c extends Lambda implements Function1<IntRange, String> {
        final /* synthetic */ CharSequence $this_splitToSequence;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        c(CharSequence charSequence) {
            super(1);
            this.$this_splitToSequence = charSequence;
        }

        @NotNull
        /* renamed from: a */
        public final String invoke(@NotNull IntRange it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return StringsKt.substring(this.$this_splitToSequence, it);
        }
    }

    public static /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.splitToSequence(charSequence, strArr, z, i);
    }

    @NotNull
    public static final Sequence<String> splitToSequence(@NotNull CharSequence splitToSequence, @NotNull String[] delimiters, boolean z, int i) {
        Intrinsics.checkNotNullParameter(splitToSequence, "$this$splitToSequence");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        return SequencesKt.map(a(splitToSequence, delimiters, 0, z, i, 2, (Object) null), new c(splitToSequence));
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, String[] strArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.split(charSequence, strArr, z, i);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence split, @NotNull String[] delimiters, boolean z, int i) {
        Intrinsics.checkNotNullParameter(split, "$this$split");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        if (delimiters.length == 1) {
            boolean z2 = false;
            String str = delimiters[0];
            if (str.length() == 0) {
                z2 = true;
            }
            if (!z2) {
                return a(split, str, z, i);
            }
        }
        Iterable<IntRange> asIterable = SequencesKt.asIterable(a(split, delimiters, 0, z, i, 2, (Object) null));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(asIterable, 10));
        for (IntRange intRange : asIterable) {
            arrayList.add(StringsKt.substring(split, intRange));
        }
        return arrayList;
    }

    /* compiled from: Strings.kt */
    @Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {"<anonymous>", "", "it", "Lkotlin/ranges/IntRange;", "invoke"}, k = 3, mv = {1, 5, 1})
    /* loaded from: classes5.dex */
    static final class d extends Lambda implements Function1<IntRange, String> {
        final /* synthetic */ CharSequence $this_splitToSequence;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        d(CharSequence charSequence) {
            super(1);
            this.$this_splitToSequence = charSequence;
        }

        @NotNull
        /* renamed from: a */
        public final String invoke(@NotNull IntRange it) {
            Intrinsics.checkNotNullParameter(it, "it");
            return StringsKt.substring(this.$this_splitToSequence, it);
        }
    }

    public static /* synthetic */ Sequence splitToSequence$default(CharSequence charSequence, char[] cArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.splitToSequence(charSequence, cArr, z, i);
    }

    @NotNull
    public static final Sequence<String> splitToSequence(@NotNull CharSequence splitToSequence, @NotNull char[] delimiters, boolean z, int i) {
        Intrinsics.checkNotNullParameter(splitToSequence, "$this$splitToSequence");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        return SequencesKt.map(a(splitToSequence, delimiters, 0, z, i, 2, (Object) null), new d(splitToSequence));
    }

    public static /* synthetic */ List split$default(CharSequence charSequence, char[] cArr, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = false;
        }
        if ((i2 & 4) != 0) {
            i = 0;
        }
        return StringsKt.split(charSequence, cArr, z, i);
    }

    @NotNull
    public static final List<String> split(@NotNull CharSequence split, @NotNull char[] delimiters, boolean z, int i) {
        Intrinsics.checkNotNullParameter(split, "$this$split");
        Intrinsics.checkNotNullParameter(delimiters, "delimiters");
        if (delimiters.length == 1) {
            return a(split, String.valueOf(delimiters[0]), z, i);
        }
        Iterable<IntRange> asIterable = SequencesKt.asIterable(a(split, delimiters, 0, z, i, 2, (Object) null));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(asIterable, 10));
        for (IntRange intRange : asIterable) {
            arrayList.add(StringsKt.substring(split, intRange));
        }
        return arrayList;
    }

    private static final List<String> a(CharSequence charSequence, String str, boolean z, int i) {
        int i2 = 0;
        if (i >= 0) {
            int indexOf = StringsKt.indexOf(charSequence, str, 0, z);
            if (indexOf == -1 || i == 1) {
                return CollectionsKt.listOf(charSequence.toString());
            }
            boolean z2 = i > 0;
            int i3 = 10;
            if (z2) {
                i3 = RangesKt.coerceAtMost(i, 10);
            }
            ArrayList arrayList = new ArrayList(i3);
            do {
                arrayList.add(charSequence.subSequence(i2, indexOf).toString());
                i2 = str.length() + indexOf;
                if (z2 && arrayList.size() == i - 1) {
                    break;
                }
                indexOf = StringsKt.indexOf(charSequence, str, i2, z);
            } while (indexOf != -1);
            arrayList.add(charSequence.subSequence(i2, charSequence.length()).toString());
            return arrayList;
        }
        throw new IllegalArgumentException(("Limit must be non-negative, but was " + i + '.').toString());
    }

    @NotNull
    public static final Sequence<String> lineSequence(@NotNull CharSequence lineSequence) {
        Intrinsics.checkNotNullParameter(lineSequence, "$this$lineSequence");
        return StringsKt.splitToSequence$default(lineSequence, new String[]{"\r\n", "\n", StringUtils.CR}, false, 0, 6, (Object) null);
    }

    @NotNull
    public static final List<String> lines(@NotNull CharSequence lines) {
        Intrinsics.checkNotNullParameter(lines, "$this$lines");
        return SequencesKt.toList(StringsKt.lineSequence(lines));
    }

    public static final boolean contentEqualsIgnoreCaseImpl(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return StringsKt.equals((String) charSequence, (String) charSequence2, true);
        }
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!CharsKt.equals(charSequence.charAt(i), charSequence2.charAt(i), true)) {
                return false;
            }
        }
        return true;
    }

    public static final boolean contentEqualsImpl(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
            return Intrinsics.areEqual(charSequence, charSequence2);
        }
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence == null || charSequence2 == null || charSequence.length() != charSequence2.length()) {
            return false;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.5")
    public static final boolean toBooleanStrict(@NotNull String toBooleanStrict) {
        Intrinsics.checkNotNullParameter(toBooleanStrict, "$this$toBooleanStrict");
        int hashCode = toBooleanStrict.hashCode();
        if (hashCode != 3569038) {
            if (hashCode == 97196323 && toBooleanStrict.equals("false")) {
                return false;
            }
        } else if (toBooleanStrict.equals("true")) {
            return true;
        }
        throw new IllegalArgumentException("The string doesn't represent a boolean value: " + toBooleanStrict);
    }

    @SinceKotlin(version = "1.5")
    @Nullable
    public static final Boolean toBooleanStrictOrNull(@NotNull String toBooleanStrictOrNull) {
        Intrinsics.checkNotNullParameter(toBooleanStrictOrNull, "$this$toBooleanStrictOrNull");
        int hashCode = toBooleanStrictOrNull.hashCode();
        if (hashCode != 3569038) {
            if (hashCode == 97196323 && toBooleanStrictOrNull.equals("false")) {
                return false;
            }
        } else if (toBooleanStrictOrNull.equals("true")) {
            return true;
        }
        return null;
    }

    @NotNull
    public static final CharSequence trim(@NotNull CharSequence trim, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter(trim, "$this$trim");
        Intrinsics.checkNotNullParameter(chars, "chars");
        int length = trim.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean contains = ArraysKt.contains(chars, trim.charAt(!z ? i : length));
            if (!z) {
                if (!contains) {
                    z = true;
                } else {
                    i++;
                }
            } else if (!contains) {
                break;
            } else {
                length--;
            }
        }
        return trim.subSequence(i, length + 1);
    }

    @NotNull
    public static final String trim(@NotNull String trim, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter(trim, "$this$trim");
        Intrinsics.checkNotNullParameter(chars, "chars");
        String str = trim;
        int length = str.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean contains = ArraysKt.contains(chars, str.charAt(!z ? i : length));
            if (!z) {
                if (!contains) {
                    z = true;
                } else {
                    i++;
                }
            } else if (!contains) {
                break;
            } else {
                length--;
            }
        }
        return str.subSequence(i, length + 1).toString();
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence trimStart, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter(trimStart, "$this$trimStart");
        Intrinsics.checkNotNullParameter(chars, "chars");
        int length = trimStart.length();
        for (int i = 0; i < length; i++) {
            if (!ArraysKt.contains(chars, trimStart.charAt(i))) {
                return trimStart.subSequence(i, trimStart.length());
            }
        }
        return "";
    }

    @NotNull
    public static final String trimStart(@NotNull String trimStart, @NotNull char... chars) {
        String str;
        Intrinsics.checkNotNullParameter(trimStart, "$this$trimStart");
        Intrinsics.checkNotNullParameter(chars, "chars");
        String str2 = trimStart;
        int length = str2.length();
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            } else if (!ArraysKt.contains(chars, str2.charAt(i))) {
                str = str2.subSequence(i, str2.length());
                break;
            } else {
                i++;
            }
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence trimEnd, @NotNull char... chars) {
        Intrinsics.checkNotNullParameter(trimEnd, "$this$trimEnd");
        Intrinsics.checkNotNullParameter(chars, "chars");
        int length = trimEnd.length();
        do {
            length--;
            if (length < 0) {
                return "";
            }
        } while (ArraysKt.contains(chars, trimEnd.charAt(length)));
        return trimEnd.subSequence(0, length + 1);
    }

    @NotNull
    public static final String trimEnd(@NotNull String trimEnd, @NotNull char... chars) {
        String str;
        Intrinsics.checkNotNullParameter(trimEnd, "$this$trimEnd");
        Intrinsics.checkNotNullParameter(chars, "chars");
        String str2 = trimEnd;
        int length = str2.length();
        while (true) {
            length--;
            if (length >= 0) {
                if (!ArraysKt.contains(chars, str2.charAt(length))) {
                    str = str2.subSequence(0, length + 1);
                    break;
                }
            } else {
                break;
            }
        }
        return str.toString();
    }

    @NotNull
    public static final CharSequence trim(@NotNull CharSequence trim) {
        Intrinsics.checkNotNullParameter(trim, "$this$trim");
        int length = trim.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean isWhitespace = CharsKt.isWhitespace(trim.charAt(!z ? i : length));
            if (!z) {
                if (!isWhitespace) {
                    z = true;
                } else {
                    i++;
                }
            } else if (!isWhitespace) {
                break;
            } else {
                length--;
            }
        }
        return trim.subSequence(i, length + 1);
    }

    @NotNull
    public static final CharSequence trimStart(@NotNull CharSequence trimStart) {
        Intrinsics.checkNotNullParameter(trimStart, "$this$trimStart");
        int length = trimStart.length();
        for (int i = 0; i < length; i++) {
            if (!CharsKt.isWhitespace(trimStart.charAt(i))) {
                return trimStart.subSequence(i, trimStart.length());
            }
        }
        return "";
    }

    @NotNull
    public static final CharSequence trimEnd(@NotNull CharSequence trimEnd) {
        Intrinsics.checkNotNullParameter(trimEnd, "$this$trimEnd");
        int length = trimEnd.length();
        do {
            length--;
            if (length < 0) {
                return "";
            }
        } while (CharsKt.isWhitespace(trimEnd.charAt(length)));
        return trimEnd.subSequence(0, length + 1);
    }
}
