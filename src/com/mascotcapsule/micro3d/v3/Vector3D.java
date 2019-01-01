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

public class Vector3D {
    private static final int UNIT = 4096;

    public int x;
    public int y;
    public int z;

    public Vector3D() {
    }

    public Vector3D(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public Vector3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public final int getZ() {
        return z;
    }

    public final int innerProduct(Vector3D v) {
        return (this.x * v.x + this.y * v.y + this.z * v.z);
    }

    public static int innerProduct(Vector3D a, Vector3D b) {
        return (a.x * b.x + a.y * b.y + a.z * b.z);
    }

    public final void outerProduct(Vector3D v) {
        int x = this.y * v.z - this.z * v.y;
        int y = this.z * v.x - this.x * v.z;
        int z = this.x * v.y - this.y * v.x;

        set(x, y, z);
    }

    public static final Vector3D outerProduct(Vector3D a, Vector3D b) {
        Vector3D vec = new Vector3D(a);
        vec.outerProduct(b);
        return vec;
    }

    public final void set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public final void set(Vector3D v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    public final void setX(int x) {
        this.x = x;
    }

    public final void setY(int y) {
        this.y = y;
    }

    public final void setZ(int z) {
        this.z = z;
    }

    public final void unit() {
        // TODO: this should be eventually done entirely in fixed point

        double len = (double)this.x * this.x + (double)this.y * this.y + (double)this.z * this.z;

        if (len == 0)
            return;

        double scale = UNIT / Math.sqrt(len);

        this.x = (int) (this.x * scale);
        this.y = (int) (this.y * scale);
        this.z = (int) (this.z * scale);
    }

    @Override
    public String toString() {
        return "[" + this.x + ", " + this.y + ", " + this.z + "]";
    }
}
