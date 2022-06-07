package com.xiaomi.idm.api;

import android.content.Context;
import android.os.RemoteException;
import com.xiaomi.idm.api.conn.ConnConfig;
import com.xiaomi.idm.api.conn.ConnParam;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import com.xiaomi.mi_connect_service.IConnectionCallback;
import com.xiaomi.mi_connect_service.proto.IPCParam;

/* loaded from: classes3.dex */
public class IDM extends IDMBinderBase {
    private final String a;

    /* loaded from: classes3.dex */
    public interface IDMConnectionCallback {
        void onDisconnected();

        void onFailure(ConnParam connParam, int i, String str);

        void onSuccess(ConnParam connParam, Object obj);
    }

    @Override // com.xiaomi.idm.api.IDMBinderBase
    protected int getMinVersion() {
        return 6;
    }

    public IDM(Context context, String str, IDMProcessCallback iDMProcessCallback) {
        super(context, iDMProcessCallback);
        this.a = str;
    }

    public int createWifiConfigConnectionByQRCode(String str, IDMConnectionCallback iDMConnectionCallback) {
        ConnParam buildFromQRCodeProto = ConnParam.buildFromQRCodeProto(str);
        if (buildFromQRCodeProto == null) {
            return -1;
        }
        return createConnection(buildFromQRCodeProto, iDMConnectionCallback);
    }

    public byte[] getIdHash() {
        LogUtil.d("IDM", "getIdHash", new Object[0]);
        if (!serviceAvailable()) {
            return null;
        }
        try {
            return this.mService.getIdHash();
        } catch (RemoteException e) {
            LogUtil.e("IDM", e.getMessage(), e);
            return null;
        }
    }

    public int createConnection(ConnParam connParam, IDMConnectionCallback iDMConnectionCallback) {
        LogUtil.d("IDM", "createConnection", new Object[0]);
        if (serviceAvailable()) {
            try {
                a aVar = new a(iDMConnectionCallback);
                IPCParam.SetConnParam a2 = a(connParam);
                if (a2 == null) {
                    return -1;
                }
                return this.mService.createConnection(getClientId(), a2.toByteArray(), aVar);
            } catch (RemoteException e) {
                LogUtil.e("IDM", e.getMessage(), e);
            }
        }
        return -1;
    }

    public int destroyConnection(ConnParam connParam) {
        LogUtil.d("IDM", "destroyConnection", new Object[0]);
        if (serviceAvailable()) {
            try {
                IPCParam.SetConnParam a2 = a(connParam);
                if (a2 == null) {
                    return -1;
                }
                return this.mService.destroyConnection(getClientId(), a2.toByteArray());
            } catch (RemoteException e) {
                LogUtil.e("IDM", e.getMessage(), e);
            }
        }
        return -1;
    }

    protected String getClientId() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class a extends IConnectionCallback.Stub {
        private IDMConnectionCallback b;

        private a(IDMConnectionCallback iDMConnectionCallback) {
            this.b = iDMConnectionCallback;
        }

        @Override // com.xiaomi.mi_connect_service.IConnectionCallback
        public void onSuccess(byte[] bArr) {
            ConnParam buildFromProto = ConnParam.buildFromProto(bArr);
            ConnConfig config = buildFromProto == null ? null : buildFromProto.getConfig();
            if (buildFromProto != null && config != null) {
                this.b.onSuccess(buildFromProto, config);
            }
        }

        @Override // com.xiaomi.mi_connect_service.IConnectionCallback
        public void onFailure(byte[] bArr) {
            ConnParam buildFromProto = ConnParam.buildFromProto(bArr);
            if (buildFromProto != null) {
                this.b.onFailure(buildFromProto, buildFromProto.getErrCode(), buildFromProto.getErrMsg());
            }
        }

        @Override // com.xiaomi.mi_connect_service.IConnectionCallback
        public void onDisconnected() {
            this.b.onDisconnected();
        }
    }

    private IPCParam.SetConnParam a(ConnParam connParam) {
        if (connParam == null) {
            return null;
        }
        return IPCParam.SetConnParam.newBuilder().setConnParam(connParam.toProto()).build();
    }
}
