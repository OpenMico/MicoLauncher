package com.google.android.exoplayer2.trackselection;

import android.os.SystemClock;
import androidx.annotation.Nullable;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.ExoTrackSelection;
import com.google.android.exoplayer2.upstream.LoadErrorHandlingPolicy;
import com.google.android.exoplayer2.util.MimeTypes;

/* loaded from: classes2.dex */
public final class TrackSelectionUtil {

    /* loaded from: classes2.dex */
    public interface AdaptiveTrackSelectionFactory {
        ExoTrackSelection createAdaptiveTrackSelection(ExoTrackSelection.Definition definition);
    }

    private TrackSelectionUtil() {
    }

    public static ExoTrackSelection[] createTrackSelectionsForDefinitions(ExoTrackSelection.Definition[] definitionArr, AdaptiveTrackSelectionFactory adaptiveTrackSelectionFactory) {
        ExoTrackSelection[] exoTrackSelectionArr = new ExoTrackSelection[definitionArr.length];
        boolean z = false;
        for (int i = 0; i < definitionArr.length; i++) {
            ExoTrackSelection.Definition definition = definitionArr[i];
            if (definition != null) {
                if (definition.tracks.length <= 1 || z) {
                    exoTrackSelectionArr[i] = new FixedTrackSelection(definition.group, definition.tracks[0], definition.type);
                } else {
                    exoTrackSelectionArr[i] = adaptiveTrackSelectionFactory.createAdaptiveTrackSelection(definition);
                    z = true;
                }
            }
        }
        return exoTrackSelectionArr;
    }

    public static DefaultTrackSelector.Parameters updateParametersWithOverride(DefaultTrackSelector.Parameters parameters, int i, TrackGroupArray trackGroupArray, boolean z, @Nullable DefaultTrackSelector.SelectionOverride selectionOverride) {
        DefaultTrackSelector.ParametersBuilder rendererDisabled = parameters.buildUpon().clearSelectionOverrides(i).setRendererDisabled(i, z);
        if (selectionOverride != null) {
            rendererDisabled.setSelectionOverride(i, trackGroupArray, selectionOverride);
        }
        return rendererDisabled.build();
    }

    public static boolean hasTrackOfType(TrackSelectionArray trackSelectionArray, int i) {
        for (int i2 = 0; i2 < trackSelectionArray.length; i2++) {
            TrackSelection trackSelection = trackSelectionArray.get(i2);
            if (trackSelection != null) {
                for (int i3 = 0; i3 < trackSelection.length(); i3++) {
                    if (MimeTypes.getTrackType(trackSelection.getFormat(i3).sampleMimeType) == i) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    public static LoadErrorHandlingPolicy.FallbackOptions createFallbackOptions(ExoTrackSelection exoTrackSelection) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int length = exoTrackSelection.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (exoTrackSelection.isBlacklisted(i2, elapsedRealtime)) {
                i++;
            }
        }
        return new LoadErrorHandlingPolicy.FallbackOptions(1, 0, length, i);
    }
}
