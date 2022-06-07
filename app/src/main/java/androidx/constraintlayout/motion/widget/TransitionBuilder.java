package androidx.constraintlayout.motion.widget;

import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;

/* loaded from: classes.dex */
public class TransitionBuilder {
    public static void validate(MotionLayout motionLayout) {
        if (motionLayout.a != null) {
            MotionScene motionScene = motionLayout.a;
            if (!motionScene.validateLayout(motionLayout)) {
                throw new RuntimeException("MotionLayout doesn't have the right motion scene.");
            } else if (motionScene.b == null || motionScene.getDefinedTransitions().isEmpty()) {
                throw new RuntimeException("Invalid motion layout. Motion Scene doesn't have any transition.");
            }
        } else {
            throw new RuntimeException("Invalid motion layout. Layout missing Motion Scene.");
        }
    }

    public static MotionScene.Transition buildTransition(MotionScene motionScene, int i, int i2, ConstraintSet constraintSet, int i3, ConstraintSet constraintSet2) {
        MotionScene.Transition transition = new MotionScene.Transition(i, motionScene, i2, i3);
        a(motionScene, transition, constraintSet, constraintSet2);
        return transition;
    }

    private static void a(MotionScene motionScene, MotionScene.Transition transition, ConstraintSet constraintSet, ConstraintSet constraintSet2) {
        int startConstraintSetId = transition.getStartConstraintSetId();
        int endConstraintSetId = transition.getEndConstraintSetId();
        motionScene.setConstraintSet(startConstraintSetId, constraintSet);
        motionScene.setConstraintSet(endConstraintSetId, constraintSet2);
    }
}
