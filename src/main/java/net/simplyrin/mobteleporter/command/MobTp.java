package net.simplyrin.mobteleporter.command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.simplyrin.chatcolor.ChatColor;
import net.simplyrin.mobteleporter.Main;
import net.simplyrin.mobteleporter.tool.EntityManager;

/**
 * Created by SimplyRin on 2018/06/07.
 *
 * Copyright (c) 2018 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
public class MobTp implements CommandExecutor {

	public static String WAND_NAME = ChatColor.translate("&", "&eMob Teleport Wand");

	private Main instance;

	public MobTp(Main instance) {
		this.instance = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			return true;
		}

		if(!sender.hasPermission("mobtp.command")) {
			this.instance.sendMessage(sender, "§cYou do not have access to this command");
			this.instance.sendMessage(sender, "§cPlease contact to SimplyRin!");
			return true;
		}

		Player player = (Player) sender;
		EntityManager entityManager = this.instance.getEntityManager().get(player.getName());

		if(args.length > 0) {
			if(args[0].equalsIgnoreCase("wand")) {
				ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
				ItemMeta itemMeta = itemStack.getItemMeta();
				itemMeta.setDisplayName(WAND_NAME);
				itemStack.setItemMeta(itemMeta);
				player.getInventory().addItem(itemStack);
				player.updateInventory();
				this.instance.sendMessage(sender, "&c'&eMob Teleport Wand&c' &bをインベントリに追加しました！");
				return true;
			}
			if(args[0].equalsIgnoreCase("list")) {
				this.instance.sendMessage(sender, "&b追加されている Entity リスト");
				if(entityManager.getEntityList().isEmpty()) {
					this.instance.sendMessage(sender, "&e- &c'null'");
				} else {
					for(Entity entity : entityManager.getEntityList()) {
						this.instance.sendMessage(sender, "&e- &c'&b" + entity.getType().getName() + "&c'");
					}
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("info")) {
				this.instance.sendMessage(sender, "&bワールド情報");
				for(World world : this.instance.getServer().getWorlds()) {
					this.instance.sendMessage(sender, "&b- &a" + world.getName());
				}
				this.instance.sendMessage(sender, "&bプレイヤー情報");
				this.instance.sendMessage(sender, "&aワールド名: &b" + player.getWorld().getName());
				this.instance.sendMessage(sender, "&a座標: &b" + player.getLocation().getBlockX() + " / " + player.getLocation().getBlockY() + " / " + player.getLocation().getBlockZ());
				return true;
			}
			if(args[0].equalsIgnoreCase("reset")) {
				entityManager.resetEntityList();
				this.instance.getEntityManager().put(player.getName(), entityManager);
				this.instance.sendMessage(sender, "&cEntity リストをリセットしました！");
				return true;
			}
			if(args[0].equalsIgnoreCase("tp")) {
				if(entityManager.getEntityList() == null) {
					this.instance.sendMessage(sender, "&cリストに Entity が存在しません！");
					return true;
				}

				for(Entity entity : entityManager.getEntityList()) {
					if(entity != null) {
						entity.getLocation().setWorld(player.getWorld());
						entity.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()));
					}
				}

				boolean keep = false;
				if(args.length > 1) {
					if(args[1].equalsIgnoreCase("-keep")) {
						keep = true;
					}
				}
				if(keep) {
					entityManager.resetEntityList();
					this.instance.getEntityManager().put(player.getName(), entityManager);
				}

				this.instance.sendMessage(sender, "&b全ての Mob を現在地に移動させました！" + (keep ? " (リストキープ)" : ""));
				return true;
			}
		}

		this.instance.sendMessage(sender, "&cUsage: /" + cmd.getName() + " <wand|list|info|reset|tp(-keep)>");
		return true;
	}

}
