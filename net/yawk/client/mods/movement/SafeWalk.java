package net.yawk.client.mods.movement;

import net.yawk.client.Client;
import net.yawk.client.events.EventMoveEntity;
import net.yawk.client.modmanager.Mod;
import net.yawk.client.modmanager.ModDetails;
import net.yawk.client.modmanager.ModType;

import com.darkmagician6.eventapi.EventTarget;
import com.darkmagician6.eventapi.types.EventType;

@ModDetails(name = "SafeWalk", defaultKey = 0, desc = "Stops you falling off edges", type = ModType.MOVEMENT)
public class SafeWalk extends Mod{
	
	private boolean wasSneaking;
	
	public SafeWalk(){
		
	}
	
	@EventTarget
	public void onMove(EventMoveEntity e){
		if(e.type == EventType.PRE){
			wasSneaking = Client.getClient().getPlayer().movementInput.sneak;
			Client.getClient().getPlayer().movementInput.sneak = true;
		}else if(e.type == EventType.POST){
			Client.getClient().getPlayer().movementInput.sneak = wasSneaking;
		}
	}
	
	@Override
	public void onEnable() {
		
	}
	
	@Override
	public void onDisable() {
		
	}
}
