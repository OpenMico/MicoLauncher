package okhttp3;

import com.xiaomi.mipush.sdk.Constants;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.tls.CertificateChainCleaner;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: CertificatePinner.kt */
@Metadata(bv = {1, 0, 3}, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u0000 !2\u00020\u0001:\u0003 !\"B\u001f\b\u0000\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J)\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00110\u00100\u000fH\u0000¢\u0006\u0002\b\u0012J)\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00150\u0014\"\u00020\u0015H\u0007¢\u0006\u0002\u0010\u0016J\u001c\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0010J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\u001b\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u00102\u0006\u0010\f\u001a\u00020\rH\u0000¢\u0006\u0002\b\u001bJ\b\u0010\u001c\u001a\u00020\u001dH\u0016J\u0017\u0010\u001e\u001a\u00020\u00002\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0000¢\u0006\u0002\b\u001fR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006#"}, d2 = {"Lokhttp3/CertificatePinner;", "", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "certificateChainCleaner", "Lokhttp3/internal/tls/CertificateChainCleaner;", "(Ljava/util/Set;Lokhttp3/internal/tls/CertificateChainCleaner;)V", "getCertificateChainCleaner$okhttp", "()Lokhttp3/internal/tls/CertificateChainCleaner;", "check", "", "hostname", "", "cleanedPeerCertificatesFn", "Lkotlin/Function0;", "", "Ljava/security/cert/X509Certificate;", "check$okhttp", "peerCertificates", "", "Ljava/security/cert/Certificate;", "(Ljava/lang/String;[Ljava/security/cert/Certificate;)V", "equals", "", "other", "findMatchingPins", "findMatchingPins$okhttp", "hashCode", "", "withCertificateChainCleaner", "withCertificateChainCleaner$okhttp", "Builder", "Companion", "Pin", "okhttp"}, k = 1, mv = {1, 1, 16})
/* loaded from: classes5.dex */
public final class CertificatePinner {
    public static final Companion Companion = new Companion(null);
    @JvmField
    @NotNull
    public static final CertificatePinner DEFAULT = new Builder().build();
    @Nullable
    private final CertificateChainCleaner certificateChainCleaner;
    private final Set<Pin> pins;

    @JvmStatic
    @NotNull
    public static final String pin(@NotNull Certificate certificate) {
        return Companion.pin(certificate);
    }

    public CertificatePinner(@NotNull Set<Pin> pins, @Nullable CertificateChainCleaner certificateChainCleaner) {
        Intrinsics.checkParameterIsNotNull(pins, "pins");
        this.pins = pins;
        this.certificateChainCleaner = certificateChainCleaner;
    }

    @Nullable
    public final CertificateChainCleaner getCertificateChainCleaner$okhttp() {
        return this.certificateChainCleaner;
    }

    public final void check(@NotNull String hostname, @NotNull List<? extends Certificate> peerCertificates) throws SSLPeerUnverifiedException {
        Intrinsics.checkParameterIsNotNull(hostname, "hostname");
        Intrinsics.checkParameterIsNotNull(peerCertificates, "peerCertificates");
        check$okhttp(hostname, new CertificatePinner$check$1(this, peerCertificates, hostname));
    }

    public final void check$okhttp(@NotNull String hostname, @NotNull Function0<? extends List<? extends X509Certificate>> cleanedPeerCertificatesFn) {
        Intrinsics.checkParameterIsNotNull(hostname, "hostname");
        Intrinsics.checkParameterIsNotNull(cleanedPeerCertificatesFn, "cleanedPeerCertificatesFn");
        List<Pin> findMatchingPins$okhttp = findMatchingPins$okhttp(hostname);
        if (!findMatchingPins$okhttp.isEmpty()) {
            List<X509Certificate> list = (List) cleanedPeerCertificatesFn.invoke();
            for (X509Certificate x509Certificate : list) {
                ByteString byteString = null;
                ByteString byteString2 = byteString;
                for (Pin pin : findMatchingPins$okhttp) {
                    String hashAlgorithm = pin.getHashAlgorithm();
                    int hashCode = hashAlgorithm.hashCode();
                    if (hashCode != 109397962) {
                        if (hashCode == 2052263656 && hashAlgorithm.equals("sha256/")) {
                            if (byteString2 == null) {
                                byteString2 = Companion.toSha256ByteString$okhttp(x509Certificate);
                            }
                            if (Intrinsics.areEqual(pin.getHash(), byteString2)) {
                                return;
                            }
                        }
                        throw new AssertionError("unsupported hashAlgorithm: " + pin.getHashAlgorithm());
                    } else if (hashAlgorithm.equals("sha1/")) {
                        if (byteString == null) {
                            byteString = Companion.toSha1ByteString$okhttp(x509Certificate);
                        }
                        if (Intrinsics.areEqual(pin.getHash(), byteString)) {
                            return;
                        }
                    } else {
                        throw new AssertionError("unsupported hashAlgorithm: " + pin.getHashAlgorithm());
                    }
                }
            }
            StringBuilder sb = new StringBuilder();
            sb.append("Certificate pinning failure!");
            sb.append("\n  Peer certificate chain:");
            for (X509Certificate x509Certificate2 : list) {
                sb.append("\n    ");
                sb.append(Companion.pin(x509Certificate2));
                sb.append(": ");
                Principal subjectDN = x509Certificate2.getSubjectDN();
                Intrinsics.checkExpressionValueIsNotNull(subjectDN, "element.subjectDN");
                sb.append(subjectDN.getName());
            }
            sb.append("\n  Pinned certificates for ");
            sb.append(hostname);
            sb.append(Constants.COLON_SEPARATOR);
            for (Pin pin2 : findMatchingPins$okhttp) {
                sb.append("\n    ");
                sb.append(pin2);
            }
            String sb2 = sb.toString();
            Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder().apply(builderAction).toString()");
            throw new SSLPeerUnverifiedException(sb2);
        }
    }

