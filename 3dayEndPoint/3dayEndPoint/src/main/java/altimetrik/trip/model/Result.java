package altimetrik.trip.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Result implements Serializable {
  String destination;
  Date departure_date;
  double price;
}
