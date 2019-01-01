/*
	This file is part of FreeJ2ME.

	FreeJ2ME is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.

	FreeJ2ME is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.

	You should have received a copy of the GNU General Public License
	along with FreeJ2ME.  If not, see http://www.gnu.org/licenses/
*/
package com.mascotcapsule.micro3d.v3;

public class AffineTrans {
    private static final int UNIT = 4096;

    public int m00;
    public int m01;
    public int m02;
    public int m03;
    public int m10;
    public int m11;
    public int m12;
    public int m13;
    public int m20;
    public int m21;
    public int m22;
    public int m23;

    public AffineTrans() {
    }

    public AffineTrans(int m00, int m01, int m02, int m03,
                       int m10, int m11, int m12, int m13,
                       int m20, int m21, int m22, int m23) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
    }

    public AffineTrans(AffineTrans a) {
        m00 = a.m00;
        m01 = a.m01;
        m02 = a.m02;
        m03 = a.m03;
        m10 = a.m10;
        m11 = a.m11;
        m12 = a.m12;
        m13 = a.m13;
        m20 = a.m20;
        m21 = a.m21;
        m22 = a.m22;
        m23 = a.m23;
    }

    public AffineTrans(int[][] a) {
        m00 = a[0][0];
        m01 = a[0][1];
        m02 = a[0][2];
        m03 = a[0][3];
        m10 = a[1][0];
        m11 = a[1][1];
        m12 = a[1][2];
        m13 = a[1][3];
        m20 = a[2][0];
        m21 = a[2][1];
        m22 = a[2][2];
        m23 = a[2][3];
    }

    public AffineTrans(int[] a) {
        m00 = a[0];
        m01 = a[1];
        m02 = a[2];
        m03 = a[3];
        m10 = a[4];
        m11 = a[5];
        m12 = a[6];
        m13 = a[7];
        m20 = a[8];
        m21 = a[9];
        m22 = a[10];
        m23 = a[11];
    }

    public AffineTrans(int[] a, int offset) {
        m00 = a[offset + 0];
        m01 = a[offset + 1];
        m02 = a[offset + 2];
        m03 = a[offset + 3];
        m10 = a[offset + 4];
        m11 = a[offset + 5];
        m12 = a[offset + 6];
        m13 = a[offset + 7];
        m20 = a[offset + 8];
        m21 = a[offset + 9];
        m22 = a[offset + 10];
        m23 = a[offset + 11];
    }

    public final void get(int[] a) {
        a[0] = m00;
        a[1] = m01;
        a[2] = m02;
        a[3] = m03;
        a[4] = m10;
        a[5] = m11;
        a[6] = m12;
        a[7] = m13;
        a[8] = m20;
        a[9] = m21;
        a[10] = m22;
        a[11] = m23;
    }

    public final void get(int[] a, int offset) {
        a[offset + 0] = m00;
        a[offset + 1] = m01;
        a[offset + 2] = m02;
        a[offset + 3] = m03;
        a[offset + 4] = m10;
        a[offset + 5] = m11;
        a[offset + 6] = m12;
        a[offset + 7] = m13;
        a[offset + 8] = m20;
        a[offset + 9] = m21;
        a[offset + 10] = m22;
        a[offset + 11] = m23;
    }

    public final void lookAt(Vector3D pos, Vector3D look, Vector3D up) {
        // TODO: needs thorough testing

        // TODO: If the line-of-sight direction and up vector are not perpendicular, the up vector will be
        // automatically corrected so that it is perpendicular to the line-of-sight direction and the transformation
        // matrix will be set based on that correction.

        System.out.println("lookAt pos=" + pos + " look=" + look + " up=" + up);

        Vector3D n = new Vector3D(pos.x - look.x, pos.y - look.y, pos.z - look.z);
        n.unit();

        Vector3D u = Vector3D.outerProduct(up, n);
        u.unit();

        Vector3D v = Vector3D.outerProduct(n, u);
        v.unit();
        System.out.println("corrected upVector=" + v);

        m00 = u.x;
        m01 = u.y;
        m02 = u.z;

        m10 = v.x;
        m11 = v.y;
        m12 = v.z;

        m20 = n.x;
        m21 = n.y;
        m22 = n.z;

        m03 = -u.innerProduct(pos) / UNIT;
        m13 = -v.innerProduct(pos) / UNIT;
        m23 = -n.innerProduct(pos) / UNIT;

        System.out.println("Test: xform " + look);
        Vector3D xformed = this.transform(look);
        System.out.println(" => " + xformed);
    }

    public final void mul(AffineTrans a) {
        this.mul(this, a);
    }

    public final void mul(AffineTrans a1, AffineTrans a2) {
        // FIXME: optional checking for overflow

        int m00 = (a1.m00 * a2.m00 + a1.m01 * a2.m10 + a1.m02 * a2.m20 + 2048) >> 12;
        int m01 = (a1.m00 * a2.m01 + a1.m01 * a2.m11 + a1.m02 * a2.m21 + 2048) >> 12;
        int m02 = (a1.m00 * a2.m02 + a1.m01 * a2.m12 + a1.m02 * a2.m22 + 2048) >> 12;
        int m03 = ((a1.m00 * a2.m03 + a1.m01 * a2.m13 + a1.m02 * a2.m23 + 2048) >> 12) + a1.m03;

        int m10 = (a1.m10 * a2.m00 + a1.m11 * a2.m10 + a1.m12 * a2.m20 + 2048) >> 12;
        int m11 = (a1.m10 * a2.m01 + a1.m11 * a2.m11 + a1.m12 * a2.m21 + 2048) >> 12;
        int m12 = (a1.m10 * a2.m02 + a1.m11 * a2.m12 + a1.m12 * a2.m22 + 2048) >> 12;
        int m13 = ((a1.m10 * a2.m03 + a1.m11 * a2.m13 + a1.m12 * a2.m23 + 2048) >> 12) + a1.m13;

        int m20 = (a1.m20 * a2.m00 + a1.m21 * a2.m10 + a1.m22 * a2.m20 + 2048) >> 12;
        int m21 = (a1.m20 * a2.m01 + a1.m21 * a2.m11 + a1.m22 * a2.m21 + 2048) >> 12;
        int m22 = (a1.m20 * a2.m02 + a1.m21 * a2.m12 + a1.m22 * a2.m22 + 2048) >> 12;
        int m23 = ((a1.m20 * a2.m03 + a1.m21 * a2.m13 + a1.m22 * a2.m23 + 2048) >> 12) + a1.m23;

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
    }

    @Deprecated
    public final void multiply(AffineTrans a) {
        mul(a);
    }

    @Deprecated
    public final void multiply(AffineTrans a1, AffineTrans a2) {
        mul(a1, a2);
    }

    @Deprecated
    public final void rotationV(Vector3D v, int r) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public final void rotationX(int r) {
        m00 = UNIT;                 m01 = 0;                    m02 = 0;
        m10 = 0;                    m11 = 4 * Util3D.cos(r);    m12 = -4 * Util3D.sin(r);
        m20 = 0;                    m21 = 4 * Util3D.sin(r);    m22 = 4 * Util3D.cos(r);
    }

    public final void rotationY(int r) {
        m00 = 4 * Util3D.cos(r);    m01 = 0;                    m02 = 4 * Util3D.sin(r);
        m10 = 0;                    m11 = UNIT;                 m12 = 0;
        m20 = -4 * Util3D.sin(r);   m21 = 0;                    m22 = 4 * Util3D.cos(r);
    }

    public final void rotationZ(int r) {
        m00 = 4 * Util3D.cos(r);    m01 = -4 * Util3D.sin(r);   m02 = 0;
        m10 = 4 * Util3D.sin(r);    m11 = 4 * Util3D.cos(r);    m12 = 0;
        m20 = 0;                    m21 = 0;                    m22 = UNIT;
    }

    public final void set(AffineTrans a) {
        m00 = a.m00;
        m01 = a.m01;
        m02 = a.m02;
        m03 = a.m03;
        m10 = a.m10;
        m11 = a.m11;
        m12 = a.m12;
        m13 = a.m13;
        m20 = a.m20;
        m21 = a.m21;
        m22 = a.m22;
        m23 = a.m23;
    }

    public final void set(int[] a) {
        m00 = a[0];
        m01 = a[1];
        m02 = a[2];
        m03 = a[3];
        m10 = a[4];
        m11 = a[5];
        m12 = a[6];
        m13 = a[7];
        m20 = a[8];
        m21 = a[9];
        m22 = a[10];
        m23 = a[11];
    }

    public final void set(int[][] a) {
        m00 = a[0][0];
        m01 = a[0][1];
        m02 = a[0][2];
        m03 = a[0][3];
        m10 = a[1][0];
        m11 = a[1][1];
        m12 = a[1][2];
        m13 = a[1][3];
        m20 = a[2][0];
        m21 = a[2][1];
        m22 = a[2][2];
        m23 = a[2][3];
    }

    public final void set(int[] a, int offset) {
        m00 = a[offset + 0];
        m01 = a[offset + 1];
        m02 = a[offset + 2];
        m03 = a[offset + 3];
        m10 = a[offset + 4];
        m11 = a[offset + 5];
        m12 = a[offset + 6];
        m13 = a[offset + 7];
        m20 = a[offset + 8];
        m21 = a[offset + 9];
        m22 = a[offset + 10];
        m23 = a[offset + 11];
    }

    public final void set(int m00, int m01, int m02, int m03, int m10, int m11, int m12, int m13, int m20, int m21, int m22, int m23) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
    }

    public void setIdentity() {
        m00 = m11 = m22 = UNIT;
        m01 = m02 = m03 = 0;
        m10 = m12 = m13 = 0;
        m20 = m21 = m23 = 0;
    }

    public final void setRotation(Vector3D v, int r) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Deprecated
    public final void setRotationX(int r) {
        this.rotationX(r);
    }

    @Deprecated
    public final void setRotationY(int r) {
        this.rotationY(r);
    }

    @Deprecated
    public final void setRotationZ(int z) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Deprecated
    public final void setViewTrans(Vector3D pos, Vector3D look, Vector3D up) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public final Vector3D transform(Vector3D v) {
        return new Vector3D((m00 * v.x + m01 * v.y + m02 * v.z + 2048) >> 12 + m03,
                            (m10 * v.x + m11 * v.y + m12 * v.z + 2048) >> 12 + m13,
                            (m20 * v.x + m21 * v.y + m22 * v.z + 2048) >> 12 + m23);
    }

    @Deprecated
    public final Vector3D transPoint(Vector3D v) {
        return transform(v);
    }
}
