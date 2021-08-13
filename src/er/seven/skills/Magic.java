package er.seven.skills;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.SmallFireball;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import er.seven.skills.spells.Spell;
import net.kyori.adventure.text.TextComponent;
import net.md_5.bungee.api.ChatColor;


public class Magic 
{
	public enum ScrollType
	{
		TELEPORT_HOME,
		TELEPORT_BACK,
		TELEPORT_SPAWN
	}
	
	public ArrayList<Spell> spells;
	
	public void Load()
	{
		spells = new ArrayList<Spell>();
		
		LoadSpellData("spells");
	}
	
	public static ItemStack CreateScroll(ScrollType scroll)
	{
		ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
		
		if (scroll == ScrollType.TELEPORT_BACK)
		{
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Scroll of Return");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Teleport Back I");
			meta.setLore(lore);
			meta.setCustomModelData(3);
			stack.setItemMeta(meta);
		}
		else if (scroll == ScrollType.TELEPORT_HOME)
		{
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Scroll of Home");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Teleport Home I");
			meta.setLore(lore);
			meta.setCustomModelData(2);
			stack.setItemMeta(meta);
		}
		else if (scroll == ScrollType.TELEPORT_SPAWN)
		{
			ItemMeta meta = stack.getItemMeta();
			meta.setDisplayName(ChatColor.YELLOW + "Scroll of Spawn");
			List<String> lore = new ArrayList<String>();
			lore.add(ChatColor.GRAY + "Teleport Spawn I");
			meta.setLore(lore);
			meta.setCustomModelData(4);
			stack.setItemMeta(meta);
		}
		
		return stack;
	}
	
	public Spell TryCraftGetSpell(Material component)
	{
		for (int i = 0; i < spells.size(); i++)
			if (spells.get(i).canCraft() && spells.get(i).getCraftComponent() == component)
				return spells.get(i);
		
		return null;
	}
	
	public static ItemStack CreateSpellTome(Spell spell)
	{
		ItemStack stack = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.YELLOW + "Spell Tome");
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + spell.getName());
		meta.setLore(lore);
		meta.setCustomModelData(1);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static ItemStack CreateStaff(String name)
	{
		ItemStack stack = new ItemStack(Material.BOW);
		ItemMeta meta = stack.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + name);
		meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		List<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Spellcasting");
		meta.setLore(lore);
		stack.setItemMeta(meta);
		return stack;
	}
	
	public static int FindSpellTomeIndex(Spell spell, Inventory inventory)
	{
		for (int i = 0; i < inventory.getSize(); i++)
		{
			if (inventory.getContents()[i].getType() == Material.ENCHANTED_BOOK
				&& ChatColor.stripColor(inventory.getContents()[i].getItemMeta().getLore().get(0)).equalsIgnoreCase(spell.getName()))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public static void CastSpell(Player player, Spell spell, float power)
	{
		if (spell != null)
		{
			//	Check if the player can afford this cast
			if (player.getTotalExperience() - (int)(spell.getCastingCost()*power) <= 0) return;
			
			ItemStack castStack = player.getEquipment().getItemInMainHand();
			
			LivingEntity target = player;
			
			RayTraceResult ray = player.getWorld().rayTraceEntities(
					player.getEyeLocation().add(player.getEyeLocation().getDirection()).add(player.getEyeLocation().getDirection()), 
					player.getEyeLocation().getDirection(), 
					30, 
					0.3);
			
			double rayDistance = 30;
			if (ray != null && ray.getHitEntity() != null) rayDistance = ray.getHitEntity().getLocation().distance( player.getEyeLocation().add(player.getEyeLocation().getDirection()).add(player.getEyeLocation().getDirection()) );
			
			//	Precast
			spell.onPrecast(player);
			
			//	Cast effect
			player.getWorld().playSound(player.getLocation(), spell.getCastSound(), 1, 1);
			player.getWorld().spawnParticle(Particle.SPELL, 
					player.getLocation().getX(), player.getLocation().getY() + (player.getHeight() * 0.5), player.getLocation().getZ(), 
					40, 
					player.getHeight() * 0.25, player.getHeight() * 0.25, player.getHeight() * 0.25, 
					0);
			
			//	Cooldown
//			player.setCooldown(Material.WOODEN_HOE, 20);
//			player.setCooldown(Material.STONE_HOE, 20);
//			player.setCooldown(Material.IRON_HOE, 20);
//			player.setCooldown(Material.GOLDEN_HOE, 20);
//			player.setCooldown(Material.DIAMOND_HOE, 20);
//			player.setCooldown(Material.NETHERITE_HOE, 20);
			
			//	Drain resource
			player.giveExp( (int) -(spell.getCastingCost()*power) );
			
			Util.damageTool(new Random(), player, castStack, 1);
			
			if (spell.isTargeted())
			{		
				Location castLoc;
				
				//	Cast effect
				if (spell.specialEffect == "")
				{
					for (int i = 2; i < Math.max(3, rayDistance); i++)
					{
						castLoc = player.getEyeLocation().subtract(0, 0.2, 0).add(player.getEyeLocation().getDirection().multiply(i));
						spell.onCast(player, castLoc);
						player.getWorld().spawnParticle(
								spell.getCastParticle(), 
								castLoc, 
								spell.getCastParticleCount(), 
								0.2, 0.2, 0.2,
								0);
					}
				}
				
				if (ray == null || ray.getHitEntity() == null) { target = null; }
				else if (ray.getHitEntity() instanceof LivingEntity)
				{
					target = (LivingEntity)ray.getHitEntity();
				}
				else { target = null; }
			}
			else
			{
				spell.onCast(player, player.getLocation());
				
				if (spell.specialEffect.equalsIgnoreCase("FIREBALL"))
				{
					target = null;
					LargeFireball projectile = player.launchProjectile(LargeFireball.class, player.getEyeLocation().getDirection().multiply(power));
					projectile.addScoreboardTag(String.valueOf(power));
					projectile.setYield(0f);
					projectile.setIsIncendiary(false);
					Main.Instance().spellProjectiles.put(projectile, spell);
				}
				else if (spell.specialEffect.equalsIgnoreCase("SMALL_FIREBALL"))
				{
					target = null;
					SmallFireball projectile = player.launchProjectile(SmallFireball.class, player.getEyeLocation().getDirection().multiply(power));
					projectile.addScoreboardTag(String.valueOf(power));
					projectile.setYield(0f);
					projectile.setIsIncendiary(false);
					Main.Instance().spellProjectiles.put(projectile, spell);
				}
				else if (spell.specialEffect.equalsIgnoreCase("SNOWBALL"))
				{
					target = null;
					Snowball projectile = player.launchProjectile(Snowball.class, player.getEyeLocation().getDirection().multiply(power));
					projectile.addScoreboardTag(String.valueOf(power));
					Main.Instance().spellProjectiles.put(projectile, spell);
				}
				else if (spell.specialEffect.equalsIgnoreCase("POTION"))
				{
					target = null;
					ThrownPotion projectile = player.launchProjectile(ThrownPotion.class, player.getEyeLocation().getDirection().multiply(power));
					projectile.addScoreboardTag(String.valueOf(power));
					Main.Instance().spellProjectiles.put(projectile, spell);
				}
				else if (spell.specialEffect.equalsIgnoreCase("ENDER_PEARL"))
				{
					target = null;
					EnderPearl projectile = player.launchProjectile(EnderPearl.class, player.getEyeLocation().getDirection().multiply(power));
					projectile.addScoreboardTag(String.valueOf(power));
					Main.Instance().spellProjectiles.put(projectile, spell);
				}
				if (spell.specialEffect.equalsIgnoreCase("DRAGON_FIREBALL"))
				{
					target = null;
					DragonFireball projectile = player.launchProjectile(DragonFireball.class, player.getEyeLocation().getDirection().multiply(power));
					projectile.addScoreboardTag(String.valueOf(power));
					projectile.setYield(0f);
					projectile.setIsIncendiary(false);
					Main.Instance().spellProjectiles.put(projectile, spell);
				}
			}
			
			//	XP
			Integer xpReward = (int)( (Math.random()*12 + 7)*power );
			
			//	Do things
			if (target != null)
			{				
				//	Hit effect
				player.getWorld().playSound(target.getLocation(), spell.getHitSound(), 2, 1);
				player.getWorld().spawnParticle(spell.getHitParticle(), target.getLocation().getX(), target.getLocation().getY() + (target.getHeight() * 0.5), target.getLocation().getZ(), spell.getHitParticleCount(), target.getHeight() * 0.5, target.getHeight() * 0.5, target.getHeight() * 0.5, 0);
				
				if (spell.specialEffect.equalsIgnoreCase("LIGHTNING"))
					player.getWorld().strikeLightningEffect(target.getLocation());
				
				double damage = 0;
				int durationAdd = (int)(spell.getSkill().getAbilityDuration(player) * 0.35);
				double strengthMod = 1 + ( (spell.getSkill().getLevel(player) * 4) / (Main.mainConfig.getDouble("Combat.WeaponDamageScale") * 100) );
				double damageScale = 1 + ( spell.getSkill().getLevel(player) / (Main.mainConfig.getDouble("Combat.WeaponDamageScale") * 100) );
				
				durationAdd = (int)( durationAdd * power );
				strengthMod = strengthMod * power;
				
				if (spell.hasPotionEffect())
				{
					PotionEffect effect = new PotionEffect(
							spell.getPotionEffect(), 
							spell.getEffectDuration() + durationAdd, 
							(int) (-1 + (spell.getEffectLevel() * strengthMod))
							);
					
					if (spell.doesEffectOnSelf()) 
						player.addPotionEffect(effect);
					else 
						target.addPotionEffect(effect);
				}
				
				if (spell.doesDamage())
				{
					double spellScaling = ( 1 + ((spell.getSpellLevel() - 1) * 0.25) );	// 25% damage per spell level above 1
					if (spell.doesDamageScale() == false) spellScaling = 1.0;
					
					damage = 4 * spell.getDamageStrength() * damageScale * spellScaling * power;
					
					//	Other effects
					if (castStack.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 0)
					{
						damage *= 1f + (0.25f * (castStack.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) + 1));
					}
					if (castStack.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK) > 0)
					{
						Vector unitVector = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
						target.setVelocity(unitVector.multiply(castStack.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK)));
					}
					
					target.damage(damage, player);
					xpReward += (int)(damage * 2.5);
				}
				
				spell.onHit(player, target);
			}
			
			Util.giveXP(player, spell.getSkill(), xpReward);
			//spell.SendFeedback(player, false);
		}
	}	//	End cast spell
	
	public static void TriggerProjectileHit(Player player, LivingEntity target, Projectile projectile, Spell spell)
	{
		Integer xpReward = 0;
		float power = 1f;
		
		if (projectile.getScoreboardTags() != null && projectile.getScoreboardTags().isEmpty() == false)
			power = Float.valueOf(projectile.getScoreboardTags().toArray(new String[0])[0]);
		
		if (target != null)
		{
			ItemStack castStack = player.getInventory().getItemInMainHand();
			
			//	Hit effect
			player.getWorld().playSound(target.getLocation(), spell.getHitSound(), 2, 1);
			player.getWorld().spawnParticle(spell.getHitParticle(), target.getLocation().getX(), target.getLocation().getY() + (target.getHeight() * 0.5), target.getLocation().getZ(), spell.getHitParticleCount(), target.getHeight() * 0.5, target.getHeight() * 0.5, target.getHeight() * 0.5, 0);
			
			if (spell.specialEffect == "LIGHTNING")
				player.getWorld().strikeLightningEffect(target.getLocation());
			
			double damage = 0;
			int durationAdd = (int)(spell.getSkill().getAbilityDuration(player) * 0.35);
			double strengthMod = 1 + ( (spell.getSkill().getLevel(player) * 4) / (Main.mainConfig.getDouble("Combat.WeaponDamageScale") * 100) );
			double damageScale = 1 + ( spell.getSkill().getLevel(player) / (Main.mainConfig.getDouble("Combat.WeaponDamageScale") * 100) );
			
			durationAdd = (int)( durationAdd * power );
			strengthMod = strengthMod * power;
			
			if (spell.hasPotionEffect())
			{
				PotionEffect effect = new PotionEffect(
						spell.getPotionEffect(), 
						spell.getEffectDuration() + durationAdd, 
						(int) (-1 + (spell.getEffectLevel() * strengthMod))
						);
				
				if (spell.doesEffectOnSelf()) 
					player.addPotionEffect(effect);
				else 
					target.addPotionEffect(effect);
			}
			
			if (spell.doesDamage())
			{
				double spellScaling = ( 1 + ((spell.getSpellLevel() - 1) * 0.25) );	// 25% damage per spell level above 1
				if (spell.doesDamageScale() == false) spellScaling = 1.0;
				
				damage = 4 * spell.getDamageStrength() * damageScale * spellScaling * power;
				
				//	Other effects
				if (castStack.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) > 0)
				{
					damage *= 1f + (0.25f * (castStack.getEnchantmentLevel(Enchantment.ARROW_DAMAGE) + 1));
				}
				if (castStack.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK) > 0)
				{
					Vector unitVector = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
					target.setVelocity(unitVector.multiply(castStack.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK)));
				}
				
				target.damage(damage, player);
				xpReward += (int)(damage * 2.5);
			}
			
			spell.onHit(player, target);
		}
		
		Util.giveXP(player, spell.getSkill(), xpReward);
	}
	
	public static boolean isCastingTool(ItemStack item)
	{
		return (item.getType() == Material.BOW && item.getItemMeta().getLore() != null && ChatColor.stripColor(item.getItemMeta().getLore().get(0)).equalsIgnoreCase("Spellcasting"));
	}
	
	public static boolean hasSpellComponentsReady(Player player)
	{
//		if (player.getCooldown(Material.WOODEN_HOE) != 0 ||
//			player.getCooldown(Material.STONE_HOE) != 0 ||
//			player.getCooldown(Material.IRON_HOE) != 0 ||
//			player.getCooldown(Material.GOLDEN_HOE) != 0 ||
//			player.getCooldown(Material.DIAMOND_HOE) != 0 ||
//			player.getCooldown(Material.NETHERITE_HOE) != 0) 
//		{
//			return false;
//		}
		
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		
		if (offHand.getType() == Material.ENCHANTED_BOOK && isCastingTool(mainHand))
		{
			return true;
		}
		
		return false;
	}	//	End can cast spell
	
	public static void TryCast(Player player)
	{
		TryCast(player, 1f);
	}
	
	public static void TryCast(Player player, float power)
	{		
		Spell spell = null;
		
		if (hasSpellComponentsReady(player) == false) return;
		
		//////////////////////////////////
		//	FIND FIRST CASTABLE SPELL
		for (int n = 0; n < Main.GetSpells().size(); n++) { if (Main.GetSpells().get(n).isHoldingSpell(player)) { spell = Main.GetSpells().get(n); break; } }
		//////////////////////////////////
		
		if (spell != null) Magic.CastSpell(player, spell, power);
	}	//	End try cast
	
	public void LoadSpellData(String subfolder)
	{
		File dir = new File("plugins/EternalRealms-Skills/" + subfolder + "/");
		String[] files = dir.list();
		Main.Instance().getServer().getConsoleSender().sendMessage("Loading structures in /plugins/EternalRealms-Skills/" + subfolder + "/");
		
		List<Spell> loadedSpells = new ArrayList<Spell>();
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].contains(".yml") == false) 
			{
				String[] subFiles = (new File("plugins/EternalRealms-Skills/" + subfolder + "/" + files[i] + "/")).list();
				for (int n = 0; n < subFiles.length; n++)
				{
					if (subFiles[n].contains(".yml") == true)
					{
						Main.Instance().getServer().getConsoleSender().sendMessage("       Found spell: " + subFiles[n] + " in " + files[i]);
						Spell spell = LoadSpellFrom("plugins/EternalRealms-Skills/" + subfolder + "/" + files[i] + "/" + subFiles[n]);
						if (spell != null) loadedSpells.add( spell );
					}
				}
			}
			else
			{
				Main.Instance().getServer().getConsoleSender().sendMessage("       Found spell: " + files[i] + " in " + subfolder);
				Spell spell = LoadSpellFrom("plugins/EternalRealms-Skills/" + subfolder + "/" + files[i]);
				if (spell != null) loadedSpells.add( spell );
			}
		}
		
		Main.Instance().getServer().getConsoleSender().sendMessage("              Spells loaded: " + loadedSpells.size());

		//	Fill the spell list
		for (int i = 0; i < loadedSpells.size(); i++)
		{		
			spells.add( loadedSpells.get(i) );
		}
	}	//	end load spell data
	
	public Spell LoadSpellFrom(String path)
	{
		YamlConfiguration yml = Main.loadYaml(path);
		Spell spell = new Spell();
		
		if (yml.getBoolean("Spell.enabled") == false) return null;
		
		spell.name = yml.getString("Spell.name");
		spell.spellLevel = yml.getInt("Spell.level");
		spell.castingCost = yml.getInt("Spell.cost");
		spell.targeted = yml.getBoolean("Spell.targeted");
		spell.component = Material.getMaterial(yml.getString("Spell.component"));
		
		spell.damages = yml.getBoolean("Damage.enabled");
		spell.damageScales = yml.getBoolean("Damage.level-scaling");
		spell.damage = yml.getDouble("Damage.damage");
		
		if (yml.getBoolean("Special.enabled"))
			spell.specialEffect = yml.getString("Special.effect");
		
		if (yml.getBoolean("Effect.enabled") == false) { spell.effect = null; }
		else
		{
			spell.effect = PotionEffectType.getByName(yml.getString("Effect.type"));
			spell.effectScales = yml.getBoolean("Effect.level-scaling");
			spell.effectOnSelf = yml.getBoolean("Effect.self");
			spell.effectLevel = yml.getInt("Effect.base-power");
			spell.effectDuration = (int) (yml.getDouble("Effect.base-duration") * 20);
		}
		
		spell.castSound = Sound.valueOf(yml.getString("Cast.sound"));
		spell.castParticle = Particle.valueOf(yml.getString("Cast.particle"));
		spell.castParticleCount = yml.getInt("Cast.particle-count");
		
		spell.hitSound = Sound.valueOf(yml.getString("Hit.sound"));
		spell.hitParticle = Particle.valueOf(yml.getString("Hit.particle"));
		spell.hitParticleCount = yml.getInt("Hit.particle-count");
		
		return spell;
	}
}
