package mods.dieselization.thermos.component;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.api.thermos.ThermalMath;

/**
 * A heat buffer/tank. It exchanges heat with the passing medium and stores energy internally.
 */
public final class ThermalStorageComponent extends AbstractThermalComponent {
    private final double heatCapacityJoulesPerKelvin;
    private double storedTemperatureCelsius;
    private double exchangeFactor;

    public ThermalStorageComponent(
        String id,
        double initialTemperatureCelsius,
        double heatCapacityJoulesPerKelvin,
        double exchangeFactor
    ) {
        super(id, initialTemperatureCelsius);
        this.storedTemperatureCelsius = initialTemperatureCelsius;
        this.heatCapacityJoulesPerKelvin = Math.max(1.0, heatCapacityJoulesPerKelvin);
        this.exchangeFactor = ThermalMath.clamp(exchangeFactor, 0.0, 1.0);
    }

    @Override
    public void simulate(HeatMedium medium, double dtSeconds) {
        passFlowToOutlet();

        double inletTemperature = inlet().temperatureCelsius();
        double outletTemperature = ThermalMath.approach(inletTemperature, storedTemperatureCelsius, exchangeFactor);
        double heatMovedToFluid = inlet().massFlowKgPerSecond()
            * medium.specificHeat()
            * (outletTemperature - inletTemperature)
            * Math.max(0.0, dtSeconds);

        storedTemperatureCelsius -= heatMovedToFluid / heatCapacityJoulesPerKelvin;
        outlet().setTemperatureCelsius(outletTemperature);
    }

    public double storedTemperatureCelsius() {
        return storedTemperatureCelsius;
    }
}
