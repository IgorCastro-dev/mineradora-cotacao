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
import java.math.MathContext;
import java.math.RoundingMode;
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
                    .currencyPrice(new BigDecimal(currentPriceInfo.getUSDBRL().getBid()))
                    .date(new Date())
                    .build()
            );
        }
    }

    private boolean updateCurentPrice(CurrencyPriceDTO currentPriceInfo) {
        Optional<QuotationEntity> lastQuotation = quotationRepository.findLastQuotation();
        BigDecimal currentPrice = new BigDecimal(currentPriceInfo.getUSDBRL().getBid());
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
        String bidString = currentPriceInfo.getUSDBRL().getBid(); // "5.4207"
        BigDecimal bid = new BigDecimal(bidString).setScale(4, RoundingMode.HALF_UP);
        quotationEntity.setCurrentPrice(bid);
        quotationEntity.setPctChange(currentPriceInfo.getUSDBRL().getPctChange());
        quotationEntity.setPair("USD-BRL");
        quotationRepository.persist(quotationEntity);
    }
}
