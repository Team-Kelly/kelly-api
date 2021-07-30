package team.kelly.kellyserver.category.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kelly.kellyserver.category.dto.CategorySearchInfoDto;
import team.kelly.kellyserver.category.service.CategoryService;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(path = "/{user}/{category}")
    @ApiOperation(value="커스텀 카테고리 api 호출", notes="커스텀하게 만들어진 카테고리 api를 통해 데이터를 받아온다.")
    public ResponseEntity<String> getCategoryInfo(@PathVariable String user, @PathVariable String category, @RequestBody CategorySearchInfoDto infoVO){
        return ResponseEntity.ok(categoryService.getCategoryInfo(user, category, infoVO));
    }
}
