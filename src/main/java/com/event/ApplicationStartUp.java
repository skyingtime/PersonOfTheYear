package com.event;

import com.cache.NomineeStore;
import com.util.FileParser;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by yangliu on 18/12/2016.
 */
@Component
public class ApplicationStartUp implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        NomineeStore.createNomineeMap(FileParser.parseFile(this.getClass().getClassLoader().getResource("people.txt").getPath()));
    }
}
