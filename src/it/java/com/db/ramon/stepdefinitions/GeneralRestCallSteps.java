package com.db.ramon.stepdefinitions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.db.ramon.BehaviourState;
import com.db.ramon.constants.StateConstants;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class GeneralRestCallSteps {
    private final RestTemplate restTemplate;
    private final BehaviourState behaviourState;
    private final ObjectMapper objectMapper;

    GeneralRestCallSteps(
            final RestTemplate restTemplate, final BehaviourState behaviourState, final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.behaviourState = behaviourState;
        this.objectMapper = objectMapper;
    }

    @Given("path {string}")
    public void givenPath(final String path) {
        final UriComponentsBuilder uriComponentsBuilder =
                behaviourState.fetchValue(StateConstants.URI_BUILDER, UriComponentsBuilder.class);
        behaviourState.putResult(StateConstants.URI_BUILDER, () -> uriComponentsBuilder.replacePath(path));
    }

    @Given("query parameter {string} is {string}")
    public void queryParameter(final String parameterName, final String parameterValue) {
        behaviourState.map(
                StateConstants.URI_BUILDER,
                UriComponentsBuilder.class,
                spec -> spec.queryParam(parameterName, parameterValue));
    }

    @Given("passing csv file as query param")
    public void fileAsQueryParameter() throws IOException {
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        FileSystemResource value = new FileSystemResource(new ClassPathResource("NACE.csv").getFile());
        map.add("file", value);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        final UriComponentsBuilder uriComponentsBuilder =
                behaviourState.fetchValue(StateConstants.URI_BUILDER, UriComponentsBuilder.class);
        behaviourState.putResult(
                StateConstants.RESPONSE_ENTITY,
                () -> restTemplate.exchange(
                        uriComponentsBuilder.toUriString(), HttpMethod.POST, requestEntity, String.class));
        final var response = behaviourState.fetchValue(StateConstants.RESPONSE_ENTITY, ResponseEntity.class);
    }

    @When("request body is {string}")
    public void requestBody(final String bodyName) throws IOException {
        final var body = objectMapper.readValue(
                Resources.toString(
                        Resources.getResource(String.format("requestBody/%s.json", bodyName)), StandardCharsets.UTF_8),
                Map.class);
        behaviourState.putResult(StateConstants.REQUEST_BODY, () -> new HttpEntity(body));
    }

    @When("a request is made using method {string}")
    public void sendRequest(final String method) {
        final UriComponentsBuilder uriComponentsBuilder =
                behaviourState.fetchValue(StateConstants.URI_BUILDER, UriComponentsBuilder.class);
        behaviourState.putResult(StateConstants.RESPONSE_ENTITY, () -> sendRestCall(method, uriComponentsBuilder));
    }

    @Then("the request should respond with the status code {int}")
    public void shouldResponseWithStatusCode(final int statusCode) {
        final var response = behaviourState.fetchValue(StateConstants.RESPONSE_ENTITY, ResponseEntity.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.valueOf(statusCode)));
    }

    @Then("the response body should match {string}")
    public void responseBodyShouldMatch(final String responseBody) throws IOException {
        final var response = behaviourState.fetchValue(StateConstants.RESPONSE_ENTITY, ResponseEntity.class);
        final var actualMap = objectMapper.readTree((String) response.getBody());
        final var expectedMap = objectMapper.readTree(Resources.toString(
                Resources.getResource(String.format("responseBody/%s.json", responseBody)), StandardCharsets.UTF_8));
        assertThat(actualMap, equalTo(expectedMap));
    }

    private ResponseEntity<String> sendRestCall(final String method, final UriComponentsBuilder uriComponentsBuilder) {
        return restTemplate.exchange(
                uriComponentsBuilder.toUriString(),
                HttpMethod.resolve(method.toUpperCase()),
                method.equalsIgnoreCase("GET")
                        ? null
                        : behaviourState.fetchValue(StateConstants.REQUEST_BODY, HttpEntity.class),
                String.class);
    }
}
