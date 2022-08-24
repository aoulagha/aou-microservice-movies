package com.amin.dev.role;

import com.amin.dev.clients.role.ActorRequest;
import com.amin.dev.clients.role.DirectorRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/manager/role")
public class RoleController {

    private final ActingService actingService;

    private final DirectingService directingService;

    @PostMapping("/actor")
    public Integer registerActorRole
            (@RequestBody ActorRequest request) {
        log.info("Register Actor role" +
                " : " + request.roleName());

        Acting acting = actingService.addActorMovie(request);
        return acting.getId();
    }

    @PostMapping("/director")
    public Integer registerDirectorRole
            (@RequestBody DirectorRequest request) {
        log.info("Register Director " +
                "role");
        Directing directing =
                directingService.addDirectorMovie(request);
        return directing.getId();
    }
}
