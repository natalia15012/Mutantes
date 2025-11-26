package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private final MutantDetector detector = new MutantDetector();

    @Test
    void shouldDetectMutantWithHorizontalAndVerticalSequences() {
        // Caso Mutante: Tiene horizontal (AAAA) y vertical (CCCC)
        String[] dna = {
                "AAAAGA", // Fila 0: AAAA (Secuencia 1)
                "CAGTGC",
                "TCATGT",
                "CGAAGG",
                "CCCCTA", // Fila 4: CCCC (Secuencia 2)
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna), "Debería ser mutante (2 secuencias)");
    }

    @Test
    void shouldDetectMutantWithDiagonals() {
        // Caso Mutante: Diagonales
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG", // Diagonal 1 bajando
                "CCCCTA",
                "TCACTG"
        };
        String[] dnaMutante = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG", // Diagonal \ detectada
                "CCCCTA", // Horizontal detectada
                "TCACTG"
        };
        assertTrue(detector.isMutant(dnaMutante));
    }

    @Test
    void shouldReturnFalseForHuman() {
        // Caso Humano: Sin secuencias suficientes
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATTT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "Debería ser humano");
    }

    @Test
    void shouldReturnFalseForInvalidCharacter() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAXGG", //La X es inválida
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna), "Debería retornar false por carácter inválido");
    }

    @Test
    void shouldReturnFalseForNullOrEmpty() {
        assertFalse(detector.isMutant(null));
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    void shouldReturnFalseForNonSquareMatrix() {
        // Matriz de 3 filas pero largo 6 (No cuadrada)
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"
        };
        assertFalse(detector.isMutant(dna), "Debería validar NxN");
    }
    //Caso Especifico: Solo Verticales
    @Test
    void shouldDetectMutantWithVerticalSequencesOnly() {
        String[] dna = {
                "ATGCGA",
                "ATGTGC",
                "ATTTGT",
                "AGATGG",
                "CCTCTA",
                "TCTCTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 2. Caso Crítico: Contra Diagonal
    @Test
    void shouldDetectMutantWithAntiDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        String[] dnaAnti = {
                "AAAG",
                "AAGA",
                "AGAA",
                "GAAA"
        };
        String[] dnaFull = {
                "AAAGT",
                "AAGAT",
                "AGAAT",
                "GAAAT",
                "CCCCG"
        };
        assertTrue(detector.isMutant(dnaFull));
    }

    //Matriz Mínima 4x4
    @Test
    void shouldDetectMutantIn4x4Matrix() {
        String[] dna = {
                "AAAA", // Horizontal
                "CCCC", // Horizontal
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna));
    }

    //Caso Humano: Casi mutante, pero no
    @Test
    void shouldReturnFalseForSequencesOfThree() {
        String[] dna = {
                "AAAT",
                "CCCG",
                "TTTA",
                "GGGC"
        };
        assertFalse(detector.isMutant(dna), "3 letras no son mutante");
    }

    // Caso Validación: Fila Nula en medio del array
    @Test
    void shouldReturnFalseForNullRow() {
        String[] dna = {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG"
        };
        assertFalse(detector.isMutant(dna));
    }

    // Todo el ADN igual
    @Test
    void shouldDetectMutantWhenAllAreSame() {
        String[] dna = {
                "AAAA",
                "AAAA",
                "AAAA",
                "AAAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    // Caso Validación: Filas de distinto largo
    @Test
    void shouldReturnFalseForJaggedArray() {
        String[] dna = {
                "ATGC",
                "AT",
                "ATGC",
                "ATGC"
        };
        assertFalse(detector.isMutant(dna));
    }
    // Caso Límite: Secuencia Vertical en la ultima columna
    @Test
    void shouldDetectMutantWithSequenceInLastColumn() {
        String[] dna = {
                "ATGCGT",
                "CAGTGT",
                "TTATGT",
                "AGAAGT",
                "CCCCGA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna), "Fallo al detectar secuencia en última columna");
    }

    // Caso Humano
    @Test
    void shouldReturnFalseForSingleSequence() {
        String[] dna = {
                "AAAA",
                "GTCA",
                "TGAC",
                "GTCA"
        };
        assertFalse(detector.isMutant(dna), "Humano con 1 secuencia no debe ser mutante");
    }

     @Test
    void shouldDetectMutantWithCrossingSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "C C C C T A",
                "T C A C T G"
        };
        String[] dnaCross = {
                "GTCA",
                "GTCA",
                "TTTT",
                "GTCA"
        };
        assertTrue(detector.isMutant(dnaCross), "Debe detectar secuencias cruzadas");
    }

    // Letras Minúsculas
    @Test
    void shouldReturnFalseForLowerCaseCharacters() {
        String[] dna = {
                "aaaa", // minúsculas
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertFalse(detector.isMutant(dna), "Debe rechazar minúsculas");
    }

     @Test
    void shouldDetectMutantWithSparseSequences() {
        String[] dna = {
                "AAAA",
                "GTCA",
                "TGAC",
                "CCCC"
        };
        assertTrue(detector.isMutant(dna), "Debe acumular secuencias separadas");
    }
}