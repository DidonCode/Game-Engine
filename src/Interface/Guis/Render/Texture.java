package Interface.Guis.Render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import Tools.Logs;

public class Texture {
	
	private int width, height, id;
	private ByteBuffer buffer;
	
	public Texture(int id, int width, int height, ByteBuffer buffer) {
		this.width = width;
		this.height = height;
		this.id = id;
		this.buffer = buffer;
	}
	
	public static Texture loadTextureFile(String filePath) {
		BufferedImage image = null;
		try {
			File in = new File(filePath);
			image = ImageIO.read(in);
		} catch (IOException e) {
			new Logs("Error on texture path", e, false);
			return null;
		}

		int w = image.getWidth();
		int h = image.getHeight();

		int[] pixels = new int[w * h];
		image.getRGB(0, 0, w, h, pixels, 0, w);

		ByteBuffer buffer = BufferUtils.createByteBuffer(w * h * 4);

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int pixel = pixels[x + y * w];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) ((pixel) & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		int id = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, id);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, w, h, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return new Texture(id, w, h, buffer);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public ByteBuffer getBuffer() {
		return buffer;
	}

	public void bind() {
		glBindTexture(GL_TEXTURE_2D, id);
	}

	public void unbind() {
		glBindTexture(GL_TEXTURE_2D, 0);
	}
}
