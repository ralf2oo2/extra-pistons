package ralf2oo2.extrapistons.block;

import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.PistonConstants;
import net.minecraft.block.PistonExtensionBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.PistonBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;
import ralf2oo2.extrapistons.blockentity.ExtraPistonBlockEntity;

import java.util.Random;

public class ExtraPistonBlock extends TemplateBlock {
    public static final EnumProperty<Direction> FACING = EnumProperty.of("facing", Direction.class);
    public static final BooleanProperty EXTENDED = BooleanProperty.of("extended");

    public boolean sticky;
    public static boolean field_845;
    public ExtraPistonBlock(Identifier identifier, Material material, boolean sticky) {
        super(identifier, material);
        setDefaultState(getDefaultState().with(FACING, Direction.UP).with(EXTENDED, false));
        this.sticky = sticky;
    }

    @Override
    public void onPlaced(World world, int x, int y, int z, LivingEntity placer) {
        Direction placeDir = getPlacementDir(x, y, z, (PlayerEntity) placer);
        world.setBlockStateWithMetadata(x, y, z, world.getBlockState(x, y, z).with(FACING, placeDir), 15);
        updateBoundingBox(world, x, y, z);
        System.out.println(world.getBlockState(x, y, z).get(FACING));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, EXTENDED);
    }

    private Direction getPlacementDir(int x, int y, int z, PlayerEntity playerEntity) {
        if (MathHelper.abs((float)playerEntity.x - (float)x) < 2.0F && MathHelper.abs((float)playerEntity.z - (float)z) < 2.0F) {
            double var5 = playerEntity.y + 1.82 - (double)playerEntity.eyeHeight;
            if (var5 - (double)y > 2.0) {
                return Direction.UP;
            }

            if ((double)y - var5 > 0.0) {
                return Direction.DOWN;
            }
        }

        int var7 = MathHelper.floor((double)(playerEntity.yaw * 4.0F / 360.0F) + 0.5) & 3;
        if (var7 == 0) {
            return Direction.NORTH;
        } else if (var7 == 1) {
            return Direction.EAST;
        } else if (var7 == 2) {
            return Direction.SOUTH;
        } else {
            return var7 == 3 ? Direction.WEST : Direction.DOWN;
        }
    }

    private void tryMove(World world, int x, int y, int z, BlockState state){
        Direction direction = world.getBlockState(x, y, z).get(FACING);
        boolean shouldExtend = this.shouldExtend(world, x, y, z, direction);
        if(shouldExtend && !world.getBlockState(x, y, z).get(EXTENDED).booleanValue()){

        }

    }
    private boolean shouldExtend(World world, int x, int y, int z, Direction direction){
        return true;
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id) {
        if (id > 0 && Block.BLOCKS[id].canEmitRedstonePower()) {
            boolean var6 = world.method_265(x, y, z) || world.method_265(x, y + 1, z);
            if (var6) {
                world.method_216(x, y, z, this.id, this.getTickRate());
            }
        }
    }

    @Override
    public int getTickRate() {
        return 4;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random) {
//        if(world.method_265(x, y, z)){
//
//        }
        System.out.println("truege");
        //world.method_250()
    }

    @Override
    public void onBlockAction(World world, int x, int y, int z, int data1, int data2) {
        this.field_845 = true;
        if (data1 == 0) {
            if (this.method_766(world, x, y, z, data2)) {
                world.method_215(x, y, z, data2 | 8);
                world.playSound((double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "tile.piston.out", 0.5F, world.field_214.nextFloat() * 0.25F + 0.6F);
            }
        } else if (data1 == 1) {
            BlockEntity var8 = world.getBlockEntity(x + PistonConstants.HEAD_OFFSET_X[data2], y + PistonConstants.HEAD_OFFSET_Y[data2], z + PistonConstants.HEAD_OFFSET_Z[data2]);
            if (var8 != null && var8 instanceof PistonBlockEntity) {
                ((PistonBlockEntity)var8).finish();
            }

            world.method_154(x, y, z, Block.MOVING_PISTON.id, data2);
            world.method_157(x, y, z, PistonExtensionBlock.method_1533(this.id, data2, data2, false, true));
            if (this.sticky) {
                int var9 = x + PistonConstants.HEAD_OFFSET_X[data2] * 2;
                int var10 = y + PistonConstants.HEAD_OFFSET_Y[data2] * 2;
                int var11 = z + PistonConstants.HEAD_OFFSET_Z[data2] * 2;
                int var12 = world.getBlockId(var9, var10, var11);
                int var13 = world.getBlockMeta(var9, var10, var11);
                boolean var14 = false;
                if (var12 == Block.MOVING_PISTON.id) {
                    BlockEntity var15 = world.getBlockEntity(var9, var10, var11);
                    if (var15 != null && var15 instanceof PistonBlockEntity) {
                        PistonBlockEntity var16 = (PistonBlockEntity)var15;
                        if (var16.getFacing() == data2 && var16.isExtending()) {
                            var16.finish();
                            var12 = var16.getPushedBlockId();
                            var13 = var16.getPushedBlockData();
                            var14 = true;
                        }
                    }
                }

                if (var14 || var12 <= 0 || !method_759(var12, world, var9, var10, var11, false) || Block.BLOCKS[var12].getPistonBehavior() != 0 && var12 != Block.PISTON.id && var12 != Block.STICKY_PISTON.id) {
                    if (!var14) {
                        this.field_845 = false;
                        world.setBlock(x + PistonConstants.HEAD_OFFSET_X[data2], y + PistonConstants.HEAD_OFFSET_Y[data2], z + PistonConstants.HEAD_OFFSET_Z[data2], 0);
                        this.field_845 = true;
                    }
                } else {
                    this.field_845 = false;
                    world.setBlock(var9, var10, var11, 0);
                    this.field_845 = true;
                    x += PistonConstants.HEAD_OFFSET_X[data2];
                    y += PistonConstants.HEAD_OFFSET_Y[data2];
                    z += PistonConstants.HEAD_OFFSET_Z[data2];
                    world.method_154(x, y, z, Block.MOVING_PISTON.id, var13);
                    world.method_157(x, y, z, PistonExtensionBlock.method_1533(var12, var13, data2, false, false));
                }
            } else {
                this.field_845 = false;
                world.setBlock(x + PistonConstants.HEAD_OFFSET_X[data2], y + PistonConstants.HEAD_OFFSET_Y[data2], z + PistonConstants.HEAD_OFFSET_Z[data2], 0);
                this.field_845 = true;
            }

            world.playSound((double)x + 0.5, (double)y + 0.5, (double)z + 0.5, "tile.piston.in", 0.5F, world.field_214.nextFloat() * 0.15F + 0.6F);
        }

        this.field_845 = false;
    }

    private boolean method_766(World world, int i, int j, int k, int l) {
        int var6 = i + PistonConstants.HEAD_OFFSET_X[l];
        int var7 = j + PistonConstants.HEAD_OFFSET_Y[l];
        int var8 = k + PistonConstants.HEAD_OFFSET_Z[l];
        int var9 = 0;

        while(true) {
            int var10;
            if (var9 < 13) {
                if (var7 <= 0 || var7 >= 127) {
                    return false;
                }

                var10 = world.getBlockId(var6, var7, var8);
                if (var10 != 0) {
                    if (!method_759(var10, world, var6, var7, var8, true)) {
                        return false;
                    }

                    if (Block.BLOCKS[var10].getPistonBehavior() != 1) {
                        if (var9 == 12) {
                            return false;
                        }

                        var6 += PistonConstants.HEAD_OFFSET_X[l];
                        var7 += PistonConstants.HEAD_OFFSET_Y[l];
                        var8 += PistonConstants.HEAD_OFFSET_Z[l];
                        ++var9;
                        continue;
                    }

                    Block.BLOCKS[var10].dropStacks(world, var6, var7, var8, world.getBlockMeta(var6, var7, var8));
                    world.setBlock(var6, var7, var8, 0);
                }
            }

            while(var6 != i || var7 != j || var8 != k) {
                var9 = var6 - PistonConstants.HEAD_OFFSET_X[l];
                var10 = var7 - PistonConstants.HEAD_OFFSET_Y[l];
                int var11 = var8 - PistonConstants.HEAD_OFFSET_Z[l];
                int var12 = world.getBlockId(var9, var10, var11);
                int var13 = world.getBlockMeta(var9, var10, var11);
                if (var12 == this.id && var9 == i && var10 == j && var11 == k) {
                    world.method_154(var6, var7, var8, Block.MOVING_PISTON.id, l | (this.sticky ? 8 : 0));
                    world.method_157(var6, var7, var8, PistonExtensionBlock.method_1533(Block.PISTON_HEAD.id, l | (this.sticky ? 8 : 0), l, true, false));
                } else {
                    world.method_154(var6, var7, var8, Block.MOVING_PISTON.id, var13);
                    world.method_157(var6, var7, var8, PistonExtensionBlock.method_1533(var12, var13, l, true, false));
                }

                var6 = var9;
                var7 = var10;
                var8 = var11;
            }

            return true;
        }
    }
    private static boolean method_759(int i, World world, int j, int k, int l, boolean bl) {
        if (i == Block.OBSIDIAN.id) {
            return false;
        } else {
            if (i != Block.PISTON.id && i != Block.STICKY_PISTON.id) {
                if (Block.BLOCKS[i].getHardness() == -1.0F) {
                    return false;
                }

                if (Block.BLOCKS[i].getPistonBehavior() == 2) {
                    return false;
                }

                if (!bl && Block.BLOCKS[i].getPistonBehavior() == 1) {
                    return false;
                }
            } else if (true) {
                return false;
            }

            BlockEntity var6 = world.getBlockEntity(j, k, l);
            return var6 == null;
        }
    }
}

