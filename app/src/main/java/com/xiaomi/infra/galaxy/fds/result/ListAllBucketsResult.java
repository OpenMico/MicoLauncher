package com.xiaomi.infra.galaxy.fds.result;

import com.xiaomi.infra.galaxy.fds.bean.BucketBean;
import com.xiaomi.infra.galaxy.fds.bean.OwnerBean;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
/* loaded from: classes3.dex */
public class ListAllBucketsResult {
    private OwnerBean a;
    private List<BucketBean> b;

    public ListAllBucketsResult() {
    }

    public ListAllBucketsResult(OwnerBean ownerBean, List<BucketBean> list) {
        this.a = ownerBean;
        this.b = list;
    }

    public OwnerBean getOwner() {
        return this.a;
    }

    public void setOwner(OwnerBean ownerBean) {
        this.a = ownerBean;
    }

    public List<BucketBean> getBuckets() {
        return this.b;
    }

    public void setBuckets(List<BucketBean> list) {
        this.b = list;
    }
}
