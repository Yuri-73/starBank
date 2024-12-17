package com.example.starBank.services;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;

/**
 * @author Yuri-73
 */
@Service
public class PomXmlParser {
    private File xmlFile;

    public PomXmlParser() {
        this.xmlFile = new File("./pom.xml");
    }

    /**
     * Метод извлечения необходимой информации из файла pom.xml
     * @throws IOException Ошибки ввода-вывода
     * @throws ParserConfigurationException Ошибки в конфигурации
     * @throws SAXException  Ошибки формата и содержания входящих или исходящих XML-данных
     * @return Возвращает имя и версию проекта
     */
    public String getData() throws IOException, SAXException, ParserConfigurationException {
        String str = null;
        //Получение экземпляра DocumentBuilderFactory:
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);

        // Получаем корневой элемент (это <project>)
        Element root = document.getDocumentElement();

        String version = getElementValue(root, "version");
        String name = getElementValue(root, "name");
        StringBuilder stringBuilder = new StringBuilder().append("\"name\": ").append(getElementValue(root, "name")).append("\n").append("\"version\": ").append(getElementValue(root, "version"));
        return stringBuilder.toString();
    }

    /**
     * Метод извлечения объекта Node из листа и преобразования в текст
     * @param parent Корневой элемент
     * @param tagName Корневой элемент
     * @return Возвращает строку выбранного контекста
     */
    private static String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);

        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            return node.getTextContent();
        }
        return null;
    }
}
