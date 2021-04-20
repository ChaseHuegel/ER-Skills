package er.seven.skills.abilities.Unarmed;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.PlayerInventory;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class UnarmedDeflect extends Ability
{
	@Override
	public Skill getSkill() { return Skill.UNARMED; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Deflect"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "You have a " + 
				ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Unarmed.DeflectChance")) + 
				ChatColor.GRAY + "% chance to avoid any melee or ranged attack.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Unarmed.DeflectChance", 0.25f);
	}
	
	@Override
	public void onAttacked(Player player, Entity attacker, PlayerInventory equipment, EntityDamageByEntityEvent event)
	{
		if (Math.random() <= (getSkill().getLevel(player) * Main.mainConfig.getDouble("Unarmed.DeflectChance")) * 0.01)
		{
			event.setDamage(0);
			event.setCancelled(true);
			SendFeedback(player, true);
		}
	}
}
