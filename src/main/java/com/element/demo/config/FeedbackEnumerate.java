package com.element.demo.config;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEnumerate {
    @Getter @Setter private List<String> status;
    @Getter @Setter private List<String> treatmentStatus;
}