package er.seven.skills.abilities.Herbalism;

import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class HerbalismBountifulHarvest extends Ability
{
	@Override
	public Skill getSkill() { return Skill.HERBALISM; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Bountiful Harvest"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Plants broken with a hoe have a " + ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Herbalism.BountifulHarvestChance")) + ChatColor.GRAY + "% chance to drop a silk touched item.";
	}
	
	@Override
	public void registerConfigVars(YamlConfiguration yml)
	{
		yml.addDefault("Herbalism.BountifulHarvestChance", 0.75f);
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		if (skill != getSkill() || Util.isAnyHoe(player.getEquipment().getItemInMainHand().getType()) == false) { return; }
		
		if (Math.random() <= (skill.getLevel(player) * Main.mainConfig.getDouble("Herbalism.BountifulHarvestChance")) * 0.01)
		{
			Block block = event.getBlock();
			
			block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType()));
			
			SendFeedback(player, false);
		}
	}
}
