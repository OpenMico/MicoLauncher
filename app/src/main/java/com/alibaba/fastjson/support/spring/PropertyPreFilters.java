package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class PropertyPreFilters {
    private List<MySimplePropertyPreFilter> filters = new ArrayList();

    public MySimplePropertyPreFilter addFilter() {
        MySimplePropertyPreFilter mySimplePropertyPreFilter = new MySimplePropertyPreFilter();
        this.filters.add(mySimplePropertyPreFilter);
        return mySimplePropertyPreFilter;
    }

    public MySimplePropertyPreFilter addFilter(String... strArr) {
        MySimplePropertyPreFilter mySimplePropertyPreFilter = new MySimplePropertyPreFilter(strArr);
        this.filters.add(mySimplePropertyPreFilter);
        return mySimplePropertyPreFilter;
    }

    public MySimplePropertyPreFilter addFilter(Class<?> cls, String... strArr) {
        MySimplePropertyPreFilter mySimplePropertyPreFilter = new MySimplePropertyPreFilter(cls, strArr);
        this.filters.add(mySimplePropertyPreFilter);
        return mySimplePropertyPreFilter;
    }

    public List<MySimplePropertyPreFilter> getFilters() {
        return this.filters;
    }

    public void setFilters(List<MySimplePropertyPreFilter> list) {
        this.filters = list;
    }

    public MySimplePropertyPreFilter[] toFilters() {
        return (MySimplePropertyPreFilter[]) this.filters.toArray(new MySimplePropertyPreFilter[0]);
    }

    /* loaded from: classes.dex */
    public class MySimplePropertyPreFilter extends SimplePropertyPreFilter {
        public MySimplePropertyPreFilter() {
            super(new String[0]);
        }

        public MySimplePropertyPreFilter(String... strArr) {
            super(strArr);
        }

        public MySimplePropertyPreFilter(Class<?> cls, String... strArr) {
            super(cls, strArr);
        }

        public MySimplePropertyPreFilter addExcludes(String... strArr) {
            for (String str : strArr) {
                getExcludes().add(str);
            }
            return this;
        }

        public MySimplePropertyPreFilter addIncludes(String... strArr) {
            for (String str : strArr) {
                getIncludes().add(str);
            }
            return this;
        }
    }
}
