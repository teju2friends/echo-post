package com.pulsethought.echopost;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class EchoRequestController {


    private static List<String> logs = new LinkedList<>();

    @RequestMapping("/")
    public String welcome() {

        StringBuilder sb = new StringBuilder("Logs<br/>");
        for (int i = 0; i < logs.size(); i++) {
            sb.append(i).append(".<br/>").append(logs.get(i)).append("<br/><hr/>");
        }

        return sb.toString();
    }

    @RequestMapping("/reset")
    public String reset() {
        logs.clear();
        return "Logs cleared";
    }

    @RequestMapping(value = "/listener", method = RequestMethod.POST)
    String recievePost(@RequestBody Object data) {
        String log = data.toString();

        logs.add(log);

        return log;
    }
}
