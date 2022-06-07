package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.R;

/* loaded from: classes.dex */
public class MockView extends View {
    private Paint a = new Paint();
    private Paint b = new Paint();
    private Paint c = new Paint();
    private boolean d = true;
    private boolean e = true;
    protected String mText = null;
    private Rect f = new Rect();
    private int g = Color.argb(255, 0, 0, 0);
    private int h = Color.argb(255, 200, 200, 200);
    private int i = Color.argb(255, 50, 50, 50);
    private int j = 4;

    public MockView(Context context) {
        super(context);
        a(context, null);
    }

    public MockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    public MockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.MockView);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == R.styleable.MockView_mock_label) {
                    this.mText = obtainStyledAttributes.getString(index);
                } else if (index == R.styleable.MockView_mock_showDiagonals) {
                    this.d = obtainStyledAttributes.getBoolean(index, this.d);
                } else if (index == R.styleable.MockView_mock_diagonalsColor) {
                    this.g = obtainStyledAttributes.getColor(index, this.g);
                } else if (index == R.styleable.MockView_mock_labelBackgroundColor) {
                    this.i = obtainStyledAttributes.getColor(index, this.i);
                } else if (index == R.styleable.MockView_mock_labelColor) {
                    this.h = obtainStyledAttributes.getColor(index, this.h);
                } else if (index == R.styleable.MockView_mock_showLabel) {
                    this.e = obtainStyledAttributes.getBoolean(index, this.e);
                }
            }
            obtainStyledAttributes.recycle();
        }
        if (this.mText == null) {
            try {
                this.mText = context.getResources().getResourceEntryName(getId());
            } catch (Exception unused) {
            }
        }
        this.a.setColor(this.g);
        this.a.setAntiAlias(true);
        this.b.setColor(this.h);
        this.b.setAntiAlias(true);
        this.c.setColor(this.i);
        this.j = Math.round(this.j * (getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        if (this.d) {
            width--;
            height--;
            float f = width;
            float f2 = height;
            canvas.drawLine(0.0f, 0.0f, f, f2, this.a);
            canvas.drawLine(0.0f, f2, f, 0.0f, this.a);
            canvas.drawLine(0.0f, 0.0f, f, 0.0f, this.a);
            canvas.drawLine(f, 0.0f, f, f2, this.a);
            canvas.drawLine(f, f2, 0.0f, f2, this.a);
            canvas.drawLine(0.0f, f2, 0.0f, 0.0f, this.a);
        }
        String str = this.mText;
        if (str != null && this.e) {
            this.b.getTextBounds(str, 0, str.length(), this.f);
            float width2 = (width - this.f.width()) / 2.0f;
            float height2 = ((height - this.f.height()) / 2.0f) + this.f.height();
            this.f.offset((int) width2, (int) height2);
            Rect rect = this.f;
            rect.set(rect.left - this.j, this.f.top - this.j, this.f.right + this.j, this.f.bottom + this.j);
            canvas.drawRect(this.f, this.c);
            canvas.drawText(this.mText, width2, height2, this.b);
        }
    }
}
