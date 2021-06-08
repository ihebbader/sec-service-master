package org.sid.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sid.dao.AppUserRepository;
import org.sid.dao.RequestRepository;
import org.sid.dao.UserNotificationRepository;
import org.sid.entities.AppRole;
import org.sid.entities.AppUser;
import org.sid.service.account.AccountService;
import org.sid.web.account.UserController;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private AccountService accountService;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private EmailController emailController;
    @Mock
    private AppUserRepository appUserRepository;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private AppRole appRole;
    @Mock
    private UserNotificationRepository userNotificationRepository;
    @InjectMocks
    private UserController userController;
    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void register() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        AppUser user = new AppUser();
        user.setUsername("ihebbader");
        user.setEmail("baderiheb@gmail.com");
        user.setPassword("16111995");
        user.setActived(false);
        String inputInJson= this.mapToJson(user);
        String jsonRequest=this.mapToJson(user);
        MvcResult result =mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(status().isOk()).andReturn();
        String resultContent=result.getResponse().getContentAsString();
        AppUser user1 = objectMapper.readValue(resultContent,AppUser.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Assert.assertEquals(user,user1);
    }
    //Add User Test
    @Test
    public void AddUser() throws Exception{
        AppUser appUser = new AppUser();
    appUser.setUsername("ihebbader");
    appUser.setEmail("baderiheb@gmail.com");
    appUser.setPassword(bCryptPasswordEncoder.encode("16111995"));
    String jsonRequest=this.mapToJson(appUser);
        MvcResult result =mockMvc.perform(post("/adduser").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void DeleteUser() throws Exception{
        AppUser appUser = new AppUser();
        appUser.setUsername("ihebbader");
        String jsonRequest=this.mapToJson(appUser);
        MvcResult result =mockMvc.perform(post("/delete").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest))
                .andExpect(status().isOk()).andReturn();
    }


    ///////////////////////
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}