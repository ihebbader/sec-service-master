package org.sid.service.workflow;

import lombok.NoArgsConstructor;
import org.sid.dao.AppUserRepository;
import org.sid.dao.DataModelRepoqitory;
import org.sid.entities.AppUser;
import org.sid.entities.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@NoArgsConstructor
public class DataModelServiceImpl implements DataModelService{
    @Autowired
    private DataModelRepoqitory dataModelRepoqitory;
    @Autowired
    private AppUserRepository appUserRepository;
    @Override
    public void CreateDataModel(DataModel m) {
        dataModelRepoqitory.save(m);
    }

    @Override
    public List<DataModel> getAllDataModel() {
        return dataModelRepoqitory.findAll();
    }

    @Override
    public void Delete(DataModel d) {
        dataModelRepoqitory.delete(d);
    }

    @Override
    public void update(DataModel d) {
        dataModelRepoqitory.save(d);
    }

    @Override
    public DataModel getDataModelById(Long id) {
        return dataModelRepoqitory.getOne(id);
    }

    @Override
    public List<DataModel> getDataModelListPerUser(Long id) {
        AppUser appUser = appUserRepository.getOne(id);
        List<DataModel> dataModels=new ArrayList<>();
        List<DataModel> dataModels1=dataModelRepoqitory.findAll();
        dataModels1.forEach(dataModel -> {
            dataModel.getEntity().forEach(entityModel -> {
                entityModel.getUser().forEach( user-> {
                    if(appUser == user){
                       dataModels.add(dataModel);
                    }
                });
            });
        });
        return dataModels;
    }
}
