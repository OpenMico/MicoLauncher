package kotlin.text;

import androidx.exifinterface.media.ExifInterface;
import com.xiaomi.idm.service.iot.InputMethodService;
import com.xiaomi.infra.galaxy.fds.Common;
import com.xiaomi.onetrack.api.b;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* compiled from: Regex.kt */
@Metadata(d1 = {"\u0000>\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\r\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0000\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0014\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\b\u0012\u0004\u0012\u0002H\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0082\b\u001a\u001e\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\u0016\u0010\r\u001a\u0004\u0018\u00010\b*\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0002\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H\u0002\u001a\u0014\u0010\u000e\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006H\u0002\u001a\u0012\u0010\u0012\u001a\u00020\u0006*\b\u0012\u0004\u0012\u00020\u00030\u0013H\u0002¨\u0006\u0014"}, d2 = {"fromInt", "", ExifInterface.GPS_DIRECTION_TRUE, "Lkotlin/text/FlagEnum;", "", b.p, "", "findNext", "Lkotlin/text/MatchResult;", "Ljava/util/regex/Matcher;", "from", InputMethodService.InputPropertyCommand.INPUT_SERVICE_DESC, "", "matchEntire", Common.RANGE, "Lkotlin/ranges/IntRange;", "Ljava/util/regex/MatchResult;", "groupIndex", "toInt", "", "kotlin-stdlib"}, k = 2, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public final class RegexKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final /* synthetic */ <T extends Enum<T> & d> Set<T> a(int i) {
        Intrinsics.reifiedOperationMarker(4, ExifInterface.GPS_DIRECTION_TRUE);
        EnumSet allOf = EnumSet.allOf(Enum.class);
        CollectionsKt.retainAll(allOf, new RegexKt$fromInt$$inlined$apply$lambda$1(i));
        Set<T> unmodifiableSet = Collections.unmodifiableSet(allOf);
        Intrinsics.checkNotNullExpressionValue(unmodifiableSet, "Collections.unmodifiable…mask == it.value }\n    })");
        return unmodifiableSet;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MatchResult a(Matcher matcher, int i, CharSequence charSequence) {
        if (!matcher.find(i)) {
            return null;
        }
        return new e(matcher, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final MatchResult a(Matcher matcher, CharSequence charSequence) {
        if (!matcher.matches()) {
            return null;
        }
        return new e(matcher, charSequence);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IntRange a(MatchResult matchResult) {
        return RangesKt.until(matchResult.start(), matchResult.end());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final IntRange a(MatchResult matchResult, int i) {
        return RangesKt.until(matchResult.start(i), matchResult.end(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int a(Iterable<? extends d> iterable) {
        int i = 0;
        for (d dVar : iterable) {
            i |= dVar.getValue();
        }
        return i;
    }
}
