package Interface.Guis.Elements;

import Interface.Guis.Render.Color4f;
import Interface.Guis.Render.Renderer;
import Interface.Guis.Styles.Style;

public class Checkbox extends Element {
	
	private boolean checked;

	public Checkbox(int x, int y, int width, int height) {		
		super(x, y, width, height, Style.DEFAULT_CHECKBOX);
	}
	
	public void update() {
		if(isClick()) checked = !checked; 
	}
	
	public void render() {
		if(style.backgroundColor != null) Renderer.quads(x, y, width, height, style.backgroundColor, true);
		if(style.borderColor != null) Renderer.quads(x, y, width, height, style.borderColor, false);
		
		if(checked) {
			Renderer.quads(x, y, width, height, Color4f.BLUE, true);
		}
	}
	
	public boolean isHover() {
		return defaultHover();
	}
	public boolean isClick() {
		return defaultClick();
	}

}
