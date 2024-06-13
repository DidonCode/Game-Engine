package Interface.Guis;

import java.util.ArrayList;
import java.util.List;

import Interface.Guis.Elements.Element;

public class Hud {
	
	private List<Element> elements;
	
	public Hud() {
		this.elements = new ArrayList<Element>();
	}
	
	public void update() {
		for(Element element : elements) {
			element.update();
		}
	}
	
	public void render() {
		for(Element element : elements) {
			element.render();
		}
	}
	
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
}
