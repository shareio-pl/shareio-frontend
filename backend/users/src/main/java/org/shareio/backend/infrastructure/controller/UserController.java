package org.shareio.backend.infrastructure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    @RequestMapping(value = "/employees", method = RequestMethod.GET, produces = "application/json")
    public List<String> firstPage() {
        List<String> strings = new ArrayList<>();
        strings.add("a");
        strings.add("b");
        return strings;
    }
}
