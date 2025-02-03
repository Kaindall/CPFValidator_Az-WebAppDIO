package edu.dio.cpf_validator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("validator")
public class ValidatorController {
    @GetMapping()
    public ResponseEntity<String> validateCpf(@RequestParam(value="cpf") String cpf) {
        if (isValidCPF(cpf)) {
            return new ResponseEntity<>("O CPF " + cpf + " é válido!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("O CPF " + cpf + " é inválido", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isValidCPF(String cpf) {
        if (cpf == null) return false;

        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se tem 11 dígitos
        if (cpf.length() != 11) return false;

        // Cálculo do primeiro dígito verificador
        int sum = 0, weight = 10;
        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }
        int firstDigit = (sum * 10) % 11;
        if (firstDigit == 10) firstDigit = 0;

        // Cálculo do segundo dígito verificador
        sum = 0;
        weight = 11;
        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }
        int secondDigit = (sum * 10) % 11;
        if (secondDigit == 10) secondDigit = 0;

        // Verifica se os dígitos verificadores estão corretos
        return firstDigit == (cpf.charAt(9) - '0') && secondDigit == (cpf.charAt(10) - '0');
    }
}
