package retrofit2;

import java.lang.annotation.Annotation;

/* compiled from: SkipCallbackExecutorImpl.java */
/* loaded from: classes6.dex */
final class l implements SkipCallbackExecutor {
    private static final SkipCallbackExecutor a = new l();

    @Override // java.lang.annotation.Annotation
    public int hashCode() {
        return 0;
    }

    l() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static Annotation[] a(Annotation[] annotationArr) {
        if (m.a(annotationArr, (Class<? extends Annotation>) SkipCallbackExecutor.class)) {
            return annotationArr;
        }
        Annotation[] annotationArr2 = new Annotation[annotationArr.length + 1];
        annotationArr2[0] = a;
        System.arraycopy(annotationArr, 0, annotationArr2, 1, annotationArr.length);
        return annotationArr2;
    }

    @Override // java.lang.annotation.Annotation
    public Class<? extends Annotation> annotationType() {
        return SkipCallbackExecutor.class;
    }

    @Override // java.lang.annotation.Annotation
    public boolean equals(Object obj) {
        return obj instanceof SkipCallbackExecutor;
    }

    @Override // java.lang.annotation.Annotation
    public String toString() {
        return "@" + SkipCallbackExecutor.class.getName() + "()";
    }
}
