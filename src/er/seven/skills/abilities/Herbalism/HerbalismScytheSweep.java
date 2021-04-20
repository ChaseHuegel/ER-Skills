package er.seven.skills.abilities.Herbalism;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class HerbalismScytheSweep extends Ability
{
	@Override
	public Skill getSkill() { return Skill.HERBALISM; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Scythe Sweep"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Attacks with non-wood hoes have a " + ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Herbalism.ScytheSweepChance")) + ChatColor.GRAY + "% chance to perform a sweep attack on all targets around you.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Herbalism.ScytheSweepChance", 1.0f);
	}
	
	@Override
	public void onAttackFinal(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		if (weapon != Material.WOODEN_HOE && Util.isAnyHoe(weapon) && event.getCause() == DamageCause.ENTITY_ATTACK)
		{
			if (Math.random() <= (getSkill().getLevel(player) * Main.mainConfig.getDouble("Herbalism.ScytheSweepChance")) * 0.01)
			{
				Location loc = new Location(null, 
						victim.getLocation().getX(), 
						victim.getLocation().getY() + 0.5f, 
						victim.getLocation().getZ());
				
				player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, loc, 1, 0.1f, 0.0f, 0.1f);
				player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_ATTACK_SWEEP, 1, 1);
				
				List<Entity> entities = player.getNearbyEntities(3, 0.5f, 3);
				for (int i = 0; i < entities.size(); i++)
				{
					if (entities.get(i) == victim) { continue; }
					
					if (entities.get(i) instanceof LivingEntity)
					{
						double damage = 1;
						
						int sweepEnchantLevel = player.getEquipment().getItemInMainHand().getEnchantmentLevel(Enchantment.SWEEPING_EDGE);
						switch (sweepEnchantLevel)
						{
						case 0: damage = 1; break;
						case 1: damage = event.getDamage() * 0.5f; break;
						case 2: damage = event.getDamage() * 0.67f; break;
						case 3: damage = event.getDamage() * 0.75f; break;
						}
						
						((LivingEntity)entities.get(i)).damage(damage);
						loc = new Location(null, 
								entities.get(i).getLocation().getX(), 
								entities.get(i).getLocation().getY() + 0.5f, 
								entities.get(i).getLocation().getZ());
						
						entities.get(i).setVelocity( entities.get(i).getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(0.75f) );
						player.getWorld().spawnParticle(Particle.SWEEP_ATTACK, loc, 1, 0.1f, 0.0f, 0.1f);
					}
				}
			}
		}
	}
}
