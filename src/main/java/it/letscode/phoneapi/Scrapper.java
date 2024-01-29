package it.letscode.phoneapi;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
                break; //todo
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

            Thread.sleep(1500);

            String url = baseUrl + href;
            Document doc = Jsoup.connect(url).get();

            Element image = doc.selectFirst(".specs-photo-main img");

            if(image != null) {

                String imageUrl = image.attr("src");

                System.out.println("Phone Image: " + imageUrl);

                GsmArenaItem gsmArenaItem = new GsmArenaItem();
                gsmArenaItem.setItemUrl(url);
                gsmArenaItem.setImageUrl(imageUrl);
                gsmArenaItemRepository.save(gsmArenaItem);

            } else {
                System.out.println("Phone image not found.");
            }

        } catch (Exception e) {
            System.out.println("Exception:");
            e.printStackTrace();
        }
    }

}
