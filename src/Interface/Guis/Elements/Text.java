 package Interface.Guis.Elements;

import java.awt.Font;

import static org.lwjgl.opengl.GL11.*;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.TextureImpl;

import Interface.Guis.Styles.Style;

public class Text extends Element {

	private String text;
	
	private TrueTypeFont font;
	private TextureImpl textureImpl;
	
	public Text(String text, int x, int y, Style style) {
		super(x, y, 0, 0, Style.DEFAULT_TEXT);
		
		this.text = text;
		this.style = style;
		
		setFont();
	}
	
	public Text(String text, int x, int y) {
		super(x, y, 0, 0, Style.DEFAULT_TEXT);
		
		this.text = text;
        
		setFont();
	}
	
	public void update() {}
	
	public void render() {
		this.textureImpl.bind();
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		if(text != null && text.length() >= 1) {
			String[] line = text.split(" \n ");
			for(int i = 0; i < line.length; i++) {
				if(style.color != null) {
					font.drawString(x, y + (i * font.getHeight(line[i])), line[i], style.color.convertToText());
				}else {
					font.drawString(x, y + (i * font.getHeight(line[i])), line[i]);
				}
			}
		}
		
		glDisable(GL_BLEND);
		
		TextureImpl.unbind();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont() {
		Font awtFont = new Font(style.fontName, style.fontType, style.fontSize);
		
        this.font = new TrueTypeFont(awtFont, true);
       
        this.textureImpl = new TextureImpl(text, GL_TEXTURE_2D, glGenTextures());
        
        setWidth(font.getWidth(text));
        setHeight(font.getHeight(text));
	}

	public boolean isHover(){
		return defaultHover();
	}

	public boolean isClick() {
		return defaultClick();
	}

}