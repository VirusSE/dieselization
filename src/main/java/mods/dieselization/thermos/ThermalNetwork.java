package mods.dieselization.thermos;

import mods.dieselization.api.thermos.HeatMedium;
import mods.dieselization.api.thermos.ThermalComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ordered closed-loop solver.
 *
 * For a more complex graph, split the graph into loops or replace this class
 * with a node/edge solver. For a Minecraft build with pipe chains, this class
 * is intentionally small and predictable.
 */
public final class ThermalNetwork {
    private final List<ThermalComponent> components = new ArrayList<>();
    private HeatMedium medium;
    private double massFlowKgPerSecond;

    public ThermalNetwork(HeatMedium medium, double massFlowKgPerSecond) {
        this.medium = medium;
        this.massFlowKgPerSecond = Math.max(0.0, massFlowKgPerSecond);
    }

    public void add(ThermalComponent component) {
        components.add(component);
    }

    public List<ThermalComponent> components() {
        return Collections.unmodifiableList(components);
    }

    public void simulate(double dtSeconds) {
        if (components.isEmpty()) {
            return;
        }

        ThermalComponent previous = components.get(components.size() - 1);
        for (ThermalComponent component : components) {
            component.inlet().copyFrom(previous.outlet());
            component.inlet().setMassFlowKgPerSecond(massFlowKgPerSecond);
            component.simulate(medium, dtSeconds);
            previous = component;
        }
    }

    public HeatMedium medium() {
        return medium;
    }

    public void setMedium(HeatMedium medium) {
        this.medium = medium;
    }

    public double massFlowKgPerSecond() {
        return massFlowKgPerSecond;
    }

    public void setMassFlowKgPerSecond(double massFlowKgPerSecond) {
        this.massFlowKgPerSecond = Math.max(0.0, massFlowKgPerSecond);
    }
}
