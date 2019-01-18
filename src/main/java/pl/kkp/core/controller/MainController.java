package pl.kkp.core.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    public static final String INDEX_MESSAGE = "Index page.";

    @RequestMapping(method = RequestMethod.GET)
    public String index() {
        return INDEX_MESSAGE;
    }
}
