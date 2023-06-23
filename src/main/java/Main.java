import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;



public class Main {
    private Window window =
            new Window
    (800,800,"Hello World");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private ArrayList<Object> objects1
            = new ArrayList<>();
    private ArrayList<Object> objects2
            = new ArrayList<>();

    private ArrayList<Object> objectsRectangle
            = new ArrayList<>();

    private ArrayList<Object> objectsPointsControl
            = new ArrayList<>();

    private MouseInput mouseInput;
    int countDegree = 0;

    List<Vector3f> verticesKamera;

    boolean pressed = false;

    private static int bunnyDisplayList;

    float rotation = 0.0f;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();

    public void init(){
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        mouseInput = window.getMouseInput();
        camera.setPosition(0,0.3f,2f);
//        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(180f));
        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(0.0f));


        objects1.add(new Bola(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(List.of(
                        new Vector3f(-0.5f,0.5f,0.0f),
                        new Vector3f(-0.5f,-0.5f,0.0f),
                        new Vector3f(0.5f,-0.5f,0.0f),
                        new Vector3f(0.5f,0.5f,0.0f)
                )),
                new Vector4f(0.0f,1.0f,1.0f,1.0f),
                Arrays.asList(0.0f,0.0f,0.0f),
                0.125f,
                0.125f,
                0.125f,
                36,
                18,"box"
        ));


