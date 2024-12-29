package net.svisvi.jigsawpp.client.screen;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.svisvi.jigsawpp.JigsawPpMod;
import net.svisvi.jigsawpp.client.screen.purgen_factory.PurgenFactoryMenu;
import net.svisvi.jigsawpp.client.screen.space_lift.SpaceLiftMenu;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTRY = 
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, JigsawPpMod.MODID);

    public static final RegistryObject<MenuType<PurgenFactoryMenu>> PURGEN_FACTORY_MENU =
            registerMenuTypes("purgen_factory_menu", PurgenFactoryMenu::new);
    public static final RegistryObject<MenuType<SpaceLiftMenu>> SPACE_LIFT_MENU =
            registerMenuTypes("space_lift_menu", SpaceLiftMenu::new);

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuTypes(String name, IContainerFactory<T> factory) {
        return REGISTRY.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        REGISTRY.register(eventBus);
    }
}
