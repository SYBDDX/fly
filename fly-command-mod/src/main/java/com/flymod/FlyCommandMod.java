package com.flymod;

import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(FlyCommandMod.MOD_ID)
public class FlyCommandMod {
    public static final String MOD_ID = "flycommand";

    public FlyCommandMod() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                Commands.literal("fly")
                        .then(Commands.argument("enabled", BoolArgumentType.bool())
                                .executes(context -> {
                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                    boolean enabled = BoolArgumentType.getBool(context, "enabled");

                                    player.getAbilities().mayfly = enabled;
                                    player.getAbilities().flying = enabled;
                                    player.onUpdateAbilities();

                                    context.getSource().sendSuccess(
                                            () -> Component.literal(enabled
                                                    ? "Flight mode enabled"
                                                    : "Flight mode disabled"),
                                            false
                                    );
                                    return 1;
                                })
                        )
        );
    }
}
