package androidx.renderscript;

/* loaded from: classes.dex */
public class Sampler extends BaseObj {
    Value a;
    Value b;
    Value c;
    Value d;
    Value e;
    float f;

    /* loaded from: classes.dex */
    public enum Value {
        NEAREST(0),
        LINEAR(1),
        LINEAR_MIP_LINEAR(2),
        LINEAR_MIP_NEAREST(5),
        WRAP(3),
        CLAMP(4),
        MIRRORED_REPEAT(6);
        
        int mID;

        Value(int i) {
            this.mID = i;
        }
    }

    Sampler(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public Value getMinification() {
        return this.a;
    }

    public Value getMagnification() {
        return this.b;
    }

    public Value getWrapS() {
        return this.c;
    }

    public Value getWrapT() {
        return this.d;
    }

    public float getAnisotropy() {
        return this.f;
    }

    public static Sampler CLAMP_NEAREST(RenderScript renderScript) {
        if (renderScript.ar == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.NEAREST);
            builder.setMagnification(Value.NEAREST);
            builder.setWrapS(Value.CLAMP);
            builder.setWrapT(Value.CLAMP);
            renderScript.ar = builder.create();
        }
        return renderScript.ar;
    }

    public static Sampler CLAMP_LINEAR(RenderScript renderScript) {
        if (renderScript.as == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.LINEAR);
            builder.setMagnification(Value.LINEAR);
            builder.setWrapS(Value.CLAMP);
            builder.setWrapT(Value.CLAMP);
            renderScript.as = builder.create();
        }
        return renderScript.as;
    }

    public static Sampler CLAMP_LINEAR_MIP_LINEAR(RenderScript renderScript) {
        if (renderScript.at == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.LINEAR_MIP_LINEAR);
            builder.setMagnification(Value.LINEAR);
            builder.setWrapS(Value.CLAMP);
            builder.setWrapT(Value.CLAMP);
            renderScript.at = builder.create();
        }
        return renderScript.at;
    }

    public static Sampler WRAP_NEAREST(RenderScript renderScript) {
        if (renderScript.au == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.NEAREST);
            builder.setMagnification(Value.NEAREST);
            builder.setWrapS(Value.WRAP);
            builder.setWrapT(Value.WRAP);
            renderScript.au = builder.create();
        }
        return renderScript.au;
    }

    public static Sampler WRAP_LINEAR(RenderScript renderScript) {
        if (renderScript.av == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.LINEAR);
            builder.setMagnification(Value.LINEAR);
            builder.setWrapS(Value.WRAP);
            builder.setWrapT(Value.WRAP);
            renderScript.av = builder.create();
        }
        return renderScript.av;
    }

    public static Sampler WRAP_LINEAR_MIP_LINEAR(RenderScript renderScript) {
        if (renderScript.aw == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.LINEAR_MIP_LINEAR);
            builder.setMagnification(Value.LINEAR);
            builder.setWrapS(Value.WRAP);
            builder.setWrapT(Value.WRAP);
            renderScript.aw = builder.create();
        }
        return renderScript.aw;
    }

    public static Sampler MIRRORED_REPEAT_NEAREST(RenderScript renderScript) {
        if (renderScript.ax == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.NEAREST);
            builder.setMagnification(Value.NEAREST);
            builder.setWrapS(Value.MIRRORED_REPEAT);
            builder.setWrapT(Value.MIRRORED_REPEAT);
            renderScript.ax = builder.create();
        }
        return renderScript.ax;
    }

    public static Sampler MIRRORED_REPEAT_LINEAR(RenderScript renderScript) {
        if (renderScript.ay == null) {
            Builder builder = new Builder(renderScript);
            builder.setMinification(Value.LINEAR);
            builder.setMagnification(Value.LINEAR);
            builder.setWrapS(Value.MIRRORED_REPEAT);
            builder.setWrapT(Value.MIRRORED_REPEAT);
            renderScript.ay = builder.create();
        }
        return renderScript.ay;
    }

    /* loaded from: classes.dex */
    public static class Builder {
        RenderScript a;
        Value b = Value.NEAREST;
        Value c = Value.NEAREST;
        Value d = Value.WRAP;
        Value e = Value.WRAP;
        Value f = Value.WRAP;
        float g = 1.0f;

        public Builder(RenderScript renderScript) {
            this.a = renderScript;
        }

        public void setMinification(Value value) {
            if (value == Value.NEAREST || value == Value.LINEAR || value == Value.LINEAR_MIP_LINEAR || value == Value.LINEAR_MIP_NEAREST) {
                this.b = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setMagnification(Value value) {
            if (value == Value.NEAREST || value == Value.LINEAR) {
                this.c = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setWrapS(Value value) {
            if (value == Value.WRAP || value == Value.CLAMP || value == Value.MIRRORED_REPEAT) {
                this.d = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setWrapT(Value value) {
            if (value == Value.WRAP || value == Value.CLAMP || value == Value.MIRRORED_REPEAT) {
                this.e = value;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public void setAnisotropy(float f) {
            if (f >= 0.0f) {
                this.g = f;
                return;
            }
            throw new IllegalArgumentException("Invalid value");
        }

        public Sampler create() {
            this.a.g();
            Sampler sampler = new Sampler(this.a.a(this.c.mID, this.b.mID, this.d.mID, this.e.mID, this.f.mID, this.g), this.a);
            sampler.a = this.b;
            sampler.b = this.c;
            sampler.c = this.d;
            sampler.d = this.e;
            sampler.e = this.f;
            sampler.f = this.g;
            return sampler;
        }
    }
}
