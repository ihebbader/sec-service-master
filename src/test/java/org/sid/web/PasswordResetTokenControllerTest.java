package org.sid.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sid.dao.AppUserRepository;
import org.sid.service.account.AccountService;
import org.sid.web.account.PasswordResetTokenController;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class PasswordResetTokenControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private AccountService accountService;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private EmailController emailController;
    @InjectMocks
    private PasswordResetTokenController passwordResetTokenController;
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void resetPassword() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        String username= "ihebbader";
        String inputJwon=objectMapper.writeValueAsString(username);
        MvcResult result =mockMvc.perform(post("/resetPassword").contentType(MediaType.APPLICATION_JSON_VALUE).content("ihebbader"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void changePassword() {
    }
}