package io.netty.handler.codec;

/* loaded from: classes4.dex */
public interface ValueConverter<T> {
    T convertBoolean(boolean z);

    T convertByte(byte b);

    T convertChar(char c);

    T convertDouble(double d);

    T convertFloat(float f);

    T convertInt(int i);

    T convertLong(long j);

    T convertObject(Object obj);

    T convertShort(short s);

    T convertTimeMillis(long j);

    boolean convertToBoolean(T t);

    byte convertToByte(T t);

    char convertToChar(T t);

    double convertToDouble(T t);

    float convertToFloat(T t);

    int convertToInt(T t);

    long convertToLong(T t);

    short convertToShort(T t);

    long convertToTimeMillis(T t);
}
