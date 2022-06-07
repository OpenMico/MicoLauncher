package io.netty.handler.ssl;

import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLHandshakeException;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class JdkBaseApplicationProtocolNegotiator implements JdkApplicationProtocolNegotiator {
    static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory a = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory() { // from class: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator.1
        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory
        public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set) {
            return new FailProtocolSelector((g) sSLEngine, set);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory b = new JdkApplicationProtocolNegotiator.ProtocolSelectorFactory() { // from class: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator.2
        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectorFactory
        public JdkApplicationProtocolNegotiator.ProtocolSelector newSelector(SSLEngine sSLEngine, Set<String> set) {
            return new NoFailProtocolSelector((g) sSLEngine, set);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory c = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory() { // from class: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator.3
        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory
        public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list) {
            return new FailProtocolSelectionListener((g) sSLEngine, list);
        }
    };
    static final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory d = new JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory() { // from class: io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator.4
        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory
        public JdkApplicationProtocolNegotiator.ProtocolSelectionListener newListener(SSLEngine sSLEngine, List<String> list) {
            return new NoFailProtocolSelectionListener((g) sSLEngine, list);
        }
    };
    private final List<String> e;
    private final JdkApplicationProtocolNegotiator.ProtocolSelectorFactory f;
    private final JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory g;
    private final JdkApplicationProtocolNegotiator.SslEngineWrapperFactory h;

    public JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory sslEngineWrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, Iterable<String> iterable) {
        this(sslEngineWrapperFactory, protocolSelectorFactory, protocolSelectionListenerFactory, b.a(iterable));
    }

    public JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory sslEngineWrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, String... strArr) {
        this(sslEngineWrapperFactory, protocolSelectorFactory, protocolSelectionListenerFactory, b.a(strArr));
    }

    private JdkBaseApplicationProtocolNegotiator(JdkApplicationProtocolNegotiator.SslEngineWrapperFactory sslEngineWrapperFactory, JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory, JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolSelectionListenerFactory, List<String> list) {
        this.h = (JdkApplicationProtocolNegotiator.SslEngineWrapperFactory) ObjectUtil.checkNotNull(sslEngineWrapperFactory, "wrapperFactory");
        this.f = (JdkApplicationProtocolNegotiator.ProtocolSelectorFactory) ObjectUtil.checkNotNull(protocolSelectorFactory, "selectorFactory");
        this.g = (JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory) ObjectUtil.checkNotNull(protocolSelectionListenerFactory, "listenerFactory");
        this.e = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(list, "protocols"));
    }

    @Override // io.netty.handler.ssl.ApplicationProtocolNegotiator
    public List<String> protocols() {
        return this.e;
    }

    @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator
    public JdkApplicationProtocolNegotiator.ProtocolSelectorFactory protocolSelectorFactory() {
        return this.f;
    }

    @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator
    public JdkApplicationProtocolNegotiator.ProtocolSelectionListenerFactory protocolListenerFactory() {
        return this.g;
    }

    @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator
    public JdkApplicationProtocolNegotiator.SslEngineWrapperFactory wrapperFactory() {
        return this.h;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public static class NoFailProtocolSelector implements JdkApplicationProtocolNegotiator.ProtocolSelector {
        private final g a;
        private final Set<String> b;

        public NoFailProtocolSelector(g gVar, Set<String> set) {
            this.a = gVar;
            this.b = set;
        }

        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector
        public void unsupported() {
            this.a.getSession().a(null);
        }

        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelector
        public String select(List<String> list) throws Exception {
            for (String str : this.b) {
                if (list.contains(str)) {
                    this.a.getSession().a(str);
                    return str;
                }
            }
            return noSelectMatchFound();
        }

        public String noSelectMatchFound() throws Exception {
            this.a.getSession().a(null);
            return null;
        }
    }

    /* loaded from: classes4.dex */
    protected static final class FailProtocolSelector extends NoFailProtocolSelector {
        public FailProtocolSelector(g gVar, Set<String> set) {
            super(gVar, set);
        }

        @Override // io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator.NoFailProtocolSelector
        public String noSelectMatchFound() throws Exception {
            throw new SSLHandshakeException("Selected protocol is not supported");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* loaded from: classes4.dex */
    public static class NoFailProtocolSelectionListener implements JdkApplicationProtocolNegotiator.ProtocolSelectionListener {
        private final g a;
        private final List<String> b;

        public void noSelectedMatchFound(String str) throws Exception {
        }

        public NoFailProtocolSelectionListener(g gVar, List<String> list) {
            this.a = gVar;
            this.b = list;
        }

        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener
        public void unsupported() {
            this.a.getSession().a(null);
        }

        @Override // io.netty.handler.ssl.JdkApplicationProtocolNegotiator.ProtocolSelectionListener
        public void selected(String str) throws Exception {
            if (this.b.contains(str)) {
                this.a.getSession().a(str);
            } else {
                noSelectedMatchFound(str);
            }
        }
    }

    /* loaded from: classes4.dex */
    protected static final class FailProtocolSelectionListener extends NoFailProtocolSelectionListener {
        public FailProtocolSelectionListener(g gVar, List<String> list) {
            super(gVar, list);
        }

        @Override // io.netty.handler.ssl.JdkBaseApplicationProtocolNegotiator.NoFailProtocolSelectionListener
        public void noSelectedMatchFound(String str) throws Exception {
            throw new SSLHandshakeException("No compatible protocols found");
        }
    }
}
