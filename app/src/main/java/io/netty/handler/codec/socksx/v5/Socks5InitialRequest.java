package io.netty.handler.codec.socksx.v5;

import java.util.List;

/* loaded from: classes4.dex */
public interface Socks5InitialRequest extends Socks5Message {
    List<Socks5AuthMethod> authMethods();
}
