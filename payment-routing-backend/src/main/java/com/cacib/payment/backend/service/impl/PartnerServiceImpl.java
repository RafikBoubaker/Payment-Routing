package com.cacib.payment.backend.service.impl;

import com.cacib.payment.backend.model.Partner;
import com.cacib.payment.backend.repository.MessageRepository;
import com.cacib.payment.backend.repository.PartnerRepository;
import com.cacib.payment.backend.service.PartnerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

    private final Logger log = LoggerFactory.getLogger(PartnerServiceImpl.class);


    @Autowired
    PartnerRepository partnerRepository;

    public PartnerServiceImpl(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }


    @Override
    public Partner addPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public Page<Partner> getAllPartners(Pageable pageable) {
        return partnerRepository.findAll(pageable);
    }

    @Override
    public void deletePartner(Long id) {
        partnerRepository.deleteById(id);
    }


    @Override
    public Partner updatePartner(Long id, Partner updatedPartner) {
        return partnerRepository.findById(id).map(existingPartner -> {
            existingPartner.setAlias(updatedPartner.getAlias());
            existingPartner.setType(updatedPartner.getType());
            existingPartner.setDirection(updatedPartner.getDirection());
            existingPartner.setApplication(updatedPartner.getApplication());
            existingPartner.setProcessedFlowType(updatedPartner.getProcessedFlowType());
            existingPartner.setDescription(updatedPartner.getDescription());
            return partnerRepository.save(existingPartner);
        }).orElseThrow(() -> new EntityNotFoundException("Partner with id " + id + " not found"));
    }
}
