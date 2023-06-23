package Engine;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Sphere extends Circle{
    float radiusZ;
    int stackCount;
    int sectorCount;
    List<Integer> index;
    int ibo;
    List<Vector3f> normal;
    int nbo;



    public Sphere(List<ShaderModuleData> shaderModuleDataList, List<Vector3f> vertices, Vector4f color, List<Float> centerPoint, Float radiusX, Float radiusY, Float radiusZ,
                  int sectorCount,int stackCount, String type) {
        super(shaderModuleDataList, vertices, color, centerPoint, radiusX, radiusY);
        this.radiusZ = radiusZ;
        this.stackCount = stackCount;
        this.sectorCount = sectorCount;
        vertices.clear();
        chooseShape(type);
//        createHyperboloid1side();
//        createHyeperboloid2side();
//        createElipticCone();
//        createElipticParaboloid();
//        createHyperboloidParaboloid();

        setupVAOVBO();
    }

    public Sphere() {

    }
    public void chooseShape(String type){
        if (type.equals("box")) {
            createBox();
        }else if (type.equals("ellipsoid")) {
            createEllipsoid();
        }
    }
    public List<Vector3f> getNormal() {
        return normal;
    }

    public void setNormal(List<Vector3f> normal) {
        this.normal = normal;
        setupVAOVBO();
    }


    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
        setupVAOVBO();
    }

    public void setIndicies(List<Integer> indicies){
        this.index = indicies;
        setupVAOVBO();
        //ibo
        ibo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Utils.listoInt(index),GL_STATIC_DRAW);
    }

    public void createBox(){
        Vector3f temp = new Vector3f();
        ArrayList<Vector3f> tempVertices = new ArrayList<>();
        //TITIK 1
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 2
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 3
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 4
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) - radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 5
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 6
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) + radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 7
        temp.x = centerPoint.get(0) + radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();
        //TITIK 8
        temp.x = centerPoint.get(0) - radiusX / 2.0f;
        temp.y = centerPoint.get(1) - radiusY / 2.0f;
        temp.z = centerPoint.get(2) + radiusZ / 2.0f;
        tempVertices.add(temp);
        temp = new Vector3f();

        vertices.clear();
        //kotak yg sisi belakang
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(3));
        //kotak yg sisi depan
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(7));
        //kotak yg sisi kiri
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(4));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(3));
        //kotak yg sisi kanan
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(6));
        vertices.add(tempVertices.get(2));
        //kotak yg sisi atas
        vertices.add(tempVertices.get(0));
        vertices.add(tempVertices.get(1));
        vertices.add(tempVertices.get(5));
        vertices.add(tempVertices.get(4));
        //kotak yg sisi bawah
        vertices.add(tempVertices.get(3));
        vertices.add(tempVertices.get(2));
        vertices.add(tempVertices.get(7));
        vertices.add(tempVertices.get(6));

        normal = new ArrayList<>(Arrays.asList(
                //belakang
                new Vector3f(0f,0f,-1.0f),
                new Vector3f(0f,0f,-1.0f),
                new Vector3f(0f,0f,-1.0f),
                new Vector3f(0f,0f,-1.0f),
                new Vector3f(0f,0f,-1.0f),
                new Vector3f(0f,0f,-1.0f),
                //depan
                new Vector3f(0f,0f,1.0f),
                new Vector3f(0f,0f,1.0f),
                new Vector3f(0f,0f,1.0f),
                new Vector3f(0f,0f,1.0f),
                new Vector3f(0f,0f,1.0f),
                new Vector3f(0f,0f,1.0f),
                //kiri
                new Vector3f(-1f,0f,0f),
                new Vector3f(-1f,0f,0f),
                new Vector3f(-1f,0f,0f),
                new Vector3f(-1f,0f,0f),
                new Vector3f(-1f,0f,0f),
                new Vector3f(-1f,0f,0f),
                //kanan
                new Vector3f(1f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(1f,0f,0f),
                new Vector3f(1f,0f,0f),
                //bawah
                new Vector3f(0f,-1f,0.0f),
                new Vector3f(0f,-1f,0.0f),
                new Vector3f(0f,-1f,0.0f),
                new Vector3f(0f,-1f,0.0f),
                new Vector3f(0f,-1f,0.0f),
                new Vector3f(0f,-1f,0.0f),
                //atas
                new Vector3f(0f,1f,0f),
                new Vector3f(0f,1f,0f),
                new Vector3f(0f,1f,0f),
                new Vector3f(0f,1f,0f),
                new Vector3f(0f,1f,0f),
                new Vector3f(0f,1f,0f)
        ));
    }

    public void drawSetup(Camera camera, Projection projection) {
        super.drawSetup(camera,projection);

        // Bind NBO
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glVertexAttribPointer(1, 3,
                GL_FLOAT,
                false,
                0, 0);

//        uniformsMap.setUniform("lightColor", new Vector3f(1f,1f,0f));
//        uniformsMap.setUniform("lightPos", new Vector3f(1f,1f,0f));

        //directional Light
        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f, -1f, -0.3f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f, 0.4f, 0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f, 0.5f, 0.5f));

        //posisi pointLight
        Vector3f[] _pointLightPositions = {
                new Vector3f(0.7f, 0.2f, 2.0f),
                new Vector3f(2.3f, -3.3f, -4.0f),
                new Vector3f(-4.0f, 2.0f, -12.0f),
                new Vector3f(0.0f, 0.0f, -3.0f)
        };
        for(int i = 0; i<_pointLightPositions.length; i++){
            uniformsMap.setUniform("pointLights["+i+"].position", _pointLightPositions[i]);
            uniformsMap.setUniform("pointLights["+i+"].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
            uniformsMap.setUniform("pointLights["+i+"].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
            uniformsMap.setUniform("pointLights["+i+"].specular", new Vector3f(1.0f, 1.0f, 1.0f));
            uniformsMap.setUniform("pointLights["+i+"].constant",1.0f);
            uniformsMap.setUniform("pointLights["+i+"].linear", 0.09f);
            uniformsMap.setUniform("pointLights["+i+"].quadratic", 0.032f);
        }

        //spotLight
        uniformsMap.setUniform("spotLight.position", camera.getPosition());
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f, 0.0f, 0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", (float) Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("spotLight.outerCutOff", (float) Math.cos(Math.toRadians(12.5f)));

        uniformsMap.setUniform("viewPos", camera.getPosition());
    }

    public void setupVAOVBO(){
        super.setupVAOVBO();
//        uniformsMap.createUniform("lightColor");
//        uniformsMap.createUniform("lightPos");
        //nbo
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normal),
                GL_STATIC_DRAW);


    }

    public void draw(Camera camera, Projection projection){
        drawSetup(camera,projection);
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
//        glDrawArrays(GL_POLYGON,
//                0,
//                vertices.size());
        //draw indicies
        glDrawElements(GL_TRIANGLES,
                index.size(),GL_UNSIGNED_INT,0);
        for (Object child: childObject){
            child.draw(camera,projection);
        }
    }


    public void createSphere(String type){
        vertices.clear();
        float pi = (float)Math.PI;
        float sectorStep;
        if (type.equals("Sphere half")){
            sectorStep = (float)Math.PI / sectorCount;
        }else {
            sectorStep = 2*(float)Math.PI / sectorCount;
        }
        float stackStep = (float)Math.PI / stackCount;
        float sectorAngle, StackAngle, x, y, z;

        for (int i = 0; i <= stackCount; ++i)
        {
            StackAngle = pi / 2 - i * stackStep;
            x = radiusX * (float)Math.cos(StackAngle);
            y = radiusY * (float)Math.cos(StackAngle);
            z = radiusZ * (float)Math.sin(StackAngle);

            for (int j = 0; j <= sectorCount; ++j)
            {
                sectorAngle = j * sectorStep;
                Vector3f temp_vector = new Vector3f();
                temp_vector.x = centerPoint.get(0) + x * (float)Math.cos(sectorAngle);
                temp_vector.y = centerPoint.get(1) + y * (float)Math.sin(sectorAngle);
                temp_vector.z = centerPoint.get(2) + z;
                vertices.add(temp_vector);
            }
        }
//        vertices.clear();
//        ArrayList<Vector3f> temp = new ArrayList<>();
//        int stackCount = 18, sectorCount = 36;
//        float x,y,z,xy,nx,ny,nz;
//        float sectorStep = (float)(2* Math.PI )/ sectorCount; //sector count
//        float stackStep = (float)Math.PI / stackCount; // stack count
//        float sectorAngle, stackAngle;
//
//        for(int i=0;i<=stackCount;i++){
//            stackAngle = (float)Math.PI/2 - i * stackStep;
//            xy = (float) (0.5f * Math.cos(stackAngle));
//            z = (float) (0.5f * Math.sin(stackAngle));
//            for(int j=0;j<sectorCount;j++){
//                sectorAngle = j * sectorStep;
//                x = (float) (xy * Math.cos(sectorAngle));
//                y = (float) (xy * Math.sin(sectorAngle));
//                temp.add(new Vector3f(x,y,z));
//            }
//        }
//        vertices = temp;
//
//        int k1, k2;
//        ArrayList<Integer> temp_indices = new ArrayList<>();
//        for(int i=0;i<stackCount;i++){
//            k1 = i * (sectorCount + 1);
//            k2 = k1 + sectorCount + 1;
//
//            for(int j=0;j<sectorCount;++j, ++k1, ++k2){
//                if(i != 0){
//                    temp_indices.add(k1);
//                    temp_indices.add(k2);
//                    temp_indices.add(k1+1);
//                }
//                if(i!=(18-1)){
//                    temp_indices.add(k1+1);
//                    temp_indices.add(k2);
//                    temp_indices.add(k2+1);
//                }
//            }
//        }
//        this.index = temp_indices;
//        ibo = glGenBuffers();
//        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
//        glBufferData(GL_ELEMENT_ARRAY_BUFFER,Utils.listoInt(index),GL_STATIC_DRAW);

    }

    public void createEllipsoid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(Math.cos(v) * Math.cos(u));
                float y = 0.5f * (float)(Math.cos(v) * Math.sin(u));
                float z = 0.5f * (float)(Math.sin(v));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices.clear();
        vertices = temp;
    }
    public void createTabung(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(float v = -radiusY; v<= radiusY; v+=0.0001f){
            for(float i = 0;i<360;i+=0.1){
                double rad = degToRad(i);
                float x = radiusX * (float)(Math.cos(rad));
                float y = radiusY * (float)(Math.sin(rad));
                float z = v ;
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices.clear();
        vertices = temp;
    }
    public void createHyperboloid1side(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(1/Math.cos(v) * Math.cos(u));
                float y = 0.5f * (float)(1/Math.cos(v) * Math.sin(u));
                float z = 0.5f * (float)(Math.tan(v));
                temp.add(new Vector3f(x,z,y));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createHyeperboloid2side(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI/2; u<= Math.PI/2; u+=Math.PI/60){
                float x = 0.1f * (float)(Math.tan(v) * Math.cos(u));
                float y = 0.1f * (float)(Math.tan(v) * Math.sin(u));
                float z = 0.1f * (float)(1/Math.cos(v));
                temp.add(new Vector3f(x,z,y));
            }


        }
        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60) {
            for (double u = Math.PI / 2; u <= 3 * Math.PI / 2; u += Math.PI / 60) {
                float x = 0.3f * (float) (Math.tan(v) * Math.cos(u));
                float y = 0.3f * (float) (Math.tan(v) * Math.sin(u));
                float z = -0.3f * (float) (1 / Math.cos(v));
                temp.add(new Vector3f(x, z, y));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createElipticCone(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = -Math.PI/2; v<= Math.PI/2; v+=Math.PI/60){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(v * Math.cos(u));
                float y = 0.5f * (float)(v * Math.sin(u));
                float z = 0.5f * (float)(v);
                temp.add(new Vector3f(z,y,x));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createElipticParaboloid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=0.05){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(v * Math.cos(u));
                float y = 0.5f * (float)(v * Math.sin(u));
                float z = (float) Math.pow(v,2);
                temp.add(new Vector3f(z,y,x));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void createHyperboloidParaboloid(){
        ArrayList<Vector3f> temp = new ArrayList<>();

        for(double v = 0; v<= 1; v+=0.05){
            for(double u = -Math.PI; u<= Math.PI; u+=Math.PI/60){
                float x = 0.5f * (float)(v * Math.tan(u));
                float y = 0.5f * (float)(v * 1/Math.sin(u));
                float z = (float) Math.pow(v,2);
                temp.add(new Vector3f(x,y,z));
            }
        }
        vertices.clear();
        vertices = temp;
    }

    public void drawIndicies(){
//        drawSetup();
        // Draw the vertices
        //optional
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);

        glDrawElements(GL_TRIANGLES,
                index.size(),GL_UNSIGNED_INT,0);
    }






}