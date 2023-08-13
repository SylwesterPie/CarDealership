package pl.zajavka.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.zajavka.api.dto.*;
import pl.zajavka.api.dto.mapper.CarServiceRequestMapper;
import pl.zajavka.api.dto.mapper.MechanicMapper;
import pl.zajavka.api.dto.mapper.PartMapper;
import pl.zajavka.api.dto.mapper.ServiceMapper;
import pl.zajavka.business.CarServiceProcessingService;
import pl.zajavka.business.CarServiceRequestService;
import pl.zajavka.business.PartCatalogService;
import pl.zajavka.business.ServiceCatalogService;
import pl.zajavka.domain.CarServiceProcessingRequest;
import pl.zajavka.domain.Part;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class MechanicController {

    public static final String MECHANIC = "/mechanic";
    public static final String MECHANIC_WORK_UNIT = "/mechanic/workUnit";

    private final CarServiceProcessingService carServiceProcessingService;
    private final CarServiceRequestService carServiceRequestService;
    private final PartCatalogService partCatalogService;
    private final ServiceCatalogService serviceCatalogService;
    private final CarServiceRequestMapper carServiceRequestMapper;
    private final MechanicMapper mechanicMapper;
    private final PartMapper partMapper;
    private final ServiceMapper serviceMapper;

    @GetMapping(value = MECHANIC)
    public ModelAndView mechanicCheckPage() {
        Map<String, ?> data = prepareNecessaryData();
        return new ModelAndView("mechanic_service", data);
    }

    private Map<String, ?> prepareNecessaryData() {
        var availableServiceRequest = getAvailableServiceRequest();
        var availableCarVins = availableServiceRequest.stream()
                .map(CarServiceRequestDTO::getCarVin)
                .toList();
        var availableMechanics =  getAvailableMechanics();
        var availableMechanicPesels = availableMechanics.stream()
                .map(MechanicDTO::getPesel)
                .toList();
        var parts = findParts();
        var services = findService();
        var partSerialNumbers = preparePartSerialNumbers(parts);
        var serviceCodes = services.stream().map(ServiceDTO::getServiceCode).toList();

        return Map.of(
                "availableServiceRequestDTOs", availableServiceRequest,
                "availableCarVins", availableCarVins,
                "availableMechanicDTOs", availableMechanics,
                "availableMechanicPesels", availableMechanicPesels,
                "partDTOs", parts,
                "partSerialNumbers", partSerialNumbers,
                "serviceDTOs", services,
                "serviceCodes", serviceCodes,
                "carServiceProcessDTO", CarServiceMechanicProcessingUnitDTO.buildDefault()
        );
    }

    @PostMapping(value = MECHANIC_WORK_UNIT)
    public String mechanicWorkUnite(
            @ModelAttribute("carServiceRequestProcessDTO") CarServiceMechanicProcessingUnitDTO dto,
            BindingResult result,
            ModelMap modelMap
    ) {
        if (result.hasErrors()) {
            return "error";
        }

        CarServiceProcessingRequest request = carServiceRequestMapper.mapFromDTO(dto);
        carServiceProcessingService.process(request);
        if (dto.getDone()) {
            return "mechanic_service_done";
        } else {
            modelMap.addAllAttributes(prepareNecessaryData());
            return "mechanic_service";
        }
    }

    private List<CarServiceRequestDTO> getAvailableServiceRequest() {
        return carServiceRequestService.availableServiceRequest().stream()
                .map(carServiceRequestMapper::mapToDTO)
                .toList();
    }

    private List<MechanicDTO> getAvailableMechanics() {
        return carServiceRequestService.availableMechanics().stream()
                .map(mechanicMapper::mapToDTO)
                .toList();
    }

    private List<PartDTO> findParts() {
        return partCatalogService.findAll().stream()
                .map(partMapper::mapToDTO)
                .toList();
    }

    private List<ServiceDTO> findService() {
        return serviceCatalogService.findAll().stream()
                .map(serviceMapper::mapToDTO)
                .toList();
    }


    private List<String> preparePartSerialNumbers(List<PartDTO> parts) {
        List<String> partSerialNumber = new ArrayList<>(
                parts.stream().map(PartDTO::getSerialNumber)
                .toList()
        );
        partSerialNumber.add(Part.NONE);
        return partSerialNumber;
    }
}
