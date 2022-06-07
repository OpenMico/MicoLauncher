package org.apache.commons.lang.exception;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes5.dex */
public class NestableDelegate implements Serializable {
    static Class a = null;
    public static boolean matchSubclasses = true;
    private static final long serialVersionUID = 1;
    public static boolean topDown = true;
    public static boolean trimStackFrames = true;
    private Throwable nestable;

    public NestableDelegate(Nestable nestable) {
        this.nestable = null;
        if (nestable instanceof Throwable) {
            this.nestable = (Throwable) nestable;
            return;
        }
        throw new IllegalArgumentException("The Nestable implementation passed to the NestableDelegate(Nestable) constructor must extend java.lang.Throwable");
    }

    public String getMessage(int i) {
        Throwable throwable = getThrowable(i);
        Class cls = a;
        if (cls == null) {
            cls = a("org.apache.commons.lang.exception.Nestable");
            a = cls;
        }
        if (cls.isInstance(throwable)) {
            return ((Nestable) throwable).getMessage(0);
        }
        return throwable.getMessage();
    }

    static Class a(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public String getMessage(String str) {
        Throwable cause = ExceptionUtils.getCause(this.nestable);
        String message = cause == null ? null : cause.getMessage();
        if (cause == null || message == null) {
            return str;
        }
        if (str == null) {
            return message;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        stringBuffer.append(": ");
        stringBuffer.append(message);
        return stringBuffer.toString();
    }

    public String[] getMessages() {
        Throwable[] throwables = getThrowables();
        String[] strArr = new String[throwables.length];
        for (int i = 0; i < throwables.length; i++) {
            Class cls = a;
            if (cls == null) {
                cls = a("org.apache.commons.lang.exception.Nestable");
                a = cls;
            }
            strArr[i] = cls.isInstance(throwables[i]) ? ((Nestable) throwables[i]).getMessage(0) : throwables[i].getMessage();
        }
        return strArr;
    }

    public Throwable getThrowable(int i) {
        if (i == 0) {
            return this.nestable;
        }
        return getThrowables()[i];
    }

    public int getThrowableCount() {
        return ExceptionUtils.getThrowableCount(this.nestable);
    }

    public Throwable[] getThrowables() {
        return ExceptionUtils.getThrowables(this.nestable);
    }

    public int indexOfThrowable(Class cls, int i) {
        if (cls == null) {
            return -1;
        }
        if (i >= 0) {
            Throwable[] throwables = ExceptionUtils.getThrowables(this.nestable);
            if (i < throwables.length) {
                if (matchSubclasses) {
                    while (i < throwables.length) {
                        if (cls.isAssignableFrom(throwables[i].getClass())) {
                            return i;
                        }
                        i++;
                    }
                } else {
                    while (i < throwables.length) {
                        if (cls.equals(throwables[i].getClass())) {
                            return i;
                        }
                        i++;
                    }
                }
                return -1;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("The start index was out of bounds: ");
            stringBuffer.append(i);
            stringBuffer.append(" >= ");
            stringBuffer.append(throwables.length);
            throw new IndexOutOfBoundsException(stringBuffer.toString());
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("The start index was out of bounds: ");
        stringBuffer2.append(i);
        throw new IndexOutOfBoundsException(stringBuffer2.toString());
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream printStream) {
        synchronized (printStream) {
            PrintWriter printWriter = new PrintWriter((OutputStream) printStream, false);
            printStackTrace(printWriter);
            printWriter.flush();
        }
    }

    public void printStackTrace(PrintWriter printWriter) {
        Throwable th = this.nestable;
        if (!ExceptionUtils.isThrowableNested()) {
            ArrayList arrayList = new ArrayList();
            while (th != null) {
                arrayList.add(getStackFrames(th));
                th = ExceptionUtils.getCause(th);
            }
            String str = "Caused by: ";
            if (!topDown) {
                str = "Rethrown as: ";
                Collections.reverse(arrayList);
            }
            if (trimStackFrames) {
                trimStackFrames(arrayList);
            }
            synchronized (printWriter) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    for (String str2 : (String[]) it.next()) {
                        printWriter.println(str2);
                    }
                    if (it.hasNext()) {
                        printWriter.print(str);
                    }
                }
            }
        } else if (th instanceof Nestable) {
            ((Nestable) th).printPartialStackTrace(printWriter);
        } else {
            th.printStackTrace(printWriter);
        }
    }

    protected String[] getStackFrames(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, true);
        if (th instanceof Nestable) {
            ((Nestable) th).printPartialStackTrace(printWriter);
        } else {
            th.printStackTrace(printWriter);
        }
        return ExceptionUtils.a(stringWriter.getBuffer().toString());
    }

    protected void trimStackFrames(List list) {
        for (int size = list.size() - 1; size > 0; size--) {
            String[] strArr = (String[]) list.get(size);
            ArrayList arrayList = new ArrayList(Arrays.asList(strArr));
            ExceptionUtils.removeCommonFrames(arrayList, new ArrayList(Arrays.asList((String[]) list.get(size - 1))));
            int length = strArr.length - arrayList.size();
            if (length > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("\t... ");
                stringBuffer.append(length);
                stringBuffer.append(" more");
                arrayList.add(stringBuffer.toString());
                list.set(size, arrayList.toArray(new String[arrayList.size()]));
            }
        }
    }
}
