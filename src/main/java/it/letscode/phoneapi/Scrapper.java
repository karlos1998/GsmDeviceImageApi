package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class Scrapper {

    private final GsmArenaItemRepository gsmArenaItemRepository;

    private final String baseUrl = "https://www.gsmarena.com/";
    private int mobilePhonesCount = 0;

    public void scrape() {
        System.out.println("Testowa komenda ok 2345");

//        GsmArenaItem gsmArenaItem = new GsmArenaItem();
//        gsmArenaItem.setUrl("testowy url");
//        gsmArenaItemRepository.save(gsmArenaItem);


        try {
            String url = "https://www.gsmarena.com/makers.php3";
            Document doc = Jsoup.connect(url).get();

            Elements elements = doc.select("table a");

            System.out.println("Test 1");

            for (Element element : elements) {
                String text = element.text();
                System.out.println(text);

                scrapTargetBrand(element.attr("href"));

//                break; //todo
            }

            System.out.println("Mobile phones count: " + mobilePhonesCount);

        } catch (Exception e) {
            System.out.println("Exception:");
            e.printStackTrace();
        }
    }

    private void scrapTargetBrand(String brandUrl) {

        try {

            Thread.sleep(3000);

            String url = baseUrl + brandUrl;
            Document doc = Jsoup.connect(url).get();

            Elements elements = doc.select(".makers li a");

            for (Element element : elements) {
                String text = element.text();
                System.out.println(text);

                readGsmItemDetails(element.attr("href"));

                mobilePhonesCount++;
//                break; //todo
            }

            /**
             * Przejdz na kolejna strone
             */
            Element link = doc.selectFirst("a.pages-next:not(.disabled)");

            if (link != null) {
                String href = link.attr("href");
                System.out.println("Link: " + href);
                scrapTargetBrand(href);
            } else {
                System.out.println("Nie znaleziono odpowiedniego linku.");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readGsmItemDetails(String href) {
        System.out.println("details: " + href);
        try {

            Thread.sleep(2000);

            /**
             * Create Full url
             */
            String url = baseUrl + href;

            /**
             * Create model
             */
            GsmArenaItem gsmArenaItem = new GsmArenaItem();
            gsmArenaItem.setItemUrl(url);

            /**
             * Load html from page
             */
            Document doc = Jsoup.connect(url).get();

            /**
             * Get default item photo
             */
            Element image = doc.selectFirst(".specs-photo-main img");
            if(image != null) {

                /**
                 * Save url
                 */
                String imageUrl = image.attr("src");
                System.out.println("Phone Image: " + imageUrl);
                gsmArenaItem.setImageUrl(imageUrl);

                /**
                 * Save binary data
                 */
                Thread.sleep(1000);
                URL imageUri = new URL(imageUrl);
                URLConnection imageUriConnection = imageUri.openConnection();
                InputStream imageInputStream = imageUriConnection.getInputStream();
                byte[] imageData = imageInputStream.readAllBytes();
                gsmArenaItem.setImageData(imageData);

            } else {
                System.out.println("Phone image not found.");
            }

            /**
             * Get model name
             */
            Element modelNameElement = doc.selectFirst(".specs-phone-name-title");
            if(modelNameElement != null) {

                String modelName = modelNameElement.text();
                System.out.println("Model name: " + modelName);
                gsmArenaItem.setModelName(modelName);

            } else {
                System.out.println("Model name not found.");
            }

            /**
             * Get models list
             */
            Element modelsElement = doc.selectFirst("[data-spec=\"models\"]");
            if(modelsElement != null) {

                String[] modelsArray = modelsElement.text().split(", ");
                System.out.println("Models: " + Arrays.toString(modelsArray));
                gsmArenaItem.setModels(modelsArray);

            } else {
                System.out.println("Models not found.");
            }

            /**
             * Save model to database
             */
            gsmArenaItemRepository.save(gsmArenaItem);

        } catch (Exception e) {
            System.out.println("Exception:");
            e.printStackTrace();
        }
    }

}
