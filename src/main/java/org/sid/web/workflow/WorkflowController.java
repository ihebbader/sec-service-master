package org.sid.web.workflow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.sid.Exception.EntityNotFoundException;
import org.sid.Exception.ErrorDateException;
import org.sid.Exception.NoDataModelFoundException;
import org.sid.dao.*;
import org.sid.entities.AppUser;
import org.sid.entities.DataModel;
import org.sid.entities.EntityModel;
import org.sid.entities.Property;
import org.sid.service.workflow.*;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WorkflowController {
    @Autowired
    private ValuesRepository valuesRepository;
    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private EmailController emailController;
    @Autowired
    private WorkflowService workflowService;
    @Autowired
    private DataModelServiceImpl dataModelService;
    @Autowired
    private EntityModelService entityModelService;
    @Autowired
    private EntityModelRepository entityModelRepository;
    @Autowired
    private DataModelRepoqitory dataModelRepoqitory;
    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    private int ord= 0;
    @PostMapping(path = "/saveProperty")
    public void property(@RequestBody Property p){
      //  System.out.println(p);
        workflowService.save(p);
    }
    @GetMapping(path = "/getProperty/{id}")
    public Optional<Property> getById(@PathVariable Long id  ){
        return workflowService.getById(id);

    }
    @GetMapping(path = "/getAllProperty")
    public List<Property> getAllProperty(){
        return workflowService.getAllProperty();
    }


    public void saveProp(@RequestBody List<Property> p){
        for(int i =0 ;i<p.size();i++){
            workflowService.save(p.get(i));
        }
    }


    @GetMapping("/getAllModel")
    public List<DataModel> getAllDataModel(){
        //System.out.println(dataModelService.getAllDataModel());
        return dataModelService.getAllDataModel();
    }
    @PostMapping(path = "/deleteDataFlow")
    public void Delete(@RequestBody  DataModel d){
        dataModelService.Delete(d);
    }
    @PutMapping(path = "/updateDataModel")
    public void update(@RequestBody  DataModel d){
        dataModelService.update(d);
    }
    //ajouter une nouvelle entités
    @PostMapping(path = "/addNewEntity/{id}")
    public void addNewEntityModel(@RequestBody EntityModel entityModel, @PathVariable Long id) throws ErrorDateException {
        DataModel dataModel=this.dataModelService.getDataModelById(id);
        if (dataModel.getStartDate().after(entityModel.getStartDate())) throw new ErrorDateException("Date de début de cette entité est invalide");
        if(dataModel.getEndDate().before(entityModel.getEndDate())) throw new ErrorDateException("Date de fin de cette entité est invalide");
       entityModel.setFinished(false);
        entityModel.setStatus("Nouveau");
        Date d = new Date();
        List<Property> properties1 = entityModel.getProperties();
        properties1.sort((left,right)->left.getOrd()- right.getOrd());
        entityModel.setProperties(properties1);
        entityModel.setDateDeCreation(d);
        if(dataModel.getEntity().size() != 0 ) {
            dataModel.getEntity().forEach(entityModel1 -> {
                if (ord < entityModel1.getEtapeOrd()) {
                    ord = entityModel1.getEtapeOrd();
                }
            });
        }
        entityModel.setEtapeOrd(ord+1);
        entityModel.getProperties().forEach(e->{
            //     System.out.println(e);
            if(e.getValues().size() != 0 ){
                e.getValues().forEach(values -> {
                    valuesRepository.save(values);
                });
            }
            if(e.getData() != null ){
                e.getData().getValues().forEach(values -> {
                    valuesRepository.save(values);

                });
                dataRepository.save(e.getData());
            }
        });
        entityModel.getProperties().forEach(e->{
            //     System.out.println(e);
            propertyRepository.save(e);
        });

        //  System.out.println(dataModel);
        Set<AppUser> appUsers = new HashSet<>();
        System.out.println(entityModel.getNotification());
        entityModel.getUser().forEach(u->{
            AppUser appUser=appUserRepository.findByUsername(u.getUsername());
            //   System.out.println(appUser);
            appUsers.add(appUser);

        });
        entityModel.setUser(appUsers);
        entityModelRepository.save(entityModel);
        dataModel.getEntity().add(entityModel);
        dataModelRepoqitory.save(dataModel);
        List<AppUser> user=appUserRepository.findAll();


    }
    @CrossOrigin("http://localhost:4200")
    @DeleteMapping("/deleteEntity/{id}")
    public void DeleteEntityFromDataModel(@PathVariable Long id){
       EntityModel e= entityModelRepository.getOne(id);
       e.getUser().remove(e.getUser());
       entityModelRepository.save(e);
        this.entityModelService.DeleteEntityFromModel(id);

    }
    @GetMapping("/getModel/{id}")
    public List<Property> getDataModelById(@PathVariable Long id){

        System.out.println(id);
        EntityModel e = entityModelRepository.getOne(id);
        return  e.getProperties();
    }
    @PostMapping("/updateEntity")
    public void updateEntity(@RequestBody EntityModel e) throws EntityNotFoundException {
        System.out.println(e);
        EntityModel entityModel = entityModelRepository.getOne(e.getId());
        if(entityModel == null) throw new EntityNotFoundException("Entité non trouvé");
        entityModelRepository.save(e);
    }
    @PostMapping("/addUserToEntity/{id}")
    public void AddUserToEntity(@RequestBody List<AppUser> users,@PathVariable Long id){

        entityModelService.addUserToEntityModel(id,users);
    }
    @GetMapping("/getAllForSuperVisor")
    public List<DataModel> getForSuperVisor(){
        return dataModelRepoqitory.findAll();
    }

    @GetMapping("/getDataModleToDo/{username}")
    public List<DataModel> getDataModelListPerUser(@PathVariable String username) throws NoDataModelFoundException {
       AppUser appUser = appUserRepository.findByUsername(username);
        List<DataModel> dataModels=new ArrayList<>();
      List<DataModel> dataModels1=dataModelRepoqitory.findAll();
        dataModels1.forEach(dataModel -> {
      //      System.out.println(dataModel);
            dataModel.getEntity().forEach(entityModel -> {
                entityModel.getUser().forEach( user-> {
//                    System.out.println(dataModel.getDataModelName()+"--"+user.getUsername() );
                    if(appUser == user){
                        if(dataModels.contains(dataModel)){

                        }else{
                            dataModels.add(dataModel);
                        }

                    }
                });
            });
       });
        dataModels.forEach(dataModel -> {
            System.out.println(dataModel.getDataModelName());
        });
     if(dataModels.isEmpty()) throw new NoDataModelFoundException("Pas de workflow associée a cette utilisateur");
      return dataModels;
    }
    @PostMapping("/executEntity")
    public void excuteEntity(@RequestBody EntityModel entityModel){
        System.out.println(entityModel);
        entityModel.setFinished(true);
        entityModel.setActived(false);
        entityModelRepository.save(entityModel);
    }
    @PostMapping(path = "/createDataModel")
    public void createDataModel(@RequestBody DataModel d){
        System.out.println(d);
        dataModelService.CreateDataModel(d);
    }
    @PostMapping("/UpdateForm/{id}")
    public void updateForm(@PathVariable Long id ,@RequestBody List<Property> property){
        EntityModel entityModel = entityModelRepository.getOne(id);
        property.sort((left,right)->left.getOrd()- right.getOrd());
        property.forEach(p->{
            propertyRepository.save(p);
            entityModel.getProperties().add(p);
        });
 entityModelRepository.save(entityModel);
    }
}

