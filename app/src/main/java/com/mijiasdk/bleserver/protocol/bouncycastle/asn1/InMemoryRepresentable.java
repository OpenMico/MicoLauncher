package com.mijiasdk.bleserver.protocol.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes2.dex */
public interface InMemoryRepresentable {
    ASN1Primitive getLoadedObject() throws IOException;
}
