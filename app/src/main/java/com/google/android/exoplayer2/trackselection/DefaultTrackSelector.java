package com.google.android.exoplayer2.trackselection;

import android.content.Context;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.RendererCapabilities;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;
import com.xiaomi.mipush.sdk.Constants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes2.dex */
public class DefaultTrackSelector extends MappingTrackSelector {
    private static final int[] a = new int[0];
    private static final Ordering<Integer> b = Ordering.from($$Lambda$DefaultTrackSelector$isJDnq31Q6GtcD8_OfjuchIZCoI.INSTANCE);
    private static final Ordering<Integer> c = Ordering.from($$Lambda$DefaultTrackSelector$Qyvtk3Bnctw92KTb46IP4kfoZw.INSTANCE);
    private final ExoTrackSelection.Factory d;
    private final AtomicReference<Parameters> e;

    public static /* synthetic */ int a(Integer num, Integer num2) {
        return 0;
    }

    /* loaded from: classes2.dex */
    public static final class ParametersBuilder extends TrackSelectionParameters.Builder {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;
        private int h;
        private boolean i;
        private boolean j;
        private boolean k;
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> l;
        private final SparseBooleanArray m;

        @Deprecated
        public ParametersBuilder() {
            this.l = new SparseArray<>();
            this.m = new SparseBooleanArray();
            a();
        }

        public ParametersBuilder(Context context) {
            super(context);
            this.l = new SparseArray<>();
            this.m = new SparseBooleanArray();
            a();
        }

