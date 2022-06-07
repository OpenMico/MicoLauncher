package org.fourthline.cling.support.contentdirectory;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.binding.annotations.UpnpAction;
import org.fourthline.cling.binding.annotations.UpnpInputArgument;
import org.fourthline.cling.binding.annotations.UpnpOutputArgument;
import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;
import org.fourthline.cling.binding.annotations.UpnpStateVariable;
import org.fourthline.cling.binding.annotations.UpnpStateVariables;
import org.fourthline.cling.model.types.ErrorCode;
import org.fourthline.cling.model.types.UnsignedIntegerFourBytes;
import org.fourthline.cling.model.types.csv.CSV;
import org.fourthline.cling.model.types.csv.CSVString;
import org.fourthline.cling.support.model.BrowseFlag;
import org.fourthline.cling.support.model.BrowseResult;
import org.fourthline.cling.support.model.DIDLContent;
import org.fourthline.cling.support.model.SortCriterion;

@UpnpService(serviceId = @UpnpServiceId("ContentDirectory"), serviceType = @UpnpServiceType(value = "ContentDirectory", version = 1))
@UpnpStateVariables({@UpnpStateVariable(datatype = "string", name = "A_ARG_TYPE_ObjectID", sendEvents = false), @UpnpStateVariable(datatype = "string", name = "A_ARG_TYPE_Result", sendEvents = false), @UpnpStateVariable(allowedValuesEnum = BrowseFlag.class, datatype = "string", name = "A_ARG_TYPE_BrowseFlag", sendEvents = false), @UpnpStateVariable(datatype = "string", name = "A_ARG_TYPE_Filter", sendEvents = false), @UpnpStateVariable(datatype = "string", name = "A_ARG_TYPE_SortCriteria", sendEvents = false), @UpnpStateVariable(datatype = "ui4", name = "A_ARG_TYPE_Index", sendEvents = false), @UpnpStateVariable(datatype = "ui4", name = "A_ARG_TYPE_Count", sendEvents = false), @UpnpStateVariable(datatype = "ui4", name = "A_ARG_TYPE_UpdateID", sendEvents = false), @UpnpStateVariable(datatype = "uri", name = "A_ARG_TYPE_URI", sendEvents = false), @UpnpStateVariable(datatype = "string", name = "A_ARG_TYPE_SearchCriteria", sendEvents = false)})
/* loaded from: classes5.dex */
public abstract class AbstractContentDirectoryService {
    public static final String CAPS_WILDCARD = "*";
    protected final PropertyChangeSupport propertyChangeSupport;
    @UpnpStateVariable(sendEvents = false)
    private final CSV<String> searchCapabilities;
    @UpnpStateVariable(sendEvents = false)
    private final CSV<String> sortCapabilities;
    @UpnpStateVariable(defaultValue = "0", eventMaximumRateMilliseconds = 200, sendEvents = true)
    private UnsignedIntegerFourBytes systemUpdateID;

    public abstract BrowseResult browse(String str, BrowseFlag browseFlag, String str2, long j, long j2, SortCriterion[] sortCriterionArr) throws ContentDirectoryException;

    protected AbstractContentDirectoryService() {
        this(new ArrayList(), new ArrayList(), null);
    }

    protected AbstractContentDirectoryService(List<String> list, List<String> list2) {
        this(list, list2, null);
    }

