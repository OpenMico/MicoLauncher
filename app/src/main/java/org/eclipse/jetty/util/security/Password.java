package org.eclipse.jetty.util.security;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Credential;

/* loaded from: classes5.dex */
public class Password extends Credential {
    private static final Logger LOG = Log.getLogger(Password.class);
    public static final String __OBFUSCATE = "OBF:";
    private static final long serialVersionUID = 5062906681431569445L;
    private String _pw;

    public Password(String str) {
        this._pw = str;
        while (true) {
            String str2 = this._pw;
            if (str2 != null && str2.startsWith(__OBFUSCATE)) {
                this._pw = deobfuscate(this._pw);
            } else {
                return;
            }
        }
    }

    public String toString() {
        return this._pw;
    }

    public String toStarString() {
        return "*****************************************************".substring(0, this._pw.length());
    }

    @Override // org.eclipse.jetty.util.security.Credential
    public boolean check(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Password) && !(obj instanceof String)) {
            if (obj instanceof char[]) {
                return Arrays.equals(this._pw.toCharArray(), (char[]) obj);
            }
            if (obj instanceof Credential) {
                return ((Credential) obj).check(this._pw);
            }
            return false;
        }
        return obj.equals(this._pw);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Password) {
            String str = ((Password) obj)._pw;
            String str2 = this._pw;
            if (str != str2) {
                return str2 != null && str2.equals(str);
            }
            return true;
        } else if (obj instanceof String) {
            return obj.equals(this._pw);
        } else {
            return false;
        }
    }

    public int hashCode() {
        String str = this._pw;
        return str == null ? super.hashCode() : str.hashCode();
    }

    public static String obfuscate(String str) {
        StringBuilder sb = new StringBuilder();
        byte[] bytes = str.getBytes();
        sb.append(__OBFUSCATE);
        int i = 0;
        while (i < bytes.length) {
            byte b = bytes[i];
            i++;
            byte b2 = bytes[str.length() - i];
            int i2 = b + Byte.MAX_VALUE;
            String num = Integer.toString(((i2 + b2) * 256) + (i2 - b2), 36);
            switch (num.length()) {
                case 1:
                    sb.append('0');
                    sb.append('0');
                    sb.append('0');
                    sb.append(num);
                    break;
                case 2:
                    sb.append('0');
                    sb.append('0');
                    sb.append(num);
                    break;
                case 3:
                    sb.append('0');
                    sb.append(num);
                    break;
                default:
                    sb.append(num);
                    break;
            }
        }
        return sb.toString();
    }

    public static String deobfuscate(String str) {
        if (str.startsWith(__OBFUSCATE)) {
            str = str.substring(4);
        }
        byte[] bArr = new byte[str.length() / 2];
        int i = 0;
        int i2 = 0;
        while (i < str.length()) {
            int i3 = i + 4;
            int parseInt = Integer.parseInt(str.substring(i, i3), 36);
            i2++;
            bArr[i2] = (byte) ((((parseInt / 256) + (parseInt % 256)) - 254) / 2);
            i = i3;
        }
        return new String(bArr, 0, i2);
    }

    public static Password getPassword(String str, String str2, String str3) {
        String property = System.getProperty(str, str2);
        if (property == null || property.length() == 0) {
            try {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append(str);
                sb.append((str3 == null || str3.length() <= 0) ? "" : " [dft]");
                sb.append(" : ");
                printStream.print(sb.toString());
                System.out.flush();
                byte[] bArr = new byte[512];
                int read = System.in.read(bArr);
                if (read > 0) {
                    property = new String(bArr, 0, read).trim();
                }
            } catch (IOException e) {
                LOG.warn(Log.EXCEPTION, e);
            }
            if (property == null || property.length() == 0) {
                property = str3;
            }
        }
        return new Password(property);
    }

    public static void main(String[] strArr) {
        char c = 1;
        if (!(strArr.length == 1 || strArr.length == 2)) {
            System.err.println("Usage - java org.eclipse.jetty.security.Password [<user>] <password>");
            System.err.println("If the password is ?, the user will be prompted for the password");
            System.exit(1);
        }
        if (strArr.length == 1) {
            c = 0;
        }
        String str = strArr[c];
        Password password = new Password(str);
        System.err.println(password.toString());
        System.err.println(obfuscate(password.toString()));
        System.err.println(Credential.MD5.digest(str));
        if (strArr.length == 2) {
            System.err.println(Credential.Crypt.crypt(strArr[0], password.toString()));
        }
    }
}
