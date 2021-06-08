package org.sid.service.workflow;

import org.sid.entities.DataModel;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface DataModelService {
    public void CreateDataModel(DataModel m);
    public List<DataModel> getAllDataModel();
    public void Delete(DataModel d);
    public void update(DataModel d);
    public DataModel getDataModelById(Long id);
    public List<DataModel> getDataModelListPerUser(Long id);
}
