package er.seven.skills;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.ChatPaginator;

import com.aim.coltonjgriswold.sga.api.SimpleGui;
import com.aim.coltonjgriswold.sga.api.gui.ISimpleAction;
import com.aim.coltonjgriswold.sga.api.gui.ISimpleGuiPage;
import com.codingforcookies.armorequip.ArmorListener;
import com.codingforcookies.armorequip.DispenserArmorListener;

import er.seven.skills.abilities.Ability;
import er.seven.skills.mobs.RPGMobs;
import er.seven.skills.spells.Spell;

public class Main extends JavaPlugin
{
	public final JavaPlugin plugin = this;
	private static Main instance;
	  
    public static YamlConfiguration mainConfig = new YamlConfiguration();
    private static boolean savingMainYaml = false;
    private static boolean queueSavingMainYaml = false;
    
    public Integer mobCount = 0;
    public Integer maxMobCount = 60;
    
    public YamlConfiguration skillSources = new YamlConfiguration();
    
    public SkillsListener skills;
    public Magic magic;
    public static SkillsListener GetSkills()
    {
    	return Instance().skills;
    }
    public static Ability[] GetAbilities()
    {
    	return Instance().skills.abilities;
    }
    public static ArrayList<Spell> GetSpells()
    {
    	return Instance().magic.spells;
    }
    
    public ArrayList<Skill> disabledSkills = new ArrayList<Skill>(Arrays.asList(
    		Skill.WITCHCRAFT,
    		Skill.BARD
    ));
    
    public HashMap<Player, SimpleGui> skillGUI = new HashMap<>();

    public HashMap<SkillPair, Integer> skillCooldown = new HashMap<>();
    public HashMap<SkillPair, Integer> skillTimer = new HashMap<>();
    
    public HashMap<SkillPair, BossBar> skillBar = new HashMap<>();
    public HashMap<SkillPair, Integer> skillBarLifetime = new HashMap<>();
    
    public HashMap<Player, BossBar> healthBar = new HashMap<>();
    public HashMap<Player, Integer> healthBarLifetime = new HashMap<>();
    public HashMap<Player, LivingEntity> healthBarTarget = new HashMap<>();
    
    public HashMap<Player, Integer> combatLevel = new HashMap<>();
    public HashMap<Player, Integer> totalLevel = new HashMap<>();
    public HashMap<Player, Integer> totalXP = new HashMap<>();
    
    public HashMap<Player, Integer> miningLevel = new HashMap<>();
    public HashMap<Player, Integer> miningXP = new HashMap<>();
    public HashMap<Player, Integer> diggingLevel = new HashMap<>();
    public HashMap<Player, Integer> diggingXP = new HashMap<>();
    public HashMap<Player, Integer> woodcuttingLevel = new HashMap<>();
    public HashMap<Player, Integer> woodcuttingXP = new HashMap<>();
    public HashMap<Player, Integer> herbalismLevel = new HashMap<>();
    public HashMap<Player, Integer> herbalismXP = new HashMap<>();
    public HashMap<Player, Integer> farmingLevel = new HashMap<>();
    public HashMap<Player, Integer> farmingXP = new HashMap<>();
    public HashMap<Player, Integer> husbandryLevel = new HashMap<>();
    public HashMap<Player, Integer> husbandryXP = new HashMap<>();
    public HashMap<Player, Integer> fishingLevel = new HashMap<>();
    public HashMap<Player, Integer> fishingXP = new HashMap<>();
    public HashMap<Player, Integer> buildingLevel = new HashMap<>();
    public HashMap<Player, Integer> buildingXP = new HashMap<>();
    public HashMap<Player, Integer> vitalityLevel = new HashMap<>();
    public HashMap<Player, Integer> vitalityXP = new HashMap<>();
    public HashMap<Player, Integer> unarmoredLevel = new HashMap<>();
    public HashMap<Player, Integer> unarmoredXP = new HashMap<>();
    public HashMap<Player, Integer> acrobaticsLevel = new HashMap<>();
    public HashMap<Player, Integer> alchemyLevel = new HashMap<>();
    public HashMap<Player, Integer> archeryLevel = new HashMap<>();
    public HashMap<Player, Integer> bardLevel = new HashMap<>();
    public HashMap<Player, Integer> cartographyLevel = new HashMap<>();
    public HashMap<Player, Integer> cookingLevel = new HashMap<>();
    public HashMap<Player, Integer> dualWieldLevel = new HashMap<>();
    public HashMap<Player, Integer> enchantingLevel = new HashMap<>();
    public HashMap<Player, Integer> heavyArmorLevel = new HashMap<>();
    public HashMap<Player, Integer> heavyWeaponsLevel = new HashMap<>();
    public HashMap<Player, Integer> inventionLevel = new HashMap<>();
    public HashMap<Player, Integer> lightArmorLevel = new HashMap<>();
    public HashMap<Player, Integer> lightWeaponsLevel = new HashMap<>();
    public HashMap<Player, Integer> mediumArmorLevel = new HashMap<>();
    public HashMap<Player, Integer> oneHandedLevel = new HashMap<>();
    public HashMap<Player, Integer> pietyLevel = new HashMap<>();
    public HashMap<Player, Integer> shieldsLevel = new HashMap<>();
    public HashMap<Player, Integer> smithingLevel = new HashMap<>();
    public HashMap<Player, Integer> stealthLevel = new HashMap<>();
    public HashMap<Player, Integer> tailoringLevel = new HashMap<>();
    public HashMap<Player, Integer> unarmedLevel = new HashMap<>();
    public HashMap<Player, Integer> witchcraftLevel = new HashMap<>();
    public HashMap<Player, Integer> wizardryLevel = new HashMap<>();
    public HashMap<Player, Integer> woodworkingLevel = new HashMap<>();
    public HashMap<Player, Integer> acrobaticsXP = new HashMap<>();
    public HashMap<Player, Integer> alchemyXP = new HashMap<>();
    public HashMap<Player, Integer> archeryXP = new HashMap<>();
    public HashMap<Player, Integer> bardXP = new HashMap<>();
    public HashMap<Player, Integer> cartographyXP = new HashMap<>();
    public HashMap<Player, Integer> cookingXP = new HashMap<>();
    public HashMap<Player, Integer> dualWieldXP = new HashMap<>();
    public HashMap<Player, Integer> enchantingXP = new HashMap<>();
    public HashMap<Player, Integer> heavyArmorXP = new HashMap<>();
    public HashMap<Player, Integer> heavyWeaponsXP = new HashMap<>();
    public HashMap<Player, Integer> inventionXP = new HashMap<>();
    public HashMap<Player, Integer> lightArmorXP = new HashMap<>();
    public HashMap<Player, Integer> lightWeaponsXP = new HashMap<>();
    public HashMap<Player, Integer> mediumArmorXP = new HashMap<>();
    public HashMap<Player, Integer> oneHandedXP = new HashMap<>();
    public HashMap<Player, Integer> pietyXP = new HashMap<>();
    public HashMap<Player, Integer> shieldsXP = new HashMap<>();
    public HashMap<Player, Integer> smithingXP = new HashMap<>();
    public HashMap<Player, Integer> stealthXP = new HashMap<>();
    public HashMap<Player, Integer> tailoringXP = new HashMap<>();
    public HashMap<Player, Integer> unarmedXP = new HashMap<>();
    public HashMap<Player, Integer> witchcraftXP = new HashMap<>();
    public HashMap<Player, Integer> wizardryXP = new HashMap<>();
    public HashMap<Player, Integer> woodworkingXP = new HashMap<>();
    
    public static Main Instance() { return instance; }
    
    @Override
    public void onEnable() 
    {
    	instance = this;
    	skills = new SkillsListener();
    	magic = new Magic();
    	
    	Load();
    	
    	getServer().getPluginManager().registerEvents(skills, this);
		getServer().getPluginManager().registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
		getServer().getPluginManager().registerEvents(new DispenserArmorListener(), this);
		
		if (mainConfig.getBoolean("Settings.RPGMobs") == true)
		{
			getServer().getPluginManager().registerEvents(new RPGMobs(), this);
		}
		
//		getServer().getPluginManager().registerEvents(new DiscordListener(), this);
    	
        RunScheduler();
    }
    
    @Override
    public void onDisable() 
    {
        org.bukkit.event.HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        saveAllPlayerData();
    }
    
