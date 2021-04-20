package er.seven.skills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import net.md_5.bungee.api.ChatColor;

public enum Skill
{
	MINING,
	DIGGING,
	WOODCUTTING,
	HERBALISM,
	FARMING,
	HUSBANDRY,
	FISHING,
	
	BUILDING,
	CARTOGRAPHY,
	ACROBATICS,
	STEALTH,
	
	SMITHING,
	WOODWORKING,
	INVENTION,
	TAILORING,
	COOKING,
	ALCHEMY,
	
	VITALITY,
	UNARMORED,
	LIGHT_ARMOR,
	MEDIUM_ARMOR,
	HEAVY_ARMOR,
	UNARMED,
	LIGHT_WEAPONS,
	HEAVY_WEAPONS,
	ARCHERY,
	ONE_HANDED,
	DUAL_WIELD,
	SHIELDS,
	
	ENCHANTING,
	PIETY,
	BARD,
	WIZARDRY,
	WITCHCRAFT,
	
	NONE;
	
	public static List<Skill> GATHER_SKILLS()
	{
		return Arrays.asList(
				MINING,
				DIGGING,
				WOODCUTTING,
				HERBALISM,
				FARMING,
				HUSBANDRY,
				FISHING);
	}
	
	public static List<Skill> MISC_SKILLS()
	{
		return Arrays.asList(
				BUILDING,
				CARTOGRAPHY,
				ACROBATICS,
				STEALTH);
	}
	
	public static List<Skill> CRAFT_SKILLS()
	{
		return Arrays.asList(
				SMITHING,
				WOODWORKING,
				INVENTION,
				TAILORING,
				COOKING,
				ALCHEMY);
	}
	
	public static List<Skill> COMBAT_SKILLS()
	{
		return Arrays.asList(
				VITALITY,
				UNARMORED,
				LIGHT_ARMOR,
				MEDIUM_ARMOR,
				HEAVY_ARMOR,
				UNARMED,
				LIGHT_WEAPONS,
				HEAVY_WEAPONS,
				ARCHERY,
				ONE_HANDED,
				DUAL_WIELD,
				SHIELDS);
	}
	
	public static List<Skill> MAGIC_SKILLS()
	{
		return Arrays.asList(
				ENCHANTING,
				PIETY,
				BARD,
				WIZARDRY,
				WITCHCRAFT);
	}
	
	public static List<Skill> getSortedValues()
	{
		List<Skill> skillList = new ArrayList<Skill>();
		skillList.addAll( Skill.COMBAT_SKILLS() );
		skillList.addAll( Skill.MAGIC_SKILLS() );
		skillList.addAll( Skill.MISC_SKILLS() );
		skillList.addAll( Skill.GATHER_SKILLS() );
		skillList.addAll( Skill.CRAFT_SKILLS() );
		
		return skillList;
	}
	
	public String getName()
	{
        switch (this)
        {
		case DIGGING:
			return "Digging";
		case FARMING:
			return "Farming";
		case FISHING:
			return "Fishing";
		case HERBALISM:
			return "Herbalism";
		case HUSBANDRY:
			return "Husbandry";
		case MINING:
			return "Mining";
		case WOODCUTTING:
			return "Woodcutting";
		case BUILDING:
			return "Building";
		case VITALITY:
			return "Vitality";
		case UNARMORED:
			return "Unarmored";
		case ACROBATICS:
			return "Acrobatics";
		case ALCHEMY:
			return "Alchemy";
		case ARCHERY:
			return "Archery";
		case BARD:
			return "Bard";
		case CARTOGRAPHY:
			return "Cartography";
		case COOKING:
			return "Cooking";
		case DUAL_WIELD:
			return "Dual Wield";
		case ENCHANTING:
			return "Enchanting";
		case HEAVY_ARMOR:
			return "Heavy Armor";
		case HEAVY_WEAPONS:
			return "Heavy Weapons";
		case INVENTION:
			return "Invention";
		case LIGHT_ARMOR:
			return "Light Armor";
		case LIGHT_WEAPONS:
			return "Light Weapons";
		case MEDIUM_ARMOR:
			return "Medium Armor";
		case ONE_HANDED:
			return "One Handed";
		case PIETY:
			return "Piety";
		case SHIELDS:
			return "Shields";
		case SMITHING:
			return "Smithing";
		case STEALTH:
			return "Stealth";
		case TAILORING:
			return "Tailoring";
		case UNARMED:
			return "Unarmed";
		case WITCHCRAFT:
			return "Witchcraft";
		case WIZARDRY:
			return "Magic";
		case WOODWORKING:
			return "Woodworking";
		default:
			return this.toString();
        }
	}
	
