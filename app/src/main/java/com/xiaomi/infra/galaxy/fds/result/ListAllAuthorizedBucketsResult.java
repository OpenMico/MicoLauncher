package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.BucketBean;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class ListAllAuthorizedBucketsResult {
    private List<BucketBean> a;

    public ListAllAuthorizedBucketsResult() {
    }

    public ListAllAuthorizedBucketsResult(List<BucketBean> list) {
        this.a = list;
    }

    public List<BucketBean> getBuckets() {
        return this.a;
    }

    public void setBuckets(List<BucketBean> list) {
        this.a = list;
    }
}