        private ParametersBuilder(Parameters parameters) {
            super(parameters);
            this.h = parameters.disabledTextTrackSelectionFlags;
            this.a = parameters.exceedVideoConstraintsIfNecessary;
            this.b = parameters.allowVideoMixedMimeTypeAdaptiveness;
            this.c = parameters.allowVideoNonSeamlessAdaptiveness;
            this.d = parameters.exceedAudioConstraintsIfNecessary;
            this.e = parameters.allowAudioMixedMimeTypeAdaptiveness;
            this.f = parameters.allowAudioMixedSampleRateAdaptiveness;
            this.g = parameters.allowAudioMixedChannelCountAdaptiveness;
            this.i = parameters.exceedRendererCapabilitiesIfNecessary;
            this.j = parameters.tunnelingEnabled;
            this.k = parameters.allowMultipleAdaptiveSelections;
            this.l = a(parameters.a);
            this.m = parameters.b.clone();
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoSizeSd() {
            super.setMaxVideoSizeSd();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder clearVideoSizeConstraints() {
            super.clearVideoSizeConstraints();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoSize(int i, int i2) {
            super.setMaxVideoSize(i, i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoFrameRate(int i) {
            super.setMaxVideoFrameRate(i);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxVideoBitrate(int i) {
            super.setMaxVideoBitrate(i);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoSize(int i, int i2) {
            super.setMinVideoSize(i, i2);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoFrameRate(int i) {
            super.setMinVideoFrameRate(i);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMinVideoBitrate(int i) {
            super.setMinVideoBitrate(i);
            return this;
        }

        public ParametersBuilder setExceedVideoConstraintsIfNecessary(boolean z) {
            this.a = z;
            return this;
        }

        public ParametersBuilder setAllowVideoMixedMimeTypeAdaptiveness(boolean z) {
            this.b = z;
            return this;
        }

        public ParametersBuilder setAllowVideoNonSeamlessAdaptiveness(boolean z) {
            this.c = z;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setViewportSizeToPhysicalDisplaySize(Context context, boolean z) {
            super.setViewportSizeToPhysicalDisplaySize(context, z);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder clearViewportSizeConstraints() {
            super.clearViewportSizeConstraints();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setViewportSize(int i, int i2, boolean z) {
            super.setViewportSize(i, i2, z);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredVideoMimeType(@Nullable String str) {
            super.setPreferredVideoMimeType(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredVideoMimeTypes(String... strArr) {
            super.setPreferredVideoMimeTypes(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioLanguage(@Nullable String str) {
            super.setPreferredAudioLanguage(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioLanguages(String... strArr) {
            super.setPreferredAudioLanguages(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioRoleFlags(int i) {
            super.setPreferredAudioRoleFlags(i);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxAudioChannelCount(int i) {
            super.setMaxAudioChannelCount(i);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setMaxAudioBitrate(int i) {
            super.setMaxAudioBitrate(i);
            return this;
        }

        public ParametersBuilder setExceedAudioConstraintsIfNecessary(boolean z) {
            this.d = z;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedMimeTypeAdaptiveness(boolean z) {
            this.e = z;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedSampleRateAdaptiveness(boolean z) {
            this.f = z;
            return this;
        }

        public ParametersBuilder setAllowAudioMixedChannelCountAdaptiveness(boolean z) {
            this.g = z;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioMimeType(@Nullable String str) {
            super.setPreferredAudioMimeType(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredAudioMimeTypes(String... strArr) {
            super.setPreferredAudioMimeTypes(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(Context context) {
            super.setPreferredTextLanguageAndRoleFlagsToCaptioningManagerSettings(context);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguage(@Nullable String str) {
            super.setPreferredTextLanguage(str);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextLanguages(String... strArr) {
            super.setPreferredTextLanguages(strArr);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setPreferredTextRoleFlags(int i) {
            super.setPreferredTextRoleFlags(i);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setSelectUndeterminedTextLanguage(boolean z) {
            super.setSelectUndeterminedTextLanguage(z);
            return this;
        }

        public ParametersBuilder setDisabledTextTrackSelectionFlags(int i) {
            this.h = i;
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setForceLowestBitrate(boolean z) {
            super.setForceLowestBitrate(z);
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public ParametersBuilder setForceHighestSupportedBitrate(boolean z) {
            super.setForceHighestSupportedBitrate(z);
            return this;
        }

        public ParametersBuilder setExceedRendererCapabilitiesIfNecessary(boolean z) {
            this.i = z;
            return this;
        }

        public ParametersBuilder setTunnelingEnabled(boolean z) {
            this.j = z;
            return this;
        }

        public ParametersBuilder setAllowMultipleAdaptiveSelections(boolean z) {
            this.k = z;
            return this;
        }

        public final ParametersBuilder setRendererDisabled(int i, boolean z) {
            if (this.m.get(i) == z) {
                return this;
            }
            if (z) {
                this.m.put(i, true);
            } else {
                this.m.delete(i);
            }
            return this;
        }

        public final ParametersBuilder setSelectionOverride(int i, TrackGroupArray trackGroupArray, @Nullable SelectionOverride selectionOverride) {
            Map<TrackGroupArray, SelectionOverride> map = this.l.get(i);
            if (map == null) {
                map = new HashMap<>();
                this.l.put(i, map);
            }
            if (map.containsKey(trackGroupArray) && Util.areEqual(map.get(trackGroupArray), selectionOverride)) {
                return this;
            }
            map.put(trackGroupArray, selectionOverride);
            return this;
        }

        public final ParametersBuilder clearSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map<TrackGroupArray, SelectionOverride> map = this.l.get(i);
            if (map == null || !map.containsKey(trackGroupArray)) {
                return this;
            }
            map.remove(trackGroupArray);
            if (map.isEmpty()) {
                this.l.remove(i);
            }
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides(int i) {
            Map<TrackGroupArray, SelectionOverride> map = this.l.get(i);
            if (map == null || map.isEmpty()) {
                return this;
            }
            this.l.remove(i);
            return this;
        }

        public final ParametersBuilder clearSelectionOverrides() {
            if (this.l.size() == 0) {
                return this;
            }
            this.l.clear();
            return this;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters.Builder
        public Parameters build() {
            return new Parameters(this);
        }

        private void a() {
            this.a = true;
            this.b = false;
            this.c = true;
            this.d = true;
            this.e = false;
            this.f = false;
            this.g = false;
            this.h = 0;
            this.i = true;
            this.j = false;
            this.k = true;
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> a(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray) {
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2 = new SparseArray<>();
            for (int i = 0; i < sparseArray.size(); i++) {
                sparseArray2.put(sparseArray.keyAt(i), new HashMap(sparseArray.valueAt(i)));
            }
            return sparseArray2;
        }
    }

    /* loaded from: classes2.dex */
    public static final class Parameters extends TrackSelectionParameters implements Parcelable {
        private final SparseArray<Map<TrackGroupArray, SelectionOverride>> a;
        public final boolean allowAudioMixedChannelCountAdaptiveness;
        public final boolean allowAudioMixedMimeTypeAdaptiveness;
        public final boolean allowAudioMixedSampleRateAdaptiveness;
        public final boolean allowMultipleAdaptiveSelections;
        public final boolean allowVideoMixedMimeTypeAdaptiveness;
        public final boolean allowVideoNonSeamlessAdaptiveness;
        private final SparseBooleanArray b;
        public final int disabledTextTrackSelectionFlags;
        public final boolean exceedAudioConstraintsIfNecessary;
        public final boolean exceedRendererCapabilitiesIfNecessary;
        public final boolean exceedVideoConstraintsIfNecessary;
        public final boolean tunnelingEnabled;
        public static final Parameters DEFAULT_WITHOUT_CONTEXT = new ParametersBuilder().build();
        @Deprecated
        public static final Parameters DEFAULT = DEFAULT_WITHOUT_CONTEXT;
        public static final Parcelable.Creator<Parameters> CREATOR = new Parcelable.Creator<Parameters>() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.1
            /* renamed from: a */
            public Parameters createFromParcel(Parcel parcel) {
                return new Parameters(parcel);
            }

            /* renamed from: a */
            public Parameters[] newArray(int i) {
                return new Parameters[i];
            }
        };

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters, android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public static Parameters getDefaults(Context context) {
            return new ParametersBuilder(context).build();
        }

        private Parameters(ParametersBuilder parametersBuilder) {
            super(parametersBuilder);
            this.exceedVideoConstraintsIfNecessary = parametersBuilder.a;
            this.allowVideoMixedMimeTypeAdaptiveness = parametersBuilder.b;
            this.allowVideoNonSeamlessAdaptiveness = parametersBuilder.c;
            this.exceedAudioConstraintsIfNecessary = parametersBuilder.d;
            this.allowAudioMixedMimeTypeAdaptiveness = parametersBuilder.e;
            this.allowAudioMixedSampleRateAdaptiveness = parametersBuilder.f;
            this.allowAudioMixedChannelCountAdaptiveness = parametersBuilder.g;
            this.disabledTextTrackSelectionFlags = parametersBuilder.h;
            this.exceedRendererCapabilitiesIfNecessary = parametersBuilder.i;
            this.tunnelingEnabled = parametersBuilder.j;
            this.allowMultipleAdaptiveSelections = parametersBuilder.k;
            this.a = parametersBuilder.l;
            this.b = parametersBuilder.m;
        }

        Parameters(Parcel parcel) {
            super(parcel);
            this.exceedVideoConstraintsIfNecessary = Util.readBoolean(parcel);
            this.allowVideoMixedMimeTypeAdaptiveness = Util.readBoolean(parcel);
            this.allowVideoNonSeamlessAdaptiveness = Util.readBoolean(parcel);
            this.exceedAudioConstraintsIfNecessary = Util.readBoolean(parcel);
            this.allowAudioMixedMimeTypeAdaptiveness = Util.readBoolean(parcel);
            this.allowAudioMixedSampleRateAdaptiveness = Util.readBoolean(parcel);
            this.allowAudioMixedChannelCountAdaptiveness = Util.readBoolean(parcel);
            this.disabledTextTrackSelectionFlags = parcel.readInt();
            this.exceedRendererCapabilitiesIfNecessary = Util.readBoolean(parcel);
            this.tunnelingEnabled = Util.readBoolean(parcel);
            this.allowMultipleAdaptiveSelections = Util.readBoolean(parcel);
            this.a = a(parcel);
            this.b = (SparseBooleanArray) Util.castNonNull(parcel.readSparseBooleanArray());
        }

        public final boolean getRendererDisabled(int i) {
            return this.b.get(i);
        }

        public final boolean hasSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map<TrackGroupArray, SelectionOverride> map = this.a.get(i);
            return map != null && map.containsKey(trackGroupArray);
        }

        @Nullable
        public final SelectionOverride getSelectionOverride(int i, TrackGroupArray trackGroupArray) {
            Map<TrackGroupArray, SelectionOverride> map = this.a.get(i);
            if (map != null) {
                return map.get(trackGroupArray);
            }
            return null;
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public ParametersBuilder buildUpon() {
            return new ParametersBuilder(this);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Parameters parameters = (Parameters) obj;
            return super.equals(parameters) && this.exceedVideoConstraintsIfNecessary == parameters.exceedVideoConstraintsIfNecessary && this.allowVideoMixedMimeTypeAdaptiveness == parameters.allowVideoMixedMimeTypeAdaptiveness && this.allowVideoNonSeamlessAdaptiveness == parameters.allowVideoNonSeamlessAdaptiveness && this.exceedAudioConstraintsIfNecessary == parameters.exceedAudioConstraintsIfNecessary && this.allowAudioMixedMimeTypeAdaptiveness == parameters.allowAudioMixedMimeTypeAdaptiveness && this.allowAudioMixedSampleRateAdaptiveness == parameters.allowAudioMixedSampleRateAdaptiveness && this.allowAudioMixedChannelCountAdaptiveness == parameters.allowAudioMixedChannelCountAdaptiveness && this.disabledTextTrackSelectionFlags == parameters.disabledTextTrackSelectionFlags && this.exceedRendererCapabilitiesIfNecessary == parameters.exceedRendererCapabilitiesIfNecessary && this.tunnelingEnabled == parameters.tunnelingEnabled && this.allowMultipleAdaptiveSelections == parameters.allowMultipleAdaptiveSelections && a(this.b, parameters.b) && a(this.a, parameters.a);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters
        public int hashCode() {
            return ((((((((((((((((((((((super.hashCode() + 31) * 31) + (this.exceedVideoConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowVideoMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowVideoNonSeamlessAdaptiveness ? 1 : 0)) * 31) + (this.exceedAudioConstraintsIfNecessary ? 1 : 0)) * 31) + (this.allowAudioMixedMimeTypeAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedSampleRateAdaptiveness ? 1 : 0)) * 31) + (this.allowAudioMixedChannelCountAdaptiveness ? 1 : 0)) * 31) + this.disabledTextTrackSelectionFlags) * 31) + (this.exceedRendererCapabilitiesIfNecessary ? 1 : 0)) * 31) + (this.tunnelingEnabled ? 1 : 0)) * 31) + (this.allowMultipleAdaptiveSelections ? 1 : 0);
        }

        @Override // com.google.android.exoplayer2.trackselection.TrackSelectionParameters, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            Util.writeBoolean(parcel, this.exceedVideoConstraintsIfNecessary);
            Util.writeBoolean(parcel, this.allowVideoMixedMimeTypeAdaptiveness);
            Util.writeBoolean(parcel, this.allowVideoNonSeamlessAdaptiveness);
            Util.writeBoolean(parcel, this.exceedAudioConstraintsIfNecessary);
            Util.writeBoolean(parcel, this.allowAudioMixedMimeTypeAdaptiveness);
            Util.writeBoolean(parcel, this.allowAudioMixedSampleRateAdaptiveness);
            Util.writeBoolean(parcel, this.allowAudioMixedChannelCountAdaptiveness);
            parcel.writeInt(this.disabledTextTrackSelectionFlags);
            Util.writeBoolean(parcel, this.exceedRendererCapabilitiesIfNecessary);
            Util.writeBoolean(parcel, this.tunnelingEnabled);
            Util.writeBoolean(parcel, this.allowMultipleAdaptiveSelections);
            a(parcel, this.a);
            parcel.writeSparseBooleanArray(this.b);
        }

        private static SparseArray<Map<TrackGroupArray, SelectionOverride>> a(Parcel parcel) {
            int readInt = parcel.readInt();
            SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray = new SparseArray<>(readInt);
            for (int i = 0; i < readInt; i++) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                HashMap hashMap = new HashMap(readInt3);
                for (int i2 = 0; i2 < readInt3; i2++) {
                    hashMap.put((TrackGroupArray) Assertions.checkNotNull((TrackGroupArray) parcel.readParcelable(TrackGroupArray.class.getClassLoader())), (SelectionOverride) parcel.readParcelable(SelectionOverride.class.getClassLoader()));
                }
                sparseArray.put(readInt2, hashMap);
            }
            return sparseArray;
        }

        private static void a(Parcel parcel, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray) {
            int size = sparseArray.size();
            parcel.writeInt(size);
            for (int i = 0; i < size; i++) {
                int keyAt = sparseArray.keyAt(i);
                Map<TrackGroupArray, SelectionOverride> valueAt = sparseArray.valueAt(i);
                int size2 = valueAt.size();
                parcel.writeInt(keyAt);
                parcel.writeInt(size2);
                for (Map.Entry<TrackGroupArray, SelectionOverride> entry : valueAt.entrySet()) {
                    parcel.writeParcelable(entry.getKey(), 0);
                    parcel.writeParcelable(entry.getValue(), 0);
                }
            }
        }

        private static boolean a(SparseBooleanArray sparseBooleanArray, SparseBooleanArray sparseBooleanArray2) {
            int size = sparseBooleanArray.size();
            if (sparseBooleanArray2.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if (sparseBooleanArray2.indexOfKey(sparseBooleanArray.keyAt(i)) < 0) {
                    return false;
                }
            }
            return true;
        }

        private static boolean a(SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray, SparseArray<Map<TrackGroupArray, SelectionOverride>> sparseArray2) {
            int size = sparseArray.size();
            if (sparseArray2.size() != size) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                int indexOfKey = sparseArray2.indexOfKey(sparseArray.keyAt(i));
                if (indexOfKey < 0 || !a(sparseArray.valueAt(i), sparseArray2.valueAt(indexOfKey))) {
                    return false;
                }
            }
            return true;
        }

        /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        private static boolean a(java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride> r4, java.util.Map<com.google.android.exoplayer2.source.TrackGroupArray, com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride> r5) {
            /*
                int r0 = r4.size()
                int r1 = r5.size()
                r2 = 0
                if (r1 == r0) goto L_0x000c
                return r2
            L_0x000c:
                java.util.Set r4 = r4.entrySet()
                java.util.Iterator r4 = r4.iterator()
            L_0x0014:
                boolean r0 = r4.hasNext()
                if (r0 == 0) goto L_0x003b
                java.lang.Object r0 = r4.next()
                java.util.Map$Entry r0 = (java.util.Map.Entry) r0
                java.lang.Object r1 = r0.getKey()
                com.google.android.exoplayer2.source.TrackGroupArray r1 = (com.google.android.exoplayer2.source.TrackGroupArray) r1
                boolean r3 = r5.containsKey(r1)
                if (r3 == 0) goto L_0x003a
                java.lang.Object r0 = r0.getValue()
                java.lang.Object r1 = r5.get(r1)
                boolean r0 = com.google.android.exoplayer2.util.Util.areEqual(r0, r1)
                if (r0 != 0) goto L_0x0014
            L_0x003a:
                return r2
            L_0x003b:
                r4 = 1
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.Parameters.a(java.util.Map, java.util.Map):boolean");
        }
    }

    /* loaded from: classes2.dex */
    public static final class SelectionOverride implements Parcelable {
        public static final Parcelable.Creator<SelectionOverride> CREATOR = new Parcelable.Creator<SelectionOverride>() { // from class: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.SelectionOverride.1
            /* renamed from: a */
            public SelectionOverride createFromParcel(Parcel parcel) {
                return new SelectionOverride(parcel);
            }

            /* renamed from: a */
            public SelectionOverride[] newArray(int i) {
                return new SelectionOverride[i];
            }
        };
        public final int groupIndex;
        public final int length;
        public final int[] tracks;
        public final int type;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public SelectionOverride(int i, int... iArr) {
            this(i, iArr, 0);
        }

        public SelectionOverride(int i, int[] iArr, int i2) {
            this.groupIndex = i;
            this.tracks = Arrays.copyOf(iArr, iArr.length);
            this.length = iArr.length;
            this.type = i2;
            Arrays.sort(this.tracks);
        }

        SelectionOverride(Parcel parcel) {
            this.groupIndex = parcel.readInt();
            this.length = parcel.readByte();
            this.tracks = new int[this.length];
            parcel.readIntArray(this.tracks);
            this.type = parcel.readInt();
        }

        public boolean containsTrack(int i) {
            for (int i2 : this.tracks) {
                if (i2 == i) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((this.groupIndex * 31) + Arrays.hashCode(this.tracks)) * 31) + this.type;
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            SelectionOverride selectionOverride = (SelectionOverride) obj;
            return this.groupIndex == selectionOverride.groupIndex && Arrays.equals(this.tracks, selectionOverride.tracks) && this.type == selectionOverride.type;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.groupIndex);
            parcel.writeInt(this.tracks.length);
            parcel.writeIntArray(this.tracks);
            parcel.writeInt(this.type);
        }
    }

    public static /* synthetic */ int b(Integer num, Integer num2) {
        if (num.intValue() == -1) {
            return num2.intValue() == -1 ? 0 : -1;
        }
        if (num2.intValue() == -1) {
            return 1;
        }
        return num.intValue() - num2.intValue();
    }

    @Deprecated
    public DefaultTrackSelector() {
        this(Parameters.DEFAULT_WITHOUT_CONTEXT, new AdaptiveTrackSelection.Factory());
    }

    @Deprecated
    public DefaultTrackSelector(ExoTrackSelection.Factory factory) {
        this(Parameters.DEFAULT_WITHOUT_CONTEXT, factory);
    }

    public DefaultTrackSelector(Context context) {
        this(context, new AdaptiveTrackSelection.Factory());
    }

    public DefaultTrackSelector(Context context, ExoTrackSelection.Factory factory) {
        this(Parameters.getDefaults(context), factory);
    }

    public DefaultTrackSelector(Parameters parameters, ExoTrackSelection.Factory factory) {
        this.d = factory;
        this.e = new AtomicReference<>(parameters);
    }

    public void setParameters(Parameters parameters) {
        Assertions.checkNotNull(parameters);
        if (!this.e.getAndSet(parameters).equals(parameters)) {
            invalidate();
        }
    }

    public void setParameters(ParametersBuilder parametersBuilder) {
        setParameters(parametersBuilder.build());
    }

    public Parameters getParameters() {
        return this.e.get();
    }

    public ParametersBuilder buildUponParameters() {
        return getParameters().buildUpon();
    }

    @Override // com.google.android.exoplayer2.trackselection.MappingTrackSelector
    protected final Pair<RendererConfiguration[], ExoTrackSelection[]> selectTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, MediaSource.MediaPeriodId mediaPeriodId, Timeline timeline) throws ExoPlaybackException {
        Parameters parameters = this.e.get();
        int rendererCount = mappedTrackInfo.getRendererCount();
        ExoTrackSelection.Definition[] selectAllTracks = selectAllTracks(mappedTrackInfo, iArr, iArr2, parameters);
        int i = 0;
        while (true) {
            ExoTrackSelection.Definition definition = null;
            if (i >= rendererCount) {
                break;
            }
            if (parameters.getRendererDisabled(i)) {
                selectAllTracks[i] = null;
            } else {
                TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
                if (parameters.hasSelectionOverride(i, trackGroups)) {
                    SelectionOverride selectionOverride = parameters.getSelectionOverride(i, trackGroups);
                    if (selectionOverride != null) {
                        definition = new ExoTrackSelection.Definition(trackGroups.get(selectionOverride.groupIndex), selectionOverride.tracks, selectionOverride.type);
                    }
                    selectAllTracks[i] = definition;
                }
            }
            i++;
        }
        ExoTrackSelection[] createTrackSelections = this.d.createTrackSelections(selectAllTracks, getBandwidthMeter(), mediaPeriodId, timeline);
        RendererConfiguration[] rendererConfigurationArr = new RendererConfiguration[rendererCount];
        for (int i2 = 0; i2 < rendererCount; i2++) {
            rendererConfigurationArr[i2] = !parameters.getRendererDisabled(i2) && (mappedTrackInfo.getRendererType(i2) == 7 || createTrackSelections[i2] != null) ? RendererConfiguration.DEFAULT : null;
        }
        if (parameters.tunnelingEnabled) {
            a(mappedTrackInfo, iArr, rendererConfigurationArr, createTrackSelections);
        }
        return Pair.create(rendererConfigurationArr, createTrackSelections);
    }

    protected ExoTrackSelection.Definition[] selectAllTracks(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] iArr, int[] iArr2, Parameters parameters) throws ExoPlaybackException {
        int i;
        int i2;
        int rendererCount = mappedTrackInfo.getRendererCount();
        ExoTrackSelection.Definition[] definitionArr = new ExoTrackSelection.Definition[rendererCount];
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        while (true) {
            i = true;
            if (i3 >= rendererCount) {
                break;
            }
            if (2 == mappedTrackInfo.getRendererType(i3)) {
                if (!z) {
                    definitionArr[i3] = selectVideoTrack(mappedTrackInfo.getTrackGroups(i3), iArr[i3], iArr2[i3], parameters, true);
                    z = definitionArr[i3] != null;
                }
                if (mappedTrackInfo.getTrackGroups(i3).length <= 0) {
                    i = false;
                }
                z2 |= i;
            }
            i3++;
        }
        int i4 = -1;
        AudioTrackScore audioTrackScore = null;
        String str = null;
        int i5 = 0;
        while (i5 < rendererCount) {
            if (i == mappedTrackInfo.getRendererType(i5)) {
                boolean z3 = (parameters.allowMultipleAdaptiveSelections || !z2) ? i : false;
                i4 = i4;
                audioTrackScore = audioTrackScore;
                str = str;
                i2 = i5;
                Pair<ExoTrackSelection.Definition, AudioTrackScore> selectAudioTrack = selectAudioTrack(mappedTrackInfo.getTrackGroups(i5), iArr[i5], iArr2[i5], parameters, z3);
                if (selectAudioTrack != null && (audioTrackScore == null || ((AudioTrackScore) selectAudioTrack.second).compareTo(audioTrackScore) > 0)) {
                    if (i4 != -1) {
                        definitionArr[i4] = null;
                    }
                    ExoTrackSelection.Definition definition = (ExoTrackSelection.Definition) selectAudioTrack.first;
                    definitionArr[i2] = definition;
                    str = definition.group.getFormat(definition.tracks[0]).language;
                    audioTrackScore = (AudioTrackScore) selectAudioTrack.second;
                    i4 = i2;
                    i5 = i2 + 1;
                    i = true;
                }
            } else {
                i4 = i4;
                audioTrackScore = audioTrackScore;
                str = str;
                i2 = i5;
            }
            i5 = i2 + 1;
            i = true;
        }
        String str2 = str;
        int i6 = -1;
        TextTrackScore textTrackScore = null;
        for (int i7 = 0; i7 < rendererCount; i7++) {
            int rendererType = mappedTrackInfo.getRendererType(i7);
            switch (rendererType) {
                case 1:
                case 2:
                    str2 = str2;
                    break;
                case 3:
                    str2 = str2;
                    Pair<ExoTrackSelection.Definition, TextTrackScore> selectTextTrack = selectTextTrack(mappedTrackInfo.getTrackGroups(i7), iArr[i7], parameters, str2);
                    if (selectTextTrack != null && (textTrackScore == null || ((TextTrackScore) selectTextTrack.second).compareTo(textTrackScore) > 0)) {
                        if (i6 != -1) {
                            definitionArr[i6] = null;
                        }
                        definitionArr[i7] = (ExoTrackSelection.Definition) selectTextTrack.first;
                        textTrackScore = (TextTrackScore) selectTextTrack.second;
                        i6 = i7;
                        break;
                    }
                    break;
                default:
                    str2 = str2;
                    definitionArr[i7] = selectOtherTrack(rendererType, mappedTrackInfo.getTrackGroups(i7), iArr[i7], parameters);
                    break;
            }
        }
        return definitionArr;
    }

    @Nullable
    protected ExoTrackSelection.Definition selectVideoTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i, Parameters parameters, boolean z) throws ExoPlaybackException {
        ExoTrackSelection.Definition a2 = (parameters.forceHighestSupportedBitrate || parameters.forceLowestBitrate || !z) ? null : a(trackGroupArray, iArr, i, parameters);
        return a2 == null ? a(trackGroupArray, iArr, parameters) : a2;
    }

    @Nullable
    private static ExoTrackSelection.Definition a(TrackGroupArray trackGroupArray, int[][] iArr, int i, Parameters parameters) {
        TrackGroupArray trackGroupArray2 = trackGroupArray;
        Parameters parameters2 = parameters;
        int i2 = parameters2.allowVideoNonSeamlessAdaptiveness ? 24 : 16;
        boolean z = parameters2.allowVideoMixedMimeTypeAdaptiveness && (i & i2) != 0;
        int i3 = 0;
        while (i3 < trackGroupArray2.length) {
            TrackGroup trackGroup = trackGroupArray2.get(i3);
            int[] a2 = a(trackGroup, iArr[i3], z, i2, parameters2.maxVideoWidth, parameters2.maxVideoHeight, parameters2.maxVideoFrameRate, parameters2.maxVideoBitrate, parameters2.minVideoWidth, parameters2.minVideoHeight, parameters2.minVideoFrameRate, parameters2.minVideoBitrate, parameters2.viewportWidth, parameters2.viewportHeight, parameters2.viewportOrientationMayChange);
            if (a2.length > 0) {
                return new ExoTrackSelection.Definition(trackGroup, a2);
            }
            i3++;
            trackGroupArray2 = trackGroupArray;
            parameters2 = parameters;
        }
        return null;
    }

    private static int[] a(TrackGroup trackGroup, int[] iArr, boolean z, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, boolean z2) {
        String str;
        int i12;
        if (trackGroup.length < 2) {
            return a;
        }
        List<Integer> a2 = a(trackGroup, i10, i11, z2);
        if (a2.size() < 2) {
            return a;
        }
        if (!z) {
            HashSet hashSet = new HashSet();
            int i13 = 0;
            String str2 = null;
            for (int i14 = 0; i14 < a2.size(); i14 = i12 + 1) {
                String str3 = trackGroup.getFormat(a2.get(i14).intValue()).sampleMimeType;
                if (hashSet.add(str3)) {
                    i13 = i13;
                    i12 = i14;
                    hashSet = hashSet;
                    int a3 = a(trackGroup, iArr, i, str3, i2, i3, i4, i5, i6, i7, i8, i9, a2);
                    if (a3 > i13) {
                        i13 = a3;
                        str2 = str3;
                    }
                } else {
                    i13 = i13;
                    i12 = i14;
                    hashSet = hashSet;
                }
            }
            str = str2;
        } else {
            str = null;
        }
        b(trackGroup, iArr, i, str, i2, i3, i4, i5, i6, i7, i8, i9, a2);
        return a2.size() < 2 ? a : Ints.toArray(a2);
    }

    private static int a(TrackGroup trackGroup, int[] iArr, int i, @Nullable String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, List<Integer> list) {
        int i10 = 0;
        for (int i11 = 0; i11 < list.size(); i11++) {
            int intValue = list.get(i11).intValue();
            if (a(trackGroup.getFormat(intValue), str, iArr[intValue], i, i2, i3, i4, i5, i6, i7, i8, i9)) {
                i10++;
            }
        }
        return i10;
    }

    private static void b(TrackGroup trackGroup, int[] iArr, int i, @Nullable String str, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, List<Integer> list) {
        for (int size = list.size() - 1; size >= 0; size--) {
            int intValue = list.get(size).intValue();
            if (!a(trackGroup.getFormat(intValue), str, iArr[intValue], i, i2, i3, i4, i5, i6, i7, i8, i9)) {
                list.remove(size);
            }
        }
    }

    private static boolean a(Format format, @Nullable String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
        if ((format.roleFlags & 16384) != 0 || !isSupported(i, false) || (i & i2) == 0) {
            return false;
        }
        if (str != null && !Util.areEqual(format.sampleMimeType, str)) {
            return false;
        }
        if (format.width != -1 && (i7 > format.width || format.width > i3)) {
            return false;
        }
        if (format.height == -1 || (i8 <= format.height && format.height <= i4)) {
            return (format.frameRate == -1.0f || (((float) i9) <= format.frameRate && format.frameRate <= ((float) i5))) && format.bitrate != -1 && i10 <= format.bitrate && format.bitrate <= i6;
        }
        return false;
    }

    @Nullable
    private static ExoTrackSelection.Definition a(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) {
        int i = -1;
        TrackGroup trackGroup = null;
        VideoTrackScore videoTrackScore = null;
        int i2 = 0;
        while (i2 < trackGroupArray.length) {
            TrackGroup trackGroup2 = trackGroupArray.get(i2);
            List<Integer> a2 = a(trackGroup2, parameters.viewportWidth, parameters.viewportHeight, parameters.viewportOrientationMayChange);
            int[] iArr2 = iArr[i2];
            TrackGroup trackGroup3 = trackGroup;
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                Format format = trackGroup2.getFormat(i3);
                if ((format.roleFlags & 16384) == 0 && isSupported(iArr2[i3], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    VideoTrackScore videoTrackScore2 = new VideoTrackScore(format, parameters, iArr2[i3], a2.contains(Integer.valueOf(i3)));
                    if ((videoTrackScore2.isWithinMaxConstraints || parameters.exceedVideoConstraintsIfNecessary) && (videoTrackScore == null || videoTrackScore2.compareTo(videoTrackScore) > 0)) {
                        i = i3;
                        trackGroup3 = trackGroup2;
                        videoTrackScore = videoTrackScore2;
                    }
                }
            }
            i2++;
            trackGroup = trackGroup3;
            videoTrackScore = videoTrackScore;
        }
        if (trackGroup == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroup, i);
    }

    @Nullable
    protected Pair<ExoTrackSelection.Definition, AudioTrackScore> selectAudioTrack(TrackGroupArray trackGroupArray, int[][] iArr, int i, Parameters parameters, boolean z) throws ExoPlaybackException {
        ExoTrackSelection.Definition definition = null;
        int i2 = 0;
        AudioTrackScore audioTrackScore = null;
        int i3 = -1;
        int i4 = -1;
        while (i2 < trackGroupArray.length) {
            TrackGroup trackGroup = trackGroupArray.get(i2);
            int[] iArr2 = iArr[i2];
            int i5 = i3;
            for (int i6 = 0; i6 < trackGroup.length; i6++) {
                if (isSupported(iArr2[i6], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    AudioTrackScore audioTrackScore2 = new AudioTrackScore(trackGroup.getFormat(i6), parameters, iArr2[i6]);
                    if ((audioTrackScore2.isWithinConstraints || parameters.exceedAudioConstraintsIfNecessary) && (audioTrackScore == null || audioTrackScore2.compareTo(audioTrackScore) > 0)) {
                        i5 = i2;
                        i4 = i6;
                        audioTrackScore = audioTrackScore2;
                    }
                }
            }
            i2++;
            i3 = i5;
        }
        if (i3 == -1) {
            return null;
        }
        TrackGroup trackGroup2 = trackGroupArray.get(i3);
        if (!parameters.forceHighestSupportedBitrate && !parameters.forceLowestBitrate && z) {
            int[] a2 = a(trackGroup2, iArr[i3], i4, parameters.maxAudioBitrate, parameters.allowAudioMixedMimeTypeAdaptiveness, parameters.allowAudioMixedSampleRateAdaptiveness, parameters.allowAudioMixedChannelCountAdaptiveness);
            if (a2.length > 1) {
                definition = new ExoTrackSelection.Definition(trackGroup2, a2);
            }
        }
        if (definition == null) {
            definition = new ExoTrackSelection.Definition(trackGroup2, i4);
        }
        return Pair.create(definition, (AudioTrackScore) Assertions.checkNotNull(audioTrackScore));
    }

    private static int[] a(TrackGroup trackGroup, int[] iArr, int i, int i2, boolean z, boolean z2, boolean z3) {
        Format format = trackGroup.getFormat(i);
        int[] iArr2 = new int[trackGroup.length];
        int i3 = 0;
        for (int i4 = 0; i4 < trackGroup.length; i4++) {
            if (i4 == i || a(trackGroup.getFormat(i4), iArr[i4], format, i2, z, z2, z3)) {
                i3++;
                iArr2[i3] = i4;
            }
        }
        return Arrays.copyOf(iArr2, i3);
    }

    private static boolean a(Format format, int i, Format format2, int i2, boolean z, boolean z2, boolean z3) {
        if (!isSupported(i, false) || format.bitrate == -1 || format.bitrate > i2) {
            return false;
        }
        if (!z3 && (format.channelCount == -1 || format.channelCount != format2.channelCount)) {
            return false;
        }
        if (z || (format.sampleMimeType != null && TextUtils.equals(format.sampleMimeType, format2.sampleMimeType))) {
            return z2 || (format.sampleRate != -1 && format.sampleRate == format2.sampleRate);
        }
        return false;
    }

    @Nullable
    protected Pair<ExoTrackSelection.Definition, TextTrackScore> selectTextTrack(TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters, @Nullable String str) throws ExoPlaybackException {
        int i = -1;
        TrackGroup trackGroup = null;
        TextTrackScore textTrackScore = null;
        int i2 = 0;
        while (i2 < trackGroupArray.length) {
            TrackGroup trackGroup2 = trackGroupArray.get(i2);
            int[] iArr2 = iArr[i2];
            TextTrackScore textTrackScore2 = textTrackScore;
            TrackGroup trackGroup3 = trackGroup;
            for (int i3 = 0; i3 < trackGroup2.length; i3++) {
                if (isSupported(iArr2[i3], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    TextTrackScore textTrackScore3 = new TextTrackScore(trackGroup2.getFormat(i3), parameters, iArr2[i3], str);
                    if (textTrackScore3.isWithinConstraints && (textTrackScore2 == null || textTrackScore3.compareTo(textTrackScore2) > 0)) {
                        i = i3;
                        trackGroup3 = trackGroup2;
                        textTrackScore2 = textTrackScore3;
                    }
                }
            }
            i2++;
            trackGroup = trackGroup3;
            textTrackScore = textTrackScore2;
        }
        if (trackGroup == null) {
            return null;
        }
        return Pair.create(new ExoTrackSelection.Definition(trackGroup, i), (TextTrackScore) Assertions.checkNotNull(textTrackScore));
    }

    @Nullable
    protected ExoTrackSelection.Definition selectOtherTrack(int i, TrackGroupArray trackGroupArray, int[][] iArr, Parameters parameters) throws ExoPlaybackException {
        TrackGroup trackGroup = null;
        OtherTrackScore otherTrackScore = null;
        int i2 = 0;
        int i3 = 0;
        while (i2 < trackGroupArray.length) {
            TrackGroup trackGroup2 = trackGroupArray.get(i2);
            int[] iArr2 = iArr[i2];
            TrackGroup trackGroup3 = trackGroup;
            for (int i4 = 0; i4 < trackGroup2.length; i4++) {
                if (isSupported(iArr2[i4], parameters.exceedRendererCapabilitiesIfNecessary)) {
                    OtherTrackScore otherTrackScore2 = new OtherTrackScore(trackGroup2.getFormat(i4), iArr2[i4]);
                    if (otherTrackScore == null || otherTrackScore2.compareTo(otherTrackScore) > 0) {
                        i3 = i4;
                        trackGroup3 = trackGroup2;
                        otherTrackScore = otherTrackScore2;
                    }
                }
            }
            i2++;
            trackGroup = trackGroup3;
            otherTrackScore = otherTrackScore;
        }
        if (trackGroup == null) {
            return null;
        }
        return new ExoTrackSelection.Definition(trackGroup, i3);
    }

    private static void a(MappingTrackSelector.MappedTrackInfo mappedTrackInfo, int[][][] iArr, RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr) {
        boolean z;
        boolean z2 = false;
        int i = 0;
        int i2 = -1;
        int i3 = -1;
        while (true) {
            if (i >= mappedTrackInfo.getRendererCount()) {
                z = true;
                break;
            }
            int rendererType = mappedTrackInfo.getRendererType(i);
            ExoTrackSelection exoTrackSelection = exoTrackSelectionArr[i];
            if ((rendererType == 1 || rendererType == 2) && exoTrackSelection != null && a(iArr[i], mappedTrackInfo.getTrackGroups(i), exoTrackSelection)) {
                if (rendererType == 1) {
                    if (i3 != -1) {
                        z = false;
                        break;
                    }
                    i3 = i;
                } else if (i2 != -1) {
                    z = false;
                    break;
                } else {
                    i2 = i;
                }
            }
            i++;
        }
        if (!(i3 == -1 || i2 == -1)) {
            z2 = true;
        }
        if (z && z2) {
            RendererConfiguration rendererConfiguration = new RendererConfiguration(true);
            rendererConfigurationArr[i3] = rendererConfiguration;
            rendererConfigurationArr[i2] = rendererConfiguration;
        }
    }

    private static boolean a(int[][] iArr, TrackGroupArray trackGroupArray, ExoTrackSelection exoTrackSelection) {
        if (exoTrackSelection == null) {
            return false;
        }
        int indexOf = trackGroupArray.indexOf(exoTrackSelection.getTrackGroup());
        for (int i = 0; i < exoTrackSelection.length(); i++) {
            if (RendererCapabilities.getTunnelingSupport(iArr[indexOf][exoTrackSelection.getIndexInTrackGroup(i)]) != 32) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isSupported(int i, boolean z) {
        int formatSupport = RendererCapabilities.getFormatSupport(i);
        return formatSupport == 4 || (z && formatSupport == 3);
    }

    @Nullable
    protected static String normalizeUndeterminedLanguageToNull(@Nullable String str) {
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, C.LANGUAGE_UNDETERMINED)) {
            return null;
        }
        return str;
    }

    protected static int getFormatLanguageScore(Format format, @Nullable String str, boolean z) {
        if (!TextUtils.isEmpty(str) && str.equals(format.language)) {
            return 4;
        }
        String normalizeUndeterminedLanguageToNull = normalizeUndeterminedLanguageToNull(str);
        String normalizeUndeterminedLanguageToNull2 = normalizeUndeterminedLanguageToNull(format.language);
        if (normalizeUndeterminedLanguageToNull2 == null || normalizeUndeterminedLanguageToNull == null) {
            return (!z || normalizeUndeterminedLanguageToNull2 != null) ? 0 : 1;
        }
        if (normalizeUndeterminedLanguageToNull2.startsWith(normalizeUndeterminedLanguageToNull) || normalizeUndeterminedLanguageToNull.startsWith(normalizeUndeterminedLanguageToNull2)) {
            return 3;
        }
        return Util.splitAtFirst(normalizeUndeterminedLanguageToNull2, Constants.ACCEPT_TIME_SEPARATOR_SERVER)[0].equals(Util.splitAtFirst(normalizeUndeterminedLanguageToNull, Constants.ACCEPT_TIME_SEPARATOR_SERVER)[0]) ? 2 : 0;
    }

    private static List<Integer> a(TrackGroup trackGroup, int i, int i2, boolean z) {
        ArrayList arrayList = new ArrayList(trackGroup.length);
        for (int i3 = 0; i3 < trackGroup.length; i3++) {
            arrayList.add(Integer.valueOf(i3));
        }
        if (i == Integer.MAX_VALUE || i2 == Integer.MAX_VALUE) {
            return arrayList;
        }
        int i4 = Integer.MAX_VALUE;
        for (int i5 = 0; i5 < trackGroup.length; i5++) {
            Format format = trackGroup.getFormat(i5);
            if (format.width > 0 && format.height > 0) {
                Point a2 = a(z, i, i2, format.width, format.height);
                int i6 = format.width * format.height;
                if (format.width >= ((int) (a2.x * 0.98f)) && format.height >= ((int) (a2.y * 0.98f)) && i6 < i4) {
                    i4 = i6;
                }
            }
        }
        if (i4 != Integer.MAX_VALUE) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                int pixelCount = trackGroup.getFormat(((Integer) arrayList.get(size)).intValue()).getPixelCount();
                if (pixelCount == -1 || pixelCount > i4) {
                    arrayList.remove(size);
                }
            }
        }
        return arrayList;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x000d, code lost:
        if (r1 != r3) goto L_0x0013;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static android.graphics.Point a(boolean r3, int r4, int r5, int r6, int r7) {
        /*
            if (r3 == 0) goto L_0x0010
            r3 = 1
            r0 = 0
            if (r6 <= r7) goto L_0x0008
            r1 = r3
            goto L_0x0009
        L_0x0008:
            r1 = r0
        L_0x0009:
            if (r4 <= r5) goto L_0x000c
            goto L_0x000d
        L_0x000c:
            r3 = r0
        L_0x000d:
            if (r1 == r3) goto L_0x0010
            goto L_0x0013
        L_0x0010:
            r2 = r5
            r5 = r4
            r4 = r2
        L_0x0013:
            int r3 = r6 * r4
            int r0 = r7 * r5
            if (r3 < r0) goto L_0x0023
            android.graphics.Point r3 = new android.graphics.Point
            int r4 = com.google.android.exoplayer2.util.Util.ceilDivide(r0, r6)
            r3.<init>(r5, r4)
            return r3
        L_0x0023:
            android.graphics.Point r5 = new android.graphics.Point
            int r3 = com.google.android.exoplayer2.util.Util.ceilDivide(r3, r7)
            r5.<init>(r3, r4)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.trackselection.DefaultTrackSelector.a(boolean, int, int, int, int):android.graphics.Point");
    }

    /* loaded from: classes2.dex */
    public static final class VideoTrackScore implements Comparable<VideoTrackScore> {
        private final Parameters a;
        private final boolean b;
        private final boolean c;
        private final int d;
        private final int e;
        private final int f;
        public final boolean isWithinMaxConstraints;

        public VideoTrackScore(Format format, Parameters parameters, int i, boolean z) {
            this.a = parameters;
            boolean z2 = true;
            int i2 = 0;
            this.isWithinMaxConstraints = z && (format.width == -1 || format.width <= parameters.maxVideoWidth) && ((format.height == -1 || format.height <= parameters.maxVideoHeight) && ((format.frameRate == -1.0f || format.frameRate <= ((float) parameters.maxVideoFrameRate)) && (format.bitrate == -1 || format.bitrate <= parameters.maxVideoBitrate)));
            if (!z || ((format.width != -1 && format.width < parameters.minVideoWidth) || ((format.height != -1 && format.height < parameters.minVideoHeight) || ((format.frameRate != -1.0f && format.frameRate < parameters.minVideoFrameRate) || (format.bitrate != -1 && format.bitrate < parameters.minVideoBitrate))))) {
                z2 = false;
            }
            this.b = z2;
            this.c = DefaultTrackSelector.isSupported(i, false);
            this.d = format.bitrate;
            this.e = format.getPixelCount();
            int i3 = Integer.MAX_VALUE;
            while (true) {
                if (i2 < parameters.preferredVideoMimeTypes.size()) {
                    if (format.sampleMimeType != null && format.sampleMimeType.equals(parameters.preferredVideoMimeTypes.get(i2))) {
                        i3 = i2;
                        break;
                    }
                    i2++;
                } else {
                    break;
                }
            }
            this.f = i3;
        }

        public int compareTo(VideoTrackScore videoTrackScore) {
            Ordering reverse = (!this.isWithinMaxConstraints || !this.c) ? DefaultTrackSelector.b.reverse() : DefaultTrackSelector.b;
            return ComparisonChain.start().compareFalseFirst(this.c, videoTrackScore.c).compareFalseFirst(this.isWithinMaxConstraints, videoTrackScore.isWithinMaxConstraints).compareFalseFirst(this.b, videoTrackScore.b).compare(Integer.valueOf(this.f), Integer.valueOf(videoTrackScore.f), Ordering.natural().reverse()).compare(Integer.valueOf(this.d), Integer.valueOf(videoTrackScore.d), this.a.forceLowestBitrate ? DefaultTrackSelector.b.reverse() : DefaultTrackSelector.c).compare(Integer.valueOf(this.e), Integer.valueOf(videoTrackScore.e), reverse).compare(Integer.valueOf(this.d), Integer.valueOf(videoTrackScore.d), reverse).result();
        }
    }

    /* loaded from: classes2.dex */
    public static final class AudioTrackScore implements Comparable<AudioTrackScore> {
        @Nullable
        private final String a;
        private final Parameters b;
        private final boolean c;
        private final int d;
        private final int e;
        private final int f;
        private final int g;
        private final int h;
        private final boolean i;
        public final boolean isWithinConstraints;
        private final int j;
        private final int k;
        private final int l;
        private final int m;

        public AudioTrackScore(Format format, Parameters parameters, int i) {
            int i2;
            int i3;
            this.b = parameters;
            this.a = DefaultTrackSelector.normalizeUndeterminedLanguageToNull(format.language);
            int i4 = 0;
            this.c = DefaultTrackSelector.isSupported(i, false);
            int i5 = 0;
            while (true) {
                if (i5 >= parameters.preferredAudioLanguages.size()) {
                    i2 = 0;
                    i5 = Integer.MAX_VALUE;
                    break;
                }
                i2 = DefaultTrackSelector.getFormatLanguageScore(format, (String) parameters.preferredAudioLanguages.get(i5), false);
                if (i2 > 0) {
                    break;
                }
                i5++;
            }
            this.e = i5;
            this.d = i2;
            this.f = Integer.bitCount(format.roleFlags & parameters.preferredAudioRoleFlags);
            boolean z = true;
            this.i = (format.selectionFlags & 1) != 0;
            this.j = format.channelCount;
            this.k = format.sampleRate;
            this.l = format.bitrate;
            if ((format.bitrate != -1 && format.bitrate > parameters.maxAudioBitrate) || (format.channelCount != -1 && format.channelCount > parameters.maxAudioChannelCount)) {
                z = false;
            }
            this.isWithinConstraints = z;
            String[] systemLanguageCodes = Util.getSystemLanguageCodes();
            int i6 = 0;
            while (true) {
                if (i6 >= systemLanguageCodes.length) {
                    i3 = 0;
                    i6 = Integer.MAX_VALUE;
                    break;
                }
                i3 = DefaultTrackSelector.getFormatLanguageScore(format, systemLanguageCodes[i6], false);
                if (i3 > 0) {
                    break;
                }
                i6++;
            }
            this.g = i6;
            this.h = i3;
            while (true) {
                if (i4 < parameters.preferredAudioMimeTypes.size()) {
                    if (format.sampleMimeType != null && format.sampleMimeType.equals(parameters.preferredAudioMimeTypes.get(i4))) {
                        break;
                    }
                    i4++;
                } else {
                    i4 = Integer.MAX_VALUE;
                    break;
                }
            }
            this.m = i4;
        }

        public int compareTo(AudioTrackScore audioTrackScore) {
            Ordering reverse = (!this.isWithinConstraints || !this.c) ? DefaultTrackSelector.b.reverse() : DefaultTrackSelector.b;
            ComparisonChain compare = ComparisonChain.start().compareFalseFirst(this.c, audioTrackScore.c).compare(Integer.valueOf(this.e), Integer.valueOf(audioTrackScore.e), Ordering.natural().reverse()).compare(this.d, audioTrackScore.d).compare(this.f, audioTrackScore.f).compareFalseFirst(this.isWithinConstraints, audioTrackScore.isWithinConstraints).compare(Integer.valueOf(this.m), Integer.valueOf(audioTrackScore.m), Ordering.natural().reverse()).compare(Integer.valueOf(this.l), Integer.valueOf(audioTrackScore.l), this.b.forceLowestBitrate ? DefaultTrackSelector.b.reverse() : DefaultTrackSelector.c).compareFalseFirst(this.i, audioTrackScore.i).compare(Integer.valueOf(this.g), Integer.valueOf(audioTrackScore.g), Ordering.natural().reverse()).compare(this.h, audioTrackScore.h).compare(Integer.valueOf(this.j), Integer.valueOf(audioTrackScore.j), reverse).compare(Integer.valueOf(this.k), Integer.valueOf(audioTrackScore.k), reverse);
            Integer valueOf = Integer.valueOf(this.l);
            Integer valueOf2 = Integer.valueOf(audioTrackScore.l);
            if (!Util.areEqual(this.a, audioTrackScore.a)) {
                reverse = DefaultTrackSelector.c;
            }
            return compare.compare(valueOf, valueOf2, reverse).result();
        }
    }

    /* loaded from: classes2.dex */
    public static final class TextTrackScore implements Comparable<TextTrackScore> {
        private final boolean a;
        private final boolean b;
        private final boolean c;
        private final int d;
        private final int e;
        private final int f;
        private final int g;
        private final boolean h;
        public final boolean isWithinConstraints;

        public TextTrackScore(Format format, Parameters parameters, int i, @Nullable String str) {
            ImmutableList immutableList;
            int i2;
            boolean z = false;
            this.a = DefaultTrackSelector.isSupported(i, false);
            int i3 = format.selectionFlags & (~parameters.disabledTextTrackSelectionFlags);
            this.b = (i3 & 1) != 0;
            this.c = (i3 & 2) != 0;
            int i4 = Integer.MAX_VALUE;
            if (parameters.preferredTextLanguages.isEmpty()) {
                immutableList = ImmutableList.of("");
            } else {
                immutableList = parameters.preferredTextLanguages;
            }
            int i5 = 0;
            while (true) {
                if (i5 >= immutableList.size()) {
                    i2 = 0;
                    break;
                }
                i2 = DefaultTrackSelector.getFormatLanguageScore(format, (String) immutableList.get(i5), parameters.selectUndeterminedTextLanguage);
                if (i2 > 0) {
                    i4 = i5;
                    break;
                }
                i5++;
            }
            this.d = i4;
            this.e = i2;
            this.f = Integer.bitCount(format.roleFlags & parameters.preferredTextRoleFlags);
            this.h = (format.roleFlags & 1088) != 0;
            this.g = DefaultTrackSelector.getFormatLanguageScore(format, str, DefaultTrackSelector.normalizeUndeterminedLanguageToNull(str) == null);
            if (this.e > 0 || ((parameters.preferredTextLanguages.isEmpty() && this.f > 0) || this.b || (this.c && this.g > 0))) {
                z = true;
            }
            this.isWithinConstraints = z;
        }

        public int compareTo(TextTrackScore textTrackScore) {
            ComparisonChain compare = ComparisonChain.start().compareFalseFirst(this.a, textTrackScore.a).compare(Integer.valueOf(this.d), Integer.valueOf(textTrackScore.d), Ordering.natural().reverse()).compare(this.e, textTrackScore.e).compare(this.f, textTrackScore.f).compareFalseFirst(this.b, textTrackScore.b).compare(Boolean.valueOf(this.c), Boolean.valueOf(textTrackScore.c), this.e == 0 ? Ordering.natural() : Ordering.natural().reverse()).compare(this.g, textTrackScore.g);
            if (this.f == 0) {
                compare = compare.compareTrueFirst(this.h, textTrackScore.h);
            }
            return compare.result();
        }
    }

    /* loaded from: classes2.dex */
    public static final class OtherTrackScore implements Comparable<OtherTrackScore> {
        private final boolean a;
        private final boolean b;

        public OtherTrackScore(Format format, int i) {
            this.a = (format.selectionFlags & 1) == 0 ? false : true;
            this.b = DefaultTrackSelector.isSupported(i, false);
        }

        public int compareTo(OtherTrackScore otherTrackScore) {
            return ComparisonChain.start().compareFalseFirst(this.b, otherTrackScore.b).compareFalseFirst(this.a, otherTrackScore.a).result();
        }
    }
}
