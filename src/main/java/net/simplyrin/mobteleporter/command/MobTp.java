package net.simplyrin.mobteleporter.command;

import org.bukkit.Material;
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
				EntityManager entityManager = this.instance.getEntityManager().get(player.getName());
				this.instance.sendMessage(sender, "&b追加されている Entity リスト");
				for(Entity entity : entityManager.getEntityList()) {
					this.instance.sendMessage(sender, "&e- &c'&b" + entity.getType().getName() + "&c'");
				}
				return true;
			}
			if(args[0].equalsIgnoreCase("reset")) {
				EntityManager entityManager = this.instance.getEntityManager().get(player.getName());
				entityManager.resetEntityList();
				this.instance.getEntityManager().put(player.getName(), entityManager);
				this.instance.sendMessage(sender, "&cEntity リストをリセットしました！");
				return true;
			}
			if(args[0].equalsIgnoreCase("tp")) {
				EntityManager entityManager = this.instance.getEntityManager().get(player.getName());

				if(entityManager.getEntityList() == null) {
					this.instance.sendMessage(sender, "&cリストに Entity が存在しません！");
					return true;
				}

				for(Entity entity : entityManager.getEntityList()) {
					entity.teleport(player);
				}
				entityManager.resetEntityList();
				this.instance.getEntityManager().put(player.getName(), entityManager);

				this.instance.sendMessage(sender, "&b全ての Mob を現在地に移動させました！");
				return true;
			}
		}

		this.instance.sendMessage(sender, "&cUsage: /" + cmd.getName() + " <wand|list|reset|tp>");
		return true;
	}

}
