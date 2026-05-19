package mods.dieselization;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class DieselizationConfig {

    public static final ModConfigSpec CLIENT_SPEC, COMMON_SPEC, SERVER_SPEC;

    static {
        ModConfigSpec.Builder clientBuilder =  new ModConfigSpec.Builder();
        Client.init(clientBuilder);
        CLIENT_SPEC = clientBuilder.build();

        ModConfigSpec.Builder commonBuilder =  new ModConfigSpec.Builder();
        Common.init(commonBuilder);
        COMMON_SPEC = commonBuilder.build();
        ModConfigSpec.Builder serverBuilder =  new ModConfigSpec.Builder();
        Server.init(serverBuilder);
        SERVER_SPEC = serverBuilder.build();


    }





    public static class Common {
        private static void init(ModConfigSpec.Builder builder) {
            builder.push("common");

            builder.pop();
        }
        private Common() {}

    }

    public static class Client {
        private static void  init(ModConfigSpec.Builder builder) {
            builder.push("client");

            builder.pop();
        }
        private Client() {}
    }

    public static class Server {
        public static ModConfigSpec.BooleanValue ENABLE_DIESEL_GENERATOR;
        public static ModConfigSpec.IntValue DIESEL_GENERATOR_FE_PER_TICK;
        public static ModConfigSpec.IntValue DIESEL_GENERATOR_FUEL_USAGE;
        public static ModConfigSpec.IntValue MAX_DIESEL_STORAGE;

        private static void  init(ModConfigSpec.Builder builder) {
            builder.push("server");
            ENABLE_DIESEL_GENERATOR = builder
                    .comment("Aktiviert oder deaktiviert den Dieselgenerator.")
                    .define("enableDieselGenerator", true);

            DIESEL_GENERATOR_FE_PER_TICK = builder
                    .comment("Wie viel Energy der Dieselgenerator pro Tick erzeugt.")
                    .defineInRange("dieselGeneratorFePerTick", 80, 1, 10000);

            DIESEL_GENERATOR_FUEL_USAGE = builder
                    .comment("Wie viel Diesel pro Tick verbraucht wird.")
                    .defineInRange("dieselGeneratorFuelUsage", 1, 1, 1000);

            MAX_DIESEL_STORAGE = builder
                    .comment("Maximale Dieselmenge, die der Generator speichern kann.")
                    .defineInRange("maxDieselStorage", 16000, 1000, 1_000_000);
            builder.pop();
        }

        private Server() {}
    }

    private DieselizationConfig() {}
}
