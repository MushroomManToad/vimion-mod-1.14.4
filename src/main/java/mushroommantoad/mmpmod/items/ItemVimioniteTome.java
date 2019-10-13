package mushroommantoad.mmpmod.items;

import java.util.ArrayList;

import mushroommantoad.mmpmod.network.SendBookOpenPacket;
import mushroommantoad.mmpmod.network.VimionPacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;

public class ItemVimioniteTome extends Item
{
	String[] nbtIDs = {"VimionAdvancements", "NecrionAdvancements", "SolarionAdvancements","NihilionAdvancements","ExpionAdvancements"};
	
	public ItemVimioniteTome(Properties properties) 
	{
		super(properties);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) 
	{
		if(!worldIn.isRemote)
		{
			//FIXME Remove this before releasing
			
			if(playerIn.isSneaking())
			{
				for(int i = 0; i < 5; i++)
				{
					int[] emptiness = new int[100];
					playerIn.getPersistentData().putIntArray(nbtIDs[i], emptiness);
				}
			}
				
			//FIXME Remove this before releasing
			
			
			
			ArrayList<Integer> vals = new ArrayList<>();
			ServerPlayerEntity playerMP = (ServerPlayerEntity) playerIn;
			for(int i = 0; i < 5; i++)
			{
				if(playerIn.getPersistentData().contains(nbtIDs[i]))
					for(int j = 0; j < 100; j++)
						vals.add(playerIn.getPersistentData().getIntArray(nbtIDs[i])[j]);
				else
					for(int j = 0; j < 100; j++)
						vals.add(0);
			}
			int[] values = new int[500];
		    for (int i = 0; i < vals.size(); i++)
		    {
		    	values[i] = vals.get(i).intValue();
		    }
			VimionPacketHandler.CHANNEL.sendTo(new SendBookOpenPacket(values), playerMP.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
