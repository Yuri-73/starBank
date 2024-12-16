package com.example.starBank.controllers;

import com.example.starBank.services.PomXmlParser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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
    @PostMapping("/info")
    public String getInfo() throws IOException, ParserConfigurationException, SAXException {
        return pomXmlParser.getData();
    }
}
