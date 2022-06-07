package org.fourthline.cling.support.model.container;

import java.util.ArrayList;
import java.util.List;
import org.fourthline.cling.support.model.DIDLObject;
import org.fourthline.cling.support.model.DescMeta;
import org.fourthline.cling.support.model.Res;
import org.fourthline.cling.support.model.WriteStatus;
import org.fourthline.cling.support.model.item.Item;

/* loaded from: classes5.dex */
public class Container extends DIDLObject {
    protected Integer childCount;
    protected List<Container> containers;
    protected List<DIDLObject.Class> createClasses;
    protected List<Item> items;
    protected List<DIDLObject.Class> searchClasses;
    protected boolean searchable;

    public Container() {
        this.childCount = null;
        this.createClasses = new ArrayList();
        this.searchClasses = new ArrayList();
        this.containers = new ArrayList();
        this.items = new ArrayList();
    }

    public Container(Container container) {
        super(container);
        this.childCount = null;
        this.createClasses = new ArrayList();
        this.searchClasses = new ArrayList();
        this.containers = new ArrayList();
        this.items = new ArrayList();
        setChildCount(container.getChildCount());
        setSearchable(container.isSearchable());
        setCreateClasses(container.getCreateClasses());
        setSearchClasses(container.getSearchClasses());
        setItems(container.getItems());
    }

    public Container(String str, String str2, String str3, String str4, boolean z, WriteStatus writeStatus, DIDLObject.Class r7, List<Res> list, List<DIDLObject.Property> list2, List<DescMeta> list3) {
        super(str, str2, str3, str4, z, writeStatus, r7, list, list2, list3);
        this.childCount = null;
        this.createClasses = new ArrayList();
        this.searchClasses = new ArrayList();
        this.containers = new ArrayList();
        this.items = new ArrayList();
    }

    public Container(String str, String str2, String str3, String str4, boolean z, WriteStatus writeStatus, DIDLObject.Class r7, List<Res> list, List<DIDLObject.Property> list2, List<DescMeta> list3, Integer num, boolean z2, List<DIDLObject.Class> list4, List<DIDLObject.Class> list5, List<Item> list6) {
        super(str, str2, str3, str4, z, writeStatus, r7, list, list2, list3);
        this.childCount = null;
        this.createClasses = new ArrayList();
        this.searchClasses = new ArrayList();
        this.containers = new ArrayList();
        this.items = new ArrayList();
        this.childCount = num;
        this.searchable = z2;
        this.createClasses = list4;
        this.searchClasses = list5;
        this.items = list6;
    }

    public Container(String str, Container container, String str2, String str3, DIDLObject.Class r21, Integer num) {
        this(str, container.getId(), str2, str3, true, null, r21, new ArrayList(), new ArrayList(), new ArrayList(), num, false, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Container(String str, String str2, String str3, String str4, DIDLObject.Class r21, Integer num) {
        this(str, str2, str3, str4, true, null, r21, new ArrayList(), new ArrayList(), new ArrayList(), num, false, new ArrayList(), new ArrayList(), new ArrayList());
    }

    public Container(String str, Container container, String str2, String str3, DIDLObject.Class r21, Integer num, boolean z, List<DIDLObject.Class> list, List<DIDLObject.Class> list2, List<Item> list3) {
        this(str, container.getId(), str2, str3, true, null, r21, new ArrayList(), new ArrayList(), new ArrayList(), num, z, list, list2, list3);
    }

    public Container(String str, String str2, String str3, String str4, DIDLObject.Class r21, Integer num, boolean z, List<DIDLObject.Class> list, List<DIDLObject.Class> list2, List<Item> list3) {
        this(str, str2, str3, str4, true, null, r21, new ArrayList(), new ArrayList(), new ArrayList(), num, z, list, list2, list3);
    }

    public Integer getChildCount() {
        return this.childCount;
    }

    public void setChildCount(Integer num) {
        this.childCount = num;
    }

    public boolean isSearchable() {
        return this.searchable;
    }

    public void setSearchable(boolean z) {
        this.searchable = z;
    }

    public List<DIDLObject.Class> getCreateClasses() {
        return this.createClasses;
    }

    public void setCreateClasses(List<DIDLObject.Class> list) {
        this.createClasses = list;
    }

    public List<DIDLObject.Class> getSearchClasses() {
        return this.searchClasses;
    }

    public void setSearchClasses(List<DIDLObject.Class> list) {
        this.searchClasses = list;
    }

    public Container getFirstContainer() {
        return getContainers().get(0);
    }

    public Container addContainer(Container container) {
        getContainers().add(container);
        return this;
    }

    public List<Container> getContainers() {
        return this.containers;
    }

    public void setContainers(List<Container> list) {
        this.containers = list;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> list) {
        this.items = list;
    }

    public Container addItem(Item item) {
        getItems().add(item);
        return this;
    }
}
