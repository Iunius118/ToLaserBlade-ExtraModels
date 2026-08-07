package com.github.iunius118.tolaserblade.exmodels;

import com.github.iunius118.tolaserblade.exmodels.data.ModRecipeProvider;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod(TLBExtraModels.MOD_ID)
public class TLBExtraModels {
    public static final String MOD_ID = "tlbexmodels";

    public TLBExtraModels(IEventBus modEventBus, ModContainer modContainer) {
        // Register mod event listeners
        modEventBus.addListener(this::gatherData);
    }

    private void gatherData(final GatherDataEvent.Client event) {
        // Data
        event.createProvider(ModRecipeProvider.Runner::new);
    }
}
