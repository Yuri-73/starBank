package com.example.starBank.controllers;

import com.example.starBank.model.RecommendationWithRules;
import com.example.starBank.services.PomXmlParser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * @author Yuri-73
 */
@RequestMapping("/management")
@RestController
public class ManagementController {

    PomXmlParser pomXmlParser;

    public ManagementController(PomXmlParser pomXmlParser) {
        this.pomXmlParser = pomXmlParser;
    }

    /**
     * Метод очищения всех закешированных результатов по имени кеша
     */
    @Operation(summary = "Сброс выбранного кеша для обновления данных (этап-3 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Сброс кеша",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PomXmlParser.class)
                            )
                    )
            })
    @PostMapping("/clear-caches")
    @CacheEvict(cacheNames = "Recommendations", allEntries = true)
    public void clearCache() {}

    /**
     * Метод получения имени и версии проекта из файла pom.xml
     * @throws IOException Ошибки ввода-вывода
     * @throws ParserConfigurationException Ошибки в конфигурации
     * @throws SAXException  Ошибки формата и содержания входящих или исходящих XML-данных
     * @return Возвращает строку с именем и версией проекта
     */
    @Operation(summary = "Получение имени и версии проекта (этап-3 работы)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Имя и версия проекта",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PomXmlParser.class)
                            )
                    )
            })
    @PostMapping("/info")
    public String getInfo() throws IOException, ParserConfigurationException, SAXException {
        return pomXmlParser.getData();
    }
}
