package top.hanshin.util;

import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.data.neo4j.transaction.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import top.hanshin.model.node.CustomRel;

import java.util.ArrayList;
import java.util.HashMap;

public class CqlExecUtils {

    private CqlExecUtils() {
    }

    public static Session getSession(SessionFactory sessionFactory) {
        SessionHolder sessionHolder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
        if (sessionHolder == null) {
            Session session = sessionFactory.openSession();
            return session;
        }
        return sessionHolder.getSession();
    }

    public static Result query(SessionFactory sessionFactory, String cql){
        Session session = getSession(sessionFactory);
        return session.query(cql, new HashMap<>(),false);
    }

    public static boolean createRel(SessionFactory sessionFactory, String startId, String endId, CustomRel customRel) {
        Session session = getSession(sessionFactory);
        String ckeckCql = "MATCH (s:CiResource{id:'" + startId+ "'})-[r:" + customRel.getKey() + "]->(e:CiResource{id:'" + endId + "'}) RETURN r";
        Result r = session.query(ckeckCql, new HashMap<>());
        //已存在该关系
        if(((ArrayList)r.queryResults()).size() > 0){
            return false;
        }

        String cql = "MATCH (s:CiResource{id:'" + startId+ "'}) , (e:CiResource{id:'" + endId + "'}) CREATE (s) -[r:" + customRel.getKey()
                + "{key:'" + customRel.getKey() + "',name:'" + customRel.getName() + "'}" + "]->(e) RETURN r";
        r = session.query(cql, new HashMap<>());
        //创建失败返回结果为空
        if(((ArrayList)r.queryResults()).size() == 0){
            return false;
        }
        return true;
    }
}