	public Material getIcon()
	{
        switch (this)
        {
		case DIGGING:
			return Material.IRON_SHOVEL;
		case FARMING:
			return Material.WHEAT;
		case FISHING:
			return Material.FISHING_ROD;
		case HERBALISM:
			return Material.CARROT;
		case HUSBANDRY:
			return Material.SADDLE;
		case MINING:
			return Material.IRON_PICKAXE;
		case WOODCUTTING:
			return Material.IRON_AXE;
		case BUILDING:
			return Material.COBBLESTONE;
		case VITALITY:
			return Material.APPLE;
		case UNARMORED:
			return Material.LEATHER;
		case ACROBATICS:
			return Material.RABBIT_FOOT;
		case ALCHEMY:
			return Material.EXPERIENCE_BOTTLE;
		case ARCHERY:
			return Material.BOW;
		case BARD:
			return Material.JUKEBOX;
		case CARTOGRAPHY:
			return Material.FILLED_MAP;
		case COOKING:
			return Material.FURNACE;
		case DUAL_WIELD:
			return Material.DIAMOND_SWORD;
		case ENCHANTING:
			return Material.ENCHANTING_TABLE;
		case HEAVY_ARMOR:
			return Material.IRON_CHESTPLATE;
		case HEAVY_WEAPONS:
			return Material.GOLDEN_AXE;
		case INVENTION:
			return Material.REDSTONE;
		case LIGHT_ARMOR:
			return Material.LEATHER_CHESTPLATE;
		case LIGHT_WEAPONS:
			return Material.GOLDEN_SWORD;
		case MEDIUM_ARMOR:
			return Material.CHAINMAIL_CHESTPLATE;
		case ONE_HANDED:
			return Material.WOODEN_SWORD;
		case PIETY:
			return Material.NETHER_STAR;
		case SHIELDS:
			return Material.SHIELD;
		case SMITHING:
			return Material.ANVIL;
		case STEALTH:
			return Material.LEATHER_BOOTS;
		case TAILORING:
			return Material.LOOM;
		case UNARMED:
			return Material.STICK;
		case WITCHCRAFT:
			return Material.CAULDRON;
		case WIZARDRY:
			return Material.ENCHANTED_BOOK;
		case WOODWORKING:
			return Material.CRAFTING_TABLE;
		default:
			return Material.BOOK;
        }
	}
	
