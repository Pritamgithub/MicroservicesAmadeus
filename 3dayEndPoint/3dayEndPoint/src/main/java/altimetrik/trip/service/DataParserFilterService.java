package altimetrik.trip.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import altimetrik.trip.amadeusclient.AmadeusApiClient;

@Service
public class DataParserFilterService {
	@Autowired
	AmadeusApiClient amCon;

	public String doFilter(String origin, String destination, String departureDate, String returnDate)
			throws JSONException {
		String results = "{\"message\":\"Results could not be loaded for one or more criteria. Try using different dates and destinations\"}";
		JSONObject fastestFlightJs;
		JSONObject nearestHotelJs;
		JSONObject fastestCarJs;
		JSONObject cheapestFlightJs;
		JSONObject cheapestHotelJs;
		JSONObject cheapestCarJs;
		JSONObject flightJs;
		JSONObject hotelJs;
		JSONObject carJs;
		try {
			fastestFlightJs = new JSONObject(amCon.loadFastestFlightsApiHandler(origin, destination, departureDate));
			nearestHotelJs = new JSONObject(amCon.loadNearestHotelsApiHandler(destination, departureDate, returnDate));
			fastestCarJs = new JSONObject(amCon.loadFastestCarsApiHandler(destination, departureDate, returnDate));
			cheapestFlightJs = new JSONObject(amCon.loadCheapestFlightsApiHandler(origin, destination, departureDate));
			cheapestHotelJs = new JSONObject(amCon.loadCheapestHotelsApiHandler(destination, departureDate, returnDate));
			cheapestCarJs = new JSONObject(amCon.loadCheapestCarsApiHandler(destination, departureDate, returnDate));
			flightJs = new JSONObject(amCon.loadFlightsApiHandler(origin, destination, departureDate));
			hotelJs = new JSONObject(amCon.loadHotelsApiHandler(destination, departureDate, returnDate));
			carJs = new JSONObject(amCon.loadCarsApiHandler(destination, departureDate, returnDate));
		} catch (Exception e) {
			return "{\"message\":\"This input caused an Internal Server Error. PLEASE NOTE:Results are returned for current or future dates upto 360 days only\"}";

		}
		if (fastestFlightJs.getJSONArray("results").length() > 0 && flightJs.getJSONArray("results").length() > 0
				&& hotelJs.getJSONArray("results").length() > 0 && carJs.getJSONArray("results").length() > 0) {
			String flightValue1 = fastestFlightJs.getJSONArray("results").get(0).toString();
			String hotelValue1 = nearestHotelJs.getJSONArray("results").get(0).toString();
			String carValue1 = fastestCarJs.getJSONArray("results").get(0).toString();
			String flightValue2 = cheapestFlightJs.getJSONArray("results").get(0).toString();
			String hotelValue2 = cheapestHotelJs.getJSONArray("results").get(0).toString();
			String carValue2 = cheapestCarJs.getJSONArray("results").get(0).toString();
			String flightValue3 = flightJs.getJSONArray("results").get(0).toString();
			String hotelValue3 = hotelJs.getJSONArray("results").get(0).toString();
			String carValue3 = carJs.getJSONArray("results").get(0).toString();
			results = "[" + flightValue1 + "," + hotelValue1 + "," + carValue1 + "," + flightValue2 + "," + hotelValue2
					+ "," + carValue2 + "," + flightValue3 + "," + hotelValue3 + "," + carValue3 + "]";
		}
		return results;
	}
}
