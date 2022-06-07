package io.realm.internal;

import io.realm.CompactOnLaunchCallback;
import io.realm.RealmConfiguration;
import io.realm.internal.OsSharedRealm;
import io.realm.log.RealmLog;
import java.io.File;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* loaded from: classes5.dex */
public class OsRealmConfig implements NativeObject {
    public static final byte CLIENT_RESYNC_MODE_DISCARD = 1;
    public static final byte CLIENT_RESYNC_MODE_MANUAL = 2;
    public static final byte CLIENT_RESYNC_MODE_RECOVER = 0;
    private static final long a = nativeGetFinalizerPtr();
    private final RealmConfiguration b;
    private final URI c;
    private final long d;
    private final NativeContext e;
    private final CompactOnLaunchCallback f;
    private final OsSharedRealm.MigrationCallback g;
    private final OsSharedRealm.InitializationCallback h;

    private static native long nativeCreate(String str, String str2, boolean z, long j);

    private static native String nativeCreateAndSetSyncConfig(long j, String str, String str2, String str3, String str4, boolean z, byte b, String str5, String str6, String[] strArr, byte b2);

    private static native void nativeEnableChangeNotification(long j, boolean z);

    private static native long nativeGetFinalizerPtr();

    private static native void nativeSetCompactOnLaunchCallback(long j, CompactOnLaunchCallback compactOnLaunchCallback);

    private static native void nativeSetEncryptionKey(long j, byte[] bArr);

    private static native void nativeSetInMemory(long j, boolean z);

    private native void nativeSetInitializationCallback(long j, OsSharedRealm.InitializationCallback initializationCallback);

    private native void nativeSetSchemaConfig(long j, byte b, long j2, long j3, @Nullable OsSharedRealm.MigrationCallback migrationCallback);

    private static native void nativeSetSyncConfigProxySettings(long j, byte b, String str, int i);

    private static native void nativeSetSyncConfigSslSettings(long j, boolean z, String str);

    /* synthetic */ OsRealmConfig(RealmConfiguration realmConfiguration, String str, boolean z, OsSchemaInfo osSchemaInfo, OsSharedRealm.MigrationCallback migrationCallback, OsSharedRealm.InitializationCallback initializationCallback, AnonymousClass1 r7) {
        this(realmConfiguration, str, z, osSchemaInfo, migrationCallback, initializationCallback);
    }

    /* loaded from: classes5.dex */
    public enum Durability {
        FULL(0),
        MEM_ONLY(1);
        
        final int value;

        Durability(int i) {
            this.value = i;
        }
    }

    /* loaded from: classes5.dex */
    public enum SchemaMode {
        SCHEMA_MODE_AUTOMATIC((byte) 0),
        SCHEMA_MODE_IMMUTABLE((byte) 1),
        SCHEMA_MODE_READONLY((byte) 2),
        SCHEMA_MODE_RESET_FILE((byte) 3),
        SCHEMA_MODE_ADDITIVE((byte) 4),
        SCHEMA_MODE_MANUAL((byte) 5);
        
        final byte value;

        SchemaMode(byte b) {
            this.value = b;
        }

        public byte getNativeValue() {
            return this.value;
        }
    }

    /* loaded from: classes5.dex */
    public enum SyncSessionStopPolicy {
        IMMEDIATELY((byte) 0),
        LIVE_INDEFINITELY((byte) 1),
        AFTER_CHANGES_UPLOADED((byte) 2);
        
        final byte value;

        SyncSessionStopPolicy(byte b) {
            this.value = b;
        }

        public byte getNativeValue() {
            return this.value;
        }
    }

    /* loaded from: classes5.dex */
    public static class Builder {
        private RealmConfiguration a;
        private OsSchemaInfo b = null;
        private OsSharedRealm.MigrationCallback c = null;
        private OsSharedRealm.InitializationCallback d = null;
        private boolean e = false;
        private String f = "";

        public Builder(RealmConfiguration realmConfiguration) {
            this.a = realmConfiguration;
        }

        public Builder schemaInfo(@Nullable OsSchemaInfo osSchemaInfo) {
            this.b = osSchemaInfo;
            return this;
        }

        public Builder migrationCallback(@Nullable OsSharedRealm.MigrationCallback migrationCallback) {
            this.c = migrationCallback;
            return this;
        }

        public Builder initializationCallback(@Nullable OsSharedRealm.InitializationCallback initializationCallback) {
            this.d = initializationCallback;
            return this;
        }

        public Builder autoUpdateNotification(boolean z) {
            this.e = z;
            return this;
        }

        public OsRealmConfig build() {
            return new OsRealmConfig(this.a, this.f, this.e, this.b, this.c, this.d, null);
        }

        public Builder fifoFallbackDir(File file) {
            this.f = file.getAbsolutePath();
            return this;
        }
    }

