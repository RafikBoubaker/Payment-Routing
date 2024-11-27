package com.cacib.payment.backend.service;

import com.cacib.payment.backend.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerService {


    Partner addPartner(Partner partner);


    Page<Partner> getAllPartners(Pageable pageable);

    Partner updatePartner(Long id, Partner updatedPartner);

    void deletePartner(Long id);

}
