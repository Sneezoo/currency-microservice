package de.flo.currencies.tasks;

import de.flo.currencies.model.CurrencyRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipInputStream;

@Component
public class CSVReloadTask {

    private static final String DATE_FORMAT = "dd MMMM yyyy";
    private static final String DATE_FIELD = "Date";
    @Resource
    CurrencyRepository currencyRepository;

    private static final Logger log = LoggerFactory.getLogger(CSVReloadTask.class);

    private static final String EUROXREF_URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref.zip";

    @Scheduled(cron="*/10 * * * * *")
    public void reloadCSV() {
        try {
            Iterable<CSVRecord> records = downloadAndParse();
            for (CSVRecord record : records) {
                Date date = getDateFromCsvRecord(record);
                if (!date.after(currencyRepository.getLastUpdate())) {
                    return;
                }
                Map<String, String> recordMap = record.toMap();
                recordMap.remove(DATE_FIELD);
                saveCurrencies(recordMap);

                currencyRepository.update(date);
            }
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (ParseException e) {
            log.error(e.getMessage());
        }
    }

    private void saveCurrencies(Map<String, String> recordMap) {
        for(Map.Entry<String, String> entry : recordMap.entrySet()) {
            if (StringUtils.isNotBlank(entry.getKey()) && StringUtils.isNotBlank(entry.getValue())) {
                try {
                    currencyRepository.putCurrency(entry.getKey().trim(), Double.parseDouble(entry.getValue()));
                } catch (NumberFormatException e) {
                    log.warn(e.getMessage());
                    continue;
                }
            }
        }
    }

    private Date getDateFromCsvRecord(CSVRecord record) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return dateFormat.parse(record.get(DATE_FIELD));
    }

    private Iterable<CSVRecord> downloadAndParse() throws IOException {
        URL url = new URL(EUROXREF_URL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        ZipInputStream zipInput = new ZipInputStream(httpConn.getInputStream());
        zipInput.getNextEntry();
        return CSVFormat.RFC4180
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(zipInput));
    }

}
