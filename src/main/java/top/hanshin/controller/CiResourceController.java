package top.hanshin.controller;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hanshin.constant.ErrorCode;
import top.hanshin.constant.SysConstance;
import top.hanshin.exception.CommonException;
import top.hanshin.model.PageDTO;
import top.hanshin.model.RelDTO;
import top.hanshin.model.RelDeleteDTO;
import top.hanshin.model.node.CiResource;
import top.hanshin.service.ICiResourceService;
import top.hanshin.util.R;

import java.util.Map;

@Api(tags="资源管理")
@Validated
@RestController
@RequestMapping("/ci")
public class CiResourceController {

    @Autowired
    private ICiResourceService ciResourceService;

    @ApiOperation(value="资源列表")
	@GetMapping("/list")
	public R<Page<Map<String, Object>>> list(PageDTO dto) {
	    return R.data(ciResourceService.list(dto));
	}

    @ApiOperation(value="查询")
    @GetMapping("/detail")
    public R<Map<String, Object>> detail(@RequestParam("id") String id) {
        return R.data(ciResourceService.detail(id));
    }

    @ApiOperation(value="新增")
	@PostMapping("/insert")
	public R<Map<String, Object>> insert(@RequestBody Map<String,Object> insertDto) {
	    if(!insertDto.containsKey(SysConstance.CI_KEY) ||
                StringUtils.isEmpty(insertDto.get(SysConstance.CI_KEY).toString())){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(), "缺少参数：ciKey");
        }
	    return R.data(ciResourceService.saveNodeAndRelation(insertDto));
	}

    @ApiOperation(value="新增关联")
    @PostMapping("/insert-rel")
    public R<Void> insertRel(@RequestBody RelDTO dto) {
        return R.status(ciResourceService.createRel(dto.getStartId(), dto.getEndId(), dto.getRelId()));
    }

    @ApiOperation(value="修改")
    @PostMapping("/update")
	public R<Void> update(@RequestBody Map<String,Object> updateDto) {
        if(!updateDto.containsKey(SysConstance.FIELD_ID)){
            throw new CommonException(ErrorCode.BAD_REQUEST.getCode(), "缺少参数：id");
        }
        ciResourceService.update(updateDto);
	    return R.status(true);
	}

    @ApiOperation(value="删除")
	@DeleteMapping("/delete")
	public R<Void> delete(@NotNull @RequestParam(value="id") String id) {
        ciResourceService.delete(id);
	    return R.status(true);
	}

    @ApiOperation(value="取消关联")
    @PostMapping("/delete-rel")
    public R<Void> deleteRel(@RequestBody RelDeleteDTO dto) {
        ciResourceService.deleteRel(dto.getStartId(), dto.getEndId(), dto.getRelName());
        return R.status(true);
    }

}
