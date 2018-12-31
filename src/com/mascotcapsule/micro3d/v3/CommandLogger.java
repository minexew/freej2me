/*
 * Copyright (c) 2018 minexew
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.mascotcapsule.micro3d.v3;

import org.recompile.utility.SExpWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class CommandLogger {
    private SExpWriter writer;

    private Set<Figure> figures = new HashSet<>();
    private Set<Texture> textures = new HashSet<>();

    public void begin() {
        try {
            this.writer = new SExpWriter(new PrintWriter("commandlist.txt", "UTF-8"));
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void end() {
        this.writer.close();
    }

    public void drawFigure(Figure figure, int x, int y, FigureLayout layout, Effect3D effect) {
        if (this.writer == null) {
            return;
        }

        this.updateFigureData(figure);

        writer.enter("drawFigure");
        this.emitFigure(figure);
        writer.leaf("x", x);
        writer.leaf("y", y);
        this.emitFigureLayout(layout);
        this.emitEffect3D(effect);
        writer.leave();
    }

    public void renderFigure(Figure figure, int x, int y, FigureLayout layout, Effect3D effect) {
        if (this.writer == null) {
            return;
        }

        this.updateFigureData(figure);

        writer.enter("renderFigure");
        this.emitFigure(figure);
        writer.leaf("x", x);
        writer.leaf("y", y);
        this.emitFigureLayout(layout);
        this.emitEffect3D(effect);
        writer.leave();
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
        if (this.writer == null) {
            return;
        }

        this.updateTextureData(texture);

        writer.enter("renderPrimitives");
        writer.leaf("texture", Integer.toHexString(texture.hashCode()));
        writer.leaf("x", x);
        writer.leaf("y", y);
        this.emitFigureLayout(layout);
        this.emitEffect3D(effect);
        writer.leaf("command", commandToString(command));
        writer.leaf("numPrimitives", numPrimitives);
        writer.leaf("vertexCoords", vertexCoords);
        writer.leaf("normals", normals);
        writer.leaf("textureCoords", textureCoords);
        writer.leaf("colors", colors);
        writer.leave();
    }

    private static final Map<Integer, String> opcodeNames = Map.of(
            Graphics3D.PRIMITVE_TRIANGLES, "PRIMITVE_TRIANGLES",
            Graphics3D.PRIMITVE_QUADS, "PRIMITVE_QUADS",
            Graphics3D.PRIMITVE_POINT_SPRITES, "PRIMITVE_POINT_SPRITES"
    );

    private static final Map<Integer, String> pointSpritesPdata = Map.of(
            Graphics3D.PDATA_POINT_SPRITE_PARAMS_PER_CMD, "PDATA_POINT_SPRITE_PARAMS_PER_CMD",
            Graphics3D.PDATA_POINT_SPRITE_PARAMS_PER_FACE, "PDATA_POINT_SPRITE_PARAMS_PER_FACE",
            Graphics3D.PDATA_POINT_SPRITE_PARAMS_PER_VERTEX, "PDATA_POINT_SPRITE_PARAMS_PER_VERTEX"
    );

    private static final Map<Integer, String> quadsPdata = Map.of(
            Graphics3D.PDATA_TEXURE_COORD, "PDATA_TEXURE_COORD",
            Graphics3D.PDATA_TEXURE_COORD_NONE, "PDATA_TEXURE_COORD_NONE"
    );

    private String commandToString(int command) {
        int opcode = command & 0xFF000000;
        int flags = command & 0x00FFFFFF;

        String s = Optional.of(opcodeNames.get(opcode)).orElseThrow(IllegalArgumentException::new);

        switch (opcode) {
            case Graphics3D.PRIMITVE_POINT_SPRITES:
                s += "|" + Optional.of(pointSpritesPdata.get(command & 0x3000)).orElseThrow(IllegalArgumentException::new);
                flags &= ~0x3000;

                break;

            case Graphics3D.PRIMITVE_QUADS:
                s += "|" + Optional.of(quadsPdata.get(command & 0x3000)).orElseThrow(IllegalArgumentException::new);
                flags &= ~0x3000;
                break;

            default:
                throw new IllegalArgumentException();
        }

        if ((flags & Graphics3D.PATTR_BLEND_ADD) != 0) {
            s += "|PATTR_BLEND_ADD";
        }
        flags = (flags & ~Graphics3D.PATTR_BLEND_ADD);

        if (flags != 0) {
            throw new IllegalArgumentException();
        }

        return s;
    }

    private void emitActionTable(ActionTable act) {
        if (act != null) {
            writer.leaf("actionTable", Integer.toHexString(act.hashCode()));
        }
        else {
            writer.leaf("actionTable", "null");
        }
    }

    private void emitAffineTrans(AffineTrans affineTrans) {
        int[] matrix = new int[12];
        affineTrans.get(matrix);
        writer.leaf("affineTrans", matrix);
    }

    private void emitEffect3D(Effect3D effect) {
        writer.enter("effect");
        this.emitLight(effect.getLight());
        writer.leaf("shadingType", effect.getShadingType() == Effect3D.NORMAL_SHADING ? "NORMAL_SHADING" : "TOON_SHADING");
        this.emitTexture(effect.getSphereTexture());
        writer.leaf("toonHigh", effect.getToonHigh());
        writer.leaf("toonLow", effect.getToonLow());
        writer.leaf("toonThreshold", effect.getToonThreshold());
        writer.leaf("transparency", effect.isTransparency());
        writer.leave();
    }

    private void emitFigure(Figure figure) {
        writer.enter("figure", Integer.toHexString(figure.hashCode()));
        this.emitTexture(figure.getTexture());
        writer.leaf("pattern", figure.getPattern());
        this.emitActionTable(figure.getPostureActionTable());
        writer.leaf("postureAction", figure.getPostureAction());
        writer.leaf("postureFrame", figure.getPostureFrame());
        writer.leave();
    }

    private void emitFigureLayout(FigureLayout layout) {
        writer.enter("layout");
        this.emitAffineTrans(layout.getAffineTrans());
        writer.leaf("projection", layout.getProjectionType().toString());
        writer.leaf("center", new int[] { layout.getCenterX(), layout.getCenterY() });

        switch (layout.getProjectionType()) {
            case PARALLEL_SCALE:
                writer.leaf("scale", new int[] { layout.getScaleX(), layout.getScaleY() });
                break;

            case PARALLEL_SIZE:
                writer.leaf("parallelSize", new int[] { layout.getParallelWidth(), layout.getParallelHeight() });
                break;

            case PERSPECTIVE_FOV:
                writer.leaf("perspective", new int[] { layout.getZNear(), layout.getZFar(), layout.getPerspectiveAngle() });
                break;

            case PERSPECTIVE_WH:
                writer.leaf("perspective", new int[] { layout.getZNear(), layout.getZFar(), layout.getPerspectiveWidth(), layout.getPerspectiveHeight() });
                break;
        }

        writer.leave();
    }

    private void emitLight(Light light) {
        if (light != null) {
            writer.enter("light");
            writer.leaf("ambientIntensity", light.getAmbientIntensity());
            writer.leaf("parallelLightDirection", new int[] {light.getParallelLightDirection().x, light.getParallelLightDirection().y, light.getParallelLightDirection().z});
            writer.leaf("parallelLightIntensity", light.getParallelLightIntensity());
            writer.leave();
        }
        else {
            writer.leaf("light", "null");
        }
    }

    private void emitTexture(Texture texture) {
        if (texture != null) {
            writer.leaf("texture", Integer.toHexString(texture.hashCode()));
        }
        else {
            writer.leaf("texture", "null");
        }
    }

    // https://stackoverflow.com/a/13006907
    // Supposedly this is really slow, but does it matter?
    private static String stringify(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for(byte b: a)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    private void updateFigureData(Figure figure) {
        if (!figures.contains(figure)) {
            writer.enter("Figure", Integer.toHexString(figure.hashCode()));
            writer.leaf("data", stringify(figure.getRawData()));
            writer.leave();

            figures.add(figure);
        }

        if (figure.getTexture() != null) {
            this.updateTextureData(figure.getTexture());
        }
    }

    private void updateTextureData(Texture texture) {
        if (!textures.contains(texture)) {
            writer.enter("Texture", Integer.toHexString(texture.hashCode()));
            writer.leaf("data", stringify(texture.getRawData()));
            writer.leave();

            textures.add(texture);
        }
    }
}