	public Integer getLevel(Player player)
	{
		switch (this) 
        {
		case DIGGING:
			return Main.Instance().diggingLevel.get(player);
		case FARMING:
			return Main.Instance().farmingLevel.get(player);
		case FISHING:
			return Main.Instance().fishingLevel.get(player);
		case HERBALISM:
			return Main.Instance().herbalismLevel.get(player);
		case HUSBANDRY:
			return Main.Instance().husbandryLevel.get(player);
		case MINING:
			return Main.Instance().miningLevel.get(player);
		case WOODCUTTING:
			return Main.Instance().woodcuttingLevel.get(player);
		case BUILDING:
			return Main.Instance().buildingLevel.get(player);
		case VITALITY:
			return Main.Instance().vitalityLevel.get(player);
		case UNARMORED:
			return Main.Instance().unarmoredLevel.get(player);
		case ACROBATICS:
			return Main.Instance().acrobaticsLevel.get(player);
		case ALCHEMY:
			return Main.Instance().alchemyLevel.get(player);
		case ARCHERY:
			return Main.Instance().archeryLevel.get(player);
		case BARD:
			return Main.Instance().bardLevel.get(player);
		case CARTOGRAPHY:
			return Main.Instance().cartographyLevel.get(player);
		case COOKING:
			return Main.Instance().cookingLevel.get(player);
		case DUAL_WIELD:
			return Main.Instance().dualWieldLevel.get(player);
		case ENCHANTING:
			return Main.Instance().enchantingLevel.get(player);
		case HEAVY_ARMOR:
			return Main.Instance().heavyArmorLevel.get(player);
		case HEAVY_WEAPONS:
			return Main.Instance().heavyWeaponsLevel.get(player);
		case INVENTION:
			return Main.Instance().inventionLevel.get(player);
		case LIGHT_ARMOR:
			return Main.Instance().lightArmorLevel.get(player);
		case LIGHT_WEAPONS:
			return Main.Instance().lightWeaponsLevel.get(player);
		case MEDIUM_ARMOR:
			return Main.Instance().mediumArmorLevel.get(player);
		case ONE_HANDED:
			return Main.Instance().oneHandedLevel.get(player);
		case PIETY:
			return Main.Instance().pietyLevel.get(player);
		case SHIELDS:
			return Main.Instance().shieldsLevel.get(player);
		case SMITHING:
			return Main.Instance().smithingLevel.get(player);
		case STEALTH:
			return Main.Instance().stealthLevel.get(player);
		case TAILORING:
			return Main.Instance().tailoringLevel.get(player);
		case UNARMED:
			return Main.Instance().unarmedLevel.get(player);
		case WITCHCRAFT:
			return Main.Instance().witchcraftLevel.get(player);
		case WIZARDRY:
			return Main.Instance().wizardryLevel.get(player);
		case WOODWORKING:
			return Main.Instance().woodworkingLevel.get(player);
		default:
			return 1;
        }
	}
	
	public Integer getXP(Player player)
	{
		switch (this) 
        {
		case DIGGING:
			return Main.Instance().diggingXP.get(player);
		case FARMING:
			return Main.Instance().farmingXP.get(player);
		case FISHING:
			return Main.Instance().fishingXP.get(player);
		case HERBALISM:
			return Main.Instance().herbalismXP.get(player);
		case HUSBANDRY:
			return Main.Instance().husbandryXP.get(player);
		case MINING:
			return Main.Instance().miningXP.get(player);
		case WOODCUTTING:
			return Main.Instance().woodcuttingXP.get(player);
		case BUILDING:
			return Main.Instance().buildingXP.get(player);
		case VITALITY:
			return Main.Instance().vitalityXP.get(player);
		case UNARMORED:
			return Main.Instance().unarmoredXP.get(player);
		case ACROBATICS:
			return Main.Instance().acrobaticsXP.get(player);
		case ALCHEMY:
			return Main.Instance().alchemyXP.get(player);
		case ARCHERY:
			return Main.Instance().archeryXP.get(player);
		case BARD:
			return Main.Instance().bardXP.get(player);
		case CARTOGRAPHY:
			return Main.Instance().cartographyXP.get(player);
		case COOKING:
			return Main.Instance().cookingXP.get(player);
		case DUAL_WIELD:
			return Main.Instance().dualWieldXP.get(player);
		case ENCHANTING:
			return Main.Instance().enchantingXP.get(player);
		case HEAVY_ARMOR:
			return Main.Instance().heavyArmorXP.get(player);
		case HEAVY_WEAPONS:
			return Main.Instance().heavyWeaponsXP.get(player);
		case INVENTION:
			return Main.Instance().inventionXP.get(player);
		case LIGHT_ARMOR:
			return Main.Instance().lightArmorXP.get(player);
		case LIGHT_WEAPONS:
			return Main.Instance().lightWeaponsXP.get(player);
		case MEDIUM_ARMOR:
			return Main.Instance().mediumArmorXP.get(player);
		case ONE_HANDED:
			return Main.Instance().oneHandedXP.get(player);
		case PIETY:
			return Main.Instance().pietyXP.get(player);
		case SHIELDS:
			return Main.Instance().shieldsXP.get(player);
		case SMITHING:
			return Main.Instance().smithingXP.get(player);
		case STEALTH:
			return Main.Instance().stealthXP.get(player);
		case TAILORING:
			return Main.Instance().tailoringXP.get(player);
		case UNARMED:
			return Main.Instance().unarmedXP.get(player);
		case WITCHCRAFT:
			return Main.Instance().witchcraftXP.get(player);
		case WIZARDRY:
			return Main.Instance().wizardryXP.get(player);
		case WOODWORKING:
			return Main.Instance().woodworkingXP.get(player);
		default:
			return 1;
        }
	}
	
