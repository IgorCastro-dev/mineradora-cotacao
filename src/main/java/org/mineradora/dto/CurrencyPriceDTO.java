package org.mineradora.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyPriceDTO {
    private USDBRL usdbrl;
}
