package io.netty.handler.ssl.util;

import io.netty.util.internal.ThreadLocalRandom;
import java.security.SecureRandom;
import java.util.Random;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: ThreadLocalInsecureRandom.java */
/* loaded from: classes4.dex */
public final class c extends SecureRandom {
    private static final SecureRandom a = new c();
    private static final long serialVersionUID = -8209473337192526191L;

    @Override // java.security.SecureRandom
    public String getAlgorithm() {
        return "insecure";
    }

    @Override // java.security.SecureRandom, java.util.Random
    public void setSeed(long j) {
    }

    @Override // java.security.SecureRandom
    public void setSeed(byte[] bArr) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static SecureRandom a() {
        return a;
    }

    private c() {
    }

    @Override // java.security.SecureRandom, java.util.Random
    public void nextBytes(byte[] bArr) {
        b().nextBytes(bArr);
    }

    @Override // java.security.SecureRandom
    public byte[] generateSeed(int i) {
        byte[] bArr = new byte[i];
        b().nextBytes(bArr);
        return bArr;
    }

    @Override // java.util.Random
    public int nextInt() {
        return b().nextInt();
    }

    @Override // java.util.Random
    public int nextInt(int i) {
        return b().nextInt(i);
    }

    @Override // java.util.Random
    public boolean nextBoolean() {
        return b().nextBoolean();
    }

    @Override // java.util.Random
    public long nextLong() {
        return b().nextLong();
    }

    @Override // java.util.Random
    public float nextFloat() {
        return b().nextFloat();
    }

    @Override // java.util.Random
    public double nextDouble() {
        return b().nextDouble();
    }

    @Override // java.util.Random
    public double nextGaussian() {
        return b().nextGaussian();
    }

    private static Random b() {
        return ThreadLocalRandom.current();
    }
}
