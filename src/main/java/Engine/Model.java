package Engine;
import org.joml.Vector3f;
import org.lwjgl.openvr.Texture;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Model {
    public List<Vector3f> vertices = new ArrayList<>();
    public List<Vector3f> normals = new ArrayList<>();
    public List<Face> faces = new ArrayList<>();
    public Model(){

    }

}