	public Integer setXP(Player player, Integer value)
	{
		switch (this) 
        {
		case DIGGING:
			return Main.Instance().diggingXP.replace(player, value);
		case FARMING:
			return Main.Instance().farmingXP.replace(player, value);
		case FISHING:
			return Main.Instance().fishingXP.replace(player, value);
		case HERBALISM:
			return Main.Instance().herbalismXP.replace(player, value);
		case HUSBANDRY:
			return Main.Instance().husbandryXP.replace(player, value);
		case MINING:
			return Main.Instance().miningXP.replace(player, value);
		case WOODCUTTING:
			return Main.Instance().woodcuttingXP.replace(player, value);
		case BUILDING:
			return Main.Instance().buildingXP.replace(player, value);
		case VITALITY:
			return Main.Instance().vitalityXP.replace(player, value);
		case UNARMORED:
			return Main.Instance().unarmoredXP.replace(player, value);
		case ACROBATICS:
			return Main.Instance().acrobaticsXP.replace(player, value);
		case ALCHEMY:
			return Main.Instance().alchemyXP.replace(player, value);
		case ARCHERY:
			return Main.Instance().archeryXP.replace(player, value);
		case BARD:
			return Main.Instance().bardXP.replace(player, value);
		case CARTOGRAPHY:
			return Main.Instance().cartographyXP.replace(player, value);
		case COOKING:
			return Main.Instance().cookingXP.replace(player, value);
		case DUAL_WIELD:
			return Main.Instance().dualWieldXP.replace(player, value);
		case ENCHANTING:
			return Main.Instance().enchantingXP.replace(player, value);
		case HEAVY_ARMOR:
			return Main.Instance().heavyArmorXP.replace(player, value);
		case HEAVY_WEAPONS:
			return Main.Instance().heavyWeaponsXP.replace(player, value);
		case INVENTION:
			return Main.Instance().inventionXP.replace(player, value);
		case LIGHT_ARMOR:
			return Main.Instance().lightArmorXP.replace(player, value);
		case LIGHT_WEAPONS:
			return Main.Instance().lightWeaponsXP.replace(player, value);
		case MEDIUM_ARMOR:
			return Main.Instance().mediumArmorXP.replace(player, value);
		case ONE_HANDED:
			return Main.Instance().oneHandedXP.replace(player, value);
		case PIETY:
			return Main.Instance().pietyXP.replace(player, value);
		case SHIELDS:
			return Main.Instance().shieldsXP.replace(player, value);
		case SMITHING:
			return Main.Instance().smithingXP.replace(player, value);
		case STEALTH:
			return Main.Instance().stealthXP.replace(player, value);
		case TAILORING:
			return Main.Instance().tailoringXP.replace(player, value);
		case UNARMED:
			return Main.Instance().unarmedXP.replace(player, value);
		case WITCHCRAFT:
			return Main.Instance().witchcraftXP.replace(player, value);
		case WIZARDRY:
			return Main.Instance().wizardryXP.replace(player, value);
		case WOODWORKING:
			return Main.Instance().woodworkingXP.replace(player, value);
		default:
			return 1;
        }
	}
	
