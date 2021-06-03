package com.vluee.cloud.orgs.application.command.handler;

import com.vluee.cloud.commons.canonicalmodel.publishedlanguage.AggregateId;
import com.vluee.cloud.commons.cqrs.annotations.CommandHandlerAnnotation;
import com.vluee.cloud.commons.cqrs.command.handler.CommandHandler;
import com.vluee.cloud.commons.ddd.annotations.application.ApplicationService;
import com.vluee.cloud.orgs.application.command.AddHotelCommand;
import com.vluee.cloud.orgs.core.hotel.domain.Hotel;
import com.vluee.cloud.orgs.core.hotel.domain.HotelRepository;
import com.vluee.cloud.orgs.core.hotel.domain.events.HotelAddedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@ApplicationService
@CommandHandlerAnnotation
@EnableBinding(Source.class)
@Slf4j
public class AddHotelCommandHandler implements CommandHandler<AddHotelCommand, Void> {

    private final HotelRepository hotelRepository;

    private final Source source;

    public Void handle(AddHotelCommand addHotelCommand) {
        Hotel hotel = new Hotel(AggregateId.generate(), addHotelCommand.getHotelName(), addHotelCommand.getHotelName(), addHotelCommand.getHotelState());
        hotelRepository.save(hotel);

        //代码应该放置在何处 是个需要考虑的问题？ TODO

//        DomainEventPublisherFactory.getPublisher().publish(new HotelAddedEvent(hotel.getAggregateId()));
        source.output().send(MessageBuilder.withPayload(new HotelAddedEvent(hotel.getAggregateId())).build());
        log.info("Send event successfully");
        return null;
    }
}