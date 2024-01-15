package com.green.greengram4.feed.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder

public class FeedInsProcDto {
    private int iuser;
    private int ifeed;
    private String contents;
    private String location;

}
