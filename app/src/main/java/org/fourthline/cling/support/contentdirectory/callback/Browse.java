package org.fourthline.cling.support.contentdirectory.callback;

import java.util.logging.Logger;
import org.fourthline.cling.controlpoint.ActionCallback;
import org.fourthline.cling.model.action.ActionException;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.support.contentdirectory.DIDLParser;
import org.fourthline.cling.support.model.BrowseFlag;
import org.fourthline.cling.support.model.BrowseResult;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.SortCriterion;

/* loaded from: classes5.dex */
public abstract class Browse extends ActionCallback {
    public static final String CAPS_WILDCARD = "*";
    private static Logger log = Logger.getLogger(Browse.class.getName());

    public long getDefaultMaxResults() {
        return 999L;
    }

    public abstract void received(ActionInvocation actionInvocation, DIDLContent dIDLContent);

    public boolean receivedRaw(ActionInvocation actionInvocation, BrowseResult browseResult) {
        return true;
    }

    public abstract void updateStatus(Status status);

    /* loaded from: classes5.dex */
    public enum Status {
        NO_CONTENT("No Content"),
        LOADING("Loading..."),
        OK("OK");
        
        private String defaultMessage;

        Status(String str) {
            this.defaultMessage = str;
        }

        public String getDefaultMessage() {
            return this.defaultMessage;
        }
    }

    public Browse(Service service, String str, BrowseFlag browseFlag) {
        this(service, str, browseFlag, "*", 0L, null, new SortCriterion[0]);
    }

    public Browse(Service service, String str, BrowseFlag browseFlag, String str2, long j, Long l, SortCriterion... sortCriterionArr) {
        super(new ActionInvocation(service.getAction("Browse")));
        Logger logger = log;
        logger.fine("Creating browse action for object ID: " + str);
        getActionInvocation().setInput("ObjectID", str);
        getActionInvocation().setInput("BrowseFlag", browseFlag.toString());
        getActionInvocation().setInput("Filter", str2);
        getActionInvocation().setInput("StartingIndex", new UnsignedIntegerFourBytes(j));
        getActionInvocation().setInput("RequestedCount", new UnsignedIntegerFourBytes(l == null ? getDefaultMaxResults() : l.longValue()));
        getActionInvocation().setInput("SortCriteria", SortCriterion.toString(sortCriterionArr));
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback, java.lang.Runnable
    public void run() {
        updateStatus(Status.LOADING);
        super.run();
    }

    @Override // org.fourthline.cling.controlpoint.ActionCallback
    public void success(ActionInvocation actionInvocation) {
        log.fine("Successful browse action, reading output argument values");
        BrowseResult browseResult = new BrowseResult(actionInvocation.getOutput("Result").getValue().toString(), (UnsignedIntegerFourBytes) actionInvocation.getOutput("NumberReturned").getValue(), (UnsignedIntegerFourBytes) actionInvocation.getOutput("TotalMatches").getValue(), (UnsignedIntegerFourBytes) actionInvocation.getOutput("UpdateID").getValue());
        if (!receivedRaw(actionInvocation, browseResult) || browseResult.getCountLong() <= 0 || browseResult.getResult().length() <= 0) {
            received(actionInvocation, new DIDLContent());
            updateStatus(Status.NO_CONTENT);
            return;
        }
        try {
            received(actionInvocation, new DIDLParser().parse(browseResult.getResult()));
            updateStatus(Status.OK);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.ACTION_FAILED;
            actionInvocation.setFailure(new ActionException(errorCode, "Can't parse DIDL XML response: " + e, e));
            failure(actionInvocation, null);
        }
    }
}
