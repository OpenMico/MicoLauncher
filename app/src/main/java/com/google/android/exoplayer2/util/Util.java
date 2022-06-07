package com.google.android.exoplayer2.util;

import android.app.Activity;
import android.app.UiModeManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.SystemClock;
import android.security.NetworkSecurityPolicy;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.SparseLongArray;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.exifinterface.media.ExifInterface;
import androidx.renderscript.ScriptIntrinsicBLAS;
import com.alibaba.fastjson.asm.Opcodes;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerLibraryInfo;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ts.PsExtractor;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.common.base.Ascii;
import com.google.common.base.Charsets;
import com.umeng.analytics.pro.ai;
import com.xiaomi.mi_connect_sdk.BuildConfig;
import com.xiaomi.micolauncher.module.setting.bluetooth.BluetoothConstants;
import com.xiaomi.micolauncher.skills.music.model.PlayerEvent;
import com.xiaomi.mipush.sdk.Constants;
import com.xiaomi.onetrack.api.b;
import com.xiaomi.smarthome.setting.ServerSetting;
import io.netty.handler.codec.dns.DnsRecord;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPOutputStream;
import java.util.zip.Inflater;
import miuix.animation.internal.AnimTask;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;
import org.eclipse.jetty.http.HttpStatus;

/* loaded from: classes2.dex */
public final class Util {
    public static final String DEVICE;
    public static final String DEVICE_DEBUG_INFO;
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final String MANUFACTURER;
    public static final String MODEL;
    public static final int SDK_INT;
    private static final Pattern a;
    private static final Pattern b;
    private static final Pattern c;
    private static final Pattern d;
    @Nullable
    private static HashMap<String, String> e;
    private static final String[] f;
    private static final String[] g;
    private static final int[] h;
    private static final int[] i;

    private static boolean a(char c2) {
        if (c2 == '\"' || c2 == '%' || c2 == '*' || c2 == '/' || c2 == ':' || c2 == '<' || c2 == '\\' || c2 == '|') {
            return true;
        }
        switch (c2) {
            case '>':
            case '?':
                return true;
            default:
                return false;
        }
    }

    public static long addWithOverflowDefault(long j, long j2, long j3) {
        long j4 = j + j2;
        return ((j ^ j4) & (j2 ^ j4)) < 0 ? j3 : j4;
    }

    @EnsuresNonNull({"#1"})
    public static <T> T castNonNull(@Nullable T t) {
        return t;
    }

    @EnsuresNonNull({"#1"})
    public static <T> T[] castNonNullTypeArray(T[] tArr) {
        return tArr;
    }

    public static int compareLong(long j, long j2) {
        int i2 = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        if (i2 < 0) {
            return -1;
        }
        return i2 == 0 ? 0 : 1;
    }

    @Nullable
    public static String getAdaptiveMimeTypeForContentType(int i2) {
        switch (i2) {
            case 0:
                return MimeTypes.APPLICATION_MPD;
            case 1:
                return MimeTypes.APPLICATION_SS;
            case 2:
                return MimeTypes.APPLICATION_M3U8;
            default:
                return null;
        }
    }

    public static int getAudioContentTypeForStreamType(int i2) {
        switch (i2) {
            case 0:
                return 1;
            case 1:
            case 2:
            case 4:
            case 5:
            case 8:
                return 4;
            case 3:
            case 6:
            case 7:
            default:
                return 2;
        }
    }

    public static int getAudioUsageForStreamType(int i2) {
        switch (i2) {
            case 0:
                return 2;
            case 1:
                return 13;
            case 2:
                return 6;
            case 3:
            case 6:
            case 7:
            default:
                return 1;
            case 4:
                return 4;
            case 5:
                return 5;
            case 8:
                return 3;
        }
    }

    public static int getPcmEncoding(int i2) {
        if (i2 == 8) {
            return 3;
        }
        if (i2 == 16) {
            return 2;
        }
        if (i2 == 24) {
            return 536870912;
        }
        if (i2 != 32) {
            return 0;
        }
        return C.ENCODING_PCM_32BIT;
    }

    public static int getStreamTypeForAudioUsage(int i2) {
        switch (i2) {
            case 1:
            case 12:
            case 14:
                return 3;
            case 2:
                return 0;
            case 3:
                return 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
            default:
                return 3;
            case 13:
                return 1;
        }
    }

    public static boolean isEncodingHighResolutionPcm(int i2) {
        return i2 == 536870912 || i2 == 805306368 || i2 == 4;
    }

    public static boolean isEncodingLinearPcm(int i2) {
        return i2 == 3 || i2 == 2 || i2 == 268435456 || i2 == 536870912 || i2 == 805306368 || i2 == 4;
    }

    public static boolean isLinebreak(int i2) {
        return i2 == 10 || i2 == 13;
    }

    public static long subtractWithOverflowDefault(long j, long j2, long j3) {
        long j4 = j - j2;
        return ((j ^ j4) & (j2 ^ j)) < 0 ? j3 : j4;
    }

    public static long toUnsignedLong(int i2) {
        return i2 & 4294967295L;
    }

