package ch.duerri.middledemo.core.info;

import ch.duerri.middledemo.core.info.response.MiddleInfoDataResponse;
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
    public MiddleInfoDataResponse getInfoData(@PathVariable("id") String id) {
        return infoService.getInfoData(id);
    }
}
