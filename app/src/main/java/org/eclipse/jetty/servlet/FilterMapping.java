package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;

/* loaded from: classes5.dex */
public class FilterMapping implements Dumpable {
    public static final int ALL = 31;
    public static final int ASYNC = 16;
    public static final int DEFAULT = 0;
    public static final int ERROR = 8;
    public static final int FORWARD = 2;
    public static final int INCLUDE = 4;
    public static final int REQUEST = 1;
    private int _dispatches = 0;
    private String _filterName;
    private transient FilterHolder _holder;
    private String[] _pathSpecs;
    private String[] _servletNames;

    public static DispatcherType dispatch(String str) {
        if ("request".equalsIgnoreCase(str)) {
            return DispatcherType.REQUEST;
        }
        if ("forward".equalsIgnoreCase(str)) {
            return DispatcherType.FORWARD;
        }
        if ("include".equalsIgnoreCase(str)) {
            return DispatcherType.INCLUDE;
        }
        if ("error".equalsIgnoreCase(str)) {
            return DispatcherType.ERROR;
        }
        if ("async".equalsIgnoreCase(str)) {
            return DispatcherType.ASYNC;
        }
        throw new IllegalArgumentException(str);
    }

    public static int dispatch(DispatcherType dispatcherType) {
        switch (dispatcherType) {
            case REQUEST:
                return 1;
            case ASYNC:
                return 16;
            case FORWARD:
                return 2;
            case INCLUDE:
                return 4;
            case ERROR:
                return 8;
            default:
                throw new IllegalArgumentException(dispatcherType.toString());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean appliesTo(String str, int i) {
        if (appliesTo(i)) {
            int i2 = 0;
            while (true) {
                String[] strArr = this._pathSpecs;
                if (i2 >= strArr.length) {
                    break;
                } else if (strArr[i2] != null && PathMap.match(strArr[i2], str, true)) {
                    return true;
                } else {
                    i2++;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean appliesTo(int i) {
        int i2 = this._dispatches;
        return i2 == 0 ? i == 1 || (i == 16 && this._holder.isAsyncSupported()) : (i & i2) != 0;
    }

    public String getFilterName() {
        return this._filterName;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public FilterHolder getFilterHolder() {
        return this._holder;
    }

    public String[] getPathSpecs() {
        return this._pathSpecs;
    }

    public void setDispatcherTypes(EnumSet<DispatcherType> enumSet) {
        this._dispatches = 0;
        if (enumSet != null) {
            if (enumSet.contains(DispatcherType.ERROR)) {
                this._dispatches |= 8;
            }
            if (enumSet.contains(DispatcherType.FORWARD)) {
                this._dispatches |= 2;
            }
            if (enumSet.contains(DispatcherType.INCLUDE)) {
                this._dispatches |= 4;
            }
            if (enumSet.contains(DispatcherType.REQUEST)) {
                this._dispatches |= 1;
            }
            if (enumSet.contains(DispatcherType.ASYNC)) {
                this._dispatches |= 16;
            }
        }
    }

    public void setDispatches(int i) {
        this._dispatches = i;
    }

    public void setFilterName(String str) {
        this._filterName = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setFilterHolder(FilterHolder filterHolder) {
        this._holder = filterHolder;
        setFilterName(filterHolder.getName());
    }

    public void setPathSpecs(String[] strArr) {
        this._pathSpecs = strArr;
    }

    public void setPathSpec(String str) {
        this._pathSpecs = new String[]{str};
    }

    public String[] getServletNames() {
        return this._servletNames;
    }

    public void setServletNames(String[] strArr) {
        this._servletNames = strArr;
    }

    public void setServletName(String str) {
        this._servletNames = new String[]{str};
    }

    public String toString() {
        return TypeUtil.asList(this._pathSpecs) + "/" + TypeUtil.asList(this._servletNames) + "==" + this._dispatches + "=>" + this._filterName;
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public void dump(Appendable appendable, String str) throws IOException {
        appendable.append(String.valueOf(this)).append("\n");
    }

    @Override // org.eclipse.jetty.util.component.Dumpable
    public String dump() {
        return AggregateLifeCycle.dump(this);
    }
}
