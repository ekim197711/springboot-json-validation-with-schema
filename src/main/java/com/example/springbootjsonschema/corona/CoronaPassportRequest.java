package com.example.springbootjsonschema.corona;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoronaPassportRequest {
    private Passport passport;
    private Person person;
}
