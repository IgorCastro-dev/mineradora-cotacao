package org.mineradora.message;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.mineradora.dto.QuotationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class KafkaEvents {

    private final Logger Log = LoggerFactory.getLogger(KafkaEvents.class);

    @Channel("quotation-channel")
    private Emitter<QuotationDto> emitter;

    @Inject
    private void sendMessage(QuotationDto quotationDto) {
        Log.info("---enviando a mensagem quotation ----");
        emitter.send(quotationDto).toCompletableFuture().join();
    }
}