    static {
        int i2;
        if (ExifInterface.LATITUDE_SOUTH.equals(Build.VERSION.CODENAME)) {
            i2 = 31;
        } else {
            i2 = "R".equals(Build.VERSION.CODENAME) ? 30 : Build.VERSION.SDK_INT;
        }
        SDK_INT = i2;
        DEVICE = Build.DEVICE;
        MANUFACTURER = Build.MANUFACTURER;
        MODEL = Build.MODEL;
        String str = DEVICE;
        String str2 = MODEL;
        String str3 = MANUFACTURER;
        int i3 = SDK_INT;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 17 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append(", ");
        sb.append(str2);
        sb.append(", ");
        sb.append(str3);
        sb.append(", ");
        sb.append(i3);
        DEVICE_DEBUG_INFO = sb.toString();
        EMPTY_BYTE_ARRAY = new byte[0];
        a = Pattern.compile("(\\d\\d\\d\\d)\\-(\\d\\d)\\-(\\d\\d)[Tt](\\d\\d):(\\d\\d):(\\d\\d)([\\.,](\\d+))?([Zz]|((\\+|\\-)(\\d?\\d):?(\\d\\d)))?");
        b = Pattern.compile("^(-)?P(([0-9]*)Y)?(([0-9]*)M)?(([0-9]*)D)?(T(([0-9]*)H)?(([0-9]*)M)?(([0-9.]*)S)?)?$");
        c = Pattern.compile("%([A-Fa-f0-9]{2})");
        d = Pattern.compile(".*\\.isml?(?:/(manifest(.*))?)?");
        f = new String[]{"alb", "sq", "arm", "hy", "baq", "eu", "bur", "my", "tib", "bo", "chi", "zh", "cze", "cs", "dut", "nl", "ger", ServerSetting.SERVER_DE, "gre", "el", "fre", "fr", "geo", "ka", "ice", ai.ae, b.B, "mk", "mao", "mi", "may", "ms", "per", "fa", "rum", "ro", "scc", "hbs-srp", "slo", "sk", "wel", "cy", "id", "ms-ind", "iw", "he", "heb", "he", "ji", "yi", ServerSetting.SERVER_IN, "ms-ind", "ind", "ms-ind", "nb", "no-nob", "nob", "no-nob", "nn", "no-nno", "nno", "no-nno", ServerSetting.SERVER_TW, "ak-twi", "twi", "ak-twi", "bs", "hbs-bos", "bos", "hbs-bos", "hr", "hbs-hrv", "hrv", "hbs-hrv", "sr", "hbs-srp", "srp", "hbs-srp", "cmn", "zh-cmn", "hak", "zh-hak", "nan", "zh-nan", "hsn", "zh-hsn"};
        g = new String[]{"i-lux", "lb", "i-hak", "zh-hak", "i-navajo", "nv", "no-bok", "no-nob", "no-nyn", "no-nno", "zh-guoyu", "zh-cmn", "zh-hakka", "zh-hak", "zh-min-nan", "zh-nan", "zh-xiang", "zh-hsn"};
        h = new int[]{0, 79764919, 159529838, 222504665, 319059676, 398814059, 445009330, 507990021, 638119352, 583659535, 797628118, 726387553, 890018660, 835552979, 1015980042, 944750013, 1276238704, 1221641927, 1167319070, 1095957929, 1595256236, 1540665371, 1452775106, 1381403509, 1780037320, 1859660671, 1671105958, 1733955601, 2031960084, 2111593891, 1889500026, 1952343757, -1742489888, -1662866601, -1851683442, -1788833735, -1960329156, -1880695413, -2103051438, -2040207643, -1104454824, -1159051537, -1213636554, -1284997759, -1389417084, -1444007885, -1532160278, -1603531939, -734892656, -789352409, -575645954, -646886583, -952755380, -1007220997, -827056094, -898286187, -231047128, -151282273, -71779514, -8804623, -515967244, -436212925, -390279782, -327299027, 881225847, 809987520, 1023691545, 969234094, 662832811, 591600412, 771767749, 717299826, 311336399, 374308984, 453813921, 533576470, 25881363, 88864420, 134795389, 214552010, 2023205639, 2086057648, 1897238633, 1976864222, 1804852699, 1867694188, 1645340341, 1724971778, 1587496639, 1516133128, 1461550545, 1406951526, 1302016099, 1230646740, 1142491917, 1087903418, -1398421865, -1469785312, -1524105735, -1578704818, -1079922613, -1151291908, -1239184603, -1293773166, -1968362705, -1905510760, -2094067647, -2014441994, -1716953613, -1654112188, -1876203875, -1796572374, -525066777, -462094256, -382327159, -302564546, -206542021, -143559028, -97365931, -17609246, -960696225, -1031934488, -817968335, -872425850, -709327229, -780559564, -600130067, -654598054, 1762451694, 1842216281, 1619975040, 1682949687, 2047383090, 2127137669, 1938468188, 2001449195, 1325665622, 1271206113, 1183200824, 1111960463, 1543535498, 1489069629, 1434599652, 1363369299, 622672798, 568075817, 748617968, 677256519, 907627842, 853037301, 1067152940, 995781531, 51762726, 131386257, 177728840, 240578815, 269590778, 349224269, 429104020, 491947555, -248556018, -168932423, -122852000, -60002089, -500490030, -420856475, -341238852, -278395381, -685261898, -739858943, -559578920, -630940305, -1004286614, -1058877219, -845023740, -916395085, -1119974018, -1174433591, -1262701040, -1333941337, -1371866206, -1426332139, -1481064244, -1552294533, -1690935098, -1611170447, -1833673816, -1770699233, -2009983462, -1930228819, -2119160460, -2056179517, 1569362073, 1498123566, 1409854455, 1355396672, 1317987909, 1246755826, 1192025387, 1137557660, 2072149281, 2135122070, 1912620623, 1992383480, 1753615357, 1816598090, 1627664531, 1707420964, 295390185, 358241886, 404320391, 483945776, 43990325, 106832002, 186451547, 266083308, 932423249, 861060070, 1041341759, 986742920, 613929101, 542559546, 756411363, 701822548, -978770311, -1050133554, -869589737, -924188512, -693284699, -764654318, -550540341, -605129092, -475935807, -413084042, -366743377, -287118056, -257573603, -194731862, -114850189, -35218492, -1984365303, -1921392450, -2143631769, -2063868976, -1698919467, -1635936670, -1824608069, -1744851700, -1347415887, -1418654458, -1506661409, -1561119128, -1129027987, -1200260134, -1254728445, -1309196108};
        i = new int[]{0, 7, 14, 9, 28, 27, 18, 21, 56, 63, 54, 49, 36, 35, 42, 45, 112, 119, 126, 121, 108, 107, 98, 101, 72, 79, 70, 65, 84, 83, 90, 93, 224, 231, 238, 233, 252, 251, 242, 245, 216, 223, 214, 209, 196, 195, 202, 205, 144, Opcodes.DCMPL, Opcodes.IFLE, Opcodes.IFEQ, 140, 139, 130, 133, 168, 175, Opcodes.IF_ACMPNE, Opcodes.IF_ICMPLT, Opcodes.GETFIELD, 179, 186, PsExtractor.PRIVATE_STREAM_1, Opcodes.IFNONNULL, 192, 201, 206, 219, 220, 213, 210, 255, 248, 241, 246, 227, 228, 237, 234, Opcodes.INVOKESPECIAL, Opcodes.ARETURN, Opcodes.INVOKEINTERFACE, 190, 171, TsExtractor.TS_STREAM_TYPE_AC4, Opcodes.IF_ACMPEQ, Opcodes.IF_ICMPGE, 143, 136, 129, 134, 147, Opcodes.LCMP, 157, Opcodes.IFNE, 39, 32, 41, 46, 59, 60, 53, 50, 31, 24, 17, 22, 3, 4, 13, 10, 87, 80, 89, 94, 75, 76, 69, 66, 111, 104, 97, 102, 115, 116, 125, 122, 137, ScriptIntrinsicBLAS.RIGHT, 135, 128, Opcodes.FCMPL, 146, 155, BuildConfig.MiConnectVersionBuild, Opcodes.RETURN, Opcodes.INVOKEVIRTUAL, 191, Opcodes.INVOKESTATIC, 173, 170, Opcodes.IF_ICMPGT, 164, 249, DnsRecord.CLASS_NONE, 247, PsExtractor.VIDEO_STREAM_MASK, 229, 226, 235, 236, Opcodes.INSTANCEOF, Opcodes.IFNULL, HttpStatus.MULTI_STATUS_207, 200, 221, 218, 211, 212, 105, 110, 103, 96, 117, 114, 123, 124, 81, 86, 95, 88, 77, 74, 67, 68, 25, 30, 23, 16, 5, 2, 11, 12, 33, 38, 47, 40, 61, 58, 51, 52, 78, 73, 64, 71, 82, 85, 92, 91, 118, 113, 120, 127, 106, 109, 100, 99, 62, 57, 48, 55, 34, 37, 44, 43, 6, 1, 8, 15, 26, 29, 20, 19, 174, Opcodes.RET, Opcodes.IF_ICMPNE, Opcodes.GOTO, Opcodes.GETSTATIC, Opcodes.PUTFIELD, 188, Opcodes.NEW, AnimTask.MAX_PAGE_SIZE, 145, 152, Opcodes.IF_ICMPEQ, TsExtractor.TS_STREAM_TYPE_DTS, ScriptIntrinsicBLAS.LEFT, 132, ScriptIntrinsicBLAS.NON_UNIT, 222, 217, 208, 215, 194, 197, 204, 203, 230, 225, 232, 239, 250, 253, 244, 243};
    }

