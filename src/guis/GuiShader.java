package guis;

import org.lwjgl.util.vector.Matrix4f;
import shaders.ShaderProgram;


public class GuiShader extends ShaderProgram {

    private static final String VERTEX_FILE = "/guis/guiVertexShader.txt";
    private static final String FRAGMENT_FILE = "/guis/guiFragmentShader.txt";

    private int location_transformationMatrix;

    public GuiShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
    }

    @Override
    protected void bindAttribute() {
        super.bindAttribute(0, "position");
    }




}