package com.element.demo.dao;

import java.time.LocalDateTime;

import com.element.demo.config.Config;
import com.element.demo.util.LocalDateAdapter;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class QueryMap {
    @JsonFormat(pattern = LocalDateAdapter.GLOBALTIMEFORMAT)
    @Getter @Setter private LocalDateTime from;
    @JsonFormat(pattern = LocalDateAdapter.GLOBALTIMEFORMAT)
    @Getter @Setter private LocalDateTime to;
    @Getter @Setter private String institution;
    @Getter @Setter private Boolean distributedExclusive;
    @Setter private Integer limitCount = 0;

    public Integer getLimitCount() {
        if (limitCount == 0 && (getInstitution() == null || getInstitution().isEmpty())){
            limitCount = new Config().getFeedbackQueryLimitCount();
        }
        return limitCount;
    }


}