    private OsRealmConfig(RealmConfiguration realmConfiguration, String str, boolean z, @Nullable OsSchemaInfo osSchemaInfo, @Nullable OsSharedRealm.MigrationCallback migrationCallback, @Nullable OsSharedRealm.InitializationCallback initializationCallback) {
        URI uri;
        URI uri2;
        this.e = new NativeContext();
        this.b = realmConfiguration;
        this.d = nativeCreate(realmConfiguration.getPath(), str, true, realmConfiguration.getMaxNumberOfActiveVersions());
        NativeContext.dummyContext.addReference(this);
        Object[] syncConfigurationOptions = ObjectServerFacade.getSyncFacadeIfPossible().getSyncConfigurationOptions(this.b);
        String str2 = (String) syncConfigurationOptions[0];
        String str3 = (String) syncConfigurationOptions[1];
        String str4 = (String) syncConfigurationOptions[2];
        String str5 = (String) syncConfigurationOptions[3];
        boolean equals = Boolean.TRUE.equals(syncConfigurationOptions[4]);
        String str6 = (String) syncConfigurationOptions[5];
        Byte b = (Byte) syncConfigurationOptions[6];
        boolean equals2 = Boolean.TRUE.equals(syncConfigurationOptions[7]);
        String str7 = (String) syncConfigurationOptions[8];
        String str8 = (String) syncConfigurationOptions[9];
        Byte b2 = (Byte) syncConfigurationOptions[11];
        Map map = (Map) syncConfigurationOptions[10];
        String[] strArr = new String[map != null ? map.size() * 2 : 0];
        if (map != null) {
            int i = 0;
            for (Map.Entry entry : map.entrySet()) {
                strArr[i] = (String) entry.getKey();
                strArr[i + 1] = (String) entry.getValue();
                i += 2;
            }
        }
        byte[] encryptionKey = realmConfiguration.getEncryptionKey();
        if (encryptionKey != null) {
            nativeSetEncryptionKey(this.d, encryptionKey);
        }
        nativeSetInMemory(this.d, realmConfiguration.getDurability() == Durability.MEM_ONLY);
        nativeEnableChangeNotification(this.d, z);
        SchemaMode schemaMode = SchemaMode.SCHEMA_MODE_MANUAL;
        if (realmConfiguration.isRecoveryConfiguration()) {
            schemaMode = SchemaMode.SCHEMA_MODE_IMMUTABLE;
        } else if (realmConfiguration.isReadOnly()) {
            schemaMode = SchemaMode.SCHEMA_MODE_READONLY;
        } else if (str3 != null) {
            schemaMode = SchemaMode.SCHEMA_MODE_ADDITIVE;
        } else if (realmConfiguration.shouldDeleteRealmIfMigrationNeeded()) {
            schemaMode = SchemaMode.SCHEMA_MODE_RESET_FILE;
        }
        long schemaVersion = realmConfiguration.getSchemaVersion();
        long nativePtr = osSchemaInfo == null ? 0L : osSchemaInfo.getNativePtr();
        this.g = migrationCallback;
        nativeSetSchemaConfig(this.d, schemaMode.getNativeValue(), schemaVersion, nativePtr, migrationCallback);
        this.f = realmConfiguration.getCompactOnLaunchCallback();
        CompactOnLaunchCallback compactOnLaunchCallback = this.f;
        if (compactOnLaunchCallback != null) {
            nativeSetCompactOnLaunchCallback(this.d, compactOnLaunchCallback);
        }
        this.h = initializationCallback;
        if (initializationCallback != null) {
            nativeSetInitializationCallback(this.d, initializationCallback);
        }
        URI uri3 = null;
        if (str3 != null) {
            String nativeCreateAndSetSyncConfig = nativeCreateAndSetSyncConfig(this.d, str3, str4, str2, str5, equals2, b.byteValue(), str7, str8, strArr, b2.byteValue());
            try {
                uri = new URI(nativeCreateAndSetSyncConfig);
            } catch (URISyntaxException e) {
                RealmLog.error(e, "Cannot create a URI from the Realm URL address", new Object[0]);
                uri = null;
            }
            nativeSetSyncConfigSslSettings(this.d, equals, str6);
            ProxySelector proxySelector = ProxySelector.getDefault();
            if (!(uri == null || proxySelector == null)) {
                try {
                    uri2 = new URI(nativeCreateAndSetSyncConfig.replaceFirst("realm", "http"));
                } catch (URISyntaxException e2) {
                    RealmLog.error(e2, "Cannot create a URI from the Realm URL address", new Object[0]);
                    uri2 = null;
                }
                List<Proxy> select = proxySelector.select(uri2);
                if (select != null && !select.isEmpty()) {
                    Proxy proxy = select.get(0);
                    if (proxy.type() != Proxy.Type.DIRECT) {
                        byte b3 = AnonymousClass1.a[proxy.type().ordinal()] == 1 ? (byte) 0 : (byte) -1;
                        if (proxy.type() == Proxy.Type.HTTP) {
                            SocketAddress address = proxy.address();
                            if (address instanceof InetSocketAddress) {
                                InetSocketAddress inetSocketAddress = (InetSocketAddress) address;
                                nativeSetSyncConfigProxySettings(this.d, b3, inetSocketAddress.getHostString(), inetSocketAddress.getPort());
                            } else {
                                RealmLog.error("Unsupported proxy socket address type: " + address.getClass().getName(), new Object[0]);
                            }
                        } else {
                            RealmLog.error("SOCKS proxies are not supported.", new Object[0]);
                        }
                    }
                }
            }
            uri3 = uri;
        }
        this.c = uri3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: io.realm.internal.OsRealmConfig$1  reason: invalid class name */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[Proxy.Type.values().length];

        static {
            try {
                a[Proxy.Type.HTTP.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    @Override // io.realm.internal.NativeObject
    public long getNativePtr() {
        return this.d;
    }

    @Override // io.realm.internal.NativeObject
    public long getNativeFinalizerPtr() {
        return a;
    }

    public RealmConfiguration getRealmConfiguration() {
        return this.b;
    }

    public URI getResolvedRealmURI() {
        return this.c;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NativeContext a() {
        return this.e;
    }
}
