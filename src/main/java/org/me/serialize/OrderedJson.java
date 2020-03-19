package org.me.serialize;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = JsonReader.class)
public class OrderedJson {
}
