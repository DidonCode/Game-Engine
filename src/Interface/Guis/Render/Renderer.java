package Interface.Guis.Render;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {
	
	public static void quads(int x, int y, int width, int height, Color4f color, boolean fill) {
		if(!fill) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	
		glBegin(GL_QUADS);
			glColor4f(color.r, color.g, color.b, color.a);
			glVertex2f(x, y);
			glVertex2f(x + width, y);
			glVertex2f(x + width, y + height);
			glVertex2f(x, y + height);
		glEnd();
		
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
	}
	
    public static void circle(float x, float y, float radius, int segments, Color4f color) {
        glBegin(GL_TRIANGLE_FAN);
        glColor4f(color.r, color.g, color.b, color.a);
        
        glVertex2f(x, y);

        for (int i = 0; i <= segments; i++) {
            float theta = (float) (2.0 * Math.PI * i / segments);
            float xx = (float) (radius * Math.cos(theta)) + x;
            float yy = (float) (radius * Math.sin(theta)) + y;
            glVertex2f(xx, yy);
        }

        glEnd();
    }
    
    public static void line(float x1, float y1, float x2, float y2, float width, Color4f color) {
    	glBegin(GL_LINES);
    		glColor4f(color.r, color.g, color.b, color.a);
    		glLineWidth(width);
    		
    		glVertex2f(x1, y1);
    		glVertex2f(x2, y2);
    	glEnd();
    }
	
	public static void texturedQuads(float x, float y, float width, float height, Color4f color, float size, int xo, int yo) {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glBegin(GL_QUADS);
			glColor4f(color.r, color.g, color.b, color.a);
			
			glTexCoord2f((0 + xo) / size, (0 + yo) / size); glVertex2f(x , y);
			glTexCoord2f((1 + xo) / size, (0 + yo) / size); glVertex2f(x + width, y);
			glTexCoord2f((1 + xo) / size, (1 + yo) / size); glVertex2f(x + width, y + height);
			glTexCoord2f((0 + xo) / size, (1 + yo) / size); glVertex2f(x , y + height);
		glEnd();
		
		glDisable(GL_BLEND);
	}
	
}
