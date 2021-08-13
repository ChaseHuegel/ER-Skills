package er.seven.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BossBar;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.BlockIterator;

import er.seven.skills.mobs.Monster;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Util 
{	
	public static float clamp(float val, float min, float max) { return Math.max(min, Math.min(max, val)); }
	public static double clamp(double val, double min, double max) { return Math.max(min, Math.min(max, val)); }
	public static Integer clamp(Integer val, Integer min, Integer max) { return Math.max(min, Math.min(max, val)); }
	
	public static boolean hasTag(Entity entity, String tag)
	{
		for (String entry : entity.getScoreboardTags())
		{
			if (entry.equalsIgnoreCase(tag)) return true;
		}
		
		return false;
	}
	
	public static String formatSplitString(final String string, final String splitter, boolean lowerCase) 
	{
        final String[] split = StringUtils.splitByWholeSeparator(string, splitter);
        final StringBuilder buff = new StringBuilder();
        for (final String str : split) 
        {
            final String result = str.toLowerCase();
            buff.append((lowerCase ? result : StringUtils.capitalize(result)) + " ");
        }
        return buff.toString().trim();
    }
	
	public static <T, E> T getKeyByValue(HashMap<T, E> map, E value) 
	{
		for (Entry<T, E> entry : map.entrySet()) 
		{
			if (Objects.equals(value, entry.getValue())) 
			{
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	public static String randomString(String... candidates) 
	{
		return candidates[new Random().nextInt(candidates.length)];
	}
	
	public static Monster randomMonster(Random rand, Monster... candidates) 
	{
		return candidates[rand.nextInt(candidates.length)];
	}
	
	public static EntityType randomEntity(Random rand, EntityType... candidates) 
	{
		return candidates[rand.nextInt(candidates.length)];
	}

	public static Integer randomRange(Integer min, Integer max)
	{
		return (new Random()).nextInt(max - min + 1 + min);
	}
	
	public static Material randMaterial(Random rand, Material... candidates) 
	{
		return candidates[rand.nextInt(candidates.length)];
	}
		  
	public static Material randMaterial(Material... candidates) 
	{
		return randMaterial(new Random(), candidates);
	}
	
	public static Material weightedRandomMaterial(Random rand, Object... candidates) 
	{
	    if (candidates.length % 2 != 0)
	      throw new IllegalArgumentException(); 
	    ArrayList<Material> types = new ArrayList<>();
	    for (int i = 0; i < candidates.length; i++) {
	      Material type = (Material)candidates[i];
	      i++;
	      int freq = ((Integer)candidates[i]).intValue();
	      for (int z = 0; z < freq; z++)
	        types.add(type); 
	    } 
	    return types.get(rand.nextInt(types.size()));
	}
	
	public static ItemStack fromItemTable(String table)
	{
		Integer totalWeight = 0;
		
		String[] entries = table.split(";");
		List<Material> items = new ArrayList<Material>();
		List<Integer> itemAmounts = new ArrayList<Integer>();
		List<Integer> weights = new ArrayList<Integer>();
		
		for (String entry : entries)
		{
			String[] set = entry.split(",");
			
			if (set.length > 1)
			{
				if (set[0].contains("@") == true)
				{
					String[] compound = set[0].split("@");
					items.add(Material.valueOf(compound[0]));
					itemAmounts.add(randomRange(1, Integer.parseInt(compound[1])));
				}
				else
				{
					items.add(Material.valueOf(set[0]));
					itemAmounts.add(1);
				}
				
				weights.add(Integer.parseInt(set[1]));
				totalWeight += Integer.parseInt(set[1]);
			}
		}
		
		Integer weightPick = randomRange(0, totalWeight);
		Integer index = 0;
		
		Integer curWeight = 0;
		for (int i = 0; i < items.size(); ++i)
		{
			curWeight += weights.get(i);
			if (weightPick <= curWeight)
			{
				index = i;
				break;
			}
		}
		
		if (index >= items.size()) { index = items.size() - 1; }
		if (index < 0) { index = 0; }
		
		if (items.size() > 0)
		{
			return new ItemStack(items.get(index), itemAmounts.get(index));
		}
		else
		{
			return null;
		}
	}
	
	public static String fromTextTable(String table)
	{
		Integer totalWeight = 0;
		
		String[] entries = table.split(";");
		List<String> strings = new ArrayList<String>();
		List<Integer> weights = new ArrayList<Integer>();
		
		for (String entry : entries)
		{
			String[] set = entry.split(",");
			
			if (set.length > 1)
			{
				strings.add(set[0]);
				weights.add(Integer.parseInt(set[1]));
				totalWeight += Integer.parseInt(set[1]);
			}
		}
		
		Integer weightPick = randomRange(0, totalWeight);
		
		for (int i = 0; i < strings.size(); ++i)
		{
			weightPick -= weights.get(i);
			if (weightPick <= 0)
			{
				return strings.get(i);
			}
		}
		
		return "";
	}
	
	public static void sendActionbar(Player player, String message, boolean reflectChat) 
    {
		if (reflectChat == true) { player.sendMessage(message); }
		sendActionbar(player, message);
	}
	
	@SuppressWarnings("deprecation")
	public static void sendActionbar(Player player, String message) 
    {
		player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(message).create());
	}
	
	public static void sendSkillBar(Player player, Skill skill)
	{
		Main.Instance().skillBarLifetime.replace(new SkillPair(player, skill), 0);
		BossBar bar = Main.Instance().skillBar.get(new SkillPair(player, skill));
		
		Integer level = skill.getLevel(player);
		double prevNeededXP = Util.getTNL(level - 1); if (level <= 1) { prevNeededXP = 0; }
		double neededXP = Util.getTNL(level) - prevNeededXP;
		double currentXP = skill.getXP(player) - prevNeededXP;
		
		bar.setColor(BarColor.GREEN);
		bar.setTitle(ChatColor.WHITE + skill.getName() + " " + ChatColor.GOLD + level.toString());
		bar.setProgress(clamp((currentXP / neededXP), 0.0f, 1.0f));
		bar.setVisible(true);
	}
	
	public static void sendHealthBar(Player player, LivingEntity target)
	{
		Main.Instance().healthBarLifetime.replace(player, 0);
		BossBar bar = Main.Instance().healthBar.get(player);
		
		double maxHP = target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
		double currentHP = target.getHealth();
		
		bar.setColor(BarColor.RED);
		bar.setTitle(ChatColor.WHITE + target.getName() + " " + ChatColor.RED + Math.round(currentHP) + ChatColor.WHITE + "/" + ChatColor.RED + Math.round(maxHP));
		bar.setProgress(clamp((currentHP / maxHP), 0.0f, 1.0f));
		bar.setVisible(true);
	}
	
	public static void updateTablistName(Player player)
	{
		player.setPlayerListName(ChatColor.GOLD + Main.Instance().totalLevel.get(player).toString() + " " + ChatColor.WHITE + player.getDisplayName());
	}
	
	public static double getTNL(int level)
	{
		double tnl = 0.0;
		
		double x = Math.pow(level, 2.22);
		x *= 5.1337;
		x *= 2.35;
		x += 50;
		
		tnl = Math.round(Math.floor(x));
		
		return tnl;
	}
	
	public static boolean giveXP(Player player, Skill skill, double damage, boolean quiet)
	{
		if (skill == Skill.NONE) return false;
		
		if (damage < 1) { damage = 1; }
		
		boolean didLevel = false;
		
		damage *= Main.mainConfig.getDouble("Settings.XPRate");
		Integer xpAdd = (int) Math.round(damage);
		
		skill.setXP(player, skill.getXP(player) + xpAdd);	//	Skill xp
        Main.Instance().totalXP.replace(player, Main.Instance().totalXP.get(player) + xpAdd);	// Total xp
        
        //	Handle level ups
        Integer level = skill.getLevel(player);
        Integer xp = skill.getXP(player);
        if (level < Main.mainConfig.getInt("Settings.MaxLevel"))
        {
        	while (xp > Util.getTNL(level))
        	{
        		didLevel = true;
        		
        		level += 1; skill.setLevel(player, level);	//	Skill level
        		Main.Instance().totalLevel.replace(player, Main.Instance().totalLevel.get(player) + 1);	// Total level
        		
        		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
        		player.sendTitle(
        				ChatColor.GOLD + "Level up!", 
        				ChatColor.GRAY + "Your " + ChatColor.GREEN + skill.getName() + ChatColor.GRAY + " is now level " + ChatColor.YELLOW + level.toString() + ChatColor.GRAY + ".", 
        				10, 20, 20);
        		
        		player.sendMessage(ChatColor.GOLD + "Level up! " + ChatColor.GRAY + "Your " + ChatColor.GREEN + skill.getName() + ChatColor.GRAY + " is now level " + ChatColor.YELLOW + level.toString() + ChatColor.GRAY + ".");
        		
        		player.giveExp(level * 2);
        		
        		if (level >= Main.mainConfig.getInt("Settings.MaxLevel"))
        		{
        			skill.setLevel(player, Main.mainConfig.getInt("Settings.MaxLevel"));	//	Cap skill level
        			Bukkit.getServer().broadcastMessage(
        					ChatColor.GOLD + player.getDisplayName() + ChatColor.BLUE + " has reached level 100 " + ChatColor.YELLOW + skill.getName() + ChatColor.BLUE + "!");
        			break;
        		}
        	}
        }
        else
        {
        	player.giveExp(1);
        }
        
        if (quiet == false)
		{
			sendSkillBar(player, skill);
			//sendActionbar(player, ChatColor.GREEN + "+" + xpAdd + " XP");
		}
        
        if (didLevel == true) { updateCharacter(player); }
        
        return didLevel;
	}
	
	public static void giveXP(Player player, Skill skill, double damage)
	{
		giveXP(player, skill, damage, false);
	}
	
	public static LivingEntity getTarget(Player player)
	{
		Entity target = player.getTargetEntity(16);
		if (target instanceof LivingEntity)
		{
			return (LivingEntity) target;
		}
		
		return null;
	}
	
	public static LivingEntity getTargetOf(Player player)
	{
		List<Entity> nearbyE = player.getNearbyEntities(16, 16, 16);
        ArrayList<LivingEntity> livingE = new ArrayList<LivingEntity>();

        for (Entity e : nearbyE) {
            if (e instanceof LivingEntity) {
                livingE.add((LivingEntity) e);
            }
        }

        BlockIterator bItr = new BlockIterator(player, 16);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        // loop through player's line of sight
        while (bItr.hasNext()) {
                block = bItr.next();
                bx = block.getX();
                by = block.getY();
                bz = block.getZ();
                if (block.getType().isSolid() == true) { return null; }
                        // check for entities near this block in the line of sight
                        for (LivingEntity e : livingE) {
                                loc = e.getLocation();
                                ex = loc.getX();
                                ey = loc.getY();
                                ez = loc.getZ();
                                if ((bx-0.75 <= ex && ex <= bx+0.75) && (bz-0.75 <= ez && ez <= bz+0.75) && (by-e.getHeight() <= ey && ey <= by+0.5)) {
                                        // entity is close enough, set target and stop
                                        return e;
                                }
                        }
                }
        
        return null;

	}
	
	public static void updateCharacter(Player player)
	{
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < Main.GetAbilities().length; n++) { if (!Main.GetAbilities()[n].isPassive() && Main.GetAbilities()[n].getSkill().isAbilityActive(player) == false) continue; else Main.GetAbilities()[n].onCharacterUpdate(player); }
		//////////////////////////////////
		
		updateTablistName(player);
	}
	
	public static int getCraftItemAmount(Material m, CraftItemEvent e)
	{
        if (e.isCancelled()) { return 0; }
        if(!e.getRecipe().getResult().getType().equals(m)) { return 0; }
        
        int amount = e.getRecipe().getResult().getAmount();
        
        if (e.isShiftClick()) 
        {
            int max = e.getInventory().getMaxStackSize();
            
           ItemStack[] matrix = e.getInventory().getMatrix();
            
            for (ItemStack is: matrix) 
            {
                if (is == null || is.getType().equals(Material.AIR)) { continue; }
                int tmp = is.getAmount();
                if (tmp < max && tmp > 0) { max = tmp; }
            }
            
            amount *= max;
        }
        
        return amount;
    }
	
	public static boolean removeItemFromInventory(Inventory inventory, Material material, Integer amount)
	{
		boolean canRemove = false;
		Integer amountLeft = amount;
		Integer count = 0;
		
		for (ItemStack item : inventory.getContents())
		{
			if (item == null) { continue; }
			if (item.getType() == material)
			{
				if (item.getAmount() >= amount)
				{
					if (item.getAmount() - amount <= 0) { inventory.remove(item); }
					else { item.setAmount(item.getAmount() - amount); }
					return true;
				}
				else
				{
					count += item.getAmount();
					
					if (count >= amount)
					{
						canRemove = true;
						break;
					}
				}
			}
		}
		
		if (canRemove)
		{
			for (ItemStack item : inventory.getContents())
			{
				if (item == null) { continue; }
				if (item.getType() == material)
				{
					Integer thisAmount = item.getAmount();					
					Integer removeAmount = thisAmount - amountLeft;
					if (removeAmount < 0) { removeAmount = amountLeft - Math.abs(removeAmount); }
					amountLeft -= removeAmount;
					if (thisAmount - removeAmount <= 0) { inventory.remove(item); }
					else { item.setAmount( thisAmount - removeAmount ); }
				}
				
				if (amountLeft <= 0)
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void damageTool(Random random, Player player, ItemStack tool, int damage) 
	{
		if (player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE || tool.getItemMeta().isUnbreakable()) 
		{
			double damageChance = 1.0D / (tool.getEnchantmentLevel(Enchantment.DURABILITY) + 1.0D);
			if (random.nextFloat() < damageChance) damageTool(player, tool, damage); 
		} 
	}

	public static void damageTool(Player player, ItemStack tool, int damage) 
	{
		boolean destroyed = damageTool(tool, damage);
		
		if (destroyed) 
		{
			player.spawnParticle(Particle.ITEM_CRACK, player.getLocation(), 1, tool);
			player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
			
			tool.setAmount(tool.getAmount() - 1);
		} 
	}

	public static boolean damageTool(ItemStack tool, int damage) 
	{
		ItemMeta meta = tool.getItemMeta();
		if (meta instanceof Damageable) 
		{
			Damageable damageable = (Damageable)meta;
			damageable.setDamage(damageable.getDamage() + damage);
			tool.setItemMeta(meta);
			
			return (damageable.getDamage() >= tool.getType().getMaxDurability());
		} 
		return false;
	}
	
	public static void damageItem(ItemStack item, Integer amount)
	{
		Damageable meta = (Damageable)item.getItemMeta();
		
		if (meta.getDamage() + amount >= item.getType().getMaxDurability())
		{
			item.setType(Material.AIR);
		}
		else
		{
			meta.setDamage(meta.getDamage() + amount);
			item.setItemMeta((ItemMeta) meta);
		}
	}
	
	public static void setItemDamage(ItemStack item, Integer amount)
	{
		Damageable meta = (Damageable)item.getItemMeta();
		meta.setDamage(amount);
		item.setItemMeta((ItemMeta) meta);
	}
	
	public static void addAbilityBuff(Player player, Skill skill)
	{
		ItemStack item = player.getInventory().getItemInMainHand();
		
		if (item == null || item.getType() == Material.AIR) { return; }
		
		Material material = item.getType();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = new ArrayList<String>(); if (meta.hasLore()) { lore = meta.getLore(); }
		Integer enchantLevel = 0;
		
		switch (skill)
		{
		case MINING:
			if (Util.isAnyPickaxe(material) == false) { return; }
			lore.add("Double Time");
			enchantLevel = meta.getEnchantLevel(Enchantment.DIG_SPEED);
			meta.addEnchant(Enchantment.DIG_SPEED, enchantLevel + Main.mainConfig.getInt("Mining.DoubleTimeStrength"), true);
			break;
			
		case WOODCUTTING:
			if (Util.isAnyAxe(material) == false) { return; }
			lore.add("Lumberjack");
			enchantLevel = meta.getEnchantLevel(Enchantment.DIG_SPEED);
			meta.addEnchant(Enchantment.DIG_SPEED, enchantLevel + Main.mainConfig.getInt("Woodcutting.LumberjackStrength"), true);
			break;
			
		case DIGGING:
			if (Util.isAnyShovel(material) == false) { return; }
			lore.add("Excavator");
			enchantLevel = meta.getEnchantLevel(Enchantment.DIG_SPEED);
			meta.addEnchant(Enchantment.DIG_SPEED, enchantLevel + Main.mainConfig.getInt("Digging.ExcavatorStrength"), true);
			break;
			
		default:
			return;
		}
		
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public static void removeAbilityBuff(ItemStack item, Skill skill)
	{
		if (item == null || item.getType() == Material.AIR) { return; }
		
		Material material = item.getType();
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		Integer enchantLevel = 0;
		
		if (lore != null && lore.isEmpty() == false)
		{
			switch (skill)
			{
			case MINING:
				if (Util.isAnyPickaxe(material) == false || lore.remove("Double Time") == false) { return; }
				enchantLevel = meta.getEnchantLevel(Enchantment.DIG_SPEED);
				if (enchantLevel <= Main.mainConfig.getInt("Mining.DoubleTimeStrength")) { meta.removeEnchant(Enchantment.DIG_SPEED); }
				else  { meta.addEnchant(Enchantment.DIG_SPEED, enchantLevel - Main.mainConfig.getInt("Mining.DoubleTimeStrength"), true); }
				break;
				
			case WOODCUTTING:
				if (Util.isAnyAxe(material) == false || lore.remove("Lumberjack") == false) { return; }
				enchantLevel = meta.getEnchantLevel(Enchantment.DIG_SPEED);
				if (enchantLevel <= Main.mainConfig.getInt("Woodcutting.LumberjackStrength")) { meta.removeEnchant(Enchantment.DIG_SPEED); }
				else  { meta.addEnchant(Enchantment.DIG_SPEED, enchantLevel - Main.mainConfig.getInt("Woodcutting.LumberjackStrength"), true); }
				break;
				
			case DIGGING:
				if (Util.isAnyShovel(material) == false || lore.remove("Excavator") == false) { return; }
				enchantLevel = meta.getEnchantLevel(Enchantment.DIG_SPEED);
				if (enchantLevel <= Main.mainConfig.getInt("Digging.ExcavatorStrength")) { meta.removeEnchant(Enchantment.DIG_SPEED); }
				else  { meta.addEnchant(Enchantment.DIG_SPEED, enchantLevel - Main.mainConfig.getInt("Digging.ExcavatorStrength"), true); }
				break;
				
			default:
				return;
			}
			
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
	}
	
	public static void removeAbilityBuff(Player player, Skill skill)
	{
		for (ItemStack item : player.getInventory().getContents())
		{
			removeAbilityBuff(item, skill);
		}
		
		if (skill == Skill.ONE_HANDED || skill == Skill.DUAL_WIELD)
		{
			player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0f);
		}
		else if (skill == Skill.SHIELDS)
		{
			player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.0f);
		}
	}
	
	public static void removeAbilityBuffs(ItemStack item)
	{
		for (Skill skill : Skill.values())
		{
			removeAbilityBuff(item, skill);
		}
	}
	
	public static void removeAbilityBuffs(Player player)
	{
		for (ItemStack item : player.getInventory().getContents())
		{
			for (Skill skill : Skill.values())
			{
				removeAbilityBuff(item, skill);
			}
		}
		
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.0f);
		player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.0f);
	}
	
	public static boolean isAnyHoe(Material material)
	{
		return (material == Material.WOODEN_HOE ||
				material == Material.STONE_HOE ||
				material == Material.IRON_HOE ||
				material == Material.GOLDEN_HOE ||
				material == Material.DIAMOND_HOE ||
				material == Material.NETHERITE_HOE);
	}
	
	public static boolean isAnyAxe(Material material)
	{
		return (material == Material.WOODEN_AXE ||
				material == Material.STONE_AXE ||
				material == Material.IRON_AXE ||
				material == Material.GOLDEN_AXE ||
				material == Material.DIAMOND_AXE ||
				material == Material.NETHERITE_AXE);
	}
	
	public static boolean isAnySword(Material material)
	{
		return (material == Material.WOODEN_SWORD ||
				material == Material.STONE_SWORD ||
				material == Material.IRON_SWORD ||
				material == Material.GOLDEN_SWORD ||
				material == Material.DIAMOND_SWORD ||
				material == Material.NETHERITE_SWORD);
	}
	
	public static boolean isAnyPickaxe(Material material)
	{
		return (material == Material.WOODEN_PICKAXE ||
				material == Material.STONE_PICKAXE ||
				material == Material.IRON_PICKAXE ||
				material == Material.GOLDEN_PICKAXE ||
				material == Material.DIAMOND_PICKAXE ||
				material == Material.NETHERITE_PICKAXE);
	}
	
	public static boolean isAnyShovel(Material material)
	{
		return (material == Material.WOODEN_SHOVEL ||
				material == Material.STONE_SHOVEL ||
				material == Material.IRON_SHOVEL ||
				material == Material.GOLDEN_SHOVEL ||
				material == Material.DIAMOND_SHOVEL ||
				material == Material.NETHERITE_SHOVEL);
	}
	
	public static boolean isWoodFence(Material material)
	{
		return (material == Material.ACACIA_FENCE ||
				material == Material.BIRCH_FENCE ||
				material == Material.DARK_OAK_FENCE ||
				material == Material.JUNGLE_FENCE ||
				material == Material.OAK_FENCE ||
				material == Material.SPRUCE_FENCE);
	}
	
	public static boolean isLeaves(Material material)
	{
		return (material == Material.ACACIA_LEAVES ||
				material == Material.BIRCH_LEAVES ||
				material == Material.DARK_OAK_LEAVES ||
				material == Material.JUNGLE_LEAVES ||
				material == Material.OAK_LEAVES ||
				material == Material.SPRUCE_LEAVES);
	}
	
	public static boolean isLog(Material material)
	{
		return (material == Material.ACACIA_LOG ||
				material == Material.BIRCH_LOG ||
				material == Material.DARK_OAK_LOG ||
				material == Material.JUNGLE_LOG ||
				material == Material.OAK_LOG ||
				material == Material.SPRUCE_LOG);
	}
	
	public static boolean isUnarmed(Player player)
	{
		return (player.getEquipment().getItemInMainHand() == null || 
				player.getEquipment().getItemInMainHand().getType() == Material.AIR || 
				player.getEquipment().getItemInMainHand().getType() == Material.STICK || 
				(isAnyHoe(player.getEquipment().getItemInMainHand().getType()) && player.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().contains("staff")));
	}
	
	public static Skill getMainHandSkill(Player player)
	{
		Material mainHand = player.getEquipment().getItemInMainHand().getType();
		Material offHand = player.getEquipment().getItemInOffHand().getType();
		
		if (offHand == Material.ENCHANTED_BOOK && isAnyHoe(mainHand) == true)	{ return Skill.WIZARDRY; }
		if (isUnarmed(player) == true) 						 					{ return Skill.UNARMED; }
		else if (mainHand == Material.BOW || mainHand == Material.CROSSBOW) 	{ return Skill.ARCHERY; }
		else if (Skill.LIGHT_WEAPONS.checkSource(mainHand.toString())) 			{ return Skill.LIGHT_WEAPONS; }
		else if (Skill.HEAVY_WEAPONS.checkSource(mainHand.toString())) 			{ return Skill.HEAVY_WEAPONS; }
		
		return Skill.NONE;
	}
	
	public static Skill getOffHandSkill(Player player)
	{
//		if (player.getEquipment().getItemInOffHand() == null) return Skill.UNARMED;
		Material mainHand = player.getEquipment().getItemInMainHand().getType();
		Material offHand = player.getEquipment().getItemInOffHand().getType();
		
		if (offHand == Material.SHIELD) 											{ return Skill.SHIELDS; }
		else if (Skill.LIGHT_WEAPONS.checkSource(offHand.toString())) 				{ return Skill.LIGHT_WEAPONS; }
		else if (Skill.HEAVY_WEAPONS.checkSource(offHand.toString())) 				{ return Skill.HEAVY_WEAPONS; }
		else if (offHand == Material.ENCHANTED_BOOK && isAnyHoe(mainHand) == true)	{ return Skill.WIZARDRY; }
		
		return Skill.NONE;
	}
	
	public static Skill getWieldSkill(Player player)
	{
		Skill main = getMainHandSkill(player);
		Skill off = getOffHandSkill(player);
		
		if ( (main == Skill.LIGHT_WEAPONS || main == Skill.HEAVY_WEAPONS) && (off == Skill.LIGHT_WEAPONS || off == Skill.HEAVY_WEAPONS) ) { return Skill.DUAL_WIELD; }
		else if ( (main == Skill.LIGHT_WEAPONS || main == Skill.HEAVY_WEAPONS) && off == Skill.SHIELDS ) { return Skill.SHIELDS; }
		else if ( (main == Skill.LIGHT_WEAPONS || main == Skill.HEAVY_WEAPONS) && off == Skill.UNARMED ) { return Skill.ONE_HANDED; }
		else if (main == Skill.ARCHERY) { return Skill.ARCHERY; }
		else if (main == Skill.UNARMED) { return Skill.UNARMED; }
		else if (main == Skill.WIZARDRY && off == Skill.WIZARDRY) { return Skill.WIZARDRY; }
		
		return Skill.NONE;
	}
	
	public static boolean isGrapeVine(Block block)
	{
		if (Util.isLeaves(block.getRelative(BlockFace.EAST).getType()) == true &&
			Util.isWoodFence(block.getRelative(BlockFace.EAST).getRelative(BlockFace.DOWN).getType()) == true)
		{
			return true;
		}
		else if (Util.isWoodFence(block.getRelative(BlockFace.EAST).getType()) == true &&
				 Util.isLeaves(block.getRelative(BlockFace.EAST).getRelative(BlockFace.UP).getType()) == true)
		{
			return true;
		}
		
		if (Util.isLeaves(block.getRelative(BlockFace.WEST).getType()) == true &&
			Util.isWoodFence(block.getRelative(BlockFace.WEST).getRelative(BlockFace.DOWN).getType()) == true)
		{
			return true;
		}
		else if (Util.isWoodFence(block.getRelative(BlockFace.WEST).getType()) == true &&
				 Util.isLeaves(block.getRelative(BlockFace.WEST).getRelative(BlockFace.UP).getType()) == true)
		{
			return true;
		}
		
		if (Util.isLeaves(block.getRelative(BlockFace.NORTH).getType()) == true &&
			Util.isWoodFence(block.getRelative(BlockFace.NORTH).getRelative(BlockFace.DOWN).getType()) == true)
		{
			return true;
		}
		else if (Util.isWoodFence(block.getRelative(BlockFace.NORTH).getType()) == true &&
				 Util.isLeaves(block.getRelative(BlockFace.NORTH).getRelative(BlockFace.UP).getType()) == true)
		{
			return true;
		}
		
		if (Util.isLeaves(block.getRelative(BlockFace.SOUTH).getType()) == true &&
			Util.isWoodFence(block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.DOWN).getType()) == true)
		{
			return true;
		}
		else if (Util.isWoodFence(block.getRelative(BlockFace.SOUTH).getType()) == true &&
				 Util.isLeaves(block.getRelative(BlockFace.SOUTH).getRelative(BlockFace.UP).getType()) == true)
		{
			return true;
		}
		
		return false;
	}
}
