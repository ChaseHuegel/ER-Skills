package er.seven.skills.abilities.Unarmed;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class UnarmedIronFists extends Ability
{
	@Override
	public Skill getSkill() { return Skill.UNARMED; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Iron Fists"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Fists and sticks deal " + 
				ChatColor.GOLD + (int)(Main.mainConfig.getInt("Unarmed.FistDamage")) + 
				ChatColor.GRAY + " damage.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Unarmed.FistDamage", 3);
	}
	
	@Override
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{
		if (weapon == Material.AIR || weapon == Material.STICK)
		{
			double damage = event.getDamage();
			
			damage += player.getCooledAttackStrength(0) * (Main.mainConfig.getInt("Unarmed.FistDamage") - 1);
			
			event.setDamage(damage);
		}
	}
}
