package top.hanshin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hanshin.model.CiModelDTO;
import top.hanshin.model.CiModelUpdateDTO;
import top.hanshin.model.CiModelVO;
import top.hanshin.model.node.CiModel;
import top.hanshin.service.ICiModelService;
import top.hanshin.util.R;

import java.util.List;
import java.util.Map;

@Api(tags="模型管理")
@Validated
@RestController
@RequestMapping("/ci-model")
public class CiModelController {

    @Autowired
    private ICiModelService ciModelService;

    @ApiOperation(value="模型菜单树")
	@GetMapping("/tree")
	public R<List<CiModelVO>> tree() {
	    return R.data(ciModelService.getTree());
	}

    @ApiOperation(value="查询详情")
    @GetMapping("/detail")
    public R<CiModel> detail(@RequestParam("id") String id) {

        return R.data(ciModelService.detail(id));
    }

    @ApiOperation(value="查询模型配置")
    @GetMapping("/model-props")
    public R<Map<String, Object>> modelProps(@RequestParam("id") String id) {

        return R.data(ciModelService.getModelProps(id));
    }

    @ApiOperation(value="新增")
	@PostMapping("/insert")
	public R<Void> insert(@RequestBody CiModelDTO ciModelDTO) {
        CiModel ciModel = null;
        try {
            ciModel = new CiModel().setCode(ciModelDTO.getCode())
                    .setParentCode(ciModelDTO.getParentCode())
                    .setEndFlag(ciModelDTO.getEndFlag())
                    .setName(ciModelDTO.getName())
                    .setProps(new ObjectMapper().writeValueAsString(ciModelDTO.getProps()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ciModelService.saveNodeAndRelation(ciModel);
	    return R.status(true);
	}

    @ApiOperation(value="修改(节点属性)")
    @PostMapping("/update")
	public R<Void> update(@RequestBody CiModelUpdateDTO ciDTO) {
        CiModel ciModel = new CiModel().setEndFlag(ciDTO.getEndFlag())
                .setName(ciDTO.getName());
        ciModel.setId(ciDTO.getId());
        return R.status(ciModelService.update(ciModel));
	}

    @ApiOperation(value="修改(模型属性)")
    @PostMapping("/update-model")
    public R<Void> updateModel(@RequestBody CiModelUpdateDTO ciDTO) {
        CiModel ciModel = null;
        try {
            ciModel = new CiModel().setProps(new ObjectMapper().writeValueAsString(ciDTO.getProps()));
            ciModel.setId(ciDTO.getId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return R.status(ciModelService.update(ciModel));
    }

    @ApiOperation(value="删除")
	@DeleteMapping("/delete")
	public R<Void> delete(@NotNull @RequestParam(value="id") String ciId) {
        ciModelService.delete(ciId);
	    return R.status(true);
	}

}
