package er.seven.skills.abilities.Mining;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.Util;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class MiningProspector extends Ability
{
	private Random random = new Random();
	private static Object[] dropTable = new Object[] {
			Material.DIAMOND, 1,
			Material.EMERALD, 5,
			Material.GUNPOWDER, 10,
			Material.QUARTZ, 15,
			Material.GOLD_NUGGET, 19,
			Material.IRON_NUGGET, 50 };
	
	@Override
	public Skill getSkill() { return Skill.MINING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Prospector"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Mining stone has a " + 
				ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Trade.LootingChance")) + 
				ChatColor.GRAY + "% chance to drop nuggets, quartz, gunpowder, emeralds, or diamonds.";
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		if (skill != getSkill()) { return; }
		
		if (Math.random() <= (skill.getLevel(player) * Main.mainConfig.getDouble("Trade.LootingChance")) * 0.01)
		{			
			player.getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Util.weightedRandomMaterial(random, dropTable)));
			
			SendFeedback(player, false);
		}
	}
}
