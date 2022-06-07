package org.conscrypt;

/* loaded from: classes5.dex */
abstract class PeerInfoProvider {
    private static final PeerInfoProvider NULL_PEER_INFO_PROVIDER = new PeerInfoProvider() { // from class: org.conscrypt.PeerInfoProvider.1
        @Override // org.conscrypt.PeerInfoProvider
        String getHostname() {
            return null;
        }

        @Override // org.conscrypt.PeerInfoProvider
        public String getHostnameOrIP() {
            return null;
        }

        @Override // org.conscrypt.PeerInfoProvider
        public int getPort() {
            return -1;
        }
    };

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getHostname();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract String getHostnameOrIP();

    /* JADX INFO: Access modifiers changed from: package-private */
    public abstract int getPort();

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PeerInfoProvider nullProvider() {
        return NULL_PEER_INFO_PROVIDER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static PeerInfoProvider forHostAndPort(final String str, final int i) {
        return new PeerInfoProvider() { // from class: org.conscrypt.PeerInfoProvider.2
            @Override // org.conscrypt.PeerInfoProvider
            String getHostname() {
                return str;
            }

            @Override // org.conscrypt.PeerInfoProvider
            public String getHostnameOrIP() {
                return str;
            }

            @Override // org.conscrypt.PeerInfoProvider
            public int getPort() {
                return i;
            }
        };
    }
}
