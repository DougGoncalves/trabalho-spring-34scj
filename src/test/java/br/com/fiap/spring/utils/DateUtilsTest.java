package br.com.fiap.spring.utils;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateUtilsTest {

    @Test
    public void shouldLocalDateTimeToDate(){
        LocalDateTime localDateTime = LocalDateTime.now();
        String resultDate = DateUtils.convertToDate(localDateTime);
        String expected = String.format("%02d-%02d-%s", localDateTime.getDayOfMonth(),
                localDateTime.getMonthValue(), localDateTime.getYear());
        assertEquals(expected, resultDate);
    }

    @Test
    public void shouldConvertDateToLocalDate(){
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        LocalDate resultDate = DateUtils.toLocalDate(date);
        String expected = String.format("%s-%02d-%02d", calendar.get(Calendar.YEAR),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1);
        assertEquals(expected, resultDate.toString());
    }

    @Test
    public void shouldValidateIfDatesInAInvalidPeriod(){
        LocalDateTime startLocalDateTime = LocalDateTime.now();
        LocalDateTime endLocalDateTime = LocalDateTime.now().plusDays(1);

        assertFalse(DateUtils.isInvalidPeriod(startLocalDateTime, endLocalDateTime));

        startLocalDateTime = LocalDateTime.now();
        endLocalDateTime = LocalDateTime.now().minusDays(1);

        assertTrue(DateUtils.isInvalidPeriod(startLocalDateTime, endLocalDateTime));
    }
}
