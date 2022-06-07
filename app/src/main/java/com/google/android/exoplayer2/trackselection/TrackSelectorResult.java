package com.google.android.exoplayer2.trackselection;

import androidx.annotation.Nullable;
import com.google.android.exoplayer2.RendererConfiguration;
import com.google.android.exoplayer2.util.Util;

/* loaded from: classes2.dex */
public final class TrackSelectorResult {
    @Nullable
    public final Object info;
    public final int length;
    public final RendererConfiguration[] rendererConfigurations;
    public final ExoTrackSelection[] selections;

    public TrackSelectorResult(RendererConfiguration[] rendererConfigurationArr, ExoTrackSelection[] exoTrackSelectionArr, @Nullable Object obj) {
        this.rendererConfigurations = rendererConfigurationArr;
        this.selections = (ExoTrackSelection[]) exoTrackSelectionArr.clone();
        this.info = obj;
        this.length = rendererConfigurationArr.length;
    }

    public boolean isRendererEnabled(int i) {
        return this.rendererConfigurations[i] != null;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult) {
        if (trackSelectorResult == null || trackSelectorResult.selections.length != this.selections.length) {
            return false;
        }
        for (int i = 0; i < this.selections.length; i++) {
            if (!isEquivalent(trackSelectorResult, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEquivalent(@Nullable TrackSelectorResult trackSelectorResult, int i) {
        return trackSelectorResult != null && Util.areEqual(this.rendererConfigurations[i], trackSelectorResult.rendererConfigurations[i]) && Util.areEqual(this.selections[i], trackSelectorResult.selections[i]);
    }
}
