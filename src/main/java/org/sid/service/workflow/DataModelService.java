package org.sid.service.workflow;

import org.sid.entities.DataModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DataModelService {
    void CreateDataModel(DataModel m);
    List<DataModel> getAllDataModel();
    void Delete(DataModel d);
    void update(DataModel d);
    DataModel getDataModelById(Long id);
    List<DataModel> getDataModelListPerUser(Long id);
}
