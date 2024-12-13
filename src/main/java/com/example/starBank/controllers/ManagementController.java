package com.example.starBank.controllers;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/management")
@RestController
public class ManagementController {

    /**
     * Метод очищения всех закешированных результатов по имени кеша
     */
    @PostMapping("/clear-caches")
    @CacheEvict(cacheNames = "Recommendations", allEntries = true)
    public void clearCache() {}
}
