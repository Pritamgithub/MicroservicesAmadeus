package altimetrik.trip.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import altimetrik.trip.amadeusclient.AmadeusApiClient;
import altimetrik.trip.service.DataParserFilterService;

@RestController
@RequestMapping("/3daytrip")
public class TripOpController {
	@Autowired
	AmadeusApiClient aCon;

	@Autowired
	DataParserFilterService dpFilter;

	@RequestMapping(value = "/top3/{origin}/{destination}/{departureDate}/{returnDate}", method = RequestMethod.GET)
	public ResponseEntity<String> getTripResults(@PathVariable String origin, @PathVariable String destination,
			@PathVariable String departureDate, @PathVariable String returnDate) {
		String flightData = dpFilter.doFilter(origin, destination, departureDate, returnDate);
		return new ResponseEntity<>(flightData, HttpStatus.OK);
	}

}
