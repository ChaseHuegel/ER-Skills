package er.seven.skills.abilities.Unarmed;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EntityEquipment;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class UnarmedDisarm extends Ability
{
	@Override
	public Skill getSkill() { return Skill.UNARMED; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Disarm"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "You have a " + 
				ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Unarmed.DisarmChance")) + 
				ChatColor.GRAY + "% chance to disarm with your unarmed attacks.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Unarmed.DisarmChance", 0.25f);
	}
	
	@Override
	public void onAttack(Player player, Entity victim, Material weapon, EntityDamageByEntityEvent event)
	{		
		if (Math.random() <= (getSkill().getLevel(player) * Main.mainConfig.getDouble("Unarmed.DisarmChance")) * 0.01)
		{
			if (Util.isUnarmed(player) == false) { return; }
			
			EntityEquipment equipment = ((LivingEntity)victim).getEquipment();
			
			if (equipment.getItemInMainHand() != null && equipment.getItemInMainHand().getType() != Material.AIR)
			{
				player.getWorld().dropItemNaturally(player.getLocation(), equipment.getItemInMainHand());
				((LivingEntity)victim).getEquipment().setItemInMainHand(null);
				
				if (victim instanceof Player) Util.sendActionbar((Player)victim, ChatColor.BLUE + "- DISARMED -", true);
				SendFeedback(player, true);
			}
		}
	}
}
