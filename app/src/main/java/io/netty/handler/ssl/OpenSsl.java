package io.netty.handler.ssl;

import com.xiaomi.micolauncher.common.player.policy.IAudioPolicy;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.codec.language.bm.Rule;
import org.apache.tomcat.jni.Buffer;
import org.apache.tomcat.jni.Library;
import org.apache.tomcat.jni.Pool;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLContext;

/* loaded from: classes4.dex */
public final class OpenSsl {
    static final /* synthetic */ boolean a = !OpenSsl.class.desiredAssertionStatus();
    private static final InternalLogger b = InternalLoggerFactory.getInstance(OpenSsl.class);
    private static final Throwable c;
    private static final Set<String> d;

    public static boolean a(long j) {
        return j != 0;
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v10, types: [long] */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v2 */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r3v7 */
    static {
        ClassNotFoundException classNotFoundException;
        try {
            Class.forName("org.apache.tomcat.jni.SSL", false, OpenSsl.class.getClassLoader());
            classNotFoundException = null;
        } catch (ClassNotFoundException e) {
            b.debug("netty-tcnative not in the classpath; " + OpenSslEngine.class.getSimpleName() + " will be unavailable.");
            classNotFoundException = e;
        }
        if (classNotFoundException == null) {
            try {
                a();
                classNotFoundException = classNotFoundException;
            } catch (Throwable th) {
                b.debug("Failed to load netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable, unless the application has already loaded the symbols by some other means. See http://netty.io/wiki/forked-tomcat-native.html for more information.", th);
                classNotFoundException = th;
            }
            try {
                b();
                classNotFoundException = 0;
            } catch (Throwable th2) {
                if (classNotFoundException == null) {
                    classNotFoundException = th2;
                }
                b.debug("Failed to initialize netty-tcnative; " + OpenSslEngine.class.getSimpleName() + " will be unavailable. See http://netty.io/wiki/forked-tomcat-native.html for more information.", th2);
            }
        }
        c = classNotFoundException;
        if (classNotFoundException == 0) {
            try {
                LinkedHashSet linkedHashSet = new LinkedHashSet(128);
                classNotFoundException = Pool.create(0L);
                try {
                    long make = SSLContext.make((long) classNotFoundException, 31, 1);
                    try {
                        SSLContext.setOptions(make, (int) IAudioPolicy.AUDIO_SOURCE_TYPE_ALL);
                        SSLContext.setCipherSuite(make, Rule.ALL);
                        long newSSL = SSL.newSSL(make, true);
                        String[] ciphers = SSL.getCiphers(newSSL);
                        for (String str : ciphers) {
                            if (!(str == null || str.length() == 0 || linkedHashSet.contains(str))) {
                                linkedHashSet.add(str);
                            }
                        }
                        SSL.freeSSL(newSSL);
                    } finally {
                        SSLContext.free(make);
                    }
                } catch (Exception e2) {
                    b.warn("Failed to get the list of available OpenSSL cipher suites.", (Throwable) e2);
                }
                Pool.destroy((long) classNotFoundException);
                d = Collections.unmodifiableSet(linkedHashSet);
            } catch (Throwable th3) {
                Pool.destroy(classNotFoundException == true ? 1L : 0L);
                throw th3;
            }
        } else {
            d = Collections.emptySet();
        }
    }

    public static boolean isAvailable() {
        return c == null;
    }

    public static boolean isAlpnSupported() {
        return ((long) version()) >= 268443648;
    }

    public static int version() {
        if (isAvailable()) {
            return SSL.version();
        }
        return -1;
    }

    public static String versionString() {
        if (isAvailable()) {
            return SSL.versionString();
        }
        return null;
    }

    public static void ensureAvailability() {
        if (c != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(c));
        }
    }

    public static Throwable unavailabilityCause() {
        return c;
    }

    public static Set<String> availableCipherSuites() {
        return d;
    }

    public static boolean isCipherSuiteAvailable(String str) {
        String a2 = c.a(str);
        if (a2 != null) {
            str = a2;
        }
        return d.contains(str);
    }

    public static long a(ByteBuf byteBuf) {
        if (a || byteBuf.isDirect()) {
            return byteBuf.hasMemoryAddress() ? byteBuf.memoryAddress() : Buffer.address(byteBuf.nioBuffer());
        }
        throw new AssertionError();
    }

    private OpenSsl() {
    }

    private static void a() throws Exception {
        String a2 = a(SystemPropertyUtil.get("os.name", ""));
        String b2 = b(SystemPropertyUtil.get("os.arch", ""));
        LinkedHashSet linkedHashSet = new LinkedHashSet(3);
        linkedHashSet.add("netty-tcnative-" + a2 + '-' + b2);
        if ("linux".equalsIgnoreCase(a2)) {
            linkedHashSet.add("netty-tcnative-" + a2 + '-' + b2 + "-fedora");
        }
        linkedHashSet.add("netty-tcnative");
        NativeLibraryLoader.loadFirstAvailable(SSL.class.getClassLoader(), (String[]) linkedHashSet.toArray(new String[linkedHashSet.size()]));
    }

    private static void b() throws Exception {
        Library.initialize("provided");
        SSL.initialize((String) null);
    }

    private static String a(String str) {
        String c2 = c(str);
        return c2.startsWith("aix") ? "aix" : c2.startsWith("hpux") ? "hpux" : (!c2.startsWith("os400") || (c2.length() > 5 && Character.isDigit(c2.charAt(5)))) ? c2.startsWith("linux") ? "linux" : (c2.startsWith("macosx") || c2.startsWith("osx")) ? "osx" : c2.startsWith("freebsd") ? "freebsd" : c2.startsWith("openbsd") ? "openbsd" : c2.startsWith("netbsd") ? "netbsd" : (c2.startsWith("solaris") || c2.startsWith("sunos")) ? "sunos" : c2.startsWith("windows") ? "windows" : "unknown" : "os400";
    }

    private static String b(String str) {
        String c2 = c(str);
        return c2.matches("^(x8664|amd64|ia32e|em64t|x64)$") ? "x86_64" : c2.matches("^(x8632|x86|i[3-6]86|ia32|x32)$") ? "x86_32" : c2.matches("^(ia64|itanium64)$") ? "itanium_64" : c2.matches("^(sparc|sparc32)$") ? "sparc_32" : c2.matches("^(sparcv9|sparc64)$") ? "sparc_64" : c2.matches("^(arm|arm32)$") ? "arm_32" : "aarch64".equals(c2) ? "aarch_64" : c2.matches("^(ppc|ppc32)$") ? "ppc_32" : "ppc64".equals(c2) ? "ppc_64" : "ppc64le".equals(c2) ? "ppcle_64" : "s390".equals(c2) ? "s390_32" : "s390x".equals(c2) ? "s390_64" : "unknown";
    }

    private static String c(String str) {
        return str.toLowerCase(Locale.US).replaceAll("[^a-z0-9]+", "");
    }
}
