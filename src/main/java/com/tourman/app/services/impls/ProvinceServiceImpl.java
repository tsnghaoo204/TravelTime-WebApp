package com.tourman.app.services.impls;

import com.tourman.app.domains.dtos.commons.ProvinceDto;
import com.tourman.app.domains.dtos.mappers.ProvinceMapping;
import com.tourman.app.domains.entities.Attraction;
import com.tourman.app.domains.entities.Province;
import com.tourman.app.repositories.ProvinceRepository;
import com.tourman.app.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final ProvinceMapping provinceMapping;

    @Override
    public List<ProvinceDto> getProvinces() {
        return provinceRepository.findAll()
                .stream()
                .map(provinceMapping::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProvinceDto getProvinceById(Long id) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Province not found"));
        return provinceMapping.toDto(province);
    }

    @Override
    public boolean createProvince(ProvinceDto provinceDto) {
        if(provinceRepository.existsByName(provinceDto.getName())) {
            throw new RuntimeException("Province name already exists");
        }
        provinceRepository.save(provinceMapping.fromDto(provinceDto));
        return true;
    }

    @Override
    public ProvinceDto updateProvince(Long id, ProvinceDto provinceDto) {
        Province province = provinceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Province not found"));

        return null;
    }

    @Override
    public void deleteProvince(Long id) {

    }

    private Province switchProvince(ProvinceDto provinceDto) {
        Province province = new Province();
        province.setName(provinceDto.getName());
        province.setDescription(provinceDto.getDescription());
        province.setCode(province.getCode());
        return province;
    }
}
