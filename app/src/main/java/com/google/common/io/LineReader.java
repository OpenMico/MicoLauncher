package com.google.common.io;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;
import java.util.LinkedList;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Beta
@GwtIncompatible
/* loaded from: classes2.dex */
public final class LineReader {
    private final Readable a;
    @NullableDecl
    private final Reader b;
    private final CharBuffer c = CharStreams.a();
    private final char[] d = this.c.array();
    private final Queue<String> e = new LinkedList();
    private final c f = new c() { // from class: com.google.common.io.LineReader.1
        @Override // com.google.common.io.c
        protected void a(String str, String str2) {
            LineReader.this.e.add(str);
        }
    };

    public LineReader(Readable readable) {
        this.a = (Readable) Preconditions.checkNotNull(readable);
        this.b = readable instanceof Reader ? (Reader) readable : null;
    }

    @CanIgnoreReturnValue
    public String readLine() throws IOException {
        int i;
        while (true) {
            if (this.e.peek() != null) {
                break;
            }
            this.c.clear();
            Reader reader = this.b;
            if (reader != null) {
                char[] cArr = this.d;
                i = reader.read(cArr, 0, cArr.length);
            } else {
                i = this.a.read(this.c);
            }
            if (i == -1) {
                this.f.a();
                break;
            }
            this.f.a(this.d, 0, i);
        }
        return this.e.poll();
    }
}
