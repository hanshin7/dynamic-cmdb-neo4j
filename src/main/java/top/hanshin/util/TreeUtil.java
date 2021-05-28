package top.hanshin.util;

import org.springframework.util.StringUtils;
import top.hanshin.model.CiModelVO;
import top.hanshin.model.node.CiModel;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    private TreeUtil() {
    }

    public static List<CiModelVO> builTree(List<CiModel> ciModelList){

        List<CiModelVO> tree =new  ArrayList<>();
        for(CiModelVO node : getRootNode(ciModelList)) {
            node=buildChilTree(ciModelList, node);
            tree.add(node);
        }
        return tree;
    }

    //递归，建立子树形结构
    private static CiModelVO buildChilTree(List<CiModel> ciModelList, CiModelVO pNode){
        List<CiModelVO> children =new  ArrayList<>();
        for(CiModel node : ciModelList) {
            if(pNode.getCode().equals(node.getParentCode())) {
                CiModelVO ciModelVO = new CiModelVO();
                ciModelVO.setId(node.getId());
                ciModelVO.setCode(node.getCode());
                ciModelVO.setParentCode(node.getParentCode());
                ciModelVO.setName(node.getName());
                ciModelVO.setEndItem(node.getEndFlag());
                children.add(buildChilTree(ciModelList, ciModelVO));
            }
        }
        pNode.setChildren(children);
        return pNode;
    }

    //获取根节点
    private static List<CiModelVO> getRootNode(List<CiModel> ciModelList) {
        List<CiModelVO> rootList =new  ArrayList<>();
        for(CiModel node : ciModelList) {
            if(StringUtils.isEmpty(node.getParentCode())) {
                CiModelVO ciModelVO = new CiModelVO();
                ciModelVO.setId(node.getId());
                ciModelVO.setCode(node.getCode());
                ciModelVO.setParentCode(node.getParentCode());
                ciModelVO.setName(node.getName());
                ciModelVO.setEndItem(node.getEndFlag());
                rootList.add(ciModelVO);
            }
        }
        return rootList;
    }
}
