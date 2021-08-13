package team.kelly.kellyserver.category.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Test
    public void preArgsToStr_원소가_여러개일때_잘_작동되는지(){

        List<String> given = new ArrayList<>(Arrays.asList("aaa", "bbb", "ccc"));

        String actual = categoryService.preArgsToStr(given);

        Assertions.assertEquals(actual, "['aaa', 'bbb', 'ccc', ]");

    }

    @Test
    public void preArgsToStr_원소가_한개일때_잘_작동되는지(){

        List<String> given = new ArrayList<>(Arrays.asList("aaa"));

        String actual = categoryService.preArgsToStr(given);

        Assertions.assertEquals(actual, "['aaa', ]");

    }
}