    @Deprecated(message = "replaced with {@link #check(String, List)}.", replaceWith = @ReplaceWith(expression = "check(hostname, peerCertificates.toList())", imports = {}))
    public final void check(@NotNull String hostname, @NotNull Certificate... peerCertificates) throws SSLPeerUnverifiedException {
        Intrinsics.checkParameterIsNotNull(hostname, "hostname");
        Intrinsics.checkParameterIsNotNull(peerCertificates, "peerCertificates");
        check(hostname, ArraysKt.toList(peerCertificates));
    }

    @NotNull
    public final List<Pin> findMatchingPins$okhttp(@NotNull String hostname) {
        Intrinsics.checkParameterIsNotNull(hostname, "hostname");
        ArrayList emptyList = CollectionsKt.emptyList();
        for (Pin pin : this.pins) {
            if (pin.matches(hostname)) {
                if (emptyList.isEmpty()) {
                    emptyList = new ArrayList();
                }
                if (emptyList != null) {
                    TypeIntrinsics.asMutableList(emptyList).add(pin);
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableList<okhttp3.CertificatePinner.Pin>");
                }
            }
        }
        return emptyList;
    }

    @NotNull
    public final CertificatePinner withCertificateChainCleaner$okhttp(@Nullable CertificateChainCleaner certificateChainCleaner) {
        return Intrinsics.areEqual(this.certificateChainCleaner, certificateChainCleaner) ? this : new CertificatePinner(this.pins, certificateChainCleaner);
    }

    public boolean equals(@Nullable Object obj) {
        if (obj instanceof CertificatePinner) {
            CertificatePinner certificatePinner = (CertificatePinner) obj;
            if (Intrinsics.areEqual(certificatePinner.pins, this.pins) && Intrinsics.areEqual(certificatePinner.certificateChainCleaner, this.certificateChainCleaner)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int hashCode = (1517 + this.pins.hashCode()) * 41;
        CertificateChainCleaner certificateChainCleaner = this.certificateChainCleaner;
        return hashCode + (certificateChainCleaner != null ? certificateChainCleaner.hashCode() : 0);
    }

    /* compiled from: CertificatePinner.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\b\u0080\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÂ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0006HÆ\u0003J'\u0010\u000f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001J\u000e\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0003J\b\u0010\u0017\u001a\u00020\u0003H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lokhttp3/CertificatePinner$Pin;", "", "pattern", "", "hashAlgorithm", "hash", "Lokio/ByteString;", "(Ljava/lang/String;Ljava/lang/String;Lokio/ByteString;)V", "getHash", "()Lokio/ByteString;", "getHashAlgorithm", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "matches", "hostname", "toString", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Pin {
        @NotNull
        private final ByteString hash;
        @NotNull
        private final String hashAlgorithm;
        private final String pattern;

        private final String component1() {
            return this.pattern;
        }

        public static /* synthetic */ Pin copy$default(Pin pin, String str, String str2, ByteString byteString, int i, Object obj) {
            if ((i & 1) != 0) {
                str = pin.pattern;
            }
            if ((i & 2) != 0) {
                str2 = pin.hashAlgorithm;
            }
            if ((i & 4) != 0) {
                byteString = pin.hash;
            }
            return pin.copy(str, str2, byteString);
        }

        @NotNull
        public final String component2() {
            return this.hashAlgorithm;
        }

        @NotNull
        public final ByteString component3() {
            return this.hash;
        }

        @NotNull
        public final Pin copy(@NotNull String pattern, @NotNull String hashAlgorithm, @NotNull ByteString hash) {
            Intrinsics.checkParameterIsNotNull(pattern, "pattern");
            Intrinsics.checkParameterIsNotNull(hashAlgorithm, "hashAlgorithm");
            Intrinsics.checkParameterIsNotNull(hash, "hash");
            return new Pin(pattern, hashAlgorithm, hash);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Pin)) {
                return false;
            }
            Pin pin = (Pin) obj;
            return Intrinsics.areEqual(this.pattern, pin.pattern) && Intrinsics.areEqual(this.hashAlgorithm, pin.hashAlgorithm) && Intrinsics.areEqual(this.hash, pin.hash);
        }

