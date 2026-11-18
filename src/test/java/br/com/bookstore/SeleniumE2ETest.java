package br.com.bookstore;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.time.Duration;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class SeleniumE2ETest {
 @LocalServerPort int port; WebDriver driver;
 @BeforeEach void iniciar(){WebDriverManager.chromedriver().setup();driver=new ChromeDriver();driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));}
 @AfterEach void finalizar(){if(driver!=null)driver.quit();}
 @Test void deveExibirTelaDeLogin(){driver.get("http://localhost:"+port+"/login.html");assertTrue(driver.getTitle().contains("BookStore"));assertTrue(driver.findElement(By.id("email")).isDisplayed());}
 @Test void deveEntrarNoPainel(){driver.get("http://localhost:"+port+"/login.html");driver.findElement(By.id("email")).sendKeys(Keys.chord(Keys.CONTROL,"a"));driver.findElement(By.id("email")).sendKeys("admin@bookstore.com");driver.findElement(By.id("senha")).sendKeys(Keys.chord(Keys.CONTROL,"a"));driver.findElement(By.id("senha")).sendKeys("123456");driver.findElement(By.cssSelector("button")).click();new WebDriverWait(driver,Duration.ofSeconds(10)).until(d->d.getCurrentUrl().contains("index.html"));assertTrue(driver.getPageSource().contains("Painel da livraria"));}
}
