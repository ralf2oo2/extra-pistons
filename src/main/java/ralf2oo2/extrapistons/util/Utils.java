package ralf2oo2.extrapistons.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.block.Block;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtString;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.State;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.Property;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;


public class Utils {
    public static BlockState toBlockState(NbtCompound compound){
        if(!compound.contains("Id")){
            return null;
        }
        else{
            Block block = Block.BLOCKS[compound.getInt("Id")];
            BlockState blockState = block.getDefaultState();
            if(compound.contains("Properies")){
                NbtCompound nbtCompound = compound.getCompound("Properties");
                StateManager<Block, BlockState> stateManager = block.getStateManager();
                Iterator iterator = nbtCompound.values().iterator();

                while(iterator.hasNext()){
                    String string = ((NbtString)iterator.next()).getKey();
                    Property<?> property = stateManager.getProperty(string);
                    if(property != null){
                        blockState = withProperty(blockState, property, string, nbtCompound, compound);
                    }
                }
            }
            return blockState;
        }
    }
    public static NbtCompound fromBlockState(BlockState state){
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putInt("Id", state.getBlock().id);

        ImmutableMap<Property<?>, Comparable<?>> immutableMap = state.getEntries();
        if(!immutableMap.isEmpty()){
            NbtCompound properties = new NbtCompound();
            UnmodifiableIterator iterator = immutableMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Property<?>, Comparable<?>> entry = (Map.Entry)iterator.next();
                Property<?> property = entry.getKey();
                properties.putString(property.getName(), nameValue(property, entry.getValue()));
            }
            nbtCompound.put("Properties", properties);
        }
        return nbtCompound;
    }
    private static <T extends Comparable<T>> String nameValue(Property<T> property, Comparable<?> value) {
        return property.name((T) value);
    }

    private static <S extends State<?, S>, T extends Comparable<T>> S withProperty(S state, Property<T> property, String key, NbtCompound properties, NbtCompound root) {
        Optional<T> optional = property.parse(properties.getString(key));
        if (optional.isPresent()) {
            return (S) state.with(property, optional.get());
        } else {
            return state;
        }
    }

}