        public int hashCode() {
            String str = this.pattern;
            int i = 0;
            int hashCode = (str != null ? str.hashCode() : 0) * 31;
            String str2 = this.hashAlgorithm;
            int hashCode2 = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
            ByteString byteString = this.hash;
            if (byteString != null) {
                i = byteString.hashCode();
            }
            return hashCode2 + i;
        }

        public Pin(@NotNull String pattern, @NotNull String hashAlgorithm, @NotNull ByteString hash) {
            Intrinsics.checkParameterIsNotNull(pattern, "pattern");
            Intrinsics.checkParameterIsNotNull(hashAlgorithm, "hashAlgorithm");
            Intrinsics.checkParameterIsNotNull(hash, "hash");
            this.pattern = pattern;
            this.hashAlgorithm = hashAlgorithm;
            this.hash = hash;
        }

        @NotNull
        public final String getHashAlgorithm() {
            return this.hashAlgorithm;
        }

        @NotNull
        public final ByteString getHash() {
            return this.hash;
        }

        public final boolean matches(@NotNull String hostname) {
            Intrinsics.checkParameterIsNotNull(hostname, "hostname");
            if (StringsKt.startsWith$default(this.pattern, "**.", false, 2, (Object) null)) {
                int length = this.pattern.length() - 3;
                int length2 = hostname.length() - length;
                if (StringsKt.regionMatches$default(hostname, hostname.length() - length, this.pattern, 3, length, false, 16, (Object) null)) {
                    return length2 == 0 || hostname.charAt(length2 - 1) == '.';
                }
                return false;
            } else if (!StringsKt.startsWith$default(this.pattern, "*.", false, 2, (Object) null)) {
                return Intrinsics.areEqual(hostname, this.pattern);
            } else {
                int length3 = this.pattern.length() - 1;
                return StringsKt.regionMatches$default(hostname, hostname.length() - length3, this.pattern, 1, length3, false, 16, (Object) null) && StringsKt.lastIndexOf$default((CharSequence) hostname, '.', (hostname.length() - length3) + (-1), false, 4, (Object) null) == -1;
            }
        }

