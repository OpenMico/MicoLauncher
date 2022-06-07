package org.fourthline.cling.controlpoint;

import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fourthline.cling.model.UnsupportedDataException;
import org.fourthline.cling.model.gena.CancelReason;
import org.fourthline.cling.model.gena.GENASubscription;
import org.fourthline.cling.model.gena.LocalGENASubscription;
import org.fourthline.cling.model.gena.RemoteGENASubscription;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.RemoteService;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.protocol.ProtocolCreationException;
import org.seamless.util.Exceptions;

/* loaded from: classes5.dex */
public abstract class SubscriptionCallback implements Runnable {
    protected static Logger log = Logger.getLogger(SubscriptionCallback.class.getName());
    private ControlPoint controlPoint;
    protected final Integer requestedDurationSeconds;
    protected final Service service;
    private GENASubscription subscription;

    protected abstract void ended(GENASubscription gENASubscription, CancelReason cancelReason, UpnpResponse upnpResponse);

    protected abstract void established(GENASubscription gENASubscription);

    protected abstract void eventReceived(GENASubscription gENASubscription);

    protected abstract void eventsMissed(GENASubscription gENASubscription, int i);

    protected abstract void failed(GENASubscription gENASubscription, UpnpResponse upnpResponse, Exception exc, String str);

    protected SubscriptionCallback(Service service) {
        this.service = service;
        this.requestedDurationSeconds = 1800;
    }

    protected SubscriptionCallback(Service service, int i) {
        this.service = service;
        this.requestedDurationSeconds = Integer.valueOf(i);
    }

    public Service getService() {
        return this.service;
    }

    public synchronized ControlPoint getControlPoint() {
        return this.controlPoint;
    }

    public synchronized void setControlPoint(ControlPoint controlPoint) {
        this.controlPoint = controlPoint;
    }

    public synchronized GENASubscription getSubscription() {
        return this.subscription;
    }

    public synchronized void setSubscription(GENASubscription gENASubscription) {
        this.subscription = gENASubscription;
    }

    @Override // java.lang.Runnable
    public synchronized void run() {
        if (getControlPoint() == null) {
            throw new IllegalStateException("Callback must be executed through ControlPoint");
        } else if (getService() instanceof LocalService) {
            establishLocalSubscription((LocalService) this.service);
        } else if (getService() instanceof RemoteService) {
            establishRemoteSubscription((RemoteService) this.service);
        }
    }

    private void establishLocalSubscription(LocalService localService) {
        Exception e;
        LocalGENASubscription localGENASubscription;
        if (getControlPoint().getRegistry().getLocalDevice(localService.getDevice().getIdentity().getUdn(), false) == null) {
            log.fine("Local device service is currently not registered, failing subscription immediately");
            failed(null, null, new IllegalStateException("Local device is not registered"));
            return;
        }
        try {
            localGENASubscription = new LocalGENASubscription(localService, Integer.MAX_VALUE, Collections.EMPTY_LIST) { // from class: org.fourthline.cling.controlpoint.SubscriptionCallback.1
                public void failed(Exception exc) {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.setSubscription(null);
                        SubscriptionCallback.this.failed(null, null, exc);
                    }
                }

                @Override // org.fourthline.cling.model.gena.GENASubscription
                public void established() {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.setSubscription(this);
                        SubscriptionCallback.this.established(this);
                    }
                }

                @Override // org.fourthline.cling.model.gena.LocalGENASubscription
                public void ended(CancelReason cancelReason) {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.setSubscription(null);
                        SubscriptionCallback.this.ended(this, cancelReason, null);
                    }
                }

