package com.google.android.exoplayer2.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionUtil;
import com.google.android.exoplayer2.util.Assertions;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: classes2.dex */
public final class TrackSelectionDialogBuilder {
    private final Context a;
    @StyleRes
    private int b;
    private final CharSequence c;
    private final MappingTrackSelector.MappedTrackInfo d;
    private final int e;
    private final DialogCallback f;
    private boolean g;
    private boolean h;
    private boolean i;
    @Nullable
    private TrackNameProvider j;
    private boolean k;
    private List<DefaultTrackSelector.SelectionOverride> l;
    @Nullable
    private Comparator<Format> m;

    /* loaded from: classes2.dex */
    public interface DialogCallback {
        void onTracksSelected(boolean z, List<DefaultTrackSelector.SelectionOverride> list);
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence charSequence, MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int i, DialogCallback dialogCallback) {
        this.a = context;
        this.c = charSequence;
        this.d = mappedTrackInfo;
        this.e = i;
        this.f = dialogCallback;
        this.l = Collections.emptyList();
    }

    public TrackSelectionDialogBuilder(Context context, CharSequence charSequence, final DefaultTrackSelector defaultTrackSelector, final int i) {
        this.a = context;
        this.c = charSequence;
        this.d = (MappingTrackSelector.MappedTrackInfo) Assertions.checkNotNull(defaultTrackSelector.getCurrentMappedTrackInfo());
        this.e = i;
        final TrackGroupArray trackGroups = this.d.getTrackGroups(i);
        final DefaultTrackSelector.Parameters parameters = defaultTrackSelector.getParameters();
        this.k = parameters.getRendererDisabled(i);
        DefaultTrackSelector.SelectionOverride selectionOverride = parameters.getSelectionOverride(i, trackGroups);
        this.l = selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride);
        this.f = new DialogCallback() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$TrackSelectionDialogBuilder$mO6SBX7YlFZsLeRE3Xyt3o_ECgE
            @Override // com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder.DialogCallback
            public final void onTracksSelected(boolean z, List list) {
                TrackSelectionDialogBuilder.a(DefaultTrackSelector.this, parameters, i, trackGroups, z, list);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(DefaultTrackSelector defaultTrackSelector, DefaultTrackSelector.Parameters parameters, int i, TrackGroupArray trackGroupArray, boolean z, List list) {
        defaultTrackSelector.setParameters(TrackSelectionUtil.updateParametersWithOverride(parameters, i, trackGroupArray, z, list.isEmpty() ? null : (DefaultTrackSelector.SelectionOverride) list.get(0)));
    }

    public TrackSelectionDialogBuilder setTheme(@StyleRes int i) {
        this.b = i;
        return this;
    }

    public TrackSelectionDialogBuilder setIsDisabled(boolean z) {
        this.k = z;
        return this;
    }

    public TrackSelectionDialogBuilder setOverride(@Nullable DefaultTrackSelector.SelectionOverride selectionOverride) {
        return setOverrides(selectionOverride == null ? Collections.emptyList() : Collections.singletonList(selectionOverride));
    }

    public TrackSelectionDialogBuilder setOverrides(List<DefaultTrackSelector.SelectionOverride> list) {
        this.l = list;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean z) {
        this.g = z;
        return this;
    }

    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean z) {
        this.h = z;
        return this;
    }

    public TrackSelectionDialogBuilder setShowDisableOption(boolean z) {
        this.i = z;
        return this;
    }

    public void setTrackFormatComparator(@Nullable Comparator<Format> comparator) {
        this.m = comparator;
    }

    public TrackSelectionDialogBuilder setTrackNameProvider(@Nullable TrackNameProvider trackNameProvider) {
        this.j = trackNameProvider;
        return this;
    }

    public Dialog build() {
        Dialog b = b();
        return b == null ? a() : b;
    }

    private Dialog a() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.a, this.b);
        View inflate = LayoutInflater.from(builder.getContext()).inflate(R.layout.exo_track_selection_dialog, (ViewGroup) null);
        return builder.setTitle(this.c).setView(inflate).setPositiveButton(17039370, a(inflate)).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
    }

    @Nullable
    private Dialog b() {
        try {
            Class<?> cls = Class.forName("androidx.appcompat.app.AlertDialog$Builder");
            Object newInstance = cls.getConstructor(Context.class, Integer.TYPE).newInstance(this.a, Integer.valueOf(this.b));
            View inflate = LayoutInflater.from((Context) cls.getMethod("getContext", new Class[0]).invoke(newInstance, new Object[0])).inflate(R.layout.exo_track_selection_dialog, (ViewGroup) null);
            DialogInterface.OnClickListener a = a(inflate);
            cls.getMethod("setTitle", CharSequence.class).invoke(newInstance, this.c);
            cls.getMethod("setView", View.class).invoke(newInstance, inflate);
            cls.getMethod("setPositiveButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(newInstance, 17039370, a);
            cls.getMethod("setNegativeButton", Integer.TYPE, DialogInterface.OnClickListener.class).invoke(newInstance, 17039360, null);
            return (Dialog) cls.getMethod("create", new Class[0]).invoke(newInstance, new Object[0]);
        } catch (ClassNotFoundException unused) {
            return null;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private DialogInterface.OnClickListener a(View view) {
        final TrackSelectionView trackSelectionView = (TrackSelectionView) view.findViewById(R.id.exo_track_selection_view);
        trackSelectionView.setAllowMultipleOverrides(this.h);
        trackSelectionView.setAllowAdaptiveSelections(this.g);
        trackSelectionView.setShowDisableOption(this.i);
        TrackNameProvider trackNameProvider = this.j;
        if (trackNameProvider != null) {
            trackSelectionView.setTrackNameProvider(trackNameProvider);
        }
        trackSelectionView.init(this.d, this.e, this.k, this.l, this.m, null);
        return new DialogInterface.OnClickListener() { // from class: com.google.android.exoplayer2.ui.-$$Lambda$TrackSelectionDialogBuilder$CJqUOiuuQCe8jHN5Lw_vX2WJQ_g
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                TrackSelectionDialogBuilder.this.a(trackSelectionView, dialogInterface, i);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(TrackSelectionView trackSelectionView, DialogInterface dialogInterface, int i) {
        this.f.onTracksSelected(trackSelectionView.getIsDisabled(), trackSelectionView.getOverrides());
    }
}
