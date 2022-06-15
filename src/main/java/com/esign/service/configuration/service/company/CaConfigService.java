package com.esign.service.configuration.service.company;

import com.esign.service.configuration.entity.company.CaConfig;
import com.esign.service.configuration.repository.company.CaConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CaConfigService {
    @Autowired
    protected CaConfigRepository caConfigRepository;

    public CaConfig getCaConfigByCaId(Integer caId){
        return caConfigRepository.getByCaId(caId);
    }

    public CaConfig save(CaConfig caConfig){
        return caConfigRepository.save(caConfig);
    }
}
