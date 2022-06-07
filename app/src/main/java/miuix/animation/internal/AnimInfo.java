package miuix.animation.internal;

/* loaded from: classes5.dex */
public class AnimInfo {
    public volatile long delay;
    public volatile long initTime;
    public volatile boolean justEnd;
    public volatile byte op;
    public volatile double progress;
    public volatile long startTime;
    public volatile int tintMode = -1;
    public volatile double startValue = Double.MAX_VALUE;
    public volatile double targetValue = Double.MAX_VALUE;
    public volatile double value = Double.MAX_VALUE;
    public volatile double setToValue = Double.MAX_VALUE;

    public void clear() {
        this.op = (byte) 0;
        this.delay = 0L;
        this.initTime = 0L;
        this.startTime = 0L;
        this.progress = 0.0d;
        this.tintMode = -1;
        this.startValue = Double.MAX_VALUE;
        this.targetValue = Double.MAX_VALUE;
        this.value = Double.MAX_VALUE;
        this.justEnd = false;
    }

    public String toString() {
        return "AnimInfo{op=" + ((int) this.op) + ", delay = " + this.delay + ", initTime=" + this.initTime + ", startTime=" + this.startTime + ", progress=" + this.progress + ", config=" + this.tintMode + ", startValue=" + this.startValue + ", targetValue=" + this.targetValue + ", value=" + this.value + ", setToValue=" + this.setToValue + '}';
    }
}
