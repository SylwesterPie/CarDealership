package pl.zajavka.domain;


import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
@ToString(of = "carVin")
public class CarHistory {
    String carVin;
    List<CarServiceRequest> carServiceRequest;


    @Value
    @Builder
    @ToString(of = {"carServiceRequestNumber", "receivedDataTime", "completedDataTime", "customerComment"})
    public static class CarServiceRequest {

        String carServiceRequestNumber;
        OffsetDateTime receivedDataTime;
        OffsetDateTime completedDataTime;
        String customerComment;
        List<Service> services;
        List<Part> parts;
    }

}
