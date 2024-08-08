package ralf2oo2.extrapistons.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import ralf2oo2.extrapistons.property.PistonType;

import java.util.Random;

public class ExtraPistonHeadBlock extends TemplateBlock {
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class);
    public static final EnumProperty<PistonType> TYPE = EnumProperty.of("type", PistonType.class);
    public static final BooleanProperty SHORT = BooleanProperty.of("short");
    public ExtraPistonHeadBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(TYPE, PistonType.DEFAULT).with(SHORT, false));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TYPE, SHORT);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public int getDroppedItemCount(Random random) {
        return 0;
    }


}
