import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.Browser.Companion.drive
import com.automation.remarks.kirk.Config
import me.tatarka.assertk.assert
import me.tatarka.assertk.assertAll
import me.tatarka.assertk.assertions.isEqualTo
import org.testng.annotations.Test
import java.io.File

/**
 * Created by sergey on 27.06.17.
 */
class ConfigTest : BaseTest() {

    @Test
    fun testCheckDefaultConfigLoaded() {
        val cfg = Browser.getDefaultConfig()
        assertAll {
            assert(cfg.browserName()).isEqualTo("chrome")
            assert(cfg.timeout()).isEqualTo(4000)
            assert(cfg.startMaximized())
        }
    }

    @Test
    fun testCanOverrideDefaultConfig() {
        System.setProperty("browserName", "firefox")
        System.setProperty("timeout", "6000")
        System.setProperty("startMaximized", "false")
        val cfg = Browser.getDefaultConfig()
        System.clearProperty("browserName")
        System.clearProperty("timeout")
        System.clearProperty("startMaximized")

        assertAll {
            assert(cfg.browserName()).isEqualTo("firefox")
            assert(cfg.timeout()).isEqualTo(6000)
            assert(cfg.startMaximized()).isEqualTo(false)
        }
    }

    @Test
    fun testCanUserConfigFile() {
        val file = File("src/main/resources/browser.config")
        file.writeText("browserName=firefox\ntimeout=300")
        val cfg = Browser.getDefaultConfig()
        file.delete()
        assertAll {
            assert(cfg.browserName()).isEqualTo("firefox")
            assert(cfg.timeout()).isEqualTo(300)
            assert(cfg.startMaximized()).isEqualTo(true)
        }
    }

    @Test
    fun testConfigWithBrowser() {
        val cfg = Config(startMaximized = false,
                screenSize = listOf(640, 480))

        drive(config = cfg) {
            to(url)
        }

        drive {
            to(url)
        }
    }
}