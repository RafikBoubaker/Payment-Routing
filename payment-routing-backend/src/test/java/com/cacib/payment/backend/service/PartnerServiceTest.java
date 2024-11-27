package com.cacib.payment.backend.service;


import com.cacib.payment.backend.model.Partner;
import com.cacib.payment.backend.repository.PartnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PartnerServiceTest {

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PartnerRepository partnerRepository;

    @Test
    public void testAddPartner() {
        Partner partner = new Partner();
        partner.setAlias("NewPartner");
        partner.setType("Type1");

        Partner savedPartner = partnerService.addPartner(partner);

        assertNotNull(savedPartner);
        assertNotNull(savedPartner.getId());
        assertEquals("NewPartner", savedPartner.getAlias());
    }

    @Test
    public void testUpdatePartner() {

        Partner partner = new Partner();
        partner.setAlias("InitialPartner");
        Partner savedPartner = partnerRepository.save(partner);


        savedPartner.setAlias("UpdatedPartner");
        Partner updatedPartner = partnerService.updatePartner(savedPartner.getId(), savedPartner);

        assertEquals("UpdatedPartner", updatedPartner.getAlias());
    }
}

