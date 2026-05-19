package thermos;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.thermos.ThermalNetwork;
import mods.dieselization.thermos.component.HeatSinkComponent;
import mods.dieselization.thermos.component.HeatSourceComponent;
import mods.dieselization.thermos.component.ThermalPipeComponent;
import mods.dieselization.thermos.component.ThermalStorageComponent;

import java.util.List;

public final class ThermalCycleDemo {
    private static final int DEFAULT_TICKS = 200;
    private static final int DEFAULT_PRINT_EVERY = 20;
    private static final double DEFAULT_DT_SECONDS = 1.0 / 20.0;
    private static final double DEFAULT_MASS_FLOW = 0.25;
    private static final double DEFAULT_INITIAL_TEMPERATURE = 25.0;
    private static final NetworkPreset DEFAULT_NETWORK = NetworkPreset.LAVA;

    private ThermalCycleDemo() {
    }

    public static void main(String[] args) {
        SimulationOptions options = SimulationOptions.parse(args);
        if (options.help()) {
            printUsage();
            return;
        }

        List<NamedNetwork> networks = createNetworks(
                options.networkPreset(),
                options.initialTemperatureCelsius(),
                options.massFlowKgPerSecond()
        );
        printHeader(options);
        printNetworkStates(0, networks);

        for (int tick = 1; tick <= options.ticks(); tick++) {
            for (NamedNetwork network : networks) {
                network.network().simulate(options.dtSeconds());
            }
            if (tick % options.printEveryTicks() == 0) {
                printNetworkStates(tick, networks);
            }
        }
    }

    public static ThermalNetwork createExampleNetwork() {
        return createExampleNetwork(DEFAULT_INITIAL_TEMPERATURE, DEFAULT_MASS_FLOW);
    }

    public static ThermalNetwork createExampleNetwork(double initialTemperatureCelsius, double massFlowKgPerSecond) {
        ThermalNetwork network = new ThermalNetwork(HeatMedium.WATER, massFlowKgPerSecond);

        network.add(new HeatSourceComponent("lava_heater", initialTemperatureCelsius, 12_000.0, 0.85));
        network.add(new ThermalPipeComponent("hot_pipe", initialTemperatureCelsius, 20.0, 0.02));
        network.add(new ThermalStorageComponent("buffer_tank", initialTemperatureCelsius, 80_000.0, 0.25));
        network.add(new HeatSinkComponent("radiator", initialTemperatureCelsius, 7_500.0, 10.0));
        network.add(new ThermalPipeComponent("cold_pipe", initialTemperatureCelsius, 20.0, 0.03));

        return network;
    }

    public static ThermalNetwork createDieselEngineCoolingNetwork() {
        return createDieselEngineCoolingNetwork(DEFAULT_INITIAL_TEMPERATURE, DEFAULT_MASS_FLOW);
    }

    public static ThermalNetwork createDieselEngineCoolingNetwork(
            double initialTemperatureCelsius,
            double massFlowKgPerSecond
    ) {
        ThermalNetwork network = new ThermalNetwork(HeatMedium.WATER, massFlowKgPerSecond);

        network.add(new HeatSourceComponent("diesel_engine", initialTemperatureCelsius, 42_000.0, 0.72));
        network.add(new ThermalPipeComponent("engine_outlet_pipe", initialTemperatureCelsius, 32.0, 0.01));
        network.add(new ThermalStorageComponent("coolant_jacket", initialTemperatureCelsius, 140_000.0, 0.35));
        network.add(new ThermalPipeComponent("radiator_inlet_pipe", initialTemperatureCelsius, 28.0, 0.015));
        network.add(new HeatSinkComponent("honeycomb_radiator", initialTemperatureCelsius, 31_000.0, 22.0));
        network.add(new ThermalPipeComponent("return_pipe", initialTemperatureCelsius, 24.0, 0.02));

        return network;
    }

    private static List<NamedNetwork> createNetworks(
            NetworkPreset preset,
            double initialTemperatureCelsius,
            double massFlowKgPerSecond
    ) {
        return switch (preset) {
            case LAVA -> List.of(new NamedNetwork(
                    "lava",
                    createExampleNetwork(initialTemperatureCelsius, massFlowKgPerSecond)
            ));
            case DIESEL -> List.of(new NamedNetwork(
                    "diesel",
                    createDieselEngineCoolingNetwork(initialTemperatureCelsius, massFlowKgPerSecond)
            ));
            case ALL -> List.of(
                    new NamedNetwork("lava", createExampleNetwork(initialTemperatureCelsius, massFlowKgPerSecond)),
                    new NamedNetwork("diesel", createDieselEngineCoolingNetwork(initialTemperatureCelsius, massFlowKgPerSecond))
            );
        };
    }

    private static void printHeader(SimulationOptions options) {
        System.out.println("Thermocycle simulation");
        System.out.printf(
                "network=%s, ticks=%d, dt=%.4fs, massFlow=%.3fkg/s, initialTemperature=%.2fC%n",
                options.networkPreset().argumentName(),
                options.ticks(),
                options.dtSeconds(),
                options.massFlowKgPerSecond(),
                options.initialTemperatureCelsius()
        );
        System.out.println();
    }

