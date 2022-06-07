package androidx.databinding.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.annotation.RestrictTo;
import androidx.databinding.ObservableList;
import java.util.List;

/* compiled from: ObservableListAdapter.java */
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
class a<T> extends BaseAdapter {
    private List<T> a;
    private ObservableList.OnListChangedCallback b;
    private final Context c;
    private final int d;
    private final int e;
    private final int f;
    private final LayoutInflater g;

    @Override // android.widget.Adapter
    public long getItemId(int i) {
        return i;
    }

    public a(Context context, List<T> list, int i, int i2, int i3) {
        this.c = context;
        this.e = i;
        this.d = i2;
        this.f = i3;
        this.g = i == 0 ? null : (LayoutInflater) context.getSystemService("layout_inflater");
        a(list);
    }

    public void a(List<T> list) {
        List<T> list2 = this.a;
        if (list2 != list) {
            if (list2 instanceof ObservableList) {
                ((ObservableList) list2).removeOnListChangedCallback(this.b);
            }
            this.a = list;
            if (this.a instanceof ObservableList) {
                if (this.b == null) {
                    this.b = new ObservableList.OnListChangedCallback() { // from class: androidx.databinding.adapters.a.1
                        @Override // androidx.databinding.ObservableList.OnListChangedCallback
                        public void onChanged(ObservableList observableList) {
                            a.this.notifyDataSetChanged();
                        }

                        @Override // androidx.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeChanged(ObservableList observableList, int i, int i2) {
                            a.this.notifyDataSetChanged();
                        }

                        @Override // androidx.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeInserted(ObservableList observableList, int i, int i2) {
                            a.this.notifyDataSetChanged();
                        }

                        @Override // androidx.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeMoved(ObservableList observableList, int i, int i2, int i3) {
                            a.this.notifyDataSetChanged();
                        }

                        @Override // androidx.databinding.ObservableList.OnListChangedCallback
                        public void onItemRangeRemoved(ObservableList observableList, int i, int i2) {
                            a.this.notifyDataSetChanged();
                        }
                    };
                }
                ((ObservableList) this.a).addOnListChangedCallback(this.b);
            }
            notifyDataSetChanged();
        }
    }

    @Override // android.widget.Adapter
    public int getCount() {
        return this.a.size();
    }

    @Override // android.widget.Adapter
    public Object getItem(int i) {
        return this.a.get(i);
    }

    @Override // android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        return a(this.e, i, view, viewGroup);
    }

    @Override // android.widget.BaseAdapter, android.widget.SpinnerAdapter
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return a(this.d, i, view, viewGroup);
    }

    public View a(int i, int i2, View view, ViewGroup viewGroup) {
        CharSequence charSequence;
        if (view == null) {
            if (i == 0) {
                view = new TextView(this.c);
            } else {
                view = this.g.inflate(i, viewGroup, false);
            }
        }
        int i3 = this.f;
        TextView textView = (TextView) (i3 == 0 ? view : view.findViewById(i3));
        T t = this.a.get(i2);
        if (t instanceof CharSequence) {
            charSequence = (CharSequence) t;
        } else {
            charSequence = String.valueOf(t);
        }
        textView.setText(charSequence);
        return view;
    }
}