        @NotNull
        public String toString() {
            return this.hashAlgorithm + this.hash.base64();
        }
    }

    /* compiled from: CertificatePinner.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J'\u0010\u0006\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\b2\u0012\u0010\u0003\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\t\"\u00020\b¢\u0006\u0002\u0010\nJ\u0006\u0010\u000b\u001a\u00020\fR\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, d2 = {"Lokhttp3/CertificatePinner$Builder;", "", "()V", "pins", "", "Lokhttp3/CertificatePinner$Pin;", "add", "pattern", "", "", "(Ljava/lang/String;[Ljava/lang/String;)Lokhttp3/CertificatePinner$Builder;", "build", "Lokhttp3/CertificatePinner;", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Builder {
        private final List<Pin> pins = new ArrayList();

        @NotNull
        public final Builder add(@NotNull String pattern, @NotNull String... pins) {
            Intrinsics.checkParameterIsNotNull(pattern, "pattern");
            Intrinsics.checkParameterIsNotNull(pins, "pins");
            Builder builder = this;
            for (String str : pins) {
                builder.pins.add(CertificatePinner.Companion.newPin$okhttp(pattern, str));
            }
            return builder;
        }

        @NotNull
        public final CertificatePinner build() {
            return new CertificatePinner(CollectionsKt.toSet(this.pins), null);
        }
    }

    /* compiled from: CertificatePinner.kt */
    @Metadata(bv = {1, 0, 3}, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001d\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0000¢\u0006\u0002\b\nJ\u0010\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0011\u0010\r\u001a\u00020\u000e*\u00020\u000fH\u0000¢\u0006\u0002\b\u0010J\u0011\u0010\u0011\u001a\u00020\u000e*\u00020\u000fH\u0000¢\u0006\u0002\b\u0012R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lokhttp3/CertificatePinner$Companion;", "", "()V", "DEFAULT", "Lokhttp3/CertificatePinner;", "newPin", "Lokhttp3/CertificatePinner$Pin;", "pattern", "", "pin", "newPin$okhttp", "certificate", "Ljava/security/cert/Certificate;", "toSha1ByteString", "Lokio/ByteString;", "Ljava/security/cert/X509Certificate;", "toSha1ByteString$okhttp", "toSha256ByteString", "toSha256ByteString$okhttp", "okhttp"}, k = 1, mv = {1, 1, 16})
    /* loaded from: classes5.dex */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final String pin(@NotNull Certificate certificate) {
            Intrinsics.checkParameterIsNotNull(certificate, "certificate");
            if (certificate instanceof X509Certificate) {
                return "sha256/" + toSha256ByteString$okhttp((X509Certificate) certificate).base64();
            }
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates".toString());
        }

        @NotNull
        public final ByteString toSha1ByteString$okhttp(@NotNull X509Certificate toSha1ByteString) {
            Intrinsics.checkParameterIsNotNull(toSha1ByteString, "$this$toSha1ByteString");
            ByteString.Companion companion = ByteString.Companion;
            PublicKey publicKey = toSha1ByteString.getPublicKey();
            Intrinsics.checkExpressionValueIsNotNull(publicKey, "publicKey");
            byte[] encoded = publicKey.getEncoded();
            Intrinsics.checkExpressionValueIsNotNull(encoded, "publicKey.encoded");
            return ByteString.Companion.of$default(companion, encoded, 0, 0, 3, null).sha1();
        }

        @NotNull
        public final ByteString toSha256ByteString$okhttp(@NotNull X509Certificate toSha256ByteString) {
            Intrinsics.checkParameterIsNotNull(toSha256ByteString, "$this$toSha256ByteString");
            ByteString.Companion companion = ByteString.Companion;
            PublicKey publicKey = toSha256ByteString.getPublicKey();
            Intrinsics.checkExpressionValueIsNotNull(publicKey, "publicKey");
            byte[] encoded = publicKey.getEncoded();
            Intrinsics.checkExpressionValueIsNotNull(encoded, "publicKey.encoded");
            return ByteString.Companion.of$default(companion, encoded, 0, 0, 3, null).sha256();
        }

        @NotNull
        public final Pin newPin$okhttp(@NotNull String pattern, @NotNull String pin) {
            Intrinsics.checkParameterIsNotNull(pattern, "pattern");
            Intrinsics.checkParameterIsNotNull(pin, "pin");
            if ((StringsKt.startsWith$default(pattern, "*.", false, 2, (Object) null) && StringsKt.indexOf$default((CharSequence) pattern, "*", 1, false, 4, (Object) null) == -1) || (StringsKt.startsWith$default(pattern, "**.", false, 2, (Object) null) && StringsKt.indexOf$default((CharSequence) pattern, "*", 2, false, 4, (Object) null) == -1) || StringsKt.indexOf$default((CharSequence) pattern, "*", 0, false, 6, (Object) null) == -1) {
                String canonicalHost = HostnamesKt.toCanonicalHost(pattern);
                if (canonicalHost == null) {
                    throw new IllegalArgumentException("Invalid pattern: " + pattern);
                } else if (StringsKt.startsWith$default(pin, "sha1/", false, 2, (Object) null)) {
                    ByteString.Companion companion = ByteString.Companion;
                    String substring = pin.substring(5);
                    Intrinsics.checkExpressionValueIsNotNull(substring, "(this as java.lang.String).substring(startIndex)");
                    ByteString decodeBase64 = companion.decodeBase64(substring);
                    if (decodeBase64 == null) {
                        Intrinsics.throwNpe();
                    }
                    return new Pin(canonicalHost, "sha1/", decodeBase64);
                } else if (StringsKt.startsWith$default(pin, "sha256/", false, 2, (Object) null)) {
                    ByteString.Companion companion2 = ByteString.Companion;
                    String substring2 = pin.substring(7);
                    Intrinsics.checkExpressionValueIsNotNull(substring2, "(this as java.lang.String).substring(startIndex)");
                    ByteString decodeBase642 = companion2.decodeBase64(substring2);
                    if (decodeBase642 == null) {
                        Intrinsics.throwNpe();
                    }
                    return new Pin(canonicalHost, "sha256/", decodeBase642);
                } else {
                    throw new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + pin);
                }
            } else {
                throw new IllegalArgumentException(("Unexpected pattern: " + pattern).toString());
            }
        }
    }
}
