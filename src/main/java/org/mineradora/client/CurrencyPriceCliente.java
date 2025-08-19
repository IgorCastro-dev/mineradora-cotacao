package org.mineradora.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mineradora.dto.CurrencyPriceDTO;

@RestClient
@Path("/last")
public interface CurrencyPriceCliente {

    @GET
    @Path("/{pair}")
    CurrencyPriceDTO  getPricePair(@PathParam("pair") String pair);
}
