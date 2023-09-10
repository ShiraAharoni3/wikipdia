import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WikipediaScraper {
    public static void main(String[] args)
    {
    try {
        Challenge.showInstructions();
        List<River> riverList = new ArrayList<>();
        Document document = Jsoup.connect("https://en.wikipedia.org/wiki/List_of_river_systems_by_length#cite_note-britannica-amazon-4").get();
        Elements elements = document.select("table.wikitable.sortable tr");
        for (Element row:elements) {
            if (row.child(0).text().equals("Rank")){
                continue;
            }
            String name = row.child(1).text();
            String meters = row.child(2).text();
            meters= getParsableNumber(meters);
            String mile = row.child(3).text();
            mile= getParsableNumber(mile);
            String drain = row.child(4).text();
            drain = getParsableNumber(drain);
            River river = new River(name,Integer.parseInt(meters),Integer.parseInt(mile),Integer.parseInt(drain));
            riverList.add(river);
        }
        ApiUtil.send(riverList);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private static String getParsableNumber(String str) {
        str = str.replace(",", "");
        for (int i = 0; i < str.length(); i++) {
            char a = str.charAt(i);
            if (!Character.isDigit(a)) {
                str = str.substring(0, i);
                break;
            }
        }
        if (str.equals("")) {
            str = "0";
        }
        return str;
    }
}
