package com.jobNinza.util;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.jobNinza.clients.LeverClient;
import com.jobNinza.service.ATSClient;
@Component
public class ATSClientFactory {
	private final LeverClient leverClient;
	private final Map<String, ATSClient> clients;
	public ATSClientFactory(List<ATSClient> clients, LeverClient leverClient) {
		this.leverClient = leverClient;
		this.clients = clients.stream()
                .collect(Collectors.toMap(
                        ATSClient::getAtsType,
                        Function.identity()
                ));
    }

    public ATSClient getClient(String atsType) {

        ATSClient client = clients.get(atsType);

        if (client == null) {
        	return leverClient;
        }

        return client;
    }
}