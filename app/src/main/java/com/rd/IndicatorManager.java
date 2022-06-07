package com.rd;

import androidx.annotation.Nullable;
import com.rd.animation.AnimationManager;
import com.rd.animation.controller.ValueController;
import com.rd.animation.data.Value;
import com.rd.draw.DrawManager;
import com.rd.draw.data.Indicator;

/* loaded from: classes2.dex */
public class IndicatorManager implements ValueController.UpdateListener {
    private DrawManager a = new DrawManager();
    private AnimationManager b = new AnimationManager(this.a.indicator(), this);
    private a c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public interface a {
        void onIndicatorUpdated();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public IndicatorManager(@Nullable a aVar) {
        this.c = aVar;
    }

    public AnimationManager animate() {
        return this.b;
    }

    public Indicator indicator() {
        return this.a.indicator();
    }

    public DrawManager drawer() {
        return this.a;
    }

    @Override // com.rd.animation.controller.ValueController.UpdateListener
    public void onValueUpdated(@Nullable Value value) {
        this.a.updateValue(value);
        a aVar = this.c;
        if (aVar != null) {
            aVar.onIndicatorUpdated();
        }
    }
}
