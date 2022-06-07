package androidx.cursoradapter.widget;

import android.database.Cursor;
import android.widget.Filter;

/* compiled from: CursorFilter.java */
/* loaded from: classes.dex */
class a extends Filter {
    AbstractC0013a a;

    /* compiled from: CursorFilter.java */
    /* renamed from: androidx.cursoradapter.widget.a$a  reason: collision with other inner class name */
    /* loaded from: classes.dex */
    interface AbstractC0013a {
        void changeCursor(Cursor cursor);

        CharSequence convertToString(Cursor cursor);

        Cursor getCursor();

        Cursor runQueryOnBackgroundThread(CharSequence charSequence);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public a(AbstractC0013a aVar) {
        this.a = aVar;
    }

    @Override // android.widget.Filter
    public CharSequence convertResultToString(Object obj) {
        return this.a.convertToString((Cursor) obj);
    }

    @Override // android.widget.Filter
    protected Filter.FilterResults performFiltering(CharSequence charSequence) {
        Cursor runQueryOnBackgroundThread = this.a.runQueryOnBackgroundThread(charSequence);
        Filter.FilterResults filterResults = new Filter.FilterResults();
        if (runQueryOnBackgroundThread != null) {
            filterResults.count = runQueryOnBackgroundThread.getCount();
            filterResults.values = runQueryOnBackgroundThread;
        } else {
            filterResults.count = 0;
            filterResults.values = null;
        }
        return filterResults;
    }

    @Override // android.widget.Filter
    protected void publishResults(CharSequence charSequence, Filter.FilterResults filterResults) {
        Cursor cursor = this.a.getCursor();
        if (filterResults.values != null && filterResults.values != cursor) {
            this.a.changeCursor((Cursor) filterResults.values);
        }
    }
}
