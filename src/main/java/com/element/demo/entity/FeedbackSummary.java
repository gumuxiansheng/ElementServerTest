package com.element.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class FeedbackSummary {

    @Getter @Setter List<FeedbackColumnSummary> summary = new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    public static class FeedbackColumnSummary {
        @Getter @Setter private String colName;
        @Getter @Setter private List<CategorySummaryItem> colSummary;
    }

    public static class CategorySummaryItem {
        @SerializedName("Category")
        @Getter @Setter private String category;
        @SerializedName("Count")
        @Getter @Setter private int count;
    }

    public void addColumnSummary(FeedbackColumnSummary feedbackColumnSummary){
        summary.add(feedbackColumnSummary);
    }

    public void removeColumnSummary(FeedbackColumnSummary feedbackColumnSummary){
        summary.remove(feedbackColumnSummary);
    }

    public void removeColumnSummary(int index){
        summary.remove(index);
    }
}