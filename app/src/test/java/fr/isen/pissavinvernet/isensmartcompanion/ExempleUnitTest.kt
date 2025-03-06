package fr.isen.pissavinvernet.isensmartcompanion

import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Test unitaire pour vérifier que l'addition fonctionne correctement.
 * Ce test s'exécute sur la machine de développement (host).
 * Voir la documentation : http://d.android.com/tools/testing.
 */
class ExempleUnitTest {

    @Test
    fun addition_isCorrect() {
        // Définition des opérandes et du résultat attendu
        val operand1 = 2
        val operand2 = 2
        val expectedResult = 4

        // Calcul de la somme des opérandes
        val actualResult = operand1 + operand2

        // Vérifie que le résultat obtenu correspond au résultat attendu
        assertEquals("L'addition de 2 et 2 devrait donner 4", expectedResult, actualResult)
    }
}
