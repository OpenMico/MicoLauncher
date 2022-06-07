package com.xiaomi.smarthome.devicelibrary.bluetooth.connect;

/* loaded from: classes4.dex */
public class Code {
    public static final int BLE_NOT_SUPPORTED = -4;
    public static final int BLUETOOTH_DISABLED = -5;
    public static final int CONFIG_UNREADY = -12;
    public static final int CONNECTION_NOT_READY = -6;
    public static final int ILLEGAL_ARGUMENT = -3;
    public static final int REQUEST_BIND_DID_FAILED = -30;
    public static final int REQUEST_CANCELED = -2;
    public static final int REQUEST_DENIED = -14;
    public static final int REQUEST_EXCEPTION = -15;
    public static final int REQUEST_FAILED = -1;
    public static final int REQUEST_GET_DID_FAILED = -29;
    public static final int REQUEST_MESH_PROVISION_INFO_FAILED = -44;
    public static final int REQUEST_MESH_REG_DEVICE_VERIFY_CERT_FAILED = -41;
    public static final int REQUEST_MESH_REG_DEVICE_VERIFY_PUB_FAILED = -43;
    public static final int REQUEST_MESH_REG_DEVICE_VERIFY_SIGN_FAILED = -42;
    public static final int REQUEST_MESH_REG_SERVER_VERIFY_CERT_FAILED = -39;
    public static final int REQUEST_MESH_REG_SERVER_VERIFY_SIGN_FAILED = -40;
    public static final int REQUEST_MESH_SEND_SERVER_RESULT_FAILED = -45;
    public static final int REQUEST_NOTIFY_FAILED = -27;
    public static final int REQUEST_NOTIFY_TIMEDOUT = -55;
    public static final int REQUEST_NOT_CONNECTED = -8;
    public static final int REQUEST_ONGOING = -13;
    public static final int REQUEST_OVERFLOW = -11;
    public static final int REQUEST_PINCODE_IS_EMPTY = -38;
    public static final int REQUEST_SC_BIND_LTMK_FAILED = -26;
    public static final int REQUEST_SC_LOGIN_ENCRYPT_DATA_FAILED = -21;
    public static final int REQUEST_SC_LOGIN_FAILED = -22;
    public static final int REQUEST_SC_NOT_REGISTERED = -16;
    public static final int REQUEST_SC_REGISTERED = -17;
    public static final int REQUEST_SC_REGISTER_FAILED = -20;
    public static final int REQUEST_SC_REGISTER_GET_BIND_KEY_FAILED = -46;
    public static final int REQUEST_SC_REGISTER_GET_VERSION_FAILED = -36;
    public static final int REQUEST_SC_REGISTER_INPUT_PAIR_CODE = -33;
    public static final int REQUEST_SC_REGISTER_PAIR_CODE_EXPIRED = -35;
    public static final int REQUEST_SC_REGISTER_PAIR_CODE_FAILED = -34;
    public static final int REQUEST_SC_REGISTER_UNSUPPORT_VERSION = -37;
    public static final int REQUEST_SC_SHARED_KEY_EXPIRED = -18;
    public static final int REQUEST_SC_SHARED_KEY_FAILED = -19;
    public static final int REQUEST_SC_SHARED_LOGIN_ENCRYPT_DATA_FAILED = -23;
    public static final int REQUEST_SC_SHARED_LOGIN_FAILED = -24;
    public static final int REQUEST_SC_SHARED_LOGIN_KEY_ID_EMPTY = -25;
    public static final int REQUEST_STANDARD_AUTH_ERR_RELOGIN = -53;
    public static final int REQUEST_STANDARD_AUTH_GET_APP_CONFIRM_FAILED = -48;
    public static final int REQUEST_STANDARD_AUTH_GET_DEVICE_INFO_FAILED = -47;
    public static final int REQUEST_STANDARD_AUTH_GET_QR_OOB_FAILED = -50;
    public static final int REQUEST_STANDARD_AUTH_LOGIN_FAILED = -52;
    public static final int REQUEST_STANDARD_AUTH_OOB_FAILED = -49;
    public static final int REQUEST_STANDARD_AUTH_REGISTER_FAILED = -51;
    public static final int REQUEST_STANDARD_AUTH_TOKEN_IS_EMPTY = -54;
    public static final int REQUEST_STATUS_DISCONNECTED = -32;
    public static final int REQUEST_SUCCESS = 0;
    public static final int REQUEST_TIMEDOUT = -7;
    public static final int REQUEST_TOKEN_VERIFY_FAILED = -31;
    public static final int REQUEST_WRITE_FAILED = -28;
    public static final int TOKEN_NOT_MATCHED = -10;

