package mods.dieselization.api;

import mods.dieselization.api.thermos.ThermalComponent;
import mods.dieselization.api.thermos.ThermalSegments;

import java.util.OptionalInt;

public interface LocomotiveEngine {
    public boolean isStarting();

    public boolean isRunning();

    public boolean hasCriticalStartTemperature();

    public ThermalSegments createThermalSegments(
            OptionalInt purple,
            OptionalInt blue,
            OptionalInt green,
            OptionalInt yellow,
            OptionalInt red);

    //public void set
}
