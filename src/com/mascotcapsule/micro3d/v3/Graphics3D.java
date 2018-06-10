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

import com.mascotcapsule.micro3d.private_.ObjDump;

import javax.microedition.lcdui.Graphics;

public class Graphics3D
{
	/*public static final int COMMAND_LIST_VERSION_1_0 = 254; //-2 ?
	public static final int COMMAND_END = Integer.MIN_VALUE;
	public static final int COMMAND_NOP = 129;
	public static final int COMMAND_FLUSH = 130;
	public static final int COMMAND_ATTRIBUTE = 131;
	public static final int COMMAND_CLIP = 132;
	public static final int COMMAND_CENTER = 133;
	public static final int COMMAND_TEXTURE_INDEX = 134;
	public static final int COMMAND_AFFINE_INDEX = 135;
	public static final int COMMAND_PARALLEL_SCALE = 144;
	public static final int COMMAND_PARALLEL_SIZE = 145;
	public static final int COMMAND_PERSPECTIVE_FOV = 146;
	public static final int COMMAND_PERSPECTIVE_WH = 147;
	public static final int COMMAND_AMBIENT_LIGHT = 160;
	public static final int COMMAND_DIRECTION_LIGHT = 161;
	public static final int COMMAND_THRESHOLD = 175;
	public static final int PRIMITVE_POINTS = 16;
	public static final int PRIMITVE_LINES = 32;
	public static final int PRIMITVE_TRIANGLES = 48;
	public static final int PRIMITVE_QUADS = 64;
	public static final int PRIMITVE_POINT_SPRITES = 80;
	public static final int POINT_SPRITE_LOCAL_SIZE = 0;
	public static final int POINT_SPRITE_PIXEL_SIZE = 1;
	public static final int POINT_SPRITE_PERSPECTIVE = 0;
	public static final int POINT_SPRITE_NO_PERS = 2;
	public static final int ENV_ATTR_LIGHTING = 1;
	public static final int ENV_ATTR_SPHERE_MAP = 2;
	public static final int ENV_ATTR_TOON_SHADING = 4;
	public static final int ENV_ATTR_SEMI_TRANSPARENT = 8;
	public static final int PATTR_LIGHTING = 1;
	public static final int PATTR_SPHERE_MAP = 2;
	public static final int PATTR_COLORKEY = 16;
	public static final int PATTR_BLEND_NORMAL = 0;
	public static final int PATTR_BLEND_HALF = 32;
	public static final int PATTR_BLEND_ADD = 64;
	public static final int PATTR_BLEND_SUB = 96;
	public static final int PDATA_NORMAL_NONE = 0;
	public static final int PDATA_NORMAL_PER_FACE = 2;
	public static final int PDATA_NORMAL_PER_VERTEX = 3;
	public static final int PDATA_COLOR_NONE = 0;
	public static final int PDATA_COLOR_PER_COMMAND = 4;
	public static final int PDATA_COLOR_PER_FACE = 8;
	public static final int PDATA_TEXURE_COORD_NONE = 0;
	public static final int PDATA_POINT_SPRITE_PARAMS_PER_CMD = 16;
	public static final int PDATA_POINT_SPRITE_PARAMS_PER_FACE = 32;
	public static final int PDATA_POINT_SPRITE_PARAMS_PER_VERTEX = 48;
	public static final int PDATA_TEXURE_COORD = 48;*/

	public static final int PRIMITVE_TRIANGLES = 0x03000000;
	public static final int PRIMITVE_QUADS = 0x04000000;
	public static final int PRIMITVE_POINT_SPRITES = 0x05000000;

	public static final int PDATA_POINT_SPRITE_PARAMS_PER_CMD = 0x1000;
	public static final int PDATA_POINT_SPRITE_PARAMS_PER_FACE = 0x2000;
	public static final int PDATA_POINT_SPRITE_PARAMS_PER_VERTEX = 0x3000;

	public static final int PDATA_TEXURE_COORD = 0x3000;

	public static final int PATTR_BLEND_ADD = 0x40;

	private int skipframes = 30;
	private com.mascotcapsule.micro3d.private_.ObjDump objdump = new ObjDump();

	public void flush() {
		int dummy = 0;
	}

