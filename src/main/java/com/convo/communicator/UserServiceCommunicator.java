package com.convo.communicator;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.convo.datamodel.User;

public class UserServiceCommunicator {
	private static final String HOST = "http://localhost:8080";

	public List<User> getUsersDetails(List<String> userIds) {
		JSONObject request = new JSONObject();
		request.append("userIds", ObjectUtils.firstNonNull(userIds, new ArrayList<>()));
		JSONObject usersListResponse = getResponse("/user/v1/list", request, RequestMethod.POST, JSONObject.class);
		JSONArray usersJsonArray = usersListResponse.getJSONArray("userIds");
		List<User> usersDetails = usersJsonArray.toList().stream().map(obj -> (User) obj).collect(Collectors.toList());
		return ObjectUtils.firstNonNull(usersDetails, new ArrayList<>());
	}

	public User authenticate(String token) throws Exception {
		JSONObject request = new JSONObject();
		request.append("token", token);
		JSONObject authenticationResponse = getResponse("/user/v1/authenticate", request, RequestMethod.POST,
				JSONObject.class);
		if (authenticationResponse.getJSONObject("user") != null) {
			return User.createUserFromJsonObjet(authenticationResponse.getJSONObject("user"));
		} else {
			String error = (String) authenticationResponse.getJSONArray("errors").toList().get(0);
			throw new Exception("Failed to authenticate the user. Error message is: " + error);
		}
	}

	private <T, RT> RT getResponse(String endpoint, T input, RequestMethod requestMethod, Class<RT> returnType) {
		if (requestMethod.equals(RequestMethod.POST)) {
			RestTemplate restTemplate = new RestTemplate();
			RT response = restTemplate.postForObject(URI.create(HOST + endpoint), input, returnType);
			return response;
		}
		return null;
	}
}
