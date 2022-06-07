package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RestrictTo;

/* loaded from: classes.dex */
public class SimpleCursorAdapter extends ResourceCursorAdapter {
    String[] a;
    private int b = -1;
    private CursorToStringConverter c;
    private ViewBinder d;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected int[] mFrom;
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    protected int[] mTo;

    /* loaded from: classes.dex */
    public interface CursorToStringConverter {
        CharSequence convertToString(Cursor cursor);
    }

    /* loaded from: classes.dex */
    public interface ViewBinder {
        boolean setViewValue(View view, Cursor cursor, int i);
    }

    @Deprecated
    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr) {
        super(context, i, cursor);
        this.mTo = iArr;
        this.a = strArr;
        a(cursor, strArr);
    }

    public SimpleCursorAdapter(Context context, int i, Cursor cursor, String[] strArr, int[] iArr, int i2) {
        super(context, i, cursor, i2);
        this.mTo = iArr;
        this.a = strArr;
        a(cursor, strArr);
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public void bindView(View view, Context context, Cursor cursor) {
        ViewBinder viewBinder = this.d;
        int[] iArr = this.mTo;
        int length = iArr.length;
        int[] iArr2 = this.mFrom;
        for (int i = 0; i < length; i++) {
            View findViewById = view.findViewById(iArr[i]);
            if (findViewById != null) {
                if (viewBinder != null ? viewBinder.setViewValue(findViewById, cursor, iArr2[i]) : false) {
                    continue;
                } else {
                    String string = cursor.getString(iArr2[i]);
                    if (string == null) {
                        string = "";
                    }
                    if (findViewById instanceof TextView) {
                        setViewText((TextView) findViewById, string);
                    } else if (findViewById instanceof ImageView) {
                        setViewImage((ImageView) findViewById, string);
                    } else {
                        throw new IllegalStateException(findViewById.getClass().getName() + " is not a  view that can be bounds by this SimpleCursorAdapter");
                    }
                }
            }
        }
    }

    public ViewBinder getViewBinder() {
        return this.d;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.d = viewBinder;
    }

    public void setViewImage(ImageView imageView, String str) {
        try {
            imageView.setImageResource(Integer.parseInt(str));
        } catch (NumberFormatException unused) {
            imageView.setImageURI(Uri.parse(str));
        }
    }

    public void setViewText(TextView textView, String str) {
        textView.setText(str);
    }

    public int getStringConversionColumn() {
        return this.b;
    }

    public void setStringConversionColumn(int i) {
        this.b = i;
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.c;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.c = cursorToStringConverter;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter, androidx.cursoradapter.widget.a.AbstractC0013a
    public CharSequence convertToString(Cursor cursor) {
        CursorToStringConverter cursorToStringConverter = this.c;
        if (cursorToStringConverter != null) {
            return cursorToStringConverter.convertToString(cursor);
        }
        int i = this.b;
        if (i > -1) {
            return cursor.getString(i);
        }
        return super.convertToString(cursor);
    }

    private void a(Cursor cursor, String[] strArr) {
        if (cursor != null) {
            int length = strArr.length;
            int[] iArr = this.mFrom;
            if (iArr == null || iArr.length != length) {
                this.mFrom = new int[length];
            }
            for (int i = 0; i < length; i++) {
                this.mFrom[i] = cursor.getColumnIndexOrThrow(strArr[i]);
            }
            return;
        }
        this.mFrom = null;
    }

    @Override // androidx.cursoradapter.widget.CursorAdapter
    public Cursor swapCursor(Cursor cursor) {
        a(cursor, this.a);
        return super.swapCursor(cursor);
    }

    public void changeCursorAndColumns(Cursor cursor, String[] strArr, int[] iArr) {
        this.a = strArr;
        this.mTo = iArr;
        a(cursor, this.a);
        super.changeCursor(cursor);
    }
}
