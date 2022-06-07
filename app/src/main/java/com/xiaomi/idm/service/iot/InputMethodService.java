package com.xiaomi.idm.service.iot;

import com.google.protobuf.InvalidProtocolBufferException;
import com.xiaomi.idm.api.IDMClient;
import com.xiaomi.idm.api.IDMService;
import com.xiaomi.idm.api.RequestException;
import com.xiaomi.idm.api.ResponseCode;
import com.xiaomi.idm.api.RmiException;
import com.xiaomi.idm.api.proto.IDMServiceProto;
import com.xiaomi.idm.service.iot.PropertyService;
import com.xiaomi.idm.service.iot.proto.InputMethodServiceProto;
import com.xiaomi.idm.service.iot.proto.PropertyServiceProto;
import com.xiaomi.idm.task.CallFuture;
import com.xiaomi.mi_connect_sdk.util.LogUtil;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/* loaded from: classes3.dex */
public abstract class InputMethodService extends PropertyService {
    public static final String SERVICE_TYPE = "urn:aiot-spec-v3:service:input:00000001:1";

    /* loaded from: classes3.dex */
    public static class InputPropertyCommand extends PropertyService.PropertyCommand {
        public static final String FOCUSSTATUS = "focusstatus";
        public static final String INPUT_SERVICE_DESC = "input";
        public static final String TEXT = "text";
    }

    public abstract InputMethodServiceProto.InputMethodResponse startInputBox(String str, int i, int i2, String str2, int i3, int i4) throws RmiException;

    protected InputMethodService(IDMServiceProto.IDMService iDMService) {
        super(iDMService);
    }

    public InputMethodService(String str, String str2) {
        super(str, str2, "urn:aiot-spec-v3:service:input:00000001:1");
    }

