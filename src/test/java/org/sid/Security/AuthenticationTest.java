package org.sid.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sid.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthenticationTest {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

    }
    @Test
    public void login() throws Exception{
        ObjectMapper obm = new ObjectMapper();
        //setup my user
        AppUser user = new AppUser();
        user.setUsername("ihebbader");
        user.setPassword("52359654");
        System.out.println();
        String inputJson= obm.writeValueAsString(user);
        MvcResult result= mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
                .andExpect(status().isOk()).andReturn();
        String Jwt = (String) result.getResponse().getHeaderValue("Authorization");
        assertNotNull(Jwt);
        System.out.println(Jwt);
    }
}