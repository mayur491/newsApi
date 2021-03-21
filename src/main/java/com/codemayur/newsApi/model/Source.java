package com.codemayur.newsApi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Source {

    private String id;
    private String name;
    private String description;
    private String url;
    private String category;
    private String language;
    private String country;

}
