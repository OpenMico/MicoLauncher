package com.mi.milink.mediacore;

import android.os.Bundle;
import androidx.annotation.Keep;
import androidx.annotation.NonNull;
import com.milink.kit.lock.MiLinkLock;
import java.io.File;
import java.util.Objects;

@Keep
/* loaded from: classes2.dex */
public class MiPlayEngine {

    @Keep
    /* loaded from: classes2.dex */
    public interface EventCallback {
        void onEvent(int i, String str);
    }

    public static native int getState();

    public static native String getVersion();

    public static native int install(@NonNull Config config);

    public static native boolean isInstalled();

    public static native int uninstall();

    static {
        System.loadLibrary("mirror_sink_server");
    }

    @Keep
    /* loaded from: classes2.dex */
    public static class Config {
        public final String OSVersion;
        public final MiLinkLock audioMiLinkLock;
        public final String btMAC;
        public final String dataDir;
        public final int deviceCategory;
        public final String deviceID;
        public final String deviceModel;
        public final EventCallback eventCallback;
        public final String networkType;
        public final Bundle optionArguments;

        private Config(Builder builder) {
            this.btMAC = builder.btMAC;
            this.eventCallback = builder.eventCallback;
            this.deviceID = builder.deviceID;
            this.deviceModel = builder.deviceModel;
            this.OSVersion = builder.OSVersion;
            this.networkType = builder.networkType;
            this.dataDir = builder.dataDir;
            this.audioMiLinkLock = builder.audioMiLinkLock;
            this.deviceCategory = builder.deviceCategory;
            this.optionArguments = builder.optionArg;
        }

        @Keep
        /* loaded from: classes2.dex */
        public static class Builder {
            private String OSVersion;
            private MiLinkLock audioMiLinkLock;
            private String btMAC;
            private String dataDir;
            private int deviceCategory;
            private String deviceID;
            private String deviceModel;
            private EventCallback eventCallback;
            private String networkType;
            private Bundle optionArg = Bundle.EMPTY;

            public Builder setBluetoothMAC(@NonNull String str) {
                this.btMAC = str;
                return this;
            }

            public Builder setDeviceCategory(int i) {
                this.deviceCategory = i;
                return this;
            }

            public Builder setDeviceID(@NonNull String str) {
                this.deviceID = str;
                return this;
            }

            public Builder setDeviceModel(@NonNull String str) {
                this.deviceModel = str;
                return this;
            }

            public Builder setOSVersion(@NonNull String str) {
                this.OSVersion = str;
                return this;
            }

            public Builder setNetworkType(@NonNull String str) {
                this.networkType = str;
                return this;
            }

            public Builder setEventCallback(@NonNull EventCallback eventCallback) {
                this.eventCallback = eventCallback;
                return this;
            }

            public Builder setDataDir(@NonNull File file) {
                this.dataDir = file.getAbsolutePath();
                return this;
            }

            public Builder setAudioMiLinkLock(MiLinkLock miLinkLock) {
                this.audioMiLinkLock = miLinkLock;
                return this;
            }

            public Builder setOptionArguments(Bundle bundle) {
                if (bundle == null) {
                    bundle = Bundle.EMPTY;
                }
                this.optionArg = bundle;
                return this;
            }

            public Config create() {
                Objects.requireNonNull(this.btMAC, "BluetoothMAC must not null");
                Objects.requireNonNull(this.eventCallback, "eventCallback must not null");
                Objects.requireNonNull(this.deviceID, "deviceID must not null");
                Objects.requireNonNull(this.deviceModel, "deviceModel must not null");
                Objects.requireNonNull(this.OSVersion, "OSVersion must not null");
                Objects.requireNonNull(this.networkType, "networkType must not null");
                Objects.requireNonNull(this.dataDir, "dataPath must not null");
                return new Config(this);
            }
        }
    }
}
