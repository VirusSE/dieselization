package mods.dieselization.api.thermos;

/**
 * Physical constants of the circulating medium.
 *
 * Units:
 * - specificHeat: J / (kg*K)
 * - density: kg / m^3
 */
public record HeatMedium(String id, double specificHeat, double density) {
    public static final HeatMedium WATER = new HeatMedium("water", 4184.0, 997.0);
    public static final HeatMedium STEAM = new HeatMedium("steam", 2010.0, 0.6);

    public HeatMedium {
        if (specificHeat <= 0.0) {
            throw new IllegalArgumentException("specificHeat must be positive");
        }
        if (density <= 0.0) {
            throw new IllegalArgumentException("density must be positive");
        }
    }
}

