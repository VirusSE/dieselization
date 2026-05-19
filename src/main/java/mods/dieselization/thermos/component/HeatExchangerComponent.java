package mods.dieselization.thermos.component;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.api.thermos.ThermalMath;

/**
 * Exchanges heat with a secondary temperature source.
 */
public final class HeatExchangerComponent extends AbstractThermalComponent {
    private double secondaryTemperatureCelsius;
    private double transferFactor;

    public HeatExchangerComponent(
        String id,
        double initialTemperatureCelsius,
        double secondaryTemperatureCelsius,
        double transferFactor
    ) {
        super(id, initialTemperatureCelsius);
        this.secondaryTemperatureCelsius = secondaryTemperatureCelsius;
        this.transferFactor = ThermalMath.clamp(transferFactor, 0.0, 1.0);
    }

    @Override
    public void simulate(HeatMedium medium, double dtSeconds) {
        passFlowToOutlet();
        outlet().setTemperatureCelsius(ThermalMath.approach(
            inlet().temperatureCelsius(),
            secondaryTemperatureCelsius,
            transferFactor
        ));
    }
}
