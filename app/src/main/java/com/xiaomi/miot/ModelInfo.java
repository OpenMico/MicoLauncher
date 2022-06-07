package com.xiaomi.miot;

/* loaded from: classes2.dex */
public class ModelInfo {
    private String categoryName;
    private String model;
    private String name;
    private String specUrn;
    private int subCategoryId;

    public String getModel() {
        return this.model;
    }

    public void setModel(String str) {
        this.model = str;
    }

    public String getSpecUrn() {
        return this.specUrn;
    }

    public void setSpecUrn(String str) {
        this.specUrn = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public int getSubCategoryId() {
        return this.subCategoryId;
    }

    public void setSubCategoryId(int i) {
        this.subCategoryId = i;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String str) {
        this.categoryName = str;
    }

    public String toJsonString() {
        return "{\"model\":\"" + this.model + "\",\"specUrn\":\"" + this.specUrn + "\",\"name\":\"" + this.name + "\",\"subCategoryId\":" + this.subCategoryId + ",\"categoryName\":\"" + this.categoryName + "\"}";
    }
}
