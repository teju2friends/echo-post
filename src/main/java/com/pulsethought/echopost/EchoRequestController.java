package com.pulsethought.echopost;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
public class EchoRequestController {


    private static List<String> logs = new LinkedList<>();

    @RequestMapping("/")
    @ResponseBody
    public String welcome() {
        StringBuilder sb = new StringBuilder("Logs<br/><form action=\"/reset\"><input type=\"submit\" value=\"Reset\"></form><br/>");
        for (int i = 0; i < logs.size(); i++) {
            sb.append(i).append(".<br/>").append(logs.get(i)).append("<br/><hr/>");
        }

        return sb.toString();
    }

    @RequestMapping("/reset")
    public String reset() {
        logs.clear();
        return "redirect:/";
    }

    @RequestMapping(value = "/listener", method = RequestMethod.POST)
    @ResponseBody
    String recievePost(@RequestBody Object data) {
        String log = data.toString();

        logs.add(log);

        return log;
    }
}
