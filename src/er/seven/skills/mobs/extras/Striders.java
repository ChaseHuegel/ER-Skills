package er.seven.skills.mobs.extras;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Strider;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.StriderTemperatureChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Striders implements Listener
{
	//	Feeding striders magma cubes gives them a speed buff,
	//	doesnt slow land movement, and protects against rain & water
	
	@EventHandler
	public void onEntityDamaged(EntityDamageEvent event)
	{
		if (event.getEntityType() == EntityType.STRIDER && event.getCause() == DamageCause.DROWNING)
			event.setCancelled(true);
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event)
	{
		if (event.getRightClicked().getType() == EntityType.STRIDER && event.getPlayer().getInventory().getItem(event.getHand()).getType() == Material.MAGMA_CREAM)
		{
			Strider strider = (Strider)event.getRightClicked();
			
			if (!strider.getScoreboardTags().contains("FED_STRIDER"))
			{
				strider.addScoreboardTag("FED_STRIDER");
				event.getPlayer().getInventory().getItem(event.getHand()).subtract();
				event.getPlayer().getWorld().spawnParticle(Particle.LAVA, event.getRightClicked().getBoundingBox().getCenter().toLocation(event.getPlayer().getWorld()), 6, 1f, 0.5f, 1f);
				event.getPlayer().getWorld().spawnParticle(Particle.HEART, event.getRightClicked().getBoundingBox().getCenter().toLocation(event.getPlayer().getWorld()), 10, 1f, 0.5f, 1f);
				event.getPlayer().getWorld().playSound(event.getRightClicked().getLocation(), Sound.ENTITY_HORSE_EAT, 1f, 1f);
				
				if (strider.isShivering())
					strider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5d);
				else
					strider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2d);
				
				event.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onStriderShiver(StriderTemperatureChangeEvent event)
	{
		Strider strider = event.getEntity();
		
		if (strider.getScoreboardTags().contains("FED_STRIDER"))
		{
			if (event.isShivering())
				strider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.5d);
			else
				strider.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.2d);
		}
	}
}
