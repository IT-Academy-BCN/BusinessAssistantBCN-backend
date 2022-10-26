package apitest.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/test")
public class Controller {

    @GetMapping(value = "/getFlowers", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAll(){
        return "[{\"pk_flowerID\":1,\"flowerName\":\"Rosa\",\"flowerCountry\":\"Italy\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":2,\"flowerName\":\"Tulipán\",\"flowerCountry\":\"Spain\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":4,\"flowerName\":\"Margarita\",\"flowerCountry\":\"Germany\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":5,\"flowerName\":\"Clavel\",\"flowerCountry\":\"France\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":6,\"flowerName\":\"Tulipán\",\"flowerCountry\":\"France\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":7,\"flowerName\":\"Petunia12334455\",\"flowerCountry\":\"France\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":21,\"flowerName\":\"Jazmin\",\"flowerCountry\":\"Italy\",\"flowerType\":\"EU\"}]";
    }

    @GetMapping(value = "/ccae", produces = "application/json; charset=UTF-8")
    public String getGencatData() throws URISyntaxException, IOException {

        Path path = Paths.get(Objects.requireNonNull(Controller.class.getClassLoader().getResource("ccaeValidData.json")).toURI());
        return Files.readAllLines(path, StandardCharsets.UTF_8).get(0);
    }
}