package androidx.transition;

import android.content.Context;
import android.util.AttributeSet;

/* loaded from: classes.dex */
public class AutoTransition extends TransitionSet {
    public AutoTransition() {
        a();
    }

    public AutoTransition(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    private void a() {
        setOrdering(1);
        addTransition(new Fade(2)).addTransition(new ChangeBounds()).addTransition(new Fade(1));
    }
}
