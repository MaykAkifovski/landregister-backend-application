package de.htw.berlin.landregisterbackendapplication.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.htw.berlin.landregisterbackendapplication.hyperledger.client.ChannelClient;
import de.htw.berlin.landregisterbackendapplication.hyperledger.client.FabricClient;
import de.htw.berlin.landregisterbackendapplication.models.LandRegister;
import de.htw.berlin.landregisterbackendapplication.models.LandRegisterWrapper;
import de.htw.berlin.landregisterbackendapplication.services.LandRegisterService;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static de.htw.berlin.landregisterbackendapplication.hyperledger.config.Config.*;
import static de.htw.berlin.landregisterbackendapplication.models.DefaultLandRegister.DEFAULT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

@Service
public class LandRegisterServiceImpl implements LandRegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LandRegisterService.class);

    @Override
    public List<LandRegister> queryAllLandRegisters() {
        List<LandRegister> landRegisters;

        try {
            ChannelClient channelClient = evalChannelClient();
            LOGGER.info("Query all land registers ...");
            Collection<ProposalResponse> responsesQuery = channelClient.queryByChainCode(CHAINCODE_1_NAME, FUNCTION_QUERY_ALL_LANDREGISTERS, null);
            landRegisters = extractLandRegisters(responsesQuery);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            landRegisters = singletonList(DEFAULT);
        }

        return landRegisters;
    }

    private List<LandRegister> extractLandRegisters(Collection<ProposalResponse> responsesQuery) {
        return responsesQuery.stream()
                .map(this::proposalResponseToString)
                .findFirst()
                .map(this::transformToLandRegisters)
                .orElse(emptyList());
    }

    private List<LandRegister> transformToLandRegisters(String landRegisterWrapperAsString) {
        return transformToLandRegisterWrapper(landRegisterWrapperAsString)
                .stream()
                .map(LandRegisterWrapper::getRecord)
                .collect(Collectors.toList());
    }

    private String proposalResponseToString(ProposalResponse proposalResponse) {
        try {
            return new String(proposalResponse.getChaincodeActionResponsePayload());
        } catch (InvalidArgumentException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    private List<LandRegisterWrapper> transformToLandRegisterWrapper(String landRegistersAsString) {
        try {
            LOGGER.info("Transforming {} to {}", landRegistersAsString, LandRegisterWrapper.class);
            List<LandRegisterWrapper> result = new ObjectMapper().readValue(landRegistersAsString, new TypeReference<List<LandRegisterWrapper>>() {
            });
            LOGGER.info("Result of transforming String to LandRegisterWrapper {}", result);
            return result;
        } catch (IOException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return emptyList();
        }
    }

    @Override
    public LandRegister queryLandRegister(String id) {
        try {
            ChannelClient channelClient = evalChannelClient();
            LOGGER.info("Query land registers with Id = {}", id);
            Collection<ProposalResponse> responsesQuery = channelClient.queryByChainCode(CHAINCODE_1_NAME, FUNCTION_QUERY_LANDREGISTER, new String[]{id});

            return responsesQuery.stream()
                    .map(this::proposalResponseToString)
                    .findFirst()
                    .map(this::transformToLandRegister)
                    .orElse(DEFAULT);
        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
        }
        return DEFAULT;

    }

    private LandRegister transformToLandRegister(String landRegisterAsString) {
        try {
            LOGGER.info("Transforming {} to {}", landRegisterAsString, LandRegister.class);
            return new ObjectMapper().readValue(landRegisterAsString, LandRegister.class);
        } catch (IOException e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return DEFAULT;
        }
    }

    private ChannelClient evalChannelClient() throws IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, CryptoException, TransactionException {
        FabricClient fabricClient = new FabricClient();
        ChannelClient channelClient = fabricClient.createChannelClient(CHANNEL_NAME);
        Channel channel = channelClient.getChannel();
        Peer peer = fabricClient.getInstance().newPeer(ORG1_PEER_0, ORG1_PEER_0_URL);
        Orderer orderer = fabricClient.getInstance().newOrderer(ORDERER_NAME, ORDERER_URL);
        channel.addPeer(peer);
        channel.addOrderer(orderer);
        channel.initialize();
        return channelClient;
    }

}
