package com.casisd.feedback.config;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FeedbackEnumerate {
    @Getter @Setter private List<String> status = new ArrayList<>();
    @Getter @Setter private List<String> treatmentStatus = new ArrayList<>();
}