package top.hanshin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.hanshin.model.CiNode;
import top.hanshin.model.node.Ci;
import top.hanshin.model.relation.HaveDynamicRel;
import top.hanshin.repository.node.CiRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.service.ICiService;
import top.hanshin.util.TreeUtil;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CiService implements ICiService {

    @Autowired
    private CiRepository ciRepository;
    @Autowired
    private HaveDynamicRelRepository haveDynamicRelRepository;

    @Override
    public List<CiNode> getTree() {
        List<Ci> nodes = ciRepository.tree();
        return TreeUtil.builTree(nodes);
    }

    public void saveNodeAndRelation(Ci ci) {
        ciRepository.save(ci);
        if(!StringUtils.isEmpty(ci.getParentCode())){
            Optional<Ci> parentNode = ciRepository.findById(ci.getParentCode());
            parentNode.ifPresent(parentCi -> {
                HaveDynamicRel<Ci, Ci> dynamicRel = new HaveDynamicRel<>(parentCi, ci, new Date());
                haveDynamicRelRepository.save(dynamicRel);
            });
        }
    }

    public boolean updateNode(Ci ci){
        Ci r = ciRepository.save(ci);
        return r == null ? false : true;
    }

    @Transactional
    public void deleteNode(String ciId){

    }
}
