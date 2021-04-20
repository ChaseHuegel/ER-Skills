package er.seven.skills.abilities.OneHanded;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class OneHandedHeavyBlows extends Ability
{
	@Override
	public Skill getSkill() { return Skill.ONE_HANDED; }
	
	@Override
	public boolean isPassive() { return false; }
	
	@Override
	public String getName() { return "Heavy Blows"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Attacks with heavy weapons have " + 
				ChatColor.GOLD + (int)(Main.mainConfig.getDouble("OneHanded.HeavyBlowsKnockback") * 100) + 
				ChatColor.GRAY + "% increased knockback.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("OneHanded.HeavyBlowsKnockback", 1.5f);
	}
	
	@Override
	public void onReady(Player player)
	{
		if ( Skill.HEAVY_WEAPONS.checkSource( player.getEquipment().getItemInMainHand().getType().toString() ) )
		{
			Util.sendActionbar(player, ChatColor.GOLD + "You ready " + ChatColor.BLUE + getName(), true);
			player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 3, 1);
		}
	}
	
	@Override
	public void onUnready(Player player)
	{
		Util.sendActionbar(player, ChatColor.GRAY + "You steady yourself", true);
		player.getWorld().playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 3, 0.6f);
	}
	
	@Override
	public void onActivate(Player player)
	{
		SendFeedback(player, true);
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 0.6f);
	}
	
	@Override
	public void onDeactivate(Player player)
	{
		Util.sendActionbar(player, ChatColor.GRAY + "You steady yourself", true);
	}
	
	@Override
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		if ( Skill.HEAVY_WEAPONS.checkSource( weapon.toString() ) )
		{
			if (getSkill().isAbilityReady(player)) { getSkill().activateAbility(player); }
			
			if (getSkill().isAbilityActive(player))
			{
				victim.setVelocity( 
						victim.getLocation().toVector().subtract(
						player.getLocation().toVector())
						.normalize()
						.multiply(Main.mainConfig.getDouble("OneHanded.HeavyBlowsKnockback")) );
			}
		}
	}
}
