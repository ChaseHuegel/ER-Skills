package er.seven.skills.abilities.Unarmored;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class UnarmoredDefense extends Ability
{
	@Override
	public Skill getSkill() { return Skill.UNARMORED; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Unarmored Defense"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "You have " + ChatColor.GOLD + (int)(Main.mainConfig.getInt("Unarmored.Defense") * ((double)Skill.UNARMORED.getLevel(player) / 100.0f)) + 
				ChatColor.GRAY + " defense points when fully unarmored.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Unarmored.Defense", 20);
	}
	
	@Override
	public void onCharacterUpdate(Player player)
	{		
		double unarmoredDefense = 0.0f;
		PlayerInventory equipment = player.getInventory();
		if (equipment.getHelmet() == null) 		{ unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.14f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getHelmet().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getHelmet().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getHelmet().getType().toString()) == false)
				{
					unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.14f;
				}
		
		if (equipment.getChestplate() == null) 	{ unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.43f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getChestplate().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getChestplate().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getChestplate().getType().toString()) == false)
				{
					unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.43f;
				}
		
		if (equipment.getLeggings() == null) 	{ unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.29f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getLeggings().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getLeggings().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getLeggings().getType().toString()) == false)
				{
					unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.29f;
				}
		
		if (equipment.getBoots() == null) 		{ unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.14f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getBoots().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getBoots().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getBoots().getType().toString()) == false)
				{
					unarmoredDefense += Main.mainConfig.getInt("Unarmored.Defense") * 0.14f;
				}
		
		unarmoredDefense *= (double)Skill.UNARMORED.getLevel(player) / 100.0f;
		player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(unarmoredDefense);
	}
}
