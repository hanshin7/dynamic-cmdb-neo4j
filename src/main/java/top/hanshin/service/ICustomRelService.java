package top.hanshin.service;


import org.springframework.data.domain.Page;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CustomRel;

public interface ICustomRelService {
    Page<CustomRel> list(PageDTO dto);

    CustomRel deatil(String id);

    void create(CustomRel customRel);

    void update(CustomRel customRel);

    void delete(String id);
}
