package org.mineradora.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.mineradora.dto.CurrencyPriceDTO;

@RegisterRestClient(baseUri = "https://economia.awesomeapi.com.br")
@Path("/last")
public interface CurrencyPriceCliente {

    @GET
    @Path("/{pair}")
    CurrencyPriceDTO  getPricePair(@PathParam("pair") String pair);

    @GET
    @Path("/{pair}")
    @Produces(MediaType.TEXT_PLAIN)
    String getPricePairString(@PathParam("pair") String pair);
}
