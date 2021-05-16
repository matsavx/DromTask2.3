package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class DataStruct {
    public String carBrand;
    public int adQuantity;

    public DataStruct(String carBrand, int adQuantity) {
        this.carBrand = carBrand;
        this.adQuantity = adQuantity;
    }
}

public class MainPage {
    WebDriver driver;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy (xpath = "/html/body/div[2]/div[5]/div[1]/div[1]/div[3]/form/div[1]/div[1]/div[2]")
    private WebElement selectBox;

    public void read_n_find() {

        String selectBoxText = selectBox.getAttribute("innerHTML");
        Document selectBoxParsed = Jsoup.parse(selectBoxText);

        Elements divs = selectBoxParsed.getElementsByTag("div");

        DataStruct[] dataStructArray = new DataStruct[20];

        for (int i = 0; i < dataStructArray.length; i++)
            dataStructArray[i] = new DataStruct("", 0);

        String carBrand;
        int adQuantity;
        Pattern pattern = Pattern.compile("\\d+");

        for (Element div : divs) {

            Matcher matcher = pattern.matcher(div.text());

            if (matcher.find()) {
                adQuantity = Integer.parseInt(div.text().substring(matcher.start(), matcher.end()));
                carBrand = div.text().substring(0, div.text().indexOf(" "));

                for (int i = 0; i < dataStructArray.length; i++) {

                    if (dataStructArray[i].carBrand.equals(carBrand))
                        break;

                    else if (adQuantity > dataStructArray[i].adQuantity) {

                        for (int j = dataStructArray.length - 1; j > i; j--) {
                            dataStructArray[j].carBrand = dataStructArray[j-1].carBrand;
                            dataStructArray[j].adQuantity = dataStructArray[j-1].adQuantity;
                        }

                        dataStructArray[i].adQuantity = adQuantity;
                        dataStructArray[i].carBrand = carBrand;
                        break;
                    }
                }
            }
        }

        System.out.println("Фирма | Количество объявлений");
        for (int k = 0; k < dataStructArray.length; k++) {
            System.out.println(dataStructArray[k].carBrand + " | " + dataStructArray[k].adQuantity);
        }
    }
}
