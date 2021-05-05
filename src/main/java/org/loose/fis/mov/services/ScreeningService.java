package org.loose.fis.mov.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.mov.model.Cinema;
import org.loose.fis.mov.model.Screening;

import java.util.Calendar;
import java.util.List;

import static org.dizitart.no2.objects.filters.ObjectFilters.*;

public class ScreeningService {
    public static List<Screening> findAllScreeningsForCinema(String cinemaName) {
        return DatabaseService.getScreeningRepo().find(
                eq("cinemaName", cinemaName)
        ).toList();
    }

    public static List<Screening> findAllScreeningsForCinema(Cinema cinema) {
        return DatabaseService.getScreeningRepo().find(
                eq("cinemaName", cinema.getName())
        ).toList();
    }

    public static List<Screening> findAllFutureScreeningsForCinema(String cinemaName) {
        return DatabaseService.getScreeningRepo().find(
                and(
                        eq("cinemaName", cinemaName),
                        gt("date", Calendar.getInstance().getTime())
                )
        ).toList();
    }

    public static List<Screening> findAllFutureScreeningsForCinema(Cinema cinema) {
        return DatabaseService.getScreeningRepo().find(
                and(
                        eq("cinemaName", cinema.getName()),
                        gt("date", Calendar.getInstance().getTime())
                )
        ).toList();
    }

    public static void deleteScreening(Screening screening) {
        DatabaseService.getScreeningRepo().remove(screening);
    }
}
