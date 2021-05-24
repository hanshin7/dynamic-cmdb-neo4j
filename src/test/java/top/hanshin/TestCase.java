package top.hanshin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.hanshin.repository.node.CiModelRepository;
import top.hanshin.repository.relation.HaveDynamicRelRepository;
import top.hanshin.util.CqlExecUtils;

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
    }

    @Test
    public void test2(){
    }

}
