package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.InputStream;

/* loaded from: classes2.dex */
public interface ASN1OctetStringParser extends ASN1Encodable, InMemoryRepresentable {
    InputStream getOctetStream();
}
