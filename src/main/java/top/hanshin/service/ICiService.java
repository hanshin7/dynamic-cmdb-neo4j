package top.hanshin.service;


import top.hanshin.model.CiNode;
import top.hanshin.model.node.Ci;

import java.util.List;

public interface ICiService {

    List<CiNode> getTree();

    void saveNodeAndRelation(Ci ci);

    boolean updateNode(Ci ci);

    void deleteNode(String id);
}
