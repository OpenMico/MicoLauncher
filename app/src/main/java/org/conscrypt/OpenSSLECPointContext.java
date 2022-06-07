package org.conscrypt;

import java.math.BigInteger;
import java.security.spec.ECPoint;
import org.conscrypt.NativeRef;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes5.dex */
public final class OpenSSLECPointContext {
    private final OpenSSLECGroupContext group;
    private final NativeRef.EC_POINT pointCtx;

    /* JADX INFO: Access modifiers changed from: package-private */
    public OpenSSLECPointContext(OpenSSLECGroupContext openSSLECGroupContext, NativeRef.EC_POINT ec_point) {
        this.group = openSSLECGroupContext;
        this.pointCtx = ec_point;
    }

    public boolean equals(Object obj) {
        throw new IllegalArgumentException("OpenSSLECPointContext.equals is not defined.");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ECPoint getECPoint() {
        byte[][] EC_POINT_get_affine_coordinates = NativeCrypto.EC_POINT_get_affine_coordinates(this.group.getNativeRef(), this.pointCtx);
        return new ECPoint(new BigInteger(EC_POINT_get_affine_coordinates[0]), new BigInteger(EC_POINT_get_affine_coordinates[1]));
    }

    public int hashCode() {
        return super.hashCode();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public NativeRef.EC_POINT getNativeRef() {
        return this.pointCtx;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static OpenSSLECPointContext getInstance(OpenSSLECGroupContext openSSLECGroupContext, ECPoint eCPoint) {
        OpenSSLECPointContext openSSLECPointContext = new OpenSSLECPointContext(openSSLECGroupContext, new NativeRef.EC_POINT(NativeCrypto.EC_POINT_new(openSSLECGroupContext.getNativeRef())));
        NativeCrypto.EC_POINT_set_affine_coordinates(openSSLECGroupContext.getNativeRef(), openSSLECPointContext.getNativeRef(), eCPoint.getAffineX().toByteArray(), eCPoint.getAffineY().toByteArray());
        return openSSLECPointContext;
    }
}
