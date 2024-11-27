package com.cacib.payment.backend.controller;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.cacib.payment.backend.model.Partner;
import com.cacib.payment.backend.model.enumeration.Direction;
import com.cacib.payment.backend.model.enumeration.ProcessedFlowType;
import com.cacib.payment.backend.service.PartnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(PartnerController.class)
public class PartnerControllerTests {

    @Mock
    private PartnerService partnerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private PartnerController partnerController;

    @Test
    void shouldAddPartner() throws Exception {
        Partner partner = new Partner();
        partner.setAlias("Partner1");
        partner.setType("Type1");
        partner.setDirection(Direction.valueOf("INBOUND"));
        partner.setApplication("App1");
        partner.setProcessedFlowType(ProcessedFlowType.valueOf("MESSAGE"));
        partner.setDescription("Description1");

        when(partnerService.addPartner(any(Partner.class))).thenReturn(partner);

        mockMvc.perform(post("/api/v1/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(partner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("Partner1"))
                .andExpect(jsonPath("$.type").value("Type1"))
                .andExpect(jsonPath("$.direction").value("INBOUND"))
                .andDo(print());
    }

    @Test
    void shouldGetAllPartners() throws Exception {
        Partner partner = new Partner();
        partner.setAlias("Partner1");
        Page<Partner> page = new PageImpl<>(Collections.singletonList(partner), PageRequest.of(0, 10), 1);

        when(partnerService.getAllPartners(any(PageRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/partners")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.content[0].alias").value("Partner1"))
                .andDo(print());
    }

    @Test
    void shouldUpdatePartner() throws Exception {
        Partner updatedPartner = new Partner();
        updatedPartner.setAlias("UpdatedPartner");
        updatedPartner.setType("UpdatedType");
        updatedPartner.setDirection(Direction.valueOf("OUTBOUND"));
        updatedPartner.setApplication("UpdatedApp");
        updatedPartner.setProcessedFlowType(ProcessedFlowType.valueOf("ALERTING"));
        updatedPartner.setDescription("UpdatedDescription");

        when(partnerService.updatePartner(eq(1L), any(Partner.class))).thenReturn(updatedPartner);

        mockMvc.perform(put("/api/v1/partners/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPartner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.alias").value("UpdatedPartner"))
                .andExpect(jsonPath("$.direction").value("OUTBOUND"))
                .andDo(print());
    }

    @Test
    void shouldDeletePartner() throws Exception {
        doNothing().when(partnerService).deletePartner(1L);

        mockMvc.perform(delete("/api/v1/partners/{id}", 1L))
                .andExpect(status().isOk())
                .andDo(print());

        verify(partnerService, times(1)).deletePartner(1L);
    }
}
