package Interface.Guis;

import java.util.ArrayList;
import java.util.List;

public class Gui {
	
	private List<Menu> menus;
	private Hud hud;
	
	public Gui() {
		this.menus = new ArrayList<Menu>();
	}
	
	public void update() {
		for(Menu menu : menus) {
			menu.update();
		}
	}
	
	public void render() {
		for(Menu menu : menus) {
			menu.render();
		}
	}
	
	public void addMenu(Menu menu) {
		menus.add(menu);
	}
	
	public List<Menu> getMenus() {
		return menus;
	}
	
	public void removeMenu(Menu menu) {
		menus.remove(menu);
	}
	
	public void clearMenu() {
		menus.clear();
	}
	
	public void setHud(Hud hud) {
		this.hud = hud;
	}
	
	public Hud getHud() {
		return hud;
	}
}
