package ralf2oo2.extrapistons.registry;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.util.Identifier;
import ralf2oo2.extrapistons.ExtraPistons;
import ralf2oo2.extrapistons.block.ExtraPistonBlock;
import ralf2oo2.extrapistons.block.ExtraPistonExtentionBlock;
import ralf2oo2.extrapistons.block.ExtraPistonHeadBlock;

public class BlockRegistry {
    public static Block pistonMk2Block;
    public static Block pistonExtentionBlock;
    public static Block pistonHeadBlock;
    @EventListener
    private static void registerBlocks(BlockRegistryEvent event) {
        pistonMk2Block = new ExtraPistonBlock(Identifier.of(ExtraPistons.NAMESPACE, "piston_mk2"), Material.WOOD, false).setTranslationKey(ExtraPistons.NAMESPACE, "piston_mk2");
        pistonExtentionBlock = new ExtraPistonExtentionBlock(Identifier.of(ExtraPistons.NAMESPACE, "piston_extention"), Material.WOOD).setTranslationKey(ExtraPistons.NAMESPACE, "piston_extention");
        pistonHeadBlock = new ExtraPistonHeadBlock(Identifier.of(ExtraPistons.NAMESPACE, "piston_head"), Material.WOOD).setTranslationKey(ExtraPistons.NAMESPACE, "piston_head");
    }
}
