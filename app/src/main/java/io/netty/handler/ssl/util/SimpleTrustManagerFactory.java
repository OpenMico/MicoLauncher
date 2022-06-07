package io.netty.handler.ssl.util;

import io.netty.util.concurrent.FastThreadLocal;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Provider;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;

/* loaded from: classes4.dex */
public abstract class SimpleTrustManagerFactory extends TrustManagerFactory {
    private static final Provider a = new Provider("", 0.0d, "") { // from class: io.netty.handler.ssl.util.SimpleTrustManagerFactory.1
        private static final long serialVersionUID = -2680540247105807895L;
    };
    private static final FastThreadLocal<a> b = new FastThreadLocal<a>() { // from class: io.netty.handler.ssl.util.SimpleTrustManagerFactory.2
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public a initialValue() {
            return new a();
        }
    };

    protected abstract TrustManager[] engineGetTrustManagers();

    protected abstract void engineInit(KeyStore keyStore) throws Exception;

    protected abstract void engineInit(ManagerFactoryParameters managerFactoryParameters) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public SimpleTrustManagerFactory() {
        this("");
    }

    protected SimpleTrustManagerFactory(String str) {
        super(b.get(), a, str);
        b.get().a(this);
        b.remove();
        if (str == null) {
            throw new NullPointerException("name");
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public static final class a extends TrustManagerFactorySpi {
        private SimpleTrustManagerFactory a;

        a() {
        }

        void a(SimpleTrustManagerFactory simpleTrustManagerFactory) {
            this.a = simpleTrustManagerFactory;
        }

        @Override // javax.net.ssl.TrustManagerFactorySpi
        protected void engineInit(KeyStore keyStore) throws KeyStoreException {
            try {
                this.a.engineInit(keyStore);
            } catch (KeyStoreException e) {
                throw e;
            } catch (Exception e2) {
                throw new KeyStoreException(e2);
            }
        }

        @Override // javax.net.ssl.TrustManagerFactorySpi
        protected void engineInit(ManagerFactoryParameters managerFactoryParameters) throws InvalidAlgorithmParameterException {
            try {
                this.a.engineInit(managerFactoryParameters);
            } catch (InvalidAlgorithmParameterException e) {
                throw e;
            } catch (Exception e2) {
                throw new InvalidAlgorithmParameterException(e2);
            }
        }

        @Override // javax.net.ssl.TrustManagerFactorySpi
        protected TrustManager[] engineGetTrustManagers() {
            return this.a.engineGetTrustManagers();
        }
    }
}
