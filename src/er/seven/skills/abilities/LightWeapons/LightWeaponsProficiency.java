package er.seven.skills.abilities.LightWeapons;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class LightWeaponsProficiency extends Ability
{
	@Override
	public Skill getSkill() { return Skill.LIGHT_WEAPONS; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Proficiency"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Light weapons deal " + 
				ChatColor.GOLD + (int)(100 * (getSkill().getLevel(player) * Main.mainConfig.getDouble("Combat.WeaponDamageScale") / 100)) + 
				ChatColor.GRAY + "% bonus damage.";
	}
	
	@Override
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		if (getSkill().checkSource(weapon.toString()))
		{
			double damage = event.getDamage();
			
			damage *= 1 + ( getSkill().getLevel(player) * Main.mainConfig.getDouble("Combat.WeaponDamageScale") / 100 );
			
			event.setDamage(damage);
		}
	}
}
