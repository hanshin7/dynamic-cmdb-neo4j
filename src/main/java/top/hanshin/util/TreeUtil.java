package top.hanshin.util;

import org.springframework.util.StringUtils;
import top.hanshin.model.CiNode;
import top.hanshin.model.node.Ci;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
   // private List<Ci> ciList;

    private TreeUtil() {
    }

    public static List<CiNode> builTree(List<Ci> ciList){

        List<CiNode> tree =new  ArrayList<>();
        for(CiNode node : getRootNode(ciList)) {
            node=buildChilTree(ciList, node);
            tree.add(node);
        }
        return tree;
    }

    //递归，建立子树形结构
    private static CiNode buildChilTree(List<Ci> ciList, CiNode pNode){
        List<CiNode> children =new  ArrayList<>();
        for(Ci node : ciList) {
            if(pNode.getCode().equals(node.getParentCode())) {
                CiNode ciNode = new CiNode();
                ciNode.setId(node.getId());
                ciNode.setCode(node.getCode());
                ciNode.setParentCode(node.getParentCode());
                ciNode.setName(node.getName());
                ciNode.setEndItem(node.getEndFlag());
                children.add(buildChilTree(ciList, ciNode));
            }
        }
        pNode.setChildren(children);
        return pNode;
    }

    //获取根节点
    private static List<CiNode> getRootNode(List<Ci> ciList) {
        List<CiNode> rootList =new  ArrayList<>();
        for(Ci node : ciList) {
            if(StringUtils.isEmpty(node.getParentCode())) {
                CiNode ciNode = new CiNode();
                ciNode.setId(node.getId());
                ciNode.setCode(node.getCode());
                ciNode.setParentCode(node.getParentCode());
                ciNode.setName(node.getName());
                ciNode.setEndItem(node.getEndFlag());
                rootList.add(ciNode);
            }
        }
        return rootList;
    }
}
