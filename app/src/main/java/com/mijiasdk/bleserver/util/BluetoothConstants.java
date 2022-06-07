package com.mijiasdk.bleserver.util;

import java.util.UUID;

/* loaded from: classes2.dex */
public class BluetoothConstants {
    public static final String ACTION_CHARACTER_CHANGED = "com.xiaomi.smarthome.bluetooth.character_changed";
    public static final String ACTION_CHARACTER_WRITE = "com.xiaomi.smarthome.bluetooth.character_write";
    public static final String ACTION_CONNECT_STATUS_CHANGED = "com.xiaomi.smarthome.bluetooth.connect_status_changed";
    public static final String ACTION_LOG = "action.log";
    public static final int BIND_STYLE_STRONG = 1;
    public static final int BIND_STYLE_UNKNOWN = 0;
    public static final int BIND_STYLE_WEAK = 2;
    public static final int DEFAULT_DURATION = 10000;
    public static final int DEVICE_TYPE_BLE = 2;
    public static final int DEVICE_TYPE_CLASSIC = 1;
    public static final String DID_PREFIX = "blt.";
    public static final String EXTRA_NEW_STATE = "extra.new.state";
    public static final String EXTRA_PAIR_CODE = "extra.pair.code";
    public static final String EXTRA_STATUS = "extra.status";
    public static final int GATT_ERROR = 133;
    public static final String KEY_BYTES = "key_bytes";
    public static final String KEY_CHARACTER_UUID = "key_character_uuid";
    public static final String KEY_CHARACTER_VALUE = "key_character_value";
    public static final String KEY_CHARACTER_WRITE_STATUS = "key_character_write_status";
    public static final String KEY_CODE = "key_code";
    public static final String KEY_CONNECT_STATUS = "key_connect_status";
    public static final String KEY_DEVICE_ADDRESS = "key_device_address";
    public static final String KEY_DEVICE_DID = "key_device_did";
    public static final String KEY_GATT_PROFILE = "key_gatt_profile";
    public static final String KEY_IS_CONNECT_FAILED = "key_is_connect_failed";
    public static final String KEY_LTMK = "ltmk";
    public static final String KEY_MISERVICE_CHARACTERS = "key_miservice_characters";
    public static final String KEY_MTU = "key_mtu";
    public static final String KEY_RSSI = "key_rssi";
    public static final String KEY_SERVICE_UUID = "key_service_uuid";
    public static final String KEY_SESSION_KEY = "session_key";
    public static final String KEY_TOKEN = "key_token";
    public static final String KEY_VERSION = "key_version";
    public static final int MIIO_UUID = 65173;
    public static final int MIOT_UUID = 65173;
    public static final int NOTIFY_TIMEOUT = 15000;
    public static final int REQUEST_CODE_OPEN_BLUETOOTH = 16;
    public static final int SEARCH_TYPE_BLE = 2;
    public static final int SEARCH_TYPE_CLASSIC = 1;
    public static final int STATUS_CONNECTED = 16;
    public static final int STATUS_DISCONNECTED = 32;
    public static final int STATUS_UNKNOWN = 5;
    public static final UUID MISERVICE = UUIDUtils.makeUUID(65173);
    public static UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static UUID UUID_NOTIFY = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static final UUID CHARACTER_PID = UUIDUtils.makeUUID(2);
    public static final UUID CHARACTER_FIRMWARE_VERSION = UUIDUtils.makeUUID(4);
    public static final UUID CHARACTER_WIFIAPSSID = UUIDUtils.makeUUID(5);
    public static final UUID CHARACTER_EVENT = UUIDUtils.makeUUID(16);
    public static final UUID CHARACTER_STANDARD_AUTH = UUIDUtils.makeUUID(25);
    public static final UUID HUAMI_CUSTOM_SERVICE_UUID = UUID.fromString("00001000-1720-0206-0100-00805f9bab34");
    public static final UUID HUAMI_CUSTOM_CHARACTER_SMAC_UUID = UUID.fromString("00002000-1720-0206-0100-00805f9bab34");
}
