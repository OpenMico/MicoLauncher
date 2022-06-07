package com.google.common.net;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
@Beta
@GwtCompatible
/* loaded from: classes2.dex */
public final class InternetDomainName {
    private static final CharMatcher a = CharMatcher.anyOf(".。．｡");
    private static final Splitter b = Splitter.on('.');
    private static final Joiner c = Joiner.on('.');
    private static final CharMatcher h = CharMatcher.anyOf("-_");
    private static final CharMatcher i = CharMatcher.javaLetterOrDigit().or(h);
    private final String d;
    private final ImmutableList<String> e;
    private final int f;
    private final int g;

    InternetDomainName(String str) {
        String lowerCase = Ascii.toLowerCase(a.replaceFrom((CharSequence) str, '.'));
        boolean z = true;
        lowerCase = lowerCase.endsWith(".") ? lowerCase.substring(0, lowerCase.length() - 1) : lowerCase;
        Preconditions.checkArgument(lowerCase.length() <= 253, "Domain name too long: '%s':", lowerCase);
        this.d = lowerCase;
        this.e = ImmutableList.copyOf(b.split(lowerCase));
        Preconditions.checkArgument(this.e.size() > 127 ? false : z, "Domain has too many parts: '%s'", lowerCase);
        Preconditions.checkArgument(a(this.e), "Not a valid domain name: '%s'", lowerCase);
        this.f = a(Optional.absent());
        this.g = a(Optional.of(PublicSuffixType.REGISTRY));
    }

    private int a(Optional<PublicSuffixType> optional) {
        int size = this.e.size();
        for (int i2 = 0; i2 < size; i2++) {
            String join = c.join(this.e.subList(i2, size));
            if (a(optional, Optional.fromNullable(PublicSuffixPatterns.EXACT.get(join)))) {
                return i2;
            }
            if (PublicSuffixPatterns.EXCLUDED.containsKey(join)) {
                return i2 + 1;
            }
            if (a(optional, join)) {
                return i2;
            }
        }
        return -1;
    }

    public static InternetDomainName from(String str) {
        return new InternetDomainName((String) Preconditions.checkNotNull(str));
    }

    private static boolean a(List<String> list) {
        int size = list.size() - 1;
        if (!a(list.get(size), true)) {
            return false;
        }
        for (int i2 = 0; i2 < size; i2++) {
            if (!a(list.get(i2), false)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(String str, boolean z) {
        if (str.length() < 1 || str.length() > 63) {
            return false;
        }
        if (i.matchesAllOf(CharMatcher.ascii().retainFrom(str)) && !h.matches(str.charAt(0)) && !h.matches(str.charAt(str.length() - 1))) {
            return !z || !CharMatcher.digit().matches(str.charAt(0));
        }
        return false;
    }

    public ImmutableList<String> parts() {
        return this.e;
    }

    public boolean isPublicSuffix() {
        return this.f == 0;
    }

    public boolean hasPublicSuffix() {
        return this.f != -1;
    }

    public InternetDomainName publicSuffix() {
        if (hasPublicSuffix()) {
            return a(this.f);
        }
        return null;
    }

    public boolean isUnderPublicSuffix() {
        return this.f > 0;
    }

    public boolean isTopPrivateDomain() {
        return this.f == 1;
    }

    public InternetDomainName topPrivateDomain() {
        if (isTopPrivateDomain()) {
            return this;
        }
        Preconditions.checkState(isUnderPublicSuffix(), "Not under a public suffix: %s", this.d);
        return a(this.f - 1);
    }

    public boolean isRegistrySuffix() {
        return this.g == 0;
    }

    public boolean hasRegistrySuffix() {
        return this.g != -1;
    }

    public InternetDomainName registrySuffix() {
        if (hasRegistrySuffix()) {
            return a(this.g);
        }
        return null;
    }

    public boolean isUnderRegistrySuffix() {
        return this.g > 0;
    }

    public boolean isTopDomainUnderRegistrySuffix() {
        return this.g == 1;
    }

    public InternetDomainName topDomainUnderRegistrySuffix() {
        if (isTopDomainUnderRegistrySuffix()) {
            return this;
        }
        Preconditions.checkState(isUnderRegistrySuffix(), "Not under a registry suffix: %s", this.d);
        return a(this.g - 1);
    }

    public boolean hasParent() {
        return this.e.size() > 1;
    }

    public InternetDomainName parent() {
        Preconditions.checkState(hasParent(), "Domain '%s' has no parent", this.d);
        return a(1);
    }

    private InternetDomainName a(int i2) {
        Joiner joiner = c;
        ImmutableList<String> immutableList = this.e;
        return from(joiner.join(immutableList.subList(i2, immutableList.size())));
    }

    public InternetDomainName child(String str) {
        return from(((String) Preconditions.checkNotNull(str)) + "." + this.d);
    }

    public static boolean isValid(String str) {
        try {
            from(str);
            return true;
        } catch (IllegalArgumentException unused) {
            return false;
        }
    }

    private static boolean a(Optional<PublicSuffixType> optional, String str) {
        List<String> splitToList = b.limit(2).splitToList(str);
        return splitToList.size() == 2 && a(optional, Optional.fromNullable(PublicSuffixPatterns.UNDER.get(splitToList.get(1))));
    }

    private static boolean a(Optional<PublicSuffixType> optional, Optional<PublicSuffixType> optional2) {
        return optional.isPresent() ? optional.equals(optional2) : optional2.isPresent();
    }

    public String toString() {
        return this.d;
    }

    public boolean equals(@NullableDecl Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof InternetDomainName) {
            return this.d.equals(((InternetDomainName) obj).d);
        }
        return false;
    }

    public int hashCode() {
        return this.d.hashCode();
    }
}
