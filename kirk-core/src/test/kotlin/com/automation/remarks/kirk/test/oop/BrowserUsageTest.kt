package com.automation.remarks.kirk.test.oop

import com.automation.remarks.kirk.Browser
import com.automation.remarks.kirk.conditions.size
import com.automation.remarks.kirk.conditions.text
import com.automation.remarks.kirk.ex.WrongUrlException
import com.automation.remarks.kirk.test.BaseTest
import me.tatarka.assertk.assert
import me.tatarka.assertk.assertions.isEqualTo
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.testng.annotations.Test

/**
 * Created by sergey on 28.06.17.
 */
class BrowserUsageTest : BaseTest() {

    // tag::testСanRunBrowser[]
    @Test
    fun testСanRunBrowser() {
        val browser = Browser(ChromeDriver())
        browser.open(url)
        browser.element("#header").shouldHave(text("Kirk"))
    }
    // end::testСanRunBrowser[]

    @Test
    fun testCanSetNewDriver() {
        val firefox = Browser(FirefoxDriver()).apply {
            startMaximized = false
        }
        firefox.open(url)
        firefox.element(".paginator a").click()
        firefox.element("#header").shouldHave(text("Second page"))
    }

    @Test
    fun testCanOpenTwoBrowser() {
        val chrome = Browser(ChromeDriver())
        chrome.open(url)
        chrome.element("#header").shouldHave(text("Kirk"))

        val firefox = Browser(FirefoxDriver()).apply {
            startMaximized = false
        }
        firefox.open(url)
        firefox.all("li").shouldHave(size(10))
    }

    @Test
    fun testCanGetCurrentUrl() {
        val chrome = Browser()
        chrome.open(url)
        assert(chrome.currentUrl).isEqualTo(url)
    }

    @Test
    fun testCanOpenCanonicalUrl() {
        val chrome = Browser(FirefoxDriver()).apply {
            baseUrl = url
            startMaximized = false
        }

        chrome.open("/")
        chrome.all("li").shouldHave(size(10))
    }

    @Test
    fun testCanOpenCanonicalUrlWithoutSpecifiedBase() {
        assertExceptionThrown(WrongUrlException::class) {
            Browser().open("/")
        }
    }

    @Test
    fun testBrowserInteract() {
        Browser().to(url) {
            element("#header").shouldHave(text("Kirk"))
            all("li").shouldHave(size(10))
        }
    }
}