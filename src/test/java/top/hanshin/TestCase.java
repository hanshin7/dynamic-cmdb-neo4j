package top.hanshin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.hanshin.repository.node.CiModelRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.util.CqlExecUtils;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = App.class)
@RunWith(SpringRunner.class)
public class TestCase {

    @Autowired
    HaveDynamicRelRepository haveDynamicRelRepository;

    @Autowired
    CiModelRepository ciModelRepository;

    @Autowired
    SessionFactory sessionFactory;

    @Test
    public void test1(){

       //boolean r = CqlExecUtils.createRel(sessionFactory, "8b49293b-1da6-4ea4-bfdc-c68e7aecfd0a","6d1fb557-d8ac-4eae-b2b7-f981d7766be6","vv2");
       // System.out.println(r);
        String node = "CiResource";
        String nodeId = "4f7ef74a-a43a-4455-b390-1a2c48fdc116qq";
        String col = "hostName";
        String cql = "MATCH (n:" + node + "{id:'" + nodeId + "'}) RETURN properties(n) as n";
        Object r = CqlExecUtils.query(sessionFactory,cql);
        System.out.println(r == null ?  "": ((Map)((Map)r).get("n")).get("hostName"));
    }

    @Test
    public void test2(){
        CqlExecUtils.deleteRel(sessionFactory,"0119deb9-4c02-4c93-867c-8a7da2138bc5","4f7ef74a-a43a-4455-b390-1a2c48fdc116","connect");
    }

}
