package org.fourthline.cling.support.avtransport.lastchange;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import org.fourthline.cling.model.types.InvalidValueException;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.lastchange.EventedValue;
import org.fourthline.cling.support.lastchange.EventedValueEnum;
import org.fourthline.cling.support.lastchange.EventedValueEnumArray;
import org.fourthline.cling.support.lastchange.EventedValueString;
import org.fourthline.cling.support.lastchange.EventedValueURI;
import org.fourthline.cling.support.lastchange.EventedValueUnsignedIntegerFourBytes;
import org.fourthline.cling.support.model.PlayMode;
import org.fourthline.cling.support.model.RecordQualityMode;
import org.fourthline.cling.support.model.StorageMedium;
import org.fourthline.cling.support.model.TransportAction;

/* loaded from: classes5.dex */
public class AVTransportVariable {
    public static Set<Class<? extends EventedValue>> ALL = new HashSet<Class<? extends EventedValue>>() { // from class: org.fourthline.cling.support.avtransport.lastchange.AVTransportVariable.1
        {
            add(TransportState.class);
            add(TransportStatus.class);
            add(RecordStorageMedium.class);
            add(PossibleRecordStorageMedia.class);
            add(PossiblePlaybackStorageMedia.class);
            add(CurrentPlayMode.class);
            add(TransportPlaySpeed.class);
            add(RecordMediumWriteStatus.class);
            add(CurrentRecordQualityMode.class);
            add(PossibleRecordQualityModes.class);
            add(NumberOfTracks.class);
            add(CurrentTrack.class);
            add(CurrentTrackDuration.class);
            add(CurrentMediaDuration.class);
            add(CurrentTrackMetaData.class);
            add(CurrentTrackURI.class);
            add(AVTransportURI.class);
            add(NextAVTransportURI.class);
            add(AVTransportURIMetaData.class);
            add(NextAVTransportURIMetaData.class);
            add(CurrentTransportActions.class);
            add(RelativeTimePosition.class);
            add(AbsoluteTimePosition.class);
            add(RelativeCounterPosition.class);
            add(AbsoluteCounterPosition.class);
        }
    };

    /* loaded from: classes5.dex */
    public static class TransportState extends EventedValueEnum<org.fourthline.cling.support.model.TransportState> {
        public TransportState(org.fourthline.cling.support.model.TransportState transportState) {
            super(transportState);
        }

