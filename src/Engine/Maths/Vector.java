package Engine.Maths;

import org.lwjgl.util.vector.Vector3f;

public class Vector {
	
	public static Vector3f add(Vector3f left, Vector3f right) {
		return new Vector3f(left.x + right.x, left.y + right.y, left.z + right.z);
	}
	
	public static Vector3f add(Vector3f left, float x, float y, float z) {
		return new Vector3f(left.x + x, left.y + y, left.z + z);
	}
	
	public static Vector3f sub(Vector3f left, Vector3f right) {
		return new Vector3f(left.x - right.x, left.y - right.y, left.z - right.z);
	}
	
	public static Vector3f sub(Vector3f left, float x, float y, float z) {
		return new Vector3f(left.x - x, left.y - y, left.z - z);
	}
	
	public static void add(Vector3f left, Vector3f right, Vector3f dest) {
		dest.x = left.x + right.x;
		dest.y = left.y + right.y;
		dest.z = left.z + right.z;
	}
	
	public static void add(Vector3f left, float x, float y, float z, Vector3f dest) {
		dest.x = left.x + x;
		dest.y = left.y + y;
		dest.z = left.z + z;
	}
	
	public static void sub(Vector3f left, Vector3f right, Vector3f dest) {
		dest.x = left.x - right.x;
		dest.y = left.y - right.y;
		dest.z = left.z - right.z;
	}
	
	public static void sub(Vector3f left, float x, float y, float z, Vector3f dest) {
		dest.x = left.x - x;
		dest.y = left.y - y;
		dest.z = left.z - z;
	}
	
	public static Vector3f mul(Vector3f left, Vector3f right) {
		float x = left.x, y = left.y, z = left.z;
		
		if(left.x != 0 && right.x != 0) x *= right.x;
		if(left.y != 0 && right.y != 0) y *= right.y;
		if(left.z != 0 && right.z != 0) z *= right.z;
			
		return new Vector3f(x, y, z);
	}

	public static float dist(Vector3f vector1, Vector3f vector2) {
		return (float) Math.sqrt(Math.pow(vector2.x - vector1.x, 2) + Math.pow(vector2.y - vector1.y, 2) + Math.pow(vector2.z - vector1.z, 2));
	}
}
