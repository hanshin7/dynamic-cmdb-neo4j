package top.hanshin.service;


import top.hanshin.model.CiModelVO;
import top.hanshin.model.node.CiModel;

import java.util.List;
import java.util.Map;

public interface ICiModelService {

    List<CiModelVO> getTree();

    void saveNodeAndRelation(CiModel ciModel);

    boolean update(CiModel ciModel);

    void delete(String id);

    CiModel detail(String id);

    Map<String, Object> getModelProps(String id);
}
