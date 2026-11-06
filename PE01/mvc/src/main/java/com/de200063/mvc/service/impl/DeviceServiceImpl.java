package com.de200063.mvc.service.impl;

import com.de200063.mvc.dto.DeviceDTO;
import com.de200063.mvc.entity.Category;
import com.de200063.mvc.entity.Device;
import com.de200063.mvc.repository.CategoryRepository;
import com.de200063.mvc.repository.DeviceRepository;
import com.de200063.mvc.service.DeviceService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final DateTimeFormatter DATE_FORMATTER_SLASH = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_FORMATTER_HYPHEN = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private final DeviceRepository deviceRepository;
    private final CategoryRepository categoryRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository, CategoryRepository categoryRepository) {
        this.deviceRepository = deviceRepository;
        this.categoryRepository = categoryRepository;
    }

    private LocalDate parseInstallationDate(String text) {
        if (text == null) {
            return null;
        }
        String trimmed = text.trim();
        if (trimmed.contains("/")) {
            return LocalDate.parse(trimmed, DATE_FORMATTER_SLASH);
        } else {
            return LocalDate.parse(trimmed, DATE_FORMATTER_HYPHEN);
        }
    }

    @Override
    public List<Device> getAllDevices() {
        return deviceRepository.findAllByOrderByInstallationDateDesc(); // gọi phương thức tìm kiếm tất cả thiết bị và sắp xếp giảm dần theo InstallationDate
    }

    @Override
    public List<Device> searchByName(String keyword) {
        return deviceRepository.findByDeviceNameContainingIgnoreCaseOrderByInstallationDateDesc(keyword); // gọi phương thức tìm kiếm theo tên thiết bị và sắp xếp giảm dần theo InstallationDate
    }

    @Override
    public Device getById(Integer id) {
        return deviceRepository.findById(id).orElse(null); // gọi phương thức tìm kiếm thiết bị theo id, nếu không tìm thấy trả về null
    }

    @Override
    public void validateBusinessRules(DeviceDTO deviceDTO, BindingResult bindingResult) {
        String objName = bindingResult.getObjectName();

        // Device code duy nhất (unique), check format đã valid
        if (deviceDTO.getDeviceCode() != null && !deviceDTO.getDeviceCode().isBlank() && !bindingResult.hasFieldErrors("deviceCode")) { // Lưu ý tên trường cần kiểm tra
            Optional<Device> existing = deviceRepository.findByDeviceCode(deviceDTO.getDeviceCode().trim());
            if (existing.isPresent()) {
                bindingResult.addError(new FieldError(objName, "deviceCode", "Device Code already exists"));
            }
        }

        // Category must exist in the database
        if (deviceDTO.getCategoryId() != null) {
            if (!categoryRepository.existsById(deviceDTO.getCategoryId())) {
                bindingResult.addError(new FieldError(objName, "categoryId", "Selected category does not exist"));
            }
        }

        // Installation date: dd/MM/yyyy format + Ngay cài đặt là ngày trong tương lai
        if (deviceDTO.getInstallationDate() != null && !deviceDTO.getInstallationDate().isBlank()
                && !bindingResult.hasFieldErrors("installationDate")) {
            try {
                LocalDate installationDate = parseInstallationDate(deviceDTO.getInstallationDate());
                if (!installationDate.isAfter(LocalDate.now())) {
                    bindingResult.addError(new FieldError(objName, "installationDate", "Installation date must be a future date"));
                }
            } catch (DateTimeParseException e) {
                bindingResult.addError(new FieldError(objName, "installationDate", "Invalid date format. Please use dd/MM/yyyy"));
            }
        }
    }

    @Override
    public void createDevice(DeviceDTO deviceDTO) {
        Category category = categoryRepository.findById(deviceDTO.getCategoryId()).orElseThrow(
                () -> new IllegalArgumentException("Category not found with id: " + deviceDTO.getCategoryId())
        );
        Device device = new Device();
        device.setDeviceCode(deviceDTO.getDeviceCode().trim());
        device.setDeviceName(deviceDTO.getDeviceName().trim());
        device.setPrice(deviceDTO.getPrice());
        device.setWarrantyMonths(deviceDTO.getWarrantyMonths());
        device.setInstallationDate(parseInstallationDate(deviceDTO.getInstallationDate()));
        device.setCategory(category);
        device.setStatus(deviceDTO.getStatus());

        deviceRepository.save(device);
    }

    @Override
    public void deleteDevice(Integer id) {
        Device device = deviceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Device not found with id: " + id)
        );
        // Q2.2 - delete a device; throws IllegalStateException when device status is Active (AC)
        if ("AC".equalsIgnoreCase(device.getStatus())) {
            throw new IllegalStateException("Cannot delete an active device");
        }
        deviceRepository.deleteById(id);
    }
}
