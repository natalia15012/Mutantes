package org.example.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        if (dna == null) {
            return false;
        }

        if (dna.length == 0) {
            return false;
        }

        int n = dna.length;

        for (String row : dna) {
            if (row == null || row.length() != n) {
                return false; // Fila nula o longitud incorrecta
            }

            if (!row.matches("[ATCG]+")) {
                return false;
            }
        }

        return true;
    }
}