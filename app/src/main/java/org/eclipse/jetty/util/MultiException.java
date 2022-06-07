package org.eclipse.jetty.util;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import okhttp3.HttpUrl;

/* loaded from: classes5.dex */
public class MultiException extends Exception {
    private Object nested;

    public MultiException() {
        super("Multiple exceptions");
    }

    public void add(Throwable th) {
        if (th instanceof MultiException) {
            MultiException multiException = (MultiException) th;
            for (int i = 0; i < LazyList.size(multiException.nested); i++) {
                this.nested = LazyList.add(this.nested, LazyList.get(multiException.nested, i));
            }
            return;
        }
        this.nested = LazyList.add(this.nested, th);
    }

    public int size() {
        return LazyList.size(this.nested);
    }

    public List<Throwable> getThrowables() {
        return LazyList.getList(this.nested);
    }

    public Throwable getThrowable(int i) {
        return (Throwable) LazyList.get(this.nested, i);
    }

    public void ifExceptionThrow() throws Exception {
        switch (LazyList.size(this.nested)) {
            case 0:
                return;
            case 1:
                Throwable th = (Throwable) LazyList.get(this.nested, 0);
                if (th instanceof Error) {
                    throw ((Error) th);
                } else if (th instanceof Exception) {
                    throw ((Exception) th);
                }
                break;
        }
        throw this;
    }

    public void ifExceptionThrowRuntime() throws Error {
        switch (LazyList.size(this.nested)) {
            case 0:
                return;
            case 1:
                Throwable th = (Throwable) LazyList.get(this.nested, 0);
                if (th instanceof Error) {
                    throw ((Error) th);
                } else if (th instanceof RuntimeException) {
                    throw ((RuntimeException) th);
                } else {
                    throw new RuntimeException(th);
                }
            default:
                throw new RuntimeException(this);
        }
    }

    public void ifExceptionThrowMulti() throws MultiException {
        if (LazyList.size(this.nested) > 0) {
            throw this;
        }
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (LazyList.size(this.nested) > 0) {
            return MultiException.class.getSimpleName() + LazyList.getList(this.nested);
        }
        return MultiException.class.getSimpleName() + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
    }

    @Override // java.lang.Throwable
    public void printStackTrace() {
        super.printStackTrace();
        for (int i = 0; i < LazyList.size(this.nested); i++) {
            ((Throwable) LazyList.get(this.nested, i)).printStackTrace();
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintStream printStream) {
        super.printStackTrace(printStream);
        for (int i = 0; i < LazyList.size(this.nested); i++) {
            ((Throwable) LazyList.get(this.nested, i)).printStackTrace(printStream);
        }
    }

    @Override // java.lang.Throwable
    public void printStackTrace(PrintWriter printWriter) {
        super.printStackTrace(printWriter);
        for (int i = 0; i < LazyList.size(this.nested); i++) {
            ((Throwable) LazyList.get(this.nested, i)).printStackTrace(printWriter);
        }
    }
}
