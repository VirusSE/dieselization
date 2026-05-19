package mods.dieselization.thermos.component;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.api.thermos.ThermalMath;

/**
 * Adds a fixed heat power to the medium, for example lava, a furnace, or a reactor.
 */
public final class HeatSourceComponent extends AbstractThermalComponent {
    private double heatWatts;
    private double efficiency;

    public HeatSourceComponent(String id, double initialTemperatureCelsius, double heatWatts, double efficiency) {
        super(id, initialTemperatureCelsius);
        this.heatWatts = Math.max(0.0, heatWatts);
        this.efficiency = ThermalMath.clamp(efficiency, 0.0, 1.0);
    }

    @Override
    public void simulate(HeatMedium medium, double dtSeconds) {
        passFlowToOutlet();
        double added = heatWatts * efficiency;
        double delta = ThermalMath.deltaTemperatureFromHeat(
            added,
            inlet().massFlowKgPerSecond(),
            medium.specificHeat()
        );
        outlet().setTemperatureCelsius(inlet().temperatureCelsius() + delta);
    }

    public double heatWatts() {
        return heatWatts;
    }

    public void setHeatWatts(double heatWatts) {
        this.heatWatts = Math.max(0.0, heatWatts);
    }

    public double efficiency() {
        return efficiency;
    }

    public void setEfficiency(double efficiency) {
        this.efficiency = ThermalMath.clamp(efficiency, 0.0, 1.0);
    }
}
