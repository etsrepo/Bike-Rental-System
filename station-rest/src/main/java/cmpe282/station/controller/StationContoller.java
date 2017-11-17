package cmpe282.station.controller;

import static cmpe282.message.Topics.TOPIC_RESERVATION_REQUEST;
import static cmpe282.station.config.UrlConstants.STATION;

import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.core.ApiFuture;
import com.google.cloud.ServiceOptions;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;

import cmpe282.message.ReservRequestMsg;
import cmpe282.message.ReservResponseMsg;
import cmpe282.station.config.JsonResponse;
import cmpe282.station.entity.Station;
import cmpe282.station.service.StationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@Api(tags = {"Station"})
@SwaggerDefinition(tags = { @Tag(name="Sation Controller", description="Station Controller Endpoints")})
public class StationContoller extends AbstractController {

    private static Logger LOGGER = Logger.getLogger(StationContoller.class.getName());

    @Autowired
    StationService stationSvc;

    @ApiOperation(value = "Get Station Detail by ID")
    @GetMapping(STATION + "{station_id}")
    public ResponseEntity<Station> getStationDetail(@PathVariable int station_id) throws JsonProcessingException {
	ObjectMapper mapper = new ObjectMapper();
	Station station = stationSvc.findStationDetail(station_id);
	if (station != null)
	    return success(station);

	return notFound(station);
    }

    @ApiOperation(value = "Reserve a bike")
    @PostMapping(STATION)
    public ResponseEntity<ReservResponseMsg> reserveBike(@RequestBody ReservRequestMsg reserveRequestMsg) {
	return null;
    }

    @ApiOperation(value = "Publish a message")
    @PostMapping(STATION + "/publish")
    public ResponseEntity<String> publishMsg(@RequestBody String msg) throws Exception {
	String PROJECT_ID = ServiceOptions.getDefaultProjectId();
	LOGGER.info(PROJECT_ID);
	TopicName topicName = TopicName.newBuilder()
		.setProject(PROJECT_ID)
		.setTopic(TOPIC_RESERVATION_REQUEST.name())
		.build();
	Publisher publisher = Publisher.newBuilder(topicName).build();
	ApiFuture<String> messageId = publishMessage(publisher, msg);
	return success(messageId.get());
    }

    private static ApiFuture<String> publishMessage(Publisher publisher, String message) throws Exception {
	// convert message to bytes
	ByteString data = ByteString.copyFromUtf8(message);
	PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
	return publisher.publish(pubsubMessage);
    }

}
