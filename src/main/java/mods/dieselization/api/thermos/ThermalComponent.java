package mods.dieselization.api.thermos;

/**
 * Simulation-facing contract implemented by every heat circuit element.
 */
public interface ThermalComponent {
    String id();

    HeatPort inlet();

    HeatPort outlet();

    /**
     * Advances this component by one simulation step.
     *
     * @param medium circulating medium
     * @param dtSeconds elapsed simulation time in seconds
     */
    void simulate(HeatMedium medium, double dtSeconds);
}