    private static void printNetworkStates(int tick, List<NamedNetwork> networks) {
        System.out.printf("tick %d%n", tick);
        for (NamedNetwork network : networks) {
            System.out.printf("  network: %s%n", network.name());
            for (var component : network.network().components()) {
                System.out.printf(
                        "    %-20s in=%8.2fC out=%8.2fC flow=%6.3fkg/s%n",
                        component.id(),
                        component.inlet().temperatureCelsius(),
                        component.outlet().temperatureCelsius(),
                        component.outlet().massFlowKgPerSecond()
                );
            }
        }
        System.out.println();
    }

    private static void printUsage() {
        System.out.println("Usage:");
        System.out.println("  java dev.example.thermocycle.demo.ThermalCycleDemo [options]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --ticks <int>                 Number of simulation ticks. Default: " + DEFAULT_TICKS);
        System.out.println("  --dt <double>                 Seconds per tick. Default: " + DEFAULT_DT_SECONDS);
        System.out.println("  --mass-flow <double>          Mass flow in kg/s. Default: " + DEFAULT_MASS_FLOW);
        System.out.println("  --initial-temp <double>       Initial temperature in C. Default: " + DEFAULT_INITIAL_TEMPERATURE);
        System.out.println("  --print-every <int>           Print interval in ticks. Default: " + DEFAULT_PRINT_EVERY);
        System.out.println("  --network <lava|diesel|all>   Network preset. Default: " + DEFAULT_NETWORK.argumentName());
        System.out.println("  --help                        Show this help text.");
    }

    private record NamedNetwork(String name, ThermalNetwork network) {
    }

    private record SimulationOptions(
            int ticks,
            double dtSeconds,
            double massFlowKgPerSecond,
            double initialTemperatureCelsius,
            int printEveryTicks,
            NetworkPreset networkPreset,
            boolean help
    ) {
        static SimulationOptions parse(String[] args) {
            int ticks = DEFAULT_TICKS;
            double dtSeconds = DEFAULT_DT_SECONDS;
            double massFlow = DEFAULT_MASS_FLOW;
            double initialTemperature = DEFAULT_INITIAL_TEMPERATURE;
            int printEvery = DEFAULT_PRINT_EVERY;
            NetworkPreset networkPreset = DEFAULT_NETWORK;

            for (int i = 0; i < args.length; i++) {
                String arg = args[i];
                switch (arg) {
                    case "--help" -> {
                        return new SimulationOptions(
                                ticks,
                                dtSeconds,
                                massFlow,
                                initialTemperature,
                                printEvery,
                                networkPreset,
                                true
                        );
                    }
                    case "--ticks" -> ticks = parsePositiveInt(arg, nextValue(args, ++i, arg));
                    case "--dt" -> dtSeconds = parsePositiveDouble(arg, nextValue(args, ++i, arg));
                    case "--mass-flow" -> massFlow = parsePositiveDouble(arg, nextValue(args, ++i, arg));
                    case "--initial-temp" -> initialTemperature = parseDouble(arg, nextValue(args, ++i, arg));
                    case "--print-every" -> printEvery = parsePositiveInt(arg, nextValue(args, ++i, arg));
                    case "--network" -> networkPreset = NetworkPreset.parse(nextValue(args, ++i, arg));
                    default -> throw new IllegalArgumentException("Unknown option: " + arg);
                }
            }

            return new SimulationOptions(ticks, dtSeconds, massFlow, initialTemperature, printEvery, networkPreset, false);
        }

        private static String nextValue(String[] args, int index, String option) {
            if (index >= args.length) {
                throw new IllegalArgumentException("Missing value for " + option);
            }
            return args[index];
        }

        private static int parsePositiveInt(String option, String value) {
            int parsed = Integer.parseInt(value);
            if (parsed <= 0) {
                throw new IllegalArgumentException(option + " must be positive");
            }
            return parsed;
        }

        private static double parsePositiveDouble(String option, String value) {
            double parsed = Double.parseDouble(value);
            if (parsed <= 0.0) {
                throw new IllegalArgumentException(option + " must be positive");
            }
            return parsed;
        }

        private static double parseDouble(String option, String value) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException(option + " must be a number", exception);
            }
        }
    }

    private enum NetworkPreset {
        LAVA("lava"),
        DIESEL("diesel"),
        ALL("all");

        private final String argumentName;

        NetworkPreset(String argumentName) {
            this.argumentName = argumentName;
        }

        String argumentName() {
            return argumentName;
        }

        static NetworkPreset parse(String value) {
            for (NetworkPreset preset : values()) {
                if (preset.argumentName.equalsIgnoreCase(value)) {
                    return preset;
                }
            }
            throw new IllegalArgumentException("Unknown network preset: " + value);
        }
    }
}
