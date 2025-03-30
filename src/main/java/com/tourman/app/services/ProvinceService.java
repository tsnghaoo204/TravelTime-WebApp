package com.tourman.app.services;

import com.tourman.app.domains.dtos.commons.ProvinceDto;

import java.util.List;

public interface ProvinceService {
    List<ProvinceDto> getProvinces();
    ProvinceDto getProvinceById(Long id);
    boolean createProvince(ProvinceDto provinceDto);
    ProvinceDto updateProvince(Long id ,ProvinceDto provinceDto);
    void deleteProvince(Long id);
}