    public void Load()
    {
    	mainConfig = loadMainYaml();
    	skillSources = loadSourcesYaml();
    	magic.Load();
    	
    	maxMobCount = mainConfig.getInt("Settings.MobCount");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {    	
    	if (cmd.getName().equalsIgnoreCase("erskills"))
    	{
    		if (args.length == 0 || args[0].equalsIgnoreCase("help"))
    		{
				sender.sendMessage(ChatColor.BLUE + "-------------------------------");
    			sender.sendMessage(ChatColor.GOLD + "EternalRealms Skills" + ChatColor.GRAY +" - Help 1/1");
    			sender.sendMessage(ChatColor.BLUE + "-------------------------------");
    			sender.sendMessage(ChatColor.GOLD + "/erskills reload" + ChatColor.GRAY + " Reload config and XP sources. (Admin only)");
    			sender.sendMessage(ChatColor.GOLD + "/resetskills" + ChatColor.GRAY + " Reset your skills to level 1.");
    			return true;
    		}
    		else if (args[0].equalsIgnoreCase("reload") && sender.isOp())
    		{
    			sender.sendMessage(ChatColor.GREEN + "Reloading skill config and XP sources...");
    			Load();
    			sender.sendMessage(ChatColor.GREEN + "Finished reloading skill config and sources.");
    			
    			return true;
    		}
    		else if (args[0].equalsIgnoreCase("report") && sender.isOp())
    		{
    			sender.sendMessage(ChatColor.GREEN + "" + mobCount + " / " + maxMobCount + " mobs");
    			
    			return true;
    		}
    	}
    	else if (cmd.getName().equalsIgnoreCase("resetskills"))
    	{
    		Player player = (Player)sender;
    		deletePlayerData(player);
    		Main.loadPlayerData(player);
            Util.updateCharacter(player);
            sender.sendMessage(ChatColor.RED + "Your skills have been reset.");
            
            return true;
    	}
		else if (cmd.getName().equalsIgnoreCase("skills"))
    	{
			Player player = (Player)sender;
			
			SimpleGui gui = skillGUI.get(player);
			ISimpleGuiPage page = gui.createPage(
					ChatColor.GOLD + "LVL: " + ChatColor.GRAY + totalLevel.get(player) +
					ChatColor.GOLD + " | " + 
					ChatColor.GOLD + "XP: " + ChatColor.GRAY + totalXP.get(player), 36);
			gui.removePage(0);
			gui.addPages(page);
			
			int slot = 0;
			int wordWrap = 42;
			for (Skill skill : Skill.getSortedValues())
			{
				if (disabledSkills.contains(skill)) continue;
				
				ItemStack icon = new ItemStack(skill.getIcon());
				ItemMeta meta = icon.getItemMeta();
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.setDisplayName(ChatColor.GOLD + skill.getName());
				
				List<String> lore = new ArrayList<String>();
				lore.add(  ChatColor.GRAY + "Level: " + ChatColor.BLUE + skill.getLevel(player) + " " + ChatColor.GRAY + (int)skill.getXP(player) + "/" + (int)Util.getTNL(skill.getLevel(player)) );

				for (int n = 0; n < skills.abilities.length; n++)
				{					
					if (skills.abilities[n].getSkill() == skill)
					{
						lore.add("");
						if (skills.abilities[n].isPassive())
						{							
							lore.addAll( Arrays.asList(
									ChatPaginator.wordWrap(
											ChatColor.BLUE + "[Passive] " + ChatColor.GOLD + skills.abilities[n].getName(), wordWrap)
									));
							
							lore.addAll( Arrays.asList(
									ChatPaginator.wordWrap(
											ChatColor.GRAY + "  " + skills.abilities[n].getDescription(player), wordWrap)
									));
						}
						else
						{
							Integer abilityTimeDur = (int)(skill.getAbilityDuration(player) / 20);
							Integer abilityTimeCD = skill.getCooldownTime(player);
							
							lore.addAll( Arrays.asList(
									ChatPaginator.wordWrap(
											ChatColor.RED + "[Active] " + ChatColor.GOLD + skills.abilities[n].getName(), wordWrap)
									));
							
							lore.add(ChatColor.WHITE + "[Duration: " + ChatColor.GOLD + abilityTimeDur + ChatColor.WHITE + 
										"s] [Cooldown: " + ChatColor.GOLD + abilityTimeCD + ChatColor.WHITE + "s]");
							
							lore.addAll( Arrays.asList(
									ChatPaginator.wordWrap(
											ChatColor.GRAY + "  " + skills.abilities[n].getDescription(player), wordWrap)
									));
						}
					}
				}
				
				meta.setLore(lore);
				
				icon.setItemMeta(meta);
				page.setSimpleButton(icon, slot, new ISimpleAction() {
					
					@Override
					public void run(Player arg0, ClickType arg1) 
					{
					}
		        });
				
				slot++;
			}
			
			gui.open(player, 0);
			
			return true;
		}
    	
        return false;
    }

    public void RunScheduler()
	{
		// Save player data
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(Main.Instance(), new Runnable() 
		{
			@Override
			public void run()
			{
				if (Bukkit.getServer().getOnlinePlayers().size() > 0)
				{
					saveAllPlayerData();
				}
			}
		}, 20 * Main.mainConfig.getInt("Settings.SaveSecondsInterval"), 20 * Main.mainConfig.getInt("Settings.SaveSecondsInterval"));
    	
		//	Per second
		scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() 
		{
			@Override
			public void run()
			{
				List<World> worlds = Bukkit.getWorlds();
				
				for (World world : worlds)
				{
					if (world.getEnvironment() == World.Environment.NORMAL)
					{
						List<LivingEntity> entities = world.getLivingEntities();
						
						for (LivingEntity entity : entities)
						{
							//	Handle entity effects
							Set<String> tags = entity.getScoreboardTags();
							
							for (String tag : tags)
							{
								String[] set = tag.split(":");
								
								if (set.length > 1)
								{
									//	Bleeding
									if (set[0].equals("BLEED"))
									{
										Integer remainingTime = Integer.parseInt(set[1]);
										remainingTime -= 1;
										
										entity.removeScoreboardTag(tag);
										
										if (remainingTime >= 0)
										{
											entity.addScoreboardTag("BLEED:" + remainingTime.toString());
										}
										
										entity.damage(1);
									}
								}
							}
						}
					}
				}
				
				for( Player player : Bukkit.getServer().getOnlinePlayers() )
                {					
					//  Kill health bars that live too long
                    if (Main.Instance().healthBar.get(player).isVisible() == true && Main.Instance().healthBarLifetime.get(player) >= 60)
                    {
                        Main.Instance().healthBar.get(player).setVisible(false);
                    }
                    
					for (Skill skill : Skill.values())
			        {
						SkillPair skillPair = new SkillPair(player, skill);
						
						//  Kill skill bars that live too long
	                    if (Main.Instance().skillBar.get(skillPair).isVisible() == true && Main.Instance().skillBarLifetime.get(skillPair) >= 60)
	                    {
	                        Main.Instance().skillBar.get(skillPair).setVisible(false);
	                    }
	                    
	                    //	Handle skill cooldowns
	                    Integer cooldown = skill.getCooldown(player);
	                    if (cooldown > 0)
                    	{
                    		Main.Instance().skillCooldown.replace(skillPair, cooldown - 1);
                    	}
                    	else if (cooldown < 0)
                    	{
                    		Main.Instance().skillCooldown.replace(skillPair, cooldown + 1);
                    		if (cooldown == -1 && skill.isAbilityActive(player) == false)
                    		{
                    			skill.unreadyAbility(player);
                    		}
                    	}
			        }
                }
			}
		}, 20, 20);
		
		//	Per half second
		scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() 
		{
			@Override
			public void run()
			{
                for( Player player : Bukkit.getServer().getOnlinePlayers() )
                {
                	//	Update player targets
					healthBarTarget.put(player, Util.getTargetOf(player));
                }
			}
		}, 0, 10);

