package de.htw.berlin.landregisterbackendapplication.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.berlin.landregisterbackendapplication.hyperledger.client.CAClient;
import de.htw.berlin.landregisterbackendapplication.hyperledger.client.ChannelClient;
import de.htw.berlin.landregisterbackendapplication.hyperledger.client.FabricClient;
import de.htw.berlin.landregisterbackendapplication.models.*;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static de.htw.berlin.landregisterbackendapplication.hyperledger.config.Config.*;
import static java.util.Collections.emptyList;

@Component
public class LandRegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandRegisterService.class);

    private static final LandRegister tmp = LandRegister.builder()
            .docType("landRegister")
            .titlePage(TitlePage.builder()
                    .districtCourt("districtCourt")
                    .landRegistryDistrict("landRegistryDistrict")
                    .sheetNumber("sheetNumber")
                    .build())
            .inventoryRegister(InventoryRegister.builder()
                    .subdistrict("subdistrict")
                    .hall("hall")
                    .parcel("parcel")
                    .economicType("economicType")
                    .location("location")
                    .size("size")
                    .build())
            .owners(Arrays.asList(
                    Owner.builder()
                            .identityNumber("identityNumber_1")
                            .title("title_1")
                            .firstname("firstname_1")
                            .lastname("lastname_1")
                            .dateOfBirth("dateOfBirth_1")
                            .postcode("postcode_1")
                            .city("city_1")
                            .street("street_1")
                            .streetnumber("streetnumber_1")
                            .build(),
                    Owner.builder()
                            .identityNumber("identityNumber_2")
                            .title("title_2")
                            .firstname("firstname_2")
                            .lastname("lastname_2")
                            .dateOfBirth("dateOfBirth_2")
                            .postcode("postcode_2")
                            .city("city_2")
                            .street("street_2")
                            .streetnumber("streetnumber_2")
                            .build()
            ))
            .reservationNote(false)
            .build();

    public List<LandRegister> queryAllLandRegisters() {
        try {

            FabricClient fabricClient = new FabricClient();

            ChannelClient channelClient = fabricClient.createChannelClient(CHANNEL_NAME);
            Channel channel = channelClient.getChannel();
            Peer peer = fabricClient.getInstance().newPeer(ORG1_PEER_0, ORG1_PEER_0_URL);
            Orderer orderer = fabricClient.getInstance().newOrderer(ORDERER_NAME, ORDERER_URL);
            channel.addPeer(peer);
            channel.addOrderer(orderer);
            channel.initialize();

            LOGGER.info("Query all land registers ...");
            Collection<ProposalResponse> responsesQuery = channelClient.queryByChainCode(CHAINCODE_1_NAME, FUNCTION_QUERY_ALL_LANDREGISTERS, null);

            return responsesQuery.stream()
                    .map(this::proposalResponseToString)
                    .findFirst()
                    .map(s -> transformToLandRegisterWrapper(s).stream()
                            .map(LandRegisterWrapper::getRecord)
                            .collect(Collectors.toList()))
                    .orElse(emptyList());
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
        return emptyList();
    }

    private String proposalResponseToString(ProposalResponse proposalResponse) {
        try {
            return new String(proposalResponse.getChaincodeActionResponsePayload());
        } catch (InvalidArgumentException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private List<LandRegisterWrapper> transformToLandRegisterWrapper(String landRegisterAsString) {
        try {
            return new ObjectMapper().readValue(landRegisterAsString, new TypeReference<List<LandRegisterWrapper>>() {
            });
        } catch (IOException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return emptyList();
        }
    }
}
