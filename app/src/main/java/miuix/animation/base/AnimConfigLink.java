package miuix.animation.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.internal.AnimConfigUtils;
import miuix.animation.utils.EaseManager;

/* loaded from: classes5.dex */
public class AnimConfigLink {
    private static final AtomicInteger a = new AtomicInteger();
    private final int b = a.getAndIncrement();
    public final List<AnimConfig> configList = new ArrayList();
    private final AnimConfig c = new AnimConfig();

    public static AnimConfigLink linkConfig(AnimConfig... animConfigArr) {
        AnimConfigLink animConfigLink = new AnimConfigLink();
        for (AnimConfig animConfig : animConfigArr) {
            animConfigLink.add(animConfig, new boolean[0]);
        }
        return animConfigLink;
    }

    public void add(AnimConfig animConfig, boolean... zArr) {
        if (animConfig != null && !this.configList.contains(animConfig)) {
            if (zArr.length <= 0 || !zArr[0]) {
                this.configList.add(animConfig);
                return;
            }
            this.configList.add(new AnimConfig(animConfig));
        }
    }

    public void add(AnimConfigLink animConfigLink, boolean... zArr) {
        if (animConfigLink != null) {
            for (AnimConfig animConfig : animConfigLink.configList) {
                add(animConfig, zArr);
            }
        }
    }

    public int size() {
        return this.configList.size();
    }

    public void remove(AnimConfig animConfig) {
        if (animConfig != null) {
            this.configList.remove(animConfig);
            if (this.configList.isEmpty()) {
                this.c.clear();
                this.configList.add(this.c);
            }
        }
    }

    public void copy(AnimConfigLink animConfigLink) {
        a();
        if (animConfigLink != null) {
            this.configList.addAll(animConfigLink.configList);
        }
    }

    public void addTo(AnimConfig animConfig) {
        for (int size = this.configList.size() - 1; size >= 0; size--) {
            AnimConfig animConfig2 = this.configList.get(size);
            animConfig.delay = Math.max(animConfig.delay, animConfig2.delay);
            EaseManager.EaseStyle easeStyle = animConfig.ease;
            EaseManager.EaseStyle easeStyle2 = animConfig2.ease;
            if (!(easeStyle2 == null || easeStyle2 == AnimConfig.sDefEase)) {
                easeStyle = easeStyle2;
            }
            animConfig.setEase(easeStyle);
            animConfig.listeners.addAll(animConfig2.listeners);
            animConfig.flags |= animConfig2.flags;
            animConfig.fromSpeed = AnimConfigUtils.chooseSpeed(animConfig.fromSpeed, animConfig2.fromSpeed);
            animConfig.minDuration = Math.max(animConfig.minDuration, animConfig2.minDuration);
            animConfig.tintMode = Math.max(animConfig.tintMode, animConfig2.tintMode);
            animConfig.addSpecialConfigs(animConfig2);
        }
    }

    public void clear() {
        a();
        this.configList.add(this.c);
    }

    private void a() {
        this.configList.clear();
        this.c.clear();
    }

    public AnimConfig getHead() {
        if (this.configList.isEmpty()) {
            this.configList.add(this.c);
        }
        return this.configList.get(0);
    }

    public String toString() {
        return "AnimConfigLink{id = " + this.b + ", configList=" + Arrays.toString(this.configList.toArray()) + '}';
    }
}
