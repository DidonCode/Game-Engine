package Models;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;

import Engine.Maths.Vector;


public class Hitbox {
	
	private List<Vector3f[]> faces;
	private boolean hit;
	
	public Hitbox(List<Vector3f[]> faces) {	
		this.faces = faces;
	}
	
	public static List<Vector3f[]> transformModel(List<Vector3f[]> faces, Vector3f position, Vector3f rotation){
		List<Vector3f[]> transformedFaces = new ArrayList<Vector3f[]>();
		Matrix3f rotationMatrix = createRotationMatrix(rotation);
		
		for(Vector3f[] triangle : faces) {
			Vector3f[] tranformedFace = new Vector3f[3];
			
			tranformedFace[0] = transformPoint(triangle[0], rotationMatrix, position);
			tranformedFace[1] = transformPoint(triangle[1], rotationMatrix, position);
			tranformedFace[2] = transformPoint(triangle[1], rotationMatrix, position);
			
			transformedFaces.add(tranformedFace);
		}
		
		return transformedFaces;
	}
	
	public static Vector3f transformPoint(Vector3f point, Matrix3f rotationMatrix, Vector3f translation) {
		Vector3f rotatedPoint = new Vector3f(0, 0, 0);
		Matrix3f.transform(rotationMatrix, point, rotatedPoint);
		Vector.add(point, translation, rotatedPoint);
		return rotatedPoint;
	}
	
	public static Matrix3f createRotationMatrix(Vector3f rotation) {        
		Matrix3f rx = new Matrix3f();
		rx.m00 = (float) Math.toRadians(rotation.x);
		
		Matrix3f ry = new Matrix3f();
		ry.m00 = (float) Math.toRadians(rotation.y);
		
		Matrix3f rz = new Matrix3f();
		rz.m00 = (float) Math.toRadians(rotation.z);
		
		Matrix3f roationMatrix = new Matrix3f();
		Matrix3f.mul(roationMatrix, rx, roationMatrix);
		Matrix3f.mul(roationMatrix, ry, roationMatrix);
		Matrix3f.mul(roationMatrix, rz, roationMatrix);
		
		return roationMatrix;
	}
	
	public static boolean checkTriangleIntersection(Vector3f p0, Vector3f p1, Vector3f p2, Vector3f q0, Vector3f q1, Vector3f q2) {
        Vector3f e1 = new Vector3f();
        Vector3f e2 = new Vector3f();
        Vector3f h = new Vector3f();
        Vector3f s = new Vector3f();
        Vector3f q = new Vector3f();
        double a, f, u, v;
        
        Vector3f.sub(p1, p0, e1);
        Vector3f.sub(p2, p0, e2);
        
        Vector3f.cross(Vector.sub(q2, q1), Vector.sub(q1, q0), h);
        a = Vector3f.dot(e1, h);
 
        if (a > -1e-7 && a < 1e-7) return false;
        f = 1.0 / a;
        Vector3f.sub(q0, p0, s);
        u = f * Vector3f.dot(s, h);

        if (u < 0.0 || u > 1.0) return false;
        Vector3f.cross(s, e1, q);
        v =  f * Vector3f.dot(Vector.sub(q2, q1), q);

        if (v < 0.0 || u + v > 1.0) return false;
        double t = f * Vector3f.dot(e2, q);

        return t > 1e-7;
	}
	
	public static boolean hit(Hitbox hitbox1, Vector3f position1, Vector3f rotation1, Hitbox hitbox2, Vector3f position2, Vector3f rotation2) {
		List<Vector3f[]> hitbox1Faces = transformModel(hitbox1.getFaces(), position1, rotation1);
		List<Vector3f[]> hitbox2Faces = transformModel(hitbox2.getFaces(), position2, rotation2);
		
		for(Vector3f[] faces1 : hitbox1Faces) {
			
			for(Vector3f[] faces2 : hitbox2Faces) {

				if(checkTriangleIntersection(faces1[0], faces1[1], faces1[2], faces2[0], faces2[1], faces2[2])) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean[] hits(Hitbox hitbox1, Vector3f position1, Hitbox hitbox2, Vector3f position2) {
		for(Vector3f[] faces1 : hitbox1.faces) {
			
			Vector3f vertex1Min = Vector.add(faces1[0], position1);
			Vector3f vertex1Max = Vector.add(faces1[1], position1);

			for(Vector3f[] faces2 : hitbox2.faces) {
				
				Vector3f vertex2Min = Vector.add(faces2[0], position2);
				Vector3f vertex2Max = Vector.add(faces2[1], position2);
				
				boolean xUp = vertex1Max.x >= vertex2Min.x;
				boolean xDown = vertex1Min.x <= vertex2Max.x;
				
				boolean yUp = vertex1Max.y >= vertex2Min.y;
				boolean yDown = vertex1Min.y <= vertex2Max.y;
				
				boolean zUp = vertex1Max.z >= vertex2Min.z;
				boolean zDown = vertex1Min.z <= vertex2Max.z;
				
				return new boolean[] {xUp, xDown, yUp, yDown, zUp, zDown};
			}
		}
		return new boolean[] {false, false, false, false, false, false};
	}
	
	public List<Vector3f[]> getFaces() {
		return faces;
	}

	public boolean isHit() {
		return hit;
	}
	
}
