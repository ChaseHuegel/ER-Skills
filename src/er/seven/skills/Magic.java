package er.seven.skills;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import er.seven.skills.spells.Spell;

public class Magic 
{
	public ArrayList<Spell> spells;
	
	public void Load()
	{
		spells = new ArrayList<Spell>();
		
		LoadSpellData("spells");
	}
	
	public static void CastSpell(Player player, Spell spell)
	{
		if (spell != null)
		{
			//	Check if the player can afford this cast
			if (player.getTotalExperience() - spell.getCastingCost() <= 0) return;
			
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
			player.setCooldown(Material.WOODEN_HOE, 20);
			player.setCooldown(Material.STONE_HOE, 20);
			player.setCooldown(Material.IRON_HOE, 20);
			player.setCooldown(Material.GOLDEN_HOE, 20);
			player.setCooldown(Material.DIAMOND_HOE, 20);
			player.setCooldown(Material.NETHERITE_HOE, 20);
			
			//	Drain resource
			player.giveExp( -spell.getCastingCost() );
			
			if (Math.random() < 0.15) { Util.damageTool(new Random(), player, castStack, 1); }
			
			if (spell.isTargeted())
			{		
				Location castLoc;
				
				//	Cast effect
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
			}
			
			//	XP
			Integer xpReward = (int)(Math.random() * 25) + 15;
			
			//	Do things
			if (target != null)
			{				
				//	Hit effect
				player.getWorld().playSound(target.getLocation(), spell.getHitSound(), 2, 1);
				player.getWorld().spawnParticle(spell.getHitParticle(), target.getLocation().getX(), target.getLocation().getY() + (target.getHeight() * 0.5), target.getLocation().getZ(), spell.getHitParticleCount(), target.getHeight() * 0.5, target.getHeight() * 0.5, target.getHeight() * 0.5, 0);
				
				double damage = 0;
				int durationAdd = (int)(spell.getSkill().getAbilityDuration(player) * 0.01);
				double strengthMod = 1 + ( (spell.getSkill().getLevel(player) * 1.5) / (Main.mainConfig.getDouble("Combat.WeaponDamageScale") * 100) );
				double damageScale = 1 + ( spell.getSkill().getLevel(player) / (Main.mainConfig.getDouble("Combat.WeaponDamageScale") * 100) );
				
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
					
					damage = 4 * spell.getDamageStrength() * damageScale * spellScaling;
					target.damage(damage, player);
					xpReward += (int)(damage * 2.5);
				}
				
				spell.onHit(player, target);
			}
			
			Util.giveXP(player, spell.getSkill(), xpReward);
			//spell.SendFeedback(player, false);
		}
	}	//	End cast spell
	
	public static boolean hasSpellComponentsReady(Player player)
	{
		if (player.getCooldown(Material.WOODEN_HOE) != 0 ||
			player.getCooldown(Material.STONE_HOE) != 0 ||
			player.getCooldown(Material.IRON_HOE) != 0 ||
			player.getCooldown(Material.GOLDEN_HOE) != 0 ||
			player.getCooldown(Material.DIAMOND_HOE) != 0 ||
			player.getCooldown(Material.NETHERITE_HOE) != 0) 
		{
			return false;
		}
		
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		ItemStack offHand = player.getInventory().getItemInOffHand();
		
		if (offHand.getType() == Material.ENCHANTED_BOOK && Util.isAnyHoe(mainHand.getType()))
		{
			return true;
		}
		
		return false;
	}	//	End can cast spell
	
	public static void TryCast(Player player)
	{		
		Spell spell = null;
		
		if (hasSpellComponentsReady(player) == false) return;
		
		//////////////////////////////////
		//	FIND FIRST CASTABLE SPELL
		for (int n = 0; n < Main.GetSpells().size(); n++) { if (Main.GetSpells().get(n).isHoldingSpell(player)) { spell = Main.GetSpells().get(n); break; } }
		//////////////////////////////////
		
		if (spell != null) Magic.CastSpell(player, spell);
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
		
		spell.damages = yml.getBoolean("Damage.enabled");
		spell.damageScales = yml.getBoolean("Damage.level-scaling");
		spell.damage = yml.getDouble("Damage.damage");
		
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
