package ralf2oo2.extrapistons.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import ralf2oo2.extrapistons.blockentity.ExtraPistonBlockEntity;

public class BlockEntityRegistry {
    @EventListener
    private static void registerBlockEntities(BlockEntityRegisterEvent event) {
        event.register(ExtraPistonBlockEntity.class, "extra_piston_blockentity");
    }
}
