package ouhk.comps380f.controller;

import java.util.Hashtable;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ouhk.comps380f.model.LectureModel;

@Controller
@RequestMapping("lecture")
public class LectureController {

    private volatile long LECTURE_ID_SEQUENCE = 1;
    private Map<Long, LectureModel> lectureDatabase = new Hashtable<>();

    @RequestMapping(value = {"", "index"}, method = RequestMethod.GET)
    public String list(ModelMap model) {
        model.addAttribute("lectureDatabase", lectureDatabase);
        return "index";
    }
    
  
}
