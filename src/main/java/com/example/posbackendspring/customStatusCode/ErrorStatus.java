package com.example.posbackendspring.customStatusCode;

import com.example.posbackendspring.dto.CustomerStatus;
import com.example.posbackendspring.dto.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorStatus implements CustomerStatus, ItemStatus {
    private Integer Status;
    private String statusMessage;
}