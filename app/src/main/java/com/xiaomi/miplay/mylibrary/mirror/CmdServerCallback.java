package com.xiaomi.miplay.mylibrary.mirror;

import com.xiaomi.miplay.mylibrary.MiDevice;

/* loaded from: classes4.dex */
public interface CmdServerCallback {
    void onAudioOpenNotify(MiDevice miDevice, int i);

    void onBeSeized(MiDevice miDevice);

    void onBtFrequencyACK(MiDevice miDevice, int i);

    void onBufferStateNotify(MiDevice miDevice, int i);

    void onChannelsAck(MiDevice miDevice, int i);

    void onDeviceInfo(MiDevice miDevice, byte[] bArr);

    void onDeviceInfoChanged(MiDevice miDevice, byte[] bArr);

    void onDeviceNetworkChanged(MiDevice miDevice, byte[] bArr);

    void onDeviceStartPlaying(MiDevice miDevice);

    void onDisConnectOneNotify(MiDevice miDevice);

    void onDisconnectNotify(MiDevice miDevice);

    void onDurationUpdated(MiDevice miDevice, long j);

    void onError();

    void onMediaInfoAck(MiDevice miDevice, byte[] bArr);

    void onMediaInfoNotify(MiDevice miDevice, byte[] bArr);

    void onMirrorModeAck(MiDevice miDevice, int i);

    void onMirrorModeNotify(MiDevice miDevice, int i);

    void onNextNotify(MiDevice miDevice);

    void onNotifyPropertiesInfo(MiDevice miDevice, byte[] bArr);

    void onOpenAck(MiDevice miDevice, int i);

    void onPauseNotify(MiDevice miDevice);

    void onPhysicalBtVolumeNotify(MiDevice miDevice, int i);

    void onPlayStateAck(MiDevice miDevice, int i);

    void onPlayStateNotify(MiDevice miDevice, int i);

    void onPositionAck(MiDevice miDevice, long j);

    void onPrevNotify(MiDevice miDevice);

    void onResumeNotify(MiDevice miDevice);

    void onSeekDoneNotify(MiDevice miDevice);

    void onSeekNotify(MiDevice miDevice, long j);

    void onStartPlater(MiDevice miDevice, byte[] bArr);

    void onSuccess();

    void onVolumeAck(MiDevice miDevice, int i);

    void onVolumeNotify(MiDevice miDevice, int i);
}