                @Override // org.fourthline.cling.model.gena.GENASubscription
                public void eventReceived() {
                    synchronized (SubscriptionCallback.this) {
                        Logger logger = SubscriptionCallback.log;
                        logger.fine("Local service state updated, notifying callback, sequence is: " + getCurrentSequence());
                        SubscriptionCallback.this.eventReceived(this);
                        incrementSequence();
                    }
                }
            };
        } catch (Exception e2) {
            e = e2;
            localGENASubscription = null;
        }
        try {
            log.fine("Local device service is currently registered, also registering subscription");
            getControlPoint().getRegistry().addLocalSubscription(localGENASubscription);
            log.fine("Notifying subscription callback of local subscription availablity");
            localGENASubscription.establish();
            log.fine("Simulating first initial event for local subscription callback, sequence: " + localGENASubscription.getCurrentSequence());
            eventReceived(localGENASubscription);
            localGENASubscription.incrementSequence();
            log.fine("Starting to monitor state changes of local service");
            localGENASubscription.registerOnService();
        } catch (Exception e3) {
            e = e3;
            log.fine("Local callback creation failed: " + e.toString());
            log.log(Level.FINE, "Exception root cause: ", Exceptions.unwrap(e));
            if (localGENASubscription != null) {
                getControlPoint().getRegistry().removeLocalSubscription(localGENASubscription);
            }
            failed(localGENASubscription, null, e);
        }
    }

    private void establishRemoteSubscription(RemoteService remoteService) {
        try {
            getControlPoint().getProtocolFactory().createSendingSubscribe(new RemoteGENASubscription(remoteService, this.requestedDurationSeconds.intValue()) { // from class: org.fourthline.cling.controlpoint.SubscriptionCallback.2
                @Override // org.fourthline.cling.model.gena.RemoteGENASubscription
                public void failed(UpnpResponse upnpResponse) {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.setSubscription(null);
                        SubscriptionCallback.this.failed(this, upnpResponse, null);
                    }
                }

                @Override // org.fourthline.cling.model.gena.GENASubscription
                public void established() {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.setSubscription(this);
                        SubscriptionCallback.this.established(this);
                    }
                }

                @Override // org.fourthline.cling.model.gena.RemoteGENASubscription
                public void ended(CancelReason cancelReason, UpnpResponse upnpResponse) {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.setSubscription(null);
                        SubscriptionCallback.this.ended(this, cancelReason, upnpResponse);
                    }
                }

                @Override // org.fourthline.cling.model.gena.GENASubscription
                public void eventReceived() {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.eventReceived(this);
                    }
                }

                @Override // org.fourthline.cling.model.gena.RemoteGENASubscription
                public void eventsMissed(int i) {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.eventsMissed(this, i);
                    }
                }

                @Override // org.fourthline.cling.model.gena.RemoteGENASubscription
                public void invalidMessage(UnsupportedDataException unsupportedDataException) {
                    synchronized (SubscriptionCallback.this) {
                        SubscriptionCallback.this.invalidMessage(this, unsupportedDataException);
                    }
                }
            }).run();
        } catch (ProtocolCreationException e) {
            failed(this.subscription, null, e);
        }
    }

    public synchronized void end() {
        if (this.subscription != null) {
            if (this.subscription instanceof LocalGENASubscription) {
                endLocalSubscription((LocalGENASubscription) this.subscription);
            } else if (this.subscription instanceof RemoteGENASubscription) {
                endRemoteSubscription((RemoteGENASubscription) this.subscription);
            }
        }
    }

    private void endLocalSubscription(LocalGENASubscription localGENASubscription) {
        Logger logger = log;
        logger.fine("Removing local subscription and ending it in callback: " + localGENASubscription);
        getControlPoint().getRegistry().removeLocalSubscription(localGENASubscription);
        localGENASubscription.end(null);
    }

    private void endRemoteSubscription(RemoteGENASubscription remoteGENASubscription) {
        Logger logger = log;
        logger.fine("Ending remote subscription: " + remoteGENASubscription);
        getControlPoint().getConfiguration().getSyncProtocolExecutorService().execute(getControlPoint().getProtocolFactory().createSendingUnsubscribe(remoteGENASubscription));
    }

    protected void failed(GENASubscription gENASubscription, UpnpResponse upnpResponse, Exception exc) {
        failed(gENASubscription, upnpResponse, exc, createDefaultFailureMessage(upnpResponse, exc));
    }

    public static String createDefaultFailureMessage(UpnpResponse upnpResponse, Exception exc) {
        if (upnpResponse != null) {
            return "Subscription failed:  HTTP response was: " + upnpResponse.getResponseDetails();
        } else if (exc != null) {
            return "Subscription failed:  Exception occured: " + exc;
        } else {
            return "Subscription failed:  No response received.";
        }
    }

    protected void invalidMessage(RemoteGENASubscription remoteGENASubscription, UnsupportedDataException unsupportedDataException) {
        Logger logger = log;
        logger.info("Invalid event message received, causing: " + unsupportedDataException);
        if (log.isLoggable(Level.FINE)) {
            log.fine("------------------------------------------------------------------------------");
            log.fine(unsupportedDataException.getData() != null ? unsupportedDataException.getData().toString() : "null");
            log.fine("------------------------------------------------------------------------------");
        }
    }

    public String toString() {
        return "(SubscriptionCallback) " + getService();
    }
}
