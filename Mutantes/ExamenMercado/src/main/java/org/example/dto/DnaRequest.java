package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema; // Import nuevo
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.validator.ValidDnaSequence;

@Data
public class DnaRequest {

    @Schema(
            description = "Array de Strings que representa la secuencia de ADN (NxN)",
            example = "[\"ATGCGA\",\"CAGTGC\",\"TTATGT\",\"AGAAGG\",\"CCCCTA\",\"TCACTG\"]"
    )
    @ValidDnaSequence
    @NotNull
    private String[] dna;
}