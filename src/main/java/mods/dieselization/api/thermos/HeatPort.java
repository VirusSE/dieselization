package mods.dieselization.api.thermos;

/**
 * One connection point of a thermal component.
 * <p>
 * Temperature is stored in Celsius. Delta calculations still work because
 * Celsius and Kelvin have the same interval size.
 */
public final class HeatPort {
    private double temperatureCelsius;
    private double massFlowKgPerSecond;

    public HeatPort(double temperatureCelsius, double massFlowKgPerSecond) {
        this.temperatureCelsius = temperatureCelsius;
        this.massFlowKgPerSecond = Math.max(0.0, massFlowKgPerSecond);
    }

    public double temperatureCelsius() {
        return temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public double massFlowKgPerSecond() {
        return massFlowKgPerSecond;
    }

    public void setMassFlowKgPerSecond(double massFlowKgPerSecond) {
        this.massFlowKgPerSecond = Math.max(0.0, massFlowKgPerSecond);
    }

    public void copyFrom(HeatPort other) {
        this.temperatureCelsius = other.temperatureCelsius;
        this.massFlowKgPerSecond = other.massFlowKgPerSecond;
    }
}
