package com.businessassistantbcn.gencat.adapters;

import com.businessassistantbcn.gencat.config.PropertiesConfig;
import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.proxy.HttpProxy;
import com.businessassistantbcn.gencat.utils.resources.ResourcesUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class GencatDataAdapterTest {

    @Autowired
    private GencatDataAdapter adapter;

    @MockBean
    private HttpProxy proxy;

    @MockBean
    private PropertiesConfig config;

    @Value("classpath:json/someRaiscData.json")
    private Resource someRaiscDataResource;

    @Value("classpath:json/raiscDataChanged.json")
    private Resource raiscDataChanged;


    @Test
    @DisplayName("Gencat find all raisc test.")
    void findAllRaiscTest() {
        when(config.getDsRaisc()).thenReturn("https://stackoverflow.com/");
        String someRaiscData = new ResourcesUtils(someRaiscDataResource).asString();
        //System.out.println(someRaiscData);
        when(proxy.getRequestData(any(URL.class), eq(String.class))).thenReturn(Mono.just(someRaiscData));

        Flux<RaiscResponseDto> source = adapter.findAllRaisc();

        StepVerifier.create(source)
                .assertNext(first -> assertEquals(expectedFirst(), first))
                .expectNextCount(5)
                .assertNext(last -> assertEquals(expectedLast(), last))
                .verifyComplete();
    }

    @SneakyThrows(IOException.class)
    private RaiscResponseDto expectedFirst() {
        String[] data = new String[]{"row-igf4.y4er-s6mv", "00000000-0000-0000-3BDF-CA7CD820BDD5", "0", "1665460926", null, "1665460926", null, "{ }", "6800-22-027", "652027", "6800", "AGÈNCIA DE GESTIÓ D'AJUTS UNIVERSITARIS I DE RECERCA (AGAUR)", "O", "Subvenció amb concurrència", "2022", "Ajuts a les associacions d'estudiantat universitari per al curs 2022-2023 (AEU)", "Ayudas a las asociaciones de estudiantado universitario para el curso 2022-2023 (AEU)", "011", "DOGC. D.O. DE LA GENERALITAT DE CATALUNYA", "2022-10-10T00:00:00", "https://dogc.gencat.cat/ca/document-del-dogc/?documentId=939420", "RESOLUCIÓ REU/2555/2021, de 2 d'agost", "RESOLUCIÓ REU/2555/2021, de 2 d'agost, per la qual s'aproven les bases reguladores dels ajuts a les associacions d'estudiantat universitari de Catalunya (AEU)", "011", "DOGC. D.O. DE LA GENERALITAT DE CATALUNYA", "https://dogc.gencat.cat/ca/document-del-dogc/?documentId=908267", "https://dogc.gencat.cat/es/document-del-dogc/index.html?documentId=908267", "SUBV", "Subvenció i lliurament dinerari sense contraprest.", "ES51", "CATALUNYA", "14", " EDUCACIÓ", "09", "EDUCACIÓ I ESPORT", "10", "Educació", "JSA", "Persones jurídiques que no desenvolupen activitat econòmica", "85.4", "Educació postsecundària", "PCA", "Pressupostos Generals de les Comunitats Autònomes", "26636.4", "0", "26636.4", "Des de l’endemà de la publicació en el diari oficial de la convocatòria inicial", "03/11/2022", "2022-11-03T00:00:00", "14:00:00", null, "2023-09-29T00:00:00", null, null, null, null, null, null, null, null, "002", "NUL: Quan no existint desigualtats de partida amb relació a la igualtat d'oportunitats i de tracte entre dones i homes, no es prevegi modificació d'aquesta situació", "1", "Sí publicable", null, "Ajuts a les associacions d'estudiantat universitari per al curs 2022-2023 (AEU)", "001", "Autonòmica", "UR", "Departament de Recerca i Universitats" };
        return new GencatJsonMapper.OneAnnouncementGencat(data).toRaiscResponseDto();
    }

    @SneakyThrows(IOException.class)
    private RaiscResponseDto expectedLast() {
        String[] data = new String[]{ "row-sm63_w48e~rsqg", "00000000-0000-0000-7FD4-26C883B75398", "0", "1665460926", null, "1665460926", null, "{ }", "7025-22-017", "651875", "7025", "AGÈNCIA PER A LA COMPETITIVITAT DE L'EMPRESA (ACCIÓ)", "O", "Subvenció amb concurrència", "2022", "RESOLUCIO EMT/ /2022, , per la qual s'obre la convocatoria per a l'any 2022 de la linia d'ajuts a les xarxes d'inversors privats d'ACCIO (ref. BDNS ).", "RESOLUCION EMT/ /2022, , por la que se abre la convocatoria para el ano 2022 de la linea de ayudas a las redes de inversores privados de ACCIO (ref. BDNS ).", "011", "DOGC. D.O. DE LA GENERALITAT DE CATALUNYA", "2022-10-10T00:00:00", "https://dogc.gencat.cat/ca/document-del-dogc/?documentId=939448", "RESOLUCIÓ EMT/3460/2021", "RESOLUCIÓ EMT/3460/2021, de 18 de novembre, per la qual s'aproven les bases reguladores de la línia d'ajuts a les xarxes d'inversors privats d'ACCIÓ", "011", "DOGC. D.O. DE LA GENERALITAT DE CATALUNYA", "https://portaldogc.gencat.cat/utilsEADOP/PDF/8550/1879859.pdf", "https://portaldogc.gencat.cat/utilsEADOP/PDF/8550/1879860.pdf", "SUBV", "Subvenció i lliurament dinerari sense contraprest.", "ES51", "CATALUNYA", "36", "INDÚSTRIA", "18", "INDÚSTRIA I ENERGIA", "13", "Indústria i Energia", "GRA; JSA; PFA", "Gran empresa; Persones jurídiques que no desenvolupen activitat econòmica; PYME i Pers. físiques que desenvolupen activitat econòmica", "N", "ACTIVITATS ADMINISTRATIVES I SERVEIS AUXILIARS", "PCA", "Pressupostos Generals de les Comunitats Autònomes", "175000", "0", "175000", "Des de l’endemà de la publicació en el diari oficial de la convocatòria inicial", "26/10/2022", "2022-10-26T00:00:00", "14:00:00", null, "2023-02-01T00:00:00", "SÍ", "MINIM", "Per aplic. Reg. UE d'ex. sol·lic. d'aut. per imp. de l'ajuda", "1", "REG (UE) 1407/2013, DE 18 DE DESEMBRE, de minimis", null, null, null, "002", "NUL: Quan no existint desigualtats de partida amb relació a la igualtat d'oportunitats i de tracte entre dones i homes, no es prevegi modificació d'aquesta situació", "1", "Sí publicable", null, "Ajuts a les Xarxes d'inversors privats d'ACCIÓ 2022", "001", "Autonòmica", "IU", "Departament d'Empresa i Treball" };
        return new GencatJsonMapper.OneAnnouncementGencat(data).toRaiscResponseDto();
    }

    //proxy failure not tested -> do it when circuitbreaker implemented

    @Test
    @DisplayName("Adapter propagates error when url malformed test.")
    void malformedUrlTest() {
        when(config.getDsRaisc()).thenReturn("htxtps://stackoverflow  .com/");
        String errorMessage = "URL in config malformed";
        when(config.getErrorUrlStored()).thenReturn(errorMessage);
        String someRaiscData = new ResourcesUtils(someRaiscDataResource).asString();
        when(proxy.getRequestData(any(URL.class), eq(String.class))).thenReturn(Mono.just(someRaiscData));

        Flux<RaiscResponseDto> source = adapter.findAllRaisc();

        StepVerifier.create(source)
                .expectErrorMessage(errorMessage)
                .verify();
    }

    @Test
    @DisplayName("Adapter propagates error when unable mapping due changes in source json.")
    void gencatChangesJsonStrucureTest() {
        when(config.getDsRaisc()).thenReturn("https://stackoverflow.com/");
        String updatedJsonStructure = new ResourcesUtils(raiscDataChanged).asString();
        when(proxy.getRequestData(any(URL.class), eq(String.class))).thenReturn(Mono.just(updatedJsonStructure));
        String errorMessage = "Source client has changed expected json's structure";
        when(config.getErrorJsonChange()).thenReturn(errorMessage);

        Flux<RaiscResponseDto> source = adapter.findAllRaisc();

        StepVerifier.create(source)
                .expectErrorMessage(errorMessage)
                .verify();
    }
}
