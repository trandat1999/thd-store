package com.thd.store.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author DatNuclear 1/15/2024
 * @project store-movie
 */
public class DateUtils {
    public static Date atStartOfDay(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    public static Date atEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static Date atStartOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        return atStartOfDay(cal.getTime());
    }

    public static Date atEndOfYear(Integer year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        return atEndOfDay(cal.getTime());
    }

    public static int getYear(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static int getMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    public static Integer getQuarter(Date date) {
        return dateToLocalDateTime(date).get(IsoFields.QUARTER_OF_YEAR);
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Chuyển đổi định dạng date/datetime
     *
     * @param fromFormat hh:mm:ii dd/MM/yy
     * @param toFormat   hh:mm:ii dd/MM/yy
     * @param date
     * @return
     */
    public static String formatDate(String fromFormat, String toFormat, String date) {
        try {
            Date dateConvert = new SimpleDateFormat(fromFormat).parse(date);
            return formatDate(dateConvert, toFormat);
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Chuyển đổi date sang định dạng yêu cầu
     *
     * @param date
     * @param format hh:mm:ii dd/MM/yy
     * @return
     */
    public static String formatDate(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static Date getFirstDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_YEAR, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        return cal.getTime();
    }

    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static Date minusDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.minusYears(year);
        localDateTime = localDateTime.minusMonths(month);
        localDateTime = localDateTime.minusDays(day);
        localDateTime = localDateTime.minusHours(hour);
        localDateTime = localDateTime.minusMinutes(minute);
        localDateTime = localDateTime.minusSeconds(second);
        return localDateTimeToDate(localDateTime);
    }

    public static Date plusDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        localDateTime = localDateTime.plusYears(year);
        localDateTime = localDateTime.plusMonths(month);
        localDateTime = localDateTime.plusDays(day);
        localDateTime = localDateTime.plusHours(hour);
        localDateTime = localDateTime.plusMinutes(minute);
        localDateTime = localDateTime.plusSeconds(second);
        return localDateTimeToDate(localDateTime);
    }

    public static int maxDay(int month, int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
