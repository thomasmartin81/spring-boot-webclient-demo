package ch.duerri.frontdemo.core.info;

import ch.duerri.frontdemo.core.info.response.FrontInfoDataResponse;
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
    public FrontInfoDataResponse getInfoData(@PathVariable("id") String id) {
        return infoService.getInfoData(id);
    }
}
