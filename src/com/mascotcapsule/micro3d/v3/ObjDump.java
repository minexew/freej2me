/*
 * Copyright (c) 2017 minexew
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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by minexew on 12/29/2017.
 */
class ObjDump {
    private PrintWriter writer;

    public void begin() {
        try {
            this.writer = new PrintWriter("dump.obj", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void end() {
        this.writer.close();
    }

    public void point(int a) {
        if (this.writer != null)
            this.writer.printf("p %d\n", a);
    }

    public void vertex(float x, float y, float z) {
        if (this.writer != null)
            this.writer.printf("v %f %f %f\n", x, y, z);
    }

    public void quad(int a, int b, int c, int d) {
        if (this.writer != null)
            this.writer.printf("f %d %d %d %d\n", a, b, c, d);
    }
}
