import org.json.JSONArray;
import org.json.JSONObject;

import com.vdurmont.emoji.EmojiParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=6fff53a641b9b9a799cfd6b079f5cd4e");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setMain((String) obj.get("main"));
        }

        String weather = "";
        String icon_weather = "";

        switch (model.getMain()) {
            case "Clouds": weather = "Облачно"; icon_weather = ":cloud:"; break;
            case "Clear": weather = "Ясно"; icon_weather = ":sunny:"; break;
            case "Rain": weather = "Дождь"; icon_weather = ":cloud_with_rain:"; break;
            case "Snow": weather = "Снег"; icon_weather = ":cloud_with_snow:"; break;
        }

        return "Погода в Адлере" + "\n" +
                EmojiParser.parseToUnicode(":thermometer:") + " Температура: " + model.getTemp() + "°C" + "\n" +
                EmojiParser.parseToUnicode(":droplet:") + " Влажность: " + model.getHumidity() + "%" + "\n" +
                EmojiParser.parseToUnicode(icon_weather) + " " + weather;
    }
}
