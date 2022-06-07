package kotlin.collections;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.micolauncher.module.homepage.record.HomePageRecordHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Arrays.kt */
@Metadata(d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a5\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u00032\u0010\u0010\u0004\u001a\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a#\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a;\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\u0087\bø\u0001\u0000¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\u0087\b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\""}, d2 = {"contentDeepEqualsImpl", "", ExifInterface.GPS_DIRECTION_TRUE, "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", HomePageRecordHelper.AREA_C, "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, k = 5, mv = {1, 5, 1}, xi = 1, xs = "kotlin/collections/ArraysKt")
/* loaded from: classes5.dex */
class c extends b {
    @NotNull
    public static final <T> List<T> flatten(@NotNull T[][] flatten) {
        Intrinsics.checkNotNullParameter(flatten, "$this$flatten");
        int i = 0;
        for (T[] tArr : flatten) {
            i += tArr.length;
        }
        ArrayList arrayList = new ArrayList(i);
        for (T[] tArr2 : flatten) {
            CollectionsKt.addAll(arrayList, tArr2);
        }
        return arrayList;
    }

    @NotNull
    public static final <T, R> Pair<List<T>, List<R>> unzip(@NotNull Pair<? extends T, ? extends R>[] unzip) {
        Intrinsics.checkNotNullParameter(unzip, "$this$unzip");
        ArrayList arrayList = new ArrayList(unzip.length);
        ArrayList arrayList2 = new ArrayList(unzip.length);
        for (Pair<? extends T, ? extends R> pair : unzip) {
            arrayList.add(pair.getFirst());
            arrayList2.add(pair.getSecond());
        }
        return TuplesKt.to(arrayList, arrayList2);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    @JvmName(name = "contentDeepEquals")
    public static final <T> boolean contentDeepEquals(@Nullable T[] tArr, @Nullable T[] tArr2) {
        if (tArr == tArr2) {
            return true;
        }
        if (tArr == null || tArr2 == null || tArr.length != tArr2.length) {
            return false;
        }
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            T t = tArr[i];
            T t2 = tArr2[i];
            if (t != t2) {
                if (t == null || t2 == null) {
                    return false;
                }
                if (!(t instanceof Object[]) || !(t2 instanceof Object[])) {
                    if (!(t instanceof byte[]) || !(t2 instanceof byte[])) {
                        if (!(t instanceof short[]) || !(t2 instanceof short[])) {
                            if (!(t instanceof int[]) || !(t2 instanceof int[])) {
                                if (!(t instanceof long[]) || !(t2 instanceof long[])) {
                                    if (!(t instanceof float[]) || !(t2 instanceof float[])) {
                                        if (!(t instanceof double[]) || !(t2 instanceof double[])) {
                                            if (!(t instanceof char[]) || !(t2 instanceof char[])) {
                                                if (!(t instanceof boolean[]) || !(t2 instanceof boolean[])) {
                                                    if (!(t instanceof UByteArray) || !(t2 instanceof UByteArray)) {
                                                        if (!(t instanceof UShortArray) || !(t2 instanceof UShortArray)) {
                                                            if (!(t instanceof UIntArray) || !(t2 instanceof UIntArray)) {
                                                                if (!(t instanceof ULongArray) || !(t2 instanceof ULongArray)) {
                                                                    if (!Intrinsics.areEqual(t, t2)) {
                                                                        return false;
                                                                    }
                                                                } else if (!UArraysKt.m1382contentEqualslec5QzE(((ULongArray) t).m1302unboximpl(), ((ULongArray) t2).m1302unboximpl())) {
                                                                    return false;
                                                                }
                                                            } else if (!UArraysKt.m1378contentEqualsKJPZfPQ(((UIntArray) t).m1277unboximpl(), ((UIntArray) t2).m1277unboximpl())) {
                                                                return false;
                                                            }
                                                        } else if (!UArraysKt.m1377contentEqualsFGO6Aew(((UShortArray) t).m1327unboximpl(), ((UShortArray) t2).m1327unboximpl())) {
                                                            return false;
                                                        }
                                                    } else if (!UArraysKt.m1380contentEqualskV0jMPg(((UByteArray) t).m1252unboximpl(), ((UByteArray) t2).m1252unboximpl())) {
                                                        return false;
                                                    }
                                                } else if (!Arrays.equals((boolean[]) t, (boolean[]) t2)) {
                                                    return false;
                                                }
                                            } else if (!Arrays.equals((char[]) t, (char[]) t2)) {
                                                return false;
                                            }
                                        } else if (!Arrays.equals((double[]) t, (double[]) t2)) {
                                            return false;
                                        }
                                    } else if (!Arrays.equals((float[]) t, (float[]) t2)) {
                                        return false;
                                    }
                                } else if (!Arrays.equals((long[]) t, (long[]) t2)) {
                                    return false;
                                }
                            } else if (!Arrays.equals((int[]) t, (int[]) t2)) {
                                return false;
                            }
                        } else if (!Arrays.equals((short[]) t, (short[]) t2)) {
                            return false;
                        }
                    } else if (!Arrays.equals((byte[]) t, (byte[]) t2)) {
                        return false;
                    }
                } else if (!ArraysKt.contentDeepEquals((Object[]) t, (Object[]) t2)) {
                    return false;
                }
            }
        }
        return true;
    }

    @SinceKotlin(version = "1.3")
    @JvmName(name = "contentDeepToString")
    @NotNull
    @PublishedApi
    public static final <T> String contentDeepToString(@Nullable T[] tArr) {
        if (tArr == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder((RangesKt.coerceAtMost(tArr.length, 429496729) * 5) + 2);
        a(tArr, sb, new ArrayList());
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    private static final <T> void a(T[] tArr, StringBuilder sb, List<Object[]> list) {
        if (list.contains(tArr)) {
            sb.append("[...]");
            return;
        }
        list.add(tArr);
        sb.append('[');
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            T t = tArr[i];
            if (t == null) {
                sb.append("null");
            } else if (t instanceof Object[]) {
                a((Object[]) t, sb, list);
            } else if (t instanceof byte[]) {
                String arrays = Arrays.toString((byte[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays, "java.util.Arrays.toString(this)");
                sb.append(arrays);
            } else if (t instanceof short[]) {
                String arrays2 = Arrays.toString((short[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays2, "java.util.Arrays.toString(this)");
                sb.append(arrays2);
            } else if (t instanceof int[]) {
                String arrays3 = Arrays.toString((int[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays3, "java.util.Arrays.toString(this)");
                sb.append(arrays3);
            } else if (t instanceof long[]) {
                String arrays4 = Arrays.toString((long[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays4, "java.util.Arrays.toString(this)");
                sb.append(arrays4);
            } else if (t instanceof float[]) {
                String arrays5 = Arrays.toString((float[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays5, "java.util.Arrays.toString(this)");
                sb.append(arrays5);
            } else if (t instanceof double[]) {
                String arrays6 = Arrays.toString((double[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays6, "java.util.Arrays.toString(this)");
                sb.append(arrays6);
            } else if (t instanceof char[]) {
                String arrays7 = Arrays.toString((char[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays7, "java.util.Arrays.toString(this)");
                sb.append(arrays7);
            } else if (t instanceof boolean[]) {
                String arrays8 = Arrays.toString((boolean[]) t);
                Intrinsics.checkNotNullExpressionValue(arrays8, "java.util.Arrays.toString(this)");
                sb.append(arrays8);
            } else {
                long[] jArr = null;
                byte[] bArr = null;
                short[] sArr = null;
                int[] iArr = null;
                if (t instanceof UByteArray) {
                    UByteArray uByteArray = (UByteArray) t;
                    if (uByteArray != null) {
                        bArr = uByteArray.m1252unboximpl();
                    }
                    sb.append(UArraysKt.m1394contentToString2csIQuQ(bArr));
                } else if (t instanceof UShortArray) {
                    UShortArray uShortArray = (UShortArray) t;
                    if (uShortArray != null) {
                        sArr = uShortArray.m1327unboximpl();
                    }
                    sb.append(UArraysKt.m1398contentToStringd6D3K8(sArr));
                } else if (t instanceof UIntArray) {
                    UIntArray uIntArray = (UIntArray) t;
                    if (uIntArray != null) {
                        iArr = uIntArray.m1277unboximpl();
                    }
                    sb.append(UArraysKt.m1397contentToStringXUkPCBk(iArr));
                } else if (t instanceof ULongArray) {
                    ULongArray uLongArray = (ULongArray) t;
                    if (uLongArray != null) {
                        jArr = uLongArray.m1302unboximpl();
                    }
                    sb.append(UArraysKt.m1400contentToStringuLth9ew(jArr));
                } else {
                    sb.append(t.toString());
                }
            }
        }
        sb.append(']');
        list.remove(CollectionsKt.getLastIndex(list));
    }
}
