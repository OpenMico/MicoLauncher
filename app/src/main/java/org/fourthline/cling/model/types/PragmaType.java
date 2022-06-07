package org.fourthline.cling.model.types;

/* loaded from: classes5.dex */
public class PragmaType {
    private boolean quote;
    private String token;
    private String value;

    public PragmaType(String str, String str2, boolean z) {
        this.token = str;
        this.value = str2;
        this.quote = z;
    }

    public PragmaType(String str, String str2) {
        this.token = str;
        this.value = str2;
    }

    public PragmaType(String str) {
        this.token = null;
        this.value = str;
    }

    public String getToken() {
        return this.token;
    }

    public String getValue() {
        return this.value;
    }

    public String getString() {
        String str;
        String str2 = "";
        if (this.token != null) {
            str2 = str2 + this.token + "=";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        if (this.quote) {
            str = "\"" + this.value + "\"";
        } else {
            str = this.value;
        }
        sb.append(str);
        return sb.toString();
    }

    public static PragmaType valueOf(String str) throws InvalidValueException {
        if (str.length() != 0) {
            String str2 = null;
            String[] split = str.split("=");
            boolean z = false;
            if (split.length > 1) {
                str2 = split[0];
                str = split[1];
                if (str.startsWith("\"") && str.endsWith("\"")) {
                    str = str.substring(1, str.length() - 1);
                    z = true;
                }
            }
            return new PragmaType(str2, str, z);
        }
        throw new InvalidValueException("Can't parse Bytes Range: " + str);
    }
}
