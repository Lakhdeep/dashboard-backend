package controllers;

import com.google.inject.Inject;
import models.TravisLog;
import play.*;
import play.mvc.*;

import service.TravisLogService;
import service.impl.TravisLogServiceImpl;
import views.html.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Application extends Controller {
    @Inject
    private TravisLogServiceImpl service;

    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result getLog() {
        TravisLog travisLog = service.findOneTravisLog();
        String everything = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(travisLog.getPath()));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ok(index.render(everything));
    }

}
