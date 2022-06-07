package io.netty.handler.codec;

/* loaded from: classes4.dex */
public final class UnsupportedValueConverter<V> implements ValueConverter<V> {
    private static final UnsupportedValueConverter a = new UnsupportedValueConverter();

    private UnsupportedValueConverter() {
    }

    public static <V> UnsupportedValueConverter<V> instance() {
        return a;
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertObject(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertBoolean(boolean z) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public boolean convertToBoolean(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertByte(byte b) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public byte convertToByte(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertChar(char c) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public char convertToChar(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertShort(short s) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public short convertToShort(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertInt(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public int convertToInt(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertLong(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public long convertToLong(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertTimeMillis(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public long convertToTimeMillis(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertFloat(float f) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public float convertToFloat(V v) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public V convertDouble(double d) {
        throw new UnsupportedOperationException();
    }

    @Override // io.netty.handler.codec.ValueConverter
    public double convertToDouble(V v) {
        throw new UnsupportedOperationException();
    }
}