    public InputMethodService(String str, String str2, String str3) {
        super(str, str2, str3);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0062  */
    @Override // com.xiaomi.idm.service.iot.PropertyService, com.xiaomi.idm.api.IDMService
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.xiaomi.idm.api.proto.IDMServiceProto.IDMResponse request(com.xiaomi.idm.api.proto.IDMServiceProto.IDMRequest r6) {
        /*
            r5 = this;
            int r0 = r6.getAid()
            com.google.protobuf.ByteString r1 = r6.getRequest()
            byte[] r1 = r1.toByteArray()
            r2 = 0
            if (r1 != 0) goto L_0x0010
            return r2
        L_0x0010:
            r3 = 1
            if (r0 == r3) goto L_0x001e
            r3 = 3
            if (r0 == r3) goto L_0x0018
            r3 = r2
            goto L_0x002f
        L_0x0018:
            com.xiaomi.idm.service.iot.PropertyService$Actions$SetPropertyCommands r3 = new com.xiaomi.idm.service.iot.PropertyService$Actions$SetPropertyCommands     // Catch: InvalidProtocolBufferException -> 0x0024
            r3.<init>(r5, r1)     // Catch: InvalidProtocolBufferException -> 0x0024
            goto L_0x002f
        L_0x001e:
            com.xiaomi.idm.service.iot.InputMethodService$Actions$StartInputBox r3 = new com.xiaomi.idm.service.iot.InputMethodService$Actions$StartInputBox     // Catch: InvalidProtocolBufferException -> 0x0024
            r3.<init>(r5, r1)     // Catch: InvalidProtocolBufferException -> 0x0024
            goto L_0x002f
        L_0x0024:
            r1 = move-exception
            java.lang.String r3 = "InputMethodService"
            java.lang.String r4 = r1.getMessage()
            com.xiaomi.mi_connect_sdk.util.LogUtil.e(r3, r4, r1)
            r3 = r2
        L_0x002f:
            if (r3 != 0) goto L_0x0062
            com.xiaomi.idm.api.ResponseCode$RequestCode r1 = com.xiaomi.idm.api.ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND
            int r1 = r1.getCode()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            com.xiaomi.idm.api.ResponseCode$RequestCode r4 = com.xiaomi.idm.api.ResponseCode.RequestCode.ERR_ACTION_NOT_FOUND
            java.lang.String r4 = r4.getMsg()
            r3.append(r4)
            java.lang.String r4 = " for uuid: "
            r3.append(r4)
            java.lang.String r4 = r5.getUUID()
            r3.append(r4)
            java.lang.String r4 = " aid: "
            r3.append(r4)
            r3.append(r0)
            java.lang.String r0 = r3.toString()
            com.xiaomi.idm.api.proto.IDMServiceProto$IDMResponse r6 = com.xiaomi.idm.utils.ResponseHelper.buildResponse(r1, r0, r6, r2)
            return r6
        L_0x0062:
            byte[] r0 = r3.invoke()
            com.xiaomi.idm.api.proto.IDMServiceProto$IDMResponse r6 = com.xiaomi.idm.utils.ResponseHelper.buildResponse(r6, r0)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.idm.service.iot.InputMethodService.request(com.xiaomi.idm.api.proto.IDMServiceProto$IDMRequest):com.xiaomi.idm.api.proto.IDMServiceProto$IDMResponse");
    }

    /* loaded from: classes3.dex */
    public static class Stub extends InputMethodService {
        private IDMClient a;

        public Stub(IDMClient iDMClient, IDMServiceProto.IDMService iDMService) {
            super(iDMService);
            this.a = iDMClient;
        }

        public CallFuture<InputMethodServiceProto.InputMethodResponse> startInputBoxAsync(String str, int i, int i2, String str2, int i3, int i4) {
            return this.a.request(new Actions.StartInputBox(this, str, i, i2, str2, i3, i4));
        }

        @Override // com.xiaomi.idm.service.iot.InputMethodService
        public InputMethodServiceProto.InputMethodResponse startInputBox(String str, int i, int i2, String str2, int i3, int i4) throws RmiException {
            try {
                return startInputBoxAsync(str, i, i2, str2, i3, i4).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public void subscribeTextEvent(Events.TextEvent.Callback callback) {
            this.a.setEventCallback(new Events.TextEvent(this, callback), true);
        }

        public void unsubscribeTextEvent(Events.TextEvent.Callback callback) {
            this.a.setEventCallback(new Events.TextEvent(this, callback), false);
        }

        public void subscribeInputCompleteEvent(Events.InputCompleteEvent.Callback callback) {
            this.a.setEventCallback(new Events.InputCompleteEvent(this, callback), true);
        }

        public void unsubscribeInputCompleteEvent(Events.InputCompleteEvent.Callback callback) {
            this.a.setEventCallback(new Events.InputCompleteEvent(this, callback), false);
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> setPropertyAsync(String str, boolean z) {
            return this.a.request(new PropertyService.Actions.SetProperty(this, str, z));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse setProperty(String str, boolean z) throws RmiException {
            try {
                return setPropertyAsync(str, z).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> getPropertyAsync(String str) {
            return this.a.request(new PropertyService.Actions.GetProperty(this, str));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse getProperty(String str) throws RmiException {
            try {
                return getPropertyAsync(str).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> setPropertyCommandsAsync(Map<String, String> map) {
            return this.a.request(new PropertyService.Actions.SetPropertyCommands(this, map));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse setPropertyCommands(Map<String, String> map) throws RmiException {
            try {
                return setPropertyCommandsAsync(map).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }

        public CallFuture<PropertyServiceProto.PropertyResponse> getPropertyCommandsAsync(Map<String, String> map) {
            return this.a.request(new PropertyService.Actions.GetPropertyCommands(this, map));
        }

        @Override // com.xiaomi.idm.service.iot.PropertyService
        public PropertyServiceProto.PropertyResponse getPropertyCommands(Map<String, String> map) throws RmiException {
            try {
                return getPropertyCommandsAsync(map).get();
            } catch (InterruptedException | CancellationException unused) {
                throw new RequestException(ResponseCode.RequestCode.ERR_RMI_CANCELED);
            } catch (ExecutionException e) {
                throw RmiException.createException(e);
            }
        }
    }

    /* loaded from: classes3.dex */
    public static abstract class Skeleton extends InputMethodService {
        private boolean a;
        private boolean b;

        public Skeleton(String str, String str2) {
            super(str, str2, "urn:aiot-spec-v3:service:input:00000001:1");
        }

        public Skeleton(String str, String str2, String str3) {
            super(str, str2, str3);
        }

        @Override // com.xiaomi.idm.api.IDMService, com.xiaomi.idm.api.IIDMService
        public int enableEvent(int i, boolean z) {
            if (i == -1) {
                this.mEventEnable |= z;
                this.a = z;
                this.b = z;
            } else if (i == 1) {
                this.a = z;
                this.mEventEnable |= z;
            } else if (i != 2) {
                return -1;
            } else {
                this.b = z;
                this.mEventEnable |= z;
            }
            return 0;
        }

        public void notifyTextEvent(String str) {
            if (this.a) {
                notifyEvent(1, InputMethodServiceProto.TextEvent.newBuilder().setInputData(str).build().toByteArray());
            }
        }

        public void notifyInputCompleteEvent(int i) {
            if (this.b) {
                notifyEvent(2, InputMethodServiceProto.InputCompleteEvent.newBuilder().setActionData(i).build().toByteArray());
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Actions {
        public static final int AID_STARTINPUTBOX = 1;

        /* loaded from: classes3.dex */
        public static class StartInputBox extends IDMService.Action<InputMethodServiceProto.InputMethodResponse> {
            InputMethodServiceProto.StartInputBox a;

            StartInputBox(InputMethodService inputMethodService, String str, int i, int i2, String str2, int i3, int i4) {
                super(1, inputMethodService);
                this.a = InputMethodServiceProto.StartInputBox.newBuilder().setAid(1).setClientId(str).setMethodType(i).setImeOptions(i2).setInputContent(str2).setCharacterType(i4).setInputTextLength(i3).build();
            }

            StartInputBox(InputMethodService inputMethodService, byte[] bArr) throws InvalidProtocolBufferException {
                super(1, inputMethodService);
                this.a = InputMethodServiceProto.StartInputBox.parseFrom(bArr);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // com.xiaomi.idm.api.IDMService.Action
            public InputMethodServiceProto.InputMethodResponse parseResponse(byte[] bArr) throws RmiException {
                try {
                    return InputMethodServiceProto.InputMethodResponse.parseFrom(bArr);
                } catch (InvalidProtocolBufferException unused) {
                    throw new RequestException(ResponseCode.RequestCode.ERR_RESPONSE_PARSE_IN_ACTION);
                }
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] invoke() {
                InputMethodServiceProto.InputMethodResponse inputMethodResponse;
                try {
                    inputMethodResponse = ((InputMethodService) this.service).startInputBox(this.a.getClientId(), this.a.getMethodType(), this.a.getImeOptions(), this.a.getInputContent(), this.a.getInputTextLength(), this.a.getCharacterType());
                } catch (RmiException e) {
                    LogUtil.e("InputMethodService", e.getMessage(), e);
                    inputMethodResponse = null;
                }
                if (inputMethodResponse == null) {
                    return null;
                }
                return inputMethodResponse.toByteArray();
            }

            @Override // com.xiaomi.idm.api.IDMService.Action
            public byte[] toBytes() {
                InputMethodServiceProto.StartInputBox startInputBox = this.a;
                if (startInputBox == null) {
                    return null;
                }
                return startInputBox.toByteArray();
            }
        }
    }

    /* loaded from: classes3.dex */
    public static class Events {
        public static final int EID_INPUTCOMPLETEEVENT = 2;
        public static final int EID_TEXTEVENT = 1;

        /* loaded from: classes3.dex */
        public static class TextEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onTextEvent(String str);
            }

            TextEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 1);
                this.a = callback;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    this.a.onTextEvent(InputMethodServiceProto.TextEvent.parseFrom(bArr).getInputData());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("InputMethodService", e.getMessage(), e);
                }
            }
        }

        /* loaded from: classes3.dex */
        public static class InputCompleteEvent extends IDMService.Event {
            Callback a;

            /* loaded from: classes3.dex */
            public interface Callback {
                void onInputCompleteEvent(int i);
            }

            InputCompleteEvent(IDMService iDMService, Callback callback) {
                super(iDMService, 2);
                this.a = callback;
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // com.xiaomi.idm.api.IDMService.Event
            public void onEvent(byte[] bArr) {
                try {
                    this.a.onInputCompleteEvent(InputMethodServiceProto.InputCompleteEvent.parseFrom(bArr).getActionData());
                } catch (InvalidProtocolBufferException e) {
                    LogUtil.e("InputMethodService", e.getMessage(), e);
                }
            }
        }
    }
}
