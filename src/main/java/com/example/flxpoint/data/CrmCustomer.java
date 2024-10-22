package com.example.flxpoint.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrmCustomer {
    private Long id;
    private String fullName;
    private String contactEmail;
    private String primaryPhone;
    private String location;
}
