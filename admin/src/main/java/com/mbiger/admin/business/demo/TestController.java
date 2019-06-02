package com.mbiger.admin.business.demo;

import com.mbiger.admin.web.base.AbstractBaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController extends AbstractBaseController {

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model) {


        return "index";
    }

    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginIndex() {
        return "login";
    }

    @RequestMapping(value = {"/test/{path1}"}, method = RequestMethod.GET)
    public String test1(@PathVariable String path1) {
        return "pages" + "/" + path1;
    }

    @RequestMapping(value = {"/test/{path1}/{path2}"}, method = RequestMethod.GET)
    public String test2(@PathVariable String path1, @PathVariable String path2) {
        return "pages" + "/" + path1 + "/" + path2;
    }

    @RequestMapping(value = {"/test/{path1}/{path2}/{path3}"}, method = RequestMethod.GET)
    public String test3(@PathVariable String path1, @PathVariable String path2, @PathVariable String path3) {
        return "pages" + "/" + path1 + "/" + path2 + "/" + path3;
    }
}
