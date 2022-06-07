package io.netty.handler.codec.http;

import io.netty.util.concurrent.FastThreadLocal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes4.dex */
public final class HttpHeaderDateFormat extends SimpleDateFormat {
    private static final FastThreadLocal<HttpHeaderDateFormat> a = new FastThreadLocal<HttpHeaderDateFormat>() { // from class: io.netty.handler.codec.http.HttpHeaderDateFormat.1
        /* JADX INFO: Access modifiers changed from: protected */
        /* renamed from: a */
        public HttpHeaderDateFormat initialValue() {
            return new HttpHeaderDateFormat();
        }
    };
    private static final long serialVersionUID = -925286159755905325L;
    private final SimpleDateFormat format1;
    private final SimpleDateFormat format2;

    public static HttpHeaderDateFormat get() {
        return a.get();
    }

    private HttpHeaderDateFormat() {
        super("E, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        this.format1 = new a();
        this.format2 = new b();
        setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override // java.text.SimpleDateFormat, java.text.DateFormat
    public Date parse(String str, ParsePosition parsePosition) {
        Date parse = super.parse(str, parsePosition);
        if (parse == null) {
            parse = this.format1.parse(str, parsePosition);
        }
        return parse == null ? this.format2.parse(str, parsePosition) : parse;
    }

    /* loaded from: classes4.dex */
    private static final class a extends SimpleDateFormat {
        private static final long serialVersionUID = -3178072504225114298L;

        a() {
            super("E, dd-MMM-yy HH:mm:ss z", Locale.ENGLISH);
            setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }

    /* loaded from: classes4.dex */
    private static final class b extends SimpleDateFormat {
        private static final long serialVersionUID = 3010674519968303714L;

        b() {
            super("E MMM d HH:mm:ss yyyy", Locale.ENGLISH);
            setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }
}
