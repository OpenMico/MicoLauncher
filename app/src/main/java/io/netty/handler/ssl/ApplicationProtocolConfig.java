package io.netty.handler.ssl;

import io.netty.util.internal.ObjectUtil;
import java.util.Collections;
import java.util.List;

/* loaded from: classes4.dex */
public final class ApplicationProtocolConfig {
    public static final ApplicationProtocolConfig DISABLED = new ApplicationProtocolConfig();
    private final List<String> a;
    private final Protocol b;
    private final SelectorFailureBehavior c;
    private final SelectedListenerFailureBehavior d;

    /* loaded from: classes4.dex */
    public enum Protocol {
        NONE,
        NPN,
        ALPN,
        NPN_AND_ALPN
    }

    /* loaded from: classes4.dex */
    public enum SelectedListenerFailureBehavior {
        ACCEPT,
        FATAL_ALERT,
        CHOOSE_MY_LAST_PROTOCOL
    }

    /* loaded from: classes4.dex */
    public enum SelectorFailureBehavior {
        FATAL_ALERT,
        NO_ADVERTISE,
        CHOOSE_MY_LAST_PROTOCOL
    }

    public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorFailureBehavior, SelectedListenerFailureBehavior selectedListenerFailureBehavior, Iterable<String> iterable) {
        this(protocol, selectorFailureBehavior, selectedListenerFailureBehavior, b.a(iterable));
    }

    public ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorFailureBehavior, SelectedListenerFailureBehavior selectedListenerFailureBehavior, String... strArr) {
        this(protocol, selectorFailureBehavior, selectedListenerFailureBehavior, b.a(strArr));
    }

    private ApplicationProtocolConfig(Protocol protocol, SelectorFailureBehavior selectorFailureBehavior, SelectedListenerFailureBehavior selectedListenerFailureBehavior, List<String> list) {
        this.a = Collections.unmodifiableList((List) ObjectUtil.checkNotNull(list, "supportedProtocols"));
        this.b = (Protocol) ObjectUtil.checkNotNull(protocol, "protocol");
        this.c = (SelectorFailureBehavior) ObjectUtil.checkNotNull(selectorFailureBehavior, "selectorBehavior");
        this.d = (SelectedListenerFailureBehavior) ObjectUtil.checkNotNull(selectedListenerFailureBehavior, "selectedBehavior");
        if (protocol == Protocol.NONE) {
            throw new IllegalArgumentException("protocol (" + Protocol.NONE + ") must not be " + Protocol.NONE + '.');
        } else if (list.isEmpty()) {
            throw new IllegalArgumentException("supportedProtocols must be not empty");
        }
    }

    private ApplicationProtocolConfig() {
        this.a = Collections.emptyList();
        this.b = Protocol.NONE;
        this.c = SelectorFailureBehavior.CHOOSE_MY_LAST_PROTOCOL;
        this.d = SelectedListenerFailureBehavior.ACCEPT;
    }

    public List<String> supportedProtocols() {
        return this.a;
    }

    public Protocol protocol() {
        return this.b;
    }

    public SelectorFailureBehavior selectorFailureBehavior() {
        return this.c;
    }

    public SelectedListenerFailureBehavior selectedListenerFailureBehavior() {
        return this.d;
    }
}
