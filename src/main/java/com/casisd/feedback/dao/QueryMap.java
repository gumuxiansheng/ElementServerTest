package com.casisd.feedback.dao;

import java.time.LocalDateTime;

import com.casisd.feedback.config.Config;
import com.casisd.feedback.util.LocalDateAdapter;
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
    // 只查询已经分发的条目
    @Getter @Setter private Boolean distributedExclusive;
    // 只查询未分发的条目，此条件只有在distributedExclusive为false的时候有效。
    @Getter @Setter private Boolean undistributedExclusive;
    @Setter private Integer limitCount = 0;

    public Integer getLimitCount() {
        if (limitCount == 0 && (getInstitution() == null || getInstitution().isEmpty())){
            limitCount = new Config().getFeedbackQueryLimitCount();
        }
        return limitCount;
    }


}
