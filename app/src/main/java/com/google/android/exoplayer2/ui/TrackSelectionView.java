package com.google.android.exoplayer2.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import androidx.annotation.AttrRes;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.android.exoplayer2.util.Assertions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/* loaded from: classes2.dex */
public class TrackSelectionView extends LinearLayout {
    private final int a;
    private final LayoutInflater b;
    private final CheckedTextView c;
    private final CheckedTextView d;
    private final a e;
    private final SparseArray<DefaultTrackSelector.SelectionOverride> f;
    private boolean g;
    private boolean h;
    private TrackNameProvider i;
    private CheckedTextView[][] j;
    private MappingTrackSelector.MappedTrackInfo k;
    private int l;
    private TrackGroupArray m;
    private boolean n;
    @Nullable
    private Comparator<b> o;
    @Nullable
    private TrackSelectionListener p;

    /* loaded from: classes2.dex */
    public interface TrackSelectionListener {
        void onTrackSelectionChanged(boolean z, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionView(Context context) {
        this(context, null);
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TrackSelectionView(Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        super(context, attributeSet, i);
        setOrientation(1);
        this.f = new SparseArray<>();
        setSaveFromParentEnabled(false);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{16843534});
        this.a = obtainStyledAttributes.getResourceId(0, 0);
        obtainStyledAttributes.recycle();
        this.b = LayoutInflater.from(context);
        this.e = new a();
        this.i = new DefaultTrackNameProvider(getResources());
        this.m = TrackGroupArray.EMPTY;
        this.c = (CheckedTextView) this.b.inflate(17367055, (ViewGroup) this, false);
        this.c.setBackgroundResource(this.a);
        this.c.setText(R.string.exo_track_selection_none);
        this.c.setEnabled(false);
        this.c.setFocusable(true);
        this.c.setOnClickListener(this.e);
        this.c.setVisibility(8);
        addView(this.c);
        addView(this.b.inflate(R.layout.exo_list_divider, (ViewGroup) this, false));
        this.d = (CheckedTextView) this.b.inflate(17367055, (ViewGroup) this, false);
        this.d.setBackgroundResource(this.a);
        this.d.setText(R.string.exo_track_selection_auto);
        this.d.setEnabled(false);
        this.d.setFocusable(true);
        this.d.setOnClickListener(this.e);
        addView(this.d);
    }

    public void setAllowAdaptiveSelections(boolean z) {
        if (this.g != z) {
            this.g = z;
            a();
        }
    }

    public void setAllowMultipleOverrides(boolean z) {
        if (this.h != z) {
            this.h = z;
            if (!z && this.f.size() > 1) {
                for (int size = this.f.size() - 1; size > 0; size--) {
                    this.f.remove(size);
                }
            }
            a();
        }
    }

    public void setShowDisableOption(boolean z) {
        this.c.setVisibility(z ? 0 : 8);
    }

    public void setTrackNameProvider(TrackNameProvider trackNameProvider) {
        this.i = (TrackNameProvider) Assertions.checkNotNull(trackNameProvider);
        a();
    }

    public void init(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i, boolean z, List<DefaultTrackSelector.SelectionOverride> list, @Nullable final Comparator<Format> comparator, @Nullable TrackSelectionListener trackSelectionListener) {
        this.k = mappedTrackInfo;
        this.l = i;
        this.n = z;
        this.o = comparator == null ? null : new Comparator() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$TrackSelectionView$FWRVAHLSjR5QlmxbRZJoKK3vmss
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                int a2;
                a2 = TrackSelectionView.a(comparator, (TrackSelectionView.b) obj, (TrackSelectionView.b) obj2);
                return a2;
            }
        };
        this.p = trackSelectionListener;
        int size = this.h ? list.size() : Math.min(list.size(), 1);
        for (int i2 = 0; i2 < size; i2++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = list.get(i2);
            this.f.put(selectionOverride.groupIndex, selectionOverride);
        }
        a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(Comparator comparator, b bVar, b bVar2) {
        return comparator.compare(bVar.c, bVar2.c);
    }

    public boolean getIsDisabled() {
        return this.n;
    }

    public List<DefaultTrackSelector.SelectionOverride> getOverrides() {
        ArrayList arrayList = new ArrayList(this.f.size());
        for (int i = 0; i < this.f.size(); i++) {
            arrayList.add(this.f.valueAt(i));
        }
        return arrayList;
    }

