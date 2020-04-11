package br.com.fiap.spring.utils;

import br.com.fiap.spring.advice.ResponseError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT_INPUT = new SimpleDateFormat("MM-dd-yyyy");
    private static final SimpleDateFormat DATE_FORMAT_OUTPUT = new SimpleDateFormat("dd-MM-yyyy");

    public static LocalDate toLocalDate(Date date) {
        try {
            return DATE_FORMAT_OUTPUT.parse(DATE_FORMAT_INPUT.format(date)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            throw new ResponseError("O período de pesquisa informado é inválido.");
        }
    }

    public static String convertToDate(LocalDateTime localDateTime) {
        return DATE_FORMAT_OUTPUT.format(Date.from(localDateTime.toLocalDate().atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant()));
    }

    public static boolean isInvalidPeriod(LocalDateTime dateTimeOne, LocalDateTime dateTimeTwo){
        return dateTimeOne.isAfter(dateTimeTwo);
    }
}
