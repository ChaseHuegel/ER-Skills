package er.seven.skills.abilities.Digging;

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

public class DiggingArchaeology extends Ability
{
	private Random random = new Random();
	private static Object[] dropTable = new Object[] {
			Material.DIAMOND, 1,
			Material.EMERALD, 1,
			Material.WITHER_SKELETON_SKULL, 1,
			Material.BEETROOT_SEEDS, 1,
			Material.BEETROOT_SEEDS, 1,
			Material.MELON_SEEDS, 1,
			Material.PUMPKIN_SEEDS, 1,
			Material.CARROT, 1,
			Material.POTATO, 1,
			Material.WRITABLE_BOOK, 1,
			Material.EXPERIENCE_BOTTLE, 1,
			Material.CAKE, 1,
			Material.ENCHANTED_GOLDEN_APPLE, 1,
			Material.NAME_TAG, 1,
			Material.LEAD, 1,
			Material.MYCELIUM, 1,
			Material.BONE_BLOCK, 5,
			Material.GOLD_NUGGET, 15,
			Material.COAL, 20,
			Material.SKELETON_SKULL, 30,
			Material.BONE, 120,
			Material.FLINT, 60 };
	
	@Override
	public Skill getSkill() { return Skill.DIGGING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Archaeology"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Digging has a " + 
				ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Trade.LootingChance")) + 
				ChatColor.GRAY + "% chance to drop bones, skulls, flint, or random items.";
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
