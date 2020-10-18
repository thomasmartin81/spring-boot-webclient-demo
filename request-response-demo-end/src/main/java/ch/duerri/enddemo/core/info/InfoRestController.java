package ch.duerri.enddemo.core.info;

import ch.duerri.enddemo.core.info.response.EndInfoDataResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/info")
public class InfoRestController {

    private final InfoService infoService;

    public InfoRestController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/{id}")
    public EndInfoDataResponse getInfoData(@PathVariable("id") String id) {
        return infoService.getInfoData(id);
    }

    private String getInfoById(String id) {
        return id + "-end-info";
    }
}
