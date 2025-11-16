package yuureiki.ender_smelting.inventory;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.FurnaceScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import yuureiki.ender_smelting.EnderSmelting;
import yuureiki.ender_smelting.interfaces.EnderFurnaceInventoryInterface;

import java.util.List;

public abstract class AbstractEnderFurnaceInventory implements RecipeInputProvider, RecipeUnlocker, Inventory, EnderFurnaceInventoryInterface {
    private final int size = size();
    private World current_world;

    @Nullable
    private World activeBlockWorld;
    @Nullable
    private BlockPos activeBlockPos;

    protected static final int INPUT_SLOT_INDEX = 0;
    protected static final int FUEL_SLOT_INDEX = 1;
    protected static final int OUTPUT_SLOT_INDEX = 2;
    public static final int BURN_TIME_PROPERTY_INDEX = 0;
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    public static final int FUEL_TIME_PROPERTY_INDEX = 1;
    public static final int COOK_TIME_PROPERTY_INDEX = 2;
    public static final int COOK_TIME_TOTAL_PROPERTY_INDEX = 3;
    public static final int PROPERTY_COUNT = 4;
    public static final int DEFAULT_COOK_TIME = 200;
    public static final int field_31295 = 2;
    private static final int FINAL_GET_SIZE = 4;
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap<>();
    private final RecipeManager.MatchGetter<Inventory, ? extends AbstractCookingRecipe> matchGetter;

    protected AbstractEnderFurnaceInventory(RecipeType<? extends AbstractCookingRecipe> recipeType) {
        this.matchGetter = RecipeManager.createCachedMatchGetter(recipeType);
    }

