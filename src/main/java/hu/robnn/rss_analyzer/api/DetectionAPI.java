package hu.robnn.rss_analyzer.api;

import hu.robnn.rss_analyzer.enums.UserRole;
import hu.robnn.rss_analyzer.model.CandidateHolder;
import hu.robnn.rss_analyzer.model.TagWithDepth;
import hu.robnn.rss_analyzer.model.UrlHolder;
import hu.robnn.rss_analyzer.service.DetectionService;
import hu.robnn.rss_analyzer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Component
@RestController("/detection")
@CrossOrigin
public class DetectionAPI {

    private static final Logger LOGGER = Logger.getLogger("DetectionAPI");

    private final DetectionService detectionService;
    private final UserService userService;

    @Autowired
    public DetectionAPI(DetectionService detectionService, UserService userService) {
        this.detectionService = detectionService;
        this.userService = userService;
    }

    @RequestMapping(path = "detection", method = RequestMethod.POST)
    public ResponseEntity<List<CandidateHolder>> detectForWebPage(@RequestBody UrlHolder urlHolder, @RequestHeader String authToken){
        userService.authenticate(authToken, UserRole.USER);
        return new ResponseEntity<>(detectionService.detectForUrlHolder(urlHolder), HttpStatus.OK);
    }

    @RequestMapping(path = "detection/setFeedable", method = RequestMethod.POST)
    public HttpEntity<?> selectFeedable(@RequestBody TagWithDepth tagWithDepth, @RequestHeader String authToken){
        userService.authenticate(authToken, UserRole.USER);
        detectionService.setNeededFeedAndStartChangeDetection(tagWithDepth);
        return HttpEntity.EMPTY;
    }

    @RequestMapping(path = "detection/changeInterval", method = RequestMethod.POST)
    public HttpEntity<?> changeInterval(@RequestParam("interval") Integer interval, @RequestHeader String authToken){
        userService.authenticate(authToken, UserRole.USER);
        detectionService.changeInterval(interval);
        return HttpEntity.EMPTY;
    }

    @RequestMapping(path = "detection/stopDetection", method = RequestMethod.POST)
    public HttpEntity<?> stopDetection(@RequestHeader String authToken){
        userService.authenticate(authToken, UserRole.USER);
        detectionService.stopDetection();
        return HttpEntity.EMPTY;
    }

    @RequestMapping(path = "detection/rss", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public HttpEntity<FileSystemResource> getRss(){
        File file = new File("output.xml");
        if(file.exists()){
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(new MediaType("application","xml"));
            httpHeaders.set("Content-Disposition", "inline; filename=" + "rss.xml");
            try {
                httpHeaders.setContentLength(fileSystemResource.contentLength());
            } catch (IOException e) {
                LOGGER.severe("Failed read the RSS file! \n" + e.getMessage());
                e.printStackTrace();
            }
            return new HttpEntity<>(fileSystemResource, httpHeaders);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
