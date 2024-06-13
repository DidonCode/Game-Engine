package Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import Models.Model;
import Models.Animated.AnimatedModelData;
import Models.Animated.Joint;
import Models.Animated.JointData;
import Models.Animated.MeshData;
import Models.Animation.ColladaLoader;
import Models.Skeleton.SkeletonData;
import Tools.Logs;

public class Loader {
	
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	public Model loadObjModel(String filePath) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File(filePath));
		} catch (FileNotFoundException e) {
			new Logs("Error to find " + filePath + " file", e, false);
		}
		
		BufferedReader reader = new BufferedReader(fr);
		String line;
		
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		
		List<Vector3f[]> faces = new ArrayList<Vector3f[]>();
		
		List<Integer> indices = new ArrayList<Integer>();
		
		float[] verticesArray = null;
		float[] textureArray = null;
		float[] normalsArray = null;
		
		int[] indicesArray = null;
		
		try {
			while(true) {
				
				line = reader.readLine();
				
				String[] currentLine = line.split(" ");
				
				if(line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				}
				else if(line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				}
				else if(line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}
				else if(line.startsWith("f ")) {
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while(line != null && line.startsWith("f ")) {
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, vertices, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, vertices, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, vertices,textureArray, normalsArray);
				
				Vector3f[] face = new Vector3f[3];
				face[0] = vertices.get(Integer.parseInt(vertex1[0]) - 1);
				face[1] = vertices.get(Integer.parseInt(vertex2[0]) - 1);
				face[2] = vertices.get(Integer.parseInt(vertex3[0]) - 1);
				
				faces.add(face);

				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (IOException e) {
			new Logs("Error to load " + filePath + " model", e, false);
		}
		
		verticesArray = new float[vertices.size() * 3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		
		for(Vector3f vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		
		return loadToVAO(verticesArray, textureArray, normalsArray, indicesArray, faces);
	}
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals, List<Vector3f> vertices, float[] textureArray, float[] normalsArray) {
	    int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
	    indices.add(currentVertexPointer);
	    
	    Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
	    textureArray[currentVertexPointer * 2] = currentTex.x;
	    textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
	    
	    Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
	    normalsArray[currentVertexPointer * 3] = currentNorm.x;
	    normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
	    normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}
	
	public Model loadToVAO(float[] vertices, float[] textureCoords, float[] normals, int[] indices, int[] jointIds, float[] vertexWeights, AnimatedModelData animatedModel) {
		int vaoID = createVAO();
		
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		storeDataInAttributeList(3, 3, jointIds);
		storeDataInAttributeList(4, 3, vertexWeights);
		unbindVAO();
		
		SkeletonData skeletonData = animatedModel.getJointsData();
		Joint headJoint = createJoints(skeletonData.headJoint);
		
		return new Model(vaoID, indices.length, headJoint, skeletonData.jointCount);
	}
	
	public Model loadToVAO(float[] vertices, float[] textureCoords, float[] normals, int[] indices, List<Vector3f[]> faces) {
		int vaoID = createVAO();
		
		bindIndicesBuffer(indices);
		storeDataInAttributeList(0, 3, vertices);
		storeDataInAttributeList(1, 2, textureCoords);
		storeDataInAttributeList(2, 3, normals);
		unbindVAO();
		
		return new Model(vaoID, indices.length, faces);
	}
	
	public Model loadToVAO(float[] positions, int dimensions) {
		int vaoID = createVAO();
		storeDataInAttributeList(0, dimensions, positions);
		unbindVAO();
		
		return new Model(vaoID, positions.length / dimensions);
	}

	public Model loadAnimated(String filePath) {
		int MAX_WEIGHTS = 3;
		AnimatedModelData entityData = ColladaLoader.loadColladaModel(filePath, MAX_WEIGHTS);
		MeshData meshData = entityData.getMeshData();
		
		return loadToVAO(meshData.getVertices(), meshData.getTextureCoords(), meshData.getNormals(), meshData.getIndices(), meshData.getJointIds(), meshData.getVertexWeights(), entityData);
	}
	
	private static Joint createJoints(JointData data) {
		Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
		for (JointData child : data.children) {
			joint.addChild(createJoints(child));
		}
		return joint;
	}
	
	public int loadCubeMap(String[] textureFiles) {
		int textureID = GL11.glGenTextures();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, textureID);
		
		for(int i = 0; i < textureFiles.length; i++) {
			Interface.Guis.Render.Texture texure = Interface.Guis.Render.Texture.loadTextureFile(textureFiles[i]);
			GL11.glTexImage2D(GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL11.GL_RGBA, texure.getWidth(), texure.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, texure.getBuffer());
		}
		
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL13.GL_TEXTURE_CUBE_MAP, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		
		return textureID;
	}
	
	public int loadTexture(String filePath) {
		Texture texture = null;
		
		try {
			
			texture = TextureLoader.getTexture("PNG", new FileInputStream(filePath));
			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, -0.4f);
			
		} catch (IOException e) {
			new Logs("Error to load " + filePath + " texture", e, false);
		}
		
		int textureID = texture.getTextureID();
		textures.add(textureID);
		
		return textureID;
	}
	
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		
		return vaoID;
	}
	
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	public void cleanUp() {
		for(int vao : vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		
		for(int vbo : vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		
		for(int texture : textures) {
			GL11.glDeleteTextures(texture);
		}
	}
	
	private void bindIndicesBuffer(int[] indices) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer = storeDataInIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private void storeDataInAttributeList(int attributeNumber, int coordinateSize, int[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		IntBuffer buffer = storeDataInIntBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		GL30.glVertexAttribIPointer(attributeNumber, coordinateSize, GL11.GL_INT, coordinateSize * 4, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}
	
	private IntBuffer storeDataInIntBuffer(int[] data) {
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		
		return buffer;
	}
	
}