	public void renderPrimitives(Texture texture,
								 int x,
								 int y,
								 FigureLayout layout,
								 Effect3D effect,
								 int command,
								 int numPrimitives,
								 int[] vertexCoords,
								 int[] normals,
								 int[] textureCoords,
								 int[] colors) {
		System.out.printf("renderPrimitives(%s, x=%d, y=%d, layout=..., effect=..., command=%08Xh, numPrimitives=%d, " +
						"vertexCoords=[%d]{...}, normals=[%d]{...}, textureCoords=[%d]{...}, colors=[%d]={...})\n", texture, x, y, command, numPrimitives,
				vertexCoords.length, normals.length, textureCoords.length, colors.length);

		int opcode = command & 0xFF000000;
		int flags = command & 0x00FFFFFF;

		if ((opcode & ~(PRIMITVE_POINT_SPRITES | PRIMITVE_QUADS | PRIMITVE_TRIANGLES)) != 0)
			throw new IllegalArgumentException();

		switch (opcode) {
			case PRIMITVE_QUADS:
				System.out.println("PRIMITVE_QUADS");

				if ((flags & ~(PDATA_TEXURE_COORD | PATTR_BLEND_ADD)) != 0)
					throw new IllegalArgumentException();

				if ((flags & PDATA_TEXURE_COORD) != 0)
					System.out.println("PDATA_TEXURE_COORD");

				if ((flags & PATTR_BLEND_ADD) != 0)
					System.out.println("PATTR_BLEND_ADD");

                for (int p = 0; p < numPrimitives; p++) {
					objdump.vertex(vertexCoords[p * 12 + 0] / 4096.0f, vertexCoords[p * 12 + 1] / 4096.0f, vertexCoords[p * 12 + 2] / 4096.0f);
					objdump.vertex(vertexCoords[p * 12 + 3] / 4096.0f, vertexCoords[p * 12 + 4] / 4096.0f, vertexCoords[p * 12 + 5] / 4096.0f);
					objdump.vertex(vertexCoords[p * 12 + 6] / 4096.0f, vertexCoords[p * 12 + 7] / 4096.0f, vertexCoords[p * 12 + 8] / 4096.0f);
					objdump.vertex(vertexCoords[p * 12 + 9] / 4096.0f, vertexCoords[p * 12 + 10] / 4096.0f, vertexCoords[p * 12 + 11] / 4096.0f);
					objdump.quad(1, 2, 3, 4);
                }
				break;

			case PRIMITVE_POINT_SPRITES:
				System.out.println("PRIMITVE_POINT_SPRITES");

				switch (command & 0x3000) {
					case PDATA_POINT_SPRITE_PARAMS_PER_CMD:
						System.out.println("PDATA_POINT_SPRITE_PARAMS_PER_CMD");

						//System.out.printf("color: (%d, %d, %d)\tnormal: (%d, %d, %d)\n", colors[0], colors[1], colors[2], normals[0], normals[1], normals[2]);

						System.out.printf("color: #%06X\n", colors[0]);

						for (int p = 0; p < numPrimitives; p++) {
							//System.out.printf("  (%d, %d, %d)\n", vertexCoords[p * 3], vertexCoords[p * 3 + 1], vertexCoords[p * 3 + 2]);
							objdump.vertex(vertexCoords[p * 3] / 4096.0f, vertexCoords[p * 3 + 1] / 4096.0f, vertexCoords[p * 3 + 2] / 4096.0f);
							objdump.point(1);
						}
						break;

                    case PDATA_POINT_SPRITE_PARAMS_PER_FACE:
                        System.out.println("PDATA_POINT_SPRITE_PARAMS_PER_FACE");
                        break;

                    case PDATA_POINT_SPRITE_PARAMS_PER_VERTEX:
                        System.out.println("PDATA_POINT_SPRITE_PARAMS_PER_VERTEX");
                        break;

					default:
						throw new IllegalArgumentException();
				}

				flags &= ~0x3000;

				if ((flags & ~(PATTR_BLEND_ADD)) != 0)
					throw new IllegalArgumentException();

				break;

			default:
				throw new IllegalArgumentException();
		}
	}


	public void renderPrimitives(Figure fig,
								 int p1,
								 int p2,
								 FigureLayout figLayout,
								 Effect3D paramEffect3D,
								 int p3, int p4, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4) {
		int dummy = 0;
	}

	public void drawCommandList(Figure fig, int p1, int p2, FigureLayout figLayout, Effect3D e3d, int[] p3) {int dummy = 0; }

	public void renderFigure(Figure fig, int p1, int p2, FigureLayout figLayout, Effect3D e3d) {
		//System.out.printf("renderFigure(fig=%s, x=%d, y=%d, layout=..., effect=..., command=%08Xh, numPrimitives=%d, " +
		//		"vertexCoords=[%d]{...}, normals=[%d]{...}, textureCoords=[%d]{...}, colors=[%d]={...})\n", fig, x, y, command, numPrimitives,
	}



	public void drawFigure(Figure fig, int p1, int p2, FigureLayout figLayout, Effect3D e3d) {int dummy = 0; }

	public void dispose() {int dummy = 0; }

	public void bind(Graphics g) {
		System.out.println("Graphics3D bind");
		if (--skipframes == 0)
			objdump.begin();
	}

	public void release(Graphics g) {
		System.out.println("Graphics3D release");
		if (skipframes == 0) {
			objdump.end();
			System.exit(0);
		}
	}
}
