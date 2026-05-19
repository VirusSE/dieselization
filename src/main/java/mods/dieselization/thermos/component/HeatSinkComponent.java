package mods.dieselization.thermos.component;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.api.thermos.ThermalMath;

/**
 * Removes heat power from the medium, for example a radiator, cooler, or machine load.
 */
public final class HeatSinkComponent extends AbstractThermalComponent {
    private double coolingWatts;
    private double minimumTemperatureCelsius;

    public HeatSinkComponent(
        String id,
        double initialTemperatureCelsius,
        double coolingWatts,
        double minimumTemperatureCelsius
    ) {
        super(id, initialTemperatureCelsius);
        this.coolingWatts = Math.max(0.0, coolingWatts);
        this.minimumTemperatureCelsius = minimumTemperatureCelsius;
    }

    @Override
    public void simulate(HeatMedium medium, double dtSeconds) {
        passFlowToOutlet();
        double delta = ThermalMath.deltaTemperatureFromHeat(
            coolingWatts,
            inlet().massFlowKgPerSecond(),
            medium.specificHeat()
        );
        outlet().setTemperatureCelsius(Math.max(minimumTemperatureCelsius, inlet().temperatureCelsius() - delta));
    }
}
