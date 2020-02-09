package com.element.demo.config;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FeedbackSchema {

    @Getter @Setter private String name;
    @Getter @Setter private List<String> display;
    @Getter @Setter private boolean editable;
}