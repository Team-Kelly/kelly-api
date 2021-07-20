package team.kelly.kellyserver.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import team.kelly.kellyserver.category.dto.CategorySearchInfoDto;
import team.kelly.kellyserver.category.service.CategoryService;

@Slf4j
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping(method = RequestMethod.POST, path = "/{user}/{category}")
    public String getCategoryInfo(@PathVariable String user, @PathVariable String category, @RequestBody CategorySearchInfoDto infoVO){
        return categoryService.getCategoryInfo(user, category, infoVO);
    }
}
