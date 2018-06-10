package com.mascotcapsule.micro3d.private_;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by minexew on 12/29/2017.
 */
public class ObjDump {
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
