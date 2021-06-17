package org.sid.service.workflow;

import lombok.NoArgsConstructor;
import org.sid.dao.AppUserRepository;
import org.sid.dao.DataModelRepoqitory;
import org.sid.dao.EntityModelRepository;
import org.sid.dao.NotificationRepository;
import org.sid.entities.AppUser;
import org.sid.entities.DataModel;
import org.sid.entities.EntityModel;
import org.sid.entities.Notification;
import org.sid.web.email.EmailController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@NoArgsConstructor
@EnableScheduling
public class EntityModelServiceImpl implements EntityModelService{
    @Autowired
    private EntityModelRepository entityModelRepository;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private DataModelRepoqitory dataModelRepoqitory;
    @Autowired
    private EmailController emailController;
    @Autowired
    private NotificationRepository notificationRepository;
    @Override
    public void SaveEntityModel(EntityModel e) {
        entityModelRepository.save(e);

    }

    @Override
    public void DeleteEntityFromModel(Long id) {
        entityModelRepository.deleteById(id);
    }

    @Override
    public void addUserToEntityModel(Long id, List<AppUser> appUsers) {
        EntityModel entityModel = entityModelRepository.getOne(id);
        appUsers.forEach(user->{
            AppUser user1=appUserRepository.getOne(user.getId());
            entityModel.getUser().add(user1);
        });
        entityModelRepository.save(entityModel);

    }

    @Override
    @Scheduled(fixedRate = 100L)
    public void UpdateEntityStatus() {

        Date date = new Date();
        List<DataModel> dataModels =dataModelRepoqitory.findAll();
        dataModels.forEach(dataModel -> {
            dataModel.getEntity().forEach(entityModel -> {
                entityModel.setEtapeOrd(dataModel.getEntity().indexOf(entityModel)+1);
            });
        });
        dataModels.forEach(dataModel -> {
            dataModel.getEntity().forEach(entityModel -> {
                if(entityModel.getStartDate().after(date)){
                    entityModel.setEtat("Programmé");
                }
                if(entityModel.getStartDate().before(date)   && entityModel.getEndDate().after(date) && !entityModel.getFinished()){
                    entityModel.setEtat("En cours");
                    entityModel.setStatus("En cours de traitement");
                    entityModelRepository.save(entityModel);
                }

                if(entityModel.getEndDate().before(date)){
                    entityModel.setEtat("Terminer");
                    entityModel.setFinished(true);
                    entityModel.setActived(false);
                    entityModelRepository.save(entityModel);
                }

                if(entityModel.getEtat().equals("En cours") && entityModel.getFinished().equals(true)){
                    entityModel.setEtat("Terminer");
                    entityModel.setActived(false);
                    entityModelRepository.save(entityModel);
                }
            });
        });
        dataModels.forEach(dataModel -> {
                    dataModel.getEntity().forEach(entityModel -> {
                                List<EntityModel> entityModels =  dataModel.getEntity();
                                if(entityModel.getEtapeOrd()==1 && entityModel.getFinished().equals(false) && entityModel.getEtat().equals("En cours")){
                                    entityModel.setActived(true);
                                    entityModelRepository.save(entityModel);
                                }
                                if(entityModel.getEtapeOrd() == 1 && entityModel.getFinished().equals(true) && entityModel.getEtat().equals("En cours") ){
                                    entityModel.setActived(false);
                                    entityModel.setEtat("Terminer");
                                    entityModelRepository.save(entityModel);
                                }
                                if(entityModel.getEtapeOrd()!=1 && entityModel.getEtat().equals("En cours") && entityModels.get(entityModel.getEtapeOrd()-2).getFinished()){

                                    entityModel.setActived(true);
                                    entityModelRepository.save(entityModel);

                                }
                            }
                    );
                }
        );
        notificationSystem("Nouveau");
    }
    public void notificationSystem(String etat) {
        List<DataModel> dataModels = dataModelRepoqitory.findAll();
        List<AppUser> user = appUserRepository.findAll();
        dataModels.forEach(dataModel -> {
            dataModel.getEntity().forEach(entityModel -> {
                entityModel.getNotification().forEach(notification -> {
                    if (notification != null) {
                        if (notification.getStatus().equals(etat) && notification.getIsseneded().equals(false) && entityModel.getEtat().equals("Programmé")) {
                            if (notification.getRole().equals("Les utilisateurs de cette étape")) {
                                System.out.println(entityModel.getUser());
                                entityModel.getUser().forEach(u -> {
                                    AppUser user1 = appUserRepository.findByUsername(u.getUsername());
                                    emailController.ConfirmMailiNFO(user1, notification.getMessage());
                                    notification.setIsseneded(true);
                                    notificationRepository.save(notification);
                                });
                            }
                            if (notification.getRole().equals("Employée")) {
                                user.forEach(u -> {
                                    u.getRoles().forEach(r -> {
                                        if (r.getRoleName().equals("USER")) {
                                            emailController.ConfirmMailiNFO(u, notification.getMessage());
                                        }
                                    });
                                });
                            }
                            if (notification.getRole().equals("Administarateur")) {
                                user.forEach(u -> {
                                    u.getRoles().forEach(r -> {
                                        if (r.getRoleName().equals("ADMIN")) {
                                            emailController.ConfirmMailiNFO(u, notification.getMessage());
                                        }
                                    });
                                });
                            }
                        }
                    }
                });
            });
        });
    }
}
