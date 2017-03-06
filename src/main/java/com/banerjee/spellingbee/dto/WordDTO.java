package com.banerjee.spellingbee.dto;

import java.util.List;

/**
 * Created by somobanerjee on 2/26/17.
 */
public class WordDTO {
    private String value;
    private List<String> categories;
    private Integer difficulty;
    private String origin;
    private String root;
    private String prefix;
    private String base;
    private String suffix;
    private String type;
    private String audioUrl;
    @Override
    public String toString() {
        return "WordDTO{" +
            "value='" + value + '\'' +
            ", categories=" + categories +
            ", difficulty=" + difficulty +
            ", origin='" + origin + '\'' +
            ", root='" + root + '\'' +
            ", prefix='" + prefix + '\'' +
            ", base='" + base + '\'' +
            ", suffix='" + suffix + '\'' +
            '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
