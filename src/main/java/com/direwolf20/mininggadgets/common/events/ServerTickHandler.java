package com.direwolf20.mininggadgets.common.events;

import com.direwolf20.mininggadgets.common.network.PacketHandler;
import com.direwolf20.mininggadgets.common.network.Packets.PacketDurabilitySync;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ServerTickHandler {

    private static List<Tuple<BlockPos, Integer>> updateList = new ArrayList<>();
    private static World serverWorld;

    @SubscribeEvent
    public static void handleTickEndEvent(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!updateList.isEmpty()) {
                PacketHandler.sendToAll(new PacketDurabilitySync(updateList), serverWorld);
                updateList.clear();
            }
        }
    }

    public static void addToList(BlockPos pos, int durability, World world) {
        updateList.add(new Tuple<>(pos, durability));
        serverWorld = world;
    }

}
