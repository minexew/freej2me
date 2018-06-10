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

public class Light {
    private Vector3D dir;
    private Vector3D parDir;
    private int dirIntensity;
    private int ambIntensity;
    private int parIntensity;

    public Light() {
        this(new Vector3D(0, 0, 4096), 4096, 0);
    }

    public Light(Vector3D dir, int dirIntensity, int ambIntensity) {
        this.dir = dir;
        this.dirIntensity = dirIntensity;
        this.ambIntensity = ambIntensity;
    }

    public final int getAmbientIntensity() {
        return ambIntensity;
    }

    @Deprecated
    public final int getAmbIntensity() {
        return ambIntensity;
    }

    public final Vector3D getDirection() {
        return dir;
    }

    @Deprecated
    public final int getDirIntensity() {
        return dirIntensity;
    }

    public final Vector3D getParallelLightDirection() {
        return parDir;
    }

    public final int getParallelLightIntensity() {
        return parIntensity;
    }

    public final void setAmbientIntensity(int p) {
        ambIntensity = p;
    }

    @Deprecated
    public final void setAmbIntensity(int value) {
        ambIntensity = value;
    }

    @Deprecated
    public final void setDirection(Vector3D v) {
        dir.x = v.x;
        dir.y = v.y;
        dir.z = v.z;
    }

    @Deprecated
    public final void setDirIntensity(int value) {
        dirIntensity = value;
    }

    public final void setParallelLightDirection(Vector3D v) {
        parDir.x = v.x;
        parDir.y = v.y;
        parDir.z = v.z;
    }

    public final void setParallelLightIntensity(int value) {
        parIntensity = value;
    }
}
