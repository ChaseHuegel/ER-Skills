package er.seven.skills.abilities.Unarmed;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class UnarmedShillelagh extends Ability
{
	@Override
	public Skill getSkill() { return Skill.UNARMED; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Shillelagh"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Staffs have a " + 
				ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Unarmed.ShillelaghChance")) + 
				ChatColor.GRAY + "% chance to hurl enemies away from you.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Unarmed.ShillelaghChance", 1.0f);
	}
	
	@Override
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		if (Util.isAnyHoe(weapon) && player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().contains("staff"))
		{			
			if (Math.random() <= (getSkill().getLevel(player) * Main.mainConfig.getDouble("Unarmed.ShillelaghChance")) * 0.01)
			{
				victim.setVelocity( victim.getLocation().toVector().subtract(player.getLocation().toVector()).normalize().multiply(1.5f) );
				SendFeedback(player, false);
			}
		}
	}
}
