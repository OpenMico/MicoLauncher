package kotlin.internal.jdk8;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import kotlin.Metadata;
import kotlin.internal.jdk7.JDK7PlatformImplementations;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.jdk8.PlatformThreadLocalRandom;
import kotlin.ranges.IntRange;
import kotlin.text.MatchGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: JDK8PlatformImplementations.kt */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0010\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u001a\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, d2 = {"Lkotlin/internal/jdk8/JDK8PlatformImplementations;", "Lkotlin/internal/jdk7/JDK7PlatformImplementations;", "()V", "defaultPlatformRandom", "Lkotlin/random/Random;", "getMatchResultNamedGroup", "Lkotlin/text/MatchGroup;", "matchResult", "Ljava/util/regex/MatchResult;", "name", "", "kotlin-stdlib-jdk8"}, k = 1, mv = {1, 5, 1})
/* loaded from: classes5.dex */
public class JDK8PlatformImplementations extends JDK7PlatformImplementations {
    @Override // kotlin.internal.PlatformImplementations
    @Nullable
    public MatchGroup getMatchResultNamedGroup(@NotNull MatchResult matchResult, @NotNull String name) {
        Intrinsics.checkNotNullParameter(matchResult, "matchResult");
        Intrinsics.checkNotNullParameter(name, "name");
        if (!(matchResult instanceof Matcher)) {
            matchResult = null;
        }
        Matcher matcher = (Matcher) matchResult;
        if (matcher != null) {
            IntRange intRange = new IntRange(matcher.start(name), matcher.end(name) - 1);
            if (intRange.getStart().intValue() < 0) {
                return null;
            }
            String group = matcher.group(name);
            Intrinsics.checkNotNullExpressionValue(group, "matcher.group(name)");
            return new MatchGroup(group, intRange);
        }
        throw new UnsupportedOperationException("Retrieving groups by name is not supported on this platform.");
    }

    @Override // kotlin.internal.PlatformImplementations
    @NotNull
    public Random defaultPlatformRandom() {
        return new PlatformThreadLocalRandom();
    }
}
