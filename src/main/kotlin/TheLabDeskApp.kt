import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import core.log.Timber

object TheLabDeskApp {

    /**
     * Source https://github.com/ToxicBakery/Arbor
     */
    fun initArbor() {
        Arbor.sow(Seedling())
        Timber.d("initArbor()")
    }
}