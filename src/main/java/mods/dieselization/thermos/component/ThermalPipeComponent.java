package mods.dieselization.thermos.component;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.api.thermos.ThermalMath;

/**
 * Pipe with simple heat loss toward the ambient world temperature.
 */
public final class ThermalPipeComponent extends AbstractThermalComponent {
    private double ambientTemperatureCelsius;
    private double lossFactorPerSecond;

    public ThermalPipeComponent(
        String id,
        double initialTemperatureCelsius,
        double ambientTemperatureCelsius,
        double lossFactorPerSecond
    ) {
        super(id, initialTemperatureCelsius);
        this.ambientTemperatureCelsius = ambientTemperatureCelsius;
        this.lossFactorPerSecond = Math.max(0.0, lossFactorPerSecond);
    }

    @Override
    public void simulate(HeatMedium medium, double dtSeconds) {
        passFlowToOutlet();
        double factor = 1.0 - Math.exp(-lossFactorPerSecond * Math.max(0.0, dtSeconds));
        outlet().setTemperatureCelsius(ThermalMath.approach(
            inlet().temperatureCelsius(),
            ambientTemperatureCelsius,
            factor
        ));
    }
}
