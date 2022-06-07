package io.netty.handler.ssl;

import java.security.AlgorithmConstraints;
import javax.net.ssl.SSLParameters;

/* compiled from: SslParametersUtils.java */
/* loaded from: classes4.dex */
final class m {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static void a(SSLParameters sSLParameters, Object obj) {
        sSLParameters.setAlgorithmConstraints((AlgorithmConstraints) obj);
    }
}
