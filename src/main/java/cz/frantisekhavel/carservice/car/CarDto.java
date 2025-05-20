package cz.frantisekhavel.carservice.car;

import java.time.Instant;
import jakarta.validation.constraints.NotBlank;

public record CarDto(@NotBlank String name, Instant created) {}
