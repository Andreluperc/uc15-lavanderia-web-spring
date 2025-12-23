package br.senac.pi.lavanderia;

import br.senac.pi.lavanderia.util.CpfUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpfUtilsTest {

    @Test
    void normalizar_deve_remover_pontuacao() {
        assertEquals("12345678900", CpfUtils.normalizar("123.456.789-00"));
        assertEquals("12345678900", CpfUtils.normalizar(" 123 456 789 00 "));
    }

    @Test
    void isValidoBasico_deve_rejeitar_nulo_ou_tamanho_incorreto() {
        assertFalse(CpfUtils.isValidoBasico(null));
        assertFalse(CpfUtils.isValidoBasico(""));
        assertFalse(CpfUtils.isValidoBasico("123"));
        assertFalse(CpfUtils.isValidoBasico("123.456.789-0"));
    }

    @Test
    void isValidoBasico_deve_rejeitar_todos_digitos_iguais() {
        assertFalse(CpfUtils.isValidoBasico("000.000.000-00"));
        assertFalse(CpfUtils.isValidoBasico("11111111111"));
    }

    @Test
    void isValidoBasico_deve_aceitar_11_digitos_nao_uniforme() {
        assertTrue(CpfUtils.isValidoBasico("123.456.789-00"));
        assertTrue(CpfUtils.isValidoBasico("98765432100"));
    }
}
