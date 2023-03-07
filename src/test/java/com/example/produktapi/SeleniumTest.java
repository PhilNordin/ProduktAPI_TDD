package com.example.produktapi;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeleniumTest {

    @Test
    //@Disabled
    void checkWebsiteTitle(){
        //Hämta in den webDriver som ska användas
        WebDriver driver = new ChromeDriver();

        //Navigera till den webbsida som ska testas - navigate().to kan va get()
        driver.get("https://java22.netlify.app/");

        //fail = "https://java22.netlify.app/"
        assertEquals("Webbutik", driver.getTitle(),"felmeddelande: Title dont match as expected! note its case sensitive");

        driver.quit();
    }

    @Test
    //@Disabled
    void numberOfProductsShouldBeTwenty(){
        //Hämta in webdriver
        WebDriver driver = new ChromeDriver();

        //Navigera till hemsidan som ska testas
        driver.get("https://java22.netlify.app/");

        //
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        List <WebElement> products = driver.findElements(By.className("productItem"));

        //21 = fail
        assertEquals(20,products.size(), "Antalet matchar inte!");

        driver.quit();
    }



    @Test
    public void checkIfPriceIsRightOnThreeProducts(){
        //Hämta in webdriver
        WebDriver driver = new ChromeDriver();

        //Navigera till hemsidan som ska testas
        driver.get("https://java22.netlify.app/");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String productOne = driver.findElement(By.xpath("//*[@id='productsContainer']/div/div[1]/div/div/p")).getText();
        String productTwo = driver.findElement(By.xpath("//*[@id='productsContainer']/div/div[2]/div/div/p")).getText();
        String productThree = driver.findElement(By.xpath("//*[@id='productsContainer']/div/div[3]/div/div/p")).getText();

        String findPriceForProductOne = "109.95";
        String findPriceForProductTwo = "22.3";
        String findPriceForProductThree = "55.99";

        boolean validatePriceForProductOne = productOne.contains(findPriceForProductOne);
        boolean validatePriceForProductTwo = productTwo.contains(findPriceForProductTwo);
        boolean validatePriceForProductThree = productThree.contains(findPriceForProductThree);

        assertTrue(validatePriceForProductOne, "Priset på produkt 1 stämmer inte!");
        assertTrue(validatePriceForProductTwo, "Priset på produkt 2 stämmer inte!");
        assertTrue(validatePriceForProductThree, "Priset på produkt 3 stämmer inte!");

        driver.quit();
    }
}