    private Util() {
    }

    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[4096];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    @Nullable
    public static ComponentName startForegroundService(Context context, Intent intent) {
        if (SDK_INT >= 26) {
            return context.startForegroundService(intent);
        }
        return context.startService(intent);
    }

    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, Uri... uriArr) {
        if (SDK_INT < 23) {
            return false;
        }
        for (Uri uri : uriArr) {
            if (isLocalFileUri(uri)) {
                return a(activity);
            }
        }
        return false;
    }

    public static boolean maybeRequestReadExternalStoragePermission(Activity activity, MediaItem... mediaItemArr) {
        if (SDK_INT < 23) {
            return false;
        }
        for (MediaItem mediaItem : mediaItemArr) {
            if (mediaItem.playbackProperties != null) {
                if (isLocalFileUri(mediaItem.playbackProperties.uri)) {
                    return a(activity);
                }
                for (int i2 = 0; i2 < mediaItem.playbackProperties.subtitles.size(); i2++) {
                    if (isLocalFileUri(mediaItem.playbackProperties.subtitles.get(i2).uri)) {
                        return a(activity);
                    }
                }
                continue;
            }
        }
        return false;
    }

    public static boolean checkCleartextTrafficPermitted(MediaItem... mediaItemArr) {
        if (SDK_INT < 24) {
            return true;
        }
        for (MediaItem mediaItem : mediaItemArr) {
            if (mediaItem.playbackProperties != null) {
                if (a(mediaItem.playbackProperties.uri)) {
                    return false;
                }
                for (int i2 = 0; i2 < mediaItem.playbackProperties.subtitles.size(); i2++) {
                    if (a(mediaItem.playbackProperties.subtitles.get(i2).uri)) {
                        return false;
                    }
                }
                continue;
            }
        }
        return true;
    }

    public static boolean isLocalFileUri(Uri uri) {
        String scheme = uri.getScheme();
        return TextUtils.isEmpty(scheme) || "file".equals(scheme);
    }

    public static boolean areEqual(@Nullable Object obj, @Nullable Object obj2) {
        if (obj == null) {
            return obj2 == null;
        }
        return obj.equals(obj2);
    }

    public static boolean contains(Object[] objArr, @Nullable Object obj) {
        for (Object obj2 : objArr) {
            if (areEqual(obj2, obj)) {
                return true;
            }
        }
        return false;
    }

    public static <T> void removeRange(List<T> list, int i2, int i3) {
        if (i2 < 0 || i3 > list.size() || i2 > i3) {
            throw new IllegalArgumentException();
        } else if (i2 != i3) {
            list.subList(i2, i3).clear();
        }
    }

    public static <T> T[] nullSafeArrayCopy(T[] tArr, int i2) {
        Assertions.checkArgument(i2 <= tArr.length);
        return (T[]) Arrays.copyOf(tArr, i2);
    }

    public static <T> T[] nullSafeArrayCopyOfRange(T[] tArr, int i2, int i3) {
        boolean z = true;
        Assertions.checkArgument(i2 >= 0);
        if (i3 > tArr.length) {
            z = false;
        }
        Assertions.checkArgument(z);
        return (T[]) Arrays.copyOfRange(tArr, i2, i3);
    }

    public static <T> T[] nullSafeArrayAppend(T[] tArr, T t) {
        Object[] copyOf = Arrays.copyOf(tArr, tArr.length + 1);
        copyOf[tArr.length] = t;
        return (T[]) castNonNullTypeArray(copyOf);
    }

    public static <T> T[] nullSafeArrayConcatenation(T[] tArr, T[] tArr2) {
        T[] tArr3 = (T[]) Arrays.copyOf(tArr, tArr.length + tArr2.length);
        System.arraycopy(tArr2, 0, tArr3, tArr.length, tArr2.length);
        return tArr3;
    }

    public static <T> void nullSafeListToArray(List<T> list, T[] tArr) {
        Assertions.checkState(list.size() == tArr.length);
        list.toArray(tArr);
    }

    public static Handler createHandlerForCurrentLooper() {
        return createHandlerForCurrentLooper(null);
    }

    public static Handler createHandlerForCurrentLooper(@Nullable Handler.Callback callback) {
        return createHandler((Looper) Assertions.checkStateNotNull(Looper.myLooper()), callback);
    }

    public static Handler createHandlerForCurrentOrMainLooper() {
        return createHandlerForCurrentOrMainLooper(null);
    }

    public static Handler createHandlerForCurrentOrMainLooper(@Nullable Handler.Callback callback) {
        return createHandler(getCurrentOrMainLooper(), callback);
    }

    public static Handler createHandler(Looper looper, @Nullable Handler.Callback callback) {
        return new Handler(looper, callback);
    }

    public static boolean postOrRun(Handler handler, Runnable runnable) {
        if (!handler.getLooper().getThread().isAlive()) {
            return false;
        }
        if (handler.getLooper() != Looper.myLooper()) {
            return handler.post(runnable);
        }
        runnable.run();
        return true;
    }

    public static Looper getCurrentOrMainLooper() {
        Looper myLooper = Looper.myLooper();
        return myLooper != null ? myLooper : Looper.getMainLooper();
    }

    public static /* synthetic */ Thread a(String str, Runnable runnable) {
        return new Thread(runnable, str);
    }

    public static ExecutorService newSingleThreadExecutor(final String str) {
        return Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.google.android.exoplayer2.util.-$$Lambda$Util$U0WicrBmYE4RSt9ILhJpj1NQ228
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                Thread a2;
                a2 = Util.a(str, runnable);
                return a2;
            }
        });
    }

    public static byte[] readToEnd(DataSource dataSource) throws IOException {
        byte[] bArr = new byte[1024];
        int i2 = 0;
        int i3 = 0;
        while (i2 != -1) {
            if (i3 == bArr.length) {
                bArr = Arrays.copyOf(bArr, bArr.length * 2);
            }
            i2 = dataSource.read(bArr, i3, bArr.length - i3);
            if (i2 != -1) {
                i3 += i2;
            }
        }
        return Arrays.copyOf(bArr, i3);
    }

    public static byte[] readExactly(DataSource dataSource, int i2) throws IOException {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int read = dataSource.read(bArr, i3, bArr.length - i3);
            if (read != -1) {
                i3 += read;
            } else {
                StringBuilder sb = new StringBuilder(56);
                sb.append("Not enough data could be read: ");
                sb.append(i3);
                sb.append(" < ");
                sb.append(i2);
                throw new IllegalStateException(sb.toString());
            }
        }
        return bArr;
    }

    public static void closeQuietly(@Nullable DataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (IOException unused) {
            }
        }
    }

    public static void closeQuietly(@Nullable Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException unused) {
            }
        }
    }

    public static boolean readBoolean(Parcel parcel) {
        return parcel.readInt() != 0;
    }

    public static void writeBoolean(Parcel parcel, boolean z) {
        parcel.writeInt(z ? 1 : 0);
    }

    public static String getLocaleLanguageTag(Locale locale) {
        return SDK_INT >= 21 ? a(locale) : locale.toString();
    }

    public static String normalizeLanguageCode(String str) {
        if (str == null) {
            return null;
        }
        String replace = str.replace('_', '-');
        if (!replace.isEmpty() && !replace.equals(C.LANGUAGE_UNDETERMINED)) {
            str = replace;
        }
        String lowerCase = Ascii.toLowerCase(str);
        String str2 = splitAtFirst(lowerCase, Constants.ACCEPT_TIME_SEPARATOR_SERVER)[0];
        if (e == null) {
            e = b();
        }
        String str3 = e.get(str2);
        if (str3 != null) {
            String valueOf = String.valueOf(str3);
            String valueOf2 = String.valueOf(lowerCase.substring(str2.length()));
            lowerCase = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            str2 = str3;
        }
        return ("no".equals(str2) || ai.aA.equals(str2) || "zh".equals(str2)) ? b(lowerCase) : lowerCase;
    }

    public static String fromUtf8Bytes(byte[] bArr) {
        return new String(bArr, Charsets.UTF_8);
    }

    public static String fromUtf8Bytes(byte[] bArr, int i2, int i3) {
        return new String(bArr, i2, i3, Charsets.UTF_8);
    }

    public static byte[] getUtf8Bytes(String str) {
        return str.getBytes(Charsets.UTF_8);
    }

    public static String[] split(String str, String str2) {
        return str.split(str2, -1);
    }

    public static String[] splitAtFirst(String str, String str2) {
        return str.split(str2, 2);
    }

    public static String formatInvariant(String str, Object... objArr) {
        return String.format(Locale.US, str, objArr);
    }

    public static int ceilDivide(int i2, int i3) {
        return ((i2 + i3) - 1) / i3;
    }

    public static long ceilDivide(long j, long j2) {
        return ((j + j2) - 1) / j2;
    }

    public static int constrainValue(int i2, int i3, int i4) {
        return Math.max(i3, Math.min(i2, i4));
    }

    public static long constrainValue(long j, long j2, long j3) {
        return Math.max(j2, Math.min(j, j3));
    }

    public static float constrainValue(float f2, float f3, float f4) {
        return Math.max(f3, Math.min(f2, f4));
    }

    public static int linearSearch(int[] iArr, int i2) {
        for (int i3 = 0; i3 < iArr.length; i3++) {
            if (iArr[i3] == i2) {
                return i3;
            }
        }
        return -1;
    }

    public static int linearSearch(long[] jArr, long j) {
        for (int i2 = 0; i2 < jArr.length; i2++) {
            if (jArr[i2] == j) {
                return i2;
            }
        }
        return -1;
    }

    public static int binarySearchFloor(int[] iArr, int i2, boolean z, boolean z2) {
        int i3;
        int binarySearch = Arrays.binarySearch(iArr, i2);
        if (binarySearch < 0) {
            i3 = -(binarySearch + 2);
        } else {
            do {
                binarySearch--;
                if (binarySearch < 0) {
                    break;
                }
            } while (iArr[binarySearch] == i2);
            i3 = z ? binarySearch + 1 : binarySearch;
        }
        return z2 ? Math.max(0, i3) : i3;
    }

    public static int binarySearchFloor(long[] jArr, long j, boolean z, boolean z2) {
        int i2;
        int binarySearch = Arrays.binarySearch(jArr, j);
        if (binarySearch < 0) {
            i2 = -(binarySearch + 2);
        } else {
            do {
                binarySearch--;
                if (binarySearch < 0) {
                    break;
                }
            } while (jArr[binarySearch] == j);
            i2 = z ? binarySearch + 1 : binarySearch;
        }
        return z2 ? Math.max(0, i2) : i2;
    }

    public static <T extends Comparable<? super T>> int binarySearchFloor(List<? extends Comparable<? super T>> list, T t, boolean z, boolean z2) {
        int i2;
        int binarySearch = Collections.binarySearch(list, t);
        if (binarySearch < 0) {
            i2 = -(binarySearch + 2);
        } else {
            do {
                binarySearch--;
                if (binarySearch < 0) {
                    break;
                }
            } while (((Comparable) list.get(binarySearch)).compareTo(t) == 0);
            i2 = z ? binarySearch + 1 : binarySearch;
        }
        return z2 ? Math.max(0, i2) : i2;
    }

    public static int binarySearchFloor(LongArray longArray, long j, boolean z, boolean z2) {
        int i2;
        int size = longArray.size() - 1;
        int i3 = 0;
        while (i3 <= size) {
            int i4 = (i3 + size) >>> 1;
            if (longArray.get(i4) < j) {
                i3 = i4 + 1;
            } else {
                size = i4 - 1;
            }
        }
        if (z && (i2 = size + 1) < longArray.size() && longArray.get(i2) == j) {
            return i2;
        }
        if (!z2 || size != -1) {
            return size;
        }
        return 0;
    }

    public static int binarySearchCeil(int[] iArr, int i2, boolean z, boolean z2) {
        int i3;
        int binarySearch = Arrays.binarySearch(iArr, i2);
        if (binarySearch < 0) {
            i3 = ~binarySearch;
        } else {
            do {
                binarySearch++;
                if (binarySearch >= iArr.length) {
                    break;
                }
            } while (iArr[binarySearch] == i2);
            i3 = z ? binarySearch - 1 : binarySearch;
        }
        return z2 ? Math.min(iArr.length - 1, i3) : i3;
    }

    public static int binarySearchCeil(long[] jArr, long j, boolean z, boolean z2) {
        int i2;
        int binarySearch = Arrays.binarySearch(jArr, j);
        if (binarySearch < 0) {
            i2 = ~binarySearch;
        } else {
            do {
                binarySearch++;
                if (binarySearch >= jArr.length) {
                    break;
                }
            } while (jArr[binarySearch] == j);
            i2 = z ? binarySearch - 1 : binarySearch;
        }
        return z2 ? Math.min(jArr.length - 1, i2) : i2;
    }

    public static <T extends Comparable<? super T>> int binarySearchCeil(List<? extends Comparable<? super T>> list, T t, boolean z, boolean z2) {
        int i2;
        int binarySearch = Collections.binarySearch(list, t);
        if (binarySearch < 0) {
            i2 = ~binarySearch;
        } else {
            int size = list.size();
            do {
                binarySearch++;
                if (binarySearch >= size) {
                    break;
                }
            } while (((Comparable) list.get(binarySearch)).compareTo(t) == 0);
            i2 = z ? binarySearch - 1 : binarySearch;
        }
        return z2 ? Math.min(list.size() - 1, i2) : i2;
    }

    @RequiresApi(18)
    public static long minValue(SparseLongArray sparseLongArray) {
        if (sparseLongArray.size() != 0) {
            long j = Long.MAX_VALUE;
            for (int i2 = 0; i2 < sparseLongArray.size(); i2++) {
                j = Math.min(j, sparseLongArray.valueAt(i2));
            }
            return j;
        }
        throw new NoSuchElementException();
    }

    public static long parseXsDuration(String str) {
        Matcher matcher = b.matcher(str);
        if (!matcher.matches()) {
            return (long) (Double.parseDouble(str) * 3600.0d * 1000.0d);
        }
        boolean isEmpty = true ^ TextUtils.isEmpty(matcher.group(1));
        String group = matcher.group(3);
        double d2 = 0.0d;
        double parseDouble = group != null ? Double.parseDouble(group) * 3.1556908E7d : 0.0d;
        String group2 = matcher.group(5);
        double parseDouble2 = parseDouble + (group2 != null ? Double.parseDouble(group2) * 2629739.0d : 0.0d);
        String group3 = matcher.group(7);
        double parseDouble3 = parseDouble2 + (group3 != null ? Double.parseDouble(group3) * 86400.0d : 0.0d);
        String group4 = matcher.group(10);
        double parseDouble4 = parseDouble3 + (group4 != null ? 3600.0d * Double.parseDouble(group4) : 0.0d);
        String group5 = matcher.group(12);
        double parseDouble5 = parseDouble4 + (group5 != null ? Double.parseDouble(group5) * 60.0d : 0.0d);
        String group6 = matcher.group(14);
        if (group6 != null) {
            d2 = Double.parseDouble(group6);
        }
        long j = (long) ((parseDouble5 + d2) * 1000.0d);
        return isEmpty ? -j : j;
    }

    public static long parseXsDateTime(String str) throws ParserException {
        Matcher matcher = a.matcher(str);
        if (!matcher.matches()) {
            String valueOf = String.valueOf(str);
            throw ParserException.createForMalformedContainer(valueOf.length() != 0 ? "Invalid date/time format: ".concat(valueOf) : new String("Invalid date/time format: "), null);
        }
        int i2 = 0;
        if (matcher.group(9) != null && !matcher.group(9).equalsIgnoreCase("Z")) {
            i2 = (Integer.parseInt(matcher.group(12)) * 60) + Integer.parseInt(matcher.group(13));
            if (Constants.ACCEPT_TIME_SEPARATOR_SERVER.equals(matcher.group(11))) {
                i2 *= -1;
            }
        }
        GregorianCalendar gregorianCalendar = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        gregorianCalendar.clear();
        gregorianCalendar.set(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) - 1, Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)), Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
        if (!TextUtils.isEmpty(matcher.group(8))) {
            String valueOf2 = String.valueOf(matcher.group(8));
            gregorianCalendar.set(14, new BigDecimal(valueOf2.length() != 0 ? "0.".concat(valueOf2) : new String("0.")).movePointRight(3).intValue());
        }
        long timeInMillis = gregorianCalendar.getTimeInMillis();
        return i2 != 0 ? timeInMillis - (i2 * 60000) : timeInMillis;
    }

    public static long scaleLargeTimestamp(long j, long j2, long j3) {
        int i2 = (j3 > j2 ? 1 : (j3 == j2 ? 0 : -1));
        if (i2 >= 0 && j3 % j2 == 0) {
            return j / (j3 / j2);
        }
        if (i2 < 0 && j2 % j3 == 0) {
            return j * (j2 / j3);
        }
        return (long) (j * (j2 / j3));
    }

    public static long[] scaleLargeTimestamps(List<Long> list, long j, long j2) {
        long[] jArr = new long[list.size()];
        int i2 = (j2 > j ? 1 : (j2 == j ? 0 : -1));
        int i3 = 0;
        if (i2 >= 0 && j2 % j == 0) {
            long j3 = j2 / j;
            while (i3 < jArr.length) {
                jArr[i3] = list.get(i3).longValue() / j3;
                i3++;
            }
        } else if (i2 >= 0 || j % j2 != 0) {
            double d2 = j / j2;
            while (i3 < jArr.length) {
                jArr[i3] = (long) (list.get(i3).longValue() * d2);
                i3++;
            }
        } else {
            long j4 = j / j2;
            while (i3 < jArr.length) {
                jArr[i3] = list.get(i3).longValue() * j4;
                i3++;
            }
        }
        return jArr;
    }

    public static void scaleLargeTimestampsInPlace(long[] jArr, long j, long j2) {
        int i2 = (j2 > j ? 1 : (j2 == j ? 0 : -1));
        int i3 = 0;
        if (i2 >= 0 && j2 % j == 0) {
            long j3 = j2 / j;
            while (i3 < jArr.length) {
                jArr[i3] = jArr[i3] / j3;
                i3++;
            }
        } else if (i2 >= 0 || j % j2 != 0) {
            double d2 = j / j2;
            while (i3 < jArr.length) {
                jArr[i3] = (long) (jArr[i3] * d2);
                i3++;
            }
        } else {
            long j4 = j / j2;
            while (i3 < jArr.length) {
                jArr[i3] = jArr[i3] * j4;
                i3++;
            }
        }
    }

    public static long getMediaDurationForPlayoutDuration(long j, float f2) {
        return f2 == 1.0f ? j : Math.round(j * f2);
    }

    public static long getPlayoutDurationForMediaDuration(long j, float f2) {
        return f2 == 1.0f ? j : Math.round(j / f2);
    }

    public static int getIntegerCodeForString(String str) {
        int length = str.length();
        Assertions.checkArgument(length <= 4);
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            i2 = (i2 << 8) | str.charAt(i3);
        }
        return i2;
    }

    public static long toLong(int i2, int i3) {
        return toUnsignedLong(i3) | (toUnsignedLong(i2) << 32);
    }

    public static CharSequence truncateAscii(CharSequence charSequence, int i2) {
        return charSequence.length() <= i2 ? charSequence : charSequence.subSequence(0, i2);
    }

    public static byte[] getBytesFromHexString(String str) {
        byte[] bArr = new byte[str.length() / 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) ((Character.digit(str.charAt(i3), 16) << 4) + Character.digit(str.charAt(i3 + 1), 16));
        }
        return bArr;
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder sb = new StringBuilder(bArr.length * 2);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            sb.append(Character.forDigit((bArr[i2] >> 4) & 15, 16));
            sb.append(Character.forDigit(bArr[i2] & 15, 16));
        }
        return sb.toString();
    }

    public static String getCommaDelimitedSimpleClassNames(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < objArr.length; i2++) {
            sb.append(objArr[i2].getClass().getSimpleName());
            if (i2 < objArr.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public static String getUserAgent(Context context, String str) {
        String str2;
        try {
            str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException unused) {
            str2 = "?";
        }
        String str3 = Build.VERSION.RELEASE;
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 38 + String.valueOf(str2).length() + String.valueOf(str3).length());
        sb.append(str);
        sb.append("/");
        sb.append(str2);
        sb.append(" (Linux;Android ");
        sb.append(str3);
        sb.append(") ");
        sb.append(ExoPlayerLibraryInfo.VERSION_SLASHY);
        return sb.toString();
    }

    public static int getCodecCountOfType(@Nullable String str, int i2) {
        int i3 = 0;
        for (String str2 : splitCodecs(str)) {
            if (i2 == MimeTypes.getTrackTypeOfCodec(str2)) {
                i3++;
            }
        }
        return i3;
    }

    @Nullable
    public static String getCodecsOfType(@Nullable String str, int i2) {
        String[] splitCodecs = splitCodecs(str);
        if (splitCodecs.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String str2 : splitCodecs) {
            if (i2 == MimeTypes.getTrackTypeOfCodec(str2)) {
                if (sb.length() > 0) {
                    sb.append(Constants.ACCEPT_TIME_SEPARATOR_SP);
                }
                sb.append(str2);
            }
        }
        if (sb.length() > 0) {
            return sb.toString();
        }
        return null;
    }

    public static String[] splitCodecs(@Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return new String[0];
        }
        return split(str.trim(), "(\\s*,\\s*)");
    }

    public static Format getPcmFormat(int i2, int i3, int i4) {
        return new Format.Builder().setSampleMimeType(MimeTypes.AUDIO_RAW).setChannelCount(i3).setSampleRate(i4).setPcmEncoding(i2).build();
    }

    public static int getAudioTrackChannelConfig(int i2) {
        switch (i2) {
            case 1:
                return 4;
            case 2:
                return 12;
            case 3:
                return 28;
            case 4:
                return 204;
            case 5:
                return 220;
            case 6:
                return 252;
            case 7:
                return 1276;
            case 8:
                int i3 = SDK_INT;
                return (i3 < 23 && i3 < 21) ? 0 : 6396;
            default:
                return 0;
        }
    }

    public static int getPcmFrameSize(int i2, int i3) {
        if (i2 != 268435456) {
            if (i2 == 536870912) {
                return i3 * 3;
            }
            if (i2 != 805306368) {
                switch (i2) {
                    case 2:
                        break;
                    case 3:
                        return i3;
                    case 4:
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
            return i3 * 4;
        }
        return i3 * 2;
    }

    @Nullable
    public static UUID getDrmUuid(String str) {
        char c2;
        String lowerCase = Ascii.toLowerCase(str);
        int hashCode = lowerCase.hashCode();
        if (hashCode == -1860423953) {
            if (lowerCase.equals("playready")) {
                c2 = 1;
            }
            c2 = 65535;
        } else if (hashCode != -1400551171) {
            if (hashCode == 790309106 && lowerCase.equals("clearkey")) {
                c2 = 2;
            }
            c2 = 65535;
        } else {
            if (lowerCase.equals("widevine")) {
                c2 = 0;
            }
            c2 = 65535;
        }
        switch (c2) {
            case 0:
                return C.WIDEVINE_UUID;
            case 1:
                return C.PLAYREADY_UUID;
            case 2:
                return C.CLEARKEY_UUID;
            default:
                try {
                    return UUID.fromString(str);
                } catch (RuntimeException unused) {
                    return null;
                }
        }
    }

    public static int inferContentType(Uri uri, @Nullable String str) {
        if (TextUtils.isEmpty(str)) {
            return inferContentType(uri);
        }
        String valueOf = String.valueOf(str);
        return inferContentType(valueOf.length() != 0 ? ".".concat(valueOf) : new String("."));
    }

    public static int inferContentType(Uri uri) {
        String scheme = uri.getScheme();
        if (scheme != null && Ascii.equalsIgnoreCase("rtsp", scheme)) {
            return 3;
        }
        String path = uri.getPath();
        if (path == null) {
            return 4;
        }
        return inferContentType(path);
    }

    public static int inferContentType(String str) {
        String lowerCase = Ascii.toLowerCase(str);
        if (lowerCase.endsWith(".mpd")) {
            return 0;
        }
        if (lowerCase.endsWith(".m3u8")) {
            return 2;
        }
        Matcher matcher = d.matcher(lowerCase);
        if (!matcher.matches()) {
            return 4;
        }
        String group = matcher.group(2);
        if (group == null) {
            return 1;
        }
        if (group.contains("format=mpd-time-csf")) {
            return 0;
        }
        return group.contains("format=m3u8-aapl") ? 2 : 1;
    }

    public static int inferContentTypeForUriAndMimeType(Uri uri, @Nullable String str) {
        if (str == null) {
            return inferContentType(uri);
        }
        char c2 = 65535;
        int hashCode = str.hashCode();
        if (hashCode != -979127466) {
            if (hashCode != -156749520) {
                if (hashCode != 64194685) {
                    if (hashCode == 1154777587 && str.equals(MimeTypes.APPLICATION_RTSP)) {
                        c2 = 3;
                    }
                } else if (str.equals(MimeTypes.APPLICATION_MPD)) {
                    c2 = 0;
                }
            } else if (str.equals(MimeTypes.APPLICATION_SS)) {
                c2 = 2;
            }
        } else if (str.equals(MimeTypes.APPLICATION_M3U8)) {
            c2 = 1;
        }
        switch (c2) {
            case 0:
                return 0;
            case 1:
                return 2;
            case 2:
                return 1;
            case 3:
                return 3;
            default:
                return 4;
        }
    }

    public static Uri fixSmoothStreamingIsmManifestUri(Uri uri) {
        String path = uri.getPath();
        if (path == null) {
            return uri;
        }
        Matcher matcher = d.matcher(Ascii.toLowerCase(path));
        return (!matcher.matches() || matcher.group(1) != null) ? uri : Uri.withAppendedPath(uri, "Manifest");
    }

    public static String getStringForTime(StringBuilder sb, Formatter formatter, long j) {
        if (j == C.TIME_UNSET) {
            j = 0;
        }
        String str = j < 0 ? Constants.ACCEPT_TIME_SEPARATOR_SERVER : "";
        long abs = (Math.abs(j) + 500) / 1000;
        long j2 = abs % 60;
        long j3 = (abs / 60) % 60;
        long j4 = abs / 3600;
        sb.setLength(0);
        return j4 > 0 ? formatter.format("%s%d:%02d:%02d", str, Long.valueOf(j4), Long.valueOf(j3), Long.valueOf(j2)).toString() : formatter.format("%s%02d:%02d", str, Long.valueOf(j3), Long.valueOf(j2)).toString();
    }

    public static String escapeFileName(String str) {
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (a(str.charAt(i4))) {
                i3++;
            }
        }
        if (i3 == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder((i3 * 2) + length);
        while (i3 > 0) {
            i2++;
            char charAt = str.charAt(i2);
            if (a(charAt)) {
                sb.append('%');
                sb.append(Integer.toHexString(charAt));
                i3--;
            } else {
                sb.append(charAt);
            }
        }
        if (i2 < length) {
            sb.append((CharSequence) str, i2, length);
        }
        return sb.toString();
    }

    @Nullable
    public static String unescapeFileName(String str) {
        int length = str.length();
        int i2 = 0;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            if (str.charAt(i4) == '%') {
                i3++;
            }
        }
        if (i3 == 0) {
            return str;
        }
        int i5 = length - (i3 * 2);
        StringBuilder sb = new StringBuilder(i5);
        Matcher matcher = c.matcher(str);
        while (i3 > 0 && matcher.find()) {
            sb.append((CharSequence) str, i2, matcher.start());
            sb.append((char) Integer.parseInt((String) Assertions.checkNotNull(matcher.group(1)), 16));
            i2 = matcher.end();
            i3--;
        }
        if (i2 < length) {
            sb.append((CharSequence) str, i2, length);
        }
        if (sb.length() != i5) {
            return null;
        }
        return sb.toString();
    }

    public static Uri getDataUriForString(String str, String str2) {
        String encodeToString = Base64.encodeToString(str2.getBytes(), 2);
        StringBuilder sb = new StringBuilder(String.valueOf(str).length() + 13 + String.valueOf(encodeToString).length());
        sb.append("data:");
        sb.append(str);
        sb.append(";base64,");
        sb.append(encodeToString);
        return Uri.parse(sb.toString());
    }

    public static void sneakyThrow(Throwable th) {
        a(th);
    }

    private static <T extends Throwable> void a(Throwable th) throws Throwable {
        throw th;
    }

    public static void recursiveDelete(File file) {
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                recursiveDelete(file2);
            }
        }
        file.delete();
    }

    public static File createTempDirectory(Context context, String str) throws IOException {
        File createTempFile = createTempFile(context, str);
        createTempFile.delete();
        createTempFile.mkdir();
        return createTempFile;
    }

    public static File createTempFile(Context context, String str) throws IOException {
        return File.createTempFile(str, null, (File) Assertions.checkNotNull(context.getCacheDir()));
    }

    public static int crc32(byte[] bArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            i4 = h[((i4 >>> 24) ^ (bArr[i2] & 255)) & 255] ^ (i4 << 8);
            i2++;
        }
        return i4;
    }

    public static int crc8(byte[] bArr, int i2, int i3, int i4) {
        while (i2 < i3) {
            i4 = i[i4 ^ (bArr[i2] & 255)];
            i2++;
        }
        return i4;
    }

    public static byte[] gzip(byte[] bArr) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e2) {
            throw new IllegalStateException(e2);
        }
    }

    public static int getBigEndianInt(ByteBuffer byteBuffer, int i2) {
        int i3 = byteBuffer.getInt(i2);
        return byteBuffer.order() == ByteOrder.BIG_ENDIAN ? i3 : Integer.reverseBytes(i3);
    }

    public static String getCountryCode(@Nullable Context context) {
        TelephonyManager telephonyManager;
        if (!(context == null || (telephonyManager = (TelephonyManager) context.getSystemService("phone")) == null)) {
            String networkCountryIso = telephonyManager.getNetworkCountryIso();
            if (!TextUtils.isEmpty(networkCountryIso)) {
                return Ascii.toUpperCase(networkCountryIso);
            }
        }
        return Ascii.toUpperCase(Locale.getDefault().getCountry());
    }

    public static String[] getSystemLanguageCodes() {
        String[] a2 = a();
        for (int i2 = 0; i2 < a2.length; i2++) {
            a2[i2] = normalizeLanguageCode(a2[i2]);
        }
        return a2;
    }

    public static boolean inflate(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, @Nullable Inflater inflater) {
        if (parsableByteArray.bytesLeft() <= 0) {
            return false;
        }
        if (parsableByteArray2.capacity() < parsableByteArray.bytesLeft()) {
            parsableByteArray2.ensureCapacity(parsableByteArray.bytesLeft() * 2);
        }
        if (inflater == null) {
            inflater = new Inflater();
        }
        inflater.setInput(parsableByteArray.getData(), parsableByteArray.getPosition(), parsableByteArray.bytesLeft());
        int i2 = 0;
        while (true) {
            try {
                i2 += inflater.inflate(parsableByteArray2.getData(), i2, parsableByteArray2.capacity() - i2);
                if (!inflater.finished()) {
                    if (inflater.needsDictionary() || inflater.needsInput()) {
                        break;
                    } else if (i2 == parsableByteArray2.capacity()) {
                        parsableByteArray2.ensureCapacity(parsableByteArray2.capacity() * 2);
                    }
                } else {
                    parsableByteArray2.setLimit(i2);
                    return true;
                }
            } catch (DataFormatException unused) {
                return false;
            } finally {
                inflater.reset();
            }
        }
        return false;
    }

    public static boolean isTv(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getApplicationContext().getSystemService("uimode");
        return uiModeManager != null && uiModeManager.getCurrentModeType() == 4;
    }

    public static Point getCurrentDisplayModeSize(Context context) {
        DisplayManager displayManager;
        Display display = (SDK_INT < 17 || (displayManager = (DisplayManager) context.getSystemService("display")) == null) ? null : displayManager.getDisplay(0);
        if (display == null) {
            display = ((WindowManager) Assertions.checkNotNull((WindowManager) context.getSystemService("window"))).getDefaultDisplay();
        }
        return getCurrentDisplayModeSize(context, display);
    }

    public static Point getCurrentDisplayModeSize(Context context, Display display) {
        String str;
        if (display.getDisplayId() == 0 && isTv(context)) {
            if (SDK_INT < 28) {
                str = a("sys.display-size");
            } else {
                str = a("vendor.display-size");
            }
            if (!TextUtils.isEmpty(str)) {
                try {
                    String[] split = split(str.trim(), "x");
                    if (split.length == 2) {
                        int parseInt = Integer.parseInt(split[0]);
                        int parseInt2 = Integer.parseInt(split[1]);
                        if (parseInt > 0 && parseInt2 > 0) {
                            return new Point(parseInt, parseInt2);
                        }
                    }
                } catch (NumberFormatException unused) {
                }
                String valueOf = String.valueOf(str);
                Log.e("Util", valueOf.length() != 0 ? "Invalid display size: ".concat(valueOf) : new String("Invalid display size: "));
            }
            if ("Sony".equals(MANUFACTURER) && MODEL.startsWith("BRAVIA") && context.getPackageManager().hasSystemFeature("com.sony.dtv.hardware.panel.qfhd")) {
                return new Point(3840, 2160);
            }
        }
        Point point = new Point();
        int i2 = SDK_INT;
        if (i2 >= 23) {
            a(display, point);
        } else if (i2 >= 17) {
            b(display, point);
        } else {
            c(display, point);
        }
        return point;
    }

    public static String getTrackTypeString(int i2) {
        switch (i2) {
            case 0:
                return "default";
            case 1:
                return "audio";
            case 2:
                return "video";
            case 3:
                return "text";
            case 4:
            default:
                if (i2 < 10000) {
                    return "?";
                }
                StringBuilder sb = new StringBuilder(20);
                sb.append("custom (");
                sb.append(i2);
                sb.append(")");
                return sb.toString();
            case 5:
                return PlayerEvent.METADATA;
            case 6:
                return "camera motion";
            case 7:
                return "none";
        }
    }

    public static long getNowUnixTimeMs(long j) {
        if (j == C.TIME_UNSET) {
            return System.currentTimeMillis();
        }
        return j + SystemClock.elapsedRealtime();
    }

    public static <T> void moveItems(List<T> list, int i2, int i3, int i4) {
        ArrayDeque arrayDeque = new ArrayDeque();
        for (int i5 = (i3 - i2) - 1; i5 >= 0; i5--) {
            arrayDeque.addFirst(list.remove(i2 + i5));
        }
        list.addAll(Math.min(i4, list.size()), arrayDeque);
    }

    public static boolean tableExists(SQLiteDatabase sQLiteDatabase, String str) {
        return DatabaseUtils.queryNumEntries(sQLiteDatabase, "sqlite_master", "tbl_name = ?", new String[]{str}) > 0;
    }

    public static int getErrorCodeFromPlatformDiagnosticsInfo(@Nullable String str) {
        String[] split;
        int length;
        if (str == null || (length = (split = split(str, "_")).length) < 2) {
            return 0;
        }
        String str2 = split[length - 1];
        boolean z = length >= 3 && "neg".equals(split[length - 2]);
        try {
            int parseInt = Integer.parseInt((String) Assertions.checkNotNull(str2));
            return z ? -parseInt : parseInt;
        } catch (NumberFormatException unused) {
            return 0;
        }
    }

    @Nullable
    private static String a(String str) {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            return (String) cls.getMethod(BluetoothConstants.GET, String.class).invoke(cls, str);
        } catch (Exception e2) {
            String valueOf = String.valueOf(str);
            Log.e("Util", valueOf.length() != 0 ? "Failed to read system property ".concat(valueOf) : new String("Failed to read system property "), e2);
            return null;
        }
    }

    @RequiresApi(23)
    private static void a(Display display, Point point) {
        Display.Mode mode = display.getMode();
        point.x = mode.getPhysicalWidth();
        point.y = mode.getPhysicalHeight();
    }

    @RequiresApi(17)
    private static void b(Display display, Point point) {
        display.getRealSize(point);
    }

    private static void c(Display display, Point point) {
        display.getSize(point);
    }

    private static String[] a() {
        Configuration configuration = Resources.getSystem().getConfiguration();
        return SDK_INT >= 24 ? a(configuration) : new String[]{getLocaleLanguageTag(configuration.locale)};
    }

    @RequiresApi(24)
    private static String[] a(Configuration configuration) {
        return split(configuration.getLocales().toLanguageTags(), Constants.ACCEPT_TIME_SEPARATOR_SP);
    }

    @RequiresApi(21)
    private static String a(Locale locale) {
        return locale.toLanguageTag();
    }

    private static HashMap<String, String> b() {
        String[] iSOLanguages = Locale.getISOLanguages();
        HashMap<String, String> hashMap = new HashMap<>(iSOLanguages.length + f.length);
        int i2 = 0;
        for (String str : iSOLanguages) {
            try {
                String iSO3Language = new Locale(str).getISO3Language();
                if (!TextUtils.isEmpty(iSO3Language)) {
                    hashMap.put(iSO3Language, str);
                }
            } catch (MissingResourceException unused) {
            }
        }
        while (true) {
            String[] strArr = f;
            if (i2 >= strArr.length) {
                return hashMap;
            }
            hashMap.put(strArr[i2], strArr[i2 + 1]);
            i2 += 2;
        }
    }

    @RequiresApi(api = 23)
    private static boolean a(Activity activity) {
        if (activity.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0) {
            return false;
        }
        activity.requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
        return true;
    }

    @RequiresApi(api = 24)
    private static boolean a(Uri uri) {
        return "http".equals(uri.getScheme()) && !NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted((String) Assertions.checkNotNull(uri.getHost()));
    }

    private static String b(String str) {
        int i2 = 0;
        while (true) {
            String[] strArr = g;
            if (i2 >= strArr.length) {
                return str;
            }
            if (str.startsWith(strArr[i2])) {
                String valueOf = String.valueOf(g[i2 + 1]);
                String valueOf2 = String.valueOf(str.substring(g[i2].length()));
                return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
            }
            i2 += 2;
        }
    }
}
