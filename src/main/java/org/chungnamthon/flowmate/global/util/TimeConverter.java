package org.chungnamthon.flowmate.global.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.chungnamthon.flowmate.global.exception.BadRequestException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TimeConverter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd. a h:mm")
            .withLocale(Locale.KOREAN);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withLocale(Locale.KOREAN);

    public static String dateToString(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    public static LocalDate stringToDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new BadRequestException(ErrorStatus.INVALID_DATE_FORMAT);
        }
    }

}