package java8.util;

/* loaded from: classes5.dex */
public final class StringJoiner {
    private final String a;
    private final String b;
    private final String c;
    private StringBuilder d;
    private String e;

    public StringJoiner(CharSequence charSequence) {
        this(charSequence, "", "");
    }

    public StringJoiner(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        Objects.requireNonNull(charSequence2, "The prefix must not be null");
        Objects.requireNonNull(charSequence, "The delimiter must not be null");
        Objects.requireNonNull(charSequence3, "The suffix must not be null");
        this.a = charSequence2.toString();
        this.b = charSequence.toString();
        this.c = charSequence3.toString();
        this.e = this.a + this.c;
    }

    public StringJoiner setEmptyValue(CharSequence charSequence) {
        this.e = ((CharSequence) Objects.requireNonNull(charSequence, "The empty value must not be null")).toString();
        return this;
    }

    public String toString() {
        if (this.d == null) {
            return this.e;
        }
        if (this.c.equals("")) {
            return this.d.toString();
        }
        int length = this.d.length();
        StringBuilder sb = this.d;
        sb.append(this.c);
        String sb2 = sb.toString();
        this.d.setLength(length);
        return sb2;
    }

    public StringJoiner add(CharSequence charSequence) {
        a().append(charSequence);
        return this;
    }

    public StringJoiner merge(StringJoiner stringJoiner) {
        Objects.requireNonNull(stringJoiner);
        StringBuilder sb = stringJoiner.d;
        if (sb != null) {
            a().append((CharSequence) stringJoiner.d, stringJoiner.a.length(), sb.length());
        }
        return this;
    }

    private StringBuilder a() {
        StringBuilder sb = this.d;
        if (sb != null) {
            sb.append(this.b);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.a);
            this.d = sb2;
        }
        return this.d;
    }

    public int length() {
        StringBuilder sb = this.d;
        return sb != null ? sb.length() + this.c.length() : this.e.length();
    }
}