    protected AbstractContentDirectoryService(List<String> list, List<String> list2, PropertyChangeSupport propertyChangeSupport) {
        this.systemUpdateID = new UnsignedIntegerFourBytes(0L);
        this.propertyChangeSupport = propertyChangeSupport == null ? new PropertyChangeSupport(this) : propertyChangeSupport;
        this.searchCapabilities = new CSVString();
        this.searchCapabilities.addAll(list);
        this.sortCapabilities = new CSVString();
        this.sortCapabilities.addAll(list2);
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "SearchCaps")})
    public CSV<String> getSearchCapabilities() {
        return this.searchCapabilities;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "SortCaps")})
    public CSV<String> getSortCapabilities() {
        return this.sortCapabilities;
    }

    @UpnpAction(out = {@UpnpOutputArgument(name = "Id")})
    public synchronized UnsignedIntegerFourBytes getSystemUpdateID() {
        return this.systemUpdateID;
    }

    public PropertyChangeSupport getPropertyChangeSupport() {
        return this.propertyChangeSupport;
    }

    protected synchronized void changeSystemUpdateID() {
        Long value = getSystemUpdateID().getValue();
        this.systemUpdateID.increment(true);
        getPropertyChangeSupport().firePropertyChange("SystemUpdateID", value, getSystemUpdateID().getValue());
    }

    @UpnpAction(out = {@UpnpOutputArgument(getterName = "getResult", name = "Result", stateVariable = "A_ARG_TYPE_Result"), @UpnpOutputArgument(getterName = "getCount", name = "NumberReturned", stateVariable = "A_ARG_TYPE_Count"), @UpnpOutputArgument(getterName = "getTotalMatches", name = "TotalMatches", stateVariable = "A_ARG_TYPE_Count"), @UpnpOutputArgument(getterName = "getContainerUpdateID", name = "UpdateID", stateVariable = "A_ARG_TYPE_UpdateID")})
    public BrowseResult browse(@UpnpInputArgument(aliases = {"ContainerID"}, name = "ObjectID") String str, @UpnpInputArgument(name = "BrowseFlag") String str2, @UpnpInputArgument(name = "Filter") String str3, @UpnpInputArgument(name = "StartingIndex", stateVariable = "A_ARG_TYPE_Index") UnsignedIntegerFourBytes unsignedIntegerFourBytes, @UpnpInputArgument(name = "RequestedCount", stateVariable = "A_ARG_TYPE_Count") UnsignedIntegerFourBytes unsignedIntegerFourBytes2, @UpnpInputArgument(name = "SortCriteria") String str4) throws ContentDirectoryException {
        try {
            try {
                return browse(str, BrowseFlag.valueOrNullOf(str2), str3, unsignedIntegerFourBytes.getValue().longValue(), unsignedIntegerFourBytes2.getValue().longValue(), SortCriterion.valueOf(str4));
            } catch (ContentDirectoryException e) {
                throw e;
            } catch (Exception e2) {
                throw new ContentDirectoryException(ErrorCode.ACTION_FAILED, e2.toString());
            }
        } catch (Exception e3) {
            throw new ContentDirectoryException(ContentDirectoryErrorCode.UNSUPPORTED_SORT_CRITERIA, e3.toString());
        }
    }

    @UpnpAction(out = {@UpnpOutputArgument(getterName = "getResult", name = "Result", stateVariable = "A_ARG_TYPE_Result"), @UpnpOutputArgument(getterName = "getCount", name = "NumberReturned", stateVariable = "A_ARG_TYPE_Count"), @UpnpOutputArgument(getterName = "getTotalMatches", name = "TotalMatches", stateVariable = "A_ARG_TYPE_Count"), @UpnpOutputArgument(getterName = "getContainerUpdateID", name = "UpdateID", stateVariable = "A_ARG_TYPE_UpdateID")})
    public BrowseResult search(@UpnpInputArgument(name = "ContainerID", stateVariable = "A_ARG_TYPE_ObjectID") String str, @UpnpInputArgument(name = "SearchCriteria") String str2, @UpnpInputArgument(name = "Filter") String str3, @UpnpInputArgument(name = "StartingIndex", stateVariable = "A_ARG_TYPE_Index") UnsignedIntegerFourBytes unsignedIntegerFourBytes, @UpnpInputArgument(name = "RequestedCount", stateVariable = "A_ARG_TYPE_Count") UnsignedIntegerFourBytes unsignedIntegerFourBytes2, @UpnpInputArgument(name = "SortCriteria") String str4) throws ContentDirectoryException {
        try {
            try {
                return search(str, str2, str3, unsignedIntegerFourBytes.getValue().longValue(), unsignedIntegerFourBytes2.getValue().longValue(), SortCriterion.valueOf(str4));
            } catch (ContentDirectoryException e) {
                throw e;
            } catch (Exception e2) {
                throw new ContentDirectoryException(ErrorCode.ACTION_FAILED, e2.toString());
            }
        } catch (Exception e3) {
            throw new ContentDirectoryException(ContentDirectoryErrorCode.UNSUPPORTED_SORT_CRITERIA, e3.toString());
        }
    }

    public BrowseResult search(String str, String str2, String str3, long j, long j2, SortCriterion[] sortCriterionArr) throws ContentDirectoryException {
        try {
            return new BrowseResult(new DIDLParser().generate(new DIDLContent()), 0L, 0L);
        } catch (Exception e) {
            throw new ContentDirectoryException(ErrorCode.ACTION_FAILED, e.toString());
        }
    }
}
