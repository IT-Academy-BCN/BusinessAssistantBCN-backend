package com.businessassistantbcn.gencat.adapters;

import com.businessassistantbcn.gencat.dto.output.RaiscResponseDto;
import com.businessassistantbcn.gencat.utils.json.deserializer.JsonDeserializerUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;


import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public abstract class GencatJsonMapper {

    protected final ObjectMapper mapper;

    protected final JsonDeserializerUtils jsonUtils;

    protected GencatJsonMapper(ObjectMapper mapper) {
        this.mapper = mapper;
        jsonUtils = new JsonDeserializerUtils(mapper);
    }

    /*
    Expected: data field is an array where each element is a representation of one "announcement"/convocatoria
    registered in RAISC.
    Each element is another array whose elements are "attributes" of the announcement.
    Each "attribute" can be interpreted if you check meta.columns (in the json url source):
            specifies index and the concept associated to the element's value
     */
    protected List<RaiscResponseDto> mapAllRaiscsWantedInfoFromGencat(String jsonGencat) throws IOException {
        final String DATA_FIELD = "data";
        jsonUtils.setJson(jsonGencat);
        JsonNode allDataNode = jsonUtils.getNodeByName(DATA_FIELD);
        if(allDataNode.isArray()){
            return interpretGencatData(allDataNode);
        }else {
            String msg = "all announcements are no longer provided as array";
            log.debug(msg);
            throw new IOException(msg);
        }
    }

    protected List<RaiscResponseDto> interpretGencatData(JsonNode allDataArray) throws IOException {
        Iterator<JsonNode> allDataIterator = allDataArray.elements();
        List<RaiscResponseDto> raiscResponses = new LinkedList<>();
        while(allDataIterator.hasNext()){
            JsonNode oneAnnouncementNode = allDataIterator.next();
            if(oneAnnouncementNode.isArray()){
                raiscResponses.add(interpretGencatAnnouncementArray(oneAnnouncementNode));
            }else {
                String msg = "one announcement's values are no longer provided as array";
                log.debug(msg);
                throw new IOException(msg);
            }
        }
        return raiscResponses;
    }

    protected RaiscResponseDto interpretGencatAnnouncementArray(JsonNode oneAnnouncementArray) throws IOException {
        String[] announcementArray = jsonUtils.parseNonNullNodeArrayToStringArray(oneAnnouncementArray);
        return new OneAnnouncementGencat(announcementArray).toRaiscResponseDto();
    }

    public static class OneAnnouncementGencat {
        private final String[] announcement;

        public OneAnnouncementGencat(String[] announcementArray) throws IOException {
            if(announcementArray.length != 70){
                String msg= "expected 70 elems from source";
                log.debug(msg);
                throw new IOException(msg);
            }
            this.announcement = announcementArray;
        }

        //not tested due so simple: DO NOT MODIFY unless array structure changes
        public RaiscResponseDto toRaiscResponseDto() {
            RaiscResponseDto raiscResponse = new RaiscResponseDto();
            raiscResponse.setIdRaisc(announcement[1]);
            raiscResponse.setEntity(announcement[11]);
            raiscResponse.setRaiscType(announcement[13]);
            raiscResponse.setAnyo(announcement[14]);
            raiscResponse.setTitleCA(announcement[15]);
            raiscResponse.setTitleES(announcement[16]);
            raiscResponse.setBasesCA(announcement[25]);
            raiscResponse.setBasesES(announcement[26]);
            raiscResponse.setSubventionType(announcement[28]);
            raiscResponse.setIdRegion(announcement[29]);
            raiscResponse.setRegion(announcement[30]);
            raiscResponse.setIdScope(announcement[35]);
            raiscResponse.setScope(announcement[36]);
            raiscResponse.setIdSector(announcement[39]);
            raiscResponse.setSector(announcement[40]);
            raiscResponse.setOrigin(announcement[42]);
            raiscResponse.setMaxBudgetPublish(announcement[43]);
            raiscResponse.setMaxBudgetUE(announcement[44]);
            raiscResponse.setMaxBudge(announcement[45]);
            raiscResponse.setStartDate(announcement[46]);
            raiscResponse.setEndDate(announcement[47]);
            raiscResponse.setUrlRequest(announcement[50]);
            raiscResponse.setDescription(announcement[65]);
            return raiscResponse;
        }
    }
}
