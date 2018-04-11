package com.pulsethought.echopost;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.Enumeration;
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
    String recievePost(HttpServletRequest request) throws Exception {
        StringBuilder sb = new StringBuilder();

        sb.append("Headers:").append("\r\n");

        Enumeration<String> headerNames = request.getHeaderNames();

        String header;
        while ((header = headerNames.nextElement()) != null)
            sb
                    .append(header)
                    .append(":")
                    .append(request.getHeader(header))
                    .append("\r\n");

        sb.append("\r\n<br\\>");
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null)
                sb.append(new String(line.getBytes(), "UTF-8"));
        } catch (Exception e) {
            sb.append("Error reading body");
        }

        sb.append("\r\n<br\\>");
        sb.append("Parts:<br\\>");
        for (Part part : request.getParts()) {
            for (String headerName : part.getHeaderNames()) {
                sb
                        .append(headerName)
                        .append(":")
                        .append(part.getHeader(headerName))
                        .append("\n<br\\>");
            }
            sb.append("ContentType :").append(part.getContentType());
            sb.append("Size :").append(part.getSize());
            InputStream is = part.getInputStream();

            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            String content = s.hasNext() ? s.next() : "";

            sb.append(content);
            sb.append("\r\n<br\\>");

        }

        logs.add(sb.toString());

        return sb.toString();
    }
}
