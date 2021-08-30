package team.kelly.kellyserver.category.service;

import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.CategorySearchInfoDto;

import java.util.List;

@Slf4j
@Service
public class CategoryService {


    public String getCategoryResult(String user, String category, CategorySearchInfoDto infoVO) {
        return getInformationFromPython(user, category, infoVO.getArgs());
    }

    //프로젝트 기준 경로 파일 이름 반환 (수정 필요)
    public String getCategoryFilename(String user, String category) {
        return "src/main/java/team/kelly/kellyserver/category/pyresource/" + user + "/" + category + ".py";
    }

    //파이썬 args 넘기기 전 Str형 전처리
    public String preArgsToStr(List<String> args) {
        String str = "[";
        for (String item : args) {
            str += "'" + item + "', "; //앞 뒤로 문자처리
        }
        str += "]";
        return str;
    }

    //파이썬 파일에서 데이터 가져옴
    public String getInformationFromPython(String user, String category, List<String> args) {

        try (Interpreter interp = new SharedInterpreter()) {
            interp.eval("args = " + preArgsToStr(args));

            log.info(String.valueOf(preArgsToStr(args)));

            interp.runScript(getCategoryFilename(user, category));
            interp.eval("from java.lang import System");
            Object output = interp.getValue("output");

            return output.toString();

        } catch (JepException e) {
            e.printStackTrace();
            return "Python File Internal Error";
        }
    }
}
