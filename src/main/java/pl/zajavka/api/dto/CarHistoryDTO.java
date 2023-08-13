package pl.zajavka.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CarHistoryDTO {

    private String carVin;
    private List<ServiceRequestDTO> carServiceRequest;

    public static CarHistoryDTO buildDefault() {
        return CarHistoryDTO.builder()
                .carVin("empty")
                .carServiceRequest(Collections.emptyList())
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ServiceRequestDTO {
        private String carServiceRequestNumber;
        private String receivedDataTime;
        private String completedDateTime;
        private String customerComment;
        private List<ServiceDTO> services;
        private List<PartDTO> parts;
    }
}