    public static String toString(int i) {
        switch (i) {
            case REQUEST_NOTIFY_TIMEDOUT /* -55 */:
                return "REQUEST_NOTIFY_TIMEDOUT";
            case REQUEST_STANDARD_AUTH_TOKEN_IS_EMPTY /* -54 */:
                return "REQUEST_STANDARD_AUTH_TOKEN_IS_EMPTY";
            case REQUEST_STANDARD_AUTH_ERR_RELOGIN /* -53 */:
                return "REQUEST_STANDARD_AUTH_ERR_RELOGIN";
            case REQUEST_STANDARD_AUTH_LOGIN_FAILED /* -52 */:
                return "REQUEST_STANDARD_AUTH_LOGIN_FAILED";
            case REQUEST_STANDARD_AUTH_REGISTER_FAILED /* -51 */:
                return "REQUEST_STANDARD_AUTH_REGISTER_FAILED";
            case REQUEST_STANDARD_AUTH_GET_QR_OOB_FAILED /* -50 */:
                return "REQUEST_STANDARD_AUTH_GET_QR_OOB_FAILED";
            case REQUEST_STANDARD_AUTH_OOB_FAILED /* -49 */:
                return "REQUEST_STANDARD_AUTH_OOB_FAILED";
            case REQUEST_STANDARD_AUTH_GET_APP_CONFIRM_FAILED /* -48 */:
                return "REQUEST_STANDARD_AUTH_GET_APP_CONFIRM_FAILED";
            case REQUEST_STANDARD_AUTH_GET_DEVICE_INFO_FAILED /* -47 */:
                return "REQUEST_STANDARD_AUTH_GET_DEVICE_INFO_FAILED";
            case REQUEST_SC_REGISTER_GET_BIND_KEY_FAILED /* -46 */:
                return "REQUEST_SC_REGISTER_GET_BIND_KEY_FAILED";
            case REQUEST_MESH_SEND_SERVER_RESULT_FAILED /* -45 */:
                return "REQUEST_MESH_SEND_SERVER_RESULT_FAILED";
            case REQUEST_MESH_PROVISION_INFO_FAILED /* -44 */:
                return "REQUEST_MESH_PROVISION_INFO_FAILED";
            case REQUEST_MESH_REG_DEVICE_VERIFY_PUB_FAILED /* -43 */:
                return "REQUEST_MESH_REG_DEVICE_VERIFY_PUB_FAILED";
            case REQUEST_MESH_REG_DEVICE_VERIFY_SIGN_FAILED /* -42 */:
                return "REQUEST_MESH_REG_DEVICE_VERIFY_SIGN_FAILED";
            case REQUEST_MESH_REG_DEVICE_VERIFY_CERT_FAILED /* -41 */:
                return "REQUEST_MESH_REG_DEVICE_VERIFY_CERT_FAILED";
            case REQUEST_MESH_REG_SERVER_VERIFY_SIGN_FAILED /* -40 */:
                return "REQUEST_MESH_REG_SERVER_VERIFY_SIGN_FAILED";
            case REQUEST_MESH_REG_SERVER_VERIFY_CERT_FAILED /* -39 */:
                return "REQUEST_MESH_REG_SERVER_VERIFY_CERT_FAILED";
            case REQUEST_PINCODE_IS_EMPTY /* -38 */:
                return "REQUEST_PINCODE_IS_EMPTY";
            case REQUEST_SC_REGISTER_UNSUPPORT_VERSION /* -37 */:
                return "REQUEST_SC_REGISTER_UNSUPPORT_VERSION";
            case REQUEST_SC_REGISTER_GET_VERSION_FAILED /* -36 */:
                return "REQUEST_SC_REGISTER_GET_VERSION_FAILED";
            case REQUEST_SC_REGISTER_PAIR_CODE_EXPIRED /* -35 */:
                return "REQUEST_SC_REGISTER_PAIR_CODE_EXPIRED";
            case REQUEST_SC_REGISTER_PAIR_CODE_FAILED /* -34 */:
                return "REQUEST_SC_REGISTER_PAIR_CODE_FAILED";
            case REQUEST_SC_REGISTER_INPUT_PAIR_CODE /* -33 */:
                return "REQUEST_SC_REGISTER_INPUT_PAIR_CODE";
            case REQUEST_STATUS_DISCONNECTED /* -32 */:
                return "REQUEST_STATUS_DISCONNECTED";
            case REQUEST_TOKEN_VERIFY_FAILED /* -31 */:
                return "REQUEST_TOKEN_VERIFY_FAILED";
            case REQUEST_BIND_DID_FAILED /* -30 */:
                return "REQUEST_BIND_DID_FAILED";
            case REQUEST_GET_DID_FAILED /* -29 */:
                return "REQUEST_GET_DID_FAILED";
            case REQUEST_WRITE_FAILED /* -28 */:
                return "REQUEST_WRITE_FAILED";
            case REQUEST_NOTIFY_FAILED /* -27 */:
                return "REQUEST_NOTIFY_FAILED";
            case REQUEST_SC_BIND_LTMK_FAILED /* -26 */:
                return "REQUEST_SC_BIND_LTMK_FAILED";
            case REQUEST_SC_SHARED_LOGIN_KEY_ID_EMPTY /* -25 */:
                return "REQUEST_SC_SHARED_LOGIN_KEY_ID_EMPTY";
            case REQUEST_SC_SHARED_LOGIN_FAILED /* -24 */:
                return "REQUEST_SC_SHARED_LOGIN_FAILED";
            case REQUEST_SC_SHARED_LOGIN_ENCRYPT_DATA_FAILED /* -23 */:
                return "REQUEST_SC_SHARED_LOGIN_ENCRYPT_DATA_FAILED";
            case REQUEST_SC_LOGIN_FAILED /* -22 */:
                return "REQUEST_SC_LOGIN_FAILED";
            case REQUEST_SC_LOGIN_ENCRYPT_DATA_FAILED /* -21 */:
                return "REQUEST_SC_LOGIN_ENCRYPT_DATA_FAILED";
            case REQUEST_SC_REGISTER_FAILED /* -20 */:
                return "REQUEST_SC_REGISTER_FAILED";
            case REQUEST_SC_SHARED_KEY_FAILED /* -19 */:
                return "REQUEST_SC_SHARED_KEY_FAILED";
            case REQUEST_SC_SHARED_KEY_EXPIRED /* -18 */:
                return "REQUEST_SC_SHARED_KEY_EXPIRED";
            case REQUEST_SC_REGISTERED /* -17 */:
                return "REQUEST_SC_REGISTERED";
            case REQUEST_SC_NOT_REGISTERED /* -16 */:
                return "REQUEST_SC_NOT_REGISTERED";
            case REQUEST_EXCEPTION /* -15 */:
                return "REQUEST_EXCEPTION";
            case -14:
                return "REQUEST_DENIED";
            case -13:
                return "REQUEST_ONGOING";
            case -12:
                return "CONFIG_UNREADY";
            case -11:
                return "REQUEST_OVERFLOW";
            case -10:
                return "TOKEN_NOT_MATCHED";
            case -9:
            default:
                return "unknown code: " + i;
            case -8:
                return "REQUEST_NOT_CONNECTED";
            case -7:
                return "REQUEST_TIMEDOUT";
            case -6:
                return "CONNECTION_NOT_READY";
            case -5:
                return "BLUETOOTH_DISABLED";
            case -4:
                return "BLE_NOT_SUPPORTED";
            case -3:
                return "ILLEGAL_ARGUMENT";
            case -2:
                return "REQUEST_CANCELED";
            case -1:
                return "REQUEST_FAILED";
            case 0:
                return "REQUEST_SUCCESS";
        }
    }
}
