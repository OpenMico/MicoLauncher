package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.api.model.ChildVideo;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.onetrack.api.b;
import io.netty.handler.codec.rtsp.RtspHeaders;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.SinceKotlin;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: Maps.kt */
@Metadata(d1 = {"\u0000~\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010%\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010&\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010(\n\u0002\u0010)\n\u0002\u0010'\n\u0002\b\n\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0017\u001a`\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u00052%\b\u0001\u0010\u0006\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u001aX\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032%\b\u0001\u0010\u0006\u001a\u001f\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\u0087\bø\u0001\u0000\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u001a\u001e\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\u001a1\u0010\f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\rj\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001a_\u0010\f\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\rj\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u000e\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0012\u001a1\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0014j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001a_\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0014j\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003`\u0015\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0016\u001a!\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001aO\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0018\u001a!\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003H\u0087\b\u001aO\u0010\u0019\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032*\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010\"\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011¢\u0006\u0002\u0010\u0018\u001a*\u0010\u001a\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\n¢\u0006\u0002\u0010\u001c\u001a*\u0010\u001d\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\n¢\u0006\u0002\u0010\u001c\u001a9\u0010\u001e\u001a\u00020\u001f\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010\"\u001a1\u0010#\u001a\u00020\u001f\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b *\u000e\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0002\b\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u0010\"\u001a7\u0010$\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\t\b\u0001\u0010\u0003¢\u0006\u0002\b *\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010%\u001a\u0002H\u0003H\u0087\b¢\u0006\u0002\u0010\"\u001aV\u0010&\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001aJ\u0010(\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001aV\u0010)\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001aq\u0010*\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001aq\u0010.\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010'\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001aJ\u0010/\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010'\u001a\u000e\u0012\u0004\u0012\u0002H\u0003\u0012\u0004\u0012\u00020\u001f0\u0007H\u0086\bø\u0001\u0000\u001a;\u00100\u001a\u0004\u0018\u0001H\u0003\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u00101\u001aC\u00102\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0087\bø\u0001\u0000¢\u0006\u0002\u00105\u001aC\u00106\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0080\bø\u0001\u0000¢\u0006\u0002\u00105\u001aC\u00107\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u00022\f\u00103\u001a\b\u0012\u0004\u0012\u0002H\u000304H\u0086\bø\u0001\u0000¢\u0006\u0002\u00105\u001a1\u00108\u001a\u0002H\u0003\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0007¢\u0006\u0002\u00101\u001a?\u00109\u001a\u0002H:\"\u0014\b\u0000\u0010+*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0001*\u0002H:\"\u0004\b\u0001\u0010:*\u0002H+2\f\u00103\u001a\b\u0012\u0004\u0012\u0002H:04H\u0087\bø\u0001\u0000¢\u0006\u0002\u0010;\u001a'\u0010<\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\b\u001a:\u0010=\u001a\u00020\u001f\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000\u001a9\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b0?\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\n\u001a<\u0010>\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030A0@\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\bH\u0087\n¢\u0006\u0002\bB\u001a\\\u0010C\u001a\u000e\u0012\u0004\u0012\u0002H:\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000\u001aw\u0010E\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:\"\u0018\b\u0003\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H:\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001a\\\u0010F\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H:0\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000\u001aw\u0010G\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0004\b\u0002\u0010:\"\u0018\b\u0003\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H:0\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+2\u001e\u0010D\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001b\u0012\u0004\u0012\u0002H:0\u0007H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010-\u001a@\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010!\u001a\u0002H\u0002H\u0087\u0002¢\u0006\u0002\u0010I\u001aH\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u000e\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010H\u0087\u0002¢\u0006\u0002\u0010K\u001aA\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020LH\u0087\u0002\u001aA\u0010H\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0087\u0002\u001a2\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u0002H\u0087\n¢\u0006\u0002\u0010O\u001a:\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u000e\u0010J\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0010H\u0087\n¢\u0006\u0002\u0010P\u001a3\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020LH\u0087\n\u001a3\u0010N\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\f\u0010J\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0087\n\u001a0\u0010Q\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0000\u001a3\u0010R\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u0003\u0018\u00010\u0001H\u0087\b\u001aT\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010H\u0086\u0002¢\u0006\u0002\u0010T\u001aG\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011H\u0086\u0002\u001aM\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110LH\u0086\u0002\u001aI\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0014\u0010V\u001a\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0086\u0002\u001aM\u0010S\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110MH\u0086\u0002\u001aJ\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010H\u0087\n¢\u0006\u0002\u0010X\u001a=\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0012\u0010U\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011H\u0087\n\u001aC\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110LH\u0087\n\u001a=\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0012\u0010V\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0087\n\u001aC\u0010W\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110MH\u0087\n\u001aG\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u001a\u0010\u000f\u001a\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010¢\u0006\u0002\u0010X\u001a@\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L\u001a@\u0010Y\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b2\u0018\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M\u001a;\u0010Z\u001a\u0004\u0018\u0001H\u0003\"\t\b\u0000\u0010\u0002¢\u0006\u0002\b \"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u0002H\u0087\b¢\u0006\u0002\u00101\u001a:\u0010[\u001a\u00020\t\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b2\u0006\u0010!\u001a\u0002H\u00022\u0006\u0010%\u001a\u0002H\u0003H\u0087\n¢\u0006\u0002\u0010\\\u001a;\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u0010¢\u0006\u0002\u0010\u0018\u001aQ\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110\u00102\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010^\u001a4\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L\u001aO\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110L2\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010_\u001a2\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001aM\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00012\u0006\u0010,\u001a\u0002H+H\u0007¢\u0006\u0002\u0010`\u001a4\u0010]\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M\u001aO\u0010]\u001a\u0002H+\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003\"\u0018\b\u0002\u0010+*\u0012\u0012\u0006\b\u0000\u0012\u0002H\u0002\u0012\u0006\b\u0000\u0012\u0002H\u00030\b*\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u00110M2\u0006\u0010,\u001a\u0002H+¢\u0006\u0002\u0010a\u001a2\u0010b\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\b\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u0010\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001H\u0007\u001a1\u0010c\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0011\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u001bH\u0087\b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006d"}, d2 = {"buildMap", "", "K", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "capacity", "", "builderAction", "Lkotlin/Function1;", "", "", "Lkotlin/ExtensionFunctionType;", "emptyMap", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", b.p, ChildVideo.ItemsBean.TYPE_FILTER, "predicate", "filterKeys", "filterNot", "filterNotTo", "M", RtspHeaders.Values.DESTINATION, "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", BluetoothConstants.GET, "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", BluetoothConstants.SET, "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"}, k = 5, mv = {1, 5, 1}, xi = 1, xs = "kotlin/collections/MapsKt")
/* loaded from: classes5.dex */
public class x extends w {
    @NotNull
    public static final <K, V> Map<K, V> emptyMap() {
        q qVar = q.a;
        if (qVar != null) {
            return qVar;
        }
        throw new NullPointerException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
    }

    @NotNull
    public static final <K, V> Map<K, V> mapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        return pairs.length > 0 ? MapsKt.toMap(pairs, new LinkedHashMap(MapsKt.mapCapacity(pairs.length))) : MapsKt.emptyMap();
    }

    @NotNull
    public static final <K, V> Map<K, V> mutableMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(pairs.length));
        MapsKt.putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    @NotNull
    public static final <K, V> HashMap<K, V> hashMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        HashMap<K, V> hashMap = new HashMap<>(MapsKt.mapCapacity(pairs.length));
        MapsKt.putAll(hashMap, pairs);
        return hashMap;
    }

    @NotNull
    public static final <K, V> LinkedHashMap<K, V> linkedMapOf(@NotNull Pair<? extends K, ? extends V>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        return (LinkedHashMap) MapsKt.toMap(pairs, new LinkedHashMap(MapsKt.mapCapacity(pairs.length)));
    }

    public static final <K, V> V getOrElseNullable(@NotNull Map<K, ? extends V> getOrElseNullable, K k, @NotNull Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(getOrElseNullable, "$this$getOrElseNullable");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        V v = (V) getOrElseNullable.get(k);
        return (v != null || getOrElseNullable.containsKey(k)) ? v : (V) defaultValue.invoke();
    }

    @SinceKotlin(version = "1.1")
    public static final <K, V> V getValue(@NotNull Map<K, ? extends V> getValue, K k) {
        Intrinsics.checkNotNullParameter(getValue, "$this$getValue");
        return (V) MapsKt.getOrImplicitDefaultNullable(getValue, k);
    }

    public static final <K, V> V getOrPut(@NotNull Map<K, V> getOrPut, K k, @NotNull Function0<? extends V> defaultValue) {
        Intrinsics.checkNotNullParameter(getOrPut, "$this$getOrPut");
        Intrinsics.checkNotNullParameter(defaultValue, "defaultValue");
        V v = getOrPut.get(k);
        if (v != null) {
            return v;
        }
        V v2 = (V) defaultValue.invoke();
        getOrPut.put(k, v2);
        return v2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, R, M extends Map<? super K, ? super R>> M mapValuesTo(@NotNull Map<? extends K, ? extends V> mapValuesTo, @NotNull M destination, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(mapValuesTo, "$this$mapValuesTo");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Object obj : mapValuesTo.entrySet()) {
            destination.put(((Map.Entry) obj).getKey(), transform.invoke(obj));
        }
        return destination;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, R, M extends Map<? super R, ? super V>> M mapKeysTo(@NotNull Map<? extends K, ? extends V> mapKeysTo, @NotNull M destination, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(mapKeysTo, "$this$mapKeysTo");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(transform, "transform");
        for (Object obj : mapKeysTo.entrySet()) {
            destination.put(transform.invoke(obj), ((Map.Entry) obj).getValue());
        }
        return destination;
    }

    public static final <K, V> void putAll(@NotNull Map<? super K, ? super V> putAll, @NotNull Pair<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkNotNullParameter(putAll, "$this$putAll");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        for (Pair<? extends K, ? extends V> pair : pairs) {
            putAll.put((Object) pair.component1(), (Object) pair.component2());
        }
    }

    public static final <K, V> void putAll(@NotNull Map<? super K, ? super V> putAll, @NotNull Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(putAll, "$this$putAll");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        Iterator<? extends Pair<? extends K, ? extends V>> it = pairs.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            putAll.put((Object) pair.component1(), (Object) pair.component2());
        }
    }

    public static final <K, V> void putAll(@NotNull Map<? super K, ? super V> putAll, @NotNull Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(putAll, "$this$putAll");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        Iterator<? extends Pair<? extends K, ? extends V>> it = pairs.iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            putAll.put((Object) pair.component1(), (Object) pair.component2());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, R> Map<K, R> mapValues(@NotNull Map<? extends K, ? extends V> mapValues, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(mapValues, "$this$mapValues");
        Intrinsics.checkNotNullParameter(transform, "transform");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapValues.size()));
        for (Object obj : mapValues.entrySet()) {
            linkedHashMap.put(((Map.Entry) obj).getKey(), transform.invoke(obj));
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, R> Map<R, V> mapKeys(@NotNull Map<? extends K, ? extends V> mapKeys, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, ? extends R> transform) {
        Intrinsics.checkNotNullParameter(mapKeys, "$this$mapKeys");
        Intrinsics.checkNotNullParameter(transform, "transform");
        LinkedHashMap linkedHashMap = new LinkedHashMap(MapsKt.mapCapacity(mapKeys.size()));
        for (Object obj : mapKeys.entrySet()) {
            linkedHashMap.put(transform.invoke(obj), ((Map.Entry) obj).getValue());
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V> Map<K, V> filterKeys(@NotNull Map<? extends K, ? extends V> filterKeys, @NotNull Function1<? super K, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filterKeys, "$this$filterKeys");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<? extends K, ? extends V> entry : filterKeys.entrySet()) {
            if (predicate.invoke((Object) entry.getKey()).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V> Map<K, V> filterValues(@NotNull Map<? extends K, ? extends V> filterValues, @NotNull Function1<? super V, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filterValues, "$this$filterValues");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<? extends K, ? extends V> entry : filterValues.entrySet()) {
            if (predicate.invoke((Object) entry.getValue()).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M filterTo(@NotNull Map<? extends K, ? extends V> filterTo, @NotNull M destination, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filterTo, "$this$filterTo");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        for (Map.Entry<? extends K, ? extends V> entry : filterTo.entrySet()) {
            if (predicate.invoke(entry).booleanValue()) {
                destination.put(entry.getKey(), entry.getValue());
            }
        }
        return destination;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V> Map<K, V> filter(@NotNull Map<? extends K, ? extends V> filter, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filter, "$this$filter");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<? extends K, ? extends V> entry : filter.entrySet()) {
            if (predicate.invoke(entry).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M filterNotTo(@NotNull Map<? extends K, ? extends V> filterNotTo, @NotNull M destination, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filterNotTo, "$this$filterNotTo");
        Intrinsics.checkNotNullParameter(destination, "destination");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        for (Map.Entry<? extends K, ? extends V> entry : filterNotTo.entrySet()) {
            if (!predicate.invoke(entry).booleanValue()) {
                destination.put(entry.getKey(), entry.getValue());
            }
        }
        return destination;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V> Map<K, V> filterNot(@NotNull Map<? extends K, ? extends V> filterNot, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, Boolean> predicate) {
        Intrinsics.checkNotNullParameter(filterNot, "$this$filterNot");
        Intrinsics.checkNotNullParameter(predicate, "predicate");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<? extends K, ? extends V> entry : filterNot.entrySet()) {
            if (!predicate.invoke(entry).booleanValue()) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        return linkedHashMap;
    }

    @NotNull
    public static final <K, V> Map<K, V> toMap(@NotNull Iterable<? extends Pair<? extends K, ? extends V>> toMap) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        if (!(toMap instanceof Collection)) {
            return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(toMap, new LinkedHashMap()));
        }
        Collection collection = (Collection) toMap;
        switch (collection.size()) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.mapOf((Pair) (toMap instanceof List ? ((List) toMap).get(0) : toMap.iterator().next()));
            default:
                return MapsKt.toMap(toMap, new LinkedHashMap(MapsKt.mapCapacity(collection.size())));
        }
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Iterable<? extends Pair<? extends K, ? extends V>> toMap, @NotNull M destination) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        Intrinsics.checkNotNullParameter(destination, "destination");
        MapsKt.putAll(destination, toMap);
        return destination;
    }

    @NotNull
    public static final <K, V> Map<K, V> toMap(@NotNull Pair<? extends K, ? extends V>[] toMap) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        switch (toMap.length) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.mapOf(toMap[0]);
            default:
                return MapsKt.toMap(toMap, new LinkedHashMap(MapsKt.mapCapacity(toMap.length)));
        }
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Pair<? extends K, ? extends V>[] toMap, @NotNull M destination) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        Intrinsics.checkNotNullParameter(destination, "destination");
        MapsKt.putAll(destination, toMap);
        return destination;
    }

    @NotNull
    public static final <K, V> Map<K, V> toMap(@NotNull Sequence<? extends Pair<? extends K, ? extends V>> toMap) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        return MapsKt.optimizeReadOnlyMap(MapsKt.toMap(toMap, new LinkedHashMap()));
    }

    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Sequence<? extends Pair<? extends K, ? extends V>> toMap, @NotNull M destination) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        Intrinsics.checkNotNullParameter(destination, "destination");
        MapsKt.putAll(destination, toMap);
        return destination;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V> Map<K, V> toMap(@NotNull Map<? extends K, ? extends V> toMap) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        switch (toMap.size()) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.toSingletonMap(toMap);
            default:
                return MapsKt.toMutableMap(toMap);
        }
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V> Map<K, V> toMutableMap(@NotNull Map<? extends K, ? extends V> toMutableMap) {
        Intrinsics.checkNotNullParameter(toMutableMap, "$this$toMutableMap");
        return new LinkedHashMap(toMutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V, M extends Map<? super K, ? super V>> M toMap(@NotNull Map<? extends K, ? extends V> toMap, @NotNull M destination) {
        Intrinsics.checkNotNullParameter(toMap, "$this$toMap");
        Intrinsics.checkNotNullParameter(destination, "destination");
        destination.putAll(toMap);
        return destination;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> plus, @NotNull Pair<? extends K, ? extends V> pair) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(pair, "pair");
        if (plus.isEmpty()) {
            return MapsKt.mapOf(pair);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        linkedHashMap.put(pair.getFirst(), pair.getSecond());
        return linkedHashMap;
    }

    @NotNull
    public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> plus, @NotNull Iterable<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        if (plus.isEmpty()) {
            return MapsKt.toMap(pairs);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        MapsKt.putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    @NotNull
    public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> plus, @NotNull Pair<? extends K, ? extends V>[] pairs) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        if (plus.isEmpty()) {
            return MapsKt.toMap(pairs);
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        MapsKt.putAll(linkedHashMap, pairs);
        return linkedHashMap;
    }

    @NotNull
    public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> plus, @NotNull Sequence<? extends Pair<? extends K, ? extends V>> pairs) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        MapsKt.putAll(linkedHashMap, pairs);
        return MapsKt.optimizeReadOnlyMap(linkedHashMap);
    }

    @NotNull
    public static final <K, V> Map<K, V> plus(@NotNull Map<? extends K, ? extends V> plus, @NotNull Map<? extends K, ? extends V> map) {
        Intrinsics.checkNotNullParameter(plus, "$this$plus");
        Intrinsics.checkNotNullParameter(map, "map");
        LinkedHashMap linkedHashMap = new LinkedHashMap(plus);
        linkedHashMap.putAll(map);
        return linkedHashMap;
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> minus, K k) {
        Intrinsics.checkNotNullParameter(minus, "$this$minus");
        Map mutableMap = MapsKt.toMutableMap(minus);
        mutableMap.remove(k);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> minus, @NotNull Iterable<? extends K> keys) {
        Intrinsics.checkNotNullParameter(minus, "$this$minus");
        Intrinsics.checkNotNullParameter(keys, "keys");
        Map mutableMap = MapsKt.toMutableMap(minus);
        CollectionsKt.removeAll(mutableMap.keySet(), keys);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> minus, @NotNull K[] keys) {
        Intrinsics.checkNotNullParameter(minus, "$this$minus");
        Intrinsics.checkNotNullParameter(keys, "keys");
        Map mutableMap = MapsKt.toMutableMap(minus);
        CollectionsKt.removeAll(mutableMap.keySet(), keys);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    @SinceKotlin(version = "1.1")
    @NotNull
    public static final <K, V> Map<K, V> minus(@NotNull Map<? extends K, ? extends V> minus, @NotNull Sequence<? extends K> keys) {
        Intrinsics.checkNotNullParameter(minus, "$this$minus");
        Intrinsics.checkNotNullParameter(keys, "keys");
        Map mutableMap = MapsKt.toMutableMap(minus);
        CollectionsKt.removeAll(mutableMap.keySet(), keys);
        return MapsKt.optimizeReadOnlyMap(mutableMap);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @NotNull
    public static final <K, V> Map<K, V> optimizeReadOnlyMap(@NotNull Map<K, ? extends V> optimizeReadOnlyMap) {
        Intrinsics.checkNotNullParameter(optimizeReadOnlyMap, "$this$optimizeReadOnlyMap");
        switch (optimizeReadOnlyMap.size()) {
            case 0:
                return MapsKt.emptyMap();
            case 1:
                return MapsKt.toSingletonMap(optimizeReadOnlyMap);
            default:
                return optimizeReadOnlyMap;
        }
    }
}
