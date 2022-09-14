package apitest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flowers")
public class Controller {

    @GetMapping("/getAll")
    public String getAll(){
        return "[{\"pk_flowerID\":1,\"flowerName\":\"Rosa\",\"flowerCountry\":\"Italy\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":2,\"flowerName\":\"Tulipán\",\"flowerCountry\":\"Spain\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":4,\"flowerName\":\"Margarita\",\"flowerCountry\":\"Germany\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":5,\"flowerName\":\"Clavel\",\"flowerCountry\":\"France\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":6,\"flowerName\":\"Tulipán\",\"flowerCountry\":\"France\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":7,\"flowerName\":\"Petunia12334455\",\"flowerCountry\":\"France\",\"flowerType\":\"EU\"}," +
                "{\"pk_flowerID\":21,\"flowerName\":\"Jazmin\",\"flowerCountry\":\"Italy\",\"flowerType\":\"EU\"}]";
    }


}