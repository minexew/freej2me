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

import java.io.*;
import java.util.ArrayList;

import org.recompile.mobile.Mobile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Figure
{
	// TODO: find any documentation of the MB format

	private byte[] figure;

	private Texture[] textures;
	private int selected = 0;
	private int pattern = 0;

	private ActionTable act;
	private int action, frame;

	public Figure(byte[] b) {
		figure = b;
	}

	public Figure(String name) throws IOException
	{
		InputStream stream = Mobile.getPlatform().loader.getResourceAsStream(name);
		try
		{
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			int count=0;
			byte[] data = new byte[4096];
			while (count!=-1)
			{
				count = stream.read(data);
				if(count!=-1) { buffer.write(data, 0, count); }
			}
			figure = buffer.toByteArray();
		}
		catch (Exception e) {  }
	}


	public final void dispose() {
	}

	public final void setPosture(ActionTable act, int action, int frame) {
		this.act = act;
		this.action = action;
		this.frame = frame;
	}

	public final Texture getTexture() {
		return textures[selected];
	}

	public final void setTexture(Texture t) {
		this.textures = new Texture[] { t };
		this.selected = 0;
	}

	public final void setTexture(Texture[] t) {
		this.textures = t;
	}

	public final int getNumTextures() {
		return textures.length;
	}

	public final void selectTexture(int idx) {
		selected = idx;
	}

	public final int getNumPattern() {
		throw new NotImplementedException();
	}

	public final void setPattern(int idx) {
		pattern = idx;
	}

	// Private API

	final int getPattern() {
		return pattern;
	}

	final int getPostureAction() {
		return pattern;
	}

	final ActionTable getPostureActionTable() {
		return act;
	}

	final int getPostureFrame() {
		return frame;
	}

	final byte[] getRawData() {
		return figure;
	}
}
