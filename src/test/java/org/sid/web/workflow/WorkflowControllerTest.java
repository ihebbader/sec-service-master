package org.sid.web.workflow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.sid.dao.*;
import org.sid.entities.DataModel;
import org.sid.entities.EntityModel;
import org.sid.service.workflow.DataModelServiceImpl;
import org.sid.service.workflow.EntityModelService;
import org.sid.service.workflow.WorkflowService;
import org.sid.web.account.PasswordResetTokenController;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class WorkflowControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private ValuesRepository valuesRepository;
    @Mock
    private DataRepository dataRepository;
    @Mock
    private EmailController emailController;
    @Mock
    private WorkflowService workflowService;
    @Mock
    private DataModelServiceImpl dataModelService;
    @Mock
    private EntityModelService entityModelService;
    @Mock
    private EntityModelRepository entityModelRepository;
    @Mock
    private DataModelRepoqitory dataModelRepoqitory;
    @Mock
    private PropertyRepository propertyRepository;
    @Mock
    private AppUserRepository appUserRepository;
    @InjectMocks
    private WorkflowController workflowController;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(workflowController).build();
    }

    @Test
    void excuteEntity()throws  Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        EntityModel entityModel = new EntityModel();
        entityModel.setId(6L);
        entityModel.setEntityModelName("Versification Feuill");
        String inputJwon=objectMapper.writeValueAsString(entityModel);
        MvcResult result =mockMvc.perform(post("/executEntity")
                .header("authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpaGViYmFkZXIiLCJyb2xlcyI6WyJBRE1JTiIsIlVTRVIiXSwiaXNzIjoiL2xvZ2luIiwiZXhwIjoxNjIzNDU3Nzc5fQ.2yywaRkp6urzz2JRObIiLBL6oYbzoONUjhwFzN04-E0\n")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJwon))
                .andExpect(status().isOk()).andReturn();
    }
    @Test
    public void  addNewDataModel() throws Exception{
        DataModel dataModel = new DataModel();
        dataModel.setDataModelName("New DataVa Model");
        dataModel.setDataModelDescrip("Description");
        dataModel.setStartDate(new Date());
        String jsonRequest=this.mapToJson(dataModel);
        MvcResult result =mockMvc.perform(post("/createDataModel")
                .header("authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJpaGViYmFkZXIiLCJyb2xlcyI6WyJVU0VSIl0sImlzcyI6Ii9sb2dpbiIsImV4cCI6MTYyMzAxMzMyNn0.JMFKuLRcdvgFugNMFRuCf2t4vK5JSevp2VB6Xx6gDhU")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonRequest))
                .andExpect(status().isOk()).andReturn();

    }
    private String mapToJson(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
}