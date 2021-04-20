package er.seven.skills.abilities.Unarmored;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class UnarmoredLightFooted extends Ability
{
	@Override
	public Skill getSkill() { return Skill.UNARMORED; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Light Footed"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "You have " + ChatColor.GOLD + (int)(Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * ((double)Skill.UNARMORED.getLevel(player) / 100.0f) * 100) + 
				ChatColor.GRAY + "% increased movement speed when fully unarmored.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Unarmored.LightFootedBonus", 0.5f);
	}
	
	@Override
	public void onCharacterUpdate(Player player)
	{
		EntityEquipment equipment = player.getEquipment();
		double unarmoredSpeed = 0.0f;
		if (equipment.getHelmet() == null) 		{ unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.14f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getHelmet().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getHelmet().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getHelmet().getType().toString()) == false)
				{
					unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.14f;
				}
		
		if (equipment.getChestplate() == null) 	{ unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.43f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getChestplate().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getChestplate().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getChestplate().getType().toString()) == false)
				{
					unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.43f;
				}
		
		if (equipment.getLeggings() == null) 	{ unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.29f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getLeggings().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getLeggings().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getLeggings().getType().toString()) == false)
				{
					unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.29f;
				}
		
		if (equipment.getBoots() == null) 		{ unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.14f; }
		else if (Skill.LIGHT_ARMOR.checkSource(equipment.getBoots().getType().toString()) == false &&
				Skill.MEDIUM_ARMOR.checkSource(equipment.getBoots().getType().toString()) == false &&
				Skill.HEAVY_ARMOR.checkSource(equipment.getBoots().getType().toString()) == false)
				{
					unarmoredSpeed += Main.mainConfig.getDouble("Unarmored.LightFootedBonus") * 0.14f;
				}
		
		unarmoredSpeed = 1 + (unarmoredSpeed * Skill.UNARMORED.getLevel(player) / 100.0f);
		player.setWalkSpeed((float) (0.2f * unarmoredSpeed));
	}
}
