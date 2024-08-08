package ralf2oo2.extrapistons.blockentity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.nbt.NbtHelper;
import net.modificationstation.stationapi.api.util.math.Direction;
import ralf2oo2.extrapistons.util.Utils;

public class ExtraPistonBlockEntity extends BlockEntity {
    private BlockState pushedBlock;
    private Direction facing;
    private boolean extending;
    private boolean source;
    private float progress;
    private float lastProgress;

    public ExtraPistonBlockEntity(){}
    public ExtraPistonBlockEntity(BlockState pushedBlock, Direction facing, boolean extending, boolean source){
        this.pushedBlock = pushedBlock;
        this.facing = facing;
        this.extending = extending;
        this.source = source;
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if(pushedBlock != null){
            nbt.put("blockState", Utils.fromBlockState(pushedBlock));
        }
        nbt.putInt("facing", this.facing.getId());
        nbt.putFloat("progress", this.lastProgress);
        nbt.putBoolean("extending", this.extending);
        nbt.putBoolean("source", this.source);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.pushedBlock = Utils.toBlockState(nbt.getCompound("blockState"));
        this.facing = Direction.byId(nbt.getInt("facing"));
        this.progress = nbt.getFloat("progress");
        this.lastProgress = this.progress;
        this.extending = nbt.getBoolean("extending");
        this.source = nbt.getBoolean("source");
    }
}
