package er.seven.skills.abilities.Digging;

import java.util.Collection;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import er.seven.skills.Main;
import er.seven.skills.Skill;
import er.seven.skills.abilities.Ability;
import net.md_5.bungee.api.ChatColor;

public class DiggingEfficiency extends Ability
{
	@Override
	public Skill getSkill() { return Skill.DIGGING; }
	
	@Override
	public boolean isPassive() { return true; }
	
	@Override
	public String getName() { return "Efficiency"; }
	
	@Override
	public String getDescription(Player player) 
	{ 
		return "Digging has a " + ChatColor.GOLD + (int)(getSkill().getLevel(player) * Main.mainConfig.getDouble("Trade.DoubleDropsChance")) + ChatColor.GRAY + "% chance for double drops.";
	}
	
	@Override
	public void onBreak(Player player, Skill skill, BlockBreakEvent event)
	{
		if (skill != getSkill()) { return; }
		
		if (Math.random() <= (skill.getLevel(player) * Main.mainConfig.getDouble("Trade.DoubleDropsChance")) * 0.01)
		{
			Block block = event.getBlock();
			
			Collection<ItemStack> drops = block.getDrops();
			
			for (ItemStack drop : drops)
			{
				block.getWorld().dropItemNaturally(block.getLocation(), drop);
			}
			
			SendFeedback(player, false);
		}
	}
}
