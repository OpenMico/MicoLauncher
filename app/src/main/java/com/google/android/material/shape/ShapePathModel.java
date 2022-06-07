package com.google.android.material.shape;

@Deprecated
/* loaded from: classes2.dex */
public class ShapePathModel extends ShapeAppearanceModel {
    @Deprecated
    public void setAllCorners(CornerTreatment cornerTreatment) {
        this.a = cornerTreatment;
        this.b = cornerTreatment;
        this.c = cornerTreatment;
        this.d = cornerTreatment;
    }

    @Deprecated
    public void setAllEdges(EdgeTreatment edgeTreatment) {
        this.l = edgeTreatment;
        this.i = edgeTreatment;
        this.j = edgeTreatment;
        this.k = edgeTreatment;
    }

    @Deprecated
    public void setCornerTreatments(CornerTreatment cornerTreatment, CornerTreatment cornerTreatment2, CornerTreatment cornerTreatment3, CornerTreatment cornerTreatment4) {
        this.a = cornerTreatment;
        this.b = cornerTreatment2;
        this.c = cornerTreatment3;
        this.d = cornerTreatment4;
    }

    @Deprecated
    public void setEdgeTreatments(EdgeTreatment edgeTreatment, EdgeTreatment edgeTreatment2, EdgeTreatment edgeTreatment3, EdgeTreatment edgeTreatment4) {
        this.l = edgeTreatment;
        this.i = edgeTreatment2;
        this.j = edgeTreatment3;
        this.k = edgeTreatment4;
    }

    @Deprecated
    public void setTopLeftCorner(CornerTreatment cornerTreatment) {
        this.a = cornerTreatment;
    }

    @Deprecated
    public void setTopRightCorner(CornerTreatment cornerTreatment) {
        this.b = cornerTreatment;
    }

    @Deprecated
    public void setBottomRightCorner(CornerTreatment cornerTreatment) {
        this.c = cornerTreatment;
    }

    @Deprecated
    public void setBottomLeftCorner(CornerTreatment cornerTreatment) {
        this.d = cornerTreatment;
    }

    @Deprecated
    public void setTopEdge(EdgeTreatment edgeTreatment) {
        this.i = edgeTreatment;
    }

    @Deprecated
    public void setRightEdge(EdgeTreatment edgeTreatment) {
        this.j = edgeTreatment;
    }

    @Deprecated
    public void setBottomEdge(EdgeTreatment edgeTreatment) {
        this.k = edgeTreatment;
    }

    @Deprecated
    public void setLeftEdge(EdgeTreatment edgeTreatment) {
        this.l = edgeTreatment;
    }
}
