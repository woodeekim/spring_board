package org.woodee.sample;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@ToString
@Getter
@RequiredArgsConstructor
public class SampleHotel {

    private final Chef chef;


}