        public TransportState(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnum
        public org.fourthline.cling.support.model.TransportState enumValueOf(String str) {
            return org.fourthline.cling.support.model.TransportState.valueOf(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class TransportStatus extends EventedValueEnum<org.fourthline.cling.support.model.TransportStatus> {
        public TransportStatus(org.fourthline.cling.support.model.TransportStatus transportStatus) {
            super(transportStatus);
        }

        public TransportStatus(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnum
        public org.fourthline.cling.support.model.TransportStatus enumValueOf(String str) {
            return org.fourthline.cling.support.model.TransportStatus.valueOf(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class RecordStorageMedium extends EventedValueEnum<StorageMedium> {
        public RecordStorageMedium(StorageMedium storageMedium) {
            super(storageMedium);
        }

        public RecordStorageMedium(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnum
        public StorageMedium enumValueOf(String str) {
            return StorageMedium.valueOf(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class PossibleRecordStorageMedia extends EventedValueEnumArray<StorageMedium> {
        public PossibleRecordStorageMedia(StorageMedium[] storageMediumArr) {
            super(storageMediumArr);
        }

        public PossibleRecordStorageMedia(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnumArray
        public StorageMedium[] enumValueOf(String[] strArr) {
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                arrayList.add(StorageMedium.valueOf(str));
            }
            return (StorageMedium[]) arrayList.toArray(new StorageMedium[arrayList.size()]);
        }
    }

    /* loaded from: classes5.dex */
    public static class PossiblePlaybackStorageMedia extends PossibleRecordStorageMedia {
        public PossiblePlaybackStorageMedia(StorageMedium[] storageMediumArr) {
            super(storageMediumArr);
        }

        public PossiblePlaybackStorageMedia(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentPlayMode extends EventedValueEnum<PlayMode> {
        public CurrentPlayMode(PlayMode playMode) {
            super(playMode);
        }

        public CurrentPlayMode(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnum
        public PlayMode enumValueOf(String str) {
            return PlayMode.valueOf(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class TransportPlaySpeed extends EventedValueString {
        static final Pattern pattern = Pattern.compile("^-?\\d+(/\\d+)?$", 2);

        public TransportPlaySpeed(String str) {
            super(str);
            if (!pattern.matcher(str).matches()) {
                throw new InvalidValueException("Can't parse TransportPlaySpeed speeds.");
            }
        }

        public TransportPlaySpeed(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class RecordMediumWriteStatus extends EventedValueEnum<org.fourthline.cling.support.model.RecordMediumWriteStatus> {
        public RecordMediumWriteStatus(org.fourthline.cling.support.model.RecordMediumWriteStatus recordMediumWriteStatus) {
            super(recordMediumWriteStatus);
        }

        public RecordMediumWriteStatus(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnum
        public org.fourthline.cling.support.model.RecordMediumWriteStatus enumValueOf(String str) {
            return org.fourthline.cling.support.model.RecordMediumWriteStatus.valueOf(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentRecordQualityMode extends EventedValueEnum<RecordQualityMode> {
        public CurrentRecordQualityMode(RecordQualityMode recordQualityMode) {
            super(recordQualityMode);
        }

        public CurrentRecordQualityMode(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnum
        public RecordQualityMode enumValueOf(String str) {
            return RecordQualityMode.valueOf(str);
        }
    }

    /* loaded from: classes5.dex */
    public static class PossibleRecordQualityModes extends EventedValueEnumArray<RecordQualityMode> {
        public PossibleRecordQualityModes(RecordQualityMode[] recordQualityModeArr) {
            super(recordQualityModeArr);
        }

        public PossibleRecordQualityModes(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnumArray
        public RecordQualityMode[] enumValueOf(String[] strArr) {
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                arrayList.add(RecordQualityMode.valueOf(str));
            }
            return (RecordQualityMode[]) arrayList.toArray(new RecordQualityMode[arrayList.size()]);
        }
    }

    /* loaded from: classes5.dex */
    public static class NumberOfTracks extends EventedValueUnsignedIntegerFourBytes {
        public NumberOfTracks(UnsignedIntegerFourBytes unsignedIntegerFourBytes) {
            super(unsignedIntegerFourBytes);
        }

        public NumberOfTracks(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentTrack extends EventedValueUnsignedIntegerFourBytes {
        public CurrentTrack(UnsignedIntegerFourBytes unsignedIntegerFourBytes) {
            super(unsignedIntegerFourBytes);
        }

        public CurrentTrack(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentTrackDuration extends EventedValueString {
        public CurrentTrackDuration(String str) {
            super(str);
        }

        public CurrentTrackDuration(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentMediaDuration extends EventedValueString {
        public CurrentMediaDuration(String str) {
            super(str);
        }

        public CurrentMediaDuration(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentTrackMetaData extends EventedValueString {
        public CurrentTrackMetaData(String str) {
            super(str);
        }

        public CurrentTrackMetaData(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentTrackURI extends EventedValueURI {
        public CurrentTrackURI(URI uri) {
            super(uri);
        }

        public CurrentTrackURI(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class AVTransportURI extends EventedValueURI {
        public AVTransportURI(URI uri) {
            super(uri);
        }

        public AVTransportURI(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class NextAVTransportURI extends EventedValueURI {
        public NextAVTransportURI(URI uri) {
            super(uri);
        }

        public NextAVTransportURI(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class AVTransportURIMetaData extends EventedValueString {
        public AVTransportURIMetaData(String str) {
            super(str);
        }

        public AVTransportURIMetaData(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class NextAVTransportURIMetaData extends EventedValueString {
        public NextAVTransportURIMetaData(String str) {
            super(str);
        }

        public NextAVTransportURIMetaData(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class CurrentTransportActions extends EventedValueEnumArray<TransportAction> {
        public CurrentTransportActions(TransportAction[] transportActionArr) {
            super(transportActionArr);
        }

        public CurrentTransportActions(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }

        @Override // org.fourthline.cling.support.lastchange.EventedValueEnumArray
        public TransportAction[] enumValueOf(String[] strArr) {
            if (strArr == null) {
                return new TransportAction[0];
            }
            ArrayList arrayList = new ArrayList();
            for (String str : strArr) {
                arrayList.add(TransportAction.valueOf(str));
            }
            return (TransportAction[]) arrayList.toArray(new TransportAction[arrayList.size()]);
        }
    }

    /* loaded from: classes5.dex */
    public static class RelativeTimePosition extends EventedValueString {
        public RelativeTimePosition(String str) {
            super(str);
        }

        public RelativeTimePosition(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class AbsoluteTimePosition extends EventedValueString {
        public AbsoluteTimePosition(String str) {
            super(str);
        }

        public AbsoluteTimePosition(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class RelativeCounterPosition extends EventedValueString {
        public RelativeCounterPosition(String str) {
            super(str);
        }

        public RelativeCounterPosition(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }

    /* loaded from: classes5.dex */
    public static class AbsoluteCounterPosition extends EventedValueString {
        public AbsoluteCounterPosition(String str) {
            super(str);
        }

        public AbsoluteCounterPosition(Map.Entry<String, String>[] entryArr) {
            super(entryArr);
        }
    }
}