	public Integer setLevel(Player player, Integer value)
	{
		switch (this) 
        {
		case DIGGING:
			return Main.Instance().diggingLevel.replace(player, value);
		case FARMING:
			return Main.Instance().farmingLevel.replace(player, value);
		case FISHING:
			return Main.Instance().fishingLevel.replace(player, value);
		case HERBALISM:
			return Main.Instance().herbalismLevel.replace(player, value);
		case HUSBANDRY:
			return Main.Instance().husbandryLevel.replace(player, value);
		case MINING:
			return Main.Instance().miningLevel.replace(player, value);
		case WOODCUTTING:
			return Main.Instance().woodcuttingLevel.replace(player, value);
		case BUILDING:
			return Main.Instance().buildingLevel.replace(player, value);
		case VITALITY:
			return Main.Instance().vitalityLevel.replace(player, value);
		case UNARMORED:
			return Main.Instance().unarmoredLevel.replace(player, value);
		case ACROBATICS:
			return Main.Instance().acrobaticsLevel.replace(player, value);
		case ALCHEMY:
			return Main.Instance().alchemyLevel.replace(player, value);
		case ARCHERY:
			return Main.Instance().archeryLevel.replace(player, value);
		case BARD:
			return Main.Instance().bardLevel.replace(player, value);
		case CARTOGRAPHY:
			return Main.Instance().cartographyLevel.replace(player, value);
		case COOKING:
			return Main.Instance().cookingLevel.replace(player, value);
		case DUAL_WIELD:
			return Main.Instance().dualWieldLevel.replace(player, value);
		case ENCHANTING:
			return Main.Instance().enchantingLevel.replace(player, value);
		case HEAVY_ARMOR:
			return Main.Instance().heavyArmorLevel.replace(player, value);
		case HEAVY_WEAPONS:
			return Main.Instance().heavyWeaponsLevel.replace(player, value);
		case INVENTION:
			return Main.Instance().inventionLevel.replace(player, value);
		case LIGHT_ARMOR:
			return Main.Instance().lightArmorLevel.replace(player, value);
		case LIGHT_WEAPONS:
			return Main.Instance().lightWeaponsLevel.replace(player, value);
		case MEDIUM_ARMOR:
			return Main.Instance().mediumArmorLevel.replace(player, value);
		case ONE_HANDED:
			return Main.Instance().oneHandedLevel.replace(player, value);
		case PIETY:
			return Main.Instance().pietyLevel.replace(player, value);
		case SHIELDS:
			return Main.Instance().shieldsLevel.replace(player, value);
		case SMITHING:
			return Main.Instance().smithingLevel.replace(player, value);
		case STEALTH:
			return Main.Instance().stealthLevel.replace(player, value);
		case TAILORING:
			return Main.Instance().tailoringLevel.replace(player, value);
		case UNARMED:
			return Main.Instance().unarmedLevel.replace(player, value);
		case WITCHCRAFT:
			return Main.Instance().witchcraftLevel.replace(player, value);
		case WIZARDRY:
			return Main.Instance().wizardryLevel.replace(player, value);
		case WOODWORKING:
			return Main.Instance().woodworkingLevel.replace(player, value);
		default:
			return 1;
        }
	}
	
	public Integer getCooldown(Player player)
	{
		return Main.Instance().skillCooldown.get(new SkillPair(player, this));
	}
	
	public void startCooldown(Player player)
	{
		Main.Instance().skillCooldown.replace(new SkillPair(player, this), this.getCooldownTime(player));
	}
	
	public Integer getCooldownTime(Player player)
	{
		return (int)Math.round( Main.mainConfig.getInt("Abilities.CooldownBaseSeconds") - (this.getLevel(player) * Main.mainConfig.getDouble("Abilities.CooldownFactor")) );
	}
	
