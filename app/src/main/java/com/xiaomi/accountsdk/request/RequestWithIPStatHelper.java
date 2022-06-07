package com.xiaomi.accountsdk.request;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public abstract class RequestWithIPStatHelper extends IPStatHelper {
    public void finish() {
    }

    public void onBackupIpFailed(int i) {
    }

    public void onBackupIpStarted(int i, String str) {
    }

    public void onBackupIpSucceeded(int i) {
    }

    public void onCachedIpFailed() {
    }

    public void onCachedIpStarted(String str) {
    }

    public void onCachedIpSucceed() {
    }

    public void onDnsIp0Failed() {
    }

    public void onDnsIp0Succeed() {
    }

    public void onDnsResolvingFinished(String str) {
    }

    public void onDnsResolvingStarted() {
    }

    public void onDnsip0Started(String str) {
    }

    public void onHostNameRequestFinished(boolean z) {
    }

    public void onHostNameRequestStarted() {
    }

    public void onIpRequest200Succeed() {
    }

    public void onIpRequestFailed() {
    }

    public void onIpRequestIOSucceed() {
    }

    public void onIpRequestStarted() {
    }

    public void start() {
    }

    /* loaded from: classes2.dex */
    public static final class Composition extends RequestWithIPStatHelper {
        private final List<RequestWithIPStatHelper> entities = new ArrayList();

        public Composition(RequestWithIPStatHelper... requestWithIPStatHelperArr) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : requestWithIPStatHelperArr) {
                if (requestWithIPStatHelper != null) {
                    this.entities.add(requestWithIPStatHelper);
                }
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onIpRequestStarted() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onIpRequestStarted();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onIpRequest200Succeed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onIpRequest200Succeed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onIpRequestIOSucceed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onIpRequestIOSucceed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onIpRequestFailed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onIpRequestFailed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onCachedIpStarted(String str) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onCachedIpStarted(str);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onCachedIpSucceed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onCachedIpSucceed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onCachedIpFailed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onCachedIpFailed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onDnsip0Started(String str) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onDnsip0Started(str);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onDnsIp0Succeed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onDnsIp0Succeed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onDnsIp0Failed() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onDnsIp0Failed();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onBackupIpStarted(int i, String str) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onBackupIpStarted(i, str);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onBackupIpSucceeded(int i) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onBackupIpSucceeded(i);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onBackupIpFailed(int i) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onBackupIpFailed(i);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onHostNameRequestStarted() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onHostNameRequestStarted();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onHostNameRequestFinished(boolean z) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onHostNameRequestFinished(z);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onDnsResolvingStarted() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onDnsResolvingStarted();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void onDnsResolvingFinished(String str) {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.onDnsResolvingFinished(str);
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void start() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.start();
            }
        }

        @Override // com.xiaomi.accountsdk.request.RequestWithIPStatHelper
        public void finish() {
            for (RequestWithIPStatHelper requestWithIPStatHelper : this.entities) {
                requestWithIPStatHelper.finish();
            }
        }
    }
}
