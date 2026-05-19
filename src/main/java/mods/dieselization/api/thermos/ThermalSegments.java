package mods.dieselization.api.thermos;

import org.jetbrains.annotations.NotNull;

import java.util.OptionalInt;

public record ThermalSegments(OptionalInt purple, OptionalInt blue, OptionalInt green, OptionalInt yellow, OptionalInt red) {

    public ThermalSegments {
        validateStrictlyIncreasing(purple, blue, green, yellow, red);
    }

    private static void validateStrictlyIncreasing(OptionalInt... values) {
        OptionalInt previous = OptionalInt.empty();

        for (OptionalInt current : values) {
            if (current == null) {
                throw new IllegalArgumentException("values must not contain null");
            }

            if (current.isEmpty()) {
                continue;
            }

            if (previous.isPresent() && previous.getAsInt() >= current.getAsInt()) {
                throw new IllegalArgumentException(
                        "values must be strictly increasing"
                );
            }

            previous = current;
        }
    }
}

