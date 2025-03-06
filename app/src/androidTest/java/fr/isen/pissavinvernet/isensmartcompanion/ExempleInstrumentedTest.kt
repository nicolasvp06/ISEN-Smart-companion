package fr.isen.pissavinvernet.isensmartcompanion

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Test instrumenté s'exécutant sur un appareil Android.
 * Pour plus d'informations, voir la documentation : http://d.android.com/tools/testing.
 */
@RunWith(AndroidJUnit4::class)
class ExempleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Récupère le contexte de l'application testée
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("fr.isen.pissavinvernet.isensmartcompanion", context.packageName)
    }
}
