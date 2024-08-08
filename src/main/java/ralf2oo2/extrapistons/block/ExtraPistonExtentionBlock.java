package ralf2oo2.extrapistons.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import ralf2oo2.extrapistons.blockentity.ExtraPistonBlockEntity;
import ralf2oo2.extrapistons.property.PistonType;

import java.util.Random;

public class ExtraPistonExtentionBlock extends TemplateBlockWithEntity {
    public static final EnumProperty<Direction> FACING;
    public static final EnumProperty<PistonType> TYPE;

    public ExtraPistonExtentionBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(TYPE, PistonType.DEFAULT));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE);
    }

    @Override
    protected BlockEntity createBlockEntity() {
        return null;
    }

    public static BlockEntity createBlockEntityPiston(BlockState pushedBlock, Direction dir, boolean extending, boolean bl){
        return new ExtraPistonBlockEntity(pushedBlock, dir, extending, bl);
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 0;
    }

    static {
        FACING = ExtraPistonHeadBlock.FACING;
        TYPE = ExtraPistonHeadBlock.TYPE;
    }
}
