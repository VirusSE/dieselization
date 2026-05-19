package mods.dieselization.thermos.component;

import mods.dieselization.api.thermos.HeatPort;
import mods.dieselization.api.thermos.ThermalComponent;

public abstract class AbstractThermalComponent implements ThermalComponent {
    private final String id;
    private final HeatPort inlet;
    private final HeatPort outlet;

    protected AbstractThermalComponent(String id, double initialTemperatureCelsius) {
        this.id = id;
        this.inlet = new HeatPort(initialTemperatureCelsius, 0.0);
        this.outlet = new HeatPort(initialTemperatureCelsius, 0.0);
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public HeatPort inlet() {
        return inlet;
    }

    @Override
    public HeatPort outlet() {
        return outlet;
    }

    protected void passFlowToOutlet() {
        outlet.setMassFlowKgPerSecond(inlet.massFlowKgPerSecond());
    }
}