		//	Per tick
		scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() 
		{
			@Override
			public void run()
			{
                for( Player player : Bukkit.getServer().getOnlinePlayers() )
                {
                	//  Manage lifetime of health bars
                    if (Main.Instance().healthBar.get(player).isVisible() == true)
                    {
                        Main.Instance().healthBarLifetime.replace(player, Main.Instance().healthBarLifetime.get(player) + 1);
                    }
                    
                    //	Update health bars
                    if (healthBarTarget.get(player) != null)
                    {
                    	Util.sendHealthBar(player, healthBarTarget.get(player));
                    }
                    
                	for (Skill skill : Skill.values())
			        {
                		SkillPair skillPair = new SkillPair(player, skill);
                		
                		//  Manage lifetime of skill bars
	                    if (Main.Instance().skillBar.get(skillPair).isVisible() == true)
	                    {
	                        Main.Instance().skillBarLifetime.replace(skillPair, Main.Instance().skillBarLifetime.get(skillPair) + 1);
	                    }
	                    
	                    //	Handle ability timers
	                    Integer timer = Main.Instance().skillTimer.get(skillPair);
	                    if (timer != null)
	                    {
	                    	if (timer > 0)
	                    	{
	                    		Main.Instance().skillTimer.replace(skillPair, timer - 1);
	                    		if (timer == 1)
	                    		{
	                    			skill.unreadyAbility(player);
	                    			skill.startCooldown(player);
	                    			skill.deactivateAbility(player);
	                    		}
	                    	}
	                    }
			        }
                }
			}
		}, 0, 1);
	}
    
    public void saveMainYaml()
    {
        if(savingMainYaml==false)
        {
            savingMainYaml = true;
            Bukkit.getScheduler().runTaskAsynchronously(plugin, new Runnable()
            {
            	public void run()
	            {
	                saveYaml(mainConfig, "plugins/EternalRealms-Skills/config.yml");
	                savingMainYaml = false;
	            }
            });
        }
        else
        {
            if(queueSavingMainYaml==false)
            {
                queueSavingMainYaml = true;
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable()
                {
                	public void run()
	                {
	                    saveMainYaml();	//Recursion
	                    queueSavingMainYaml = false;
	                }
                },20*5);
            }
        }
    }
    
    public static YamlConfiguration loadMainYaml()
    {  
        YamlConfiguration yml = new YamlConfiguration();
        
        yml.addDefault("Settings.XPRate", 1.0f);
        yml.addDefault("Settings.MaxLevel", 100);
        yml.addDefault("Settings.SaveSecondsInterval", 300);
        yml.addDefault("Settings.RPGMobs", true);
        yml.addDefault("Settings.MobCount", 60);
        
        yml.addDefault("Abilities.ReadySeconds", 4);
        yml.addDefault("Abilities.CooldownFactor", 1.8f);
        yml.addDefault("Abilities.CooldownBaseSeconds", 240);
        yml.addDefault("Abilities.DurationPower", 1.5f);
        yml.addDefault("Abilities.DurationBaseSeconds", 10);
        
        yml.addDefault("Combat.WeaponDamageScale", 0.5f);
        yml.addDefault("Combat.TradeDamageScale", 0.8f);
        yml.addDefault("Combat.CritChance", 0.05f);
        
        yml.addDefault("Trade.DoubleDropsChance", 1.0f);
        yml.addDefault("Trade.LootingChance", 0.3f);
        
		//////////////////////////////////
		//	REGISTER ABILITY VARIABLES
		for (int n = 0; n < Main.GetAbilities().length; n++) { Main.GetAbilities()[n].registerConfigVars(yml); }
		//////////////////////////////////
		
		yml.options().copyDefaults(true);
        
        try
        {
            yml.load("plugins/EternalRealms-Skills/config.yml");
        }
        catch(FileNotFoundException e)
        {
            try
            {            	
            	yml.save("plugins/EternalRealms-Skills/config.yml");	//Create the file if it didn't exist
            }
            catch(Exception e2){e.printStackTrace();}
        }
        catch(Exception e){e.printStackTrace();}
        
        return yml;
    }
  
    public static YamlConfiguration loadYaml(String ymlFile)
    {  
        YamlConfiguration yml = new YamlConfiguration();
        
        try
        {
            yml.load(ymlFile);
        }
        catch(FileNotFoundException e)
        {
            try
            {
                yml.save(ymlFile);	//Create the file if it didn't exist
            }
            catch(Exception e2){e.printStackTrace();}
        }
        catch(Exception e){e.printStackTrace();}
        
        return yml;
    }

    public static boolean saveYaml(YamlConfiguration yamlConfig, String ymlFile)
    {
        try
        {
            yamlConfig.save(ymlFile);
            return true;
        }
        catch(Exception e){e.printStackTrace();}
        
        return false;
    }
    
    public static boolean deleteYaml(String ymlFile)
    {
        try
        {
            File yml = new File(ymlFile);
            yml.delete();
            return true;
        }
        catch(Exception e){e.printStackTrace();}
        
        return false;
    }
    
    public static void loadPlayerData(Player player)
    {
    	YamlConfiguration skillData = Main.loadYaml("plugins/EternalRealms-Skills/players/" + player.getUniqueId().toString() + ".yml");
    	//	Levels
    	skillData.addDefault("lv-to", 1);//total
    	skillData.addDefault("lv-co", 1);//combat
    	//	Skills
    	skillData.addDefault("lv-mi", 1);//mining
    	skillData.addDefault("lv-di", 1);//digging
    	skillData.addDefault("lv-wc", 1);//woodcutting
    	skillData.addDefault("lv-he", 1);//herbalism
    	skillData.addDefault("lv-fa", 1);//farming
    	skillData.addDefault("lv-hu", 1);//husbandry
    	skillData.addDefault("lv-fi", 1);//fishing
    	skillData.addDefault("lv-bu", 1);//building
    	skillData.addDefault("lv-vi", 1);//vitality
    	skillData.addDefault("lv-ua", 1);//unarmored
    	skillData.addDefault("lv-ac", 1);//acrobatics
    	skillData.addDefault("lv-ca", 1);//cartography
    	skillData.addDefault("lv-al", 1);//alchemy
    	skillData.addDefault("lv-ck", 1);//cooking
    	skillData.addDefault("lv-ar", 1);//archery
    	skillData.addDefault("lv-lw", 1);//light weapons
    	skillData.addDefault("lv-hw", 1);//heavy weapons
    	skillData.addDefault("lv-ba", 1);//bard
    	skillData.addDefault("lv-dw", 1);//dual wield
    	skillData.addDefault("lv-oh", 1);//one handed
    	skillData.addDefault("lv-sh", 1);//shields
    	skillData.addDefault("lv-en", 1);//enchanting
    	skillData.addDefault("lv-ha", 1);//heavy armor
    	skillData.addDefault("lv-ma", 1);//medium armor
    	skillData.addDefault("lv-la", 1);//light armor
    	skillData.addDefault("lv-sm", 1);//smithing
    	skillData.addDefault("lv-in", 1);//invention
    	skillData.addDefault("lv-st", 1);//stealth
    	skillData.addDefault("lv-ta", 1);//tailoring
    	skillData.addDefault("lv-um", 1);//unarmed
    	skillData.addDefault("lv-pi", 1);//piety
    	skillData.addDefault("lv-wi", 1);//witchcraft
    	skillData.addDefault("lv-wz", 1);//wizardry
    	skillData.addDefault("lv-ww", 1);//woodworking
    	
    	Main.Instance().combatLevel.put(player, 		skillData.getInt("lv-co"));
    	Main.Instance().totalLevel.put(player, 			skillData.getInt("lv-to"));
    	Main.Instance().totalXP.put(player, 			skillData.getInt("xp-to"));

		Main.Instance().miningLevel.put(player, 		skillData.getInt("lv-mi"));
		Main.Instance().miningXP.put(player, 			skillData.getInt("xp-mi"));
		Main.Instance().diggingLevel.put(player, 		skillData.getInt("lv-di"));
		Main.Instance().diggingXP.put(player,			skillData.getInt("xp-di"));
		Main.Instance().woodcuttingLevel.put(player, 	skillData.getInt("lv-wc"));
		Main.Instance().woodcuttingXP.put(player, 		skillData.getInt("xp-wc"));
		Main.Instance().herbalismLevel.put(player, 		skillData.getInt("lv-he"));
		Main.Instance().herbalismXP.put(player, 		skillData.getInt("xp-he"));
		Main.Instance().farmingLevel.put(player, 		skillData.getInt("lv-fa"));
		Main.Instance().farmingXP.put(player, 			skillData.getInt("xp-fa"));
		Main.Instance().husbandryLevel.put(player, 		skillData.getInt("lv-hu"));
		Main.Instance().husbandryXP.put(player, 		skillData.getInt("xp-hu"));
		Main.Instance().fishingLevel.put(player, 		skillData.getInt("lv-fi"));
		Main.Instance().fishingXP.put(player, 			skillData.getInt("xp-fi"));
		Main.Instance().buildingLevel.put(player, 		skillData.getInt("lv-bu"));
		Main.Instance().buildingXP.put(player, 			skillData.getInt("xp-bu"));
		Main.Instance().vitalityLevel.put(player, 		skillData.getInt("lv-vi"));
		Main.Instance().vitalityXP.put(player, 			skillData.getInt("xp-vi"));
		Main.Instance().unarmoredLevel.put(player, 		skillData.getInt("lv-ua"));
		Main.Instance().unarmoredXP.put(player, 		skillData.getInt("xp-ua"));
		Main.Instance().acrobaticsLevel.put(player, 	skillData.getInt("lv-ac"));
		Main.Instance().acrobaticsXP.put(player, 		skillData.getInt("xp-ac"));
		Main.Instance().cartographyLevel.put(player, 	skillData.getInt("lv-ca"));
		Main.Instance().cartographyXP.put(player, 		skillData.getInt("xp-ca"));
		Main.Instance().alchemyLevel.put(player, 		skillData.getInt("lv-al"));
		Main.Instance().alchemyXP.put(player, 			skillData.getInt("xp-al"));
		Main.Instance().cookingLevel.put(player, 		skillData.getInt("lv-ck"));
		Main.Instance().cookingXP.put(player, 			skillData.getInt("xp-ck"));
		Main.Instance().archeryLevel.put(player, 		skillData.getInt("lv-ar"));
		Main.Instance().archeryXP.put(player, 			skillData.getInt("xp-ar"));
		Main.Instance().lightWeaponsLevel.put(player, 	skillData.getInt("lv-lw"));
		Main.Instance().lightWeaponsXP.put(player, 		skillData.getInt("xp-lw"));
		Main.Instance().heavyWeaponsLevel.put(player, 	skillData.getInt("lv-hw"));
		Main.Instance().heavyWeaponsXP.put(player, 		skillData.getInt("xp-hw"));
		Main.Instance().bardLevel.put(player, 			skillData.getInt("lv-ba"));
		Main.Instance().bardXP.put(player, 				skillData.getInt("xp-ba"));
		Main.Instance().dualWieldLevel.put(player, 		skillData.getInt("lv-dw"));
		Main.Instance().dualWieldXP.put(player, 		skillData.getInt("xp-dw"));
		Main.Instance().oneHandedLevel.put(player, 		skillData.getInt("lv-oh"));
		Main.Instance().oneHandedXP.put(player, 		skillData.getInt("xp-oh"));
		Main.Instance().shieldsLevel.put(player, 		skillData.getInt("lv-sh"));
		Main.Instance().shieldsXP.put(player, 			skillData.getInt("xp-sh"));
		Main.Instance().enchantingLevel.put(player, 	skillData.getInt("lv-en"));
		Main.Instance().enchantingXP.put(player, 		skillData.getInt("xp-en"));
		Main.Instance().heavyArmorLevel.put(player, 	skillData.getInt("lv-ha"));
		Main.Instance().heavyArmorXP.put(player, 		skillData.getInt("xp-ha"));
		Main.Instance().mediumArmorLevel.put(player, 	skillData.getInt("lv-ma"));
		Main.Instance().mediumArmorXP.put(player, 		skillData.getInt("xp-ma"));
		Main.Instance().lightArmorLevel.put(player, 	skillData.getInt("lv-la"));
		Main.Instance().lightArmorXP.put(player, 		skillData.getInt("xp-la"));
		Main.Instance().smithingLevel.put(player, 		skillData.getInt("lv-sm"));
		Main.Instance().smithingXP.put(player, 			skillData.getInt("xp-sm"));
		Main.Instance().inventionLevel.put(player, 		skillData.getInt("lv-in"));
		Main.Instance().inventionXP.put(player, 		skillData.getInt("xp-in"));
		Main.Instance().stealthLevel.put(player, 		skillData.getInt("lv-st"));
		Main.Instance().stealthXP.put(player, 			skillData.getInt("xp-st"));
		Main.Instance().tailoringLevel.put(player, 		skillData.getInt("lv-ta"));
		Main.Instance().tailoringXP.put(player, 		skillData.getInt("xp-ta"));
		Main.Instance().unarmedLevel.put(player, 		skillData.getInt("lv-um"));
		Main.Instance().unarmedXP.put(player, 			skillData.getInt("xp-um"));
		Main.Instance().pietyLevel.put(player, 			skillData.getInt("lv-pi"));
		Main.Instance().pietyXP.put(player, 			skillData.getInt("xp-pi"));
		Main.Instance().witchcraftLevel.put(player, 	skillData.getInt("lv-wi"));
		Main.Instance().witchcraftXP.put(player, 		skillData.getInt("xp-wi"));
		Main.Instance().wizardryLevel.put(player, 		skillData.getInt("lv-wz"));
		Main.Instance().wizardryXP.put(player, 			skillData.getInt("xp-wz"));
		Main.Instance().woodworkingLevel.put(player, 	skillData.getInt("lv-ww"));
		Main.Instance().woodworkingXP.put(player, 		skillData.getInt("xp-ww"));
    }
    
    public static void savePlayerData(Player player)
    {
    	YamlConfiguration skillData = new YamlConfiguration();
		
		skillData.set("lv-co", Main.Instance().combatLevel.get(player));
		skillData.set("lv-to", Main.Instance().totalLevel.get(player));
		skillData.set("xp-to", Main.Instance().totalXP.get(player));
    	
		skillData.set("lv-mi", Main.Instance().miningLevel.get(player));
		skillData.set("xp-mi", Main.Instance().miningXP.get(player));
		skillData.set("lv-di", Main.Instance().diggingLevel.get(player));
		skillData.set("xp-di", Main.Instance().diggingXP.get(player));
		skillData.set("lv-wc", Main.Instance().woodcuttingLevel.get(player));
		skillData.set("xp-wc", Main.Instance().woodcuttingXP.get(player));
		skillData.set("lv-he", Main.Instance().herbalismLevel.get(player));
		skillData.set("xp-he", Main.Instance().herbalismXP.get(player));
		skillData.set("lv-fa", Main.Instance().farmingLevel.get(player));
		skillData.set("xp-fa", Main.Instance().farmingXP.get(player));
		skillData.set("lv-hu", Main.Instance().husbandryLevel.get(player));
		skillData.set("xp-hu", Main.Instance().husbandryXP.get(player));
		skillData.set("lv-fi", Main.Instance().fishingLevel.get(player));
		skillData.set("xp-fi", Main.Instance().fishingXP.get(player));
		
		skillData.set("lv-bu", Main.Instance().buildingLevel.get(player));
		skillData.set("xp-bu", Main.Instance().buildingXP.get(player));
		
		skillData.set("lv-vi", Main.Instance().vitalityLevel.get(player));
		skillData.set("xp-vi", Main.Instance().vitalityXP.get(player));
		skillData.set("lv-ua", Main.Instance().unarmoredLevel.get(player));
		skillData.set("xp-ua", Main.Instance().unarmoredXP.get(player));
		skillData.set("lv-ac", Main.Instance().acrobaticsLevel.get(player));
		skillData.set("xp-ac", Main.Instance().acrobaticsXP.get(player));
		skillData.set("lv-ca", Main.Instance().cartographyLevel.get(player));
		skillData.set("xp-ca", Main.Instance().cartographyXP.get(player));
		skillData.set("lv-al", Main.Instance().alchemyLevel.get(player));
		skillData.set("xp-al", Main.Instance().alchemyXP.get(player));
		skillData.set("lv-ck", Main.Instance().cookingLevel.get(player));
		skillData.set("xp-ck", Main.Instance().cookingXP.get(player));
		skillData.set("lv-ar", Main.Instance().archeryLevel.get(player));
		skillData.set("xp-ar", Main.Instance().archeryXP.get(player));
		skillData.set("lv-lw", Main.Instance().lightWeaponsLevel.get(player));
		skillData.set("xp-lw", Main.Instance().lightWeaponsXP.get(player));
		skillData.set("lv-hw", Main.Instance().heavyWeaponsLevel.get(player));
		skillData.set("xp-hw", Main.Instance().heavyWeaponsXP.get(player));
		skillData.set("lv-ba", Main.Instance().bardLevel.get(player));
		skillData.set("xp-ba", Main.Instance().bardXP.get(player));
		skillData.set("lv-dw", Main.Instance().dualWieldLevel.get(player));
		skillData.set("xp-dw", Main.Instance().dualWieldXP.get(player));
		skillData.set("lv-oh", Main.Instance().oneHandedLevel.get(player));
		skillData.set("xp-oh", Main.Instance().oneHandedXP.get(player));
		skillData.set("lv-sh", Main.Instance().shieldsLevel.get(player));
		skillData.set("xp-sh", Main.Instance().shieldsXP.get(player));
		skillData.set("lv-en", Main.Instance().enchantingLevel.get(player));
		skillData.set("xp-en", Main.Instance().enchantingXP.get(player));
		skillData.set("lv-ha", Main.Instance().heavyArmorLevel.get(player));
		skillData.set("xp-ha", Main.Instance().heavyArmorXP.get(player));
		skillData.set("lv-ma", Main.Instance().mediumArmorLevel.get(player));
		skillData.set("xp-ma", Main.Instance().mediumArmorXP.get(player));
		skillData.set("lv-la", Main.Instance().lightArmorLevel.get(player));
		skillData.set("xp-la", Main.Instance().lightArmorXP.get(player));
		skillData.set("lv-sm", Main.Instance().smithingLevel.get(player));
		skillData.set("xp-sm", Main.Instance().smithingXP.get(player));
		skillData.set("lv-in", Main.Instance().inventionLevel.get(player));
		skillData.set("xp-in", Main.Instance().inventionXP.get(player));
		skillData.set("lv-st", Main.Instance().stealthLevel.get(player));
		skillData.set("xp-st", Main.Instance().stealthXP.get(player));
		skillData.set("lv-ta", Main.Instance().tailoringLevel.get(player));
		skillData.set("xp-ta", Main.Instance().tailoringXP.get(player));
		skillData.set("lv-um", Main.Instance().unarmedLevel.get(player));
		skillData.set("xp-um", Main.Instance().unarmedXP.get(player));
		skillData.set("lv-pi", Main.Instance().pietyLevel.get(player));
		skillData.set("xp-pi", Main.Instance().pietyXP.get(player));
		skillData.set("lv-wi", Main.Instance().witchcraftLevel.get(player));
		skillData.set("xp-wi", Main.Instance().witchcraftXP.get(player));
		skillData.set("lv-wz", Main.Instance().wizardryLevel.get(player));
		skillData.set("xp-wz", Main.Instance().wizardryXP.get(player));
		skillData.set("lv-ww", Main.Instance().woodworkingLevel.get(player));
		skillData.set("xp-ww", Main.Instance().woodworkingXP.get(player));
		
		Main.saveYaml(skillData, "plugins/EternalRealms-Skills/players/" + player.getUniqueId().toString() + ".yml");
    }
    
    public static void deletePlayerData(Player player)
    {
    	deleteYaml("plugins/EternalRealms-Skills/players/" + player.getUniqueId().toString() + ".yml");
    }
    
    public static void saveAllPlayerData()
    {		
		for( Player player : Bukkit.getServer().getOnlinePlayers() )
        {
			savePlayerData(player);
        }
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Saved skill data.");
    }
    
    public static YamlConfiguration loadSourcesYaml()
    {  
        YamlConfiguration yml = new YamlConfiguration();
        yml.addDefault("mining", Arrays.asList(
        		"STONE:2",
        		"GRANITE:6",
        		"DIORITE:6",
        		"ANDESITE:6",
        		"SANDSTONE:2",
        		"RED_SANDSTONE:2",
        		"OBSIDIAN:50",
        		"NETHERRACK:1",
        		"GLOWSTONE:10",
        		"END_STONE:2",
        		"MAGMA_BLOCK:10",
        		"TUBE_CORAL_BLOCK:10",
        		"BRAIN_CORAL_BLOCK:10",
        		"BUBBLE_CORAL_BLOCK:10",
        		"FIRE_CORAL_BLOCK:10",
        		"HORN_CORAL_BLOCK:10",
        		"COAL_ORE:10",
        		"DIAMOND_ORE:60",
        		"EMERALD_ORE:100",
        		"GOLD_ORE:36",
        		"IRON_ORE:20",
        		"LAPIS_ORE:30",
        		"NETHER_QUARTZ_ORE:24",
        		"REDSTONE_ORE:20",
        		"BLACK_TERRACOTTA:6",
        		"BLUE_TERRACOTTA:6",
        		"BROWN_TERRACOTTA:6",
        		"CYAN_TERRACOTTA:6",
        		"GREY_TERRACOTTA:6",
        		"GREEN_TERRACOTTA:6",
        		"LIME_TERRACOTTA:6",
        		"MAGENTA_TERRACOTTA:6",
        		"ORANGE_TERRACOTTA:6",
        		"PINK_TERRACOTTA:6",
        		"PURPLE_TERRACOTTA:6",
        		"RED_TERRACOTTA:6",
        		"WHITE_TERRACOTTA:6",
        		"YELLOW_TERRACOTTA:6",
        		"TERRACOTTA:6",
        		"ANCIENT_DEBRIES:100",
        		"BASALT:4",
        		"BLACKSTONE:6",
        		"GILDED_BLACKSTONE:36",
        		"NETHER_GOLD_ORE:24",
        		"DEEPSLATE:4",
        		"TUFF:4",
        		"DRIPSTONE:6",
        		"SMOOTH_BASALT:10",
        		"AMETHYST_BLOCK:10",
        		"AMETHYST_BUD:10",
        		"AMETHYST_CLUSTER:6",
        		"DEEPSLATE_COAL_ORE:10",
        		"DEEPSLATE_DIAMOND_ORE:60",
        		"DEEPSLATE_EMERALD_ORE:100",
        		"DEEPSLATE_GOLD_ORE:36",
        		"DEEPSLATE_IRON_ORE:20",
        		"DEEPSLATE_LAPIS_ORE:30",
        		"DEEPSLATE_REDSTONE_ORE:20",
        		"COPPER_ORE:20",
        		"DEEPSLATE_COPPER_ORE:20"));
        
        yml.addDefault("digging", Arrays.asList(
        		"DIRT:3",
        		"GRAVEL:6",
        		"SAND:1",
        		"SNOW_BLOCK:1",
        		"POWDER_SNOW:1",
        		"SNOW:1",
        		"GRASS_BLOCK:2",
        		"COARSE_DIRT:4",
        		"PODZOL:4",
        		"CLAY:10",
        		"RED_SAND:4",
        		"SOUL_SAND:4",
        		"MYCELIUM:6",
        		"DIGPATH:4",
        		"CRIMSON_NYLIUM:4",
        		"WARPED_NYLIUM:4",
        		"SOUL_SOIL:3",
        		"ROOTED_DIRT:6"));
        
        yml.addDefault("woodcutting", Arrays.asList(
        		"OAK_LOG:4",
        		"SPRUCE_LOG:6",
        		"BIRCH_LOG:6",
        		"ACACIA_LOG:6",
        		"JUNGLE_LOG:6",
        		"DARK_OAK_LOG:8",
        		"OAK_WOOD:4",
        		"SPRUCE_WOOD:6",
        		"BIRCH_WOOD:6",
        		"ACACIA_WOOD:6",
        		"JUNGLE_WOOD:6",
        		"DARK_OAK_WOOD:8",
        		"STRIP:2",
        		"CRIMSON_STEM:8",
        		"WARPED_STEM:8"));
        
        yml.addDefault("herbalism", Arrays.asList(
        		"BEETROOT_SOUP:6",
        		"MUSHROOM_STEW:4",
        		"RABBIT_STEW:8",
        		"SUSPICIOUS_STEW:8",
        		"BEETROOTS:4",
        		"CARROTS:4",
        		"COCOA:4",
        		"BAMBOO:2",
        		"RED_MUSHROOM:4",
        		"BROWN_MUSHROOM:4",
        		"MUSHROOM_STEM:1",
        		"RED_MUSHROOM_BLOCK:2",
        		"BROWN_MUSHROOM_BLOCK:2",
        		"NETHER_WART:4",
        		"CACTUS:8",
        		"KELP_PLANT:4",
        		"VINE:8",
        		"LILY_PAD:2",
        		"CHORUS_FLOWER:4",
        		"CORNFLOWER:4",
        		"LILY_OF_THE_VALLEY:4",
        		"WITHER_ROSE:4",
        		"SWEET_BERRIES:2",
        		"SWEET_BERRY_BUSH:4",
        		"DANDELION:4",
        		"POPPY:4",
        		"BLUE_ORCHID:4",
        		"ALLIUM:4",
        		"AZURE_BLUET:4",
        		"RED_TULIP:4",
        		"ORANGE_TULIP:4",
        		"WHITE_TULIP:4",
        		"PINK_TULIP:4",
        		"OXEYE_DAISY:4",
        		"WHITE_DYE:2",
        		"ORANGE_DYE:2",
        		"MAGENTA_DYE:2",
        		"LIGHT_BLUE_DYE:2",
        		"YELLOW_DYE:2",
        		"LIME_DYE:2",
        		"PINK_DYE:2",
        		"GRAY_DYE:2",
        		"LIGHT_GRAY_DYE:2",
        		"CYAN_DYE:2",
        		"PURPLE_DYE:2",
        		"BLUE_DYE:2",
        		"BROWN_DYE:2",
        		"GREEN_DYE:2",
        		"RED_DYE:2",
        		"BLACK_DYE:2",
        		"SUNFLOWER:6",
        		"LILAC:6",
        		"ROSE_BUSH:6",
        		"PEONY:6",
        		"GRASS:1",
        		"TALL_GRASS:2",
        		"OAK_LEAVES:4",
        		"SPRUCE_LEAVES:4",
        		"ACACIA_LEAVES:4",
        		"BIRCH_LEAVES:4",
        		"JUNGLE_LEAVES:4",
        		"DARK_OAK_LEAVES:4",
        		"SEA_PICKLE:6",
        		"TUBE_CORAL:6",
        		"TUBE_CORAL_FAN:6",
        		"TUBE_CORAL_WALL_FAN:6",
        		"BRAIN_CORAL:6",
        		"BRAIN_CORAL_FAN:6",
        		"BRAIN_CORAL_WALL_FAN:6",
        		"BUBBLE_CORAL:6",
        		"BUBBLE_CORAL_FAN:6",
        		"BUBBLE_CORAL_WALL_FAN:6",
        		"FIRE_CORAL:6",
        		"FIRE_CORAL_FAN:6",
        		"FIRE_CORAL_WALL_FAN:6",
        		"HORN_CORAL:6",
        		"HORN_CORAL_FAN:6",
        		"HORN_CORAL_WALL_FAN:6",
        		"HARVEST_TOOL:1",
        		"SEED_GRASS:4",
        		"CRIMSON_FUNGI:8",
        		"WARPPED_FUNGI:8",
        		"NETHER_SPROUTS:3",
        		"SHROOMLIGHT:8",
        		"TWISTING_VINES_PLANT:8",
        		"WEEPING_VINES_PLAN:8",
        		"NETHER_WART_BLOCK:4",
        		"MOSS_BLOCK:4",
        		"MOSS_CARPET:4",
        		"SPORE_BLOSSOM:20",
        		"SMALL_DRIPLEAF:4",
        		"BIG_DRIPLEAF:4",
        		"BIG_DRIPLEAF_STEM:4",
        		"AZALEA:4",
        		"FLOWERING_AZALEA:4",
        		"AZALEA_LEAVES:4",
        		"FLOWERING_AZALEA_LEAVES:4",
        		"CAVE_VINES:4",
        		"CAVE_VINES_PLANT:4"));
        
        yml.addDefault("farming", Arrays.asList(
        		"WHEAT:4",
        		"POTATOES:4",
        		"SUGAR_CANE:2",
        		"MELON:6",
        		"PUMPKIN:6",
        		"TILLING:2",
        		"HARVEST_TOOL:1"));
        
        yml.addDefault("fishing", Arrays.asList(
        		"FAILED_ATTEMPT:4",
        		"CAUGHT_FISH:12"));
        
        yml.addDefault("husbandry", Arrays.asList(
        		"HORSE:30",
        		"DONKEY:30",
        		"LLAMA:20",
        		"SHEEP:12",
        		"COW:12",
        		"PIG:14",
        		"CHICKEN:8",
        		"RABBIT:14",
        		"TURTLE:20",
        		"WOLF:14",
        		"OCELOT:14",
        		"CAT:14",
        		"FOX:14",
        		"PANDA:14",
        		"COMBAT:1",
        		"TRAVELING:1",
        		"SHEARING:5",
        		"MILKING:4",
        		"PLUCKING:3",
        		"BEE:30",
        		"HOGLIN:14",
        		"AXOLOTL:20",
        		"GOAT:12"));
        
        yml.addDefault("cooking", Arrays.asList(
        		"COOKED_BEEF:2",
        		"COOKED_CHICKEN:2",
        		"COOKED_MUTTON:2",
        		"COOKED_PORKCHOP:2",
        		"COOKED_RABBIT:2",
        		"COOKED_COD:4",
        		"COOKED_SALMON:4",
        		"BAKED_POTATO:1",
        		"DRIED_KELP:1",
        		"BREAD:6",
        		"CAKE:12",
        		"COOKIE:4",
        		"GOLDEN_APPLE:20",
        		"GOLDEN_CARROT:20",
        		"PUMPKIN_PIE:8"));
        
        yml.addDefault("smithing", Arrays.asList(
        		"IRON_INGOT:2",
        		"GOLD_INGOT:4",
        		"GLASS:2",
        		"STONE:2",
        		"BRICK:4",
        		"NETHER_BRICK_ITEM:2",
        		"TERRACOTTA:2",
        		"CRACKED_STONE_BRICKS:2",
        		"BLACK_GLAZED_TERRACOTTA:2",
        		"BLUE_GLAZED_TERRACOTTA:2",
        		"BROWN_GLAZED_TERRACOTTA:2",
        		"CYAN_GLAZED_TERRACOTTA:2",
        		"GREY_GLAZED_TERRACOTTA:2",
        		"GREEN_GLAZED_TERRACOTTA:2",
        		"LIME_GLAZED_TERRACOTTA:2",
        		"MAGENTA_GLAZED_TERRACOTTA:2",
        		"ORANGE_GLAZED_TERRACOTTA:2",
        		"PINK_GLAZED_TERRACOTTA:2",
        		"PURPLE_GLAZED_TERRACOTTA:2",
        		"RED_GLAZED_TERRACOTTA:2",
        		"WHITE_GLAZED_TERRACOTTA:2",
        		"YELLOW_GLAZED_TERRACOTTA:2",
        		"ANVIL:120",
        		"BUCKET:12",
        		"SHEARS:8",
        		"CAULDRON:24",
        		"HEAVY_WEIGHTED_PRESSURE_PLATE:8",
        		"LIGHT_WEIGHTED_PRESSURE_PLATE:12",
        		"IRON_TRAPDOOR:12",
        		"IRON_BARS:2",
        		"IRON_DOOR:12",
        		"MINECART:16",
        		"IRON_HORSE_ARMOR:40",
        		"IRON_HELMET:24",
        		"IRON_CHESTPLATE:40",
        		"IRON_LEGGINGS:32",
        		"IRON_BOOTS:16",
        		"IRON_AXE:12",
        		"IRON_PICKAXE:8",
        		"IRON_SWORD:6",
        		"IRON_SHOVEL:4",
        		"IRON_HOE:8",
        		"GOLDEN_HORSE_ARMOR:80",
        		"GOLDEN_HELMET:48",
        		"GOLDEN_CHESTPLATE:80",
        		"GOLDEN_LEGGINGS:64",
        		"GOLDEN_BOOTS:36",
        		"GOLDEN_AXE:24",
        		"GOLDEN_PICKAXE:16",
        		"GOLDEN_SWORD:12",
        		"GOLDEN_SHOVEL:8",
        		"GOLDEN_HOE:16",
        		"CHAINMAIL_HELMET:24",
        		"CHAINMAIL_CHESTPLATE:40",
        		"CHAINMAIL_LEGGINGS:32",
        		"CHAINMAIL_BOOTS:16",
        		"DIAMOND_HORSE_ARMOR:80",
        		"DIAMOND_HELMET:48",
        		"DIAMOND_CHESTPLATE:80",
        		"DIAMOND_LEGGINGS:64",
        		"DIAMOND_BOOTS:36",
        		"DIAMOND_AXE:24",
        		"DIAMOND_PICKAXE:16",
        		"DIAMOND_SWORD:12",
        		"DIAMOND_SHOVEL:8",
        		"DIAMOND_HOE:16",
        		"LANTERN:4",
        		"CHAIN:4",
        		"SOUL_LANTERN:4",
        		"NETHERITE_HELMET:160",
        		"NETHERITE_CHESTPLATE:160",
        		"NETHERITE_LEGGINGS:160",
        		"NETHERITE_BOOTS:160",
        		"NETHERITE_AXE:160",
        		"NETHERITE_PICKAXE:160",
        		"NETHERITE_SWORD:160",
        		"NETHERITE_SHOVEL:160",
        		"NETHERITE_HOE:160",
        		"NETHERITE_INGOT:32",
        		"NETHERITE_SCRAP:8"));
        
        yml.addDefault("invention", Arrays.asList(
        		"NOTEBLOCK:36",
        		"JUKEBOX:92",
        		"DAYLIGHT_DETECTOR:24",
        		"LEVER:4",
        		"TNT:30",
        		"CLOCK:28",
        		"COMPASS:18",
        		"REDSTONE_TORCH:6",
        		"RAIL:2",
        		"ACTIVATOR_RAIL:6",
        		"DETECTOR_RAIL:4",
        		"DISPENSER:20",
        		"DROPPER:16",
        		"HOPPER:20",
        		"OBSERVER:20",
        		"PISTON:28",
        		"STICKY_PISTON:15",
        		"REDSTONE_LAMP:16",
        		"REDSTONE_REPEATER:20",
        		"REDSTONE_COMPARATOR:20",
        		"TRIPWIRE_HOOK:4"));
        
        yml.addDefault("woodworking", Arrays.asList(
        		"BARREL:8",
        		"CROSSBOW:12",
        		"CAMPFIRE:6",
        		"CARTOGRAPHY_TABLE:8",
        		"FLETCHING_TABLE:8",
        		"LECTERN:8",
        		"LOOM:8",
        		"STICK:1",
        		"ARROW:2",
        		"FISHING_ROD:10",
        		"LADDER:2",
        		"ARMOR_STAND:12",
        		"BOWL:2",
        		"CHEST:8",
        		"CRAFTING_TABLE:6",
        		"OAK_SIGN:6",
        		"SPRUCE_SIGN:6",
        		"BIRCH_SIGN:6",
        		"JUNGLE_SIGN:6",
        		"ACACIA_SIGN:6",
        		"DARK_OAK_SIGN:6",
        		"BOOKSHELF:8",
        		"WHITE_BED:8",
        		"ORANGE_BED:8",
        		"MAGENTA_BED:8",
        		"LIGHT_BLUE_BED:8",
        		"YELLOW_BED:8",
        		"LIME_BED:8",
        		"PINK_BED:8",
        		"GRAY_BED:8",
        		"LIGHT_GRAY_BED:8",
        		"CYAN_BED:8",
        		"PURPLE_BED:8",
        		"BLUE_BED:8",
        		"BROWN_BED:8",
        		"GREEN_BED:8",
        		"RED_BED:8",
        		"BLACK_BED:8",
        		"SHIELD",
        		"STONE_AXE:12",
        		"STONE_SWORD:8",
        		"STONE_PICKAXE:12",
        		"STONE_SHOVEL:4",
        		"STONE_HOE:8",
        		"WOODEN_AXE:12",
        		"WOODEN_SWORD:8",
        		"WOODEN_PICKAXE:12",
        		"WOODEN_SHOVEL:4",
        		"WOODEN_HOE:8",
        		"OAK_BOAT:8",
        		"SPRUCE_BOAT:8",
        		"BIRCH_BOAT:8",
        		"JUNGLE_BOAT:8",
        		"ACACIA_BOAT:8",
        		"DARK_OAK_BOAT:8",
        		"OAK_DOOR:12",
        		"SPRUCE_DOOR:12",
        		"BIRCH_DOOR:12",
        		"JUNGLE_DOOR:12",
        		"ACACIA_DOOR:12",
        		"DARK_OAK_DOOR:12",
        		"OAK_WOOD:4",
        		"SPRUCE_WOOD:4",
        		"BIRCH_WOOD:4",
        		"JUNGLE_WOOD:4",
        		"ACACIA_WOOD:4",
        		"DARK_OAK_WOOD:4",
        		"OAK_PLANKS:2",
        		"SPRUCE_PLANKS:2",
        		"BIRCH_PLANKS:2",
        		"JUNGLE_PLANKS:2",
        		"ACACIA_PLANKS:2",
        		"DARK_OAK_PLANKS:2",
        		"OAK_SLAB:2",
        		"SPRUCE_SLAB:2",
        		"BIRCH_SLAB:2",
        		"JUNGLE_SLAB:2",
        		"ACACIA_SLAB:2",
        		"DARK_OAK_SLAB:2",
        		"OAK_STAIRS:2",
        		"SPRUCE_STAIRS:2",
        		"BIRCH_STAIRS:2",
        		"JUNGLE_STAIRS:2",
        		"ACACIA_STAIRS:2",
        		"DARK_OAK_STAIRS:2",
        		"OAK_BUTTON:2",
        		"SPRUCE_BUTTON:2",
        		"BIRCH_BUTTON:2",
        		"JUNGLE_BUTTON:2",
        		"ACACIA_BUTTON:2",
        		"DARK_OAK_BUTTON:2",
        		"OAK_PRESSURE_PLATE:4",
        		"SPRUCE_PRESSURE_PLATE:4",
        		"BIRCH_PRESSURE_PLATE:4",
        		"JUNGLE_PRESSURE_PLATE:4",
        		"ACACIA_PRESSURE_PLATE:4",
        		"DARK_OAK_PRESSURE_PLATE:4",
        		"OAK_TRAPDOOR:4",
        		"SPRUCE_TRAPDOOR:4",
        		"BIRCH_TRAPDOOR:4",
        		"JUNGLE_TRAPDOOR:4",
        		"ACACIA_TRAPDOOR:4",
        		"DARK_OAK_TRAPDOOR:4",
        		"OAK_FENCE:4",
        		"SPRUCE_FENCE:4",
        		"BIRCH_FENCE:4",
        		"JUNGLE_FENCE:4",
        		"ACACIA_FENCE:4",
        		"DARK_OAK_FENCE:4",
        		"OAK_FENCE_GATE:6",
        		"SPRUCE_FENCE_GATE:6",
        		"BIRCH_FENCE_GATE:6",
        		"JUNGLE_FENCE_GATE:6",
        		"ACACIA_FENCE_GATE:6",
        		"DARK_OAK_FENCE_GATE:6",
        		"WARPED_DOOR:12",
        		"WARPED_HYPHAE:4",
        		"WARPED_PLANKS:2",
        		"WARPED_SLAB:2",
        		"WARPED_STAIRS:2",
        		"WARPED_BUTTON:2",
        		"WARPED_PRESSURE_PLATE:4",
        		"WARPED_TRAPDOOR:4",
        		"WARPED_FENCE:4",
        		"WARPED_FENCE_GATE:6",
        		"CRIMSON_DOOR:12",
        		"CRIMSON_HYPHAE:4",
        		"CRIMSON_PLANKS:2",
        		"CRIMSON_SLAB:2",
        		"CRIMSON_STAIRS:2",
        		"CRIMSON_BUTTON:2",
        		"CRIMSON_PRESSURE_PLATE:4",
        		"CRIMSON_TRAPDOOR:4",
        		"CRIMSON_FENCE:4",
        		"CRIMSON_FENCE_GATE:6"));
        
        yml.addDefault("tailoring", Arrays.asList(
        		"WHITE_WOOL:8",
        		"ORANGE_WOOL:8",
        		"MAGENTA_WOOL:8",
        		"LIGHT_BLUE_WOOL:8",
        		"YELLOW_WOOL:8",
        		"LIME_WOOL:8",
        		"PINK_WOOL:8",
        		"GRAY_WOOL:8",
        		"LIGHT_GRAY_WOOL:8",
        		"CYAN_WOOL:8",
        		"PURPLE_WOOL:8",
        		"BLUE_WOOL:8",
        		"BROWN_WOOL:8",
        		"GREEN_WOOL:8",
        		"RED_WOOL:8",
        		"BLACK_WOOL:8",
        		"WHITE_BANNER:12",
        		"ORANGE_BANNER:12",
        		"MAGENTA_BANNER:12",
        		"LIGHT_BLUE_BANNER:12",
        		"YELLOW_BANNER:12",
        		"LIME_BANNER:12",
        		"PINK_BANNER:12",
        		"GRAY_BANNER:12",
        		"LIGHT_GRAY_BANNER:12",
        		"CYAN_BANNER:12",
        		"PURPLE_BANNER:12",
        		"BLUE_BANNER:12",
        		"BROWN_BANNER:12",
        		"GREEN_BANNER:12",
        		"RED_BANNER:12",
        		"BLACK_BANNER:12",
        		"WHITE_CARPET:2",
        		"ORANGE_CARPET:2",
        		"MAGENTA_CARPET:2",
        		"LIGHT_BLUE_CARPET:2",
        		"YELLOW_CARPET:2",
        		"LIME_CARPET:2",
        		"PINK_CARPET:2",
        		"GRAY_CARPET:2",
        		"LIGHT_GRAY_CARPET:2",
        		"CYAN_CARPET:2",
        		"PURPLE_CARPET:2",
        		"BLUE_CARPET:2",
        		"BROWN_CARPET:2",
        		"GREEN_CARPET:2",
        		"RED_CARPET:2",
        		"BLACK_CARPET:2",
        		"STRING:4",
        		"LEAD:12",
        		"ITEM_FRAME:16",
        		"PAINTING:20",
        		"SADDLE:36",
        		"LEATHER:10",
        		"LEATHER_HORSE_ARMOR:40",
        		"LEATHER_HELMET:24",
        		"LEATHER_CHESTPLATE:40",
        		"LEATHER_LEGGINGS:32",
        		"LEATHER_BOOTS:16"));
        
        yml.addDefault("cartography", Arrays.asList(
        		"MAP:20",
        		"FILLED_MAP:40"));
        
        yml.addDefault("alchemy", Arrays.asList(
        		"BREWING:1",
        		"NETHER_WART:4",
        		"REDSTONE:6",
        		"GLOWSTONE_DUST:6",
        		"SUGAR:6",
        		"RABBIT_FOOT:12",
        		"BLAZE_POWDER:6",
        		"GLISTERING_MELON_SLICE:8",
        		"SPIDER_EYE:6",
        		"GHAST_TEAR:10",
        		"MAGMA_CREAM:8",
        		"PUFFERFISH:10",
        		"GOLDEN_CARROT:8",
        		"TURTLE_HELMET:10",
        		"PHANTOM_MEMBRANE:8",
        		"FERMENTED_SPIDER_EYE:10",
        		"DRAGON_BREATH:10"));
        
        yml.addDefault("archery", Arrays.asList(
        		"BOW:1",
        		"CROSSBOW:1",
        		"TRIDENT:1"));
        
        yml.addDefault("light_weapons", Arrays.asList(
        		"WOODEN_SWORD:1",
        		"STONE_SWORD:1",
        		"IRON_SWORD:1",
        		"GOLDEN_SWORD:1",
        		"DIAMOND_SWORD:1",
        		"NETHERITE_SWORD:1",
        		"STONE_HOE:1",
        		"IRON_HOE:1",
        		"DIAMOND_HOE:1",
        		"NETHERITE_HOE:1",
        		"WOODEN_SHOVEL:1",
        		"STONE_SHOVEL:1",
        		"IRON_SHOVEL:1",
        		"GOLDEN_SHOVEL:1",
        		"DIAMOND_SHOVEL:1",
        		"NETHERITE_SHOVEL:1",
        		"SHEARS:1",
        		"TORCH:1"));
        
        yml.addDefault("heavy_weapons", Arrays.asList(
        		"WOODEN_AXE:1",
        		"STONE_AXE:1",
        		"IRON_AXE:1",
        		"GOLDEN_AXE:1",
        		"DIAMOND_AXE:1",
        		"NETHERITE_AXE:1",
        		"WOODEN_PICKAXE:1",
        		"STONE_PICKAXE:1",
        		"IRON_PICKAXE:1",
        		"GOLDEN_PICKAXE:1",
        		"DIAMOND_PICKAXE:1",
        		"NETHERITE_PICKAXE:1",
        		"TRIDENT:1"));
        
        yml.addDefault("light_armor", Arrays.asList(
        		"LEATHER_HELMET:1",
        		"LEATHER_CHESTPLATE:1",
        		"LEATHER_LEGGINGS:1",
        		"LEATHER_BOOTS:1"));
        
        yml.addDefault("medium_armor", Arrays.asList(
        		"CHAINMAIL_HELMET:1",
        		"CHAINMAIL_CHESTPLATE:1",
        		"CHAINMAIL_LEGGINGS:1",
        		"CHAINMAIL_BOOTS:1"));
        
        yml.addDefault("heavy_armor", Arrays.asList(
        		"IRON_HELMET:1",
        		"IRON_CHESTPLATE:1",
        		"IRON_LEGGINGS:1",
        		"IRON_BOOTS:1",
        		"GOLDEN_HELMET:1",
        		"GOLDEN_CHESTPLATE:1",
        		"GOLDEN_LEGGINGS:1",
        		"GOLDEN_BOOTS:1",
        		"DIAMOND_HELMET:1",
        		"DIAMOND_CHESTPLATE:1",
        		"DIAMOND_LEGGINGS:1",
        		"DIAMOND_BOOTS:1",
        		"NETHERITE_HELMET:1",
        		"NETHERITE_CHESTPLATE:1",
        		"NETHERITE_LEGGINGS:1",
        		"NETHERITE_BOOTS:1"));
        
        yml.addDefault("piety", Arrays.asList(
        		"ZOMBIE:12",
        		"HUSK:12",
        		"ZOMBIE_VILLAGER:8",
        		"DROWNED:18",
        		"ZOMBIE_HORSE:18",
        		"SKELETON:12",
        		"SKELETON_HORSE:10",
        		"STRAY:16",
        		"ZOMBIE_PIGMAN:30",
        		"PHANTOM:30",
        		"WITHER_SKELETON:40",
        		"WITHER:1000",
        		"BURY_BONE:2",
        		"BURY_SKULL:12",
        		"BURY_HEAD:20",
        		"PURIFY:60",
        		"ZOMBIFIED_PIGLIN:10",
        		"ZOGLIN:20"));
        
        yml.addDefault("building", Arrays.asList(
        		"ACACIA_BUTTON:1",
        		"ACACIA_DOOR:1",
        		"ACACIA_FENCE:1",
        		"ACACIA_FENCE_GATE:1",
        		"ACACIA_LEAVES:1",
        		"ACACIA_LOG:1",
        		"ACACIA_PLANKS:1",
        		"ACACIA_PRESSURE_PLATE:1",
        		"ACACIA_SAPLING:1",
        		"ACACIA_SIGN:1",
        		"ACACIA_SLAB:1",
        		"ACACIA_STAIRS:1",
        		"ACACIA_TRAPDOOR:1",
        		"ACACIA_WALL_SIGN:1",
        		"ACACIA_WOOD:1",
        		"OAK_BUTTON:1",
        		"OAK_DOOR:1",
        		"OAK_FENCE:1",
        		"OAK_FENCE_GATE:1",
        		"OAK_LEAVES:1",
        		"OAK_LOG:1",
        		"OAK_PLANKS:1",
        		"OAK_PRESSURE_PLATE:1",
        		"OAK_SAPLING:1",
        		"OAK_SIGN:1",
        		"OAK_SLAB:1",
        		"OAK_STAIRS:1",
        		"OAK_TRAPDOOR:1",
        		"OAK_WALL_SIGN:1",
        		"OAK_WOOD:1",
        		"SPRUCE_BUTTON:1",
        		"SPRUCE_DOOR:1",
        		"SPRUCE_FENCE:1",
        		"SPRUCE_FENCE_GATE:1",
        		"SPRUCE_LEAVES:1",
        		"SPRUCE_LOG:1",
        		"SPRUCE_PLANKS:1",
        		"SPRUCE_PRESSURE_PLATE:1",
        		"SPRUCE_SAPLING:1",
        		"SPRUCE_SIGN:1",
        		"SPRUCE_SLAB:1",
        		"SPRUCE_STAIRS:1",
        		"SPRUCE_TRAPDOOR:1",
        		"SPRUCE_WALL_SIGN:1",
        		"SPRUCE_WOOD:1",
        		"BIRCH_BUTTON:1",
        		"BIRCH_DOOR:1",
        		"BIRCH_FENCE:1",
        		"BIRCH_FENCE_GATE:1",
        		"BIRCH_LEAVES:1",
        		"BIRCH_LOG:1",
        		"BIRCH_PLANKS:1",
        		"BIRCH_PRESSURE_PLATE:1",
        		"BIRCH_SAPLING:1",
        		"BIRCH_SIGN:1",
        		"BIRCH_SLAB:1",
        		"BIRCH_STAIRS:1",
        		"BIRCH_TRAPDOOR:1",
        		"BIRCH_WALL_SIGN:1",
        		"BIRCH_WOOD:1",
        		"JUNGLE_BUTTON:1",
        		"JUNGLE_DOOR:1",
        		"JUNGLE_FENCE:1",
        		"JUNGLE_FENCE_GATE:1",
        		"JUNGLE_LEAVES:1",
        		"JUNGLE_LOG:1",
        		"JUNGLE_PLANKS:1",
        		"JUNGLE_PRESSURE_PLATE:1",
        		"JUNGLE_SAPLING:1",
        		"JUNGLE_SIGN:1",
        		"JUNGLE_SLAB:1",
        		"JUNGLE_STAIRS:1",
        		"JUNGLE_TRAPDOOR:1",
        		"JUNGLE_WALL_SIGN:1",
        		"JUNGLE_WOOD:1",
        		"DARK_OAK_BUTTON:1",
        		"DARK_OAK_DOOR:1",
        		"DARK_OAK_FENCE:1",
        		"DARK_OAK_FENCE_GATE:1",
        		"DARK_OAK_LEAVES:1",
        		"DARK_OAK_LOG:1",
        		"DARK_OAK_PLANKS:1",
        		"DARK_OAK_PRESSURE_PLATE:1",
        		"DARK_OAK_SAPLING:1",
        		"DARK_OAK_SIGN:1",
        		"DARK_OAK_SLAB:1",
        		"DARK_OAK_STAIRS:1",
        		"DARK_OAK_TRAPDOOR:1",
        		"DARK_OAK_WALL_SIGN:1",
        		"DARK_OAK_WOOD:1",
        		"ACTIVATOR_RAIL:1",
        		"ANDESITE:1",
        		"ANDESITE_SLAB:1",
        		"ANDESITE_STAIRS:1",
        		"ANDESITE_WALL:1",
        		"GRANITE:1",
        		"GRANITE_SLAB:1",
        		"GRANITE_STAIRS:1",
        		"GRANITE_WALL:1",
        		"DIORITE:1",
        		"DIORITE_SLAB:1",
        		"DIORITE_STAIRS:1",
        		"DIORITE_WALL:1",
        		"STONE:1",
        		"STONE_SLAB:1",
        		"STONE_STAIRS:1",
        		"STONE_WALL:1",
        		"ANVIL:1",
        		"BARREL:1",
        		"BEACON:1",
        		"BELL:1",
        		"BLACK_BANNER:1",
        		"BLACK_BED:1",
        		"BLACK_CONCRETE:1",
        		"BLACK_CONCRETE_POWDER:1",
        		"BLACK_GLAZED_TERRACOTTA:1",
        		"BLACK_STAINED_GLASS:1",
        		"BLACK_TERRACOTTA:1",
        		"BLACK_WOOL:1",
        		"RED_BANNER:1",
        		"RED_BED:1",
        		"RED_CONCRETE:1",
        		"RED_CONCRETE_POWDER:1",
        		"RED_GLAZED_TERRACOTTA:1",
        		"RED_STAINED_GLASS:1",
        		"RED_TERRACOTTA:1",
        		"RED_WOOL:1",
        		"GREEN_BANNER:1",
        		"GREEN_BED:1",
        		"GREEN_CONCRETE:1",
        		"GREEN_CONCRETE_POWDER:1",
        		"GREEN_GLAZED_TERRACOTTA:1",
        		"GREEN_STAINED_GLASS:1",
        		"GREEN_TERRACOTTA:1",
        		"GREEN_WOOL:1",
        		"BROWN_BANNER:1",
        		"BROWN_BED:1",
        		"BROWN_CONCRETE:1",
        		"BROWN_CONCRETE_POWDER:1",
        		"BROWN_GLAZED_TERRACOTTA:1",
        		"BROWN_STAINED_GLASS:1",
        		"BROWN_TERRACOTTA:1",
        		"BROWN_WOOL:1",
        		"BLUE_BANNER:1",
        		"BLUE_BED:1",
        		"BLUE_CONCRETE:1",
        		"BLUE_CONCRETE_POWDER:1",
        		"BLUE_GLAZED_TERRACOTTA:1",
        		"BLUE_STAINED_GLASS:1",
        		"BLUE_TERRACOTTA:1",
        		"BLUE_WOOL:1",
        		"PURPLE_BANNER:1",
        		"PURPLE_BED:1",
        		"PURPLE_CONCRETE:1",
        		"PURPLE_CONCRETE_POWDER:1",
        		"PURPLE_GLAZED_TERRACOTTA:1",
        		"PURPLE_STAINED_GLASS:1",
        		"PURPLE_TERRACOTTA:1",
        		"PURPLE_WOOL:1",
        		"CYAN_BANNER:1",
        		"CYAN_BED:1",
        		"CYAN_CONCRETE:1",
        		"CYAN_CONCRETE_POWDER:1",
        		"CYAN_GLAZED_TERRACOTTA:1",
        		"CYAN_STAINED_GLASS:1",
        		"CYAN_TERRACOTTA:1",
        		"CYAN_WOOL:1",
        		"LIGHT_GRAY_BANNER:1",
        		"LIGHT_GRAY_BED:1",
        		"LIGHT_GRAY_CONCRETE:1",
        		"LIGHT_GRAY_CONCRETE_POWDER:1",
        		"LIGHT_GRAY_GLAZED_TERRACOTTA:1",
        		"LIGHT_GRAY_STAINED_GLASS:1",
        		"LIGHT_GRAY_TERRACOTTA:1",
        		"LIGHT_GRAY_WOOL:1",
        		"GRAY_BANNER:1",
        		"GRAY_BED:1",
        		"GRAY_CONCRETE:1",
        		"GRAY_CONCRETE_POWDER:1",
        		"GRAY_GLAZED_TERRACOTTA:1",
        		"GRAY_STAINED_GLASS:1",
        		"GRAY_TERRACOTTA:1",
        		"GRAY_WOOL:1",
        		"PINK_BANNER:1",
        		"PINK_BED:1",
        		"PINK_CONCRETE:1",
        		"PINK_CONCRETE_POWDER:1",
        		"PINK_GLAZED_TERRACOTTA:1",
        		"PINK_STAINED_GLASS:1",
        		"PINK_TERRACOTTA:1",
        		"PINK_WOOL:1",
        		"LIME_BANNER:1",
        		"LIME_BED:1",
        		"LIME_CONCRETE:1",
        		"LIME_CONCRETE_POWDER:1",
        		"LIME_GLAZED_TERRACOTTA:1",
        		"LIME_STAINED_GLASS:1",
        		"LIME_TERRACOTTA:1",
        		"LIME_WOOL:1",
        		"YELLOW_BANNER:1",
        		"YELLOW_BED:1",
        		"YELLOW_CONCRETE:1",
        		"YELLOW_CONCRETE_POWDER:1",
        		"YELLOW_GLAZED_TERRACOTTA:1",
        		"YELLOW_STAINED_GLASS:1",
        		"YELLOW_TERRACOTTA:1",
        		"YELLOW_WOOL:1",
        		"LIGHT_BLUE_BANNER:1",
        		"LIGHT_BLUE_BED:1",
        		"LIGHT_BLUE_CONCRETE:1",
        		"LIGHT_BLUE_CONCRETE_POWDER:1",
        		"LIGHT_BLUE_GLAZED_TERRACOTTA:1",
        		"LIGHT_BLUE_STAINED_GLASS:1",
        		"LIGHT_BLUE_TERRACOTTA:1",
        		"LIGHT_BLUE_WOOL:1",
        		"MAGENTA_BANNER:1",
        		"MAGENTA_BED:1",
        		"MAGENTA_CONCRETE:1",
        		"MAGENTA_CONCRETE_POWDER:1",
        		"MAGENTA_GLAZED_TERRACOTTA:1",
        		"MAGENTA_STAINED_GLASS:1",
        		"MAGENTA_TERRACOTTA:1",
        		"MAGENTA_WOOL:1",
        		"ORANGE_BANNER:1",
        		"ORANGE_BED:1",
        		"ORANGE_CONCRETE:1",
        		"ORANGE_CONCRETE_POWDER:1",
        		"ORANGE_GLAZED_TERRACOTTA:1",
        		"ORANGE_STAINED_GLASS:1",
        		"ORANGE_TERRACOTTA:1",
        		"ORANGE_WOOL:1",
        		"WHITE_BANNER:1",
        		"WHITE_BED:1",
        		"WHITE_CONCRETE:1",
        		"WHITE_CONCRETE_POWDER:1",
        		"WHITE_GLAZED_TERRACOTTA:1",
        		"WHITE_STAINED_GLASS:1",
        		"WHITE_TERRACOTTA:1",
        		"WHITE_WOOL:1",
        		"BOOKSHELF:1",
        		"BRICK_SLAB:1",
        		"BRICK_STAIRS:1",
        		"BRICK_WALL:1",
        		"BRICKS:1",
        		"CHISELED_QUARTZ_BLOCK:1",
        		"CHISELED_RED_SANDSTONE:1",
        		"CHISELED_SANDSTONE:1",
        		"CHISELED_STONE_BRICKS:1",
        		"COARSE_DIRT:1",
        		"COBBLESTONE:1",
        		"COBBLESTONE_SLAB:1",
        		"COBBLESTONE_STAIRS:1",
        		"COBBLESTONE_WALL:1",
        		"CRACKED_STONE_BRICKS:1",
        		"CUT_RED_SANDSTONE:1",
        		"CUT_RED_SANDSTONE_SLAB:1",
        		"CUT_SANDSTONE:1",
        		"CUT_SANDSTONE_SLAB:1",
        		"DAYLIGHT_DETECTOR:1",
        		"DIRT:1",
        		"DROPPER:1",
        		"END_STONE:1",
        		"END_STONE_BRICK_SLAB:1",
        		"END_STONE_BRICK_STAIRS:1",
        		"END_STONE_BRICK_WALL:1",
        		"END_STONE_BRICKS:1",
        		"GLASS:1",
        		"GLASS_PANE:1",
        		"GLOWSTONE:1",
        		"GRASS_BLOCK:1",
        		"GRAVEL:1",
        		"IRON_BARS:1",
        		"IRON_DOOR:1",
        		"IRON_TRAPDOOR:1",
        		"JACK_O_LANTERN:1",
        		"LANTERN:1",
        		"LADDER:1",
        		"MOSSY_COBBLESTONE:1",
        		"MOSSY_COBBLESTONE_SLAB:1",
        		"MOSSY_COBBLESTONE_STAIRS:1",
        		"MOSSY_COBBLESTONE_WALL:1",
        		"MOSSY_STONE_BRICK_SLAB:1",
        		"MOSSY_STONE_BRICK_STAIRS:1",
        		"MOSSY_STONE_BRICK_WALL:1",
        		"MOSSY_STONE_BRICKS:1",
        		"NETHER_BRICK_FENCE:1",
        		"NETHER_BRICK_STAIRS:1",
        		"NETHER_BRICK_WALL:1",
        		"NETHER_BRICKS:1",
        		"NETHERRACK:1",
        		"OBSERVER:1",
        		"OBSIDIAN:1",
        		"PACKED_ICE:1",
        		"ICE:1",
        		"PISTON:1",
        		"POLISHED_ANDESITE:1",
        		"POLISHED_ANDESITE_SLAB:1",
        		"POLISHED_ANDESITE_STAIRS:1",
        		"POLISHED_DIORITE:1",
        		"POLISHED_DIORITE_SLAB:1",
        		"POLISHED_DIORITE_STAIRS:1",
        		"POLISHED_GRANITE:1",
        		"POLISHED_GRANITE_SLAB:1",
        		"POLISHED_GRANITE_STAIRS:1",
        		"POWERED_RAIL:1",
        		"PRISMARINE:1",
        		"PRISMARINE_BRICK_SLAB:1",
        		"PRISMARINE_BRICK_STAIRS:1",
        		"PRISMARINE_BRICKS:1",
        		"PRISMARINE_SLAB:1",
        		"PRISMARINE_STAIRS:1",
        		"PRISMARINE_WALL:1",
        		"PURPUR_BLOCK:1",
        		"PURPUR_PILLAR:1",
        		"PURPUR_SLAB:1",
        		"PURPUR_STAIRS:1",
        		"QUARTZ_PILLAR:1",
        		"QUARTZ_SLAB:1",
        		"QUARTZ_STAIRS:1",
        		"RAIL:1",
        		"RED_NETHER_BRICK_SLAB:1",
        		"RED_NETHER_BRICK_STAIRS:1",
        		"RED_NETHER_BRICK_WALL:1",
        		"RED_NETHER_BRICKS:1",
        		"RED_SAND:1",
        		"RED_SANDSTONE:1",
        		"RED_SANDSTONE_SLAB:1",
        		"RED_SANDSTONE_STAIRS:1",
        		"RED_SANDSTONE_WALL:1",
        		"SAND:1",
        		"SANDSTONE:1",
        		"SANDSTONE_SLAB:1",
        		"SANDSTONE_STAIRS:1",
        		"SANDSTONE_WALL:1",
        		"SEA_LANTERN:1",
        		"SLIME_BLOCK:1",
        		"SMOOTH_QUARTZ:1",
        		"SMOOTH_QUARTZ_SLAB:1",
        		"SMOOTH_QUARTZ_STAIRS:1",
        		"SMOOTH_RED_SANDSTONE:1",
        		"SMOOTH_RED_SANDSTONE_SLAB:1",
        		"SMOOTH_RED_SANDSTONE_STAIRS:1",
        		"SMOOTH_SANDSTONE:1",
        		"SMOOTH_SANDSTONE_SLAB:1",
        		"SMOOTH_SANDSTONE_STAIRS:1",
        		"SMOOTH_STONE:1",
        		"SMOOTH_STONE_SLAB:1",
        		"SNOW_BLOCK:1",
        		"SOUL_SAND:1",
        		"STICK_PISTON:1",
        		"STONE_BRICK_SLAB:1",
        		"STONE_BRICK_STAIRS:1",
        		"STONE_BRICK_WALL:1",
        		"STONE_BRICKS:1",
        		"STONE_BUTTOn:1",
        		"STONE_PRESSURE_PLATE:1",
        		"STRIPPED_ACACIA_LOG:1",
        		"STRIPPED_ACACIA_WOOD:1",
        		"STRIPPED_BIRCH_LOG:1",
        		"STRIPPED_BIRCH_WOOD:1",
        		"STRIPPED_DARK_OAK_LOG:1",
        		"STRIPPED_DARK_OAK_WOOD:1",
        		"STRIPPED_JUNGLE_LOG:1",
        		"STRIPPED_JUNGLE_WOOD:1",
        		"STRIPPED_OAK_LOG:1",
        		"STRIPPED_OAK_WOOD:1",
        		"STRIPPED_SPRUCE_LOG:1",
        		"STRIPPED_SPRUCE_WOOD:1",
        		"TERRACOTTA:1",
        		"TRIPWIRE_HOOK:1",
        		"COPPER_BLOCK:1",
        		"CUT_COPPER:1",
        		"CUT_COPPER_SLAB:1",
        		"CUT_COPPER_STAIRS:1",
        		"EXPOSED_COPPER:1",
        		"EXPOSED_CUT_COPPER:1",
        		"EXPOSED_CUT_COPPER_SLAB:1",
        		"EXPOSED_CUT_COPPER_STAIRS:1",
        		"OXIDIZED_COPPER:1",
        		"OXIDIZED_CUT_COPPER:1",
        		"OXIDIZED_CUT_COPPER_SLAB:1",
        		"OXIDIZED_CUT_COPPER_STAIRS:1",
        		"WEATHERED_COPPER:1",
        		"WEATHERED_CUT_COPPER:1",
        		"WEATHERED_CUT_COPPER_SLAB:1",
        		"WEATHERED_CUT_COPPER_STAIRS:1",
        		"WAXED_COPPER_BLOCK:1",
        		"WAXED_CUT_COPPER:1",
        		"WAXED_CUT_COPPER_SLAB:1",
        		"WAXED_CUT_COPPER_STAIRS:1",
        		"WAXED_EXPOSED_COPPER:1",
        		"WAXED_EXPOSED_CUT_COPPER:1",
        		"WAXED_EXPOSED_CUT_COPPER_SLAB:1",
        		"WAXED_EXPOSED_CUT_COPPER_STAIRS:1",
        		"WAXED_OXIDIZED_COPPER:1",
        		"WAXED_OXIDIZED_CUT_COPPER:1",
        		"WAXED_OXIDIZED_CUT_COPPER_SLAB:1",
        		"WAXED_OXIDIZED_CUT_COPPER_STAIRS:1",
        		"WAXED_WEATHERED_COPPER:1",
        		"WAXED_WEATHERED_CUT_COPPER:1",
        		"WAXED_WEATHERED_CUT_COPPER_SLAB:1",
        		"WAXED_WEATHERED_CUT_COPPER_STAIRS:1",
        		"DEEPSLATE:1",
        		"COBBLED_DEEPSLATE:1",
        		"COBBLED_DEEPSLATE_SLAB:1",
        		"COBBLED_DEEPSLATE_STAIRS:1",
        		"COBBLED_DEEPSLATE_WALL:1",
        		"POLISHED_DEEPSLATE:1",
        		"POLISHED_DEEPSLATE_SLAB:1",
        		"POLISHED_DEEPSLATE_STAIRS:1",
        		"POLISHED_DEEPSLATE_WALL:1",
        		"DEEPSLATE_TILES:1",
        		"CRACKED_DEEPSLATE_TILES:1",
        		"DEEPSLATE_TILE_SLAB:1",
        		"DEEPSLATE_TILE_STAIRS:1",
        		"DEEPSLATE_TILE_WALL:1",
        		"DEEPSLATE_BRICKS:1",
        		"DEEPSLATE_BRICK_DEEPSLATE_SLAB:1",
        		"DEEPSLATE_BRICK_DEEPSLATE_STAIRS:1",
        		"DEEPSLATE_BRICK_DEEPSLATE_WALL:1",
        		"CRACKED_DEEPSLATE_BRICKS:1",
        		"CHISELED_DEEPSLATE:1",
        		"CANDLE:1",
        		"LIGHTNING_ROD:1",
        		"AMETHYST_BUD:1",
        		"AMETHYST_BLOCK:1",
        		"AMETHYST_CLUSTER:1"));
        
        yml.options().copyDefaults(true);
        
        try
        {
            yml.load("plugins/EternalRealms-Skills/xp_sources.yml");
        }
        catch(FileNotFoundException e)
        {
            try
            {
//                yml.set("mining", yml.get("mining"));
//                yml.set("digging", yml.get("digging"));
//                yml.set("woodcutting", yml.get("woodcutting"));
//                yml.set("herbalism", yml.get("herbalism"));
//                yml.set("farming", yml.get("farming"));
//                yml.set("fishing", yml.get("fishing"));
//                yml.set("husbandry", yml.get("husbandry"));
//                yml.set("cooking", yml.get("cooking"));
//                yml.set("smithing", yml.get("smithing"));
//                yml.set("invention", yml.get("invention"));
//                yml.set("woodworking", yml.get("woodworking"));
//                yml.set("tailoring", yml.get("tailoring"));
//                yml.set("cartography", yml.get("cartography"));
//                yml.set("alchemy", yml.get("alchemy"));
//                yml.set("light_weapons", yml.get("light_weapons"));
//                yml.set("heavy_weapons", yml.get("heavy_weapons"));
//                yml.set("light_armor", yml.get("light_armor"));
//                yml.set("medium_armor", yml.get("medium_armor"));
//                yml.set("heavy_armor", yml.get("heavy_armor"));
//                yml.set("piety", yml.get("piety"));
                yml.save("plugins/EternalRealms-Skills/xp_sources.yml");	//Create the file if it didn't exist
            }
            catch(Exception e2){e.printStackTrace();}
        }
        catch(Exception e){e.printStackTrace();}
        
        //	Process skill sources
        for (Skill skill : Skill.values())
        {
	        List<String> src = new ArrayList<String>();
	        List<Integer> xp = new ArrayList<Integer>();
	        for (String node : yml.getStringList(skill.toString().toLowerCase())) 
	        {
	        	String[] section = node.split(":");
	        	src.add(section[0]);
	        	if (section.length > 1)
	        	{
	        		xp.add(Integer.parseInt(section[1]));
	        	}
	        	else
	        	{
	        		xp.add(1);
	        	}
	        }
	        yml.set(skill.toString().toLowerCase() + "-sources", src);
	    	yml.set(skill.toString().toLowerCase() + "-xp", xp);
        }
        
        return yml;
    }
}
