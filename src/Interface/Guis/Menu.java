package Interface.Guis;

import java.util.ArrayList;
import java.util.List;

import Interface.Guis.Elements.Element;

public abstract class Menu {
	
	protected int x, y;
	protected int width, height;
	protected List<Element> elements;
	protected boolean open;
	
	public Menu(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.open = false;
		
		this.elements = new ArrayList<Element>();
	}
	
	public abstract void update();
	public abstract void render();
	
	public void addElement(Element element) {
		elements.add(element);
	}
	
	public List<Element> getElements() {
		return elements;
	}
	
	public void removeElement(Element element) {
		elements.remove(element);
	}
	
	public void clearElement() {
		elements.clear();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
}
