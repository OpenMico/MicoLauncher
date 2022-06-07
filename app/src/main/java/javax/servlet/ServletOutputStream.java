package javax.servlet;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/* loaded from: classes5.dex */
public abstract class ServletOutputStream extends OutputStream {
    private static final String LSTRING_FILE = "javax.servlet.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    public void print(String str) throws IOException {
        if (str == null) {
            str = "null";
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((65280 & charAt) == 0) {
                write(charAt);
            } else {
                throw new CharConversionException(MessageFormat.format(lStrings.getString("err.not_iso8859_1"), new Character(charAt)));
            }
        }
    }

    public void print(boolean z) throws IOException {
        String str;
        if (z) {
            str = lStrings.getString("value.true");
        } else {
            str = lStrings.getString("value.false");
        }
        print(str);
    }

    public void print(char c) throws IOException {
        print(String.valueOf(c));
    }

    public void print(int i) throws IOException {
        print(String.valueOf(i));
    }

    public void print(long j) throws IOException {
        print(String.valueOf(j));
    }

    public void print(float f) throws IOException {
        print(String.valueOf(f));
    }

    public void print(double d) throws IOException {
        print(String.valueOf(d));
    }

    public void println() throws IOException {
        print("\r\n");
    }

    public void println(String str) throws IOException {
        print(str);
        println();
    }

    public void println(boolean z) throws IOException {
        print(z);
        println();
    }

    public void println(char c) throws IOException {
        print(c);
        println();
    }

    public void println(int i) throws IOException {
        print(i);
        println();
    }

    public void println(long j) throws IOException {
        print(j);
        println();
    }

    public void println(float f) throws IOException {
        print(f);
        println();
    }

    public void println(double d) throws IOException {
        print(d);
        println();
    }
}
