package org.mineradora;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mineradora.client.CurrencyPriceCliente;
import org.mineradora.dto.CurrencyPriceDTO;

@ApplicationScoped
public class TestService {

    @Inject
    @RestClient
    CurrencyPriceCliente currencyPriceCliente;

    public void debugApiCall() {
        try {
            System.out.println("🔍 Testando chamada API...");

            // Teste 1: Chamada direta
            CurrencyPriceDTO response = currencyPriceCliente.getPricePair("USD-BRL");
            System.out.println("Resposta completa: " + response);

            if (response == null) {
                System.out.println("❌ Resposta TOTALMENTE nula");
                return;
            }

            if (response.getUSDBRL() == null) {
                System.out.println("❌ USDBRL é nulo, mas response não é: " + response);

                // Teste 2: Verifique o JSON bruto
                try {
                    String rawJson = currencyPriceCliente.getPricePairString("USD-BRL");
                    System.out.println("📦 JSON bruto: " + rawJson);
                } catch (Exception e) {
                    System.out.println("❌ Erro ao pegar JSON bruto: " + e.getMessage());
                }
            } else {
                System.out.println("✅ Sucesso! Bid: " + response.getUSDBRL().getBid());
            }

        } catch (Exception e) {
            System.out.println("💥 Erro na chamada: " + e.getMessage());
            e.printStackTrace();
        }
    }
}