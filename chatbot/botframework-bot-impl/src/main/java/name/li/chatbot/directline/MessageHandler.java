package name.li.chatbot.directline;

import com.imsavva.weatherclient.beans.DailyForecast;
import com.imsavva.weatherclient.beans.ForecastEntry;
import com.imsavva.weatherclient.beans.WeeklyForecast;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Savva Kodeikin
 */
@Component
public class MessageHandler {

    private static final String CURRENT_DATE_FORMAT = "dd.MM.yyyy";
    private static final String DEFAULT_LOCATION = "Ryazan";

    private static final String DEGREES_CELSIUS = "C";
    private static final String MMHG = "mmHg";
    private static final String PERCENT = "%";

    private static final String HORIZONTAL_RUZE = "---------";
    private static final String NEW_LINE = "\n\n";
    private static final String ITALIC_TEXT = "*";
    private static final String BOLD_TEXT = "**";
    private static final String SPACE = " ";

    /**
     * Extracts location from a message assuming that message is like:<br/>
     *
     * weather today Moscow
     *
     * @param message
     * @return extracted or default location
     */
    public String extractLocation(String message) {
        String location = DEFAULT_LOCATION;

        if (message != null) {
            String[] splitted = message.split(SPACE);

            if (splitted.length >= 3) {
                location = splitted[2];
            }
        }

        return location;
    }

    public String createDailyForecastMessage(String location, DailyForecast forecast) {
        StringBuilder builder = new StringBuilder();
        builder.append("Got forecasts for ")
                .append(capitalizeFirstLetter(location))
                .append(" from ")
                .append(forecast.getDetail().size())
                .append(" suppliers:")
                .append(NEW_LINE)
                .append(NEW_LINE);

        for (Map.Entry<String, ForecastEntry> entry : forecast.getDetail().entrySet()) {
            builder.append(handleForecastSupplier(entry.getKey(), entry.getValue()));
        }

        return builder.toString();
    }

    public String createWeeklyForecastMessage(String location, WeeklyForecast forecast) {
        StringBuilder builder = new StringBuilder();
        builder.append("Weekly weather forecast for ")
                .append(capitalizeFirstLetter(location))
                .append(" from ")
                .append(forecast.getDetail().size())
                .append(" suppliers:")
                .append(NEW_LINE)
                .append(NEW_LINE);

        for (Map.Entry<String, List<ForecastEntry>> entry : forecast.getDetail().entrySet()) {
            for (ForecastEntry forecastEntry : entry.getValue()) {
                builder.append(handleForecastSupplier(entry.getKey(), forecastEntry));
            }
        }

        return builder.toString();
    }

    public String removeSubstring(String message, String substring) {
        if (message == null) {
            return  null;
        }
        return message.replace(substring, "").trim();
    }

    private String handleForecastSupplier(String supplier, ForecastEntry forecastEntry) {
        StringBuilder builder = new StringBuilder();
        builder.append(BOLD_TEXT)
                .append("Forecast from ")
                .append(supplier)
                .append(" on ")
                .append(formatDate(forecastEntry.getDate()))
                .append(BOLD_TEXT)
                .append(NEW_LINE)

                .append(ITALIC_TEXT)
                .append("Daily temperature:")
                .append(ITALIC_TEXT)
                .append(SPACE)
                .append(forecastEntry.getTempDay())
                .append(DEGREES_CELSIUS)
                .append(NEW_LINE)

                .append(ITALIC_TEXT)
                .append("Nightly temperature:")
                .append(ITALIC_TEXT)
                .append(SPACE)
                .append(forecastEntry.getTempNight())
                .append(DEGREES_CELSIUS)
                .append(NEW_LINE)

                .append(ITALIC_TEXT)
                .append("Daily pressure:")
                .append(ITALIC_TEXT)
                .append(SPACE)
                .append(forecastEntry.getPressureDay())
                .append(MMHG)
                .append(NEW_LINE)

                .append(ITALIC_TEXT)
                .append("Nightly pressure:")
                .append(ITALIC_TEXT)
                .append(SPACE)
                .append(forecastEntry.getPressureNight())
                .append(MMHG)
                .append(NEW_LINE)

                .append(ITALIC_TEXT)
                .append("Precipitation probability:")
                .append(ITALIC_TEXT)
                .append(SPACE)
                .append(forecastEntry.getPrecipitationProbability())
                .append(PERCENT)
                .append(NEW_LINE)
                .append(HORIZONTAL_RUZE)
                .append(NEW_LINE);

        return builder.toString();
    }

    private String formatDate(Date date) {
        return new SimpleDateFormat(CURRENT_DATE_FORMAT).format(date);
    }

    private String capitalizeFirstLetter(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
