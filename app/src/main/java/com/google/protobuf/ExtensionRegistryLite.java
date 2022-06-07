package com.google.protobuf;

import com.google.protobuf.GeneratedMessageLite;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class ExtensionRegistryLite {
    static final ExtensionRegistryLite a = new ExtensionRegistryLite(true);
    private static volatile boolean b = false;
    private static boolean c = true;
    private static volatile ExtensionRegistryLite d;
    private final Map<b, GeneratedMessageLite.GeneratedExtension<?, ?>> e;

    /* loaded from: classes2.dex */
    private static class a {
        static final Class<?> a = a();

        static Class<?> a() {
            try {
                return Class.forName("com.google.protobuf.Extension");
            } catch (ClassNotFoundException unused) {
                return null;
            }
        }
    }

    public static boolean isEagerlyParseMessageSets() {
        return b;
    }

    public static void setEagerlyParseMessageSets(boolean z) {
        b = z;
    }

    public static ExtensionRegistryLite newInstance() {
        return c ? i.b() : new ExtensionRegistryLite();
    }

    public static ExtensionRegistryLite getEmptyRegistry() {
        ExtensionRegistryLite extensionRegistryLite = d;
        if (extensionRegistryLite == null) {
            synchronized (ExtensionRegistryLite.class) {
                extensionRegistryLite = d;
                if (extensionRegistryLite == null) {
                    extensionRegistryLite = c ? i.c() : a;
                    d = extensionRegistryLite;
                }
            }
        }
        return extensionRegistryLite;
    }

    public ExtensionRegistryLite getUnmodifiable() {
        return new ExtensionRegistryLite(this);
    }

    public <ContainingType extends MessageLite> GeneratedMessageLite.GeneratedExtension<ContainingType, ?> findLiteExtensionByNumber(ContainingType containingtype, int i) {
        return (GeneratedMessageLite.GeneratedExtension<ContainingType, ?>) this.e.get(new b(containingtype, i));
    }

    public final void add(GeneratedMessageLite.GeneratedExtension<?, ?> generatedExtension) {
        this.e.put(new b(generatedExtension.getContainingTypeDefaultInstance(), generatedExtension.getNumber()), generatedExtension);
    }

    public final void add(ExtensionLite<?, ?> extensionLite) {
        if (GeneratedMessageLite.GeneratedExtension.class.isAssignableFrom(extensionLite.getClass())) {
            add((GeneratedMessageLite.GeneratedExtension) extensionLite);
        }
        if (c && i.a(this)) {
            try {
                getClass().getMethod("add", a.a).invoke(this, extensionLite);
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format("Could not invoke ExtensionRegistry#add for %s", extensionLite), e);
            }
        }
    }

    public ExtensionRegistryLite() {
        this.e = new HashMap();
    }

    ExtensionRegistryLite(ExtensionRegistryLite extensionRegistryLite) {
        if (extensionRegistryLite == a) {
            this.e = Collections.emptyMap();
        } else {
            this.e = Collections.unmodifiableMap(extensionRegistryLite.e);
        }
    }

    ExtensionRegistryLite(boolean z) {
        this.e = Collections.emptyMap();
    }

    /* loaded from: classes2.dex */
    public static final class b {
        private final Object a;
        private final int b;

        b(Object obj, int i) {
            this.a = obj;
            this.b = i;
        }

        public int hashCode() {
            return (System.identityHashCode(this.a) * 65535) + this.b;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof b)) {
                return false;
            }
            b bVar = (b) obj;
            return this.a == bVar.a && this.b == bVar.b;
        }
    }
}
