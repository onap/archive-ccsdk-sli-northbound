package org.onap.ccsdk.sli.northbound.vlantagapi.service.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AssignVlanTagRequest {

    private @Valid List<AssignVlanTagRequestInput> input = new ArrayList<AssignVlanTagRequestInput>();

    /**
     **/
    public AssignVlanTagRequest input(List<AssignVlanTagRequestInput> input) {
        this.input = input;
        return this;
    }

    @ApiModelProperty(value = "")
    @JsonProperty("input")
    public List<AssignVlanTagRequestInput> getInput() {
        return input;
    }

    public void setInput(List<AssignVlanTagRequestInput> input) {
        this.input = input;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AssignVlanTagRequest assignVlanTagRequest = (AssignVlanTagRequest) o;
        return Objects.equals(input, assignVlanTagRequest.input);
    }

    @Override
    public int hashCode() {
        return Objects.hash(input);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AssignVlanTagRequest {\n");

        sb.append("    input: ").append(toIndentedString(input)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
