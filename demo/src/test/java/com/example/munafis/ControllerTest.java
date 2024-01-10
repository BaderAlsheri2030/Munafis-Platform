package com.example.munafis;



/////////////////////////
import com.example.munafis.DTO.ProviderDTO;
import com.example.munafis.Model.User;
import com.example.munafis.Repository.AuthRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import com.example.munafis.Controller.ProviderController;
import com.example.munafis.Service.ProviderService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProviderController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ControllerTest {

    @MockBean
    AuthRepository authRepository;

    @MockBean
    ProviderService providerService;

    @Autowired
    MockMvc mockMvc;
    ProviderDTO providerDTO1;
    User user;

    @BeforeEach
    void setUp() {
        user = new User(null,"username","1234","user@gmail.com","PROVIDER",null,null);
         providerDTO1 = new ProviderDTO(1, "STC122" , "1234","PROVIDER@gmeil.acom","PROVIDER","STC","111","Riyadh","IT",null,null,null);


    }
      @Test
        public void testRegisterProvider() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mockMvc.perform(post("/api/v1/provider/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(providerDTO1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateProvider() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mockMvc.perform(put("/api/v1/provider/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(providerDTO1)))
                .andExpect(status().isOk());
    }
    @Test
    public void testViewMyAcceptedOffer() throws Exception {
        mockMvc.perform(get("/api/v1/provider/viewMyAcceptedOffers"))
                .andExpect(status().isOk());
    }
    @Test
    public void testViewMyPendingOffers() throws Exception {
        mockMvc.perform(get("/api/v1/provider/viewMyPendingOffers"))
                .andExpect(status().isOk());
    }
    @Test
    public void testViewMyRejectedOffers() throws Exception {
        mockMvc.perform(get("/api/v1/provider/viewMyRejectedOffers"))
                .andExpect(status().isOk());
    }
    }