    private void a() {
        for (int childCount = getChildCount() - 1; childCount >= 3; childCount--) {
            removeViewAt(childCount);
        }
        if (this.k == null) {
            this.c.setEnabled(false);
            this.d.setEnabled(false);
            return;
        }
        this.c.setEnabled(true);
        this.d.setEnabled(true);
        this.m = this.k.getTrackGroups(this.l);
        this.j = new CheckedTextView[this.m.length];
        boolean e = e();
        for (int i = 0; i < this.m.length; i++) {
            TrackGroup trackGroup = this.m.get(i);
            boolean a2 = a(i);
            this.j[i] = new CheckedTextView[trackGroup.length];
            b[] bVarArr = new b[trackGroup.length];
            for (int i2 = 0; i2 < trackGroup.length; i2++) {
                bVarArr[i2] = new b(i, i2, trackGroup.getFormat(i2));
            }
            Comparator<b> comparator = this.o;
            if (comparator != null) {
                Arrays.sort(bVarArr, comparator);
            }
            for (int i3 = 0; i3 < bVarArr.length; i3++) {
                if (i3 == 0) {
                    addView(this.b.inflate(R.layout.exo_list_divider, (ViewGroup) this, false));
                }
                CheckedTextView checkedTextView = (CheckedTextView) this.b.inflate((a2 || e) ? 17367056 : 17367055, (ViewGroup) this, false);
                checkedTextView.setBackgroundResource(this.a);
                checkedTextView.setText(this.i.getTrackName(bVarArr[i3].c));
                checkedTextView.setTag(bVarArr[i3]);
                if (this.k.getTrackSupport(this.l, i, i3) == 4) {
                    checkedTextView.setFocusable(true);
                    checkedTextView.setOnClickListener(this.e);
                } else {
                    checkedTextView.setFocusable(false);
                    checkedTextView.setEnabled(false);
                }
                this.j[i][i3] = checkedTextView;
                addView(checkedTextView);
            }
        }
        b();
    }

    private void b() {
        this.c.setChecked(this.n);
        this.d.setChecked(!this.n && this.f.size() == 0);
        for (int i = 0; i < this.j.length; i++) {
            DefaultTrackSelector.SelectionOverride selectionOverride = this.f.get(i);
            int i2 = 0;
            while (true) {
                CheckedTextView[][] checkedTextViewArr = this.j;
                if (i2 < checkedTextViewArr[i].length) {
                    if (selectionOverride != null) {
                        this.j[i][i2].setChecked(selectionOverride.containsTrack(((b) Assertions.checkNotNull(checkedTextViewArr[i][i2].getTag())).b));
                    } else {
                        checkedTextViewArr[i][i2].setChecked(false);
                    }
                    i2++;
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(View view) {
        if (view == this.c) {
            c();
        } else if (view == this.d) {
            d();
        } else {
            b(view);
        }
        b();
        TrackSelectionListener trackSelectionListener = this.p;
        if (trackSelectionListener != null) {
            trackSelectionListener.onTrackSelectionChanged(getIsDisabled(), getOverrides());
        }
    }

    private void c() {
        this.n = true;
        this.f.clear();
    }

    private void d() {
        this.n = false;
        this.f.clear();
    }

    private void b(View view) {
        this.n = false;
        b bVar = (b) Assertions.checkNotNull(view.getTag());
        int i = bVar.a;
        int i2 = bVar.b;
        DefaultTrackSelector.SelectionOverride selectionOverride = this.f.get(i);
        Assertions.checkNotNull(this.k);
        if (selectionOverride == null) {
            if (!this.h && this.f.size() > 0) {
                this.f.clear();
            }
            this.f.put(i, new DefaultTrackSelector.SelectionOverride(i, i2));
            return;
        }
        int i3 = selectionOverride.length;
        int[] iArr = selectionOverride.tracks;
        boolean isChecked = ((CheckedTextView) view).isChecked();
        boolean a2 = a(i);
        boolean z = a2 || e();
        if (!isChecked || !z) {
            if (isChecked) {
                return;
            }
            if (a2) {
                this.f.put(i, new DefaultTrackSelector.SelectionOverride(i, a(iArr, i2)));
            } else {
                this.f.put(i, new DefaultTrackSelector.SelectionOverride(i, i2));
            }
        } else if (i3 == 1) {
            this.f.remove(i);
        } else {
            this.f.put(i, new DefaultTrackSelector.SelectionOverride(i, b(iArr, i2)));
        }
    }

    @RequiresNonNull({"mappedTrackInfo"})
    private boolean a(int i) {
        return this.g && this.m.get(i).length > 1 && this.k.getAdaptiveSupport(this.l, i, false) != 0;
    }

    private boolean e() {
        return this.h && this.m.length > 1;
    }

    private static int[] a(int[] iArr, int i) {
        int[] copyOf = Arrays.copyOf(iArr, iArr.length + 1);
        copyOf[copyOf.length - 1] = i;
        return copyOf;
    }

    private static int[] b(int[] iArr, int i) {
        int[] iArr2 = new int[iArr.length - 1];
        int i2 = 0;
        for (int i3 : iArr) {
            if (i3 != i) {
                i2++;
                iArr2[i2] = i3;
            }
        }
        return iArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public class a implements View.OnClickListener {
        private a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            TrackSelectionView.this.a(view);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class b {
        public final int a;
        public final int b;
        public final Format c;

        public b(int i, int i2, Format format) {
            this.a = i;
            this.b = i2;
            this.c = format;
        }
    }
}
