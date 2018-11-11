package hu.robnn.rss_analyzer.api;

import hu.robnn.rss_analyzer.model.CandidateHolder;
import hu.robnn.rss_analyzer.model.TagWithDepth;
import hu.robnn.rss_analyzer.model.UrlHolder;
import hu.robnn.rss_analyzer.service.DetectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController("/detection")
public class DetectionAPI {

    private final DetectionService detectionService;

    @Autowired
    public DetectionAPI(DetectionService detectionService) {
        this.detectionService = detectionService;
    }

    @RequestMapping(path = "detection", method = RequestMethod.POST)
    public ResponseEntity<List<CandidateHolder>> detectForWebPage(@RequestBody UrlHolder urlHolder){
        return new ResponseEntity<>(detectionService.detectForUrlHolder(urlHolder), HttpStatus.OK);
    }

    @RequestMapping(path = "detection/setFeedable", method = RequestMethod.POST)
    public HttpEntity<?> selectFeedable(@RequestBody TagWithDepth tagWithDepth){
        detectionService.setNeededFeedAndStartChangeDetection(tagWithDepth);
        return HttpEntity.EMPTY;
    }

    @RequestMapping(path = "detection/changeInterval", method = RequestMethod.POST)
    public HttpEntity<?> changeInterval(@RequestParam("interval") Integer interval){
        detectionService.changeInterval(interval);
        return HttpEntity.EMPTY;
    }

}
