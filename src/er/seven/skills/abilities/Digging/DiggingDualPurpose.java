package er.seven.skills.abilities.Digging;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class DiggingDualPurpose extends Ability
{	
	@Override
	public Skill getSkill() { return Skill.DIGGING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Dual Purpose"; }
	
	@Override
	public String getDescription(Player player) 
	{
		return "Shovels deal " + 
				ChatColor.GOLD + (int)(100 * (getSkill().getLevel(player) * (Main.mainConfig.getDouble("Combat.TradeDamageScale") / 100))) + 
				ChatColor.GRAY + "% bonus damage.";
	}
	
	@Override
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		if (Util.isAnyShovel(weapon))
		{
			double damage = event.getDamage();
			
			damage *= 1 + ( getSkill().getLevel(player) * Main.mainConfig.getDouble("Combat.TradeDamageScale") / 100 );
			
			event.setDamage(damage);
		}
	}
}
