package net.simplyrin.mobteleporter;

import java.util.HashMap;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import net.simplyrin.chatcolor.ChatColor;
import net.simplyrin.mobteleporter.command.MobTp;
import net.simplyrin.mobteleporter.listener.TeleportListener;
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
public class Main extends JavaPlugin implements Listener {

	/**
	 * だがしかし、Getter は設置しない。
	 */
	private static Main instance;

	/**
	 * player.getName(), new EntityManager(player);
	 */
	@Getter
	private HashMap<String, EntityManager> entityManager = new HashMap<>();

	@Override
	public void onEnable() {
		instance = this;

		instance.getCommand("mobtp").setExecutor(new MobTp(instance));
		instance.getCommand("mobteleport").setExecutor(new MobTp(instance));
		instance.getCommand("mobteleporter").setExecutor(new MobTp(instance));

		instance.getServer().getPluginManager().registerEvents(new TeleportListener(instance), instance);
	}

	public void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(this.getPrefix() + ChatColor.translate("&", message));
	}

	public void sendMessage(Player player, String message) {
		player.sendMessage(this.getPrefix() + ChatColor.translate("&", message));
	}

	public String getPrefix() {
		return ChatColor.translate("&", "&7[&cMobTeleporter&7] &r");
	}

}