    /// PropertyDelegate for ScreenHandlers.
    public final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> burnTime;
                case 1 -> fuelTime;
                case 2 -> cookTime;
                case 3 -> cookTimeTotal;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index){
                case 0: burnTime = value; break;
                case 1: fuelTime = value; break;
                case 2: cookTime = value; break;
                case 3: cookTimeTotal = value; break;
                default: break;
            }
        }

        @Override
        public int size() { return FINAL_GET_SIZE; }
    };

    /// Fuel flammability check.
    private static boolean isNonFlammableWood(Item item){
        return item.getRegistryEntry().isIn(ItemTags.NON_FLAMMABLE_WOOD);
    }

    // Various stuff related to smelting and recipes
    public void tick() {
        if (current_world == null) return;

        boolean bl = isBurning();
        boolean bl2 = false;
        if (isBurning()) {
            burnTime--;
        }

        ItemStack itemStack = inventory.get(1);
        boolean bl3 = !inventory.get(0).isEmpty();
        boolean bl4 = !itemStack.isEmpty();
        if (isBurning() || bl4 && bl3) {
            Recipe<?> recipe;
            if (bl3) {
                recipe = matchGetter.getFirstMatch(this, current_world).orElse(null);
            } else {
                recipe = null;
            }

            int i = getMaxCountPerStack();
            if (!isBurning() && canAcceptRecipeOutput(current_world.getRegistryManager(), recipe, inventory, i)) {
                burnTime = getFuelTime(itemStack);
                fuelTime = burnTime;
                if (isBurning()) {
                    bl2 = true;
                    if (bl4) {
                        Item item = itemStack.getItem();
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            Item item2 = item.getRecipeRemainder();
                            inventory.set(1, item2 == null ? ItemStack.EMPTY : new ItemStack(item2));
                        }
                    }
                }
            }

            if (isBurning() && canAcceptRecipeOutput(current_world.getRegistryManager(), recipe, inventory, i)) {
                cookTime++;
                if (cookTime == cookTimeTotal) {
                    cookTime = 0;
                    cookTimeTotal = getCookTime();
                    if (craftRecipe(current_world.getRegistryManager(), recipe, inventory, i)) {
                        setLastRecipe(recipe);
                    }

                    bl2 = true;
                }
            } else {
                cookTime = 0;
            }
        } else if (!isBurning() && cookTime > 0) {
            cookTime = MathHelper.clamp(cookTime - 2, 0, cookTimeTotal);
        }

        if (bl != isBurning()) {
            bl2 = true;
            // This part announces that the block has stopped smelting.
            // TODO: Maybe best to do special client-side rendering.
            // Ender Furnaces can be lit 24/7 like Ender Chests.
            // Just maybe not as much compared to normal Furnaces.
            //state = state.with(AbstractFurnaceBlock.LIT, isBurning());
            //world.setBlockState(pos, state, Block.NOTIFY_ALL);
        }

        if (bl2) {
            // TODO: Saving the inventory in NBT anyways so no reason to mark dirty, frankly.
            //markDirty(world, pos, state);
        }
    }

    /// Check if the recipe output is proper.
    private boolean canAcceptRecipeOutput(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (!slots.get(0).isEmpty() && recipe != null) {
            ItemStack itemStack = recipe.getOutput(registryManager);
            if (itemStack.isEmpty()) {
                return false;
            } else {
                ItemStack itemStack2 = slots.get(2);
                if (itemStack2.isEmpty()) {
                    return true;
                } else if (!ItemStack.areItemsEqual(itemStack2, itemStack)) {
                    return false;
                } else {
                    return itemStack2.getCount() < count && itemStack2.getCount() < itemStack2.getMaxCount() ?
                            true : itemStack2.getCount() < itemStack.getMaxCount();
                }
            }
        } else {
            return false;
        }
    }

    /// Commence crafting.
    private boolean craftRecipe(DynamicRegistryManager registryManager, @Nullable Recipe<?> recipe, DefaultedList<ItemStack> slots, int count) {
        if (recipe != null && canAcceptRecipeOutput(registryManager, recipe, slots, count)) {
            ItemStack itemStack = slots.get(0);
            ItemStack itemStack2 = recipe.getOutput(registryManager);
            ItemStack itemStack3 = slots.get(2);
            if (itemStack3.isEmpty()) {
                slots.set(2, itemStack2.copy());
            } else if (itemStack3.isOf(itemStack2.getItem())) {
                itemStack3.increment(1);
            }

            if (itemStack.isOf(Blocks.WET_SPONGE.asItem()) && !slots.get(1).isEmpty() && slots.get(1).isOf(Items.BUCKET)) {
                slots.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemStack.decrement(1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setLastRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.getId();
            this.recipesUsed.addTo(identifier, 1);
        }
    }

    @Override
    public @Nullable Recipe<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player, List<ItemStack> ingredients) {}

    public void dropExperienceForRecipesUsed(ServerPlayerEntity player) {
        List<Recipe<?>> list = this.getRecipesUsedAndDropExperience(player.getServerWorld(), player.getPos());
        player.unlockRecipes(list);

        for (Recipe<?> recipe : list) {
            if (recipe != null) {
                player.onRecipeCrafted(recipe, this.inventory);
            }
        }

        this.recipesUsed.clear();
    }

    public List<Recipe<?>> getRecipesUsedAndDropExperience(ServerWorld world, Vec3d pos) {
        List<Recipe<?>> list = Lists.newArrayList();

        for (Object2IntMap.Entry<Identifier> entry : this.recipesUsed.object2IntEntrySet()) {
            world.getRecipeManager().get(entry.getKey()).ifPresent(recipe -> {
                list.add(recipe);
                dropExperience(world, pos, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
            });
        }

        return list;
    }

    private static void dropExperience(ServerWorld world, Vec3d pos, int multiplier, float experience) {
        int i = MathHelper.floor(multiplier * experience);
        float f = MathHelper.fractionalPart(multiplier * experience);
        if (f != 0.0F && Math.random() < f) {
            i++;
        }

        ExperienceOrbEntity.spawn(world, pos, i);
    }

    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addInput(itemStack);
        }
    }

    // Inventory slot management
    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
        }
    }

    private int getCookTime() {
        if (current_world == null) return 200;
        return matchGetter.getFirstMatch(this, current_world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    private boolean isBurning() {
        return burnTime > 0;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : inventory) {
            if (!itemStack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(inventory, slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(inventory, slot, amount);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = inventory.get(slot);
        boolean bl = !stack.isEmpty() && ItemStack.canCombine(itemStack, stack);
        inventory.set(slot, stack);
        if (stack.getCount() > getMaxCountPerStack()) {
            stack.setCount(getMaxCountPerStack());
        }

        if (slot == 0 && !bl) {
            cookTimeTotal = getCookTime();
            cookTime = 0;
            //markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {

        return true;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        } else if (slot != 1) {
            return true;
        } else {
            ItemStack itemStack = this.inventory.get(1);
            return AbstractFurnaceBlockEntity.canUseAsFuel(stack) || stack.isOf(Items.BUCKET) && !itemStack.isOf(Items.BUCKET);
        }
    }

    @Override
    public void clear() { inventory.clear(); }

    // Active Block
    public void currentBlockBroken(World world, BlockPos pos){
        EnderSmelting.LOGGER.info("Checking if current block got broken...");
        if (activeBlockWorld == world && activeBlockPos == pos){
            EnderSmelting.LOGGER.info("Current block got broken.");
            activeBlockWorld = null;
            activeBlockPos = null;
        }
    }

    public void setActiveBlock(World world, BlockPos pos){
        activeBlockWorld = world;
        activeBlockPos = pos;
    }

    // NBT data saving and loading
    public void readNbtList(NbtList nbtList){
        EnderSmelting.LOGGER.info("Reading Furnace data...");

        for (int i = 0; i < this.size(); i++) {
            this.setStack(i, ItemStack.EMPTY);
        }

        for (int i = 0; i < nbtList.size() - 1; i++) {
            NbtCompound nbtCompound = nbtList.getCompound(i);
            int j = nbtCompound.getByte("Slot") & 255;
            if (j >= 0 && j < this.size()) {
                this.setStack(j, ItemStack.fromNbt(nbtCompound));
            }
        }

        NbtCompound smeltCompound = nbtList.getCompound(nbtList.size());
        burnTime = smeltCompound.getInt("BurnTime");
        fuelTime = smeltCompound.getInt("FuelTime");
        cookTime = smeltCompound.getInt("CookTime");
        cookTimeTotal = smeltCompound.getInt("CookTimeTotal");
    }

    public NbtList toNbtList(){
        NbtList nbtList = new NbtList();

        for (int i = 0; i < this.size(); i++) {
            ItemStack itemStack = this.getStack(i);
            if (!itemStack.isEmpty()) {
                NbtCompound nbtCompound = new NbtCompound();
                nbtCompound.putByte("Slot", (byte)i);
                itemStack.writeNbt(nbtCompound);
                nbtList.add(nbtCompound);
            }
        }

        NbtCompound smeltCompound = new NbtCompound();
        smeltCompound.putInt("BurnTime", burnTime);
        smeltCompound.putInt("FuelTime", fuelTime);
        smeltCompound.putInt("CookTime", cookTime);
        smeltCompound.putInt("CookTimeTotal", cookTimeTotal);
        nbtList.add(smeltCompound);

        EnderSmelting.LOGGER.info("Now saving Furnace data!");

        return nbtList;
    }

    // Added for reasons unknown.
    @Override
    public void markDirty() {

    }

    @Override
    public int size() {
        return FINAL_GET_SIZE - 1;
    }
}
