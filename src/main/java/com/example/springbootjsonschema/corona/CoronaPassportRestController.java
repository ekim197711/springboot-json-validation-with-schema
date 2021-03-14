package com.example.springbootjsonschema.corona;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.Set;

@RestController
@RequestMapping("/api/corona")
@Slf4j
public class CoronaPassportRestController {

    @PostMapping("/novalidation")
    public CoronaPassportRequest createPassport(@RequestBody CoronaPassportRequest request){
        log.info("Request: " + request);
        return request;
    }

    @PostMapping("/withvalidation")
    public CoronaPassportRequest createPassportValidation(@RequestBody String requestStr) throws JsonProcessingException {
        log.info("Request Json String: " + requestStr);
        InputStream schemaAsStream = CoronaPassportRestController.class.getClassLoader().getResourceAsStream("model/coronopassportrequest.schema.json");
        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);

        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategy.KEBAB_CASE);
        JsonNode jsonNode = om.readTree(requestStr);

        Set<ValidationMessage> errors = schema.validate(jsonNode);
        String errorsCombined = "";
        for (ValidationMessage error : errors) {
            log.error("Validation Error: {}", error);
            errorsCombined += error.toString() + "\n";
        }

        if (errors.size() > 0)
            throw new RuntimeException("Please fix your json! " + errorsCombined);

        CoronaPassportRequest request = om.readValue(requestStr, CoronaPassportRequest.class);
        log.info("Return this request: {}", request);
        return request;
    }
}
