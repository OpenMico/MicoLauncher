package miuix.animation.physics;

import java.util.LinkedList;
import java.util.List;

/* loaded from: classes5.dex */
public class SpringAnimationSet {
    private List<SpringAnimation> a = new LinkedList();

    public void start() {
        if (!this.a.isEmpty()) {
            for (SpringAnimation springAnimation : this.a) {
                if (springAnimation != null) {
                    springAnimation.start();
                }
            }
        }
    }

    public void cancel() {
        if (!this.a.isEmpty()) {
            for (SpringAnimation springAnimation : this.a) {
                if (springAnimation != null) {
                    springAnimation.cancel();
                }
            }
            this.a.clear();
        }
    }

    public void endAnimation() {
        if (!this.a.isEmpty()) {
            for (SpringAnimation springAnimation : this.a) {
                if (springAnimation != null) {
                    springAnimation.skipToEnd();
                }
            }
            this.a.clear();
        }
    }

    public void play(SpringAnimation springAnimation) {
        if (springAnimation != null) {
            this.a.add(springAnimation);
        }
    }

    public void playTogether(SpringAnimation... springAnimationArr) {
        for (SpringAnimation springAnimation : springAnimationArr) {
            if (springAnimation != null) {
                this.a.add(springAnimation);
            }
        }
    }
}
