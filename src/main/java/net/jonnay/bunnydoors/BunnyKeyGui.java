package net.jonnay.bunnydoors;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.GenericTexture;
import org.getspout.spoutapi.gui.RenderPriority;

public class BunnyKeyGui extends GenericPopup {

	public BunnyDoors plugin;

	public BunnyKeyGui(BunnyDoors plugin, Player player, String keys) {

		this.plugin = plugin;

		// Label
		GenericLabel label = new GenericLabel(keys);
		label.setX(175).setY(25);
		label.setPriority(RenderPriority.Lowest);
		label.setWidth(-1).setHeight(-1);

		// Border
		GenericTexture border = new GenericTexture("http://dev.bukkit.org/media/images/38/254/default-key.png");
		border.setX(65).setY(20);
		border.setPriority(RenderPriority.High);
		border.setWidth(24).setHeight(24);
		
		this.setTransparent(true);
		this.attachWidgets(plugin, border, label);

	}
}