        ObjectLoader objectBotol = new ObjectLoader("D:\\Kuliah Semester 4\\Grafkom B\\Code\\Project_UAS\\Project_UAS\\Project_UAS_Grafkom\\resources\\aset\\Bottle\\Bottle.obj", "obj");
        objects.add(new Sphere(
                Arrays.asList(
                        //shaderFile lokasi menyesuaikan objectnya
                        new ShaderProgram.ShaderModuleData
                                ("resources/shaders/scene.vert"
                                        , GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData
                                ("resources/shaders/scene.frag"
                                        , GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(
                        List.of(
                                new Vector3f(-0.0f,0.0f,0.0f),
                                new Vector3f(-0.0f,-0.0f,0.0f),
                                new Vector3f(0.0f,-0.0f,0.0f),
                                new Vector3f(0.0f,0.0f,0.0f)
                        )
                ), new Vector4f(1.0f,1.0f,0.0f,1.0f), Arrays.asList(0.0f,0.0f,0f), 1f,1f,1f,36,18,"box"
        ));
        ((Sphere)objects.get(0)).setVertices(objectBotol.vertices);
        ((Sphere)objects.get(0)).setNormal(objectBotol.normals);
        ((Sphere)objects.get(0)).setIndicies(objectBotol.indicies);

        objects.get(0).scaleObject(0.05f,0.05f,0.05f);


    }

    public void input(){
        float move = 0.01f;
//        if (window.isKeyPressed(GLFW_KEY_A)){
//            camera.moveLeft(0.1f);
//        }
//        if (window.isKeyPressed(GLFW_KEY_D)){
//            camera.moveRight(0.1f);
//        }
//        if (window.isKeyPressed(GLFW_KEY_W)){
//            camera.moveUp(0.1f);
//        }
//        if (window.isKeyPressed(GLFW_KEY_S)){
//            camera.moveDown(0.1f);
//        }
//        if (window.isKeyPressed(GLFW_KEY_X)){
//            camera.moveForward(0.1f);
//
//        }
//        if (window.isKeyPressed(GLFW_KEY_C)){
//            camera.moveBackwards(0.1f);
//        }
        if (window.isKeyPressed(GLFW_KEY_W)) {
            objects.get(0).translateObject(0.0f, move, 0.0f);
//            camera.moveUp(move);
//            Vector3f posObject = objects.get(0).model.transformPosition(new Vector3f());
//            camera.setPosition(-posObject.x, -posObject.y, -posObject.z-2.0f);
//            camera.setPosition(posObject.x, posObject.y, posObject.z+2.0f);
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

            if (rotation >= 360.0) {
                rotation = 0.0f;
            }
            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            objects.get(0).translateObject(0.0f, -move, 0.0f);
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

            if (rotation >= 360.0) {
                rotation = 0.0f;
            }
            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//            Vector3f posObject = objects.get(0).model.transformPosition(new Vector3f());
////            camera.moveDown(move);
//            camera.setPosition(-posObject.x, -posObject.y, -posObject.z-2.0f);
//            camera.setPosition(posObject.x, posObject.y, posObject.z+2.0f);

        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            objects.get(0).translateObject(-move, 0.0f, 0.0f);
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

            if (rotation >= 360.0) {
                rotation = 0.0f;
            }
            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//            camera.moveLeft(move);
//            Vector3f posObject = objects.get(0).model.transformPosition(new Vector3f());
//            camera.setPosition(-posObject.x, -posObject.y, -posObject.z-2.0f);
//            camera.setPosition(posObject.x, posObject.y, posObject.z+2.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            objects.get(0).translateObject(move, 0.0f, 0.0f);
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
            float posX = camera.getPosition().x;
            float posY = camera.getPosition().y;
            float posZ = camera.getPosition().z;

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

            if (rotation >= 360.0) {
                rotation = 0.0f;
            }
            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//            camera.moveRight(move);
//            Vector3f posObject = objects.get(0).model.transformPosition(new Vector3f());
//            camera.setPosition(-posObject.x, -posObject.y, -posObject.z-2.0f);
//            camera.setPosition(posObject.x, posObject.y, posObject.z+2.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_Q)) {
            objects.get(0).translateObject(0.0f, 0.0f, move);
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
            float posX = camera.getPosition().x;
            float posY = camera.getPosition().y;
            float posZ = camera.getPosition().z;

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

            if (rotation >= 360.0) {
                rotation = 0.0f;
            }
            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//            camera.moveRight(move);
//            Vector3f posObject = objects.get(0).model.transformPosition(new Vector3f());
//            camera.setPosition(-posObject.x, -posObject.y, -posObject.z-2.0f);
//            camera.setPosition(posObject.x, posObject.y, posObject.z+2.0f);
        }
        if (window.isKeyPressed(GLFW_KEY_F)) {
            objects.get(0).translateObject(0.0f, 0.0f, -move);
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
            float posX = camera.getPosition().x;
            float posY = camera.getPosition().y;
            float posZ = camera.getPosition().z;

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

            if (rotation >= 360.0) {
                rotation = 0.0f;
            }
            camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//            camera.moveRight(move);
//            Vector3f posObject = objects.get(0).model.transformPosition(new Vector3f());
//            camera.setPosition(-posObject.x, -posObject.y, -posObject.z-2.0f);
//            camera.setPosition(posObject.x, posObject.y, posObject.z+2.0f);
        }
        if(mouseInput.isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f),
                    (float)Math.toRadians(displayVec.y * 0.1f));
        }
        if(window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()- (window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }

        if(window.isKeyPressed(GLFW_KEY_R)){
            Vector3f posObj = objects.get(0).model.transformPosition(new Vector3f());
            float posX = camera.getPosition().x;
            float posY = camera.getPosition().y;
            float posZ = camera.getPosition().z;

            ArrayList<Vector3f> verticesK = new ArrayList<>(List.of());

            for(float i = 0;i<360;i+=1) {
                float x = (float) (posObj.x + 2f * Math.sin(Math.toRadians(i)));
                float z = (float) (posObj.z + 2f * Math.cos(Math.toRadians(i)));
                float y =(float) posObj.y+0.3f;
                verticesK.add(new Vector3f(x, y, z));
            }
            camera.setPosition(verticesK.get(0).x, verticesK.get(0).y, verticesK.get(0).z);

                camera.setPosition(-posX, -posY, -posZ);
                camera.addRotation(0.0f, (float) Math.toRadians(-1f));
                camera.setPosition(posX, posY, posZ);

                rotation += 1f;

                if (rotation >= 360.0) {
                    rotation = 0.0f;
                }
                camera.setPosition(verticesK.get((int)rotation).x,verticesK.get((int)rotation).y, verticesK.get((int)rotation).z);
//                camera.moveLeft(0.7f);
            }
        }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();
            input();
//            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
//            glLoadIdentity();
//            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
//            glCallList(bunnyDisplayList);
            //code
            for(Object object: objects1){
                object.draw(camera,projection);
            }
            for(Object object: objects){
                object.draw(camera,projection);
            }
            for(Object object: objects2){
                object.draw(camera,projection);
            }

//            for(Object object: objectsRectangle){
//                object.draw();
//            }
//            for(Object object: objectsPointsControl){
//                object.drawLine();
//            }

            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
    public static void main(String[] args) {
        new Main().run();
    }
}