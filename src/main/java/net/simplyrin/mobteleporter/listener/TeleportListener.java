package net.simplyrin.mobteleporter.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import net.simplyrin.mobteleporter.Main;
import net.simplyrin.mobteleporter.command.MobTp;
import net.simplyrin.mobteleporter.tool.EntityManager;

/**
 * Created by SimplyRin on 2018/06/08.
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
public class TeleportListener implements Listener {

	private Main instance;

	public TeleportListener(Main instance) {
		this.instance = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if(this.instance.getEntityManager().get(player.getName()) == null) {
			this.instance.getEntityManager().put(player.getName(), new EntityManager(player));
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();

		if(player.getItemInHand() == null) {
			return;
		}

		if(player.getItemInHand().getItemMeta() == null) {
			return;
		}

		String displayName = player.getItemInHand().getItemMeta().getDisplayName();

		if(displayName == null) {
			return;
		}

		if(displayName.equalsIgnoreCase(MobTp.WAND_NAME)) {
			if(event.getRightClicked() == null) {
				return;
			}

			Entity entity = event.getRightClicked();

			if(entity instanceof Player) {
				return;
			}

			EntityManager entityManager = this.instance.getEntityManager().get(player.getName());
			entityManager.addEntity(entity);
			this.instance.getEntityManager().put(player.getName(), entityManager);
			this.instance.sendMessage(player, "&c'&b" + entity.getType().getName() + "&c' &eをリストに追加しました！");
			return;
		}
	}

}
