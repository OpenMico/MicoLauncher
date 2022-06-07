package java8.util.stream;

/* loaded from: classes5.dex */
final /* synthetic */ class he implements Runnable {
    private final BaseStream a;

    private he(BaseStream baseStream) {
        this.a = baseStream;
    }

    public static Runnable a(BaseStream baseStream) {
        return new he(baseStream);
    }

    @Override // java.lang.Runnable
    public void run() {
        this.a.close();
    }
}
