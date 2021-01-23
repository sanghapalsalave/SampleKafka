package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.SampleKafkaModel;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/kafka")
public class SampleKafkaProducerController {
	private KafkaTemplate<String, String> kafkaTemplate;
	private Gson jsonConverter;

	@Autowired
	public SampleKafkaProducerController(KafkaTemplate<String, String> kafkaTemplate, Gson jsonConverter) {
		this.kafkaTemplate = kafkaTemplate;
		this.jsonConverter = jsonConverter;
	}

	// @RequestMapping(value="/kafka",method = RequestMethod.POST,consumes
	// =MediaType.APPLICATION_JSON_VALUE)
	@PostMapping
	public void post(@RequestBody SampleKafkaModel sampleKafkaModel) {
		System.out.println("Inside controller");
		kafkaTemplate.send("myTopic", jsonConverter.toJson(sampleKafkaModel));
	}

	@KafkaListener(topics = "myTopic")
	public void getFromKafka(String sampleKafkaModel) {

		System.out.println(sampleKafkaModel);

		SampleKafkaModel simpleModel1 = (SampleKafkaModel) jsonConverter.fromJson(sampleKafkaModel,
				SampleKafkaModel.class);

		System.out.println(simpleModel1.toString());
	}
}
