package net.yawk.client.mods.building;

import net.yawk.client.Client;
import net.yawk.client.events.*;
import net.yawk.client.modmanager.Mod;
import net.yawk.client.modmanager.ModDetails;
import net.yawk.client.modmanager.ModType;

import com.darkmagician6.eventapi.EventTarget;

@ModDetails(name = "Speedmine", defaultKey = 0, desc = "Break blocks faster", type = ModType.BUILDING)
public class SpeedMine extends Mod{
	
	@EventTarget
	public void onTick(EventTick e){
		Client.getClient().getMinecraft().playerController.blockHitDelay = 0;
	}
	
	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		Client.getClient().getMinecraft().playerController.blockHitDelay = 5;
	}
}
