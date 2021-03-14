package com.example.springbootjsonschema.corona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Passport {
    private Boolean vaccinationProcessBegun;
    private Boolean vaccinationProcessEnded;
    private Boolean testedNegative;
    private String testDate;
    private Boolean hasBeenSickWithCorona;
}
