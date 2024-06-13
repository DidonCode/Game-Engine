package Interface.Guis.Elements;

import Interface.Guis.Render.Color4f;
import Interface.Guis.Render.Renderer;
import Interface.Guis.Styles.Style;

public class Button extends Element {
	
	private Text text;
	
	public Button(int x, int y, int width, int height){
		super(x, y, width, height, Style.DEFAULT_BUTTON);
		this.text = null;
	}
	
	public Button(int x, int y, int width, int height, Text text){
		super(x, y, width, height, Style.DEFAULT_BUTTON);
		this.text = text;
	}
	
	public Button(int x, int y, int width, int height, String text){
		super(x, y, width, height, Style.DEFAULT_BUTTON);
		this.text = new Text(text, x, y);
	}
	
	public Button(int x, int y, int width, int height, String text, Style textStyle){
		super(x, y, width, height, Style.DEFAULT_BUTTON);
		this.text = new Text(text, x, y, textStyle);
	}
	
	public void update(){
		if(style.alignX && text != null) text.setX(x + width / 2 - text.getWidth() / 2);
		if(style.alignY && text != null) text.setY(y + height / 2 - text.getHeight() / 2);
	}	
	
	public void render(){
		if(style.background != null) {
			style.background.bind();
			Renderer.texturedQuads(x, y, width, height, Color4f.RED, 1, 0, 0);
			style.background.unbind();
		}else {
			if(style.backgroundColor != null) Renderer.quads(x, y, width, height, style.backgroundColor, true);
			if(style.borderColor != null) Renderer.quads(x, y, width, height, style.borderColor, false);
		}
		if(text != null) text.render();
	}
	
	public boolean isHover(){
		return defaultHover();
	}
	
	public boolean isClick(){
		return defaultClick();
	}
}