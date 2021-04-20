package er.seven.skills.abilities.Farming;

import org.bukkit.Particle;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class XFarmingThreshAndSow extends Ability
{
	@Override
	public Skill getSkill() { return Skill.FARMING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Thresh And Sow"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Crops harvested with a hoe have a " + ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Farming.ThreshAndSowChance")) + ChatColor.GRAY + "% chance to replant themselves";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Farming.ThreshAndSowChance", 1.0f);
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		if (skill != getSkill() || Util.isAnyHoe(player.getEquipment().getItemInMainHand().getType()) == false) { return; }
		
		if (Math.random() <= (skill.getLevel(player) * Main.mainConfig.getDouble("Farming.ThreshAndSowChance")) * 0.01)
		{
			event.getBlock().setType( event.getBlock().getState().getType() );
			event.getBlock().getWorld().spawnParticle(Particle.COMPOSTER, event.getBlock().getLocation(), 5, 0.2f, 0.0f, 0.2f);
		}
	}
}
