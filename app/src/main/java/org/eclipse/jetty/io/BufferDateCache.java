package org.eclipse.jetty.io;

import java.text.DateFormatSymbols;
import java.util.Locale;
import org.eclipse.jetty.util.DateCache;

/* loaded from: classes5.dex */
public class BufferDateCache extends DateCache {
    Buffer _buffer;
    String _last;

    public BufferDateCache() {
    }

    public BufferDateCache(String str, DateFormatSymbols dateFormatSymbols) {
        super(str, dateFormatSymbols);
    }

    public BufferDateCache(String str, Locale locale) {
        super(str, locale);
    }

    public BufferDateCache(String str) {
        super(str);
    }

    public synchronized Buffer formatBuffer(long j) {
        String format = super.format(j);
        if (format == this._last) {
            return this._buffer;
        }
        this._last = format;
        this._buffer = new ByteArrayBuffer(format);
        return this._buffer;
    }
}
