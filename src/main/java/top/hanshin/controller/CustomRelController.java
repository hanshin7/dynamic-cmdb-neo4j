package top.hanshin.controller;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hanshin.model.CustomRelDTO;
import top.hanshin.model.CustomRelUpdateDTO;
import top.hanshin.model.PageDTO;
import top.hanshin.model.node.CustomRel;
import top.hanshin.service.impl.CustomRelService;
import top.hanshin.util.R;

import java.util.List;

@Api(tags="关联关系管理")
@Validated
@RestController
@RequestMapping("/rel")
public class CustomRelController {

    @Autowired
    CustomRelService customRelService;

    @ApiOperation(value="列表")
    @GetMapping("/list")
    public R<Page<CustomRel>> list(PageDTO dto) {
        return R.data(customRelService.list(dto));
    }

    @ApiOperation(value="查询详情")
    @GetMapping("/detail")
    public R<CustomRel> detail(@RequestParam("id") String id) {

        return R.data(customRelService.deatil(id));
    }

    @ApiOperation(value="新增")
	@PostMapping("/insert")
	public R<String> insert(@RequestBody CustomRelDTO dto) {

        CustomRel customRel = new CustomRel();
        BeanUtils.copyProperties(dto, customRel);
        customRelService.create(customRel);
	    return R.data(customRel.getId());
	}

    @ApiOperation(value="修改")
    @PostMapping("/update")
	public R<Void> update(@RequestBody CustomRelUpdateDTO dto) {
        CustomRel customRel = new CustomRel();
        BeanUtils.copyProperties(dto, customRel);
        customRelService.update(customRel);
        return R.status(true);
	}

    @ApiOperation(value="删除")
	@DeleteMapping("/delete")
	public R<Void> delete(@NotNull @RequestParam(value="id") String id) {
        customRelService.delete(id);
	    return R.status(true);
	}

}
