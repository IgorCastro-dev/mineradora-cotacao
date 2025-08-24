package org.mineradora.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mineradora.client.CurrencyPriceCliente;
import org.mineradora.dto.CurrencyPriceDTO;
import org.mineradora.dto.QuotationDto;
import org.mineradora.entity.QuotationEntity;
import org.mineradora.message.KafkaEvents;
import org.mineradora.repository.QuotationRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
public class QuotationService {

    @Inject
    public QuotationRepository quotationRepository;

    @Inject
    @RestClient
    public CurrencyPriceCliente currencyPriceCliente;

    @Inject
    public KafkaEvents kafkaEvents;

    public void getCurrencyPrice(){
        CurrencyPriceDTO currentPriceInfo = currencyPriceCliente.getPricePair("USD-BRL");

        if (updateCurentPrice(currentPriceInfo)){
            kafkaEvents.sendMessage(QuotationDto.builder()
                    .currencyPrice(new BigDecimal(currentPriceInfo.getUsdbrl().getBid()))
                    .date(new Date())
                    .build()
            );
        }
    }

    private boolean updateCurentPrice(CurrencyPriceDTO currentPriceInfo) {
        Optional<QuotationEntity> lastQuotation = quotationRepository.findLastQuotation();
        BigDecimal currentPrice = new BigDecimal(currentPriceInfo.getUsdbrl().getBid());
        if (lastQuotation.isEmpty()) {
            saveQuotation(currentPriceInfo);
            return true;
        } else if (currentPrice.floatValue() > lastQuotation.get().getCurrentPrice().floatValue()) {
            saveQuotation(currentPriceInfo);
            return true;
        }
        return false;
    }

    private void saveQuotation(CurrencyPriceDTO currentPriceInfo) {
        QuotationEntity quotationEntity = new QuotationEntity();
        quotationEntity.setDate(new Date());
        quotationEntity.setCurrentPrice(new BigDecimal(currentPriceInfo.getUsdbrl().getBid()));
        quotationEntity.setPctChange(currentPriceInfo.getUsdbrl().getPctChange());
        quotationEntity.setPair("USD-BRL");
        quotationRepository.persist(quotationEntity);
    }
}
