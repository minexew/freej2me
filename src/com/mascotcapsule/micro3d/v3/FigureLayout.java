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

public class FigureLayout
{
	private ProjectionType projectionType;

	private int scalex=1;
	private int scaley=1;
	private int centerx=1;
	private int centery=1;

	private int pwidth=1;
	private int pheight=1;

	private int selected = 0;

	private AffineTrans[] trans;

	private int zNear;
	private int zFar;
	private int angle, width, height;

	public FigureLayout() {
		this(null, 512, 512, 0, 0);
	}

	public FigureLayout(AffineTrans trans, int sx, int sy, int cx, int cy)
	{
		this.setAffineTrans(trans);
		this.setScale(sx, sy);
		this.setCenter(cx, cy);
	}

	public final AffineTrans getAffineTrans() {
		return trans[selected];
	}

	public final void setAffineTrans(AffineTrans trans)
	{
		this.trans = new AffineTrans[] { trans };
		selected = 0;
	}

	public final void setAffineTransArray(AffineTrans[] trans)
	{
		this.trans = trans;
	}

	public final void setAffineTrans(AffineTrans[] trans) {
		this.trans = trans;
	}

	public final void selectAffineTrans(int i) {
		selected = i;
	}

	public final int getScaleX() {
		return scalex;
	}

	public final int getScaleY() {
		return scaley;
	}

	public final void setScale(int x, int y) {
		scalex = x;
		scaley = y;
		projectionType = ProjectionType.PARALLEL_SCALE;
	}

	public final int getParallelWidth() {
		return pwidth;
	}

	public final int getParallelHeight() {
		return pheight;
	}

	public final void setParallelSize(int w, int h) {
		pwidth = w;
		pheight = h;
		projectionType = ProjectionType.PARALLEL_SIZE;
	}

	public final int getCenterX() {
		return centerx;
	}

	public final int getCenterY() {
		return centery;
	}

	public final void setCenter(int x, int y) {
		centerx = x;
		centery = y;
	}

	public final void setPerspective(int zNear, int zFar, int angle) {
		// Angle is FOV, but is it horizontal or vertical?
		// Or perhaps equivalent-horizontal? (see https://quakewiki.org/wiki/fov)

		projectionType = ProjectionType.PERSPECTIVE_FOV;
		this.zNear = zNear;
		this.zFar = zFar;
		this.angle = angle;
	}

	public final void setPerspective(int zNear, int zFar, int width, int height) {
		projectionType = ProjectionType.PERSPECTIVE_WH;
		this.zNear = zNear;
		this.zFar = zFar;
		this.width = width;
		this.height = height;
	}

	// Private API

	ProjectionType getProjectionType() {
		return projectionType;
	}

	int getPerspectiveAngle() {
		return angle;
	}

	int getPerspectiveWidth() {
		return width;
	}

	int getPerspectiveHeight() {
		return height;
	}

	int getZFar() {
		return zFar;
	}

	int getZNear() {
		return zNear;
	}
}
