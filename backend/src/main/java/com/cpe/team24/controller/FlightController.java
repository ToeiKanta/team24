package com.cpe.team24.controller;

import com.cpe.team24.entity.Flight;
import com.cpe.team24.entity.FlightAirport;
import com.cpe.team24.repository.FlightAirportRepository;
import com.cpe.team24.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@RestController
@RequestMapping(path="/api/flight")
public class FlightController {
    @Autowired
    private FlightRepository flightRepository;

    public FlightController(FlightRepository flightRepository){}

    @Autowired
    private FlightAirportRepository flightAirportRepository;

    @GetMapping("")
    public Collection<FlightAirport> getFlightAirport(){
        return flightAirportRepository.findAll();
    }
    // For Book Flight - ToeiKanta had been creating.
    @GetMapping("/{date}")
    public Collection<Flight> getFlightByDepartDate(@PathVariable String date) throws ParseException {
        Date departDayStart = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        String dateEnd = getNextDate(date);
        Date departDayEnd = new SimpleDateFormat("yyyy-MM-dd").parse(dateEnd);
        Collection<Flight> result = flightRepository.findAllByDepartBetween(departDayStart,departDayEnd);
        //System.out.println(departDayStart);
        //System.out.println(departDayEnd);
        //System.out.println(result);
        return result;
    }

    // This Function return Tomorrow's Date from Today's Date - ToeiKanta had been creating.
    public String getNextDate(String curDate) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = format.parse(curDate);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return format.format(calendar.getTime());
    }
}
