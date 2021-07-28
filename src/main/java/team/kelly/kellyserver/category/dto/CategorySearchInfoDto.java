package team.kelly.kellyserver.category.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorySearchInfoDto {
    private int argc;
    private List<String> args;

}
