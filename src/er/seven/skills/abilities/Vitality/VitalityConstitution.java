package er.seven.skills.abilities.Vitality;

import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class VitalityConstitution extends Ability
{
	@Override
	public Skill getSkill() { return Skill.VITALITY; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Constitution"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "You have " + ChatColor.GOLD + (int)(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue()) + 
				ChatColor.GRAY + " health (" + 
				ChatColor.GOLD + (int)(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 0.5f) + 
				ChatColor.GRAY + " hearts)";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Vitality.BaseHealth", 20);
		yml.addDefault("Vitality.HealthBonus", 40);
	}
	
	@Override
	public void onCharacterUpdate(Player player)
	{
		double maxHealth = Main.mainConfig.getInt("Vitality.BaseHealth") + ( (Skill.VITALITY.getLevel(player) * Main.mainConfig.getInt("Vitality.HealthBonus")) / 100 );
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
		if (Main.mainConfig.getBoolean("Vitality.EnableHealthScale")) player.setHealthScale(20);
	}
}
