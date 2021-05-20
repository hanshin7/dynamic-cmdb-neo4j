package top.hanshin.controller;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hanshin.constant.ErrorCode;
import top.hanshin.constant.SysConstance;
import top.hanshin.exception.CommonException;
import top.hanshin.service.ICiBusiModelService;
import top.hanshin.util.R;

import java.util.List;
import java.util.Map;

@Api(tags="CI配置库-实体对象")
@Validated
@RestController
@RequestMapping("/ci-model")
public class CiBusiModelController {

    @Autowired
    private ICiBusiModelService ciBusiModelService;

    @ApiOperation(value="查询列表")
	@GetMapping("/list")
	public R<List<Map<String, Object>>> list(PageRequest pageRequest) {
	    return R.data(ciBusiModelService.list(pageRequest));
	}

    @ApiOperation(value="查询")
    @GetMapping("/detail")
    public R<Map<String, Object>> detail(@RequestParam("id") String id) {
        return R.data(ciBusiModelService.detail(id));
    }

    @ApiOperation(value="新增")
	@PostMapping("/insert")
	public R<Map<String, Object>> insert(@RequestBody Map<String,Object> insertDto) {
	    if(!insertDto.containsKey(SysConstance.CI_KEY) ||
                StringUtils.isEmpty(insertDto.get(SysConstance.CI_KEY).toString())){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(), "缺少参数：ciKey");
        }
	    return R.data(ciBusiModelService.saveNodeAndRelation(insertDto));
	}

    @ApiOperation(value="修改")
    @PostMapping("/update")
	public R<Void> update(@RequestBody Map<String,Object> updateDto) {
        if(!updateDto.containsKey(SysConstance.FIELD_ID)){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(), "缺少参数：id");
        }
        ciBusiModelService.update(updateDto);
	    return R.status(true);
	}

    @ApiOperation(value="删除")
	@DeleteMapping("/delete")
	public R<Void> delete(@NotNull @RequestParam(value="id") String id) {
	    ciBusiModelService.delete(id);
	    return R.status(true);
	}

}
