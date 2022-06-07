package com.google.zxing.client.result;

/* loaded from: classes2.dex */
public final class AddressBookParsedResult extends ParsedResult {
    private final String[] a;
    private final String[] b;
    private final String c;
    private final String[] d;
    private final String[] e;
    private final String[] f;
    private final String[] g;
    private final String h;
    private final String i;
    private final String[] j;
    private final String[] k;
    private final String l;
    private final String m;
    private final String n;
    private final String[] o;
    private final String[] p;

    public AddressBookParsedResult(String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, String[] strArr5, String[] strArr6, String[] strArr7) {
        this(strArr, null, null, strArr2, strArr3, strArr4, strArr5, null, null, strArr6, strArr7, null, null, null, null, null);
    }

    public AddressBookParsedResult(String[] strArr, String[] strArr2, String str, String[] strArr3, String[] strArr4, String[] strArr5, String[] strArr6, String str2, String str3, String[] strArr7, String[] strArr8, String str4, String str5, String str6, String[] strArr9, String[] strArr10) {
        super(ParsedResultType.ADDRESSBOOK);
        if (strArr3 != null && strArr4 != null && strArr3.length != strArr4.length) {
            throw new IllegalArgumentException("Phone numbers and types lengths differ");
        } else if (strArr5 != null && strArr6 != null && strArr5.length != strArr6.length) {
            throw new IllegalArgumentException("Emails and types lengths differ");
        } else if (strArr7 == null || strArr8 == null || strArr7.length == strArr8.length) {
            this.a = strArr;
            this.b = strArr2;
            this.c = str;
            this.d = strArr3;
            this.e = strArr4;
            this.f = strArr5;
            this.g = strArr6;
            this.h = str2;
            this.i = str3;
            this.j = strArr7;
            this.k = strArr8;
            this.l = str4;
            this.m = str5;
            this.n = str6;
            this.o = strArr9;
            this.p = strArr10;
        } else {
            throw new IllegalArgumentException("Addresses and types lengths differ");
        }
    }

    public String[] getNames() {
        return this.a;
    }

    public String[] getNicknames() {
        return this.b;
    }

    public String getPronunciation() {
        return this.c;
    }

    public String[] getPhoneNumbers() {
        return this.d;
    }

    public String[] getPhoneTypes() {
        return this.e;
    }

    public String[] getEmails() {
        return this.f;
    }

    public String[] getEmailTypes() {
        return this.g;
    }

    public String getInstantMessenger() {
        return this.h;
    }

    public String getNote() {
        return this.i;
    }

    public String[] getAddresses() {
        return this.j;
    }

    public String[] getAddressTypes() {
        return this.k;
    }

    public String getTitle() {
        return this.n;
    }

    public String getOrg() {
        return this.l;
    }

    public String[] getURLs() {
        return this.o;
    }

    public String getBirthday() {
        return this.m;
    }

    public String[] getGeo() {
        return this.p;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(100);
        maybeAppend(this.a, sb);
        maybeAppend(this.b, sb);
        maybeAppend(this.c, sb);
        maybeAppend(this.n, sb);
        maybeAppend(this.l, sb);
        maybeAppend(this.j, sb);
        maybeAppend(this.d, sb);
        maybeAppend(this.f, sb);
        maybeAppend(this.h, sb);
        maybeAppend(this.o, sb);
        maybeAppend(this.m, sb);
        maybeAppend(this.p, sb);
        maybeAppend(this.i, sb);
        return sb.toString();
    }
}
