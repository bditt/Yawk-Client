package net.yawk.client.hooks;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.types.EventType;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.world.World;
import net.yawk.client.Client;
import net.yawk.client.command.Command;
import net.yawk.client.command.CommandManager;
import net.yawk.client.events.*;

public class EntityPlayerSPHook extends EntityPlayerSP{

	public EntityPlayerSPHook(Minecraft mcIn, World worldIn,
			NetHandlerPlayClient p_i46278_3_, StatFileWriter p_i46278_4_) {
		super(mcIn, worldIn, p_i46278_3_, p_i46278_4_);
	}

	private EventTick tick = new EventTick();

	@Override
	public void onUpdate(){
		super.onUpdate();
		EventManager.call(tick);
	}

	@Override
	protected boolean pushOutOfBlocks(double x, double y, double z) {

		EventPushOut e = new EventPushOut(x, y, z);
		EventManager.call(e);

		if(e.isCancelled()){
			return false;
		}else{
			return super.pushOutOfBlocks(x, y, z);
		}
	}

	@Override
	public void setHealth(float f){

		EventSetHealth e = new EventSetHealth(f);

		EventManager.call(e);

		if(!e.isCancelled()){
			super.setHealth(f);
		}
	}

	@Override
	public boolean isEntityInsideOpaqueBlock() {

		EventInsideOpaqueBlock e = new EventInsideOpaqueBlock(super.isEntityInsideOpaqueBlock());
		EventManager.call(e);

		return e.inside;
	}

	@Override
	public void moveEntity(double x, double y, double z) {

		EventMoveEntity e = new EventMoveEntity(EventType.PRE, x, y, z);
		EventManager.call(e);

		if(!e.isCancelled()){
			super.moveEntity(e.x, e.y, e.z);
		}

		EventMoveEntity e1 = new EventMoveEntity(EventType.POST, e.x, e.y, e.z);
		EventManager.call(e1);
	}

	@Override
	public void sendMotionUpdates() {

		EventMotionUpdate e = new EventMotionUpdate(EventType.PRE);
		EventManager.call(e);

		if(!e.isCancelled()){
			super.sendMotionUpdates();
		}

		EventMotionUpdate e1 = new EventMotionUpdate(EventType.POST);
		EventManager.call(e1);
	}

	@Override
	public void respawnPlayer() {

		EventManager.call(new EventRespawn());

		super.respawnPlayer();
	}

	@Override
	public boolean interactWith(Entity en) {

		EventInteractWithEntity e = new EventInteractWithEntity(en);

		EventManager.call(e);

		if(!e.isCancelled()){
			return super.interactWith(en);
		}else{
			return false;
		}
	}

	@Override
	public void jump() {

		EventJump e = new EventJump(EventType.PRE);
		EventManager.call(e);

		if(!e.isCancelled()){
			super.jump();
		}

		EventJump e1 = new EventJump(EventType.POST);
		EventManager.call(e1);
	}

	@Override
	public void sendChatMessage(String message) {

		if(message.startsWith(CommandManager.PREFIX)) {

			String commandWithArgs = message.toLowerCase().replaceFirst(CommandManager.PREFIX, "");

			String[] parts = commandWithArgs.split(" ");

			String[] response = Client.getClient().getCommandManager().run(parts);

			if(response != null){

				for(String line : response){
					Client.getClient().addChat(line);
				}

			}

		}else{
			super.sendChatMessage(message);
		}
	}

}
