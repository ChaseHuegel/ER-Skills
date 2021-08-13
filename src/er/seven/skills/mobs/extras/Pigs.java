package er.seven.skills.mobs.extras;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class Pigs implements Listener
{
	//	Feeding pigs gold carrots gives them a speed buff
	
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent event)
	{
		if (event.getRightClicked().getType() == EntityType.PIG && event.getPlayer().getInventory().getItem(event.getHand()).getType() == Material.GOLDEN_CARROT)
		{
			LivingEntity entity = (LivingEntity)event.getRightClicked();
			
			if (!entity.getScoreboardTags().contains("FED_PIG"))
			{
				entity.addScoreboardTag("FED_PIG");
				event.getPlayer().getInventory().getItem(event.getHand()).subtract();
				event.getPlayer().getWorld().spawnParticle(Particle.HEART, event.getRightClicked().getBoundingBox().getCenter().toLocation(event.getPlayer().getWorld()), 10, 1f, 0.5f, 1f);
				event.getPlayer().getWorld().playSound(event.getRightClicked().getLocation(), Sound.ENTITY_HORSE_EAT, 1f, 1f);
				entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3d);
				event.setCancelled(true);
			}
		}
	}
}