package com.rd.animation.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.rd.animation.data.Value;
import com.rd.animation.type.ColorAnimation;
import com.rd.animation.type.DropAnimation;
import com.rd.animation.type.FillAnimation;
import com.rd.animation.type.ScaleAnimation;
import com.rd.animation.type.ScaleDownAnimation;
import com.rd.animation.type.SlideAnimation;
import com.rd.animation.type.SwapAnimation;
import com.rd.animation.type.ThinWormAnimation;
import com.rd.animation.type.WormAnimation;

/* loaded from: classes2.dex */
public class ValueController {
    private ColorAnimation a;
    private ScaleAnimation b;
    private WormAnimation c;
    private SlideAnimation d;
    private FillAnimation e;
    private ThinWormAnimation f;
    private DropAnimation g;
    private SwapAnimation h;
    private ScaleDownAnimation i;
    private UpdateListener j;

    /* loaded from: classes2.dex */
    public interface UpdateListener {
        void onValueUpdated(@Nullable Value value);
    }

    public ValueController(@Nullable UpdateListener updateListener) {
        this.j = updateListener;
    }

    @NonNull
    public ColorAnimation color() {
        if (this.a == null) {
            this.a = new ColorAnimation(this.j);
        }
        return this.a;
    }

    @NonNull
    public ScaleAnimation scale() {
        if (this.b == null) {
            this.b = new ScaleAnimation(this.j);
        }
        return this.b;
    }

    @NonNull
    public WormAnimation worm() {
        if (this.c == null) {
            this.c = new WormAnimation(this.j);
        }
        return this.c;
    }

    @NonNull
    public SlideAnimation slide() {
        if (this.d == null) {
            this.d = new SlideAnimation(this.j);
        }
        return this.d;
    }

    @NonNull
    public FillAnimation fill() {
        if (this.e == null) {
            this.e = new FillAnimation(this.j);
        }
        return this.e;
    }

    @NonNull
    public ThinWormAnimation thinWorm() {
        if (this.f == null) {
            this.f = new ThinWormAnimation(this.j);
        }
        return this.f;
    }

    @NonNull
    public DropAnimation drop() {
        if (this.g == null) {
            this.g = new DropAnimation(this.j);
        }
        return this.g;
    }

    @NonNull
    public SwapAnimation swap() {
        if (this.h == null) {
            this.h = new SwapAnimation(this.j);
        }
        return this.h;
    }

    @NonNull
    public ScaleDownAnimation scaleDown() {
        if (this.i == null) {
            this.i = new ScaleDownAnimation(this.j);
        }
        return this.i;
    }
}
