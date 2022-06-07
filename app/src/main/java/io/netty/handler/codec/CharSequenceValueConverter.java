package io.netty.handler.codec;

import io.netty.handler.codec.DefaultHeaders;
import io.netty.util.AsciiString;
import io.netty.util.internal.PlatformDependent;
import java.text.ParseException;

/* loaded from: classes4.dex */
public class CharSequenceValueConverter implements ValueConverter<CharSequence> {
    public static final CharSequenceValueConverter INSTANCE = new CharSequenceValueConverter();

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertObject(Object obj) {
        if (obj instanceof CharSequence) {
            return (CharSequence) obj;
        }
        return obj.toString();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertInt(int i) {
        return String.valueOf(i);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertLong(long j) {
        return String.valueOf(j);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertDouble(double d) {
        return String.valueOf(d);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertChar(char c) {
        return String.valueOf(c);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertBoolean(boolean z) {
        return String.valueOf(z);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertFloat(float f) {
        return String.valueOf(f);
    }

    public boolean convertToBoolean(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseBoolean();
        }
        return Boolean.parseBoolean(charSequence.toString());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertByte(byte b) {
        return String.valueOf((int) b);
    }

    public byte convertToByte(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).byteAt(0);
        }
        return Byte.valueOf(charSequence.toString()).byteValue();
    }

    public char convertToChar(CharSequence charSequence) {
        return charSequence.charAt(0);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertShort(short s) {
        return String.valueOf((int) s);
    }

    public short convertToShort(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseShort();
        }
        return Short.valueOf(charSequence.toString()).shortValue();
    }

    public int convertToInt(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseInt();
        }
        return Integer.parseInt(charSequence.toString());
    }

    public long convertToLong(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseLong();
        }
        return Long.parseLong(charSequence.toString());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.netty.handler.codec.ValueConverter
    public CharSequence convertTimeMillis(long j) {
        return String.valueOf(j);
    }

    public long convertToTimeMillis(CharSequence charSequence) {
        try {
            return DefaultHeaders.HeaderDateFormat.a().a(charSequence.toString());
        } catch (ParseException e) {
            PlatformDependent.throwException(e);
            return 0L;
        }
    }

    public float convertToFloat(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseFloat();
        }
        return Float.valueOf(charSequence.toString()).floatValue();
    }

    public double convertToDouble(CharSequence charSequence) {
        if (charSequence instanceof AsciiString) {
            return ((AsciiString) charSequence).parseDouble();
        }
        return Double.valueOf(charSequence.toString()).doubleValue();
    }
}
