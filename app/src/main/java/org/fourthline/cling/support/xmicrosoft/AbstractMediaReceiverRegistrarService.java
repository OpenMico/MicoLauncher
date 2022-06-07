package org.fourthline.cling.support.xmicrosoft;

import java.beans.PropertyChangeSupport;
import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;
import org.fourthline.cling.binding.annotations.UpnpStateVariables;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;

@UpnpService(serviceId = @UpnpServiceId(namespace = "microsoft.com", value = "X_MS_MediaReceiverRegistrar"), serviceType = @UpnpServiceType(namespace = "microsoft.com", value = "X_MS_MediaReceiverRegistrar", version = 1))
@UpnpStateVariables({@UpnpStateVariable(datatype = "string", name = "A_ARG_TYPE_DeviceID", sendEvents = false), @UpnpStateVariable(datatype = "int", name = "A_ARG_TYPE_Result", sendEvents = false), @UpnpStateVariable(datatype = "bin.base64", name = "A_ARG_TYPE_RegistrationReqMsg", sendEvents = false), @UpnpStateVariable(datatype = "bin.base64", name = "A_ARG_TYPE_RegistrationRespMsg", sendEvents = false)})
/* loaded from: classes5.dex */
public abstract class AbstractMediaReceiverRegistrarService {
    @UpnpStateVariable(eventMinimumDelta = 1)
    private UnsignedIntegerFourBytes authorizationDeniedUpdateID;
    @UpnpStateVariable(eventMinimumDelta = 1)
    private UnsignedIntegerFourBytes authorizationGrantedUpdateID;
    protected final PropertyChangeSupport propertyChangeSupport;
    @UpnpStateVariable
    private UnsignedIntegerFourBytes validationRevokedUpdateID;
    @UpnpStateVariable
    private UnsignedIntegerFourBytes validationSucceededUpdateID;

    @UpnpAction(out = {@UpnpOutputArgument(name = "Result", stateVariable = "A_ARG_TYPE_Result")})
    public int isAuthorized(@UpnpInputArgument(name = "DeviceID", stateVariable = "A_ARG_TYPE_DeviceID") String str) {
        return 1;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "Result", stateVariable = "A_ARG_TYPE_Result")})
    public int isValidated(@UpnpInputArgument(name = "DeviceID", stateVariable = "A_ARG_TYPE_DeviceID") String str) {
        return 1;
    }

    protected AbstractMediaReceiverRegistrarService() {
        this(null);
    }

    protected AbstractMediaReceiverRegistrarService(PropertyChangeSupport propertyChangeSupport) {
        this.authorizationGrantedUpdateID = new UnsignedIntegerFourBytes(0L);
        this.authorizationDeniedUpdateID = new UnsignedIntegerFourBytes(0L);
        this.validationSucceededUpdateID = new UnsignedIntegerFourBytes(0L);
        this.validationRevokedUpdateID = new UnsignedIntegerFourBytes(0L);
        this.propertyChangeSupport = propertyChangeSupport == null ? new PropertyChangeSupport(this) : propertyChangeSupport;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.propertyChangeSupport;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "AuthorizationGrantedUpdateID")})
    public UnsignedIntegerFourBytes getAuthorizationGrantedUpdateID() {
        return this.authorizationGrantedUpdateID;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "AuthorizationDeniedUpdateID")})
    public UnsignedIntegerFourBytes getAuthorizationDeniedUpdateID() {
        return this.authorizationDeniedUpdateID;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "ValidationSucceededUpdateID")})
    public UnsignedIntegerFourBytes getValidationSucceededUpdateID() {
        return this.validationSucceededUpdateID;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "ValidationRevokedUpdateID")})
    public UnsignedIntegerFourBytes getValidationRevokedUpdateID() {
        return this.validationRevokedUpdateID;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "RegistrationRespMsg", stateVariable = "A_ARG_TYPE_RegistrationRespMsg")})
    public byte[] registerDevice(@UpnpInputArgument(name = "RegistrationReqMsg", stateVariable = "A_ARG_TYPE_RegistrationReqMsg") byte[] bArr) {
        return new byte[0];
    }
}