	public boolean isCooldownDone(Player player)
	{
		return (Main.Instance().skillCooldown.get(new SkillPair(player, this)) == 0);
	}
	
	public boolean isAbilityReady(Player player)
	{
		return (Main.Instance().skillCooldown.get(new SkillPair(player, this)) < 0 && this.isAbilityActive(player) == false);
	}
	
	public boolean readyAbility(Player player)
	{
		if (this == Skill.NONE) return false;
		
		if (this.isAbilityActive(player) == false && this.isAbilityReady(player) == false)
		{
			if (this.isCooldownDone(player) == true)
			{				
				//////////////////////////////////
				//	TRIGGER ABLITIES
				for (int n = 0; n < Main.GetAbilities().length; n++) 
				{ 
					if (Main.GetAbilities()[n].isPassive() == false && Main.GetAbilities()[n].getSkill() == this)
					{
						Main.GetAbilities()[n].onReady(player);
						Main.Instance().skillCooldown.replace(new SkillPair(player, this), -Main.mainConfig.getInt("Abilities.ReadySeconds"));
						return true;
					}
				}
				//////////////////////////////////
			}
			else
			{
				Util.sendActionbar(player, ChatColor.GRAY + "You are too exhausted to use this ability [" + ChatColor.GOLD + this.getCooldown(player) + "s" + ChatColor.GRAY + "]", true);
				return false;
			}
		}
		
		return false;
	}
	
	public void unreadyAbility(Player player)
	{
		Main.Instance().skillCooldown.replace(new SkillPair(player, this), 0);
		
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < Main.GetAbilities().length; n++) { if (Main.GetAbilities()[n].getSkill() != this) continue; else Main.GetAbilities()[n].onUnready(player); }
		//////////////////////////////////
	}
	
	public boolean isAbilityActive(Player player)
	{
		return (Main.Instance().skillTimer.get(new SkillPair(player, this)) > 0);
	}
	
	public void activateAbility(Player player)
	{
		Main.Instance().skillTimer.put(new SkillPair(player, this), this.getAbilityDuration(player));
		
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < Main.GetAbilities().length; n++) { if (Main.GetAbilities()[n].getSkill() != this) continue; else Main.GetAbilities()[n].onActivate(player); }
		//////////////////////////////////
		
//		switch (this)
//		{
//		case DUAL_WIELD:
//			Util.sendActionbar(player, ChatColor.RED + "- FLURRY -", true);
//			player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(Main.Instance().dualWield_Flurry * player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getBaseValue());
//			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3, 0.6f);
//			break;
//		}
	}
	
	public void deactivateAbility(Player player)
	{
		//////////////////////////////////
		//	TRIGGER ABLITIES
		for (int n = 0; n < Main.GetAbilities().length; n++) { if (Main.GetAbilities()[n].getSkill() != this) continue; else Main.GetAbilities()[n].onDeactivate(player); }
		//////////////////////////////////
	}
	
	public Integer getAbilityDuration(Player player)
	{
		return (int)Math.round( Math.pow(this.getLevel(player), Main.mainConfig.getDouble("Abilities.DurationPower")) + (Main.mainConfig.getInt("Abilities.DurationBaseSeconds") * 20) );
	}
	
	public boolean checkSource(String source)
	{
		if (Main.Instance().skillSources.getStringList(this.toString().toLowerCase() + "-sources").size() <= 0) { return false; }
		return Main.Instance().skillSources.getStringList(this.toString().toLowerCase() + "-sources").contains(source);
	}
	
	public Integer getSourceXP(String source)
	{
		if (Main.Instance().skillSources.getIntegerList(this.toString().toLowerCase() + "-xp").size() <= 0) { return 1; }
		Integer index = Main.Instance().skillSources.getStringList(this.toString().toLowerCase() + "-sources").indexOf(source);
		return Main.Instance().skillSources.getIntegerList(this.toString().toLowerCase() + "-xp").get(index);
	}
}
