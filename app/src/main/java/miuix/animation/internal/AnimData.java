package miuix.animation.internal;

import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class AnimData {
    boolean a;
    boolean b;
    public long delay;
    public EaseManager.EaseStyle ease;
    public int frameCount;
    public long initTime;
    public boolean isCompleted;
    public byte op;
    public double progress;
    public FloatProperty property;
    public long startTime;
    public double velocity;
    public int tintMode = -1;
    public double startValue = Double.MAX_VALUE;
    public double targetValue = Double.MAX_VALUE;
    public double value = Double.MAX_VALUE;

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a() {
        this.isCompleted = false;
        this.frameCount = 0;
        this.a = false;
    }

    public void setOp(byte b) {
        this.op = b;
        this.isCompleted = b == 0 || b > 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UpdateInfo updateInfo, AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        this.property = updateInfo.property;
        this.velocity = updateInfo.velocity;
        this.frameCount = updateInfo.frameCount;
        this.op = updateInfo.animInfo.op;
        this.initTime = updateInfo.animInfo.initTime;
        this.startTime = updateInfo.animInfo.startTime;
        this.progress = updateInfo.animInfo.progress;
        this.startValue = updateInfo.animInfo.startValue;
        this.targetValue = updateInfo.animInfo.targetValue;
        this.value = updateInfo.animInfo.value;
        this.isCompleted = updateInfo.isCompleted;
        this.a = updateInfo.animInfo.justEnd;
        this.tintMode = AnimConfigUtils.c(animConfig, animSpecialConfig);
        this.ease = AnimConfigUtils.a(animConfig, animSpecialConfig);
        this.delay = AnimConfigUtils.b(animConfig, animSpecialConfig);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void a(UpdateInfo updateInfo) {
        updateInfo.frameCount = this.frameCount;
        updateInfo.animInfo.op = this.op;
        updateInfo.animInfo.delay = this.delay;
        updateInfo.animInfo.tintMode = this.tintMode;
        updateInfo.animInfo.initTime = this.initTime;
        updateInfo.animInfo.startTime = this.startTime;
        updateInfo.animInfo.progress = this.progress;
        updateInfo.animInfo.startValue = this.startValue;
        updateInfo.animInfo.targetValue = this.targetValue;
        updateInfo.isCompleted = this.isCompleted;
        updateInfo.animInfo.value = this.value;
        updateInfo.velocity = this.velocity;
        updateInfo.animInfo.justEnd = this.a;
        b();
    }

    void b() {
        this.property = null;
        this.ease = null;
    }
}
