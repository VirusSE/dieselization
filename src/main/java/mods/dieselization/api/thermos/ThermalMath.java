package mods.dieselization.api.thermos;

public class ThermalMath {
    private ThermalMath() {
    }

    public static double deltaTemperatureFromHeat(double heatWatts, double massFlowKgPerSecond, double specificHeat) {
        if (massFlowKgPerSecond <= 0.0) {
            return 0.0;
        }
        return heatWatts / (massFlowKgPerSecond * specificHeat);
    }

    public static double approach(double current, double target, double factor) {
        double clamped = clamp(factor, 0.0, 1.0);
        return current + (target - current) * clamped;
    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
