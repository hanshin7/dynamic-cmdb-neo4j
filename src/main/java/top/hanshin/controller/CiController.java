package top.hanshin.controller;

import com.sun.istack.internal.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.hanshin.model.CiNode;
import top.hanshin.model.node.Ci;
import top.hanshin.service.ICiService;
import top.hanshin.util.R;

import java.util.List;

@Api(tags="CI配置库")
@Validated
@RestController
@RequestMapping("/ci")
public class CiController {

    @Autowired
    private ICiService ciService;

    @ApiOperation(value="查询菜单树")
	@GetMapping("/tree")
	public R<List<CiNode>> tree() {
	    return R.data(ciService.getTree());
	}

    @ApiOperation(value="新增")
	@PostMapping("/insert")
	public R<Void> insert(@RequestBody Ci ci) {
        ciService.saveNodeAndRelation(ci);
	    return R.status(true);
	}

    @ApiOperation(value="修改")
    @PostMapping("/update")
	public R<Void> update(@RequestBody Ci ci) {
	    return R.status(ciService.updateNode(ci));
	}

    @ApiOperation(value="删除")
	@DeleteMapping("/delete")
	public R<Void> delete(@NotNull @RequestParam(value="id") String ciId) {
        ciService.deleteNode(ciId);
	    return R.status(true);
	}

}
