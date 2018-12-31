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

package org.recompile.utility;

import java.io.PrintWriter;

public class SExpWriter {
    private final PrintWriter writer;
    private int level = 0;

    public SExpWriter(PrintWriter printWriter) {
        this.writer = printWriter;
    }

    public void close() {
        this.writer.close();
    }

    public void enter(String name, Object... parameters) {
        indent(this.level);
        this.writer.printf("(%s", name);

        for (Object parameter : parameters) {
            this.writer.printf(" \"%s\"", parameter);
        }

        this.writer.printf("\n");
        this.level++;
    }

    public void leaf(String name, boolean value) {
        indent(this.level);
        this.writer.printf("(%s %d)\n", name, value ? 1 : 0);
    }

    public void leaf(String name, int value) {
        indent(this.level);
        this.writer.printf("(%s %d)\n", name, value);
    }

    public void leaf(String name, String value) {
        indent(this.level);
        this.writer.printf("(%s \"%s\")\n", name, value);
    }

//    public void leaf(String name, Object... parameters) {
//        indent(this.level);
//        this.writer.printf("(%s", name);
//
//        for (Object parameter : parameters) {
//            this.writer.printf(" \"%s\"", parameter);
//        }
//
//        this.writer.printf(")\n");
//    }

    public void leaf(String name, int[] array) {
        indent(this.level);
        this.writer.printf("(%s", name);

        for (int value : array) {
            this.writer.printf(" %d", value);
        }

        this.writer.printf(")\n");
    }

    public void leave() {
        this.level--;
        indent(this.level);
        this.writer.printf(")\n");
    }

    private void indent(int level) {
        for (int i = 0; i < level; i++) {
            this.writer.printf("  ");
        }
    }
}
