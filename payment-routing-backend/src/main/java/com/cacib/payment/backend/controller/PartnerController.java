package com.cacib.payment.backend.controller;


import com.cacib.payment.backend.model.Partner;
import com.cacib.payment.backend.service.MessageService;
import com.cacib.payment.backend.service.PartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/partners")
public class PartnerController {

    private final Logger log = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private PartnerService partnerService;

    public PartnerController(PartnerService partnerService) {
        this.partnerService = partnerService;
    }

    @PostMapping
    public ResponseEntity<Partner> addPartner(@RequestBody Partner partner) {
        Partner savedPartner = partnerService.addPartner(partner);
        return ResponseEntity.ok(savedPartner);
    }


    @GetMapping
    public ResponseEntity<Page<Partner>> getAllPartners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(partnerService.getAllPartners(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner updatedPartner) {
        Partner existingPartner = partnerService.updatePartner(id, updatedPartner);
        return ResponseEntity.ok(existingPartner);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.ok().build();
    }
}
