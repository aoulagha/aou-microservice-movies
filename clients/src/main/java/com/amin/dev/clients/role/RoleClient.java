package com.amin.dev.clients.role;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "role",
        url = "${clients.role.url}"
)
public interface RoleClient {

    @PostMapping(path = "/api/manager" +
            "/role/actor")
    public Integer registerActorRole
            (@RequestBody ActorRequest request);

    @PostMapping(path = "/api/manager" +
            "/role/director")
    public Integer registerDirectorRole
            (@RequestBody DirectorRequest request);
}
