package com.dinuscxj.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.TypedValue;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
public class ShaderItemDecoration extends RecyclerView.ItemDecoration {
    public static final int SHADER_BOTTOM = 2;
    public static final int SHADER_LEFT = 4;
    public static final int SHADER_RIGHT = 8;
    public static final int SHADER_TOP = 1;
    private static final int a = Color.parseColor("#FF3c3c3c");
    private static final int b = Color.parseColor("#00000000");
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private Shader h;
    private Shader i;
    private Shader j;
    private Shader k;
    private ShaderCreator l;
    private ShaderCreator m;
    private ShaderCreator n;
    private ShaderCreator o;

    /* loaded from: classes.dex */
    public interface ShaderCreator {
        Shader createShader(RecyclerView recyclerView);
    }

    public ShaderItemDecoration(Context context, int i) {
        a(context);
        this.c = i;
    }

    private void a(Context context) {
        this.d = (int) TypedValue.applyDimension(1, 88.0f, context.getResources().getDisplayMetrics());
        this.e = this.d;
        this.f = (int) TypedValue.applyDimension(1, 66.0f, context.getResources().getDisplayMetrics());
        this.g = this.f;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if ((this.c & 2) != 0) {
            b(canvas, recyclerView);
        }
        if ((this.c & 1) != 0) {
            a(canvas, recyclerView);
        }
        if ((this.c & 4) != 0) {
            c(canvas, recyclerView);
        }
        if ((this.c & 8) != 0) {
            d(canvas, recyclerView);
        }
    }

    private void a(Canvas canvas, RecyclerView recyclerView) {
        Paint paint = new Paint(1);
        paint.setShader(a(recyclerView));
        canvas.drawRect(0.0f, 0.0f, recyclerView.getWidth(), this.d, paint);
    }

    private Shader a(RecyclerView recyclerView) {
        if (this.h == null) {
            ShaderCreator shaderCreator = this.l;
            if (shaderCreator != null) {
                this.h = shaderCreator.createShader(recyclerView);
            } else {
                this.h = new LinearGradient(0.0f, 0.0f, 0.0f, this.d, a, b, Shader.TileMode.CLAMP);
            }
        }
        return this.h;
    }

    private void b(Canvas canvas, RecyclerView recyclerView) {
        Paint paint = new Paint(1);
        paint.setShader(b(recyclerView));
        canvas.drawRect(0.0f, recyclerView.getHeight() - this.e, recyclerView.getWidth(), recyclerView.getHeight(), paint);
    }

    private Shader b(RecyclerView recyclerView) {
        if (this.i == null) {
            ShaderCreator shaderCreator = this.m;
            if (shaderCreator != null) {
                this.i = shaderCreator.createShader(recyclerView);
            } else {
                this.i = new LinearGradient(0.0f, recyclerView.getHeight(), 0.0f, recyclerView.getHeight() - this.e, a, b, Shader.TileMode.CLAMP);
            }
        }
        return this.i;
    }

    private void c(Canvas canvas, RecyclerView recyclerView) {
        Paint paint = new Paint(1);
        paint.setShader(c(recyclerView));
        canvas.drawRect(0.0f, 0.0f, this.f, recyclerView.getHeight(), paint);
    }

    private Shader c(RecyclerView recyclerView) {
        if (this.j == null) {
            ShaderCreator shaderCreator = this.n;
            if (shaderCreator != null) {
                this.j = shaderCreator.createShader(recyclerView);
            } else {
                this.j = new LinearGradient(0.0f, 0.0f, this.f, 0.0f, a, b, Shader.TileMode.CLAMP);
            }
        }
        return this.j;
    }

    private void d(Canvas canvas, RecyclerView recyclerView) {
        Paint paint = new Paint(1);
        paint.setShader(d(recyclerView));
        canvas.drawRect(recyclerView.getWidth() - this.g, 0.0f, recyclerView.getWidth(), recyclerView.getHeight(), paint);
    }

    private Shader d(RecyclerView recyclerView) {
        if (this.k == null) {
            ShaderCreator shaderCreator = this.o;
            if (shaderCreator != null) {
                this.k = shaderCreator.createShader(recyclerView);
            } else {
                this.k = new LinearGradient(recyclerView.getWidth(), 0.0f, recyclerView.getWidth() - this.g, 0.0f, a, b, Shader.TileMode.CLAMP);
            }
        }
        return this.k;
    }

    public void setShaderBottomDistance(int i) {
        this.e = i;
    }

    public void setShaderTopDistance(int i) {
        this.d = i;
    }

    public void setShaderLeftDistance(int i) {
        this.f = i;
    }

    public void setShaderRightDistance(int i) {
        this.g = i;
    }

    public void registerTopShaderCreator(ShaderCreator shaderCreator) {
        this.l = shaderCreator;
    }

    public void registerBottomShaderCreator(ShaderCreator shaderCreator) {
        this.m = shaderCreator;
    }

    public void registerLeftShaderCreator(ShaderCreator shaderCreator) {
        this.n = shaderCreator;
    }

    public void registerRightShaderCreator(ShaderCreator shaderCreator) {
        this.o = shaderCreator;
    }
}
